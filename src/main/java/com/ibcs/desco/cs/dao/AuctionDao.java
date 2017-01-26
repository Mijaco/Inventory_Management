package com.ibcs.desco.cs.dao;

import java.util.List;
import java.util.Map;

import com.ibcs.desco.cs.model.AuctionView;

public interface AuctionDao {

	List<AuctionView> getAuctionViewListByDateRange(String toDate, List<String> catList);

	Map<String, Double> getAuctionViewListByGroup(String toDate, List<String> catList);

	Map<String, Double> getAuctionProcesDtlListGroupByDept(String aucProcesMstId,  List<Integer> itemMasterIds);

	List<String> getItemCategoryList(String auctionCategoryId);

	String getNextAuctionId();
	
	List<Integer> getContractorUserIdList();
}
