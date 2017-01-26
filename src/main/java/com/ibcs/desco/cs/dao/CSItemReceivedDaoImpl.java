package com.ibcs.desco.cs.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSItemReceived;

@Repository
@Transactional
public class CSItemReceivedDaoImpl implements CSItemReceivedDao {
	private static final Logger logger = LoggerFactory
			.getLogger(CSItemReceivedDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCSItemReceived(CSItemReceived cSItemReceived) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cSItemReceived);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSItemReceived> listCSItemReceiveds() {

		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CSItemReceived>) session
				.createCriteria(CSItemReceived.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		/*
		 * Criteria criteria = session.createCriteria(CSItemReceived.class );
		 * criteria.setProjection( Projections.distinct(
		 * Projections.property("receivedReportNo"))); List<CSItemReceived>
		 * csitemList = (List<CSItemReceived>) criteria.list(); return
		 * csitemList;
		 */
	}

	@Override
	public CSItemReceived getCSItemReceived(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CSItemReceived) session.get(CSItemReceived.class, id);
	}

	@Override
	public void deleteCSItemReceived(CSItemReceived cSItemReceived) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(cSItemReceived);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSItemReceived> listCSItemReceivedByReceivedReportNo(
			String receivedReportNo) {
		// TODO Auto-generated method stub
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CSItemReceived where receivedReportNo = :receivedReportNo");
			// query.setParameter("operationName", "%" + receivedReportNo +
			// "%");
			query.setString("receivedReportNo", receivedReportNo);
			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List<CSItemReceived> listCSItemReceivedByOperationIds(
			Integer[] operationId) {
		// TODO Auto-generated method stub

		try {
			Session session = sessionFactory.getCurrentSession();
			List<Integer> operation = Arrays.asList(operationId);
			/*
			 * Query query = session .createQuery(
			 * "FROM CSItemReceived WHERE receivedReportNo IN (:operationId)");
			 * query.setParameterList("operationId", operation);
			 * List<CSItemReceived> items = query.list(); return items;
			 */

			Query query = session
					.createQuery("select distinct c.receivedReportNo, c.chalanNo, c.contractNo, c.receivedBy, c.receivedDate FROM CSItemReceived c WHERE receivedReportNo IN (:operationId)");
			query.setParameterList("operationId", operation);
			List items1 = query.list();

			List<CSItemReceived> items = new ArrayList<CSItemReceived>();
			// List cs=query.list();
			// String cn=items1.get(0).
			// query.l

			// CSItemReceived abc = new CSItemReceived[items1.size()];
			int y = 0;

			for (Object obj : items1) {
				Object[] fields = (Object[]) obj;
				CSItemReceived abc = new CSItemReceived();
				// abc[y] =new CSItemReceived();

				abc.setReceivedReportNo(Integer.parseInt(fields[0].toString()));
				abc.setChalanNo(fields[1].toString());
				abc.setContractNo(fields[2].toString());
				abc.setReceivedBy(fields[3].toString());
				// Date d=new Date(fields[4].toString());
				// String text = "2011-10-02 18:48:05.123456";
				Timestamp ts = Timestamp.valueOf(fields[4].toString());
				abc.setReceivedDate(ts);
				items.add(abc);
				// System.out.println("sistemOperare = " + fields[0] +
				// " (count = " + fields[1] + ")");
			}

			/*
			 * for(CSItemReceived x : items1) {
			 * 
			 * }
			 */

			return items;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
