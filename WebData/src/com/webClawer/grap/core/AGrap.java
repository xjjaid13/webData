package com.webClawer.grap.core;

import com.webClawer.grap.analysis.source.IStoreData;

public abstract class AGrap implements IGrap{

	private String sourceString;
	
	private IStoreData storeData;
	
	private String storePlace;

	public String getSourceString() {
		return sourceString;
	}

	public void setSourceString(String sourceString) {
		this.sourceString = sourceString;
	}

	public IStoreData getStoreData() {
		return storeData;
	}

	public void setStoreData(IStoreData storeData) {
		this.storeData = storeData;
	}

	public String getStorePlace() {
		return storePlace;
	}

	public void setStorePlace(String storePlace) {
		this.storePlace = storePlace;
	}

}
