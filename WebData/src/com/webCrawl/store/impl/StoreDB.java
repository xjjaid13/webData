package com.webCrawl.store.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.DataHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.ECrawlBug;
import com.webCrawl.linkHandle.IWaitList;
import com.webCrawl.store.IStoreDB;

@Component("storeDB")
public class StoreDB extends ECrawlBug implements IStoreDB{

	@Autowired
	IWaitList arrayBlockingQueueWaitList;
	
	@Override
	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
		arrayBlockingQueueWaitList.setCrawlBug(crawlBug);
	}

	@Override
	public void save(Set<String> urlList) {
		if(!DataHandle.isNullOrEmpty(urlList)){
			for(String url : urlList){
				arrayBlockingQueueWaitList.addList(url);
			}
		}
	}
	
	@Override
	public String returnLink() {
		return arrayBlockingQueueWaitList.popValue();
	}

	@Override
	public void save(String url) {
		if(!DataHandle.isNullOrEmpty(url)){
			arrayBlockingQueueWaitList.addList(url);
		}
	}

}
