package com.ibcs.desco.workshop.Dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.AuctionMst;
import com.ibcs.desco.workshop.model.MeterTestingReport;

@Repository
@Transactional
public class MeterTestingReportDaoImpl implements MeterTestingReportDao {

	private static final Logger logger = LoggerFactory
			.getLogger(MeterTestingReportDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdateMeterTestingReport(
			MeterTestingReport meterTestingReport) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(meterTestingReport);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterTestingReport> listMeterTestingReport() {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			return (List<MeterTestingReport>) session.createCriteria(
					MeterTestingReport.class).list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterTestingReport> listMeterTestingReportByDateRangeAndFinalSubmit(Date startDate, Date endDate, boolean finalSubmit) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(MeterTestingReport.class);
			criteria.add(Restrictions.eq("finalSubmit", finalSubmit));
			criteria.add(Restrictions.ge("reportDate", startDate)); 
			criteria.add(Restrictions.lt("reportDate", endDate));
			criteria.addOrder(Order.desc("reportDate"));
			List<MeterTestingReport> meterTestingReportList = (List<MeterTestingReport>) criteria.list();
			return meterTestingReportList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterTestingReport> listMeterTestingReportByFinalSubmit(boolean finalSubmit) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(MeterTestingReport.class);
			criteria.add(Restrictions.eq("finalSubmit", finalSubmit));
			List<MeterTestingReport> meterTestingReportList = (List<MeterTestingReport>) criteria.list();
			return meterTestingReportList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public MeterTestingReport getMeterTestingReport(int id) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			return (MeterTestingReport) session.get(MeterTestingReport.class,
					id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteMeterTestingReport(MeterTestingReport meterTestingReport) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(meterTestingReport);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AuctionMst getAuctionMstByCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		String [] searchingParamArray = createdBy.split("\\&"); 
		String searchingParam = "";
		for(int i=0; i<searchingParamArray.length;i++){		
			//searchingParam+=searchingParamArray[i]+"||&||";
			searchingParam+=searchingParamArray[i]+"'||'&'||'";
		}
		//searchingParam = searchingParam.substring(0, searchingParam.length()-5);
		searchingParam = searchingParam.substring(0, searchingParam.length()-9);
		
		try {
			Session session = sessionFactory.getCurrentSession();
			/*Criteria criteria = session.createCriteria(AuctionMst.class);
			criteria.add(Restrictions.eq("createdBy", searchingParam));
			List<AuctionMst> auctionMstList = (List<AuctionMst>) criteria.list();*/
			Query query = session.createQuery("from AuctionMst where createdBy = '"+searchingParam+"'");
			List<AuctionMst> auctionMstList = (List<AuctionMst>) query.list();
			AuctionMst auctionMst = null;
			if(auctionMstList.size() > 0){
				auctionMst = (AuctionMst) auctionMstList.get(0);
			}
			return auctionMst;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
