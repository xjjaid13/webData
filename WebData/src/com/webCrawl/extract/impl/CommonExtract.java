package com.webCrawl.extract.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.DataHandle;
import com.util.FileHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.ECrawlBug;
import com.webCrawl.extract.IExtract;
import com.webCrawl.linkHandle.IReduceRepeat;

@Component("commonExtract")
public class CommonExtract extends ECrawlBug implements IExtract{

	@Autowired
	IReduceRepeat bloomFilterReduceRepeat;
	
	@Override
	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
		bloomFilterReduceRepeat.setCrawlBug(crawlBug);
	}
	
	public Set<String> extractHtml(String path) {
		List<String> list = new ArrayList<String>();
		File localPathFile = new File(path);
		if(localPathFile.exists()){
			String localFileContent = FileHandle.readFile(path);
			
			Document doc = Jsoup.parse(localFileContent);
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
		}
		Set<String> result = new HashSet<String>();
		if(list != null){
			for(String url : list){
				if(!bloomFilterReduceRepeat.exist(url)){
					bloomFilterReduceRepeat.add(url);
					result.add(url);
				}
			}
		}
		
		return result;
	}

}
