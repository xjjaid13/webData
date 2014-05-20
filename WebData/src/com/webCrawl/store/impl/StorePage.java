package com.webCrawl.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.HtmlHandle;
import com.util.Log;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;
import com.webCrawl.entity.ECrawlLink;
import com.webCrawl.service.ICrawlLinkService;
import com.webCrawl.source.impl.HtmlSource;
import com.webCrawl.store.IStorePage;

/**
 * @author Taylor
 *
 */
@Component("storePageHtml")
public class StorePage extends ECrawlLink implements IStorePage{

	private CrawlBug crawlBug;
	
	@Autowired
	ICrawlLinkService crawlLinkService;
	
	@Override
	public void setCrawlBug(CrawlBug crawlBug) {
		this.crawlBug = crawlBug;
	}
	
	@Override
	public String store(String url) {
		try{
			String savePath = crawlBug.getSavePath() + url.replaceAll("http(.*?)//(.+?)/(.*)", "$2/$3").replace(":", "-");
			if(!savePath.endsWith("/")){
				int lastIndex = savePath.lastIndexOf("/");
				savePath = savePath.substring(0,lastIndex);
			}
			HtmlSource source = new HtmlSource(url);
			String fileName = source.returnHtmlName();
			
			CrawlLink crawlLink = new CrawlLink();
			crawlLink.setLink(url);
			crawlLink = HtmlHandle.parseUrlToCrawlLink(crawlLink);
			
			String filePath = savePath + "/" + fileName;
			
			if(crawlLink.getStatusCode() == 200){
				HtmlHandle.download(url, fileName , savePath);
			}
			crawlLinkService.updateCrawlLinkStatus(crawlBug, url);
			return filePath;
		}catch(Exception e){
			Log.Error(url + "存储异常,StorePageHtml.store:" + url + ",exception:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("http://demo.jspxcms.com:8080/uploads/1/image/public/201303/20130319013832_fclut6.jpg".replaceAll("http(.*?)//(.+?)/(.*)", "$2/$3").replace(":", "-"));
	}
	
}
