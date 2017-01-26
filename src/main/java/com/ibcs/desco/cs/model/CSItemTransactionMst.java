package com.ibcs.desco.cs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "CS_ITEM_TNX_MST")
public class CSItemTransactionMst {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_item_tran_mst_seq")
	@SequenceGenerator(name = "cs_item_tran_mst_seq", sequenceName = "cs_item_tran_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "ledger_name")
	private String ledgerName;

	@Transient
	private String itemName;

	@Transient
	private String uom;

	@Transient
	private ItemMaster itemMaster;

	// This is used for Material Item Delivery
	@Transient
	private Double deliveredQty;

	// @Column(name = "treshol_balance")
	// private double tresholBalance;

	@Column(name = "safety_margin")
	private double safetyMargin;

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getSafetyMargin() {
		return safetyMargin;
	}

	public void setSafetyMargin(double safetyMargin) {
		this.safetyMargin = safetyMargin;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
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

	public Double getDeliveredQty() {
		return deliveredQty;
	}

	public void setDeliveredQty(Double deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

}
