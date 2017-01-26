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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "inventory_item")
public class InventoryItem {

	@Id
	@Column(name = "item_code")    
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_item_sequence")
	@SequenceGenerator(name = "inventory_item_sequence", sequenceName = "inventory_item_sequence", allocationSize = 1, initialValue = 1)
	private Integer inventoryItemId;

	@Column(name = "Version")
	private Integer inventoryItemVersion;

	@Column(name = "Item_group_name")
	private String inventoryItemGroupName;

	@Column(name = "isActive")
	private Integer inventoryItemIsActive;

	@Column(name = "Inv_Item_code")
	private String inventoryItemItemCode;

	@Column(name = "Item_name")
	private String inventoryItemName;

	@Column(name = "Item_sub_group_id")
	private String inventoryItemSubGroupName;

	@Column(name = "Item_type")
	private String inventoryItemType;

	@Column(name = "UOM_ID")
	private String inventoryItemUint;

	@Column(name = "Short_item_name")
	private String inventoryItemShortName;

	@Column(name = "Asset_major_category")
	private String inventoryItemAssetMajorCategory;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_Date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createdDate;
	
	@Column(name = "modified_by")
	private String modifiedby;
	
	@Column(name = "modified_Date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date modifiedDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getInventoryItemGroupName() {
		return inventoryItemGroupName;
	}

	public void setInventoryItemGroupName(String inventoryItemGroupName) {
		this.inventoryItemGroupName = inventoryItemGroupName;
	}

	public String getInventoryItemSubGroupName() {
		return inventoryItemSubGroupName;
	}

	public void setInventoryItemSubGroupName(String inventoryItemSubGroupName) {
		this.inventoryItemSubGroupName = inventoryItemSubGroupName;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public InventoryItem() {
	}

	public Integer getInventoryItemId() {
		return inventoryItemId;
	}

	public void setInventoryItemId(Integer inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public Integer getInventoryItemVersion() {
		return inventoryItemVersion;
	}

	public void setInventoryItemVersion(Integer inventoryItemVersion) {
		this.inventoryItemVersion = inventoryItemVersion;
	}

	
	public Integer getInventoryItemIsActive() {
		return inventoryItemIsActive;
	}

	public void setInventoryItemIsActive(Integer inventoryItemIsActive) {
		this.inventoryItemIsActive = inventoryItemIsActive;
	}

	public String getInventoryItemItemCode() {
		return inventoryItemItemCode;
	}

	public void setInventoryItemItemCode(String inventoryItemItemCode) {
		this.inventoryItemItemCode = inventoryItemItemCode;
	}

	public String getInventoryItemName() {
		return inventoryItemName;
	}

	public void setInventoryItemName(String inventoryItemName) {
		this.inventoryItemName = inventoryItemName;
	}

	
	public String getInventoryItemType() {
		return inventoryItemType;
	}

	public void setInventoryItemType(String inventoryItemType) {
		this.inventoryItemType = inventoryItemType;
	}

	

	public String getInventoryItemUint() {
		return inventoryItemUint;
	}

	public void setInventoryItemUint(String inventoryItemUint) {
		this.inventoryItemUint = inventoryItemUint;
	}

	public String getInventoryItemShortName() {
		return inventoryItemShortName;
	}

	public void setInventoryItemShortName(String inventoryItemShortName) {
		this.inventoryItemShortName = inventoryItemShortName;
	}

	public String getInventoryItemAssetMajorCategory() {
		return inventoryItemAssetMajorCategory;
	}

	public void setInventoryItemAssetMajorCategory(String inventoryItemAssetMajorCategory) {
		this.inventoryItemAssetMajorCategory = inventoryItemAssetMajorCategory;
	}

	@Override
	public String toString() {
		return "InventoryItem [inventoryItemId=" + inventoryItemId + ", inventoryItemVersion=" + inventoryItemVersion
				+ ", inventoryItemGroupId=" + inventoryItemGroupName + ", inventoryItemIsActive=" + inventoryItemIsActive
				+ ", inventoryItemItemCode=" + inventoryItemItemCode + ", inventoryItemName=" + inventoryItemName
				+ ", inventoryItemSubGroupId=" + inventoryItemSubGroupName + ", inventoryItemType=" + inventoryItemType
				+ ", inventoryItemUintId=" + inventoryItemUint + ", inventoryItemShortName=" + inventoryItemShortName
				+ ", inventoryItemAssetMajorCategory=" + inventoryItemAssetMajorCategory + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedby=" + modifiedby + ", modifiedDate=" + modifiedDate
				+ "]";
	}

	

}
