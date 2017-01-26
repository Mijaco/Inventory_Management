package com.ibcs.desco.procurement.dao;

import java.util.List;

import com.ibcs.desco.procurement.model.Vendor;


public interface VendorDAO {
	// data access for add new Vendor
	public void addVendor(Vendor vendor);

	// data access for get all Vendor as List
	public List<Vendor> listVendors();
	
	// data access for get specific one Vendor information and update Vendor info
	public Vendor getVendor(int id);
	
	// data access for Delete an Vendor
	public void deleteVendor(Vendor vendor);	
}
