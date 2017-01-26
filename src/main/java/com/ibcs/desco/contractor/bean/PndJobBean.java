package com.ibcs.desco.contractor.bean;

import java.util.List;

public class PndJobBean {

	private Integer id;

	private String woNumber;

	private String jobNo;

	private String jobTitle;

	private String jobLocation;

	private String pdNo;

	private String pndNo;

	private String name;

	private String address;

	private String lot;

	private String constructionNature;

	private String typeOfScheme;

	private String justification;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> quantity;

	private List<Double> extendQuantity;

	private List<Double> remainningQuantity;

	private boolean active = true;

	private String remarks;

	private String createdBy;

	private String createdDate;

	private String modifiedBy;

	private String modifiedDate;

	private String userid;

	private String stateCode;

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
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

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getPdNo() {
		return pdNo;
	}

	public void setPdNo(String pdNo) {
		this.pdNo = pdNo;
	}

	public String getPndNo() {
		return pndNo;
	}

	public void setPndNo(String pndNo) {
		this.pndNo = pndNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getConstructionNature() {
		return constructionNature;
	}

	public void setConstructionNature(String constructionNature) {
		this.constructionNature = constructionNature;
	}

	public String getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(String typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

	public List<Double> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Double> quantity) {
		this.quantity = quantity;
	}

	public List<Double> getExtendQuantity() {
		return extendQuantity;
	}

	public void setExtendQuantity(List<Double> extendQuantity) {
		this.extendQuantity = extendQuantity;
	}

	public List<Double> getRemainningQuantity() {
		return remainningQuantity;
	}

	public void setRemainningQuantity(List<Double> remainningQuantity) {
		this.remainningQuantity = remainningQuantity;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

}
