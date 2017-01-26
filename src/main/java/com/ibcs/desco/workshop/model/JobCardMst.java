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
@Table(name = "JOB_CARD_MST")
public class JobCardMst {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_CARD_MST_ID_SEQ")
	@SequenceGenerator(name = "JOB_CARD_MST_ID_SEQ", sequenceName = "JOB_CARD_MST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "type_of_work")
	private String typeOfWork;

	@Column(name = "job_card_version")
	private Integer version = 1;

	@Column(name = "job_card_no")
	private String jobCardNo;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "transformer_type")
	private String transformerType;

	@Column(name = "transformer_serial_no")
	private String transformerSerialNo;

	@Column(name = "manufactured_name")
	private String manufacturedName;

	@Column(name = "manufactured_year")
	private String manufacturedYear;

	@Transient
	private int transformerRegisterId;

	@ManyToOne
	@JoinColumn(name = "transformer_register_id")
	private TransformerRegister transformerRegister;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "isApproved")
	private boolean approved = false;

	@Column(name = "isCloseOut")
	private boolean closeOut = false;

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

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(String transformerType) {
		this.transformerType = transformerType;
	}

	public String getTransformerSerialNo() {
		return transformerSerialNo;
	}

	public void setTransformerSerialNo(String transformerSerialNo) {
		this.transformerSerialNo = transformerSerialNo;
	}

	public String getManufacturedName() {
		return manufacturedName;
	}

	public void setManufacturedName(String manufacturedName) {
		this.manufacturedName = manufacturedName;
	}

	public String getManufacturedYear() {
		return manufacturedYear;
	}

	public void setManufacturedYear(String manufacturedYear) {
		this.manufacturedYear = manufacturedYear;
	}

	public int getTransformerRegisterId() {
		return transformerRegisterId;
	}

	public void setTransformerRegisterId(int transformerRegisterId) {
		this.transformerRegisterId = transformerRegisterId;
	}

	public TransformerRegister getTransformerRegister() {
		return transformerRegister;
	}

	public void setTransformerRegister(TransformerRegister transformerRegister) {
		this.transformerRegister = transformerRegister;
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

	public String getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isCloseOut() {
		return closeOut;
	}

	public void setCloseOut(boolean closeOut) {
		this.closeOut = closeOut;
	}

}
