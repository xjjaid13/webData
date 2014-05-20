package com.webCrawl.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.DataHandle;
import com.util.HtmlHandle;
import com.webCrawl.MainTest1;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.extract.IExtract;
import com.webCrawl.filter.ILinkFilter;
import com.webCrawl.nofilter.INoFilter;
import com.webCrawl.store.IStoreDB;
import com.webCrawl.store.IStorePage;

public class main {

	
	public static void main(String[] args) {
		
		/** spring上下文 */
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
		
		/** 爬虫实体 */
		final CrawlBug crawlBug = new CrawlBug();
		crawlBug.setBugId(2);
		crawlBug.setSavePath("D:/crawl2/"+crawlBug.getBugId()+"/");
		crawlBug.setSeedUrl("http://localhost:9080/cms/");
		crawlBug.setDomain("localhost");
		crawlBug.setBugName("bug Num.2");
		
		/** 存储数据库 */
		final IStoreDB storeDB = context.getBean("storeDB",IStoreDB.class);
		storeDB.setCrawlBug(crawlBug);
		
		/** 抽取页面 */
		final IExtract commonExtract = context.getBean("commonExtract",IExtract.class);
		
		/** 存储文件 */
		final IStorePage storePage = context.getBean("storePageHtml",IStorePage.class);
		storePage.setCrawlBug(crawlBug);

		/** 设置过滤和不过滤 */
		ILinkFilter domainFilter = context.getBean("domainFilter",ILinkFilter.class);
		ILinkFilter testFilter = context.getBean("testFilter",ILinkFilter.class);
		final List<ILinkFilter> linkFilterList = new ArrayList<ILinkFilter>();
		linkFilterList.add(domainFilter);
		linkFilterList.add(testFilter);
		
		final List<INoFilter> noFilterList = new ArrayList<INoFilter>();
		INoFilter cssNoFilter = context.getBean("cssNoFilter",INoFilter.class);
		INoFilter jsNoFilter = context.getBean("jsNoFilter",INoFilter.class);
		INoFilter picNoFilter = context.getBean("picNoFilter",INoFilter.class);
		noFilterList.add(cssNoFilter);
		noFilterList.add(jsNoFilter);
		noFilterList.add(picNoFilter);
		
		/** 存储种子节点 */
		storeDB.save(crawlBug.getSeedUrl());
		
		/** 创建线程池 */
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		
		for (int i = 0; i < 1; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					boolean flag = false;
					while(true){
						
						String newUrl = storeDB.returnLink();
						
						if(DataHandle.isNullOrEmpty(newUrl) && flag){
							break;
						}else if(DataHandle.isNullOrEmpty(newUrl)){
							flag = true;
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue;
						}
						
						String savePath = storePage.store(newUrl);
						
						if(savePath != null){

							List<String> urlList = commonExtract.extractHtml(savePath);
							
							urlList = returnUrlList(urlList,noFilterList,linkFilterList,crawlBug);
							
							storeDB.save(urlList);
							
						}
						
					}
					
				}
			});
		}
		
	}
	
	public static List<String> returnUrlList(List<String> urlList,List<INoFilter> noFilterList,List<ILinkFilter> linkFilterList,CrawlBug crawlBug){
		List<String> objList = new ArrayList<String>();
		if(urlList != null && urlList.size() > 0){
			for(Object obj : urlList){
				String urlString = obj.toString();
				if(!urlString.startsWith("http") || !urlString.startsWith("www")){
					urlString = HtmlHandle.joinUrl(crawlBug.getSeedUrl(), urlString);
				}
				System.out.println(urlString);
				CrawlLink crawlLink = new CrawlLink();
				crawlLink.setLink(urlString);
				crawlLink = HtmlHandle.parseUrlToCrawlLink(crawlLink);
				
				if(crawlLink.getStatusCode() == 200){
					boolean flag = false;
					
					for(INoFilter noFilter : noFilterList){
						noFilter.setCrawlLink(crawlLink);
						if(noFilter.noFilter(urlString)){
							flag = true;
							break;
						}
					}
					
					if(!flag){
						flag = true;
						for(ILinkFilter linkFilter : linkFilterList){
							linkFilter.setCrawlLink(crawlLink);
							if(!linkFilter.filter(urlString)){
								flag = false;
								break;
							}
						}
					}
					
					if(flag){
						objList.add(urlString);
					}
				}else{
					objList.add(urlString);
				}
			}
		}
		return objList;
	}
	
}
