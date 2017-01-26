package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemReceived {
	private Integer id;

	private String receivedReportNo;

	private String contractNo;

	private String stateCode;

	private String uuid;
	
	private Integer khathId;	

	private String khathName;

	private String ledgerName;

	private String chalanNo;

	private String roleName;

	private String supplierName;

	private String justification;

	private String rrNo;

	private String stage;

	private String return_to;

	private String return_state;

	private List<Double> expectedQty;

	private List<Double> receivedQty;

	private List<Double> remainingQty;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date landingDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date contractDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date invoiceDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date deliveryDate;

	private String billOfLanding;

	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedDate;
	private String receivedBy;
	private List<String> itemId;
	private List<String> description;
	private List<String> uom;
	private List<Double> quantity;
	private List<Double> cost;
	private List<String> ledgerBookNo;
	private List<String> ledgerPageNo;
	private List<String> remarks;
	private String createdBy;
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	private boolean active = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getBillOfLanding() {
		return billOfLanding;
	}
	

	public String getKhathName() {
		return khathName;
	}
	
	

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public void setBillOfLanding(String billOfLanding) {
		this.billOfLanding = billOfLanding;
	}

	public String getReceivedReportNo() {
		return receivedReportNo;
	}

	public void setReceivedReportNo(String receivedReportNo) {
		this.receivedReportNo = receivedReportNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public Date getLandingDate() {
		return landingDate;
	}

	public void setLandingDate(Date landingDate) {
		this.landingDate = landingDate;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public List<String> getUom() {
		return uom;
	}

	public void setUom(List<String> uom) {
		this.uom = uom;
	}

	public List<Double> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Double> quantity) {
		this.quantity = quantity;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRrNo() {
		return rrNo;
	}

	public void setRrNo(String rrNo) {
		this.rrNo = rrNo;
	}

	public List<Double> getExpectedQty() {
		return expectedQty;
	}

	public void setExpectedQty(List<Double> expectedQty) {
		this.expectedQty = expectedQty;
	}

	public List<Double> getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(List<Double> receivedQty) {
		this.receivedQty = receivedQty;
	}

	public List<Double> getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(List<Double> remainingQty) {
		this.remainingQty = remainingQty;
	}

	public List<Double> getCost() {
		return cost;
	}

	public void setCost(List<Double> cost) {
		this.cost = cost;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
