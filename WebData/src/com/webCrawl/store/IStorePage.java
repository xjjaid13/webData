package com.webCrawl.store;

import com.webCrawl.entity.CrawlBug;

/**
 * 存储数据
 * @author Taylor
 *
 */
public interface IStorePage {

	public void init(CrawlBug crawlBug);
	
	/**
	 * 存储文件
	 * @return
	 */
	public String store(String url);
	
}
