package com.webCrawl.entity;

import java.util.Set;

import com.webCrawl.filter.ILinkFilter;

public class CrawlBug {

	private int bugId;
	
	private String bugName;
	
	private String savePath;
	
	private String seedUrl;
	
	private String domain;
	
	private String regex;
	
	private Set<ILinkFilter> iLinkFilterSet;

	public int getBugId() {
		return bugId;
	}

	public void setBugId(int bugId) {
		this.bugId = bugId;
	}

	public String getBugName() {
		return bugName;
	}

	public void setBugName(String bugName) {
		this.bugName = bugName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public Set<ILinkFilter> getiLinkFilterSet() {
		return iLinkFilterSet;
	}

	public void setiLinkFilterSet(Set<ILinkFilter> iLinkFilterSet) {
		this.iLinkFilterSet = iLinkFilterSet;
	}
	
}
