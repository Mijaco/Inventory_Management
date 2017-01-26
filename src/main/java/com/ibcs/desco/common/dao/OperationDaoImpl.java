package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.Operation;

@Repository
@Transactional
public class OperationDaoImpl implements OperationDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addOperation(Operation operation) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(operation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> listOperations() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<Operation>) session.createCriteria(Operation.class).list();
	}

	@Override
	public Operation getOperation(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (Operation) session.get(Operation.class, id);
	}

	@Override
	public void deleteOperation(Operation operation) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(operation);
	}

}
