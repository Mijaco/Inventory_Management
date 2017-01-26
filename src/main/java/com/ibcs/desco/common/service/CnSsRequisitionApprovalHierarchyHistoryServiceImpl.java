package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.CnSsRequisitionApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;

@Service
public class CnSsRequisitionApprovalHierarchyHistoryServiceImpl implements
		CnSsRequisitionApprovalHierarchyHistoryService {

	private CnSsRequisitionApprovalHierarchyHistoryDao cnSsRequisitionApprovalHierarchyHistoryDao;

	public CnSsRequisitionApprovalHierarchyHistoryDao getCnSsRequisitionApprovalHierarchyHistoryDao() {
		return cnSsRequisitionApprovalHierarchyHistoryDao;
	}

	public void setCnSsRequisitionApprovalHierarchyHistoryDao(
			CnSsRequisitionApprovalHierarchyHistoryDao CnSsRequisitionApprovalHierarchyHistoryDao) {
		this.cnSsRequisitionApprovalHierarchyHistoryDao = CnSsRequisitionApprovalHierarchyHistoryDao;
	}

	@Override
	public List<CnSsRequisitionApprovalHierarchyHistory> getCnSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return cnSsRequisitionApprovalHierarchyHistoryDao
				.getCnSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public CnSsRequisitionApprovalHierarchyHistory getCnSsRequisitionApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return cnSsRequisitionApprovalHierarchyHistoryDao.getCnSsRequisitionApprovalHierarchyHistory(id);
	}

}
