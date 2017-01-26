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

import org.springframework.format.annotation.DateTimeFormat;

	@Entity
	@Table(name = "WS_RCV_PREVENTIVE_MST")
	public class WsReceivePreventiveMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ws_rcv_preventive_mst_id_seq")
	@SequenceGenerator(name = "ws_rcv_preventive_mst_id_seq", sequenceName = "ws_rcv_preventive_mst_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "WO_NUMBER")
	private String woNumber;

	@Column(name = "WORK_ORDER_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date workOrderDate;
	
	@Column(name = "NOTE_NUMBER")
	private String noteNumber;
	
	@Column(name = "REF_NUMBER")
	private String referenceNumber;

	@Column(name = "REFERENCE_DOC")
	private String referenceDoc;

	@Column(name = "SENDER_DEPT_ID")
	private String senderDeptId;

	@Column(name = "SENDER_DEPT_NAME")
	private String senderDeptName;

	@Column(name = "RCV_DEPT_NAME")
	private String rcvDeptName;

	@Column(name = "RECEIVED_FROM")
	private String receiveFrom;

	@Column(name = "RECEIVED_DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date receiveDate;

	@Column(name = "ZONE")
	private String zone;

	@Column(name = "SENDER_STORE")
	private String senderStore;

	@Column(name = "SND_CODE")
	private String sndCode;
	
	@Column(name = "IS_ACTIVE")
	private boolean active = true;
	
	@Column(name = "IS_APPROVE")
	private boolean approve = false;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWoNumber() {
		return woNumber;
	}

	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public String getNoteNumber() {
		return noteNumber;
	}

	public void setNoteNumber(String noteNumber) {
		this.noteNumber = noteNumber;
	}

	public String getSenderDeptId() {
		return senderDeptId;
	}

	public void setSenderDeptId(String senderDeptId) {
		this.senderDeptId = senderDeptId;
	}

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

	public String getRcvDeptName() {
		return rcvDeptName;
	}

	public void setRcvDeptName(String rcvDeptName) {
		this.rcvDeptName = rcvDeptName;
	}

	public String getReceiveFrom() {
		return receiveFrom;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getReferenceDoc() {
		return referenceDoc;
	}

	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public String getSenderStore() {
		return senderStore;
	}

	public void setSenderStore(String senderStore) {
		this.senderStore = senderStore;
	}

	public String getSndCode() {
		return sndCode;
	}

	public void setSndCode(String sndCode) {
		this.sndCode = sndCode;
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

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}


}
