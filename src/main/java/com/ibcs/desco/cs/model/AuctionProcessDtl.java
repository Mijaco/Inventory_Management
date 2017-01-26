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

import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "auction_process_dtl")
public class AuctionProcessDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_process_dtl_seq")
	@SequenceGenerator(name = "auction_process_dtl_seq", sequenceName = "auction_process_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "auction_process_mst_id", nullable = false)
	private AuctionProcessMst auctionProcessMst;

	@ManyToOne
	@JoinColumn(name = "descoKhath_mst_id", nullable = false)
	private DescoKhath descoKhath;

	@ManyToOne
	@JoinColumn(name = "departments_mst_id", nullable = false)
	private Departments departments;

	@ManyToOne
	@JoinColumn(name = "store_Locations_mst_id", nullable = false)
	private StoreLocations storeLocations;

	@ManyToOne
	@JoinColumn(name = "itemMaster_id", nullable = false)
	private ItemMaster itemMaster;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "book_value")
	private Double bookValue;

	@Column(name = "estmiated_price")
	private Double estmiatedPrice;

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

	public DescoKhath getDescoKhath() {
		return descoKhath;
	}

	public void setDescoKhath(DescoKhath descoKhath) {
		this.descoKhath = descoKhath;
	}

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public StoreLocations getStoreLocations() {
		return storeLocations;
	}

	public void setStoreLocations(StoreLocations storeLocations) {
		this.storeLocations = storeLocations;
	}

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
