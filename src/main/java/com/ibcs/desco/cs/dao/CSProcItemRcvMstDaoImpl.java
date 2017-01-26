package com.ibcs.desco.cs.dao;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSProcItemRcvMst;

@Repository
@Transactional
public class CSProcItemRcvMstDaoImpl implements CSProcItemRcvMstDao {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CSProcItemRcvMstDaoImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void removeCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(csProcItemRcvMst);
	}

	@Override
	public void editCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(csProcItemRcvMst);
	}

	@Override
	public CSProcItemRcvMst getCSProcItemRcvMst(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CSProcItemRcvMst) session.get(CSProcItemRcvMst.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSProcItemRcvMst> getAllCSProcItemRcvMst() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CSProcItemRcvMst>) session.createCriteria(
				CSProcItemRcvMst.class).list();
	}

	@Override
	public CSProcItemRcvMst getCSProcItemRcvMstByRrNo(String rrNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CSProcItemRcvMst where rrNo = :rrNo");
			query.setString("rrNo", rrNo);
			return (CSProcItemRcvMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@Override
	public void addCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(csProcItemRcvMst);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSProcItemRcvMst> listCSProcItemRcvMstByOperationIds(
			String  [] operationId) {
		Session session = sessionFactory.getCurrentSession();
		
		// TODO Auto-generated method stub
		try {
			//List<Integer> operation = Arrays.asList(operationId);
			String[] a=Arrays.toString(operationId).split("[\\[\\]]")[1].split(", "); 
			List<String> operation = Arrays.asList(a);
			/*Query query = session
					.createQuery("FROM CSProcItemRcvMst WHERE rrNo IN (:operationId)");
			query.setParameterList("operationId", operation);
			List<CSProcItemRcvMst> items = query.list();
			return items;*/
			
			List<CSProcItemRcvMst> items1 = (List<CSProcItemRcvMst>) session.createCriteria(CSProcItemRcvMst.class)
				     .add(Restrictions.in("rrNo", operation) )
				    .list();
			return items1;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
