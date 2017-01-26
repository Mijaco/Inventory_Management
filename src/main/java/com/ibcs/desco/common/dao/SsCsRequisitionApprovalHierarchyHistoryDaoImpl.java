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

import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;

@Repository("ssCsRequisitionApprovalHierarchyHistoryDao")
@Transactional
public class SsCsRequisitionApprovalHierarchyHistoryDaoImpl implements
		SsCsRequisitionApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SsCsRequisitionApprovalHierarchyHistory> getSsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(SsCsRequisitionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<SsCsRequisitionApprovalHierarchyHistory> appHierList = (List<SsCsRequisitionApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public SsCsRequisitionApprovalHierarchyHistory getSsCsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (SsCsRequisitionApprovalHierarchyHistory) session.get(
				SsCsRequisitionApprovalHierarchyHistory.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session
					.createCriteria(SsCsRequisitionApprovalHierarchyHistory.class);
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
