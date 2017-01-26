package com.ibcs.desco.workshop.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.model.AuctionMst;
import com.ibcs.desco.workshop.Dao.MeterTestingReportDao;
import com.ibcs.desco.workshop.model.MeterTestingReport;

@Service
public class MeterTestingReportServiceImpl implements MeterTestingReportService {

	MeterTestingReportDao meterTestingReportDao;

	public MeterTestingReportDao getMeterTestingReportDao() {
		return meterTestingReportDao;
	}

	public void setMeterTestingReportDao(
			MeterTestingReportDao meterTestingReportDao) {
		this.meterTestingReportDao = meterTestingReportDao;
	}

	@Override
	public void saveOrUpdateMeterTestingReport(
			MeterTestingReport meterTestingReport) {
		// TODO Auto-generated method stub
		meterTestingReportDao
				.saveOrUpdateMeterTestingReport(meterTestingReport);

	}

	@Override
	public List<MeterTestingReport> listMeterTestingReport() {
		// TODO Auto-generated method stub
		return meterTestingReportDao.listMeterTestingReport();
	}
	
	@Override
	public List<MeterTestingReport> listMeterTestingReportByFinalSubmit(boolean finalSubmit) {
		// TODO Auto-generated method stub
		return meterTestingReportDao.listMeterTestingReportByFinalSubmit(finalSubmit);
	}

	@Override
	public MeterTestingReport getMeterTestingReport(int id) {
		// TODO Auto-generated method stub
		return meterTestingReportDao.getMeterTestingReport(id);
	}

	@Override
	public void deleteMeterTestingReport(MeterTestingReport meterTestingReport) {
		// TODO Auto-generated method stub
		meterTestingReportDao.deleteMeterTestingReport(meterTestingReport);
	}

	@Override
	public List<MeterTestingReport> listMeterTestingReportByDateRangeAndFinalSubmit(
			Date startDate, Date endDate, boolean finalSubmit) {
		// TODO Auto-generated method stub
		return meterTestingReportDao.listMeterTestingReportByDateRangeAndFinalSubmit(startDate, endDate, finalSubmit);
	}

	@Override
	public AuctionMst getAuctionMstByCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
		return meterTestingReportDao.getAuctionMstByCreatedBy(createdBy);
	}

}
