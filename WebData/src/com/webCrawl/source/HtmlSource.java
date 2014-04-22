package com.webCrawl.source;

import java.math.BigInteger;
import java.util.BitSet;

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
			String lastString = super.getSourceMark().substring(lastIndex,super.getSourceMark().length());
			int paramIndex = lastString.indexOf("?");
			if(paramIndex != -1){
				return lastString.substring(0,paramIndex);
			}else{
				return lastString;
			}
		}
	}
	
	public static void main(String[] args) {
		BigInteger bi = new BigInteger("1000000000");
		System.out.println(bi.doubleValue());
		BitSet bitSet = new BitSet(bi.bitLength());
		System.out.println(bitSet.size());
	}
	
}
