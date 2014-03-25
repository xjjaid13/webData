package com.webClawer.grap.analysis.filter;

/**
 * @author Taylor
 *
 */
public class DomainFilter implements ILinkFilter{

	String domain = "";
	
	public DomainFilter(String domain){
		this.domain = domain;
	}
	
	@Override
	public boolean filter(String url) {
		if(url.indexOf(domain) != -1){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) {
		String url = "http://wxjj.com.cn/datac/weblink/view/4";
		System.out.println(url.replaceAll("(.+?)/(.+?).(.+?)/(.+)/(.+)", "$4"));
	}

}
