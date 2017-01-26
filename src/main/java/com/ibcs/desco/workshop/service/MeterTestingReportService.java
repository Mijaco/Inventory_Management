package com.ibcs.desco.workshop.service;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.cs.model.AuctionMst;
import com.ibcs.desco.workshop.model.MeterTestingReport;

public interface MeterTestingReportService {
	// data access for add new MeterTestingReport and Update
	public void saveOrUpdateMeterTestingReport(
			MeterTestingReport meterTestingReport);

	// data access for get all MeterTestingReport as List
	public List<MeterTestingReport> listMeterTestingReport();

	// data access for get specific one MeterTestingReport information and
	// update
	// MeterTestingReport info
	public MeterTestingReport getMeterTestingReport(int id);

	// data access for Delete an MeterTestingReport
	public void deleteMeterTestingReport(MeterTestingReport meterTestingReport);

	public List<MeterTestingReport> listMeterTestingReportByFinalSubmit(
			boolean finalSubmit);

	public List<MeterTestingReport> listMeterTestingReportByDateRangeAndFinalSubmit(
			Date startDate, Date endDate, boolean finalSubmit);
	
	public AuctionMst getAuctionMstByCreatedBy(String createdBy);
}
