package com.ibcs.desco.localStore.service;

import com.ibcs.desco.localStore.model.LSItemTransactionMst;

public interface LSItemTransactionMstService {
	public LSItemTransactionMst getLSItemTransectionMst(String itemCode,
			 Integer khathId, String ledgerName);
}
