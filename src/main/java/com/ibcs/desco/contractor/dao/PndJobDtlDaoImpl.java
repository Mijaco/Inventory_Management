package com.ibcs.desco.contractor.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.contractor.model.AsBuiltDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;

@Transactional
@Repository
public class PndJobDtlDaoImpl implements PndJobDtlDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<PndJobDtl> getJobItemList(String contractNo) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from PndJobDtl dtl where dtl.pndJobMst.woNumber = :contractNo");
			query.setString("contractNo", contractNo);
			return (List<PndJobDtl>) query.list();

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PndJobDtl> getPndJobDtlList(String jobNo) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from PndJobDtl dtl where dtl.pndJobMst.jobNo = :jobNo");
			query.setString("jobNo", jobNo);
			return (List<PndJobDtl>) query.list();

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AsBuiltDtl> getPndJobDtlList(String[] jobNo, String contractNo) {
		
		String job=""; int i=1;
		for(String j:jobNo){
			job+="'"+j+"'";
			if(i<jobNo.length){
				job+=",";}
			i++;
		}
//select distinct dtl.ITEM_CODE, dtl.ITEM_NAME from PND_JOB_DTL dtl, PND_JOB_MST mst where mst.ID=dtl.MST_ID and JOB_NO in ('J001','J003');
Query query = null;
String hql = "select distinct dtl.itemCode, dtl.itemName, dtl.uom from PndJobDtl dtl, PndJobMst mst where mst.id=dtl.pndJobMst and mst.jobNo in ("+job+")";
	query = sessionFactory.getCurrentSession().createQuery(hql);

List<AsBuiltDtl> asBuiltList = new ArrayList<AsBuiltDtl>();
AsBuiltDtl as = null;
List<Object[]> list = query.list();
for (Object object : list) {
	as = new AsBuiltDtl();
	Object[] li = (Object[]) object;
	as.setItemCode(li[0].toString()); // item code
	as.setItemName(li[1].toString()); // item name
	as.setUom(li[2].toString()); // item name
	as.setConsume(getConsume(as.getItemCode(), job));
	
	asBuiltList.add(as);
}
return asBuiltList;

	}
	@SuppressWarnings("unchecked")
	private Double getConsume(String itemCode, String jobNo) {
		  String hql = "select sum(quantityIssued) from CnPdRequisitionDtl where itemCode='"+itemCode+"' and cnPdRequisitionMst.approved=1 and jobNo in ("+jobNo+") group by itemCode";
		   
		  
		  Query query = sessionFactory.getCurrentSession().createQuery(hql);
		  Double dValue=null;
		 
		  List<Object[]> list = query.list();
			for (Object object : list) {
				dValue=Double.valueOf(object.toString());								
			}
			if(dValue==null){dValue=0.0;}
			
			return dValue;
	}
	@SuppressWarnings("unchecked")
	public List<PndJobMst> getJobList() {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from PndJobMst dtl");
			// query.setString("contractNo", contractNo);
			return (List<PndJobMst>) query.list();

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	public PndJobMst getJobMst(String jobNo) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from PndJobMst dtl where dtl.jobNo = :jobNo");
			query.setString("jobNo", jobNo);
			return (PndJobMst) query.list().get(0);

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	public PndJobDtl getPndJobDtl(String itemCode, String contractId) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from PndJobDtl dtl where dtl.itemCode = :itemCode and pndJobMst.woNumber = :contractNo");
			query.setString("itemCode", itemCode);
			query.setParameter("contractNo", contractId);
			return (PndJobDtl) query.list().get(0);

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	@Override
	public void addPndJobMst(PndJobMst pndJobMst) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(pndJobMst);

	}

	@Override
	public void addPndJobDtl(PndJobDtl pndJobDtl) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(pndJobDtl);
	}

	@SuppressWarnings("unchecked")
	public JobItemMaintenance getTotalJobItems(String itemCode,
			String contractNo) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from JobItemMaintenance dtl where dtl.itemCode = :itemCode and dtl.contractor.contractNo = :contractNo");
			query.setString("itemCode", itemCode);
			query.setParameter("contractNo", contractNo);
			List<JobItemMaintenance> jobItems = query.list();
			if (jobItems.size() > 0) {
				return jobItems.get(0);
			} else {
				return null;
			}

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<JobItemMaintenance> getJobItems(String contractNo) {

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from JobItemMaintenance dtl where dtl.contractor.contractNo = :contractNo");

			query.setParameter("contractNo", contractNo);
			List<JobItemMaintenance> jobItems = query.list();
			if (jobItems.size() > 0) {
				return jobItems;
			} else {
				return null;
			}

		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CnPdRequisitionDtl> getCnPdRequisitionDtlListOrderByJobNo(
			String cnReqMstId) {

		Session session = sessionFactory.getCurrentSession();
		Integer id=Integer.parseInt(cnReqMstId);
		try {
			Query query = session
					.createQuery("from CnPdRequisitionDtl where cnPdRequisitionMst.id = :cnReqMstId order by jobNo ASC");
			query.setParameter("cnReqMstId", id);

			List<CnPdRequisitionDtl> cnPdRequisitionDtlList = query.list();

			if (cnPdRequisitionDtlList.size() > 0) {
				return cnPdRequisitionDtlList;
			} else {
				return null;
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

	}
	
	// Added By Ashid
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CnPdRequisitionMst> getCnPdRequisitionMstListByOperationIds(
			String[] operationIds) {

		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationIds).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<CnPdRequisitionMst> items1 = (List<CnPdRequisitionMst>) session
					.createCriteria(CnPdRequisitionMst.class)
					.add(Restrictions.in("requisitionNo", operation)).list();
			return items1;

		} catch (Exception e) {
			return null;
		}
	}

}
