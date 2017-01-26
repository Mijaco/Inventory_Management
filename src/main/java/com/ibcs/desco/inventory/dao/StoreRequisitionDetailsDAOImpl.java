package com.ibcs.desco.inventory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.StoreRequisionDetail1;
import com.ibcs.desco.inventory.model.StoreRequisiotionDetail2;
import com.ibcs.desco.inventory.model.StoreRequisitionMst;

@Repository
public class StoreRequisitionDetailsDAOImpl implements StoreRequisitionDetailsDAO {

	private static final Logger logger = LoggerFactory.getLogger(StoreRequisitionDetailsDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	private StoreRequisitionMasterDAO storeRequisitionMasterDAO;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addStoreRequisitionDetail(StoreRequisionDetail1 storeRequisitionDtl1) {
		Session session = sessionFactory.getCurrentSession();
		StoreRequisitionMst storeRequisitionMst = storeRequisitionMasterDAO
				.getStoreRequisitionMst(storeRequisitionDtl1.getRequisitionId());
		storeRequisitionDtl1.setStoreRequisitionMst(storeRequisitionMst);
		session.save(storeRequisitionDtl1);

	}

	@SuppressWarnings("unused")
	@Override
	public void removeStoreRequisitionDetail(int id) {
		Session session = sessionFactory.getCurrentSession();

	}

	@Override
	public void editStoreRequisitionDetail(StoreRequisiotionDetail2 storeRequisitionDtl2) {
		Session session = sessionFactory.getCurrentSession();
		// session.update(requisitionDtl);
		session.saveOrUpdate(storeRequisitionDtl2);
	}

	@Override
	public StoreRequisionDetail1 getRequisitionDetail(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();
			StoreRequisionDetail1 storeRequisitionDtl1 = (StoreRequisionDetail1) session
					.get(StoreRequisionDetail1.class, id);
			return storeRequisitionDtl1;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new StoreRequisionDetail1();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreRequisionDetail1> getAllStoreRequisitionDetail(int storeRequisitionMstId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from StoreRequisionDetail1 where requisition_id=" + storeRequisitionMstId).list();
	}

}
