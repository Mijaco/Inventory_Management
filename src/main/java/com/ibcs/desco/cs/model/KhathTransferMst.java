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

@Entity
@Table(name = "KHATH_TRANSFER_MST")
public class KhathTransferMst {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_khath_transfer_mst_seq")
	@SequenceGenerator(name = "cs_khath_transfer_mst_seq", sequenceName = "cs_khath_transfer_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "khath_id_from")
	private Integer khathIdFrom;
	
	@Column(name = "khath_id_to")
	private Integer khathIdTo;
	
	@Column(name = "ledger_book")
	private String ledgerBook;
	
	@Column(name = "khath_from")
	private String khathFrom;
	
	@Column(name = "khath_to")
	private String khathTo;

	@Column(name = "transfer_no")
	private String transferNo;

	@Column(name = "reference_no")
	private String referenceNo;
	
	@Column(name = "reference_doc")
	private String referenceDoc;

	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "approved_flag")
	private String approvedFlag;

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

	public Integer getKhathIdFrom() {
		return khathIdFrom;
	}

	public void setKhathIdFrom(Integer khathIdFrom) {
		this.khathIdFrom = khathIdFrom;
	}

	public Integer getKhathIdTo() {
		return khathIdTo;
	}

	public void setKhathIdTo(Integer khathIdTo) {
		this.khathIdTo = khathIdTo;
	}

	public String getLedgerBook() {
		return ledgerBook;
	}

	public void setLedgerBook(String ledgerBook) {
		this.ledgerBook = ledgerBook;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getReferenceDoc() {
		return referenceDoc;
	}

	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getApprovedFlag() {
		return approvedFlag;
	}

	public void setApprovedFlag(String approvedFlag) {
		this.approvedFlag = approvedFlag;
	}

	public String getKhathFrom() {
		return khathFrom;
	}

	public void setKhathFrom(String khathFrom) {
		this.khathFrom = khathFrom;
	}

	public String getKhathTo() {
		return khathTo;
	}

	public void setKhathTo(String khathTo) {
		this.khathTo = khathTo;
	}

	
	
}
