package com.webClawer;

import java.util.List;

import com.util.HtmlHandle;
import com.webClawer.grap.analysis.extract.CommonExtract;
import com.webClawer.grap.analysis.extract.IExtract;
import com.webClawer.grap.analysis.filter.DomainFilter;
import com.webClawer.grap.analysis.filter.ILinkFilter;
import com.webClawer.grap.analysis.source.HtmlSource;
import com.webClawer.grap.analysis.source.ILinkArray;
import com.webClawer.grap.analysis.source.IStoreData;
import com.webClawer.grap.analysis.source.JvmLinkArray;
import com.webClawer.grap.analysis.source.Seed;
import com.webClawer.grap.analysis.source.StoreDataHtml;

public class MainTest {

	public static void main(String[] args) {
		new MainTest().claw();
	}
	
	ILinkArray linkArray = new JvmLinkArray();
	
	Seed seed = new Seed("http://127.0.0.1:9080/cms/node/69.jspx");
	
	public void claw(){
		linkArray.addNewLink(seed.getSourceMark());
		loose(linkArray.returnNewLink());
	}
	
	public void loose(String newLink){
		HtmlSource source = new HtmlSource(newLink);
		String sourceContent = source.getSourceContent();
		
		if(sourceContent == null || sourceContent.length() > 0){
			IStoreData storeData = new StoreDataHtml();
			String htmlContent = "D:/htmlContent/"+newLink.replaceAll("(.+?)/(.+?).(.+?)/(.+)/(.+)", "$4");
			storeData.store(sourceContent, htmlContent ,source.returnHtmlName());
			
			IExtract extract = new CommonExtract();
			List<Object> urlList = extract.extractHtml(sourceContent);
			
			ILinkFilter linkFilter = new DomainFilter(seed.returnDomain());
			
			if(urlList != null && urlList.size() > 0){
				for(Object url : urlList){
					String urlString = url.toString();
					if(urlString.startsWith("/")){
						urlString = HtmlHandle.joinUrl(seed.returnDomain(), urlString);
					}
					if(linkFilter.filter(urlString)){
						linkArray.addNewLink(urlString);
					}
				}
			}
		}
		
		newLink = linkArray.returnNewLink();
		if(newLink != null){
			loose(newLink);
		}
	}
	
}
