package com.webClawer.grap.analysis.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter implements ILinkFilter{

	private String regex;
	
	RegexFilter(String regex){
		this.regex = regex;
	}
	
	@Override
	public boolean filter(String url) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

}
