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

import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;

@Repository
@Transactional
public class ItemRcvApprovalHierarchyHistoryDaoImpl implements
		ItemRcvApprovalHierarchyHistoryDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(approvalHierarchyHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> listApprovalHierarchyHistorys() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<ItemRcvApprovalHierarchyHistory>) session.createCriteria(
				ItemRcvApprovalHierarchyHistory.class).list();
	}

	@Override
	public ItemRcvApprovalHierarchyHistory getApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (ItemRcvApprovalHierarchyHistory) session.get(
				ItemRcvApprovalHierarchyHistory.class, id);
	}

	@Override
	public void deleteApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(approvalHierarchyHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByRoleName(
			String actRoleName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory where lower(actRoleName) like lower(:actRoleName)");
			query.setParameter("actRoleName", "%" + actRoleName + "%");
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStateCode(
			int stateCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory where stateCode= :stateCode");
			query.setInteger("stateCode", stateCode);
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOperationId(
			String operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory where operationId= :operationId");
			query.setString("operationId", operationId);
			return query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		// Query query = session.createQuery("from ApprovalHierarchyHistory");
		// query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// return query.list();

		Criteria criteria = session
				.createCriteria(ItemRcvApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("stateCode", stateCode));
		criteria.add(Restrictions.eq("status", status));
		criteria.setProjection(Projections.distinct(Projections
				.property("operationId")));
		List<ItemRcvApprovalHierarchyHistory> csitemList = (List<ItemRcvApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		// String optId = operationId+"";
		Criteria criteria = session
				.createCriteria(ItemRcvApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("operationId", operationId));
		criteria.add(Restrictions.eq("status", status));
		// criteria.createAlias("stateCode", "stateCode");
		criteria.addOrder(Order.asc("stateCode"));

		List<ItemRcvApprovalHierarchyHistory> csitemList = (List<ItemRcvApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(ItemRcvApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<ItemRcvApprovalHierarchyHistory> csitemList = (List<ItemRcvApprovalHierarchyHistory>) criteria
				.list();
		return csitemList;
	}

}
