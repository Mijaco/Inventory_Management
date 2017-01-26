package com.ibcs.desco.localStore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.ibcs.desco.contractor.model.ContractorRepresentive;

@Entity
@Table(name = "LS_REQUISITION_MST")
public class LocalStoreRequisitionMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ls_requisition_mst_id_seq")
	@SequenceGenerator(name = "ls_requisition_mst_id_seq", sequenceName = "ls_requisition_mst_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "requisition_No")
	private String requisitionNo;

	@Column(name = "Store_Ticket_NO")
	private String storeTicketNO;

	@Column(name = "khath_id")
	private Integer khathId;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "khath_name")
	private String khathName;

	@Column(name = "dept_name")
	private String deptName;

	@Column(name = "Received_By")
	private String receivedBy;

	@Column(name = "requisition_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date requisitionDate;

	@Column(name = "identer_Designation")
	private String identerDesignation;

	@Column(name = "gate_Pass_No")
	private String gatePassNo;

	@Column(name = "gate_Pass_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date gatePassDate;

	@Column(name = "isReceived")
	private boolean received = false;

	@Column(name = "isApproved")
	private boolean approved = false;

	@Column(name = "isActive")
	private boolean active = true;

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

	@Column(name = "sender_store")
	private String senderStore;

	@Column(name = "requisition_to")
	private String requisitionTo;

	@Column(name = "requested_dept_id")
	private Integer requestedDeptId;

	@Column(name = "store_prefix")
	private String storePrefix;
	
	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	private ContractorRepresentive contractorRepresentive;

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

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getStoreTicketNO() {
		return storeTicketNO;
	}

	public void setStoreTicketNO(String storeTicketNO) {
		this.storeTicketNO = storeTicketNO;
	}

	public Integer getKhathId() {
		return khathId;
	}

	public void setKhathId(Integer khathId) {
		this.khathId = khathId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getKhathName() {
		return khathName;
	}

	public void setKhathName(String khathName) {
		this.khathName = khathName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public Date getRequisitionDate() {
		return requisitionDate;
	}

	public void setRequisitionDate(Date requisitionDate) {
		this.requisitionDate = requisitionDate;
	}

	public String getIdenterDesignation() {
		return identerDesignation;
	}

	public void setIdenterDesignation(String identerDesignation) {
		this.identerDesignation = identerDesignation;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	public String getSenderStore() {
		return senderStore;
	}

	public void setSenderStore(String senderStore) {
		this.senderStore = senderStore;
	}

	public String getRequisitionTo() {
		return requisitionTo;
	}

	public void setRequisitionTo(String requisitionTo) {
		this.requisitionTo = requisitionTo;
	}

	public String getStorePrefix() {
		return storePrefix;
	}

	public void setStorePrefix(String storePrefix) {
		this.storePrefix = storePrefix;
	}

	public Integer getRequestedDeptId() {
		return requestedDeptId;
	}

	public void setRequestedDeptId(Integer requestedDeptId) {
		this.requestedDeptId = requestedDeptId;
	}

	public ContractorRepresentive getContractorRepresentive() {
		return contractorRepresentive;
	}

	public void setContractorRepresentive(
			ContractorRepresentive contractorRepresentive) {
		this.contractorRepresentive = contractorRepresentive;
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

}
