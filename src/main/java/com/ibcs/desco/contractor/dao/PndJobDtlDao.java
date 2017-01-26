package com.ibcs.desco.contractor.dao;

import java.util.List;

import com.ibcs.desco.contractor.model.AsBuiltDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;

public interface PndJobDtlDao {
	
	
	List<PndJobDtl> getJobItemList(String contractNo);
	
	List<PndJobMst> getJobList();
	
	PndJobMst getJobMst(String jobNo);

	PndJobDtl getPndJobDtl(String itemCode, String contractId);

	List<PndJobDtl> getPndJobDtlList(String jobNo);

	List<AsBuiltDtl> getPndJobDtlList(String[] jobNo, String contractNo);
	
	void addPndJobMst(PndJobMst pndJobMst);
	
	void addPndJobDtl(PndJobDtl pndJobDtl);

	JobItemMaintenance getTotalJobItems(String itemCode, String contractNo);


	List<JobItemMaintenance> getJobItems(String contractNo);

	List<CnPdRequisitionDtl> getCnPdRequisitionDtlListOrderByJobNo(
			String cnReqMstId);
	
	// Added  By Ashid
	List<CnPdRequisitionMst> getCnPdRequisitionMstListByOperationIds(
			String[] operationIds);

	
}
