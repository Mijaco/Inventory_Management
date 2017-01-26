package com.ibcs.desco.workshop.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CnWsRequisitionMstDtl {
	private Integer id;

	private String requisitionNo;

	private String stateCode;

	private String requisitionTo;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date requisitionDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date contractDate;

	private String contractNo;

	private Integer khathId;

	private String deptId;

	private String khathName;

	private String deptName;

	private String uuid;

	private String identerDesignation;

	private String storeTicketNo;

	private String gatePassNo;

	private String receivedBy;

	private String remarks;

	private String justification;

	private List<String> issueqty;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	private List<String> jobCardNo;

	private List<String> dtlId;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> unit;

	private List<String> ledgerName;

	private List<String> remarksDtl;

	private List<Double> quantityRequired;

	private List<Double> quantityIssued;

	private List<String> headOfAccount;

	private List<Double> quantityRecovery;

	private List<Double> quantityUsed;

	private List<String> jobCardDtlId;

	private List<String> cnReqDtlId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRequisitionTo() {
		return requisitionTo;
	}

	public void setRequisitionTo(String requisitionTo) {
		this.requisitionTo = requisitionTo;
	}

	public Date getRequisitionDate() {
		return requisitionDate;
	}

	public void setRequisitionDate(Date requisitionDate) {
		this.requisitionDate = requisitionDate;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIdenterDesignation() {
		return identerDesignation;
	}

	public void setIdenterDesignation(String identerDesignation) {
		this.identerDesignation = identerDesignation;
	}

	public String getStoreTicketNo() {
		return storeTicketNo;
	}

	public void setStoreTicketNo(String storeTicketNo) {
		this.storeTicketNo = storeTicketNo;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public List<String> getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(List<String> issueqty) {
		this.issueqty = issueqty;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<String> getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(List<String> jobCardNo) {
		this.jobCardNo = jobCardNo;
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

	public List<String> getUnit() {
		return unit;
	}

	public void setUnit(List<String> unit) {
		this.unit = unit;
	}

	public List<String> getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(List<String> ledgerName) {
		this.ledgerName = ledgerName;
	}

	public List<Double> getQuantityRequired() {
		return quantityRequired;
	}

	public void setQuantityRequired(List<Double> quantityRequired) {
		this.quantityRequired = quantityRequired;
	}

	public List<Double> getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(List<Double> quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public List<String> getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(List<String> headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public List<Double> getQuantityRecovery() {
		return quantityRecovery;
	}

	public void setQuantityRecovery(List<Double> quantityRecovery) {
		this.quantityRecovery = quantityRecovery;
	}

	public List<Double> getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(List<Double> quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public List<String> getJobCardDtlId() {
		return jobCardDtlId;
	}

	public void setJobCardDtlId(List<String> jobCardDtlId) {
		this.jobCardDtlId = jobCardDtlId;
	}

	public List<String> getCnReqDtlId() {
		return cnReqDtlId;
	}

	public void setCnReqDtlId(List<String> cnReqDtlId) {
		this.cnReqDtlId = cnReqDtlId;
	}

	public List<String> getDtlId() {
		return dtlId;
	}

	public void setDtlId(List<String> dtlId) {
		this.dtlId = dtlId;
	}

	public List<String> getRemarksDtl() {
		return remarksDtl;
	}

	public void setRemarksDtl(List<String> remarksDtl) {
		this.remarksDtl = remarksDtl;
	}

}
