package com.ibcs.desco.workshop.model;

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

@Entity
@Table(name = "WS_AS_BUILT_DTL")
public class WsAsBuiltDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ws_as_built_dtl_id_seq")
	@SequenceGenerator(name = "ws_as_built_dtl_id_seq", sequenceName = "ws_as_built_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Transient
	private int asBuiltMstId;

	@ManyToOne
	@JoinColumn(name = "MST_ID")
	private WsAsBuiltMst asBuiltMst;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "MATERIALS_QUANTITY")
	private double materialsQuantity=0.0;

	@Column(name = "RECEIVED_QUANTITY")
	private double receivedQuantity=0.0;

	@Column(name = "MATERIALS_CONSUME")
	private double materialsConsume=0.0;

	@Column(name = "MATERIALS_IN_HAND")
	private double materialsInHand=0.0;

	@Column(name = "TOTAL")
	private double total;

	@Column(name = "IS_ACTIVE")
	private boolean active = true;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Transient
	private String jobNo;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getAsBuiltMstId() {
		return asBuiltMstId;
	}

	public void setAsBuiltMstId(int asBuiltMstId) {
		this.asBuiltMstId = asBuiltMstId;
	}

	public WsAsBuiltMst getAsBuiltMst() {
		return asBuiltMst;
	}

	public void setAsBuiltMst(WsAsBuiltMst asBuiltMst) {
		this.asBuiltMst = asBuiltMst;
	}

	public double getMaterialsQuantity() {
		return materialsQuantity;
	}

	public void setMaterialsQuantity(double materialsQuantity) {
		this.materialsQuantity = materialsQuantity;
	}

	public double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	
	public double getMaterialsConsume() {
		return materialsConsume;
	}

	public void setMaterialsConsume(double materialsConsume) {
		this.materialsConsume = materialsConsume;
	}

	public double getMaterialsInHand() {
		return materialsInHand;
	}

	public void setMaterialsInHand(double materialsInHand) {
		this.materialsInHand = materialsInHand;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

}
