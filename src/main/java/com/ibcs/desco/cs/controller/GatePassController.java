package com.ibcs.desco.cs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.GatePassApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.CSGatePassMstDtl;
import com.ibcs.desco.cs.model.CSGatePassDtl;
import com.ibcs.desco.cs.model.CSGatePassMst;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
@PropertySource("classpath:common.properties")
public class GatePassController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	GatePassApprovalHierarchyHistoryService gatePassApprovalHierarchyHistoryService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/gatePassSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csGatePassSearch(CSGatePassMstDtl csGatePassMstDtl) {

		String operationId = csGatePassMstDtl.getGatePassNo();

		String roleName = commonService.getAuthRoleName();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		/*
		 * List<GatePassApprovalHierarchyHistory>
		 * gatePassApprovalHierarchyHistoryList =
		 * (List<GatePassApprovalHierarchyHistory>) (Object) commonService
		 * .getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
		 * "GatePassApprovalHierarchyHistory", deptId, roleName, OPEN,
		 * operationId);
		 */
		List<GatePassApprovalHierarchyHistory> gatePassApprovalHierarchyHistoryList = gatePassApprovalHierarchyHistoryService
				.getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRoleAndGPNo(
						deptId, roleName, OPEN, operationId);

		List<String> ticketNoList = new ArrayList<String>();

		for (int i = 0; i < gatePassApprovalHierarchyHistoryList.size(); i++) {
			ticketNoList.add(gatePassApprovalHierarchyHistoryList.get(i)
					.getTicketNo());
		}

