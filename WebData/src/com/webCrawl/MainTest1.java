package com.webCrawl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.DataHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.extract.IExtract;
import com.webCrawl.store.IStoreDB;
import com.webCrawl.store.IStorePage;

public class MainTest1 {
	
	static CrawlBug crawlBug;
	
	static IStorePage IStorePage;
	
	static IExtract commonExtract;
	
	static IStoreDB storeDB;
	
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
		
		crawlBug = new CrawlBug();
		crawlBug.setBugId(2);
		crawlBug.setSavePath("D:/crawl/"+crawlBug.getBugId()+"/");
		crawlBug.setSeedUrl("http://localhost:9080/cms/");
		crawlBug.setDomain("localhost");
		crawlBug.setBugName("bug Num.2");
		
		storeDB = context.getBean("storeDB",IStoreDB.class);
		storeDB.init(crawlBug);
		
		commonExtract = context.getBean("commonExtract",IExtract.class);
		
		IStorePage = context.getBean("storePageHtml",IStorePage.class);
		IStorePage.init(crawlBug);

		storeDB.save(crawlBug.getSeedUrl());
		
		new MainTest1().handle();
	}
	
	public void handle() throws InterruptedException{
		
		System.out.println(".............");
		
		String newUrl = storeDB.returnLink();
		
		if(DataHandle.isNullOrEmpty(newUrl)){
			return;
		}
		
		String savePath = IStorePage.store(newUrl);
		
		List<Object> urlList = commonExtract.extractHtml(savePath);
		
		System.out.println(urlList.size());
		
		storeDB.save(urlList);
		
		handle();
		
	}
	
}
