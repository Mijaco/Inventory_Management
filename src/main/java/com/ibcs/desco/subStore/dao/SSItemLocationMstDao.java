package com.ibcs.desco.subStore.dao;

import com.ibcs.desco.subStore.model.SSItemLocationMst;

public interface SSItemLocationMstDao {
	public SSItemLocationMst getSSItemLocationMst(String locationId, String ledgerName, String itemCode);
}
