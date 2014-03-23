package com.webClawer.grap.analysis.source;

import org.jsoup.Jsoup;

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
			return Jsoup.connect(super.sourceMark).get().toString();
		}catch(Exception e){
			Log.Error(e.getMessage());
			return null;
		}
	}

}
