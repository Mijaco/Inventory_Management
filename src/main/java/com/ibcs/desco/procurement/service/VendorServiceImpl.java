package com.ibcs.desco.procurement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.procurement.dao.VendorDAO;
import com.ibcs.desco.procurement.model.Vendor;

@Service
public class VendorServiceImpl implements VendorService {

	VendorDAO vendorDao;

	public VendorDAO getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(VendorDAO vendorDao) {
		this.vendorDao = vendorDao;
	}

	@Override
	public void addVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		vendorDao.addVendor(vendor);
	}

	@Override
	public List<Vendor> listVendors() {
		// TODO Auto-generated method stub
		return vendorDao.listVendors();
	}

	@Override
	public Vendor getVendor(int id) {
		// TODO Auto-generated method stub
		return vendorDao.getVendor(id);
	}

	@Override
	public void deleteVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		vendorDao.deleteVendor(vendor);
	}

}
