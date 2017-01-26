package com.ibcs.desco.fixedassets.Dao;


import java.util.List;

import com.ibcs.desco.fixedassets.model.DepreciationSummery;
import com.ibcs.desco.fixedassets.model.FixedAssetCategory;
import com.ibcs.desco.fixedassets.model.FixedAssetReceive;
import com.ibcs.desco.fixedassets.model.FixedAssets;

public interface FixedAssetCategoryDao {

	public List<FixedAssetCategory> getFixedAssetCategoryList();
	
	 List<FixedAssets> getFixedAssetList();
	 
	 List<FixedAssetReceive> getFixedAssetReceiveList();
	 
	 FixedAssetReceive getFixedAssetReceive(String itemId, String date);
	 
	 FixedAssets getFixedAsset(String itemId, String date);
	 
	 DepreciationSummery getDepreciation(String itemId, String purchaseDate);
	 
	 List<DepreciationSummery> getDepreciationListSchedulled(String itemId, String purchaseDate, String flag);
	 
	 List<DepreciationSummery> getDepreciationList(String itemId, String date);
	 
	 List<DepreciationSummery> getDepSummListForUpdate(String itemId, String date, String pDate);
	 
	 FixedAssets getAdditionLengthBySubCategory(String distributionLine, String disLineDivision, String purchaseDate);
	
	 List<FixedAssets> getSLFixedAssetList();
	 
	 List<FixedAssetReceive> getSLRCVFixedAssetList();
}
