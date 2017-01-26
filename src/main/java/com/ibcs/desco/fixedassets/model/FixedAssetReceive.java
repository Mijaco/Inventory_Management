package com.ibcs.desco.fixedassets.model;

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

import com.ibcs.desco.inventory.model.ItemMaster;

@Entity
@Table(name = "FIXED_ASSET_RCV")
public class FixedAssetReceive {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIXED_ASSET_RCV_ID_SEQ")
	@SequenceGenerator(name = "FIXED_ASSET_RCV_ID_SEQ", sequenceName = "FIXED_ASSET_RCV_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_MASTER_ID", nullable = false)
	private ItemMaster itemMaster;
	
	private Integer itemMasterId;
	
	@Column(name = "ASSET_ID")
	private String assetId;
	
	@Column(name = "ASSET_TYPE")
	private String assetType;

	@Column(name = "VERSION")
	private Integer version = 1;

	@Column(name = "TICKET_ID")
	private String ticketId;

	@Column(name = "TICKET_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ticketDate;

	@Column(name = "FROM_DEPT")
	private String fromDept;

	@Column(name = "TO_DEPT")
	private String toDept;

	@Column(name = "ZONE")
	private String zone;

	@Column(name = "REG_NO")
	private String regNo;
	
	@Column(name = "FA_REG_KEY")
	private String faRegKey;

	@Column(name = "RECEIVE_DATE")
	private Date receiveDate;

	@Column(name = "KHAT_CODE")
	private String khatCode;

	@Column(name = "ITEM_ID")
	private String itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "AVG_PRICE")
	private Double avgPrice;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;
	
	@Column(name = "NON_CODED")
	private boolean nonCoded=false;	

	@Column(name = "WRITE_OFF")
	private boolean writeOff=false;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "SUB_CATEGORY")
	private String subCategory;

	@Column(name = "RATING")
	private String rating;
	
	@Column(name = "LIFE_TIME")
	private String lifeTime;
	
	@Column(name = "DEPRECIATION_RATE")
	private Double depreciationRate;
	
	@Column(name = "MODEL_NUMBER")
	private String modelNumber;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name = "DEPRECIATED")
	private boolean depreciated=false;

	@Column(name = "WRITE_OFF_VALUE")
	private Double writeOffValue;

	@Column(name = "REASON_WRITE_OFF")
	private String reasonWriteOff;

	@Column(name = "WRITE_OFF_NOTE")
	private String writeOffNote;	

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

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	public String getFromDept() {
		return fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public String getToDept() {
		return toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getKhatCode() {
		return khatCode;
	}

	public void setKhatCode(String khatCode) {
		this.khatCode = khatCode;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	/*public String getLifeTime() {
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
	}*/

	public boolean isActive() {
		return active;
	}

	public boolean isDepreciated() {
		return depreciated;
	}

	public void setDepreciated(boolean depreciated) {
		this.depreciated = depreciated;
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
	
	public ItemMaster getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}

	public Integer getItemMasterId() {
		return itemMasterId;
	}

	public void setItemMasterId(Integer itemMasterId) {
		this.itemMasterId = itemMasterId;
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

	public String getFaRegKey() {
		return faRegKey;
	}

	public void setFaRegKey(String faRegKey) {
		this.faRegKey = faRegKey;
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

}
