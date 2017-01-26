package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.GatePassApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;

@Service
public class GatePassApprovalHierarchyHistoryServiceImpl implements
		GatePassApprovalHierarchyHistoryService {

	GatePassApprovalHierarchyHistoryDao gatePassApprovalHierarchyHistoryDao;

	public GatePassApprovalHierarchyHistoryDao getGatePassApprovalHierarchyHistoryDao() {
		return gatePassApprovalHierarchyHistoryDao;
	}

	public void setGatePassApprovalHierarchyHistoryDao(
			GatePassApprovalHierarchyHistoryDao gatePassApprovalHierarchyHistoryDao) {
		this.gatePassApprovalHierarchyHistoryDao = gatePassApprovalHierarchyHistoryDao;
	}

	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return gatePassApprovalHierarchyHistoryDao
				.getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public GatePassApprovalHierarchyHistory getGatePassApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return gatePassApprovalHierarchyHistoryDao
				.getGatePassApprovalHierarchyHistory(id);
	}

	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		return gatePassApprovalHierarchyHistoryDao
				.getGatePassApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						operationName, operationId, status);
	}

	@Override
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRoleAndGPNo(
			String deptId, String roleName, String status, String gpNo) {
		// TODO Auto-generated method stub
		return gatePassApprovalHierarchyHistoryDao
				.getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRoleAndGPNo(
						deptId, roleName, status, gpNo);
	}

}
