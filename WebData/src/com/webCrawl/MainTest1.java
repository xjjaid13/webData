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
		crawlBug.setBugId(1);
		crawlBug.setSavePath("D:/crawl/1/");
		crawlBug.setSeedUrl("http://www.csdn.net/article/2014-04-09/2819214-Oculus-and-HMD");
		crawlBug.setDomain("csdn.net");
		crawlBug.setBugName("bug Num.1");
		
		storeDB = context.getBean("storeDB",IStoreDB.class);
		storeDB.init(crawlBug);
		
		commonExtract = context.getBean("commonExtract",IExtract.class);
		
		IStorePage = context.getBean("storePageHtml",IStorePage.class);
		IStorePage.init(crawlBug);
		
		new MainTest1().handle(crawlBug.getSeedUrl());
	}
	
	public void handle(String newLink) throws InterruptedException{
		
		String savePath = IStorePage.store(newLink);
		
		List<Object> urlList = commonExtract.extractHtml(savePath);
		
		String newUrl = storeDB.save(urlList);
		
		System.out.println(newUrl);
		
		if(!DataHandle.isNullOrEmpty(newUrl)){
			handle(newUrl);
		}
		
	}
	
}
