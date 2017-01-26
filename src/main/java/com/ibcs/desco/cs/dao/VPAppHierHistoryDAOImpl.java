package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.VehiclePermissionApprovalHierarchyHistory;

@Repository
@Transactional
public class VPAppHierHistoryDAOImpl implements VehiclePermissionApprovalHierarchyHistoryDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<VehiclePermissionApprovalHierarchyHistory> getvpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(VehiclePermissionApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("deptId", deptId));
		criteria.add(Restrictions.eq("actRoleName", roleName));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));
		
		@SuppressWarnings("unchecked")
		List<VehiclePermissionApprovalHierarchyHistory> appHierList = 
		(List<VehiclePermissionApprovalHierarchyHistory>) criteria.list();
		
		return appHierList;
	}

	@Override
	public VehiclePermissionApprovalHierarchyHistory getvpApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory .getCurrentSession();
		return (VehiclePermissionApprovalHierarchyHistory) 
				session.get(VehiclePermissionApprovalHierarchyHistory.class, id);		
	}

}
