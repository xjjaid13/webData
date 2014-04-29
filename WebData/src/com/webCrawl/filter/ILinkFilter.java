package com.webCrawl.filter;

import com.webCrawl.entity.CrawlBug;

/**
 * 过滤url
 * @author Taylor
 *
 */
public interface ILinkFilter {

	void init(CrawlBug crawlBug);
	
	boolean filter(String url);
	
}
