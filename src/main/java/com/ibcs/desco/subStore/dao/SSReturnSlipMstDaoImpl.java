package com.ibcs.desco.subStore.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SSReturnSlipMst;

@Repository
@Transactional
public class SSReturnSlipMstDaoImpl implements SSReturnSlipMstDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addSSReturnSlipMst(SSReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(returnSlipMst);
	}

	@Override
	public void removeSSReturnSlipMst(int returnSlipMstId) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) session.load(
					SSReturnSlipMst.class, returnSlipMstId);
			session.delete(returnSlipMst);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public void editSSReturnSlipMst(SSReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(returnSlipMst);
	}

	@Override
	public SSReturnSlipMst getSSReturnSlipMst(int id) {
		// TODO Auto-generated method stub
		/*
		 * Session session = sessionFactory.getCurrentSession(); ReturnSlipMst
		 * returnSlipMst = (ReturnSlipMst) session.load( ReturnSlipMst.class,
		 * id); return returnSlipMst;
		 */
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.subStore.model.SSReturnSlipMst where id = :id");
			query.setInteger("id", id);
			return (SSReturnSlipMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSReturnSlipMst> getAllSSReturnSlipMst() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<SSReturnSlipMst>) session
				.createCriteria(SSReturnSlipMst.class).list();
	}

	@Override
	public SSReturnSlipMst getSSReturnSlipMstByRSNo(String rsNo) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.subStore.model.SSReturnSlipMst where lower(returnSlipNo) like lower(:returnSlipNo)");
			// query.setString("rsNo", rsNo);
			query.setParameter("returnSlipNo", "%" + rsNo + "%");
			SSReturnSlipMst returnSlipMst = new SSReturnSlipMst();
			if (query.list().size() > 0) {
				returnSlipMst = (SSReturnSlipMst) query.list().get(0);
			}
			return returnSlipMst;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new SSReturnSlipMst();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSReturnSlipMst> listSSReturnSlipMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<SSReturnSlipMst> items1 = (List<SSReturnSlipMst>) session
					.createCriteria(SSReturnSlipMst.class)
					.add(Restrictions.in("returnSlipNo", operation)).list();
			return items1;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSReturnSlipMst> listSSReturnSlipMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		try {
			if (operationId.size() > 0) {
				returnSlipMstList = (List<SSReturnSlipMst>) session
						.createCriteria(SSReturnSlipMst.class)
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
