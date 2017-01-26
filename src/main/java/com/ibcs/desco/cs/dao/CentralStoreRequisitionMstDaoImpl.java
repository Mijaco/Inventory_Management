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

import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;

@Transactional
@Repository
public class CentralStoreRequisitionMstDaoImpl implements
		CentralStoreRequisitionMstDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CentralStoreRequisitionMstDaoImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCentralStoreRequisitionMst(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.persist(centralStoreRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void removeCentralStoreRequisitionMst(
			int centralStoreRequisitionMstId) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.delete(centralStoreRequisitionMstId);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void editCentralStoreRequisitionMst(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.update(centralStoreRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequisitionMst(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();

			CentralStoreRequisitionMst centralStoreRequisitionMst = (CentralStoreRequisitionMst) session
					.get(CentralStoreRequisitionMst.class, id);
			System.out.println("dao impl " + centralStoreRequisitionMst);
			return centralStoreRequisitionMst;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CentralStoreRequisitionMst();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreRequisitionMst> getAllCentralStoreRequisitionMst() {
		Session session = sessionFactory.getCurrentSession();
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = session
				.createQuery("from CentralStoreRequisitionMst").list();
		return centralStoreRequisitionMstList;
	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequistionMstByRequisitionNo(
			String requisitionNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CentralStoreRequisitionMst where requisitionNo = :requisitionNo");
			query.setString("requisitionNo", requisitionNo);
			// query.list();
			return (CentralStoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByRequisitionNo(
			String requisitionNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CentralStoreRequisitionMst where lower(requisitionNo) like lower(:requisitionNo)");

			query.setParameter("requisitionNo", "%" + requisitionNo + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequisitionMstLastRowFromTab() {
		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session
					.createQuery("from CentralStoreRequisitionMst csreq where csreq.id = (select max(id) from CentralStoreRequisitionMst)");

			return (CentralStoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByStatus(
			String status) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CentralStoreRequisitionMst where status= :status");
			query.setString("status", status);
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreRequisitionMst> listCentralStoreRequisitionMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<CentralStoreRequisitionMst> items1 = (List<CentralStoreRequisitionMst>) session
					.createCriteria(CentralStoreRequisitionMst.class)
					.add(Restrictions.in("requisitionNo", operation)).list();
			return items1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
