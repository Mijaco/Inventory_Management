package com.ibcs.desco.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOM_SEQUENCE")
public class CustomSequence {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_seq")
	@SequenceGenerator(name = "custom_id_seq", sequenceName = "custom_id_seq", allocationSize = 1, initialValue = 1)	
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "max_id", nullable=false)
	private Integer maxId;
	
	@Column(name = "serial", unique=true, nullable=false)
	private String serial;

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	
	
}
