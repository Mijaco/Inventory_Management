package com.ibcs.desco.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PROC_DEMAND_NOTE_TAB")
public class ProcDemandNoteSequence {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROC_DEMAND_NOTE_SEQ")
	@SequenceGenerator(name = "PROC_DEMAND_NOTE_SEQ", sequenceName = "PROC_DEMAND_NOTE_SEQ", allocationSize = 1, initialValue = 1)
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
