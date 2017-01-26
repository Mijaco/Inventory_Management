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

@Entity
@Table(name = "item_category")
public class ItemCategory {

	@Id
	@Column(name = "CATEGORY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_category_id_seq")
	@SequenceGenerator(name = "item_category_id_seq", sequenceName = "item_category_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "categoryId")
	private Integer categoryId;
	
	@Column(name = "category_id_old")
	private Integer oldCategoryId;	

	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name = "item_type")
	private String itemType;

	@Column(name = "ACTIVE")
	private Integer activeItemCategory;

	@Column(name = "REMARKS")
	private String itemCategoryRemarks;

	@Column(name = "CREATE_BY")
	private String createdBy;

	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getActiveItemCategory() {
		return activeItemCategory;
	}

	public void setActiveItemCategory(Integer activeItemCategory) {
		this.activeItemCategory = activeItemCategory;
	}

	public String getItemCategoryRemarks() {
		return itemCategoryRemarks;
	}

	public void setItemCategoryRemarks(String itemCategoryRemarks) {
		this.itemCategoryRemarks = itemCategoryRemarks;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOldCategoryId() {
		return oldCategoryId;
	}

	public void setOldCategoryId(Integer oldCategoryId) {
		this.oldCategoryId = oldCategoryId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return "ItemCategory [id=" + id + ", categoryId=" + categoryId
				+ ", oldCategoryId=" + oldCategoryId + ", categoryName="
				+ categoryName + ", itemType=" + itemType
				+ ", activeItemCategory=" + activeItemCategory
				+ ", itemCategoryRemarks=" + itemCategoryRemarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

	

}
