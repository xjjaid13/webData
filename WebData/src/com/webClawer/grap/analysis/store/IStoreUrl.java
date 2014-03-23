package com.webClawer.grap.analysis.store;

/**
 * @author Taylor
 *
 */
public interface IStoreUrl {

	/**
	 * 存放新爬取的url
	 * @param url
	 */
	public void save(String url);
	
	/**
	 * 获得未爬取的url
	 * @return
	 */
	public String returnNoGrapUrl();
	
}
