package com.webCrawl.extract;

import java.util.List;

import com.webCrawl.entity.CrawlBug;

/**
 * 解析html
 * @author Taylor
 *
 */
public interface IExtract {

	void init(CrawlBug crawlBug);
	
	public List<Object> extractHtml(String path);
	
}
