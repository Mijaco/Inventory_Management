package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CentralStoreItemsDao;
import com.ibcs.desco.cs.model.CentralStoreItems;

@Service
public class CentralStoreItemsServiceImpl implements CentralStoreItemsService {

	CentralStoreItemsDao centralStoreItemsDao;

	public CentralStoreItemsDao getCentralStoreItemsDao() {
		return centralStoreItemsDao;
	}

	public void setCentralStoreItemsDao(
			CentralStoreItemsDao centralStoreItemsDao) {
		this.centralStoreItemsDao = centralStoreItemsDao;
	}

	@Override
	public void addCentralStoreItems(CentralStoreItems centralStoreItems) {
		
		centralStoreItemsDao.addCentralStoreItems(centralStoreItems);
	}

	@Override
	public List<CentralStoreItems> listCentralStoreItems() {
		
		return centralStoreItemsDao.listCentralStoreItems();
	}

	@Override
	public CentralStoreItems getCentralStoreItems(int id) {
		
		return centralStoreItemsDao.getCentralStoreItems(id);
	}

	@Override
	public void deleteCentralStoreItems(CentralStoreItems centralStoreItems) {
		
		centralStoreItemsDao.deleteCentralStoreItems(centralStoreItems);
	}

	@Override
	public CentralStoreItems getCentralStoreItemsByItemId(String itemId) {
		
		return centralStoreItemsDao.getCentralStoreItemsByItemId(itemId);
	}

}
