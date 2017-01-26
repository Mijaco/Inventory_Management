package com.ibcs.desco.contractor.model;

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
@Table(name = "COST_ESTIMATE_MATERIALS_DTL")
public class CostEstimateMaterialsDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cost_Estimate_Mat_dtl_id_seq")
	@SequenceGenerator(name = "Cost_Estimate_Mat_dtl_id_seq", sequenceName = "Cost_Estimate_Mat_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "UNIT_PRICE")
	private Double unitPrice;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;

	@Column(name = "IS_ACTIVE")
	private boolean active = true;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Transient
	private int costEstimationMstId;
	
	@ManyToOne
	@JoinColumn(name = "Master_Id")
	private CostEstimationMst costEstimationMst;
	
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}


	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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

	public int getCostEstimationMstId() {
		return costEstimationMstId;
	}

	public void setCostEstimationMstId(int costEstimationMstId) {
		this.costEstimationMstId = costEstimationMstId;
	}

	public CostEstimationMst getCostEstimationMst() {
		return costEstimationMst;
	}

	public void setCostEstimationMst(CostEstimationMst costEstimationMst) {
		this.costEstimationMst = costEstimationMst;
	}

}
