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

import com.ibcs.desco.common.model.DescoKhath;

@Entity
@Table(name = "CONTRACT_MANAGEMENT")
public class ContractManagement {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONT_MANAGE_ID_SEQ")
	@SequenceGenerator(name = "CONT_MANAGE_ID_SEQ", sequenceName = "CONT_MANAGE_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "contractor_name")
	private String contractorName;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "contract_validity_Month")
	private Integer contractValidityMonth;

	@Column(name = "pg_amount")
	private double pgAmount;

	@Column(name = "contract_doc")
	private String contractDoc;

	@Column(name = "tender_no")
	private String tenderNo;

	@Column(name = "contractor_address")
	private String contractorAddress;

	@Transient
	private String userid;

	@Transient
	private String justification;

	@Transient
	private String stateCode;

	@ManyToOne
	@JoinColumn(name = "desco_project")
	private DescoKhath descoKhath;

	// 400
	@Column(name = "psi_verification_ref")
	private String psiVerificationRef;

	@Column(name = "psi_verification_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date psiVerificationDate;

	@Column(name = "psi_verification_remarks")
	private String psiVerificationRemarks;
	// 500
	@Column(name = "psi_accept_ref")
	private String psiAcceptanceRef;

	@Column(name = "psi_accept_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date psiAcceptanceDate;

	@Column(name = "psi_accept_remarks")
	private String psiAcceptanceRemarks;
	// 600
	@Column(name = "chalan_no")
	private String chalanNo;

	@Column(name = "chalan_copy")
	private String chalanCopy;

	@Column(name = "goods_received_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date goodsReceivedDate;

	@Column(name = "goods_received_remarks")
	private String goodsReceivedRemarks;
	// 700
	@Column(name = "pli_report_copy")
	private String pliReportCopy;

	@Column(name = "pli_report_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date pliReportDate;

	@Column(name = "pli_report_remarks")
	private String pliReportRemarks;
	// 800
	@Column(name = "pli_accept_ref")
	private String pliAcceptanceRef;

	@Column(name = "pli_accept_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date pliAcceptanceDate;

	@Column(name = "pli_accept_remarks")
	private String pliAcceptanceRemarks;
	//

	@ManyToOne
	@JoinColumn(name = "app_purchase_mst")
	private AppPurchaseMst appPurchaseMst;

	@Column(name = "contract_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractDate;

	@Column(name = "pg_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date pgDate;

	@Column(name = "contract_expir_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractExpiredDate;

	@Column(name = "contract_extend_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractExtendedDate;

	@Column(name = "confirm_flag")
	private String confirmFlag;

	@Column(name = "drw_approval_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date drwApprovalDate;

	@Column(name = "drw_approval_copy")
	private String drwApprovalCopy;

	@Column(name = "drw_approval_remarks")
	private String drwApprovalRemarks;

	@Column(name = "psi_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date psiDate;

	@Transient
	private String downloadPath;

	@Column(name = "psi_report")
	private String psiReport;

	@Column(name = "psi_remarks")
	private String psiRemarks;

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

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getContractValidityMonth() {
		return contractValidityMonth;
	}

	public void setContractValidityMonth(Integer contractValidityMonth) {
		this.contractValidityMonth = contractValidityMonth;
	}

	public double getPgAmount() {
		return pgAmount;
	}

	public void setPgAmount(double pgAmount) {
		this.pgAmount = pgAmount;
	}

	public String getContractDoc() {
		return contractDoc;
	}

	public void setContractDoc(String contractDoc) {
		this.contractDoc = contractDoc;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public DescoKhath getDescoKhath() {
		return descoKhath;
	}

	public void setDescoKhath(DescoKhath descoKhath) {
		this.descoKhath = descoKhath;
	}

	public AppPurchaseMst getAppPurchaseMst() {
		return appPurchaseMst;
	}

	public void setAppPurchaseMst(AppPurchaseMst appPurchaseMst) {
		this.appPurchaseMst = appPurchaseMst;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getPgDate() {
		return pgDate;
	}

	public void setPgDate(Date pgDate) {
		this.pgDate = pgDate;
	}

	public Date getContractExpiredDate() {
		return contractExpiredDate;
	}

	public void setContractExpiredDate(Date contractExpiredDate) {
		this.contractExpiredDate = contractExpiredDate;
	}

	public Date getContractExtendedDate() {
		return contractExtendedDate;
	}

	public void setContractExtendedDate(Date contractExtendedDate) {
		this.contractExtendedDate = contractExtendedDate;
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

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Date getDrwApprovalDate() {
		return drwApprovalDate;
	}

	public void setDrwApprovalDate(Date drwApprovalDate) {
		this.drwApprovalDate = drwApprovalDate;
	}

	public String getDrwApprovalCopy() {
		return drwApprovalCopy;
	}

	public void setDrwApprovalCopy(String drwApprovalCopy) {
		this.drwApprovalCopy = drwApprovalCopy;
	}

	public String getDrwApprovalRemarks() {
		return drwApprovalRemarks;
	}

	public void setDrwApprovalRemarks(String drwApprovalRemarks) {
		this.drwApprovalRemarks = drwApprovalRemarks;
	}

	public Date getPsiDate() {
		return psiDate;
	}

	public void setPsiDate(Date psiDate) {
		this.psiDate = psiDate;
	}

	public String getPsiReport() {
		return psiReport;
	}

	public void setPsiReport(String psiReport) {
		this.psiReport = psiReport;
	}

	public String getPsiRemarks() {
		return psiRemarks;
	}

	public void setPsiRemarks(String psiRemarks) {
		this.psiRemarks = psiRemarks;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPsiVerificationRef() {
		return psiVerificationRef;
	}

	public void setPsiVerificationRef(String psiVerificationRef) {
		this.psiVerificationRef = psiVerificationRef;
	}

	public Date getPsiVerificationDate() {
		return psiVerificationDate;
	}

	public void setPsiVerificationDate(Date psiVerificationDate) {
		this.psiVerificationDate = psiVerificationDate;
	}

	public String getPsiVerificationRemarks() {
		return psiVerificationRemarks;
	}

	public void setPsiVerificationRemarks(String psiVerificationRemarks) {
		this.psiVerificationRemarks = psiVerificationRemarks;
	}

	public String getPsiAcceptanceRef() {
		return psiAcceptanceRef;
	}

	public void setPsiAcceptanceRef(String psiAcceptanceRef) {
		this.psiAcceptanceRef = psiAcceptanceRef;
	}

	public Date getPsiAcceptanceDate() {
		return psiAcceptanceDate;
	}

	public void setPsiAcceptanceDate(Date psiAcceptanceDate) {
		this.psiAcceptanceDate = psiAcceptanceDate;
	}

	public String getPsiAcceptanceRemarks() {
		return psiAcceptanceRemarks;
	}

	public void setPsiAcceptanceRemarks(String psiAcceptanceRemarks) {
		this.psiAcceptanceRemarks = psiAcceptanceRemarks;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public String getChalanCopy() {
		return chalanCopy;
	}

	public void setChalanCopy(String chalanCopy) {
		this.chalanCopy = chalanCopy;
	}

	public Date getGoodsReceivedDate() {
		return goodsReceivedDate;
	}

	public void setGoodsReceivedDate(Date goodsReceivedDate) {
		this.goodsReceivedDate = goodsReceivedDate;
	}

	public String getGoodsReceivedRemarks() {
		return goodsReceivedRemarks;
	}

	public void setGoodsReceivedRemarks(String goodsReceivedRemarks) {
		this.goodsReceivedRemarks = goodsReceivedRemarks;
	}

	public String getPliReportCopy() {
		return pliReportCopy;
	}

	public void setPliReportCopy(String pliReportCopy) {
		this.pliReportCopy = pliReportCopy;
	}

	public Date getPliReportDate() {
		return pliReportDate;
	}

	public void setPliReportDate(Date pliReportDate) {
		this.pliReportDate = pliReportDate;
	}

	public String getPliReportRemarks() {
		return pliReportRemarks;
	}

	public void setPliReportRemarks(String pliReportRemarks) {
		this.pliReportRemarks = pliReportRemarks;
	}

	public String getPliAcceptanceRef() {
		return pliAcceptanceRef;
	}

	public void setPliAcceptanceRef(String pliAcceptanceRef) {
		this.pliAcceptanceRef = pliAcceptanceRef;
	}

	public Date getPliAcceptanceDate() {
		return pliAcceptanceDate;
	}

	public void setPliAcceptanceDate(Date pliAcceptanceDate) {
		this.pliAcceptanceDate = pliAcceptanceDate;
	}

	public String getPliAcceptanceRemarks() {
		return pliAcceptanceRemarks;
	}

	public void setPliAcceptanceRemarks(String pliAcceptanceRemarks) {
		this.pliAcceptanceRemarks = pliAcceptanceRemarks;
	}

	@Override
	public String toString() {
		return "ContractManagement [id=" + id + ", contractorName="
				+ contractorName + ", contractNo=" + contractNo
				+ ", contractValidityMonth=" + contractValidityMonth
				+ ", pgAmount=" + pgAmount + ", contractDoc=" + contractDoc
				+ ", tenderNo=" + tenderNo + ", contractorAddress="
				+ contractorAddress + ", descoKhath=" + descoKhath
				+ ", appPurchaseMst=" + appPurchaseMst + ", contractDate="
				+ contractDate + ", pgDate=" + pgDate
				+ ", contractExpiredDate=" + contractExpiredDate
				+ ", contractExtendedDate=" + contractExtendedDate
				+ ", active=" + active + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
