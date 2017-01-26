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
@Table(name = "DISPOSAL_ASSET")
public class DisposalAssets {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISPOSAL_ASST_ID_SEQ")
	@SequenceGenerator(name = "DISPOSAL_ASST_ID_SEQ", sequenceName = "DISPOSAL_ASST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "ASSET_CODE")
	private String assetCode;

	@Column(name = "DISPOSAL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date disposalDate;

	@Column(name = "DISPOSAL_VALUE")
	private Double disposalValue;

	@Column(name = "REASON_OF_DISPOSAL")
	private String reasonOfDisposal;

	@Column(name = "SALE_VALUE")
	private Double saleValue;

	@Column(name = "GAIN_OR_LOSS")
	private Double gainOrLoss;

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

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public Date getDisposalDate() {
		return disposalDate;
	}

	public void setDisposalDate(Date disposalDate) {
		this.disposalDate = disposalDate;
	}

	public String getReasonOfDisposal() {
		return reasonOfDisposal;
	}

	public void setReasonOfDisposal(String reasonOfDisposal) {
		this.reasonOfDisposal = reasonOfDisposal;
	}

	public Double getDisposalValue() {
		return disposalValue;
	}

	public void setDisposalValue(Double disposalValue) {
		this.disposalValue = disposalValue;
	}

	public Double getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(Double saleValue) {
		this.saleValue = saleValue;
	}

	public Double getGainOrLoss() {
		return gainOrLoss;
	}

	public void setGainOrLoss(Double gainOrLoss) {
		this.gainOrLoss = gainOrLoss;
	}

}
