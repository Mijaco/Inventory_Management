package com.ibcs.desco.subStore.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class SSReturnSlipMstDtl {
	private Integer id;

	private Integer khathId;
	
	private String khathName;

	private String returnSlipNo;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date returnSlipDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date workOrderDate;

	private String workOrderNo;

	private String receiveFrom;

	private String stateCode;

	private String zone;

	private String justification;

	private Integer returnSlipMstId;

	private List<String> itemCode;

	private List<String> description;

	private List<String> uom;

	private List<Double> qtyNewServiceable;

	private List<Double> qtyRecServiceable;

	private List<Double> qtyUnServiceable;

	private List<Double> totalReturn;
	
	private List<String> remarks;

	private String stage;

	private String return_to;

	private String return_state;

	private String createdBy;
	
	private String userid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private boolean active = true;
	
	private String senderStore;
	
	private String returnTo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getReturnSlipNo() {
		return returnSlipNo;
	}

	public void setReturnSlipNo(String returnSlipNo) {
		this.returnSlipNo = returnSlipNo;
	}

	public Date getReturnSlipDate() {
		return returnSlipDate;
	}

	public void setReturnSlipDate(Date returnSlipDate) {
		this.returnSlipDate = returnSlipDate;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getReceiveFrom() {
		return receiveFrom;
	}

	public void setReceiveFrom(String receiveFrom) {
		this.receiveFrom = receiveFrom;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getReturnSlipMstId() {
		return returnSlipMstId;
	}

	public void setReturnSlipMstId(Integer returnSlipMstId) {
		this.returnSlipMstId = returnSlipMstId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getReturn_to() {
		return return_to;
	}

	public void setReturn_to(String return_to) {
		this.return_to = return_to;
	}

	public String getReturn_state() {
		return return_state;
	}

	public void setReturn_state(String return_state) {
		this.return_state = return_state;
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


	

	public List<Double> getQtyNewServiceable() {
		return qtyNewServiceable;
	}

	public void setQtyNewServiceable(List<Double> qtyNewServiceable) {
		this.qtyNewServiceable = qtyNewServiceable;
	}

	public List<Double> getQtyRecServiceable() {
		return qtyRecServiceable;
	}

	public void setQtyRecServiceable(List<Double> qtyRecServiceable) {
		this.qtyRecServiceable = qtyRecServiceable;
	}

	public List<Double> getQtyUnServiceable() {
		return qtyUnServiceable;
	}

	public void setQtyUnServiceable(List<Double> qtyUnServiceable) {
		this.qtyUnServiceable = qtyUnServiceable;
	}

	public List<Double> getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(List<Double> totalReturn) {
		this.totalReturn = totalReturn;
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

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getSenderStore() {
		return senderStore;
	}

	public void setSenderStore(String senderStore) {
		this.senderStore = senderStore;
	}

	public String getReturnTo() {
		return returnTo;
	}

	public void setReturnTo(String returnTo) {
		this.returnTo = returnTo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
