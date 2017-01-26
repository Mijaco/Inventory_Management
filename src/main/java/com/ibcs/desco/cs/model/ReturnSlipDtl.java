package com.ibcs.desco.cs.model;

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
@Table(name = "cs_return_slip_dtl")
public class ReturnSlipDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_return_slip_dtl_id_seq")
	@SequenceGenerator(name = "cs_return_slip_dtl_id_seq", sequenceName = "cs_return_slip_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "new_servicable")
	private Double qtyNewServiceable = 0.0;

	@Column(name = "total_return")
	private Double totalReturn = 0.0;

	@Column(name = "unservicable")
	private Double qtyUnServiceable = 0.0;

	@Column(name = "recovery_servicable")
	private Double qtyRecServiceable = 0.0;

	@Column(name = "new_servicable_rcv")
	private Double qtyNewServiceableRcv = 0.0;

	@Column(name = "total_return_rcv")
	private Double totalReturnRcv = 0.0;

	@Column(name = "unservicable_rcv")
	private Double qtyUnServiceableRcv = 0.0;

	@Column(name = "recovery_servicable_rcv")
	private Double qtyRecServiceableRcv = 0.0;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "description")
	private String description;

	@Column(name = "return_slip_no")
	private String returnSlipNo;

	@Column(name = "uom")
	private String uom;
	

	@Column(name = "job_no")
	private String jobNo;

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
	private ReturnSlipMst returnSlipMst;

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

	public Double getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(Double totalReturn) {
		this.totalReturn = totalReturn;
	}

	public Double getQtyUnServiceable() {
		return qtyUnServiceable;
	}

	public void setQtyUnServiceable(Double qtyUnServiceable) {
		this.qtyUnServiceable = qtyUnServiceable;
	}

	public Double getQtyRecServiceable() {
		return qtyRecServiceable;
	}

	public void setQtyRecServiceable(Double qtyRecServiceable) {
		this.qtyRecServiceable = qtyRecServiceable;
	}

	public Double getQtyNewServiceable() {
		return qtyNewServiceable;
	}

	public void setQtyNewServiceable(Double qtyNewServiceable) {
		this.qtyNewServiceable = qtyNewServiceable;
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

	public ReturnSlipMst getReturnSlipMst() {
		return returnSlipMst;
	}

	public void setReturnSlipMst(ReturnSlipMst returnSlipMst) {
		this.returnSlipMst = returnSlipMst;
	}

	public Integer getReturnSlipMstId() {
		return returnSlipMstId;
	}

	public void setReturnSlipMstId(Integer returnSlipMstId) {
		this.returnSlipMstId = returnSlipMstId;
	}

	public Double getQtyNewServiceableRcv() {
		return qtyNewServiceableRcv;
	}

	public void setQtyNewServiceableRcv(Double qtyNewServiceableRcv) {
		this.qtyNewServiceableRcv = qtyNewServiceableRcv;
	}

	public Double getTotalReturnRcv() {
		return totalReturnRcv;
	}

	public void setTotalReturnRcv(Double totalReturnRcv) {
		this.totalReturnRcv = totalReturnRcv;
	}

	public Double getQtyUnServiceableRcv() {
		return qtyUnServiceableRcv;
	}

	public void setQtyUnServiceableRcv(Double qtyUnServiceableRcv) {
		this.qtyUnServiceableRcv = qtyUnServiceableRcv;
	}

	public Double getQtyRecServiceableRcv() {
		return qtyRecServiceableRcv;
	}

	public void setQtyRecServiceableRcv(Double qtyRecServiceableRcv) {
		this.qtyRecServiceableRcv = qtyRecServiceableRcv;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	
	

}
