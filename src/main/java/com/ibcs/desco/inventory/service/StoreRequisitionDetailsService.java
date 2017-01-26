package com.ibcs.desco.inventory.service;

import java.util.List;

import com.ibcs.desco.inventory.model.StoreRequisionDetail1;
import com.ibcs.desco.inventory.model.StoreRequisiotionDetail2;

public interface StoreRequisitionDetailsService {

	public void addStoreRequisitionDetail(StoreRequisionDetail1 storeRequisitionDtl);

	public void removeStireRequisitionDetail(int id);

	public void editStoreRequisitionDetail(StoreRequisiotionDetail2 storeRequisitionDtl);

	public StoreRequisionDetail1 getStoreRequisitionDetail(int id);

	public List<StoreRequisionDetail1> getAllStoreRequisitionDetail(int storeRequisitionMstId);

}
