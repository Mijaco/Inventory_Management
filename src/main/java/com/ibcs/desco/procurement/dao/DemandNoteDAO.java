package com.ibcs.desco.procurement.dao;

import java.util.List;

import com.ibcs.desco.procurement.model.DemandNoteDtl;

public interface DemandNoteDAO {
	public List<DemandNoteDtl> getDemandNoteSummarybyMstIdColumnValueList( List<String> columnValues);
	
	public List<DemandNoteDtl> getDemandNoteCodedSummary(String sessionId, String itemType);
}
