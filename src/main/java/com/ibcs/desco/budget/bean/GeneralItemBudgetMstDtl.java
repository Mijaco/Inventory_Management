package com.ibcs.desco.budget.bean;

import java.util.List;


/*
 * 
 * Author: Shi!mul 
 * 
 */
public class GeneralItemBudgetMstDtl {
	private Integer id;
	private Integer sessionId;
	private String itemName;
	private String itemCode;
	private String uom;
	private Double rate;
	private Double mquantity;
	private Double amount;
	
	private List<String> costCentre;
	private List<Double> cost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionName) {
		this.sessionId = sessionName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getMquantity() {
		return mquantity;
	}
	public void setMquantity(Double mquantity) {
		this.mquantity = mquantity;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public List<String> getCostCentre() {
		return costCentre;
	}
	public void setCostCentre(List<String> costCentre) {
		this.costCentre = costCentre;
	}
	public List<Double> getCost() {
		return cost;
	}
	public void setCost(List<Double> cost) {
		this.cost = cost;
	}
}
