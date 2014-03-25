package com.webClawer.grap.analysis.source;

/**
 * 链接数组
 * @author Taylor
 *
 */
public interface ILinkArray {

	/**
	 * 添加新的链接
	 */
	void addNewLink(String url);
	
	/**
	 * 返回一个未处理过的链接
	 * @return
	 */
	String returnNewLink();
	
}
