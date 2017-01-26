package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.ReturnSlipMst;

public interface ReturnSlipMstService {
public void addReturnSlipMst(ReturnSlipMst returnSlipMst);
	
	public void removeReturnSlipMst(int returnSlipMstId);

	public void editReturnSlipMst(ReturnSlipMst returnSlipMst);

	public ReturnSlipMst getReturnSlipMst(int id);

	public List<ReturnSlipMst> getAllReturnSlipMst();
	
	public ReturnSlipMst getReturnSlipMstByRSNo(String rsNo);
	
	public List<ReturnSlipMst> listReturnSlipMstByOperationIds(
			String[] operationId);

	List<ReturnSlipMst> listReturnSlipMstByOperationIdList(
			List<String> operationId);
}
