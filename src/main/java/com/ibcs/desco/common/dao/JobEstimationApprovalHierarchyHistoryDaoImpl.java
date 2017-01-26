package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.JobEstimationApprovalHierarchyHistory;

@Repository
@Transactional
public class JobEstimationApprovalHierarchyHistoryDaoImpl implements
JobEstimationApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobEstimationApprovalHierarchyHistory> getJobEstimationApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(JobEstimationApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<JobEstimationApprovalHierarchyHistory> appHierList = (List<JobEstimationApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public JobEstimationApprovalHierarchyHistory getJobEstimationApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (JobEstimationApprovalHierarchyHistory) session.get(
				JobEstimationApprovalHierarchyHistory.class, id);
	}

}
