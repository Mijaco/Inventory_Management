package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;

public interface CnCsReturnSlipApprovalHierarchyHistoryDao {
	public List<CnCsReturnSlipApprovalHierarchyHistory> getCnCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public CnCsReturnSlipApprovalHierarchyHistory getCnCsReturnSlipApprovalHierarchyHistory(int id);
}
