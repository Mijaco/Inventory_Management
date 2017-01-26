package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.KhathTransferHierarchyHistoryDao;
import com.ibcs.desco.common.model.KhathTransferApprovalHierarchyHistory;
import com.ibcs.desco.cs.model.KhathTransferMst;

@Service
public class KhathTransferHierarchyHistoryServiceImpl implements
		KhathTransferHierarchyHistoryService {
	KhathTransferHierarchyHistoryDao khathTransferHierarchyHistoryDao;

	public KhathTransferHierarchyHistoryDao getKhathTransferHierarchyHistoryDao() {
		return khathTransferHierarchyHistoryDao;
	}

	public void setKhathTransferHierarchyHistoryDao(
			KhathTransferHierarchyHistoryDao khathTransferHierarchyHistoryDao) {
		this.khathTransferHierarchyHistoryDao = khathTransferHierarchyHistoryDao;
	}

	@Override
	public List<KhathTransferApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		return khathTransferHierarchyHistoryDao
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						operationName, operationId, status);
	}

	@Override
	public List<KhathTransferMst> getKhathTransferMstListByOperationIds(
			String[] operationIds) {
		// TODO Auto-generated method stub
		return khathTransferHierarchyHistoryDao.getKhathTransferMstListByOperationIds(operationIds);
	}

}
