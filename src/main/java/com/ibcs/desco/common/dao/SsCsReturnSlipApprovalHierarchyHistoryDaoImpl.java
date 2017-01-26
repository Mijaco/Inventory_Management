package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;

@Repository("ssCsReturnSlipApprovalHierarchyHistoryDao")
@Transactional
public class SsCsReturnSlipApprovalHierarchyHistoryDaoImpl implements
		SsCsReturnSlipApprovalHierarchyHistoryDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SsCsReturnSlipApprovalHierarchyHistory> getSsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(LsCsReturnSlipApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<SsCsReturnSlipApprovalHierarchyHistory> appHierList = (List<SsCsReturnSlipApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public SsCsReturnSlipApprovalHierarchyHistory getSsCsReturnSlipApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (SsCsReturnSlipApprovalHierarchyHistory) session.get(
				SsCsReturnSlipApprovalHierarchyHistory.class, id);
	}

	
}
