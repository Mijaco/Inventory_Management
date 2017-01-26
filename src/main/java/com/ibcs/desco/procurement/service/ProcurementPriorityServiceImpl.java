package com.ibcs.desco.procurement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.procurement.dao.ProcurementPriorityDAO;
import com.ibcs.desco.procurement.model.ProcurementFlowPriority;

@Service
public class ProcurementPriorityServiceImpl implements
		ProcurementPriorityService {

	ProcurementPriorityDAO procurementPriorityDAO;

	public ProcurementPriorityDAO getProcurementPriorityDAO() {
		return procurementPriorityDAO;
	}

	public void setProcurementPriorityDAO(
			ProcurementPriorityDAO procurementPriorityDAO) {
		this.procurementPriorityDAO = procurementPriorityDAO;
	}

	@Override
	public void addProcurementFlowPriority(
			ProcurementFlowPriority procurementFlowPriority) {
		// TODO Auto-generated method stub
		procurementPriorityDAO.addProcurementFlowPriority(procurementFlowPriority);

	}

	@Override
	public List<ProcurementFlowPriority> listProcurementFlowPrioritys() {
		// TODO Auto-generated method stub
		return procurementPriorityDAO.listProcurementFlowPrioritys();
	}

	@Override
	public ProcurementFlowPriority getProcurementFlowPriority(int id) {
		// TODO Auto-generated method stub
		return procurementPriorityDAO.getProcurementFlowPriority(id);
	}

	@Override
	public void deleteProcurementPrioriy(ProcurementFlowPriority procurementFlowPriority) {
		// TODO Auto-generated method stub
		procurementPriorityDAO.deleteProcurementPrioriy(procurementFlowPriority);
	}

	@Override
	public List<ProcurementFlowPriority> getProcurementFlowPriorityByRoleName(
			String roleName) {
		// TODO Auto-generated method stub
		return procurementPriorityDAO
				.getProcurementFlowPriorityByRoleName(roleName);
	}

	@Override
	public List<ProcurementFlowPriority> getProcurementFlowPriorityByPriority(
			int priority) {
		return procurementPriorityDAO
				.getProcurementFlowPriorityByPriority(priority);
	}

}
