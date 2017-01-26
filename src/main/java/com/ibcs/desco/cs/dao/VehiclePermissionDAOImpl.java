package com.ibcs.desco.cs.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.VehiclePermission;

@Repository
@Transactional
public class VehiclePermissionDAOImpl implements VehiclePermissionDAO {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CSProcItemRcvMstDaoImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveVehiclePermission(VehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.save(vehiclePermission);
	}

	@Override
	public void deleteVehiclePermission(VehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(vehiclePermission);
	}

	@Override
	public void updateVehiclePermission(VehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.update(vehiclePermission);
	}

	@Override
	public VehiclePermission getVehiclePermission(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (VehiclePermission) session.get(VehiclePermission.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehiclePermission> getVehiclePermissionList() {
		Session session = sessionFactory.getCurrentSession();
		return (List<VehiclePermission>) session.createCriteria(
				VehiclePermission.class).list();
	}

	@Override
	public VehiclePermission getVehiclePermissionByContractor(String contractorName) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createQuery("from com.ibcs.desco.cs.model.VehiclePermission "
					+ "where contractorName = :contractorName");
			query.setString("contractorName", contractorName);
			return (VehiclePermission) query.list().get(0);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public VehiclePermission getVehiclePermissionByEntryDate(Date entryTime) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from com.ibcs.desco.cs.model.VehiclePermission "
					+ "where entryTime = :entryTime");
			query.setDate("entryTime", entryTime);
			return (VehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public VehiclePermission getVehiclePermissionByVehicleType(String vehicleType) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from com.ibcs.desco.cs.model.VehiclePermission "
					+ "where vehicleType = :vehicleType");
			query.setString("vehicleType", vehicleType);
			return (VehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public VehiclePermission getVehiclePermissionVehicleNumber(String vehicleNumber) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from com.ibcs.desco.cs.model.VehiclePermission "
					+ "where vehicleNumber = :vehicleNumber");
			query.setString("vehicleNumber", vehicleNumber);
			return (VehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public VehiclePermission getVehiclePermissionByRequisionBy(String requisitionBy) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from com.ibcs.desco.cs.model.VehiclePermission "
					+ "where requisitionBy = :requisitionBy");
			query.setString("requisitionBy", requisitionBy);
			return (VehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<VehiclePermission> getVehiclePermissionListByOperationId(String[] operationId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			@SuppressWarnings("unchecked")
			List<VehiclePermission> items1 = (List<VehiclePermission>) session
					.createCriteria(VehiclePermission.class)
					.add(Restrictions.in("slipNo", operation)).list();
			return items1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
