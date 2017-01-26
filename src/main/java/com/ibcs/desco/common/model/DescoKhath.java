package com.ibcs.desco.common.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.ibcs.desco.admin.model.Departments;

@Entity
@Table(name = "DESCO_KHATH")
public class DescoKhath {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESCO_KHATH_SEQ")
	@SequenceGenerator(name = "DESCO_KHATH_SEQ", sequenceName = "DESCO_KHATH_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "khath_code")
	private String khathCode;

	@Column(name = "reference_no")
	private String referenceNo;

	@Column(name = "description")
	private String description;

	@Column(name = "source_of_fund")
	private String sourceOfFund;

	@ManyToOne
	@JoinColumn(name = "dept_mst_id")
	private Departments department;

	@Column(name = "pd_name")
	private String pdName;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date endDate;

	@Column(name = "duration")
	private String duration;

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
	private String deptId;

	public DescoKhath() {
	}

	public DescoKhath(Integer id, String khathName, String khathCode,
			String description, String sourceOfFund, String pdName,
			Date startDate, Date endDate, String duration, boolean active,
			String remarks, String createdBy, Date createdDate, Departments department) {
		super();
		this.id = id;
		this.khathName = khathName;
		this.khathCode = khathCode;
		this.description = description;
		this.sourceOfFund = sourceOfFund;
		this.pdName = pdName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKhathCode() {
		return khathCode;
	}

	public void setKhathCode(String khathCode) {
		this.khathCode = khathCode;
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

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
