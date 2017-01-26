package com.ibcs.desco.admin.dao;

import java.util.List;

import com.ibcs.desco.admin.model.Departments;


public interface DepartmentsDao {

	// data access for add new Departments
	public void addDepartments(Departments departments);

	// data access for edit Departments
	public void editDepartments(Departments departments);

	// data access for get all Departments as List
	public List<Departments> listDepartments();

	// data access for get specific one Departments information and update
	// Departments info
	public Departments getDepartments(int id);

	// data access for Delete an Departments
	public void deleteDepartments(Departments departments);
	
	public Departments getDepartmentByDeptId(String deptId);
}
