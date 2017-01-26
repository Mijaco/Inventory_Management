package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.StoreTicketApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;

@Service
public class StoreTicketApprovalHierarchyHistoryServiceImpl implements
		StoreTicketApprovalHierarchyHistoryService {

	StoreTicketApprovalHierarchyHistoryDao storeTicketApprovalHierarchyHistoryDao;

	public StoreTicketApprovalHierarchyHistoryDao getStoreTicketApprovalHierarchyHistoryDao() {
		return storeTicketApprovalHierarchyHistoryDao;
	}

	public void setStoreTicketApprovalHierarchyHistoryDao(
			StoreTicketApprovalHierarchyHistoryDao storeTicketApprovalHierarchyHistoryDao) {
		this.storeTicketApprovalHierarchyHistoryDao = storeTicketApprovalHierarchyHistoryDao;
	}

	@Override
	public void addApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory) {
		// TODO Auto-generated method stub
		storeTicketApprovalHierarchyHistoryDao
				.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> listStoreTicketApprovalHierarchyHistorys() {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.listStoreTicketApprovalHierarchyHistorys();
	}

	@Override
	public StoreTicketApprovalHierarchyHistory getStoreTicketApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistory(id);
	}

	@Override
	public void deleteStoreTicketApprovalHierarchyHistory(
			StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory) {
		// TODO Auto-generated method stub
		storeTicketApprovalHierarchyHistoryDao
				.deleteStoreTicketApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByRoleName(
			String actRoleName) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByRoleName(actRoleName);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStateCode(
			int stateCode) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByStateCode(stateCode);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOperationId(
			String operationId) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByOperationId(operationId);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByStatusAndStateCode(
						stateCode, status);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						operationName, operationId, status);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
						operationName, roleName, status);
	}

	@Override
	public List<StoreTicketApprovalHierarchyHistory> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
			String deptId, String roleName, String status, String ticketNo) {
		// TODO Auto-generated method stub
		return storeTicketApprovalHierarchyHistoryDao
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
						deptId, roleName, status, ticketNo);
	}

}
