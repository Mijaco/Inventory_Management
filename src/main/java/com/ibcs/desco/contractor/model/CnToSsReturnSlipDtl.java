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
@Table(name = "cn_ss_return_slip_dtl")
public class CnToSsReturnSlipDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cn_ss_str_tkt_dtl_seq")
	@SequenceGenerator(name = "cn_ss_str_tkt_dtl_seq", sequenceName = "cn_ss_str_tkt_dtl_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "description")
	private String description;

	@Column(name = "return_slip_no")
	private String returnSlipNo;

	@Column(name = "uom")
	private String uom;

	@Column(name = "quantity_retruned")
	private Double qtyRetrun;

	@Column(name = "quantity_serviceable")
	private Double qtyServiceable;

	@Column(name = "quantity_repairable")
	private Double qtyRepairable;

	@Column(name = "quantity_non_repairable")
	private Double qtyNonRepairable;

	@Column(name = "total_received")
	private Double totalReceived;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "is_active")
	private boolean active = true;

	@ManyToOne
	@JoinColumn(name = "return_slip_mst_id")
	private CnToSsReturnSlipMst returnSlipMst;

	@Transient
	private Integer returnSlipMstId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReturnSlipNo() {
		return returnSlipNo;
	}

	public void setReturnSlipNo(String returnSlipNo) {
		this.returnSlipNo = returnSlipNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getQtyRetrun() {
		return qtyRetrun;
	}

	public void setQtyRetrun(Double qtyRetrun) {
		this.qtyRetrun = qtyRetrun;
	}

	public Double getQtyServiceable() {
		return qtyServiceable;
	}

	public void setQtyServiceable(Double qtyServiceable) {
		this.qtyServiceable = qtyServiceable;
	}

	public Double getQtyRepairable() {
		return qtyRepairable;
	}

	public void setQtyRepairable(Double qtyRepairable) {
		this.qtyRepairable = qtyRepairable;
	}

	public Double getQtyNonRepairable() {
		return qtyNonRepairable;
	}

	public void setQtyNonRepairable(Double qtyNonRepairable) {
		this.qtyNonRepairable = qtyNonRepairable;
	}

	public Double getTotalReceived() {
		return totalReceived;
	}

	public void setTotalReceived(Double totalReceived) {
		this.totalReceived = totalReceived;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CnToSsReturnSlipMst getReturnSlipMst() {
		return returnSlipMst;
	}

	public void setReturnSlipMst(CnToSsReturnSlipMst returnSlipMst) {
		this.returnSlipMst = returnSlipMst;
	}

	public Integer getReturnSlipMstId() {
		return returnSlipMstId;
	}

	public void setReturnSlipMstId(Integer returnSlipMstId) {
		this.returnSlipMstId = returnSlipMstId;
	}

}
