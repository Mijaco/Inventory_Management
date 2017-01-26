package com.ibcs.desco.subStore.dao;

import com.ibcs.desco.subStore.model.SSItemTransactionMst;

public interface SSItemTransactionMstDao {

	public SSItemTransactionMst getSSItemTransactionMst(String itemCode,
			Integer khathId, String ledgerName);

}