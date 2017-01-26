package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;

@Repository
@Transactional
public class CnSsRequisitionApprovalHierarchyHistoryDaoImpl implements
		CnSsRequisitionApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CnSsRequisitionApprovalHierarchyHistory> getCnSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(CnSsRequisitionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<CnSsRequisitionApprovalHierarchyHistory> appHierList = (List<CnSsRequisitionApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public CnSsRequisitionApprovalHierarchyHistory getCnSsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CnSsRequisitionApprovalHierarchyHistory) session.get(
				CnSsRequisitionApprovalHierarchyHistory.class, id);
	}

}
