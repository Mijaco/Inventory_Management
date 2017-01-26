package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;

@Transactional
@Repository
public class GatePassApprovalHierarchyHistoryDaoImpl implements
		GatePassApprovalHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(LsCsReturnSlipApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<GatePassApprovalHierarchyHistory> appHierList = (List<GatePassApprovalHierarchyHistory>) criteria
				.list();
		return appHierList;
	}

	@Override
	public GatePassApprovalHierarchyHistory getGatePassApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (GatePassApprovalHierarchyHistory) session.get(
				GatePassApprovalHierarchyHistory.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String optId = operationId + "";
		Criteria criteria = session
				.createCriteria(GatePassApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("operationId", optId));
		criteria.add(Restrictions.eq("status", status));
		// criteria.createAlias("stateCode", "stateCode");
		criteria.addOrder(Order.asc("stateCode"));

		List<GatePassApprovalHierarchyHistory> csitemList = (List<GatePassApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRoleAndGPNo(
			String deptId, String roleName, String status, String gpNo) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from GatePassApprovalHierarchyHistory where deptId = '" + deptId + "' and actRoleName = '"
					+ roleName + "' and status ='" + status
					+ "'and lower(gatePassNo) like lower('%'||'" + gpNo
					+ "'||'%')");

			return (List<GatePassApprovalHierarchyHistory>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
