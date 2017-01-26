package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.CnCsReturnSlipApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;

@Service
public class CnCsReturnSlipApprovalHierarchyHistoryServiceImpl implements
	CnCsReturnSlipApprovalHierarchyHistoryService {

	CnCsReturnSlipApprovalHierarchyHistoryDao cnCsReturnSlipApprovalHierarchyHistoryDao;
	
	public CnCsReturnSlipApprovalHierarchyHistoryDao getCnCsReturnSlipApprovalHierarchyHistoryDao() {
		return cnCsReturnSlipApprovalHierarchyHistoryDao;
	}

	public void CnCsReturnSlipApprovalHierarchyHistoryDao(
			CnCsReturnSlipApprovalHierarchyHistoryDao cnCsReturnSlipApprovalHierarchyHistoryDao) {
		this.cnCsReturnSlipApprovalHierarchyHistoryDao = cnCsReturnSlipApprovalHierarchyHistoryDao;
	}

	@Override
	public List<CnCsReturnSlipApprovalHierarchyHistory> getCnCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		return cnCsReturnSlipApprovalHierarchyHistoryDao.getCnCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(deptId, roleName, status);
	}

	@Override
	public CnCsReturnSlipApprovalHierarchyHistory getCnCsReturnSlipApprovalHierarchyHistory(
			int id) {
		return cnCsReturnSlipApprovalHierarchyHistoryDao.getCnCsReturnSlipApprovalHierarchyHistory(id);
	}
}
