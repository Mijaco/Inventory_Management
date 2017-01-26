package com.ibcs.desco.subStore.service;

import java.util.List;

import com.ibcs.desco.subStore.model.SSReturnSlipMst;

public interface SSReturnSlipMstService {
public void addSSReturnSlipMst(SSReturnSlipMst returnSlipMst);
	
	public void removeSSReturnSlipMst(int returnSlipMstId);

	public void editSSReturnSlipMst(SSReturnSlipMst returnSlipMst);

	public SSReturnSlipMst getSSReturnSlipMst(int id);

	public List<SSReturnSlipMst> getAllSSReturnSlipMst();
	
	public SSReturnSlipMst getSSReturnSlipMstByRSNo(String rsNo);
	
	public List<SSReturnSlipMst> listSSReturnSlipMstByOperationIds(
			String[] operationId);

	List<SSReturnSlipMst> listSSReturnSlipMstByOperationIdList(
			List<String> operationId);
}
