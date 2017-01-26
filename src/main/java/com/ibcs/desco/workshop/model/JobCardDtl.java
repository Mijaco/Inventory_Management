package com.ibcs.desco.workshop.model;

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
import javax.persistence.Transient;

@Entity
@Table(name = "JOB_CARD_DTL")
public class JobCardDtl {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_CARD_DTL_ID_SEQ")
	@SequenceGenerator(name = "JOB_CARD_DTL_ID_SEQ", sequenceName = "JOB_CARD_DTL_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Transient
	private int jobCardMstId;

	@ManyToOne
	@JoinColumn(name = "job_card_mst_id")
	private JobCardMst jobCardMst;

	@Column(name = "remainning_quantity")
	private Double remainningQuantity = 0.0;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_name")
	private String itemName;

	@Transient
	private String jobCardNo;

	@Column(name = "unit")
	private String unit;

	@Column(name = "quantity_used")
	private double quantityUsed = 0.0;

	@Column(name = "quantity_recovery")
	private double quantityRecovery = 0.0;

	@Column(name = "quantity_issue")
	private double quantityIssue = 0.0;

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

	public int getJobCardMstId() {
		return jobCardMstId;
	}

	public void setJobCardMstId(int jobCardMstId) {
		this.jobCardMstId = jobCardMstId;
	}

	public JobCardMst getJobCardMst() {
		return jobCardMst;
	}

	public void setJobCardMst(JobCardMst jobCardMst) {
		this.jobCardMst = jobCardMst;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(double quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public double getQuantityRecovery() {
		return quantityRecovery;
	}

	public void setQuantityRecovery(double quantityRecovery) {
		this.quantityRecovery = quantityRecovery;
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

	public String getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}

	public Double getRemainningQuantity() {
		return remainningQuantity;
	}

	public void setRemainningQuantity(Double remainningQuantity) {
		this.remainningQuantity = remainningQuantity;
	}

	public double getQuantityIssue() {
		return quantityIssue;
	}

	public void setQuantityIssue(double quantityIssue) {
		this.quantityIssue = quantityIssue;
	}

}
