package com.webCrawl.store;

import java.util.List;

import com.webCrawl.entity.CrawlBug;

public interface IStoreDB {

	public void init(CrawlBug crawlBug);
	
	public String save(List<Object> urlList);
	
}
