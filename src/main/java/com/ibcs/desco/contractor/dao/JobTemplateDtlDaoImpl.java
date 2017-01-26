package com.ibcs.desco.contractor.dao;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.contractor.model.AsBuiltMst;
import com.ibcs.desco.contractor.model.CostEstimateInstallationDtl;
import com.ibcs.desco.contractor.model.CostEstimateMaterialsDtl;
import com.ibcs.desco.contractor.model.CostEstimateMiscellaniousDtl;
import com.ibcs.desco.contractor.model.CostEstimateRecoveryDtl;
import com.ibcs.desco.contractor.model.CostEstimationMst;

@Transactional
@Repository
public class JobTemplateDtlDaoImpl implements JobTemplateDtlDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimateMaterialsDtl> getJobTemplateDtl(int templateId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimateMaterialsDtl where costEstimationMst.id=:templateId");
			query.setInteger("templateId", templateId);
			return (List<CostEstimateMaterialsDtl>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CostEstimationMst getJobTemplateMst(int templateId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimationMst where id=:templateId");
			query.setInteger("templateId", templateId);
			List<CostEstimationMst> costList = query.list();

			if (costList.size() > 0) {
				return costList.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimationMst> getJobTemplateMst() {

		return this.sessionFactory.getCurrentSession()
				.createCriteria(CostEstimationMst.class).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimationMst> getCostEstimationMstByAssignFlag() {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from CostEstimationMst where assign='0' order by id DESC");
			return query.list();

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public void addJobTemplateMst(CostEstimationMst jobTemplateMst) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobTemplateMst);
	}

	@Override
	public void addJobMatDtl(CostEstimateMaterialsDtl jobTemplateDtl) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobTemplateDtl);
	}

	@Override
	public void addJobInsDtl(CostEstimateInstallationDtl jobTemplateDtl) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobTemplateDtl);
	}

	@Override
	public void addJobRecDtl(CostEstimateRecoveryDtl jobTemplateDtl) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobTemplateDtl);
	}

	@Override
	public void addJobMiscDtl(CostEstimateMiscellaniousDtl jobTemplateDtl) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobTemplateDtl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimateMaterialsDtl> getJobMaterialsDtl(String pndNo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimateMaterialsDtl where costEstimationMst.pndNo=:pndNo");
			query.setString("pndNo", pndNo);
			return (List<CostEstimateMaterialsDtl>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimateInstallationDtl> getJobInstallationDtl(String pndNo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimateInstallationDtl where costEstimationMst.pndNo=:pndNo");
			query.setString("pndNo", pndNo);
			return (List<CostEstimateInstallationDtl>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimateRecoveryDtl> getJobRecoveryDtl(String pndNo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimateRecoveryDtl where costEstimationMst.pndNo=:pndNo");
			query.setString("pndNo", pndNo);
			return (List<CostEstimateRecoveryDtl>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimateMiscellaniousDtl> getJobMiscellaniousDtl(
			String pndNo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CostEstimateMiscellaniousDtl where costEstimationMst.pndNo=:pndNo");
			query.setString("pndNo", pndNo);
			return (List<CostEstimateMiscellaniousDtl>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostEstimationMst> listCostEstimationMstByOperationIds(
			String[] pndNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(pndNo).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<CostEstimationMst> items1 = (List<CostEstimationMst>) session
					.createCriteria(CostEstimationMst.class)
					.add(Restrictions.in("pndNo", operation)).list();
			return items1;
		} catch (Exception e) {

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsBuiltMst> listAsBuiltMstByOperationIds(String[] id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(id).split("[\\[\\]]")[1].split(", ");
			List<String> operation = Arrays.asList(a);
			List<AsBuiltMst> items1 = (List<AsBuiltMst>) session
					.createCriteria(AsBuiltMst.class)
					.add(Restrictions.in("asBuiltNo", operation)).list();
			return items1;
		} catch (Exception e) {

			return null;
		}
	}

}
