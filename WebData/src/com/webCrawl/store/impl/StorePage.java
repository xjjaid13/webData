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
			
			String filePath = savePath + "/" + fileName;
			
			CrawlLink crawlLink = HtmlHandle.download(url,fileName,savePath);
			
			crawlLinkService.updateCrawlLinkStatus(crawlBug, url);
			
			/** 如果为图片等，返回null,不需要解析 */
			if(isNeedExtract(crawlLink.getContentType())){
				return filePath;
			}
		}catch(Exception e){
			Log.Error(url + "存储异常",e);
		}
		return null;
	}
	
	public boolean isNeedExtract(String type){
		if(type.indexOf("image") != -1){
			return false;
		}else if(type.indexOf("css") != -1){
			return false;
		}else if(type.indexOf("javascript") != -1){
			return false;
		}else{
			return true;
		}
	}
	
}
