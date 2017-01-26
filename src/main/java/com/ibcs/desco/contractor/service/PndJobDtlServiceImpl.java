package com.ibcs.desco.contractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.contractor.dao.PndJobDtlDao;
import com.ibcs.desco.contractor.model.AsBuiltDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;

@Service
public class PndJobDtlServiceImpl implements PndJobDtlService {

	@Autowired
	PndJobDtlDao pndJobDtlDao;

	public PndJobDtlDao getPndJobDtlDao() {
		return pndJobDtlDao;
	}

	public void setPndJobDtlDao(PndJobDtlDao pndJobDtlDao) {
		this.pndJobDtlDao = pndJobDtlDao;
	}

	public List<PndJobDtl> getJobItemList(String contractNo) {
		return pndJobDtlDao.getJobItemList(contractNo);
	}

	public PndJobDtl getPndJobDtl(String itemCode, String contractId) {
		return pndJobDtlDao.getPndJobDtl(itemCode, contractId);

	}

	@Override
	public void addPndJobMst(PndJobMst pndJobMst) {
		pndJobDtlDao.addPndJobMst(pndJobMst);

	}

	@Override
	public void addPndJobDtl(PndJobDtl pndJobDtl) {
		pndJobDtlDao.addPndJobDtl(pndJobDtl);

	}

	@Override
	public JobItemMaintenance getTotalJobItems(String itemCode,
			String contractNo) {

		return pndJobDtlDao.getTotalJobItems(itemCode, contractNo);
	}

	@Override
	public List<JobItemMaintenance> getJobItems(String contractNo) {

		return pndJobDtlDao.getJobItems(contractNo);
	}

	@Override
	public List<PndJobMst> getJobList() {

		return pndJobDtlDao.getJobList();
	}

	@Override
	public PndJobMst getJobMst(String jobNo) {

		return pndJobDtlDao.getJobMst(jobNo);
	}

	@Override
	public List<PndJobDtl> getPndJobDtlList(String jobNo) {

		return pndJobDtlDao.getPndJobDtlList(jobNo);
	}

	@Override
	public List<AsBuiltDtl> getPndJobDtlList(String[] jobNo, String contractNo) {

		return pndJobDtlDao.getPndJobDtlList(jobNo, contractNo);
	}

	@Override
	public List<CnPdRequisitionDtl> getCnPdRequisitionDtlListOrderByJobNo(
			String cnReqMstId) {

		return pndJobDtlDao.getCnPdRequisitionDtlListOrderByJobNo(cnReqMstId);
	}
	
	// Added By Ashid
	@Override
	public List<CnPdRequisitionMst> getCnPdRequisitionMstListByOperationIds(
			String[] operationIds) {

		return pndJobDtlDao
				.getCnPdRequisitionMstListByOperationIds(operationIds);
	}

}
