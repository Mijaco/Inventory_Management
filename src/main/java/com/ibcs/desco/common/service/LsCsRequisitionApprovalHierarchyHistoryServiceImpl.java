package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.LsCsRequisitionApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;

@Service
public class LsCsRequisitionApprovalHierarchyHistoryServiceImpl implements
		LsCsRequisitionApprovalHierarchyHistoryService {

	private LsCsRequisitionApprovalHierarchyHistoryDao lsCsRequisitionApprovalHierarchyHistoryDao;

	public LsCsRequisitionApprovalHierarchyHistoryDao getLsCsRequisitionApprovalHierarchyHistoryDao() {
		return lsCsRequisitionApprovalHierarchyHistoryDao;
	}

	public void setLsCsRequisitionApprovalHierarchyHistoryDao(
			LsCsRequisitionApprovalHierarchyHistoryDao lsCsRequisitionApprovalHierarchyHistoryDao) {
		this.lsCsRequisitionApprovalHierarchyHistoryDao = lsCsRequisitionApprovalHierarchyHistoryDao;
	}

	@Override
	public List<LsCsRequisitionApprovalHierarchyHistory> getLsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return lsCsRequisitionApprovalHierarchyHistoryDao
				.getLsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public LsCsRequisitionApprovalHierarchyHistory getLsCsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return lsCsRequisitionApprovalHierarchyHistoryDao.getLsCsRequisitionApprovalHierarchyHistory(id);
	}

	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {
		// TODO Auto-generated method stub
		return lsCsRequisitionApprovalHierarchyHistoryDao.getOperationIdListByDeptIdAndStatus(deptId, status);
	}

}
