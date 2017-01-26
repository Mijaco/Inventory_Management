package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SubStoreItemsDao;
import com.ibcs.desco.subStore.model.SubStoreItems;

@Service("subStoreItemsService")
public class SubStoreItemsServiceImpl implements SubStoreItemsService {

	SubStoreItemsDao subStoreItemsDao;

	public SubStoreItemsDao getSubStoreItemsDao() {
		return subStoreItemsDao;
	}

	public void setSubStoreItemsDao(
			SubStoreItemsDao subStoreItemsDao) {
		this.subStoreItemsDao = subStoreItemsDao;
	}

	@Override
	public void addSubStoreItems(SubStoreItems subStoreItems) {
		// TODO Auto-generated method stub
		subStoreItemsDao.addSubStoreItems(subStoreItems);
	}

	@Override
	public List<SubStoreItems> listSubStoreItems() {
		// TODO Auto-generated method stub
		return subStoreItemsDao.listSubStoreItems();
	}

	@Override
	public SubStoreItems getSubStoreItems(int id) {
		// TODO Auto-generated method stub
		return subStoreItemsDao.getSubStoreItems(id);
	}

	@Override
	public void deleteSubStoreItems(SubStoreItems subStoreItems) {
		// TODO Auto-generated method stub
		subStoreItemsDao.deleteSubStoreItems(subStoreItems);
	}

	@Override
	public SubStoreItems getSubStoreItemsByItemId(String itemId) {
		// TODO Auto-generated method stub
		return subStoreItemsDao.getSubStoreItemsByItemId(itemId);
	}

}
