package com.ibcs.desco.contractor.model;

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
@Table(name = "AS_BUILT_DTL")
public class AsBuiltDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "as_built_dtl_id_seq")
	@SequenceGenerator(name = "as_built_dtl_id_seq", sequenceName = "as_built_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Transient
	private int asBuiltMstId;

	@ManyToOne
	@JoinColumn(name = "MST_ID")
	private AsBuiltMst asBuiltMst;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "CONSUME")
	private double consume;

	@Column(name = "RE_USE")
	private double reUse;

	@Column(name = "TOTAL")
	private double total;

	@Column(name = "REC_SERVICEABLE")
	private double recServiceable;

	@Column(name = "REC_UNSERVICEABLE")
	private double recUnServiceable;

	@Column(name = "RE_BAL_SERVICEABLE")
	private double reBalServiceable;

	@Column(name = "RE_BAL_UNSERVICEABLE")
	private double reBalUnServiceable;

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

	public AsBuiltMst getAsBuiltMst() {
		return asBuiltMst;
	}

	public void setAsBuiltMst(AsBuiltMst asBuiltMst) {
		this.asBuiltMst = asBuiltMst;
	}

	public double getConsume() {
		return consume;
	}

	public void setConsume(double consume) {
		this.consume = consume;
	}

	public double getReUse() {
		return reUse;
	}

	public void setReUse(double reUse) {
		this.reUse = reUse;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getRecServiceable() {
		return recServiceable;
	}

	public void setRecServiceable(double recServiceable) {
		this.recServiceable = recServiceable;
	}

	public double getRecUnServiceable() {
		return recUnServiceable;
	}

	public void setRecUnServiceable(double recUnServiceable) {
		this.recUnServiceable = recUnServiceable;
	}

	public double getReBalServiceable() {
		return reBalServiceable;
	}

	public void setReBalServiceable(double reBalServiceable) {
		this.reBalServiceable = reBalServiceable;
	}

	public double getReBalUnServiceable() {
		return reBalUnServiceable;
	}

	public void setReBalUnServiceable(double reBalUnServiceable) {
		this.reBalUnServiceable = reBalUnServiceable;
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
