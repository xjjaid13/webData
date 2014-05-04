package com.webCrawl.linkHandle.impl;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.util.DataHandle;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.linkHandle.IReduceRepeat;
import com.webCrawl.service.ICrawlLinkService;

@Component("bloomFilterReduceRepeat")
public class BloomFilterReduceRepeat implements IReduceRepeat{

	@Autowired
	ICrawlLinkService crawlLinkService;
	
	private CrawlBug crawlBug;
	
	BloomFilter<CharSequence> bf;
	
	public BloomFilterReduceRepeat(){
		bf =  BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000000,0.001);
	}
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
		List<CrawlLink> crawlLinkList = crawlLinkService.queryAllLink(crawlBug);
		if(!DataHandle.isNullOrEmpty(crawlLinkList)){
			for(CrawlLink crawlLink : crawlLinkList){
				bf.put(crawlLink.getLink());
			}
		}
	}

	@Override
	public synchronized boolean exist(String url) {
		return bf.mightContain(url);
	}

	@Override
	public synchronized void add(String url) {
		bf.put(url);
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
}
