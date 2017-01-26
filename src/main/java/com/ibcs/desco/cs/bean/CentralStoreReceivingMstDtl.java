package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CentralStoreReceivingMstDtl {

	private int id;

	private String rrNo;

	private String storeSection;

	private String supplier;

	private String workOrder;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date workDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateOfDelivery;

	private String challanNo;

	private String billOfLanding;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date invoiceDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date landingDate;

	private List<String> itemName;

	private List<String> partNo;

	private List<String> description;

	private List<String> unit;

	private List<Integer> quantity;

	private List<Double> cost;

	private List<Integer> number;

	private List<String> remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRrNo() {
		return rrNo;
	}

	public void setRrNo(String rrNo) {
		this.rrNo = rrNo;
	}

	public String getStoreSection() {
		return storeSection;
	}

	public void setStoreSection(String storeSection) {
		this.storeSection = storeSection;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Date getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(Date dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getBillOfLanding() {
		return billOfLanding;
	}

	public void setBillOfLanding(String billOfLanding) {
		this.billOfLanding = billOfLanding;
	}

	public Date getLandingDate() {
		return landingDate;
	}

	public void setLandingDate(Date landingDate) {
		this.landingDate = landingDate;
	}


	public List<String> getItemName() {
		return itemName;
	}

	public void setItemName(List<String> itemName) {
		this.itemName = itemName;
	}

	public List<String> getPartNo() {
		return partNo;
	}

	public void setPartNo(List<String> partNo) {
		this.partNo = partNo;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getUnit() {
		return unit;
	}

	public void setUnit(List<String> unit) {
		this.unit = unit;
	}

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}

	public List<Double> getCost() {
		return cost;
	}

	public void setCost(List<Double> cost) {
		this.cost = cost;
	}

	public List<Integer> getNumber() {
		return number;
	}

	public void setNumber(List<Integer> number) {
		this.number = number;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Override
	public String toString() {
		return "CentralStoreReceivingMstDtl [id=" + id + ", rrNo=" + rrNo + ", storeSection=" + storeSection
				+ ", supplier=" + supplier + ", workOrder=" + workOrder + ", workDate=" + workDate + ", dateOfDelivery="
				+ dateOfDelivery + ", challanNo=" + challanNo + ", billOfLanding=" + billOfLanding + ", invoiceDate="
				+ invoiceDate + ", landingDate=" + landingDate + ", slNo=" + itemName + ", partNo=" + partNo
				+ ", description=" + description + ", unit=" + unit + ", quantity=" + quantity + ", cost=" + cost
				+ ", number=" + number + ", remarks=" + remarks + "]";
	}

}
