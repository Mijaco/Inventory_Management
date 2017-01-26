package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.SsCsReturnSlipApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;

@Service("ssCsReturnSlipApprovalHierarchyHistoryService")
public class SsCsReturnSlipApprovalHierarchyHistoryServiceImpl implements
		SsCsReturnSlipApprovalHierarchyHistoryService {

	SsCsReturnSlipApprovalHierarchyHistoryDao ssCsReturnSlipApprovalHierarchyHistoryDao;

	public SsCsReturnSlipApprovalHierarchyHistoryDao getSsCsReturnSlipApprovalHierarchyHistoryDao() {
		return ssCsReturnSlipApprovalHierarchyHistoryDao;
	}

	public void setSsCsReturnSlipApprovalHierarchyHistoryDao(
			SsCsReturnSlipApprovalHierarchyHistoryDao ssCsReturnSlipApprovalHierarchyHistoryDao) {
		this.ssCsReturnSlipApprovalHierarchyHistoryDao = ssCsReturnSlipApprovalHierarchyHistoryDao;
	}

	@Override
	public List<SsCsReturnSlipApprovalHierarchyHistory> getSsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return ssCsReturnSlipApprovalHierarchyHistoryDao.getSsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(deptId, roleName, status);
	}

	@Override
	public SsCsReturnSlipApprovalHierarchyHistory getSsCsReturnSlipApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return ssCsReturnSlipApprovalHierarchyHistoryDao.getSsCsReturnSlipApprovalHierarchyHistory(id);
	}

}
