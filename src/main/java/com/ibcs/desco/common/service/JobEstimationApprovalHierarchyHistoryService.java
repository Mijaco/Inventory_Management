package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.JobEstimationApprovalHierarchyHistory;

public interface JobEstimationApprovalHierarchyHistoryService {
	public List<JobEstimationApprovalHierarchyHistory> getJobEstimationApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public JobEstimationApprovalHierarchyHistory getJobEstimationApprovalHierarchyHistory(
			int id);
}
