package com.webCrawl.store;

import java.util.Set;

import com.webCrawl.entity.ICrawlBug;

public interface IStoreDB extends ICrawlBug{

	public void save(Set<String> urlList);
	
	public void save(String url);
	
	public String returnLink();
	
}
