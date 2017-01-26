package com.ibcs.desco.cs.model;

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
@Table(name = "TEMP_ITEM_LOCATION")
/*@Table(name = "TEMP_ITEM_LOCATION", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"uuid", "item_code", "location_id" }) })*/
public class TempItemLocation {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temp_item_loc_id_seq")
	@SequenceGenerator(name = "temp_item_loc_id_seq", sequenceName = "temp_item_loc_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "item_code", nullable = false)
	private String itemCode;

	@Column(name = "ledger_type", nullable = true)
	private String ledgerName;

	@Column(name = "uuid", nullable = false)
	private String uuid;

	@Column(name = "location_id", nullable = false)
	private String locationId;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "godown_id")
	private String godownId;
	
	@Column(name = "floor_id")
	private String floorId;
	
	@Column(name = "sector_id")
	private String sectorId;
	
	@Column(name = "rake_id")
	private String rakeId;
	
	@Column(name = "bin_id")
	private String binId;	
	
	@Column(name = "child_location_id")
	private String childLocationId;
	
	@Column(name = "child_location_name")
	private String childLocationName;	

	@Column(name = "quantity", nullable = false)
	private Double quantity;

	@Column(name = "flag")
	private boolean flag = true;
	
	@Column(name = "khath_id")
	private String khathId;

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

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getChildLocationId() {
		return childLocationId;
	}

	public String getChildLocationName() {
		return childLocationName;
	}

	public void setChildLocationName(String childLocationName) {
		this.childLocationName = childLocationName;
	}

	public void setChildLocationId(String childLocationId) {
		this.childLocationId = childLocationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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

	public String getGodownId() {
		return godownId;
	}

	public void setGodownId(String godownId) {
		this.godownId = godownId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getRakeId() {
		return rakeId;
	}

	public void setRakeId(String rakeId) {
		this.rakeId = rakeId;
	}

	public String getBinId() {
		return binId;
	}

	public void setBinId(String binId) {
		this.binId = binId;
	}

	public String getKhathId() {
		return khathId;
	}

	public void setKhathId(String khathId) {
		this.khathId = khathId;
	}
	
	

}
