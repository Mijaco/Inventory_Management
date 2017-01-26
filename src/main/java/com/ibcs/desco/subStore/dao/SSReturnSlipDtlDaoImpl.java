package com.ibcs.desco.subStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;

@Repository
@Transactional
public class SSReturnSlipDtlDaoImpl implements SSReturnSlipDtlDao {

	private SessionFactory sessionFactory;

	private SSReturnSlipMstDao ssReturnSlipMstDao;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SSReturnSlipMstDao getSSReturnSlipMstDao() {
		return ssReturnSlipMstDao;
	}

	public void setSSReturnSlipMstDao(SSReturnSlipMstDao returnSlipMstDao) {
		this.ssReturnSlipMstDao = returnSlipMstDao;
	}

	@Override
	public void addSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		SSReturnSlipMst returnSlipMst = ssReturnSlipMstDao
				.getSSReturnSlipMst(returnSlipDtl.getReturnSlipMstId());
		returnSlipDtl.setReturnSlipMst(returnSlipMst);
		session.save(returnSlipDtl);
	}

	@Override
	public void removeSSReturnSlipDtl(int returnSlipDtlId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			SSReturnSlipDtl returnSlipDtl = (SSReturnSlipDtl) session.load(
					SSReturnSlipDtl.class, returnSlipDtlId);
			session.delete(returnSlipDtl);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public void editSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(returnSlipDtl);
	}

	@Override
	public SSReturnSlipDtl getSSReturnSlipDtl(int id) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.subStore.model.SSReturnSlipDtl where id = :id");
			query.setInteger("id", id);
			return (SSReturnSlipDtl) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSReturnSlipDtl> getAllSSReturnSlipDtl() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<SSReturnSlipDtl>) session
				.createCriteria(SSReturnSlipDtl.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSReturnSlipDtl> getAllSSReturnSlipDtlByMstId(int returnSlipMstId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.subStore.model.SSReturnSlipDtl where return_slip_mst_id = :returnSlipMstId");
			query.setInteger("returnSlipMstId", returnSlipMstId);
			return (List<SSReturnSlipDtl>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
