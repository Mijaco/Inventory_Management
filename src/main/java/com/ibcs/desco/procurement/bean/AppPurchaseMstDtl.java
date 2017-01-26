package com.ibcs.desco.procurement.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class AppPurchaseMstDtl {

	private Integer id;

	private String annexureNo;

	private String userid;

	private String tenderNo;

	private String cmsFlag;

	private Double pgAmount = 0.0;

	private String deptId;

	private String stateCode;

	private String requestedComments;

	private String downloadDocFile;

	private String projectName;

	private String purchaseStatus;

	private String requisitionRef;

	private String workOrderNo;

	private String contractorName;

	private String approvedBy;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqPubDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqSubDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqEvalDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date noaDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date workOrderDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqExtentionDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendOpeningDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date expiredDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date approvedDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date reviewDate;

	private Double contractAmount;

	private String postedBy;

	private Integer numberOfSubmission;

	private boolean active = true;

	private String comments;

	private String justification;

	private List<String> remarks;

	private List<Double> purchaseCost;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnnexureNo() {
		return annexureNo;
	}

	public void setAnnexureNo(String annexureNo) {
		this.annexureNo = annexureNo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDownloadDocFile() {
		return downloadDocFile;
	}

	public void setDownloadDocFile(String downloadDocFile) {
		this.downloadDocFile = downloadDocFile;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public String getRequisitionRef() {
		return requisitionRef;
	}

	public void setRequisitionRef(String requisitionRef) {
		this.requisitionRef = requisitionRef;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getTendRfqPubDate() {
		return tendRfqPubDate;
	}

	public void setTendRfqPubDate(Date tendRfqPubDate) {
		this.tendRfqPubDate = tendRfqPubDate;
	}

	public Date getTendRfqSubDate() {
		return tendRfqSubDate;
	}

	public void setTendRfqSubDate(Date tendRfqSubDate) {
		this.tendRfqSubDate = tendRfqSubDate;
	}

	public Date getTendRfqEvalDate() {
		return tendRfqEvalDate;
	}

	public void setTendRfqEvalDate(Date tendRfqEvalDate) {
		this.tendRfqEvalDate = tendRfqEvalDate;
	}

	public Date getNoaDate() {
		return noaDate;
	}

	public void setNoaDate(Date noaDate) {
		this.noaDate = noaDate;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public Date getTendRfqExtentionDate() {
		return tendRfqExtentionDate;
	}

	public void setTendRfqExtentionDate(Date tendRfqExtentionDate) {
		this.tendRfqExtentionDate = tendRfqExtentionDate;
	}

	public Date getTendOpeningDate() {
		return tendOpeningDate;
	}

	public void setTendOpeningDate(Date tendOpeningDate) {
		this.tendOpeningDate = tendOpeningDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public Integer getNumberOfSubmission() {
		return numberOfSubmission;
	}

	public void setNumberOfSubmission(Integer numberOfSubmission) {
		this.numberOfSubmission = numberOfSubmission;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public List<Double> getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(List<Double> purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRequestedComments() {
		return requestedComments;
	}

	public void setRequestedComments(String requestedComments) {
		this.requestedComments = requestedComments;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public String getCmsFlag() {
		return cmsFlag;
	}

	public void setCmsFlag(String cmsFlag) {
		this.cmsFlag = cmsFlag;
	}

	public Double getPgAmount() {
		return pgAmount;
	}

	public void setPgAmount(Double pgAmount) {
		this.pgAmount = pgAmount;
	}

}
