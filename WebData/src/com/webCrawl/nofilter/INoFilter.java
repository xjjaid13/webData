package com.webCrawl.nofilter;

import com.webCrawl.entity.CrawlBug;

public interface INoFilter {

	void init(CrawlBug crawlBug);
	
	boolean noFilter(String url);
	
}
