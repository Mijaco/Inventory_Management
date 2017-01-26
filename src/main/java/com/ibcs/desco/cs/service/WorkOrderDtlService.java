package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.WorkOrderDtl;

public interface WorkOrderDtlService {
	
	//public void addWorkOrderDtl(WorkOrderDtl workOrderDtl);
	
	/*public void removeWorkOrderDtl(WorkOrderDtl workOrderDtl);

	public void editWorkOrderDtl(WorkOrderDtl workOrderDtl);

	public WorkOrderDtl getWorkOrderDtl(int id);

	public List<WorkOrderDtl> getAllWorkOrderDtl();*/

	public List<WorkOrderDtl> getWorkOrderDtlByWorkOrderNo(String rrNo);
}
