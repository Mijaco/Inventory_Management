package com.ibcs.desco.admin.model;

import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "ALLOCATION_TABLE")
public class AllocationTable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allocation_tab_id_seq")
	@SequenceGenerator(name = "allocation_tab_id_seq", sequenceName = "allocation_tab_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "requisition_limit")
	private double requisitionLimit = 0.0;

	@Column(name = "isUnlimited")
	private boolean unlimited = true;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "uom")
	private String uom;

	@ManyToOne
	@JoinColumn(name = "desco_session_mst_id", nullable = false)
	private DescoSession descoSession;

	@Transient
	private Integer descoSessionId;

	@Transient
	private String deptId;

	@Column(name = "snd_code")
	private String sndCode;

	@Transient
	private String sndName;

	@Column(name = "used_quantity")
	private double usedQuantity = 0.0;

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

	@Transient
	private List<String> allocationId;

	@Transient
	private List<Double> requisitionLimitQty;

	@Transient
	private List<Boolean> unlimitedReq;

	public AllocationTable() {
	}

	public AllocationTable(Integer id, double requisitionLimit,
			boolean unlimited, String itemCode, String itemName, String uom,
			DescoSession descoSession, String sndCode, double usedQuantity,
			boolean active, String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.requisitionLimit = requisitionLimit;
		this.unlimited = unlimited;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.uom = uom;
		this.descoSession = descoSession;
		this.sndCode = sndCode;
		this.usedQuantity = usedQuantity;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getRequisitionLimit() {
		return requisitionLimit;
	}

	public void setRequisitionLimit(double requisitionLimit) {
		this.requisitionLimit = requisitionLimit;
	}

	public boolean isUnlimited() {
		return unlimited;
	}

	public void setUnlimited(boolean unlimited) {
		this.unlimited = unlimited;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
	}

	public Integer getDescoSessionId() {
		return descoSessionId;
	}

	public void setDescoSessionId(Integer descoSessionId) {
		this.descoSessionId = descoSessionId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSndCode() {
		return sndCode;
	}

	public void setSndCode(String sndCode) {
		this.sndCode = sndCode;
	}

	public double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(double usedQuantity) {
		this.usedQuantity = usedQuantity;
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

	public List<String> getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(List<String> allocationId) {
		this.allocationId = allocationId;
	}

	public List<Double> getRequisitionLimitQty() {
		return requisitionLimitQty;
	}

	public void setRequisitionLimitQty(List<Double> requisitionLimitQty) {
		this.requisitionLimitQty = requisitionLimitQty;
	}

	public List<Boolean> getUnlimitedReq() {
		return unlimitedReq;
	}

	public void setUnlimitedReq(List<Boolean> unlimitedReq) {
		this.unlimitedReq = unlimitedReq;
	}

	public String getSndName() {
		return sndName;
	}

	public void setSndName(String sndName) {
		this.sndName = sndName;
	}

}
