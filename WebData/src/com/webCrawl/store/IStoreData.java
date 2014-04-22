package com.webCrawl.store;

/**
 * 存储数据
 * @author Taylor
 *
 */
public interface IStoreData {

	/**
	 * 存储文件
	 * @return
	 */
	public boolean store(String content,String storeRootFile,String fileName);
	
}
