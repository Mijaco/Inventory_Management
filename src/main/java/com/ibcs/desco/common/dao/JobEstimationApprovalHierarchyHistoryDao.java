package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.JobEstimationApprovalHierarchyHistory;

public interface JobEstimationApprovalHierarchyHistoryDao {
	public List<JobEstimationApprovalHierarchyHistory> getJobEstimationApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public JobEstimationApprovalHierarchyHistory getJobEstimationApprovalHierarchyHistory(int id);
}
