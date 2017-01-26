package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.LsPdSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;

@Repository
@Transactional
public class LsSsRequisitionApprovalHierarchyHistoryDaoImpl implements
		LsSsRequisitionApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LsSsRequisitionApprovalHierarchyHistory> getLsSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(LsSsRequisitionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<LsSsRequisitionApprovalHierarchyHistory> appHierList = (List<LsSsRequisitionApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public LsSsRequisitionApprovalHierarchyHistory getLsSsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (LsSsRequisitionApprovalHierarchyHistory) session.get(
				LsSsRequisitionApprovalHierarchyHistory.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session
					.createCriteria(LsSsRequisitionApprovalHierarchyHistory.class);
			criteria.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("status", status));
			criteria.setProjection(Projections.distinct(Projections
					.property("operationId")));

			List<String> optIdList = criteria.list();

			return optIdList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatusLSPDSS(String deptId,
			String status) {

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session
					.createCriteria(LsPdSsRequisitionApprovalHierarchyHistory.class);
			criteria.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("status", status));
			criteria.setProjection(Projections.distinct(Projections
					.property("operationId")));

			List<String> optIdList = criteria.list();

			return optIdList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
