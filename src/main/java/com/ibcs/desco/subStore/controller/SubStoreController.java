package com.ibcs.desco.subStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/ss")
public class SubStoreController {

	

	@RequestMapping(value = "/store/conToSsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsRequisitionReport() {
		return new ModelAndView("subStore/conToSsRequisitionReport");
	}
	
	@RequestMapping(value = "/store/conToSsRequisitionStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsRequisitionStoreTicketReport() {
		return new ModelAndView("subStore/conToSsRequisitionStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/conToSsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsReturnSlipReport() {
		return new ModelAndView("subStore/conToSsReturnSlipReport");
	}
	
	@RequestMapping(value = "/store/conToSsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsReturnStoreTicketReport() {
		return new ModelAndView("subStore/conToSsReturnStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/lsToSsGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsGatePassReport() {
		return new ModelAndView("subStore/lsToSsGatePassReport");
	}	
	
	@RequestMapping(value = "/store/requisitionReportLsToSs.do", method = RequestMethod.GET)
	public ModelAndView requisitionReportLsToSs() {
		return new ModelAndView("subStore/requisitionReportLsToSs");
	}
	
	@RequestMapping(value = "/store/lsToSsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsReturnSlipReport() {
		return new ModelAndView("subStore/lsToSsReturnSlipReport");
	}
	
	@RequestMapping(value = "/store/lsToSsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsReturnStoreTicketReport() {
		return new ModelAndView("subStore/lsToSsReturnStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/ssToCsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView ssToCsReturnSlipReport() {
		return new ModelAndView("subStore/ssToCsReturnSlipReport");
	}
	
	@RequestMapping(value = "/store/ssToCsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView ssToCsReturnStoreTicketReport() {
		return new ModelAndView("subStore/ssToCsReturnStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/ssLedgerReport.do", method = RequestMethod.GET)
	public ModelAndView ssLedgerReport() {
		return new ModelAndView("subStore/ssLedgerReport");
	}
	
}

