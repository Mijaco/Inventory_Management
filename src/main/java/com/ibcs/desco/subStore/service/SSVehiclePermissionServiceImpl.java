package com.ibcs.desco.subStore.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SSVehiclePermissionDAO;
import com.ibcs.desco.subStore.model.SSVehiclePermission;

@Service
public class SSVehiclePermissionServiceImpl implements SSVehiclePermissionService {
	

	private SSVehiclePermissionDAO ssVehiclePermissionDAO;
	
	
	public SSVehiclePermissionDAO getSsVehiclePermissionDAO() {
		return ssVehiclePermissionDAO;
	}

	public void setSsVehiclePermissionDAO(SSVehiclePermissionDAO ssVehiclePermissionDAO) {
		this.ssVehiclePermissionDAO = ssVehiclePermissionDAO;
	}

	public void saveSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		ssVehiclePermissionDAO.saveSSVehiclePermission(vehiclePermission);
	}

	public void deleteSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		ssVehiclePermissionDAO.deleteSSVehiclePermission(vehiclePermission);
	}

	public void updateSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		ssVehiclePermissionDAO.updateSSVehiclePermission(vehiclePermission);
	}

	public SSVehiclePermission getSSVehiclePermission(int id) {
		return ssVehiclePermissionDAO.getSSVehiclePermission(id);
	}

	public List<SSVehiclePermission> getSSVehiclePermissionList() {
		return ssVehiclePermissionDAO.getSSVehiclePermissionList();
	}

	public SSVehiclePermission getSSVehiclePermissionByContractor(String contractorName) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionByContractor(contractorName);
	}

	public SSVehiclePermission getSSVehiclePermissionByEntryDate(Date entryTime) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionByEntryDate(entryTime);
	}

	public SSVehiclePermission getSSVehiclePermissionByVehicleType(String vehicleType) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionByVehicleType(vehicleType);
	}

	public SSVehiclePermission getSSVehiclePermissionVehicleNumber(String vehicleNumber) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionVehicleNumber(vehicleNumber);
	}

	public SSVehiclePermission getSSVehiclePermissionByRequisionBy(String requisitionBy) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionByRequisionBy(requisitionBy);
	}

	@Override
	public List<SSVehiclePermission> getSSVehiclePermissionListByOperationId(String[] operationId) {
		return ssVehiclePermissionDAO.getSSVehiclePermissionListByOperationId(operationId);
	}
}
