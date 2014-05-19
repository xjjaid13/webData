package com.webCrawl.linkHandle;

import com.webCrawl.entity.ICrawlBug;

public interface IReduceRepeat extends ICrawlBug{
	
	public boolean exist(String url);
	
	public void add(String url);
	
}
