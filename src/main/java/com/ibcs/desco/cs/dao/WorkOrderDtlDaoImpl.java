package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.WorkOrderDtl;

@Repository
@Transactional
public class WorkOrderDtlDaoImpl implements WorkOrderDtlDao {

	//private WorkOrderMstDao workOrderMstDao;

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
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkOrderDtl> getWorkOrderDtlByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from com.ibcs.desco.cs.model.WorkOrderDtl where workOrderNo = :workOrderNo");
			query.setString("workOrderNo", workOrderNo);
			return (List<WorkOrderDtl>) query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
