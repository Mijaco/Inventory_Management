package com.ibcs.desco.workshop.bean;

import java.util.Date;
import java.util.List;

public class RepairAndMaintenanceTargetBean {

	private Integer id;

	private String workOrderNo;
	
	private List<String> targetDate;
	private List<String> strTargetMonth;
	private List<Double> repairTarget1P;
	private List<Double> repairTarget3P;
	private List<Double> preventiveTarget1P;
	private List<Double> preventiveTarget3P;
	private List<String> remarks;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public List<String> getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(List<String> targetDate) {
		this.targetDate = targetDate;
	}

	public List<String> getStrTargetMonth() {
		return strTargetMonth;
	}

	public void setStrTargetMonth(List<String> strTargetMonth) {
		this.strTargetMonth = strTargetMonth;
	}

	public List<Double> getRepairTarget1P() {
		return repairTarget1P;
	}

	public void setRepairTarget1P(List<Double> repairTarget1P) {
		this.repairTarget1P = repairTarget1P;
	}

	public List<Double> getRepairTarget3P() {
		return repairTarget3P;
	}

	public void setRepairTarget3P(List<Double> repairTarget3P) {
		this.repairTarget3P = repairTarget3P;
	}

	public List<Double> getPreventiveTarget1P() {
		return preventiveTarget1P;
	}

	public void setPreventiveTarget1P(List<Double> preventiveTarget1P) {
		this.preventiveTarget1P = preventiveTarget1P;
	}

	public List<Double> getPreventiveTarget3P() {
		return preventiveTarget3P;
	}

	public void setPreventiveTarget3P(List<Double> preventiveTarget3P) {
		this.preventiveTarget3P = preventiveTarget3P;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

}
