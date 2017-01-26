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
@Table(name = "PND_JOB_DTL")
public class PndJobDtl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pnd_job_dtl_id_seq")
	@SequenceGenerator(name = "pnd_job_dtl_id_seq", sequenceName = "pnd_job_dtl_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Transient
	private int pndJobMstId;

	@ManyToOne
	@JoinColumn(name = "MST_ID")
	private PndJobMst pndJobMst;

	@Column(name = "ITEM_CODE")
	private String itemCode;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "QTY")
	private double quantity;

	@Column(name = "EXTEND_QTY")
	private double extendQuantity;

	@Column(name = "REMAINNING_QUANTITY")
	private double remainningQuantity;

	@Column(name = "OTHERS")
	private Date others;

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

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getExtendQuantity() {
		return extendQuantity;
	}

	public void setExtendQuantity(double extendQuantity) {
		this.extendQuantity = extendQuantity;
	}

	public double getRemainningQuantity() {
		return remainningQuantity;
	}

	public void setRemainningQuantity(double remainningQuantity) {
		this.remainningQuantity = remainningQuantity;
	}

	public Date getOthers() {
		return others;
	}

	public void setOthers(Date others) {
		this.others = others;
	}

	public PndJobMst getPndJobMst() {
		return pndJobMst;
	}

	public void setPndJobMst(PndJobMst pndJobMst) {
		this.pndJobMst = pndJobMst;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getPndJobMstId() {
		return pndJobMstId;
	}

	public void setPndJobMstId(int pndJobMstId) {
		this.pndJobMstId = pndJobMstId;
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
