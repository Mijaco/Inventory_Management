package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;

@Repository
@Transactional
public class ReturnSlipDtlDaoImpl implements ReturnSlipDtlDao {

	private SessionFactory sessionFactory;

	private ReturnSlipMstDao returnSlipMstDao;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ReturnSlipMstDao getReturnSlipMstDao() {
		return returnSlipMstDao;
	}

	public void setReturnSlipMstDao(ReturnSlipMstDao returnSlipMstDao) {
		this.returnSlipMstDao = returnSlipMstDao;
	}

	@Override
	public void addReturnSlipDtl(ReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		ReturnSlipMst returnSlipMst = returnSlipMstDao
				.getReturnSlipMst(returnSlipDtl.getReturnSlipMstId());
		returnSlipDtl.setReturnSlipMst(returnSlipMst);
		session.save(returnSlipDtl);
	}

	@Override
	public void removeReturnSlipDtl(int returnSlipDtlId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			ReturnSlipDtl returnSlipDtl = (ReturnSlipDtl) session.load(
					ReturnSlipDtl.class, returnSlipDtlId);
			session.delete(returnSlipDtl);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public void editReturnSlipDtl(ReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(returnSlipDtl);
	}

	@Override
	public ReturnSlipDtl getReturnSlipDtl(int id) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.ReturnSlipDtl where id = :id");
			query.setInteger("id", id);
			return (ReturnSlipDtl) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnSlipDtl> getAllReturnSlipDtl() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<ReturnSlipDtl>) session
				.createCriteria(ReturnSlipDtl.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnSlipDtl> getAllReturnSlipDtlByMstId(int returnSlipMstId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.ReturnSlipDtl where return_slip_mst_id = :returnSlipMstId");
			query.setInteger("returnSlipMstId", returnSlipMstId);
			return (List<ReturnSlipDtl>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
