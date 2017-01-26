package com.ibcs.desco.workshop.controller;

//@author Nasrin
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsAsBuiltDtl;
import com.ibcs.desco.workshop.model.WsCnAllocation;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
@RequestMapping(value = "/wsm")
@PropertySource("classpath:common.properties")
public class WsToCsTransformerMaterialsReturnController extends Constrants {

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	WSTransformerService wSTransformerService;

	@Value("${desco.returnslip.prefix}")
	private String descoReturnSlipNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/returnSlip/wsTransformerMaterialReturnSlipForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlip() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService.getDepartmentByDeptId(deptId);

			ContractorRepresentive contractorRepresentive = (ContractorRepresentive) commonService
					.getAnObjectByAnyUniqueColumn("ContractorRepresentive", "userId", userName);
			model.put("contractNo", contractorRepresentive.getContractNo());

			/*List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
			List<ItemCategory> catList = new ArrayList<ItemCategory>();
		for(ItemCategory category:categoryList){
			if(category.getCategoryId()==301){catList.add(category);}
			if(category.getCategoryId()==302){catList.add(category);}
		}*/
		
		List<WsAsBuiltDtl> jobCardTemplateList = wSTransformerService.getToBeReturnMaterialList(contractorRepresentive.getContractNo(),REPAIR_WORKS);
			//model.put("categoryList", catList);
			model.put("deptName", department.getDeptName());
			model.put("department", department);
			model.put("deptAddress", department.getAddress() + ", " + department.getContactNo());
			model.put("jobCardTemplateList", jobCardTemplateList);
			return new ModelAndView("workshop/transformerMaterials/wsToCsMaterialsReturnSlipForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("workshop/transformerMaterials/errorProject", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/returnSlip/getItemDataFromAsbuilt.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getItemDataFromAsbuilt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String itemCode = request.getParameter("itemCode");
		String workOrderNumber = request.getParameter("workOrderNumber");
		
		/*JobCardMst jobCardMst = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst",
						"contractNo", workOrderNumber);*/
		List<WsAsBuiltDtl> jobCardTemplateList = (List<WsAsBuiltDtl>) (Object) commonService
				.getObjectListByTwoColumn("WsAsBuiltDtl", "asBuiltMst.woNumber", workOrderNumber+"", "itemCode", itemCode);
		
	
		
		double qty=0.0;
		for(WsAsBuiltDtl job:jobCardTemplateList){
			qty+=job.getMaterialsInHand();
			
		}
		
		if (jobCardTemplateList.size()>0) {

			return  jobCardTemplateList.get(0).getItemCode() + ":" + jobCardTemplateList.get(0).getItemName() + ":" +jobCardTemplateList.get(0).getUom() + ":" + qty;
		} else {
			return "";
		}
	}

	@RequestMapping(value = "/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipMstSave(Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		ContractorRepresentive contractorRep = contractorRepresentiveService.getContractorRep(authUser.getUserid());
		Contractor contractor = contractorRepresentiveService.getContractorByContractNo(contractorRep.getContractNo());
		DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
				contractor.getKhathId() + "");

