package com.webCrawl.entity;

public class CrawlLink {

	private int linkId;
	
	private String link;
	
	private int isCrawled;
	
	private CrawlBug crawlBug;
	
	private int statusCode;
	
	private String linkContent;
	
	private String contentType;

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

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLinkContent() {
		return linkContent;
	}

	public void setLinkContent(String linkContent) {
		this.linkContent = linkContent;
	}
	
}
