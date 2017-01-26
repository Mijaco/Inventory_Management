package com.ibcs.desco.workshop.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class WsReceivePreventiveMstDtl {

	private Integer id;

	private String noteNumber;

	private String workOrderNumber;

	private Integer khathId;

	private List<String> ledgerName;
	
	private String referenceNumber;
	
	private String receiveDate;

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

	private String deptId;

	private String deptName;

	private String rcvDeptName;
	
	private String zone;

	private String receivedBy;

	private String gatePassNo;

	private String requisitionTo;

	private String senderStore;

	private String remark;
	
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

	private List<Double> quantity;

	private List<String> remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNoteNumber() {
		return noteNumber;
	}

	public void setNoteNumber(String noteNumber) {
		this.noteNumber = noteNumber;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List<Double> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Double> quantity) {
		this.quantity = quantity;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
	
	public String getRcvDeptName() {
		return rcvDeptName;
	}

	public void setRcvDeptName(String rcvDeptName) {
		this.rcvDeptName = rcvDeptName;
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
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

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
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

}
