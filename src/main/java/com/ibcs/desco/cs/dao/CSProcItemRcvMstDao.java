package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CSProcItemRcvMst;

public interface CSProcItemRcvMstDao {

	public void addCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst);

	public void removeCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst);

	public void editCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst);

	public CSProcItemRcvMst getCSProcItemRcvMst(int id);

	public List<CSProcItemRcvMst> getAllCSProcItemRcvMst();

	public CSProcItemRcvMst getCSProcItemRcvMstByRrNo(String rrNo);

	// data access for get all CSProcItemRcvMst by RR No as List
	public List<CSProcItemRcvMst> listCSProcItemRcvMstByOperationIds(
			String [] operationId);
}
