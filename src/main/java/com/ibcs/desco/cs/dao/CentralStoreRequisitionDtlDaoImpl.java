package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;

@Transactional
@Repository
public class CentralStoreRequisitionDtlDaoImpl implements CentralStoreRequisitionDtlDao {

	private static final Logger logger = LoggerFactory.getLogger(CentralStoreRequisitionDtlDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	private CentralStoreRequisitionMstDao centralStoreRequisitionMstDao;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCentralStoreRequisition(CentralStoreRequisitionDtl centralStorRequisitionDtl) {

		System.out.println("in dao =================== " + centralStorRequisitionDtl);
		Session session = sessionFactory.getCurrentSession();
		CentralStoreRequisitionMst centralStoreRequisitionMst = centralStoreRequisitionMstDao
				.getCentralStoreRequisitionMst(centralStorRequisitionDtl.getCentralStoreRequisitionId());

		centralStorRequisitionDtl.setCentralStoreRequisitionMst(centralStoreRequisitionMst);
		System.out.println("==============" + centralStoreRequisitionMst);
		session.saveOrUpdate(centralStorRequisitionDtl);
		System.out.println("=======after save=======");
	}

	@SuppressWarnings("unused")
	@Override
	public void removeCentralStoreRequisitionDetail(int id) {
		Session session = sessionFactory.getCurrentSession();
	}

	@Override
	public CentralStoreRequisitionDtl getCentralStoreRequisitionDtl(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();
			CentralStoreRequisitionDtl centralStoreRequisitionDtl = (CentralStoreRequisitionDtl) session
					.get(CentralStoreRequisitionDtl.class, id);
			return centralStoreRequisitionDtl;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CentralStoreRequisitionDtl();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreRequisitionDtl> getAllCentralStoreRequisitionDtl(int centralStoreRequisitionDtlId) {
		Session session = sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"from CentralStoreRequisitionDtl where central_Store_Requisition_id=" + centralStoreRequisitionDtlId)
				.list();
	}

}
