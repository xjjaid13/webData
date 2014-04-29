package com.webCrawl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webCrawl.dao.crawl.ICrawlLinkDao;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.service.ICrawlLinkService;

@Service("crawlLinkService")
public class CrawlLinkServiceImpl implements ICrawlLinkService{

	@Autowired
	ICrawlLinkDao crawlLinkDao;
	
	@Override
	public List<CrawlLink> queryAllLink(CrawlBug crawlBug) {
		return crawlLinkDao.queryAllLink(crawlBug);
	}

	@Override
	public List<CrawlLink> queryWaitListLink(CrawlBug crawlBug) {
		return crawlLinkDao.queryWaitListLink(crawlBug);
	}

	@Override
	public void insertCrawlLink(CrawlBug crawlBug, String url) {
		crawlLinkDao.insertCrawlLink(crawlBug, url);
	}

	@Override
	public void updateCrawlLinkStatus(CrawlBug crawlBug, String url) {
		crawlLinkDao.updateCrawlLinkStatus(crawlBug, url);
	}
	
}
