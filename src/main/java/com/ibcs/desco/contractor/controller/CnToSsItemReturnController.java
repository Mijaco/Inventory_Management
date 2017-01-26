package com.ibcs.desco.contractor.controller;

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

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CnSsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.bean.SSReturnSlipMstDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

//@author nasrin
//@modified by: Ihteshamul Alam

@Controller
@PropertySource("classpath:common.properties")
public class CnToSsItemReturnController extends Constrants {


	@Autowired
	CnSsReturnSlipApprovalHierarchyHistoryService cnSsReturnSlipApprovalHierarchyHistoryService;

	@Autowired
	SSReturnSlipMstService ssReturnSlipMstService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	SubStoreRequisitionDtlService subStoreRequisitionDtlService;

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
	PndJobDtlService pndJobDtlService;

	@Value("${desco.returnslip.prefix}")
	private String descoReturnSlipNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;	
	
	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/cn/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipMstSave(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		ContractorRepresentive contractorRep=contractorRepresentiveService.getContractorRep(authUser.getUserid());
		Contractor contractor=contractorRepresentiveService.getContractorByContractNo(contractorRep.getContractNo());
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						contractor.getKhathId() + "");

		SSReturnSlipMst returnSlipMst = new SSReturnSlipMst();

		returnSlipMst.setReceiveFrom(returnSlipMstDtl.getReceiveFrom());
		returnSlipMst.setWorkOrderDate(returnSlipMstDtl.getWorkOrderDate());
		returnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(contractor.getKhathId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setSndCode(department.getDescoCode());

	
		// set current date time as RequisitionDate. GUI date is not used here.
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setReturnTo(ContentType.SUB_STORE.toString());
		returnSlipMst.setSenderStore(ContentType.CONTRACTOR.toString());
		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addStoreTicketDtls(returnSlipMstDtl, returnSlipMst,
				roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
		}
		// return "contractor/ssToCsRequisitionList";
		SSReturnSlipMst returnSlipMstdb = (SSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "returnSlipNo",
						returnSlipMst.getReturnSlipNo());
		return "redirect:/cn/returnSlip/pageShow.do?id="
				+ returnSlipMstdb.getId();

	}

