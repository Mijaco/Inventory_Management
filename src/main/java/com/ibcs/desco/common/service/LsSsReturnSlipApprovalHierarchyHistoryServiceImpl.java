package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.LsSsReturnSlipApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;

@Service
public class LsSsReturnSlipApprovalHierarchyHistoryServiceImpl implements
		LsSsReturnSlipApprovalHierarchyHistoryService {

	LsSsReturnSlipApprovalHierarchyHistoryDao lsSsReturnSlipApprovalHierarchyHistoryDao;

	public LsSsReturnSlipApprovalHierarchyHistoryDao getLsSsReturnSlipApprovalHierarchyHistoryDao() {
		return lsSsReturnSlipApprovalHierarchyHistoryDao;
	}

	public void setLsSsReturnSlipApprovalHierarchyHistoryDao(
			LsSsReturnSlipApprovalHierarchyHistoryDao lsSsReturnSlipApprovalHierarchyHistoryDao) {
		this.lsSsReturnSlipApprovalHierarchyHistoryDao = lsSsReturnSlipApprovalHierarchyHistoryDao;
	}

	@Override
	public List<LsSsReturnSlipApprovalHierarchyHistory> getLsSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return lsSsReturnSlipApprovalHierarchyHistoryDao.getLsSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(deptId, roleName, status);
	}

	@Override
	public LsSsReturnSlipApprovalHierarchyHistory getLsSsReturnSlipApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return lsSsReturnSlipApprovalHierarchyHistoryDao.getLsSsReturnSlipApprovalHierarchyHistory(id);
	}

}
