package com.ibcs.desco.common.model;

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
@Table(name = "PETTY_CASH_DTL")
public class PettyCashDtl {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETTY_CASH_DTL_SEQ")
	@SequenceGenerator(name = "PETTY_CASH_DTL_SEQ", sequenceName = "PETTY_CASH_DTL_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	
	@Column( name= "petty_cash_code" )
	private String pettyCashCode;
	
	@Column( name = "petty_cash_head" )
	private String pettyCashHead;
	
	@Column( name = "description" )
	private String description;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "PETTY_CASH_MST_ID")
	private PettyCashMst pettyCashMst;
	
	@Column( name = "total_cost" )
	private Double totalCost = 0.0;
	
	@Column(name = "isActive")
	private boolean active = true;

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

	public String getPettyCashCode() {
		return pettyCashCode;
	}

	public void setPettyCashCode(String pettyCashCode) {
		this.pettyCashCode = pettyCashCode;
	}

	public String getPettyCashHead() {
		return pettyCashHead;
	}

	public void setPettyCashHead(String pettyCashHead) {
		this.pettyCashHead = pettyCashHead;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public PettyCashMst getPettyCashMst() {
		return pettyCashMst;
	}

	public void setPettyCashMst(PettyCashMst pettyCashMst) {
		this.pettyCashMst = pettyCashMst;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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
