package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CentralStoreRequisitionDtlDao;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;

@Service
public class CentralStoreRequisitionDTLServiceImpl implements CentralStoreRequisitionDtlService {

	@Autowired
	private CentralStoreRequisitionDtlDao centralStoreRequisitionDtlDao;

	@Override
	public void addCentralStoreRequisition(CentralStoreRequisitionDtl centralStorRequisitionDtl) {
		centralStoreRequisitionDtlDao.addCentralStoreRequisition(centralStorRequisitionDtl);

	}

	@Override
	public void removeCentralStoreRequisitionDetail(int id) {
		centralStoreRequisitionDtlDao.removeCentralStoreRequisitionDetail(id);

	}

	@Override
	public CentralStoreRequisitionDtl getCentralStoreRequisitionDtl(int id) {
		return centralStoreRequisitionDtlDao.getCentralStoreRequisitionDtl(id);
	}

	@Override
	public List<CentralStoreRequisitionDtl> getAllCentralStoreRequisitionDtl(int centralStoreRequisitionDtlId) {
		return centralStoreRequisitionDtlDao.getAllCentralStoreRequisitionDtl(centralStoreRequisitionDtlId);
	}

}