		ReturnSlipMst returnSlipMst = new ReturnSlipMst();
		returnSlipMst.setReceiveFrom(returnSlipMstDtl.getReceiveFrom());
		returnSlipMst.setWorkOrderDate(contractor.getContractDate());
		returnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(contractor.getKhathId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setUuid(UUID.randomUUID().toString());
		returnSlipMst.setSndCode(department.getDescoCode());
		returnSlipMst.setSenderDeptName(department.getDeptName());
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setReturnTo(ContentType.CENTRAL_STORE.toString());
		returnSlipMst.setSenderStore(ContentType.WS_CN_XFORMER_REPAIR_MATERIALS.toString());
		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addWsCsReturnDtls(returnSlipMstDtl, returnSlipMst, roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
		}

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
				"returnSlipNo", returnSlipMst.getReturnSlipNo());
		return "redirect:/wsm/returnSlipShow.do?id=" + returnSlipMstdb.getId();

	}

	public boolean addWsCsReturnDtls(ReturnSlipMstDtl returnSlipMstDtl, ReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> newServiceableList = null;

		List<Double> reccoveryServicableList = null;

		List<Double> unServiceableList = null;

		List<Double> totalReturnList = null;

		List<String> remarksList = null;

		if (returnSlipMstDtl.getItemCode() != null) {
			itemCodeList = returnSlipMstDtl.getItemCode();
		}

		if (returnSlipMstDtl.getDescription() != null) {
			descriptionList = returnSlipMstDtl.getDescription();
		}

		if (returnSlipMstDtl.getUom() != null) {
			uomList = returnSlipMstDtl.getUom();
		}

		if (returnSlipMstDtl.getQtyNewServiceable() != null) {
			newServiceableList = returnSlipMstDtl.getQtyNewServiceable();
		}

		if (returnSlipMstDtl.getQtyRecServiceable() != null) {
			reccoveryServicableList = returnSlipMstDtl.getQtyRecServiceable();
		}

		if (returnSlipMstDtl.getQtyUnServiceable() != null) {
			unServiceableList = returnSlipMstDtl.getQtyUnServiceable();
		}

		if (returnSlipMstDtl.getTotalReturn() != null) {
			totalReturnList = returnSlipMstDtl.getTotalReturn();
		}

		if (returnSlipMstDtl.getRemarks() != null) {
			remarksList = returnSlipMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on CN_SS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(WS_CS_RETURN);

		// get All State code which define for local to returnSlip process
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get hierarchy information for minimum state of ReturnSlip operation
		ApprovalHierarchy approvalHierarchy = null;
		String[] roles = null;

		if (stateCodes.length > 0) {
			approvalHierarchy = approvalHierarchyService.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN,
					stateCodes[0].toString());
			roles = approvalHierarchy.getRoleName().split(",");
		}

		// login person have permission for ReturnSlip process??
		int flag = 0; // 0 = no permission
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1; // 1 = has permission
				break;
			}
		}

		// do next transaction if the role has permission for ReturnSlip
		// process ??

		if (flag == 1) {

			// ReturnSlip Items Details insert process Start from Here
			if (itemCodeList != null && itemCodeList.size() > 0) {

				// Saving master information of ReturnSlip to Master Table
				String descoDeptCode = department.getDescoCode();
				String returnSlipNo = commonService.getOperationIdByPrefixAndSequenceName(descoReturnSlipNoPrefix,
						descoDeptCode, separator, "WS_CS_X_RS_SEQ");
				returnSlipMst.setReturnSlipNo(returnSlipNo);
				commonService.saveOrUpdateModelObjectToDB(returnSlipMst);

				ReturnSlipMst returnSlipMstDb = (ReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo", returnSlipMst.getReturnSlipNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					ReturnSlipDtl returnSlipDtl = new ReturnSlipDtl();

					if (!itemCodeList.isEmpty()) {
						returnSlipDtl.setItemCode(itemCodeList.get(i));
					} else {
						returnSlipDtl.setItemCode("");
					}

					if (!descriptionList.isEmpty()) {
						returnSlipDtl.setDescription(descriptionList.get(i));
					} else {
						returnSlipDtl.setDescription("");
					}

					if (!uomList.isEmpty()) {
						returnSlipDtl.setUom(uomList.get(i));
					} else {
						returnSlipDtl.setUom("");
					}

					if (newServiceableList.size() > 0) {
						returnSlipDtl.setQtyNewServiceable(newServiceableList.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!reccoveryServicableList.isEmpty()) {
						returnSlipDtl.setQtyRecServiceable(reccoveryServicableList.get(i));
					} else {
						returnSlipDtl.setQtyRecServiceable(0.0);
					}

					if (!unServiceableList.isEmpty()) {
						returnSlipDtl.setQtyUnServiceable(unServiceableList.get(i));
					} else {
						returnSlipDtl.setQtyUnServiceable(0.0);
					}

					if (!totalReturnList.isEmpty()) {
						returnSlipDtl.setTotalReturn(totalReturnList.get(i));

					} else {
						returnSlipDtl.setTotalReturn(0.0);
					}

					if (!remarksList.isEmpty()) {
						returnSlipDtl.setRemarks(remarksList.get(i));
					} else {
						returnSlipDtl.setRemarks("");
					}

					// set id null for add all items in db and id set from auto
					// number
					returnSlipDtl.setId(null);

					// set RequisitionNo to each detail row using previous auto
					// id, not from front-end
					returnSlipDtl.setReturnSlipNo(returnSlipMst.getReturnSlipNo());

					returnSlipDtl.setCreatedBy(returnSlipMst.getCreatedBy());
					returnSlipDtl.setCreatedDate(returnSlipMst.getCreatedDate());
					returnSlipDtl.setActive(true);
					returnSlipDtl.setReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addReturnSlipHierarchyHistory(returnSlipMst, approvalHierarchy, stateCodes, roleName, department,
						authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addReturnSlipHierarchyHistory(ReturnSlipMst returnSlipMst, ApprovalHierarchy approvalHierarchy,
			Integer[] stateCodes, String roleName, Departments department, AuthUser authUser) {
		WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new WsCsReturnSlipApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(returnSlipMst.getReturnSlipNo());
		approvalHierarchyHistory.setOperationName(WS_CS_RETURN);
		approvalHierarchyHistory.setCreatedBy(returnSlipMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(returnSlipMst.getCreatedDate());

		if (stateCodes.length > 0) {
			// All time start with 1st
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateName(approvalHierarchy.getStateName());
		}
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/returnSlip/List.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReturnnListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<WsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus("WsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList.get(i).getOperationId());
		}
		List<ReturnSlipMst> returnSlipMstList = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("ReturnSlipMst", "returnSlipNo", operationId);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/transformerMaterials/wsToCsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_WS")) {
			return new ModelAndView("workshop/transformerMaterials/wsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_CS")) {
			return new ModelAndView("workshop/transformerMaterials/csReturnSlipList", model);
		} else {
			model.put("errorMsg",
					"You are not authorized to view this List. Please Login as Development and Project's users.");
			return new ModelAndView("workshop/transformerMaterials/errorProject", model);
		}

	}

	@RequestMapping(value = "/returnSlip/pageShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShowCS(ReturnSlipMst returnSlipMst) {

		return returnSlipShow(returnSlipMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/returnSlipShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipShow(ReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
				"id", returnSlipMst.getId() + "");

		/*
		 * The following four lines are added by Ihteshamul Alam. It will show
		 * Username instead of userid
		 */
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
				"userid", userrole);
		returnSlipMstdb.setCreatedBy(createBy.getName());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo", operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;
		String currentStatus = "";

		List<WsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus("WsCsReturnSlipApprovalHierarchyHistory",
						WS_CS_RETURN, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(rsApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<WsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus("WsCsReturnSlipApprovalHierarchyHistory",
						WS_CS_RETURN, operationId, OPEN);

		int currentStateCode = rsApprovalHierarchyHistoryOpenList.get(rsApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

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
				.getApprovalHierarchyByOperationNameAndRoleName(WS_CS_RETURN, roleNameList);

		// Send To Upper Authority of same department
		nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs.add(approveHeirchyList.get(countBackStateCodes));
			}
		}
		String returnStateCode = "";
		// button Name define
		if (!rsApprovalHierarchyHistoryOpenList.isEmpty() && rsApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = rsApprovalHierarchyHistoryOpenList.get(rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// Decide for return or not
			returnStateCode = rsApprovalHierarchyHistoryOpenList.get(rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations", "storeCode", "CS", "parentId");
		String locationOptions = commonService.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);

		model.put("returnStateCode", returnStateCode);
		model.put("returnSlipMst", returnSlipMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);

		model.put("returnSlipDtlList", returnSlipDtlList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/transformerMaterials/wsToCsReturnSlipShow", model);
		} else if (rolePrefix.equals("ROLE_WS")) {
			List<String> itemCodes = new ArrayList<String>();
			for(ReturnSlipDtl returnSlipDtl:returnSlipDtlList){
				itemCodes.add(returnSlipDtl.getItemCode());
				
			}
			List<TransformerRegister> transformerRegisterList = wSTransformerService
					.getRegisteredTransformerListForReturn(itemCodes, returnSlipMstdb.getWorkOrderNo());
			model.put("transformerList", transformerRegisterList);
			
			return new ModelAndView("workshop/transformerMaterials/wsReturnSlipShow", model);
		} else if (rolePrefix.equals("ROLE_CS")) {
			// Get all Location under CS || Added by Ashid
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations", "storeCode", "CS", "parentId");
			String locationListJson = commonService.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);

			return new ModelAndView("workshop/transformerMaterials/csReturnSlipShow", model);

		} else {
			return null;
		}
	}

	@RequestMapping(value = "/returnSlip/itemReturnSlipApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String itemReturnSlipApprovedGET(Model model, ReturnSlipMstDtl returnSlipMstDtl) {

		return returnSlipForItemSubmitApproved(model, returnSlipMstDtl);

	}

	@RequestMapping(value = "/returnSlip/itemReturnSlipApproved.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String itemReturnSlipApprovedPOST(Model model, ReturnSlipMstDtl returnSlipMstDtl) {

		return returnSlipForItemSubmitApproved(model, returnSlipMstDtl);
	}

	// Return Slip (RS) Approving process
	// @SuppressWarnings("unchecked")
	// @RequestMapping(value = "/itemReturnSlipApproved.do", method =
	// RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@SuppressWarnings("unchecked")
	public String returnSlipForItemSubmitApproved(Model model, ReturnSlipMstDtl returnSlipMstDtl) {
		
		boolean departmentChange=false;
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		String operationId = returnSlipMstDtl.getReturnSlipNo();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService.getDepartmentByDeptId(deptId);

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("") || returnSlipMstDtl.getReturn_state().length() > 0) {
			List<WsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn("WsCsReturnSlipApprovalHierarchyHistory", "operationId",
							returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current approval hierarchy history by State Code
			WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (WsCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn("WsCsReturnSlipApprovalHierarchyHistory", "id", ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(returnSlipMstDtl.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsCsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN, returnSlipMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer.parseInt(returnSlipMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(returnSlipMstDtl.getReturnSlipNo());
			approvalHierarchyHistoryNextState.setOperationName(approvalHierarchyNextSate.getOperationName());
			approvalHierarchyHistoryNextState.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState.setApprovalHeader(approvalHierarchyNextSate.getApprovalHeader());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setcDeptName(department.getDeptName());
			approvalHierarchyHistoryNextState.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {
			// get Item List against operation No
			

			ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
					"returnSlipNo", operationId);

			/*
			 * returnSlipDtlList will be used if dtl is changed during approval.
			 * But now Dtl is not editable. So returnSlipDtlList is
			 * intentionally blocked
			 */

			/*
			 * List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>)
			 * (Object) commonService .getObjectListByAnyColumn("ReturnSlipDtl",
			 * "returnSlipNo", operationId);
			 */

			/*
			 * Get All State Codes from Approval Hierarchy and sort Descending
			 * order for highest State Code.
			 */
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_CS_RETURN);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<WsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn("WsCsReturnSlipApprovalHierarchyHistory", "operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (WsCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn("WsCsReturnSlipApprovalHierarchyHistory", "id", ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo", operationId);

			List<Double> qtyNewServiceableList = new ArrayList<Double>();
			List<Double> qtyRecServiceableList = new ArrayList<Double>();
			List<Double> qtyUnServiceableList = new ArrayList<Double>();
			List<Double> totalReturnList = new ArrayList<Double>();

			String rolePrefix = roleName.substring(0, 7);

			if (rolePrefix.equals("ROLE_WS")) {

				qtyNewServiceableList = returnSlipMstDtl.getQtyNewServiceable();
				qtyRecServiceableList = returnSlipMstDtl.getQtyRecServiceable();
				qtyUnServiceableList = returnSlipMstDtl.getQtyUnServiceable();
				totalReturnList = returnSlipMstDtl.getTotalReturn();

				String other = returnSlipMstDtl.getOther();
				if (returnSlipMst.getOthers() == null) {
					returnSlipMst.setOthers(other.toString());
					commonService.saveOrUpdateModelObjectToDB(returnSlipMst);
				}

				if (totalReturnList != null) {
					int p = 0;
					for (ReturnSlipDtl returnSlipDtl : returnSlipDtlList) {
						Double qtyNewServiceable = qtyNewServiceableList.get(p);
						Double qtyRecServiceable = qtyRecServiceableList.get(p);
						Double qtyUnServiceable = qtyUnServiceableList.get(p);
						Double totalReturn = totalReturnList.get(p);
						returnSlipDtl.setQtyNewServiceable(qtyNewServiceable);
						returnSlipDtl.setQtyRecServiceable(qtyRecServiceable);
						returnSlipDtl.setQtyUnServiceable(qtyUnServiceable);
						returnSlipDtl.setTotalReturn(totalReturn);
						returnSlipDtl.setModifiedBy(userName);
						returnSlipDtl.setModifiedDate(new Date());
						commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
						// dtlList.add(csReqDtl);
						p++;
					}
				}
			}
			// searching next State code and decision for send
			// this process to next person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN, nextStateCode + "");

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {
						//
					} else {
						Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
								"com.ibcs.desco.admin.model.Roles", "role", approvalHierarchy.getRoleName());
						List<AuthUser> userList = (List<AuthUser>) (Object) commonService.getObjectListByAnyColumn(
								"com.ibcs.desco.admin.model.AuthUser", "roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService.getAnObjectByAnyUniqueColumn("Departments",
								"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart.getDeptName());
						if(!commonService.getAuthRoleName().equals(ROLE_WO_CN_USER)){
						departmentChange=true;
						}
					}

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
				// process will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(WS_CS_RETURN, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(returnSlipMstDtl.getJustification());
					approvalHierarchyHistory.setApprovalHeader(approvalHierarchy.getApprovalHeader());

					commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst", "operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser.getName());
						approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						returnSlipMst.setApproved(true);
						commonService.saveOrUpdateModelObjectToDB(returnSlipMst);

						// now insert data in store ticket mst and history
						CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
						csStoreTicketMst.setStoreTicketType(WS_CS_RETURN);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setWorkOrderNo(returnSlipMst.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst.getCreatedBy());
						csStoreTicketMst.setReturnFor(returnSlipMst.getReceiveFrom());
						csStoreTicketMst.setReceivedBy(userName);
						csStoreTicketMst.setFlag(false);
						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst.getSndCode());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode, separator, "CS_ST_SEQ");

						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

						// Get All Approval Hierarchy on CS_STORE_TICKET
						List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
								.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

						Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb.size()];
						for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
							sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i).getStateCode();
						}
						Arrays.sort(sStoreTicketStateCodes);

						// get approve hierarchy for last state
						ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
								.getApprovalHierarchyByOperationNameAndSateCode(CS_STORE_TICKET,
										sStoreTicketStateCodes[0].toString());

						StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

						storeTicketApprovalHierarchyHistory.setActRoleName(storeTicketpprovalHierarchy.getRoleName());
						storeTicketApprovalHierarchyHistory.setOperationId(operationId);
						storeTicketApprovalHierarchyHistory.setTicketNo(csStoreTicketMst.getTicketNo());
						storeTicketApprovalHierarchyHistory.setOperationName(CS_STORE_TICKET);
						storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
						storeTicketApprovalHierarchyHistory.setDeptId(deptId);
						storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
						if (stateCodes.length > 0) {
							storeTicketApprovalHierarchyHistory.setStateCode(sStoreTicketStateCodes[0]);
							storeTicketApprovalHierarchyHistory
									.setStateName(storeTicketpprovalHierarchy.getStateName());
						}
						storeTicketApprovalHierarchyHistory.setStatus(OPEN);
						storeTicketApprovalHierarchyHistory.setActive(true);
						commonService.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						//model.addAttribute("operationId", operationId);

						return "redirect:/wsm/wsToCsReturnSlipReport.do?srNo="
							+ operationId;
					}

				}
			}
		}
		if(departmentChange){
			return "redirect:/wsm/showReturnReportWS.do?srNo="
							+ operationId;
			
		}else{
		return "redirect:/wsm/returnSlip/List.do";}
	}

	@RequestMapping(value = "/showReturnReportWS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportWS(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("operationId", srNo);		
		return new ModelAndView("workshop/transformerMaterials/showRequisitionReportWS",
				model);
	}
	@RequestMapping(value = "/wsToCsReturnSlipReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsToCsReturnSlipReport(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("workshop/transformerMaterials/wsToCsReturnSlipReport", model);
	}
	
	@RequestMapping(value = "/returnSlip/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		@SuppressWarnings("unchecked")
		List<WsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (WsCsReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn("WsCsReturnSlipApprovalHierarchyHistory", "id", ids[0] + "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_RETURN, currentStateCode + "");
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
		WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_RETURN, nextStateCode + "");
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

		return "redirect:/ws/returnSlip/List.do";
	}

	@RequestMapping(value = "/returnSlip/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {
		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String backStateCode = returnSlipMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		@SuppressWarnings("unchecked")
		List<WsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (WsCsReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn("WsCsReturnSlipApprovalHierarchyHistory", "id", ids[0] + "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_RETURN, currentStateCode + "");
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
		WsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new WsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_CS_RETURN, backStateCode + "");
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

		return "redirect:/ws/returnSlip/List.do";
	}


	
	// @RequestMapping(value = "/cn/returnSlip/cnViewInventoryItem.do", method =
	// RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/wsReturnViewInventoryItemCategory.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String wsReturnViewInventoryItemCategory(@RequestBody String json) throws Exception {
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

	@RequestMapping(value = "/returnSlip/wsReturnViewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String wsReturnViewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			CSItemTransactionMst invItem = gson.fromJson(json, CSItemTransactionMst.class);
			ItemMaster selectItemFromDb = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn("ItemMaster",
					"id", invItem.getId().toString());
					/*
					 * itemInventoryService
					 * .getInventoryItemById(invItem.getItemCode());
					 */

			// CSItemTransactionMst centralStoreItems =
			// csItemTransactionMstService.getCSItemTransectionMst(selectItemFromDb.getItemId(),invItem.getKhathId(),invItem.getLedgerName());
			/*
			 * CSItemTransactionMst centralStoreItems =
			 * csItemTransactionMstService
			 * .getCSItemTransectionMstForss(selectItemFromDb.getItemId(),
			 * invItem.getKhathId());
			 * 
			 * selectItemFromDb .setCurrentStock(centralStoreItems != null ?
			 * centralStoreItems .getQuantity() : 0.0);
			 */

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}
	@RequestMapping(value = "/returnSlip/validContractor.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public boolean validContractor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean result = true;
		String contractNo = request.getParameter("contractNo").trim();
		WsCnAllocation allocation = (WsCnAllocation) commonService.getAnObjectByAnyUniqueColumn("WsCnAllocation",
				"workOrderNo", contractNo);

		if (allocation.getPreventiveQty1P() == 0 && allocation.getPreventiveQty3P() == 0
				&& allocation.getRepairQty1P() == 0 && allocation.getRepairQty3P() == 0) {
			result = false;
		}

		return result;

	}

}
