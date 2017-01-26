package com.ibcs.desco.contractor.model;

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

@Entity
@Table(name = "COST_ESTIMATION_MST")
public class CostEstimationMst {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cost_estimate_mst_id_seq")
	@SequenceGenerator(name = "cost_estimate_mst_id_seq", sequenceName = "cost_estimate_mst_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "PND_NO")
	private String pndNo;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "TYOPE_OF_SCHEME")
	private String typeOfScheme;

	@Column(name = "SERVICE_CHARGE_PERCENT")
	private Double serviceChargePercent;

	@Column(name = "SERVICE_CHARGE_AMOUNT")
	private Double serviceChargeAmount;

	@Column(name = "GRAND_TOTAL")
	private Double grandTotal;

	@Column(name = "IS_ASSIGN")
	private boolean assign = false;

	@Column(name = "IS_APPROVE")
	private boolean approve = false;

	@Column(name = "IS_ACTIVE")
	private boolean active = true;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "IS_CONFIRMED")
	private String confirmed;

	@Column(name = "IS_CONFIRMED_BY")
	private String confirmedBy;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name = "department_pk_id", nullable = true)
	private Departments department;

	@Column(name = "role_name", nullable = true)
	private String roleName;

	@ManyToOne
	@JoinColumn(name = "auth_user_pk_id", nullable = true)
	private AuthUser authUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getServiceChargePercent() {
		return serviceChargePercent;
	}

	public void setServiceChargePercent(Double serviceChargePercent) {
		this.serviceChargePercent = serviceChargePercent;
	}

	public Double getServiceChargeAmount() {
		return serviceChargeAmount;
	}

	public void setServiceChargeAmount(Double serviceChargeAmount) {
		this.serviceChargeAmount = serviceChargeAmount;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
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

	public String getPndNo() {
		return pndNo;
	}

	public void setPndNo(String pndNo) {
		this.pndNo = pndNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(String typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

	public boolean isAssign() {
		return assign;
	}

	public void setAssign(boolean assign) {
		this.assign = assign;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getConfirmedBy() {
		return confirmedBy;
	}

	public void setConfirmedBy(String confirmedBy) {
		this.confirmedBy = confirmedBy;
	}

}
