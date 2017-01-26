package com.ibcs.desco.cs.bean;

import java.util.List;

public class KhathTransferMstDtl {

	private int id;

	private int khathIdFrom;
	
	private int khathIdTo;

	private String ledgerBook;

	private String transferApprovalDoc;

	private String uuid;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> uom;

	private List<Double> transferedQty;

	private List<Double> unitCost;
	
	private List<Double> totalCost;

	private List<String> remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKhathIdFrom() {
		return khathIdFrom;
	}

	public void setKhathIdFrom(int khathIdFrom) {
		this.khathIdFrom = khathIdFrom;
	}

	public int getKhathIdTo() {
		return khathIdTo;
	}

	public void setKhathIdTo(int khathIdTo) {
		this.khathIdTo = khathIdTo;
	}

	public String getLedgerBook() {
		return ledgerBook;
	}

	public void setLedgerBook(String ledgerBook) {
		this.ledgerBook = ledgerBook;
	}

	

	public String getTransferApprovalDoc() {
		return transferApprovalDoc;
	}

	public void setTransferApprovalDoc(String transferApprovalDoc) {
		this.transferApprovalDoc = transferApprovalDoc;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public List<String> getUom() {
		return uom;
	}

	public void setUom(List<String> uom) {
		this.uom = uom;
	}
	
	public List<Double> getTransferedQty() {
		return transferedQty;
	}

	public void setTransferedQty(List<Double> transferedQty) {
		this.transferedQty = transferedQty;
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

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	

}
