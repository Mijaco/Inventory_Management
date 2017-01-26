package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSProcItemRcvMst;

@Repository
@Transactional
public class CSProcItemRcvDtlDaoImpl implements CSProcItemRcvDtlDao {

	private CSProcItemRcvMstDao csProcItemRcvMstDao;

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CSProcItemRcvMstDao getCsProcItemRcvMstDao() {
		return csProcItemRcvMstDao;
	}

	public void setCsProcItemRcvMstDao(CSProcItemRcvMstDao csProcItemRcvMstDao) {
		this.csProcItemRcvMstDao = csProcItemRcvMstDao;
	}

	@Override
	public void addCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		CSProcItemRcvMst csProcItemRcvMst = csProcItemRcvMstDao
				.getCSProcItemRcvMstByRrNo(csProcItemRcvDtl
						.getReceivedReportNo());
		csProcItemRcvDtl.setCsProcItemRcvMst(csProcItemRcvMst);
		session.save(csProcItemRcvDtl);
	}

	@Override
	public void removeCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(csProcItemRcvDtl);
	}

	@Override
	public void editCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(csProcItemRcvDtl);
	}

	@Override
	public CSProcItemRcvDtl getCSProcItemRcvDtl(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CSProcItemRcvDtl) session.get(CSProcItemRcvDtl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSProcItemRcvDtl> getAllCSProcItemRcvDtl() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CSProcItemRcvDtl>) session.createCriteria(
				CSProcItemRcvDtl.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSProcItemRcvDtl> getCSProcItemRcvDtlByRrNo(String rrNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CSProcItemRcvDtl where rrNo = :rrNo");
			query.setString("rrNo", rrNo);
			return (List<CSProcItemRcvDtl>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	public CSProcItemRcvDtl getCSProcItemRcvDtlByContratNo(String contractNo, String itemId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSProcItemRcvDtl dtl where dtl.itemId = :itemId and csProcItemRcvMst.contractNo = :contractNo order by csProcItemRcvMst.id desc");
			query.setString("contractNo", contractNo);
			query.setString("itemId", itemId);
			return (CSProcItemRcvDtl) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
