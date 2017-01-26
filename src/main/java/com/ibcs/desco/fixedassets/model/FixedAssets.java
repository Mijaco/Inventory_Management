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
@Table(name = "fixed_asset")
public class FixedAssets {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fixed_asset_id_seq")
	@SequenceGenerator(name = "fixed_asset_id_seq", sequenceName = "fixed_asset_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	private Integer itemMasterId;
	
	@Column(name = "FIXED_ASSET_ID")
	private String fixedAssetId;

	@Column(name = "FIXED_ASSET_NAME")
	private String fixedAssetName;

	@Column(name = "VERSION")
	private Integer version = 1;

	@Column(name = "LOCATION_ID")
	private String locationId;

	@Column(name = "ZONE")
	private String zone;

	@Column(name = "REG_NO")
	private String regNo;

	@Column(name = "ITEM_ID")
	private String itemId;
	
	@Column(name = "ASSET_TYPE")
	private String assetType;
	
	@Column(name = "MODEL_NUMBER")
	private String modelNumber;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name = "BRAND_NAME")
	private String brandName;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "VENDOR_NAME")
	private String vandorName;
	
	@Column(name = "FA_REG_KEY")
	private String faRegKey;

	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;

	@Column(name = "COMPLETE_DATE")
	private Date completeDate;

	@Column(name = "PURCHASE_PRICE")
	private Double purchasePrice;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "INSTALLATION_CHARGE")
	private Double installationCharge;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;	
	
	@Column(name = "NON_CODED")
	private boolean nonCoded=false;	

	@Column(name = "WRITE_OFF")
	private boolean writeOff=false;

	@Column(name = "LIFE_TIME")
	private String lifeTime;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "SUB_CATEGORY")
	private String subCategory;

	@Column(name = "RATING")
	private String rating;
	
	@Column(name = "DEPRECIATION_RATE")
	private Double depreciationRate;
	
	//vehicle
	
	@Column(name = "TYPE_OF_VEHICLE")
	private String typeOfVehicle;

	@Column(name = "CC")
	private String cc;

	@Column(name = "SEATS")
	private String seats;	
	
	@Column(name = "ENGINE_NO")
	private String engineNo;

	@Column(name = "CHACIS_NO")
	private String chacisNo;
	
	//furniture
	


	@Column(name = "TARIFF_CATEGORY")
	private String tariffCategory;
	
	@Column(name = "METER_TYPE")
	private String meterType;	
	
	//land

	@Column(name = "DIVISION_NAME")
	private String divisionName;
	
	@Column(name = "KHATIAN_NO")
	private String khatianNo;
	
	/*@Column(name = "MEASUREMENT")
	private String measurement;
	
	@Column(name = "TOTAL_ASSET_PRICE")
	private Double totalAssetPrice;*/
	
	//--------

	//Distribution Line
	
	@Column(name = "OPENING_LENGTH")
	private Double openingLength;
	
	@Column(name = "ADDITION_LENGTH")
	private Double additionLength;
	
	@Column(name = "TOTAL_LENGTH")
	private Double totalLength;

	//---------------
	
	@Column(name = "DEPRECIATED")
	private boolean depreciated=false;

	@Column(name = "WRITE_OFF_VALUE")
	private Double writeOffValue;

	@Column(name = "REASON_WRITE_OFF")
	private String reasonWriteOff;	
	
	@Column(name = "ACTIVE")
	private boolean active=true;

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

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDepreciated() {
		return depreciated;
	}

	public void setDepreciated(boolean depreciated) {
		this.depreciated = depreciated;
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

	public Integer getItemMasterId() {
		return itemMasterId;
	}
	public void setItemMasterId(Integer itemMasterId) {
		this.itemMasterId = itemMasterId;
	}
	
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
