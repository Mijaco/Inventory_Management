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
import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "condemn_dtl")
public class CondemnDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "condemn_dtl_seq")
	@SequenceGenerator(name = "condemn_dtl_seq", sequenceName = "condemn_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "dept_mst_id", nullable = false)
	private Departments departments;

	@ManyToOne
	@JoinColumn(name = "item_master_id", nullable = false)
	private ItemMaster itemMaster;

	@ManyToOne
	@JoinColumn(name = "condemn_mst_id", nullable = false)
	private CondemnMst condemnMst;

	@Column(name = "qty")
	private Double qty;

	@Column(name = "condemn_qty")
	private Double condemnQty;

	@Column(name = "short_qty")
	private Double shortQty;

	@Column(name = "book_value")
	private Double bookValue;

	@Column(name = "estmiated_price")
	private Double estmiatedPrice;

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

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public CondemnMst getCondemnMst() {
		return condemnMst;
	}

	public void setCondemnMst(CondemnMst condemnMst) {
		this.condemnMst = condemnMst;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
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

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public Double getCondemnQty() {
		return condemnQty;
	}

	public void setCondemnQty(Double condemnQty) {
		this.condemnQty = condemnQty;
	}

	public Double getShortQty() {
		return shortQty;
	}

	public void setShortQty(Double shortQty) {
		this.shortQty = shortQty;
	}
	
	

}
