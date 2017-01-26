package com.ibcs.desco.contractor.service;


import java.util.List;

import com.ibcs.desco.contractor.model.AsBuiltMst;
import com.ibcs.desco.contractor.model.CostEstimateInstallationDtl;
import com.ibcs.desco.contractor.model.CostEstimateMaterialsDtl;
import com.ibcs.desco.contractor.model.CostEstimateMiscellaniousDtl;
import com.ibcs.desco.contractor.model.CostEstimateRecoveryDtl;
import com.ibcs.desco.contractor.model.CostEstimationMst;


public interface JobTemplateDtlService {
	
	void addJobTemplateMst(CostEstimationMst jobTemplateMst);
	
	void addJobMatDtl(CostEstimateMaterialsDtl jobTemplateDtl);
	
	void addJobInsDtl(CostEstimateInstallationDtl jobTemplateDtl);
	
	void addJobRecDtl(CostEstimateRecoveryDtl jobTemplateDtl);
	
	void addJobMiscDtl(CostEstimateMiscellaniousDtl jobTemplateDtl);
	
	List<CostEstimateMaterialsDtl> getJobTemplateDtl(int templateId);
	
	CostEstimationMst getJobTemplateMst(int templateId);
	
	List<CostEstimateMaterialsDtl> getJobMaterialsDtl(String pndNo);
	
	List<CostEstimateInstallationDtl> getJobInstallationDtl(String pndNo);
	
	List<CostEstimateRecoveryDtl> getJobRecoveryDtl(String pndNo);
	
	List<CostEstimateMiscellaniousDtl> getJobMiscellaniousDtl(String pndNo);
		
	List<CostEstimationMst> getJobTemplateMst();
	
	List<CostEstimationMst> getCostEstimationMstByAssignFlag();
	
	List<CostEstimationMst> listCostEstimationMstByOperationIds(
			String [] pndNo);
	
	List<AsBuiltMst> listAsBuiltMstByOperationIds(
			String [] id);
}
