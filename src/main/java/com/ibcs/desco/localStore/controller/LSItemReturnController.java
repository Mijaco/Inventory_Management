package com.ibcs.desco.localStore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ibcs.desco.common.model.CN2LSReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LSStoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.LsSsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.cs.bean.LedgerLocQty;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtlReturnSlip;
import com.ibcs.desco.cs.model.CentralStoreItems;
import com.ibcs.desco.cs.model.ContractorDepartmentReference;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.model.WorkOrderMst;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.ReturnSlipMstService;
import com.ibcs.desco.cs.service.WorkOrderMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.model.LSReturnSlipDtl;
import com.ibcs.desco.localStore.model.LSReturnSlipMst;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;

@Controller
@PropertySource("classpath:common.properties")
public class LSItemReturnController extends Constrants {

	// Create Object for Service Classes which will be instantiated from spring
	// bean

	@Autowired
	LsCsReturnSlipApprovalHierarchyHistoryService lsCsReturnSlipApprovalHierarchyHistoryService;

	@Autowired
	LsSsReturnSlipApprovalHierarchyHistoryService lsSsReturnSlipApprovalHierarchyHistoryService;

	@Autowired
	ReturnSlipMstService returnSlipMstService;

	@Autowired
	SSReturnSlipMstService ssReturnSlipMstService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	WorkOrderMstService workOrderMstService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Value("${desco.returnslip.prefix}")
	private String descoReturnSlipNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/c2ls/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipMstSave(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		Date now = new Date();

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		// String returnTo=request.getParameter("returnTo");
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		/*
		 * if (returnSlipMstDtl.getReturnTo().equalsIgnoreCase(
		 * ContentType.LOCAL_STORE.toString())) { return
		 * saveReturnItemData(model, returnSlipMstDtl); }
		 */

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);
		/*
		 * DescoKhath descoKhath = (DescoKhath) commonService
		 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
		 * returnSlipMstDtl.getKhathId() + "");
		 */
		ContractorRepresentive contractorRepresentive = (ContractorRepresentive) commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
						"userId", userName);

		LSReturnSlipMst returnSlipMst = new LSReturnSlipMst();
		returnSlipMst.setReceiveFrom(contractorRep.getContractor()
				.getContractorName());
		returnSlipMst.setWorkOrderDate(contractorRep.getContractor()
				.getContractDate());
		returnSlipMst.setWorkOrderNo(contractorRep.getContractNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(descoKhath.getId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setSenderDeptName(department.getDeptName());
		returnSlipMst.setContractorRepresentive(contractorRepresentive);

		// set current date time as RequisitionDate. GUI date is not used here.
		returnSlipMst.setUuid(UUID.randomUUID().toString());
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setSenderStore(ContentType.CONTRACTOR.toString());

		returnSlipMst.setRequestedDeptId(returnSlipMstDtl.getRequestedDeptId());

		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addStoreTicketDtls(returnSlipMstDtl, returnSlipMst,
				roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! Please select item correctly.";
			model.addAttribute("msg", msg);
			return "redirect:/c2ls/returnSlip/getForm.do";
		}
		// return "localStore/lsToCsRequisitionList";
		LSReturnSlipMst returnSlipMstdb = (LSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("LSReturnSlipMst",
						"returnSlipNo", returnSlipMst.getReturnSlipNo());
		return "redirect:/c2ls/returnSlip/pageShow.do?id="
				+ returnSlipMstdb.getId() + "&returnTo="
				+ returnSlipMstdb.getReturnTo();

	}

	public boolean addStoreTicketDtls(ReturnSlipMstDtl returnSlipMstDtl,
			LSReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> qtyNewServiceableList = null;

		List<Double> qtyRecServiceableList = null;

		List<Double> qtyUnServiceableList = null;

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
			qtyNewServiceableList = returnSlipMstDtl.getQtyNewServiceable();
		}

		if (returnSlipMstDtl.getQtyRecServiceable() != null) {
			qtyRecServiceableList = returnSlipMstDtl.getQtyRecServiceable();
		}

		if (returnSlipMstDtl.getQtyUnServiceable() != null) {
			qtyUnServiceableList = returnSlipMstDtl.getQtyUnServiceable();
		}

		if (returnSlipMstDtl.getTotalReturn() != null) {
			totalReturnList = returnSlipMstDtl.getTotalReturn();
		}

		if (returnSlipMstDtl.getRemarks() != null) {
			remarksList = returnSlipMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN2_LS_RETURN_SLIP);

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
							CN2_LS_RETURN_SLIP, stateCodes[0].toString());
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
				String returnSlipNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoReturnSlipNoPrefix, descoDeptCode,
								separator, "CN2_LS_RS_SEQ");
				returnSlipMst.setReturnSlipNo(returnSlipNo);
				commonService.saveOrUpdateModelObjectToDB(returnSlipMst);

				LSReturnSlipMst returnSlipMstDb = (LSReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("LSReturnSlipMst",
								"returnSlipNo", returnSlipMst.getReturnSlipNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					LSReturnSlipDtl returnSlipDtl = new LSReturnSlipDtl();

					if (!itemCodeList.isEmpty()) {
						returnSlipDtl.setItemCode(itemCodeList.get(i));
					} else {
						returnSlipDtl.setItemCode("");
					}

					if (!descriptionList.isEmpty()) {
						returnSlipDtl.setDescription(descriptionList.get(i));
					} else {
						returnSlipDtl.setDescription(descriptionList.get(i));
					}

					if (!uomList.isEmpty()) {
						returnSlipDtl.setUom(uomList.get(i));
					} else {
						returnSlipDtl.setUom("");
					}

					if (!qtyNewServiceableList.isEmpty()) {
						returnSlipDtl
								.setQtyNewServiceable(qtyNewServiceableList
										.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!qtyUnServiceableList.isEmpty()) {
						returnSlipDtl.setQtyUnServiceable(qtyUnServiceableList
								.get(i));
					} else {
						returnSlipDtl.setQtyUnServiceable(0.0);
					}

					if (!qtyRecServiceableList.isEmpty()) {
						returnSlipDtl
								.setQtyRecServiceable(qtyRecServiceableList
										.get(i));
					} else {
						returnSlipDtl.setQtyRecServiceable(0.0);
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
					returnSlipDtl.setReturnSlipNo(returnSlipMst
							.getReturnSlipNo());

					returnSlipDtl.setCreatedBy(returnSlipMst.getCreatedBy());
					returnSlipDtl
							.setCreatedDate(returnSlipMst.getCreatedDate());
					returnSlipDtl.setActive(true);
					returnSlipDtl.setLsReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addReturnSlipHierarchyHistory(returnSlipMst, approvalHierarchy,
						stateCodes, roleName, department, authUser);
				return true;
				// return success
			} else {
				return false;
				// mst dtl not saved
			}

		} else {
			return false;
		}

	}

	public void addReturnSlipHierarchyHistory(LSReturnSlipMst returnSlipMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {

		CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new CN2LSReturnSlipApprovalHierarchyHistory();
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
		approvalHierarchyHistory.setOperationName(CN2_LS_RETURN_SLIP);
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
	@RequestMapping(value = "/c2ls/returnSlip/List.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<CN2LSReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CN2LSReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}

		List<LSReturnSlipMst> returnSlipMstList = new ArrayList<LSReturnSlipMst>();

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList = (List<LSReturnSlipMst>) (Object) commonService
					.getObjectListByAnyColumnValueList("LSReturnSlipMst",
							"returnSlipNo", operationId1);

		}
		if (returnSlipMstList != null) {
			for (LSReturnSlipMst rsMst : returnSlipMstList) {
				Departments dept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								rsMst.getReturnTo());
				rsMst.setReturnTo(dept.getDeptName());
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/cnLsReturnSlipList", model);
		} else {
			return new ModelAndView("sndContractor/c2LsReturnSlipList", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/returnSlip/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		// String roleName = commonService.getAuthRoleName();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

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

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		/*
		 * List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object)
		 * commonService .getObjectListByAnyColumn("DescoKhath", "khathCode",
		 * REVENUE);
		 */

		model.put("categoryList", categoryList);
		model.put("contractorRep", contractorRep);
		// model.put("descoKhathList", descoKhathList);
		model.put("deptIdList", deptList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress", department.getAddress());

		return new ModelAndView("sndContractor/c2LsReturnSlipForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/returnSlip/pageShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShow(ReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String returnTo = returnSlipMst.getReturnTo();
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		LSReturnSlipMst returnSlipMstdb = (LSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("LSReturnSlipMst", "id",
						returnSlipMst.getId() + "");

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<LSReturnSlipDtl> returnSlipDtlList = (List<LSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("LSReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CN2LSReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CN2LSReturnSlipApprovalHierarchyHistory",
						CN2_LS_RETURN_SLIP, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CN2LSReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CN2LSReturnSlipApprovalHierarchyHistory",
						CN2_LS_RETURN_SLIP, operationId, OPEN);

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
						CN2_LS_RETURN_SLIP, roleNameList);

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
								CN2_LS_RETURN_SLIP, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}

		// Get all Location under CS || Added by Ashid
		List<StoreLocations> storeLocationList = getStoreLocationList("CS");

		/* Following four lines are added by Ihteshamul Alam */
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		returnSlipMstdb.setCreatedBy(createBy.getName());

		Departments dept = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						returnSlipMstdb.getReturnTo());
		returnSlipMstdb.setReturnTo(dept.getDeptName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);

		model.put("returnSlipMst", returnSlipMstdb);

		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);

		model.put("returnSlipDtlList", returnSlipDtlList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			model.put("locationList", storeLocationList);
			model.put("ledgerBooks", Constrants.LedgerBook.values());
			return new ModelAndView("sndContractor/lsReturnSlipShow", model);
		} else {
			return new ModelAndView("sndContractor/returnSlipShow", model);
		}
	}

	// Return Slip (RS) Approving process
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/c2ls/itemReturnSlipApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApproved(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			List<CN2LSReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CN2LSReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CN2LSReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CN2LSReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN2_LS_RETURN_SLIP, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CN2LSReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN2_LS_RETURN_SLIP,
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

			LSReturnSlipMst returnSlipMst = (LSReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("LSReturnSlipMst",
							"returnSlipNo", operationId);

			List<LSReturnSlipDtl> returnSlipDtlList = (List<LSReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("LSReturnSlipDtl",
							"returnSlipNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN2_LS_RETURN_SLIP);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<CN2LSReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CN2LSReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			/*
			 * LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory =
			 * lsCsReturnSlipApprovalHierarchyHistoryService
			 * .getLsCsReturnSlipApprovalHierarchyHistory(ids[0]);
			 */

			CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CN2LSReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CN2LSReturnSlipApprovalHierarchyHistory", "id",
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
									CN2_LS_RETURN_SLIP, nextStateCode + "");

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
										"deptId", returnSlipMst.getReturnTo());
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
									CN2_LS_RETURN_SLIP, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					;
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(returnSlipMstDtl
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

					LSStoreTicketMst lsStoreTicketMstdb = (LSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("LSStoreTicketMst",
									"operationId", operationId);
					if (lsStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());

						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						returnSlipMst.setApproved(true);
						commonService
								.saveOrUpdateModelObjectToDB(returnSlipMst);

						// now we have to insert data in store ticket mst and
						// history
						LSStoreTicketMst lsStoreTicketMst = new LSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						lsStoreTicketMst.setStoreTicketType(CN2_LS_RETURN_SLIP);
						lsStoreTicketMst.setOperationId(operationId);
						// lsStoreTicketMst.setIssuedTo(department.getDeptName());
						lsStoreTicketMst.setReceivedFrom(returnSlipMst
								.getReceiveFrom());
						lsStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						lsStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						lsStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						lsStoreTicketMst.setReturnFor(returnSlipMst
								.getCreatedBy());
						// lsStoreTicketMst.setReceivedBy(authUser.getName());
						lsStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						lsStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						// csStoreTicketMst.setIssuedFor(returnSlipMst
						// .getIdenterDesignation());
						lsStoreTicketMst.setFlag(false);

						// Auto generate Store Ticket number

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService
								.getOperationIdByPrefixAndSequenceName(
										descoStoreTicketNoPrefix,
										descoDeptCode, separator, "C2LS_ST_SEQ");

						lsStoreTicketMst.setTicketNo(storeTicketNo);

						lsStoreTicketMst.setSndCode(descoDeptCode);

						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketMst);

						//

						// Get All Approval Hierarchy on CS_STORE_TICKET
						List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
								.getApprovalHierarchyByOperationName(LS_STORE_TICKET);

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
										LS_STORE_TICKET,
										sStoreTicketStateCodes[0].toString());

						LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new LSStoreTicketApprovalHierarchyHistory();

						storeTicketApprovalHierarchyHistory
								.setActRoleName(storeTicketpprovalHierarchy
										.getRoleName());
						storeTicketApprovalHierarchyHistory
								.setOperationId(operationId);
						storeTicketApprovalHierarchyHistory
								.setTicketNo(storeTicketNo);
						storeTicketApprovalHierarchyHistory
								.setOperationName(LS_STORE_TICKET);
						storeTicketApprovalHierarchyHistory
								.setCreatedBy(userName);
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
						storeTicketApprovalHierarchyHistory.setDeptId(deptId);
						storeTicketApprovalHierarchyHistory
								.setcDeptName(department.getDeptName());
						storeTicketApprovalHierarchyHistory
								.setcDesignation(authUser.getDesignation());
						// process will done and go for store ticket
						commonService
								.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						// model.addAttribute("operationId", operationId);

						// return "localStore/c2lsReturnSlipReport";
						return "redirect:/c2ls/showReturnSlipReportLS.do?rsNo="
								+ operationId;

					}

				}
			}
		}
		return "redirect:/c2ls/returnSlip/List.do";
	}

	@RequestMapping(value = "/c2ls/showReturnSlipReportLS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportLS(String rsNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", rsNo);
		return new ModelAndView("localStore/c2lsReturnSlipReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/returnSlip/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CN2LSReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CN2LSReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CN2LSReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CN2LSReturnSlipApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN2_LS_RETURN_SLIP, currentStateCode + "");
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
		CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CN2LSReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN2_LS_RETURN_SLIP, nextStateCode + "");
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

		return "redirect:/c2ls/returnSlip/List.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/returnSlip/backTo.do", method = RequestMethod.GET)
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

		List<CN2LSReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CN2LSReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CN2LSReturnSlipApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CN2LSReturnSlipApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN2_LS_RETURN_SLIP, currentStateCode + "");
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
		CN2LSReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CN2LSReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN2_LS_RETURN_SLIP, backStateCode + "");
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

		return "redirect:/c2ls/returnSlip/List.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/returnSlip/rsSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipSerarch(
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		String operationId = returnSlipMstDtl.getReturnSlipNo();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<CN2LSReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"CN2LSReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);
		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/cnLsReturnSlipList", model);
		} else {
			return new ModelAndView("sndContractor/c2LsReturnSlipList", model);
		}

	}

	@RequestMapping(value = "/c2ls/returnSlip/viewInventoryItemCategory.do", method = RequestMethod.POST)
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

	@RequestMapping(value = "/c2ls/returnSlip/viewInventoryItem.do", method = RequestMethod.POST)
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

			CentralStoreItems centralStoreItems = centralStoreItemsService
					.getCentralStoreItemsByItemId(selectItemFromDb.getItemId());

			selectItemFromDb
					.setCurrentStock(centralStoreItems != null ? centralStoreItems
							.getBalance() : 0.0);

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

	@RequestMapping(value = "/c2ls/returnSlip/getWwOrderToReturn", method = RequestMethod.POST, produces = "application/json")
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

	// Added by Taleb
	@RequestMapping(value = "/c2ls/editReturnSlipDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String editReturnSlipDtl(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			LSReturnSlipDtl returnSlipDtl = gson.fromJson(cData,
					LSReturnSlipDtl.class);
			Integer id = returnSlipDtl.getId();
			LSReturnSlipDtl returnSlipDtldb = (LSReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("LSReturnSlipDtl", "id", id
							+ "");
			returnSlipDtldb.setModifiedBy(commonService.getAuthUserName());
			returnSlipDtldb.setModifiedDate(new Date());
			returnSlipDtldb.setQtyNewServiceable(returnSlipDtl
					.getQtyNewServiceable());
			returnSlipDtldb.setQtyRecServiceable(returnSlipDtl
					.getQtyRecServiceable());
			returnSlipDtldb.setQtyUnServiceable(returnSlipDtl
					.getQtyUnServiceable());
			returnSlipDtldb.setTotalReturn(returnSlipDtl.getTotalReturn());
			commonService.saveOrUpdateModelObjectToDB(returnSlipDtldb);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Update Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Update Failed.");
		}
		return toJson;
	}

	@RequestMapping(value = "/c2ls/editReturnSlipDtlCs.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String editReturnSlipDtlCs(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			LSReturnSlipDtl returnSlipDtl = gson.fromJson(cData,
					LSReturnSlipDtl.class);
			Integer id = returnSlipDtl.getId();
			LSReturnSlipDtl returnSlipDtldb = (LSReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("LSReturnSlipDtl", "id", id
							+ "");
			returnSlipDtldb.setModifiedBy(commonService.getAuthUserName());
			returnSlipDtldb.setModifiedDate(new Date());
			returnSlipDtldb.setQtyNewServiceableRcv(returnSlipDtl
					.getQtyNewServiceableRcv());
			returnSlipDtldb.setQtyRecServiceableRcv(returnSlipDtl
					.getQtyRecServiceableRcv());
			returnSlipDtldb.setQtyUnServiceableRcv(returnSlipDtl
					.getQtyUnServiceableRcv());
			returnSlipDtldb
					.setTotalReturnRcv(returnSlipDtl.getTotalReturnRcv());
			commonService.saveOrUpdateModelObjectToDB(returnSlipDtldb);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Update Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Update Failed.");
		}
		return toJson;
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
	@RequestMapping(value = "/c2ls/rs/setRcvedLocation.do", method = RequestMethod.POST)
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
			TempLocationMstDtlReturnSlip temLocationDtl = gson.fromJson(
					locationQtyJsonString, TempLocationMstDtlReturnSlip.class);
			String itemCode = temLocationDtl.getItemCode();
			String uuid = temLocationDtl.getUuid();
			List<LedgerLocQty> ledLocQtyList = temLocationDtl
					.getLedLocQtyList();

			for (LedgerLocQty ledgerLocQty : ledLocQtyList) {
				String locationId = ledgerLocQty.getLocationId();
				String ledgerName = ledgerLocQty.getLedgerName();

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								locationId);

				Double qty = ledgerLocQty.getQuantity();

				TempItemLocation tempItemLocation = new TempItemLocation();
				tempItemLocation.setId(null);
				tempItemLocation.setItemCode(itemCode);
				tempItemLocation.setUuid(uuid);
				tempItemLocation.setLocationId(locationId);
				tempItemLocation.setLedgerName(ledgerName);
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
	@RequestMapping(value = "/c2ls/rs/getTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
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
			Double qtyServ = 0.0;
			Double qtyUnServ = 0.0;
			Double qtyRecServ = 0.0;
			for (TempItemLocation temp : tempItemLocationList) {
				if (temp.getLedgerName().equals(NEW_SERVICEABLE)) {
					qtyServ += temp.getQuantity();
				}
				if (temp.getLedgerName().equals(RECOVERY_SERVICEABLE)) {
					qtyRecServ += temp.getQuantity();
				}
				if (temp.getLedgerName().equals(UNSERVICEABLE)) {
					qtyUnServ += temp.getQuantity();
				}
			}

			double total = qtyServ + qtyUnServ + qtyRecServ;

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();

			ReturnSlipDtl rtnSlpDtl = new ReturnSlipDtl();
			rtnSlpDtl.setQtyNewServiceable(qtyServ);
			rtnSlpDtl.setQtyRecServiceable(qtyUnServ);
			rtnSlpDtl.setQtyUnServiceable(qtyRecServ);
			rtnSlpDtl.setTotalReturn(total);
			toJson = ow.writeValueAsString(rtnSlpDtl);
		}
		return toJson;
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/rs/getTemplocation.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/c2ls/rs/updateLocationQty.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/c2ls/rs/deleteLocationQty.do", method = RequestMethod.POST)
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

}
