package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;

@Repository
@Transactional
public class StoreTicketApprovalHierarchyHistoryDaoImpl implements
		StoreTicketApprovalHierarchyHistoryDao {
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(storeTicketApprovalHierarchyHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> listStoreTicketApprovalHierarchyHistorys() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<StoreTicketApprovalHierarchyHistory>) session
				.createCriteria(StoreTicketApprovalHierarchyHistory.class)
				.list();
	}

	@Override
	public StoreTicketApprovalHierarchyHistory getStoreTicketApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (StoreTicketApprovalHierarchyHistory) session.get(
				StoreTicketApprovalHierarchyHistory.class, id);
	}

	@Override
	public void deleteStoreTicketApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(storeTicketApprovalHierarchyHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByRoleName(
			String actRoleName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory where lower(actRoleName) like lower(:actRoleName)");
			query.setParameter("actRoleName", "%" + actRoleName + "%");
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStateCode(
			int stateCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory where stateCode= :stateCode");
			query.setInteger("stateCode", stateCode);
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOperationId(
			String operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory where operationId= :operationId");
			query.setString("operationId", operationId);
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(StoreTicketApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("stateCode", stateCode));
		criteria.add(Restrictions.eq("status", status));
		criteria.setProjection(Projections.distinct(Projections
				.property("operationId")));
		List<StoreTicketApprovalHierarchyHistory> csitemList = (List<StoreTicketApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String optId = operationId + "";
		Criteria criteria = session
				.createCriteria(StoreTicketApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("operationId", optId));
		criteria.add(Restrictions.eq("status", status));
		// criteria.createAlias("stateCode", "stateCode");
		criteria.addOrder(Order.asc("stateCode"));

		List<StoreTicketApprovalHierarchyHistory> csitemList = (List<StoreTicketApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(StoreTicketApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<StoreTicketApprovalHierarchyHistory> csitemList = (List<StoreTicketApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreTicketApprovalHierarchyHistory> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
			String deptId, String roleName, String status, String ticketNo) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from  StoreTicketApprovalHierarchyHistory where deptId = '"
							+ deptId
							+ "' and actRoleName = '"
							+ roleName
							+ "' and status ='"
							+ status
							+ "'and lower(ticketNo) like lower('%'||'"
							+ ticketNo + "'||'%')");

			return (List<StoreTicketApprovalHierarchyHistory>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}

	}

}
