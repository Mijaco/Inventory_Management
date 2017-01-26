package com.ibcs.desco.cs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;

@Controller
@RequestMapping(value = "/cs")
public class CentralStoreController {
	
	@Autowired
	CommonService commonService;

	/*@RequestMapping(value = "/store/gatepass.do", method = RequestMethod.GET)
	public ModelAndView csGatePass() {
		return new ModelAndView("centralStore/csGatePass");
	}*/

	@RequestMapping(value = "/store/receivingReport.do", method = RequestMethod.GET)
	public ModelAndView receivingReport() {
		return new ModelAndView("centralStore/receivingReport");
	}
	
	@RequestMapping(value = "/store/monthlyReceivingReport.do", method = RequestMethod.GET)
	public ModelAndView monthlyReceivingReport() {
		return new ModelAndView("centralStore/monthlyReceivingReport");
	}
	
	@RequestMapping(value = "/store/requisitionReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionReport() {
		return new ModelAndView("centralStore/reports/menu/lsCsRequisitionReport");
	}
	
	@RequestMapping(value = "/store/lsPdCsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView lsPdCsRequisitionReport() {
		return new ModelAndView("centralStore/reports/menu/lsPdCsRequisitionReport");
	}
	
	@RequestMapping(value = "/store/cnPdCsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView cnPdCsRequisitionReport() {
		return new ModelAndView("centralStore/reports/menu/cnPdCsRequisitionReport");
	}
	
	@RequestMapping(value = "/store/ssCsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView ssCsRequisitionReport() {
		return new ModelAndView("centralStore/reports/menu/ssCsRequisitionReport");
	}
	
	@RequestMapping(value = "/store/wsCsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView wsCsRequisitionReport() {
		return new ModelAndView("centralStore/reports/menu/wsCsRequisitionReport");
	}

	@RequestMapping(value = "/store/returnSlipReportSS.do", method = RequestMethod.GET)
	public ModelAndView returnSlipReportSS() {
		return new ModelAndView("centralStore/returnSlipReportSS");
	}
	
	@RequestMapping(value = "/store/returnSlipReportLS.do", method = RequestMethod.GET)
	public ModelAndView returnSlipReportLS() {
		return new ModelAndView("centralStore/returnSlipReportLS");
	}
	
	@RequestMapping(value = "/store/returnSlipReportCN.do", method = RequestMethod.GET)
	public ModelAndView returnSlipReportCN() {
		return new ModelAndView("centralStore/returnSlipReportCN");
	}

	@RequestMapping(value = "/store/csStoreTicketIssueReport.do", method = RequestMethod.GET)
	public ModelAndView csStoreTicketIssueReport() {
		return new ModelAndView("centralStore/csStoreTicketIssueReport");
	}
	
	@RequestMapping(value = "/store/csStoreTicketReturnReport.do", method = RequestMethod.GET)
	public ModelAndView csStoreTicketReturnReport() {
		return new ModelAndView("centralStore/csStoreTicketReturnReport");
	}

	@RequestMapping(value = "/store/ledgerReport.do", method = RequestMethod.GET)
	public ModelAndView ledgerReport() {
		return new ModelAndView("centralStore/ledgerReport");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/store/stockRevenueReport.do", method = RequestMethod.GET)
	public ModelAndView stockRevenueReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		/*List<ItemMaster> itemList = (List<ItemMaster>) (Object) commonService
				.getAllObjectList("ItemMaster");*/
		
		List<ItemCategory> categoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");		
		
		model.put("khathList", khathList);
		model.put("categoryList", categoryList);
		//model.put("itemList", itemList);
		return new ModelAndView("centralStore/stockRevenueReport", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/store/stockProjectReport.do", method = RequestMethod.GET)
	public ModelAndView stockProjectReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		/*List<ItemMaster> itemList = (List<ItemMaster>) (Object) commonService
				.getAllObjectList("ItemMaster");*/
		
		List<ItemCategory> categoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");		
		
		model.put("khathList", khathList);
		model.put("categoryList", categoryList);
		//model.put("itemList", itemList);
		return new ModelAndView("centralStore/stockProjectReport", model);
	}
	
//	@RequestMapping(value = "/store/stockRevenueNProjectReport.do", method = RequestMethod.GET)
//	public ModelAndView stockRevenueNProjectReport() {
//		Map<String, Object> model = new HashMap<String, Object>();
//        List<ItemCategory> categoryList = (List<ItemCategory>) (Object) commonService
//				.getAllObjectList("ItemCategory");		
//				model.put("categoryList", categoryList);
//		
//		return new ModelAndView("centralStore/stockRevenueNProjectReport", model);
//	}
	
	
	@RequestMapping(value = "/store/stockRevenueNProjectReport.do", method = RequestMethod.GET)
	public ModelAndView stockRevenueNProjectRepor() {
		return new ModelAndView("centralStore/stockRevenueNProjectReport");
	}
	
	
	
	
	
	@RequestMapping(value = "/store/csVehiclePermissionReport.do", method = RequestMethod.GET)
	public ModelAndView csVehiclePermissionReport() {
		return new ModelAndView("centralStore/vehiclePermissionReport");
	}
	
	@RequestMapping(value = "/store/csGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView csGatePassReport() {
		return new ModelAndView("centralStore/gatePassReport");
	}
	
	@RequestMapping(value = "/store/MeCnPdCsGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView cnPdCsGatePassReport() {
		return new ModelAndView("centralStore/reports/MeCnPdCsGatePassReport");
	}
	
	@RequestMapping(value = "/store/MelsCsGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView lsCsGatePassReport() {
		return new ModelAndView("centralStore/reports/MelsCsGatePassReport");
	}
	
	@RequestMapping(value = "/store/MeSsCsGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView ssCsGatePassReport() {
		return new ModelAndView("centralStore/reports/MeSsCsGatePassReport");
	}
	
	

}
