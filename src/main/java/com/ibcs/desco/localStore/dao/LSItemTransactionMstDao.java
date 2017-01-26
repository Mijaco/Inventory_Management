package com.ibcs.desco.localStore.dao;

import com.ibcs.desco.localStore.model.LSItemTransactionMst;

public interface LSItemTransactionMstDao {

	public LSItemTransactionMst getLSItemTransectionMst(String itemCode,
			 Integer khathId, String ledgerName);

}
