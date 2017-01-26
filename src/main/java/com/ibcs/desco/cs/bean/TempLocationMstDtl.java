package com.ibcs.desco.cs.bean;

import java.util.Map;

public class TempLocationMstDtl {

	private String itemCode;

	private String uuid;

	private Map<String, String> locQtyDtl;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Map<String, String> getLocQtyDtl() {
		return locQtyDtl;
	}

	public void setLocQtyDtl(Map<String, String> locQtyDtl) {
		this.locQtyDtl = locQtyDtl;
	}

}
