package com.ibcs.desco.inventory.dao;

import java.util.List;

import com.ibcs.desco.inventory.model.StoreRequisionDetail1;
import com.ibcs.desco.inventory.model.StoreRequisiotionDetail2;

public interface StoreRequisitionDetailsDAO {

	public void addStoreRequisitionDetail(StoreRequisionDetail1 storeRequisitionDtl1);

	public void removeStoreRequisitionDetail(int id);

	public void editStoreRequisitionDetail(StoreRequisiotionDetail2 storeRequisitionDtl2);

	public StoreRequisionDetail1 getRequisitionDetail(int id);

	public List<StoreRequisionDetail1> getAllStoreRequisitionDetail(int storeRequisitionMstId);

}
