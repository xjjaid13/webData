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
			String savePath = crawlBug.getSavePath() + url.replaceAll("http(.*?)//(.+?)[\\:](.*?)/(.*)", "$2/$4");
			if(!savePath.endsWith("/")){
				int lastIndex = savePath.lastIndexOf("/");
				savePath = savePath.substring(0,lastIndex);
			}
			HtmlSource source = new HtmlSource(url);
			String fileName = source.returnHtmlName();
			HtmlHandle.download(url, fileName , savePath);
			crawlLinkService.updateCrawlLinkStatus(crawlBug, url);
			return savePath + "/" + fileName;
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
	
	public static void main(String[] args) {
		System.out.println("http://192.168.199.164:9080/cms/files/1/bluewise/_files/litenav.js".replaceAll("http(.*?)//(.+?)[\\:](.*?)/(.*)", "$2/$4"));
	}
	
}
