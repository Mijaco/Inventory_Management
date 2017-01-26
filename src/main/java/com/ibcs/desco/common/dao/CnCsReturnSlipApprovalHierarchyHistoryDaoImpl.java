package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.CnCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;

@Repository
@Transactional
public class CnCsReturnSlipApprovalHierarchyHistoryDaoImpl implements 
	CnCsReturnSlipApprovalHierarchyHistoryDao {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CnCsReturnSlipApprovalHierarchyHistory> getCnCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(CnCsRequisitionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));
		
		List<CnCsReturnSlipApprovalHierarchyHistory> appHierList = (List<CnCsReturnSlipApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public CnCsReturnSlipApprovalHierarchyHistory getCnCsReturnSlipApprovalHierarchyHistory(
			int id) {

		Session session = sessionFactory.getCurrentSession();
		return (CnCsReturnSlipApprovalHierarchyHistory) session.get(
				CnCsReturnSlipApprovalHierarchyHistory.class, id);
	}
	
}
