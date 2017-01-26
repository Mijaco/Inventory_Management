package com.ibcs.desco.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.admin.dao.DepartmentsDao;
import com.ibcs.desco.admin.model.Departments;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {

	private DepartmentsDao departmentsDao;

	public DepartmentsDao getDepartmentsDao() {
		return departmentsDao;
	}

	public void setDepartmentsDao(DepartmentsDao departmentsDao) {
		this.departmentsDao = departmentsDao;
	}

	@Override
	public void addDepartments(Departments departments) {
		// TODO Auto-generated method stub
		departmentsDao.addDepartments(departments);
	}

	@Override
	public void editDepartments(Departments departments) {
		// TODO Auto-generated method stub
		departmentsDao.editDepartments(departments);
	}

	@Override
	public List<Departments> listDepartments() {
		// TODO Auto-generated method stub
		return departmentsDao.listDepartments();
	}

	@Override
	public Departments getDepartments(int id) {
		// TODO Auto-generated method stub
		return departmentsDao.getDepartments(id);
	}

	@Override
	public void deleteDepartments(Departments departments) {
		// TODO Auto-generated method stub
		departmentsDao.deleteDepartments(departments);
	}

	@Override
	public Departments getDepartmentByDeptId(String deptId) {
		// TODO Auto-generated method stub
		return departmentsDao.getDepartmentByDeptId(deptId);
	}

}
