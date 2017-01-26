package com.ibcs.desco.cs.bean;

import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class StoreTicketMstDtl {
	private Integer id;

	private int ticketNo;

	private String gtPassNo;

	private String storeTicketType;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date gtpassDate;

	private String descoFrmNo;

	private String requisitonNo;

	private String issuedTo;

	private String issuedFor;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date issuedDate;

	private String woNo;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date woDate;

	private String returnBy;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date returnDate;

	private String returnSlipNo;

	private String returnFor;

	private String returnedStatus;

	private List<String> itemCodeNo;

	private List<String> description;

	private List<String> uom;

	private List<Integer> quantity;

	private List<Integer> totalCost;

	private List<String> ledgerNo;

	private List<Integer> pageNo;

	private List<String> workNew;

	private List<String> maintanance;

	private List<String> remarks;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private boolean active = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getGtPassNo() {
		return gtPassNo;
	}

	public void setGtPassNo(String gtPassNo) {
		this.gtPassNo = gtPassNo;
	}

	public Date getGtpassDate() {
		return gtpassDate;
	}

	public void setGtpassDate(Date gtpassDate) {
		this.gtpassDate = gtpassDate;
	}

	public String getDescoFrmNo() {
		return descoFrmNo;
	}

	public void setDescoFrmNo(String descoFrmNo) {
		this.descoFrmNo = descoFrmNo;
	}

	public String getRequisitonNo() {
		return requisitonNo;
	}

	public void setRequisitonNo(String requisitonNo) {
		this.requisitonNo = requisitonNo;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}

	public String getIssuedFor() {
		return issuedFor;
	}

	public void setIssuedFor(String issuedFor) {
		this.issuedFor = issuedFor;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getWoNo() {
		return woNo;
	}

	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}

	public Date getWoDate() {
		return woDate;
	}

	public void setWoDate(Date woDate) {
		this.woDate = woDate;
	}

	public String getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnSlipNo() {
		return returnSlipNo;
	}

	public void setReturnSlipNo(String returnSlipNo) {
		this.returnSlipNo = returnSlipNo;
	}

	public String getReturnFor() {
		return returnFor;
	}

	public void setReturnFor(String returnFor) {
		this.returnFor = returnFor;
	}

	public String getReturnedStatus() {
		return returnedStatus;
	}

	public void setReturnedStatus(String returnedStatus) {
		this.returnedStatus = returnedStatus;
	}

	public List<String> getItemCodeNo() {
		return itemCodeNo;
	}

	public void setItemCodeNo(List<String> itemCodeNo) {
		this.itemCodeNo = itemCodeNo;
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

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}

	public List<Integer> getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(List<Integer> totalCost) {
		this.totalCost = totalCost;
	}

	public List<String> getLedgerNo() {
		return ledgerNo;
	}

	public void setLedgerNo(List<String> ledgerNo) {
		this.ledgerNo = ledgerNo;
	}

	public List<Integer> getPageNo() {
		return pageNo;
	}

	public void setPageNo(List<Integer> pageNo) {
		this.pageNo = pageNo;
	}

	public List<String> getWorkNew() {
		return workNew;
	}

	public void setWorkNew(List<String> workNew) {
		this.workNew = workNew;
	}

	public List<String> getMaintanance() {
		return maintanance;
	}

	public void setMaintanance(List<String> maintanance) {
		this.maintanance = maintanance;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStoreTicketType() {
		return storeTicketType;
	}

	public void setStoreTicketType(String storeTicketType) {
		this.storeTicketType = storeTicketType;
	}

}
