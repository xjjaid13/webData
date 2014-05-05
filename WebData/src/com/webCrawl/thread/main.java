package com.webCrawl.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.DataHandle;
import com.webCrawl.MainTest1;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.extract.IExtract;
import com.webCrawl.store.IStoreDB;
import com.webCrawl.store.IStorePage;

public class main {

	static CrawlBug crawlBug;
	
	static IStorePage IStorePage;
	
	static IExtract commonExtract;
	
	static IStoreDB storeDB;
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
		
		crawlBug = new CrawlBug();
		crawlBug.setBugId(2);
		crawlBug.setSavePath("D:/crawl2/"+crawlBug.getBugId()+"/");
		crawlBug.setSeedUrl("http://192.168.199.164:9080/cms/");
		crawlBug.setDomain("192.168.199.164");
		crawlBug.setBugName("bug Num.2");
		
		storeDB = context.getBean("storeDB",IStoreDB.class);
		storeDB.init(crawlBug);
		
		commonExtract = context.getBean("commonExtract",IExtract.class);
		
		IStorePage = context.getBean("storePageHtml",IStorePage.class);
		IStorePage.init(crawlBug);

		storeDB.save(crawlBug.getSeedUrl());
		
		long s1 = System.currentTimeMillis();
		
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		
		for (int i = 0; i < 10; i++) {
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
						
						String savePath = IStorePage.store(newUrl);
						
						List<Object> urlList = commonExtract.extractHtml(savePath);
						
						storeDB.save(urlList);
					} 
					
				}
			});
		}
		
		long s2 = System.currentTimeMillis();
		
		//aaaaaaaaaaa
		System.out.println(s2 - s1);
		
	}
	
}
