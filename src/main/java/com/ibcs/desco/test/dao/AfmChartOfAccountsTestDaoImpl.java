package com.ibcs.desco.test.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.dao.AuctionDaoImpl;
import com.ibcs.desco.cs.model.CentralStoreItems;
import com.ibcs.desco.test.model.AfmChartOfAccountsTest;

@Repository
@Transactional
public class AfmChartOfAccountsTestDaoImpl implements AfmChartOfAccountsTestDao {

	private static final Logger logger = LoggerFactory
			.getLogger(AfmChartOfAccountsTest.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	@Override
	public void addCentralStoreItems(AfmChartOfAccountsTest afmChartOfAccountsTest) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(afmChartOfAccountsTest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AfmChartOfAccountsTest> listAfmChartOfAccountsTest() {
		Session session = sessionFactory.getCurrentSession();
		return (List<AfmChartOfAccountsTest>) session.createCriteria(
				AfmChartOfAccountsTest.class).list();
	}

	@Override
	public AfmChartOfAccountsTest getAfmChartOfAccountsTest(Long id) {
		Session session = sessionFactory.getCurrentSession();
		
		return (AfmChartOfAccountsTest) session.get(AfmChartOfAccountsTest.class, id);
	}


	@Override
	public AfmChartOfAccountsTest getAfmChartOfAccountsTestByCode(Long code) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from com.ibcs.desco.test.model.AfmChartOfAccountsTest s where s.accountCode = :code and parentAccountHeadId is null")
					.setParameter("code", code);
			//return (AfmChartOfAccountsTest) query.uniqueResult();
			return (AfmChartOfAccountsTest) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		
	}

	@Override
	public void deleteAfmChartOfAccountsTest(
			AfmChartOfAccountsTest afmChartOfAccountsTest) {
		// TODO Auto-generated method stub
				Session session = sessionFactory.getCurrentSession();
				session.delete(afmChartOfAccountsTest);

	}

}
