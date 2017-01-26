package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.WorkOrderDtlDao;
import com.ibcs.desco.cs.model.WorkOrderDtl;

@Service
public class WorkOrderDtlServiceImpl implements WorkOrderDtlService {

	private WorkOrderDtlDao workOrderDtlDao;

	public WorkOrderDtlDao getWorkOrderDtlDao() {
		return workOrderDtlDao;
	}



	public void setWorkOrderDtlDao(WorkOrderDtlDao workOrderDtlDao) {
		this.workOrderDtlDao = workOrderDtlDao;
	}

	//@Override
	//public void addWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		//workOrderDtlDao.addWorkOrderDtl(workOrderDtl);
	//}

	/*@Override
	public void removeWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		workOrderDtlDao.removeWorkOrderDtl(workOrderDtl);
	}

	@Override
	public void editWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		workOrderDtlDao.editWorkOrderDtl(workOrderDtl);
	}

	@Override
	public WorkOrderDtl getWorkOrderDtl(int id) {
		// TODO Auto-generated method stub
		return workOrderDtlDao.getWorkOrderDtl(id);
	}

	@Override
	public List<WorkOrderDtl> getAllWorkOrderDtl() {
		// TODO Auto-generated method stub
		return workOrderDtlDao.getAllWorkOrderDtl();
	}*/

	@Override
	public List<WorkOrderDtl> getWorkOrderDtlByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		return workOrderDtlDao.getWorkOrderDtlByWorkOrderNo(workOrderNo);
	}

	/*@Override
	public List<WorkOrderDtl> listWorkOrderDtlByOperationIds(
			Integer[] operationId) {
		// TODO Auto-generated method stub
		return workOrderDtlDao.listWorkOrderDtlByOperationIds(operationId);
	}*/

}