	public boolean addStoreTicketDtls(SSReturnSlipMstDtl returnSlipMstDtl,
			SSReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;

		List<String> uomList = null;

		List<Double> newServiceableList = null;

		List<Double> reccoveryServicableList = null;

		List<Double> unServiceableList = null;

		List<Double> totalReturnList = null;

		List<String> remarksList = null;

		if (returnSlipMstDtl.getItemCode() != null) {
			itemCodeList = returnSlipMstDtl.getItemCode();
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
				.getApprovalHierarchyByOperationName(CN_SS_RETURN_SLIP);

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
			approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_SS_RETURN_SLIP, stateCodes[0].toString());
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
				// sequence remove by taleb
				/*String returnSlipNo = commonService.getOperationIdByPrefixAndSequenceName(descoReturnSlipNoPrefix, descoDeptCode, separator, "SS_CS_RS_SEQ");*/
				// sequence add by taleb
				String returnSlipNo = commonService
						.getOperationIdByPrefixAndSequenceName(descoReturnSlipNoPrefix, 
						descoDeptCode, separator, "WS_CS_X_RS_SEQ");
				returnSlipMst.setReturnSlipNo(returnSlipNo);
				commonService.saveOrUpdateModelObjectToDB(returnSlipMst);

				SSReturnSlipMst returnSlipMstDb = (SSReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
								"returnSlipNo", returnSlipMst.getReturnSlipNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					SSReturnSlipDtl returnSlipDtl = new SSReturnSlipDtl();

					if (!itemCodeList.isEmpty()) {
						returnSlipDtl.setItemCode(itemCodeList.get(i));
					} else {
						returnSlipDtl.setItemCode("");
					}

					/*if (!descriptionList.isEmpty()) {
						returnSlipDtl.setDescription(descriptionList.get(i));
					} else {
						returnSlipDtl.setDescription(descriptionList.get(i));
					}*/

					if (!uomList.isEmpty()) {
						returnSlipDtl.setUom(uomList.get(i));
					} else {
						returnSlipDtl.setUom("");
					}

					if (newServiceableList.size()>0) {
						returnSlipDtl.setQtyNewServiceable(newServiceableList
								.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!reccoveryServicableList.isEmpty()) {
						returnSlipDtl
								.setQtyRecServiceable(reccoveryServicableList.get(i));
					} else {
						returnSlipDtl.setQtyRecServiceable(0.0);
					}

					if (!unServiceableList.isEmpty()) {
						returnSlipDtl.setQtyUnServiceable(unServiceableList
								.get(i));
					} else {
						returnSlipDtl.setQtyUnServiceable(0.0);
					}

					if (!totalReturnList.isEmpty()) {
						returnSlipDtl
								.setTotalReturn(totalReturnList.get(i));

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
					returnSlipDtl.setReturnSlipNo(returnSlipMst
							.getReturnSlipNo());

					returnSlipDtl.setCreatedBy(returnSlipMst.getCreatedBy());
					returnSlipDtl
							.setCreatedDate(returnSlipMst.getCreatedDate());
					returnSlipDtl.setActive(true);
					returnSlipDtl.setReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addReturnSlipHierarchyHistory(returnSlipMst, approvalHierarchy,
						stateCodes, roleName, department, authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addReturnSlipHierarchyHistory(SSReturnSlipMst returnSlipMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new CnSsReturnSlipApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);

		// Added by Taleb
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		approvalHierarchyHistory
				.setOperationId(returnSlipMst.getReturnSlipNo());
		approvalHierarchyHistory.setOperationName(CN_SS_RETURN_SLIP);
		approvalHierarchyHistory.setCreatedBy(returnSlipMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(returnSlipMst.getCreatedDate());

		if (stateCodes.length > 0) {
			// All time start with 1st
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateName(approvalHierarchy
					.getStateName());
		}
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/List.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReturnnListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<CnSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		/*
		 * String[] operationId = new
		 * String[storeSlipApprovalHierarchyHistoryList .size()]; for (int i =
		 * 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
		 * operationId[i] = storeSlipApprovalHierarchyHistoryList.get(i)
		 * .getOperationId(); }
		 */
		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId) != null) {
			returnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CO")) {
			return new ModelAndView("contractor/cnToSsReturnSlipList", model);
		}
		else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("contractor/cnToSsReturnSlipList", model);
		} else {
			return new ModelAndView("contractor/cnToSsReturnSlipList", model);
		}

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/cn/returnSlip/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreReturnnForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);		
		ContractorRepresentive contractorRep=contractorRepresentiveService.getContractorRep(authUser.getUserid());
				
		if(!contractorRepresentiveService.getValidContractor(contractorRep.getContractNo())){
			model.put("msg", "This Contract is not valid");
			return new ModelAndView("contractor/contractor", model);
		}else if(contractorRep==null){
				model.put("msg", "Your Contract validity date is expired");
				return new ModelAndView("contractor/contractor", model);
			}
		List<PndJobDtl> pndJobDtl = pndJobDtlService.getJobItemList(contractorRep.getContractNo());
		model.put("jobItemList", pndJobDtl);
		model.put("contractNo", contractorRep.getContractNo());
		return new ModelAndView("contractor/cnToSsReturnSlipForm", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/pageShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShow(SSReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		SSReturnSlipMst returnSlipMstdb = ssReturnSlipMstService
				.getSSReturnSlipMst(returnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory",
						CN_SS_RETURN_SLIP, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory",
						CN_SS_RETURN_SLIP, operationId, OPEN);

		int currentStateCode = rsApprovalHierarchyHistoryOpenList.get(
				rsApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

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
						CN_SS_RETURN_SLIP, roleNameList);

		// Send To Upper Authority of same department
		nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
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
		if (!rsApprovalHierarchyHistoryOpenList.isEmpty()
				&& rsApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = rsApprovalHierarchyHistoryOpenList.get(
					rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = rsApprovalHierarchyHistoryOpenList.get(
					rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_SS_RETURN_SLIP, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);

		model.put("returnSlipMst", returnSlipMstdb);

		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);

		model.put("returnSlipDtlList", returnSlipDtlList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CO")) {
			return new ModelAndView("contractor/cnReturnSlipShow", model);
		}
		else if (rolePrefix.equals("ROLE_SS")) {
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "SS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);
			return new ModelAndView("contractor/cnToSsReturnSlipShow", model);
		} else {
			return new ModelAndView("contractor/cnReturnSlipShow", model);
		}
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/pageShowCn.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShowCn(SSReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		SSReturnSlipMst returnSlipMstdb = ssReturnSlipMstService
				.getSSReturnSlipMst(returnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;
		List<ApprovalHierarchy> nextManReqProcs1 = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory",
						CN_PD_SS_RETURN, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory",
						CN_PD_SS_RETURN, operationId, OPEN);

		int currentStateCode = rsApprovalHierarchyHistoryOpenList.get(
				rsApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

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
						CN_PD_SS_RETURN, roleNameList);

		// Send To Upper Authority of same department
		nextManReqProcs = new ArrayList<ApprovalHierarchy>();
		nextManReqProcs1 = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}
		
		//forked and modified by Shimul
		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs1.add(approveHeirchyList.get(countStateCodes));
			}
		}
		
		//forked & modified by Shimul for Send To
		for (ApprovalHierarchy approvalHierarchy : nextManReqProcs1) {
			Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					approvalHierarchy.getRoleName());
			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByTwoColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid",
							role.getRole_id() + "", "deptId", authUser.getDeptId());
			approvalHierarchy.setAuthUser(authUserList);
		}

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		List<ApprovalHierarchy> backManRcvProcs1 = new ArrayList<ApprovalHierarchy>();
		
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}
		
