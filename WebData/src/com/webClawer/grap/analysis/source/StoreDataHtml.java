package com.webClawer.grap.analysis.source;

import com.util.FileHandle;
import com.util.Log;

/**
 * @author Taylor
 *
 */
public class StoreDataHtml implements IStoreData{

	@Override
	public boolean store(String content,String storeRootFile) {
		String fileName = storeRootFile + "/"+System.currentTimeMillis() + ".html";
		boolean isSuccess = FileHandle.write(fileName, content);
		if(isSuccess){
			Log.Info(fileName + " 创建成功");
		}
		return isSuccess;
	}
	
}
