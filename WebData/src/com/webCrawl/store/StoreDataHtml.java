package com.webCrawl.store;

import com.util.FileHandle;
import com.util.Log;

/**
 * @author Taylor
 *
 */
public class StoreDataHtml implements IStoreData{

	@Override
	public boolean store(String content,String storeRootFile,String fileName) {
		FileHandle.createPath(storeRootFile);
		fileName = storeRootFile + "/"+fileName;
		boolean isSuccess = FileHandle.write(fileName, content);
		if(isSuccess){
			Log.Info(fileName + " 创建成功");
		}
		return isSuccess;
	}
	
}
