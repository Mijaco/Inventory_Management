package com.ibcs.desco.contractor.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CONTRACTOR")
public class Contractor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractor_mst_id_seq")
	@SequenceGenerator(name = "contractor_mst_id_seq", sequenceName = "contractor_mst_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "CONTRACT_NO")
	private String contractNo;

	@Column(name = "CONTRACTOR_NAME")
	private String contractorName;

	@Column(name = "CONTRACTOR_TYPE")
	private String contractorType;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CONTRACT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	// @DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date contractDate;

	@Column(name = "EXPIRY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	// @DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date expiryDate;

	@Column(name = "TENDER_NO")
	private String tenderNo;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "DEPT_ID")
	private String deptId;

	@Column(name = "OTHERS")
	private String others;

	@Column(name = "snd_dept_pk")
	private String sndDeptPk;

	@Column(name = "UPDATED_VALIDITY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date updatedValidityDate;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "fco_doc")
	private String fcoDoc;

	@Column(name = "fco_payment_details")
	private String paymentDetails;

	@Column(name = "fco_reasion")
	private String reasionCloseOut;

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

	@Column(name = "close_out")
	private String closeOut;

	@Transient
	private String downloadDocFile;

	@Transient
	private List<PndJobMst> pndJobMstList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Date getUpdatedValidityDate() {
		return updatedValidityDate;
	}

	public void setUpdatedValidityDate(Date updatedValidityDate) {
		this.updatedValidityDate = updatedValidityDate;
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

	public String getContractorType() {
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public String getSndDeptPk() {
		return sndDeptPk;
	}

	public void setSndDeptPk(String sndDeptPk) {
		this.sndDeptPk = sndDeptPk;
	}

	public String getCloseOut() {
		return closeOut;
	}

	public void setCloseOut(String closeOut) {
		this.closeOut = closeOut;
	}

	public List<PndJobMst> getPndJobMstList() {
		return pndJobMstList;
	}

	public void setPndJobMstList(List<PndJobMst> pndJobMstList) {
		this.pndJobMstList = pndJobMstList;
	}

	public String getFcoDoc() {
		return fcoDoc;
	}

	public void setFcoDoc(String fcoDoc) {
		this.fcoDoc = fcoDoc;
	}

	public String getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getReasionCloseOut() {
		return reasionCloseOut;
	}

	public void setReasionCloseOut(String reasionCloseOut) {
		this.reasionCloseOut = reasionCloseOut;
	}

	public String getDownloadDocFile() {
		return downloadDocFile;
	}

	public void setDownloadDocFile(String downloadDocFile) {
		this.downloadDocFile = downloadDocFile;
	}
}
