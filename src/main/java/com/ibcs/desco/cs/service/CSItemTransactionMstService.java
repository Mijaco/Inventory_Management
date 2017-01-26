package com.ibcs.desco.cs.service;

import com.ibcs.desco.cs.model.CSItemTransactionMst;

public interface CSItemTransactionMstService {
	public CSItemTransactionMst getCSItemTransectionMst(String itemCode,
			 Integer khathId, String ledgerName);
	public CSItemTransactionMst getCSItemTransectionMstForss(String itemCode,
			 Integer khathId);
}
