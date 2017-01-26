package com.ibcs.desco.contractor.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;

@Transactional
@Repository
public class ContractorRepresentiveDaoImpl implements ContractorRepresentiveDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unused")
	public boolean isValidContractor(String userName) {
		
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createQuery("from com.ibcs.desco.contractor.model.ContractorRepresentive where ");
		// query.setString("prfNo", prfNo);
		//query.setParameter("itemName", "%" + itemName + "%");
		//return query.list();
		return true;
	} catch (Exception e1) {
		e1.printStackTrace();

		return false;
	}
	}
	
	public ContractorRepresentive getContractorRep(String userId) {
		
		Session session = sessionFactory.getCurrentSession();
	try{
		/*Query query = session	
				.createQuery("from ContractorRepresentive where userId = :userId and isActive = 1 and endDate >= sysdate");*/
		Query query = session	
				.createQuery("from ContractorRepresentive where userId = :userId and isActive = 1");
		query.setParameter("userId", userId);
		return (ContractorRepresentive) query.list().get(0);

	} catch (Exception e1) {
		e1.printStackTrace();

		return null;
	}
		
}
	public Contractor getContractorByContractNo(String contractNo) {
		
		Session session = sessionFactory.getCurrentSession();
	try{
		Query query = session			
			.createQuery("from Contractor where contractNo = :contractNo and isActive = 1");
				// .createQuery("from Contractor where contractNo = :contractNo and isActive = 1 and updatedValidityDate >= sysdate");
				// select * from CONTRACTOR  where CONTRACT_NO='CN0002' and ISACTIVE='1' and UPDATED_VALIDITY_DATE>=sysdate;
		query.setParameter("contractNo", contractNo);
		return (Contractor) query.list().get(0);

	} catch (Exception e1) {
		e1.printStackTrace();

		return null;
	}
		
}
	public boolean getValidContractor(String contractNo){
		
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session
		
					.createQuery("from Contractor where contractNo = :contractNo and isActive = 1 and updatedValidityDate >= sysdate");
			query.setParameter("contractNo", contractNo);
			Contractor c= (Contractor) query.list().get(0);
			if(c!=null){
				return true;
			}else{
				return false;
			}

		} catch (Exception e1) {
			e1.printStackTrace();

			return false;
		}
		//return contractorRepresentiveDao.getValidContractor(contractNo);

	}
	@Override
	public void addContractor(Contractor contractor) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(contractor);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contractor> getContractorList(){
		
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Contractor.class).list();
		
	}
	
	@Override
	public void addContractorRepresentive(ContractorRepresentive contractorRepresentive) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(contractorRepresentive);
	}
	
/*
	@Override
	public void addItemInventory(ItemMaster itemMaster) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(itemMaster);
	}

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

	@Override
	public List<ItemMaster> getInventoryItemForAutoComplete(String itemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemMaster> getItemListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.ItemMaster where categoryId=:categoryId");
			query.setInteger("categoryId", categoryId);
			return (List<ItemMaster>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
*/
}
