package com.webCrawl.extract;

import java.util.Set;

import com.webCrawl.entity.ICrawlBug;

/**
 * 解析html
 * @author Taylor
 *
 */
public interface IExtract extends ICrawlBug{

	public Set<String> extractHtml(String path);
	
}
