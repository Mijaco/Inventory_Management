package com.ibcs.desco.procurement.model;

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
@Table(name="Requisition_Mst")
public class RequisitionMst {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "req_mst_id_seq")
	@SequenceGenerator(name = "req_mst_id_seq", sequenceName = "req_mst_id_seq", allocationSize = 1, initialValue = 1)
	private int id;
	
	@Column(name = "PRF_NO", unique=true, nullable=false)
	private String prfNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss a")
	private Date requisitionDate;
	
	@Column(name = "requisitionOfficer")
	private String requisitionOfficer;
	
	@Column(name = "department_from")
	private String departmentFrom;
	
	@Column(name = "requisition_to")
	private String requisitionTo;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "justification")
	private String justification;	
	
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss a")
	private Date createdDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss a")
	private Date modifiedDate;
	
	@Column(name = "isActive")
	private boolean isActive;	

	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getPrfNo() {
		return prfNo;
	}

	public void setPrfNo(String prfNo) {
		this.prfNo = prfNo;
	}

	public Date getRequisitionDate() {
		return requisitionDate;
	}

	public void setRequisitionDate(Date requisitionDate) {
		this.requisitionDate = requisitionDate;
	}

	public String getRequisitionOfficer() {
		return requisitionOfficer;
	}

	public void setRequisitionOfficer(String requisitionOfficer) {
		this.requisitionOfficer = requisitionOfficer;
	}

	public String getDepartmentFrom() {
		return departmentFrom;
	}

	public void setDepartmentFrom(String departmentFrom) {
		this.departmentFrom = departmentFrom;
	}

	public String getRequisitionTo() {
		return requisitionTo;
	}

	public void setRequisitionTo(String requisitionTo) {
		this.requisitionTo = requisitionTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}		

}
