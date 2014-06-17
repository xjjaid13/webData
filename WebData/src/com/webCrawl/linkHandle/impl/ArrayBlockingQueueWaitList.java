package com.webCrawl.linkHandle.impl;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.DataHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.entity.ECrawlBug;
import com.webCrawl.linkHandle.IWaitList;
import com.webCrawl.service.ICrawlLinkService;

@Component("arrayBlockingQueueWaitList")
public class ArrayBlockingQueueWaitList extends ECrawlBug implements IWaitList{

	@Autowired
	ICrawlLinkService crawlLinkService;
	
	Queue<String> queue = null;
	
	@Override
	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
		queue = new ArrayBlockingQueue<String>(1000000,true);
		List<CrawlLink> crawlLinkList = crawlLinkService.queryWaitListLink(crawlBug);
		if(!DataHandle.isNullOrEmpty(crawlLinkList)){
			for(CrawlLink crawlLink : crawlLinkList){
				queue.offer(crawlLink.getLink());
			}
		}
	}

	@Override
	public String popValue() {
		return queue.poll();
	}

	@Override
	public void addList(String url) {
		queue.offer(url);
		crawlLinkService.insertCrawlLink(crawlBug, url);
	}

}