		List<CSGatePassMst> gatePassMstList = (List<CSGatePassMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("CSGatePassMst", "ticketNo",
						ticketNoList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gatePassMstList", gatePassMstList);
		return new ModelAndView("centralStore/csGataPassList", model);
	}

	// gate pass form open
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/openGatePass.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSGatePassCreateOpen(
			CSGatePassMstDtl csGatePassMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();

		// first time flag value is 0.. so if 0 than user should be generate a
		// store ticket against operation id
		// so first condition is true (1). if one user will only show the store
		// ticket
		if (csGatePassMstDtl.isFlag()) {
			// target : show the store ticket
			String currentStatus = "";

			String operationId = csGatePassMstDtl.getRequisitonNo();

			// get all hierarchy history against current operation id and status
			// done
			List<GatePassApprovalHierarchyHistory> gatePassApproveHistoryList = gatePassApprovalHierarchyHistoryService
					.getGatePassApprovalHierarchyHistoryByOppNameOppIdAndStatus(
							CS_GATE_PASS, operationId, DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CS_GATE_PASS);

			// set the button name or value
			String buttonValue = "";
			if (!gatePassApproveHistoryList.isEmpty()
					&& gatePassApproveHistoryList != null) {

				int stateCode = gatePassApproveHistoryList.get(
						gatePassApproveHistoryList.size() - 1).getStateCode();

				int nextStateCode = 0;
				for (int i = 0; i < approveHeirchyList.size(); i++) {
					if (approveHeirchyList.get(i).getStateCode() > stateCode) {
						nextStateCode = approveHeirchyList.get(i)
								.getStateCode();
						break;
					}
				}
				if (nextStateCode > 0) {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_GATE_PASS, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_GATE_PASS, stateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				}

			} else {
				buttonValue = approveHeirchyList.get(0).getButtonName();
			}

			// set current status
			if (!gatePassApproveHistoryList.isEmpty()) {
				currentStatus = gatePassApproveHistoryList.get(
						gatePassApproveHistoryList.size() - 1).getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// current ticket no
			String gatePassNo = csGatePassMstDtl.getGatePassNo();

			// get all store ticket master info against current ticket no
			CSGatePassMst csGatePassMstdb = (CSGatePassMst) commonService
					.getAnObjectByAnyUniqueColumn("CSGatePassMst",
							"gatePassNo", gatePassNo);

			// get all store ticket item details info against current ticket no
			List<CSGatePassDtl> csGatePassDtlList = (List<CSGatePassDtl>) (Object) commonService
					.getObjectListByAnyColumn("CSGatePassDtl", "gatePassNo",
							gatePassNo);

			/* Follwoing four lines are added by Ihteshamul Alam */
			String userrole = csGatePassMstdb.getCreatedBy();
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userrole);
			csGatePassMstdb.setCreatedBy(createBy.getName());

			// sent to browser
			model.put("gatePassApproveHistoryList", gatePassApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csGatePassMstdb", csGatePassMstdb);
			model.put("csGatePassDtlList", csGatePassDtlList);
			model.put("buttonValue", buttonValue);

			return new ModelAndView("centralStore/csGatePassShowRequisition",
					model);

		} else {
			// target : for generate a Gate Pass now will open a form of
			String gatePassNo = csGatePassMstDtl.getGatePassNo();
			CSGatePassMst csGatePassMstdb = (CSGatePassMst) commonService
					.getAnObjectByAnyUniqueColumn("CSGatePassMst",
							"gatePassNo", gatePassNo);

			model.put("csGatePassMst", csGatePassMstdb);
			// Store ticket Items information

			List<CentralStoreRequisitionDtl> csReqDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", csGatePassMstDtl.getRequisitonNo());
			model.put("csReqDtlList", csReqDtlList);
			return new ModelAndView("centralStore/csGatePassForm", model);
		}
	}

	@RequestMapping(value = "/cs/saveGatePass.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getCSStoreTicketCreateSave(CSGatePassMstDtl csGatePassMstDtl) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		// AuthUser authUser = userService.getAuthUserByUserId(userName);

		// Departments department =
		// departmentsService.getDepartmentByDeptId(authUser.getDeptId());

		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> quantityList = null;

		Date now = new Date();

		CSGatePassMst csGatePassMst = (CSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("CSGatePassMst", "id",
						csGatePassMstDtl.getId() + "");

		csGatePassMst.setCreatedBy(userName);
		csGatePassMst.setCreatedDate(now);
		csGatePassMst.setActive(true);
		csGatePassMst.setFlag(true);
		csGatePassMst.setTicketNo(csGatePassMstDtl.getTicketNo());
		csGatePassMst.setGatePassDate(now);

		commonService.saveOrUpdateModelObjectToDB(csGatePassMst);

		if (csGatePassMstDtl.getItemCode() != null) {
			itemCodeList = csGatePassMstDtl.getItemCode();
		}

		if (csGatePassMstDtl.getDescription() != null) {
			descriptionList = csGatePassMstDtl.getDescription();
		}

		if (csGatePassMstDtl.getUom() != null) {
			uomList = csGatePassMstDtl.getUom();
		}

		if (csGatePassMstDtl.getQuantity() != null) {
			quantityList = csGatePassMstDtl.getQuantity();
		}

		if (itemCodeList != null) {
			for (int i = 0; i < itemCodeList.size(); i++) {
				CSGatePassDtl csGatePassDtl = new CSGatePassDtl();
				csGatePassDtl.setActive(true);
				csGatePassDtl.setCreatedBy(userName);
				csGatePassDtl.setCreatedDate(now);
				csGatePassDtl.setGatePassNo(csGatePassMstDtl.getGatePassNo());
				csGatePassDtl.setId(null);
				csGatePassDtl.setCsGatePassMst(csGatePassMst);

				if (itemCodeList != null) {
					csGatePassDtl.setItemCode(itemCodeList.get(i));
				}

				if (descriptionList != null) {
					csGatePassDtl.setDescription(descriptionList.get(i));
				} else {
					csGatePassDtl.setDescription("");
				}

				if (uomList != null) {
					csGatePassDtl.setUom(uomList.get(i));
				} else {
					csGatePassDtl.setUom("");
				}

				if (quantityList != null) {
					csGatePassDtl.setQuantity(quantityList.get(i));
				} else {
					csGatePassDtl.setQuantity(0.0);
				}

				commonService.saveOrUpdateModelObjectToDB(csGatePassDtl);
			}
		}

		return "redirect:/cs/openGatePass.do?requisitonId="
				+ csGatePassMstDtl.getRequisitonNo() + "&flag=" + true
				+ "&ticketNo=" + csGatePassMstDtl.getTicketNo()
				+ "&gatePassNo=" + csGatePassMstDtl.getGatePassNo();
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/cs/gatePassSubmitApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String csGatePassSubmitApproved(
			Model model,
			@ModelAttribute("csGatePassMstDtl") CSGatePassMstDtl csGatePassMstDtl) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		// AuthUser authUser = userService.getAuthUserByUserId(userName);

		// String deptId = authUser.getDeptId();
		//
		String gatePassNo = csGatePassMstDtl.getGatePassNo();

		CSGatePassMst csGatePassMst = (CSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("CSGatePassMst", "gatePassNo",
						gatePassNo);

		List<CSGatePassDtl> csGatePassDtlList = (List<CSGatePassDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSGatePassDtl", "gatePassNo",
						gatePassNo);

		// get All State Codes from Approval Hierarchy and sort Dececding oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_GATE_PASS);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get Current State Code from Approval hierarchy by RR No (Operation
		// Id)

		String operationId = csGatePassMstDtl.getRequisitonNo();
		List<GatePassApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<GatePassApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("GatePassApprovalHierarchyHistory",
						"operationId", operationId);

		/*
		 * List<Integer> idList = new ArrayList<Integer>();
		 * for(GatePassApprovalHierarchyHistory gp :
		 * approvalHierarchyHistoryList){ idList.add(gp.getId()); }
		 */

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		GatePassApprovalHierarchyHistory gatePassApprovalHierarchyHistory = gatePassApprovalHierarchyHistoryService
				.getGatePassApprovalHierarchyHistory(ids[0]);

		int currentStateCode = gatePassApprovalHierarchyHistory.getStateCode();

		int nextStateCode = 0;

		for (int state : stateCodes) {
			if (state > currentStateCode) {
				nextStateCode = state;

				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CS_GATE_PASS, nextStateCode + "");

				gatePassApprovalHierarchyHistory.setStatus(OPEN);
				gatePassApprovalHierarchyHistory.setStateCode(nextStateCode);
				gatePassApprovalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());
				gatePassApprovalHierarchyHistory.setId(null);
				gatePassApprovalHierarchyHistory.setCreatedBy(userName);
				gatePassApprovalHierarchyHistory.setCreatedDate(new Date());
				gatePassApprovalHierarchyHistory
						.setActRoleName(approvalHierarchy.getRoleName());
				gatePassApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());
				gatePassApprovalHierarchyHistory.setTicketNo(gatePassNo);
				commonService
						.saveOrUpdateModelObjectToDB(gatePassApprovalHierarchyHistory);
				break;
			}

			if (state == currentStateCode) {

				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CS_GATE_PASS, state + "");

				gatePassApprovalHierarchyHistory.setStatus(DONE);
				gatePassApprovalHierarchyHistory.setCreatedBy(userName);

				gatePassApprovalHierarchyHistory.setTicketNo(gatePassNo);

				gatePassApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(gatePassApprovalHierarchyHistory);
			}

			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				gatePassApprovalHierarchyHistory.setStatus(DONE);
				commonService
						.saveOrUpdateModelObjectToDB(gatePassApprovalHierarchyHistory);

				// Ledger Update

				/*
				 * for (CSStoreTicketDtl csStoreTicketDtl :
				 * csStoreTicketDtlList) { CentralStoreItems centralStoreItemsdb
				 * = centralStoreItemsService
				 * .getCentralStoreItemsByItemId(csStoreTicketDtl .getItemId());
				 * if (centralStoreItemsdb != null) { // update item quantity
				 * and last ticket No double newBalance = 0.0; double
				 * currentBalance = Double
				 * .parseDouble(centralStoreItemsdb.getBalance()); double
				 * changeBalance = Double
				 * .parseDouble(csStoreTicketDtl.getUseableQty());
				 * 
				 * if (csStoreTicketMstDtl.getStoreTicketType().equals(
				 * CS_RECEIVING_ITEMS)) { newBalance = currentBalance +
				 * changeBalance; } else if
				 * (csStoreTicketMstDtl.getStoreTicketType()
				 * .equals(LS_CS_REQUISITION)) { newBalance = currentBalance -
				 * changeBalance; }
				 * 
				 * centralStoreItemsdb.setBalance(newBalance + "");
				 * centralStoreItemsdb.setModifiedBy(auth.getName());
				 * centralStoreItemsdb.setModifiedDate(new Date());
				 * centralStoreItemsdb.setLastTicketNo(ticketNo);
				 * centralStoreItemsService
				 * .addCentralStoreItems(centralStoreItemsdb); } else {
				 * CentralStoreItems centralStoreItems = new
				 * CentralStoreItems(); centralStoreItems.setActive(true);
				 * centralStoreItems.setBalance(csStoreTicketDtl
				 * .getUseableQty());
				 * centralStoreItems.setCreatedBy(auth.getName());
				 * centralStoreItems.setCreatedDate(new Date());
				 * centralStoreItems.setLastTicketNo(ticketNo);
				 * centralStoreItems.setItemId(csStoreTicketDtl .getItemId());
				 * centralStoreItems.setId(null);
				 * centralStoreItems.setUom(csStoreTicketDtl.getUom());
				 * centralStoreItems.setRemarks(csStoreTicketDtl .getRemarks());
				 * centralStoreItemsService
				 * .addCentralStoreItems(centralStoreItems); // new row insert }
				 * }
				 * 
				 * // open the gate pass for Item issued against Requisition if
				 * (csStoreTicketMstDtl.getStoreTicketType().equals(
				 * LS_CS_REQUISITION)) {
				 * 
				 * CentralStoreRequisitionMst csReqDb =
				 * (CentralStoreRequisitionMst) commonService
				 * .getAnObjectByAnyUniqueColumn( "CentralStoreRequisitionMst",
				 * "requisitionNo", operationId); // code for gate pass
				 * CSGatePassMst csGatePassMst = new CSGatePassMst(); // need to
				 * generate gatepassNo
				 * csGatePassMst.setGatePassNo(UUID.randomUUID() + "");
				 * csGatePassMst.setIssuedTo(csReqDb.getIdenterDesignation());
				 * csGatePassMst.setIssuedBy(commonService.getAuthUserName());
				 * csGatePassMst.setFlag(false); csGatePassMst.setActive(true);
				 * csGatePassMst.setRequisitonNo(operationId);
				 * csGatePassMst.setTicketNo(ticketNo);
				 * csGatePassMst.setWorkOrderNo(csStoreTicketMst
				 * .getWorkOrderNo()); // Save command for Central Store Gate
				 * pass
				 * commonService.saveOrUpdateModelObjectToDB(csGatePassMst);
				 * 
				 * // insert data to gate pass history List<ApprovalHierarchy>
				 * approvalHierarchyListDb = approvalHierarchyService
				 * .getApprovalHierarchyByOperationName(CS_GATE_PASS);
				 * 
				 * Integer[] gatePassStateCodes = new
				 * Integer[approvalHierarchyListDb .size()]; for (int i = 0; i <
				 * approvalHierarchyListDb.size(); i++) { gatePassStateCodes[i]
				 * = approvalHierarchyListDb.get(i) .getStateCode(); }
				 * Arrays.sort(gatePassStateCodes); // get approve hierarchy for
				 * last state ApprovalHierarchy gatePasspprovalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * CS_GATE_PASS, gatePassStateCodes[0].toString());
				 * GatePassApprovalHierarchyHistory gpApprovalHierarchyHistory =
				 * new GatePassApprovalHierarchyHistory();
				 * gpApprovalHierarchyHistory
				 * .setActRoleName(gatePasspprovalHierarchy .getRoleName());
				 * gpApprovalHierarchyHistory.setOperationId(operationId);
				 * 
				 * // dept info Roles role = (Roles) commonService
				 * .getAnObjectByAnyUniqueColumn(
				 * "com.ibcs.desco.admin.model.Roles", "role",
				 * gatePasspprovalHierarchy.getRoleName()); List<AuthUser>
				 * userList = (List<AuthUser>) (Object) commonService
				 * .getObjectListByAnyColumn(
				 * "com.ibcs.desco.admin.model.AuthUser", "roleid",
				 * role.getRole_id() + ""); Departments depart = (Departments)
				 * commonService .getAnObjectByAnyUniqueColumn("Departments",
				 * "deptId", userList.get(0).getDeptId());
				 * gpApprovalHierarchyHistory.setDeptId(depart.getDeptId());
				 * gpApprovalHierarchyHistory.setcDeptName(depart
				 * .getDeptName());
				 * 
				 * gpApprovalHierarchyHistory.setTicketNo(ticketNo);
				 * gpApprovalHierarchyHistory.setOperationName(CS_GATE_PASS);
				 * gpApprovalHierarchyHistory.setCreatedBy(commonService
				 * .getAuthUserName());
				 * gpApprovalHierarchyHistory.setCreatedDate(new Date()); if
				 * (stateCodes.length > 0) { gpApprovalHierarchyHistory
				 * .setStateCode(gatePassStateCodes[0]);
				 * gpApprovalHierarchyHistory
				 * .setStateName(gatePasspprovalHierarchy .getStateName()); }
				 * gpApprovalHierarchyHistory.setStatus(OPEN);
				 * gpApprovalHierarchyHistory.setActive(true); commonService
				 * .saveOrUpdateModelObjectToDB(gpApprovalHierarchyHistory); }
				 */
				// Instant Store Ticket Report Call
				model.addAttribute("gatePassNo", gatePassNo);

				return "centralStore/csGatePassReport";

			}

		}

