package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CentralStoreRequisitionMstDao;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;

@Service
public class CentralStoreRequisitionMstServiceImpl implements CentralStoreRequisitionMstService {

	@Autowired
	private CentralStoreRequisitionMstDao centralStoreRequisitionMstDao;

	@Override
	public void addCentralStoreRequisitionMst(CentralStoreRequisitionMst centralStoreRequisitionMst) {
		centralStoreRequisitionMstDao.addCentralStoreRequisitionMst(centralStoreRequisitionMst);

	}

	@Override
	public void removeCentralStoreRequisitionMst(int centralStoreRequisitionMstId) {
		centralStoreRequisitionMstDao.removeCentralStoreRequisitionMst(centralStoreRequisitionMstId);

	}

	@Override
	public void editCentralStoreRequisitionMst(CentralStoreRequisitionMst centralStoreRequisitionMst) {
		centralStoreRequisitionMstDao.editCentralStoreRequisitionMst(centralStoreRequisitionMst);

	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequisitionMst(int id) {
		return centralStoreRequisitionMstDao.getCentralStoreRequisitionMst(id);
	}

	@Override
	public List<CentralStoreRequisitionMst> getAllCentralStoreRequisitionMst() {
		return centralStoreRequisitionMstDao.getAllCentralStoreRequisitionMst();
	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequistionMstByRequisitionNo(String requisitionNo) {
		return centralStoreRequisitionMstDao.getCentralStoreRequistionMstByRequisitionNo(requisitionNo);
	}

	@Override
	public List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByRequisitionNo(String requisitionNo) {
		return centralStoreRequisitionMstDao.getCentralStoreRequisitionMstListByRequisitionNo(requisitionNo);
	}

	@Override
	public CentralStoreRequisitionMst getCentralStoreRequisitionMstLastRowFromTab() {
		return centralStoreRequisitionMstDao.getCentralStoreRequisitionMstLastRowFromTab();
	}

	@Override
	public List<CentralStoreRequisitionMst> getCentralStoreRequisitionMstListByStatus(String status) {
		return centralStoreRequisitionMstDao.getCentralStoreRequisitionMstListByStatus(status);
	}

	@Override
	public List<CentralStoreRequisitionMst> listCentralStoreRequisitionMstByOperationIds(
			String [] operationId) {
		// TODO Auto-generated method stub
		return centralStoreRequisitionMstDao.listCentralStoreRequisitionMstByOperationIds(operationId);
	}

}
