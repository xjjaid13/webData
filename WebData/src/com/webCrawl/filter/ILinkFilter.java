package com.webCrawl.filter;

import com.webCrawl.entity.ICrawlBug;

/**
 * 过滤url
 * @author Taylor
 *
 */
public interface ILinkFilter extends ICrawlBug{

	boolean filter(String url);
	
}
