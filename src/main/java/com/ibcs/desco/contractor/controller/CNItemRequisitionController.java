package com.ibcs.desco.contractor.controller;

import java.io.IOException;
//@author nasrin
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
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CnSsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.bean.CSStoreTicketMstDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
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
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/cn")
@PropertySource("classpath:common.properties")
public class CNItemRequisitionController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	CnSsRequisitionApprovalHierarchyHistoryService cnSsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;

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
	public String storeRequisitionSave(Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		ContractorRepresentive contractorRep = contractorRepresentiveService.getContractorRep(authUser.getUserid());
		Contractor contractor = contractorRepresentiveService.getContractorByContractNo(contractorRep.getContractNo());
		DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
				contractor.getKhathId() + "");

		SubStoreRequisitionMst ssRequisitionMst = new SubStoreRequisitionMst();
		ssRequisitionMst.setIdenterDesignation(ssRequisitionMstDtl.getIdenterDesignation());
		ssRequisitionMst.setDeptName(department.getDeptName());
		ssRequisitionMst.setSndCode(department.getDescoCode());
		ssRequisitionMst.setKhathId(contractor.getKhathId());
		ssRequisitionMst.setKhathName(descoKhath.getKhathName());
		ssRequisitionMst.setSndCode(department.getDescoCode());

		// set current date time as RequisitionDate. GUI date is not used here.
		ssRequisitionMst.setUuid(UUID.randomUUID().toString());
		ssRequisitionMst.setRequisitionDate(now);
		ssRequisitionMst.setActive(ssRequisitionMstDtl.isActive());
		ssRequisitionMst.setCreatedBy(userName);
		ssRequisitionMst.setCreatedDate(now);
		ssRequisitionMst.setRequisitionTo(ssRequisitionMstDtl.getRequisitionTo());
		ssRequisitionMst.setSenderStore(ContentType.CONTRACTOR.toString());
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addStoreRequisitionDtls(ssRequisitionMstDtl, ssRequisitionMst, roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
			return "redirect:/cn/storeRequisitionShow.do?id=" + ssRequisitionMst.getId();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/cn/requisitionList.do";
		}
	}

	public boolean addStoreRequisitionDtls(SubStoreRequisitionMstDtl ssRequisitionMstDtl,
			SubStoreRequisitionMst ssRequisitionMst, String roleName, Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
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

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_SS_REQUISITION);

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
					.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, stateCodes[0].toString());
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
				String requisitionNo = commonService.getOperationIdByPrefixAndSequenceName(descoRequisitionNoPrefix,
						descoDeptCode, separator, "CN_SS_REQ_SEQ");
				ssRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);

				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "requisitionNo",
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
						ssRequisitionDtl.setQuantityRequired(quantityRequiredList.get(i));
					} else {
						ssRequisitionDtl.setQuantityRequired(0);
					}

					/*
					 * if (!unitCostList.isEmpty()) {
					 * ssRequisitionDtl.setUnitCost(unitCostList.get(i)); } else
					 * { ssRequisitionDtl.setUnitCost(unitCostList.get(i)); }
					 */

					/*
					 * if (!totalCostList.isEmpty()) {
					 * ssRequisitionDtl.setTotalCost(totalCostList.get(i)); }
					 * else { ssRequisitionDtl.setTotalCost(0); }
					 * 
					 * if (!headOfAccountList.isEmpty()) {
					 * ssRequisitionDtl.setHeadOfAccount(headOfAccountList
					 * .get(i)); } else { ssRequisitionDtl.setHeadOfAccount("");
					 * }
					 */
					if (!remarksList.isEmpty()) {
						ssRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						ssRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					ssRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					ssRequisitionDtl.setRequisitionNo(ssRequisitionMst.getRequisitionNo());
					ssRequisitionDtl.setCreatedBy(ssRequisitionMst.getCreatedBy());
					ssRequisitionDtl.setCreatedDate(ssRequisitionMst.getCreatedDate());
					ssRequisitionDtl.setActive(true);
					ssRequisitionDtl.setSubStoreRequisitionMst(ssRequisitionMstDb);

					// insert item detail in ssRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);
				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(ssRequisitionMst, approvalHierarchy, stateCodes, roleName,
						department, authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addStoreRequisitionHierarchyHistory(SubStoreRequisitionMst ssRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes, String roleName, Departments department,
			AuthUser authUser) {
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new CnSsRequisitionApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(ssRequisitionMst.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(CN_SS_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(ssRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(ssRequisitionMst.getCreatedDate());
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			approvalHierarchyHistory.setStateName(approvalHierarchy.getStateName());
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
		List<CnSsRequisitionApprovalHierarchyHistory> cnSsRequisitionHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("CnSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[cnSsRequisitionHistoryList.size()];
		for (int i = 0; i < cnSsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = cnSsRequisitionHistoryList.get(i).getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("subStoreRequisitionMstList", subStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CO")) {
			return new ModelAndView("contractor/cnToSsRequisitionList", model);
		} else if (rolePrefix.equals("ROLE_PD")) {
			return new ModelAndView("contractor/cnToSsRequisitionList", model);
		} else {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		}

	}

	// Form
	@SuppressWarnings("unused")
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();
		
		Departments department = departmentsService.getDepartmentByDeptId(deptId);
		
		ContractorRepresentive contractorRep = contractorRepresentiveService.getContractorRep(authUser.getUserid());

		if (!contractorRepresentiveService.getValidContractor(contractorRep.getContractNo())) {
			model.put("msg", "This Contract is not valid");
			return new ModelAndView("contractor/contractor", model);
		} else if (contractorRep == null) {
			model.put("msg", "Your Contract validity date is expired");
			return new ModelAndView("contractor/contractor", model);
		}
		List<JobItemMaintenance> JobDtl = pndJobDtlService.getJobItems(contractorRep.getContractNo());
		model.put("jobItemList", JobDtl);
		model.put("contractNo", contractorRep.getContractNo());
		
		model.put("deptName", department.getDeptName());
		model.put("deptAddress", department.getAddress()+", "+department.getContactNo());
		return new ModelAndView("contractor/storeRequisitionForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(SubStoreRequisitionMst centralStoreRequisitionMst) throws IOException {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		/*
		 * Departments department = departmentsService
		 * .getDepartmentByDeptId(authUser.getDeptId());
		 */

		

		String deptId = authUser.getDeptId();

		SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
				.getSubStoreRequisitionMst(centralStoreRequisitionMst.getId());
		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl", "requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnSsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus("CnSsRequisitionApprovalHierarchyHistory",
						CN_SS_REQUISITION, operationId, DONE);

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 */

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList
					.get(sCsRequisitionApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnSsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus("CnSsRequisitionApprovalHierarchyHistory",
						CN_SS_REQUISITION, operationId, OPEN);

		int currentStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
				.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn("com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList("com.ibcs.desco.admin.model.Roles", "role_id", roleIdList);
		// App_hier List by RoleList & Op name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(CN_SS_REQUISITION, roleNameList);
				/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!sCsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sCsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();
			// deciede for return or not
			returnStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1).getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService.getAllObjectList("DescoKhath");

		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations", "storeCode", "CS", "parentId");
		String locationOptions = "";
		for (int i = 0; i < locationsList.size(); i++) {
			locationOptions += locationsList.get(i).getId() + ":" + locationsList.get(i).getName() + ";";
		}
		locationOptions = locationOptions.substring(0, locationOptions.length() - 1);

		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String locationList = ow.writeValueAsString(locationsList);
		// model.put("locationList", "'" + locationOptions + "'");
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("khathList", khathList);
		model.put("uuid", UUID.randomUUID());
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		model.put("locationList", "'" + locationOptions + "'");

		model.put("returnStateCode", returnStateCode);
		model.put("subStoreRequisitionMst", subStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList", sCsRequisitionApprovalHierarchyHistoryList);
		model.put("subStoreRequisitionDtlList", subStoreRequisitionDtlList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CO")) {
			return new ModelAndView("contractor/cnRequisitionShow", model);
		}
		if (rolePrefix.equals("ROLE_PD")) {
			return new ModelAndView("contractor/pdRequisitionShow", model);
		} else {
			return new ModelAndView("contractor/ssRequisitionShow", model);
		}

	}

	// Receiving Report (RR) Approving process
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!ssRequisitionMstDtl.getReturn_state().equals("") || ssRequisitionMstDtl.getReturn_state().length() > 0) {

			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn("CnSsRequisitionApprovalHierarchyHistory", "operationId",
							ssRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = cnSsRequisitionApprovalHierarchyHistoryService
					.getCnSsRequisitionApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(ssRequisitionMstDtl.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION,
							ssRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer.parseInt(ssRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(ssRequisitionMstDtl.getRequisitionNo());
			approvalHierarchyHistoryNextState.setOperationName(approvalHierarchyNextSate.getOperationName());
			approvalHierarchyHistoryNextState.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState.setApprovalHeader(approvalHierarchyNextSate.getApprovalHeader());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setcDeptName(department.getDeptName());
			approvalHierarchyHistoryNextState.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {

			// get Received Item List against RR No
			String operationId = ssRequisitionMstDtl.getRequisitionNo();

			SubStoreRequisitionMst ssRequisitionMst = (SubStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "requisitionNo", operationId);

			List<SubStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl", "requisitionNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN_SS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn("CnSsRequisitionApprovalHierarchyHistory", "operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = cnSsRequisitionApprovalHierarchyHistoryService
					.getCnSsRequisitionApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// update issued qty
			String rolePrefix = roleName.substring(0, 7);
			if (rolePrefix.equals("ROLE_SS")) {
			List<String> idList = ssRequisitionMstDtl.getIds();
			List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
			for(int i = 0; i < idList.size(); i++){
				SubStoreRequisitionDtl dtlDb=(SubStoreRequisitionDtl)
				commonService.getAnObjectByAnyUniqueColumn("SubStoreRequisitionDtl", "id", idList.get(i));				
				dtlDb.setQuantityIssued(Double.parseDouble(issuedQtyList.get(i)));
				commonService.saveOrUpdateModelObjectToDB(dtlDb);
			}
			}
			/*if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					// int counter = 0;
					// int i=issuedQtyList.size();
					for (int i = 0; i < issuedQtyList.size(); i++) {
						SubStoreRequisitionDtl centralStoreRequisitionDtl = centralStoreRequisitionDtlList.get(i);
						// if(i==counter){break;}
						double issuedqty = Double.parseDouble(issuedQtyList.get(i));

						if (issuedqty > 0) {
							centralStoreRequisitionDtl.setQuantityIssued(issuedqty);
							commonService.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
						}

						// counter++;
					}
				}
			}*/

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, nextStateCode + "");

					// next role name
					// next role id
					// next role users dept

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {

					} else {
						Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
								"com.ibcs.desco.admin.model.Roles", "role", approvalHierarchy.getRoleName());
						List<AuthUser> userList = (List<AuthUser>) (Object) commonService.getObjectListByAnyColumn(
								"com.ibcs.desco.admin.model.AuthUser", "roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService.getAnObjectByAnyUniqueColumn("Departments",
								"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart.getDeptName());
					}
					// AuthUser

					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy.getStateName());

					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy.getRoleName());
					approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

					commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process
				// will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(ssRequisitionMstDtl.getJustification());
					approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

					commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now we have to insert data in store ticket mst and
					// history
					SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
					// csStoreTicketMst.setTicketNo(ticketNo);
					csStoreTicketMst.setStoreTicketType(CN_SS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst.setIssuedTo(ssRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(ssRequisitionMst.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);

					csStoreTicketMst.setKhathId(ssRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(ssRequisitionMst.getKhathName());
					csStoreTicketMst.setSndCode(ssRequisitionMst.getSndCode());

					// Auto generate Store Ticket number
					/*
					 * int storeTicketMaxId = (Integer) commonService
					 * .getMaxValueByObjectAndColumn("CSStoreTicketMst", "id");
					 * Date now = new Date(); String year = now.getYear() + "";
					 * String mm = now.getMonth() + ""; String formattedId =
					 * String.format("%06d", storeTicketMaxId + 1);
					 * csStoreTicketMst .setTicketNo(csStoreTicketNoPrefix +
					 * year.trim() + "-" + mm.trim() + "-" + formattedId);
					 */
					/*
					 * String storeTicketNo = commonService.getCustomSequence1(
					 * csStoreTicketNoPrefix, "-");
					 * commonService.saveOrUpdateCustomSequence1ToDB(
					 * storeTicketNo);
					 */
					String descoDeptCode = department.getDescoCode();
					String storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(descoStoreTicketNoPrefix,
							descoDeptCode, separator, "SS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);

					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "operationId", operationId);

					//

					// Get All Approval Hierarchy on CS_STORE_TICKET
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

					Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb.size()];
					for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
						sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i).getStateCode();
					}
					Arrays.sort(sStoreTicketStateCodes);

					// get approve hierarchy for last state
					ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(SS_STORE_TICKET,
									sStoreTicketStateCodes[0].toString());

					StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

					storeTicketApprovalHierarchyHistory.setActRoleName(storeTicketpprovalHierarchy.getRoleName());
					storeTicketApprovalHierarchyHistory.setOperationId(operationId);
					storeTicketApprovalHierarchyHistory.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory.setOperationName(SS_STORE_TICKET);
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						storeTicketApprovalHierarchyHistory.setStateCode(sStoreTicketStateCodes[0]);
						storeTicketApprovalHierarchyHistory.setStateName(storeTicketpprovalHierarchy.getStateName());
					}
					storeTicketApprovalHierarchyHistory.setStatus(OPEN);
					storeTicketApprovalHierarchyHistory.setActive(true);
					// process will done and go for store ticket
					commonService.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

					model.addAttribute("operationId", operationId);

					return "contractor/cnToSsRequisitionReport";

				}
			}
		}
		return "redirect:/cn/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String nextStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("CnSsRequisitionApprovalHierarchyHistory", "operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = cnSsRequisitionApprovalHierarchyHistoryService
				.getCnSsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(rrNo + "");
		approvalHierarchyHistoryNextState.setOperationName(approvalHierarchyNextSate.getOperationName());
		approvalHierarchyHistoryNextState.setActRoleName(approvalHierarchyNextSate.getRoleName());
		approvalHierarchyHistoryNextState.setApprovalHeader(approvalHierarchyNextSate.getApprovalHeader());

		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryNextState.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		return "redirect:/cn/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {
		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String backStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("CnSsRequisitionApprovalHierarchyHistory", "operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = cnSsRequisitionApprovalHierarchyHistoryService
				.getCnSsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(CN_SS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(approvalHierarchyBackSate.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(rrNo + "");
		approvalHierarchyHistoryBackState.setOperationName(approvalHierarchyBackSate.getOperationName());
		approvalHierarchyHistoryBackState.setActRoleName(approvalHierarchyBackSate.getRoleName());
		approvalHierarchyHistoryBackState.setApprovalHeader(approvalHierarchyBackSate.getApprovalHeader());

		// for return same user
		approvalHierarchyHistoryBackState.setStage(BACK);
		approvalHierarchyHistoryBackState.setReturn_to(roleName);
		approvalHierarchyHistoryBackState.setReturn_state(currentStateCode + "");

		approvalHierarchyHistoryBackState.setDeptId(deptId);
		approvalHierarchyHistoryBackState.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryBackState.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/cn/requisitionList.do";
	}

	@RequestMapping(value = "/cnViewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String cnViewInventoryItem(@RequestBody String json) throws Exception {
		String toJson = "";
		String s = json.substring(1, json.length() - 1);
		String[] strValue = s.split(",");
		String itemId[] = strValue[0].split(":");
		String contractId = strValue[1].toString();
		/*
		 * PndJobDtl selectItemFromDb = pndJobDtlService
		 * .getPndJobDtl(itemId[1].toString(), contractId);
		 */
		JobItemMaintenance selectItemFromDb = pndJobDtlService.getTotalJobItems(itemId[1].toString(), contractId);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(selectItemFromDb);

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

	// get store ticket List filter on login user and login user role
	@SuppressWarnings({ "unused", "unchecked" })
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
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(deptId, roleName, OPEN, "");

		// get all operation id which open for this login user
		/*
		 * String[] operationId = new
		 * String[storeTicketApprovalHierarchyHistoryList .size()]; int x = 0;
		 * for (StoreTicketApprovalHierarchyHistory
		 * storeTicketApprovalHierarchyHistory :
		 * storeTicketApprovalHierarchyHistoryList) { operationId[x] =
		 * storeTicketApprovalHierarchyHistory .getOperationId(); x++; }
		 */
		List<String> operationList = new ArrayList<String>();
		// get all operation id which open for this login user
		//String[] operationId = new String[storeTicketApprovalHierarchyHistoryList.size()];
		int x = 0;
		for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {

			operationList.add(storeTicketApprovalHierarchyHistory.getOperationId());
			x++;
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
		/*
		 * List<SSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
		 * .listSSProcItemRcvMstByOperationIds(operationId);
		 */
		List<SSStoreTicketMst> csStoreTicketMstList = (List<SSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSStoreTicketMst", "operationId", operationList);

		/*
		 * List<CSStoreTicketMst> csStoreTicketMstList = csStoreTicketMstService
		 * .listCSProcItemRcvMstByOperationIdList(operationId);
		 */
		// List<CSStoreTicketMst> csStoreTicketMstList =
		// (List<CSStoreTicketMst>)(Object)commonService.getObjectListByAnyColumnValueList("CSStoreTicketMst",
		// "operationId", operationId);
		model.put("csStoreTicketMstList", csStoreTicketMstList);
		return new ModelAndView("subStore/ssStoreTicketList", model);
	}

	// after receiving report store ticket come to next user who generate a
	// store ticket and show that
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/openStoreTicket.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSStoreTicketCreateOpen(CSStoreTicketMst csStoreTicketMst) {
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
					.getStoreTicketApprovalHierarchyHistoryByOppNameOppIdAndStatus(SS_STORE_TICKET, operationId, DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

			// set the button name or value
			String buttonValue = "";
			if (!storeTicketApproveHistoryList.isEmpty() && storeTicketApproveHistoryList != null) {

				int stateCode = storeTicketApproveHistoryList.get(storeTicketApproveHistoryList.size() - 1)
						.getStateCode();

				int nextStateCode = 0;
				for (int i = 0; i < approveHeirchyList.size(); i++) {
					if (approveHeirchyList.get(i).getStateCode() > stateCode) {
						nextStateCode = approveHeirchyList.get(i).getStateCode();
						break;
					}
				}
				if (nextStateCode > 0) {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(SS_STORE_TICKET, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(SS_STORE_TICKET, stateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				}

			} else {
				buttonValue = approveHeirchyList.get(0).getButtonName();
			}

			// set current status
			if (!storeTicketApproveHistoryList.isEmpty()) {
				currentStatus = storeTicketApproveHistoryList.get(storeTicketApproveHistoryList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// current ticket no
			String ticketNo = csStoreTicketMst.getTicketNo();

			// get all store ticket master info against current ticket no
			SSStoreTicketMst csStoreTicketMstdb = csStoreTicketMstService.getSSStoreTicketMstByTicketNo(ticketNo);

			// get all store ticket item details info against current ticket no
			/*
			 * List<CSStoreTicketDtl> csStoreTicketDtlList =
			 * csStoreTicketDtlService .getCSStoreTicketDtlByTicketNo(ticketNo);
			 */

			List<SSStoreTicketDtl> csStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo", ticketNo);

			// sent to browser
			model.put("storeTicketApproveHistoryList", storeTicketApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csStoreTicketMst", csStoreTicketMstdb);
			model.put("csStoreTicketDtlList", csStoreTicketDtlList);
			model.put("buttonValue", buttonValue);

			if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
				return new ModelAndView("subStore/ssStoreTicketShowRS", model);
			} else if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
				return new ModelAndView("subStore/ssStoreTicketShowRequisition", model);
			} else {
				return null;
			}

		} else {
			// target : for generate a store ticket now will open a form of
			// store ticket with some value
			// store ticket master (common) information
			SSStoreTicketMst csStoreTicketMstdb = csStoreTicketMstService
					.getSSStoreTicketMstByTicketNo(csStoreTicketMst.getTicketNo());
			model.put("csStoreTicketMst", csStoreTicketMstdb);
			// Store ticket Items information
			if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
				List<SSReturnSlipDtl> csReturnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
						.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo", csStoreTicketMst.getOperationId());

				model.put("csReturnSlipDtlList", csReturnSlipDtlList);
				return new ModelAndView("subStore/ssStoreTicketFormRS", model);
			} else if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
				List<SubStoreRequisitionDtl> ssRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("SubStoreRequisitionDtl", "requisitionNo",
								csStoreTicketMst.getOperationId());
				model.put("ssRequisitionDtlList", ssRequisitionDtlList);
				return new ModelAndView("subStore/ssStoreTicketFormRequisition", model);
			}

			return null;
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveStoreTicket.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getCSStoreTicketCreateSave(CSStoreTicketMstDtl csStoreTicketMstDtl) {

		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		//AuthUser authUser = userService.getAuthUserByUserId(userName);
		//String deptId = authUser.getDeptId();

		List<String> itemIdList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> costList = null;

		List<String> ledgerBookNoList = null;

		List<String> ledgerPageNoList = null;

		List<Double> quantityList = null;

		List<String> remarksList = null;

		Date now = new Date();

		SSStoreTicketMst ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "id", csStoreTicketMstDtl.getId() + "");

		ssStoreTicketMst.setCreatedBy(userName);
		ssStoreTicketMst.setCreatedDate(now);
		ssStoreTicketMst.setActive(true);
		ssStoreTicketMst.setFlag(true);
		ssStoreTicketMst.setTicketDate(now);
		/*
		 * if (csStoreTicketMst.getStoreTicketType().equals(CS_RECEIVING_ITEMS))
		 * { csStoreTicketMst.setReceivedBy(userName); }
		 */

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

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				SSStoreTicketDtl csStoreTicketDtl = new SSStoreTicketDtl();
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
				} else {
					csStoreTicketDtl.setDescription("");
				}

				if (uomList.size() > 0 || uomList != null) {
					csStoreTicketDtl.setUom(uomList.get(i));
				} else {
					csStoreTicketDtl.setUom("");
				}

				if (costList != null) {
					csStoreTicketDtl.setCost(Double.parseDouble(costList.get(i).toString()));
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
				} else {
					csStoreTicketDtl.setQuantity(0.0);
				}
				commonService.saveOrUpdateModelObjectToDB(csStoreTicketDtl);

			}
		}

		// return "redirect:/cs/itemRecieved/storeTicketlist.do";

		if (csStoreTicketMstDtl.getStoreTicketType().equals(CN_SS_REQUISITION)) {
			return "redirect:/ss/itemRecieved/openStoreTicket.do?operationId=" + csStoreTicketMstDtl.getOperationId()
					+ "&flag=" + true + "&storeTicketType=" + CN_SS_REQUISITION + "&ticketNo="
					+ csStoreTicketMstDtl.getTicketNo();

		} else if (csStoreTicketMstDtl.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
			return "redirect:/ss/itemRecieved/openStoreTicket.do?operationId=" + csStoreTicketMstDtl.getOperationId()
					+ "&flag=" + true + "&storeTicketType=" + CN_SS_RETURN_SLIP + "&ticketNo="
					+ csStoreTicketMstDtl.getTicketNo();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeTicketSubmitApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String subSotreStoreTicketSubmitApproved(Model model,
			@ModelAttribute("csStoreTicketMstDtl") CSStoreTicketMstDtl csStoreTicketMstDtl) {

		// get Current User and Role Information

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		//
		String ticketNo = csStoreTicketMstDtl.getTicketNo();
		SSStoreTicketMst csStoreTicketMst = csStoreTicketMstService.getSSStoreTicketMstByTicketNo(ticketNo);

		/*
		 * List<CSStoreTicketDtl> csStoreTicketDtlList = csStoreTicketDtlService
		 * .getCSStoreTicketDtlByTicketNo(ticketNo);
		 */
		List<SSStoreTicketDtl> csStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo", ticketNo);

		// get All State Codes from Approval Hierarchy and sort Dececding oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get Current State Code from Approval hierarchy by RR No (Operation
		// Id)

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

		int currentStateCode = storeTicketApprovalHierarchyHistory.getStateCode();

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
						.getApprovalHierarchyByOperationNameAndSateCode(csStoreTicketMstDtl.getStoreTicketType(),
								nextStateCode + "");

				storeTicketApprovalHierarchyHistory.setStatus(OPEN);
				storeTicketApprovalHierarchyHistory.setStateCode(nextStateCode);
				storeTicketApprovalHierarchyHistory.setStateName(approvalHierarchy.getStateName());
				storeTicketApprovalHierarchyHistory.setId(null);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory.setActRoleName(approvalHierarchy.getRoleName());
				storeTicketApprovalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());
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
						.getApprovalHierarchyByOperationNameAndSateCode(csStoreTicketMst.getStoreTicketType(),
								state + "");

				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);

				storeTicketApprovalHierarchyHistory.setTicketNo(ticketNo);

				storeTicketApprovalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

				storeTicketApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);
			}

			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(storeTicketApprovalHierarchyHistory);

				// new ledger update
				// Ledger Mst
				this.ssLedgerLocation(csStoreTicketDtlList, userName, roleName, authUser, department, csStoreTicketMst);
				// Ledger Update

				/*
				 * for (CSStoreTicketDtl csStoreTicketDtl :
				 * csStoreTicketDtlList) { SubStoreItems subStoreItemsdb =
				 * subStoreItemsService
				 * .getSubStoreItemsByItemId(csStoreTicketDtl .getItemId()); if
				 * (subStoreItemsdb != null) { // update item quantity and last
				 * ticket No double newBalance = 0.0; double currentBalance =
				 * subStoreItemsdb.getBalance(); double changeBalance =
				 * csStoreTicketDtl.getUseableQty();
				 * 
				 * if (csStoreTicketMst.getStoreTicketType().equals(
				 * LS_SS_RETURN_SLIP) || csStoreTicketMst.getStoreTicketType()
				 * .equals(CN_SS_RETURN_SLIP)) { newBalance = currentBalance +
				 * changeBalance; } else if
				 * (csStoreTicketMst.getStoreTicketType()
				 * .equals(LS_SS_REQUISITION) ||
				 * csStoreTicketMst.getStoreTicketType()
				 * .equals(CN_SS_REQUISITION)) { newBalance = currentBalance -
				 * changeBalance; }
				 * 
				 * subStoreItemsdb.setBalance(newBalance);
				 * subStoreItemsdb.setModifiedBy(userName);
				 * subStoreItemsdb.setModifiedDate(new Date());
				 * subStoreItemsdb.setLastTicketNo(ticketNo);
				 * subStoreItemsService .addSubStoreItems(subStoreItemsdb); }
				 * else { SubStoreItems subStoreItems = new SubStoreItems();
				 * subStoreItems.setActive(true);
				 * subStoreItems.setBalance(csStoreTicketDtl .getUseableQty());
				 * subStoreItems.setCreatedBy(userName);
				 * subStoreItems.setCreatedDate(new Date());
				 * subStoreItems.setLastTicketNo(ticketNo);
				 * subStoreItems.setItemId(csStoreTicketDtl .getItemId());
				 * subStoreItems.setId(null);
				 * subStoreItems.setUom(csStoreTicketDtl.getUom());
				 * subStoreItems.setRemarks(csStoreTicketDtl .getRemarks());
				 * subStoreItemsService .addSubStoreItems(subStoreItems); // new
				 * row insert } }
				 */

				// open the gate pass for Item issued against Requisition
				if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {

					SubStoreRequisitionMst csReqDb = (SubStoreRequisitionMst) commonService
							.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "requisitionNo", operationId);
					// code for gate pass
					SSGatePassMst csGatePassMst = new SSGatePassMst();
					// need to generate gatepassNo
					String descoDeptCode = department.getDescoCode();
					String gatePassNo = commonService.getOperationIdByPrefixAndSequenceName(descoGatePassNoPrefix,
							descoDeptCode, separator, "SS_GATE_PASS_SEQ");
					csGatePassMst.setGatePassNo(gatePassNo);

					csGatePassMst.setIssuedTo(csReqDb.getIdenterDesignation());
					csGatePassMst.setIssuedBy(commonService.getAuthUserName());
					csGatePassMst.setFlag(false);
					csGatePassMst.setActive(true);
					csGatePassMst.setRequisitonNo(operationId);
					csGatePassMst.setTicketNo(ticketNo);
					csGatePassMst.setWorkOrderNo(csStoreTicketMst.getWorkOrderNo());
					// Save command for Central Store Gate pass
					commonService.saveOrUpdateModelObjectToDB(csGatePassMst);

					SSGatePassMst csGatePassMstdb = (SSGatePassMst) commonService
							.getAnObjectByAnyUniqueColumn("SSGatePassMst", "ticketNo", ticketNo);

					// insert data to gate pass history
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(SS_GATE_PASS);

					Integer[] gatePassStateCodes = new Integer[approvalHierarchyListDb.size()];
					for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
						gatePassStateCodes[i] = approvalHierarchyListDb.get(i).getStateCode();
					}
					Arrays.sort(gatePassStateCodes);
					// get approve hierarchy for last state
					ApprovalHierarchy gatePasspprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(SS_GATE_PASS,
									gatePassStateCodes[0].toString());
					GatePassApprovalHierarchyHistory gpApprovalHierarchyHistory = new GatePassApprovalHierarchyHistory();
					gpApprovalHierarchyHistory.setActRoleName(gatePasspprovalHierarchy.getRoleName());
					gpApprovalHierarchyHistory.setOperationId(operationId);

					// dept info
					Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.Roles",
							"role", gatePasspprovalHierarchy.getRoleName());
					List<AuthUser> userList = (List<AuthUser>) (Object) commonService.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid", role.getRole_id() + "");
					Departments depart = (Departments) commonService.getAnObjectByAnyUniqueColumn("Departments",
							"deptId", userList.get(0).getDeptId());
					gpApprovalHierarchyHistory.setDeptId(depart.getDeptId());
					gpApprovalHierarchyHistory.setcDeptName(depart.getDeptName());

					gpApprovalHierarchyHistory.setTicketNo(ticketNo);
					gpApprovalHierarchyHistory.setOperationName(SS_GATE_PASS);
					gpApprovalHierarchyHistory.setCreatedBy(commonService.getAuthUserName());
					gpApprovalHierarchyHistory.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						gpApprovalHierarchyHistory.setStateCode(gatePassStateCodes[0]);
						gpApprovalHierarchyHistory.setStateName(gatePasspprovalHierarchy.getStateName());
					}
					gpApprovalHierarchyHistory.setStatus(OPEN);
					gpApprovalHierarchyHistory.setGatePassNo(csGatePassMstdb.getGatePassNo());
					gpApprovalHierarchyHistory.setActive(true);
					commonService.saveOrUpdateModelObjectToDB(gpApprovalHierarchyHistory);
				}

				// Instant Store Ticket Report Call
				model.addAttribute("ticketNo", ticketNo);
				if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
					return "subStore/SsStoreTicketReportRS";
				} else if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
					return "subStore/SsStoreTicketReportRequisition";
				} else {
					return "";
				}
			}

		}

		return "redirect:/ss/storeTicketlist.do";
	}

	@SuppressWarnings("unchecked")
	private void ssLedgerLocation(List<SSStoreTicketDtl> csStoreTicketDtlList, String userName, String roleName,
			AuthUser authUser, Departments dept, SSStoreTicketMst csStoreTicketMst) {

		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			SSItemTransactionMst csItemTransactionMst = null;
			SSItemTransactionMst csItemTransactionMstdb = ssItemTransactionMstService.getSSItemTransactionMst(
					csStoreTicketDtl.getItemId(), csStoreTicketMst.getKhathId(), csStoreTicketDtl.getLedgerName());
			DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
					csStoreTicketMst.getKhathId() + "");
			// Ledger Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionMstdb.setCreatedDate(new Date());
				csItemTransactionMstdb.setCreatedBy(userName);// for only
																// now....addede
																// by nasrin
				csItemTransactionMstdb.setModifiedBy(userName);
				csItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() - csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(RECOVERY_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() - csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(UNSERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() - csStoreTicketDtl.getQuantity());
					}

				} else if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() + csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(RECOVERY_SERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() + csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(UNSERVICEABLE)) {
						csItemTransactionMstdb
								.setQuantity(csItemTransactionMstdb.getQuantity() + csStoreTicketDtl.getQuantity());
					}
				}
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMstdb);
			} else {
				csItemTransactionMst = new SSItemTransactionMst();
				csItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() - csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(RECOVERY_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() - csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(UNSERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() - csStoreTicketDtl.getQuantity());
					}

				} else if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() + csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(RECOVERY_SERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() + csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(UNSERVICEABLE)) {
						csItemTransactionMst
								.setQuantity(csItemTransactionMst.getQuantity() + csStoreTicketDtl.getQuantity());
					}
				}
				csItemTransactionMst.setKhathName(descoKhath.getKhathName());
				csItemTransactionMst.setKhathId(descoKhath.getId());
				csItemTransactionMst.setLedgerName(csStoreTicketDtl.getLedgerName());
				csItemTransactionMst.setId(null);
				csItemTransactionMst.setCreatedBy(userName);
				csItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService.getMaxValueByObjectAndColumn("SSItemTransactionMst", "id");
			SSItemTransactionMst csItemTransactionMstLastRow = (SSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "id", maxTnxMstId + "");

			// Ledger Dtl
			SSItemTransactionDtl csItemTransactionDtl = new SSItemTransactionDtl();
			// common Info
			csItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			csItemTransactionDtl.setKhathId(descoKhath.getId());
			csItemTransactionDtl.setLedgerType(csStoreTicketDtl.getLedgerName());
			csItemTransactionDtl.setId(null);
			csItemTransactionDtl.setCreatedBy(userName);
			csItemTransactionDtl.setCreatedDate(new Date());
			csItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			csItemTransactionDtl.setTransactionType(csStoreTicketMst.getStoreTicketType());
			csItemTransactionDtl.setTransactionId(csStoreTicketMst.getOperationId());
			csItemTransactionDtl.setTransactionDate(csStoreTicketMst.getTicketDate());

			// relation with transaction Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionDtl.setSsItemTransactionMst(csItemTransactionMstdb);
			} else {
				csItemTransactionDtl.setSsItemTransactionMst(csItemTransactionMstLastRow);
			}

			// item related info
			csItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			csItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			csItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			csItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			csItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			csItemTransactionDtl.setReceivedBy(csStoreTicketMst.getReceivedBy());
			csItemTransactionDtl.setReceivedFrom(csStoreTicketMst.getReceivedFrom());

			// Ledger Quantity Info
			csItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());
			csItemTransactionDtl.setTnxnMode(true);

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

			Integer maxTnxDtlId = (Integer) commonService.getMaxValueByObjectAndColumn("SSItemTransactionDtl", "id");
			SSItemTransactionDtl csItemTransactionDtlTemp = (SSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionDtl", "id", maxTnxDtlId + "");

			// Location Update
			// Location Mst

			// added by taleb || check requisition or return and get data frm
			// related model
			List<TempItemLocation> tempLocationList = null;

			if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
					|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
				SubStoreRequisitionMst csReqMst = (SubStoreRequisitionMst) commonService.getAnObjectByAnyUniqueColumn(
						"SubStoreRequisitionMst", "requisitionNo", csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService.getObjectListByTwoColumn(
						"TempItemLocation", "uuid", csReqMst.getUuid(), "itemCode", csStoreTicketDtl.getItemId());
			} else if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
					|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
				SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService.getAnObjectByAnyUniqueColumn(
						"SSReturnSlipMst", "returnSlipNo", csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService.getObjectListByTwoColumn(
						"TempItemLocation", "uuid", returnSlipMst.getUuid(), "itemCode", csStoreTicketDtl.getItemId());
			}
			// end of block

			for (TempItemLocation tempLoc : tempLocationList) {

				SSItemLocationMst csItemLocationMstdb = ssItemLocationMstService.getSSItemLocationMst(
						tempLoc.getLocationId(), csStoreTicketDtl.getLedgerName(), csStoreTicketDtl.getItemId());

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id", tempLoc.getLocationId());

				// LEDGERNAME, ITEMCODE, LOCATIONID
				if (csItemLocationMstdb != null) {
					csItemLocationMstdb.setModifiedBy(userName);
					csItemLocationMstdb.setModifiedDate(new Date());

					if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
							|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb.getQuantity() - tempLoc.getQuantity());
					} else if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
							|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb.getQuantity() + tempLoc.getQuantity());
					}
					commonService.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					SSItemLocationMst csItemLocationMst = new SSItemLocationMst();
					csItemLocationMst.setItemCode(csStoreTicketDtl.getItemId());
					csItemLocationMst.setLedgerType(csStoreTicketDtl.getLedgerName());
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);

					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					commonService.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}

				Integer maxLocMstId = (Integer) commonService.getMaxValueByObjectAndColumn("SSItemLocationMst", "id");
				SSItemLocationMst csItemLocationMstTemp = (SSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("SSItemLocationMst", "id", maxLocMstId + "");

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

				if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
					csItemLocationDtl.setTnxnMode(false);
				} else if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
					csItemLocationDtl.setTnxnMode(true);
				}

				// set Item Location Mst Id
				if (csItemLocationMstdb != null) {
					csItemLocationDtl.setSsItemLocationMst(csItemLocationMstdb);
				} else {
					csItemLocationDtl.setSsItemLocationMst(csItemLocationMstTemp);
				}

				// set Transaction dtl info for get khathwise and location wise
				// item report
				csItemLocationDtl.setSsItemTransactionDtl(csItemTransactionDtlTemp);

				csItemLocationDtl.setStoreLocation(storeLocations);

				commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);
			}
		}
		/*
		 * if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP))
		 * { this.lsToSsLedgerUpdateForReturn(csStoreTicketDtlList, userName,
		 * roleName, authUser, dept, csStoreTicketMst); }
		 */
		if (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
			this.cnToSsLedgerUpdate(csStoreTicketDtlList, userName, roleName, authUser, dept, csStoreTicketMst);
		}
	}

	private void cnToSsLedgerUpdate(List<SSStoreTicketDtl> csStoreTicketDtlList, String userName, String roleName,
			AuthUser authUser, Departments dept, SSStoreTicketMst csStoreTicketMst) {
		for (SSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			SubStoreRequisitionMst ssm = subStoreRequisitionMstService
					.getSubStoreRequistionMstByRequisitionNo(csStoreTicketMst.getOperationId());
			ContractorRepresentive cr = contractorRepresentiveService.getContractorRep(ssm.getCreatedBy());
			PndJobDtl pj = pndJobDtlService.getPndJobDtl(csStoreTicketDtl.getItemId(), cr.getContractNo());
			pj.setRemainningQuantity(pj.getRemainningQuantity() - csStoreTicketDtl.getQuantity());
			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * csStoreTicketMst.getKhathId() + "");
			 */
			commonService.saveOrUpdateModelObjectToDB(pj);
			JobItemMaintenance jobItems = pndJobDtlService.getTotalJobItems(csStoreTicketDtl.getItemId(),
					cr.getContractNo());

			jobItems.setRemainningQuantity(jobItems.getRemainningQuantity() - csStoreTicketDtl.getQuantity());
			commonService.saveOrUpdateModelObjectToDB(jobItems);
		}
		// SSItemTransactionMst csItemTransactionMst = null;

	}

	@RequestMapping(value = "/cnViewInventoryItemList.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String cnViewInventoryItemList(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			List<ItemMaster> selectItemsFromDb = itemInventoryService.getItemListByCategoryId(invItem.getCategoryId());

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}
}
