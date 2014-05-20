package com.webCrawl.filter.impl;

import org.springframework.stereotype.Component;

import com.webCrawl.entity.ECrawlLink;
import com.webCrawl.filter.ILinkFilter;

/**
 * @author Taylor
 *
 */
@Component("domainFilter")
public class DomainFilter extends ECrawlLink implements ILinkFilter{
	
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

}
