package com.ibcs.desco.cs.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.AuctionCategoryReference;
import com.ibcs.desco.cs.model.AuctionProcessDtl;
import com.ibcs.desco.cs.model.AuctionView;

@Repository
@Transactional
public class AuctionDaoImpl implements AuctionDao {

	private static final Logger logger = LoggerFactory
			.getLogger(AuctionDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuctionView> getAuctionViewListByDateRange(String toDate,
			List<String> catList) {
		// TODO Auto-generated method stub
		try {
			String itemCategoryIds = "";
			for (String str : catList) {
				itemCategoryIds += str + ",";
			}
			itemCategoryIds = itemCategoryIds.substring(0,
					itemCategoryIds.length() - 1);

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from AuctionView  where transactionDate <=TO_DATE('"
							+ toDate
							+ "', 'DD-MM-YYYY') and  substr(itemCode,0,3) in ("
							+ itemCategoryIds + ")");
			List<AuctionView> auctionViewList = (List<AuctionView>) (Object) query
					.list();
			return auctionViewList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Double> getAuctionViewListByGroup(String toDate,
			List<String> catList) {
		// TODO Auto-generated method stub
		try {

			String itemCategoryIds = "";
			for (String str : catList) {
				itemCategoryIds += str + ",";
			}
			itemCategoryIds = itemCategoryIds.substring(0,
					itemCategoryIds.length() - 1);

			Session session = sessionFactory.getCurrentSession();
			String sql = "SELECT  dept_id, khath_id, location_id, item_mst_id, item_code, sum(balance_qty) balance_qty "
					+ "FROM AUCTION_VIEW where transaction_date <= TO_DATE('"
					+ toDate
					+ "', 'DD-MM-YYYY') and  substr(item_Code,0,3) in ("
					+ itemCategoryIds
					+ ") group by dept_id, khath_id, location_id, item_mst_id, item_code order by dept_id";

			SQLQuery query = session.createSQLQuery(sql);
			List<Object[]> list = query.list();
			Map<String, Double> map = new HashMap<String, Double>();
			for (Object[] row : list) {
				String key = row[0].toString();
				key += "-" + row[1].toString();
				key += "-" + row[2].toString();
				key += "-" + row[3].toString();
				Double value = Double.parseDouble(row[5].toString());
				map.put(key, value);
			}
			return map;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Double> getAuctionProcesDtlListGroupByDept(
			String aucProcesMstId, List<Integer> itemMasterIds) {
		try {
			Session session = sessionFactory.getCurrentSession();

			Criteria criteria = session.createCriteria(AuctionProcessDtl.class);
			criteria.add(Restrictions.eq("auctionProcessMst.id",
					Integer.parseInt(aucProcesMstId)));
			//criteria.add(Restrictions.in("itemMaster.id", itemMasterIds));
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("auctionProcessMst.id"))
					.add(Projections.groupProperty("departments.id"))
					.add(Projections.groupProperty("itemMaster.id"))
					.add(Projections.sum("quantity")));

			// List<AuctionProcessDtl> auctionProcessDtlList =
			// (List<AuctionProcessDtl>) criteria.list();

			List<Object[]> list = criteria.list();
			Map<String, Double> map = new HashMap<String, Double>();
			for (Object[] row : list) {
				String key = row[0].toString();
				key += "-" + row[1].toString();
				key += "-" + row[2].toString();
				Double value = Double.parseDouble(row[3].toString());
				map.put(key, value);
			}
			return map;

			// return auctionProcessDtlList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getItemCategoryList(String auctionCategoryId) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AuctionCategoryReference.class);
			criteria.add(Restrictions.eq("auctionCategory.id",
					Integer.parseInt(auctionCategoryId)));

			List<AuctionCategoryReference> csitemList = (List<AuctionCategoryReference>) criteria
					.list();

			List<String> itemCategoryId = new ArrayList<String>();
			for (AuctionCategoryReference acr : csitemList) {
				itemCategoryId.add(acr.getItemCategory().getCategoryId()
						.toString());
			}
			return itemCategoryId;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNextAuctionId() {
		// TODO Auto-generated method stub
		String result = "";
		try {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = 1 + cal.get(Calendar.MONTH);
			String mm = (month + "").length() == 1 ? "0" + month : "" + month;
			// DESCO/CS/AUCTION/2016/11/001

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select distinct (auctionName) from AuctionProcessMst order by auctionName desc");

			List<String> objList = query.list();

			if (objList == null || objList.size() == 0) {
				result = "DESCO/CS/AUCTION/" + year + "/" + mm + "/001";
			} else {
				String lastAucId = objList.get(0);
				String[] temp = lastAucId.split("/");
				lastAucId = temp[temp.length - 1];
				int id = Integer.parseInt(lastAucId) + 1;
				lastAucId = String.format("%03d", id);

				result = "DESCO/CS/AUCTION/" + year + "/" + mm + "/"
						+ lastAucId;
			}

			return result;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getContractorUserIdList() {
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery criteria = session.createSQLQuery("select id from auth_user where length(trim(department_id)) = 5 and department_id like '540%'");
			
			List<BigDecimal> dataList = criteria.list();
			List<Integer> contractorList = new ArrayList<Integer>();
			for (BigDecimal bigDecimal : dataList) {
				contractorList.add(Integer.parseInt(bigDecimal.toString()));
			}
			return contractorList;
			
		} catch( Exception e ) {
			logger.error( e.getMessage() );
			return null;
		}
	}

}
