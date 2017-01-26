package com.ibcs.desco.subStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory;

@Repository
@Transactional
public class SSVPAppHierHistoryDAOImpl implements SSVPAppHierHistoryDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<SSVehiclePermissionApprovalHierarchyHistory> getSSVpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SSVehiclePermissionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));
		
		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> appHierList = 
		(List<SSVehiclePermissionApprovalHierarchyHistory>) criteria.list();
		
		return appHierList;
	}

	@Override
	public SSVehiclePermissionApprovalHierarchyHistory getSSVpApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory .getCurrentSession();
		return (SSVehiclePermissionApprovalHierarchyHistory) 
				session.get(SSVehiclePermissionApprovalHierarchyHistory.class, id);		
	}

}
