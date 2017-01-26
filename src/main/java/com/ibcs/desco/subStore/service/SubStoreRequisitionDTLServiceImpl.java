package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SubStoreRequisitionDtlDao;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;

@Service
public class SubStoreRequisitionDTLServiceImpl implements SubStoreRequisitionDtlService {

	
	private SubStoreRequisitionDtlDao subStoreRequisitionDtlDao;

	public SubStoreRequisitionDtlDao getSubStoreRequisitionDtlDao() {
		return subStoreRequisitionDtlDao;
	}

	public void setSubStoreRequisitionDtlDao(SubStoreRequisitionDtlDao subStoreRequisitionDtlDao) {
		this.subStoreRequisitionDtlDao = subStoreRequisitionDtlDao;
	}

	@Override
	public void addSubStoreRequisition(SubStoreRequisitionDtl subStoreRequisitionDtl) {
		subStoreRequisitionDtlDao.addSubStoreRequisition(subStoreRequisitionDtl);

	}

	@Override
	public void removeSubStoreRequisitionDetail(int id) {
		subStoreRequisitionDtlDao.removeSubStoreRequisitionDetail(id);

	}

	@Override
	public SubStoreRequisitionDtl getSubStoreRequisitionDtl(int id) {
		return subStoreRequisitionDtlDao.getSubStoreRequisitionDtl(id);
	}

	@Override
	public List<SubStoreRequisitionDtl> getAllSubStoreRequisitionDtl(int subStoreRequisitionDtlId) {
		return subStoreRequisitionDtlDao.getAllSubStoreRequisitionDtl(subStoreRequisitionDtlId);
	}

	@Override
	public List<SubStoreRequisitionDtl> getSubStoreRequisitionDtlList(int mstId) {
		return subStoreRequisitionDtlDao.getSubStoreRequisitionDtlList(mstId);
	}

}
