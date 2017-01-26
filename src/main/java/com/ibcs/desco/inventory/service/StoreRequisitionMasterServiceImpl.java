package com.ibcs.desco.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibcs.desco.inventory.dao.StoreRequisitionMasterDAO;
import com.ibcs.desco.inventory.model.StoreRequisitionMst;

@Service
public class StoreRequisitionMasterServiceImpl implements StoreRequisitionMasterService {

	@Autowired
	private StoreRequisitionMasterDAO storeRequisitionMasterDAO;

	@Override
	@Transactional
	public void addStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst) {
		// TODO Auto-generated method stub
		storeRequisitionMasterDAO.addStoreRequisitionMst(storeRequisitionMst);

	}

	@Override
	@Transactional
	public void storeRemoveRequisitionMst(int storeRequisitionMstId) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void editStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst) {
		// TODO Auto-generated method stub
		storeRequisitionMasterDAO.editStoreRequisitionMst(storeRequisitionMst);
	}

	@Override
	@Transactional
	public StoreRequisitionMst getStoreRequisitionMst(int id) {
		return storeRequisitionMasterDAO.getStoreRequisitionMst(id);
	}

	@Override
	@Transactional
	public List<StoreRequisitionMst> getAllStoreRequisitionMst() {
		return storeRequisitionMasterDAO.getAllStoreRequisitionMst();
	}

	@Override
	@Transactional
	public StoreRequisitionMst getRequisitionMstBySRFNo(String srfNo) {
		return storeRequisitionMasterDAO.getStoreRequisitionMstBySRFNo(srfNo);
	}

	@Override
	@Transactional
	public List<StoreRequisitionMst> getStoreRequisitionMstListBySRFNo(String srfNo) {
		return storeRequisitionMasterDAO.getStoreRequisitionMstListBySRFNo(srfNo);
	}

	@Override
	@Transactional
	public StoreRequisitionMst getStoreRequisitionMstLastRowFromTab() {
		return this.storeRequisitionMasterDAO.getStoreRequisitionMstLastRowFromTab();
	}

	@Override
	public List<StoreRequisitionMst> getStoreRequisitionMstListByStatus(String status) {
		return storeRequisitionMasterDAO.getRequisitionMstListByStatus(status);
	}

}
