package com.ibcs.desco.cs.service;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.cs.model.VehiclePermission;

public interface VehiclePermissionService {
	
	public void saveVehiclePermission(VehiclePermission vehiclePermission);

	public void deleteVehiclePermission(VehiclePermission vehiclePermission);

	public void updateVehiclePermission(VehiclePermission vehiclePermission);

	public VehiclePermission getVehiclePermission(int id);

	public List<VehiclePermission> getVehiclePermissionList();
	
	public List<VehiclePermission> getVehiclePermissionListByOperationId(String [] operationId);
	
	public VehiclePermission getVehiclePermissionByContractor(String contractorName);
	
	public VehiclePermission getVehiclePermissionByEntryDate(Date entryTime);
	
	public VehiclePermission getVehiclePermissionByVehicleType(String vehicleType);
	
	public VehiclePermission getVehiclePermissionVehicleNumber(String vehicleNumber);
	
	public VehiclePermission getVehiclePermissionByRequisionBy(String requisitionBy);

}
