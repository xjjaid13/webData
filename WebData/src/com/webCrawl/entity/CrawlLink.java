package com.webCrawl.entity;

public class CrawlLink {

	private int linkId;
	
	private String link;
	
	private int isCrawled;
	
	private CrawlBug crawlBug;

	public int getLinkId() {
		return linkId;
	}

	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getIsCrawled() {
		return isCrawled;
	}

	public void setIsCrawled(int isCrawled) {
		this.isCrawled = isCrawled;
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
}
