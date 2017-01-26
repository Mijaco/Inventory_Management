package com.ibcs.desco.common.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class PettyCashMstDtl {
	
	private String purchaseBy;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date purchaseDate;
	
	private String referenceNo;
	
	private String deptCode;
	
	private List<String> pettyCashCode;
	
	private List<String> pettyCashHead;
	
	private List<String> description;
	
	private List<Double> totalCost;
	
	private List<String> remarks;

	public String getPurchaseBy() {
		return purchaseBy;
	}

	public void setPurchaseBy(String purchaseBy) {
		this.purchaseBy = purchaseBy;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public List<String> getPettyCashCode() {
		return pettyCashCode;
	}

	public void setPettyCashCode(List<String> pettyCashCode) {
		this.pettyCashCode = pettyCashCode;
	}

	public List<String> getPettyCashHead() {
		return pettyCashHead;
	}

	public void setPettyCashHead(List<String> pettyCashHead) {
		this.pettyCashHead = pettyCashHead;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<Double> getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(List<Double> totalCost) {
		this.totalCost = totalCost;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
