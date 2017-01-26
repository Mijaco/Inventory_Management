package com.ibcs.desco.procurement.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.procurement.model.ProcurementFlowPriority;

@Repository
@Transactional
public class ProcurementPriorityDAOImpl implements ProcurementPriorityDAO {
	private static final Logger logger = LoggerFactory
			.getLogger(ProcurementPriorityDAOImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addProcurementFlowPriority(
			ProcurementFlowPriority procurementFlowPriority) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(procurementFlowPriority);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcurementFlowPriority> listProcurementFlowPrioritys() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<ProcurementFlowPriority>) session.createCriteria(
				ProcurementFlowPriority.class).list();
	}

	@Override
	public ProcurementFlowPriority getProcurementFlowPriority(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (ProcurementFlowPriority) session.get(
				ProcurementFlowPriority.class, id);
	}

	@Override
	public void deleteProcurementPrioriy(
			ProcurementFlowPriority procurementFlowPriority) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(procurementFlowPriority);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcurementFlowPriority> getProcurementFlowPriorityByRoleName(
			String roleName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.procurement.model.ProcurementFlowPriority where lower(roleName) like lower(:roleName)");
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
	public List<ProcurementFlowPriority> getProcurementFlowPriorityByPriority(
			int priority) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.procurement.model.ProcurementFlowPriority where priority= :priority");
			query.setInteger("priority", priority);
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
