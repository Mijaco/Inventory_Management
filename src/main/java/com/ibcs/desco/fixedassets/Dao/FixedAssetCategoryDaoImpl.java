package com.ibcs.desco.fixedassets.Dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.fixedassets.model.DepreciationSummery;
import com.ibcs.desco.fixedassets.model.FixedAssetCategory;
import com.ibcs.desco.fixedassets.model.FixedAssetReceive;
import com.ibcs.desco.fixedassets.model.FixedAssets;

@Repository
@Transactional
public class FixedAssetCategoryDaoImpl implements FixedAssetCategoryDao {
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<FixedAssetCategory> getFixedAssetCategoryList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<FixedAssetCategory>) session.createCriteria(
				FixedAssetCategory.class).list();
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FixedAssetCategory> getFixedAssetCategoryList() {
	
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select distinct assetCategoryCode, assetCategoryName from FixedAssetCategory");

		//List<FixedAssetCategory> fixedAssets=(List<FixedAssetCategory>) query.list();
		List<FixedAssetCategory> dmds = new ArrayList<FixedAssetCategory>();
		FixedAssetCategory dmd=null;
		List<Object[]> list = query.list();
		for (Object object : list) {
			dmd = new FixedAssetCategory();
			Object[] li = (Object[]) object;
			dmd.setAssetCategoryCode(li[0].toString()); // property
																	// id
		    dmd.setAssetCategoryName(li[1].toString());
			dmds.add(dmd);
		}
		return dmds;

	
}
	
	
//select distinct asset_category_code, asset_category_name from FIXED_ASSET_CATEGORY
	@SuppressWarnings("unchecked")
	@Override
	public List<FixedAssets> getFixedAssetList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<FixedAssets>) session.createCriteria(
				FixedAssets.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FixedAssetReceive> getFixedAssetReceiveList() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<FixedAssetReceive>) session.createCriteria(
				FixedAssetReceive.class).list();
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public FixedAssetReceive getFixedAssetReceive(String itemId, String date) {
	
		String[] s=date.split(" ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from FixedAssetReceive where itemId ='" + itemId + "' and TO_CHAR(receiveDate, 'YYYY-MM-DD')='"+ s[0] + "'");

		List<FixedAssetReceive> fixedAssets=(List<FixedAssetReceive>) query.list();
		
		if(fixedAssets.size()>0){
			return fixedAssets.get(0);
		}else{return null;}

	
}*/
	@SuppressWarnings("unchecked")
	@Override
	public FixedAssetReceive getFixedAssetReceive(String itemId, String key) {
	
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from FixedAssetReceive where itemId ='" + itemId + "' and faRegKey='"+ key + "'");

		List<FixedAssetReceive> fixedAssets=(List<FixedAssetReceive>) query.list();
		
		if(fixedAssets.size()>0){
			return fixedAssets.get(0);
		}else{return null;}

	
}

	/*@SuppressWarnings("unchecked")
	@Override
	public FixedAssets getFixedAsset(String itemId, String date) {
		
		String[] s=date.split(" ");
		Session session = sessionFactory.getCurrentSession();
		String hql = "from FixedAssets where fixedAssetId ='" + itemId + "' and TO_CHAR(purchaseDate, 'YYYY-MM-DD')='"+ s[0] + "'"; 
		Query query = session.createQuery(hql);
		
		List<FixedAssets> fixedAssets=(List<FixedAssets>) query.list();
	
		if(fixedAssets.size()>0){
			return fixedAssets.get(0);
		}else{return null;}
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public FixedAssets getFixedAsset(String itemId, String key) {
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from FixedAssets where fixedAssetId ='" + itemId + "'  and faRegKey='"+ key + "'"; 
		Query query = session.createQuery(hql);
		
		List<FixedAssets> fixedAssets=(List<FixedAssets>) query.list();
	
		if(fixedAssets.size()>0){
			return fixedAssets.get(0);
		}else{return null;}
	}
	
	private static String DATE_FORMAT = "yyyy-MM-dd";
	
	public String getCurrentDate(Date date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
		String strDate = sdfDate.format(date);

		return strDate;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DepreciationSummery getDepreciation(String itemId, String purchaseDate){//depEndDate   depStartDate
		String d = getCurrentDate(new Date());
		String[] s=purchaseDate.split(" ");
		Session session = sessionFactory.getCurrentSession();
		String hql = "from DepreciationSummery where assetId ='" + itemId + "' and TO_CHAR(purchaseDate, 'YYYY-MM-DD')='"+ s[0] + "'and TO_CHAR(depStartDate, 'YYYY-MM-DD')<='"+ d
							+ "' and TO_CHAR(depEndDate, 'YYYY-MM-DD')>='"+ d
							+ "'"; 
		Query query = session.createQuery(hql);
		
		List<DepreciationSummery> ds=(List<DepreciationSummery>) query.list();
		
		if(ds.size()>0){
			return ds.get(0);
		}else{return null;}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DepreciationSummery> getDepreciationListSchedulled(String itemId, String purchaseDate, String flag){//depEndDate   depStartDate
		
		String d = getCurrentDate(new Date());
		Session session = sessionFactory.getCurrentSession();
		String hql = null;
		if(flag=="1"){
			 hql = "from DepreciationSummery where assetId ='" + itemId + "' and faRegKey='"+ purchaseDate + "'and TO_CHAR(depStartDate, 'YYYY-MM-DD')<='"+ d + "'"; 
	
		}else{
			String[] s=purchaseDate.split(" ");
			 hql = "from DepreciationSummery where assetId ='" + itemId + "' and TO_CHAR(purchaseDate, 'YYYY-MM-DD')='"+ s[0] + "'and TO_CHAR(depStartDate, 'YYYY-MM-DD')<='"+ d
								+ "' and TO_CHAR(depEndDate, 'YYYY-MM-DD')>='"+ d
								+ "'"; 
				
		}
		Query query = session.createQuery(hql);
		
		List<DepreciationSummery> ds=(List<DepreciationSummery>) query.list();
		
		if(ds.size()>0){
			return ds;
		}else{return null;}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<DepreciationSummery> getDepreciationList(String itemId, String date){//depEndDate   depStartDate
		
		String[] s=date.split(" ");
		Session session = sessionFactory.getCurrentSession();
		//TODO: change TO_CHAR(depStartDate, 'YYYY-MM-DD') to TO_CHAR(depStartDate, 'DD-MM-YYYY')
		String hql = "from DepreciationSummery where assetId ='" + itemId + "' and TO_CHAR(depStartDate, 'YYYY-MM-DD')<='"+ date
							+ "' and TO_CHAR(depEndDate, 'YYYY-MM-DD')>='"+ date
							+ "'"; 
		Query query = session.createQuery(hql);
		
		List<DepreciationSummery> ds=(List<DepreciationSummery>) query.list();
		
		if(ds.size()>0){
			return ds;
		}else{return null;}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepreciationSummery> getDepSummListForUpdate(String itemId, String date, String pDate) {
	
		String[] s=date.split(" ");
		String[] pd=pDate.split(" ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from DepreciationSummery where assetId ='" + itemId + "' and TO_CHAR(depStartDate, 'YYYY-MM-DD')>'"+ s[0] + "' and TO_CHAR(purchaseDate, 'YYYY-MM-DD')='"+ pd[0] + "'");

		List<DepreciationSummery> depreciationSummery=(List<DepreciationSummery>) query.list();
		
		return depreciationSummery;	
}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public FixedAssets getAdditionLengthBySubCategory(String distributionLine, String disLineDivision, String purchaseDate){
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from FixedAssets where subCategory ='" + distributionLine + "' and UPPER(divisionName) ='" + disLineDivision.toUpperCase() + "' and TO_CHAR(purchaseDate, 'DD-MM-YYYY')='"+ purchaseDate
							+ "'"; 
		Query query = session.createQuery(hql);
		
		List<FixedAssets> ds=(List<FixedAssets>) query.list();
		
		if(ds.size()>0){
			return ds.get(0);
		}else{return null;}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<FixedAssets> getSLFixedAssetList(){
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from FixedAssets where serialNumber is not null");

		List<FixedAssets> fixedAssets=(List<FixedAssets>) query.list();
		return fixedAssets;
	}
	 
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<FixedAssetReceive> getSLRCVFixedAssetList(){
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from FixedAssetReceive where serialNumber is not null");

		List<FixedAssetReceive> fixedAssets=(List<FixedAssetReceive>) query.list();
		return fixedAssets;
	}
}
