package com.ibcs.desco.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.AuctionCategory;
import com.ibcs.desco.cs.model.AuctionProcessMst;
import com.ibcs.desco.cs.model.CondemnMst;
import com.ibcs.desco.inventory.model.ItemCategory;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/receivingReport.do", method = RequestMethod.GET)
	public ModelAndView receivingReport() {
		return new ModelAndView("report/receivingReport");
	}

	@RequestMapping(value = "/monthlyReceivingReport.do", method = RequestMethod.GET)
	public ModelAndView monthlyReceivingReport() {
		return new ModelAndView("report/monthlyReceivingReport");
	}

	@RequestMapping(value = "/requisitionReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionReport() {
		return new ModelAndView("report/requisitionReport");
	}

	@RequestMapping(value = "/returnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView returnSlipReport() {
		return new ModelAndView("report/returnSlipReport");
	}

	@RequestMapping(value = "/csStoreTicketIssueReport.do", method = RequestMethod.GET)
	public ModelAndView csStoreTicketIssueReport() {
		return new ModelAndView("report/csStoreTicketIssueReport");
	}

	@RequestMapping(value = "/csStoreTicketReceiveReport.do", method = RequestMethod.GET)
	public ModelAndView csStoreTicketReceiveReport() {
		return new ModelAndView("report/csStoreTicketReceiveReport");
	}

	@RequestMapping(value = "/csStoreTicketReturnReport.do", method = RequestMethod.GET)
	public ModelAndView csStoreTicketReturnReport() {
		return new ModelAndView("report/csStoreTicketReturnReport");
	}

	@RequestMapping(value = "/ledgerReport.do", method = RequestMethod.GET)
	public ModelAndView ledgerReport() {
		return new ModelAndView("report/ledgerReport");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/stockReport.do", method = RequestMethod.GET)
	public ModelAndView stockReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		List<ItemCategory> categoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");
		model.put("khathList", khathList);
		model.put("categoryList", categoryList);
		return new ModelAndView("report/stockReport", model);
	}

	@RequestMapping(value = "/csVehiclePermissionReport.do", method = RequestMethod.GET)
	public ModelAndView csVehiclePermissionReport() {
		return new ModelAndView("report/csVehiclePermissionReport");
	}

	@RequestMapping(value = "/gatePassReport.do", method = RequestMethod.GET)
	public ModelAndView csGatePassReport() {
		return new ModelAndView("report/gatePassReport");
	}

	@RequestMapping(value = "/conToSsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsRequisitionReport() {
		return new ModelAndView("report/conToSsRequisitionReport");
	}

	@RequestMapping(value = "/conToSsRequisitionStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsRequisitionStoreTicketReport() {
		return new ModelAndView("report/conToSsRequisitionStoreTicketReport");
	}

	@RequestMapping(value = "/conToSsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsReturnSlipReport() {
		return new ModelAndView("report/conToSsReturnSlipReport");
	}

	@RequestMapping(value = "/conToSsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView conToSsReturnStoreTicketReport() {
		return new ModelAndView("report/conToSsReturnStoreTicketReport");
	}

	@RequestMapping(value = "/lsToSsGatePassReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsGatePassReport() {
		return new ModelAndView("report/lsToSsGatePassReport");
	}

	@RequestMapping(value = "/requisitionReportLsToSs.do", method = RequestMethod.GET)
	public ModelAndView requisitionReportLsToSs() {
		return new ModelAndView("report/requisitionReportLsToSs");
	}

	@RequestMapping(value = "/lsToSsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsReturnSlipReport() {
		return new ModelAndView("report/lsToSsReturnSlipReport");
	}

	@RequestMapping(value = "/lsToSsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView lsToSsReturnStoreTicketReport() {
		return new ModelAndView("report/lsToSsReturnStoreTicketReport");
	}

	@RequestMapping(value = "/ssToCsReturnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView ssToCsReturnStoreTicketReport() {
		return new ModelAndView("report/ssToCsReturnStoreTicketReport");
	}

	@RequestMapping(value = "/lsGtPassReport.do", method = RequestMethod.GET)
	public ModelAndView lsGatePassReport() {
		return new ModelAndView("report/lsGtPassReport");
	}

	@RequestMapping(value = "/lsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView lsRequisitionReport() {
		return new ModelAndView("report/lsRequisitionReport");
	}

	@RequestMapping(value = "/requisitionStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionStoreTicketReport() {
		return new ModelAndView("report/requisitionStoreTicketReport");
	}

	@RequestMapping(value = "/lsReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView lsReturnSlipReport() {
		return new ModelAndView("report/lsReturnSlipReport");
	}

	@RequestMapping(value = "/returnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView returnStoreTicketReport() {
		return new ModelAndView("report/returnStoreTicketReport");
	}

	@RequestMapping(value = "/lsLedgerReport.do", method = RequestMethod.GET)
	public ModelAndView lsLedgerReport() {
		return new ModelAndView("report/lsLedgerReport");
	}

	@RequestMapping(value = "/conMaterialReport.do", method = RequestMethod.GET)
	public ModelAndView conMaterialReport() {
		return new ModelAndView("report/conMaterialReport");
	}

	@RequestMapping(value = "/conJobReport.do", method = RequestMethod.GET)
	public ModelAndView conJobReport() {
		return new ModelAndView("report/conJobReport");
	}

	@RequestMapping(value = "/averageControlReport.do", method = RequestMethod.GET)
	public ModelAndView averageControlReport() {
		return new ModelAndView("report/averageControlReport");
	}

	@RequestMapping(value = "/storeWiseItemReport.do", method = RequestMethod.GET)
	public ModelAndView storeWiseItemReport() {
		return new ModelAndView("report/storeWiseItemReport");
	}

	@RequestMapping(value = "/demandNoteCodedReport.do", method = RequestMethod.GET)
	public ModelAndView demandNoteCodedReport() {
		return new ModelAndView("report/demandNoteCodedReport");
	}

	@RequestMapping(value = "/demandNoteNonCodedReport.do", method = RequestMethod.GET)
	public ModelAndView demandNoteNonCodedReport() {
		return new ModelAndView("report/demandNoteNonCodedReport");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/common/requirementNoteCoded.do", method = RequestMethod.GET)
	public ModelAndView requirementNoteCoded() {
		Map<String, Object> model = new HashMap<String, Object>();

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", deptId);

		List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");
		model.put("descoSession", descoSession);
		model.put("department", department);
		return new ModelAndView("common/report/requirementNoteCoded", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/common/requirementNoteNonCoded.do", method = RequestMethod.GET)
	public ModelAndView requirementNoteNonCoded() {
		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", deptId);

		List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");
		model.put("descoSession", descoSession);
		model.put("department", department);
		return new ModelAndView("common/report/requirementNoteNonCoded", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/requirementNoteSummaryCoded.do", method = RequestMethod.GET)
	public ModelAndView requirementNoteSummaryCoded() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<Departments> departmentsList = (List<Departments>) (Object) commonService
				.getAllObjectList("Departments");

		List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");

		model.put("descoSession", descoSessionList);
		model.put("departmentsList", departmentsList);
		model.put("itemCategoryList", itemCategoryList);

		return new ModelAndView(
				"procurement/report/requirementNoteSummaryCoded", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/requirementNoteSummaryNonCoded.do", method = RequestMethod.GET)
	public ModelAndView requirementNoteSummaryNonCoded() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<Departments> departmentsList = (List<Departments>) (Object) commonService
				.getAllObjectList("Departments");

		List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		model.put("descoSession", descoSessionList);
		model.put("departmentsList", departmentsList);

		return new ModelAndView(
				"procurement/report/requirementNoteSummaryNonCoded", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proc/appReport.do", method = RequestMethod.GET)
	public ModelAndView procAppReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		model.put("descoSession", descoSessionList);

		return new ModelAndView("procurement/report/appReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/appReport.do", method = RequestMethod.GET)
	public ModelAndView budgetAppReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		model.put("descoSession", descoSessionList);

		return new ModelAndView("budget/report/appReport", model);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/varianceReport.do", method = RequestMethod.GET)
	public ModelAndView budgetVarianceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		model.put("descoSession", descoSessionList);

		return new ModelAndView("budget/report/varianceReport", model);
	}

	// @RequestMapping(value = "/assetPurchasedReport.do", method =
	// RequestMethod.GET)
	// public ModelAndView assetPurchasedReport() {
	// return new ModelAndView("report/assetPurchasedReport");
	// }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assetPurchasedReport.do", method = RequestMethod.GET)
	public ModelAndView generalItemUploadForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			model.put("itemCategoryList", itemCategoryList);
			return new ModelAndView("report/assetPurchasedReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("report/assetPurchasedReport", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/categoryWiseAssetReport.do", method = RequestMethod.GET)
	public ModelAndView categoryWiseAssetReportForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			model.put("itemCategoryList", itemCategoryList);
			return new ModelAndView("report/categoryWiseAssetReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("report/categoryWiseAssetReport", model);
		}
	}

	@RequestMapping(value = "/xReturnReport.do", method = RequestMethod.GET)
	public ModelAndView xReturnReport() {
		return new ModelAndView("workshop/reports/wsToCsXReturnSlipReport");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fixedAssetRegisterReport.do", method = RequestMethod.GET)
	public ModelAndView fixedAssetRegisterReportForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");
			model.put("itemCategoryList", itemCategoryList);
			return new ModelAndView("report/fixedAssetRegisterReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("report/fixedAssetRegisterReport");
		}
	}

	// added by Ashid for SS Menu
	@RequestMapping(value = "/query/lsSsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView queryLsSsRequisitionReport() {
		return new ModelAndView("report/subStore/lsSsRequisitionReport");
	}

	// added by Ashid for SS Menu
	@RequestMapping(value = "/query/lsPdSsRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView queryLsPdSsRequisitionReport() {
		return new ModelAndView("report/subStore/lsPdSsRequisitionReport");
	}

	// added by Ashid for SS Menu
	@RequestMapping(value = "/query/lsSsStoreTicket.do", method = RequestMethod.GET)
	public ModelAndView queryLsSsStoreTicket() {
		return new ModelAndView("report/subStore/lsSsStoreTicketReport");
	}

	// added by Ashid for SS Menu
	@RequestMapping(value = "/query/lsSsGatePass.do", method = RequestMethod.GET)
	public ModelAndView queryLsSsGatePass() {
		return new ModelAndView("report/subStore/lsSsStoreGatePassReport");
	}

	// added by Ashid for CS Menu
	@RequestMapping(value = "/query/wsCsXfRequisitionReport.do", method = RequestMethod.GET)
	public ModelAndView queryWsCsXfRequisitionReport() {
		return new ModelAndView("report/subStore/wsCsXfRequisitionReport");
	}

	// added by Ashid for CS Menu
	@RequestMapping(value = "/query/lsPdCsRequisitionStoreTicket.do", method = RequestMethod.GET)
	public ModelAndView queryLsPdCsRequisitionStoreTicket() {
		return new ModelAndView(
				"centralStore/reports/menu/lsPdCsRequisitionStoreTicket");
	}

	// added by Ashid for Common Menu
	@RequestMapping(value = "/query/localRecieveReport.do", method = RequestMethod.GET)
	public ModelAndView queryLocalRecieveReport() {
		return new ModelAndView("report/common/localRrReportByQuery");
	}

	// Added by Shimul for SS Menu
	@RequestMapping(value = "/query/lsPdSsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	public ModelAndView lsPdSsStoreTicketReportRequisition() {
		return new ModelAndView(
				"subStore/reports/lsPdSsStoreTicketReportRequisition");
	}

	// Added by Shimul for SS Menu
	@RequestMapping(value = "/query/lsPdSsGatePass.do", method = RequestMethod.GET)
	public ModelAndView lsPdSsGatePass() {
		return new ModelAndView("subStore/reports/lsPdSsStoreGatePassReport");
	}

	// Added by Shimul for LS Menu
	@RequestMapping(value = "/query/lsPdCsRequisitionReportLS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsPdCsRequisitionReport() {
		return new ModelAndView(
				"localStore/reports/lsPdCsRequisitionReportLsByQuery");
	}

	// Added by Shimul for LS Menu
	@RequestMapping(value = "/query/lsPdSsRequisitionReportLS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsPdSsRequisitionReportLS() {
		return new ModelAndView(
				"localStore/reports/lsPdSsRequisitionReportLsByQuery");
	}

	// Added by Shimul for LS Menu
	@RequestMapping(value = "/query/lsPdSsGatePassLS.do", method = RequestMethod.GET)
	public ModelAndView lsPdSsGatePassLS() {
		return new ModelAndView("localStore/reports/lsPdSsStoreGatePassReport");
	}

	// Added by Shimul for LS Menu
	@RequestMapping(value = "/query/lsPdCsGatePassLS.do", method = RequestMethod.GET)
	public ModelAndView lsPdCsGatePassLS() {
		return new ModelAndView("localStore/reports/lsPdCsStoreGatePassReport");
	}

	// Added by Shimul for LS Menu
	@RequestMapping(value = "/query/lsPdCsGatePassCS.do", method = RequestMethod.GET)
	public ModelAndView lsPdCsGatePassCS() {
		return new ModelAndView(
				"centralStore/reports/lsPdCsGatePassReportByQuery");
	}

	// added by Nasrin
	@RequestMapping(value = "/query/wsxToCsReqTicket.do", method = RequestMethod.GET)
	public ModelAndView wsxToCsReqTicket() {
		return new ModelAndView(
				"workshop/reports/wsXCsStoreTicketReportRequisition");
	}

	@RequestMapping(value = "/query/wsToCsReqTicket.do", method = RequestMethod.GET)
	public ModelAndView wsToCsReqTicket() {
		return new ModelAndView(
				"workshop/reports/wsCsStoreTicketReportRequisition");
	}
	
	//added by taleb
	@RequestMapping(value = "/query/wsxToCsRetTicket.do", method = RequestMethod.GET)
	public ModelAndView wsxToCsRetTicket() {
		return new ModelAndView(
				"workshop/reports/wsXCsStoreTicketReportRequisition");
	}

	@RequestMapping(value = "/query/wsToCsRetTicket.do", method = RequestMethod.GET)
	public ModelAndView wsToCsRetTicket() {
		return new ModelAndView(
				"workshop/reports/wsCsStoreTicketReportRequisition");
	}

	// added by taleb
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/store/auction/storeWiseUnServItemReport.do", method = RequestMethod.GET)
	public ModelAndView auctionStoreWiseUnServItemReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<AuctionProcessMst> auctionProcessMstList = (List<AuctionProcessMst>) (Object) commonService
					.getAllObjectList("AuctionProcessMst");
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			List<String> csSsList = new ArrayList<String>();
			csSsList.add(SS_DEPT_ID);
			csSsList.add(CS_DEPT_ID);
			List<Departments> depts2 = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumnValueList("Departments", "deptId",
							csSsList);
			depts.addAll(depts2);

			model.put("auctionProcessMstList", auctionProcessMstList);
			model.put("depts", depts);
			return new ModelAndView(
					"centralStore/reports/menu/auctionStoreWiseUnServItemReport",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/store/auction/summeryUnServItemPassReport.do", method = RequestMethod.GET)
	public ModelAndView summeryUnServItemPassReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<AuctionProcessMst> auctionProcessMstList = (List<AuctionProcessMst>) (Object) commonService
					.getAllObjectList("AuctionProcessMst");

			List<AuctionCategory> auctionCategoryList = (List<AuctionCategory>) (Object) commonService
					.getAllObjectList("AuctionCategory");

			model.put("auctionProcessMstList", auctionProcessMstList);
			model.put("auctionCategoryList", auctionCategoryList);
			return new ModelAndView(
					"centralStore/reports/menu/summeryUnServItemPassReport",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/cs/auction/condemnMaterials.do", method = RequestMethod.GET)
	public ModelAndView auctionCondemnMaterials() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<String> auctionIdList = (List<String>) (Object) commonService
					.getDistinctValueListByColumnName("AuctionProcessMst",
							"auctionName");
			model.put("auctionIdList", auctionIdList);
			return new ModelAndView(
					"centralStore/reports/menu/csCondemnMaterialsReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/cs/auction/deptWiseCondemnMaterials.do", method = RequestMethod.GET)
	public ModelAndView auctionDeptWiseCondemnMaterials() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<AuctionProcessMst> auctionProcessMstList = (List<AuctionProcessMst>) (Object) commonService
					.getAllObjectList("AuctionProcessMst");

			List<AuctionCategory> auctionCategoryList = (List<AuctionCategory>) (Object) commonService
					.getAllObjectList("AuctionCategory");

			model.put("auctionProcessMstList", auctionProcessMstList);
			model.put("auctionCategoryList", auctionCategoryList);
			return new ModelAndView(
					"centralStore/reports/menu/deptWiseCondemnMaterialsReport",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/cs/auction/divisionWiseCondemnItems.do", method = RequestMethod.GET)
	public ModelAndView getDivisionWiseCondemnItems(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("mstId", id);
			return new ModelAndView(
					"centralStore/reports/menu/deptWiseCondemnMaterials", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/cs/auction/condemnMaterials.do", method = RequestMethod.GET)
	public ModelAndView getAuctionCondemnMaterials(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("auctionName", id);
			return new ModelAndView(
					"centralStore/reports/menu/csCondemnMaterials", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/cc/auction/ccReport.do", method = RequestMethod.GET)
	public ModelAndView queryCcReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
					.getAllObjectList("CondemnMst");
			model.put("condemnMstList", condemnMstList);
			return new ModelAndView("centralStore/reports/menu/ccReportQuery",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	
	@RequestMapping(value = "/cc/auction/ccReport.do", method = RequestMethod.GET)
	public ModelAndView ccReport(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {			
			model.put("mstId", id);
			return new ModelAndView("centralStore/reports/menu/ccReport",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}


}
