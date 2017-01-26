package com.ibcs.desco.inventory.model;

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

@Entity
@Table(name = "item_master")
public class ItemMaster {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Item_master_id_seq")
	@SequenceGenerator(name = "Item_master_id_seq", sequenceName = "Item_master_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "CATEGORY_ID")
	private Integer categoryId;

	@Column(name = "ITEM_ID")
	private String itemId;
	
	@Column(name = "ITEM_ID_OLD")
	private String oldItemCode;	

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Transient
	private String requisitionTo;

	@Transient
	private String ledgerName;

	@Transient
	private String khathId;

	@Transient
	private Double price;

	@Column(name = "UNIT_CODE")
	private String unitCode;
	
	@Column(name = "item_type")
	private String itemType;
	
	@Column(name = "is_fixedAsset")
	private String fixedAsset;
	
	@Column(name = "LIFE_TIME")
	private String lifeTime;
	
	@Column(name = "DEPRECIATION_RATE")
	private Double depreciationRate;

	@Column(name = "SPECIAL_APPROVAL")
	private Integer specialApproval;

	@Column(name = "ACTIVE")
	private Integer itemActive;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATE_BY")
	private String createdBy;

	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	@Transient
	private double currentStock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	

	public String getOldItemCode() {
		return oldItemCode;
	}

	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getSpecialApproval() {
		return specialApproval;
	}

	public void setSpecialApproval(Integer specialApproval) {
		this.specialApproval = specialApproval;
	}

	public Integer getItemActive() {
		return itemActive;
	}

	public void setItemActive(Integer itemActive) {
		this.itemActive = itemActive;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public String getKhathId() {
		return khathId;
	}

	public void setKhathId(String khathId) {
		this.khathId = khathId;
	}

	public String getRequisitionTo() {
		return requisitionTo;
	}

	public void setRequisitionTo(String requisitionTo) {
		this.requisitionTo = requisitionTo;
	}
	
	
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getFixedAsset() {
		return fixedAsset;
	}

	public void setFixedAsset(String fixedAsset) {
		this.fixedAsset = fixedAsset;
	}

	public String getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Double getDepreciationRate() {
		return depreciationRate;
	}

	public void setDepreciationRate(Double depreciationRate) {
		this.depreciationRate = depreciationRate;
	}

	@Override
	public String toString() {
		return "ItemMaster [id=" + id + ", categoryId=" + categoryId
				+ ", itemId=" + itemId + ", itemName=" + itemName
				+ ", unitCode=" + unitCode + ", specialApproval="
				+ specialApproval + ", itemActive=" + itemActive + ", remarks="
				+ remarks + ", createdBy=" + createdBy + ", createDate="
				+ createDate + ", modifiedBy=" + modifiedBy + ", modifyDate="
				+ modifyDate + "]";
	}

}
