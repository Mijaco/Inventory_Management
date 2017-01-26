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
import com.ibcs.desco.contractor.model.Contractor;

@Entity
@Table(name = "WS_CN_ALLOCATION")
public class WsCnAllocation {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WS_CN_ALLOCATION_ID_SEQ")
	@SequenceGenerator(name = "WS_CN_ALLOCATION_ID_SEQ", sequenceName = "WS_CN_ALLOCATION_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "work_order_no")
	private String workOrderNo;

	@ManyToOne
	@JoinColumn(name = "contractor_mst_id", nullable = false)
	private Contractor contractorMst;

	@Column(name = "repair_qty_1P")
	private double repairQty1P;

	@Column(name = "repair_qty_1P_remain")
	private double remainingRepairQty1P;

	@Column(name = "repair_qty_3P")
	private double repairQty3P;

	@Column(name = "repair_qty_3P_remain")
	private double remainingRepairQty3P;

	@Column(name = "preventive_qty_1P")
	private double preventiveQty1P;

	@Column(name = "preventive_qty_1P_remain")
	private double remainingPreventiveQty1P;

	@Column(name = "preventive_qty_3P")
	private double preventiveQty3P;

	@Column(name = "preventive_qty_3P_remain")
	private double remainingPreventiveQty3P;

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

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public double getRepairQty1P() {
		return repairQty1P;
	}

	public void setRepairQty1P(double repairQty1P) {
		this.repairQty1P = repairQty1P;
	}

	public double getRepairQty3P() {
		return repairQty3P;
	}

	public void setRepairQty3P(double repairQty3P) {
		this.repairQty3P = repairQty3P;
	}

	public double getPreventiveQty1P() {
		return preventiveQty1P;
	}

	public void setPreventiveQty1P(double preventiveQty1P) {
		this.preventiveQty1P = preventiveQty1P;
	}

	public double getPreventiveQty3P() {
		return preventiveQty3P;
	}

	public void setPreventiveQty3P(double preventiveQty3P) {
		this.preventiveQty3P = preventiveQty3P;
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

	public Contractor getContractorMst() {
		return contractorMst;
	}

	public void setContractorMst(Contractor contractorMst) {
		this.contractorMst = contractorMst;
	}

	public double getRemainingRepairQty1P() {
		return remainingRepairQty1P;
	}

	public void setRemainingRepairQty1P(double remainingRepairQty1P) {
		this.remainingRepairQty1P = remainingRepairQty1P;
	}

	public double getRemainingRepairQty3P() {
		return remainingRepairQty3P;
	}

	public void setRemainingRepairQty3P(double remainingRepairQty3P) {
		this.remainingRepairQty3P = remainingRepairQty3P;
	}

	public double getRemainingPreventiveQty1P() {
		return remainingPreventiveQty1P;
	}

	public void setRemainingPreventiveQty1P(double remainingPreventiveQty1P) {
		this.remainingPreventiveQty1P = remainingPreventiveQty1P;
	}

	public double getRemainingPreventiveQty3P() {
		return remainingPreventiveQty3P;
	}

	public void setRemainingPreventiveQty3P(double remainingPreventiveQty3P) {
		this.remainingPreventiveQty3P = remainingPreventiveQty3P;
	}

	@Override
	public String toString() {
		return "WsCnAllocation [id=" + id + ", workOrderNo=" + workOrderNo
				+ ", contractorMst=" + contractorMst + ", repairQty1P="
				+ repairQty1P + ", remainingRepairQty1P="
				+ remainingRepairQty1P + ", repairQty3P=" + repairQty3P
				+ ", remainingRepairQty3P=" + remainingRepairQty3P
				+ ", preventiveQty1P=" + preventiveQty1P
				+ ", remainingPreventiveQty1P=" + remainingPreventiveQty1P
				+ ", preventiveQty3P=" + preventiveQty3P
				+ ", remainingPreventiveQty3P=" + remainingPreventiveQty3P
				+ ", remarks=" + remarks + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
	

}
