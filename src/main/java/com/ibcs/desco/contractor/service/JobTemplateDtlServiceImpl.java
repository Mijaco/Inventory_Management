package com.ibcs.desco.contractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.contractor.dao.JobTemplateDtlDao;
import com.ibcs.desco.contractor.model.AsBuiltMst;
import com.ibcs.desco.contractor.model.CostEstimateInstallationDtl;
import com.ibcs.desco.contractor.model.CostEstimateMaterialsDtl;
import com.ibcs.desco.contractor.model.CostEstimateMiscellaniousDtl;
import com.ibcs.desco.contractor.model.CostEstimateRecoveryDtl;
import com.ibcs.desco.contractor.model.CostEstimationMst;

@Service
public class JobTemplateDtlServiceImpl implements JobTemplateDtlService {

	@Autowired
	JobTemplateDtlDao jobTemplateDtlDao;

	public JobTemplateDtlDao getJobTemplateDtlDao() {
		return jobTemplateDtlDao;
	}

	public void setJobTemplateDtlDao(JobTemplateDtlDao jobTemplateDtlDao) {
		this.jobTemplateDtlDao = jobTemplateDtlDao;
	}
	
	public void addJobTemplateMst(CostEstimationMst jobTemplateMst){
		
		jobTemplateDtlDao.addJobTemplateMst(jobTemplateMst);
	}
	
	public void addJobMatDtl(CostEstimateMaterialsDtl jobTemplateDtl){
		
		jobTemplateDtlDao.addJobMatDtl(jobTemplateDtl);
	}
	
	public void addJobInsDtl(CostEstimateInstallationDtl jobTemplateDtl){
		
		jobTemplateDtlDao.addJobInsDtl(jobTemplateDtl);
	}
	
	public void addJobRecDtl(CostEstimateRecoveryDtl jobTemplateDtl){
		
		jobTemplateDtlDao.addJobRecDtl(jobTemplateDtl);
	}
	
	public void addJobMiscDtl(CostEstimateMiscellaniousDtl jobTemplateDtl){
		
		jobTemplateDtlDao.addJobMiscDtl(jobTemplateDtl);
	}
		
	public List<CostEstimateMaterialsDtl> getJobTemplateDtl(int templateId){
		
		return jobTemplateDtlDao.getJobTemplateDtl(templateId);
	}
	
	public CostEstimationMst getJobTemplateMst(int templateId){
	
	return jobTemplateDtlDao.getJobTemplateMst(templateId);
	}
	
	public List<CostEstimateMaterialsDtl> getJobMaterialsDtl(String pndNo){
		
		return jobTemplateDtlDao.getJobMaterialsDtl(pndNo);
	}
	
	public List<CostEstimateInstallationDtl> getJobInstallationDtl(String pndNo){
		
		return jobTemplateDtlDao.getJobInstallationDtl(pndNo);
	}
	
	public List<CostEstimateRecoveryDtl> getJobRecoveryDtl(String pndNo){
		
		return jobTemplateDtlDao.getJobRecoveryDtl(pndNo);
	}
	
	public List<CostEstimateMiscellaniousDtl> getJobMiscellaniousDtl(String pndNo){
		
		return jobTemplateDtlDao.getJobMiscellaniousDtl(pndNo);
	}
	
	public List<CostEstimationMst> getJobTemplateMst(){
		
		return jobTemplateDtlDao.getJobTemplateMst();		
	}
	
	public List<CostEstimationMst> getCostEstimationMstByAssignFlag(){
		
		return jobTemplateDtlDao.getCostEstimationMstByAssignFlag();		
	}
	
	public List<CostEstimationMst> listCostEstimationMstByOperationIds(String [] pndNo){
		
		return jobTemplateDtlDao.listCostEstimationMstByOperationIds(pndNo);		
	}
	
	public List<AsBuiltMst> listAsBuiltMstByOperationIds(String [] id){
		
		return jobTemplateDtlDao.listAsBuiltMstByOperationIds(id);		
	}

	
}
