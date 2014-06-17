package com.webCrawl.extract.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.CrawlHandle;
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
			list = CrawlHandle.returnExtract(localFileContent);
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
