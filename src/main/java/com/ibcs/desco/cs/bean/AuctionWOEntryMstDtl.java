package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class AuctionWOEntryMstDtl {
	
	private String workOrderNo;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractDate;

	private String supplierName;
	
	private String condemnMstId;

	private Integer khathId;

	private boolean psi = false;

	private boolean pli = false;	
	
	private String auctionName;

	private List<String> pk;

	private List<String> itemId;

	private List<String> description;

	private List<String> uom;

	private List<Double> itemQty;

	private List<Double> remainingQty;

	private List<Double> cost;

	private List<String> remarks;

	private boolean active = true;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;
	
	/*Materials Delivery Form Purpose*/
	private String itemCode;
	private List<String> khathIdList;
	private List<Double> deliveryQtyList;
	private String uuid;

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public boolean isPsi() {
		return psi;
	}

	public void setPsi(boolean psi) {
		this.psi = psi;
	}

	public boolean isPli() {
		return pli;
	}

	public void setPli(boolean pli) {
		this.pli = pli;
	}

	public List<String> getItemId() {
		return itemId;
	}

	public void setItemId(List<String> itemId) {
		this.itemId = itemId;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getUom() {
		return uom;
	}

	public void setUom(List<String> uom) {
		this.uom = uom;
	}

	public List<Double> getItemQty() {
		return itemQty;
	}

	public void setItemQty(List<Double> itemQty) {
		this.itemQty = itemQty;
	}

	public List<Double> getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(List<Double> remainingQty) {
		this.remainingQty = remainingQty;
	}

	public List<Double> getCost() {
		return cost;
	}

	public void setCost(List<Double> cost) {
		this.cost = cost;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public List<String> getPk() {
		return pk;
	}

	public void setPk(List<String> pk) {
		this.pk = pk;
	}

	public String getAuctionName() {
		return auctionName;
	}

	public void setAuctionName(String auctionName) {
		this.auctionName = auctionName;
	}

	public String getCondemnMstId() {
		return condemnMstId;
	}

	public void setCondemnMstId(String condemnMstId) {
		this.condemnMstId = condemnMstId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public List<String> getKhathIdList() {
		return khathIdList;
	}

	public void setKhathIdList(List<String> khathIdList) {
		this.khathIdList = khathIdList;
	}

	public List<Double> getDeliveryQtyList() {
		return deliveryQtyList;
	}

	public void setDeliveryQtyList(List<Double> deliveryQtyList) {
		this.deliveryQtyList = deliveryQtyList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
