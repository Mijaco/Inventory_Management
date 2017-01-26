package com.ibcs.desco.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.inventory.dao.ItemGroupDao;
import com.ibcs.desco.inventory.model.ItemCategory;

@Service
public class ItemGroupServiceImpl implements ItemGroupService {

	@Autowired
	ItemGroupDao itemGroupDao;

	@Override
	public void addItemGroup(ItemCategory itemCategory) {
		itemGroupDao.addItemGroup(itemCategory);
	}

	@Override
	public List<ItemCategory> getAllItemGroups() {
		return itemGroupDao.getAllItemGroups();
	}
	
	@Override
	public List<ItemCategory> getGeneralItemGroups() {
		return itemGroupDao.getGeneralItemGroups();
	}
	
	@Override
	public List<ItemCategory> getConstructionItemGroups() {
		return itemGroupDao.getConstructionItemGroups();
	}

	@Override
	public void deleteItemGroup() {

	}

	@Override
	public ItemCategory getGetItemGroupById(int id) {
		return itemGroupDao.getGetItemGroupById(id);
	}

	@Override
	public void editItemGroup(ItemCategory itemCategory) {
		itemGroupDao.editItemGroup(itemCategory);

	}

	@Override
	public List<ItemCategory> getItemGroupByGroupName(String itemCategoryName) {
		return itemGroupDao.getItemGroupByGroupName(itemCategoryName);
	}

	@Override
	public boolean checkItemGroupName(String itemCategoryName) {
		return itemGroupDao.checkItemGroupName(itemCategoryName);
	}

}
