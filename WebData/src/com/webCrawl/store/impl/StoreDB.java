package com.webCrawl.store.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.DataHandle;
import com.util.HtmlHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.filter.ILinkFilter;
import com.webCrawl.linkHandle.IReduceRepeat;
import com.webCrawl.linkHandle.IWaitList;
import com.webCrawl.store.IStoreDB;

@Component("storeDB")
public class StoreDB implements IStoreDB{

	@Autowired
	IReduceRepeat bloomFilterReduceRepeat;
	
	@Autowired
	IWaitList arrayBlockingQueueWaitList;
	
	@Autowired
	ILinkFilter domainFilter;
	
	private CrawlBug crawlBug;
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
		bloomFilterReduceRepeat.init(crawlBug);
		arrayBlockingQueueWaitList.init(crawlBug);
		domainFilter.init(crawlBug);
	}

	@Override
	public String save(List<Object> urlList) {
		if(!DataHandle.isNullOrEmpty(urlList)){
			for(Object url : urlList){
				String urlString = url.toString();
				if(urlString.startsWith("/")){
					urlString = HtmlHandle.joinUrl(crawlBug.getDomain(), urlString);
				}
				if(domainFilter.filter(urlString)){
					if(!bloomFilterReduceRepeat.exist(urlString)){
						arrayBlockingQueueWaitList.addList(urlString);
					}
				}
			}
		}
		return arrayBlockingQueueWaitList.popValue();
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}

}
