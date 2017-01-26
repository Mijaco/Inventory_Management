package com.ibcs.desco.admin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.Departments;

@Repository
@Transactional
public class DepartmentsDaoImpl implements DepartmentsDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addDepartments(Departments departments) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(departments);
	}

	@Override
	public void editDepartments(Departments departments) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(departments);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Departments> listDepartments() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<Departments>) session.createCriteria(Departments.class)
				.list();
	}

	@Override
	public Departments getDepartments(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (Departments) session.get(Departments.class, id);
	}

	@Override
	public void deleteDepartments(Departments departments) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(departments);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Departments getDepartmentByDeptId(String deptId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from com.ibcs.desco.admin.model.Departments where deptId = :deptId");
		query.setString("deptId", deptId);

		List<Departments> departmentList = (List<Departments>) query.list();

		Departments department = new Departments();
		try {
			if (departmentList.size() > 0) {
				department = departmentList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}

}
