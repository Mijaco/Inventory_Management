package com.ibcs.desco.cs.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.ReturnSlipMst;

@Repository
@Transactional
public class ReturnSlipMstDaoImpl implements ReturnSlipMstDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addReturnSlipMst(ReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(returnSlipMst);
	}

	@Override
	public void removeReturnSlipMst(int returnSlipMstId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			ReturnSlipMst returnSlipMst = (ReturnSlipMst) session.load(
					ReturnSlipMst.class, returnSlipMstId);
			session.delete(returnSlipMst);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public void editReturnSlipMst(ReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(returnSlipMst);
	}

	@Override
	public ReturnSlipMst getReturnSlipMst(int id) {
		// TODO Auto-generated method stub
		/*
		 * Session session = sessionFactory.getCurrentSession(); ReturnSlipMst
		 * returnSlipMst = (ReturnSlipMst) session.load( ReturnSlipMst.class,
		 * id); return returnSlipMst;
		 */
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.ReturnSlipMst where id = :id");
			query.setInteger("id", id);
			return (ReturnSlipMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnSlipMst> getAllReturnSlipMst() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<ReturnSlipMst>) session
				.createCriteria(ReturnSlipMst.class).list();
	}

	@Override
	public ReturnSlipMst getReturnSlipMstByRSNo(String rsNo) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.ReturnSlipMst where lower(returnSlipNo) like lower(:returnSlipNo)");
			// query.setString("rsNo", rsNo);
			query.setParameter("returnSlipNo", "%" + rsNo + "%");
			ReturnSlipMst returnSlipMst = new ReturnSlipMst();
			if (query.list().size() > 0) {
				returnSlipMst = (ReturnSlipMst) query.list().get(0);
			}
			return returnSlipMst;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ReturnSlipMst();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnSlipMst> listReturnSlipMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<ReturnSlipMst> items1 = (List<ReturnSlipMst>) session
					.createCriteria(ReturnSlipMst.class)
					.add(Restrictions.in("returnSlipNo", operation)).list();
			return items1;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnSlipMst> listReturnSlipMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		try {
			if (operationId.size() > 0) {
				returnSlipMstList = (List<ReturnSlipMst>) session
						.createCriteria(ReturnSlipMst.class)
						.add(Restrictions.in("returnSlipNo", operationId))
						.list();
			}

			return returnSlipMstList;
		} catch (Exception e) {
			e.printStackTrace();
			return returnSlipMstList;
		}
	}

}
