package com.ibcs.desco.cs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONT_DEPT_REF_TAB")
public class ContractorDepartmentReference {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_dept_ref_id_seq")
	@SequenceGenerator(name = "cont_dept_ref_id_seq", sequenceName = "cont_dept_ref_id_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "contractor_id")
	private Integer contractorId;

	@Column(name = "department_id")
	private Integer deptId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractorId() {
		return contractorId;
	}

	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
