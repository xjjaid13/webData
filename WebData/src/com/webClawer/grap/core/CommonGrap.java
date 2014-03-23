package com.webClawer.grap.core;

import com.util.Log;
import com.webClawer.grap.analysis.source.HtmlSource;
import com.webClawer.grap.analysis.source.ISource;
import com.webClawer.grap.analysis.source.IStoreData;
import com.webClawer.grap.analysis.source.StoreDataHtml;

/**
 * 普通爬取类
 * @author Taylor
 *
 */
public class CommonGrap extends AGrap{

	@Override
	public void grapData() {
		ISource iSource = returnSource(super.getSourceString());
		String content = iSource.getSourceContent();
		super.getStoreData().store(content,super.getStorePlace());
	}
	
	public static void main(String[] args) {
		CommonGrap commonGrap = new CommonGrap();
		commonGrap.setSourceString("http://wxjj.com.cn/datac/weblink/view/4");
		commonGrap.setStoreData(new StoreDataHtml());
		commonGrap.setStorePlace("D:/");
		commonGrap.grapData();
	}
	
	/**
	 * 返回资源
	 * @param source
	 * @return
	 */
	public ISource returnSource(String source){
		if(source.startsWith("http")){
			return new HtmlSource(source);
		}else{
			Log.Error("传递的资源无法解析：" + source);
			return null;
		}
	}
	
}
