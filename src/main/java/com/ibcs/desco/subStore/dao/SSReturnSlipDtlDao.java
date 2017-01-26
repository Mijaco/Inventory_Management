package com.ibcs.desco.subStore.dao;

import java.util.List;

import com.ibcs.desco.subStore.model.SSReturnSlipDtl;

public interface SSReturnSlipDtlDao {
	
	public void addSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl);

	public void removeSSReturnSlipDtl(int returnSlipDtlId);

	public void editSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl);

	public SSReturnSlipDtl getSSReturnSlipDtl(int id);

	public List<SSReturnSlipDtl> getAllSSReturnSlipDtl();
	
	public List<SSReturnSlipDtl> getAllSSReturnSlipDtlByMstId(int returnSlipMstId);
}
