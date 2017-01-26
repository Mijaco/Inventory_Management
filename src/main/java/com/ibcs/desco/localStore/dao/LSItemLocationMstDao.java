package com.ibcs.desco.localStore.dao;

import com.ibcs.desco.localStore.model.LSItemLocationMst;

public interface LSItemLocationMstDao {
	public LSItemLocationMst getLSItemLocationMst(String locationId, String ledgerName, String itemCode);
}
