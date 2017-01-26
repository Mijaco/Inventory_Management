package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.KhathTransferApprovalHierarchyHistory;
import com.ibcs.desco.cs.model.KhathTransferMst;

public interface KhathTransferHierarchyHistoryDao {
	public List<KhathTransferApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status);

	public List<KhathTransferMst> getKhathTransferMstListByOperationIds(
			String[] operationIds);

}
