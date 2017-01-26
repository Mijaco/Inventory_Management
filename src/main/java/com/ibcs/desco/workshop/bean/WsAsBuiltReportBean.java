package com.ibcs.desco.workshop.bean;

import java.util.List;

public class WsAsBuiltReportBean {

	private Integer id;

	private String woNumber;

	private String jobNo;

	private String asBuiltNo;
	
	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> materialsQuantity;

	private List<Double> receivedQuantity;

	private List<Double> materialsConsume;

	private List<Double> materialsInHand;

	private List<Double> total;

	private String return_state;

	private String justification;

	private String stateCode;
	
	private boolean active = true;

	
	private List<String> remarks;

	
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

	public String getAsBuiltNo() {
		return asBuiltNo;
	}

	public void setAsBuiltNo(String asBuiltNo) {
		this.asBuiltNo = asBuiltNo;
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

	public List<Double> getTotal() {
		return total;
	}

	public void setTotal(List<Double> total) {
		this.total = total;
	}

	public List<Double> getMaterialsQuantity() {
		return materialsQuantity;
	}

	public void setMaterialsQuantity(List<Double> materialsQuantity) {
		this.materialsQuantity = materialsQuantity;
	}

	public List<Double> getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(List<Double> receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public List<Double> getMaterialsConsume() {
		return materialsConsume;
	}

	public void setMaterialsConsume(List<Double> materialsConsume) {
		this.materialsConsume = materialsConsume;
	}

	public List<Double> getMaterialsInHand() {
		return materialsInHand;
	}

	public void setMaterialsInHand(List<Double> materialsInHand) {
		this.materialsInHand = materialsInHand;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

}
