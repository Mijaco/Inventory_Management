package com.ibcs.desco.workshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.SsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSItemTransactionMstService;
import com.ibcs.desco.cs.service.CSProcItemRcvDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.controller.ItemRequisitionController;
import com.ibcs.desco.localStore.service.LSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;
import com.ibcs.desco.workshop.model.JobCardDtl;
import com.ibcs.desco.workshop.model.JobCardTemplate;
import com.ibcs.desco.workshop.model.WsCnAllocation;

@Controller
@RequestMapping(value = "/ws")
@PropertySource("classpath:common.properties")
public class WorkShopController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	CSItemTransactionMstService csItemTransactionMstService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	SubStoreRequisitionDtlService subStoreRequisitionDtlService;

	@Autowired
	SSReturnSlipMstService ssReturnSlipMstService;

	@Autowired
	CSProcItemRcvDtlService csProcItemRcvDtlService;

	@Autowired
	CentralStoreRequisitionDtlService centralStoreRequisitionDtlService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	SsCsRequisitionApprovalHierarchyHistoryService ssCsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	ItemRequisitionController itemRequisitionController;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;

	@Autowired
	LSItemTransactionMstService lsItemTransactionMstService;

	@Autowired
	CommonService commonService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.gate.pass.prefix}")
	private String descoGatePassNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/storeRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String wsStoreRequisitionSave(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			RedirectAttributes redirectAttributes) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						csRequisitionMstDtl.getKhathId() + "");

		CentralStoreRequisitionMst csRequisitionMst = new CentralStoreRequisitionMst();
		csRequisitionMst.setIdenterDesignation(csRequisitionMstDtl
				.getIdenterDesignation());
		csRequisitionMst.setWorkOrderNumber(csRequisitionMstDtl
				.getWorkOrderNumber());
		csRequisitionMst.setDeptName(department.getDeptName());
		csRequisitionMst.setKhathId(descoKhath.getId());
		csRequisitionMst.setKhathName(descoKhath.getKhathName());

		//
		/*
		 * String requisitionNo = commonService.getCustomSequence(
		 * csItemRequisitionNoPrefix, "-");
		 * commonService.saveOrUpdateCustomSequenceToDB(requisitionNo);
		 */

		// set current date time as RequisitionDate. GUI date is not used here.

		csRequisitionMst.setSndCode(department.getDescoCode());
		csRequisitionMst.setUuid(UUID.randomUUID().toString());
		csRequisitionMst.setRequisitionDate(now);
		csRequisitionMst.setActive(csRequisitionMstDtl.isActive());
		csRequisitionMst.setCreatedBy(userName);
		csRequisitionMst.setCreatedDate(now);
		csRequisitionMst.setRequisitionTo(ContentType.CENTRAL_STORE.toString());
		csRequisitionMst.setSenderStore(ContentType.WORKSHOP.toString());
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addStoreRequisitionDtls(csRequisitionMstDtl,
				csRequisitionMst, roleName, department, authUser);
		redirectAttributes.addFlashAttribute("centralStoreRequisitionMst",
				csRequisitionMst);
		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
			/*
			 * return "redirect:/ws/storeRequisitionShowToCs.do?id=" +
			 * csRequisitionMst.getId() + "&requisitionTo=" +
			 * csRequisitionMst.getRequisitionTo();
			 */
			return "redirect:/ws/storeRequisitionShow.do";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/ws/requisitionList.do";
		}

	}

	public boolean addStoreRequisitionDtls(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			CentralStoreRequisitionMst csRequisitionMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		List<String> remarksList = null;
		// List<String> ledgerNameList = null;

		if (csRequisitionMstDtl.getItemCode() != null) {
			itemCodeList = csRequisitionMstDtl.getItemCode();
		}

		if (csRequisitionMstDtl.getItemName() != null) {
			itemNameList = csRequisitionMstDtl.getItemName();
		}

		if (csRequisitionMstDtl.getUom() != null) {
			uomList = csRequisitionMstDtl.getUom();
		}

		if (csRequisitionMstDtl.getQuantityRequired() != null) {
			quantityRequiredList = csRequisitionMstDtl.getQuantityRequired();
		}

		/*
		 * if (csRequisitionMstDtl.getUnitCost() != null) { unitCostList =
		 * csRequisitionMstDtl.getUnitCost(); }
		 * 
		 * if (csRequisitionMstDtl.getTotalCost() != null) { totalCostList =
		 * csRequisitionMstDtl.getTotalCost(); }
		 * 
		 * if (csRequisitionMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csRequisitionMstDtl.getLedgerName(); }
		 */

		if (csRequisitionMstDtl.getRemarks() != null) {
			remarksList = csRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(WS_CS_REQUISITION);

		/*
		 * Get all State code which for local to Central Store requisition
		 * process
		 */

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get hierarchy information for minimum state code of LS item
		// requisition hierarchy
		ApprovalHierarchy approvalHierarchy = null;
		String[] roles = null;

		if (stateCodes.length > 0) {
			approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_CS_REQUISITION, stateCodes[0].toString());
			roles = approvalHierarchy.getRoleName().split(",");
		}

		// login person have permission for requisition process??
		int flag = 0; // 0 = no permission
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1; // 1 = has permission
				break;
			}
		}

		// do next transaction if the role has permission for requisition??

		if (flag == 1) {

			// Requisition Items Details insert process Start from Here
			if (itemCodeList != null && itemCodeList.size() > 0) {

				// Saving master information of requisition to Master Table
				// Get and set requisition no from db
				String descoDeptCode = department.getDescoCode();
				String requisitionNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoRequisitionNoPrefix, descoDeptCode,
								separator, "CN_CS_REQ_SEQ");
				csRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

				CentralStoreRequisitionMst csRequisitionMstDb = (CentralStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CentralStoreRequisitionMst", "requisitionNo",
								csRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					CentralStoreRequisitionDtl csRequisitionDtl = new CentralStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						csRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						csRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						csRequisitionDtl.setUom(uomList.get(i));
					} else {
						csRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						csRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						csRequisitionDtl.setQuantityRequired(0);
					}

					/*
					 * if (!unitCostList.isEmpty()) {
					 * csRequisitionDtl.setUnitCost(unitCostList.get(i)); } else
					 * { csRequisitionDtl.setUnitCost(unitCostList.get(i)); }
					 */

					/*
					 * if (!totalCostList.isEmpty()) {
					 * csRequisitionDtl.setTotalCost(totalCostList.get(i)); }
					 * else { csRequisitionDtl.setTotalCost(0); }
					 */
					if (!remarksList.isEmpty()) {
						csRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						csRequisitionDtl.setRemarks("");
					}
					// set Ledger Name
					/*
					 * if (!ledgerNameList.isEmpty()) {
					 * csRequisitionDtl.setLedgerName(ledgerNameList.get(i)); }
					 * else { csRequisitionDtl.setLedgerName(""); }
					 */
					// set id null for auto number
					csRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					csRequisitionDtl.setRequisitionNo(csRequisitionMst
							.getRequisitionNo());
					csRequisitionDtl.setCreatedBy(csRequisitionMst
							.getCreatedBy());
					csRequisitionDtl.setCreatedDate(csRequisitionMst
							.getCreatedDate());
					csRequisitionDtl.setActive(true);
					csRequisitionDtl
							.setCentralStoreRequisitionMst(csRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);
				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(csRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			CentralStoreRequisitionMst csRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new WsCsRequisitionApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(csRequisitionMst
				.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(WS_CS_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(csRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(csRequisitionMst
				.getCreatedDate());
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			approvalHierarchyHistory.setStateName(approvalHierarchy
					.getStateName());
		}

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<WsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[ssCsRequisitionHistoryList.size()];
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList",
				centralStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WS")) {
			return new ModelAndView("workshop/wsRequisitionList", model);
		}
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/wsToCsRequisitionList", model);
		} else {
			return new ModelAndView("workshop/csRequisitionList", model);
			// return new ModelAndView("redirect:/ls/requisitionList.do");
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		String userName = commonService.getAuthUserName();
		List<ItemCategory> categoryList = itemGroupService
				.getConstructionItemGroups();

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);
		ContractorRepresentive cr = contractorRepresentiveService
				.getContractorRep(userName);

		List<JobCardTemplate> jobCardTemplateList = (List<JobCardTemplate>) (Object) commonService
				.getObjectListByAnyColumn("JobCardTemplate", "typeOfWork",
						REPAIR_WORKS);

		model.put("descoKhath", descoKhath);
		model.put("categoryList", categoryList);
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		model.put("contractor", cr);
		model.put("jobCardTemplateList", jobCardTemplateList);
		return new ModelAndView("workshop/wsToCsStoreRequisitionForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getItemData.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getItemData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemCode = request.getParameter("itemCode");
		String workOrderNumber = request.getParameter("workOrderNumber");

		/*
		 * JobCardMst jobCardMst = (JobCardMst) commonService
		 * .getAnObjectByAnyUniqueColumn("JobCardMst", "contractNo",
		 * workOrderNumber);
		 */
		List<JobCardDtl> jobCardTemplateList = (List<JobCardDtl>) (Object) commonService
				.getObjectListByTwoColumn("JobCardDtl",
						"jobCardMst.contractNo", workOrderNumber + "",
						"itemCode", itemCode);

		double qty = 0.0;
		for (JobCardDtl job : jobCardTemplateList) {
			qty += job.getRemainningQuantity();

		}

		CSItemTransactionMst centralStoreItems = csItemTransactionMstService
				.getCSItemTransectionMstForss(jobCardTemplateList.get(0)
						.getItemCode(), 1);
		double csQty = 0.0;
		if (centralStoreItems != null) {
			csQty = centralStoreItems.getQuantity();
		}
		if (jobCardTemplateList.size() > 0) {

			return jobCardTemplateList.get(0).getItemCode() + ":"
					+ jobCardTemplateList.get(0).getItemName() + ":"
					+ jobCardTemplateList.get(0).getUnit() + ":" + qty + ":"
					+ csQty;
		} else {
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lookupItemForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getInventoryLookupItemForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");
		model.put("itemCategoryList", itemCategoryList);
		return new ModelAndView("workshop/jobLookupItemForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobLookupItemSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getInventoryLookupItemSave(
			JobCardTemplate jobCardTemplate) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		// AuthUser authUser = userService.getAuthUserByUserId(userName);

		// Departments department = departmentsService
		// .getDepartmentByDeptId(authUser.getDeptId());
		jobCardTemplate.setId(null);

		jobCardTemplate.setCreatedBy(userName);
		jobCardTemplate.setCreatedDate(now);
		commonService.saveOrUpdateModelObjectToDB(jobCardTemplate);

		List<JobCardTemplate> inventoryLookupItemList = (List<JobCardTemplate>) (Object) commonService
				.getAllObjectList("JobCardTemplate");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inventoryLookupItemList", inventoryLookupItemList);

		return new ModelAndView("workshop/jobLookupItemList", model);
		// return "redirect:/ws/lookupItemForm.do";

	}

	@SuppressWarnings("unchecked")
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/jobCardLookupItemList.do", method = RequestMethod.GET)
	public ModelAndView JobLookupItemList() {

		List<JobCardTemplate> inventoryLookupItemList = (List<JobCardTemplate>) (Object) commonService
				.getAllObjectList("JobCardTemplate");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inventoryLookupItemList", inventoryLookupItemList);
		return new ModelAndView("workshop/jobLookupItemList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "deleteJobCardLookupItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public ModelAndView deleteJobCardLookupItem(JobCardTemplate jobCardTemplate) {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer id = jobCardTemplate.getId();

		try {
			commonService.deleteAnObjectById("JobCardTemplate", id);
			model.put("successflag", 1);
		} catch (Exception E) {
			E.printStackTrace();
			model.put("unsuccessflag", 2);
		}

		List<JobCardTemplate> inventoryLookupItemList = (List<JobCardTemplate>) (Object) commonService
				.getAllObjectList("JobCardTemplate");
		model.put("inventoryLookupItemList", inventoryLookupItemList);
		return new ModelAndView("workshop/jobLookupItemList", model);
	}

	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShowToCsPost(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {

		return getStoreRequisitionShowToCs(centralStoreRequisitionMst);
	}

	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShowToCsGet(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {
		return getStoreRequisitionShowToCs(centralStoreRequisitionMst);

	}

	@SuppressWarnings("unchecked")
	public ModelAndView getStoreRequisitionShowToCs(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(centralStoreRequisitionMst
						.getId());
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();
		Integer khathId = centralStoreRequisitionMstdb.getKhathId();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		// This Block will set Current stock of CS
		for (int i = 0; i < centralStoreRequisitionDtlList.size(); i++) {
			String itemCode = centralStoreRequisitionDtlList.get(i)
					.getItemCode();
			List<CSItemTransactionMst> csItemTnxMstRecovery = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName",
							RECOVERY_SERVICEABLE);

			List<CSItemTransactionMst> csItemTnxMstNew = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName", NEW_SERVICEABLE);

			if (csItemTnxMstRecovery.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(
								csItemTnxMstRecovery.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(0.0);
			}

			if (csItemTnxMstNew.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(
								csItemTnxMstNew.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(0.0);
			}
		}

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<WsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsCsRequisitionApprovalHierarchyHistory",
						WS_CS_REQUISITION, operationId, DONE);

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 */

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<WsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsCsRequisitionApprovalHierarchyHistory",
						WS_CS_REQUISITION, operationId, OPEN);

		int currentStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
				.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Roles", "role_id",
						roleIdList);
		// App_hier List by RoleList & Op name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(
						WS_CS_REQUISITION, roleNameList);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

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
		if (!sCsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sCsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sCsRequisitionApprovalHierarchyHistoryOpenList.get(
					sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								WS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		Map<String, Object> model = new HashMap<String, Object>();

		{ // Start m@@ || Generate drop down list for location in Grid
			List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationOptions = commonService
					.getLocationListForGrid(locationsList);
			String ledgerBookList = commonService.getLedgerListForGrid();
			model.put("locationList", locationOptions);
			model.put("ledgerBookList", ledgerBookList);

			// End m@@ || Generate drop down list for location in Grid }
		}
		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WS")) {
			return new ModelAndView("workshop/wsRequisitionShow", model);
		}
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/wsToCsRequisitionShow", model);
		} else {
			return new ModelAndView("workshop/csRequisitionShow", model);
		}

	}

	public List<StoreLocations> getStoreLocationList(String storeCode) {

		@SuppressWarnings("unchecked")
		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByAnyColumn("StoreLocations", "storeCode",
						storeCode);

		try {
			return storeLocationList;

		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setRcvedLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String setRcvedLocation(@RequestBody String locationQtyJsonString)
			throws InterruptedException, JsonGenerationException,
			JsonMappingException, IOException {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(locationQtyJsonString);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
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

			toJson = ow.writeValueAsString("success");

		} else {
			Thread.sleep(125 * 1000);
			toJson = ow.writeValueAsString("failure");
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
			System.out.println(cSProcItemRcvDtlDB.getReceivedQty());
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

	// Receiving Report (RR) Approving process

	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String sotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		return receivedItemSubmitApproved(model, csRequisitionMstDtl);

	}

	@RequestMapping(value = "/csItemRequisitionSubmitApproved.do", method = RequestMethod.POST)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		return receivedItemSubmitApproved(model, csRequisitionMstDtl);
	}

	@SuppressWarnings("unchecked")
	public String receivedItemSubmitApproved(Model model,
			CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		boolean storeChangeFlag = false;

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!csRequisitionMstDtl.getReturn_state().equals("")
				|| csRequisitionMstDtl.getReturn_state().length() > 0) {

			List<WsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsCsRequisitionApprovalHierarchyHistory",
							"operationId",
							csRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (WsCsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsCsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_CS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(csRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_CS_REQUISITION,
							csRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(csRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(csRequisitionMstDtl.getRequisitionNo());
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setcDeptName(department
					.getDeptName());
			approvalHierarchyHistoryNextState.setcDesignation(authUser
					.getDesignation());

			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {

			// get Received Item List against RR No
			String operationId = csRequisitionMstDtl.getRequisitionNo();

			CentralStoreRequisitionMst csRequisitionMst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
							"requisitionNo", operationId);

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_CS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<WsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsCsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (WsCsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsCsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;
			// update issued qty
			List<Double> requiredIssuedList = csRequisitionMstDtl
					.getQuantityIssued();

			if (requiredIssuedList != null) {
				int p = 0;
				for (CentralStoreRequisitionDtl csReqDtl : centralStoreRequisitionDtlList) {
					Double requiredIssued = requiredIssuedList.get(p);
					csReqDtl.setQuantityIssued(requiredIssued);
					csReqDtl.setModifiedBy(userName);
					csReqDtl.setModifiedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(csReqDtl);

					p++;
				}
			}

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									WS_CS_REQUISITION, nextStateCode + "");

					// next role name
					// next role id
					// next role users dept

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
							.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {

					} else {
						Roles role = (Roles) commonService
								.getAnObjectByAnyUniqueColumn(
										"com.ibcs.desco.admin.model.Roles",
										"role", approvalHierarchy.getRoleName());
						List<AuthUser> userList = (List<AuthUser>) (Object) commonService
								.getObjectListByAnyColumn(
										"com.ibcs.desco.admin.model.AuthUser",
										"roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart
								.getDeptName());
						if (!roleName.equals(ROLE_WO_CN_USER)) {
							storeChangeFlag = true;
						}

					}
					// AuthUser

					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy
							.getStateName());

					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy
							.getRoleName());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process
				// will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									WS_CS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(csRequisitionMstDtl
									.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now we have to insert data in store ticket mst and
					// history
					CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
					// csStoreTicketMst.setTicketNo(ticketNo);
					csStoreTicketMst.setStoreTicketType(WS_CS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(csRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(csRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);

					csStoreTicketMst.setKhathId(csRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(csRequisitionMst
							.getKhathName());

					String descoDeptCode = department.getDescoCode();

					// csStoreTicketMst.setIssuedBy(csRequisitionMst.getRequisitionTo());
					csStoreTicketMst.setIssuedBy(authUser.getName() + "("
							+ userName + ")");
					csStoreTicketMst.setWorkOrderNo(csRequisitionMst
							.getWorkOrderNumber());
					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "CS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);
					csStoreTicketMst.setReceivedBy(csRequisitionMst
							.getReceivedBy());

					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

					// csRequisitionMst Update
					csRequisitionMst.setApproved(true);
					csRequisitionMst.setStoreTicketNO(storeTicketNo);
					commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);

					//

					// Get All Approval Hierarchy on CS_STORE_TICKET
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

					Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
							.size()];
					for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
						sStoreTicketStateCodes[i] = approvalHierarchyListDb
								.get(i).getStateCode();
					}
					Arrays.sort(sStoreTicketStateCodes);

					// get approve hierarchy for last state
					ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_STORE_TICKET,
									sStoreTicketStateCodes[0].toString());

					StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

					storeTicketApprovalHierarchyHistory
							.setActRoleName(storeTicketpprovalHierarchy
									.getRoleName());
					storeTicketApprovalHierarchyHistory
							.setOperationId(operationId);
					storeTicketApprovalHierarchyHistory
							.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory
							.setOperationName(CS_STORE_TICKET);
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory
							.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						storeTicketApprovalHierarchyHistory
								.setStateCode(sStoreTicketStateCodes[0]);
						storeTicketApprovalHierarchyHistory
								.setStateName(storeTicketpprovalHierarchy
										.getStateName());
					}
					storeTicketApprovalHierarchyHistory.setStatus(OPEN);
					storeTicketApprovalHierarchyHistory.setActive(true);
					// process will done and go for store ticket
					commonService
							.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

					// model.addAttribute("operationId", operationId);
					// return "workshop/csRequisitionReport";
					return "redirect:/ws/csRequisitionReport.do?srNo="
							+ operationId;

				}
			}
		}
		if (storeChangeFlag) {

			String operationId = csRequisitionMstDtl.getRequisitionNo();
			return "redirect:/ws/csRequisitionReportMid.do?srNo=" + operationId;
		} else {

			if (roleName.startsWith("ROLE_CS")) {

				return "redirect:/ls/requisitionList.do";
			}
			return "redirect:/ws/requisitionList.do";

		}

	}

	// Report Temporarily added by Ashid
	// This 2 reports jsp must update by proper rptdesign

	@RequestMapping(value = "/csRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showWsCsRequisitionReport(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("workshop/reports/wsCsItemRequisitionReport",
				model);

	}

	@RequestMapping(value = "/csRequisitionReportMid.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showWsCsRequisitionReportMid(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView(
				"workshop/reports/wsCsItemRequisitionReportMid", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String rrNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (WsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsCsRequisitionApprovalHierarchyHistory", "id",
						ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
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

		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryNextState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		if (roleName.startsWith("ROLE_CS")) {

			return "redirect:/ls/requisitionList.do";
		} else {
			return "redirect:/ws/requisitionList.do";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		String rrNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String backStateCode = csRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (WsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsCsRequisitionApprovalHierarchyHistory", "id",
						ids[0].toString());

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		WsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new WsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(rrNo + "");
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

		approvalHierarchyHistoryBackState.setDeptId(deptId);
		approvalHierarchyHistoryBackState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryBackState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/ws/requisitionList.do";
	}

	@RequestMapping(value = "/wsViewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssViewInventoryItem(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			CSItemTransactionMst invItem = gson.fromJson(json,
					CSItemTransactionMst.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			// CSItemTransactionMst centralStoreItems =
			// csItemTransactionMstService.getCSItemTransectionMst(selectItemFromDb.getItemId(),invItem.getKhathId(),invItem.getLedgerName());
			CSItemTransactionMst centralStoreItems = csItemTransactionMstService
					.getCSItemTransectionMstForss(selectItemFromDb.getItemId(),
							invItem.getKhathId());

			selectItemFromDb
					.setCurrentStock(centralStoreItems != null ? centralStoreItems
							.getQuantity() : 0.0);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/ssViewInventoryItemCategory.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssViewInventoryItemCategory(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
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

	@RequestMapping(value = "/validContractor.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public boolean validContractor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Boolean result = true;
		String contractNo = request.getParameter("contractNo").trim();
		WsCnAllocation allocation = (WsCnAllocation) commonService
				.getAnObjectByAnyUniqueColumn("WsCnAllocation", "workOrderNo",
						contractNo);

		if (allocation.getPreventiveQty1P() == 0
				&& allocation.getPreventiveQty3P() == 0
				&& allocation.getRepairQty1P() == 0
				&& allocation.getRepairQty3P() == 0) {
			result = false;
		}

		return result;

	}

	@RequestMapping(value = "/deleteAnItem.do", method = RequestMethod.POST)
	public ModelAndView deleteAnItem(CentralStoreRequisitionDtl bean)
			throws Exception {

		CentralStoreRequisitionDtl csDtlDb = (CentralStoreRequisitionDtl) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl",
						"id", "" + bean.getId());

		/*--------Requisition Item Delete---------*/
		commonService.deleteAnObjectById("CentralStoreRequisitionDtl",
				bean.getId());

		return this.getStoreRequisitionShowToCs(csDtlDb
				.getCentralStoreRequisitionMst());
	}

	@RequestMapping(value = "/wsWorkFlow.do", method = RequestMethod.GET)
	public ModelAndView wsWorkFlow() {

		return new ModelAndView("workshop/readWsWorkflow");
	}

}