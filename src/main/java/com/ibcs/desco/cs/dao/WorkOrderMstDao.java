package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.WorkOrderMst;

public interface WorkOrderMstDao {
	
//public void addWorkOrderDtl(WorkOrderDtl workOrderDtl);
	
/*	public void removeWorkOrderDtl(WorkOrderDtl workOrderDtl);

	public void editWorkOrderDtl(WorkOrderDtl workOrderDtl);

	public WorkOrderDtl getWorkOrderDtl(int id);

	public List<WorkOrderDtl> getAllWorkOrderDtl();
*/
	public List<WorkOrderMst> getWorkOrderMstList();
	
	public WorkOrderMst getWorkOrderMstByWorkOrderNo(String rrNo);
}
