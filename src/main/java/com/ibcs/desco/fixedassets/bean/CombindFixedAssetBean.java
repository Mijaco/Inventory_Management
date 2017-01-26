package com.ibcs.desco.fixedassets.bean;

import java.util.Date;

public class CombindFixedAssetBean {

	private Integer id = 0;

	private String assetId;

	private String assetName;
	
	private String assetType;

	private Integer version = 1;

	private String toDept;

	private String receiveDate;

	private String locationId;

	private Double quantity;

	private Double totalPrice;	
	
	private String faRegKey;
	
	private Double writeOffValue;
	
	private String reasonWriteOff;
	
	private String writeOffNote;		

	private boolean active = true;

	private String flag;

	private Double disposalValue;

	private String reasonOfDisposal;

	private Double saleValue;

	private Double gainOrLoss;

	private String dispRemarks;

	private String remarks;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

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

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getToDept() {
		return toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public Double getWriteOffValue() {
		return writeOffValue;
	}

	public void setWriteOffValue(Double writeOffValue) {
		this.writeOffValue = writeOffValue;
	}

	public String getReasonWriteOff() {
		return reasonWriteOff;
	}

	public void setReasonWriteOff(String reasonWriteOff) {
		this.reasonWriteOff = reasonWriteOff;
	}

	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getDisposalValue() {
		return disposalValue;
	}

	public void setDisposalValue(Double disposalValue) {
		this.disposalValue = disposalValue;
	}

	public String getReasonOfDisposal() {
		return reasonOfDisposal;
	}

	public void setReasonOfDisposal(String reasonOfDisposal) {
		this.reasonOfDisposal = reasonOfDisposal;
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

	public String getDispRemarks() {
		return dispRemarks;
	}

	public void setDispRemarks(String dispRemarks) {
		this.dispRemarks = dispRemarks;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getFaRegKey() {
		return faRegKey;
	}

	public void setFaRegKey(String faRegKey) {
		this.faRegKey = faRegKey;
	}
	
}
