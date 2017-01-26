package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.WorkOrderMst;

@Repository
@Transactional
public class WorkOrderMstDaoImpl implements WorkOrderMstDao {


	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	/*@Override
	public void addWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		CSProcItemRcvMst csProcItemRcvMst = csProcItemRcvMstDao
				.getCSProcItemRcvMstByRrNo(workOrderDtl
						.getReceivedReportNo());
		workOrderDtl.setCsProcItemRcvMst(csProcItemRcvMst);
		session.save(workOrderDtl);
	}*/

	/*	@Override
	public void removeWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(workOrderDtl);
	}

	@Override
	public void editWorkOrderDtl(WorkOrderDtl workOrderDtl) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.update(workOrderDtl);
	}

	@Override
	public WorkOrderDtl getWorkOrderDtl(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (WorkOrderDtl) session.get(WorkOrderDtl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkOrderDtl> getAllWorkOrderDtl() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<WorkOrderDtl>) session.createCriteria(
				WorkOrderDtl.class).list();
	}
*/
	
	@Override
	public WorkOrderMst getWorkOrderMstByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.WorkOrderMst where pli=1 and workOrderNo = :workOrderNo");
			query.setString("workOrderNo", workOrderNo);
			return (WorkOrderMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkOrderMst> getWorkOrderMstList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.WorkOrderMst where pli=1");
			//query.setString("workOrderNo", workOrderNo+"%");
			return (List<WorkOrderMst>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
}
