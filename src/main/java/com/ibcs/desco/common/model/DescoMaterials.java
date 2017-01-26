package com.ibcs.desco.common.model;

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
@Table(name = "desco_materials_List")
public class DescoMaterials {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "desco_materials_id_seq")
	@SequenceGenerator(name = "desco_materials_id_seq", sequenceName = "desco_materials_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "item_no")
	private String itemSlNo;
	
	@Column(name = "item_code")
	private String itemCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "uom")
	private String uom;
	
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
	
	
}
