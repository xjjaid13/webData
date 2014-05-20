package com.webCrawl.nofilter.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.webCrawl.entity.ECrawlLink;
import com.webCrawl.nofilter.INoFilter;

@Component("picNoFilter")
public class PicNoFilter extends ECrawlLink implements INoFilter{

	@Override
	public boolean noFilter(String url) {
		if(crawlLink.getContentType().indexOf("image") != -1){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		System.out.println(doc.attributes().get("Content-type"));
		Elements newsHeadlines = doc.select("#mp-itn b a");
	}

}
