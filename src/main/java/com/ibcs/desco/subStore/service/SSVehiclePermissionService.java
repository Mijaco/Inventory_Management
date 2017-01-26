package com.ibcs.desco.subStore.service;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.subStore.model.SSVehiclePermission;

public interface SSVehiclePermissionService {
	
	public void saveSSVehiclePermission(SSVehiclePermission ssVehiclePermission);

	public void deleteSSVehiclePermission(SSVehiclePermission ssVehiclePermission);

	public void updateSSVehiclePermission(SSVehiclePermission ssVehiclePermission);

	public SSVehiclePermission getSSVehiclePermission(int id);

	public List<SSVehiclePermission> getSSVehiclePermissionList();
	
	public List<SSVehiclePermission> getSSVehiclePermissionListByOperationId(String [] operationId);
	
	public SSVehiclePermission getSSVehiclePermissionByContractor(String contractorName);
	
	public SSVehiclePermission getSSVehiclePermissionByEntryDate(Date entryTime);
	
	public SSVehiclePermission getSSVehiclePermissionByVehicleType(String vehicleType);
	
	public SSVehiclePermission getSSVehiclePermissionVehicleNumber(String vehicleNumber);
	
	public SSVehiclePermission getSSVehiclePermissionByRequisionBy(String requisitionBy);

}
