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
@Table(name = "LS_STORE_TICKET_DTL")
public class LSStoreTicketDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ls_st_dtl_id_seq")
	@SequenceGenerator(name = "ls_st_dtl_id_seq", sequenceName = "ls_st_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "ticket_no", nullable = true)
	private String ticketNo;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "ledger_name")
	private String ledgerName;

	@ManyToOne
	@JoinColumn(name = "store_ticket_mst_id", nullable = false)
	private LSStoreTicketMst lsStoreTicketMst;

	@Transient
	private Integer storeTicketId;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "description")
	private String description;

	@Column(name = "uom")
	private String uom;

	@Column(name = "cost")
	private double cost;

	@Column(name = "ledger_book_no")
	private String ledgerBookNo;

	@Column(name = "ledger_page_no")
	private String ledgerPageNo;

	@Column(name = "quantity")
	private double quantity;

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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public LSStoreTicketMst getLsStoreTicketMst() {
		return lsStoreTicketMst;
	}

	public void setLsStoreTicketMst(LSStoreTicketMst lsStoreTicketMst) {
		this.lsStoreTicketMst = lsStoreTicketMst;
	}

	public Integer getStoreTicketId() {
		return storeTicketId;
	}

	public void setStoreTicketId(Integer storeTicketId) {
		this.storeTicketId = storeTicketId;
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

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
