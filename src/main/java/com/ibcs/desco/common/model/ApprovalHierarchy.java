package com.ibcs.desco.common.model;

import java.util.Date;
import java.util.List;

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

import com.ibcs.desco.admin.model.AuthUser;

@Entity
@Table(name = "APPROVAL_HIERARCHY")
public class ApprovalHierarchy {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_hierercy_id_Seq")
	@SequenceGenerator(name = "app_hierercy_id_Seq", sequenceName = "app_hierercy_id_Seq", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "operation_name", nullable = false)
	private String operationName;

	@Column(name = "state_name", nullable = false)
	private String stateName;

	@Column(name = "button_name")
	private String buttonName;

	@Column(name = "approval_header")
	private String approvalHeader;

	@Column(name = "state_code", nullable = false)
	private int stateCode;

	@Column(name = "role_name", nullable = false)
	private String roleName;

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

	// added by Taleb for new hierarchy start
	@Transient
	private List<AuthUser> authUser;

	/*@Column(name = "range_indicator")
	private String rangeIndicator = "1";

	@Column(name = "boundary_value")
	private String boundaryValue;*/

	// added by Taleb for new hierarchy end

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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getApprovalHeader() {
		return approvalHeader;
	}

	public void setApprovalHeader(String approvalHeader) {
		this.approvalHeader = approvalHeader;
	}

	// added by taleb for new hierarchy start
	public List<AuthUser> getAuthUser() {
		return authUser;
	}

	public void setAuthUser(List<AuthUser> authUser) {
		this.authUser = authUser;
	}

	/*public String getRangeIndicator() {
		return rangeIndicator;
	}

	public void setRangeIndicator(String rangeIndicator) {
		this.rangeIndicator = rangeIndicator;
	}

	public String getBoundaryValue() {
		return boundaryValue;
	}

	public void setBoundaryValue(String boundaryValue) {
		this.boundaryValue = boundaryValue;
	}*/
	// added by taleb for new hierarchy end
}
