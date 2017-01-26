package com.ibcs.desco.localStore.model;

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
@Table(name = "LS_ITEM_TNX_DTL")
public class LSItemTransactionDtl {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ls_item_tran_dtl_seq")
	@SequenceGenerator(name = "ls_item_tran_dtl_seq", sequenceName = "ls_item_tran_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "issued_by")
	private String issuedBy;

	@Column(name = "issued_for")
	private String issuedFor;

	@Column(name = "issued_to")
	private String issuedTo;

	@Column(name = "received_by")
	private String receivedBy;

	@Column(name = "received_from")
	private String receivedFrom;

	@Column(name = "return_by")
	private String retrunBy;

	@Column(name = "return_for")
	private String returnFor;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "ledger_type")
	private String ledgerType;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "tnxn_mode")
	private boolean tnxnMode;

	@Transient
	private Double balance = 0.0;

	@ManyToOne
	@JoinColumn(name = "ls_itm_trn_mst_id", nullable = false)
	private LSItemTransactionMst lsItemTransactionMst;

	@Transient
	private Integer lsItemTransactionMstid;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssuedFor() {
		return issuedFor;
	}

	public void setIssuedFor(String issuedFor) {
		this.issuedFor = issuedFor;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getRetrunBy() {
		return retrunBy;
	}

	public void setRetrunBy(String retrunBy) {
		this.retrunBy = retrunBy;
	}

	public String getReturnFor() {
		return returnFor;
	}

	public void setReturnFor(String returnFor) {
		this.returnFor = returnFor;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public LSItemTransactionMst getLsItemTransactionMst() {
		return lsItemTransactionMst;
	}

	public void setLsItemTransactionMst(
			LSItemTransactionMst lsItemTransactionMst) {
		this.lsItemTransactionMst = lsItemTransactionMst;
	}

	public Integer getLsItemTransactionMstid() {
		return lsItemTransactionMstid;
	}

	public void setLsItemTransactionMstid(Integer lsItemTransactionMstid) {
		this.lsItemTransactionMstid = lsItemTransactionMstid;
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

	public boolean isTnxnMode() {
		return tnxnMode;
	}

	public void setTnxnMode(boolean tnxnMode) {
		this.tnxnMode = tnxnMode;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
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
