package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CentralStoreRequisitionMstDtl {

	private Integer id;

	private String requisitionNo;

	private String workOrderNumber;

	private Integer khathId;
	
	private String khathName;

	private List<String> ledgerName;

	private Integer requestedDeptId;

	// for back to user
	private String stage;

	private String return_to;

	private String return_state;

	private boolean received;

	// comment
	private String justification;

	private String stateCode;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date requisitionDate;

	private String identerDesignation;

	private String storeTicketNO;

	private String deptName;

	private String receivedBy;

	private String gatePassNo;

	private String requisitionTo;

	private String senderStore;
	
	private String carriedBy;
	
	private Map<String, Double> jobCardQtyMap;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date gatePassDate;

	private boolean active = true;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> quantityRequired;

	private List<Double> quantityIssued;

	private List<Double> unitCost;

	private List<Double> totalCost;

	private List<String> headOfAccount;

	private List<String> remarks;

	private List<String> issueqty;

	private List<Double> quantityIssuedNS;

	private List<Double> quantityIssuedRS;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(List<String> issueqty) {
		this.issueqty = issueqty;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public Date getRequisitionDate() {
		return requisitionDate;
	}

	public void setRequisitionDate(Date requisitionDate) {
		this.requisitionDate = requisitionDate;
	}

	public String getIdenterDesignation() {
		return identerDesignation;
	}

	public void setIdenterDesignation(String identerDesignation) {
		this.identerDesignation = identerDesignation;
	}

	public Integer getRequestedDeptId() {
		return requestedDeptId;
	}

	public void setRequestedDeptId(Integer requestedDeptId) {
		this.requestedDeptId = requestedDeptId;
	}

	public String getStoreTicketNO() {
		return storeTicketNO;
	}

	public void setStoreTicketNO(String storeTicketNO) {
		this.storeTicketNO = storeTicketNO;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getReturn_to() {
		return return_to;
	}

	public void setReturn_to(String return_to) {
		this.return_to = return_to;
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

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public String getRequisitionTo() {
		return requisitionTo;
	}

	public void setRequisitionTo(String requisitionTo) {
		this.requisitionTo = requisitionTo;
	}

	public String getSenderStore() {
		return senderStore;
	}

	public void setSenderStore(String senderStore) {
		this.senderStore = senderStore;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public List<String> getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(List<String> ledgerName) {
		this.ledgerName = ledgerName;
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

	public List<String> getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(List<String> headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
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

	public List<Double> getQuantityIssuedNS() {
		return quantityIssuedNS;
	}

	public void setQuantityIssuedNS(List<Double> quantityIssuedNS) {
		this.quantityIssuedNS = quantityIssuedNS;
	}

	public List<Double> getQuantityIssuedRS() {
		return quantityIssuedRS;
	}

	public void setQuantityIssuedRS(List<Double> quantityIssuedRS) {
		this.quantityIssuedRS = quantityIssuedRS;
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

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}
	
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public Map<String, Double> getJobCardQtyMap() {
		return jobCardQtyMap;
	}

	public void setJobCardQtyMap(Map<String, Double> jobCardQtyMap) {
		this.jobCardQtyMap = jobCardQtyMap;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getCarriedBy() {
		return carriedBy;
	}

	public void setCarriedBy(String carriedBy) {
		this.carriedBy = carriedBy;
	}	

}
