package com.webCrawl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBHandle;
import com.util.FileHandle;
import com.util.HtmlHandle;
import com.webCrawl.extract.CommonExtract;
import com.webCrawl.extract.IExtract;
import com.webCrawl.filter.DomainFilter;
import com.webCrawl.filter.ILinkFilter;
import com.webCrawl.linkHandle.JvmLinkArray;
import com.webCrawl.source.HtmlSource;
import com.webCrawl.source.Seed;


public class MainTest {
	
	public static DBHandle db = new DBHandle();

	public static void main(String[] args) throws Exception {
		db.openConnMysql();
		new MainTest().claw();
		db.closeConn();
	}
	
	JvmLinkArray linkArray = new JvmLinkArray();
	
	
	Seed seed = new Seed("http://www.csdn.net/index.html");
	
	public void init(){
		String sql = "select link,isClaw from t_clawer_link";
		String[][] arr = db.executeQuery(sql);
		
		Map<String,Integer> linkMap = new HashMap<String,Integer>();
		List<String> waitUrlList = new ArrayList<String>();
		for(int i = 0; arr != null && i < arr.length; i++){
			String link = arr[i][0];
			int isClaw = Integer.parseInt(arr[i][1]);
			linkMap.put(link, 0);
			if(isClaw == 0){
				waitUrlList.add(link);
			}
		}
		linkArray.setLinkMap(linkMap);
		linkArray.setWaitUrlList(waitUrlList);
	}
	
	public void claw() throws Exception{
		init();
		if(!linkArray.getLinkMap().containsKey(seed.getSourceMark())){
			linkArray.addNewLink(seed.getSourceMark());
		}
		handle(linkArray.returnNewLink());
	}
	
	/**
	 * @param newLink
	 * @throws Exception O
	 */
	public void handle(String newLink) throws Exception{
		String savePath = "D:/htmlContent/"+newLink.replaceAll("http(.*?)//(.+)/(.*)", "$2");
		HtmlSource source = new HtmlSource(newLink);
		String fileName = source.returnHtmlName();
		HtmlHandle.download(newLink, source.returnHtmlName(), savePath);
		
		String localPath = savePath + "/" + fileName;
		File localPathFile = new File(localPath);
		if(localPathFile.exists()){
			String localFileContent = FileHandle.readFile(localPath);
			
			IExtract extract = new CommonExtract();
			List<Object> urlList = extract.extractHtml(localFileContent);
			
			ILinkFilter linkFilter = new DomainFilter(seed.returnDomain());
			
			if(urlList != null && urlList.size() > 0){
				for(Object url : urlList){
					String urlString = url.toString();
					if(urlString.startsWith("/")){
						urlString = HtmlHandle.joinUrl(seed.returnDomain(), urlString);
					}
					if(linkFilter.filter(urlString)){
						if(!linkArray.getLinkMap().containsKey(urlString)){
							db.executeUpdate("insert into t_clawer_link (link,isClaw) values ('"+urlString+"',0)");
							linkArray.addNewLink(urlString);
						}
					}
				}
			}
		}
		
		newLink = linkArray.returnNewLink();
		System.out.println("handle="+newLink);
		db.executeUpdate("update t_clawer_link set isClaw = 1 where link = '"+newLink+"'");
		if(newLink != null){
			handle(newLink);
		}
	}
	
}
