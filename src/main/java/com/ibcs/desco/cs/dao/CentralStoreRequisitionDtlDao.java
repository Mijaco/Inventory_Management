package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;

public interface CentralStoreRequisitionDtlDao {

	void addCentralStoreRequisition(
			CentralStoreRequisitionDtl centralStorRequisitionDtl);

	void removeCentralStoreRequisitionDetail(int id);

	CentralStoreRequisitionDtl getCentralStoreRequisitionDtl(int id);

	List<CentralStoreRequisitionDtl> getAllCentralStoreRequisitionDtl(
			int centralStoreRequisitionDtlId);
}
