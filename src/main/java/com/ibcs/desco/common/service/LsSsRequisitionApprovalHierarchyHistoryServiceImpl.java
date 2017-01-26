package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.LsSsRequisitionApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;

@Service
public class LsSsRequisitionApprovalHierarchyHistoryServiceImpl implements
		LsSsRequisitionApprovalHierarchyHistoryService {

	private LsSsRequisitionApprovalHierarchyHistoryDao lsSsRequisitionApprovalHierarchyHistoryDao;

	public LsSsRequisitionApprovalHierarchyHistoryDao getLsSsRequisitionApprovalHierarchyHistoryDao() {
		return lsSsRequisitionApprovalHierarchyHistoryDao;
	}

	public void setLsSsRequisitionApprovalHierarchyHistoryDao(
			LsSsRequisitionApprovalHierarchyHistoryDao LsSsRequisitionApprovalHierarchyHistoryDao) {
		this.lsSsRequisitionApprovalHierarchyHistoryDao = LsSsRequisitionApprovalHierarchyHistoryDao;
	}

	@Override
	public List<LsSsRequisitionApprovalHierarchyHistory> getLsSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return lsSsRequisitionApprovalHierarchyHistoryDao
				.getLsSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public LsSsRequisitionApprovalHierarchyHistory getLsSsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return lsSsRequisitionApprovalHierarchyHistoryDao.getLsSsRequisitionApprovalHierarchyHistory(id);
	}
	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status) {
		// TODO Auto-generated method stub
		return lsSsRequisitionApprovalHierarchyHistoryDao.getOperationIdListByDeptIdAndStatus(deptId, status);
	}
	
	@Override
	public List<String> getOperationIdListByDeptIdAndStatusLSPDSS(String deptId,
			String status) {
		// TODO Auto-generated method stub
		return lsSsRequisitionApprovalHierarchyHistoryDao.getOperationIdListByDeptIdAndStatusLSPDSS(deptId, status);
	}

}
