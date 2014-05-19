package com.webCrawl.filter.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.ECrawlBug;
import com.webCrawl.extract.IExtract;
import com.webCrawl.filter.ILinkFilter;
import com.webCrawl.thread.main;

/**
 * @author Taylor
 *
 */
@Component("testFilter")
public class TestFilter extends ECrawlBug implements ILinkFilter{
	
	@Override
	public boolean filter(String url) {
		if(url.endsWith(".java")){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
		CrawlBug crawlBug = new CrawlBug();
		crawlBug.setBugId(2);
		crawlBug.setSavePath("D:/crawl/"+crawlBug.getBugId()+"/");
		crawlBug.setSeedUrl("http://localhost:9080/cms/info/81.jspx");
		crawlBug.setDomain("localhost");
		crawlBug.setBugName("bug Num.2");
		
		ILinkFilter domainFilter = context.getBean("domainFilter",ILinkFilter.class);
		domainFilter.setCrawlBug(crawlBug);
		ILinkFilter testFilter = context.getBean("testFilter",ILinkFilter.class);
		testFilter.setCrawlBug(crawlBug);
		Set<ILinkFilter> iLinkFilterSet = new HashSet<ILinkFilter>();
		iLinkFilterSet.add(domainFilter);
		iLinkFilterSet.add(testFilter);
		crawlBug.setiLinkFilterSet(iLinkFilterSet);
		
		String url1 = "http://localhost:999/test";
		String url2 = "http://baidu.com:999/test1.java";
		String url3 = "http://localhost:999/test2.java";
		String url4 = "http://localhost:999/test3";
		String url5 = "http://localhost:999/test4.java";
		String url6 = "http://localhost:999/test5";
		
		List<String> list = new ArrayList<String>();
		list.add(url1);
		list.add(url2);
		list.add(url3);
		list.add(url4);
		list.add(url5);
		list.add(url6);
		
		boolean flag = false;
		iLinkFilterSet = crawlBug.getiLinkFilterSet();
		for(String str : list){
			for(ILinkFilter iLinkFilter : iLinkFilterSet){
				flag = iLinkFilter.filter(str);
				if(!flag){
					break;
				}
			}
			if(flag){
				System.out.println(str);
			}
		}
		
	}
	
}

