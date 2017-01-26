package com.ibcs.desco.cs.bean;

import java.util.List;

public class Location7sMstDtl {

	private String itemCode;

	private String uuid;
	
	private String ledgerName;	

	private List<Location7sQty> locationList;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUuid() {
		return uuid;
	}
	
	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Location7sQty> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location7sQty> locationList) {
		this.locationList = locationList;
	}
	
	
}
