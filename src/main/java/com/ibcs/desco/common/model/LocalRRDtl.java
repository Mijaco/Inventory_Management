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
@Table(name = "LOCAL_RR_DTL")
public class LocalRRDtl {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PETTY_CASH_DTL_SEQ")
	@SequenceGenerator(name = "PETTY_CASH_DTL_SEQ", sequenceName = "PETTY_CASH_DTL_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	
	@Column( name= "item_code" )
	private String itemCode;
	
	@Column( name= "item_name" )
	private String itemName;
	
	@Column( name= "uom" )
	private String uom;
	
	@Column( name= "purchased_qty" )
	private String purchasedQty;
	
	@Column( name= "required_qty" )
	private String requiredQty;
	
	@Column( name= "ledger_book" )
	private String ledgerBook;
	
	@Column( name= "page_no" )
	private String pageNo;
	
	@Column( name= "remarks" )
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "LOCAL_RR_MST_ID")
	private LocalRRMst localRRMst;
	
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

	public String getPurchasedQty() {
		return purchasedQty;
	}

	public void setPurchasedQty(String purchasedQty) {
		this.purchasedQty = purchasedQty;
	}

	public String getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(String requiredQty) {
		this.requiredQty = requiredQty;
	}

	public String getLedgerBook() {
		return ledgerBook;
	}

	public void setLedgerBook(String ledgerBook) {
		this.ledgerBook = ledgerBook;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalRRMst getLocalRRMst() {
		return localRRMst;
	}

	public void setLocalRRMst(LocalRRMst localRRMst) {
		this.localRRMst = localRRMst;
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
