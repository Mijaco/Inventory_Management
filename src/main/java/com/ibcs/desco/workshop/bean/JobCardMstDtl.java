package com.ibcs.desco.workshop.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class JobCardMstDtl {
	private int id = 0;

	private List<String> jobNoIdList;

	private String typeOfWork;

	private String receivedBy;

	private String jobCardNo;

	private String contractNo;

	private String transformerType;

	private String transformerSerialNo;

	private String manufacturedName;

	private String manufacturedYear;

	private int transformerRegisterId;
	
	private boolean closeOut=false;

	private boolean active = true;

	private List<String> remarks;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private List<Integer> jobDtlIdList;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> unit;

	private List<Double> quantityUsed;

	private List<Double> quantityRecovery;
	
	private List<Double> quantityIssue;

	// for back to user
	private String stage;

	private String return_to;

	private String return_state;

	private String justification;

	private String stateCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public String getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(String transformerType) {
		this.transformerType = transformerType;
	}

	public String getTransformerSerialNo() {
		return transformerSerialNo;
	}

	public void setTransformerSerialNo(String transformerSerialNo) {
		this.transformerSerialNo = transformerSerialNo;
	}

	public String getManufacturedName() {
		return manufacturedName;
	}

	public void setManufacturedName(String manufacturedName) {
		this.manufacturedName = manufacturedName;
	}

	public String getManufacturedYear() {
		return manufacturedYear;
	}

	public void setManufacturedYear(String manufacturedYear) {
		this.manufacturedYear = manufacturedYear;
	}

	public int getTransformerRegisterId() {
		return transformerRegisterId;
	}

	public void setTransformerRegisterId(int transformerRegisterId) {
		this.transformerRegisterId = transformerRegisterId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public List<Double> getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(List<Double> quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public List<Double> getQuantityRecovery() {
		return quantityRecovery;
	}

	public void setQuantityRecovery(List<Double> quantityRecovery) {
		this.quantityRecovery = quantityRecovery;
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

	public List<Integer> getJobDtlIdList() {
		return jobDtlIdList;
	}

	public void setJobDtlIdList(List<Integer> jobDtlIdList) {
		this.jobDtlIdList = jobDtlIdList;
	}

	public List<String> getJobNoIdList() {
		return jobNoIdList;
	}

	public void setJobNoIdList(List<String> jobNoIdList) {
		this.jobNoIdList = jobNoIdList;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public List<Double> getQuantityIssue() {
		return quantityIssue;
	}

	public void setQuantityIssue(List<Double> quantityIssue) {
		this.quantityIssue = quantityIssue;
	}


	public boolean isCloseOut() {
		return closeOut;
	}

	public void setCloseOut(boolean closeOut) {
		this.closeOut = closeOut;
	}
}
