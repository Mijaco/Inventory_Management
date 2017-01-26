package com.ibcs.desco.cs.dao;

import java.util.ArrayList;
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

import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

@Repository
@Transactional
public class CSStoreTicketMstDaoImpl implements CSStoreTicketMstDao {
	private static final Logger logger = LoggerFactory
			.getLogger(CSStoreTicketMstDaoImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(csStoreTicketMst);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketMst> listCSStoreTicketMsts() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CSStoreTicketMst>) session.createCriteria(
				CSStoreTicketMst.class).list();
	}

	@Override
	public CSStoreTicketMst getCSStoreTicketMst(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CSStoreTicketMst) session.get(CSStoreTicketMst.class, id);
	}

	@Override
	public void deleteCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(csStoreTicketMst);

	}

	@Override
	public void editCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(csStoreTicketMst);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<CSStoreTicketMst> csStoreTicketMstList = new ArrayList<CSStoreTicketMst>();
		try {
			if (operationId.length > 0) {
				String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
						.split(", ");
				List<String> operation = Arrays.asList(a);

				csStoreTicketMstList = (List<CSStoreTicketMst>) session
						.createCriteria(CSStoreTicketMst.class)
						.add(Restrictions.in("operationId", operation)).list();
			}
			// .list();
			return csStoreTicketMstList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return csStoreTicketMstList;
		}
	}

	@Override
	public List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			@SuppressWarnings("unchecked")
			List<CSStoreTicketMst> items1 = (List<CSStoreTicketMst>) session
					.createCriteria(CSStoreTicketMst.class)
					.add(Restrictions.in("operationId", operationId)).list();
			return items1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public CSStoreTicketMst getCSStoreTicketMstByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CSStoreTicketMst where ticketNo = :ticketNo");
			query.setString("ticketNo", ticketNo);
			return (CSStoreTicketMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@Override
	public SSStoreTicketMst getSSStoreTicketMstByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from SSStoreTicketMst where ticketNo = :ticketNo");
			query.setString("ticketNo", ticketNo);
			return (SSStoreTicketMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(
			List<String> operationIdList, boolean flag, boolean approved) {
		if(operationIdList.size()==0){
			return operationIdList;
		}
		int f, a;
		if (flag) {
			f = 1;
		} else {
			f = 0;
		}
		if (approved) {
			a = 1;
		} else {
			a = 0;
		}

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String q = "";
		for (String s : operationIdList) {
			q = q + "'" + s + "', ";
		}
		q = q.trim();
		q = q.substring(0, q.length() - 1);
		try {
			Query query = session
					.createQuery("from CSStoreTicketMst where flag=" + f
							+ " and approved = " + a + "and operationId in ("
							+ q + ") order by operationId desc");

			List<CSStoreTicketMst> csStoreTicketMstList = new ArrayList<CSStoreTicketMst>();
			csStoreTicketMstList = query.list();

			List<String> optIdList = new ArrayList<String>();
			for (CSStoreTicketMst csStoreTicketMst : csStoreTicketMstList) {
				optIdList.add(csStoreTicketMst.getOperationId());
			}

			return optIdList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperationIdListByDeptIdAndStatusForSS(
			List<String> operationIdList, boolean flag, boolean approved) {
		int f, a;
		if (flag) {
			f = 1;
		} else {
			f = 0;
		}
		if (approved) {
			a = 1;
		} else {
			a = 0;
		}

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String q = "";
		for (String s : operationIdList) {
			q = q + "'" + s + "', ";
		}
		q = q.trim();
		q = q.substring(0, q.length() - 1);
		try {
			Query query = session
					.createQuery("from SSStoreTicketMst where flag=" + f
							+ " and approved = " + a + "and operationId in ("
							+ q + ") order by operationId desc");

			List<SSStoreTicketMst> ssStoreTicketMstList = new ArrayList<SSStoreTicketMst>();
			ssStoreTicketMstList = query.list();

			List<String> optIdList = new ArrayList<String>();
			for (SSStoreTicketMst csStoreTicketMst : ssStoreTicketMstList) {
				optIdList.add(csStoreTicketMst.getOperationId());
			}

			return optIdList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketMst> listCSStoreTicketMstifApproved(
			boolean approved) {
		int a = 0;
		if (approved) {
			a = 1;
		} else {
			a = 0;
		}
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSStoreTicketMst where approved = " + a);
			List<CSStoreTicketMst> csStoreTicketMstList = new ArrayList<CSStoreTicketMst>();
			csStoreTicketMstList = query.list();			
			return csStoreTicketMstList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	//
	@SuppressWarnings("unchecked")
	@Override
	public List<CSStoreTicketMst> csStoreTicketListForFixedAssets() {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSStoreTicketMst where approved =" + 1 + /*" and storeTicketType not like '%RETURN%'*/" and gatePass is not null");
			List<CSStoreTicketMst> csStoreTicketMstList = new ArrayList<CSStoreTicketMst>();
			csStoreTicketMstList = query.list();			
			return csStoreTicketMstList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

	//
	@SuppressWarnings("unchecked")
	@Override
	public List<SSStoreTicketMst> ssStoreTicketListForFixedAssets() {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from SSStoreTicketMst where approved =" + 1 + /*" and storeTicketType not like '%RETURN%'*/" and gatePass is not null");
			List<SSStoreTicketMst> csStoreTicketMstList = new ArrayList<SSStoreTicketMst>();
			csStoreTicketMstList = query.list();			
			return csStoreTicketMstList;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
}
