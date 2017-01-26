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

import com.ibcs.desco.admin.model.DescoSession;

@Entity
@Table(name = "DEMAND_NOTE_SUMMARY")
public class DemandNoteSummary {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMAND_NOTE_SUMRY_ID_SEQ")
	@SequenceGenerator(name = "DEMAND_NOTE_SUMRY_ID_SEQ", sequenceName = "DEMAND_NOTE_SUMRY_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "annexure_type")
	private String annexureType;

	@ManyToOne
	@JoinColumn(name = "desco_session")
	private DescoSession descoSession;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UNIT")
	private String unit;

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

	@Column(name = "isApproved")
	private boolean approved = false;

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

	public DemandNoteSummary() {
	}

	public DemandNoteSummary(Integer id, String annexureType,
			DescoSession descoSession, String itemCode, String itemName,
			String unit, Double requiredQunatity, Double estimateUnitCost,
			Double estimateTotalCost, Double previousYearConsumption,
			Double existingQty, boolean approved, boolean active,
			String remarks, String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.annexureType = annexureType;
		this.descoSession = descoSession;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.unit = unit;
		this.requiredQunatity = requiredQunatity;
		this.estimateUnitCost = estimateUnitCost;
		this.estimateTotalCost = estimateTotalCost;
		this.previousYearConsumption = previousYearConsumption;
		this.existingQty = existingQty;
		this.approved = approved;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnnexureType() {
		return annexureType;
	}

	public void setAnnexureType(String annexureType) {
		this.annexureType = annexureType;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	@Override
	public String toString() {
		return "DemandNoteSummary [id=" + id + ", annexureType=" + annexureType
				+ ", descoSession=" + descoSession + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", unit=" + unit
				+ ", requiredQunatity=" + requiredQunatity
				+ ", estimateUnitCost=" + estimateUnitCost
				+ ", estimateTotalCost=" + estimateTotalCost
				+ ", previousYearConsumption=" + previousYearConsumption
				+ ", existingQty=" + existingQty + ", approved=" + approved
				+ ", active=" + active + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
