package com.ibcs.desco.fixedassets.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DEPRECIATION_SUMMERY")
public class DepreciationSummery {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPR_SUMMERY_ID_SEQ")
	@SequenceGenerator(name = "DEPR_SUMMERY_ID_SEQ", sequenceName = "DEPR_SUMMERY_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "ASSET_ID")
	private String assetId;

	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;

	@Column(name = "PURCHASE_PRICE")
	private Double purchasePrice;

	@Column(name = "NO_OF_YEAR")
	private String noOfYear;

	@Column(name = "DEP_START_DATE")
	private Date depStartDate;

	@Column(name = "DEP_END_DATE")
	private Date depEndDate;
	
	@Column(name = "SESSION_YEAR")
	private String sessionYear;

	@Column(name = "DEP_UPTO_LAST_YEAR")
	private Double depUptoLastYear;

	@Column(name = "DEP_CURRENT_YEAR")
	private Double depCurrentYear;

	@Column(name = "CU_DEPRECIATION")
	private Double cuDepreciation;

	@Column(name = "ADJUSTMENT")
	private Double adjustment;

	@Column(name = "WRITTEN_DOWN_VALUE")
	private Double writtenDownValue;
	
	@Column(name = "LIFE_TIME")
	private String lifeTime;
	
	@Column(name = "DEPRECIATION_RATE")
	private Double depreciationRate;
	
	@Column(name = "FA_REG_KEY")
	private String faRegKey;

	@Column(name = "ACTIVE")
	private boolean active;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATE_BY")
	private String createdBy;

	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getNoOfYear() {
		return noOfYear;
	}

	public void setNoOfYear(String noOfYear) {
		this.noOfYear = noOfYear;
	}
	public Date getDepStartDate() {
		return depStartDate;
	}

	public void setDepStartDate(Date depStartDate) {
		this.depStartDate = depStartDate;
	}

	public Date getDepEndDate() {
		return depEndDate;
	}

	public void setDepEndDate(Date depEndDate) {
		this.depEndDate = depEndDate;
	}

	public String getSessionYear() {
		return sessionYear;
	}

	public void setSessionYear(String sessionYear) {
		this.sessionYear = sessionYear;
	}

	public Double getDepUptoLastYear() {
		return depUptoLastYear;
	}

	public void setDepUptoLastYear(Double depUptoLastYear) {
		this.depUptoLastYear = depUptoLastYear;
	}

	public Double getDepCurrentYear() {
		return depCurrentYear;
	}

	public void setDepCurrentYear(Double depCurrentYear) {
		this.depCurrentYear = depCurrentYear;
	}

	public Double getCuDepreciation() {
		return cuDepreciation;
	}

	public void setCuDepreciation(Double cuDepreciation) {
		this.cuDepreciation = cuDepreciation;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public Double getWrittenDownValue() {
		return writtenDownValue;
	}

	public void setWrittenDownValue(Double writtenDownValue) {
		this.writtenDownValue = writtenDownValue;
	}

	public String getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Double getDepreciationRate() {
		return depreciationRate;
	}

	public void setDepreciationRate(Double depreciationRate) {
		this.depreciationRate = depreciationRate;
	}

	public String getFaRegKey() {
		return faRegKey;
	}

	public void setFaRegKey(String faRegKey) {
		this.faRegKey = faRegKey;
	}

}
