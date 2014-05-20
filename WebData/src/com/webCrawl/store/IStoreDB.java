package com.webCrawl.store;

import java.util.List;

import com.webCrawl.entity.ICrawlBug;

public interface IStoreDB extends ICrawlBug{

	public void save(List<String> urlList);
	
	public void save(String url);
	
	public String returnLink();
	
}
