package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;

public interface CentralStoreRequisitionMstDao {

	void addCentralStoreRequisitionMst(
			CentralStoreRequisitionMst centralStoreRequisitionMst);

	void removeCentralStoreRequisitionMst(int centralStoreRequisitionMstId);

	void editCentralStoreRequisitionMst(
			CentralStoreRequisitionMst centralStoreRequisitionMst);

	CentralStoreRequisitionMst getCentralStoreRequisitionMst(int id);

	List<CentralStoreRequisitionMst> getAllCentralStoreRequisitionMst();

	CentralStoreRequisitionMst getCentralStoreRequistionMstByRequisitionNo(
			String requisitionNo);

	List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByRequisitionNo(
			String requisitionNo);

	CentralStoreRequisitionMst getCentralStoreRequisitionMstLastRowFromTab();

	List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByStatus(
			String status);

	// data access for get all CentralStoreRequisitionMst by RR No as List
	public List<CentralStoreRequisitionMst> listCentralStoreRequisitionMstByOperationIds(
			String [] operationId);

}
