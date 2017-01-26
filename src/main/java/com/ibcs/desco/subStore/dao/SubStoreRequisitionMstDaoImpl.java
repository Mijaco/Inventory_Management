package com.ibcs.desco.subStore.dao;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

@Repository
@Transactional
public class SubStoreRequisitionMstDaoImpl implements
		SubStoreRequisitionMstDao {

	private static final Logger logger = LoggerFactory
			.getLogger(SubStoreRequisitionMstDaoImpl.class);

	private SessionFactory sessionFactory;

	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addSubStoreRequisitionMst(
			SubStoreRequisitionMst subStoreRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.persist(subStoreRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void removeSubStoreRequisitionMst(
			int subStoreRequisitionMstId) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.delete(subStoreRequisitionMstId);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void editSubStoreRequisitionMst(
			SubStoreRequisitionMst subStoreRequisitionMst) {
		try {

			Session session = sessionFactory.getCurrentSession();
			session.update(subStoreRequisitionMst);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequisitionMst(int id) {
		try {

			Session session = sessionFactory.getCurrentSession();

			SubStoreRequisitionMst subStoreRequisitionMst = (SubStoreRequisitionMst) session
					.get(SubStoreRequisitionMst.class, id);
			System.out.println("dao impl " + subStoreRequisitionMst);
			return subStoreRequisitionMst;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new SubStoreRequisitionMst();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionMst> getAllSubStoreRequisitionMst() {
		Session session = sessionFactory.getCurrentSession();
		List<SubStoreRequisitionMst> subStoreRequisitionMstList = session
				.createQuery("from SubStoreRequisitionMst").list();
		return subStoreRequisitionMstList;
	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequistionMstByRequisitionNo(
			String requisitionNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from SubStoreRequisitionMst where requisitionNo = :requisitionNo");
			query.setString("requisitionNo", requisitionNo);
			// query.list();
			return (SubStoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByRequisitionNo(
			String requisitionNo) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from SubStoreRequisitionMst where lower(requisitionNo) like lower(:requisitionNo)");

			query.setParameter("requisitionNo", "%" + requisitionNo + "%");
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequisitionMstLastRowFromTab() {
		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session
					.createQuery("from SubStoreRequisitionMst csreq where csreq.id = (select max(id) from SubStoreRequisitionMst)");

			return (SubStoreRequisitionMst) query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByStatus(
			String status) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from SubStoreRequisitionMst where status= :status");
			query.setString("status", status);
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreRequisitionMst> listSubStoreRequisitionMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<SubStoreRequisitionMst> items1 = (List<SubStoreRequisitionMst>) session
					.createCriteria(SubStoreRequisitionMst.class)
					.add(Restrictions.in("requisitionNo", operation)).list();
			return items1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
