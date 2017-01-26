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

import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "DEMAND_NOTE_DTL")
public class DemandNoteDtl {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMAND_NOTE_DTL_ID_SEQ")
	@SequenceGenerator(name = "DEMAND_NOTE_DTL_ID_SEQ", sequenceName = "DEMAND_NOTE_DTL_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@ManyToOne
	@JoinColumn(name = "item_master_id")
	private ItemMaster itemMaster;

	@Column(name = "REQUIRED_QTY")
	private Double requiredQunatity;

	@Column(name = "EST_UNIT_COST")
	private Double estimateUnitCost;

	@Column(name = "EST_TOTAL_COST")
	private Double estimateTotalCost;

	@Column(name = "PREV_YEAR_CONSUM")
	private Double previousYearConsumption;

	@Column(name = "EXISTING_QTY")
	private Double existingQty;

	@Transient
	private int demandNoteMstId;

	@ManyToOne
	@JoinColumn(name = "DEMAND_NOTE_MST_ID")
	private DemandNoteMst demandNoteMst;

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

	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public Double getRequiredQunatity() {
		return requiredQunatity;
	}

	public void setRequiredQunatity(Double requiredQunatity) {
		this.requiredQunatity = requiredQunatity;
	}

	public Double getEstimateUnitCost() {
		return estimateUnitCost;
	}

	public void setEstimateUnitCost(Double estimateUnitCost) {
		this.estimateUnitCost = estimateUnitCost;
	}

	public Double getEstimateTotalCost() {
		return estimateTotalCost;
	}

	public void setEstimateTotalCost(Double estimateTotalCost) {
		this.estimateTotalCost = estimateTotalCost;
	}

	public Double getPreviousYearConsumption() {
		return previousYearConsumption;
	}

	public void setPreviousYearConsumption(Double previousYearConsumption) {
		this.previousYearConsumption = previousYearConsumption;
	}

	public Double getExistingQty() {
		return existingQty;
	}

	public void setExistingQty(Double existingQty) {
		this.existingQty = existingQty;
	}

	public int getDemandNoteMstId() {
		return demandNoteMstId;
	}

	public void setDemandNoteMstId(int demandNoteMstId) {
		this.demandNoteMstId = demandNoteMstId;
	}

	public DemandNoteMst getDemandNoteMst() {
		return demandNoteMst;
	}

	public void setDemandNoteMst(DemandNoteMst demandNoteMst) {
		this.demandNoteMst = demandNoteMst;
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

	public DemandNoteDtl() {

	}

	public DemandNoteDtl(Integer id, ItemMaster itemMaster,
			Double requiredQunatity, Double estimateUnitCost,
			Double estimateTotalCost, Double previousYearConsumption,
			Double existingQty, DemandNoteMst demandNoteMst, boolean active,
			String remarks, String createdBy, Date createdDate) {
		super();

		this.id = id;
		this.itemMaster = itemMaster;
		this.requiredQunatity = requiredQunatity;
		this.estimateUnitCost = estimateUnitCost;
		this.estimateTotalCost = estimateTotalCost;
		this.previousYearConsumption = previousYearConsumption;
		this.existingQty = existingQty;
		this.demandNoteMst = demandNoteMst;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "DemandNoteDtl [id=" + id + ", itemMaster=" + itemMaster
				+ ", requiredQunatity=" + requiredQunatity
				+ ", estimateUnitCost=" + estimateUnitCost
				+ ", estimateTotalCost=" + estimateTotalCost
				+ ", previousYearConsumption=" + previousYearConsumption
				+ ", existingQty=" + existingQty + ", demandNoteMstId="
				+ demandNoteMstId + ", demandNoteMst=" + demandNoteMst
				+ ", active=" + active + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
	

}
