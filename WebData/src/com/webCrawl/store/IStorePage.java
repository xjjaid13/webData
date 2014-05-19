package com.webCrawl.store;

import com.webCrawl.entity.ICrawlLink;

/**
 * 存储数据
 * @author Taylor
 *
 */
public interface IStorePage extends ICrawlLink{

	/**
	 * 存储文件
	 * @return
	 */
	public String store(String url);
	
}
