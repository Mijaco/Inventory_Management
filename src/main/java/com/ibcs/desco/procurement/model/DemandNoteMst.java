package com.ibcs.desco.procurement.model;

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

import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;

@Entity
@Table(name = "DEMAND_NOTE_MST")
public class DemandNoteMst {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMAND_NOTE_MST_ID_SEQ")
	@SequenceGenerator(name = "DEMAND_NOTE_MST_ID_SEQ", sequenceName = "DEMAND_NOTE_MST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@ManyToOne
	@JoinColumn(name = "DEPARTMENTS")
	private Departments department;

	@Column(name = "SENDER_NAME")
	private String senderName;
	
	@Transient
	private String messages;
	
	@Transient
	private String departmentId;
	
	@Transient
	private String itemCategoryCode;

	@Column(name = "demand_note_no")
	private String demandNoteNo;

	@Column(name = "reference_doc")
	private String referenceDoc;

	@Column(name = "annexure_type")
	private String annexureType;
	
	@Column(name = "is_confirm")
	private String confirm;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private DescoSession descoSession;

	@Column(name = "DEMAND_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date demandDate;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getAnnexureType() {
		return annexureType;
	}

	public void setAnnexureType(String annexureType) {
		this.annexureType = annexureType;
	}

	public Date getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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

	public String getDemandNoteNo() {
		return demandNoteNo;
	}

	public void setDemandNoteNo(String demandNoteNo) {
		this.demandNoteNo = demandNoteNo;
	}

	public String getReferenceDoc() {
		return referenceDoc;
	}

	public void setReferenceDoc(String referenceDoc) {
		this.referenceDoc = referenceDoc;
	}

	public DemandNoteMst() {

	}
	

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public DescoSession getDescoSession() {
		return descoSession;
	}

	public void setDescoSession(DescoSession descoSession) {
		this.descoSession = descoSession;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public DemandNoteMst(Integer id, Departments department, String senderName,
			String demandNoteNo, String referenceDoc, String annexureType,
			Date demandDate, boolean approved, boolean active, String remarks,
			String createdBy, Date createdDate, DescoSession descoSession) {
		super();
		this.id = id;
		this.department = department;
		this.senderName = senderName;
		this.demandNoteNo = demandNoteNo;
		this.referenceDoc = referenceDoc;
		this.annexureType = annexureType;
		this.demandDate = demandDate;
		this.approved = approved;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.descoSession = descoSession;
		
	}

	
	
}
