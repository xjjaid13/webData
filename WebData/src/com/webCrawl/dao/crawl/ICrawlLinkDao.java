package com.webCrawl.dao.crawl;

import java.util.List;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;

public interface ICrawlLinkDao {

    List<CrawlLink> queryAllLink(CrawlBug crawlBug);
	 
	List<CrawlLink> queryWaitListLink(CrawlBug crawlBug);
	
	void insertCrawlLink(CrawlBug crawlBug, String url);
	
	void updateCrawlLinkStatus(CrawlBug crawlBug,String url);
	
}
