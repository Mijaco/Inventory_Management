package com.ibcs.desco.localStore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LsPdCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * 
 */
@Controller
@PropertySource("classpath:common.properties")
public class ProjectRequisitionController extends Constrants {

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	UserService userService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/requisitionForm.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	public ModelAndView pdRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<ItemCategory> categoryList = itemGroupService
					.getConstructionItemGroups();

			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			model.put("categoryList", categoryList);
			model.put("department", department);
			model.put("descoKhathList", descoKhathList);

			return new ModelAndView("localStore/lsToPdStoreRequisitionForm",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	@RequestMapping(value = "/ls/pd/saveRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSave(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csRequisitionMstDtl.getKhathId().toString());

			if (csRequisitionMstDtl.getReceivedBy().equals("")) {
				String msg = "Receiver Name can not be null";
				model.put("errorMsg", msg);
				return new ModelAndView("redirect:/ls/pd/requisitionForm.do");
			}

			if (csRequisitionMstDtl.getRequisitionTo().equalsIgnoreCase(
					ContentType.SUB_STORE.toString())) {
				SubStoreRequisitionMstDtl saveSubStoreData = importObj(
						csRequisitionMstDtl, descoKhath, department);
				return saveSubStoreData(saveSubStoreData);
			}
			CentralStoreRequisitionMst csRequisitionMst = new CentralStoreRequisitionMst();
			csRequisitionMst.setIdenterDesignation(csRequisitionMstDtl
					.getIdenterDesignation());
			csRequisitionMst.setDeptName(department.getDeptName());
			csRequisitionMst.setReceivedBy(csRequisitionMstDtl.getReceivedBy());
			csRequisitionMst.setKhathId(descoKhath.getId());
			csRequisitionMst.setKhathName(descoKhath.getKhathName());
			csRequisitionMst.setUuid(UUID.randomUUID().toString());
			csRequisitionMst.setRequisitionDate(new Date());
			csRequisitionMst.setActive(csRequisitionMstDtl.isActive());
			csRequisitionMst.setCreatedBy(userName);
			csRequisitionMst.setCreatedDate(new Date());
			csRequisitionMst.setReceived(false);
			csRequisitionMst.setRequisitionTo(csRequisitionMstDtl
					.getRequisitionTo());
			csRequisitionMst.setSenderStore(ContentType.LOCAL_STORE.toString());
			csRequisitionMst.setSndCode(department.getDescoCode());
			csRequisitionMst.setCarriedBy(csRequisitionMstDtl.getCarriedBy());

			boolean successFlag = true;
			String msg = "";
			// Save requisition mst and dtl info to db
			successFlag = addStoreRequisitionDtls(csRequisitionMstDtl,
					csRequisitionMst, roleName, department, authUser,
					new Date());

			model.put("centralStoreRequisitionMst", csRequisitionMst);
			if (successFlag) {
				// must set a get method for show.
				// return getStoreRequisitionShow(csRequisitionMst);
				return new ModelAndView(
						"redirect:/ls/pd/requisitionShow.do?id="
								+ csRequisitionMst.getId() + "&requisitionTo="
								+ csRequisitionMst.getRequisitionTo());

			} else {
				msg = "Sorry! You have no permission to do this operation. Try again.";
				model.put("errorMsg", msg);
				return new ModelAndView("localStore/errorLS", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}

	}

	public boolean addStoreRequisitionDtls(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			CentralStoreRequisitionMst csRequisitionMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		List<String> remarksList = null;

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

		if (csRequisitionMstDtl.getRemarks() != null) {
			remarksList = csRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_PD_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_PD_CS_REQUISITION);

		/*
		 * Get all State code which for local to LS_PD_CS_REQUISITION
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
							LS_PD_CS_REQUISITION, stateCodes[0].toString());
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
								separator, "LS_CS_REQ_SEQ");
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

					csRequisitionDtl.setQuantityIssued(0.0);

					if (!remarksList.isEmpty()) {
						csRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						csRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					csRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					csRequisitionDtl.setRequisitionNo(csRequisitionMst
							.getRequisitionNo());
					csRequisitionDtl.setCreatedBy(csRequisitionMst
							.getCreatedBy());
					csRequisitionDtl.setCreatedDate(now);
					csRequisitionDtl.setActive(true);
					csRequisitionDtl
							.setCentralStoreRequisitionMst(csRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);

				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(csRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}

	@RequestMapping(value = "/ls/pd/requisitionShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showStoreRequisitionPOST(CentralStoreRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return showStoreRequisitionGET(bean);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/requisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showStoreRequisitionGET(CentralStoreRequisitionMst bean)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String requisitionTo = bean.getRequisitionTo();
			String mstId = bean.getId().toString();

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			if (requisitionTo
					.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {

				SubStoreRequisitionMst subStoreRequisitionMstdb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"id", mstId);

				return getStoreRequisitionShowOfLocalToSub(subStoreRequisitionMstdb);
				//eturn null;

			}
			CentralStoreRequisitionMst csRequisitionMstdb = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
							"id", mstId);
			// Set full name of Requisition Creator
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							csRequisitionMstdb.getCreatedBy());
			csRequisitionMstdb.setCreatedBy(createBy.getName());

			Integer khathId = csRequisitionMstdb.getKhathId();
			String operationId = csRequisitionMstdb.getRequisitionNo();

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", operationId);
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
								khathId.toString(), "ledgerName",
								NEW_SERVICEABLE);

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
			String currentStatus = "";

			List<LsPdCsRequisitionApprovalHierarchyHistory> historyList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"LsPdCsRequisitionApprovalHierarchyHistory",
							LS_PD_CS_REQUISITION, operationId, DONE);

			if (!historyList.isEmpty()) {
				currentStatus = historyList.get(historyList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			List<LsPdCsRequisitionApprovalHierarchyHistory> historyOpenList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"LsPdCsRequisitionApprovalHierarchyHistory",
							LS_PD_CS_REQUISITION, operationId, OPEN);

			int currentStateCode = historyOpenList.get(
					historyOpenList.size() - 1).getStateCode();

			// role id list From Auth_User by dept Id
			List<AuthUser> userList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "deptId",
							deptId);
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
							LS_PD_CS_REQUISITION, roleNameList);
			/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

			// Send To Upper Authority of same department
			List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

			for (int countStateCodes = 0; countStateCodes < approveHeirchyList
					.size(); countStateCodes++) {
				if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
					nextManReqProcs
							.add(approveHeirchyList.get(countStateCodes));
				}
			}

			// Back To User as my wish
			List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
			for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
					.size(); countBackStateCodes++) {
				if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
					backManRcvProcs.add(approveHeirchyList
							.get(countBackStateCodes));
				}
			}

			String returnStateCode = "";
			// button Name define
			if (!historyOpenList.isEmpty() && historyOpenList != null) {
				// get current state code
				int stateCode = historyOpenList.get(historyOpenList.size() - 1)
						.getStateCode();
				// decide for return or not
				returnStateCode = historyOpenList.get(
						historyOpenList.size() - 1).getReturn_state();
				// get next approval heirarchy
				ApprovalHierarchy approveHeirarchy = null;
				{
					approveHeirarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_PD_CS_REQUISITION, stateCode + "");
					buttonValue = approveHeirarchy.getButtonName();
				}

			}

			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();

			model.put("returnStateCode", returnStateCode);
			model.put("centralStoreRequisitionMst", csRequisitionMstdb);
			model.put("currentStatus", currentStatus);
			model.put("buttonValue", buttonValue);
			model.put("nextManRcvProcs", nextManReqProcs);
			model.put("backManRcvProcs", backManRcvProcs);
			model.put("requisitionTo", bean.getRequisitionTo());
			model.put("reqTo", ContentType.CENTRAL_STORE.toString());
			model.put("itemRcvApproveHistoryList", historyList);
			model.put("centralStoreRequisitionDtlList",
					centralStoreRequisitionDtlList);

			model.put("categoryList", categoryList);
			model.put("department", department);

			DescoKhath dsKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							khathId.toString());
			model.put("dsKhath", dsKhath);

			// Start m@@ || Generate drop down list for location in Grid
			List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationOptions = commonService
					.getLocationListForGrid(locationsList);
			String ledgerBookList = commonService.getLedgerListForGrid();
			model.put("locationList", locationOptions);
			model.put("ledgerBookList", ledgerBookList);

			// End m@@ || Generate drop down list for location in Grid }

			String rolePrefix = roleName.substring(0, 7);
			if (rolePrefix.equals("ROLE_LS")) {
				return new ModelAndView("localStore/lsPdRequisitionShow", model);
			} else if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
				return new ModelAndView("localStore/lsPdRequisitionShowPD",
						model);
			} else {
				return new ModelAndView("localStore/lsPdRequisitionShowCS",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}

	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView getStoreRequisitionShowOfLocalToSub(SubStoreRequisitionMst subStoreRequisitionMstdb) {
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		SubStoreRequisitionMst ssRequisitionMstdb = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"id", subStoreRequisitionMstdb.getId().toString());
		// Set full name of Requisition Creator
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						subStoreRequisitionMstdb.getCreatedBy());
		subStoreRequisitionMstdb.setCreatedBy(createBy.getName());

		Integer khathId = subStoreRequisitionMstdb.getKhathId();
		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", operationId);
		// This Block will set Curent stock of CS
		for (int i = 0; i < subStoreRequisitionDtlList.size(); i++) {
			String itemCode = subStoreRequisitionDtlList.get(i)
					.getItemCode();
			List<SSItemTransactionMst> ssItemTnxMstRecovery = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("SSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName",
							RECOVERY_SERVICEABLE);

			List<SSItemTransactionMst> ssItemTnxMstNew = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("SSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName",
							NEW_SERVICEABLE);

			if (ssItemTnxMstRecovery.size() > 0) {
				subStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(
								ssItemTnxMstRecovery.get(0).getQuantity());
			} else {
				subStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(0.0);
			}

			if (ssItemTnxMstNew.size() > 0) {
				subStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(
								ssItemTnxMstNew.get(0).getQuantity());
			} else {
				subStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(0.0);
			}
		}

		String buttonValue = null;
		String currentStatus = "";

		List<LsPdSsRequisitionApprovalHierarchyHistory> historyList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsPdSsRequisitionApprovalHierarchyHistory",
						LS_PD_SS_REQUISITION, operationId, DONE);

		if (!historyList.isEmpty()) {
			currentStatus = historyList.get(historyList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsPdSsRequisitionApprovalHierarchyHistory> historyOpenList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsPdSsRequisitionApprovalHierarchyHistory",
						LS_PD_SS_REQUISITION, operationId, OPEN);

		int currentStateCode = historyOpenList.get(
				historyOpenList.size() - 1).getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId",
						deptId);
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
						LS_PD_SS_REQUISITION, roleNameList);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs
						.add(approveHeirchyList.get(countStateCodes));
			}
		}

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs.add(approveHeirchyList
						.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!historyOpenList.isEmpty() && historyOpenList != null) {
			// get current state code
			int stateCode = historyOpenList.get(historyOpenList.size() - 1)
					.getStateCode();
			// decide for return or not
			returnStateCode = historyOpenList.get(
					historyOpenList.size() - 1).getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								LS_PD_SS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		List<ItemCategory> categoryList = itemGroupService
				.getAllItemGroups();

		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", ssRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("requisitionTo", subStoreRequisitionMstdb.getRequisitionTo());

		model.put("reqTo", ContentType.SUB_STORE.toString());
		model.put("itemRcvApproveHistoryList", historyList);
		model.put("centralStoreRequisitionDtlList",
				subStoreRequisitionDtlList);

		model.put("categoryList", categoryList);
		model.put("department", department);

		DescoKhath dsKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						khathId.toString());
		model.put("dsKhath", dsKhath);

		// Start m@@ || Generate drop down list for location in Grid
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "SS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);

		// End m@@ || Generate drop down list for location in Grid }
		
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsPdRequisitionShow", model);
		} else if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
			return new ModelAndView("localStore/lsPdRequisitionShowPD",
					model);
		} else {
			return new ModelAndView("localStore/lsPdRequisitionShowSS",
					model);
		}
	}

	public void addStoreRequisitionHierarchyHistory(
			CentralStoreRequisitionMst csRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {

		LsPdCsRequisitionApprovalHierarchyHistory history = new LsPdCsRequisitionApprovalHierarchyHistory();
		history.setActRoleName(roleName);
		history.setcDeptName(department.getDeptName());
		history.setDeptId(department.getDeptId());
		history.setcEmpFullName(authUser.getName());
		history.setcEmpId(authUser.getEmpId());
		history.setcDesignation(authUser.getDesignation());
		history.setApprovalHeader(approvalHierarchy.getApprovalHeader());
		history.setOperationId(csRequisitionMst.getRequisitionNo());
		history.setOperationName(LS_PD_CS_REQUISITION);
		history.setCreatedBy(csRequisitionMst.getCreatedBy());
		history.setCreatedDate(now);
		history.setStatus(OPEN);
		history.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st state code
			history.setStateCode(stateCodes[0]);
			history.setStateName(approvalHierarchy.getStateName());
		}

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(history);

	}

	private SubStoreRequisitionMstDtl importObj(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			DescoKhath descoKhath, Departments department) {
		SubStoreRequisitionMstDtl ss = new SubStoreRequisitionMstDtl();
		ss.setId(csRequisitionMstDtl.getId());
		ss.setRequisitionTo(csRequisitionMstDtl.getRequisitionTo());
		ss.setIdenterDesignation(csRequisitionMstDtl.getIdenterDesignation());
		ss.setCreatedBy(csRequisitionMstDtl.getCreatedBy());
		ss.setReceivedBy(csRequisitionMstDtl.getReceivedBy());
		ss.setRequisitionNo(csRequisitionMstDtl.getRequisitionNo());
		ss.setRequisitionDate(csRequisitionMstDtl.getRequisitionDate());
		ss.setStoreTicketNO(csRequisitionMstDtl.getStoreTicketNO());
		ss.setGatePassNo(csRequisitionMstDtl.getGatePassNo());
		ss.setGatePassDate(csRequisitionMstDtl.getGatePassDate());
		ss.setItemName(csRequisitionMstDtl.getItemName());
		ss.setItemCode(csRequisitionMstDtl.getItemCode());
		ss.setUom(csRequisitionMstDtl.getUom());
		ss.setQuantityRequired(csRequisitionMstDtl.getQuantityRequired());
		ss.setQuantityIssued(csRequisitionMstDtl.getQuantityIssued());
		ss.setRemarks(csRequisitionMstDtl.getRemarks());
		ss.setIssueqty(csRequisitionMstDtl.getIssueqty());
		ss.setSenderStore(csRequisitionMstDtl.getSenderStore());
		ss.setKhathId(descoKhath.getId());
		ss.setKhathName(descoKhath.getKhathName());
		ss.setCarriedBy(csRequisitionMstDtl.getCarriedBy());
		ss.setSndCode(department.getDescoCode());
		ss.setDeptName(department.getDeptName());
		// ss.setLedgerName(csRequisitionMstDtl.getLedgerName());
		return ss;
	}

	public ModelAndView saveSubStoreData(
			SubStoreRequisitionMstDtl ssRequisitionMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * ssRequisitionMstDtl.getKhathId() + "");
			 */

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							ssRequisitionMstDtl.getKhathId().toString());

			SubStoreRequisitionMst ssRequisitionMst = new SubStoreRequisitionMst();
			ssRequisitionMst.setIdenterDesignation(ssRequisitionMstDtl
					.getIdenterDesignation());
			ssRequisitionMst.setDeptName(department.getDeptName());			
			ssRequisitionMst.setReceivedBy(ssRequisitionMstDtl.getReceivedBy());
			
			
			ssRequisitionMst.setKhathId(descoKhath.getId());
			ssRequisitionMst.setKhathName(descoKhath.getKhathName());
			ssRequisitionMst.setUuid(UUID.randomUUID().toString());
			// set current date time as RequisitionDate. GUI date is not used
			// here.
			ssRequisitionMst.setRequisitionDate(new Date());
			ssRequisitionMst.setActive(ssRequisitionMstDtl.isActive());
			ssRequisitionMst.setCreatedBy(userName);
			
			ssRequisitionMst.setCreatedDate(new Date());
			ssRequisitionMst.setRequisitionTo(ssRequisitionMstDtl
					.getRequisitionTo());
			
			ssRequisitionMst.setSenderStore(ContentType.LOCAL_STORE.toString());			
			ssRequisitionMst.setSndCode(department.getDescoCode());	
			ssRequisitionMst.setCarriedBy(ssRequisitionMstDtl.getCarriedBy());
			
			boolean successFlag = true;
			String msg = "";
			// Save requisition mst and dtl to db

			successFlag = addLsSsStoreRequisitionDtls(ssRequisitionMstDtl,
			ssRequisitionMst, roleName, department, authUser, new Date());

			model.put("subStoreRequisitionMst", ssRequisitionMst);

			if (successFlag) {
				// return getStoreRequisitionShowOfLocalToSub(ssRequisitionMst);
				// return new
				// ModelAndView("redirect:/ls/storeRequisitionShowOfLocalToSub.do");
				return new ModelAndView(
						"redirect:/ls/pd/requisitionShow.do?id="
								+ ssRequisitionMst.getId() + "&requisitionTo="
								+ ssRequisitionMst.getRequisitionTo());
			} else {
				msg = "Sorry! You have no permission to do this operation. Try again.";
				model.put("errorMsg", msg);
				return new ModelAndView("localStore/errorLS", model);

				// return new ModelAndView("redirect:/ls/requisitionList.do");

			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}

	}
	
	public boolean addLsSsStoreRequisitionDtls(
			SubStoreRequisitionMstDtl ssRequisitionMstDtl,
			SubStoreRequisitionMst ssRequisitionMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		// List<String> ledgerNameList = null;
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

		if (ssRequisitionMstDtl.getRemarks() != null) {
			remarksList = ssRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_PD_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_PD_SS_REQUISITION);

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
							LS_PD_SS_REQUISITION, stateCodes[0].toString());
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
								separator, "LS_CS_REQ_SEQ");
				ssRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);

				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"requisitionNo",
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
					ssRequisitionDtl.setCreatedDate(now);
					ssRequisitionDtl.setActive(true);
					ssRequisitionDtl
							.setSubStoreRequisitionMst(ssRequisitionMstDb);

					// insert item detail in ssRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);
				}

				// Start Approval Hierarchy History insert process
				addLsSsStoreRequisitionHierarchyHistory(ssRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}
	
	public void addLsSsStoreRequisitionHierarchyHistory(
			SubStoreRequisitionMst ssRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {
		
		LsPdSsRequisitionApprovalHierarchyHistory history = new LsPdSsRequisitionApprovalHierarchyHistory();
		history.setActRoleName(roleName);
		history.setcDeptName(department.getDeptName());
		history.setDeptId(department.getDeptId());
		history.setcEmpFullName(authUser.getName());
		history.setcEmpId(authUser.getEmpId());
		history.setcDesignation(authUser.getDesignation());
		history.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		history.setOperationId(ssRequisitionMst
				.getRequisitionNo());
		history.setOperationName(LS_PD_SS_REQUISITION);
		history.setCreatedBy(ssRequisitionMst.getCreatedBy());
		history.setCreatedDate(now);
		history.setStatus(OPEN);
		history.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st
			// State code set from approval Hierarchy Table
			history.setStateCode(stateCodes[0]);
			history.setStateName(approvalHierarchy
					.getStateName());
		}

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(history);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";

		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			DescoKhath dk = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							invItem.getKhathId());

			/*
			 * CentralStoreItems centralStoreItems = centralStoreItemsService
			 * .getCentralStoreItemsByItemId(selectItemFromDb.getItemId());
			 */
			Double presentQty = 0.0;
			if (invItem.getRequisitionTo().equals(
					ContentType.CENTRAL_STORE.toString())) {
				List<CSItemTransactionMst> tnxMstList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								NEW_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				List<CSItemTransactionMst> tnxMstList1 = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								RECOVERY_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				if (tnxMstList.size() > 0) {
					presentQty = tnxMstList.get(0).getQuantity();
				}

				if (tnxMstList1.size() > 0) {
					presentQty += tnxMstList1.get(0).getQuantity();
				}

			} else if (invItem.getRequisitionTo().equals(
					ContentType.SUB_STORE.toString())) {
				List<SSItemTransactionMst> tnxMstList = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								NEW_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				List<SSItemTransactionMst> tnxMstList1 = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								RECOVERY_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				if (tnxMstList.size() > 0) {
					presentQty = tnxMstList.get(0).getQuantity();
				}

				if (tnxMstList1.size() > 0) {
					presentQty += tnxMstList1.get(0).getQuantity();
				}
			}

			selectItemFromDb.setCurrentStock(presentQty != null ? presentQty
					: 0.0);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(5 * 1000);
		}

		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/requisition/approve.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl bean) {

		String requisitionNo = bean.getRequisitionNo();
		String returnStateCode = bean.getReturn_state();
		String justification = bean.getJustification();
		String requisitionTo = bean.getRequisitionTo();
		
		if( requisitionTo.equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {

//			SubStoreRequisitionMstDtl ssBean = ( SubStoreRequisitionMstDtl ) commonService
//					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMstDtl", "requisitionNo", requisitionNo);
			return lsPdSsRequisitionApproval( model, bean, requisitionNo, returnStateCode, justification );
		}

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		boolean storeChangeFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnStateCode.equals("") || returnStateCode.length() > 0) {

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", requisitionNo);

			List<String> issuedQtyList = bean.getIssueqty();
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

			List<LsPdCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsPdCsRequisitionApprovalHierarchyHistory",
							"operationId", requisitionNo);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history

			LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdCsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsPdCsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_PD_CS_REQUISITION, currentStateCode + "");
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

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			
			// get Next State Code and all info from approval hierarchy history
			LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsPdCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_PD_CS_REQUISITION, returnStateCode);

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(returnStateCode));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(requisitionNo);
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

			// get Requisites Item List against SR No
			String operationId = requisitionNo;

			CentralStoreRequisitionMst csRequisitionMst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
							"requisitionNo", operationId);

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", operationId);

			// Get All State Codes from Approval Hierarchy
			// and sort Descending order for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_PD_CS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<LsPdCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsPdCsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdCsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsPdCsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;
			// update issued qty

			List<String> issuedQtyList = bean.getIssueqty();
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
									LS_PD_CS_REQUISITION, nextStateCode + "");

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
						
						// Project Office users must have "ROLE_PROJECT_" prefix
						if(role.getRole().contains(ROLE_PROJECT_)){
							DescoKhath descoKhath = (DescoKhath) commonService
									.getAnObjectByAnyUniqueColumn("DescoKhath",
											"id", csRequisitionMst.getKhathId().toString());												
							approvalHierarchyHistory.setDeptId(descoKhath.getDepartment().getDeptId());
							approvalHierarchyHistory.setcDeptName(descoKhath.getDepartment().getDeptName());
						}else{			
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
					
					//m@@ reset justification
					approvalHierarchyHistory.setModifiedBy(null);
					approvalHierarchyHistory.setModifiedDate(null);
					approvalHierarchyHistory.setJustification(null);
					
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

					// item Quantity History setup Start :: added by Shimul
					String historyChange = "";
					int counter = 0;
					if (issuedQtyList != null) {
						for (int i = 0; i < issuedQtyList.size(); i++) {
							if (counter == 0) {
								historyChange += centralStoreRequisitionDtlList
										.get(i).getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ centralStoreRequisitionDtlList.get(i)
												.getUom();
								counter++;
							} else {
								historyChange += ",   "
										+ centralStoreRequisitionDtlList.get(i)
												.getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ centralStoreRequisitionDtlList.get(i)
												.getUom();
							}
						}
					}
					approvalHierarchyHistory.setHistoryChange(historyChange);
					// item Quantity History setup end

					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_PD_CS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(justification);
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
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now inserting data in store ticket mst and history
					CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
					csStoreTicketMst.setStoreTicketType(LS_PD_CS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(csRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(csRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);
					csStoreTicketMst.setKhathId(csRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(csRequisitionMst
							.getKhathName());
					csStoreTicketMst.setSndCode(csRequisitionMst.getSndCode());

					// Auto generate Store Ticket number
					String descoDeptCode = department.getDescoCode();

					csStoreTicketMst.setIssuedBy(authUser.getName() + "("
							+ userName + ")");
					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "CS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);
					csStoreTicketMst.setReceivedBy(csRequisitionMst
							.getReceivedBy());
					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

					// Requisition mst update
					csRequisitionMst.setStoreTicketNO(storeTicketNo);
					csRequisitionMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);

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
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory.setcDeptName(department
							.getDeptName());
					storeTicketApprovalHierarchyHistory
							.setcDesignation(authUser.getDesignation());
					storeTicketApprovalHierarchyHistory
							.setTicketNo(csStoreTicketMstdb.getTicketNo());
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
					return "redirect:/ls/pd/requisitionReport.do?srNo="
							+ operationId;

				}
			}
		}
		if (storeChangeFlag) {
			String operationId = requisitionNo;

			return "redirect:/ls/pd/requisitionReport.do?srNo=" + operationId;
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}

	@RequestMapping(value = "/ls/pd/requisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportCS(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();
		String rolePrefix = roleName.substring(0, 7);

		model.put("operationId", srNo);

		if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
			return new ModelAndView(
					"localStore/reports/lsPdCsRequisitionReportPD", model);
		} else if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView(
					"localStore/reports/lsPdCsRequisitionReportLS", model);
		} else if (rolePrefix.equals("ROLE_CS")) {
			return new ModelAndView(
					"localStore/reports/lsPdCsRequisitionReportCS", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView(
					"localStore/reports/lsPdSsRequisitionReportSS", model);
		}else {
			return new ModelAndView(
					"localStore/reports/lsPdCsRequisitionReportLS", model);
		}

	}
	
	@RequestMapping(value = "/ls/pd/requisitionReportSS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportSS(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();
		String rolePrefix = roleName.substring(0, 7);

		model.put("operationId", srNo);

		if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
			return new ModelAndView(
					"localStore/reports/lsPdSsRequisitionReportPD", model);
		} else if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView(
					"localStore/reports/lsPdSsRequisitionReportSS", model);
		} else if (rolePrefix.equals("ROLE_CS")) {
			return new ModelAndView(
					"localStore/reports/lsPdCsRequisitionReportCS", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView(
					"localStore/reports/lsPdSsRequisitionReportSS", model);
		}else {
			return new ModelAndView(
					"localStore/reports/lsPdSsRequisitionReportLS", model);
		}

	}

	@RequestMapping(value = "/ls/pd/csStoreTicketReportSR.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsCsStoreTicketReportRequisition(String ticketNo,
			String gpNos) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> multipleGPNoList = Arrays.asList(gpNos.split("\\s*,\\s*"));
		model.put("ticketNo", ticketNo);
		model.put("multipleGPNoList", multipleGPNoList);
		return new ModelAndView(
				"centralStore/reports/lsPdCsStoreTicketReportRS", model);
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	public String lsPdSsRequisitionApproval(Model model, CentralStoreRequisitionMstDtl bean, String requisitionNo, String returnStateCode,
			String justification ) {
		
//		String requisitionNo = bean.getRequisitionNo();
//		String returnStateCode = bean.getReturn_state();
//		String justification = bean.getJustification();
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		boolean storeChangeFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();
		
		if (!returnStateCode.equals("") || returnStateCode.length() > 0) {

			List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl",
							"requisitionNo", requisitionNo);

			List<String> issuedQtyList = bean.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							subStoreRequisitionDtl
									.setQuantityIssued(issuedqty);
						} else {
							subStoreRequisitionDtl
									.setQuantityIssued(subStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
						counter++;
					}
				}
			}

			List<LsPdSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsPdSsRequisitionApprovalHierarchyHistory",
							"operationId", requisitionNo);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history

			LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdSsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsPdSsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_PD_SS_REQUISITION, currentStateCode + "");
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

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsPdSsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_PD_SS_REQUISITION, returnStateCode);

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(returnStateCode));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(requisitionNo);
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

			// get Requisites Item List against SR No
			String operationId = requisitionNo;

			SubStoreRequisitionMst ssRequisitionMst = (SubStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
							"requisitionNo", operationId);

			List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl",
							"requisitionNo", operationId);

			// Get All State Codes from Approval Hierarchy
			// and sort Descending order for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_PD_SS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<LsPdSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsPdSsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdSsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsPdSsRequisitionApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;
			// update issued qty

			List<String> issuedQtyList = bean.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							subStoreRequisitionDtl
									.setQuantityIssued(issuedqty);
						} else {
							subStoreRequisitionDtl
									.setQuantityIssued(subStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
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
									LS_PD_SS_REQUISITION, nextStateCode + "");

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
						if(role.getRole().equals(ROLE_PROJECT_DIRECTOR)){
							DescoKhath descoKhath = (DescoKhath) commonService
									.getAnObjectByAnyUniqueColumn("DescoKhath",
											"id", ssRequisitionMst.getKhathId().toString());												
							approvalHierarchyHistory.setDeptId(descoKhath.getDepartment().getDeptId());
							approvalHierarchyHistory.setcDeptName(descoKhath.getDepartment().getDeptName());
						}else{			
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

					// item Quantity History setup Start :: added by Shimul
					String historyChange = "";
					int counter = 0;
					if (issuedQtyList != null) {
						for (int i = 0; i < issuedQtyList.size(); i++) {
							if (counter == 0) {
								historyChange += subStoreRequisitionDtlList
										.get(i).getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ subStoreRequisitionDtlList.get(i)
												.getUom();
								counter++;
							} else {
								historyChange += ",   "
										+ subStoreRequisitionDtlList.get(i)
												.getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ subStoreRequisitionDtlList.get(i)
												.getUom();
							}
						}
					}
					approvalHierarchyHistory.setHistoryChange(historyChange);
					// item Quantity History setup end

					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_PD_SS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(justification);
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
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now inserting data in store ticket mst and history
					SSStoreTicketMst ssStoreTicketMst = new SSStoreTicketMst();
					ssStoreTicketMst.setStoreTicketType(LS_PD_SS_REQUISITION);
					ssStoreTicketMst.setOperationId(operationId);
					ssStoreTicketMst
							.setIssuedTo(ssRequisitionMst.getDeptName());
					ssStoreTicketMst.setIssuedFor(ssRequisitionMst
							.getIdenterDesignation());
					ssStoreTicketMst.setFlag(false);
					ssStoreTicketMst.setKhathId(ssRequisitionMst.getKhathId());
					ssStoreTicketMst.setKhathName(ssRequisitionMst
							.getKhathName());
					ssStoreTicketMst.setSndCode(ssRequisitionMst.getSndCode());

					// Auto generate Store Ticket number
					String descoDeptCode = department.getDescoCode();

					ssStoreTicketMst.setIssuedBy(authUser.getName() + "("
							+ userName + ")");
					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "SS_ST_SEQ");

					ssStoreTicketMst.setTicketNo(storeTicketNo);
					ssStoreTicketMst.setReceivedBy(ssRequisitionMst
							.getReceivedBy());
					commonService.saveOrUpdateModelObjectToDB(ssStoreTicketMst);

					// Requisition mst update
					ssRequisitionMst.setStoreTicketNO(storeTicketNo);
					ssRequisitionMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
									"operationId", operationId);

					// Get All Approval Hierarchy on CS_STORE_TICKET
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

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
									SS_STORE_TICKET,
									sStoreTicketStateCodes[0].toString());

					StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

					storeTicketApprovalHierarchyHistory
							.setActRoleName(storeTicketpprovalHierarchy
									.getRoleName());
					storeTicketApprovalHierarchyHistory
							.setOperationId(operationId);
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory.setcDeptName(department
							.getDeptName());
					storeTicketApprovalHierarchyHistory
							.setcDesignation(authUser.getDesignation());
					storeTicketApprovalHierarchyHistory
							.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory
							.setOperationName(SS_STORE_TICKET);
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

					//model.addAttribute("operationId", operationId);
					return "redirect:/ls/pd/requisitionReportSS.do?srNo="
							+ operationId;

				}
			}
		}
		if (storeChangeFlag) {
			String operationId = requisitionNo;
			
			return "redirect:/ls/pd/requisitionReportSS.do?srNo=" + operationId;
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String lsPdSendTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String reqNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();
		
		if(csRequisitionMstDtl.getRequisitionTo().equals(ContentType.SUB_STORE.toString())){
			return this.lsPdSendToSS(csRequisitionMstDtl);
		}

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", csRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						centralStoreRequisitionDtl.setQuantityIssued(issuedqty);
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

		List<LsPdCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsPdCsRequisitionApprovalHierarchyHistory",
						"operationId", reqNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdCsRequisitionApprovalHierarchyHistory)
				commonService.getAnObjectByAnyUniqueColumn("LsPdCsRequisitionApprovalHierarchyHistory", 
						"id", ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_CS_REQUISITION, currentStateCode + "");
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
		LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsPdCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_CS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(reqNo);
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
	
	@SuppressWarnings("unchecked")
	public String lsPdSendToSS(CentralStoreRequisitionMstDtl ssRequisitionMstDtl) {
		
		String reqNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String nextStateCode = ssRequisitionMstDtl.getStateCode();
		
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		// Start
		// update issued qty
		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", ssRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						subStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						subStoreRequisitionDtl
								.setQuantityIssued(subStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsPdSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsPdSsRequisitionApprovalHierarchyHistory",
						"operationId", reqNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdSsRequisitionApprovalHierarchyHistory)
				commonService.getAnObjectByAnyUniqueColumn("LsPdSsRequisitionApprovalHierarchyHistory", 
						"id", ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_SS_REQUISITION, currentStateCode + "");
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
		LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsPdSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_SS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(reqNo);
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
		// end
		
		return "redirect:/ls/requisitionList.do";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/pd/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String lsPdBackTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		String reqNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String backStateCode = csRequisitionMstDtl.getStateCode();
		
		if(csRequisitionMstDtl.getRequisitionTo().equals(ContentType.SUB_STORE.toString())){
			return this.lsPdBackToSS(csRequisitionMstDtl);
		}

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		// update issued qty
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", csRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						centralStoreRequisitionDtl.setQuantityIssued(issuedqty);
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

		List<LsPdCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsPdCsRequisitionApprovalHierarchyHistory",
						"operationId", reqNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdCsRequisitionApprovalHierarchyHistory)
				commonService.getAnObjectByAnyUniqueColumn("LsPdCsRequisitionApprovalHierarchyHistory", "id", ids[0].toString());
			
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_CS_REQUISITION, currentStateCode + "");
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
		LsPdCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsPdCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_CS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(reqNo);
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
	
	
	@SuppressWarnings("unchecked")
	public String lsPdBackToSS(CentralStoreRequisitionMstDtl ssRequisitionMstDtl) {
		String reqNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String backStateCode = ssRequisitionMstDtl.getStateCode();
		
		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		// update issued qty
		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", ssRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						subStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						subStoreRequisitionDtl
								.setQuantityIssued(subStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsPdSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsPdSsRequisitionApprovalHierarchyHistory",
						"operationId", reqNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (LsPdSsRequisitionApprovalHierarchyHistory)
				commonService.getAnObjectByAnyUniqueColumn("LsPdSsRequisitionApprovalHierarchyHistory", "id", ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_SS_REQUISITION, currentStateCode + "");
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
		LsPdSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsPdSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_PD_SS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(reqNo);
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
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/ls/pd/addMultipleItems.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView addMultipleItems( CentralStoreRequisitionMstDtl centralStoreRequisitionMstDtl ) {
		
		String requisitionTo = centralStoreRequisitionMstDtl.getRequisitionTo();
		String requisitionNo = centralStoreRequisitionMstDtl.getRequisitionNo();
		
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		// List<String> ledgerNameList = null;
		List<String> remarksList = null;
		
		if( centralStoreRequisitionMstDtl.getItemCode() != null ) {
			itemCodeList = centralStoreRequisitionMstDtl.getItemCode();
		}
		
		if( centralStoreRequisitionMstDtl.getItemName() != null ) {
			itemNameList = centralStoreRequisitionMstDtl.getItemName();
		}
		
		if( centralStoreRequisitionMstDtl.getUom() != null ) {
			uomList = centralStoreRequisitionMstDtl.getUom();
		}
		
		if( centralStoreRequisitionMstDtl.getQuantityRequired() != null ) {
			quantityRequiredList = centralStoreRequisitionMstDtl.getQuantityRequired();
		}
		
		if( centralStoreRequisitionMstDtl.getRemarks() != null ) {
			remarksList = centralStoreRequisitionMstDtl.getRemarks();
		}
		
		if( requisitionTo.equalsIgnoreCase( ContentType.CENTRAL_STORE.toString() ) ) {

			CentralStoreRequisitionMst centralStoreRequisitionMst = ( CentralStoreRequisitionMst ) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst", "requisitionNo", requisitionNo);
			
			for( int i = 0; i < itemCodeList.size(); i++ ) {
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

				csRequisitionDtl.setQuantityIssued(0.0);

				if (!remarksList.isEmpty()) {
					csRequisitionDtl.setRemarks(remarksList.get(i));
				} else {
					csRequisitionDtl.setRemarks("");
				}

				// set id null for auto number
				csRequisitionDtl.setId(null);

				// set RequisitionNo to each detail row
				csRequisitionDtl.setRequisitionNo(centralStoreRequisitionMstDtl
						.getRequisitionNo());
				csRequisitionDtl.setCreatedBy(commonService.getAuthUserName());
				csRequisitionDtl.setCreatedDate(new Date());
				csRequisitionDtl.setActive(true);
				csRequisitionDtl
						.setCentralStoreRequisitionMst(centralStoreRequisitionMst);

				// insert item detail in csRequisitionDtl table
				commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);
			}
			
			return new ModelAndView(
					"redirect:/ls/pd/requisitionShow.do?id="
							+ centralStoreRequisitionMst.getId() + "&requisitionTo="
							+ centralStoreRequisitionMst.getRequisitionTo());
			
		} else if( requisitionTo.equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {
			SubStoreRequisitionMst subStoreRequisitionMst = ( SubStoreRequisitionMst ) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "requisitionNo", requisitionNo);
			
			for( int i = 0; i < itemCodeList.size(); i++ ) {
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

				ssRequisitionDtl.setQuantityIssued(0.0);

				if (!remarksList.isEmpty()) {
					ssRequisitionDtl.setRemarks(remarksList.get(i));
				} else {
					ssRequisitionDtl.setRemarks("");
				}

				// set id null for auto number
				ssRequisitionDtl.setId(null);

				// set RequisitionNo to each detail row
				ssRequisitionDtl.setRequisitionNo(centralStoreRequisitionMstDtl
						.getRequisitionNo());
				ssRequisitionDtl.setCreatedBy(commonService.getAuthUserName());
				ssRequisitionDtl.setCreatedDate(new Date());
				ssRequisitionDtl.setActive(true);
				ssRequisitionDtl
						.setSubStoreRequisitionMst(subStoreRequisitionMst);

				// insert item detail in csRequisitionDtl table
				commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);
			}
			
			return new ModelAndView(
					"redirect:/ls/pd/requisitionShow.do?id="
							+ subStoreRequisitionMst.getId() + "&requisitionTo="
							+ subStoreRequisitionMst.getRequisitionTo());
		} else {
			return null;
		}
	}
	
	//Added by Ihteshamul Alam
	@RequestMapping(value="/ls/pd/deleteItem.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteItem( CentralStoreRequisitionMstDtl centralStoreRequisitionMstDtl ) {
		
		Integer id = centralStoreRequisitionMstDtl.getId();
		String requisitionTo = centralStoreRequisitionMstDtl.getRequisitionTo();
		String requisitionNo = centralStoreRequisitionMstDtl.getRequisitionNo();
		
		if( requisitionTo.equalsIgnoreCase( ContentType.CENTRAL_STORE.toString() ) ) {

			CentralStoreRequisitionMst centralStoreRequisitionMst = ( CentralStoreRequisitionMst ) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst", "requisitionNo", requisitionNo);
			
			commonService.deleteAnObjectById("CentralStoreRequisitionDtl", id);
			
			return new ModelAndView(
					"redirect:/ls/pd/requisitionShow.do?id="
							+ centralStoreRequisitionMst.getId() + "&requisitionTo="
							+ centralStoreRequisitionMst.getRequisitionTo());
			
		} else if( requisitionTo.equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {

			SubStoreRequisitionMst subStoreRequisitionMst = ( SubStoreRequisitionMst ) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "requisitionNo", requisitionNo);
			commonService.deleteAnObjectById("SubStoreRequisitionDtl", id);
			
			return new ModelAndView(
					"redirect:/ls/pd/requisitionShow.do?id="
							+ subStoreRequisitionMst.getId() + "&requisitionTo="
							+ subStoreRequisitionMst.getRequisitionTo());
			
		} else {
			return null;
		}
		
	}
}
