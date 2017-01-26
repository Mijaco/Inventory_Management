package com.ibcs.desco.subStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SubStoreItems;

@Repository("subStoreItemsDao")
@Transactional
public class SubStoreItemsDaoImpl implements SubStoreItemsDao {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addSubStoreItems(SubStoreItems subStoreItems) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(subStoreItems);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubStoreItems> listSubStoreItems() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<SubStoreItems>) session.createCriteria(
				SubStoreItems.class).list();
	}

	@Override
	public SubStoreItems getSubStoreItems(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (SubStoreItems) session.get(SubStoreItems.class, id);
	}

	@Override
	public void deleteSubStoreItems(SubStoreItems subStoreItems) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(subStoreItems);
	}

	@Override
	public SubStoreItems getSubStoreItemsByItemId(String itemId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.subStore.model.SubStoreItems where itemId = :itemId");
			query.setString("itemId", itemId);
			return (SubStoreItems) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
