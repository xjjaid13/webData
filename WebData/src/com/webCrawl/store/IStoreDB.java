package com.webCrawl.store;

import java.util.List;

import com.webCrawl.entity.CrawlBug;

public interface IStoreDB {

	public void init(CrawlBug crawlBug);
	
	public void save(List<Object> urlList);
	
	public void save(String url);
	
	public String returnLink();
	
}