		//forked & modified by Shimul
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs1
				.add(approveHeirchyList.get(countBackStateCodes));
			}
		}
		
		//forked & modified by Shimul for Back To
		for (ApprovalHierarchy approvalHierarchy : backManRcvProcs1) {
			Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					approvalHierarchy.getRoleName());
			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByTwoColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid",
							role.getRole_id() + "", "deptId", authUser.getDeptId());
			approvalHierarchy.setAuthUser(authUserList);
		}

		String returnStateCode = "";
		// button Name define
		if (!rsApprovalHierarchyHistoryOpenList.isEmpty()
				&& rsApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = rsApprovalHierarchyHistoryOpenList.get(
					rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = rsApprovalHierarchyHistoryOpenList.get(
					rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_RETURN, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);

		model.put("returnSlipMst", returnSlipMstdb);

		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);
		
		//Added by Shimul
		model.put("nextManRcvProcs1", nextManReqProcs1);
		model.put("backManRcvProcs1", backManRcvProcs1);

		model.put("returnSlipDtlList", returnSlipDtlList);

//		String rolePrefix = roleName.substring(0, 7);
		if (roleName.contains("ROLE_CO")) {
			return new ModelAndView("contractor/cnReturnSlipShow", model);
		}
		else if (roleName.contains("ROLE_SS")) {
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "SS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);
			return new ModelAndView("contractor/cnToSsReturnSlipShowCn", model);
		} else {
			return new ModelAndView("contractor/cnReturnSlipShow", model);
		}
	}

	// Return Slip (RS) Approving process
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/cn/itemReturnSlipApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApprovedCn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
					.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_SS_RETURN_SLIP, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_SS_RETURN_SLIP,
							returnSlipMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(returnSlipMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(returnSlipMstDtl
					.getReturnSlipNo());
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
			String operationId = returnSlipMstDtl.getReturnSlipNo();

			SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
							"returnSlipNo", operationId);

			List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
							operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN_SS_RETURN_SLIP);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			/*
			 * CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory =
			 * lsCsReturnSlipApprovalHierarchyHistoryService
			 * .getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
			 */

			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnSsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnSsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

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
									CN_SS_RETURN_SLIP, nextStateCode + "");

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
									CN_SS_RETURN_SLIP, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(returnSlipMstDtl
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
					

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
						
						returnSlipMst.setApproved(true);
						commonService.saveOrUpdateModelObjectToDB(returnSlipMst);


						// now we have to insert data in store ticket mst and
						// history
						SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						csStoreTicketMst.setStoreTicketType(CN_SS_RETURN_SLIP);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setIssuedTo(department.getDeptName());
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						csStoreTicketMst.setReturnFor(returnSlipMst
								.getCreatedBy());
						csStoreTicketMst.setReceivedBy(userName);

						// csStoreTicketMst.setIssuedFor(returnSlipMst
						// .getIdenterDesignation());
						csStoreTicketMst.setFlag(false);

						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst
								.getSndCode());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(descoStoreTicketNoPrefix, descoDeptCode, separator, "SS_ST_SEQ");;
						
						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService
								.saveOrUpdateModelObjectToDB(csStoreTicketMst);

						//

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
						storeTicketApprovalHierarchyHistory
								.setTicketNo(csStoreTicketMst.getTicketNo());
						storeTicketApprovalHierarchyHistory
								.setOperationName(SS_STORE_TICKET);
						storeTicketApprovalHierarchyHistory
								.setCreatedBy(userName);
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

						model.addAttribute("operationId", operationId);

						return "contractor/cnToSsReturnSlipReport";

					}

				}
			}
		}
		return "redirect:/cn/returnSlip/List.do";
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/cn/itemReturnSlipApprovedCn.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApproved(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
					.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_RETURN, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_RETURN,
							returnSlipMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(returnSlipMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(returnSlipMstDtl
					.getReturnSlipNo());
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
			String operationId = returnSlipMstDtl.getReturnSlipNo();

			SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
							"returnSlipNo", operationId);

			List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
							operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN_PD_SS_RETURN);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			/*
			 * CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory =
			 * lsCsReturnSlipApprovalHierarchyHistoryService
			 * .getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
			 */

			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnSsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnSsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

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
									CN_PD_SS_RETURN, nextStateCode + "");

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
									CN_PD_SS_RETURN, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(returnSlipMstDtl
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
					

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
						
						returnSlipMst.setApproved(true);
						commonService.saveOrUpdateModelObjectToDB(returnSlipMst);


						// now we have to insert data in store ticket mst and
						// history
						SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						csStoreTicketMst.setStoreTicketType(CN_PD_SS_RETURN);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setIssuedTo(department.getDeptName());
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						csStoreTicketMst.setReturnFor(returnSlipMst
								.getCreatedBy());
						csStoreTicketMst.setReceivedBy(userName);

						// csStoreTicketMst.setIssuedFor(returnSlipMst
						// .getIdenterDesignation());
						csStoreTicketMst.setFlag(false);

						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst
								.getSndCode());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(descoStoreTicketNoPrefix, descoDeptCode, separator, "SS_ST_SEQ");;
						
						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService
								.saveOrUpdateModelObjectToDB(csStoreTicketMst);

						//

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
						storeTicketApprovalHierarchyHistory
								.setTicketNo(csStoreTicketMst.getTicketNo());
						storeTicketApprovalHierarchyHistory
								.setOperationName(SS_STORE_TICKET);
						storeTicketApprovalHierarchyHistory
								.setCreatedBy(userName);
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

						return "redirect:/cn/cnToSsReturnSlipReport.do?operationId="+operationId;

					}

				}
			}
		}
		return "redirect:/ls/returnSlip/List.do";
	}
	
	@RequestMapping( value="/cn/cnToSsReturnSlipReport.do", method=RequestMethod.GET )
	public ModelAndView cnToSsReturnSlipReport( String operationId ) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", operationId);
		return new ModelAndView("contractor/cnToSsReturnSlipReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String contractorItemSendTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
				.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_SS_RETURN_SLIP, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_SS_RETURN_SLIP, nextStateCode + "");
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

		return "redirect:/cn/returnSlip/List.do";
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/sendToCn.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String contractorItemSendToCn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
				.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, nextStateCode + "");
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

		return "redirect:/ls/returnSlip/List.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String contractorItemBackTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {
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

		if( returnSlipMstDtl.getReturnTo().equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {
			return this.returnSlipbackToSS(returnSlipMstDtl, model, department);
		}
		
		List<CnCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnCsReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnCsReturnSlipApprovalHierarchyHistory", "id",
						ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_CS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_CS_RETURN, backStateCode + "");
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

		return "redirect:/ls/returnSlip/List.do";
	}
	
	@SuppressWarnings("unchecked")
	public String returnSlipbackToSS( SSReturnSlipMstDtl returnSlipMstDtl, Model model, Departments department ) {
		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String backStateCode = returnSlipMstDtl.getStateCode();
		
		String userName = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		
		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnSsReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnSsReturnSlipApprovalHierarchyHistory", "id",
						ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_SS_RETURN_SLIP, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_SS_RETURN_SLIP, backStateCode + "");
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

		return "redirect:/cn/returnSlip/List.do";
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/backToCn.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String contractorItemBackToCn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {
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

		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
				.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, backStateCode + "");
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

		return "redirect:/ls/returnSlip/List.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/rsSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipSerarch(
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		String operationId = returnSlipMstDtl.getReturnSlipNo();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<CnSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		List<String> opeId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			opeId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		if (ssReturnSlipMstService.listSSReturnSlipMstByOperationIdList(opeId) != null) {
			returnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(opeId);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("contractor/cnToSsReturnSlipList", model);
		} else {
			return new ModelAndView("contractor/cnReturnSlipList", model);
		}

	}
	@RequestMapping(value = "/cn/returnSlip/cnViewInventoryItem.do", method = RequestMethod.POST)
	//@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String cnViewInventoryItem(@RequestBody String json) throws Exception {
		String toJson = "";
		String s=json.substring(1, json.length()-1);
			String[] strValue=s.split(",");
			String itemId[]=strValue[0].split(":");
			String contractId=strValue[1].toString();
			PndJobDtl selectItemFromDb = pndJobDtlService
					.getPndJobDtl(itemId[1].toString(), contractId);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		return toJson;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/sendToCnPdReturn.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String sendToCnPdReq(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {
		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();
		
		//Added by Shimul
		String nextUserId = returnSlipMstDtl.getUserid();

		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
				.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, nextStateCode + "");
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
		
		approvalHierarchyHistoryNextState.setTargetUserId(nextUserId);

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		return "redirect:/ls/returnSlip/List.do";
	}
	
	//Added by: Shimul
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/backToCnPdReturn.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String backToCnPdReturn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String backStateCode = returnSlipMstDtl.getStateCode();
		
		String backedUserId = returnSlipMstDtl.getUserid();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CnSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = cnSsReturnSlipApprovalHierarchyHistoryService
				.getCnSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_RETURN, backStateCode + "");
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
		approvalHierarchyHistoryBackState.setTargetUserId(backedUserId);

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/ls/returnSlip/List.do";
	}
}
