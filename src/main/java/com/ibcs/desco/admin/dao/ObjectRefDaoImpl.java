package com.ibcs.desco.admin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.ObjectReference;

@Repository
public class ObjectRefDaoImpl implements ObjectRefDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ObjectReference> listObjects() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<ObjectReference>) session.createCriteria(
				ObjectReference.class).list();
	}

	@Override
	@Transactional
	public ObjectReference getObjectReference(int id){
		Session session = this.sessionFactory.getCurrentSession();
		return (ObjectReference)session.get(ObjectReference.class, id);
	}

}
