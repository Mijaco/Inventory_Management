package com.ibcs.desco.contractor.bean;


public class ContractorBean {

	private Integer id;
	
	private String contractNo;

	
	private String contractorName;

	
	private String address;

	
	private String contractDate;

	
	private String expiryDate;

	
	private String tenderNo;

	
	private Integer khathId;

	
	private String khathName;

	
	private String division;

	
	private String deptId;

	
	private String others;

	
	private String updatedValidityDate;

	
	private boolean active = true;

	
	private String remarks;

	
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


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getContractorName() {
		return contractorName;
	}


	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getContractDate() {
		return contractDate;
	}


	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}


	public String getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getTenderNo() {
		return tenderNo;
	}


	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}


	public Integer getKhathId() {
		return khathId;
	}


	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}


	public String getKhathName() {
		return khathName;
	}


	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getOthers() {
		return others;
	}


	public void setOthers(String others) {
		this.others = others;
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


	public String getUpdatedValidityDate() {
		return updatedValidityDate;
	}


	public void setUpdatedValidityDate(String updatedValidityDate) {
		this.updatedValidityDate = updatedValidityDate;
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

}
