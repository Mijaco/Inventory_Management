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

@Entity
@Table(name = "PND_JOB_MST")
public class PndJobMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pnd_job_mst_id_seq")
	@SequenceGenerator(name = "pnd_job_mst_id_seq", sequenceName = "pnd_job_mst_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "WO_NUMBER")
	private String woNumber;

	@Column(name = "auto_job_no")
	private String autoJobNo;

	@ManyToOne
	@JoinColumn(name = "Contractor_MST_ID")
	private Contractor contractor;

	@Column(name = "JOB_NO")
	private String jobNo;

	@Column(name = "JOB_TITTLE")
	private String jobTitle;

	@Column(name = "PD_NO")
	private String pdNo;

	@Column(name = "PND_NO")
	private String pndNo;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "LOT")
	private String lot;

	@Column(name = "CONSTRUCTION_NATURE")
	private String constructionNature;

	@Column(name = "JOB_LOCATION")
	private String jobLocation;

	@Column(name = "OTHERS")
	private String others;

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

	@Column(name = "is_Approved")
	private String approved;

	@Column(name = "as_build_no")
	private String asBuildNo;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getOthers() {
		return others;
	}

	public String getPdNo() {
		return pdNo;
	}

	public void setPdNo(String pdNo) {
		this.pdNo = pdNo;
	}

	public String getPndNo() {
		return pndNo;
	}

	public void setPndNo(String pndNo) {
		this.pndNo = pndNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getConstructionNature() {
		return constructionNature;
	}

	public void setConstructionNature(String constructionNature) {
		this.constructionNature = constructionNature;
	}

	public void setOthers(String others) {
		this.others = others;
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

	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	public String getAutoJobNo() {
		return autoJobNo;
	}

	public void setAutoJobNo(String autoJobNo) {
		this.autoJobNo = autoJobNo;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getAsBuildNo() {
		return asBuildNo;
	}

	public void setAsBuildNo(String asBuildNo) {
		this.asBuildNo = asBuildNo;
	}

}
