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

import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "auction_dtl")
public class AuctionDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_dtl_seq")
	@SequenceGenerator(name = "auction_dtl_seq", sequenceName = "auction_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "itemMaster_id", nullable = false)
	private ItemMaster itemMaster;

	@ManyToOne
	@JoinColumn(name = "auctionMst_id", nullable = false)
	private AuctionMst auctionMst;

	@ManyToOne
	@JoinColumn(name = "descoKhath_mst_id", nullable = false)
	private DescoKhath descoKhath;

	@Column(name = "ledger_qty")
	private Double ledgerQty;

	@Column(name = "store_final_qty")
	private Double storeFinalQty;

	@Column(name = "store_to_admin_flag")
	private String store_to_admin_flag = "0";

	@Column(name = "store_to_admin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date store_to_admin_date;

	@Column(name = "admin_final_qty")
	private Double adminFinalQty;

	@Column(name = "admin_to_cc_flag")
	private String admin_to_cc_flag = "0";

	@Column(name = "admin_to_cc_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admin_to_cc_date;

	@Column(name = "book_value")
	private Double bookValue;

	@Column(name = "estmiated_price")
	private Double estmiatedPrice;

	@Column(name = "cc_final_qty")
	private Double ccFinalQty;

	@Column(name = "cc_to_admin_flag")
	private String cc_to_admin_flag = "0";

	@Column(name = "cc_to_admin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cc_to_admin_date;

	@Column(name = "admin_to_ac_flag")
	private String admin_to_ac_flag = "0";

	@Column(name = "admin_to_ac_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admin_to_ac_date;

	@Column(name = "ac_final_Qty")
	private Double acFinalQty;

	@Column(name = "auction_final_qty")
	private Double auctionFinalQty;

	@Column(name = "isActive")
	private boolean active = true;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public AuctionMst getAuctionMst() {
		return auctionMst;
	}

	public void setAuctionMst(AuctionMst auctionMst) {
		this.auctionMst = auctionMst;
	}

	public DescoKhath getDescoKhath() {
		return descoKhath;
	}

	public void setDescoKhath(DescoKhath descoKhath) {
		this.descoKhath = descoKhath;
	}

	public Double getLedgerQty() {
		return ledgerQty;
	}

	public void setLedgerQty(Double ledgerQty) {
		this.ledgerQty = ledgerQty;
	}

	public Double getStoreFinalQty() {
		return storeFinalQty;
	}

	public void setStoreFinalQty(Double storeFinalQty) {
		this.storeFinalQty = storeFinalQty;
	}

	public String getStore_to_admin_flag() {
		return store_to_admin_flag;
	}

	public void setStore_to_admin_flag(String store_to_admin_flag) {
		this.store_to_admin_flag = store_to_admin_flag;
	}

	public Date getStore_to_admin_date() {
		return store_to_admin_date;
	}

	public void setStore_to_admin_date(Date store_to_admin_date) {
		this.store_to_admin_date = store_to_admin_date;
	}

	public Double getAdminFinalQty() {
		return adminFinalQty;
	}

	public void setAdminFinalQty(Double adminFinalQty) {
		this.adminFinalQty = adminFinalQty;
	}

	public String getAdmin_to_cc_flag() {
		return admin_to_cc_flag;
	}

	public void setAdmin_to_cc_flag(String admin_to_cc_flag) {
		this.admin_to_cc_flag = admin_to_cc_flag;
	}

	public Date getAdmin_to_cc_date() {
		return admin_to_cc_date;
	}

	public void setAdmin_to_cc_date(Date admin_to_cc_date) {
		this.admin_to_cc_date = admin_to_cc_date;
	}

	public Double getBookValue() {
		return bookValue;
	}

	public void setBookValue(Double bookValue) {
		this.bookValue = bookValue;
	}

	public Double getEstmiatedPrice() {
		return estmiatedPrice;
	}

	public void setEstmiatedPrice(Double estmiatedPrice) {
		this.estmiatedPrice = estmiatedPrice;
	}

	public Double getCcFinalQty() {
		return ccFinalQty;
	}

	public void setCcFinalQty(Double ccFinalQty) {
		this.ccFinalQty = ccFinalQty;
	}

	public String getCc_to_admin_flag() {
		return cc_to_admin_flag;
	}

	public void setCc_to_admin_flag(String cc_to_admin_flag) {
		this.cc_to_admin_flag = cc_to_admin_flag;
	}

	public Date getCc_to_admin_date() {
		return cc_to_admin_date;
	}

	public void setCc_to_admin_date(Date cc_to_admin_date) {
		this.cc_to_admin_date = cc_to_admin_date;
	}

	public String getAdmin_to_ac_flag() {
		return admin_to_ac_flag;
	}

	public void setAdmin_to_ac_flag(String admin_to_ac_flag) {
		this.admin_to_ac_flag = admin_to_ac_flag;
	}

	public Date getAdmin_to_ac_date() {
		return admin_to_ac_date;
	}

	public void setAdmin_to_ac_date(Date admin_to_ac_date) {
		this.admin_to_ac_date = admin_to_ac_date;
	}

	public Double getAcFinalQty() {
		return acFinalQty;
	}

	public void setAcFinalQty(Double acFinalQty) {
		this.acFinalQty = acFinalQty;
	}

	public Double getAuctionFinalQty() {
		return auctionFinalQty;
	}

	public void setAuctionFinalQty(Double auctionFinalQty) {
		this.auctionFinalQty = auctionFinalQty;
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
