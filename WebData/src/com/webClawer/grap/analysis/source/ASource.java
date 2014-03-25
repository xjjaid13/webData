package com.webClawer.grap.analysis.source;

/**
 * @author Taylor
 *
 */
public abstract class ASource implements ISource{

	private String sourceMark;
	
	ASource(String sourceMark){
		this.setSourceMark(sourceMark);
	}

	public String getSourceMark() {
		return sourceMark;
	}

	public void setSourceMark(String sourceMark) {
		this.sourceMark = sourceMark;
	}
	
}
