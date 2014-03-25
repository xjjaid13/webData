package com.webClawer.grap.analysis.extract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonExtract implements IExtract{

	@Override
	public List<Object> extractHtml(String htmlContent) {
		List<Object> list = new ArrayList<Object>();
		Pattern pattern = Pattern.compile("(href|src)=\"(.+?)\"");
		Matcher matcher = pattern.matcher(htmlContent);
		while(matcher.find()){
			String url = matcher.group(2);
			list.add(url);
		}
		return list;
	}
	
}
