package com.ibcs.desco.inventory.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ALL_LOOKUP")
public class AllLookUp {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "all_lookup_sequence")
	@SequenceGenerator(name = "all_lookup_sequence", sequenceName = "all_lookup_sequence", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "KEYWORD", unique = true)
	private String keyword;

	@Column(name = "PARENT_ID")
	private Integer parentId;

	@Column(name = "PARENT_NAME")
	private String parentName;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "SORT_BY")
	private Integer sortBy;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "IS_ACTIVE")
	private Integer isActiveCheck;

	@Column(name = "REMARKS")
	private String remarks;

	public AllLookUp() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSortBy() {
		return sortBy;
	}

	public void setSortBy(Integer sortBy) {
		this.sortBy = sortBy;
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

	public Integer getIsActiveCheck() {
		return isActiveCheck;
	}

	public void setIsActiveCheck(Integer isActiveCheck) {
		this.isActiveCheck = isActiveCheck;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "AllLookUp [id=" + id + ", keyword=" + keyword + ", parentId=" + parentId + ", parentName=" + parentName
				+ ", title=" + title + ", sortBy=" + sortBy + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", isActiveCheck="
				+ isActiveCheck + ", remarks=" + remarks + "]";
	}

}
