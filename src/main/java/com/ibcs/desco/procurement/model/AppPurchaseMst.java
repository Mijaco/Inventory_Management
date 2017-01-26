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

import com.ibcs.desco.admin.model.DescoSession;

@Entity
@Table(name = "APP_PURCHASE_MST")
public class AppPurchaseMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_purchase_mst_seq")
	@SequenceGenerator(name = "app_purchase_mst_seq", sequenceName = "app_purchase_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "desco_session")
	private DescoSession descoSession;

	@ManyToOne
	@JoinColumn(name = "proc_pack_mst_id", nullable = false)
	private ProcurementPackageMst procurementPackageMst;

	@Transient
	private String annexureNo;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "tender_no")
	private String tenderNo;

	@Column(name = "pg_amount")
	private Double pgAmount = 0.0;

	@Column(name = "cms_flag")
	private String cmsFlag;

	@Column(name = "purchase_status")
	private String purchaseStatus;

	@Column(name = "requisition_ref")
	private String requisitionRef;

	@Column(name = "contract_amount")
	private Double contractAmount;

	@Column(name = "posted_by")
	private String postedBy;

	@Column(name = "work_order_no")
	private String workOrderNo;

	@Column(name = "contractor_name")
	private String contractorName;

	@Column(name = "review_doc")
	private String reviewDoc;

	@Column(name = "approved_doc")
	private String approvedDoc;

	@Column(name = "work_order_doc")
	private String workOrderDoc;

	@Column(name = "tend_rfq_pub_doc")
	private String tendRfqPubDoc;

	@Column(name = "tend_rfq_doc")
	private String tendRfqDoc;

	@Column(name = "evaluation_doc")
	private String evaluationDoc;

	@Column(name = "noa_doc")
	private String noaDoc;

	@Column(name = "pg_doc")
	private String pgDoc;

	@Column(name = "specification_doc")
	private String specificationDoc;

	@Column(name = "purchase_Req_Note_Doc")
	private String purchaseRequsitionNoteDoc;

	@Column(name = "justification")
	private String justification;

	@Column(name = "requisition_app_doc")
	private String requisitionAppDoc;

	@Column(name = "draft_dender_doc")
	private String draftTenderDoc;

	@Column(name = "tend_rfq_exten_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqExtentionDate;

	@Column(name = "tend_opening_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendOpeningDate;

	@Column(name = "expired_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date expiredDate;

	@Column(name = "approved_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date approvedDate;

	@Column(name = "review_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date reviewDate;

	@Column(name = "tend_rfq_pub_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqPubDate;

	@Column(name = "tend_rfq_sub_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqSubDate;

	@Column(name = "tend_rfq_eval_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date tendRfqEvalDate;

	@Column(name = "noa_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date noaDate;

	@Column(name = "work_order_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date workOrderDate;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "requested_comments")
	private String requestedComments;

	@Column(name = "number_of_submission")
	private Integer numberOfSubmission;

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
	private String approvalStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
	}

	public ProcurementPackageMst getProcurementPackageMst() {
		return procurementPackageMst;
	}

	public void setProcurementPackageMst(
			ProcurementPackageMst procurementPackageMst) {
		this.procurementPackageMst = procurementPackageMst;
	}

	public String getAnnexureNo() {
		return annexureNo;
	}

	public void setAnnexureNo(String annexureNo) {
		this.annexureNo = annexureNo;
	}

	public String getRequisitionAppDoc() {
		return requisitionAppDoc;
	}

	public void setRequisitionAppDoc(String requisitionAppDoc) {
		this.requisitionAppDoc = requisitionAppDoc;
	}

	public String getDraftTenderDoc() {
		return draftTenderDoc;
	}

	public void setDraftTenderDoc(String draftTenderDoc) {
		this.draftTenderDoc = draftTenderDoc;
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

	public String getNoaDoc() {
		return noaDoc;
	}

	public void setNoaDoc(String noaDoc) {
		this.noaDoc = noaDoc;
	}

	public String getWorkOrderDoc() {
		return workOrderDoc;
	}

	public void setWorkOrderDoc(String workOrderDoc) {
		this.workOrderDoc = workOrderDoc;
	}

	public String getPgDoc() {
		return pgDoc;
	}

	public void setPgDoc(String pgDoc) {
		this.pgDoc = pgDoc;
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

	public String getReviewDoc() {
		return reviewDoc;
	}

	public void setReviewDoc(String reviewDoc) {
		this.reviewDoc = reviewDoc;
	}

	public String getApprovedDoc() {
		return approvedDoc;
	}

	public void setApprovedDoc(String approvedDoc) {
		this.approvedDoc = approvedDoc;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getTendRfqPubDoc() {
		return tendRfqPubDoc;
	}

	public void setTendRfqPubDoc(String tendRfqPubDoc) {
		this.tendRfqPubDoc = tendRfqPubDoc;
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

	public String getTendRfqDoc() {
		return tendRfqDoc;
	}

	public void setTendRfqDoc(String tendRfqDoc) {
		this.tendRfqDoc = tendRfqDoc;
	}

	public Integer getNumberOfSubmission() {
		return numberOfSubmission;
	}

	public void setNumberOfSubmission(Integer numberOfSubmission) {
		this.numberOfSubmission = numberOfSubmission;
	}

	public String getEvaluationDoc() {
		return evaluationDoc;
	}

	public void setEvaluationDoc(String evaluationDoc) {
		this.evaluationDoc = evaluationDoc;
	}

	public String getRequestedComments() {
		return requestedComments;
	}

	public void setRequestedComments(String requestedComments) {
		this.requestedComments = requestedComments;
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

	public String getSpecificationDoc() {
		return specificationDoc;
	}

	public void setSpecificationDoc(String specificationDoc) {
		this.specificationDoc = specificationDoc;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getPurchaseRequsitionNoteDoc() {
		return purchaseRequsitionNoteDoc;
	}

	public void setPurchaseRequsitionNoteDoc(String purchaseRequsitionNoteDoc) {
		this.purchaseRequsitionNoteDoc = purchaseRequsitionNoteDoc;
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

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	

}
