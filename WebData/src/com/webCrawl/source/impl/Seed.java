package com.webCrawl.source.impl;

import com.util.HtmlHandle;

public class Seed extends HtmlSource{

	public Seed(String sourceMark) {
		super(sourceMark);
	}

	public String returnDomain(){
		return HtmlHandle.getDomain(getSourceMark());
	}


	
}
