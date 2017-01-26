package com.ibcs.desco.common.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class LocalRRMstDtl {
	private String contractNo;
	private String invoiceNo;
	private String supplierName;
	private String referenceDoc;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date contractDate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date invoiceDate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date deliveryDate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date pliDate;
	
	private List<String> itemCode;
	private List<String> description;
	private List<String> uom;
	private List<String> purchasedQty;
	private List<String> requiredQty;
	private List<String> ledgerBook;
	private List<String> pageNo;
	private List<String> remarks;
	
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getReferenceDoc() {
		return referenceDoc;
	}
	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
	}

	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getPliDate() {
		return pliDate;
	}
	public void setPliDate(Date pliDate) {
		this.pliDate = pliDate;
	}
	public List<String> getItemCode() {
		return itemCode;
	}
	public void setItemCode(List<String> itemCode) {
		this.itemCode = itemCode;
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
	public List<String> getPurchasedQty() {
		return purchasedQty;
	}
	public void setPurchasedQty(List<String> purchasedQty) {
		this.purchasedQty = purchasedQty;
	}
	public List<String> getRequiredQty() {
		return requiredQty;
	}
	public void setRequiredQty(List<String> requiredQty) {
		this.requiredQty = requiredQty;
	}
	public List<String> getLedgerBook() {
		return ledgerBook;
	}
	public void setLedgerBook(List<String> ledgerBook) {
		this.ledgerBook = ledgerBook;
	}
	public List<String> getPageNo() {
		return pageNo;
	}
	public void setPageNo(List<String> pageNo) {
		this.pageNo = pageNo;
	}
	public List<String> getRemarks() {
		return remarks;
	}
	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}
}
