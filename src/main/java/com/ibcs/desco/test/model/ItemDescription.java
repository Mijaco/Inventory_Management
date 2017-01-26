package com.ibcs.desco.test.model;

public class ItemDescription {

	private String slNo;
	private String itemCategory;
	private String itemName;
	private String itemCode;
	private String itemDescription;
	private String unit;

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "ItemDescription [slNo=" + slNo + ", itemCategory=" + itemCategory + ", itemName=" + itemName
				+ ", itemCode=" + itemCode + ", itemDescription=" + itemDescription + ", unit=" + unit + "]";
	}

}
