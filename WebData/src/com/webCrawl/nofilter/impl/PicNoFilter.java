package com.webCrawl.nofilter.impl;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.nofilter.INoFilter;
import com.webCrawl.thread.main;

public class PicNoFilter implements INoFilter{

	private CrawlBug crawlBug;
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.setCrawlBug(crawlBug);
	}

	@Override
	public boolean noFilter(String url) {
		return isPic(url,".jpg",".png",".icon");
	}

	public boolean isPic(String url,String...ends){
		for(String end : ends){
			if(url.toLowerCase().endsWith(end)){
				return true;
			}
		}
		return false;
	}
	
	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		System.out.println(doc.attributes().get("Content-type"));
		Elements newsHeadlines = doc.select("#mp-itn b a");
	}

}
