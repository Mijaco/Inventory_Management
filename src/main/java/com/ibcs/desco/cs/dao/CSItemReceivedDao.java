package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CSItemReceived;

public interface CSItemReceivedDao {
	// data access for add new CSItemReceived
	public void addCSItemReceived(CSItemReceived cSItemReceived);

	// data access for get all CSItemReceived as List
	public List<CSItemReceived> listCSItemReceiveds();

	// data access for get specific one CSItemReceived information and update
	// CSItemReceived info
	public CSItemReceived getCSItemReceived(int id);

	// data access for Delete an CSItemReceived
	public void deleteCSItemReceived(CSItemReceived cSItemReceived);

	// data access for get all CSItemReceivedByReceivedReportNo as List
	public List<CSItemReceived> listCSItemReceivedByReceivedReportNo(
			String receivedReportNo);

	// data access for get all CSItemReceivedByReceivedReportNo as List
	public List<CSItemReceived> listCSItemReceivedByOperationIds(
			Integer [] operationId);

}
