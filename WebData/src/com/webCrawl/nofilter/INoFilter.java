package com.webCrawl.nofilter;

import com.webCrawl.entity.ICrawlBug;

public interface INoFilter extends ICrawlBug{

	boolean noFilter(String url);
	
}