		return "redirect:/cs/itemRecieved/storeTicketlist.do";
	}

	@RequestMapping(value = "/cs/gatePassGenerate.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView gatePassGenerate(CSGatePassMst gatePassMst) {

		CSGatePassMst csGatePassMst = (CSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("CSGatePassMst", "gatePassNo",
						gatePassMst.getGatePassNo());

		CSStoreTicketMst csStoreTicketMst = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst", "ticketNo",
						csGatePassMst.getTicketNo());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gatePassNo", gatePassMst.getGatePassNo());
		if (csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				CN_PD_CS_REQUISITION)) {
			return new ModelAndView(
					"centralStore/reports/cnPdCsGatePassReport", model);
		} else if (csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				CN_WS_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
						WS_CS_REQUISITION)) {
			return new ModelAndView(
					"centralStore/reports/cnWsCsGatePassReport", model);
		} else if (csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				LS_CS_REQUISITION)) {
			return new ModelAndView("centralStore/reports/lsCsGatePassReport",
					model);
		} else if (csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				LS_PD_CS_REQUISITION)) {
			return new ModelAndView(
					"centralStore/reports/lsPdCsGatePassReport", model);
		} else if (csStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				WS_CS_X_REQUISITION)) {
			return new ModelAndView("centralStore/reports/wsCsXGatePassReport",
					model);
		} else {
			return new ModelAndView("centralStore/reports/ssCsGatePassReport",
					model);
		}
	}

}
