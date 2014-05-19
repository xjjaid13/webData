package com.webCrawl.extract;

import java.util.List;

import com.webCrawl.entity.ICrawlBug;

/**
 * 解析html
 * @author Taylor
 *
 */
public interface IExtract extends ICrawlBug{

	public List<Object> extractHtml(String path);
	
}
