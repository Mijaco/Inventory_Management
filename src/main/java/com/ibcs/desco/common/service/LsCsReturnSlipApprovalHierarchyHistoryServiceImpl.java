package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.LsCsReturnSlipApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;

@Service
public class LsCsReturnSlipApprovalHierarchyHistoryServiceImpl implements
		LsCsReturnSlipApprovalHierarchyHistoryService {

	LsCsReturnSlipApprovalHierarchyHistoryDao lsCsReturnSlipApprovalHierarchyHistoryDao;

	public LsCsReturnSlipApprovalHierarchyHistoryDao getLsCsReturnSlipApprovalHierarchyHistoryDao() {
		return lsCsReturnSlipApprovalHierarchyHistoryDao;
	}

	public void setLsCsReturnSlipApprovalHierarchyHistoryDao(
			LsCsReturnSlipApprovalHierarchyHistoryDao lsCsReturnSlipApprovalHierarchyHistoryDao) {
		this.lsCsReturnSlipApprovalHierarchyHistoryDao = lsCsReturnSlipApprovalHierarchyHistoryDao;
	}

	@Override
	public List<LsCsReturnSlipApprovalHierarchyHistory> getLsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return lsCsReturnSlipApprovalHierarchyHistoryDao.getLsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(deptId, roleName, status);
	}

	@Override
	public LsCsReturnSlipApprovalHierarchyHistory getLsCsReturnSlipApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return lsCsReturnSlipApprovalHierarchyHistoryDao.getLsCsReturnSlipApprovalHierarchyHistory(id);
	}

}
