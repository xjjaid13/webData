package com.webCrawl.store;

import java.util.List;

import com.webCrawl.entity.ICrawlBug;

public interface IStoreDB extends ICrawlBug{

	public void save(List<Object> urlList);
	
	public void save(String url);
	
	public String returnLink();
	
}
