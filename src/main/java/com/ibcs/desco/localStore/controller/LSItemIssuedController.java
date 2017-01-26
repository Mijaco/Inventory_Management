package com.ibcs.desco.localStore.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.ibcs.desco.common.model.C2LSRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LSStoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.model.ContractorDepartmentReference;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionDtl;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionMst;

@Controller
@RequestMapping(value = "/c2ls")
@PropertySource("classpath:common.properties")
public class LSItemIssuedController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ContractorDepartmentReference> contDeptRefList = (List<ContractorDepartmentReference>) (Object) commonService
				.getObjectListByAnyColumn("ContractorDepartmentReference",
						"contractorId", authUser.getId() + "");

		List<String> deptIdList = new ArrayList<String>();
		for (ContractorDepartmentReference cdr : contDeptRefList) {
			deptIdList.add(cdr.getDeptId() + "");
		}

		List<Departments> deptList = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumnValueList("Departments", "id",
						deptIdList);

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		ContractorRepresentive contractorRepresentative = (ContractorRepresentive) commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
						"userId", userName);

		model.put("deptIdList", deptList);
		model.put("categoryList", categoryList);
		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		model.put("contractorRepresentative", contractorRepresentative);
		model.put("authUser", authUser);

		return new ModelAndView("sndContractor/c2LsStoreRequisitionForm", model);
	}

	@RequestMapping(value = "/storeRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String storeRequisitionSave(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		LocalStoreRequisitionMst lsRequisitionMst = new LocalStoreRequisitionMst();
		lsRequisitionMst.setIdenterDesignation(csRequisitionMstDtl
				.getIdenterDesignation());
		lsRequisitionMst.setDeptName(department.getDeptName());
		lsRequisitionMst.setReceivedBy(csRequisitionMstDtl.getReceivedBy());

		ContractorRepresentive contractorRepresentive = (ContractorRepresentive) commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
						"userId", userName);

		lsRequisitionMst.setRequisitionDate(now);
		lsRequisitionMst.setActive(csRequisitionMstDtl.isActive());
		lsRequisitionMst.setCreatedBy(userName);
		lsRequisitionMst.setCreatedDate(now);
		lsRequisitionMst.setUuid(UUID.randomUUID().toString());
		lsRequisitionMst.setReceived(false);
		lsRequisitionMst.setStorePrefix(ContentType.LS_CONTRACTOR.toString());
		lsRequisitionMst.setRequisitionTo(ContentType.LOCAL_STORE.toString());
		lsRequisitionMst.setRequestedDeptId(csRequisitionMstDtl
				.getRequestedDeptId());

		lsRequisitionMst.setKhathId(descoKhath.getId());
		lsRequisitionMst.setKhathName(descoKhath.getKhathName());
		lsRequisitionMst.setContractorRepresentive(contractorRepresentive);

		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addStoreRequisitionDtls(csRequisitionMstDtl,
				lsRequisitionMst, roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
			return "redirect:/c2ls/storeRequisitionShow.do?id="
					+ lsRequisitionMst.getId() + "&requisitionTo="
					+ lsRequisitionMst.getRequisitionTo();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/c2ls/requisitionList.do";
		}

	}

	public boolean addStoreRequisitionDtls(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			LocalStoreRequisitionMst lsRequisitionMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		// List<String> headOfAccountList = null;
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
		 */

		/*
		 * if (csRequisitionMstDtl.getHeadOfAccount() != null) {
		 * headOfAccountList = csRequisitionMstDtl.getHeadOfAccount(); }
		 */

		if (csRequisitionMstDtl.getRemarks() != null) {
			remarksList = csRequisitionMstDtl.getRemarks();
		}

		/*
		 * if (csRequisitionMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csRequisitionMstDtl.getLedgerName(); }
		 */

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_ISSUED_REQUISITION);

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
							LS_ISSUED_REQUISITION, stateCodes[0].toString());
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
								separator, "C2LS_REQ_SEQ");
				lsRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(lsRequisitionMst);

				LocalStoreRequisitionMst lsRequisitionMstDb = (LocalStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"LocalStoreRequisitionMst", "requisitionNo",
								lsRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					LocalStoreRequisitionDtl lsRequisitionDtl = new LocalStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						lsRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						lsRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						lsRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						lsRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						lsRequisitionDtl.setUom(uomList.get(i));
					} else {
						lsRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						lsRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						lsRequisitionDtl.setQuantityRequired(0);
					}

					/*
					 * if (!unitCostList.isEmpty()) {
					 * lsRequisitionDtl.setUnitCost(unitCostList.get(i)); } else
					 * { lsRequisitionDtl.setUnitCost(unitCostList.get(i)); }
					 * 
					 * if (!totalCostList.isEmpty()) {
					 * lsRequisitionDtl.setTotalCost(totalCostList.get(i)); }
					 * else { lsRequisitionDtl.setTotalCost(0); }
					 */

					/*
					 * if (!headOfAccountList.isEmpty()) {
					 * lsRequisitionDtl.setHeadOfAccount(headOfAccountList
					 * .get(i)); } else { lsRequisitionDtl.setHeadOfAccount("");
					 * }
					 */

					lsRequisitionDtl.setQuantityIssuedNS(0.0);
					lsRequisitionDtl.setQuantityIssuedRS(0.0);
					lsRequisitionDtl.setLedgerName("");

					if (!remarksList.isEmpty()) {
						lsRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						lsRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					lsRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					lsRequisitionDtl.setRequisitionNo(lsRequisitionMst
							.getRequisitionNo());
					lsRequisitionDtl.setCreatedBy(lsRequisitionMst
							.getCreatedBy());
					lsRequisitionDtl.setCreatedDate(lsRequisitionMst
							.getCreatedDate());
					lsRequisitionDtl.setActive(true);
					lsRequisitionDtl.setLsRequisitionMst(lsRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(lsRequisitionDtl);
				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(lsRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			LocalStoreRequisitionMst lsRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {

		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new C2LSRequisitionApprovalHierarchyHistory();

		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(lsRequisitionMst
				.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(LS_ISSUED_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(lsRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(lsRequisitionMst
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
				.getDepartmentByDeptId(authUser.getDeptId());

		List<C2LSRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"C2LSRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationIds = new ArrayList<String>();

		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIds
					.add(lsCsRequisitionHistoryList.get(i).getOperationId());
		}
		List<LocalStoreRequisitionMst> lsRequisitionMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LocalStoreRequisitionMst",
						"requisitionNo", operationIds);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", lsRequisitionMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/cnLsRequisitionList", model);
		} else {
			return new ModelAndView("sndContractor/c2LsRequisitionList", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(
			LocalStoreRequisitionMst lsRequisitionMst) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			String deptId = authUser.getDeptId();

			LocalStoreRequisitionMst lsRequisitionMstdb = (LocalStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalStoreRequisitionMst",
							"id", lsRequisitionMst.getId() + "");

			String operationId = lsRequisitionMstdb.getRequisitionNo();
			// String storePrefixStr[] = operationId.split("-");
			// String storePrefix = storePrefixStr[0].toLowerCase();

			List<LocalStoreRequisitionDtl> lsRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
							"requisitionNo", operationId);

			/*
			 * Following for loop is added by Ashid, 
			 * to add current stock of those items
			 */
			DescoKhath dk = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
							REVENUE);
			for (LocalStoreRequisitionDtl dtl : lsRequisitionDtlList) {
				Double nsQty = 0.0;
				Double rsQty = 0.0;

				List<LSItemTransactionMst> tnxMstListns = (List<LSItemTransactionMst>) (Object) commonService
						.getObjectListByFourColumn("LSItemTransactionMst",
								"khathId", dk.getId().toString(), "ledgerName",
								NEW_SERVICEABLE, "itemCode", dtl.getItemCode(),
								"sndCode", department.getDescoCode());

				List<LSItemTransactionMst> tnxMstListrs = (List<LSItemTransactionMst>) (Object) commonService
						.getObjectListByFourColumn("LSItemTransactionMst",
								"khathId", dk.getId().toString(), "ledgerName",
								RECOVERY_SERVICEABLE, "itemCode",
								dtl.getItemCode(), "sndCode",
								department.getDescoCode());

				for (LSItemTransactionMst tnxMst : tnxMstListns) {
					nsQty += tnxMst.getQuantity();
				}

				for (LSItemTransactionMst tnxMst : tnxMstListrs) {
					rsQty += tnxMst.getQuantity();
				}

				dtl.setPresentStockNs(nsQty);
				dtl.setPresentStockRs(rsQty);
			}

			String buttonValue = null;

			// operation Id which selected by login user
			String currentStatus = "";

			List<C2LSRequisitionApprovalHierarchyHistory> lsRequisitionApprovalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"C2LSRequisitionApprovalHierarchyHistory",
							LS_ISSUED_REQUISITION, operationId, DONE);

			if (!lsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
				currentStatus = lsRequisitionApprovalHierarchyHistoryList.get(
						lsRequisitionApprovalHierarchyHistoryList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			List<C2LSRequisitionApprovalHierarchyHistory> lsRequisitionApprovalHierarchyHistoryOpenList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"C2LSRequisitionApprovalHierarchyHistory",
							LS_ISSUED_REQUISITION, operationId, OPEN);

			int currentStateCode = lsRequisitionApprovalHierarchyHistoryOpenList
					.get(lsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();

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
							LS_ISSUED_REQUISITION, roleNameList);
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

			//

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
			if (!lsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
					&& lsRequisitionApprovalHierarchyHistoryOpenList != null) {
				// get current state code
				int stateCode = lsRequisitionApprovalHierarchyHistoryOpenList
						.get(lsRequisitionApprovalHierarchyHistoryOpenList
								.size() - 1).getStateCode();
				// Decide for return or not
				returnStateCode = lsRequisitionApprovalHierarchyHistoryOpenList
						.get(lsRequisitionApprovalHierarchyHistoryOpenList
								.size() - 1).getReturn_state();
				// get next approval hierarchy
				ApprovalHierarchy approveHeirarchy = null;
				{
					approveHeirarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_ISSUED_REQUISITION, stateCode + "");
					buttonValue = approveHeirarchy.getButtonName();
				}

			}

			/* Following four lines are added by Ihteshamul Alam */

			String userrole = lsRequisitionMstdb.getCreatedBy();
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userrole);
			lsRequisitionMstdb.setCreatedBy(createBy.getName());

			List<StoreLocations> storeLocationList = getStoreLocationList("CS");

			model.put("returnStateCode", returnStateCode);
			model.put("centralStoreRequisitionMst", lsRequisitionMstdb);
			model.put("currentStatus", currentStatus);
			model.put("buttonValue", buttonValue);
			model.put("nextManRcvProcs", nextManReqProcs);
			model.put("backManRcvProcs", backManRcvProcs);
			model.put("locationList", storeLocationList);

			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			model.put("reqTo", ContentType.LOCAL_STORE.toString());
			model.put("itemRcvApproveHistoryList",
					lsRequisitionApprovalHierarchyHistoryList);
			model.put("centralStoreRequisitionDtlList", lsRequisitionDtlList);

			String rolePrefix = roleName.substring(0, 7);

			if (rolePrefix.equals("ROLE_LS")) {

				return new ModelAndView("sndContractor/lsRequisitionShow",
						model);
			} else {
				return new ModelAndView("sndContractor/c2LsRequisitionShow",
						model);
			}

		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	// Receiving Report (RR) Approving process
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!csRequisitionMstDtl.getReturn_state().equals("")
				|| csRequisitionMstDtl.getReturn_state().length() > 0) {

			List<C2LSRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"C2LSRequisitionApprovalHierarchyHistory",
							"operationId",
							csRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (C2LSRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"C2LSRequisitionApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_ISSUED_REQUISITION, currentStateCode + "");
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
			C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new C2LSRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_ISSUED_REQUISITION,
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

			LocalStoreRequisitionMst lsRequisitionMst = (LocalStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalStoreRequisitionMst",
							"requisitionNo", operationId);
			if (lsRequisitionMst.isApproved()) {
				//
			} else {

				List<LocalStoreRequisitionDtl> lsRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
								"requisitionNo", operationId);

				// get All State Codes from Approval Hierarchy and sort
				// Desending
				// oder
				// for highest State Code
				List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
						.getApprovalHierarchyByOperationName(LS_ISSUED_REQUISITION);
				Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
				for (int i = 0; i < approvalHierarchyList.size(); i++) {
					stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
				}
				Arrays.sort(stateCodes);

				// get Current State Code from Approval hierarchy by RR No
				List<C2LSRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
						.getObjectListByAnyColumn(
								"C2LSRequisitionApprovalHierarchyHistory",
								"operationId", operationId);

				Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
				for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
					ids[i] = approvalHierarchyHistoryList.get(i).getId();
				}
				Arrays.sort(ids, Collections.reverseOrder());

				// get current State Code and all info from approval hierarchy
				// history
				C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (C2LSRequisitionApprovalHierarchyHistory) commonService
						.getAnObjectByAnyUniqueColumn(
								"C2LSRequisitionApprovalHierarchyHistory",
								"id", ids[0] + "");

				int currentStateCode = approvalHierarchyHistory.getStateCode();
				int nextStateCode = 0;
				// update issued qty

				List<Double> issuedQtyNSList = csRequisitionMstDtl
						.getQuantityIssuedNS();
				List<Double> issuedQtyRSList = csRequisitionMstDtl
						.getQuantityIssuedRS();
				if (issuedQtyNSList != null || issuedQtyRSList != null) {
					if (issuedQtyRSList.size() > 0
							|| issuedQtyNSList.size() > 0) {
						int counter = 0;
						for (LocalStoreRequisitionDtl lsRequisitionDtl : lsRequisitionDtlList) {
							double issuedqtyNS = issuedQtyNSList.get(counter);
							double issuedqtyRS = issuedQtyRSList.get(counter);
							//double totalQty = issuedqtyNS + issuedqtyRS;

							//if (lsRequisitionDtl.getQuantityRequired() >= totalQty) {
								lsRequisitionDtl
										.setQuantityIssuedNS(issuedqtyNS);
								lsRequisitionDtl
										.setQuantityIssuedRS(issuedqtyRS);
							//} else {
							//	lsRequisitionDtl.setQuantityIssuedNS(0.0);
							//	lsRequisitionDtl.setQuantityIssuedRS(0.0);
							//}
							commonService
									.saveOrUpdateModelObjectToDB(lsRequisitionDtl);
							counter++;
						}
					}
				}

				// searching next State code and decision for send this RR to
				// next person
				for (int state : stateCodes) {

					// if next state code grater than current state code than
					// this
					// process will go to next person
					if (state > currentStateCode) {
						nextStateCode = state;
						ApprovalHierarchy approvalHierarchy = approvalHierarchyService
								.getApprovalHierarchyByOperationNameAndSateCode(
										LS_ISSUED_REQUISITION, nextStateCode
												+ "");

						// next role name
						// next role id
						// next role users dept

						String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
								.getRoleName().substring(0, 8);
						// checking department switching
						if (r1.equals(r2)) {

						} else {
							/*
							 * Roles role = (Roles) commonService
							 * .getAnObjectByAnyUniqueColumn(
							 * "com.ibcs.desco.admin.model.Roles", "role",
							 * approvalHierarchy.getRoleName()); List<AuthUser>
							 * userList = (List<AuthUser>) (Object)
							 * commonService .getObjectListByAnyColumn(
							 * "com.ibcs.desco.admin.model.AuthUser", "roleid",
							 * role.getRole_id() + ""); Departments depart =
							 * (Departments) commonService
							 * .getAnObjectByAnyUniqueColumn("Departments",
							 * "deptId", userList.get(0).getDeptId());
							 * approvalHierarchyHistory
							 * .setDeptId(depart.getDeptId());
							 * approvalHierarchyHistory.setcDeptName(depart
							 * .getDeptName());
							 */
							Departments depart = (Departments) commonService
									.getAnObjectByAnyUniqueColumn(
											"Departments",
											"deptId",
											lsRequisitionMst
													.getRequestedDeptId() + "");
							approvalHierarchyHistory.setDeptId(depart
									.getDeptId());
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
						approvalHierarchyHistory
								.setActRoleName(approvalHierarchy.getRoleName());
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
										LS_ISSUED_REQUISITION, state + "");
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory
								.setJustification(csRequisitionMstDtl
										.getJustification());
						approvalHierarchyHistory
								.setApprovalHeader(approvalHierarchy
										.getApprovalHeader());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());

						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					}

					// if next state code is last as approval hierarchy than
					// this
					// process will done and go for generate a store ticket
					if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());

						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						// now we have to insert data in store ticket mst and
						// history
						LSStoreTicketMst lsStoreTicketMst = new LSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						lsStoreTicketMst
								.setStoreTicketType(LS_ISSUED_REQUISITION);
						lsStoreTicketMst.setOperationId(operationId);
						/*
						 * lsStoreTicketMst.setIssuedTo(lsRequisitionMst
						 * .getDeptName());
						 */
						lsStoreTicketMst.setIssuedTo(lsRequisitionMst
								.getReceivedBy());
						lsStoreTicketMst.setIssuedFor(lsRequisitionMst
								.getIdenterDesignation());
						lsStoreTicketMst.setFlag(false);
						lsStoreTicketMst.setKhathId(lsRequisitionMst
								.getKhathId());
						lsStoreTicketMst.setKhathName(lsRequisitionMst
								.getKhathName());
						lsStoreTicketMst.setIssuedBy(authUser.getName());
						
						lsStoreTicketMst.setReceivedBy(lsRequisitionMst.getReceivedBy());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService
								.getOperationIdByPrefixAndSequenceName(
										descoStoreTicketNoPrefix,
										descoDeptCode, separator, "C2LS_ST_SEQ");

						lsStoreTicketMst.setTicketNo(storeTicketNo);
						lsStoreTicketMst.setSndCode(descoDeptCode);

						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketMst);

						// Item Issued update
						lsRequisitionMst.setStoreTicketNO(storeTicketNo);
						lsRequisitionMst.setApproved(true);
						lsRequisitionMst.setReceived(true);
						commonService
								.saveOrUpdateModelObjectToDB(lsRequisitionMst);

						LSStoreTicketMst lsStoreTicketMstdb = (LSStoreTicketMst) commonService
								.getAnObjectByAnyUniqueColumn(
										"LSStoreTicketMst", "operationId",
										operationId);

						//

						// Get All Approval Hierarchy on CS_STORE_TICKET
						List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
								.getApprovalHierarchyByOperationName(LS_STORE_TICKET);

						Integer[] lStoreTicketStateCodes = new Integer[approvalHierarchyListDb
								.size()];
						for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
							lStoreTicketStateCodes[i] = approvalHierarchyListDb
									.get(i).getStateCode();
						}
						Arrays.sort(lStoreTicketStateCodes);

						// get approve hierarchy for last state
						ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
								.getApprovalHierarchyByOperationNameAndSateCode(
										LS_STORE_TICKET,
										lStoreTicketStateCodes[0].toString());

						LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new LSStoreTicketApprovalHierarchyHistory();

						storeTicketApprovalHierarchyHistory
								.setActRoleName(storeTicketpprovalHierarchy
										.getRoleName());
						storeTicketApprovalHierarchyHistory
								.setOperationId(operationId);
						storeTicketApprovalHierarchyHistory.setDeptId(deptId);
						storeTicketApprovalHierarchyHistory
								.setcDeptName(department.getDeptName());
						storeTicketApprovalHierarchyHistory
								.setcDesignation(authUser.getDesignation());
						storeTicketApprovalHierarchyHistory
								.setTicketNo(lsStoreTicketMstdb.getTicketNo());
						storeTicketApprovalHierarchyHistory
								.setOperationName(LS_STORE_TICKET);
						storeTicketApprovalHierarchyHistory
								.setCreatedBy(userName);
						storeTicketApprovalHierarchyHistory
								.setCreatedDate(new Date());
						if (stateCodes.length > 0) {
							storeTicketApprovalHierarchyHistory
									.setStateCode(lStoreTicketStateCodes[0]);
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

						//return "localStore/c2LsRequisitionReport";
						return "redirect:/c2ls/showRequisitionReportLS.do?srNo="+operationId;

					}
				}
			}
		}
		return "redirect:/c2ls/requisitionList.do";
	}
	
	@RequestMapping(value = "/showRequisitionReportLS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportLS(String srNo){
		Map <String, Object> model =  new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("localStore/c2LsRequisitionReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl lsRequisitionMstDtl) {

		String rrNo = lsRequisitionMstDtl.getRequisitionNo();
		String justification = lsRequisitionMstDtl.getJustification();
		String nextStateCode = lsRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		
		// update issued qty
		String operationId = lsRequisitionMstDtl.getRequisitionNo();
		List<LocalStoreRequisitionDtl> lsRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
						"requisitionNo", operationId);

		List<Double> issuedQtyNSList = lsRequisitionMstDtl
				.getQuantityIssuedNS();
		List<Double> issuedQtyRSList = lsRequisitionMstDtl
				.getQuantityIssuedRS();
		if (issuedQtyNSList != null || issuedQtyRSList != null) {
			if (issuedQtyRSList.size() > 0
					|| issuedQtyNSList.size() > 0) {
				int counter = 0;
				for (LocalStoreRequisitionDtl lsRequisitionDtl : lsRequisitionDtlList) {
					double issuedqtyNS = issuedQtyNSList.get(counter);
					double issuedqtyRS = issuedQtyRSList.get(counter);
					//double totalQty = issuedqtyNS + issuedqtyRS;

					//if (lsRequisitionDtl.getQuantityRequired() >= totalQty) {
						lsRequisitionDtl
								.setQuantityIssuedNS(issuedqtyNS);
						lsRequisitionDtl
								.setQuantityIssuedRS(issuedqtyRS);
					/*} else {
						lsRequisitionDtl.setQuantityIssuedNS(0.0);
						lsRequisitionDtl.setQuantityIssuedRS(0.0);
					}*/
					commonService
							.saveOrUpdateModelObjectToDB(lsRequisitionDtl);
					counter++;
				}
			}
		}
		// update end

		List<C2LSRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"C2LSRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (C2LSRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"C2LSRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_ISSUED_REQUISITION, currentStateCode + "");
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
		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new C2LSRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_ISSUED_REQUISITION, nextStateCode + "");
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

		return "redirect:/c2ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl lsRequisitionMstDtl) {
		String rrNo = lsRequisitionMstDtl.getRequisitionNo();
		String justification = lsRequisitionMstDtl.getJustification();
		String backStateCode = lsRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		// update issued qty
				String operationId = lsRequisitionMstDtl.getRequisitionNo();
				List<LocalStoreRequisitionDtl> lsRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
								"requisitionNo", operationId);

				List<Double> issuedQtyNSList = lsRequisitionMstDtl
						.getQuantityIssuedNS();
				List<Double> issuedQtyRSList = lsRequisitionMstDtl
						.getQuantityIssuedRS();
				if (issuedQtyNSList != null || issuedQtyRSList != null) {
					if (issuedQtyRSList.size() > 0
							|| issuedQtyNSList.size() > 0) {
						int counter = 0;
						for (LocalStoreRequisitionDtl lsRequisitionDtl : lsRequisitionDtlList) {
							double issuedqtyNS = issuedQtyNSList.get(counter);
							double issuedqtyRS = issuedQtyRSList.get(counter);
							//double totalQty = issuedqtyNS + issuedqtyRS;

							//if (lsRequisitionDtl.getQuantityRequired() >= totalQty) {
								lsRequisitionDtl
										.setQuantityIssuedNS(issuedqtyNS);
								lsRequisitionDtl
										.setQuantityIssuedRS(issuedqtyRS);
							/*} else {
								lsRequisitionDtl.setQuantityIssuedNS(0.0);
								lsRequisitionDtl.setQuantityIssuedRS(0.0);
							}*/
							commonService
									.saveOrUpdateModelObjectToDB(lsRequisitionDtl);
							counter++;
						}
					}
				}
				// update end

		List<C2LSRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"C2LSRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (C2LSRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"C2LSRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_ISSUED_REQUISITION, currentStateCode + "");
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
		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new C2LSRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_ISSUED_REQUISITION, backStateCode + "");
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

		return "redirect:/c2ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionSearch(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl lsRequisitionMstDtl) {

		String operationId = lsRequisitionMstDtl.getRequisitionNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<C2LSRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"C2LSRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		List<String> operationIdList = new ArrayList<String>();
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList.add(lsCsRequisitionHistoryList.get(i)
					.getOperationId());
		}

		List<LocalStoreRequisitionMst> lsRequisitionMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LocalStoreRequisitionMst",
						"requisitionNo", operationIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", lsRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/cnLsRequisitionList", model);
		} else {
			return new ModelAndView("sndContractor/c2LsRequisitionList", model);
		}

	}

	@RequestMapping(value = "/viewInventoryItemCategory.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		DescoKhath dk = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			Departments dept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							invItem.getRequisitionTo());

			/*
			 * CentralStoreItems centralStoreItems = centralStoreItemsService
			 * .getCentralStoreItemsByItemId(selectItemFromDb.getItemId());
			 */
			Double presentQty = 0.0;

			List<LSItemTransactionMst> tnxMstList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"khathId", dk.getId() + "", "ledgerName",
							NEW_SERVICEABLE, "itemCode",
							selectItemFromDb.getItemId(), "sndCode",
							dept.getDescoCode());

			List<LSItemTransactionMst> tnxMstList1 = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"khathId", dk.getId() + "", "ledgerName",
							RECOVERY_SERVICEABLE, "itemCode",
							selectItemFromDb.getItemId(), "sndCode",
							dept.getDescoCode());

			if (tnxMstList != null && tnxMstList.size() > 0) {
				presentQty = tnxMstList.get(0).getQuantity();
			}

			if (tnxMstList1 != null && tnxMstList1.size() > 0) {
				presentQty += tnxMstList1.get(0).getQuantity();
			}

			selectItemFromDb.setCurrentStock(presentQty != null ? presentQty
					: 0.0);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

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

	// Added by Taleb
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/reject.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String requisitionReject(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl lsRequisitionMstDtl) {

		String rrNo = lsRequisitionMstDtl.getRequisitionNo();
		String justification = lsRequisitionMstDtl.getJustification();	
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		
		List<C2LSRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"C2LSRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		C2LSRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (C2LSRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"C2LSRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_ISSUED_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(REJECT);
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

		return "redirect:/c2ls/requisitionList.do";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionRejectList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView requisitionRejectList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(now);
	    cal.add(Calendar.MONTH, -1);
	    Date firstDate = cal.getTime();
	    
	    Calendar calc = Calendar.getInstance();
	    calc.setTime(now);
	    calc.add(Calendar.DATE, 1);
	    Date secondDate = calc.getTime();
	    
	    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	    String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);
		

		List<C2LSRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn("C2LSRequisitionApprovalHierarchyHistory", 
						"modifiedDate", fromDate, toDate, "status", REJECT, "active", "1");
				/*getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"C2LSRequisitionApprovalHierarchyHistory", deptId,
						roleName, REJECT);*/

		List<String> operationIds = new ArrayList<String>();

		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIds
					.add(lsCsRequisitionHistoryList.get(i).getOperationId());
		}
		List<LocalStoreRequisitionMst> lsRequisitionMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LocalStoreRequisitionMst",
						"requisitionNo", operationIds);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", lsRequisitionMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/cnLsRequisitionRejectList", model);
		} else {
			return new ModelAndView("sndContractor/c2LsRequisitionRejectList", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionRejectShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionRejectShow(
			LocalStoreRequisitionMst lsRequisitionMst) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			String deptId = authUser.getDeptId();

			LocalStoreRequisitionMst lsRequisitionMstdb = (LocalStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalStoreRequisitionMst",
							"id", lsRequisitionMst.getId() + "");

			String operationId = lsRequisitionMstdb.getRequisitionNo();
			// String storePrefixStr[] = operationId.split("-");
			// String storePrefix = storePrefixStr[0].toLowerCase();

			List<LocalStoreRequisitionDtl> lsRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
							"requisitionNo", operationId);

			/*
			 * Following for loop is added by Ashid, 
			 * to add current stock of those items
			 */
			DescoKhath dk = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
							REVENUE);

			// operation Id which selected by login user
			String currentStatus = "";

			List<C2LSRequisitionApprovalHierarchyHistory> lsRequisitionApprovalHierarchyHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"C2LSRequisitionApprovalHierarchyHistory",
							LS_ISSUED_REQUISITION, operationId, DONE);

			if (!lsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
				currentStatus = lsRequisitionApprovalHierarchyHistoryList.get(
						lsRequisitionApprovalHierarchyHistoryList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			List<C2LSRequisitionApprovalHierarchyHistory> lsRequisitionApprovalHierarchyHistoryRejectList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"C2LSRequisitionApprovalHierarchyHistory",
							LS_ISSUED_REQUISITION, operationId, REJECT);

		
			/* Following four lines are added by Ihteshamul Alam */

			String userrole = lsRequisitionMstdb.getCreatedBy();
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userrole);
			lsRequisitionMstdb.setCreatedBy(createBy.getName());

		
			model.put("centralStoreRequisitionMst", lsRequisitionMstdb);
			model.put("currentStatus", currentStatus);
			
			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			model.put("reqTo", ContentType.LOCAL_STORE.toString());
			model.put("itemRcvApproveHistoryList",
					lsRequisitionApprovalHierarchyHistoryList);
			model.put("lsRequisitionApprovalHierarchyHistoryRejectList",
					lsRequisitionApprovalHierarchyHistoryRejectList);
			model.put("centralStoreRequisitionDtlList", lsRequisitionDtlList);

			String rolePrefix = roleName.substring(0, 7);

			if (rolePrefix.equals("ROLE_LS")) {

				return new ModelAndView("sndContractor/lsRequisitionRejectShow",
						model);
			} else {
				return new ModelAndView("sndContractor/c2LsRequisitionRejectShow",
						model);
			}

		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("localStore/errorLS", model);
		}
	}
}
