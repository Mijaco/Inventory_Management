package com.ibcs.desco.localStore.model;

import java.util.Date;

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

@Entity
@Table(name = "LOCAL_PURCHASE_DTL")
public class LocalPurchaseDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCAL_PURCHASE_DTL_SEQ")
	@SequenceGenerator(name = "LOCAL_PURCHASE_DTL_SEQ", sequenceName = "LOCAL_PURCHASE_DTL_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "LOCAL_PURCHASE_MST", nullable = false)
	LocalPurchaseMst localPurchaseMst;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "uom")
	private String uom;

	@Column(name = "qty")
	private double receivedQty = 0.0;

	@Column(name = "unit_cost")
	private double unitCost = 0.0;

	@Column(name = "total_cost")
	private double totalCost = 0.0;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalPurchaseMst getLocalPurchaseMst() {
		return localPurchaseMst;
	}

	public void setLocalPurchaseMst(LocalPurchaseMst localPurchaseMst) {
		this.localPurchaseMst = localPurchaseMst;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public double getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(double receivedQty) {
		this.receivedQty = receivedQty;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
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
	
	public LocalPurchaseDtl() {

	}

	public LocalPurchaseDtl(Integer id,
			com.ibcs.desco.localStore.model.LocalPurchaseMst localPurchaseMst,
			String itemCode, String itemName, String uom, double receivedQty,
			double unitCost, double totalCost, boolean active, String remarks,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate) {
		super();
		this.id = id;
		this.localPurchaseMst = localPurchaseMst;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.uom = uom;
		this.receivedQty = receivedQty;
		this.unitCost = unitCost;
		this.totalCost = totalCost;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "LocalPurchaseDtl [id=" + id + ", LocalPurchaseMst="
				+ localPurchaseMst + ", itemCode=" + itemCode + ", itemName="
				+ itemName + ", uom=" + uom + ", receivedQty=" + receivedQty
				+ ", unitCost=" + unitCost + ", totalCost=" + totalCost
				+ ", active=" + active + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
	

}
