package com.webCrawl.nofilter.impl;

import org.springframework.stereotype.Component;

import com.webCrawl.entity.ECrawlLink;
import com.webCrawl.nofilter.INoFilter;

@Component("jsNoFilter")
public class JsNoFilter extends ECrawlLink implements INoFilter{

	@Override
	public boolean noFilter(String url) {
		if(crawlLink.getContentType().indexOf("javascript") != -1){
			return true;
		}else{
			return false;
		}
	}

}
