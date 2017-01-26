package com.ibcs.desco.subStore.service;
//@author nasrin
import java.util.List;

import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

public interface SubStoreRequisitionMstService {

	void addSubStoreRequisitionMst(
			SubStoreRequisitionMst subStoreRequisitionMst);

	void removeSubStoreRequisitionMst(int subStoreRequisitionMstId);

	void editSubStoreRequisitionMst(
			SubStoreRequisitionMst subStoreRequisitionMst);

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
