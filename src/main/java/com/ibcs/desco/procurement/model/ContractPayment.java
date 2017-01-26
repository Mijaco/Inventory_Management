package com.ibcs.desco.procurement.model;

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

@Entity
@Table(name = "CONTRACT_PAYMENT")
public class ContractPayment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONT_PAYMENT_ID_SEQ")
	@SequenceGenerator(name = "CONT_PAYMENT_ID_SEQ", sequenceName = "CONT_PAYMENT_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "contract_management")
	private ContractManagement contractManagement;
	
	@Transient
	private String contractNo;
	
	@Transient
	private String downloadPath;
	
	// desco invoice no
	@Column(name = "desco_inv_no")
	private String descoInvNo;

	// Contractor Invoice which Approved
	@Column(name = "contractor_app_inv_doc")
	private String contractorAppInvDoc;

	@Column(name = "payment_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date paymentDate;

	@Column(name = "payment_amount")
	private Double paymentAmount = 0.0;

	@Column(name = "payment_by")
	private String paymentBy;

	@Column(name = "check_no")
	private String checkNo;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "check_receiv_by")
	private String checkReceivedBy;

	// Desco Check which pay to Contractor
	@Column(name = "desco_check_doc")
	private String descoCheckDoc;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ContractManagement getContractManagement() {
		return contractManagement;
	}

	public void setContractManagement(ContractManagement contractManagement) {
		this.contractManagement = contractManagement;
	}

	public String getDescoInvNo() {
		return descoInvNo;
	}

	public void setDescoInvNo(String descoInvNo) {
		this.descoInvNo = descoInvNo;
	}

	public String getContractorAppInvDoc() {
		return contractorAppInvDoc;
	}

	public void setContractorAppInvDoc(String contractorAppInvDoc) {
		this.contractorAppInvDoc = contractorAppInvDoc;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentBy() {
		return paymentBy;
	}

	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCheckReceivedBy() {
		return checkReceivedBy;
	}

	public void setCheckReceivedBy(String checkReceivedBy) {
		this.checkReceivedBy = checkReceivedBy;
	}

	public String getDescoCheckDoc() {
		return descoCheckDoc;
	}

	public void setDescoCheckDoc(String descoCheckDoc) {
		this.descoCheckDoc = descoCheckDoc;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	@Override
	public String toString() {
		return "ContractPayment [id=" + id + ", contractManagement="
				+ contractManagement + ", descoInvNo=" + descoInvNo
				+ ", contractorAppInvDoc=" + contractorAppInvDoc
				+ ", paymentDate=" + paymentDate + ", paymentAmount="
				+ paymentAmount + ", paymentBy=" + paymentBy + ", checkNo="
				+ checkNo + ", bankName=" + bankName + ", branchName="
				+ branchName + ", checkReceivedBy=" + checkReceivedBy
				+ ", descoCheckDoc=" + descoCheckDoc + ", active=" + active
				+ ", remarks=" + remarks + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

}
