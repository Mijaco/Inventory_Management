package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CSStoreTicketMstDtl {
	private Integer id;

	private String ticketNo;

	private String justification;

	private String storeTicketType;

	private String operationId;

	private String receivedFrom;

	private String workOrderNo;

	private String receivedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ticketDate;

	private String returnBy;

	private String returnFor;

	private String issuedTo;

	private String issuedFor;

	private String issuedBy;

	private String gatePass;

	private boolean flag = false;

	private String status;

	private boolean active = true;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	// details
	private Integer storeTicketId;

	private List<String> ledgerName;

	private List<Integer> locationId;

	private List<String> itemId;

	private List<String> description;

	private List<String> uom;

	private List<Double> cost;

	private List<String> ledgerBookNo;

	private List<String> ledgerPageNo;

	private List<Double> quantity;

	private List<Double> qtyNewServiceable;

	private List<Double> qtyRecServiceable;

	private List<Double> qtyUnServiceable;

	private List<String> remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getStoreTicketType() {
		return storeTicketType;
	}

	public void setStoreTicketType(String storeTicketType) {
		this.storeTicketType = storeTicketType;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	public String getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}

	public String getReturnFor() {
		return returnFor;
	}

	public void setReturnFor(String returnFor) {
		this.returnFor = returnFor;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getIssuedFor() {
		return issuedFor;
	}

	public void setIssuedFor(String issuedFor) {
		this.issuedFor = issuedFor;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getGatePass() {
		return gatePass;
	}

	public void setGatePass(String gatePass) {
		this.gatePass = gatePass;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getStoreTicketId() {
		return storeTicketId;
	}

	public void setStoreTicketId(Integer storeTicketId) {
		this.storeTicketId = storeTicketId;
	}

	public List<String> getItemId() {
		return itemId;
	}

	public void setItemId(List<String> itemId) {
		this.itemId = itemId;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(List<String> ledgerName) {
		this.ledgerName = ledgerName;
	}

	public List<Integer> getLocationId() {
		return locationId;
	}

	public void setLocationId(List<Integer> locationId) {
		this.locationId = locationId;
	}

	public List<String> getUom() {
		return uom;
	}

	public void setUom(List<String> uom) {
		this.uom = uom;
	}

	public List<String> getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(List<String> ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}

	public List<String> getLedgerPageNo() {
		return ledgerPageNo;
	}

	public void setLedgerPageNo(List<String> ledgerPageNo) {
		this.ledgerPageNo = ledgerPageNo;
	}

	public List<Double> getCost() {
		return cost;
	}

	public void setCost(List<Double> cost) {
		this.cost = cost;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public List<Double> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Double> quantity) {
		this.quantity = quantity;
	}

	public List<Double> getQtyNewServiceable() {
		return qtyNewServiceable;
	}

	public void setQtyNewServiceable(List<Double> qtyNewServiceable) {
		this.qtyNewServiceable = qtyNewServiceable;
	}

	public List<Double> getQtyRecServiceable() {
		return qtyRecServiceable;
	}

	public void setQtyRecServiceable(List<Double> qtyRecServiceable) {
		this.qtyRecServiceable = qtyRecServiceable;
	}

	public List<Double> getQtyUnServiceable() {
		return qtyUnServiceable;
	}

	public void setQtyUnServiceable(List<Double> qtyUnServiceable) {
		this.qtyUnServiceable = qtyUnServiceable;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

}
