package com.ibcs.desco.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.ApprovalHierarchy;

@Repository
@Transactional
public class ApprovalHierarchyDaoImpl implements ApprovalHierarchyDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ApprovalHierarchyDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(approvalHierarchy);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> listApprovalHierarchys() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<ApprovalHierarchy>) session.createCriteria(
				ApprovalHierarchy.class).list();
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchy(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (ApprovalHierarchy) session.get(ApprovalHierarchy.class, id);
	}

	@Override
	public void deleteApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(approvalHierarchy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByRoleName(
			String roleName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ApprovalHierarchy where lower(roleName) like lower(:roleName)");
			// query.setString("roleName", roleName);
			query.setParameter("roleName", "%" + roleName + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationName(
			String operationName) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ApprovalHierarchy a where lower(operationName) like lower(:operationName) order by stateCode");
			query.setParameter("operationName", "%" + operationName + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleName(
			String operationName, List<String> roleNameList) {
		// TODO Auto-generated method stub
		try {
			
			Session session = sessionFactory.getCurrentSession();

			List<ApprovalHierarchy> result = (List<ApprovalHierarchy>) (Object) session
					.createCriteria(ApprovalHierarchy.class)
					.add(Restrictions.eq("operationName", operationName))
					.add(Restrictions.in("roleName", roleNameList)).list();

			return result;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleNameAndRangeIndicator(
			String operationName, List<String> roleNameList, String rangeIndicator) {
		// TODO Auto-generated method stub
		try {
			
			Session session = sessionFactory.getCurrentSession();

			List<ApprovalHierarchy> result = (List<ApprovalHierarchy>) (Object) session
					.createCriteria(ApprovalHierarchy.class)
					.add(Restrictions.eq("operationName", operationName))
					.add(Restrictions.eq("rangeIndicator", rangeIndicator))
					.add(Restrictions.in("roleName", roleNameList)).list();

			return result;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByStateCode(int stateCode) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ApprovalHierarchy where stateCode = :stateCode");
			query.setInteger("stateCode", stateCode);
			return (ApprovalHierarchy) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByRoleAndOperation(
			String roleName, String operationName) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.common.model.ApprovalHierarchy where roleName = :roleName and operationName = :operationName");
			query.setString("roleName", roleName);
			query.setString("operationName", operationName);
			return (ApprovalHierarchy) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByOperationNameAndSateCode(
			String operationName, String stateCode) {
		// TODO Auto-generated method stub

		int state = Integer.parseInt(stateCode);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ApprovalHierarchy.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("stateCode", state));

		ApprovalHierarchy csitemList = (ApprovalHierarchy) criteria.list().get(
				0);
		return csitemList;
	}

	// Added by: Ihteshamul Alam, IBCS

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalHierarchy> getDistinctObjectByColumnName() {
		try {
			Session session = sessionFactory.getCurrentSession();

			Criteria criteria = session.createCriteria(ApprovalHierarchy.class);
			criteria.setProjection(Projections.distinct(Projections
					.property("operationName")));
			List<ApprovalHierarchy> distinctList = (List<ApprovalHierarchy>) criteria
					.list();
			return distinctList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getUserRoleByOperationName(String tableName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query qry = session.createQuery("from " + tableName + " where "
					+ columnName + " = '" + columnValue
					+ "' order by state_code ASC");
			return qry.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
