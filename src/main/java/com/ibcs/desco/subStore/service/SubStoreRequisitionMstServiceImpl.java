package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SubStoreRequisitionMstDao;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

@Service
public class SubStoreRequisitionMstServiceImpl implements SubStoreRequisitionMstService {

	
	private SubStoreRequisitionMstDao SubStoreRequisitionMstDao;

	public SubStoreRequisitionMstDao getSubStoreRequisitionMstDao() {
		return SubStoreRequisitionMstDao;
	}

	public void setSubStoreRequisitionMstDao(SubStoreRequisitionMstDao subStoreRequisitionMstDao) {
		SubStoreRequisitionMstDao = subStoreRequisitionMstDao;
	}

	@Override
	public void addSubStoreRequisitionMst(SubStoreRequisitionMst SubStoreRequisitionMst) {
		SubStoreRequisitionMstDao.addSubStoreRequisitionMst(SubStoreRequisitionMst);

	}

	@Override
	public void removeSubStoreRequisitionMst(int SubStoreRequisitionMstId) {
		SubStoreRequisitionMstDao.removeSubStoreRequisitionMst(SubStoreRequisitionMstId);

	}

	@Override
	public void editSubStoreRequisitionMst(SubStoreRequisitionMst SubStoreRequisitionMst) {
		SubStoreRequisitionMstDao.editSubStoreRequisitionMst(SubStoreRequisitionMst);

	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequisitionMst(int id) {
		return SubStoreRequisitionMstDao.getSubStoreRequisitionMst(id);
	}

	@Override
	public List<SubStoreRequisitionMst> getAllSubStoreRequisitionMst() {
		return SubStoreRequisitionMstDao.getAllSubStoreRequisitionMst();
	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequistionMstByRequisitionNo(String requisitionNo) {
		return SubStoreRequisitionMstDao.getSubStoreRequistionMstByRequisitionNo(requisitionNo);
	}

	@Override
	public List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByRequisitionNo(String requisitionNo) {
		return SubStoreRequisitionMstDao.getSubStoreRequisitionMstListByRequisitionNo(requisitionNo);
	}

	@Override
	public SubStoreRequisitionMst getSubStoreRequisitionMstLastRowFromTab() {
		return SubStoreRequisitionMstDao.getSubStoreRequisitionMstLastRowFromTab();
	}

	@Override
	public List<SubStoreRequisitionMst> getSubStoreRequisitionMstListByStatus(String status) {
		return SubStoreRequisitionMstDao.getSubStoreRequisitionMstListByStatus(status);
	}

	@Override
	public List<SubStoreRequisitionMst> listSubStoreRequisitionMstByOperationIds(
			String [] operationId) {
		// TODO Auto-generated method stub
		return SubStoreRequisitionMstDao.listSubStoreRequisitionMstByOperationIds(operationId);
	}

}
