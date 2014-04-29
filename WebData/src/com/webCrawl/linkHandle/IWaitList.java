package com.webCrawl.linkHandle;

import com.webCrawl.entity.CrawlBug;

public interface IWaitList {

	public void init(CrawlBug crawlBug);
	
	public String popValue();
	
	public void addList(String url);
	
}
