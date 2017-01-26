package com.ibcs.desco.workshop.model;

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
@Table(name = "WS_JOB_SUMMARY")
public class WsJobSummary {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WS_JOB_SUMMARY_ID_SEQ")
	@SequenceGenerator(name = "WS_JOB_SUMMARY_ID_SEQ", sequenceName = "WS_JOB_SUMMARY_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "total_qty")
	private Double totalQty = 0.0;

	@Column(name = "remaining_qty")
	private Double remainingQty;

	@Column(name = "is_active")
	private boolean active;

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
	
	public WsJobSummary(){
		
	}
	

	public WsJobSummary(Integer id, String contractNo, String itemCode,
			Double totalQty, Double remainingQty, boolean active,
			String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.contractNo = contractNo;
		this.itemCode = itemCode;
		this.totalQty = totalQty;
		this.remainingQty = remainingQty;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public Double getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Double remainingQty) {
		this.remainingQty = remainingQty;
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

}
