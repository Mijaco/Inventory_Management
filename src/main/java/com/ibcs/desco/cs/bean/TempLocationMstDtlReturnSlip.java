package com.ibcs.desco.cs.bean;

import java.util.List;

public class TempLocationMstDtlReturnSlip {

	private String itemCode;

	private String uuid;

	private List<LedgerLocQty> ledLocQtyList;

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

	public List<LedgerLocQty> getLedLocQtyList() {
		return ledLocQtyList;
	}

	public void setLedLocQtyList(List<LedgerLocQty> ledLocQtyList) {
		this.ledLocQtyList = ledLocQtyList;
	}

}
