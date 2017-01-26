package com.ibcs.desco.cs.model;

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
@Table(name = "CS_ITEM_RECEIVED_DTL")
public class CSProcItemRcvDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_itm_rcv_dtl_seq")
	@SequenceGenerator(name = "cs_itm_rcv_dtl_seq", sequenceName = "cs_itm_rcv_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "received_report_mst_id", nullable = false)
	CSProcItemRcvMst csProcItemRcvMst;

	@Transient
	private String receivedReportNo;

	@Column(name = "received_report_No")
	private String rrNo;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "description")
	private String description;

	@Column(name = "uom")
	private String uom;

	@Column(name = "expected_qty")
	private double expectedQty;

	@Column(name = "received_qty")
	private double receivedQty;

	@Column(name = "remaining_qty")
	private double remainingQty;

	@Column(name = "unit_cost")
	private double cost;

	@Column(name = "ledger_book_no")
	private String ledgerBookNo;

	@Column(name = "ledger_page_no")
	private String ledgerPageNo;

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

	public String getRrNo() {
		return rrNo;
	}

	public void setRrNo(String rrNo) {
		this.rrNo = rrNo;
	}

	public CSProcItemRcvMst getCsProcItemRcvMst() {
		return csProcItemRcvMst;
	}

	public void setCsProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		this.csProcItemRcvMst = csProcItemRcvMst;
	}

	public String getReceivedReportNo() {
		return receivedReportNo;
	}

	public void setReceivedReportNo(String receivedReportNo) {
		this.receivedReportNo = receivedReportNo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public double getExpectedQty() {
		return expectedQty;
	}

	public void setExpectedQty(double expectedQty) {
		this.expectedQty = expectedQty;
	}

	public double getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(double receivedQty) {
		this.receivedQty = receivedQty;
	}

	public double getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(double remainingQty) {
		this.remainingQty = remainingQty;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getLedgerBookNo() {
		return ledgerBookNo;
	}

	public void setLedgerBookNo(String ledgerBookNo) {
		this.ledgerBookNo = ledgerBookNo;
	}

	public String getLedgerPageNo() {
		return ledgerPageNo;
	}

	public void setLedgerPageNo(String ledgerPageNo) {
		this.ledgerPageNo = ledgerPageNo;
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

}
