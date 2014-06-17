package com.webCrawl.extract.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.CrawlHandle;
import com.util.DataHandle;
import com.util.FileHandle;
import com.util.HtmlHandle;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.source.impl.HtmlSource;
import com.webCrawl.thread.main;

public class ExtractAllPage {

	public static void main2(String[] args) {
		try {
			
			CrawlLink crawlLink = HtmlHandle.download("http://www.ofcard.com/","index.html","D:/testOf");
			String path = "D:/testOf/index.html";
			List<String> list = new ArrayList<String>();
			String htmlContent = FileHandle.readFile(path);
			Document doc = Jsoup.parse(htmlContent);
	        Elements links = doc.select("a[href]");
	        Elements media = doc.select("[src]");
	        Elements imports = doc.select("link[href]");
	        
	        for (Element src : links) {
	        	String href = src.attr("href");
	        	if(!DataHandle.isNullOrEmpty(href)){
	        		list.add(href);
	        	}
	        }
	        
	        for (Element src : media) {
	        	String href = src.attr("src");
	        	if(!DataHandle.isNullOrEmpty(href)){
	        		list.add(href);
	        	}
	        }

			for (Element src : imports) {
				String href = src.attr("href");
				if(!DataHandle.isNullOrEmpty(href)){
					list.add(href);
				}
		    }
			
			if(list != null && list.size() > 0){
				for(String url : list){
		        	HtmlSource source = new HtmlSource(url);
		        	if(!DataHandle.isNullOrEmpty(url)){
		        		
		        		url = HtmlHandle.joinUrl("http://www.ofcard.com/", url);
		        		if(!url.startsWith("http")){
		        			url = "http://" + url;
		        		}
		        		try{
		        			HtmlHandle.download(url,source.returnHtmlName(),"D:/testOf/" + CrawlHandle.returnPathUrl(url));
			        		htmlContent = htmlContent.replaceAll(url, "./"+url.replaceAll("http(.*?)//(.+?)/(.*)", "$2/$3").replace(":", "-"));
		        		}catch(Exception e){
		        			
		        		}
		        		
		        	}
				}
			}
			FileHandle.write(path, htmlContent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String url = "www.baidu.com:8080/ss/ss.html";
		System.out.println(url.replaceAll("http(.*?)//(.+?)/(.*?)", "$2/$3").replace(":", "-"));
		System.out.println(HtmlHandle.joinUrl("http://www.ofcard.com/", "http://www.baidu.com"));
	}
	
}
