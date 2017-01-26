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

import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.inventory.model.ItemMaster;


@Entity
@Table(name = "auction_worK_order_dtl")
public class AuctionWOEntryDtl {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_worK_order_dtl_seq")
	@SequenceGenerator(name = "auction_worK_order_dtl_seq", sequenceName = "auction_worK_order_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "dept_mst_id", nullable = false)
	private Departments departments;

	@ManyToOne
	@JoinColumn(name = "item_master_id", nullable = false)
	private ItemMaster itemMaster;	
	
	@Column(name = "auction_qty")
	private Double auctionQty = 0.0;
	
	@Column(name = "remaining_qty")
	private Double remainingQty = 0.0;
	
	@Column(name = "delivered_qty")
	private Double deliveredQty = 0.0;
	
	@Column(name = "cost")
	private Double cost = 0.0;
	
	@ManyToOne
	@JoinColumn(name = "auction_work_order_mst", nullable = false)
	AuctionWOEntryMst auctionWOEntryMst;
	
	@Transient
	private String temp;
	
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

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public Double getAuctionQty() {
		return auctionQty;
	}

	public void setAuctionQty(Double auctionQty) {
		this.auctionQty = auctionQty;
	}

	public Double getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Double remainingQty) {
		this.remainingQty = remainingQty;
	}

	public Double getDeliveredQty() {
		return deliveredQty;
	}

	public void setDeliveredQty(Double deliveredQty) {
		this.deliveredQty = deliveredQty;
	}


	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public AuctionWOEntryMst getAuctionWOEntryMst() {
		return auctionWOEntryMst;
	}

	public void setAuctionWOEntryMst(AuctionWOEntryMst auctionWOEntryMst) {
		this.auctionWOEntryMst = auctionWOEntryMst;
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

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
		
}
