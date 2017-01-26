package com.ibcs.desco.cs.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "CS_REQUISITION_DTL")
public class CentralStoreRequisitionDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_requisition_dtl_id_seq")
	@SequenceGenerator(name = "cs_requisition_dtl_id_seq", sequenceName = "cs_requisition_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "item_Code")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "LEDGER_NAME")
	private String ledgerName;

	@Transient
	private double remainingQty = 0.0;

	@Transient
	private List<Double> remainQty;

	@Column(name = "QTY_REQUIRED")
	private double quantityRequired = 0.0;

	@Column(name = "QTY_ISSUED")
	private double quantityIssued = 0.0;

	@Column(name = "unit_Cost")
	private double unitCost = 0.0;

	@Column(name = "total_Cost")
	private double totalCost = 0.0;

	@Column(name = "HEAD_OF_ACCT")
	private String headOfAccount;

	@Column(name = "Requisition_no")
	private String requisitionNo;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "cn_ws_status")
	private String cnWsStatus = "";
	
	@Transient
	private Double newServiceableStockQty = 0.0;
	
	@Transient
	private Double recoveryServiceableStockQty = 0.0;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "requisition_type")
	private String requisitionType;

	@Transient
	private int centralStoreRequisitionId;

	@ManyToOne
	@JoinColumn(name = "Master_Id")
	private CentralStoreRequisitionMst centralStoreRequisitionMst;

	public CentralStoreRequisitionDtl() {

	}

	public CentralStoreRequisitionDtl(Integer id, String itemCode,
			String itemName, String uom, double quantityRequired,
			String requisitionNo, boolean active, String remarks,
			String createdBy, Date createdDate,
			CentralStoreRequisitionMst centralStoreRequisitionMst) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.uom = uom;
		this.quantityRequired = quantityRequired;
		this.requisitionNo = requisitionNo;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.centralStoreRequisitionMst = centralStoreRequisitionMst;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getQuantityRequired() {
		return quantityRequired;
	}

	public void setQuantityRequired(double quantityRequired) {
		this.quantityRequired = quantityRequired;
	}

	public double getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(double quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getCentralStoreRequisitionId() {
		return centralStoreRequisitionId;
	}

	public void setCentralStoreRequisitionId(int centralStoreRequisitionId) {
		this.centralStoreRequisitionId = centralStoreRequisitionId;
	}

	public CentralStoreRequisitionMst getCentralStoreRequisitionMst() {
		return centralStoreRequisitionMst;
	}

	public void setCentralStoreRequisitionMst(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {
		this.centralStoreRequisitionMst = centralStoreRequisitionMst;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
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

	public String getRequisitionType() {
		return requisitionType;
	}

	public void setRequisitionType(String requisitionType) {
		this.requisitionType = requisitionType;
	}

	public double getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(double remainingQty) {
		this.remainingQty = remainingQty;
	}

	public List<Double> getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(List<Double> remainQty) {
		this.remainQty = remainQty;
	}

	public String getCnWsStatus() {
		return cnWsStatus;
	}

	public void setCnWsStatus(String cnWsStatus) {
		this.cnWsStatus = cnWsStatus;
	}

	public Double getNewServiceableStockQty() {
		return newServiceableStockQty;
	}

	public void setNewServiceableStockQty(Double newServiceableStockQty) {
		this.newServiceableStockQty = newServiceableStockQty;
	}

	

	public Double getRecoveryServiceableStockQty() {
		return recoveryServiceableStockQty;
	}

	public void setRecoveryServiceableStockQty(Double recoveryServiceableStockQty) {
		this.recoveryServiceableStockQty = recoveryServiceableStockQty;
	}

	@Override
	public String toString() {
		return "CentralStoreRequisitionDtl [id=" + id + ", itemCode="
				+ itemCode + ", itemName=" + itemName + ", uom=" + uom
				+ ", quantityRequired=" + quantityRequired
				+ ", quantityIssued=" + quantityIssued + ", unitCost="
				+ unitCost + ", totalCost=" + totalCost + ", remarks="
				+ remarks + ", centralStoreRequisitionId="
				+ centralStoreRequisitionId + ", centralStoreRequisitionMst="
				+ centralStoreRequisitionMst + "]";
	}

}
