package com.ibcs.desco.common.bean;

import java.util.List;

public class SafetyMarginBean {
	private List<Integer>pkList;
	private List<Double> safetyMarginList;
	
	public List<Integer> getPkList() {
		return pkList;
	}
	public void setPkList(List<Integer> pkList) {
		this.pkList = pkList;
	}
	public List<Double> getSafetyMarginList() {
		return safetyMarginList;
	}
	public void setSafetyMarginList(List<Double> safetyMarginList) {
		this.safetyMarginList = safetyMarginList;
	}
	
}
