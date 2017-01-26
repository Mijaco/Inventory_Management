package com.ibcs.desco.procurement.service;

import java.util.List;

import com.ibcs.desco.procurement.model.ProcurementFlowPriority;

public interface ProcurementPriorityService {
	// data access for add new ProcurementFlowPriority
		public void addProcurementFlowPriority(ProcurementFlowPriority procurementFlowPriority);

		// data access for get all ProcurementFlowPriority as List
		public List<ProcurementFlowPriority> listProcurementFlowPrioritys();
		
		// data access for get specific one ProcurementFlowPriority information and update ProcurementFlowPriority info
		public ProcurementFlowPriority getProcurementFlowPriority(int id);
		
		// data access for Delete an ProcurementFlowPriority
		public void deleteProcurementPrioriy(ProcurementFlowPriority procurementFlowPriority);	
		
		public List<ProcurementFlowPriority> getProcurementFlowPriorityByRoleName(String roleName);
		
		public List<ProcurementFlowPriority> getProcurementFlowPriorityByPriority(int priority);
	}
