package com.ibcs.desco.cs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.dashboard.CentralStore;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnWsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.ItemRcvApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.CSStoreTicketMstDtl;
import com.ibcs.desco.cs.bean.ItemReceived;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.model.CSGatePassDtl;
import com.ibcs.desco.cs.model.CSGatePassMst;
import com.ibcs.desco.cs.model.CSItemLocationDtl;
import com.ibcs.desco.cs.model.CSItemLocationMst;
import com.ibcs.desco.cs.model.CSItemTransactionDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSProcItemRcvMst;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.model.WorkOrderDtl;
import com.ibcs.desco.cs.model.WorkOrderMst;
import com.ibcs.desco.cs.service.CSItemLocationMstService;
import com.ibcs.desco.cs.service.CSItemTransactionMstService;
import com.ibcs.desco.cs.service.CSProcItemRcvDtlService;
import com.ibcs.desco.cs.service.CSProcItemRcvMstService;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.cs.service.ReturnSlipMstService;
import com.ibcs.desco.cs.service.WorkOrderDtlService;
import com.ibcs.desco.cs.service.WorkOrderMstService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.workshop.model.TransformerRegister;

@Controller
@RequestMapping(value = "/cs/itemRecieved")
@PropertySource("classpath:common.properties")
public class CSItemReceivedController extends Constrants {

	// Create Object for Service Classes
	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	ReturnSlipMstService returnSlipMstService;

	@Autowired
	CSItemLocationMstService csItemLocationMstService;

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CSItemTransactionMstService csItemTransactionMstService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ItemRcvApprovalHierarchyHistoryService itemRcvApprovalHierarchyHistoryService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	CSProcItemRcvMstService csProcItemRcvMstService;

	@Autowired
	CSProcItemRcvDtlService csProcItemRcvDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	WorkOrderMstService workOrderMstService;

