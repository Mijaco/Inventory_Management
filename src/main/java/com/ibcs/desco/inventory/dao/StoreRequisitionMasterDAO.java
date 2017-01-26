package com.ibcs.desco.inventory.dao;

import java.util.List;

import com.ibcs.desco.inventory.model.StoreRequisitionMst;

public interface StoreRequisitionMasterDAO {

	public void addStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst);

	public void removeStoreRequisitionMst(int storeRequisitionMstId);

	public void editStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst);

	public StoreRequisitionMst getStoreRequisitionMst(int id);

	public List<StoreRequisitionMst> getAllStoreRequisitionMst();

	public StoreRequisitionMst getStoreRequisitionMstBySRFNo(String srfNo);

	public List<StoreRequisitionMst> getStoreRequisitionMstListBySRFNo(String srfNo);

	public StoreRequisitionMst getStoreRequisitionMstLastRowFromTab();

	public List<StoreRequisitionMst> getRequisitionMstListByStatus(String status);

}
