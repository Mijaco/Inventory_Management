package com.ibcs.desco.inventory.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.StoreRequisitionMst;

@Repository
public class StoreRequisitionMasterDAOImpl implements StoreRequisitionMasterDAO {

	private static final Logger logger = LoggerFactory.getLogger(StoreRequisitionMasterDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.persist(storeRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void removeStoreRequisitionMst(int storeRequisitionMstId) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.delete(storeRequisitionMstId);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	@Transactional
	public void editStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.update(storeRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	@Transactional
	public StoreRequisitionMst getStoreRequisitionMst(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();

			StoreRequisitionMst storeRequisitionMst = (StoreRequisitionMst) session.get(StoreRequisitionMst.class, id);
			System.out.println("dao impl " + storeRequisitionMst);
			return storeRequisitionMst;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new StoreRequisitionMst();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreRequisitionMst> getAllStoreRequisitionMst() {
		Session session = sessionFactory.getCurrentSession();
		List<StoreRequisitionMst> storeRequisitionMstList = session.createQuery("from StoreRequisitionMst").list();
		System.out.println("store requisition list ========= " + storeRequisitionMstList);
		return storeRequisitionMstList;
	}

	@Override
	public StoreRequisitionMst getStoreRequisitionMstBySRFNo(String srfNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.StoreRequisitionMst where srfNo = :srfNo");
			query.setString("srfNo", srfNo);
			// query.list();
			return (StoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreRequisitionMst> getStoreRequisitionMstListBySRFNo(String srfNo) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from com.ibcs.desco.inventory.model.StoreRequisitionMst where lower(srfNo) like lower(:srfNo)");

			query.setParameter("srfNo", "%" + srfNo + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public StoreRequisitionMst getStoreRequisitionMstLastRowFromTab() {
		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery(
					"from StoreRequisitionMst sreq where sreq.id = (select max(id) from StoreRequisitionMst)");

			return (StoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreRequisitionMst> getRequisitionMstListByStatus(String status) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.StoreRequisitionMst where status= :status");
			query.setString("status", status);
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