	@Autowired
	WorkOrderDtlService workOrderDtlService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.receiving.report.prefix}")
	private String receivedReportNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.gate.pass.prefix}")
	private String descoGatePassNoPrefix;

	// get Item Received From where give item Entry after procurement and
	// generate Receiving Report
	// Added by Nasrin
	@RequestMapping(value = "/procItemRcvFromByWOrder.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvFromByWOrder() {

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("categoryList", categoryList);
		return new ModelAndView("centralStore/csProcItemReceivedFormByWOrder",
				model);
	}

	@RequestMapping(value = "/procItemRcvFrom.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvFrom() {

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("categoryList", categoryList);
		return new ModelAndView("centralStore/csProcItemReceivedForm", model);
	}

	// Save the Received Item which procure via procurement department
	@RequestMapping(value = "/itemReceivedSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView centralSotreReceivedItemSave(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived,
			RedirectAttributes redirectAttributes) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// items comes as List
		List<String> itemIdList = null;

		List<String> uomList = null;
		List<Double> expectedQtyList = null;
		List<Double> receivedQtyList = null;
		// List<Double> remainingQtyList = null;
		List<Double> costList = null;
		List<String> ledgerBookNoList = null;
		List<String> ledgerPageNoList = null;
		List<String> remarksList = null;

		// for current Date
		Date now = new Date();
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						itemReceived.getKhathId() + "");

		// Set value for the Master Information of Receiving Report
		CSProcItemRcvMst csProcItemRcvMst = new CSProcItemRcvMst();
		csProcItemRcvMst.setContractNo(itemReceived.getContractNo());
		csProcItemRcvMst.setChalanNo(itemReceived.getChalanNo());
		csProcItemRcvMst.setContractDate(itemReceived.getContractDate());
		csProcItemRcvMst.setInvoiceDate(itemReceived.getInvoiceDate());
		csProcItemRcvMst.setLandingDate(itemReceived.getLandingDate());
		csProcItemRcvMst.setDeliveryDate(itemReceived.getDeliveryDate());
		csProcItemRcvMst.setActive(itemReceived.isActive());
		csProcItemRcvMst.setCreatedBy(itemReceived.getCreatedBy());
		csProcItemRcvMst.setCreatedDate(now);
		csProcItemRcvMst.setReceivedBy(itemReceived.getCreatedBy());
		csProcItemRcvMst.setReceivedDate(now);
		csProcItemRcvMst.setSupplierName(itemReceived.getSupplierName());
		csProcItemRcvMst.setUuid(itemReceived.getUuid());
		csProcItemRcvMst.setKhathId(descoKhath.getId());
		csProcItemRcvMst.setKhathName(descoKhath.getKhathName());
		csProcItemRcvMst.setLedgerName(NEW_SERVICEABLE);

		// Details Table Save to Database from GUI value with Master Table Id}

		if (itemReceived.getItemId() != null) {
			itemIdList = itemReceived.getItemId();
		}

		if (itemReceived.getUom() != null) {
			uomList = itemReceived.getUom();
		}

		if (itemReceived.getExpectedQty() != null) {
			expectedQtyList = itemReceived.getExpectedQty();
		}

		if (itemReceived.getReceivedQty() != null) {
			receivedQtyList = itemReceived.getReceivedQty();
		}

		/*
		 * if (itemReceived.getRemainingQty() != null) { remainingQtyList =
		 * itemReceived.getRemainingQty(); }
		 */

		if (itemReceived.getCost() != null) {
			costList = itemReceived.getCost();
		}

		if (itemReceived.getLedgerBookNo() != null) {
			ledgerBookNoList = itemReceived.getLedgerBookNo();
		}

		if (itemReceived.getLedgerPageNo() != null) {
			ledgerPageNoList = itemReceived.getLedgerPageNo();
		}

		if (itemReceived.getRemarks() != null) {
			remarksList = itemReceived.getRemarks();
		}

		// Get All Approval Hierarchy on CS_RECEIVING_ITEMS
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_RECEIVING_ITEMS);

		// get All State code which define for Receiving process
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get minimum hierarchy information
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, stateCodes[0].toString());

		// get Role List in a Array
		String[] rol = approvalHierarchy.getRoleName().split(",");

		// login person have permission for received process??
		int flag = 0;
		for (int p = 0; p < rol.length; p++) {
			if (rol[p].equals(roleName)) {
				flag = 1;
				break;
			}
		}

		// match the role for receiving process ??
		if (flag == 1) {

			// for Approval Hierarchy History Maintainanace Start
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = new ItemRcvApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(roleName);
			approvalHierarchyHistory.setOperationName(CS_RECEIVING_ITEMS);
			approvalHierarchyHistory.setCreatedBy(itemReceived.getCreatedBy());
			approvalHierarchyHistory.setCreatedDate(now);
			if (stateCodes.length > 0) {
				// All time start with 1st
				approvalHierarchyHistory.setStateCode(stateCodes[0]);
				// State code set from approval hierarchy Table
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());
			}
			approvalHierarchyHistory.setStatus(OPEN);
			approvalHierarchyHistory.setActive(itemReceived.isActive());
			approvalHierarchyHistory.setDeptId(deptId);

			// Receiving Items Save Details Start from Here
			if (itemIdList != null && itemIdList.size() > 0) {

				String descoDeptCode = department.getDescoCode();
				// Auto Numbering on Requisition
				String csRRNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								receivedReportNoPrefix, descoDeptCode,
								separator, "CS_RR_SEQ");
				csProcItemRcvMst.setRrNo(csRRNo);
				approvalHierarchyHistory.setOperationId(csRRNo);

				// Receiving Items Save to Master Table
				csProcItemRcvMstService.addCSProcItemRcvMst(csProcItemRcvMst);

				for (int i = 0; i < itemIdList.size(); i++) {
					CSProcItemRcvDtl csProcItemRcvDtl = new CSProcItemRcvDtl();
					csProcItemRcvDtl.setItemId(itemIdList.get(i));

					ItemMaster itemMaster = (ItemMaster) commonService
							.getAnObjectByAnyUniqueColumn("ItemMaster",
									"itemId", itemIdList.get(i));
					csProcItemRcvDtl.setDescription(itemMaster.getItemName());

					if (!uomList.isEmpty()) {
						csProcItemRcvDtl.setUom(uomList.get(i));
					} else {
						csProcItemRcvDtl.setUom("");
					}

					if (!expectedQtyList.isEmpty()) {
						csProcItemRcvDtl.setExpectedQty(expectedQtyList.get(i));
					} else {
						csProcItemRcvDtl.setExpectedQty(0.0);
					}

					if (!receivedQtyList.isEmpty()) {
						csProcItemRcvDtl.setReceivedQty(receivedQtyList.get(i));
					} else {
						csProcItemRcvDtl.setReceivedQty(0.0);
					}

					/*
					 * if (!remainingQtyList.isEmpty()) {
					 * csProcItemRcvDtl.setRemainingQty(remainingQtyList
					 * .get(i)); } else { csProcItemRcvDtl.setRemainingQty(0.0);
					 * }
					 */

					if (!costList.isEmpty()) {
						csProcItemRcvDtl.setCost(costList.get(i));
					} else {
						csProcItemRcvDtl.setCost(0.0);
					}

					if (!ledgerBookNoList.isEmpty()) {
						csProcItemRcvDtl.setLedgerBookNo(ledgerBookNoList
								.get(i));
					} else {
						csProcItemRcvDtl.setLedgerBookNo("");
					}

					if (!ledgerPageNoList.isEmpty()) {
						csProcItemRcvDtl.setLedgerPageNo(ledgerPageNoList
								.get(i));
					} else {
						csProcItemRcvDtl.setLedgerPageNo("");
					}

					if (!remarksList.isEmpty()) {
						csProcItemRcvDtl.setRemarks(remarksList.get(i));
					} else {
						csProcItemRcvDtl.setRemarks("");
					}

					// set id null for add all items in db and id set from auto
					// number
					csProcItemRcvDtl.setId(null);

					csProcItemRcvDtl.setCreatedBy(userName);
					csProcItemRcvDtl.setCreatedDate(now);
					csProcItemRcvDtl.setActive(itemReceived.isActive());
					csProcItemRcvDtl.setReceivedReportNo(csProcItemRcvMst
							.getRrNo());
					csProcItemRcvDtl.setRrNo(csProcItemRcvMst.getRrNo());

					// item details is saved to db if rcv qty is greater than
					// zero
					if (csProcItemRcvDtl.getReceivedQty() > 0) {
						csProcItemRcvDtlService
								.addCSProcItemRcvDtl(csProcItemRcvDtl);
					}

				}

				// Insert a row to Approval Hierarchy History Table
				itemRcvApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(approvalHierarchyHistory);
			}
		}
		// redirectAttributes.addFlashAttribute("receivedReportNo",
		// csProcItemRcvMst.getRrNo());
		// return "redirect:/cs/itemRecieved/itemRcvShow.do";
		// ?receivedReportNo=" + csProcItemRcvMst.getRrNo();

		// ItemReceived it = new ItemReceived();
		// it.setReceivedReportNo(csProcItemRcvMst.getRrNo());
		// return getCSItemRcvShow(it);
		// return new ModelAndView("centralStore/csProcItemReceivedForm", );
		return new ModelAndView(
				"redirect:/cs/itemRecieved/itemRcvShow.do?operationId="
						+ csProcItemRcvMst.getRrNo());
		// return "redirect:/cs/itemRecieved/list.do";

	}

	// get Item Receiving List after save a new Received Item
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login person Role and login person information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_RECEIVING_ITEMS
		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
						CS_RECEIVING_ITEMS, roleName, OPEN);

		// get operationId List from approval hierarchy history
		String[] operationId = new String[approvalHierarchyHistoryList.size()];
		int x = 0;
		for (ItemRcvApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId[x] = approvalHierarchyHistory.getOperationId();
			x++;
		}

		// get all Received report List which open for login user
		List<CSProcItemRcvMst> csProcItemRcvMstList = csProcItemRcvMstService
				.listCSProcItemRcvMstByOperationIds(operationId);

		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		// sent to the browser
		model.put("csProcItemRcvMstList", csProcItemRcvMstList);
		return new ModelAndView("centralStore/csProcItemReceivedList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rcvItmSerch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvSearch(ItemReceived itemReceived) {
		Map<String, Object> model = new HashMap<String, Object>();

		String operationId = itemReceived.getReceivedReportNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_RECEIVING_ITEMS
		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<ItemRcvApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"ItemRcvApprovalHierarchyHistory", deptId, roleName,
						OPEN, operationId);

		// get operationId List from approval hierarchy history
		String[] operationIdList = new String[approvalHierarchyHistoryList
				.size()];
		int x = 0;
		for (ItemRcvApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationIdList[x] = approvalHierarchyHistory.getOperationId();
			x++;
		}

		// get all Received report List which open for login user
		List<CSProcItemRcvMst> csProcItemRcvMstList = csProcItemRcvMstService
				.listCSProcItemRcvMstByOperationIds(operationIdList);

		// sent to the browser
		model.put("csProcItemRcvMstList", csProcItemRcvMstList);
		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/csProcItemReceivedList", model);
	}

	// Show the items which procure and listed to CS Before
	@RequestMapping(value = "/itemRcvShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvShow(String operationId) {
		ItemReceived itemReceived = new ItemReceived();
		itemReceived.setReceivedReportNo(operationId);
		return this.postCSItemRcvShow(itemReceived);
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/itemRcvShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView postCSItemRcvShow(ItemReceived itemReceived) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		Map<String, Object> model = new HashMap<String, Object>();
		String buttonValue = null;

		List<ApprovalHierarchy> nextManRcvProcs = null;

		String currentStatus = "";
		// operation Id which selected by login user
		String operationId = itemReceived.getReceivedReportNo();

		// get All History for this operation Id which process is already done
		List<ItemRcvApprovalHierarchyHistory> itemRcvApproveHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_RECEIVING_ITEMS, operationId, DONE);

		// get All approval hierarchy of this process
		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_RECEIVING_ITEMS);

		// button name from db which berry user to user
		buttonValue = approveHeirchyList.get(0).getButtonName();

		// set current status from db
		if (!itemRcvApproveHistoryList.isEmpty()) {
			currentStatus = itemRcvApproveHistoryList.get(
					itemRcvApproveHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		// current operation code
		String operationNo = itemReceived.getReceivedReportNo().toString();

		// get master rr information against current operation id
		CSProcItemRcvMst csProcItemRcvMst = csProcItemRcvMstService
				.getCSProcItemRcvMstByRrNo(operationNo);

		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						csProcItemRcvMst.getCreatedBy());
		csProcItemRcvMst.setCreatedBy(createBy.getName());

		// get all item info against current rr no/ operation id
		List<CSProcItemRcvDtl> csProcItemRcvDtlList = csProcItemRcvDtlService
				.getCSProcItemRcvDtlByRrNo(operationNo);

		// Send To next User as my wish
		List<ItemRcvApprovalHierarchyHistory> itemRcvApproveHistoryOpenList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_RECEIVING_ITEMS, operationId, OPEN);

		int currentStateCode = itemRcvApproveHistoryOpenList.get(
				itemRcvApproveHistoryOpenList.size() - 1).getStateCode();

		// send to whom
		nextManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManRcvProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!itemRcvApproveHistoryOpenList.isEmpty()
				&& itemRcvApproveHistoryOpenList != null) {

			// get current state code
			int stateCode = itemRcvApproveHistoryOpenList.get(
					itemRcvApproveHistoryOpenList.size() - 1).getStateCode();

			// deciede for return or not
			returnStateCode = itemRcvApproveHistoryOpenList.get(
					itemRcvApproveHistoryOpenList.size() - 1).getReturn_state();

			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CS_RECEIVING_ITEMS, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		{
			// this block set Receive Qty from TempLocation in GUI
			for (int i = 0; i < csProcItemRcvDtlList.size(); i++) {
				// DecimalFormat df = new DecimalFormat("#.00");
				csProcItemRcvDtlList.get(i).setReceivedQty(
						getTotalQtyFromTempLocation(csProcItemRcvMst.getUuid(),
								csProcItemRcvDtlList.get(i).getItemId()));
			}
			// End of this block
		}

		// all information send to browser
		model.put("returnStateCode", returnStateCode);
		model.put("itemRcvApproveHistoryList", itemRcvApproveHistoryList);
		model.put("currentStatus", currentStatus);
		model.put("csProcItemRcvMst", csProcItemRcvMst);
		model.put("csProcItemRcvDtlList", csProcItemRcvDtlList);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManRcvProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		// Get all Location under CS || Added by Ashid
		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationListJson = commonService
				.getLocationListForGrid(storeLocationList);
		model.put("locationList", locationListJson);
		// End of Code || Added by Ashid

		return new ModelAndView("centralStore/csProcItemReceivedShow", model);
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	private Double getTotalQtyFromTempLocation(String uuid, String itemCode) {
		Double qty = 0.0;
		List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
				.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
						"itemCode", itemCode);
		for (TempItemLocation t : tempItemLocationList) {
			qty += t.getQuantity();
		}
		return qty;
	}

	@RequestMapping(value = "/itemReceivedBackTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {
		String rrNo = itemReceived.getReceivedReportNo();
		String justification = itemReceived.getJustification();
		String backStateCode = itemReceived.getStateCode();

		// get Current User and Role Information

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOperationId(itemReceived
						.getReceivedReportNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setCreatedDate(new Date());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		itemRcvApprovalHierarchyHistoryService
				.addApprovalHierarchyHistory(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		ItemRcvApprovalHierarchyHistory approvalHierarchyHistoryBackState = new ItemRcvApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setDeptId(deptId);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(rrNo);
		approvalHierarchyHistoryBackState
				.setOperationName(approvalHierarchyBackSate.getOperationName());
		approvalHierarchyHistoryBackState
				.setActRoleName(approvalHierarchyBackSate.getRoleName());
		approvalHierarchyHistoryBackState
				.setApprovalHeader(approvalHierarchyBackSate
						.getApprovalHeader());

		// for return same user
		approvalHierarchyHistoryBackState.setStage(BACK);
		approvalHierarchyHistoryBackState.setReturn_to(roleName);
		approvalHierarchyHistoryBackState
				.setReturn_state(currentStateCode + "");

		itemRcvApprovalHierarchyHistoryService
				.addApprovalHierarchyHistory(approvalHierarchyHistoryBackState);

		return "redirect:/cs/itemRecieved/list.do";
	}

	@RequestMapping(value = "/itemReceivedSendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {

		String rrNo = itemReceived.getReceivedReportNo();
		String justification = itemReceived.getJustification();
		String nextStateCode = itemReceived.getStateCode();

		// get Current User and Role Information

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOperationId(itemReceived
						.getReceivedReportNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setCreatedDate(new Date());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		itemRcvApprovalHierarchyHistoryService
				.addApprovalHierarchyHistory(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		ItemRcvApprovalHierarchyHistory approvalHierarchyHistoryNextState = new ItemRcvApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(rrNo + "");
		approvalHierarchyHistoryNextState
				.setOperationName(approvalHierarchyNextSate.getOperationName());
		approvalHierarchyHistoryNextState
				.setActRoleName(approvalHierarchyNextSate.getRoleName());
		approvalHierarchyHistoryNextState
				.setApprovalHeader(approvalHierarchyNextSate
						.getApprovalHeader());

		itemRcvApprovalHierarchyHistoryService
				.addApprovalHierarchyHistory(approvalHierarchyHistoryNextState);

		return "redirect:/cs/itemRecieved/list.do";
	}

	// Receiving Report (RR) Approving process
	@RequestMapping(value = "/itemReceivedSubmitApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSubmitApproved(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {

		// get Current User and Role Information

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// Send return to next user who backed me
		if (!itemReceived.getReturn_state().equals("")
				|| itemReceived.getReturn_state().length() > 0) {

			List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
					.getApprovalHierarchyHistoryByOperationId(itemReceived
							.getReceivedReportNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = itemRcvApprovalHierarchyHistoryService
					.getApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CS_RECEIVING_ITEMS, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setCreatedDate(new Date());
			approvalHierarchyHistory.setJustification(itemReceived
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			itemRcvApprovalHierarchyHistoryService
					.addApprovalHierarchyHistory(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistoryNextState = new ItemRcvApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CS_RECEIVING_ITEMS, itemReceived.getReturn_state());
			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setStatus(OPEN);

			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(itemReceived.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(itemReceived
					.getReceivedReportNo() + "");
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());

			itemRcvApprovalHierarchyHistoryService
					.addApprovalHierarchyHistory(approvalHierarchyHistoryNextState);

		} else {

			// get Received Item List against RR No
			String operationNo = itemReceived.getReceivedReportNo().toString();
			CSProcItemRcvMst csProcItemRcvMst = csProcItemRcvMstService
					.getCSProcItemRcvMstByRrNo(operationNo);
			List<CSProcItemRcvDtl> csProcItemRcvDtlList = csProcItemRcvDtlService
					.getCSProcItemRcvDtlByRrNo(operationNo);

			// get All State Codes from Approval Hierarchy and sort Descending
			// oder for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CS_RECEIVING_ITEMS);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			// (Operation Id)
			List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
					.getApprovalHierarchyHistoryByOperationId(itemReceived
							.getReceivedReportNo());
			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = itemRcvApprovalHierarchyHistoryService
					.getApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_RECEIVING_ITEMS, nextStateCode + "");
					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy
							.getStateName());
					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setDeptId(deptId);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy
							.getRoleName());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					itemRcvApprovalHierarchyHistoryService
							.addApprovalHierarchyHistory(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process
				// will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_RECEIVING_ITEMS, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setJustification(itemReceived
							.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					itemRcvApprovalHierarchyHistoryService
							.addApprovalHierarchyHistory(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {

					// check is insert before
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(itemReceived
							.getJustification());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					itemRcvApprovalHierarchyHistoryService
							.addApprovalHierarchyHistory(approvalHierarchyHistory);

					// update RR Dtl with updated Rcv Qty
					for (CSProcItemRcvDtl rrDtl : csProcItemRcvDtlList) {
						String uuid = csProcItemRcvMst.getUuid();
						String itemCode = rrDtl.getItemId();
						rrDtl.setReceivedQty(getTotalQtyFromTempLocation(uuid,
								itemCode));
						commonService.saveOrUpdateModelObjectToDB(rrDtl);
					}

					// csProcItemRcvMst update
					csProcItemRcvMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(csProcItemRcvMst);

					// get updated DtlList from DB
					csProcItemRcvDtlList = csProcItemRcvDtlService
							.getCSProcItemRcvDtlByRrNo(operationNo);

					// this block will update remaining qty in WorkOrderDtl
					for (CSProcItemRcvDtl rcvDtl : csProcItemRcvDtlList) {
						String itemCode = rcvDtl.getItemId();
						Double rcvQty = rcvDtl.getReceivedQty();
						String woNo = csProcItemRcvMst.getContractNo();
						WorkOrderDtl workOrderDtldb = (WorkOrderDtl) commonService
								.getObjectListByTwoColumn("WorkOrderDtl",
										"workOrderNo", woNo, "itemId", itemCode)
								.get(0);
						workOrderDtldb.setRemainingQty(workOrderDtldb
								.getRemainingQty() - rcvQty);
						commonService
								.saveOrUpdateModelObjectToDB(workOrderDtldb);
					}

					this.csLedgerLocationRR(csProcItemRcvDtlList, userName,
							roleName, authUser, department, csProcItemRcvMst);

					model.addAttribute("rrNo", csProcItemRcvMst.getRrNo());
					return "centralStore/csReceivedReport";
				}
			}

		}
		return "redirect:/cs/itemRecieved/list.do";
	}

	// tray value will assign for here and view central store home/welcome page
	@RequestMapping(value = "/centralStore.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#centralStore, 'READ')")
	public ModelAndView getCenteralStore(CentralStore centralStore) {

		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// Received Item Tray Start
		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
						CS_RECEIVING_ITEMS, roleName, OPEN);

		String[] operationId = new String[approvalHierarchyHistoryList.size()];
		int x = 0;
		for (ItemRcvApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId[x] = approvalHierarchyHistory.getOperationId();
			x++;
		}

		List<CSProcItemRcvMst> csProcItemRcvMstList = csProcItemRcvMstService
				.listCSProcItemRcvMstByOperationIds(operationId);

		int csProcItemRcvMstSize = 0;
		if (csProcItemRcvMstList != null) {
			csProcItemRcvMstSize = csProcItemRcvMstList.size();
		}

		// Received Item Tray End and Store Ticket Tray Start
		/*
		 * List<StoreTicketApprovalHierarchyHistory>
		 * storeTicketApprovalHierarchyHistoryList =
		 * storeTicketApprovalHierarchyHistoryService
		 * .getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole
		 * ( CS_STORE_TICKET, roleName, OPEN);
		 */
		List<StoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = storeTicketApprovalHierarchyHistoryService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
						deptId, roleName, OPEN, "");
		int storeTicketTotal = 0;
		if (storeTicketApprovalHierarchyHistoryList.size() > 0) {
			String[] operationId1 = new String[storeTicketApprovalHierarchyHistoryList
					.size()];
			int y = 0;
			for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
				operationId1[y] = storeTicketApprovalHierarchyHistory
						.getOperationId();
				y++;
			}

			List<CSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
					.listCSProcItemRcvMstByOperationIds(operationId1);
			if (csStoreTicketMstList != null) {
				storeTicketTotal = csStoreTicketMstList.size();
			}
		}

		// List<ReturnSlipMst>
		int returnSlipTray = 0;
		if (this.retrunSlipListForTray() != null) {
			returnSlipTray = this.retrunSlipListForTray().size();
		}
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.reqisitionListForTray() != null) {
			requisitionTray = this.reqisitionListForTray().size();
		}

		// Store Ticket Tray End
		model.put("receivedItemTotal", csProcItemRcvMstSize);
		model.put("storeTicketTotal", storeTicketTotal);
		model.put("returnSlipTray", returnSlipTray);
		model.put("requisitionTray", requisitionTray);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/centralStore", model);
	}

	// get store ticket List filter on login user and login user role
	@RequestMapping(value = "/storeTicketlist.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login user information and login user role

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get which all approval hierarchy which open for login user
		List<StoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = storeTicketApprovalHierarchyHistoryService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
						deptId, roleName, OPEN, "");

		// get all operation id which open for this login user
		String[] operationId = new String[storeTicketApprovalHierarchyHistoryList
				.size()];
		int x = 0;
		for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationId[x] = storeTicketApprovalHierarchyHistory
					.getOperationId();
			x++;
		}

		// get All store ticket which open for the login user
		List<CSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
				.listCSProcItemRcvMstByOperationIds(operationId);
		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		// list
		model.put("csStoreTicketMstList", csStoreTicketMstList);
		return new ModelAndView("centralStore/csStoreTicketList", model);
	}

	// get store ticket Search on login user and login user role
	@RequestMapping(value = "/storeTicketSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketSearch(CSStoreTicketMst csStoreTicketMst) {
		Map<String, Object> model = new HashMap<String, Object>();

		String operationId = csStoreTicketMst.getTicketNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get which all approval hierarchy which open for login user
		List<StoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = storeTicketApprovalHierarchyHistoryService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
						deptId, roleName, OPEN, operationId);

		// get all operation id which open for this login user
		String[] operationIdList = new String[storeTicketApprovalHierarchyHistoryList
				.size()];
		int x = 0;
		for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationIdList[x] = storeTicketApprovalHierarchyHistory
					.getOperationId();
			x++;
		}

		// get All store ticket which open for the login user
		List<CSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
				.listCSProcItemRcvMstByOperationIds(operationIdList);

		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		// list
		model.put("csStoreTicketMstList", csStoreTicketMstList);
		return new ModelAndView("centralStore/csStoreTicketList", model);
	}

	// after receiving report store ticket come to next user who generate a
	// store ticket and show that

	@RequestMapping(value = "/openStoreTicket.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView openStoreTicketPOST(CSStoreTicketMst csStoreTicketMst) {
		try {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
		} catch (Exception e) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/openStoreTicket.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView openStoreTicketGET(CSStoreTicketMst csStoreTicketMst) {
		try {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
		} catch (Exception e) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	private ModelAndView getCSStoreTicketCreateOpen(
			CSStoreTicketMst csStoreTicketMst) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		Map<String, Object> model = new HashMap<String, Object>();

		// first time flag value is 0.. so if 0 than user should be generate a
		// store ticket against operation id
		// so first condition is true (1). if one user will only show the store
		// ticket
		if (csStoreTicketMst.isFlag()) {
			// target : show the store ticket
			String currentStatus = "";

			String operationId = csStoreTicketMst.getOperationId();

			// get all hierarchy history against current operation id and status
			// done
			List<StoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = storeTicketApprovalHierarchyHistoryService
					.getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
							CS_STORE_TICKET, operationId, DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

			// set the button name or value
			String buttonValue = "";
			if (!storeTicketApproveHistoryList.isEmpty()
					&& storeTicketApproveHistoryList != null) {

				int stateCode = storeTicketApproveHistoryList.get(
						storeTicketApproveHistoryList.size() - 1)
						.getStateCode();

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
									CS_STORE_TICKET, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_STORE_TICKET, stateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				}

			} else {
				buttonValue = approveHeirchyList.get(0).getButtonName();
			}

			// set current status
			if (!storeTicketApproveHistoryList.isEmpty()) {
				currentStatus = storeTicketApproveHistoryList.get(
						storeTicketApproveHistoryList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// current ticket no
			String ticketNo = csStoreTicketMst.getTicketNo();

			// get all store ticket master info against current ticket no
			CSStoreTicketMst csStoreTicketMstdb = csStoreTicketMstService
					.getCSStoreTicketMstByTicketNo(ticketNo);

			// UserId to Username code block - Ihteshamul Alam :: start
			String receivedBy = csStoreTicketMstdb.getReceivedBy();

			if (csStoreTicketMst.getStoreTicketType().equals(
					CN_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_WS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_REQUISITION)) {
				AuthUser rcvAuth = (AuthUser) commonService
						.getAnObjectByAnyUniqueColumn(
								"com.ibcs.desco.admin.model.AuthUser",
								"userid", receivedBy);
				if (rcvAuth != null) {
					csStoreTicketMstdb.setReceivedBy(rcvAuth.getName());
				}
			}

			String createdBy = csStoreTicketMstdb.getCreatedBy();
			AuthUser createdAuth = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							createdBy);
			csStoreTicketMstdb.setCreatedBy(createdAuth.getName());

			// UserId to Username code block - Ihteshamul Alam :: end

			// get all store ticket item details info against current ticket no
			List<CSStoreTicketDtl> csStoreTicketDtlList = csStoreTicketDtlService
					.getCSStoreTicketDtlByTicketNo(ticketNo);

			// sent to browser
			model.put("storeTicketApproveHistoryList",
					storeTicketApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csStoreTicketMst", csStoreTicketMstdb);
			model.put("csStoreTicketDtlList", csStoreTicketDtlList);
			model.put("buttonValue", buttonValue);

			// header
			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_RETURN)) {
				return new ModelAndView("centralStore/csStoreTicketShowRS",
						model);
			} else if (csStoreTicketMst.getStoreTicketType().equals(
					LS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_WS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_REQUISITION)) {
				return new ModelAndView(
						"centralStore/csStoreTicketShowRequisition", model);
			} else {
				return null;
			}

		} else {
			// target : for generate a store ticket now will open a form of
			// store ticket with some value
			// store ticket master (common) information
			CSStoreTicketMst csStoreTicketMstdb = csStoreTicketMstService
					.getCSStoreTicketMstByTicketNo(csStoreTicketMst
							.getTicketNo());

			String userId = csStoreTicketMstdb.getReturnFor();
			AuthUser athUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userId);
			if (athUser != null) {
				csStoreTicketMstdb.setReturnFor(athUser.getName());
			}

			model.put("csStoreTicketMst", csStoreTicketMstdb);

			// Store ticket Items information
			if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_RETURN)) {
				List<ReturnSlipDtl> csReturnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
						.getObjectListByAnyColumn("ReturnSlipDtl",
								"returnSlipNo",
								csStoreTicketMst.getOperationId());

				// header
				model.put("deptName", department.getDeptName());
				model.put("deptAddress", department.getAddress() + ", "
						+ department.getContactNo());

				model.put("csReturnSlipDtlList", csReturnSlipDtlList);
				return new ModelAndView("centralStore/csStoreTicketFormRS",
						model);
			} else if (csStoreTicketMst.getStoreTicketType().equals(
					LS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_WS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_REQUISITION)) {
				List<CentralStoreRequisitionDtl> csRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
								"requisitionNo",
								csStoreTicketMst.getOperationId());

				// Get ST Dtl by Ledger Type || Added By Ashid
				List<CSStoreTicketDtl> csStoreTicketDtlList = getCsTktDtl(
						csStoreTicketMst, csRequisitionDtlList);

				model.put("csStoreTicketDtlList", csStoreTicketDtlList);
				// model.put("csRequisitionDtlList", csRequisitionDtlList);

				// header
				model.put("deptName", department.getDeptName());
				model.put("deptAddress", department.getAddress() + ", "
						+ department.getContactNo());

				return new ModelAndView(
						"centralStore/csStoreTicketFormRequisition", model);
			}

			return null;
		}
	}

	List<CSStoreTicketDtl> getCsTktDtl(CSStoreTicketMst csStoreTicketMst,
			List<CentralStoreRequisitionDtl> csRequisitionDtlList) {
		List<CSStoreTicketDtl> stDtlList = new ArrayList<CSStoreTicketDtl>();
		CentralStoreRequisitionMst csReqMst = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", csStoreTicketMst.getOperationId());
		for (CentralStoreRequisitionDtl sd : csRequisitionDtlList) {
			String itemCode = sd.getItemCode();
			String uuid = csReqMst.getUuid();
			//

			//
			@SuppressWarnings("unchecked")
			List<TempItemLocation> tmpList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"itemCode", itemCode);
			Double nsQty = 0.0, rsQty = 0.0, usQty = 0.0;
			for (TempItemLocation tp : tmpList) {
				if (tp.getLedgerName().toUpperCase()
						.equals(NEW_SERVICEABLE.toUpperCase())) {
					nsQty += tp.getQuantity();
				} else if (tp.getLedgerName().toUpperCase()
						.equals(RECOVERY_SERVICEABLE.toUpperCase())) {
					rsQty += tp.getQuantity();
				} else if (tp.getLedgerName().toUpperCase()
						.equals(UNSERVICEABLE.toUpperCase())) {
					usQty += tp.getQuantity();
				}
			}
			// Set Ledger Type wise Dtl
			if (nsQty > 0) {
				CSStoreTicketDtl stDtl = new CSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setCsStoreTicketMst(csStoreTicketMst);
				stDtl.setActive(true);
				stDtl.setLedgerName(NEW_SERVICEABLE);
				stDtl.setQuantity(nsQty);
				stDtlList.add(stDtl);
			}

			if (rsQty > 0) {
				CSStoreTicketDtl stDtl = new CSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setCsStoreTicketMst(csStoreTicketMst);
				stDtl.setActive(true);
				stDtl.setLedgerName(RECOVERY_SERVICEABLE);
				stDtl.setQuantity(rsQty);
				stDtlList.add(stDtl);
			}

			if (usQty > 0) {
				CSStoreTicketDtl stDtl = new CSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setCsStoreTicketMst(csStoreTicketMst);
				stDtl.setActive(true);
				stDtl.setLedgerName(UNSERVICEABLE);
				stDtl.setQuantity(usQty);
				stDtlList.add(stDtl);
			}

		}

		return stDtlList;
	}

	@RequestMapping(value = "/saveStoreTicket.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketCreateSave(
			CSStoreTicketMstDtl csStoreTicketMstDtl) {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		List<String> itemIdList = null;
		List<String> descriptionList = null;
		List<String> uomList = null;
		List<Double> costList = null;
		List<String> ledgerBookNoList = null;
		List<String> ledgerPageNoList = null;
		List<Double> quantityList = null;
		List<String> remarksList = null;
		List<String> ledgerNameList = null;
		Date now = new Date();

		CSStoreTicketMst csStoreTicketMst = csStoreTicketMstService
				.getCSStoreTicketMst(csStoreTicketMstDtl.getId());

		CentralStoreRequisitionMst csRequisitionMst = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", csStoreTicketMst.getOperationId());

		csStoreTicketMst.setReceivedBy(csRequisitionMst.getReceivedBy());
		csStoreTicketMst.setCreatedBy(userName);
		csStoreTicketMst.setCreatedDate(now);
		csStoreTicketMst.setActive(true);
		csStoreTicketMst.setFlag(true);
		csStoreTicketMst.setIssuedBy(authUser.getName());
		csStoreTicketMst.setApproved(false);
		csStoreTicketMst.setTicketDate(now);

		csStoreTicketMstService.editCSStoreTicketMst(csStoreTicketMst);

		if (csStoreTicketMstDtl.getItemId() != null) {
			itemIdList = csStoreTicketMstDtl.getItemId();
		}

		if (csStoreTicketMstDtl.getDescription() != null) {
			descriptionList = csStoreTicketMstDtl.getDescription();
		}

		if (csStoreTicketMstDtl.getUom() != null) {
			uomList = csStoreTicketMstDtl.getUom();
		}

		if (csStoreTicketMstDtl.getCost() != null) {
			costList = csStoreTicketMstDtl.getCost();
		}
		if (csStoreTicketMstDtl.getLedgerName() != null) {
			ledgerNameList = csStoreTicketMstDtl.getLedgerName();
		}
		if (csStoreTicketMstDtl.getRemarks() != null) {
			remarksList = csStoreTicketMstDtl.getRemarks();
		}

		if (csStoreTicketMstDtl.getQuantity() != null) {
			quantityList = csStoreTicketMstDtl.getQuantity();
		}

		if (csStoreTicketMstDtl.getLedgerBookNo() != null) {
			ledgerBookNoList = csStoreTicketMstDtl.getLedgerBookNo();
		}

		if (csStoreTicketMstDtl.getLedgerPageNo() != null) {
			ledgerPageNoList = csStoreTicketMstDtl.getLedgerPageNo();
		}

		if (csStoreTicketMstDtl.getLedgerName() != null) {
			ledgerNameList = csStoreTicketMstDtl.getLedgerName();
		}

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				CSStoreTicketDtl csStoreTicketDtl = new CSStoreTicketDtl();
				csStoreTicketDtl.setActive(true);
				csStoreTicketDtl.setCreatedBy(userName);
				csStoreTicketDtl.setCreatedDate(now);
				csStoreTicketDtl.setTicketNo(csStoreTicketMstDtl.getTicketNo());
				csStoreTicketDtl.setId(null);

				if (itemIdList != null) {
					csStoreTicketDtl.setItemId(itemIdList.get(i));
				}

				if (descriptionList.size() > 0 || descriptionList != null) {
					csStoreTicketDtl.setDescription(descriptionList.get(i));
				}

				if (uomList.size() > 0 || uomList != null) {
					csStoreTicketDtl.setUom(uomList.get(i));
				}

				if (costList != null) {
					csStoreTicketDtl.setCost(costList.get(i));
				} else {
					csStoreTicketDtl.setCost(0.0);
				}
				if (ledgerNameList != null) {
					csStoreTicketDtl.setLedgerName(ledgerNameList.get(i));
				} else {
					csStoreTicketDtl.setCost(0.0);
				}
				if (remarksList.size() > 0) {
					csStoreTicketDtl.setRemarks(remarksList.get(i));
				} else {
					csStoreTicketDtl.setRemarks("");
				}

				if (quantityList.size() > 0 || quantityList != null) {
					csStoreTicketDtl.setQuantity(quantityList.get(i));
				}
				if (ledgerBookNoList.size() > 0) {
					csStoreTicketDtl.setLedgerBookNo(ledgerBookNoList.get(i));
				} else {
					csStoreTicketDtl.setLedgerBookNo("");
				}

				if (ledgerPageNoList.size() > 0) {
					csStoreTicketDtl.setLedgerPageNo(ledgerPageNoList.get(i));
				} else {
					csStoreTicketDtl.setLedgerPageNo("");
				}

				if (ledgerNameList.size() > 0) {
					csStoreTicketDtl.setLedgerName(ledgerNameList.get(i));
				} else {
					csStoreTicketDtl.setLedgerName("");
				}

				csStoreTicketDtlService.addCSStoreTicketDtl(csStoreTicketDtl);
			}
		}

		// return "redirect:/cs/itemRecieved/storeTicketlist.do";

		if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						LS_PD_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						CN_PD_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						CN_WS_CS_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_X_REQUISITION)
				|| csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_REQUISITION)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveStoreTicketForCSReturnSlip.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketCreateSaveForCSReturnSlip(
			CSStoreTicketMstDtl csStoreTicketMstDtl,
			RedirectAttributes redirectAttributes) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		List<String> itemIdList = null;
		List<String> descriptionList = null;
		List<String> uomList = null;
		List<Double> costList = null;
		List<String> ledgerBookNoList = null;
		List<String> ledgerPageNoList = null;
		List<String> remarksList = null;
		List<Double> qtyNewServiceableList = null;
		List<Double> qtyRecServiceableList = null;
		List<Double> qtyUnServiceableList = null;
		Date now = new Date();

		CSStoreTicketMst csStoreTicketMst = csStoreTicketMstService
				.getCSStoreTicketMst(csStoreTicketMstDtl.getId());
		csStoreTicketMst.setReceivedBy(userName);
		csStoreTicketMst.setCreatedBy(userName);
		csStoreTicketMst.setCreatedDate(now);
		csStoreTicketMst.setActive(true);
		csStoreTicketMst.setFlag(true);
		csStoreTicketMst.setApproved(false);
		csStoreTicketMst.setTicketDate(now);

		csStoreTicketMstService.editCSStoreTicketMst(csStoreTicketMst);

		if (csStoreTicketMstDtl.getItemId() != null) {
			itemIdList = csStoreTicketMstDtl.getItemId();
		}

		if (csStoreTicketMstDtl.getDescription() != null) {
			descriptionList = csStoreTicketMstDtl.getDescription();
		}

		if (csStoreTicketMstDtl.getUom() != null) {
			uomList = csStoreTicketMstDtl.getUom();
		}

		if (csStoreTicketMstDtl.getCost() != null) {
			costList = csStoreTicketMstDtl.getCost();
		}
		/*
		 * if (csStoreTicketMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csStoreTicketMstDtl.getLedgerName(); }
		 */
		if (csStoreTicketMstDtl.getRemarks() != null) {
			remarksList = csStoreTicketMstDtl.getRemarks();
		}

		/*
		 * if (csStoreTicketMstDtl.getQuantity() != null) { quantityList =
		 * csStoreTicketMstDtl.getQuantity(); }
		 */

		if (csStoreTicketMstDtl.getLedgerBookNo() != null) {
			ledgerBookNoList = csStoreTicketMstDtl.getLedgerBookNo();
		}

		if (csStoreTicketMstDtl.getLedgerPageNo() != null) {
			ledgerPageNoList = csStoreTicketMstDtl.getLedgerPageNo();
		}

		/*
		 * if (csStoreTicketMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csStoreTicketMstDtl.getLedgerName(); }
		 */

		if (csStoreTicketMstDtl.getQtyNewServiceable() != null) {
			qtyNewServiceableList = csStoreTicketMstDtl.getQtyNewServiceable();
		}

		if (csStoreTicketMstDtl.getQtyRecServiceable() != null) {
			qtyRecServiceableList = csStoreTicketMstDtl.getQtyRecServiceable();
		}

		if (csStoreTicketMstDtl.getQtyUnServiceable() != null) {
			qtyUnServiceableList = csStoreTicketMstDtl.getQtyUnServiceable();
		}

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				CSStoreTicketDtl csStoreTicketDtl = new CSStoreTicketDtl();
				csStoreTicketDtl.setActive(true);
				csStoreTicketDtl.setCreatedBy(userName);
				csStoreTicketDtl.setCreatedDate(now);
				csStoreTicketDtl.setTicketNo(csStoreTicketMstDtl.getTicketNo());
				csStoreTicketDtl.setId(null);

				if (itemIdList != null) {
					csStoreTicketDtl.setItemId(itemIdList.get(i));
				}

				if (descriptionList.size() > 0 || descriptionList != null) {
					csStoreTicketDtl.setDescription(descriptionList.get(i));
				}
				if (uomList.size() > 0 || uomList != null) {
					csStoreTicketDtl.setUom(uomList.get(i));
				}

				if (costList != null) {
					csStoreTicketDtl.setCost(costList.get(i));
				} else {
					csStoreTicketDtl.setCost(0.0);
				}
				/*
				 * if (ledgerNameList != null) {
				 * csStoreTicketDtl.setLedgerName(ledgerNameList.get(i)); } else
				 * { csStoreTicketDtl.setCost(0.0); }
				 */
				if (remarksList.size() > 0) {
					csStoreTicketDtl.setRemarks(remarksList.get(i));
				} else {
					csStoreTicketDtl.setRemarks("");
				}

				/*
				 * if (quantityList.size() > 0 || quantityList != null) {
				 * csStoreTicketDtl.setQuantity(quantityList.get(i)); } else {
				 * csStoreTicketDtl.setQuantity(0.0); }
				 */

				if (ledgerBookNoList.size() > 0) {
					csStoreTicketDtl.setLedgerBookNo(ledgerBookNoList.get(i));
				} else {
					csStoreTicketDtl.setLedgerBookNo("");
				}

				if (ledgerPageNoList.size() > 0) {
					csStoreTicketDtl.setLedgerPageNo(ledgerPageNoList.get(i));
				} else {
					csStoreTicketDtl.setLedgerPageNo("");
				}

				/*
				 * if (ledgerNameList.size() > 0) {
				 * csStoreTicketDtl.setLedgerName(ledgerNameList.get(i)); } else
				 * { csStoreTicketDtl.setLedgerName(""); }
				 */

				if (qtyNewServiceableList.size() > 0
						|| qtyNewServiceableList != null) {
					if (qtyNewServiceableList.get(i) > 0) {
						csStoreTicketDtl.setQuantity(qtyNewServiceableList
								.get(i));
						csStoreTicketDtl.setLedgerName(NEW_SERVICEABLE);
						csStoreTicketDtlService
								.addCSStoreTicketDtl(csStoreTicketDtl);
					}
				}
				if (qtyRecServiceableList.size() > 0
						|| qtyRecServiceableList != null) {
					if (qtyRecServiceableList.get(i) > 0) {
						csStoreTicketDtl.setQuantity(qtyRecServiceableList
								.get(i));
						csStoreTicketDtl.setLedgerName(RECOVERY_SERVICEABLE);
						csStoreTicketDtlService
								.addCSStoreTicketDtl(csStoreTicketDtl);
					}
				}
				// if (qtyUnServiceableList.size() > 0 || qtyUnServiceableList
				// != null) {
				if (qtyUnServiceableList.size() > 0
						&& qtyUnServiceableList.get(0) > 0) {
					if (qtyUnServiceableList.get(i) > 0) {
						csStoreTicketDtl.setQuantity(qtyUnServiceableList
								.get(i));
						csStoreTicketDtl.setLedgerName(UNSERVICEABLE);
						csStoreTicketDtlService
								.addCSStoreTicketDtl(csStoreTicketDtl);
					}
				}

				// csStoreTicketDtlService.addCSStoreTicketDtl(csStoreTicketDtl);
			}
		}
		redirectAttributes.addFlashAttribute("csStoreTicketMst",
				csStoreTicketMst);
		// return "redirect:/cs/itemRecieved/storeTicketlist.do";

		if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_RETURN_SLIP)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
			// return "redirect:/cs/itemRecieved/openStoreTicket.do";
		} else if (csStoreTicketMst.getStoreTicketType().equals(
				SS_CS_RETURN_SLIP)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
			// return "redirect:/cs/itemRecieved/openStoreTicket.do";
		} else if (csStoreTicketMst.getStoreTicketType()
				.equals(CN_PD_CS_RETURN)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
		}

		else if (csStoreTicketMst.getStoreTicketType().equals(WS_CS_X_RETURN)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
			// return "redirect:/cs/itemRecieved/openStoreTicket.do";
		} else if (csStoreTicketMst.getStoreTicketType().equals(WS_CS_RETURN)) {
			return getCSStoreTicketCreateOpen(csStoreTicketMst);
			// return "redirect:/cs/itemRecieved/openStoreTicket.do";
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/storeTicketSubmitApproved.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreStoreTicketSubmitApproved(
			Model model,
			@ModelAttribute("csStoreTicketMstDtl") CSStoreTicketMstDtl csStoreTicketMstDtl) {

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		//
		String contractNo = null;
		String ticketNo = csStoreTicketMstDtl.getTicketNo();
		CSStoreTicketMst csStoreTicketMst = csStoreTicketMstService
				.getCSStoreTicketMstByTicketNo(ticketNo);

		List<CSStoreTicketDtl> csStoreTicketDtlList = csStoreTicketDtlService
				.getCSStoreTicketDtlByTicketNo(ticketNo);

		// get All State Codes from Approval Hierarchy and sort Descending oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get Current State Code from Approval hierarchy by RR No (OP_Id)

		String operationId = csStoreTicketMstDtl.getOperationId();
		List<StoreTicketApprovalHierarchyHistory> approvalHierarchyHistoryList = storeTicketApprovalHierarchyHistoryService
				.getStoreTicketApprovalHierarchyHistoryByOperationId(operationId);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = storeTicketApprovalHierarchyHistoryService
				.getStoreTicketApprovalHierarchyHistory(ids[0]);

		int currentStateCode = storeTicketApprovalHierarchyHistory
				.getStateCode();

		int nextStateCode = 0;

		for (int state : stateCodes) {
			if (state > currentStateCode) {
				nextStateCode = state;
				/*
				 * ApprovalHierarchy approvalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * CS_RECEIVING_ITEMS, nextStateCode + "");
				 */
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								csStoreTicketMstDtl.getStoreTicketType(),
								nextStateCode + "");

				storeTicketApprovalHierarchyHistory.setStatus(OPEN);
				storeTicketApprovalHierarchyHistory.setStateCode(nextStateCode);
				storeTicketApprovalHierarchyHistory
						.setStateName(approvalHierarchy.getStateName());
				storeTicketApprovalHierarchyHistory.setId(null);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory
						.setActRoleName(approvalHierarchy.getRoleName());
				storeTicketApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());
				storeTicketApprovalHierarchyHistory.setTicketNo(ticketNo);
				storeTicketApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
				break;
			}

			if (state == currentStateCode) {
				/*
				 * ApprovalHierarchy approvalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * CS_RECEIVING_ITEMS, state + "");
				 */
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								csStoreTicketMst.getStoreTicketType(), state
										+ "");

				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory.setTicketNo(ticketNo);

				storeTicketApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());

				storeTicketApprovalHierarchyHistory.setcEmpId(authUser
						.getEmpId());
				storeTicketApprovalHierarchyHistory.setcEmpFullName(authUser
						.getName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());

				storeTicketApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
			}

			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				storeTicketApprovalHierarchyHistory.setModifiedBy(userName);
				storeTicketApprovalHierarchyHistory.setModifiedDate(new Date());
				storeTicketApprovalHierarchyHistory.setStatus(DONE);

				storeTicketApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
				storeTicketApprovalHierarchyHistory.setcEmpId(authUser
						.getEmpId());
				storeTicketApprovalHierarchyHistory.setcEmpFullName(authUser
						.getName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());

				// new ledger update Ledger Mst
				this.csLedgerLocation(csStoreTicketDtlList, userName, roleName,
						authUser, department, csStoreTicketMst);

				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)) {
					this.lsLedgerUpdate(csStoreTicketDtlList, userName,
							roleName, authUser, department, csStoreTicketMst);
					ReturnSlipMst rsmst = (ReturnSlipMst) commonService
							.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
									"returnSlipNo",
									csStoreTicketMst.getOperationId());
					// this line will delete all row from TempItemLocation by
					// uuid
					commonService.deleteAnObjectListByAnyColumn(
							"TempItemLocation", "uuid", rsmst.getUuid());
				}

				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)) {
					this.ssLedgerUpdate(csStoreTicketDtlList, userName,
							roleName, authUser, department, csStoreTicketMst);

					ReturnSlipMst rsmst = (ReturnSlipMst) commonService
							.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
									"returnSlipNo",
									csStoreTicketMst.getOperationId());
					// this line will delete all row from TempItemLocation by
					// uuid
					commonService.deleteAnObjectListByAnyColumn(
							"TempItemLocation", "uuid", rsmst.getUuid());
				}
				if (csStoreTicketMst.getStoreTicketType()
						.equals(WS_CS_X_RETURN)) {
					this.ssLedgerUpdate(csStoreTicketDtlList, userName,
							roleName, authUser, department, csStoreTicketMst);

					ReturnSlipMst rsmst = (ReturnSlipMst) commonService
							.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
									"returnSlipNo",
									csStoreTicketMst.getOperationId());
					// this line will delete all row from TempItemLocation by
					// uuid
					commonService.deleteAnObjectListByAnyColumn(
							"TempItemLocation", "uuid", rsmst.getUuid());
				}
				if (csStoreTicketMst.getStoreTicketType().equals(WS_CS_RETURN)) {
					this.ssLedgerUpdate(csStoreTicketDtlList, userName,
							roleName, authUser, department, csStoreTicketMst);

					ReturnSlipMst rsmst = (ReturnSlipMst) commonService
							.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
									"returnSlipNo",
									csStoreTicketMst.getOperationId());
					// this line will delete all row from TempItemLocation by
					// uuid
					commonService.deleteAnObjectListByAnyColumn(
							"TempItemLocation", "uuid", rsmst.getUuid());
				}

				// Ledger Update

				csStoreTicketMst.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

				String gpNos = "";
				// open the gate pass for Item issued against Requisition
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_WS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_REQUISITION)) {

					CentralStoreRequisitionMst csReqDb = (CentralStoreRequisitionMst) commonService
							.getAnObjectByAnyUniqueColumn(
									"CentralStoreRequisitionMst",
									"requisitionNo", operationId);
					contractNo = csReqDb.getWorkOrderNumber();
					// m@@ Start
					// Assign GP nos for Ticket MST
					List<String> multipleGPNoList = saveMultipleGP(userName,
							department, csReqDb, csStoreTicketMst);
					// set GP number in CS Ticket MST and Requisition Mst
					gpNos = StringUtils.join(multipleGPNoList, ", ");
					csStoreTicketMst.setGatePass(gpNos);
					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);
					csReqDb.setGatePassNo(gpNos);
					commonService.saveOrUpdateModelObjectToDB(csReqDb);
					// set GP Nos for view page
					model.addAttribute("multipleGPNoList", multipleGPNoList);
					// m@@ End
					// this line will delete all row from TempItemLocation by
					// uuid
					commonService.deleteAnObjectListByAnyColumn(
							"TempItemLocation", "uuid", csReqDb.getUuid());
				}

				// Instant Store Ticket Report Call
				// model.addAttribute("ticketNo", ticketNo);

				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)) {
					// return "centralStore/reports/lsCsStoreTicketReportRS";
					return "redirect:/cs/itemRecieved/lsCsStoreTicketReportRS.do?ticketNo="
							+ ticketNo;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)) {
					// return "centralStore/reports/ssCsStoreTicketReportRS";
					return "redirect:/cs/itemRecieved/ssCsStoreTicketReportRS.do?ticketNo="
							+ ticketNo;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						CN_PD_CS_RETURN)) {
					// return "centralStore/reports/cnPdCsStoreTicketReportRS";
					return "redirect:/cs/itemRecieved/cnPdCsStoreTicketReportRS.do?ticketNo="
							+ ticketNo;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_X_RETURN)) {

					ReturnSlipMst csRetDb = (ReturnSlipMst) commonService
							.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
									"returnSlipNo", operationId);
					updateTransformerRegister(csRetDb, ticketNo, authUser);// nasrin.ibcs-primax
					// return "centralStore/reports/wsCsXStoreTicketReportRS";
					return "redirect:/cs/itemRecieved/wsCsXStoreTicketReportRS.do?ticketNo="
							+ ticketNo;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_RETURN)) {
					// return "centralStore/reports/wsCsStoreTicketReportRS";
					return "redirect:/cs/itemRecieved/wsCsStoreTicketReportRS.do?ticketNo="
							+ ticketNo;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)) {
					commonService.deleteAnObjectListByAnyColumn(
							"RequisitionLock", "requisitionNo",
							csStoreTicketMst.getOperationId());
					// return
					// "centralStore/reports/lsCsStoreTicketReportRequisition";
					return "redirect:/cs/itemRecieved/lsCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				}else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_PD_CS_REQUISITION)) {
					return "redirect:/ls/pd/csStoreTicketReportSR.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
					
				}  else if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_REQUISITION)) {
					commonService.deleteAnObjectListByAnyColumn(
							"RequisitionLock", "requisitionNo",
							csStoreTicketMst.getOperationId());
					// return
					// "centralStore/reports/ssCsStoreTicketReportRequisition";
					return "redirect:/cs/itemRecieved/ssCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						CN_PD_CS_REQUISITION)) {
					commonService.deleteAnObjectListByAnyColumn(
							"RequisitionLock", "requisitionNo",
							csStoreTicketMst.getOperationId());
					// return
					// "centralStore/reports/cnPdCsStoreTicketReportRequisition";
					return "redirect:/cs/itemRecieved/cnPdCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						CN_WS_CS_REQUISITION)) {
					commonService.deleteAnObjectListByAnyColumn(
							"RequisitionLock", "requisitionNo",
							csStoreTicketMst.getOperationId());
					// return
					// "centralStore/reports/cnWsCsStoreTicketReportRequisition";
					return "redirect:/cs/itemRecieved/cnWsCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_X_REQUISITION)) {
					List<CSStoreTicketDtl> dtlList = csStoreTicketDtlService
							.getCSStoreTicketDtlByTicketNo(ticketNo);
					saveTransformerRegister(dtlList, csStoreTicketMst,
							authUser, contractNo);// nasrin.ibcs-primax

					// return
					// "centralStore/reports/wsCsStoreTicketReportRequisition";
					return "redirect:/cs/itemRecieved/wsXCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						WS_CS_REQUISITION)) {

					return "redirect:/cs/itemRecieved/wsCsStoreTicketReportRequisition.do?ticketNo="
							+ ticketNo + "&gpNos=" + gpNos;
				} else {
					return "";
				}
			}

		}

		return "redirect:/cs/itemRecieved/storeTicketlist.do";
	}

	// return
	// "redirect:/cs/itemRecieved/wsCsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/wsXCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsXCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/wsXCsStoreTicketReportRequisition", model);
	}

	@RequestMapping(value = "/wsCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/wsCsStoreTicketReportRequisition", model);
	}

	// return
	// "redirect:/cs/itemRecieved/cnWsCsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/cnWsCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnWsCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/cnWsCsStoreTicketReportRequisition",
				model);
	}

	// return
	// "redirect:/cs/itemRecieved/cnPdCsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/cnPdCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnPdCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/cnPdCsStoreTicketReportRequisition",
				model);
	}

	// return
	// "redirect:/cs/itemRecieved/ssCsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/ssCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/ssCsStoreTicketReportRequisition", model);
	}

	// return
	// "redirect:/cs/itemRecieved/lsCsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/lsCsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/lsCsStoreTicketReportRequisition", model);
	}

	// return
	// "redirect:/cs/itemRecieved/wsCsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/wsCsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsCsStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", ticketNo);
		return new ModelAndView("centralStore/reports/wsCsStoreTicketReportRS",
				model);
	}

	// return
	// "redirect:/cs/itemRecieved/wsCsXStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/wsCsXStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsCsXStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", ticketNo);
		return new ModelAndView(
				"centralStore/reports/wsCsXStoreTicketReportRS", model);
	}

	// return
	// "redirect:/cs/itemRecieved/cnPdCsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/cnPdCsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnPdCsStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", ticketNo);
		return new ModelAndView(
				"centralStore/reports/cnPdCsStoreTicketReportRS", model);
	}

	// return
	// "redirect:/cs/itemRecieved/lsCsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/lsCsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsCsStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", ticketNo);
		return new ModelAndView("centralStore/reports/lsCsStoreTicketReportRS",
				model);
	}

	// return
	// "redirect:/cs/itemRecieved/ssCsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/ssCsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssCsStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", ticketNo);
		return new ModelAndView("centralStore/reports/ssCsStoreTicketReportRS",
				model);
	}

	// nasrin.ibcs-primax
	private void saveTransformerRegister(List<CSStoreTicketDtl> dtlList,
			CSStoreTicketMst csStoreTicketMst, AuthUser authUser,
			String contractNo) {

		for (CSStoreTicketDtl dtl : dtlList) {
			TransformerRegister tRegister = null;
			double d = dtl.getQuantity();
			ItemMaster item = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							dtl.getItemId());

			String transformerType = getXFormerType(item.getItemName());

			for (double p = 1.0; p <= d; p++) {
				tRegister = new TransformerRegister();
				// Model model = null;
				tRegister.setId(null);
				tRegister.setContractNo(contractNo);
				tRegister.setItemCode(dtl.getItemId());
				tRegister.setReqNo(csStoreTicketMst.getOperationId());
				tRegister.setTicketNo(csStoreTicketMst.getTicketNo());
				tRegister.setTypeOfWork(REPAIR_WORKS);
				tRegister.setRcvDeptName(departmentsService.getDepartmentByDeptId(authUser.getDeptId()).getDeptName());
				tRegister.setRcvDeptCode(authUser.getDeptId());
				tRegister.setTransformerType(transformerType);
				tRegister.setCreatedBy(csStoreTicketMst.getCreatedBy());
				tRegister.setCreatedDate(csStoreTicketMst.getCreatedDate());
				// transformerRegisterController.transformerRegisterSave(model,
				// tRegister);
				commonService.saveOrUpdateModelObjectToDB(tRegister);

			}
		}
	}

	// nasrin.ibcs-primax
	private void updateTransformerRegister(ReturnSlipMst returnMst,
			String ticketNo, AuthUser authUser) {
		String[] s = returnMst.getOthers().split(",");
		List<String> transformerSlNos = new ArrayList<String>();

		for (int p = 0; p < s.length; p++) {
			transformerSlNos.add(s[p]);
		}
		TransformerRegister tRegister = null;
		for (String SlNo : transformerSlNos) {

			tRegister = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister",
							"transformerSerialNo", SlNo);
			// tRegister = new TransformerRegister();

			tRegister.setReturnDate(returnMst.getReturnSlipDate());
			tRegister.setReturnSlipNo(returnMst.getReturnSlipNo());
			tRegister.setReturnTicketNo(ticketNo);
			tRegister.setModifiedBy(authUser.getUserid());
			tRegister.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(tRegister);

		}
	}

	// gate pass List
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gatePassList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csGatePassList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<GatePassApprovalHierarchyHistory> gatePassApprovalHierarchyHistoryList = (List<GatePassApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"GatePassApprovalHierarchyHistory", deptId, roleName,
						OPEN);

		List<String> ticketNoList = new ArrayList<String>();

		for (int i = 0; i < gatePassApprovalHierarchyHistoryList.size(); i++) {
			ticketNoList.add(gatePassApprovalHierarchyHistoryList.get(i)
					.getTicketNo());
		}

		List<CSGatePassMst> gatePassMstList = (List<CSGatePassMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("CSGatePassMst", "ticketNo",
						ticketNoList);

		Map<String, Object> model = new HashMap<String, Object>();

		// header
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		// list
		model.put("gatePassMstList", gatePassMstList);
		return new ModelAndView("centralStore/csGataPassList", model);
	}

	// Item Requisition List
	@SuppressWarnings("unchecked")
	public List<CentralStoreRequisitionMst> reqisitionListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		List<CnCsRequisitionApprovalHierarchyHistory> cnCsRequisitionHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		List<CnWsCsRequisitionApprovalHierarchyHistory> cnWsCsRequisitionHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		// added by Taleb
		List<LsPdCsRequisitionApprovalHierarchyHistory> lsPdCsRequisitionHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		// added by Nasrin
		List<WsCsRequisitionApprovalHierarchyHistory> wsCsRequisitionHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("WsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsXRequisitionApprovalHierarchyHistory> wsCsXRequisitionHistoryList = (List<WsCsXRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("WsCsXRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);


		String[] operationIdList1 = new String[lsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList1[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList2 = new String[ssCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList2[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList3 = new String[cnCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < cnCsRequisitionHistoryList.size(); i++) {
			operationIdList3[i] = cnCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		/* cnWsCsRequisitionHistoryList */

		String[] operationIdList4 = new String[cnWsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < cnWsCsRequisitionHistoryList.size(); i++) {
			operationIdList4[i] = cnWsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		// added by Taleb
		String[] operationIdList5 = new String[lsPdCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsPdCsRequisitionHistoryList.size(); i++) {
			operationIdList5[i] = lsPdCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		// added by Nasrin
		String[] operationIdList6 = new String[wsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < wsCsRequisitionHistoryList.size(); i++) {
			operationIdList6[i] = wsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList7 = new String[wsCsXRequisitionHistoryList
		                       				.size()];
		for (int i = 0; i < wsCsXRequisitionHistoryList.size(); i++) {
		   operationIdList7[i] = wsCsXRequisitionHistoryList.get(i)
		            .getOperationId();
		}
	//-------------------------

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = new ArrayList<CentralStoreRequisitionMst>();
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList1 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList1);
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList2 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList2);

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList3 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList3);
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList4 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList4);

		// added by Taleb
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList5 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList5);
		// added by Nasrin
				List<CentralStoreRequisitionMst> centralStoreRequisitionMstList6 = centralStoreRequisitionMstService
						.listCentralStoreRequisitionMstByOperationIds(operationIdList6);
				List<CentralStoreRequisitionMst> centralStoreRequisitionMstList7 = centralStoreRequisitionMstService
						.listCentralStoreRequisitionMstByOperationIds(operationIdList7);


		
		if (centralStoreRequisitionMstList1 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList1);
		}
		if (centralStoreRequisitionMstList2 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList2);
		}
		if (centralStoreRequisitionMstList3 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList3);
		}

		if (centralStoreRequisitionMstList4 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList4);
		}

		// added by Taleb
		if (centralStoreRequisitionMstList5 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList5);
		}

		// added by Nasrin
		if (centralStoreRequisitionMstList6 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList6);
		}
		if (centralStoreRequisitionMstList7 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList7);
		}
		return centralStoreRequisitionMstList;
	}

	// Return Slip List
	@SuppressWarnings("unchecked")
	public List<ReturnSlipMst> retrunSlipListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();
		// @return
		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<CnCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList3 = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		//Added by Nasrin
		List<WsCsXReturnSlipApprovalHierarchyHistory> WsCsXReturnSlipAppHierarchyHistoryList = (List<WsCsXReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("WsCsXReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsReturnSlipApprovalHierarchyHistory> WsCsReturnSlipAppHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("WsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);


		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}

		List<String> operationId3 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList3.size(); i++) {
			operationId3.add(storeSlipApprovalHierarchyHistoryList3.get(i)
					.getOperationId());
		}
		
		//Added by Nasrin
		List<String> operationId4 = new ArrayList<String>();
		for (int i = 0; i < WsCsXReturnSlipAppHierarchyHistoryList.size(); i++) {
			operationId4.add(WsCsXReturnSlipAppHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<String> operationId5 = new ArrayList<String>();
		for (int i = 0; i < WsCsReturnSlipAppHierarchyHistoryList.size(); i++) {
			operationId5.add(WsCsReturnSlipAppHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList3 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList4 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList5 = new ArrayList<ReturnSlipMst>();
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId3) != null) {
			returnSlipMstList3 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId3);
		}
		
		//Added By Nasrin
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId4) != null) {
			returnSlipMstList4 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId4);
		}

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId5) != null) {
			returnSlipMstList5 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId5);
		}

		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
		if (returnSlipMstList2 != null) {
			returnSlipMstList.addAll(returnSlipMstList2);
		}

		if (returnSlipMstList3 != null) {
			returnSlipMstList.addAll(returnSlipMstList3);
		}
		
		//Added by Nasrin
		if (returnSlipMstList4 != null) {
			returnSlipMstList.addAll(returnSlipMstList4);
		}

		if (returnSlipMstList5 != null) {
			returnSlipMstList.addAll(returnSlipMstList5);
		}
		return returnSlipMstList;

	}

	@RequestMapping(value = "/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			// InventoryItem invItem = gson.fromJson(json, InventoryItem.class);
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);

			// ItemMaster selectItemFromDb =
			// itemInventoryService.getItemGroup(invItem.getInventoryItemId());
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/viewInventoryItemCategory.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			// InventoryItem invItem = gson.fromJson(json, InventoryItem.class);
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);

			// ItemMaster selectItemFromDb =
			// itemInventoryService.getItemGroup(invItem.getInventoryItemId());
			List<ItemMaster> selectItemsFromDb = itemInventoryService
					.getItemListByCategoryId(invItem.getCategoryId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/itemEdit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String itemDtlEdit(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {

			CSProcItemRcvDtl item = gson.fromJson(json, CSProcItemRcvDtl.class);

			CSProcItemRcvDtl selectItemsFromDb = csProcItemRcvDtlService
					.getCSProcItemRcvDtl(item.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/updateItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreItemUpdate(
			@ModelAttribute("csProcItemRcvDtl") CSProcItemRcvDtl csProcItemRcvDtl) {

		// get Current User and Role Information
		CSProcItemRcvDtl csProcItemRcvDtldb = csProcItemRcvDtlService
				.getCSProcItemRcvDtl(csProcItemRcvDtl.getId());
		csProcItemRcvDtldb.setReceivedQty(csProcItemRcvDtl.getReceivedQty());
		csProcItemRcvDtldb.setRemainingQty(csProcItemRcvDtl.getRemainingQty());
		csProcItemRcvDtldb.setModifiedBy(commonService.getAuthUserName());
		csProcItemRcvDtldb.setModifiedDate(new Date());

		csProcItemRcvDtlService.editCSProcItemRcvDtl(csProcItemRcvDtldb);

		return "redirect:/cs/itemRecieved/itemRcvShow.do?receivedReportNo="
				+ csProcItemRcvDtldb.getRrNo();
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	public List<StoreLocations> getStoreLocationList(String storeCode) {

		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByAnyColumn("StoreLocations", "storeCode",
						storeCode);

		try {
			return storeLocationList;

		} catch (Exception ex) {
			return null;
		}
	}

	// Added by Ashid
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setRcvedLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String setRcvedLocation(@RequestBody String locationQtyJsonString)
			throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(locationQtyJsonString);
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String toJson = "";
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(
					locationQtyJsonString, TempLocationMstDtl.class);
			String itemCode = temLocationDtl.getItemCode();
			String uuid = temLocationDtl.getUuid();
			Map<String, String> map = temLocationDtl.getLocQtyDtl();
			Set<String> keys = map.keySet();

			for (Iterator i = keys.iterator(); i.hasNext();) {
				String locationId = (String) i.next();
				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								locationId);

				Double qty = Double.parseDouble(map.get(locationId));

				TempItemLocation tempItemLocation = new TempItemLocation();
				tempItemLocation.setId(null);
				tempItemLocation.setItemCode(itemCode);
				tempItemLocation.setUuid(uuid);
				tempItemLocation.setLocationId(locationId);
				tempItemLocation.setLocationName(storeLocations.getName());
				tempItemLocation.setQuantity(qty);
				tempItemLocation.setCreatedBy(commonService.getAuthUserName());
				tempItemLocation.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(tempItemLocation);

			}

			toJson = ow.writeValueAsString("Success");

		} else {
			Thread.sleep(125 * 1000);
			toJson = ow.writeValueAsString("Server Error");
		}

		return toJson;
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTemplocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTemplocation(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(cData,
					TempLocationMstDtl.class);
			String uuid = temLocationDtl.getUuid();
			String itemCode = temLocationDtl.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							itemCode, "uuid", uuid);
			// System.out.println(itemCode + " : " + uuid);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(tempItemLocationList);
		}

		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/updateLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String id = tp.getId().toString();
			Double quantity = tp.getQuantity();

			TempItemLocation tempItemLocationDb = (TempItemLocation) commonService
					.getAnObjectByAnyUniqueColumn("TempItemLocation", "id", id);
			tempItemLocationDb.setQuantity(quantity);
			tempItemLocationDb.setModifiedBy(commonService.getAuthUserName());
			tempItemLocationDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(tempItemLocationDb);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Upadate Failed.");

		}
		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/updateRRdtlAfterLocatinUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateRRdtlAfterLocatinUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CSProcItemRcvDtl tpDtl = gson.fromJson(cData,
					CSProcItemRcvDtl.class);
			String rrNo = tpDtl.getRrNo();
			String itemId = tpDtl.getItemId();
			double receivedQty = tpDtl.getReceivedQty();
			List<Object> obList = commonService.getObjectListByTwoColumn(
					"CSProcItemRcvDtl", "rrNo", rrNo, "itemId", itemId);
			CSProcItemRcvDtl cSProcItemRcvDtlDB = new CSProcItemRcvDtl();
			// System.out.println(cSProcItemRcvDtlDB.getReceivedQty());
			if (obList.size() > 0) {
				cSProcItemRcvDtlDB = (CSProcItemRcvDtl) obList.get(0);
			}
			if (cSProcItemRcvDtlDB.getReceivedQty() != 0.0) {
				cSProcItemRcvDtlDB.setReceivedQty(receivedQty);
				cSProcItemRcvDtlDB.setModifiedBy(commonService
						.getAuthUserName());
				cSProcItemRcvDtlDB.setModifiedDate(new Date());
			}
			commonService.saveOrUpdateModelObjectToDB(cSProcItemRcvDtlDB);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow
					.writeValueAsString("Error from updateRRdtlAfterLocatinUpdate.do");
		}
		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/deleteLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String deleteLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			Integer id = tp.getId();
			commonService.deleteAnObjectById("TempItemLocation", id);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity deleted Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}

	// return total Qty. Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTotalQtyByUuidAndItemCode(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String uuid = tp.getUuid();
			String itemCode = tp.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"itemCode", itemCode);
			Double qty = 0.0;
			for (TempItemLocation temp : tempItemLocationList) {
				qty += temp.getQuantity();
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(qty);
		}
		return toJson;
	}

	// Added by nasrin || updated by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkOrderData.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionSearch(String contractNo) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		Map<String, Object> model = new HashMap<String, Object>();

		WorkOrderMst wOrderMst = workOrderMstService
				.getWorkOrderMstByWorkOrderNo(contractNo);
		List<WorkOrderDtl> OrderDtl = workOrderDtlService
				.getWorkOrderDtlByWorkOrderNo(contractNo);
		/*
		 * String flag = null; if (OrderDtl != null || OrderDtl != null) { for
		 * (WorkOrderDtl w : OrderDtl) { cSProcItemRcvDtl =
		 * csProcItemRcvDtlService .getCSProcItemRcvDtlByContratNo(w.getWwNo(),
		 * w.getItemId()); if (cSProcItemRcvDtl != null) { if
		 * (cSProcItemRcvDtl.getRemainingQty() > 0) {
		 * w.setItemQty(cSProcItemRcvDtl.getRemainingQty()); wOrderDtl.add(w); }
		 * else { w = null; // wOrderMst=null; flag = "y"; } } } if
		 * (wOrderDtl.isEmpty() || wOrderDtl == null) { wOrderMst = null; } }
		 * 
		 * model.put("flag", flag);
		 */

		// previous block commented and new block Added by Ashid
		String flag = null;
		List<WorkOrderDtl> newWorkOrderDtlList = new ArrayList<WorkOrderDtl>();
		if (OrderDtl != null) {
			for (WorkOrderDtl workOrderDtl : OrderDtl) {

				if (workOrderDtl.getRemainingQty() > 0) {
					// workOrderDtl.setItemQty(cSProcItemRcvDtl.getRemainingQty());
					newWorkOrderDtlList.add(workOrderDtl);
				}

			}
			if (newWorkOrderDtlList.isEmpty() || newWorkOrderDtlList == null) {
				wOrderMst = null;
				flag = "y";
			}
		}

		model.put("flag", flag);
		model.put("wOrderMst", wOrderMst);
		model.put("wOrderDtl", newWorkOrderDtlList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		// Get all Location under CS || Added by Ashid
		// List<StoreLocations> storeLocationList = getStoreLocationList("CS");
		// ledgerName
		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationListJson = commonService
				.getLocationListForGrid(storeLocationList);
		model.put("locationList", locationListJson);
		model.put("ledgerName", Constrants.LedgerBook.NEW_SERVICEABLE);
		model.put("uuid", UUID.randomUUID());
		// End of Code || Added by Ashid

		return new ModelAndView("centralStore/csProcItemReceivedFormByWOrder",
				model);
	}

	@RequestMapping(value = "/getWwOrder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<WorkOrderMst> getWwOrder(
			@RequestParam String contractNo, HttpServletResponse response) {
		return simulateSearchResult(contractNo);

	}

	private List<WorkOrderMst> simulateSearchResult(String wwNo) {

		List<WorkOrderMst> workOrderMstList = new ArrayList<WorkOrderMst>();
		List<WorkOrderMst> workOrderMstListData = workOrderMstService
				.getWorkOrderMstList();
		// iterate a list and filter by tagName
		for (WorkOrderMst orderNo : workOrderMstListData) {
			if (orderNo.getWorkOrderNo().toLowerCase()
					.contains(wwNo.toLowerCase())) {
				workOrderMstList.add(orderNo);
			}
		}

		return workOrderMstList;
	}

	@SuppressWarnings("unchecked")
	private void csLedgerLocation(List<CSStoreTicketDtl> csStoreTicketDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, CSStoreTicketMst csStoreTicketMst) {

		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			CSItemTransactionMst csItemTransactionMst = null;
			CSItemTransactionMst csItemTransactionMstdb = csItemTransactionMstService
					.getCSItemTransectionMst(csStoreTicketDtl.getItemId(),
							csStoreTicketMst.getKhathId(),
							csStoreTicketDtl.getLedgerName());
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");

			// Ledger Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionMstdb.setModifiedBy(userName);
				csItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_WS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				} else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_RETURN)) {
					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					}
				}
				commonService
						.saveOrUpdateModelObjectToDB(csItemTransactionMstdb);
			} else {
				csItemTransactionMst = new CSItemTransactionMst();
				csItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_WS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				} else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_RETURN)) {
					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					}
				}
				csItemTransactionMst.setKhathName(descoKhath.getKhathName());
				csItemTransactionMst.setKhathId(descoKhath.getId());
				csItemTransactionMst.setLedgerName(csStoreTicketDtl
						.getLedgerName());
				csItemTransactionMst.setId(null);
				csItemTransactionMst.setCreatedBy(userName);
				csItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("CSItemTransactionMst", "id");
			CSItemTransactionMst csItemTransactionMstLastRow = (CSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			CSItemTransactionDtl csItemTransactionDtl = new CSItemTransactionDtl();
			// common Info
			csItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			csItemTransactionDtl.setKhathId(descoKhath.getId());
			csItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			csItemTransactionDtl.setId(null);
			csItemTransactionDtl.setCreatedBy(userName);
			csItemTransactionDtl.setCreatedDate(new Date());
			csItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			csItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			csItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			csItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionDtl
						.setCsItemTransactionMst(csItemTransactionMstdb);
			} else {
				csItemTransactionDtl
						.setCsItemTransactionMst(csItemTransactionMstLastRow);
			}

			// item related info
			csItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			csItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			csItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			csItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			csItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			csItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			csItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			csItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());

			if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_RETURN)) {
				csItemTransactionDtl.setTnxnMode(true);
			}

			if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_WS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_REQUISITION)) {
				csItemTransactionDtl.setTnxnMode(false);
			}

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

			Integer maxTnxDtlId = (Integer) commonService
					.getMaxValueByObjectAndColumn("CSItemTransactionDtl", "id");
			CSItemTransactionDtl csItemTransactionDtlTemp = (CSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionDtl", "id",
							maxTnxDtlId + "");

			// Location Update Location Mst

			// added by taleb || check requisition or return and get data frm
			// related model
			List<TempItemLocation> tempLocationList = null;

			if (csStoreTicketMst.getStoreTicketType().equals(LS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_WS_CS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_REQUISITION)) {
				CentralStoreRequisitionMst csReqMst = (CentralStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CentralStoreRequisitionMst", "requisitionNo",
								csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								csReqMst.getUuid(), "itemCode",
								csStoreTicketDtl.getItemId());
			} else if (csStoreTicketMst.getStoreTicketType().equals(
					LS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_CS_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_X_RETURN)
					|| csStoreTicketMst.getStoreTicketType().equals(
							WS_CS_RETURN)) {
				ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
								"returnSlipNo",
								csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								returnSlipMst.getUuid(), "itemCode",
								csStoreTicketDtl.getItemId());
			}
			// end of block

			for (TempItemLocation tempLoc : tempLocationList) {

				CSItemLocationMst csItemLocationMstdb = csItemLocationMstService
						.getCSItemLocationMst(tempLoc.getLocationId(),
								csStoreTicketDtl.getLedgerName(),
								csStoreTicketDtl.getItemId());

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								tempLoc.getLocationId());

				// LEDGERNAME, ITEMCODE, LOCATIONID
				if (csItemLocationMstdb != null) {
					csItemLocationMstdb.setModifiedBy(userName);
					csItemLocationMstdb.setModifiedDate(new Date());

					if (csStoreTicketMst.getStoreTicketType().equals(
							LS_CS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									LS_PD_CS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									SS_CS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_CS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_WS_CS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									WS_CS_X_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									WS_CS_REQUISITION)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() - tempLoc.getQuantity());
					} else if (csStoreTicketMst.getStoreTicketType().equals(
							LS_CS_RETURN_SLIP)
							|| csStoreTicketMst.getStoreTicketType().equals(
									SS_CS_RETURN_SLIP)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_CS_RETURN)
							|| csStoreTicketMst.getStoreTicketType().equals(
									WS_CS_X_RETURN)
							|| csStoreTicketMst.getStoreTicketType().equals(
									WS_CS_RETURN)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() + tempLoc.getQuantity());
					}
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					CSItemLocationMst csItemLocationMst = new CSItemLocationMst();
					csItemLocationMst.setItemCode(csStoreTicketDtl.getItemId());
					csItemLocationMst.setLedgerType(csStoreTicketDtl
							.getLedgerName());
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);

					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}

				Integer maxLocMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("CSItemLocationMst", "id");
				CSItemLocationMst csItemLocationMstTemp = (CSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("CSItemLocationMst",
								"id", maxLocMstId + "");

				// Location DTL
				CSItemLocationDtl csItemLocationDtl = new CSItemLocationDtl();
				// general info
				csItemLocationDtl.setItemCode(csStoreTicketDtl.getItemId());
				csItemLocationDtl.setCreatedBy(userName);
				csItemLocationDtl.setCreatedDate(new Date());
				csItemLocationDtl.setId(null);

				// Location Info
				csItemLocationDtl.setLocationName(storeLocations.getName());
				csItemLocationDtl.setStoreLocation(storeLocations);

				csItemLocationDtl.setQuantity(tempLoc.getQuantity());

				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_WS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_REQUISITION)) {
					csItemLocationDtl.setTnxnMode(false);
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_CS_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_RETURN)) {
					csItemLocationDtl.setTnxnMode(true);
				}

				// set Item Location Mst Id
				if (csItemLocationMstdb != null) {
					csItemLocationDtl.setLedgerName(csItemLocationMstdb.getLedgerType());
					csItemLocationDtl.setCsItemLocationMst(csItemLocationMstdb);
				} else {
					csItemLocationDtl.setLedgerName(csItemLocationMstTemp.getLedgerType());
					csItemLocationDtl
							.setCsItemLocationMst(csItemLocationMstTemp);
				}

				// set Transaction dtl info for get khathwise and location wise
				// item report
				csItemLocationDtl
						.setCsItemTransectionDtl(csItemTransactionDtlTemp);

				csItemLocationDtl.setStoreLocation(storeLocations);

				commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);

			}
		}
		// Blok by Taleb
		/*
		 * if (csStoreTicketMst.getStoreTicketType().equals(SS_CS_RETURN_SLIP))
		 * { this.ssToCsLedgerUpdateForReturn(csStoreTicketDtlList, userName,
		 * roleName, authUser, dept, csStoreTicketMst); }
		 */
	}

	@SuppressWarnings("unused")
	private void ssToCsLedgerUpdateForReturn(
			List<CSStoreTicketDtl> csStoreTicketDtlList, String userName,
			String roleName, AuthUser authUser, Departments dept,
			CSStoreTicketMst csStoreTicketMst) {
		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			// SSItemTransactionMst csItemTransactionMst = null;
			SSItemTransactionMst csItemTransactionMstdb = ssItemTransactionMstService
					.getSSItemTransactionMst(csStoreTicketDtl.getItemId(),
							csStoreTicketMst.getKhathId(),
							csStoreTicketDtl.getLedgerName());
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");
			// Ledger Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionMstdb.setCreatedDate(new Date());
				csItemTransactionMstdb.setCreatedBy(userName);// for only
																// now....addede
																// by nasrin
				csItemTransactionMstdb.setModifiedBy(userName);
				csItemTransactionMstdb.setModifiedDate(new Date());

				csItemTransactionMstdb.setQuantity(csItemTransactionMstdb
						.getQuantity() - csStoreTicketDtl.getQuantity());
				commonService
						.saveOrUpdateModelObjectToDB(csItemTransactionMstdb);

			} else {
				continue;
			}
			// Ledger Dtl
			SSItemTransactionDtl csItemTransactionDtl = new SSItemTransactionDtl();
			// common Info
			csItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			csItemTransactionDtl.setKhathId(descoKhath.getId());
			csItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			csItemTransactionDtl.setId(null);
			csItemTransactionDtl.setCreatedBy(userName);
			csItemTransactionDtl.setCreatedDate(new Date());
			csItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			csItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			csItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			csItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst

			csItemTransactionDtl
					.setSsItemTransactionMst(csItemTransactionMstdb);

			// item related info
			csItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			csItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			csItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			csItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			csItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			csItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			csItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			csItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());
			csItemTransactionDtl.setTnxnMode(false);
			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

		}

	}

	@SuppressWarnings("unchecked")
	private void csLedgerLocationRR(List<CSProcItemRcvDtl> itemRcvDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, CSProcItemRcvMst csProcItemRcvMst) {

		for (CSProcItemRcvDtl csProcItemRcvDtl : itemRcvDtlList) {

			CSItemTransactionMst csItemTransactionMst = null;

			// 0 get Transaction Mst Item if Exist
			CSItemTransactionMst csItemTransactionMstdb = csItemTransactionMstService
					.getCSItemTransectionMst(csProcItemRcvDtl.getItemId(),
							csProcItemRcvMst.getKhathId(), NEW_SERVICEABLE);
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csProcItemRcvMst.getKhathId() + "");
			// Ledger Mst
			// 1. update or save Transaction Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionMstdb.setModifiedBy(userName);
				csItemTransactionMstdb.setModifiedDate(new Date());
				csItemTransactionMstdb.setQuantity(csItemTransactionMstdb
						.getQuantity() + csProcItemRcvDtl.getReceivedQty());
				commonService
						.saveOrUpdateModelObjectToDB(csItemTransactionMstdb);
			} else {
				csItemTransactionMst = new CSItemTransactionMst();
				csItemTransactionMst.setItemCode(csProcItemRcvDtl.getItemId());
				csItemTransactionMst.setQuantity(csProcItemRcvDtl
						.getReceivedQty());
				csItemTransactionMst.setKhathName(descoKhath.getKhathName());
				csItemTransactionMst.setKhathId(descoKhath.getId());
				csItemTransactionMst.setLedgerName(NEW_SERVICEABLE);
				csItemTransactionMst.setId(null);
				csItemTransactionMst.setCreatedBy(userName);
				csItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMst);
			}
			// select Transaction Mst Id and get new Transaction Mst which not
			// exist and newly inserted
			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("CSItemTransactionMst", "id");
			CSItemTransactionMst csItemTransactionMstTemp = (CSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id",
							maxTnxMstId + "");
			// save Transaction Ledger Dtl
			CSItemTransactionDtl csItemTransactionDtl = new CSItemTransactionDtl();
			// general Info
			csItemTransactionDtl.setId(null);
			csItemTransactionDtl.setItemCode(csProcItemRcvDtl.getItemId());
			csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			csItemTransactionDtl.setKhathId(descoKhath.getId());

			csItemTransactionDtl.setLedgerType(NEW_SERVICEABLE);
			csItemTransactionDtl.setCreatedBy(userName);
			csItemTransactionDtl.setCreatedDate(new Date());
			csItemTransactionDtl.setRemarks(csProcItemRcvDtl.getRemarks());

			// rcv info
			csItemTransactionDtl.setTnxnMode(true);
			csItemTransactionDtl.setTransactionType(CS_RECEIVING_ITEMS);
			csItemTransactionDtl.setTransactionId(csProcItemRcvMst.getRrNo());
			csItemTransactionDtl.setTransactionDate(csProcItemRcvMst
					.getReceivedDate());
			csItemTransactionDtl.setQuantity(csProcItemRcvDtl.getReceivedQty());

			csItemTransactionDtl
					.setReceivedBy(csProcItemRcvMst.getReceivedBy());
			csItemTransactionDtl.setReceivedFrom(csProcItemRcvMst
					.getSupplierName());
			// relation with transaction master
			if (csItemTransactionMstdb != null) {
				csItemTransactionDtl
						.setCsItemTransactionMst(csItemTransactionMstdb);
			} else {
				csItemTransactionDtl
						.setCsItemTransactionMst(csItemTransactionMstTemp);
			}

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

			// 5. select Transaction Dtl Id and keep It temp
			Integer maxTnxDtlId = (Integer) commonService
					.getMaxValueByObjectAndColumn("CSItemTransactionDtl", "id");
			CSItemTransactionDtl csItemTransactionDtlTemp = (CSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionDtl", "id",
							maxTnxDtlId + "");

			// Location Update
			// Location Mst
			// 6. update or save location mst

			List<TempItemLocation> tempLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid",
							csProcItemRcvMst.getUuid(), "itemCode",
							csProcItemRcvDtl.getItemId());

			for (TempItemLocation tempLoc : tempLocationList) {

				CSItemLocationMst csItemLocationMstdb = csItemLocationMstService
						.getCSItemLocationMst(tempLoc.getLocationId(),
								NEW_SERVICEABLE, csProcItemRcvDtl.getItemId());

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								tempLoc.getLocationId());

				if (csItemLocationMstdb != null) {
					csItemLocationMstdb.setModifiedBy(userName);
					csItemLocationMstdb.setModifiedDate(new Date());
					csItemLocationMstdb.setQuantity(csItemLocationMstdb
							.getQuantity() + tempLoc.getQuantity());
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					CSItemLocationMst csItemLocationMst = new CSItemLocationMst();
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);
					csItemLocationMst.setItemCode(csProcItemRcvDtl.getItemId());
					csItemLocationMst.setLedgerType(NEW_SERVICEABLE);
					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}
				// in locationMst New Insert than get Last Location Mst Id
				Integer maxLocMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("CSItemLocationMst", "id");
				CSItemLocationMst csItemLocationMstTemp = (CSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("CSItemLocationMst",
								"id", maxLocMstId + "");

				// Location DTL
				CSItemLocationDtl csItemLocationDtl = new CSItemLocationDtl();

				// general info
				csItemLocationDtl.setCreatedBy(userName);
				csItemLocationDtl.setLedgerName(NEW_SERVICEABLE);
				csItemLocationDtl.setCreatedDate(new Date());
				csItemLocationDtl.setId(null);
				csItemLocationDtl.setItemCode(csProcItemRcvDtl.getItemId());
				csItemLocationDtl.setLocationName(storeLocations.getName());
				csItemLocationDtl.setQuantity(tempLoc.getQuantity());

				// if true item should be added and if false item should be
				// minus
				csItemLocationDtl.setTnxnMode(true);

				// itemTransactionDtl Added
				csItemLocationDtl
						.setCsItemTransectionDtl(csItemTransactionDtlTemp);

				// Store Location Added
				csItemLocationDtl.setStoreLocation(storeLocations);

				if (csItemLocationMstdb != null) {
					csItemLocationDtl.setLedgerName(csItemLocationMstdb.getLedgerType());
					csItemLocationDtl.setCsItemLocationMst(csItemLocationMstdb);
				} else {
					csItemLocationDtl.setLedgerName(csItemLocationMstTemp.getLedgerType());
					csItemLocationDtl
							.setCsItemLocationMst(csItemLocationMstTemp);
				}
				commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);
			}
		}
		// this line will delete all row from TempItemLocation by uuid
		commonService.deleteAnObjectListByAnyColumn("TempItemLocation", "uuid",
				csProcItemRcvMst.getUuid());
	}

	private void lsLedgerUpdate(List<CSStoreTicketDtl> csStoreTicketDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, CSStoreTicketMst csStoreTicketMst) {

		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			LSItemTransactionMst lsItemTransactionMst = null;
			LSItemTransactionMst lsItemTransactionMstdb = null;
			@SuppressWarnings("unchecked")
			List<LSItemTransactionMst> lsItemTransactionMstdbList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"khathId", csStoreTicketMst.getKhathId() + "",
							"ledgerName", csStoreTicketDtl.getLedgerName(),
							"itemCode", csStoreTicketDtl.getItemId(),
							"sndCode", csStoreTicketMst.getSndCode());
			if (lsItemTransactionMstdbList.size() > 0) {
				lsItemTransactionMstdb = lsItemTransactionMstdbList.get(0);
			}
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");

			// Ledger Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionMstdb.setModifiedBy(userName);
				lsItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				}
				commonService
						.saveOrUpdateModelObjectToDB(lsItemTransactionMstdb);
			} else {
				lsItemTransactionMst = new LSItemTransactionMst();
				lsItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_RETURN_SLIP)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}
					if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				}
				lsItemTransactionMst.setKhathName(descoKhath.getKhathName());
				lsItemTransactionMst.setKhathId(descoKhath.getId());
				lsItemTransactionMst.setLedgerName(csStoreTicketDtl
						.getLedgerName());
				lsItemTransactionMst.setId(null);
				lsItemTransactionMst.setSndCode(csStoreTicketMst.getSndCode());
				lsItemTransactionMst.setCreatedBy(userName);
				lsItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(lsItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("LSItemTransactionMst", "id");
			LSItemTransactionMst csItemTransactionMstLastRow = (LSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("LSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			LSItemTransactionDtl lsItemTransactionDtl = new LSItemTransactionDtl();
			// common Info
			lsItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			lsItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			lsItemTransactionDtl.setKhathId(descoKhath.getId());
			lsItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			lsItemTransactionDtl.setId(null);
			lsItemTransactionDtl.setCreatedBy(userName);
			lsItemTransactionDtl.setCreatedDate(new Date());
			lsItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			lsItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			lsItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			lsItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionDtl
						.setLsItemTransactionMst(lsItemTransactionMstdb);
			} else {
				lsItemTransactionDtl
						.setLsItemTransactionMst(csItemTransactionMstLastRow);
			}

			// item related info
			lsItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			lsItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			lsItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			lsItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			lsItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			lsItemTransactionDtl.setTnxnMode(false);
			lsItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			lsItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			lsItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());

			commonService.saveOrUpdateModelObjectToDB(lsItemTransactionDtl);
		}
	}

	//
	private void ssLedgerUpdate(List<CSStoreTicketDtl> csStoreTicketDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, CSStoreTicketMst csStoreTicketMst) {

		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			SSItemTransactionMst ssItemTransactionMst = null;
			SSItemTransactionMst ssItemTransactionMstdb = null;
			@SuppressWarnings("unchecked")
			List<SSItemTransactionMst> ssItemTransactionMstdbList = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("SSItemTransactionMst",
							"khathId", csStoreTicketMst.getKhathId() + "",
							"ledgerName", csStoreTicketDtl.getLedgerName(),
							"itemCode", csStoreTicketDtl.getItemId());
			if (ssItemTransactionMstdbList.size() > 0) {
				ssItemTransactionMstdb = ssItemTransactionMstdbList.get(0);
			}
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");

			// Ledger Mst
			if (ssItemTransactionMstdb != null) {
				ssItemTransactionMstdb.setModifiedBy(userName);
				ssItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_RETURN)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						ssItemTransactionMstdb
								.setQuantity(ssItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						ssItemTransactionMstdb
								.setQuantity(ssItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						ssItemTransactionMstdb
								.setQuantity(ssItemTransactionMstdb
										.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				}
				commonService
						.saveOrUpdateModelObjectToDB(ssItemTransactionMstdb);
			} else {
				ssItemTransactionMst = new SSItemTransactionMst();
				ssItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_X_RETURN)
						|| csStoreTicketMst.getStoreTicketType().equals(
								WS_CS_RETURN)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						ssItemTransactionMst
								.setQuantity(ssItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}
					if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						ssItemTransactionMst
								.setQuantity(ssItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}
					if (csStoreTicketDtl.getLedgerName().equals(UNSERVICEABLE)) {
						ssItemTransactionMst
								.setQuantity(ssItemTransactionMst.getQuantity()
										- csStoreTicketDtl.getQuantity());
					}

				}
				ssItemTransactionMst.setKhathName(descoKhath.getKhathName());
				ssItemTransactionMst.setKhathId(descoKhath.getId());
				ssItemTransactionMst.setLedgerName(csStoreTicketDtl
						.getLedgerName());
				ssItemTransactionMst.setId(null);
				ssItemTransactionMst.setCreatedBy(userName);
				ssItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(ssItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("SSItemTransactionMst", "id");
			SSItemTransactionMst ssItemTransactionMstLastRow = (SSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			SSItemTransactionDtl ssItemTransactionDtl = new SSItemTransactionDtl();
			// common Info
			ssItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			ssItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			ssItemTransactionDtl.setKhathId(descoKhath.getId());
			ssItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			ssItemTransactionDtl.setId(null);
			ssItemTransactionDtl.setCreatedBy(userName);
			ssItemTransactionDtl.setCreatedDate(new Date());
			ssItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			ssItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			ssItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			ssItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst
			if (ssItemTransactionMstdb != null) {
				ssItemTransactionDtl.setLedgerType(ssItemTransactionMstdb.getLedgerName());
				ssItemTransactionDtl
						.setSsItemTransactionMst(ssItemTransactionMstdb);
			} else {
				ssItemTransactionDtl.setLedgerType(ssItemTransactionMstLastRow.getLedgerName());
				ssItemTransactionDtl
						.setSsItemTransactionMst(ssItemTransactionMstLastRow);
			}

			// item related info
			ssItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			ssItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			ssItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			ssItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			ssItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			ssItemTransactionDtl.setTnxnMode(false);
			ssItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			ssItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			ssItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());

			commonService.saveOrUpdateModelObjectToDB(ssItemTransactionDtl);
		}
	}

	//
	@SuppressWarnings("unchecked")
	private List<String> saveMultipleGP(String userName,
			Departments department, CentralStoreRequisitionMst csReqDb,
			CSStoreTicketMst csStoreTicketMst) {
		List<String> gPNoList = new ArrayList<String>();
		String uuid = csReqDb.getUuid();
		List<TempItemLocation> tpList = (List<TempItemLocation>) (Object) commonService
				.getObjectListByAnyColumn("TempItemLocation", "uuid", uuid);

		Set<String> locationIdList = new HashSet<String>();
		for (TempItemLocation t : tpList) {
			locationIdList.add(t.getLocationId());
		}
		// GP MST info
		String descoDeptCode = department.getDescoCode();
		CSGatePassMst csGatePassMst = new CSGatePassMst();
		csGatePassMst.setGatePassDate(new Date());
		csGatePassMst.setIssuedTo(csReqDb.getIdenterDesignation());
		csGatePassMst.setIssuedBy(commonService.getAuthUserName());
		csGatePassMst.setReceiverName(csReqDb.getReceivedBy());
		csGatePassMst.setFlag(true);
		csGatePassMst.setActive(true);
		csGatePassMst.setCreatedBy(userName);
		csGatePassMst.setCreatedDate(new Date());
		csGatePassMst.setRequisitonNo(csReqDb.getRequisitionNo());
		csGatePassMst.setTicketNo(csStoreTicketMst.getTicketNo());
		csGatePassMst.setWorkOrderNo(csStoreTicketMst.getWorkOrderNo());
		// now create unique location wise gate pass
		for (String locId : locationIdList) {
			List<TempItemLocation> gpItemList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"locationId", locId);

			String gatePassNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoGatePassNoPrefix, descoDeptCode, separator,
							"GATE_PASS_SEQ");
			// Save CS GP Mst
			csGatePassMst.setGatePassNo(gatePassNo);
			csGatePassMst.setId(null);
			csGatePassMst.setLocationId(locId);
			csGatePassMst.setLocationName(gpItemList.get(0).getLocationName());
			commonService.saveOrUpdateModelObjectToDB(csGatePassMst);

			// create GP DTL and Savt to DB
			for (TempItemLocation gpDtl : gpItemList) {

				CSGatePassMst gpMstDB = (CSGatePassMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CSGatePassMst",
								"id",
								commonService.getMaxValueByObjectAndColumn(
										"CSGatePassMst", "id").toString());
				ItemMaster item = (ItemMaster) commonService
						.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
								gpDtl.getItemCode());
				CSGatePassDtl csGPdtl = new CSGatePassDtl();
				csGPdtl.setCsGatePassMst(gpMstDB);
				csGPdtl.setGatePassNo(gpMstDB.getGatePassNo());
				csGPdtl.setItemCode(item.getItemId());
				csGPdtl.setDescription(item.getItemName());
				csGPdtl.setUom(item.getUnitCode());
				csGPdtl.setId(null);
				csGPdtl.setQuantity(gpDtl.getQuantity());
				csGPdtl.setCreatedBy(userName);
				csGPdtl.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(csGPdtl);
			}
			gPNoList.add(gatePassNo);
		}
		return gPNoList;
	}

}