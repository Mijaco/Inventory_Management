package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.ReturnSlipDtl;

public interface ReturnSlipDtlService {

	public void addReturnSlipDtl(ReturnSlipDtl returnSlipDtl);

	public void removeReturnSlipDtl(int returnSlipDtlId);

	public void editReturnSlipDtl(ReturnSlipDtl returnSlipDtl);

	public ReturnSlipDtl getReturnSlipDtl(int id);

	public List<ReturnSlipDtl> getAllReturnSlipDtl();
	
	public List<ReturnSlipDtl> getAllReturnSlipDtlByMstId(int returnSlipMstId);
}
