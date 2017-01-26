package com.ibcs.desco.subStore.dao;

import java.util.List;

import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

public interface SubStoreRequisitionMstDao {

	void addSubStoreRequisitionMst(
			SubStoreRequisitionMst SubStoreRequisitionMst);

	void removeSubStoreRequisitionMst(int SubStoreRequisitionMstId);

	void editSubStoreRequisitionMst(
			SubStoreRequisitionMst SubStoreRequisitionMst);

	SubStoreRequisitionMst getSubStoreRequisitionMst(int id);

	List<SubStoreRequisitionMst> getAllSubStoreRequisitionMst();

	SubStoreRequisitionMst getSubStoreRequistionMstByRequisitionNo(
			String requisitionNo);

	List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByRequisitionNo(
			String requisitionNo);

	SubStoreRequisitionMst getSubStoreRequisitionMstLastRowFromTab();

	List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByStatus(
			String status);

	// data access for get all SubStoreRequisitionMst by RR No as List
	public List<SubStoreRequisitionMst> listSubStoreRequisitionMstByOperationIds(
			String [] operationId);

}
