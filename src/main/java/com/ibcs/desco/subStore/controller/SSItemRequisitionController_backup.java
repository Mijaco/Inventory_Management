/*package com.ibcs.desco.subStore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreItems;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/ss")
@PropertySource("classpath:common.properties")
public class SSItemRequisitionController {

	// Setting constant value for this controller
	public static final String SS_CS_REQUISITION = "SS_CS_REQUISITION";
	public static final String OPEN = "OPEN";
	public static final String DONE = "DONE";
	public static final String BACK = "BACK";
	public static final String RECEIVED = "RECEIVED";
	public static final String CS_STORE_TICKET = "CS_STORE_TICKET";

	// Create Object for Service Classes which will be instantiated from spring
	// bean

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	SubStoreRequisitionDtlService subStoreRequisitionDtlService;

	//@Autowired
	//SsCsRequisitionApprovalHierarchyHistoryService ssCsRequisitionApprovalHierarchyHistoryService;

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
	CommonService commonService;

	@Value("${subStore.csItemRequisitionNo.prefix}")
	private String ssCsItemRequisitionNoPrefix;

	@Value("${subStore.csStoreTicketNo.prefix}")
	private String ssCsStoreTicketNoPrefix;
	
	
	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/storeRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String storeRequisitionSave(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SubStoreRequisitionMst ssRequisitionMst = new SubStoreRequisitionMst();
		ssRequisitionMst.setIdenterDesignation(ssRequisitionMstDtl
				.getIdenterDesignation());
		ssRequisitionMst.setDeptName(department.getDeptName());
		
		//
		String requisitionNo = commonService.getCustomSequence(
				csItemRequisitionNoPrefix, "-");
		commonService.saveOrUpdateCustomSequenceToDB(requisitionNo);
		
		// set current date time as RequisitionDate. GUI date is not used here.
		ssRequisitionMst.setRequisitionDate(now);
		ssRequisitionMst.setActive(ssRequisitionMstDtl.isActive());
		ssRequisitionMst.setCreatedBy(userName);
		ssRequisitionMst.setCreatedDate(now);
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addStoreRequisitionDtls(ssRequisitionMstDtl,
				ssRequisitionMst, roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
			return "redirect:/ss/storeRequisitionShow.do?id="
					+ ssRequisitionMst.getId();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/ss/requisitionList.do";
		}

	}

	public boolean addStoreRequisitionDtls(
			SubStoreRequisitionMstDtl ssRequisitionMstDtl,
			SubStoreRequisitionMst ssRequisitionMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		List<Double> unitCostList = null;
		List<Double> totalCostList = null;
		List<String> headOfAccountList = null;
		List<String> remarksList = null;

		if (ssRequisitionMstDtl.getItemCode() != null) {
			itemCodeList = ssRequisitionMstDtl.getItemCode();
		}

		if (ssRequisitionMstDtl.getItemName() != null) {
			itemNameList = ssRequisitionMstDtl.getItemName();
		}

		if (ssRequisitionMstDtl.getUom() != null) {
			uomList = ssRequisitionMstDtl.getUom();
		}

		if (ssRequisitionMstDtl.getQuantityRequired() != null) {
			quantityRequiredList = ssRequisitionMstDtl.getQuantityRequired();
		}

		if (ssRequisitionMstDtl.getUnitCost() != null) {
			unitCostList = ssRequisitionMstDtl.getUnitCost();
		}

		if (ssRequisitionMstDtl.getTotalCost() != null) {
			totalCostList = ssRequisitionMstDtl.getTotalCost();
		}

		if (ssRequisitionMstDtl.getHeadOfAccount() != null) {
			headOfAccountList = ssRequisitionMstDtl.getHeadOfAccount();
		}

		if (ssRequisitionMstDtl.getRemarks() != null) {
			remarksList = ssRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(SS_CS_REQUISITION);

		
		 * Get all State code which for local to Central Store requisition
		 * process
		 

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
							SS_CS_REQUISITION, stateCodes[0].toString());
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
				String requisitionNo = commonService.getOperationIdByPrefixAndSequenceName(ssCsItemRequisitionNoPrefix, separator, "SS_CS_REQ_SEQ");
				ssRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);
				
				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"SubStoreRequisitionMst", "requisitionNo",
								ssRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					SubStoreRequisitionDtl ssRequisitionDtl = new SubStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						ssRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						ssRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						ssRequisitionDtl.setUom(uomList.get(i));
					} else {
						ssRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						ssRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						ssRequisitionDtl.setQuantityRequired(0);
					}

					if (!unitCostList.isEmpty()) {
						ssRequisitionDtl.setUnitCost(unitCostList.get(i));
					} else {
						ssRequisitionDtl.setUnitCost(unitCostList.get(i));
					}

					if (!totalCostList.isEmpty()) {
						ssRequisitionDtl.setTotalCost(totalCostList.get(i));
					} else {
						ssRequisitionDtl.setTotalCost(0);
					}

					if (!headOfAccountList.isEmpty()) {
						ssRequisitionDtl.setHeadOfAccount(headOfAccountList
								.get(i));
					} else {
						ssRequisitionDtl.setHeadOfAccount("");
					}

					if (!remarksList.isEmpty()) {
						ssRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						ssRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					ssRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					ssRequisitionDtl.setRequisitionNo(ssRequisitionMst
							.getRequisitionNo());
					ssRequisitionDtl.setCreatedBy(ssRequisitionMst
							.getCreatedBy());
					ssRequisitionDtl.setCreatedDate(ssRequisitionMst
							.getCreatedDate());
					ssRequisitionDtl.setActive(true);
					ssRequisitionDtl
							.setSubStoreRequisitionMst(ssRequisitionMstDb);

					// insert item detail in ssRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);
				}

				// Start Approval Hierarchy History insert process
				//addStoreRequisitionHierarchyHistory(ssRequisitionMst,
					//	approvalHierarchy, stateCodes, roleName, department,
						//authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			SubStoreRequisitionMst ssRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new LsCsRequisitionApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(ssRequisitionMst
				.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(SS_CS_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(ssRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(ssRequisitionMst
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
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);
		
		//String requisitionNo = commonService.getOperationIdByPrefixAndSequenceName(csItemRequisitionNoPrefix, separator, "LS_CS_REQ_SEQ");
		
		//int maxId = (Integer) commonService.getMaxValueByObjectAndColumn(
				//"SubStoreRequisitionMst", "id");
		//String formattedId = String.format("%06d", maxId + 1);
		//model.put("srId", csItemRequisitionNoPrefix + formattedId);
		//model.put("srId", requisitionNo);
		return new ModelAndView("subStore/ssToCsStoreRequisitionForm", model);
	}
	
	@RequestMapping(value = "/ssViewInventoryItem.do", method = RequestMethod.POST)
	//@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssViewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			CentralStoreItems centralStoreItems = centralStoreItemsService
					.getCentralStoreItemsByItemId(selectItemFromDb.getItemId());

			selectItemFromDb
					.setCurrentStock(centralStoreItems != null ? centralStoreItems
							.getBalance() : "0");

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/ssViewInventoryItemCategory.do", method = RequestMethod.POST)
	//@PreAuthorize("isAuthenticated()")
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
	
	public boolean isJSONValid(String JSON_STRING) {
		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}
	@RequestMapping(value = "/requisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[ssCsRequisitionHistoryList.size()];
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("subStoreRequisitionMstList",
				subStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		} else {
			return new ModelAndView("subStore/ssRequisitionList", model);
		}

	}

	

	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(
			SubStoreRequisitionMst subStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		
		Departments department = departmentsService
		 .getDepartmentByDeptId(authUser.getDeptId());
		 

		// Test Dual
		Long abc = commonService.getNextVal();
		//

		String deptId = authUser.getDeptId();

		SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
				.getSubStoreRequisitionMst(subStoreRequisitionMst
						.getId());
		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> SubStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionApprovalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
						SS_CS_REQUISITION, operationId, DONE);

		if (!ssCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = ssCsRequisitionApprovalHierarchyHistoryList.get(
					ssCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						SS_CS_REQUISITION, operationId, OPEN);

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
						SS_CS_REQUISITION, roleNameList);
		 buttonValue = approveHeirchyList.get(0).getButtonName(); 

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
								SS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);
		model.put("subStoreRequisitionMst", subStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList",
				ssCsRequisitionApprovalHierarchyHistoryList);
		model.put("SubStoreRequisitionDtlList",
				SubStoreRequisitionDtlList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsRequisitionShow", model);
		} else {
			return new ModelAndView("localStore/csRequisitionShow", model);
		}

	}
	@RequestMapping(value = "/requisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[lsCsRequisitionHistoryList.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("subStoreRequisitionMstList",
				subStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionList", model);
		} else {
			return new ModelAndView("localStore/csRequisitionList", model);
		}

	}

	

	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(
			SubStoreRequisitionMst subStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		
		 * Departments department = departmentsService
		 * .getDepartmentByDeptId(authUser.getDeptId());
		 

		// Test Dual
		Long abc = commonService.getNextVal();
		//

		String deptId = authUser.getDeptId();

		SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
				.getSubStoreRequisitionMst(subStoreRequisitionMst
						.getId());
		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> SubStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, DONE);

		
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, OPEN);

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
						LS_CS_REQUISITION, roleNameList);
		 buttonValue = approveHeirchyList.get(0).getButtonName(); 

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
								LS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);
		model.put("subStoreRequisitionMst", subStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("SubStoreRequisitionDtlList",
				SubStoreRequisitionDtlList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsRequisitionShow", model);
		} else {
			return new ModelAndView("localStore/csRequisitionShow", model);
		}

	}

	// Receiving Report (RR) Approving process
	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!ssRequisitionMstDtl.getReturn_state().equals("")
				|| ssRequisitionMstDtl.getReturn_state().length() > 0) {

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId",
							ssRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
					.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(ssRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_REQUISITION,
							ssRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(ssRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(ssRequisitionMstDtl.getRequisitionNo());
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
			String operationId = ssRequisitionMstDtl.getRequisitionNo();

			SubStoreRequisitionMst ssRequisitionMst = (SubStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
							"requisitionNo", operationId);

			List<SubStoreRequisitionDtl> SubStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl",
							"requisitionNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
					.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);

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
									LS_CS_REQUISITION, nextStateCode + "");

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
									LS_CS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(ssRequisitionMstDtl
									.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now we have to insert data in store ticket mst and
					// history
					CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
					// csStoreTicketMst.setTicketNo(ticketNo);
					csStoreTicketMst.setStoreTicketType(LS_CS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(ssRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(ssRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);

					// Auto generate Store Ticket number
					int storeTicketMaxId = (Integer) commonService
							.getMaxValueByObjectAndColumn("CSStoreTicketMst",
									"id");
					Date now = new Date();
					String year = now.getYear() + "";
					String mm = now.getMonth() + "";
					String formattedId = String.format("%06d",
							storeTicketMaxId + 1);
					csStoreTicketMst
							.setTicketNo(csStoreTicketNoPrefix + year.trim()
									+ "-" + mm.trim() + "-" + formattedId);
					String storeTicketNo = commonService.getCustomSequence1(
							csStoreTicketNoPrefix, "-");
					commonService.saveOrUpdateCustomSequence1ToDB(storeTicketNo);
					String storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(csStoreTicketNoPrefix, separator, "CS_ST_SEQ");
					
					csStoreTicketMst.setTicketNo(storeTicketNo);

					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

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
					storeTicketApprovalHierarchyHistory.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory
							.setOperationName(CS_STORE_TICKET);
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
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

					model.addAttribute("operationId", operationId);

					return "centralStore/csRequisitionReport";

				}
			}
		}
		return "redirect:/ls/requisitionList.do";
	}

	@RequestMapping(value = "/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String nextStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
				.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, nextStateCode + "");
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

		return "redirect:/ls/requisitionList.do";
	}

	@RequestMapping(value = "/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {
		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String backStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
				.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState
				.setStateName(approvalHierarchyBackSate.getStateName());
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

		return "redirect:/ls/requisitionList.do";
	}

	@RequestMapping(value = "/requisitionSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionSearch(
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String operationId = ssRequisitionMstDtl.getRequisitionNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		String[] operationIdList = new String[lsCsRequisitionHistoryList.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("subStoreRequisitionMstList",
				subStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionList", model);
		} else {
			return new ModelAndView("localStore/csRequisitionList", model);
		}

	}

	

}
*/