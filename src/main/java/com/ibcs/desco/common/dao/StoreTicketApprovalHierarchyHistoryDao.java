package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;

public interface StoreTicketApprovalHierarchyHistoryDao {
	// data access for add new StoreTicketApprovalHierarchyHistory
	public void addApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory);

	// data access for get all StoreTicketApprovalHierarchyHistory as List
	public List<StoreTicketApprovalHierarchyHistory> listStoreTicketApprovalHierarchyHistorys();

	// data access for get specific one StoreTicketApprovalHierarchyHistory
	// information and
	// update
	// StoreTicketApprovalHierarchyHistory info
	public StoreTicketApprovalHierarchyHistory getStoreTicketApprovalHierarchyHistory(
			int id);

	// data access for Delete an StoreTicketApprovalHierarchyHistory
	public void deleteStoreTicketApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByRoleName(
			String actRoleName);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStateCode(
			int stateCode);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOperationId(
			String operationId);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status);

	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status);

	List<StoreTicketApprovalHierarchyHistory> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
			String deptId, String roleName, String status, String ticketNo);
}
