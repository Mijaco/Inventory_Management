package com.ibcs.desco.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibcs.desco.inventory.dao.StoreRequisitionDetailsDAO;
import com.ibcs.desco.inventory.model.StoreRequisionDetail1;
import com.ibcs.desco.inventory.model.StoreRequisiotionDetail2;

@Service
public class StoreRequisitionDetailsServiceImpl implements StoreRequisitionDetailsService {

	@Autowired
	private StoreRequisitionDetailsDAO storeRequisitionDetailsDAO;

	@Override
	@Transactional
	public void addStoreRequisitionDetail(StoreRequisionDetail1 storeRequisitionDtl) {
		// TODO Auto-generated method stub
		storeRequisitionDetailsDAO.addStoreRequisitionDetail(storeRequisitionDtl);

	}

	@Override
	@Transactional
	public void removeStireRequisitionDetail(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void editStoreRequisitionDetail(StoreRequisiotionDetail2 storeRequisitionDt2) {
		storeRequisitionDetailsDAO.editStoreRequisitionDetail(storeRequisitionDt2);
	}

	@Override
	@Transactional
	public StoreRequisionDetail1 getStoreRequisitionDetail(int id) {
		return storeRequisitionDetailsDAO.getRequisitionDetail(id);
	}

	@Override
	@Transactional
	public List<StoreRequisionDetail1> getAllStoreRequisitionDetail(int storeRequisitionMstId) {
		return storeRequisitionDetailsDAO.getAllStoreRequisitionDetail(storeRequisitionMstId);
	}

}
