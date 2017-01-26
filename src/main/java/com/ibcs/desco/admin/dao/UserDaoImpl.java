package com.ibcs.desco.admin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.AuthUser;

@Repository
public class UserDaoImpl implements UserDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void addAuthUser(AuthUser authUser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(authUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AuthUser> listAuthUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<AuthUser>) session.createCriteria(AuthUser.class).list();
	}

	@Override
	@Transactional
	public AuthUser getAuthUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (AuthUser) session.get(AuthUser.class, id);
	}

	@Override
	@Transactional
	public void deleteAuthUser(AuthUser authUser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(authUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AuthUser> listAuthUsersByRoleId(int roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from com.ibcs.desco.admin.model.AuthUser where roleid = :roleid");
		query.setInteger("roleid", roleId);

		List<AuthUser> modifications = null;

		try {
			modifications = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modifications;
	}

	@Override
	@Transactional
	public AuthUser getAuthUserByUserId(String userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from com.ibcs.desco.admin.model.AuthUser where userid = :userid");
		query.setString("userid", userId);
		AuthUser authUser = null;

		try {
			authUser = (AuthUser) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return authUser;
	}

}
