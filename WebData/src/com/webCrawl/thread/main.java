package com.webCrawl.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.DataHandle;
import com.util.HtmlHandle;
import com.util.Log;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.extract.IExtract;
import com.webCrawl.filter.ILinkFilter;
import com.webCrawl.nofilter.INoFilter;
import com.webCrawl.store.IStoreDB;
import com.webCrawl.store.IStorePage;

public class main {

	public static void main(String[] args) {
		
		try{
			
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
	
			/** 设置过滤 */
			ILinkFilter domainFilter = context.getBean("domainFilter",ILinkFilter.class);
			domainFilter.setCrawlBug(crawlBug);
			//ILinkFilter testFilter = context.getBean("testFilter",ILinkFilter.class);
			final List<ILinkFilter> linkFilterList = new ArrayList<ILinkFilter>();
			linkFilterList.add(domainFilter);
			//linkFilterList.add(testFilter);
			
			/** 设置不过滤 */
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
							try{
								String newUrl = storeDB.returnLink();
								
								Log.Info("正在处理url : " + newUrl);
								
								if(DataHandle.isNullOrEmpty(newUrl) && flag){
									break;
								}else if(DataHandle.isNullOrEmpty(newUrl)){
									flag = true;
									Thread.sleep(5000);
									continue;
								}
								
								String savePath = storePage.store(newUrl);
								
								Log.Info("存储url成功 : " + newUrl);
								
								if(savePath != null){
		
									Set<String> urlList = commonExtract.extractHtml(savePath);
									
									Log.Info("解析"+newUrl+"获得 : " + urlList.size() + "个链接");
									
									urlList = returnUrlList(urlList,noFilterList,linkFilterList,crawlBug);
									
									Log.Info("过滤解析"+newUrl+"获得 : " + urlList.size() + "个链接");
									
									storeDB.save(urlList);
									
									Log.Info(newUrl + "保存成功");
									
								}
							}catch(Exception e){
								Log.Error(e);
								continue;
							}
						}
						
					}
				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static Set<String> returnUrlList(Set<String> urlList,List<INoFilter> noFilterList,List<ILinkFilter> linkFilterList,CrawlBug crawlBug){
		Set<String> objList = new HashSet<String>();
		if(urlList != null && urlList.size() > 0){
			for(Object obj : urlList){
				String urlString = obj.toString();
				Log.Info("处理是否过滤url : " + urlString);
				try{
					urlString = HtmlHandle.joinUrl(crawlBug.getSeedUrl(), urlString);
					CrawlLink crawlLink = new CrawlLink();
					crawlLink.setLink(urlString);
					crawlLink.setCrawlBug(crawlBug);
					crawlLink = HtmlHandle.parseUrlToCrawlLink(crawlLink);
					Log.Info("url statusCode : " + crawlLink.getStatusCode());
					if(crawlLink.getStatusCode() == 200){
						boolean flag = false;
						
						for(INoFilter noFilter : noFilterList){
							noFilter.setCrawlLink(crawlLink);
							if(noFilter.noFilter(urlString)){
								Log.Info("url : " + urlString + "符合要求,放入列表");
								flag = true;
								break;
							}
						}
						
						if(!flag){
							flag = true;
							for(ILinkFilter linkFilter : linkFilterList){
								linkFilter.setCrawlLink(crawlLink);
								if(!linkFilter.filter(urlString)){
									Log.Info("url : " + urlString + "不符合要求，丢弃");
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
				}catch(Exception e){
					Log.Error(urlString + "解析异常",e);
					continue;
				}
			}
		}
		return objList;
	}
	
}
