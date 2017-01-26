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

import com.ibcs.desco.admin.model.AuthUser;

@Entity
@Table(name = "PROC_PRCSS_COMMITTEE")
public class ProcurementProcessCommittee {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROC_COMMITTEE_ID_SEQ")
	@SequenceGenerator(name = "PROC_COMMITTEE_ID_SEQ", sequenceName = "PROC_COMMITTEE_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "auth_user_id", nullable = false)
	private AuthUser authUser;

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

	public AuthUser getAuthUser() {
		return authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
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

	public ProcurementProcessCommittee() {

	}

	public ProcurementProcessCommittee(Integer id, AuthUser authUser,
			boolean active, String remarks, String createdBy, Date createdDate) {
		super();
		this.id = id;
		this.authUser = authUser;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "ProcurementProcessCommittee [id=" + id + ", authUser="
				+ authUser + ", active=" + active + ", remarks=" + remarks
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
