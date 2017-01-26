package com.ibcs.desco.localStore.service;

import com.ibcs.desco.localStore.model.LSItemLocationMst;

public interface LSItemLocationMstService {
	public LSItemLocationMst getLSItemLocationMst(String locationId,
			String ledgerName, String itemCode);
}
