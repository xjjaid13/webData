package com.webCrawl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.DataHandle;
import com.util.FileHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.extract.IExtract;
import com.webCrawl.store.IStoreDB;
import com.webCrawl.store.IStorePage;

/**
*1111111111222222222222222222
*/
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
		
		new MainTest1().handle();
		
		long s2 = System.currentTimeMillis();
		
		System.out.println(s2 - s1);
		
	}
	
	public void handle() throws InterruptedException{
		
		String newUrl = storeDB.returnLink();
		
		if(DataHandle.isNullOrEmpty(newUrl)){
			return;
		}
		
		String savePath = IStorePage.store(newUrl);
		
		List<Object> urlList = commonExtract.extractHtml(savePath);
		
		storeDB.save(urlList);
		
		handle();
		
	}
	
	public static void main1(String[] args) {
		String name = "D:/crawl/test1:a";
		System.out.println(name.replaceAll("\\:", "-"));
	}
	
}
