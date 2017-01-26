package com.ibcs.desco.cs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.AuctionDao;
import com.ibcs.desco.cs.model.AuctionView;

@Service
public class AuctionServiceImpl implements AuctionService {

	private AuctionDao auctionDao;

	public AuctionDao getAuctionDao() {
		return auctionDao;
	}

	public void setAuctionDao(AuctionDao auctionDao) {
		this.auctionDao = auctionDao;
	}

	@Override
	public List<AuctionView> getAuctionViewListByDateRange(String toDate, List<String> catList) {
		// TODO Auto-generated method stub
		return auctionDao.getAuctionViewListByDateRange(toDate, catList);
	}

	@Override
	public Map<String, Double> getAuctionViewListByGroup(String toDate, List<String> catList) {
		return auctionDao.getAuctionViewListByGroup(toDate, catList); 
	}

	@Override
	public Map<String, Double> getAuctionProcesDtlListGroupByDept(
			String aucProcesMstId, List<Integer> itemMasterIds) {
		return auctionDao.getAuctionProcesDtlListGroupByDept(aucProcesMstId, itemMasterIds);
	}

	@Override
	public List<String> getItemCategoryList(String auctionCategoryId) {
		// TODO Auto-generated method stub
		return auctionDao.getItemCategoryList(auctionCategoryId);
	}

	@Override
	public String getNextAuctionId() {
		return auctionDao.getNextAuctionId();
	}
	
	@Override
	public List<Integer> getContractorUserIdList() {
		return auctionDao.getContractorUserIdList();
	}

}
