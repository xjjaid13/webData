package com.webCrawl.filter;

/**
 * 过滤url
 * @author Taylor
 *
 */
public interface ILinkFilter {

	boolean filter(String url);
	
}
