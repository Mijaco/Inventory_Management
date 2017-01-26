package com.ibcs.desco.acl.dao;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibcs.desco.acl.model.AclUIHolder;
import com.ibcs.desco.acl.model.AuthUser;
import com.ibcs.desco.acl.model.Menu;
import com.ibcs.desco.acl.model.ObjectReference;
import com.ibcs.desco.acl.model.PermissionTable;
import com.ibcs.desco.acl.model.Roles;
import com.ibcs.desco.acl.model.User;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */

public class DBAction {

	@SuppressWarnings("unused")
	private Object ob;

	@SuppressWarnings("unused")
	private Session session;

	// session=HibernateUtil.getSessionFactory().getCurrentSession();
	public DBAction() {
		if (HibernateUtil.getSessionFactory() != null) {
			this.session = HibernateUtil.getSessionFactory()
					.getCurrentSession();
		}
	}

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void add(Object o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.save(o);

		session.getTransaction().commit();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean checkIfExists(Class cls, String field, String val) {
		Boolean bval = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(cls);
		criteria.add(Restrictions.eq(field, val));
		List<Object> l = criteria.list();
		if (l.size() < 1) {
			bval = false;
		} else
			bval = true;
		return bval;
	}

	@SuppressWarnings("unchecked")
	public List<Object> NongetAdminMenu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Menu.class);
		criteria.add(Restrictions.eq("is_admin", "0"));
		List<Object> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings("rawtypes")
	public int login(String user, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("from Acount a where a.userid ='"
				+ user + "'");
		List users = query.list();
		int i = users.size();

		session.close();

		// session.getTransaction().commit();

		return i;
	}

	@SuppressWarnings("rawtypes")
	public Object getById(int id, Class cls) throws ClassNotFoundException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Object o = (Object) session.get(cls, id);
		// session.close();
		return o;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getAllTest(String cls) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("from " + cls + " a");
		List<Object> l = query.list();
		return l;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> getAll(Class cls) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(cls).addOrder(
				Order.desc("id"));
		criteria.setFetchSize(100);
		// session.getTransaction().commit();
		List<Object> l = criteria.list();

		session.close();
		return l;
	}

	@SuppressWarnings("rawtypes")
	public void delete(int id, Class cls) throws ClassNotFoundException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Object s = getById(id, cls);

		session.delete(s);
		session.getTransaction().commit();
		// session.clear();
		// session.close();
	}

	public void update(Object o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.merge(o);
		session.getTransaction().commit();
		// session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Object> GeneralSql(String sql_query)

	{

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createSQLQuery(sql_query);

		List<Object> l = query.list();
		session.close();
		return l;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> search(String id, Class cls) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(cls);
		criteria.add(Restrictions.eq("sourceId", id));
		List<Object> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Object> Login(String id, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("userid", id)).add(
				Restrictions.eq("pass", pass));
		List<Object> l = criteria.list();
		session.close();

		return l;

	}

	@SuppressWarnings("unchecked")
	public List<Roles> getRoleId(String role) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Roles.class);
		criteria.add(Restrictions.eq("role", role));
		List<Roles> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public int getRoleIdByName(String rolename) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Roles.class);
		criteria.add(Restrictions.eq("role", rolename));
		List<Roles> l = criteria.list();
		int roleId = l.get(0).getId();
		session.close();
		return roleId;
	}
	
	@SuppressWarnings("unchecked")
	public Roles getRoleObjectByRoleName(String rolename) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Roles.class);
		criteria.add(Restrictions.eq("role", rolename));
		List<Roles> roles = criteria.list();
		Roles role = null;
		if(roles.size()>0){
			role = roles.get(0);
		}		
		session.close();
		return role;
	}

	@SuppressWarnings("unchecked")
	public List<ObjectReference> getObjectId(String object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(ObjectReference.class);
		criteria.add(Restrictions.eq("class_name", object));
		List<ObjectReference> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getChildMenu(String parent) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Menu.class);
		criteria.add(Restrictions.eq("parent_menu", parent));
		List<Menu> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<PermissionTable> getPermission(int roleId, int objId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from PermissionTable a where a.role_id ="
						+ roleId + "AND a.object_id =" + objId);

		// System.out.println("eror-->"+query.toString());

		List<PermissionTable> l = query.list();

		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<PermissionTable> getPermissionByRoleName(String roleName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// int roleId = this.getRoleIdByName(roleName);

		// Query query = session
		// .createQuery("select p_read, p_write, p_delete, p_edit from permission_table a where a.role_id = (select role_id from roles where role="+roleName+")");
		Query query = session
				.createSQLQuery("select getObject(OBJECT_ID),getRole(ROLE_ID),P_READ,P_WRITE,P_EDIT,P_DELETE  from permission_table where role_id=(select role_id from roles where role = '"
						+ roleName + "')");

		// System.out.println("eror-->"+query.toString());

		List<PermissionTable> l = query.list();

		session.close();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<AuthUser> searchUser(String id) {
		// int idd=Integer.parseInt(id);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(AuthUser.class);
		criteria.add(Restrictions.eq("userid", id));
		List<AuthUser> l = criteria.list();
		session.close();
		return l;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public List test() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(AclUIHolder.class);

		Query query = session
				.createSQLQuery("select getObject(OBJECT_ID),getRole(ROLE_ID),P_READ,P_WRITE,P_EDIT,P_DELETE  from permission_table");

		List<Object> l = query.list();
		System.out.println(l.get(1).toString().toString());
		// AclUIHolder a=(AclUIHolder) l.get(1);
		session.close();
		return l;

	}

	@SuppressWarnings({ "unused", "unchecked" })
	public String RoleName(int id) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(AclUIHolder.class);

		/*
		 * Query query = session.createSQLQuery("select getRole(" + id +
		 * ") from dual");
		 */
		Query query = session.createSQLQuery(
				"select role from Roles where role_id = :role_id")
				.setParameter("role_id", id);

		List<String> l = query.list();
		session.close();
		return l.toString();

	}

	@SuppressWarnings("unchecked")
	public String ObjectName(int id) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createSQLQuery("select getObject(" + id
				+ ") from dual");

		List<String> l = query.list();
		session.close();
		return l.toString();

	}

	@SuppressWarnings({ "unused", "unchecked" })
	public List<AuthUser> EditUser(String uname) {
		int i = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(AuthUser.class);
		criteria.add(Restrictions.eq("userid", uname));
		List<AuthUser> l = criteria.list();
		session.close();
		return l;
	}
}
