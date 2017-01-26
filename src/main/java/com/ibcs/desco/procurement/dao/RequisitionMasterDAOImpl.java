package com.ibcs.desco.procurement.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.procurement.model.RequisitionMst;

@Repository
public class RequisitionMasterDAOImpl implements RequisitionMasterDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(RequisitionMasterDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addRequisitionMst(RequisitionMst requisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.persist(requisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void removeRequisitionMst(int requisitionMstId) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.delete(requisitionMstId);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	@Transactional
	public void editRequisitionMst(RequisitionMst requisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.update(requisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	@Transactional
	public RequisitionMst getRequisitionMst(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();
			// RequisitionMst requisitionMst = (RequisitionMst)
			// session.load(RequisitionMst.class, id);
			RequisitionMst requisitionMst = (RequisitionMst) session.get(
					RequisitionMst.class, id);
			return requisitionMst;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RequisitionMst();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionMst> getAllRequisitionMst() {
		Session session = sessionFactory.getCurrentSession();
		List<RequisitionMst> requisitionMstList = session.createQuery(
				"from RequisitionMst").list();
		return requisitionMstList;
	}

	@Override
	public RequisitionMst getRequisitionMstByPRFNo(String prfNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.procurement.model.RequisitionMst where prfNo = :prfNo");
			query.setString("prfNo", prfNo);
			// query.list();
			return (RequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionMst> getRequisitionMstListByPRFNo(String prfNo) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.procurement.model.RequisitionMst where lower(prfNo) like lower(:prfNo)");
			// query.setString("prfNo", prfNo);
			query.setParameter("prfNo", "%" + prfNo + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public RequisitionMst getRequisitionMstLastRowFromTab() {
		try {

			Session session = sessionFactory.getCurrentSession();
			/*
			 * Query query = session .createQuery(
			 * "from com.ibcs.desco.procurement.model.RequisitionMst where id=(Max(ID) from com.ibcs.desco.procurement.model.RequisitionMst)"
			 * );
			 */
			Query query = session
					.createQuery("from RequisitionMst req where req.id = (select max(id) from RequisitionMst)");

			return (RequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionMst> getRequisitionMstListByStatus(String status) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.procurement.model.RequisitionMst where status= :status");
			query.setString("status", status);
			// query.setParameter("prfNo", "%" + prfNo + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
