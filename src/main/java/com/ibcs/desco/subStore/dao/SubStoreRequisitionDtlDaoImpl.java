package com.ibcs.desco.subStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

@Repository
@Transactional
public class SubStoreRequisitionDtlDaoImpl implements SubStoreRequisitionDtlDao {

	private static final Logger logger = LoggerFactory.getLogger(SubStoreRequisitionDtlDaoImpl.class);
	
	private SessionFactory sessionFactory;
	private SubStoreRequisitionMstDao subStoreRequisitionMstDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SubStoreRequisitionMstDao getSubStoreRequisitionMstDao() {
		return subStoreRequisitionMstDao;
	}

	public void setSubStoreRequisitionMstDao(SubStoreRequisitionMstDao subStoreRequisitionMstDao) {
		this.subStoreRequisitionMstDao = subStoreRequisitionMstDao;
	}

	

	

	@Override
	public void addSubStoreRequisition(SubStoreRequisitionDtl subStorRequisitionDtl) {

		System.out.println("in dao =================== " + subStorRequisitionDtl);
		Session session = sessionFactory.getCurrentSession();
		SubStoreRequisitionMst subStoreRequisitionMst = subStoreRequisitionMstDao
				.getSubStoreRequisitionMst(subStorRequisitionDtl.getSubStoreRequisitionId());

		subStorRequisitionDtl.setSubStoreRequisitionMst(subStoreRequisitionMst);
		System.out.println("==============" + subStoreRequisitionMst);
		session.saveOrUpdate(subStorRequisitionDtl);
		System.out.println("=======after save=======");
	}

	@SuppressWarnings("unused")
	@Override
	public void removeSubStoreRequisitionDetail(int id) {
		Session session = sessionFactory.getCurrentSession();
	}

	@Override
	public SubStoreRequisitionDtl getSubStoreRequisitionDtl(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();
			SubStoreRequisitionDtl subStoreRequisitionDtl = (SubStoreRequisitionDtl) session
					.get(SubStoreRequisitionDtl.class, id);
			return subStoreRequisitionDtl;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new SubStoreRequisitionDtl();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionDtl> getAllSubStoreRequisitionDtl(int subStoreRequisitionDtlId) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"from SubStoreRequisitionDtl where central_Store_Requisition_id=" + subStoreRequisitionDtlId)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionDtl> getSubStoreRequisitionDtlList(int mstId) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"from SubStoreRequisitionDtl where subStoreRequisitionMst.id=" + mstId)
				.list();
	}

}
