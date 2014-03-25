package com.webClawer.grap.analysis.source;

import com.util.HtmlHandle;
import com.util.Log;

/**
 * html链接处理
 * @author Taylor
 *
 */
public class HtmlSource extends ASource{

	public HtmlSource(String sourceMark) {
		super(sourceMark);
	}

	@Override
	public String getSourceContent() {
		try{
			return HtmlHandle.getWebCon(super.getSourceMark());
		}catch(Exception e){
			Log.Error(super.getSourceMark() + " 无法解析 :" + e.getMessage());
			return null;
		}
	}
	
	public String returnHtmlName(){
		int lastIndex = super.getSourceMark().lastIndexOf("/") + 1;
		if(lastIndex >= super.getSourceMark().length()){
			return System.currentTimeMillis() + "";
		}else{
			return super.getSourceMark().substring(lastIndex,super.getSourceMark().length());
		}
	}
	
	public static void main(String[] args) {
		Seed seed = new Seed("http://wxjj.com.cn/datac/weblink/view/4");
		String str = HtmlHandle.joinUrl("http://wxjj.com.cn", "/datac/weblink/view/4");
		System.out.println(str);
	}
	
}
