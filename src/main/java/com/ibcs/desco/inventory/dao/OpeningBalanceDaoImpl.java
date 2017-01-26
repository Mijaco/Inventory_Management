package com.ibcs.desco.inventory.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.OpeningBalance;

@Transactional
@Repository
public class OpeningBalanceDaoImpl implements OpeningBalanceDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void createOpeingBalance(OpeningBalance openingBalance) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(openingBalance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OpeningBalance> getAllIOpeningBalance() {

		return this.sessionFactory.getCurrentSession().createCriteria(OpeningBalance.class).list();
	}

	@Override
	public OpeningBalance getOpenBalanceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (OpeningBalance) session.get(OpeningBalance.class, id);
	}

	@Override
	public void editOpeningBalance(OpeningBalance openingBalance) {
		System.out.println("==============edit opening balance 2=============" + openingBalance);
		Session session = sessionFactory.getCurrentSession();
		session.update(openingBalance);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OpeningBalance> getOpeningBalanceByItemName(String itemName) {
		// TODO Auto-generated method stub

		System.out.println("dao==========item name =======================" + itemName);
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.OpeningBalance where lower(itemName) like lower(:itemName)");
			// query.setString("prfNo", prfNo);
			query.setParameter("itemName", "%" + itemName + "%");
			return query.list();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
