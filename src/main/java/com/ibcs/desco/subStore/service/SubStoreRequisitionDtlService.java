package com.ibcs.desco.subStore.service;

import java.util.List;

import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;

public interface SubStoreRequisitionDtlService {

	void addSubStoreRequisition(SubStoreRequisitionDtl subStorRequisitionDtl);

	void removeSubStoreRequisitionDetail(int id);

	SubStoreRequisitionDtl getSubStoreRequisitionDtl(int id);

	List<SubStoreRequisitionDtl> getAllSubStoreRequisitionDtl(int subStoreRequisitionDtlId);
	
	List<SubStoreRequisitionDtl> getSubStoreRequisitionDtlList(int mstId);

}
