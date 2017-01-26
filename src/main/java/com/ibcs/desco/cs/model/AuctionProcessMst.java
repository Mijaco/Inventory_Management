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
@Table(name = "auction_process_mst")
public class AuctionProcessMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_process_mst_seq")
	@SequenceGenerator(name = "auction_process_mst_seq", sequenceName = "auction_process_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "auction_name")
	private String auctionName;

	@Column(name = "from_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate;

	@Column(name = "to_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date toDate;

	@Column(name = "project_id")
	private String projectId;

	@ManyToOne
	@JoinColumn(name = "auction_category_id", nullable = false)
	private AuctionCategory auctionCategory;
	
	@Column(name = "cs_to_admin_flag")
	private String cs_to_admin_flag = "0";	

	@Column(name = "cs_to_admin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cs_to_admin_date;
	
	@Column(name = "admin_to_cc_flag")
	private String admin_to_cc_flag = "0";
	
	@Column(name = "admin_to_cc_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admin_to_cc_date;
	
	@Column(name = "cc_to_admin_flag")
	private String cc_to_admin_flag = "0";

	@Column(name = "cc_to_admin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cc_to_admin_date;

	@Column(name = "admin_to_auction_flag")
	private String admin_to_auction_flag = "0";

	@Column(name = "admin_to_auction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date admin_to_auction_date;
	
	@Column(name = "auction_wo_flag")
	private String auction_wo_flag = "0";

	@Transient
	private String dateText;
	
	@Transient
	private String category_group_Id;

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

	public String getAuctionName() {
		return auctionName;
	}

	public void setAuctionName(String auctionName) {
		this.auctionName = auctionName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public AuctionCategory getAuctionCategory() {
		return auctionCategory;
	}

	public void setAuctionCategory(AuctionCategory auctionCategory) {
		this.auctionCategory = auctionCategory;
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

	public String getAdmin_to_auction_flag() {
		return admin_to_auction_flag;
	}

	public void setAdmin_to_auction_flag(String admin_to_auction_flag) {
		this.admin_to_auction_flag = admin_to_auction_flag;
	}

	public Date getAdmin_to_auction_date() {
		return admin_to_auction_date;
	}

	public void setAdmin_to_auction_date(Date admin_to_auction_date) {
		this.admin_to_auction_date = admin_to_auction_date;
	}

	public String getCs_to_admin_flag() {
		return cs_to_admin_flag;
	}

	public void setCs_to_admin_flag(String cs_to_admin_flag) {
		this.cs_to_admin_flag = cs_to_admin_flag;
	}

	public Date getCs_to_admin_date() {
		return cs_to_admin_date;
	}

	public void setCs_to_admin_date(Date cs_to_admin_date) {
		this.cs_to_admin_date = cs_to_admin_date;
	}

	public String getCategory_group_Id() {
		return category_group_Id;
	}

	public void setCategory_group_Id(String category_group_Id) {
		this.category_group_Id = category_group_Id;
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

	public String getAuction_wo_flag() {
		return auction_wo_flag;
	}

	public void setAuction_wo_flag(String auction_wo_flag) {
		this.auction_wo_flag = auction_wo_flag;
	}
	
	

}
