package com.ibcs.desco.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "PERMISSION_TABLE")
public class PermissionTable {

	@Id
	@Column(name = "p_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pt_id_Seq")
	@SequenceGenerator(name = "pt_id_Seq", sequenceName = "pt_id_Seq", allocationSize = 1, initialValue = 1)
	private Integer p_id;

	@Column(name = "role_id", nullable = false)
	private int role_id;

	@Transient
	private String roleName;

	@Column(name = "object_id", nullable = false)
	private int object_id;

	@Transient
	private String objectName;

	@Column(name = "p_read")
	private int p_read;

	@Column(name = "p_write")
	private int p_write;

	@Column(name = "p_edit")
	private int p_edit;

	@Column(name = "p_delete")
	private int p_delete;

	@Column(name = "classified")
	private int classified;

	@Column(name = "isActive")
	private boolean active = true;

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

	public int getClassified() {
		return classified;
	}

	public void setClassified(int classified) {
		this.classified = classified;
	}

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public int getP_delete() {
		return p_delete;
	}

	public void setP_delete(int p_delete) {
		this.p_delete = p_delete;
	}

	public int getP_edit() {
		return p_edit;
	}

	public void setP_edit(int p_edit) {
		this.p_edit = p_edit;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getP_read() {
		return p_read;
	}

	public void setP_read(int p_read) {
		this.p_read = p_read;
	}

	public int getP_write() {
		return p_write;
	}

	public void setP_write(int p_write) {
		this.p_write = p_write;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
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

	public PermissionTable(Integer p_id, int role_id, int object_id,
			int p_read, int p_write, int p_edit, int p_delete, int classified,
			boolean active, String remarks, String createdBy, Date createdDate) {
		super();
		this.p_id = p_id;
		this.role_id = role_id;
		this.object_id = object_id;
		this.p_read = p_read;
		this.p_write = p_write;
		this.p_edit = p_edit;
		this.p_delete = p_delete;
		this.classified = classified;
		this.active = active;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;		
	}

	public PermissionTable() {

	}

}
