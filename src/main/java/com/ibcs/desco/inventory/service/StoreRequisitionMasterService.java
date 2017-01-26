package com.ibcs.desco.inventory.service;

import java.util.List;

import com.ibcs.desco.inventory.model.StoreRequisitionMst;

public interface StoreRequisitionMasterService {

	public void addStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst);

	public void storeRemoveRequisitionMst(int storeRequisitionMstId);

	public void editStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst);

	public StoreRequisitionMst getStoreRequisitionMst(int id);

	public List<StoreRequisitionMst> getAllStoreRequisitionMst();
	
	public StoreRequisitionMst getRequisitionMstBySRFNo(String srfNo);
	
	public List<StoreRequisitionMst> getStoreRequisitionMstListBySRFNo(String srfNo);
	
	public StoreRequisitionMst getStoreRequisitionMstLastRowFromTab();
	
	public List<StoreRequisitionMst> getStoreRequisitionMstListByStatus(String status);

}
