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
@Table(name = "RP_MAIN_TARGET")
public class RepairAndMaintenanceTarget {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RP_MAIN_TARGET_ID_SEQ")
	@SequenceGenerator(name = "RP_MAIN_TARGET_ID_SEQ", sequenceName = "RP_MAIN_TARGET_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "work_order_no")
	private String workOrderNo;
	
	@Column(name = "target_date")
	private Date targetDate;
	
	@Column(name = "str_target_month")
	private String strTargetMonth;

	@ManyToOne
	@JoinColumn(name = "contractor_mst_id", nullable = false)
	private Contractor contractorMst;

	@Column(name = "repair_target_1P")
	private double repairTarget1P;

	@Column(name = "repair_target_3P")
	private double repairTarget3P;

	@Column(name = "preventive_target_1P")
	private double preventiveTarget1P;

	@Column(name = "preventive_target_3P")
	private double preventiveTarget3P;

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

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getStrTargetMonth() {
		return strTargetMonth;
	}

	public void setStrTargetMonth(String strTargetMonth) {
		this.strTargetMonth = strTargetMonth;
	}

	public double getRepairTarget1P() {
		return repairTarget1P;
	}

	public void setRepairTarget1P(double repairTarget1P) {
		this.repairTarget1P = repairTarget1P;
	}

	public double getRepairTarget3P() {
		return repairTarget3P;
	}

	public void setRepairTarget3P(double repairTarget3P) {
		this.repairTarget3P = repairTarget3P;
	}

	public double getPreventiveTarget1P() {
		return preventiveTarget1P;
	}

	public void setPreventiveTarget1P(double preventiveTarget1P) {
		this.preventiveTarget1P = preventiveTarget1P;
	}

	public double getPreventiveTarget3P() {
		return preventiveTarget3P;
	}

	public void setPreventiveTarget3P(double preventiveTarget3P) {
		this.preventiveTarget3P = preventiveTarget3P;
	}
	

}
