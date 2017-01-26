package com.ibcs.desco.workshop.bean;

import java.util.List;

public class TransformerCloseoutBean {

	private Integer id;

	private String woNumber;

	private String xCloseoutNo;

	private String startDate;

	private String endDate;
	
	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;
	
	private List<Double> balance;
	
	private List<Double> rcvPurCashQty;
	
	private List<Double> rcvFromStoreQty;

	private List<Double> totalQty;

	private List<Double> materialsConsume;

	private List<Double> materialsReturn;

	private List<Double> actualReturn;

	private List<Double> qtyShort;

	private List<Double> qtyExcess;

	private List<String> remarks;

	private String return_state;

	private String justification;

	private String stateCode;
	
	private boolean active = true;

	
	private String createdBy;

	
	private String createdDate;

	
	private String modifiedBy;

	
	private String modifiedDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getReturn_state() {
		return return_state;
	}

	public void setReturn_state(String return_state) {
		this.return_state = return_state;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public List<Double> getMaterialsConsume() {
		return materialsConsume;
	}

	public void setMaterialsConsume(List<Double> materialsConsume) {
		this.materialsConsume = materialsConsume;
	}

	public String getxCloseoutNo() {
		return xCloseoutNo;
	}

	public void setxCloseoutNo(String xCloseoutNo) {
		this.xCloseoutNo = xCloseoutNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Double> getRcvPurCashQty() {
		return rcvPurCashQty;
	}

	public void setRcvPurCashQty(List<Double> rcvPurCashQty) {
		this.rcvPurCashQty = rcvPurCashQty;
	}

	public List<Double> getRcvFromStoreQty() {
		return rcvFromStoreQty;
	}

	public void setRcvFromStoreQty(List<Double> rcvFromStoreQty) {
		this.rcvFromStoreQty = rcvFromStoreQty;
	}

	public List<Double> getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(List<Double> totalQty) {
		this.totalQty = totalQty;
	}

	public List<Double> getMaterialsReturn() {
		return materialsReturn;
	}

	public void setMaterialsReturn(List<Double> materialsReturn) {
		this.materialsReturn = materialsReturn;
	}

	public List<Double> getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(List<Double> actualReturn) {
		this.actualReturn = actualReturn;
	}

	public List<Double> getQtyShort() {
		return qtyShort;
	}

	public void setQtyShort(List<Double> qtyShort) {
		this.qtyShort = qtyShort;
	}

	public List<Double> getBalance() {
		return balance;
	}

	public void setBalance(List<Double> balance) {
		this.balance = balance;
	}

	public List<Double> getQtyExcess() {
		return qtyExcess;
	}

	public void setQtyExcess(List<Double> qtyExcess) {
		this.qtyExcess = qtyExcess;
	}
	
}
