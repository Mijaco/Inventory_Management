package com.ibcs.desco.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdCsRequisitionApprovalHierarchyHistory;

@Repository
@Transactional
public class LsCsRequisitionApprovalHierarchyHistoryDaoImpl implements
		LsCsRequisitionApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LsCsRequisitionApprovalHierarchyHistory> getLsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(LsCsRequisitionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<LsCsRequisitionApprovalHierarchyHistory> appHierList = (List<LsCsRequisitionApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public LsCsRequisitionApprovalHierarchyHistory getLsCsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (LsCsRequisitionApprovalHierarchyHistory) session.get(
				LsCsRequisitionApprovalHierarchyHistory.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria1 = session
					.createCriteria(LsCsRequisitionApprovalHierarchyHistory.class);
			criteria1.add(Restrictions.eq("deptId", deptId));
			criteria1.add(Restrictions.eq("status", status));
			criteria1.setProjection(Projections.distinct(Projections
					.property("operationId")));

			Criteria criteria2 = session
					.createCriteria(LsPdCsRequisitionApprovalHierarchyHistory.class);
			criteria2.add(Restrictions.eq("deptId", deptId));
			criteria2.add(Restrictions.eq("status", status));
			criteria2.setProjection(Projections.distinct(Projections
					.property("operationId")));

			List<String> optIdList1 = new ArrayList<String>();
			optIdList1 = criteria1.list();

			List<String> optIdList2 = new ArrayList<String>();
			optIdList2 = criteria2.list();

			List<String> operationIdList = new ArrayList<String>();
			operationIdList.addAll(optIdList1);
			operationIdList.addAll(optIdList2);

			return operationIdList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
