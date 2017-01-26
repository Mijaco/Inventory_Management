package com.ibcs.desco.budget.bean;

import java.util.List;


/*
 * 
 * Author: Galeb
 * 
 */
public class BudgetMstDto {
	
	private String descoSession;

	private String projectFundSrc;
	
	private String budgetStatus;	

	private String budgetTypeId;
	
	private String id;	
	
	private List<String> itemId;
	
	private List<String> lookUpId;
	
	private List<String> itemCode;
	
	private List<String> budgetDtlId;
	
	private List<String> costSource;
	
	private List<Double> qty;

	private List<Double> rate;

	public String getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(String descoSession) {
		this.descoSession = descoSession;
	}

	public String getProjectFundSrc() {
		return projectFundSrc;
	}

	public void setProjectFundSrc(String projectFundSrc) {
		this.projectFundSrc = projectFundSrc;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	

	public String getBudgetTypeId() {
		return budgetTypeId;
	}

	public void setBudgetTypeId(String budgetTypeId) {
		this.budgetTypeId = budgetTypeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getItemId() {
		return itemId;
	}

	public void setItemId(List<String> itemId) {
		this.itemId = itemId;
	}
	
	

	public List<String> getLookUpId() {
		return lookUpId;
	}

	public void setLookUpId(List<String> lookUpId) {
		this.lookUpId = lookUpId;
	}

	public List<String> getCostSource() {
		return costSource;
	}

	public void setCostSource(List<String> costSource) {
		this.costSource = costSource;
	}

	public List<String> getBudgetDtlId() {
		return budgetDtlId;
	}

	public void setBudgetDtlId(List<String> budgetDtlId) {
		this.budgetDtlId = budgetDtlId;
	}

	public List<Double> getQty() {
		return qty;
	}

	public void setQty(List<Double> qty) {
		this.qty = qty;
	}

	public List<Double> getRate() {
		return rate;
	}

	public void setRate(List<Double> rate) {
		this.rate = rate;
	}
	
	

	public List<String> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "BudgetMstDto [descoSession=" + descoSession
				+ ", projectFundSrc=" + projectFundSrc + ", budgetStatus="
				+ budgetStatus + ", budgetType=" + budgetTypeId + ", id=" + id
				+ ", itemId=" + itemId + ", itemCode=" + itemCode
				+ ", budgetDtlId=" + budgetDtlId + ", costSource=" + costSource
				+ ", qty=" + qty + ", rate=" + rate + "]";
	}

	

	
	

	

	
	
	
	
}
