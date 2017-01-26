package com.ibcs.desco.contractor.model;

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


@Entity
@Table(name = "construction_nature")
public class ConstructionNature {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractor_id_seq")
	@SequenceGenerator(name = "contractor_id_seq", sequenceName = "contractor_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "isActive")
	private boolean active = true;
	
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

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
	
	//Default Constructor
	public ConstructionNature() { }
	
	//For inserting Construction Nature
	public ConstructionNature(Integer id, String name, String remarks, boolean active, String createdBy,
			Date createdDate) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	
	//For updating Construction Nature
	public ConstructionNature(String name, String remarks, String modifiedBy, Date modifiedDate) {
		super();
		this.name = name;
		this.remarks = remarks;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
}
