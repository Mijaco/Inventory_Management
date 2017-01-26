package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;

public interface ItemRcvApprovalHierarchyHistoryService {
	// data access for add new ApprovalHierarchyHistory
	public void addApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory);

	// data access for get all ApprovalHierarchyHistory as List
	public List<ItemRcvApprovalHierarchyHistory> listApprovalHierarchyHistorys();

	// data access for get specific one ApprovalHierarchyHistory information and
	// update
	// ApprovalHierarchyHistory info
	public ItemRcvApprovalHierarchyHistory getApprovalHierarchyHistory(int id);

	// data access for Delete an ApprovalHierarchyHistory
	public void deleteApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory);

	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByRoleName(
			String actRoleName);

	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStateCode(
			int stateCode);
	
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOperationId(
			String operationId);
	
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status);
	
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status);
	
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status);
}
