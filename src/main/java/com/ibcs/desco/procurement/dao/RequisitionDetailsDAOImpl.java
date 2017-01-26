package com.ibcs.desco.procurement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.procurement.model.RequisitionDtl;
import com.ibcs.desco.procurement.model.RequisitionDtl2;
import com.ibcs.desco.procurement.model.RequisitionMst;

@Repository
public class RequisitionDetailsDAOImpl implements RequisitionDetailsDAO{
	
	private static final Logger logger = LoggerFactory
			.getLogger(RequisitionDetailsDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	private RequisitionMasterDAO requisitionMasterDAO;

	
	

	public RequisitionMasterDAO getRequisitionMasterDAO() {
		return requisitionMasterDAO;
	}

	public void setRequisitionMasterDAO(RequisitionMasterDAO requisitionMasterDAO) {
		this.requisitionMasterDAO = requisitionMasterDAO;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addRequisitionDetail(RequisitionDtl requisitionDtl) {
		Session session = sessionFactory.getCurrentSession();
		RequisitionMst requisitionMst = requisitionMasterDAO.getRequisitionMst(requisitionDtl.getRequisitionId());
		requisitionDtl.setRequisitionMst(requisitionMst);	
		session.save(requisitionDtl);

		
		
	}

	@SuppressWarnings("unused")
	@Override
	public void removeRequisitionDetail(int id) {
		Session session = sessionFactory.getCurrentSession();
		
	}

	@Override
	public void editRequisitionDetail(RequisitionDtl2 requisitionDtl) {
		Session session = sessionFactory.getCurrentSession();
		//session.update(requisitionDtl);
		session.saveOrUpdate(requisitionDtl);
	}

	@Override
	public RequisitionDtl getRequisitionDetail(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();
			//RequisitionMst requisitionMst = (RequisitionMst) session.load(RequisitionMst.class, id);
			RequisitionDtl requisitionDtl = (RequisitionDtl) session.get(RequisitionDtl.class, id);
			return requisitionDtl;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RequisitionDtl();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionDtl> getAllRequisitionDetail(int RequisitionMstId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from RequisitionDtl where requisition_id=" + RequisitionMstId).list();
	}

}
