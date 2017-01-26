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
@Table(name = "condemn_mst")
public class CondemnMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "condemn_mst_seq")
	@SequenceGenerator(name = "condemn_mst_seq", sequenceName = "condemn_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "auction_process_mst", nullable = false)
	private AuctionProcessMst auctionProcessMst;
	
	@ManyToOne
	@JoinColumn(name = "auction_category_id")
	private AuctionCategory auctionCategory;
	
	@Column(name = "cc_to_mps_flag")
	private String cc_to_mps_flag = "0";
	
	@Column(name = "cc_to_mps_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cc_to_mps_date;

	@Column(name = "mps_to_auction_flag")
	private String mps_to_auction_flag = "0";

	@Column(name = "mps_to_auction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mps_to_auction_date;

	@Transient
	private String dateText;
	
	@Column(name = "work_order_flag")
	private String work_order_flag = "0";

	@Column(name = "status")
	private String status;

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

	public AuctionProcessMst getAuctionProcessMst() {
		return auctionProcessMst;
	}

	public void setAuctionProcessMst(AuctionProcessMst auctionProcessMst) {
		this.auctionProcessMst = auctionProcessMst;
	}

	public AuctionCategory getAuctionCategory() {
		return auctionCategory;
	}

	public void setAuctionCategory(AuctionCategory auctionCategory) {
		this.auctionCategory = auctionCategory;
	}

	public Date getCc_to_mps_date() {
		return cc_to_mps_date;
	}

	public void setCc_to_mps_date(Date cc_to_mps_date) {
		this.cc_to_mps_date = cc_to_mps_date;
	}

	public String getMps_to_auction_flag() {
		return mps_to_auction_flag;
	}

	public void setMps_to_auction_flag(String mps_to_auction_flag) {
		this.mps_to_auction_flag = mps_to_auction_flag;
	}

	public Date getMps_to_auction_date() {
		return mps_to_auction_date;
	}

	public void setMps_to_auction_date(Date mps_to_auction_date) {
		this.mps_to_auction_date = mps_to_auction_date;
	}

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCc_to_mps_flag() {
		return cc_to_mps_flag;
	}

	public void setCc_to_mps_flag(String cc_to_mps_flag) {
		this.cc_to_mps_flag = cc_to_mps_flag;
	}

	public String getWork_order_flag() {
		return work_order_flag;
	}

	public void setWork_order_flag(String work_order_flag) {
		this.work_order_flag = work_order_flag;
	}
	
	
	
}
