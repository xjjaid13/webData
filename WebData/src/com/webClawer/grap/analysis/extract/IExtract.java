package com.webClawer.grap.analysis.extract;

import java.util.List;

/**
 * 解析html
 * @author Taylor
 *
 */
public interface IExtract {

	public List<Object> extractHtml(String htmlContent);
	
}
