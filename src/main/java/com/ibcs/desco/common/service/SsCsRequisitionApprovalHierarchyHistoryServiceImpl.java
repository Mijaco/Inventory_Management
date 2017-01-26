package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.SsCsRequisitionApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;

@Service
public class SsCsRequisitionApprovalHierarchyHistoryServiceImpl implements
		SsCsRequisitionApprovalHierarchyHistoryService {

	private SsCsRequisitionApprovalHierarchyHistoryDao ssCsRequisitionApprovalHierarchyHistoryDao;

	public SsCsRequisitionApprovalHierarchyHistoryDao getSsCsRequisitionApprovalHierarchyHistoryDao() {
		return ssCsRequisitionApprovalHierarchyHistoryDao;
	}

	public void setSsCsRequisitionApprovalHierarchyHistoryDao(
			SsCsRequisitionApprovalHierarchyHistoryDao ssCsRequisitionApprovalHierarchyHistoryDao) {
		this.ssCsRequisitionApprovalHierarchyHistoryDao = ssCsRequisitionApprovalHierarchyHistoryDao;
	}

	@Override
	public List<SsCsRequisitionApprovalHierarchyHistory> getSsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return ssCsRequisitionApprovalHierarchyHistoryDao
				.getSsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public SsCsRequisitionApprovalHierarchyHistory getSsCsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return ssCsRequisitionApprovalHierarchyHistoryDao.getSsCsRequisitionApprovalHierarchyHistory(id);
	}
	
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {
		// TODO Auto-generated method stub
		return ssCsRequisitionApprovalHierarchyHistoryDao.getOperationIdListByDeptIdAndStatus(deptId, status);
	}
}
