package com.webCrawl.filter.impl;

import org.springframework.stereotype.Component;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.filter.ILinkFilter;

/**
 * @author Taylor
 *
 */
@Component("domainFilter")
public class DomainFilter implements ILinkFilter{

	private CrawlBug crawlBug;
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
	@Override
	public boolean filter(String url) {
		if(url.indexOf(crawlBug.getDomain()) != -1){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) {
		String url = "http://wxjj.com.cn/datac/weblink/view/4";
		System.out.println(url.replaceAll("(.+?)/(.+?).(.+?)/(.+)/(.+)", "$4"));
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}

}
