package com.webCrawl.filter.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.webCrawl.entity.ECrawlLink;
import com.webCrawl.filter.ILinkFilter;

@Component("regexFilter")
public class RegexFilter extends ECrawlLink implements ILinkFilter{
	
	@Override
	public boolean filter(String url) {
		Pattern pattern = Pattern.compile(crawlBug.getRegex());
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

}
