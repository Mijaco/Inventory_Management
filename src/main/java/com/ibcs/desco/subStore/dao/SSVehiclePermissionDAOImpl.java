package com.ibcs.desco.subStore.dao;

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

import com.ibcs.desco.subStore.model.SSVehiclePermission;

@Repository
@Transactional
public class SSVehiclePermissionDAOImpl implements SSVehiclePermissionDAO {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SSVehiclePermissionDAOImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.save(vehiclePermission);
	}

	@Override
	public void deleteSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(vehiclePermission);
	}

	@Override
	public void updateSSVehiclePermission(SSVehiclePermission vehiclePermission) {
		Session session = sessionFactory.getCurrentSession();
		session.update(vehiclePermission);
	}

	@Override
	public SSVehiclePermission getSSVehiclePermission(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (SSVehiclePermission) session.get(SSVehiclePermission.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SSVehiclePermission> getSSVehiclePermissionList() {
		Session session = sessionFactory.getCurrentSession();
		return (List<SSVehiclePermission>) session.createCriteria(
				SSVehiclePermission.class).list();
	}

	@Override
	public SSVehiclePermission getSSVehiclePermissionByContractor(String contractorName) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createQuery("from SSVehiclePermission "
					+ "where contractorName = :contractorName");
			query.setString("contractorName", contractorName);
			return (SSVehiclePermission) query.list().get(0);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public SSVehiclePermission getSSVehiclePermissionByEntryDate(Date entryTime) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from SSVehiclePermission "
					+ "where entryTime = :entryTime");
			query.setDate("entryTime", entryTime);
			return (SSVehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public SSVehiclePermission getSSVehiclePermissionByVehicleType(String vehicleType) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from SSVehiclePermission "
					+ "where vehicleType = :vehicleType");
			query.setString("vehicleType", vehicleType);
			return (SSVehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public SSVehiclePermission getSSVehiclePermissionVehicleNumber(String vehicleNumber) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from SSVehiclePermission "
					+ "where vehicleNumber = :vehicleNumber");
			query.setString("vehicleNumber", vehicleNumber);
			return (SSVehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public SSVehiclePermission getSSVehiclePermissionByRequisionBy(String requisitionBy) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from SSVehiclePermission "
					+ "where requisitionBy = :requisitionBy");
			query.setString("requisitionBy", requisitionBy);
			return (SSVehiclePermission) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<SSVehiclePermission> getSSVehiclePermissionListByOperationId(String[] operationId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationId).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			@SuppressWarnings("unchecked")
			List<SSVehiclePermission> items1 = (List<SSVehiclePermission>) session
					.createCriteria(SSVehiclePermission.class)
					.add(Restrictions.in("slipNo", operation)).list();
			return items1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
