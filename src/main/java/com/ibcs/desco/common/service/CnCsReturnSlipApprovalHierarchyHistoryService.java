package com.ibcs.desco.common.service;

import java.util.List;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;

public interface CnCsReturnSlipApprovalHierarchyHistoryService {
	public List<CnCsReturnSlipApprovalHierarchyHistory> getCnCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public CnCsReturnSlipApprovalHierarchyHistory getCnCsReturnSlipApprovalHierarchyHistory(
			int id);
}
