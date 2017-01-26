package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.CnSsReturnSlipApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;

@Service
public class CnSsReturnSlipApprovalHierarchyHistoryServiceImpl implements
		CnSsReturnSlipApprovalHierarchyHistoryService {

	CnSsReturnSlipApprovalHierarchyHistoryDao cnSsReturnSlipApprovalHierarchyHistoryDao;

	public CnSsReturnSlipApprovalHierarchyHistoryDao getCnSsReturnSlipApprovalHierarchyHistoryDao() {
		return cnSsReturnSlipApprovalHierarchyHistoryDao;
	}

	public void setCnSsReturnSlipApprovalHierarchyHistoryDao(
			CnSsReturnSlipApprovalHierarchyHistoryDao cnSsReturnSlipApprovalHierarchyHistoryDao) {
		this.cnSsReturnSlipApprovalHierarchyHistoryDao = cnSsReturnSlipApprovalHierarchyHistoryDao;
	}

	@Override
	public List<CnSsReturnSlipApprovalHierarchyHistory> getCnSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return cnSsReturnSlipApprovalHierarchyHistoryDao.getCnSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(deptId, roleName, status);
	}

	@Override
	public CnSsReturnSlipApprovalHierarchyHistory getCnSsReturnSlipApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return cnSsReturnSlipApprovalHierarchyHistoryDao.getCnSsReturnSlipApprovalHierarchyHistory(id);
	}

}
