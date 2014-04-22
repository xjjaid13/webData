package com.webCrawl.linkHandle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taylor
 *
 */
public class JvmLinkArray implements ILinkArray{

	private Map<String,Integer> linkMap = Collections.synchronizedMap(new HashMap<String,Integer>());
	
	private List<String> waitUrlList = Collections.synchronizedList(new ArrayList<String>());
	
	@Override
	public void addNewLink(String url) {
		if(!linkMap.containsKey(url)){
			linkMap.put(url, 0);
			waitUrlList.add(url);
		}
	}

	@Override
	public String returnNewLink() {
		if(waitUrlList.size() > 0){
			String newLink = waitUrlList.get(0);
			waitUrlList.remove(0);
			return newLink;
		}else{
			return null;
		}
	}

	public Map<String,Integer> getLinkMap() {
		return linkMap;
	}

	public void setLinkMap(Map<String,Integer> linkMap) {
		this.linkMap = linkMap;
	}

	public List<String> getWaitUrlList() {
		return waitUrlList;
	}

	public void setWaitUrlList(List<String> waitUrlList) {
		this.waitUrlList = waitUrlList;
	}
	
}
