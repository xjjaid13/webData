package com.webCrawl.linkHandle;

public interface IReduceRepeat {
	
	public void init();
	
	public boolean exist(String url);
	
	public void add(String url);
	
}
