package com.ibcs.desco.procurement.bean;

import java.util.List;

public class ProcurementPackageMstDtl {

	private Integer id = 0;

	private Integer sessionId;

	private String packageName;

	private String packageType;
	
	private String qtyFlag;

	private String procMethod;

	private String procType;
	
	private String procurementMedium;
	
	private Double procurementQty;

	private String sourceOfFund;

	private String approvingAuth;

	private Double estimatedCost;

	private String reasonsOfProc;

	private String procDescription;

	private String annexureNo;

	private String prepDocInvTender;

	private String evaluationOfTender;

	private String awardOfContract;

	/*@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")*/
	private String tentativeCompletion;

	private Double currentSessionBudget;

	private Double nextSessionBudget;

	private String remarks;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> unit;

	private List<Double> qunatity;

	private List<Double> unitCost;

	private List<Double> totalCost;

	private Integer dtlId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
	}

	public List<String> getItemName() {
		return itemName;
	}

	public void setItemName(List<String> itemName) {
		this.itemName = itemName;
	}

	public List<String> getUnit() {
		return unit;
	}

	public void setUnit(List<String> unit) {
		this.unit = unit;
	}

	public List<Double> getQunatity() {
		return qunatity;
	}

	public void setQunatity(List<Double> qunatity) {
		this.qunatity = qunatity;
	}

	public List<Double> getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(List<Double> unitCost) {
		this.unitCost = unitCost;
	}

	public List<Double> getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(List<Double> totalCost) {
		this.totalCost = totalCost;
	}

	public Integer getDtlId() {
		return dtlId;
	}

	public void setDtlId(Integer dtlId) {
		this.dtlId = dtlId;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
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
	
	

}
