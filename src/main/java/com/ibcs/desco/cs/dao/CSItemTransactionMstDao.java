package com.ibcs.desco.cs.dao;

import com.ibcs.desco.cs.model.CSItemTransactionMst;

public interface CSItemTransactionMstDao {

	public CSItemTransactionMst getCSItemTransectionMst(String itemCode,
			 Integer khathId, String ledgerName);
	public CSItemTransactionMst getCSItemTransectionMstForss(String itemCode,
			 Integer khathId);

}
