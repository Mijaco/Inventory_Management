package com.ibcs.desco.subStore.controller;

import java.io.IOException;
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

import org.apache.commons.lang3.StringUtils;
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
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.SsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.bean.CSStoreTicketMstDtl;
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
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.localStore.service.LSItemTransactionMstService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SSGatePassDtl;
import com.ibcs.desco.subStore.model.SSGatePassMst;
import com.ibcs.desco.subStore.model.SSItemLocationDtl;
import com.ibcs.desco.subStore.model.SSItemLocationMst;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/ss")
@PropertySource("classpath:common.properties")
public class SSItemRequisitionController extends Constrants {

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
	public ModelAndView storeRequisitionSave(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			RedirectAttributes redirectAttributes) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
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
			csRequisitionMst.setDeptName(department.getDeptName());
			csRequisitionMst.setKhathId(descoKhath.getId());
			csRequisitionMst.setKhathName(descoKhath.getKhathName());

			//
			/*
			 * String requisitionNo = commonService.getCustomSequence(
			 * csItemRequisitionNoPrefix, "-");
			 * commonService.saveOrUpdateCustomSequenceToDB(requisitionNo);
			 */

			// set current date time as RequisitionDate. GUI date is not used
			// here.

			csRequisitionMst.setSndCode(department.getDescoCode());
			csRequisitionMst.setUuid(UUID.randomUUID().toString());
			csRequisitionMst.setRequisitionDate(now);
			csRequisitionMst.setActive(csRequisitionMstDtl.isActive());
			csRequisitionMst.setCreatedBy(userName);
			csRequisitionMst.setCreatedDate(now);
			csRequisitionMst.setRequisitionTo(ContentType.CENTRAL_STORE
					.toString());
			csRequisitionMst.setSenderStore(ContentType.SUB_STORE.toString());
			// Ashid
			csRequisitionMst.setReceivedBy(csRequisitionMstDtl.getReceivedBy());
			csRequisitionMst.setCarriedBy(csRequisitionMstDtl.getCarriedBy());
			boolean successFlag = true;
			String msg = "";
			// Save requisition master and details info to db if any details
			// exist
			successFlag = addStoreRequisitionDtls(csRequisitionMstDtl,
					csRequisitionMst, roleName, department, authUser);
			redirectAttributes.addFlashAttribute("centralStoreRequisitionMst",
					csRequisitionMst);
			if (successFlag) {
				return this.getStoreRequisitionShowToCs(csRequisitionMst);
			} else {
				msg = "Sorry! You have no permission to do this operation. Try again.";
				model.put("msg", msg);
				return this.showReqisitionListofLocal();
			}
		} catch (Exception E) {
			E.printStackTrace();
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("subStore/errorSS", model);
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
				.getApprovalHierarchyByOperationName(SS_CS_REQUISITION);

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
				String descoDeptCode = department.getDescoCode();
				String requisitionNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoRequisitionNoPrefix, descoDeptCode,
								separator, "SS_CS_REQ_SEQ");
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
		SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new SsCsRequisitionApprovalHierarchyHistory();
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
		approvalHierarchyHistory.setOperationName(SS_CS_REQUISITION);
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
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);
		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
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
		model.put("department", department);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		} else {
			return new ModelAndView("subStore/csRequisitionList", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhathList", descoKhathList);
		model.put("categoryList", categoryList);
		model.put("deptName", department.getDeptName());
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		return new ModelAndView("subStore/ssToCsStoreRequisitionForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShowToCs.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShowToCs(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		String requisitionTo = centralStoreRequisitionMst.getRequisitionTo();
		if (requisitionTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {

			SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
					.getSubStoreRequisitionMst(centralStoreRequisitionMst
							.getId());
			return itemRequisitionController
					.getStoreRequisitionShowOfLocalToSub(subStoreRequisitionMstdb);

		}
		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(centralStoreRequisitionMst
						.getId());
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<SsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
						SS_CS_REQUISITION, operationId, DONE);

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

		List<SsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
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
								SS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		Map<String, Object> model = new HashMap<String, Object>();

		List<StoreLocations> storeLocationList = getStoreLocationList("CS");
		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("locationList", storeLocationList);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionShow", model);
		} else {
			return new ModelAndView("subStore/csRequisitionShow", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(
			CentralStoreRequisitionMst centralStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		String requisitionTo = centralStoreRequisitionMst.getRequisitionTo();
		if (requisitionTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {

			SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
					.getSubStoreRequisitionMst(centralStoreRequisitionMst
							.getId());
			if (subStoreRequisitionMstdb.getKhathId() > 1) {
				return new ModelAndView("redirect:/ls/pd/requisitionShow.do?id="
						+ subStoreRequisitionMstdb.getId() + "&requisitionTo=" + requisitionTo);
			}
			return itemRequisitionController
					.getStoreRequisitionShowOfLocalToSub(subStoreRequisitionMstdb);

		}
		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(centralStoreRequisitionMst
						.getId());
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		Integer khathId = centralStoreRequisitionMstdb.getKhathId();

		// This Block will set Curent stock of CS
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

		List<SsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
						SS_CS_REQUISITION, operationId, DONE);

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

		List<SsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
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
								SS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		/*
		 * The following four lines of code ( 827 - 830 ) are added by
		 * Ihteshamul Alam
		 */
		String userrole = centralStoreRequisitionMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		centralStoreRequisitionMstdb.setCreatedBy(createBy.getName());

		Map<String, Object> model = new HashMap<String, Object>();

		// Start m@@ || Generate drop down list for location in Grid
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);
		// End m@@ || Generate drop down list for location in Grid

		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		// model.put("locationList", storeLocationList);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		String rolePrefix = roleName.substring(0, 7);

		// Load Category List
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		if (rolePrefix.equals("ROLE_SS")) {
			model.put("categoryList", categoryList);
			return new ModelAndView("subStore/ssToCsRequisitionShow", model);
		} else {
			for (int i = 0; i < centralStoreRequisitionDtlList.size(); i++) {
				String uuid = centralStoreRequisitionMstdb.getUuid();
				List<TempItemLocation> tpList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								uuid, "itemCode",
								centralStoreRequisitionDtlList.get(i)
										.getItemCode());
				Double issuedQty = 0.0;
				for (TempItemLocation tp : tpList) {
					issuedQty += tp.getQuantity();
				}
				centralStoreRequisitionDtlList.get(i).setQuantityIssued(
						issuedQty);

			}
			model.put("centralStoreRequisitionDtlList",
					centralStoreRequisitionDtlList);
			return new ModelAndView("subStore/csRequisitionShow", model);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		
		boolean storeChangeFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!csRequisitionMstDtl.getReturn_state().equals("")
				|| csRequisitionMstDtl.getReturn_state().length() > 0) {

			List<SsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SsCsRequisitionApprovalHierarchyHistory",
							"operationId",
							csRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = ssCsRequisitionApprovalHierarchyHistoryService
					.getSsCsRequisitionApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_CS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(csRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new SsCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_CS_REQUISITION,
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
					.getApprovalHierarchyByOperationName(SS_CS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<SsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SsCsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = ssCsRequisitionApprovalHierarchyHistoryService
					.getSsCsRequisitionApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;
			// update issued qty

			List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							centralStoreRequisitionDtl
									.setQuantityIssued(issuedqty);
						} else {
							centralStoreRequisitionDtl
									.setQuantityIssued(centralStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
						counter++;
					}
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
									SS_CS_REQUISITION, nextStateCode + "");

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
						
						storeChangeFlag = true;
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
					
					// item Quantity History setup Start
					String historyChange = "";
					int counter = 0;
					if(issuedQtyList != null){
						for( int i = 0; i < issuedQtyList.size(); i++ ) {
							if( counter == 0 ) {
								historyChange += centralStoreRequisitionDtlList.get(i).getItemCode() + ": " + issuedQtyList.get(i) + " " + centralStoreRequisitionDtlList.get(i).getUom();
								counter++;
							} else {
								historyChange += ",   " + centralStoreRequisitionDtlList.get(i).getItemCode() + ": " + issuedQtyList.get(i) + " " + centralStoreRequisitionDtlList.get(i).getUom();
							}
						}
					}
					approvalHierarchyHistory.setHistoryChange(historyChange);
					// item Quantity History setup end
					
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_CS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					approvalHierarchyHistory
							.setJustification(csRequisitionMstDtl
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
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
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
					csStoreTicketMst.setStoreTicketType(SS_CS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(csRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(csRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);
					csStoreTicketMst.setReceivedBy(csRequisitionMst.getReceivedBy());
					csStoreTicketMst.setKhathId(csRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(csRequisitionMst
							.getKhathName());

					String descoDeptCode = department.getDescoCode();
					// csStoreTicketMst.setIssuedBy(csRequisitionMst.getRequisitionTo());
					csStoreTicketMst.setIssuedBy(authUser.getName());

					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "CS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);

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
					// return "centralStore/ssCsRequisitionReport";
					return "redirect:/ss/csRequisitionReport.do?srNo="+operationId;

				}
			}
		}
		if(storeChangeFlag){
			//model.addAttribute("operationId", csRequisitionMstDtl.getRequisitionNo());
			//return "subStore/reports/ssCsRequisitionReport";
			String operationId = csRequisitionMstDtl.getRequisitionNo();			
			return "redirect:/ss/showRequisitionReportCS.do?srNo="+operationId;
		}else{
			//return "redirect:/ss/requisitionList.do";
			if(roleName.contains("ROLE_SS")){
				return "redirect:/ss/requisitionList.do";
			}else{
				return "redirect:/ls/requisitionList.do";
			}
		}
		
	}
	
	@RequestMapping(value = "/showRequisitionReportCS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportCS(String srNo){
		Map <String, Object> model =  new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("subStore/reports/ssCsRequisitionReport", model);
	}
	
	@RequestMapping(value = "/csRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csRequisitionReport(String srNo){
		Map <String, Object> model =  new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("centralStore/ssCsRequisitionReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionSearch(
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String operationId = ssRequisitionMstDtl.getRequisitionNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

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

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		} else {
			return new ModelAndView("subStore/csRequisitionList", model);
		}

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

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<SsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"SsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = ssCsRequisitionApprovalHierarchyHistoryService
				.getSsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new SsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_REQUISITION, nextStateCode + "");
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

		String roleName = commonService.getAuthRoleName();
		if(roleName.contains("ROLE_SS")){
			return "redirect:/ss/requisitionList.do";
		}else{
			return "redirect:/ls/requisitionList.do";
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

		List<SsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"SsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = ssCsRequisitionApprovalHierarchyHistoryService
				.getSsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		SsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new SsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_REQUISITION, backStateCode + "");
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

		if(roleName.contains("ROLE_SS")){
			return "redirect:/ss/requisitionList.do";
		}else{
			return "redirect:/ls/requisitionList.do";
		}
	}

	@RequestMapping(value = "/ssViewInventoryItem.do", method = RequestMethod.POST)
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

			CSItemTransactionMst centralStoreItems =
			csItemTransactionMstService.getCSItemTransectionMst(selectItemFromDb.getItemId(),invItem.getKhathId(),NEW_SERVICEABLE);
			
			CSItemTransactionMst centralStoreItems1 =
					csItemTransactionMstService.getCSItemTransectionMst(selectItemFromDb.getItemId(),invItem.getKhathId(),RECOVERY_SERVICEABLE);
			/*CSItemTransactionMst centralStoreItems = csItemTransactionMstService
					.getCSItemTransectionMstForss(selectItemFromDb.getItemId(),
							invItem.getKhathId());*/
			Double totalStockQuantity = 0.0;
			if(centralStoreItems != null){
				totalStockQuantity+=centralStoreItems.getQuantity();
			}
			if(centralStoreItems1 != null){
				totalStockQuantity+=centralStoreItems1.getQuantity();
			}

			selectItemFromDb
					.setCurrentStock(totalStockQuantity);

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

	// get store ticket List filter on login user and login user role
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeTicketlist.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login user information and login user role

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		// get which all approval hierarchy which open for login user
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

		List<String> operationList = new ArrayList<String>();
		// get all operation id which open for this login user

		for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationList.add(storeTicketApprovalHierarchyHistory
					.getOperationId());
		}

		/*
		 * List<String> operationId = new ArrayList<String>();
		 * 
		 * for (StoreTicketApprovalHierarchyHistory
		 * storeTicketApprovalHierarchyHistory :
		 * storeTicketApprovalHierarchyHistoryList) {
		 * operationId.add(storeTicketApprovalHierarchyHistory
		 * .getOperationId()); }
		 */

		// get All store ticket which open for the login user
		List<SSStoreTicketMst> ssStoreTicketMstList = (List<SSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSStoreTicketMst",
						"operationId", operationList);
		/*
		 * List<CSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
		 * .listCSProcItemRcvMstByOperationIdList(operationId);
		 */
		// List<CSStoreTicketMst> csStoreTicketMstList =
		// (List<CSStoreTicketMst>)(Object)commonService.getObjectListByAnyColumnValueList("CSStoreTicketMst",
		// "operationId", operationId);
		model.put("csStoreTicketMstList", ssStoreTicketMstList);
		return new ModelAndView("subStore/ssStoreTicketList", model);
	}

	// after receiving report store ticket come to next user who generate a
	// store ticket and show that

	@RequestMapping(value = "/openStoreTicket.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView openStoreTicketPOST(SSStoreTicketMst ssStoreTicketMst) {
		return getSSStoreTicketCreateOpen(ssStoreTicketMst);
	}

	/*
	 * @RequestMapping(value = "/openStoreTicket.do", method =
	 * RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView
	 * openStoreTicketGET(SSStoreTicketMst csStoreTicketMst) { return
	 * getSSStoreTicketCreateOpen(csStoreTicketMst); }
	 */

	// @RequestMapping(value = "/openStoreTicket.do", method =
	// RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@SuppressWarnings("unchecked")
	public ModelAndView getSSStoreTicketCreateOpen(
			SSStoreTicketMst ssStoreTicketMst) {

		ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo",
						ssStoreTicketMst.getTicketNo());
		Map<String, Object> model = new HashMap<String, Object>();

		// first time flag value is 0.. so if 0 than user should be generate a
		// store ticket against operation id
		// so first condition is true (1). if one user will only show the store
		// ticket
		if (ssStoreTicketMst.isFlag()) {
			// target : show the store ticket
			String currentStatus = "";

			String operationId = ssStoreTicketMst.getOperationId();

			// get all hierarchy history against current operation id and status
			// done
			List<StoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = storeTicketApprovalHierarchyHistoryService
					.getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(
							SS_STORE_TICKET, operationId, DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

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
									SS_STORE_TICKET, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_STORE_TICKET, stateCode + "");
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
			String ticketNo = ssStoreTicketMst.getTicketNo();

			// get all store ticket master info against current ticket no
			SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
					.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
							"ticketNo", ticketNo);
			// get all store ticket item details info against current ticket no
			List<SSStoreTicketDtl> csStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo",
							ticketNo);

			AuthUser authUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							csStoreTicketMstdb.getCreatedBy());
			csStoreTicketMstdb.setIssuedBy(authUser.getName());
			csStoreTicketMstdb.setCreatedBy(authUser.getName());
			// sent to browser
			model.put("storeTicketApproveHistoryList",
					storeTicketApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csStoreTicketMst", csStoreTicketMstdb);
			model.put("csStoreTicketDtlList", csStoreTicketDtlList);
			model.put("buttonValue", buttonValue);

			if (ssStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_SS_RETURN_SLIP)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_RETURN)) {
				return new ModelAndView("subStore/ssStoreTicketShowRS", model);
			} else if (ssStoreTicketMst.getStoreTicketType().equals(
					LS_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
									LS_PD_SS_REQUISITION)) {
				return new ModelAndView(
						"subStore/ssStoreTicketShowRequisition", model);
			} else {
				return null;
			}

		} else {
			// target : for generate a store ticket now will open a form of
			// store ticket with some value
			// store ticket master (common) information
			/*
			 * SSStoreTicketMst csStoreTicketMstdb = csStoreTicketMstService
			 * .getCSStoreTicketMstByTicketNo(csStoreTicketMst .getTicketNo());
			 */
			String ticketNo = ssStoreTicketMst.getTicketNo();
			SSStoreTicketMst ssStoreTicketMstdb = (SSStoreTicketMst) commonService
					.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
							"ticketNo", ticketNo);

			model.put("csStoreTicketMst", ssStoreTicketMstdb);
			// Store ticket Items information
			if (ssStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_SS_RETURN_SLIP)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_RETURN)) {
				List<SSReturnSlipDtl> ssReturnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
						.getObjectListByAnyColumn("SSReturnSlipDtl",
								"returnSlipNo",
								ssStoreTicketMst.getOperationId());

				model.put("ssReturnSlipDtlList", ssReturnSlipDtlList);
				return new ModelAndView("subStore/ssStoreTicketFormRS", model);
			} else if (ssStoreTicketMst.getStoreTicketType().equals(
					LS_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_REQUISITION)
					|| ssStoreTicketMst.getStoreTicketType().equals(
							LS_PD_SS_REQUISITION)) {
				List<SubStoreRequisitionDtl> ssRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("SubStoreRequisitionDtl",
								"requisitionNo",
								ssStoreTicketMst.getOperationId());

				// Get ST Dtl by Ledger Type || Added By Ashid
				List<SSStoreTicketDtl> ssStoreTicketDtlList = getSsTktDtl(
						ssStoreTicketMst, ssRequisitionDtlList);
				model.put("ssStoreTicketDtlList", ssStoreTicketDtlList);
				// model.put("csRequisitionDtlList", ssRequisitionDtlList);
				return new ModelAndView(
						"subStore/ssStoreTicketFormRequisition", model);
			}

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	List<SSStoreTicketDtl> getSsTktDtl(SSStoreTicketMst csStoreTicketMst,
			List<SubStoreRequisitionDtl> ssRequisitionDtlList) {
		List<SSStoreTicketDtl> stDtlList = new ArrayList<SSStoreTicketDtl>();
		SubStoreRequisitionMst ssReqMst = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"requisitionNo", csStoreTicketMst.getOperationId());
		for (SubStoreRequisitionDtl sd : ssRequisitionDtlList) {
			String itemCode = sd.getItemCode();
			String uuid = ssReqMst.getUuid();
			//

			//
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
				SSStoreTicketDtl stDtl = new SSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setSsStoreTicketMst(csStoreTicketMst);
				stDtl.setActive(true);
				stDtl.setLedgerName(NEW_SERVICEABLE);
				stDtl.setQuantity(nsQty);
				stDtlList.add(stDtl);
			}

			if (rsQty > 0) {
				SSStoreTicketDtl stDtl = new SSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setSsStoreTicketMst(csStoreTicketMst);
				stDtl.setActive(true);
				stDtl.setLedgerName(RECOVERY_SERVICEABLE);
				stDtl.setQuantity(rsQty);
				stDtlList.add(stDtl);
			}

			if (usQty > 0) {
				SSStoreTicketDtl stDtl = new SSStoreTicketDtl();
				stDtl.setId(null);
				stDtl.setItemId(itemCode);
				stDtl.setDescription(sd.getItemName());
				stDtl.setUom(sd.getUom());
				stDtl.setCreatedBy(commonService.getAuthUserName());
				stDtl.setCreatedDate(new Date());
				stDtl.setTicketNo(csStoreTicketMst.getTicketNo());
				stDtl.setSsStoreTicketMst(csStoreTicketMst);
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
			CSStoreTicketMstDtl csStoreTicketMstDtl,
			RedirectAttributes redirectAttributes) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		List<String> itemIdList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> costList = null;

		List<String> ledgerBookNoList = null;

		List<String> ledgerPageNoList = null;

		List<Double> quantityList = null;

		List<String> ledgerNameList = null;

		List<String> remarksList = null;

		Date now = new Date();

		SSStoreTicketMst ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "id",
						csStoreTicketMstDtl.getId() + "");
		ssStoreTicketMst.setCreatedBy(userName);
		ssStoreTicketMst.setCreatedDate(now);
		ssStoreTicketMst.setActive(true);
		ssStoreTicketMst.setFlag(true);
		ssStoreTicketMst.setTicketDate(now);
		ssStoreTicketMst.setIssuedBy(authUser.getName());
		commonService.saveOrUpdateModelObjectToDB(ssStoreTicketMst);

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

		if (csStoreTicketMstDtl.getRemarks() != null) {
			remarksList = csStoreTicketMstDtl.getRemarks();
		}

		if (csStoreTicketMstDtl.getLedgerBookNo() != null) {
			ledgerBookNoList = csStoreTicketMstDtl.getLedgerBookNo();
		}

		if (csStoreTicketMstDtl.getLedgerPageNo() != null) {
			ledgerPageNoList = csStoreTicketMstDtl.getLedgerPageNo();
		}

		if (csStoreTicketMstDtl.getQuantity() != null) {
			quantityList = csStoreTicketMstDtl.getQuantity();
		}

		if (csStoreTicketMstDtl.getLedgerName() != null) {
			ledgerNameList = csStoreTicketMstDtl.getLedgerName();
		}

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				SSStoreTicketDtl csStoreTicketDtl = new SSStoreTicketDtl();
				csStoreTicketDtl.setActive(true);
				csStoreTicketDtl.setCreatedBy(userName);
				csStoreTicketDtl.setCreatedDate(now);
				csStoreTicketDtl.setTicketNo(csStoreTicketMstDtl.getTicketNo());
				csStoreTicketDtl.setSsStoreTicketMst(ssStoreTicketMst);
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
					csStoreTicketDtl.setCost(Double.parseDouble(costList.get(i)
							.toString()));
				} else {
					csStoreTicketDtl.setCost(0.0);
				}

				if (remarksList.size() > 0) {
					csStoreTicketDtl.setRemarks(remarksList.get(i));
				} else {
					csStoreTicketDtl.setRemarks("");
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

				if (quantityList.size() > 0 || quantityList != null) {
					csStoreTicketDtl.setQuantity(quantityList.get(i));
				}

				if (ledgerNameList.size() > 0) {
					csStoreTicketDtl.setLedgerName(ledgerNameList.get(i));
				} else {
					csStoreTicketDtl.setLedgerName(NEW_SERVICEABLE);
				}
				commonService.saveOrUpdateModelObjectToDB(csStoreTicketDtl);
			}
		}
		redirectAttributes.addFlashAttribute("csStoreTicketMst",
				csStoreTicketMstDtl);

		if (ssStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION) 
				|| ssStoreTicketMst.getStoreTicketType().equals(LS_PD_SS_REQUISITION)) {
			// return "redirect:/ss/openStoreTicket.do";
			return getSSStoreTicketCreateOpen(ssStoreTicketMst);
		} else if (ssStoreTicketMst.getStoreTicketType().equals(
				CN_SS_REQUISITION)) {
			// return "redirect:/ss/openStoreTicket.do";
			return getSSStoreTicketCreateOpen(ssStoreTicketMst);
		} else if (ssStoreTicketMst.getStoreTicketType().equals(
				CN_PD_SS_REQUISITION)) {
			// return "redirect:/ss/openStoreTicket.do";
			return getSSStoreTicketCreateOpen(ssStoreTicketMst);
		} else if (ssStoreTicketMst.getStoreTicketType().equals(
				LS_SS_RETURN_SLIP)
			|| ssStoreTicketMst.getStoreTicketType().equals(
					CN_PD_SS_RETURN)) {
			// return "redirect:/ss/openStoreTicket.do";
			return getSSStoreTicketCreateOpen(ssStoreTicketMst);
		} else if (ssStoreTicketMst.getStoreTicketType().equals(
				CN_SS_RETURN_SLIP)) {
			// return "redirect:/ss/openStoreTicket.do";
			return getSSStoreTicketCreateOpen(ssStoreTicketMst);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeTicketSubmitApproved.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String subSotreStoreTicketSubmitApproved(
			Model model,
			@ModelAttribute("csStoreTicketMstDtl") CSStoreTicketMstDtl csStoreTicketMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		// nasrin
		String ticketNo = csStoreTicketMstDtl.getTicketNo();

		SSStoreTicketMst ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo",
						ticketNo + "");

		List<SSStoreTicketDtl> csStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo",
						ticketNo);

		// get All State Codes from Approval Hierarchy and sort Dececding oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

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
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							ssStoreTicketMst.getStoreTicketType(), state + "");
			if (state == currentStateCode) {

				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);

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

				if (!ssStoreTicketMst.isApproved()) {
					storeTicketApprovalHierarchyHistory.setStatus(DONE);
					storeTicketApprovalHierarchyHistory
							.setCreatedDate(new Date());
					storeTicketApprovalHierarchyHistory.setModifiedBy(userName);
					storeTicketApprovalHierarchyHistory
							.setModifiedDate(new Date());
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
					storeTicketApprovalHierarchyHistory
							.setJustification(csStoreTicketMstDtl
									.getJustification());
					storeTicketApprovalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					storeTicketApprovalHierarchyHistory.setcEmpId(authUser
							.getEmpId());
					storeTicketApprovalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					storeTicketApprovalHierarchyHistory
							.setcDesignation(authUser.getDesignation());

					storeTicketApprovalHierarchyHistoryService
							.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);

					// update Ledger Mst
					this.ssLedgerLocation(csStoreTicketDtlList, userName,
							roleName, authUser, department, ssStoreTicketMst);

					ssStoreTicketMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(ssStoreTicketMst);

					String gpNos = "";
					// open the gate pass for Item issued against Requisition
					if (ssStoreTicketMst.getStoreTicketType().equals(
							LS_SS_REQUISITION)
							|| ssStoreTicketMst.getStoreTicketType().equals(
									CN_SS_REQUISITION)
							|| ssStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_REQUISITION)
							|| ssStoreTicketMst.getStoreTicketType().equals(
									LS_PD_SS_REQUISITION)) {

						SubStoreRequisitionMst ssReqDb = (SubStoreRequisitionMst) commonService
								.getAnObjectByAnyUniqueColumn(
										"SubStoreRequisitionMst",
										"requisitionNo", operationId);

						// m@@ Start
						// Assign GP nos for Ticket MST
						List<String> multipleGPNoList = saveMultipleGP(
								userName, department, ssReqDb, ssStoreTicketMst);
						// set GP number in CS Ticket MST and Requisition Mst
						gpNos = StringUtils.join(multipleGPNoList, ", ");
						ssStoreTicketMst.setGatePass(gpNos);
						commonService
								.saveOrUpdateModelObjectToDB(ssStoreTicketMst);
						ssReqDb.setGatePassNo(gpNos);
						commonService.saveOrUpdateModelObjectToDB(ssReqDb);
						// set GP Nos for view page
						model.addAttribute("multipleGPNoList", multipleGPNoList);
						// m@@ End
					}

					// Instant Store Ticket Report Call
					// model.addAttribute("ticketNo", ticketNo);

					if (ssStoreTicketMst.getStoreTicketType().equals(
							LS_SS_RETURN_SLIP)) {
						// return "subStore/reports/lsSsStoreTicketReportRS";
						return "redirect:/ss/lsSsStoreTicketReportRS.do?stNo="
								+ ticketNo;
					} else if (ssStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_RETURN)) {
						// return "subStore/reports/cnPdSsStoreTicketReportRS";
						return "redirect:/ss/cnPdSsStoreTicketReportRS.do?ticketNo="
								+ ticketNo;
					} else if (ssStoreTicketMst.getStoreTicketType().equals(
							LS_SS_REQUISITION)
							|| ssStoreTicketMst.getStoreTicketType().equals(
							LS_PD_SS_REQUISITION)) {
						// return
						// "subStore/reports/lsSsStoreTicketReportRequisition";
						return "redirect:/ss/lsSsStoreTicketReportRequisition.do?ticketNo="
								+ ticketNo + "&gpNos=" + gpNos;
					} else if (ssStoreTicketMst.getStoreTicketType().equals(
							CN_SS_REQUISITION)
							|| ssStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_REQUISITION)) {
						commonService.deleteAnObjectListByAnyColumn(
								"RequisitionLock", "requisitionNo",
								ssStoreTicketMst.getOperationId());
						// return
						// "subStore/reports/cnPdSsStoreTicketReportRequisition";
						return "redirect:/ss/cnPdSsStoreTicketReportRequisition.do?ticketNo="
								+ ticketNo + "&gpNos=" + gpNos;
					} else {
						return "";
					}
				}
			}

		}

		return "redirect:/ss/storeTicketlist.do";
	}

	// return "redirect:/ss/lsSsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/lsSsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsSsStoreTicketReportRS(String stNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ticketNo", stNo);
		return new ModelAndView("subStore/reports/lsSsStoreTicketReportRS",
				model);
	}

	// return "redirect:/ss/cnPdSsStoreTicketReportRS.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/cnPdSsStoreTicketReportRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnPdSsStoreTicketReportRS(String ticketNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		SSStoreTicketMst ssTicket = ( SSStoreTicketMst ) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo", ticketNo);
		
		model.put("ticketNo", ticketNo);
		if( ssTicket.getStoreTicketType().contains("CN_PD_SS_RETURN") ) {
			return new ModelAndView("subStore/reports/cnPdSSReturnStoreTicketReport",
					model);
		} else {
			return new ModelAndView("subStore/reports/cnPdSsStoreTicketReportRS",
					model);
		}
	}

	// return
	// "redirect:/ss/lsSsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/lsSsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsSsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"subStore/reports/lsSsStoreTicketReportRequisition", model);
	}

	// return
	// "redirect:/ss/cnPdSsStoreTicketReportRequisition.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/cnPdSsStoreTicketReportRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnPdSsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"subStore/reports/cnPdSsStoreTicketReportRequisition", model);
	}

	@SuppressWarnings("unchecked")
	private List<String> saveMultipleGP(String userName,
			Departments department, SubStoreRequisitionMst ssReqDb,
			SSStoreTicketMst ssStoreTicketMst) {
		List<String> gPNoList = new ArrayList<String>();
		String uuid = ssReqDb.getUuid();
		List<TempItemLocation> tpList = (List<TempItemLocation>) (Object) commonService
				.getObjectListByAnyColumn("TempItemLocation", "uuid", uuid);

		Set<String> locationIdList = new HashSet<String>();
		for (TempItemLocation t : tpList) {
			locationIdList.add(t.getLocationId());
		}
		// GP MST info
		String descoDeptCode = department.getDescoCode();
		SSGatePassMst ssGatePassMst = new SSGatePassMst();
		ssGatePassMst.setGatePassDate(new Date());
		ssGatePassMst.setIssuedTo(ssReqDb.getIdenterDesignation());
		ssGatePassMst.setIssuedBy(commonService.getAuthUserName());
		ssGatePassMst.setReceiverName(ssReqDb.getReceivedBy());
		ssGatePassMst.setFlag(true);
		ssGatePassMst.setActive(true);
		ssGatePassMst.setCreatedBy(userName);
		ssGatePassMst.setCreatedDate(new Date());
		ssGatePassMst.setRequisitonNo(ssReqDb.getRequisitionNo());
		ssGatePassMst.setTicketNo(ssStoreTicketMst.getTicketNo());
		ssGatePassMst.setWorkOrderNo(ssStoreTicketMst.getWorkOrderNo());
		// now create unique location wise gate pass
		for (String locId : locationIdList) {
			List<TempItemLocation> gpItemList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"locationId", locId);

			String gatePassNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoGatePassNoPrefix, descoDeptCode, separator,
							"GATE_PASS_SEQ");
			// Save SS GP Mst
			ssGatePassMst.setGatePassNo(gatePassNo);
			ssGatePassMst.setLocationId(locId);
			ssGatePassMst.setId(null);
			ssGatePassMst.setLocationName(gpItemList.get(0).getLocationName());
			commonService.saveOrUpdateModelObjectToDB(ssGatePassMst);

			// create GP DTL and Savt to DB
			for (TempItemLocation gpDtl : gpItemList) {

				SSGatePassMst gpMstDB = (SSGatePassMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"SSGatePassMst",
								"id",
								commonService.getMaxValueByObjectAndColumn(
										"SSGatePassMst", "id").toString());
				ItemMaster item = (ItemMaster) commonService
						.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
								gpDtl.getItemCode());
				SSGatePassDtl ssGPdtl = new SSGatePassDtl();
				ssGPdtl.setSsGatePassMst(gpMstDB);
				ssGPdtl.setGatePassNo(gpMstDB.getGatePassNo());
				ssGPdtl.setItemCode(item.getItemId());
				ssGPdtl.setDescription(item.getItemName());
				ssGPdtl.setUom(item.getUnitCode());
				ssGPdtl.setQuantity(gpDtl.getQuantity());
				ssGPdtl.setCreatedBy(userName);
				ssGPdtl.setId(null);
				ssGPdtl.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(ssGPdtl);
			}
			gPNoList.add(gatePassNo);
		}
		// added by Abu Taleb
		commonService.deleteAnObjectListByAnyColumn("TempItemLocation", "uuid",
				uuid);
		return gPNoList;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void ssLedgerLocation(List<SSStoreTicketDtl> csStoreTicketDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, SSStoreTicketMst csStoreTicketMst) {

		String uuid = "";

		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			SSItemTransactionMst csItemTransactionMst = null;
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
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_SS_REQUISITION)) {

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
						LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_SS_RETURN)) {
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
				csItemTransactionMst = new SSItemTransactionMst();
				csItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_SS_REQUISITION)) {

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
						LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_SS_RETURN)) {
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
					.getMaxValueByObjectAndColumn("SSItemTransactionMst", "id");
			SSItemTransactionMst csItemTransactionMstLastRow = (SSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "id",
							maxTnxMstId + "");

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
			if (csItemTransactionMstdb != null) {
				csItemTransactionDtl
						.setSsItemTransactionMst(csItemTransactionMstdb);
			} else {
				csItemTransactionDtl
						.setSsItemTransactionMst(csItemTransactionMstLastRow);
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

			if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_SS_REQUISITION)) {
				csItemTransactionDtl.setTnxnMode(false);
			} else {
				csItemTransactionDtl.setTnxnMode(true);
			}

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

			Integer maxTnxDtlId = (Integer) commonService
					.getMaxValueByObjectAndColumn("SSItemTransactionDtl", "id");
			SSItemTransactionDtl csItemTransactionDtlTemp = (SSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionDtl", "id",
							maxTnxDtlId + "");

			// Location Update
			// Location Mst

			// added by taleb || check requisition or return and get data from
			// related model
			List<TempItemLocation> tempLocationList = null;

			if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_PD_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(
							LS_PD_SS_REQUISITION)) {
				SubStoreRequisitionMst csReqMst = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"requisitionNo",
								csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								csReqMst.getUuid(), "itemCode",
								csStoreTicketDtl.getItemId());
				uuid = csReqMst.getUuid();

			} else if (csStoreTicketMst.getStoreTicketType().equals(
					LS_SS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
							CN_SS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_RETURN)) {
				SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
								"returnSlipNo",
								csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								returnSlipMst.getUuid(), "itemCode",
								csStoreTicketDtl.getItemId());
				uuid = returnSlipMst.getUuid();
			}
			// end of block

			for (TempItemLocation tempLoc : tempLocationList) {

				SSItemLocationMst csItemLocationMstdb = ssItemLocationMstService
						.getSSItemLocationMst(tempLoc.getLocationId(),
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
							LS_SS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_SS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(
									LS_PD_SS_REQUISITION)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() - tempLoc.getQuantity());
					} else if (csStoreTicketMst.getStoreTicketType().equals(
							LS_SS_RETURN_SLIP)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_SS_RETURN_SLIP)
							|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_RETURN)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() + tempLoc.getQuantity());
					}
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					SSItemLocationMst csItemLocationMst = new SSItemLocationMst();
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
						.getMaxValueByObjectAndColumn("SSItemLocationMst", "id");
				SSItemLocationMst csItemLocationMstTemp = (SSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("SSItemLocationMst",
								"id", maxLocMstId + "");

				// Location DTL
				SSItemLocationDtl csItemLocationDtl = new SSItemLocationDtl();
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
						LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_PD_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_SS_REQUISITION)) {
					csItemLocationDtl.setTnxnMode(false);
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
									CN_PD_SS_RETURN)) {
					csItemLocationDtl.setTnxnMode(true);
				}

				// set Item Location Mst Id
				if (csItemLocationMstdb != null) {
					csItemLocationDtl.setLedgerName(csItemLocationMstdb.getLedgerType());
					csItemLocationDtl.setSsItemLocationMst(csItemLocationMstdb);
				} else {
					csItemLocationDtl.setLedgerName(csItemLocationMstTemp.getLedgerType());
					csItemLocationDtl
							.setSsItemLocationMst(csItemLocationMstTemp);
				}

				// set Transaction dtl info for get khathwise and location wise
				// item report
				csItemLocationDtl
						.setSsItemTransactionDtl(csItemTransactionDtlTemp);

				csItemLocationDtl.setStoreLocation(storeLocations);

				commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);
			}
		}

		// delete all row from TempItemLocation by uuid || added by Ashid
		// removed by Taleb and send to after store Ticket approved or generated
		/*
		 * commonService.deleteAnObjectListByAnyColumn("TempItemLocation",
		 * "uuid", uuid);
		 */

		if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)) {
			this.lsToSsLedgerUpdateForReturn(csStoreTicketDtlList, userName,
					roleName, authUser, dept, csStoreTicketMst);
		}
		if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
			this.cnToSsLedgerUpdateForReq(csStoreTicketDtlList, userName,
					roleName, authUser, dept, csStoreTicketMst);
		}
		
		// removed by taleb
		/*if (csStoreTicketMst.getStoreTicketType().equals(CN_PD_SS_REQUISITION)) {
			this.cnToSsLedgerUpdateForReq(csStoreTicketDtlList, userName,
					roleName, authUser, dept, csStoreTicketMst);
		}*/
		
		
		if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
			this.cnToSsLedgerUpdateForReturn(csStoreTicketDtlList, userName,
					roleName, authUser, dept, csStoreTicketMst);
		}
		
		// removed by taleb
		/*if (csStoreTicketMst.getStoreTicketType().equals(CN_PD_SS_RETURN)) {
			this.cnToSsLedgerUpdateForReturn(csStoreTicketDtlList, userName,
					roleName, authUser, dept, csStoreTicketMst);
		}*/
	}

	private void cnToSsLedgerUpdateForReq(
			List<SSStoreTicketDtl> csStoreTicketDtlList, String userName,
			String roleName, AuthUser authUser, Departments dept,
			SSStoreTicketMst csStoreTicketMst) {
		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			CnPdRequisitionMst cnpdReqMst = (CnPdRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CnPdRequisitionMst",
							"requisitionNo", csStoreTicketMst.getOperationId());
			/*
			 * SubStoreRequisitionMst ssm = subStoreRequisitionMstService
			 * .getSubStoreRequistionMstByRequisitionNo(csStoreTicketMst
			 * .getOperationId());
			 */
			ContractorRepresentive cr = contractorRepresentiveService
					.getContractorRep(cnpdReqMst.getCreatedBy());
			PndJobDtl pj = pndJobDtlService.getPndJobDtl(
					csStoreTicketDtl.getItemId(), cr.getContractNo());
			pj.setRemainningQuantity(pj.getRemainningQuantity()
					- csStoreTicketDtl.getQuantity());
			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * csStoreTicketMst.getKhathId() + "");
			 */
			commonService.saveOrUpdateModelObjectToDB(pj);

		}
		// SSItemTransactionMst csItemTransactionMst = null;

	}

	private void cnToSsLedgerUpdateForReturn(
			List<SSStoreTicketDtl> csStoreTicketDtlList, String userName,
			String roleName, AuthUser authUser, Departments dept,
			SSStoreTicketMst csStoreTicketMst) {
		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			SSReturnSlipMst returnSlipMst = ssReturnSlipMstService
					.getSSReturnSlipMstByRSNo(csStoreTicketMst.getOperationId());
			ContractorRepresentive cr = contractorRepresentiveService
					.getContractorRep(returnSlipMst.getCreatedBy());
			PndJobDtl pj = pndJobDtlService.getPndJobDtl(
					csStoreTicketDtl.getItemId(), cr.getContractNo());
			pj.setRemainningQuantity(pj.getRemainningQuantity()
					+ csStoreTicketDtl.getQuantity());
			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * csStoreTicketMst.getKhathId() + "");
			 */
			commonService.saveOrUpdateModelObjectToDB(pj);

		}
		// SSItemTransactionMst csItemTransactionMst = null;

	}

	@SuppressWarnings("unchecked")
	private void lsToSsLedgerUpdateForReturn(
			List<SSStoreTicketDtl> csStoreTicketDtlList, String userName,
			String roleName, AuthUser authUser, Departments dept,
			SSStoreTicketMst csStoreTicketMst) {
		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			// SSItemTransactionMst csItemTransactionMst = null;
			/*
			 * LSItemTransactionMst csItemTransactionMstdb =
			 * lsItemTransactionMstService
			 * .getLSItemTransectionMst(csStoreTicketDtl.getItemId(),
			 * csStoreTicketMst.getKhathId(), csStoreTicketDtl.getLedgerName());
			 */

			List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"itemCode", csStoreTicketDtl.getItemId(),
							"khathId", csStoreTicketMst.getKhathId() + "",
							"ledgerName", csStoreTicketDtl.getLedgerName(),
							"sndCode", csStoreTicketMst.getSndCode());

			LSItemTransactionMst lsItemTransactionMstdb = null;

			if (lsItemTransactionMstList.size() > 0) {
				lsItemTransactionMstdb = lsItemTransactionMstList.get(0);
			}

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");
			// Ledger Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionMstdb.setCreatedDate(new Date());
				lsItemTransactionMstdb.setCreatedBy(userName);// for only
																// now....addede
																// by nasrin
				lsItemTransactionMstdb.setModifiedBy(userName);
				lsItemTransactionMstdb.setModifiedDate(new Date());

				lsItemTransactionMstdb.setQuantity(lsItemTransactionMstdb
						.getQuantity() - csStoreTicketDtl.getQuantity());
				commonService
						.saveOrUpdateModelObjectToDB(lsItemTransactionMstdb);

			} else {
				continue;
			}
			// Ledger Dtl
			LSItemTransactionDtl csItemTransactionDtl = new LSItemTransactionDtl();
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
					.setLsItemTransactionMst(lsItemTransactionMstdb);

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
			csItemTransactionDtl.setTnxnMode(false);
			// Ledger Quantity Info
			csItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

		}

	}

	@RequestMapping(value = "/itemEdit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String itemDtlEdit(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {

			CentralStoreRequisitionDtl item = gson.fromJson(json,
					CentralStoreRequisitionDtl.class);

			CentralStoreRequisitionDtl selectItemsFromDb = centralStoreRequisitionDtlService
					.getCentralStoreRequisitionDtl(item.getId());
			// selectItemsFromDb.setCentralStoreRequisitionId(selectItemsFromDb.getCentralStoreRequisitionMst().getId());
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
			@ModelAttribute("csItemDtl") CentralStoreRequisitionDtl csItemDtl) {

		// get Current User and Role Information

		CentralStoreRequisitionDtl csProcItemRcvDtldb = centralStoreRequisitionDtlService
				.getCentralStoreRequisitionDtl(csItemDtl.getId());
		csProcItemRcvDtldb.setQuantityRequired(csItemDtl.getQuantityRequired());
		csProcItemRcvDtldb.setTotalCost(csProcItemRcvDtldb.getUnitCost()
				* csProcItemRcvDtldb.getQuantityRequired());
		csProcItemRcvDtldb.setModifiedBy(commonService.getAuthUserName());
		csProcItemRcvDtldb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(csProcItemRcvDtldb);
		// centralStoreRequisitionDtlService.addCentralStoreRequisition(csProcItemRcvDtldb);

		return "redirect:/ss/storeRequisitionShow.do?id="
				+ csProcItemRcvDtldb.getId()
				+ "&requisitionTo="
				+ csProcItemRcvDtldb.getCentralStoreRequisitionMst()
						.getRequisitionTo();
		/*
		 * +"&requisitionNo=" +csProcItemRcvDtldb.getRequisitionNo();
		 */
	}

	@RequestMapping(value = "/itemEditToSub.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String itemEditToSub(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {

			SubStoreRequisitionDtl item = gson.fromJson(json,
					SubStoreRequisitionDtl.class);

			SubStoreRequisitionDtl selectItemsFromDb = subStoreRequisitionDtlService
					.getSubStoreRequisitionDtl(item.getId());
			// selectItemsFromDb.setCentralStoreRequisitionId(selectItemsFromDb.getCentralStoreRequisitionMst().getId());
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/updateItemToSub.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String updateItemToSub(
			@ModelAttribute("csItemDtl") SubStoreRequisitionDtl csItemDtl) {

		SubStoreRequisitionDtl csProcItemRcvDtldb = subStoreRequisitionDtlService
				.getSubStoreRequisitionDtl(csItemDtl.getId());
		csProcItemRcvDtldb.setQuantityRequired(csItemDtl.getQuantityRequired());
		csProcItemRcvDtldb.setTotalCost(csProcItemRcvDtldb.getUnitCost()
				* csProcItemRcvDtldb.getQuantityRequired());
		csProcItemRcvDtldb.setModifiedBy(commonService.getAuthUserName());
		csProcItemRcvDtldb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(csProcItemRcvDtldb);
		// centralStoreRequisitionDtlService.addCentralStoreRequisition(csProcItemRcvDtldb);

		return "redirect:/ls/storeRequisitionShowOfLocalToSub.do?id="
				+ csProcItemRcvDtldb.getId()
				+ "&requisitionTo="
				+ csProcItemRcvDtldb.getSubStoreRequisitionMst()
						.getRequisitionTo();
		/*
		 * +"&requisitionNo=" +csProcItemRcvDtldb.getRequisitionNo();
		 */
	}

	@SuppressWarnings("unchecked")
	public List<StoreLocations> getSSStoreLocationList(String storeCode) {

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
	@RequestMapping(value = "/setSSRcvedLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String setSSRcvedLocation(@RequestBody String locationQtyJsonString)
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
	@RequestMapping(value = "/getSSTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getSSTotalQtyByUuidAndItemCode(@RequestBody String cData)
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
	@RequestMapping(value = "/getSSTemplocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getSSTemplocation(@RequestBody String cData) throws Exception {
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
	@RequestMapping(value = "/updateSSLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateSSLocationQty(@RequestBody String cData)
			throws Exception {
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
	/*
	 * @RequestMapping(value = "/updateRRdtlAfterLocatinUpdate.do", method =
	 * RequestMethod.POST)
	 * 
	 * @PreAuthorize("isAuthenticated()")
	 * 
	 * @ResponseBody public String updateRRdtlAfterLocatinUpdate(@RequestBody
	 * String cData) throws Exception { Gson gson = new GsonBuilder().create();
	 * Boolean isJson = commonService.isJSONValid(cData); String toJson = ""; if
	 * (isJson) { CSProcItemRcvDtl tpDtl = gson.fromJson(cData,
	 * CSProcItemRcvDtl.class); String rrNo = tpDtl.getRrNo(); String itemId =
	 * tpDtl.getItemId(); double receivedQty = tpDtl.getReceivedQty();
	 * List<Object> obList = commonService.getObjectListByTwoColumn(
	 * "CSProcItemRcvDtl", "rrNo", rrNo, "itemId", itemId); CSProcItemRcvDtl
	 * cSProcItemRcvDtlDB = new CSProcItemRcvDtl();
	 * System.out.println(cSProcItemRcvDtlDB.getReceivedQty()); if
	 * (obList.size() > 0) { cSProcItemRcvDtlDB = (CSProcItemRcvDtl)
	 * obList.get(0); } if (cSProcItemRcvDtlDB.getReceivedQty() != 0.0) {
	 * cSProcItemRcvDtlDB.setReceivedQty(receivedQty);
	 * cSProcItemRcvDtlDB.setModifiedBy(commonService .getAuthUserName());
	 * cSProcItemRcvDtlDB.setModifiedDate(new Date()); }
	 * commonService.saveOrUpdateModelObjectToDB(cSProcItemRcvDtlDB);
	 * 
	 * ObjectWriter ow = new ObjectMapper().writer()
	 * .withDefaultPrettyPrinter(); toJson =
	 * ow.writeValueAsString("Qantity updated Successfully."); } else {
	 * ObjectWriter ow = new ObjectMapper().writer()
	 * .withDefaultPrettyPrinter(); toJson = ow
	 * .writeValueAsString("Error from updateRRdtlAfterLocatinUpdate.do"); }
	 * return toJson; }
	 */

	// Added by Ashid
	@RequestMapping(value = "/deleteSSLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String deleteSSLocationQty(@RequestBody String cData)
			throws Exception {
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

	//

	@RequestMapping(value = "/gatePassGenerate.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView gatePassGenerate(SSGatePassMst gatePassMst) {

		SSGatePassMst ssGatePassMst = (SSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("SSGatePassMst", "gatePassNo",
						gatePassMst.getGatePassNo());

		SSStoreTicketMst ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo",
						ssGatePassMst.getTicketNo());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gatePassNo", gatePassMst.getGatePassNo());
		if (ssStoreTicketMst.getStoreTicketType().equalsIgnoreCase(
				CN_PD_SS_REQUISITION)) {
			return new ModelAndView("subStore/reports/ssGatePassReport", model);
		} else {
			return new ModelAndView("subStore/reports/ssGatePassReport", model);
		}
	}

	// Delete item by id from show page :: Ihteshamul Alam
	@RequestMapping(value = "/deleteItemById.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteItemById(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Integer id = csRequisitionMstDtl.getId();
			String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
			CentralStoreRequisitionMst centralStoreRequisitionMst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.cs.model.CentralStoreRequisitionMst",
							"requisitionNo", requisitionNo);
			commonService.deleteAnObjectById("CentralStoreRequisitionDtl", id);

			// Redirect to Show page
			CentralStoreRequisitionMst csRequisitionMst = new CentralStoreRequisitionMst();
			csRequisitionMst.setId(centralStoreRequisitionMst.getId());
			csRequisitionMst.setRequisitionNo(requisitionNo);
			csRequisitionMst.setRequisitionTo("cs");
			return this.getStoreRequisitionShow(csRequisitionMst);

		} catch (Exception E) {
			E.printStackTrace();
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("subStore/errorSS", model);
		}
	}

	// Add multiple items from Show page - Ihteshamul Alam
	@RequestMapping(value = "/storeRequisitionSaveMultipleItem", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSaveMultipleItem(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
			CentralStoreRequisitionMst centralStoreRequisitionMst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.cs.model.CentralStoreRequisitionMst",
							"requisitionNo", requisitionNo);

			List<String> itemCodeList = csRequisitionMstDtl.getItemCode();
			List<String> itemNameList = csRequisitionMstDtl.getItemName();
			List<String> uomList = csRequisitionMstDtl.getUom();
			List<Double> quantityRequiredList = csRequisitionMstDtl
					.getQuantityRequired();
			List<String> remarksList = csRequisitionMstDtl.getRemarks();

			String username = commonService.getAuthUserName();
			Date now = new Date();

			for (int i = 0; i < itemCodeList.size(); i++) {
				CentralStoreRequisitionDtl centralStoreRequisitionDtl = new CentralStoreRequisitionDtl();

				centralStoreRequisitionDtl.setItemCode(itemCodeList.get(i));
				centralStoreRequisitionDtl.setItemName(itemNameList.get(i));
				centralStoreRequisitionDtl.setUom(uomList.get(i));
				centralStoreRequisitionDtl
						.setQuantityRequired(quantityRequiredList.get(i));
				centralStoreRequisitionDtl.setRemarks(remarksList.get(i));
				centralStoreRequisitionDtl.setId(null);
				centralStoreRequisitionDtl.setCreatedBy(username);
				centralStoreRequisitionDtl.setCreatedDate(now);
				centralStoreRequisitionDtl.setRequisitionNo(requisitionNo);
				centralStoreRequisitionDtl.setActive(true);
				centralStoreRequisitionDtl
						.setCentralStoreRequisitionMst(centralStoreRequisitionMst);

				commonService
						.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
			}

			// Redirect to Show page
			CentralStoreRequisitionMst csRequisitionMst = new CentralStoreRequisitionMst();
			csRequisitionMst.setId(centralStoreRequisitionMst.getId());
			csRequisitionMst.setRequisitionNo(requisitionNo);
			csRequisitionMst.setRequisitionTo("cs");
			return this.getStoreRequisitionShow(csRequisitionMst);
		} catch (Exception E) {
			E.printStackTrace();
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("subStore/errorSS", model);
		}
	}
}
