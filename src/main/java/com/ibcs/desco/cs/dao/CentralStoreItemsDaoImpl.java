package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CentralStoreItems;

@Repository
@Transactional
public class CentralStoreItemsDaoImpl implements CentralStoreItemsDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addCentralStoreItems(CentralStoreItems centralStoreItems) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(centralStoreItems);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralStoreItems> listCentralStoreItems() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<CentralStoreItems>) session.createCriteria(
				CentralStoreItems.class).list();
	}

	@Override
	public CentralStoreItems getCentralStoreItems(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (CentralStoreItems) session.get(CentralStoreItems.class, id);
	}

	@Override
	public void deleteCentralStoreItems(CentralStoreItems centralStoreItems) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(centralStoreItems);
	}

	@Override
	public CentralStoreItems getCentralStoreItemsByItemId(String itemId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.CentralStoreItems where itemId = :itemId");
			query.setString("itemId", itemId);
			return (CentralStoreItems) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
