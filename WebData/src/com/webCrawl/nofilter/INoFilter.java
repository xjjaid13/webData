package com.webCrawl.nofilter;

import com.webCrawl.entity.ICrawlLink;

public interface INoFilter extends ICrawlLink{

	boolean noFilter(String url);
	
}
