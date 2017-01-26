package com.ibcs.desco.inventory.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.ItemCategory;

@Transactional
@Repository
public class ItemGroupDaoImpl implements ItemGroupDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addItemGroup(ItemCategory itemGroup) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(itemGroup);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemCategory> getAllItemGroups() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(ItemCategory.class);
		c.addOrder(Order.asc("categoryId"));
		List<ItemCategory> categoryList = c.list();
		return categoryList;

		/*
		 * List<ItemCategory> categoryList = (List<ItemCategory>) session
		 * .createCriteria(ItemCategory.class).list(); return categoryList;
		 */
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemCategory> getGeneralItemGroups() {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			Criteria c = session.createCriteria(ItemCategory.class);
			c.add(Restrictions.eq("itemType", "G"));
			c.addOrder(Order.asc("categoryId"));
			List<ItemCategory> categoryList = c.list();
			return categoryList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemCategory> getConstructionItemGroups() {

		try {
			/*
			 * Session session = sessionFactory.getCurrentSession(); Query query
			 * = session .createQuery(
			 * "from com.ibcs.desco.inventory.model.ItemCategory where lower(itemType) like lower(:itemType)"
			 * ); query.setParameter("itemType", "%C%");
			 * 
			 * List<ItemCategory> categoryList = query.list(); return
			 * categoryList; //
			 */
			Session session = this.sessionFactory.getCurrentSession();
			Criteria c = session.createCriteria(ItemCategory.class);
			c.add(Restrictions.eq("itemType", "C"));
			c.addOrder(Order.asc("categoryId"));
			List<ItemCategory> categoryList = c.list();
			return categoryList; //

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ItemCategory getItemGroup(int id) {

		return null;
	}

	@Override
	public void deleteItemGroup() {

	}

	@Override
	public ItemCategory getGetItemGroupById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (ItemCategory) session.get(ItemCategory.class, id);
	}

	@Override
	public void editItemGroup(ItemCategory itemGroup) {
		Session session = sessionFactory.getCurrentSession();
		session.update(itemGroup);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemCategory> getItemGroupByGroupName(String categoryName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.ItemCategory where lower(categoryName) like lower(:categoryName)");
			query.setParameter("categoryName", "%" + categoryName + "%");

			List<ItemCategory> categoryList = (List<ItemCategory>) query.list();
			return categoryList;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}

	@Override
	public boolean checkItemGroupName(String itemGroupName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("select count(*) from com.ibcs.desco.inventory.model.ItemCategory where categoryName=:categoryName");
		query.setParameter("itemGroupName", itemGroupName);
		Long count = (Long) query.uniqueResult();
		if (count > 0) {
			return true;
		} else
			return false;
	}

}
