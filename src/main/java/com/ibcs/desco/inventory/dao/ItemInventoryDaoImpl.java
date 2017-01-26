package com.ibcs.desco.inventory.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.model.PhysicalStoreInventory;

@Transactional
@Repository
public class ItemInventoryDaoImpl implements ItemInventoryDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addItemInventory(ItemMaster itemMaster) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(itemMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMaster> getInventoryItemList() {

		return this.sessionFactory.getCurrentSession()
				.createCriteria(ItemMaster.class).list();
	}

	@Override
	public ItemMaster getItemGroup(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		ItemMaster itemMaster = (ItemMaster) session.get(ItemMaster.class, id);
		return itemMaster;
	}

	@Override
	public void deleteItemGroup() {

	}

	@Override
	public ItemMaster getInventoryItemById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		ItemMaster itemMaster = (ItemMaster) session.get(ItemMaster.class, id);
		return itemMaster;
	}

	@Override
	public void editInventoryItem(ItemMaster itemMaster) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(itemMaster);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMaster> getInventoryItemByName(String itemName) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.ItemMaster where lower(itemName) like lower(:itemName)");
			// query.setString("prfNo", prfNo);
			query.setParameter("itemName", "%" + itemName + "%");
			return query.list();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemMaster getInventoryItemByItemName(String itemName) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.ItemMaster where itemName= :itemName");
			query.setParameter("itemName", itemName);
			List<ItemMaster> im = query.list();
			if (im.size() > 0) {
				return im.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AvgPrice getAvgPriceByItemCode(String ItemCode) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from AvgPrice where ItemCode= :ItemCode");
			query.setParameter("ItemCode", ItemCode);
			List<AvgPrice> im = query.list();
			if (im.size() > 0) {
				return im.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public List<ItemMaster> getInventoryItemForAutoComplete(String itemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMaster> getItemListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.ItemMaster where categoryId=:categoryId order by itemId");
			query.setInteger("categoryId", categoryId);
			return (List<ItemMaster>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getDistinctInventoryDateListFromPhysicalInventory() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			/*
			 * Query query = session .createQuery(
			 * "from PhysicalStoreInventory where status='0' and flag='0'");
			 * query.setInteger("categoryId", categoryId);
			 */
			Criteria criteria = session
					.createCriteria(PhysicalStoreInventory.class);
			criteria.setProjection(Projections.distinct(Projections
					.property("inventoryDate")));
			List<Date> invDateList = criteria.list();
			
			return invDateList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getItemNameByItemCode(String itemCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
			Query query = session
					.createQuery("Select itemName from ItemMaster where itemId=:itemCode");
			query.setString("itemCode", itemCode);
		List<String> str = query.list();
		if(str.size()>0){
			
			return str.get(0).toString();
		}else{
			return null;}
	
	}

}
