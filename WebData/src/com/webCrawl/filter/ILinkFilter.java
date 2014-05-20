package com.webCrawl.filter;

import com.webCrawl.entity.ICrawlLink;

/**
 * 过滤url
 * @author Taylor
 *
 */
public interface ILinkFilter extends ICrawlLink{

	boolean filter(String url);
	
}
