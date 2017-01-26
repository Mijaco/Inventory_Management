package com.ibcs.desco.common.model;

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

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.procurement.model.ContractManagement;

@Entity
@Table(name = "CMS_APPRV_HIER_HIST")
public class CMSApprovalHierarchyHistory {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMS_HIER_HIST_ID_SEQ")
	@SequenceGenerator(name = "CMS_HIER_HIST_ID_SEQ", sequenceName = "CMS_HIER_HIST_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "operation_name", nullable = false)
	private String operationName;

	@ManyToOne
	@JoinColumn(name = "contract_management_id", nullable = false)
	private ContractManagement contractManagement;

	@Column(name = "stage")
	private String stage;

	@Column(name = "return_to")
	private String return_to;

	@Column(name = "return_state")
	private String return_state;

	@ManyToOne
	@JoinColumn(name = "auth_user_id")
	private AuthUser authUser;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Departments department;

	@Column(name = "state_code")
	private Integer stateCode;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "justification")
	private String justification;

	@Column(name = "status")
	private String status;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "approval_header")
	private String approvalHeader;

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

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ContractManagement getContractManagement() {
		return contractManagement;
	}

	public void setContractManagement(ContractManagement contractManagement) {
		this.contractManagement = contractManagement;
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

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getApprovalHeader() {
		return approvalHeader;
	}

	public void setApprovalHeader(String approvalHeader) {
		this.approvalHeader = approvalHeader;
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

	public CMSApprovalHierarchyHistory() {

	}

	public CMSApprovalHierarchyHistory(Integer id, String operationName,
			ContractManagement contractManagement, AuthUser authUser,
			Departments department, Integer stateCode, String stateName,
			String justification, String status, String remarks,
			String approvalHeader, String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.operationName = operationName;
		this.contractManagement = contractManagement;
		this.authUser = authUser;
		this.department = department;
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.justification = justification;
		this.status = status;
		this.remarks = remarks;
		this.approvalHeader = approvalHeader;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "AppPurchaseApprovalHierarchyHistory [id=" + id
				+ ", operationName=" + operationName + ", contractManagement="
				+ contractManagement + ", stage=" + stage + ", return_to="
				+ return_to + ", return_state=" + return_state + ", authUser="
				+ authUser + ", department=" + department + ", stateCode="
				+ stateCode + ", stateName=" + stateName + ", justification="
				+ justification + ", status=" + status + ", active=" + active
				+ ", remarks=" + remarks + ", approvalHeader=" + approvalHeader
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
