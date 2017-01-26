package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

@Repository
@Transactional
public class CSStoreTicketDtlDaoImpl implements CSStoreTicketDtlDao {

	private SessionFactory sessionFactory;

	private CSStoreTicketMstDao csStoreTicketMstDao;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CSStoreTicketMstDao getCsStoreTicketMstDao() {
		return csStoreTicketMstDao;
	}

	public void setCsStoreTicketMstDao(CSStoreTicketMstDao csStoreTicketMstDao) {
		this.csStoreTicketMstDao = csStoreTicketMstDao;
	}

	@Override
	public void addCSStoreTicketDtl(CSStoreTicketDtl csCSStoreTicketDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		CSStoreTicketMst csStoreTicketMst = csStoreTicketMstDao.getCSStoreTicketMstByTicketNo(csCSStoreTicketDtl.getTicketNo());
		csCSStoreTicketDtl.setCsStoreTicketMst(csStoreTicketMst);
		session.save(csCSStoreTicketDtl);
	}

	@Override
	public void addSSStoreTicketDtl(SSStoreTicketDtl csCSStoreTicketDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		SSStoreTicketMst csStoreTicketMst = csStoreTicketMstDao.getSSStoreTicketMstByTicketNo(csCSStoreTicketDtl.getTicketNo());
		csCSStoreTicketDtl.setSsStoreTicketMst(csStoreTicketMst);
		session.save(csCSStoreTicketDtl);
	}

	@Override
	public void removeCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(csStoreTicketDtl);
	}

	@Override
	public void editCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(csStoreTicketDtl);
	}

	@Override
	public CSStoreTicketDtl getCSStoreTicketDtl(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CSStoreTicketDtl) session.get(CSStoreTicketDtl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketDtl> getAllCSStoreTicketDtl() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CSStoreTicketDtl>)session.createCriteria(CSStoreTicketDtl.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketDtl> getCSStoreTicketDtlByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CSStoreTicketDtl where ticketNo = :ticketNo");
			query.setString("ticketNo", ticketNo);
			return (List<CSStoreTicketDtl>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
