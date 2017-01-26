package com.ibcs.desco.procurement.bean;

import java.util.List;

public class DemandNoteMstDtl {

	private Integer id = 0;

	private String department;

	private String senderName;

	private String demandNoteNo;
	
	private String financialYear;

	private String remarks;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> requiredQunatity;

	private List<Double> unitCost;

	private List<Double> totalCost;

	private List<Double> previousYearConsumption;

	private List<Double> existingQty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getDemandNoteNo() {
		return demandNoteNo;
	}

	public void setDemandNoteNo(String demandNoteNo) {
		this.demandNoteNo = demandNoteNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
	}

	public List<String> getItemName() {
		return itemName;
	}

	public void setItemName(List<String> itemName) {
		this.itemName = itemName;
	}

	public List<String> getUom() {
		return uom;
	}

	public void setUom(List<String> uom) {
		this.uom = uom;
	}

	public List<Double> getRequiredQunatity() {
		return requiredQunatity;
	}

	public void setRequiredQunatity(List<Double> requiredQunatity) {
		this.requiredQunatity = requiredQunatity;
	}

	public List<Double> getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(List<Double> unitCost) {
		this.unitCost = unitCost;
	}

	public List<Double> getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(List<Double> totalCost) {
		this.totalCost = totalCost;
	}

	public List<Double> getPreviousYearConsumption() {
		return previousYearConsumption;
	}

	public void setPreviousYearConsumption(List<Double> previousYearConsumption) {
		this.previousYearConsumption = previousYearConsumption;
	}

	public List<Double> getExistingQty() {
		return existingQty;
	}

	public void setExistingQty(List<Double> existingQty) {
		this.existingQty = existingQty;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	
	

	
}
