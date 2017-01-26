package com.ibcs.desco.subStore.service;

import com.ibcs.desco.subStore.model.SSItemTransactionMst;

public interface SSItemTransactionMstService {
	public SSItemTransactionMst getSSItemTransactionMst(String itemCode,
			Integer khathId, String ledgerName);
}
