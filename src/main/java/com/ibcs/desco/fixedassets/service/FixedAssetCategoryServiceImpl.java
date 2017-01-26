package com.ibcs.desco.fixedassets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.fixedassets.Dao.FixedAssetCategoryDao;
import com.ibcs.desco.fixedassets.model.DepreciationSummery;
import com.ibcs.desco.fixedassets.model.FixedAssetCategory;
import com.ibcs.desco.fixedassets.model.FixedAssetReceive;
import com.ibcs.desco.fixedassets.model.FixedAssets;

@Service
public class FixedAssetCategoryServiceImpl implements
		FixedAssetCategoryService {

	FixedAssetCategoryDao fixedAssetCategoryDao;

	public FixedAssetCategoryDao getFixedAssetCategoryDao() {
		return fixedAssetCategoryDao;
	}

	public void setFixedAssetCategoryDao(
			FixedAssetCategoryDao fixedAssetCategoryDao) {
		this.fixedAssetCategoryDao = fixedAssetCategoryDao;
	}

	@Override
	public List<FixedAssetCategory> getFixedAssetCategoryList() {
		// TODO Auto-generated method stub
		return fixedAssetCategoryDao.getFixedAssetCategoryList();
	}
	

	
	@Override
	public List<FixedAssets> getFixedAssetList(){
	
		return fixedAssetCategoryDao.getFixedAssetList();
	}
	

	@Override
	public List<FixedAssetReceive> getFixedAssetReceiveList(){
			
			return fixedAssetCategoryDao.getFixedAssetReceiveList();
		}
	 
	@Override
	public FixedAssetReceive getFixedAssetReceive(String itemId, String date){
		
		return fixedAssetCategoryDao.getFixedAssetReceive(itemId, date);
	}
	 
	@Override
	public FixedAssets getFixedAsset(String itemId, String date){
		
		return fixedAssetCategoryDao.getFixedAsset(itemId, date);
	}

	@Override
	public DepreciationSummery getDepreciation(String itemId, String purchaseDate){
		
		return fixedAssetCategoryDao.getDepreciation(itemId, purchaseDate);
	}

	@Override
	public List<DepreciationSummery> getDepreciationListSchedulled(String itemId, String purchaseDate, String flag){
		
		return fixedAssetCategoryDao.getDepreciationListSchedulled(itemId, purchaseDate, flag);
	}
	 
	@Override
	public List<DepreciationSummery> getDepreciationList(String itemId, String date){
		
		return fixedAssetCategoryDao.getDepreciationList(itemId, date);
	}
	 
	@Override
	public List<DepreciationSummery> getDepSummListForUpdate(String itemId, String date, String pDate){
		
		return fixedAssetCategoryDao.getDepSummListForUpdate(itemId, date, pDate);
	}
	 
	@Override
	public FixedAssets getAdditionLengthBySubCategory(String distributionLine, String disLineDivision, String purchaseDate){
		
		return fixedAssetCategoryDao.getAdditionLengthBySubCategory(distributionLine, disLineDivision, purchaseDate);
			
	}
	
	@Override
	public List<FixedAssets> getSLFixedAssetList(){
		
		return fixedAssetCategoryDao.getSLFixedAssetList();
			
	}
	 
	@Override
	public List<FixedAssetReceive> getSLRCVFixedAssetList(){
		
		return fixedAssetCategoryDao.getSLRCVFixedAssetList();
			
	}

}
