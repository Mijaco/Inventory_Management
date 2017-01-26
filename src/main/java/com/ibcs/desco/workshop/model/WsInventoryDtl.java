package com.ibcs.desco.workshop.model;

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
@Table(name = "WS_INVENTORY_DTL")
public class WsInventoryDtl {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WS_INV_DTL_ID_SEQ")
	@SequenceGenerator(name = "WS_INV_DTL_ID_SEQ", sequenceName = "WS_INV_DTL_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Transient
	private int wsInventoryMstId;

	@ManyToOne
	@JoinColumn(name = "ws_inventory_mst_id")
	private WsInventoryMst wsInventoryMst;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_name")
	private String itemName;

	@Transient
	private String wsInventoryNo;

	@Column(name = "unit")
	private String unit;

	@Column(name = "standard_quantity")
	private Double standardQuantity = 0.0;

	@Column(name = "useable_quantity")
	private double useableQuantity = 0.0;

	@Column(name = "un_useable_quantity")
	private double unUseableQuantity = 0.0;

	@Column(name = "not_found")
	private double notFound = 0.0;

	@Column(name = "total_found")
	private double totalFound = 0.0;

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public int getWsInventoryMstId() {
		return wsInventoryMstId;
	}

	public void setWsInventoryMstId(int wsInventoryMstId) {
		this.wsInventoryMstId = wsInventoryMstId;
	}

	public WsInventoryMst getWsInventoryMst() {
		return wsInventoryMst;
	}

	public void setWsInventoryMst(WsInventoryMst wsInventoryMst) {
		this.wsInventoryMst = wsInventoryMst;
	}

	public String getWsInventoryNo() {
		return wsInventoryNo;
	}

	public void setWsInventoryNo(String wsInventoryNo) {
		this.wsInventoryNo = wsInventoryNo;
	}

	public Double getStandardQuantity() {
		return standardQuantity;
	}

	public void setStandardQuantity(Double standardQuantity) {
		this.standardQuantity = standardQuantity;
	}

	public double getUseableQuantity() {
		return useableQuantity;
	}

	public void setUseableQuantity(double useableQuantity) {
		this.useableQuantity = useableQuantity;
	}

	public double getUnUseableQuantity() {
		return unUseableQuantity;
	}

	public void setUnUseableQuantity(double unUseableQuantity) {
		this.unUseableQuantity = unUseableQuantity;
	}

	public double getNotFound() {
		return notFound;
	}

	public void setNotFound(double notFound) {
		this.notFound = notFound;
	}

	public double getTotalFound() {
		return totalFound;
	}

	public void setTotalFound(double totalFound) {
		this.totalFound = totalFound;
	}
	
}
