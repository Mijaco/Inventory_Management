/*package com.ibcs.desco.subStore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.GatePassApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.CSGatePassMstDtl;
import com.ibcs.desco.cs.bean.CSStoreTicketMstDtl;
import com.ibcs.desco.cs.model.CSGatePassDtl;
import com.ibcs.desco.cs.model.CSGatePassMst;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreItems;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
@PropertySource("classpath:common.properties")
public class SSGatePassController {
	public static final String SS_GATE_PASS = "SS_GATE_PASS";
	public static final String OPEN = "OPEN";
	public static final String DONE = "DONE";
	public static final String BACK = "BACK";
	public static final String RECEIVED = "RECEIVED";
	public static final String CS_STORE_TICKET = "CS_STORE_TICKET";

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CentralStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	GatePassApprovalHierarchyHistoryService gatePassApprovalHierarchyHistoryService;

	@RequestMapping(value = "/ss/gatePassSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csGatePassSearch(CSGatePassMstDtl csGatePassMstDtl) {

		String operationId = csGatePassMstDtl.getGatePassNo();

		String roleName = commonService.getAuthRoleName();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		
		 * List<GatePassApprovalHierarchyHistory>
		 * gatePassApprovalHierarchyHistoryList =
		 * (List<GatePassApprovalHierarchyHistory>) (Object) commonService
		 * .getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
		 * "GatePassApprovalHierarchyHistory", deptId, roleName, OPEN,
		 * operationId);
		 
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
		return new ModelAndView("subStore/csGataPassList", model);
	}

	// gate pass form open
	@RequestMapping(value = "/ss/openGatePass.do", method = RequestMethod.GET)
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
							SS_GATE_PASS, operationId, DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(SS_GATE_PASS);

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
									SS_GATE_PASS, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_GATE_PASS, stateCode + "");
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

			// sent to browser
			model.put("gatePassApproveHistoryList", gatePassApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csGatePassMstdb", csGatePassMstdb);
			model.put("csGatePassDtlList", csGatePassDtlList);
			model.put("buttonValue", buttonValue);

			return new ModelAndView("subStore/csGatePassShowRequisition",
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
			return new ModelAndView("subStore/csGatePassForm", model);
		}
	}

	@RequestMapping(value = "/ss/saveGatePass.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getCSStoreTicketCreateSave(CSGatePassMstDtl csGatePassMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

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

		return "redirect:/ss/openGatePass.do?requisitonId="
				+ csGatePassMstDtl.getRequisitonNo() + "&flag=" + true
				+ "&ticketNo=" + csGatePassMstDtl.getTicketNo()
				+ "&gatePassNo=" + csGatePassMstDtl.getGatePassNo();
	}

	@RequestMapping(value = "/ss/gatePassSubmitApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String csGatePassSubmitApproved(
			Model model,
			@ModelAttribute("csGatePassMstDtl") CSGatePassMstDtl csGatePassMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();
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
				.getApprovalHierarchyByOperationName(SS_GATE_PASS);

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

		
		 * List<Integer> idList = new ArrayList<Integer>();
		 * for(GatePassApprovalHierarchyHistory gp :
		 * approvalHierarchyHistoryList){ idList.add(gp.getId()); }
		 

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
								SS_GATE_PASS, nextStateCode + "");

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
								SS_GATE_PASS, state + "");

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

				
				 * for (CSStoreTicketDtl csStoreTicketDtl :
				 * csStoreTicketDtlList) { CentralStoreItems subStoreItemsdb
				 * = subStoreItemsService
				 * .getCentralStoreItemsByItemId(csStoreTicketDtl .getItemId());
				 * if (subStoreItemsdb != null) { // update item quantity
				 * and last ticket No double newBalance = 0.0; double
				 * currentBalance = Double
				 * .parseDouble(subStoreItemsdb.getBalance()); double
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
				 * subStoreItemsdb.setBalance(newBalance + "");
				 * subStoreItemsdb.setModifiedBy(auth.getName());
				 * subStoreItemsdb.setModifiedDate(new Date());
				 * subStoreItemsdb.setLastTicketNo(ticketNo);
				 * subStoreItemsService
				 * .addCentralStoreItems(subStoreItemsdb); } else {
				 * CentralStoreItems subStoreItems = new
				 * CentralStoreItems(); subStoreItems.setActive(true);
				 * subStoreItems.setBalance(csStoreTicketDtl
				 * .getUseableQty());
				 * subStoreItems.setCreatedBy(auth.getName());
				 * subStoreItems.setCreatedDate(new Date());
				 * subStoreItems.setLastTicketNo(ticketNo);
				 * subStoreItems.setItemId(csStoreTicketDtl .getItemId());
				 * subStoreItems.setId(null);
				 * subStoreItems.setUom(csStoreTicketDtl.getUom());
				 * subStoreItems.setRemarks(csStoreTicketDtl .getRemarks());
				 * subStoreItemsService
				 * .addCentralStoreItems(subStoreItems); // new row insert }
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
				 * .getApprovalHierarchyByOperationName(SS_GATE_PASS);
				 * 
				 * Integer[] gatePassStateCodes = new
				 * Integer[approvalHierarchyListDb .size()]; for (int i = 0; i <
				 * approvalHierarchyListDb.size(); i++) { gatePassStateCodes[i]
				 * = approvalHierarchyListDb.get(i) .getStateCode(); }
				 * Arrays.sort(gatePassStateCodes); // get approve hierarchy for
				 * last state ApprovalHierarchy gatePasspprovalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * SS_GATE_PASS, gatePassStateCodes[0].toString());
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
				 * gpApprovalHierarchyHistory.setOperationName(SS_GATE_PASS);
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
				 
				// Instant Store Ticket Report Call
				model.addAttribute("gatePassNo", gatePassNo);

				return "subStore/csGatePassReport";

			}

		}

		return "redirect:/ss/itemRecieved/storeTicketlist.do";
	}

}
*/