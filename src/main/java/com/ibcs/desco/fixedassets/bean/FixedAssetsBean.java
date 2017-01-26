package com.ibcs.desco.fixedassets.bean;

import java.util.Date;

import javax.persistence.Column;


public class FixedAssetsBean {

	private Integer id;

	private String fixedAssetId;

	private String fixedAssetName;

	private Integer version = 1;

	private String locationId;
	
	private String zone;

	private String itemId;
	
	private String assetType;
	
	private String modelNumber;
	
	private String faRegKey;
	
	private String serialNumber;
	
	private String brandName;
	
	private String color;
	
	private String vandorName;

	private String purchaseDate;
	
	private String completeDate;

	private Double purchasePrice;
	
	private Double quantity;

	private Double installationCharge;

	private Double totalPrice;	
	
	private String lifeTime;
	
	private Double depreciationRate;
	
	private boolean nonCoded=false;	
	
	private boolean writeOff=false;	
	
	private String projectName;
	
	private String disLineSubCategory;
	
	private String tarrifType;
	
	private String disEqpSubCategory;
	
	private String disLineDivision;
	
	private String disEqpDivision;
	
	private String landDivision;
	
	private String buildingDivision;
	
	private String rating;
	
	private String tariffCategory;
	
	private String meterType;	
	
	//vehicle
	
	
	private String typeOfVehicle;

	private String cc;

	private String seats;	
	
	private String engineNo;

	private String chacisNo;
	
	//furniture
	
	
	
	//land

	//private String divisionName;
	
	private String khatianNo;
	
	/*private String measurement;
	
	private Double totalAssetPrice;*/
	
	//--------

	//Distribution Line
	
		private Double openingLength;
		
		private Double additionLength;
		
		private Double totalLength;

		//---------------
	private String opt;
	
	private boolean active;

	private String remarks;

	private String createdBy;

	private Date createDate;

	private String modifiedBy;

	private Date modifyDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFixedAssetId() {
		return fixedAssetId;
	}

	public void setFixedAssetId(String fixedAssetId) {
		this.fixedAssetId = fixedAssetId;
	}

	public String getFixedAssetName() {
		return fixedAssetName;
	}

	public void setFixedAssetName(String fixedAssetName) {
		this.fixedAssetName = fixedAssetName;
	}

	public String getDisLineDivision() {
		return disLineDivision;
	}

	public void setDisLineDivision(String disLineDivision) {
		this.disLineDivision = disLineDivision;
	}

	public String getDisEqpDivision() {
		return disEqpDivision;
	}

	public void setDisEqpDivision(String disEqpDivision) {
		this.disEqpDivision = disEqpDivision;
	}

	public String getLandDivision() {
		return landDivision;
	}

	public void setLandDivision(String landDivision) {
		this.landDivision = landDivision;
	}

	public String getBuildingDivision() {
		return buildingDivision;
	}

	public void setBuildingDivision(String buildingDivision) {
		this.buildingDivision = buildingDivision;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isNonCoded() {
		return nonCoded;
	}

	public void setNonCoded(boolean nonCoded) {
		this.nonCoded = nonCoded;
	}

	public boolean isWriteOff() {
		return writeOff;
	}

	public void setWriteOff(boolean writeOff) {
		this.writeOff = writeOff;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getKhatianNo() {
		return khatianNo;
	}

	public void setKhatianNo(String khatianNo) {
		this.khatianNo = khatianNo;
	}

	public String getVandorName() {
		return vandorName;
	}

	public void setVandorName(String vandorName) {
		this.vandorName = vandorName;
	}

	public boolean getActive() {
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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getChacisNo() {
		return chacisNo;
	}

	public void setChacisNo(String chacisNo) {
		this.chacisNo = chacisNo;
	}

	/*public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}*/

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getInstallationCharge() {
		return installationCharge;
	}

	public void setInstallationCharge(Double installationCharge) {
		this.installationCharge = installationCharge;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	/*public Double getTotalAssetPrice() {
		return totalAssetPrice;
	}

	public void setTotalAssetPrice(Double totalAssetPrice) {
		this.totalAssetPrice = totalAssetPrice;
	}*/


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

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDisLineSubCategory() {
		return disLineSubCategory;
	}

	public void setDisLineSubCategory(String disLineSubCategory) {
		this.disLineSubCategory = disLineSubCategory;
	}

	public String getDisEqpSubCategory() {
		return disEqpSubCategory;
	}

	public void setDisEqpSubCategory(String disEqpSubCategory) {
		this.disEqpSubCategory = disEqpSubCategory;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFaRegKey() {
		return faRegKey;
	}

	public void setFaRegKey(String faRegKey) {
		this.faRegKey = faRegKey;
	}

	public Double getOpeningLength() {
		return openingLength;
	}

	public void setOpeningLength(Double openingLength) {
		this.openingLength = openingLength;
	}

	public Double getAdditionLength() {
		return additionLength;
	}

	public void setAdditionLength(Double additionLength) {
		this.additionLength = additionLength;
	}

	public Double getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Double totalLength) {
		this.totalLength = totalLength;
	}

	public String getTarrifType() {
		return tarrifType;
	}

	public void setTarrifType(String tarrifType) {
		this.tarrifType = tarrifType;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public String getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(String tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

}
