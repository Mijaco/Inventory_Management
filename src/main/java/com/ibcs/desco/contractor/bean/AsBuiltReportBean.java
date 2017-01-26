package com.ibcs.desco.contractor.bean;

import java.util.List;

public class AsBuiltReportBean {

	private Integer id;

	private String woNumber;

	private String jobNo;

	private String asBuiltNo;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> consume;

	private List<Double> reUse;

	private List<Double> total;

	private List<Double> recServiceable;

	private List<Double> recUnServiceable;

	private List<Double> reBalServiceable;

	private List<Double> reBalUnServiceable;

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

	public List<Double> getConsume() {
		return consume;
	}

	public void setConsume(List<Double> consume) {
		this.consume = consume;
	}

	public List<Double> getReUse() {
		return reUse;
	}

	public void setReUse(List<Double> reUse) {
		this.reUse = reUse;
	}

	public List<Double> getTotal() {
		return total;
	}

	public void setTotal(List<Double> total) {
		this.total = total;
	}

	public List<Double> getRecServiceable() {
		return recServiceable;
	}

	public void setRecServiceable(List<Double> recServiceable) {
		this.recServiceable = recServiceable;
	}

	public List<Double> getRecUnServiceable() {
		return recUnServiceable;
	}

	public void setRecUnServiceable(List<Double> recUnServiceable) {
		this.recUnServiceable = recUnServiceable;
	}

	public List<Double> getReBalServiceable() {
		return reBalServiceable;
	}

	public void setReBalServiceable(List<Double> reBalServiceable) {
		this.reBalServiceable = reBalServiceable;
	}

	public List<Double> getReBalUnServiceable() {
		return reBalUnServiceable;
	}

	public void setReBalUnServiceable(List<Double> reBalUnServiceable) {
		this.reBalUnServiceable = reBalUnServiceable;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

}
