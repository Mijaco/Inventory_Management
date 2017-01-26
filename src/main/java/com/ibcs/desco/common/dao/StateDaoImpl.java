package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.State;

@Repository
@Transactional
public class StateDaoImpl implements StateDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addState(State state) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(state);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> listStates() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<State>) session.createCriteria(State.class).list();
	}

	@Override
	public State getState(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (State) session.get(State.class, id);
	}

	@Override
	public void deleteState(State state) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(state);
	}

}
