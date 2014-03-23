package com.webClawer.grap.analysis.extract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.FileHandle;

public class CommonExtract implements IExtract{

	@Override
	public List<Object> extractHtml(String htmlContent) {
		List<Object> list = new ArrayList<Object>();
		String content = FileHandle.readFile("D:/1394976154300.html");
		Pattern pattern = Pattern.compile("(href|src)=\"(.+?)\"");
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()){
			String url = matcher.group(2);
			if(!(url.startsWith("http") || url.startsWith("www"))){
				list.add(url);
			}
		}
		return list;
	}
	
}
