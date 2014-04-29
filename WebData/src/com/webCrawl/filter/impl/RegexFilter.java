package com.webCrawl.filter.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.filter.ILinkFilter;

@Component("regexFilter")
public class RegexFilter implements ILinkFilter{

	private CrawlBug crawlBug;
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
	@Override
	public boolean filter(String url) {
		Pattern pattern = Pattern.compile(crawlBug.getRegex());
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}

}
