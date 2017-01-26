package com.ibcs.desco.cs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.VehiclePermissionDAO;
import com.ibcs.desco.cs.model.VehiclePermission;

@Service
public class VehiclePermissionServiceImpl implements VehiclePermissionService {
	
	@Autowired
	private VehiclePermissionDAO vehiclePermissionDAO;
	
	public VehiclePermissionDAO getVehiclePermissionDAO(){
		return vehiclePermissionDAO;
	}
	
	public void setVehiclePermissionDAO(VehiclePermissionDAO vehiclePermissionDAO) {
		this.vehiclePermissionDAO = vehiclePermissionDAO;
	}

	public void saveVehiclePermission(VehiclePermission vehiclePermission) {
		vehiclePermissionDAO.saveVehiclePermission(vehiclePermission);
	}

	public void deleteVehiclePermission(VehiclePermission vehiclePermission) {
		vehiclePermissionDAO.deleteVehiclePermission(vehiclePermission);
	}

	public void updateVehiclePermission(VehiclePermission vehiclePermission) {
		vehiclePermissionDAO.updateVehiclePermission(vehiclePermission);
	}

	public VehiclePermission getVehiclePermission(int id) {
		return vehiclePermissionDAO.getVehiclePermission(id);
	}

	public List<VehiclePermission> getVehiclePermissionList() {
		return vehiclePermissionDAO.getVehiclePermissionList();
	}

	public VehiclePermission getVehiclePermissionByContractor(String contractorName) {
		return vehiclePermissionDAO.getVehiclePermissionByContractor(contractorName);
	}

	public VehiclePermission getVehiclePermissionByEntryDate(Date entryTime) {
		return vehiclePermissionDAO.getVehiclePermissionByEntryDate(entryTime);
	}

	public VehiclePermission getVehiclePermissionByVehicleType(String vehicleType) {
		return vehiclePermissionDAO.getVehiclePermissionByVehicleType(vehicleType);
	}

	public VehiclePermission getVehiclePermissionVehicleNumber(String vehicleNumber) {
		return vehiclePermissionDAO.getVehiclePermissionVehicleNumber(vehicleNumber);
	}

	public VehiclePermission getVehiclePermissionByRequisionBy(String requisitionBy) {
		return vehiclePermissionDAO.getVehiclePermissionByRequisionBy(requisitionBy);
	}

	@Override
	public List<VehiclePermission> getVehiclePermissionListByOperationId(String[] operationId) {
		return vehiclePermissionDAO.getVehiclePermissionListByOperationId(operationId);
	}
}
