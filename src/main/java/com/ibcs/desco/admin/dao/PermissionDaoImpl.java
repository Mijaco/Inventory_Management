package com.ibcs.desco.admin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.PermissionTable;

@Repository
public class PermissionDaoImpl implements PermissionDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void addPermission(PermissionTable permission) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(permission);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PermissionTable> listPermissions() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<PermissionTable>) session.createCriteria(
				PermissionTable.class).list();
	}

	@Override
	@Transactional
	public PermissionTable getPermissions(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (PermissionTable) session.get(PermissionTable.class, id);
	}

	@Override
	@Transactional
	public void deletePermission(PermissionTable permission) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(permission);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	@Transactional
	public List<PermissionTable> listPermissionByRoleId(int roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		String q = "select p.p_id,  p.role_id,  p.object_id, p.p_read, p.p_write,  p.p_edit,  p.p_delete, o.display_name, r.role from com.ibcs.desco.admin.model.PermissionTable p, com.ibcs.desco.admin.model.ObjectReference o, com.ibcs.desco.admin.model.Roles r where p.object_id = o.id and p.role_id= :roleid and p.role_id = r.role_id";
		String q1 = "from com.ibcs.desco.admin.model.PermissionTable where role_id = :roleid";
		Query query = session.createQuery(q1);
		try {
			int i = Integer.parseInt(roleId + "");
			query.setInteger("roleid", i);
		} catch (NumberFormatException ex) { // handle your exception
			ex.printStackTrace();
		}

		List<PermissionTable> modifications = query.list();

		return modifications;
	}

}
