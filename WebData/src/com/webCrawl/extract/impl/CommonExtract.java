package com.webCrawl.extract.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.util.DataHandle;
import com.util.FileHandle;
import com.util.Log;
import com.webCrawl.entity.ECrawlBug;
import com.webCrawl.extract.IExtract;

@Component("commonExtract")
public class CommonExtract extends ECrawlBug implements IExtract{

	public List<String> extractHtml(String path) {
		try{
			List<String> list = new ArrayList<String>();
			File localPathFile = new File(path);
			if(localPathFile.exists()){
				String localFileContent = FileHandle.readFile(path);
				
				Document doc = Jsoup.parse(localFileContent);
		        Elements links = doc.select("a[href]");
		        Elements media = doc.select("[src]");
		        Elements imports = doc.select("link[href]");
		        
		        for (Element src : links) {
		        	String href = src.attr("abs:href");
		        	if(!DataHandle.isNullOrEmpty(href)){
		        		list.add(href);
		        	}
		        }
		        
		        for (Element src : media) {
		        	String href = src.attr("abs:src");
		        	if(!DataHandle.isNullOrEmpty(href)){
		        		list.add(href);
		        	}
		        }
 
				for (Element src : imports) {
					String href = src.attr("abs:href");
					if(!DataHandle.isNullOrEmpty(href)){
						list.add(href);
					}
			    }
			}
			return list;
		}catch(Exception e){
			Log.Error("CommonExtract.extractHtml : " + e.getMessage());
		}
		return null;
	}

}
