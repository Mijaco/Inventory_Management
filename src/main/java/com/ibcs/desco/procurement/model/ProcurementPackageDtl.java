package com.ibcs.desco.procurement.model;

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
@Table(name = "PROC_PACK_DTL")
public class ProcurementPackageDtl {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROC_PACK_DTL_ID_SEQ")
	@SequenceGenerator(name = "PROC_PACK_DTL_ID_SEQ", sequenceName = "PROC_PACK_DTL_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UNIT")
	private String unit;

	@Column(name = "QUANTITY")
	private Double qunatity = 0.0;

	@Column(name = "UNIT_COST")
	private Double unitCost= 0.0;

	@Column(name = "TOTAL_COST")
	private Double totalCost= 0.0;

	@Transient
	private int procurementPackageMstId;

	@ManyToOne
	@JoinColumn(name = "PROC_PACK_MST_ID")
	private ProcurementPackageMst procurementPackageMst;

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

	public Double getQunatity() {
		return qunatity;
	}

	public void setQunatity(Double qunatity) {
		this.qunatity = qunatity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public int getProcurementPackageMstId() {
		return procurementPackageMstId;
	}

	public void setProcurementPackageMstId(int procurementPackageMstId) {
		this.procurementPackageMstId = procurementPackageMstId;
	}

	public ProcurementPackageMst getProcurementPackageMst() {
		return procurementPackageMst;
	}

	public void setProcurementPackageMst(
			ProcurementPackageMst procurementPackageMst) {
		this.procurementPackageMst = procurementPackageMst;
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

	public ProcurementPackageDtl(Integer id, String itemCode, String itemName,
			String unit, Double qunatity, Double unitCost, Double totalCost,
			ProcurementPackageMst procurementPackageMst, String remarks,
			String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.unit = unit;
		this.qunatity = qunatity;
		this.unitCost = unitCost;
		this.totalCost = totalCost;
		this.procurementPackageMst = procurementPackageMst;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public ProcurementPackageDtl() {
	}

	@Override
	public String toString() {
		return "ProcurementPackageDtl [id=" + id + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", unit=" + unit + ", qunatity="
				+ qunatity + ", unitCost=" + unitCost + ", totalCost="
				+ totalCost + ", procurementPackageMstId="
				+ procurementPackageMstId + ", procurementPackageMst="
				+ procurementPackageMst + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
