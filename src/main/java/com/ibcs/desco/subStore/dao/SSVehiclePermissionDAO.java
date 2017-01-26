package com.ibcs.desco.subStore.dao;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.subStore.model.SSVehiclePermission;

public interface SSVehiclePermissionDAO {
	
	public void saveSSVehiclePermission(SSVehiclePermission vehiclePermission);

	public void deleteSSVehiclePermission(SSVehiclePermission vehiclePermission);

	public void updateSSVehiclePermission(SSVehiclePermission vehiclePermission);

	public SSVehiclePermission getSSVehiclePermission(int id);

	public List<SSVehiclePermission> getSSVehiclePermissionList();
	
	public List<SSVehiclePermission> getSSVehiclePermissionListByOperationId(String [] operationId);
	
	public SSVehiclePermission getSSVehiclePermissionByContractor(String contractorName);
	
	public SSVehiclePermission getSSVehiclePermissionByEntryDate(Date entryTime);
	
	public SSVehiclePermission getSSVehiclePermissionByVehicleType(String vehicleType);
	
	public SSVehiclePermission getSSVehiclePermissionVehicleNumber(String vehicleNumber);
	
	public SSVehiclePermission getSSVehiclePermissionByRequisionBy(String requisitionBy);
}
