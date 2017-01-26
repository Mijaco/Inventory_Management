package com.ibcs.desco.procurement.service;

import java.util.List;

import com.ibcs.desco.procurement.model.DemandNoteDtl;

public interface DemandNoteService {
	public List<DemandNoteDtl> getDemandNoteSummarybyMstIdColumnValueList( List<String> columnValues);
	
	public List<DemandNoteDtl> getDemandNoteCodedSummary(String sessionId, String itemType);
}
