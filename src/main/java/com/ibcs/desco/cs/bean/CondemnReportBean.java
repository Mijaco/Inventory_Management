package com.ibcs.desco.cs.bean;

import java.util.List;

import com.ibcs.desco.cs.model.CondemnDtl;
import com.ibcs.desco.inventory.model.ItemMaster;

public class CondemnReportBean {

	private ItemMaster itemMaster;

	private List<CondemnDtl> dtlList;
	
	private Double totalQty;

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public List<CondemnDtl> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<CondemnDtl> dtlList) {
		this.dtlList = dtlList;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}
	
	

}
