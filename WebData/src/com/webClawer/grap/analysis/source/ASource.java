package com.webClawer.grap.analysis.source;

/**
 * @author Taylor
 *
 */
public abstract class ASource implements ISource{

	public String sourceMark;
	
	ASource(String sourceMark){
		this.sourceMark = sourceMark;
	}
	
}
