package com.ibcs.desco.procurement.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.procurement.model.Vendor;

@Repository
public class VendorDAOImpl implements VendorDAO {

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public void addVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(vendor);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Vendor> listVendors() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (List<Vendor>) session.createCriteria(Vendor.class).list();
	}

	@Override
	@Transactional
	public Vendor getVendor(int id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (Vendor) session.get(Vendor.class, id);
	}

	@Override
	@Transactional
	public void deleteVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(vendor);
	}

}
