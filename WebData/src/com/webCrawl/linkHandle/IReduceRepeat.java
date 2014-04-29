package com.webCrawl.linkHandle;

import com.webCrawl.entity.CrawlBug;

public interface IReduceRepeat {
	
	public void init(CrawlBug crawlBug);
	
	public boolean exist(String url);
	
	public void add(String url);
	
}
