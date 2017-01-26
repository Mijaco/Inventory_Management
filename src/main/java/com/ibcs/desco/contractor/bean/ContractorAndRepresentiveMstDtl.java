package com.ibcs.desco.contractor.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ContractorAndRepresentiveMstDtl {

	private Integer contractorId;
	private String contractNo;
	private String contractorName;
	private String contractorType;
	private String address;
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractDate;
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date expiryDate;
	private String tenderNo;
	private Integer khathId;
	private String division;
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date updatedValidityDate;
	private String remarks;

	private String deptId;

	//

	private List<String> representativeName;
	private List<String> repUserId;
	private List<String> reDesignation;
	private List<String> repEmail;
	private List<String> repMobile;
	private List<String> repAddress;
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private List<Date> repStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private List<Date> repEndDate;
	// private List<String> picture;
	private List<String> repRemarks;
	private List<String> repSignature;

	public Integer getContractorId() {
		return contractorId;
	}

	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
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

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
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

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Date getUpdatedValidityDate() {
		return updatedValidityDate;
	}

	public void setUpdatedValidityDate(Date updatedValidityDate) {
		this.updatedValidityDate = updatedValidityDate;
	}

	public String getContractorType() {
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<String> getRepresentativeName() {
		return representativeName;
	}

	public void setRepresentativeName(List<String> representativeName) {
		this.representativeName = representativeName;
	}

	public List<String> getRepUserId() {
		return repUserId;
	}

	public void setRepUserId(List<String> repUserId) {
		this.repUserId = repUserId;
	}

	public List<String> getReDesignation() {
		return reDesignation;
	}

	public void setReDesignation(List<String> reDesignation) {
		this.reDesignation = reDesignation;
	}

	public List<String> getRepEmail() {
		return repEmail;
	}

	public void setRepEmail(List<String> repEmail) {
		this.repEmail = repEmail;
	}

	public List<String> getRepMobile() {
		return repMobile;
	}

	public void setRepMobile(List<String> repMobile) {
		this.repMobile = repMobile;
	}

	public List<String> getRepAddress() {
		return repAddress;
	}

	public void setRepAddress(List<String> repAddress) {
		this.repAddress = repAddress;
	}

	public List<Date> getRepStartDate() {
		return repStartDate;
	}

	public void setRepStartDate(List<Date> repStartDate) {
		this.repStartDate = repStartDate;
	}

	public List<Date> getRepEndDate() {
		return repEndDate;
	}

	public void setRepEndDate(List<Date> repEndDate) {
		this.repEndDate = repEndDate;
	}

	public List<String> getRepRemarks() {
		return repRemarks;
	}

	public void setRepRemarks(List<String> repRemarks) {
		this.repRemarks = repRemarks;
	}

	public List<String> getRepSignature() {
		return repSignature;
	}

	public void setRepSignature(List<String> repSignature) {
		this.repSignature = repSignature;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
