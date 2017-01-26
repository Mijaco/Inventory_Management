package com.ibcs.desco.localStore.model;

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
import com.ibcs.desco.common.model.StoreLocations;

@Entity
@Table(name = "LS_ITEMS_LOCATION_DTL")
public class LSItemLocationDtl {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LS_ITM_LOC_DTL_SEQ")
	@SequenceGenerator(name = "LS_ITM_LOC_DTL_SEQ", sequenceName = "LS_ITM_LOC_DTL_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "item_code")
	private String itemCode;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private StoreLocations storeLocation;

	@Transient
	private Integer locationId;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "ledger_name")
	private String ledgerName;

	@Column(name = "tnxn_type")
	private String tnxn_type;

	@Column(name = "tnxn_mode")
	private boolean tnxnMode;

	@Column(name = "quantity")
	private double quantity;

	@ManyToOne
	@JoinColumn(name = "ls_itm_tnx_dtl_id", nullable = false)
	private LSItemTransactionDtl lsItemTransactionDtl;

	@Transient
	private Integer lsItmTnxDtlId;

	@ManyToOne
	@JoinColumn(name = "ls_itm_loc_mst_id", nullable = false)
	private LSItemLocationMst lsItemLocationMst;

	@Transient
	private Integer lsItmLocMstId;

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public StoreLocations getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(StoreLocations storeLocation) {
		this.storeLocation = storeLocation;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public String getTnxn_type() {
		return tnxn_type;
	}

	public void setTnxn_type(String tnxn_type) {
		this.tnxn_type = tnxn_type;
	}

	public boolean isTnxnMode() {
		return tnxnMode;
	}

	public void setTnxnMode(boolean tnxnMode) {
		this.tnxnMode = tnxnMode;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public LSItemTransactionDtl getLsItemTransactionDtl() {
		return lsItemTransactionDtl;
	}

	public void setLsItemTransactionDtl(LSItemTransactionDtl lsItemTransactionDtl) {
		this.lsItemTransactionDtl = lsItemTransactionDtl;
	}

	public Integer getLsItmTnxDtlId() {
		return lsItmTnxDtlId;
	}

	public void setLsItmTnxDtlId(Integer lsItmTnxDtlId) {
		this.lsItmTnxDtlId = lsItmTnxDtlId;
	}

	public LSItemLocationMst getLsItemLocationMst() {
		return lsItemLocationMst;
	}

	public void setLsItemLocationMst(LSItemLocationMst lsItemLocationMst) {
		this.lsItemLocationMst = lsItemLocationMst;
	}

	public Integer getLsItmLocMstId() {
		return lsItmLocMstId;
	}

	public void setLsItmLocMstId(Integer lsItmLocMstId) {
		this.lsItmLocMstId = lsItmLocMstId;
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

}
