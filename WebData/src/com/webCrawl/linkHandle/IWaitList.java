package com.webCrawl.linkHandle;

import com.webCrawl.entity.ICrawlBug;

public interface IWaitList extends ICrawlBug{

	public String popValue();
	
	public void addList(String url);
	
}
