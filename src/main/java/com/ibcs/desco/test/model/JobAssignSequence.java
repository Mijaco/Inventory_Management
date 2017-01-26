package com.ibcs.desco.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "JOB_ASSIGN_TAB")
public class JobAssignSequence {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_ASSIGN_SEQ")
	@SequenceGenerator(name = "JOB_ASSIGN_SEQ", sequenceName = "JOB_ASSIGN_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;

	@Column(name = "name")
	private String name;

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

}
