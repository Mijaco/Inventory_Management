package com.ibcs.desco.subStore.service;

import com.ibcs.desco.subStore.model.SSItemLocationMst;

public interface SSItemLocationMstService {
	public SSItemLocationMst getSSItemLocationMst(String locationId,
			String ledgerName, String itemCode);
}
