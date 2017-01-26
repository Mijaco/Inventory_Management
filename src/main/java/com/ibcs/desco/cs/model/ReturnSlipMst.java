package com.ibcs.desco.cs.model;

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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cs_return_slip_mst")
public class ReturnSlipMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs_rtn_slip_mst_seq")
	@SequenceGenerator(name = "cs_rtn_slip_mst_seq", sequenceName = "cs_rtn_slip_mst_seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "return_slip_no")
	private String returnSlipNo;

	@Column(name = "sender_dept_name")
	private String senderDeptName;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "return_slip_date")
	//@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnSlipDate;

	@Column(name = "work_order_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date workOrderDate;

	@Column(name = "work_order_no")
	private String workOrderNo;

	@Column(name = "others")
	private String others;

	@Column(name = "received_from")
	private String receiveFrom;

	@Column(name = "zone")
	private String zone;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "is_active")
	private boolean active = true;

	@Column(name = "sender_store")
	private String senderStore;

	@Column(name = "return_to")
	private String returnTo;

	@Column(name = "snd_code")
	private String sndCode;

	@Column(name = "isApproved")
	private boolean approved = false;

	@Column(name = "isReturned")
	private boolean returned = false;

	@Transient
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@Transient
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReturnSlipNo() {
		return returnSlipNo;
	}

	public void setReturnSlipNo(String returnSlipNo) {
		this.returnSlipNo = returnSlipNo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getSndCode() {
		return sndCode;
	}

	public void setSndCode(String sndCode) {
		this.sndCode = sndCode;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

}
