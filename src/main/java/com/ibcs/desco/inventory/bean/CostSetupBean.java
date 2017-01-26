package com.ibcs.desco.inventory.bean;

import java.util.List;

public class CostSetupBean {

	private Integer id;
	
	private Integer rrMstId;
	
	private List<String> itemCodeList;
	
	private List<Double> unitCostList;
	
	private List<Double> quantityList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getItemCodeList() {
		return itemCodeList;
	}

	public void setItemCodeList(List<String> itemCodeList) {
		this.itemCodeList = itemCodeList;
	}

	public List<Double> getUnitCostList() {
		return unitCostList;
	}

	public void setUnitCostList(List<Double> unitCostList) {
		this.unitCostList = unitCostList;
	}

	public List<Double> getQuantityList() {
		return quantityList;
	}

	public void setQuantityList(List<Double> quantityList) {
		this.quantityList = quantityList;
	}

	public Integer getRrMstId() {
		return rrMstId;
	}

	public void setRrMstId(Integer rrMstId) {
		this.rrMstId = rrMstId;
	}
	
	
	
}
