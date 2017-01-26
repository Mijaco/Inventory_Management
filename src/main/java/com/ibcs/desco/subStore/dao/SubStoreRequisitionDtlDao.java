package com.ibcs.desco.subStore.dao;

import java.util.List;

import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;

public interface SubStoreRequisitionDtlDao {

	void addSubStoreRequisition(
			SubStoreRequisitionDtl centralStorRequisitionDtl);

	void removeSubStoreRequisitionDetail(int id);

	SubStoreRequisitionDtl getSubStoreRequisitionDtl(int id);

	List<SubStoreRequisitionDtl> getAllSubStoreRequisitionDtl(
			int subStoreRequisitionDtlId);
	
	List<SubStoreRequisitionDtl> getSubStoreRequisitionDtlList(int mstId);
}
