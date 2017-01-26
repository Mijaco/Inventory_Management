package com.ibcs.desco.contractor.bean;

import java.util.List;

public class CostEstimationBean {

	private Integer id;

	private String pndNo;

	private String name;

	private String address;

	private String typeOfScheme;

	private Double serviceChargePercent;

	private Double serviceChargeAmount;

	private Double grandTotal;

	private boolean assign = false;

	private boolean approve = false;

	private String return_state;

	private String justification;

	private String stateCode;

	// added by Taleb
	private String userid;

	// for cost of materials

	private List<String> matItemCode;

	private List<String> matItemName;

	private List<String> matUom;

	private List<Double> matQuantity;

	private List<Double> matUnitPrice;

	private List<Double> matAmount;

	private List<String> matRemarks;

	// -----end

	// for cost of installation

	private List<String> insItemName;

	private List<String> insUom;

	private List<Double> insQuantity;

	private List<Double> insUnitPrice;

	private List<Double> insAmount;

	private List<String> insRemarks;

	// -----end

	// for cost of recovery

	private List<String> recItemName;

	private List<String> recUom;

	private List<Double> recQuantity;

	private List<Double> recUnitPrice;

	private List<Double> recAmount;

	private List<String> recRemarks;

	// -----end

	// for cost of miscellanious

	private List<String> misItemName;

	private List<String> misUom;

	private List<Double> misQuantity;

	private List<Double> misUnitPrice;

	private List<Double> misAmount;

	private List<String> misRemarks;

	// -----end

	private boolean active = true;

	private String remarks;

	private String createdBy;

	private String createdDate;

	private String modifiedBy;

	private String modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPndNo() {
		return pndNo;
	}

	public void setPndNo(String pndNo) {
		this.pndNo = pndNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getServiceChargePercent() {
		return serviceChargePercent;
	}

	public void setServiceChargePercent(Double serviceChargePercent) {
		this.serviceChargePercent = serviceChargePercent;
	}

	public Double getServiceChargeAmount() {
		return serviceChargeAmount;
	}

	public void setServiceChargeAmount(Double serviceChargeAmount) {
		this.serviceChargeAmount = serviceChargeAmount;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(String typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

	public boolean isAssign() {
		return assign;
	}

	public void setAssign(boolean assign) {
		this.assign = assign;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public String getReturn_state() {
		return return_state;
	}

	public void setReturn_state(String return_state) {
		this.return_state = return_state;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public List<String> getMatItemCode() {
		return matItemCode;
	}

	public void setMatItemCode(List<String> matItemCode) {
		this.matItemCode = matItemCode;
	}

	public List<String> getMatItemName() {
		return matItemName;
	}

	public void setMatItemName(List<String> matItemName) {
		this.matItemName = matItemName;
	}

	public List<String> getMatUom() {
		return matUom;
	}

	public void setMatUom(List<String> matUom) {
		this.matUom = matUom;
	}

	public List<Double> getMatQuantity() {
		return matQuantity;
	}

	public void setMatQuantity(List<Double> matQuantity) {
		this.matQuantity = matQuantity;
	}

	public List<Double> getMatUnitPrice() {
		return matUnitPrice;
	}

	public void setMatUnitPrice(List<Double> matUnitPrice) {
		this.matUnitPrice = matUnitPrice;
	}

	public List<Double> getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(List<Double> matAmount) {
		this.matAmount = matAmount;
	}

	public List<String> getMatRemarks() {
		return matRemarks;
	}

	public void setMatRemarks(List<String> matRemarks) {
		this.matRemarks = matRemarks;
	}

	public List<String> getInsItemName() {
		return insItemName;
	}

	public void setInsItemName(List<String> insItemName) {
		this.insItemName = insItemName;
	}

	public List<String> getInsUom() {
		return insUom;
	}

	public void setInsUom(List<String> insUom) {
		this.insUom = insUom;
	}

	public List<Double> getInsQuantity() {
		return insQuantity;
	}

	public void setInsQuantity(List<Double> insQuantity) {
		this.insQuantity = insQuantity;
	}

	public List<Double> getInsUnitPrice() {
		return insUnitPrice;
	}

	public void setInsUnitPrice(List<Double> insUnitPrice) {
		this.insUnitPrice = insUnitPrice;
	}

	public List<Double> getInsAmount() {
		return insAmount;
	}

	public void setInsAmount(List<Double> insAmount) {
		this.insAmount = insAmount;
	}

	public List<String> getInsRemarks() {
		return insRemarks;
	}

	public void setInsRemarks(List<String> insRemarks) {
		this.insRemarks = insRemarks;
	}

	public List<String> getRecItemName() {
		return recItemName;
	}

	public void setRecItemName(List<String> recItemName) {
		this.recItemName = recItemName;
	}

	public List<String> getRecUom() {
		return recUom;
	}

	public void setRecUom(List<String> recUom) {
		this.recUom = recUom;
	}

	public List<Double> getRecQuantity() {
		return recQuantity;
	}

	public void setRecQuantity(List<Double> recQuantity) {
		this.recQuantity = recQuantity;
	}

	public List<Double> getRecUnitPrice() {
		return recUnitPrice;
	}

	public void setRecUnitPrice(List<Double> recUnitPrice) {
		this.recUnitPrice = recUnitPrice;
	}

	public List<Double> getRecAmount() {
		return recAmount;
	}

	public void setRecAmount(List<Double> recAmount) {
		this.recAmount = recAmount;
	}

	public List<String> getRecRemarks() {
		return recRemarks;
	}

	public void setRecRemarks(List<String> recRemarks) {
		this.recRemarks = recRemarks;
	}

	public List<String> getMisItemName() {
		return misItemName;
	}

	public void setMisItemName(List<String> misItemName) {
		this.misItemName = misItemName;
	}

	public List<String> getMisUom() {
		return misUom;
	}

	public void setMisUom(List<String> misUom) {
		this.misUom = misUom;
	}

	public List<Double> getMisQuantity() {
		return misQuantity;
	}

	public void setMisQuantity(List<Double> misQuantity) {
		this.misQuantity = misQuantity;
	}

	public List<Double> getMisUnitPrice() {
		return misUnitPrice;
	}

	public void setMisUnitPrice(List<Double> misUnitPrice) {
		this.misUnitPrice = misUnitPrice;
	}

	public List<Double> getMisAmount() {
		return misAmount;
	}

	public void setMisAmount(List<Double> misAmount) {
		this.misAmount = misAmount;
	}

	public List<String> getMisRemarks() {
		return misRemarks;
	}

	public void setMisRemarks(List<String> misRemarks) {
		this.misRemarks = misRemarks;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	// added by taleb
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
