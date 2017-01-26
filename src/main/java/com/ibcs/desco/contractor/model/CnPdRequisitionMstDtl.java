package com.ibcs.desco.contractor.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CnPdRequisitionMstDtl {
	private Integer id;

	private String requisitionNo;

	private String stateCode;

	private String requisitionTo;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date requisitionDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
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

	private Double quantity = 0.0;

	private String justification;

	private List<String> issueqty;

	private String userid;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	private List<String> jobNo;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<String> ledgerName;

	private List<Double> quantityRequired;

	private List<Double> quantityIssued;

	private List<Double> unitCost;

	private List<Double> totalCost;

	private List<String> headOfAccount;

	private List<Double> requiredQuantity;

	private List<String> pndJobDtlId;

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

	public List<String> getJobNo() {
		return jobNo;
	}

	public void setJobNo(List<String> jobNo) {
		this.jobNo = jobNo;
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

	public List<String> getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(List<String> headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public List<Double> getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(List<Double> requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public List<String> getPndJobDtlId() {
		return pndJobDtlId;
	}

	public void setPndJobDtlId(List<String> pndJobDtlId) {
		this.pndJobDtlId = pndJobDtlId;
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

	public List<String> getCnReqDtlId() {
		return cnReqDtlId;
	}

	public void setCnReqDtlId(List<String> cnReqDtlId) {
		this.cnReqDtlId = cnReqDtlId;
	}

	public List<String> getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(List<String> issueqty) {
		this.issueqty = issueqty;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
