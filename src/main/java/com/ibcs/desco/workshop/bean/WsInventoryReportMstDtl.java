package com.ibcs.desco.workshop.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class WsInventoryReportMstDtl {
	private int id = 0;

	private String typeOfWork;

	private String receivedBy;

	private String inventoryNo;
	
	private String inventoryDate;

	private String contractNo;

	private String transformerType;
	
	private String kvaRating;

	private String transformerSerialNo;

	private String manufacturedName;

	private String manufacturedYear;

	private String receivedFrom;
	
	private String receivedDate;
	
	private String lastRepairDate;
//----------------------------
	private String meggerResultHtLt;

	private String meggerResultHtE;

	private String meggerResultLtE;

	private String meggerResultLtLtAb;

	private String meggerResultLtLtBc;

	private String meggerResultLtLtCa;

	private String meggerResultHtHtAb;

	private String meggerResultHtHtBc;

	private String meggerResultHtHtCa;

	private String isPaintingRequired;

	private int transformerRegisterId;

	private boolean active = true;

	private List<String> remarks;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private List<Integer> inventoryDtlIdList;

	private List<String> itemCode;

	private List<String> itemName;

	private List<String> unit;
	
	private List<Double> standardQuantity;

	private List<Double> useableQuantity;

	private List<Double> unUseableQuantity;

	private List<Double> notFound;

	private List<Double> totalFound;

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

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getLastRepairDate() {
		return lastRepairDate;
	}

	public void setLastRepairDate(String lastRepairDate) {
		this.lastRepairDate = lastRepairDate;
	}

	public String getMeggerResultHtLt() {
		return meggerResultHtLt;
	}

	public void setMeggerResultHtLt(String meggerResultHtLt) {
		this.meggerResultHtLt = meggerResultHtLt;
	}

	public String getMeggerResultHtE() {
		return meggerResultHtE;
	}

	public void setMeggerResultHtE(String meggerResultHtE) {
		this.meggerResultHtE = meggerResultHtE;
	}

	public String getMeggerResultLtE() {
		return meggerResultLtE;
	}

	public void setMeggerResultLtE(String meggerResultLtE) {
		this.meggerResultLtE = meggerResultLtE;
	}

	public String getMeggerResultLtLtAb() {
		return meggerResultLtLtAb;
	}

	public void setMeggerResultLtLtAb(String meggerResultLtLtAb) {
		this.meggerResultLtLtAb = meggerResultLtLtAb;
	}

	public String getMeggerResultLtLtBc() {
		return meggerResultLtLtBc;
	}

	public void setMeggerResultLtLtBc(String meggerResultLtLtBc) {
		this.meggerResultLtLtBc = meggerResultLtLtBc;
	}

	public String getMeggerResultLtLtCa() {
		return meggerResultLtLtCa;
	}

	public void setMeggerResultLtLtCa(String meggerResultLtLtCa) {
		this.meggerResultLtLtCa = meggerResultLtLtCa;
	}

	public String getMeggerResultHtHtAb() {
		return meggerResultHtHtAb;
	}

	public void setMeggerResultHtHtAb(String meggerResultHtHtAb) {
		this.meggerResultHtHtAb = meggerResultHtHtAb;
	}

	public String getMeggerResultHtHtBc() {
		return meggerResultHtHtBc;
	}

	public void setMeggerResultHtHtBc(String meggerResultHtHtBc) {
		this.meggerResultHtHtBc = meggerResultHtHtBc;
	}

	public String getMeggerResultHtHtCa() {
		return meggerResultHtHtCa;
	}

	public void setMeggerResultHtHtCa(String meggerResultHtHtCa) {
		this.meggerResultHtHtCa = meggerResultHtHtCa;
	}

	public String getIsPaintingRequired() {
		return isPaintingRequired;
	}

	public void setIsPaintingRequired(String isPaintingRequired) {
		this.isPaintingRequired = isPaintingRequired;
	}

	public List<Integer> getInventoryDtlIdList() {
		return inventoryDtlIdList;
	}

	public void setInventoryDtlIdList(List<Integer> inventoryDtlIdList) {
		this.inventoryDtlIdList = inventoryDtlIdList;
	}

	public List<Double> getStandardQuantity() {
		return standardQuantity;
	}

	public void setStandardQuantity(List<Double> standardQuantity) {
		this.standardQuantity = standardQuantity;
	}

	public List<Double> getUseableQuantity() {
		return useableQuantity;
	}

	public void setUseableQuantity(List<Double> useableQuantity) {
		this.useableQuantity = useableQuantity;
	}

	public List<Double> getUnUseableQuantity() {
		return unUseableQuantity;
	}

	public void setUnUseableQuantity(List<Double> unUseableQuantity) {
		this.unUseableQuantity = unUseableQuantity;
	}

	public List<Double> getNotFound() {
		return notFound;
	}

	public void setNotFound(List<Double> notFound) {
		this.notFound = notFound;
	}

	public List<Double> getTotalFound() {
		return totalFound;
	}

	public void setTotalFound(List<Double> totalFound) {
		this.totalFound = totalFound;
	}

	public String getKvaRating() {
		return kvaRating;
	}

	public void setKvaRating(String kvaRating) {
		this.kvaRating = kvaRating;
	}

}
