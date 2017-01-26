package com.ibcs.desco.admin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.Roles;
@Repository
public class RoleDaoImpl implements RoleDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void addRoles(Roles roles) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(roles);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Roles> listRoles() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<Roles>) session.createCriteria(Roles.class).list();
	}

	@Override
	@Transactional
	public Roles getRoles(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Roles) session.get(Roles.class, id);
	}

	@Override
	@Transactional
	public void deleteRoles(Roles roles) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(roles);
	}

}
