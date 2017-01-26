package com.ibcs.desco.common.model;

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
@Table(name = "DESCO_REQUISITION_LOCK")
public class RequisitionLock {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESCO_REQUISITION_LOCK_SEQ")
	@SequenceGenerator(name = "DESCO_REQUISITION_LOCK_SEQ", sequenceName = "DESCO_REQUISITION_LOCK_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "requisition_no")
	private String requisitionNo;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "store_code")
	private String storeCode;

	@Column(name = "isFlag")
	private boolean flag = true;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "locked_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date locakedDate;

	@Column(name = "unlocked_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date unlocakedDate;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "is_active")
	private boolean active = true;
	
	public RequisitionLock(){}

	public RequisitionLock(Integer id, String requisitionNo, String itemCode,
			String storeCode, boolean flag, double quantity, Date locakedDate,
			Date unlocakedDate, String createdBy, Date createdDate,
			boolean active) {
		super();
		this.id = id;
		this.requisitionNo = requisitionNo;
		this.itemCode = itemCode;
		this.storeCode = storeCode;
		this.flag = flag;
		this.quantity = quantity;
		this.locakedDate = locakedDate;
		this.unlocakedDate = unlocakedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Date getLocakedDate() {
		return locakedDate;
	}

	public void setLocakedDate(Date locakedDate) {
		this.locakedDate = locakedDate;
	}

	public Date getUnlocakedDate() {
		return unlocakedDate;
	}

	public void setUnlocakedDate(Date unlocakedDate) {
		this.unlocakedDate = unlocakedDate;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
