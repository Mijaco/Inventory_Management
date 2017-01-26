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

import com.ibcs.desco.admin.model.DescoSession;

@Entity
@Table(name = "PROC_PACK_MST")
public class ProcurementPackageMst {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROC_PACK_MST_ID_SEQ")
	@SequenceGenerator(name = "PROC_PACK_MST_ID_SEQ", sequenceName = "PROC_PACK_MST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@ManyToOne
	@JoinColumn(name = "desco_session")
	private DescoSession descoSession;

	@Column(name = "package_type")
	private String packageType;

	@Column(name = "qty_flag")
	private String qtyFlag;

	@Column(name = "package_name")
	private String packageName;
	
	@Column(name = "purchase_status")
	private String purchaseStatus;

	@Column(name = "proc_method")
	private String procMethod;

	@Column(name = "proc_type")
	private String procType;

	@Column(name = "proc_medium")
	private String procurementMedium;

	@Column(name = "procurement_qty")
	private Double procurementQty=0.0;

	@Column(name = "source_of_fund")
	private String sourceOfFund;

	@Column(name = "approving_auth")
	private String approvingAuth;

	@Column(name = "estimated_cost")
	private Double estimatedCost=0.0;

	@Column(name = "reasions_of_proc")
	private String reasonsOfProc;

	@Column(name = "proc_description")
	private String procDescription;

	@Column(name = "annexure_no")
	private String annexureNo;

	@Column(name = "prep_doc_inv_tender")
	private String prepDocInvTender;

	@Column(name = "evaluation_of_tender")
	private String evaluationOfTender;

	@Column(name = "award_of_contract")
	private String awardOfContract;

	@Column(name = "tentative_completion")	
	private String tentativeCompletion;

	@Column(name = "current_session_budget")
	private Double currentSessionBudget=0.0;

	@Column(name = "next_session_budget")
	private Double nextSessionBudget = 0.0;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "confirm")
	private String confirm;
	
	@Column(name = "purchase_flag")
	private String purchaseFlag="0";

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

	public ProcurementPackageMst() {
	}

	public ProcurementPackageMst(Integer id, DescoSession descoSession,
			String packageName, String procMethod, String procType,
			String procurementMedium, String sourceOfFund,
			String approvingAuth, Double estimatedCost, String reasonsOfProc,
			String procDescription, String annexureNo, String prepDocInvTender,
			String evaluationOfTender, String awardOfContract,
			String tentativeCompletion, Double currentSessionBudget,
			Double nextSessionBudget, String remarks, String createdBy,
			Date createdDate, Double procurementQty, String packageType,
			String qtyFlag, String purchaseStatus) {
		super();
		this.id = id;
		this.descoSession = descoSession;
		this.packageName = packageName;
		this.procMethod = procMethod;
		this.procType = procType;
		this.procurementMedium = procurementMedium;
		this.sourceOfFund = sourceOfFund;
		this.approvingAuth = approvingAuth;
		this.estimatedCost = estimatedCost;
		this.reasonsOfProc = reasonsOfProc;
		this.procDescription = procDescription;
		this.annexureNo = annexureNo;
		this.prepDocInvTender = prepDocInvTender;
		this.evaluationOfTender = evaluationOfTender;
		this.awardOfContract = awardOfContract;
		this.tentativeCompletion = tentativeCompletion;
		this.currentSessionBudget = currentSessionBudget;
		this.nextSessionBudget = nextSessionBudget;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.procurementQty = procurementQty;
		this.packageType = packageType;
		this.qtyFlag = qtyFlag;
		this.purchaseStatus = purchaseStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

	public String getPurchaseFlag() {
		return purchaseFlag;
	}

	public void setPurchaseFlag(String purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProcMethod() {
		return procMethod;
	}

	public void setProcMethod(String procMethod) {
		this.procMethod = procMethod;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getProcurementMedium() {
		return procurementMedium;
	}

	public void setProcurementMedium(String procurementMedium) {
		this.procurementMedium = procurementMedium;
	}

	public Double getProcurementQty() {
		return procurementQty;
	}

	public void setProcurementQty(Double procurementQty) {
		this.procurementQty = procurementQty;
	}

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public String getApprovingAuth() {
		return approvingAuth;
	}

	public void setApprovingAuth(String approvingAuth) {
		this.approvingAuth = approvingAuth;
	}

	public Double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(Double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public String getReasonsOfProc() {
		return reasonsOfProc;
	}

	public void setReasonsOfProc(String reasonsOfProc) {
		this.reasonsOfProc = reasonsOfProc;
	}

	public String getProcDescription() {
		return procDescription;
	}

	public void setProcDescription(String procDescription) {
		this.procDescription = procDescription;
	}

	public String getAnnexureNo() {
		return annexureNo;
	}

	public void setAnnexureNo(String annexureNo) {
		this.annexureNo = annexureNo;
	}

	public String getPrepDocInvTender() {
		return prepDocInvTender;
	}

	public void setPrepDocInvTender(String prepDocInvTender) {
		this.prepDocInvTender = prepDocInvTender;
	}

	public String getEvaluationOfTender() {
		return evaluationOfTender;
	}

	public void setEvaluationOfTender(String evaluationOfTender) {
		this.evaluationOfTender = evaluationOfTender;
	}

	public String getAwardOfContract() {
		return awardOfContract;
	}

	public void setAwardOfContract(String awardOfContract) {
		this.awardOfContract = awardOfContract;
	}

	public String getTentativeCompletion() {
		return tentativeCompletion;
	}

	public void setTentativeCompletion(String tentativeCompletion) {
		this.tentativeCompletion = tentativeCompletion;
	}

	public Double getCurrentSessionBudget() {
		return currentSessionBudget;
	}

	public void setCurrentSessionBudget(Double currentSessionBudget) {
		this.currentSessionBudget = currentSessionBudget;
	}

	public Double getNextSessionBudget() {
		return nextSessionBudget;
	}

	public void setNextSessionBudget(Double nextSessionBudget) {
		this.nextSessionBudget = nextSessionBudget;
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

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getQtyFlag() {
		return qtyFlag;
	}

	public void setQtyFlag(String qtyFlag) {
		this.qtyFlag = qtyFlag;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	

	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	@Override
	public String toString() {
		return "ProcurementPackageMst [id=" + id + ", descoSession="
				+ descoSession + ", packageType=" + packageType + ", qtyFlag="
				+ qtyFlag + ", packageName=" + packageName
				+ ", purchaseStatus=" + purchaseStatus + ", procMethod="
				+ procMethod + ", procType=" + procType
				+ ", procurementMedium=" + procurementMedium
				+ ", procurementQty=" + procurementQty + ", sourceOfFund="
				+ sourceOfFund + ", approvingAuth=" + approvingAuth
				+ ", estimatedCost=" + estimatedCost + ", reasonsOfProc="
				+ reasonsOfProc + ", procDescription=" + procDescription
				+ ", annexureNo=" + annexureNo + ", prepDocInvTender="
				+ prepDocInvTender + ", evaluationOfTender="
				+ evaluationOfTender + ", awardOfContract=" + awardOfContract
				+ ", tentativeCompletion=" + tentativeCompletion
				+ ", currentSessionBudget=" + currentSessionBudget
				+ ", nextSessionBudget=" + nextSessionBudget + ", remarks="
				+ remarks + ", confirm=" + confirm + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

	

}
