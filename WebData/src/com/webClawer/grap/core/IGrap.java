package com.webClawer.grap.core;

import com.webClawer.grap.analysis.source.IStoreData;

/**
 * 爬取接口
 * @author Taylor
 *
 */
public interface IGrap {

	/**
	 * 爬取数据
	 * @param source 爬取来源
	 * @param storeData 爬取存储
	 */
	public void grapData();
	
}
