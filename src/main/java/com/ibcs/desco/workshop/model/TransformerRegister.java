package com.ibcs.desco.workshop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TRANSFORMER_REGISTER")
public class TransformerRegister {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSFORMER_REG_ID_SEQ")
	@SequenceGenerator(name = "TRANSFORMER_REG_ID_SEQ", sequenceName = "TRANSFORMER_REG_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "transformer_type")
	private String transformerType;
	
	@Column(name = "type_of_work")
	private String typeOfWork;
	
	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "transformer_serial_no")
	private String transformerSerialNo;

	@Column(name = "manufactured_name")
	private String manufacturedName;

	@Column(name = "manufactured_year")
	private String manufacturedYear;

	@Column(name = "kva_rating")
	private String kvaRating;

	@Column(name = "received_date")
	private Date receivedDate;

	@Column(name = "rcv_dept_code")
	private String rcvDeptCode;

	@Column(name = "rcv_dept_name")
	private String rcvDeptName;

	@Column(name = "req_no")
	private String reqNo;

	@Column(name = "ticket_no")
	private String ticketNo;

	@Column(name = "test_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date testDate;
	
	@Transient
	private Date previousRepairDate;

	@Column(name = "job_no")
	private String jobNo;

	@Column(name = "return_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;

	@Column(name = "return_slip_no")
	private String returnSlipNo;

	@Column(name = "return_ticket_no")
	private String returnTicketNo;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "isCloseOut")
	private boolean closeOut = false;

	@Column(name = "bill_no")
	private String billNo;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransformerSerialNo() {
		return transformerSerialNo;
	}

	public void setTransformerSerialNo(String transformerSerialNo) {
		this.transformerSerialNo = transformerSerialNo;
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

	public String getTypeOfWork() {
		return typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getManufacturedYear() {
		return manufacturedYear;
	}

	public void setManufacturedYear(String manufacturedYear) {
		this.manufacturedYear = manufacturedYear;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getManufacturedName() {
		return manufacturedName;
	}

	public void setManufacturedName(String manufacturedName) {
		this.manufacturedName = manufacturedName;
	}

	public String getKvaRating() {
		return kvaRating;
	}

	public void setKvaRating(String kvaRating) {
		this.kvaRating = kvaRating;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public Date getPreviousRepairDate() {
		return previousRepairDate;
	}

	public void setPreviousRepairDate(Date previousRepairDate) {
		this.previousRepairDate = previousRepairDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getRcvDeptCode() {
		return rcvDeptCode;
	}

	public void setRcvDeptCode(String rcvDeptCode) {
		this.rcvDeptCode = rcvDeptCode;
	}

	public String getRcvDeptName() {
		return rcvDeptName;
	}

	public void setRcvDeptName(String rcvDeptName) {
		this.rcvDeptName = rcvDeptName;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getReturnSlipNo() {
		return returnSlipNo;
	}

	public void setReturnSlipNo(String returnSlipNo) {
		this.returnSlipNo = returnSlipNo;
	}

	public String getReturnTicketNo() {
		return returnTicketNo;
	}

	public void setReturnTicketNo(String returnTicketNo) {
		this.returnTicketNo = returnTicketNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public boolean isCloseOut() {
		return closeOut;
	}

	public void setCloseOut(boolean closeOut) {
		this.closeOut = closeOut;
	}

}
