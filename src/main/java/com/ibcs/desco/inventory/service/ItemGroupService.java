package com.ibcs.desco.inventory.service;

import java.util.List;

import com.ibcs.desco.inventory.model.ItemCategory;

public interface ItemGroupService {

	void addItemGroup(ItemCategory itemCategory);

	List<ItemCategory> getAllItemGroups();

	void deleteItemGroup();

	ItemCategory getGetItemGroupById(int id);

	void editItemGroup(ItemCategory itemCategory);

	List<ItemCategory> getItemGroupByGroupName(String itemCategoryName);

	boolean checkItemGroupName(String itemCategoryName);

	List<ItemCategory> getGeneralItemGroups();

	List<ItemCategory> getConstructionItemGroups();

}
