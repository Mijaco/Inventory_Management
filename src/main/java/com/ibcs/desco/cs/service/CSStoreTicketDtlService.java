package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;

public interface CSStoreTicketDtlService {
	public void addCSStoreTicketDtl(CSStoreTicketDtl csCSStoreTicketDtl);
	
	public void addSSStoreTicketDtl(SSStoreTicketDtl csCSStoreTicketDtl);

	public void removeCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl);

	public void editCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl);

	public CSStoreTicketDtl getCSStoreTicketDtl(int id);

	public List<CSStoreTicketDtl> getAllCSStoreTicketDtl();

	public List<CSStoreTicketDtl> getCSStoreTicketDtlByTicketNo(String ticketNo);
}
