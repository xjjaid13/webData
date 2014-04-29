package com.webCrawl.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.HtmlHandle;
import com.util.Log;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.service.ICrawlLinkService;
import com.webCrawl.source.impl.HtmlSource;
import com.webCrawl.store.IStorePage;

/**
 * @author Taylor
 *
 */
@Component("storePageHtml")
public class StorePage implements IStorePage{

	private CrawlBug crawlBug;
	
	@Autowired
	ICrawlLinkService crawlLinkService;
	
	@Override
	public void init(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
	@Override
	public String store(String url) {
		try{
			String savePath = crawlBug.getSavePath() + url.replaceAll("http(.*?)//(.+)/(.*)", "$2");
			HtmlSource source = new HtmlSource(url);
			String fileName = source.returnHtmlName();
			HtmlHandle.download(url, source.returnHtmlName(), savePath);
			crawlLinkService.updateCrawlLinkStatus(crawlBug, url);
			return fileName;
		}catch(Exception e){
			Log.Error(url + "存储异常,StorePageHtml.store:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public CrawlBug getCrawlBug() {
		return crawlBug;
	}

	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}

}
