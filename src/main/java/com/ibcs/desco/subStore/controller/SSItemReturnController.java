package com.ibcs.desco.subStore.controller;

import java.io.IOException;
import java.text.DecimalFormat;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.SsCsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.CSStoreTicketMstDtl;
import com.ibcs.desco.cs.bean.LedgerLocQty;
import com.ibcs.desco.cs.bean.Location7sMstDtl;
import com.ibcs.desco.cs.bean.Location7sQty;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtlReturnSlip;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreItems;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.model.WorkOrderMst;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.ReturnSlipMstService;
import com.ibcs.desco.cs.service.WorkOrderMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;

@Controller
@PropertySource("classpath:common.properties")
public class SSItemReturnController extends Constrants {

	// Create Object for Service Classes which will be instantiated from spring
	// bean

	@Autowired
	SsCsReturnSlipApprovalHierarchyHistoryService ssCsReturnSlipApprovalHierarchyHistoryService;

	@Autowired
	ReturnSlipMstService returnSlipMstService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	SSReturnSlipMstService ssReturnSlipMstService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	WorkOrderMstService workOrderMstService;

	@Autowired
	CommonService commonService;

	@Value("${desco.returnslip.prefix}")
	private String descoReturnSlipNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@RequestMapping(value = "/ss/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipMstSave(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						returnSlipMstDtl.getKhathId() + "");

		ReturnSlipMst returnSlipMst = new ReturnSlipMst();

		returnSlipMst.setReceiveFrom(returnSlipMstDtl.getReceiveFrom());
		returnSlipMst.setWorkOrderDate(returnSlipMstDtl.getWorkOrderDate());
		returnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(returnSlipMstDtl.getKhathId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setSndCode(department.getDescoCode());
		returnSlipMst.setSenderDeptName(department.getDeptName());
		// setting suiffix with (last_id + 1) as RequisitionNo
		/*
		 * int maxId = (Integer) commonService.getMaxValueByObjectAndColumn(
		 * "CentralStoreRequisitionMst", "id"); String formattedReturnSlipNo =
		 * String.format("%06d", maxId + 1);
		 * 
		 * returnSlipMst.setReturnSlipNo(csReturnSlipNoPrefix +
		 * formattedReturnSlipNo);
		 */

		/*
		 * String returnSlipNo = commonService.getCustomSequence1(
		 * lsCsReturnSlipNoPrefix, "-");
		 * commonService.saveOrUpdateCustomSequence1ToDB(returnSlipNo);
		 * returnSlipMst.setReturnSlipNo(returnSlipNo);
		 */

		// set current date time as RequisitionDate. GUI date is not used here.
		returnSlipMst.setSndCode(department.getDescoCode());
		returnSlipMst.setUuid(returnSlipMstDtl.getUuid());
		// returnSlipMst.setUuid(UUID.randomUUID().toString());
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setReturnTo(ContentType.CENTRAL_STORE.toString());
		returnSlipMst.setSenderStore(ContentType.SUB_STORE.toString());
		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addReturnSlipDtls(returnSlipMstDtl, returnSlipMst,
				roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
		}
		// return "subStore/ssToCsRequisitionList";
		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo",
						returnSlipMst.getReturnSlipNo());
		return "redirect:/ss/returnSlip/pageShow.do?id="
				+ returnSlipMstdb.getId();

	}

	public boolean addReturnSlipDtls(ReturnSlipMstDtl returnSlipMstDtl,
			ReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> qtyNewServiceableList = null;

		List<Double> qtyRecServicableList = null;

		List<Double> qtyUnServicableList = null;

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

		if (returnSlipMstDtl.getQtyUnServiceable() != null) {
			qtyUnServicableList = returnSlipMstDtl.getQtyUnServiceable();
		}

		if (returnSlipMstDtl.getQtyRecServiceable() != null) {
			qtyRecServicableList = returnSlipMstDtl.getQtyRecServiceable();
		}

		if (returnSlipMstDtl.getTotalReturn() != null) {
			totalReturnList = returnSlipMstDtl.getTotalReturn();
		}

		if (returnSlipMstDtl.getRemarks() != null) {
			remarksList = returnSlipMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on SS_CS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(SS_CS_RETURN_SLIP);

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
							SS_CS_RETURN_SLIP, stateCodes[0].toString());
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
								separator, "LS_CS_RS_SEQ");
				;
				returnSlipMst.setReturnSlipNo(returnSlipNo);
				commonService.saveOrUpdateModelObjectToDB(returnSlipMst);

				ReturnSlipMst returnSlipMstDb = (ReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
								"returnSlipNo", returnSlipMst.getReturnSlipNo());
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

					if (!qtyNewServiceableList.isEmpty()) {
						returnSlipDtl
								.setQtyNewServiceable(qtyNewServiceableList
										.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!qtyUnServicableList.isEmpty()) {
						returnSlipDtl.setQtyUnServiceable(qtyUnServicableList
								.get(i));
					} else {
						returnSlipDtl.setQtyUnServiceable(0.0);
					}

					if (!qtyRecServicableList.isEmpty()) {
						returnSlipDtl.setQtyRecServiceable(qtyRecServicableList
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

	public void addReturnSlipHierarchyHistory(ReturnSlipMst returnSlipMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new SsCsReturnSlipApprovalHierarchyHistory();
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
		approvalHierarchyHistory.setOperationName(SS_CS_RETURN_SLIP);
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
	@RequestMapping(value = "/ss/returnSlip/List.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReturnnListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<CnSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		Map<String, Object> model = new HashMap<String, Object>();

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();

		List<SSReturnSlipMst> ssReturnSlipMstList = new ArrayList<SSReturnSlipMst>();

		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId) != null) {
			ssReturnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId);
		}

		if (ssReturnSlipMstList != null) {

			returnSlipMstList.addAll(importObjListToSs(ssReturnSlipMstList));

		}

		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipList", model);
		} else {
			return new ModelAndView("subStore/csReturnSlipList", model);
		}

	}
	
	//Added by: Ihteshamul Alam
	//This list will contain only those return items that will forward to CS
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/ListCS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReturnnListForwardToCS() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		Map<String, Object> model = new HashMap<String, Object>();

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId);
		}

		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}

		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipListCS", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipListCS", model);
		} else {
			return new ModelAndView("subStore/csReturnSlipList", model);
		}

	}

	private List<ReturnSlipMst> importObjListToSs(
			List<SSReturnSlipMst> ssReturnSlipMstList) {
		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		ReturnSlipMst rs = null;
		for (SSReturnSlipMst ss : ssReturnSlipMstList) {
			rs = new ReturnSlipMst();
			rs.setId(ss.getId());
			rs.setCreatedBy(ss.getCreatedBy());
			rs.setReceiveFrom(ss.getReceiveFrom());
			rs.setReturnSlipNo(ss.getReturnSlipNo());
			rs.setReturnSlipDate(ss.getReturnSlipDate());
			rs.setModifiedBy(ss.getModifiedBy());
			rs.setModifiedDate(ss.getModifiedDate());
			rs.setRemarks(ss.getRemarks());
			rs.setSenderStore(ss.getSenderStore());
			rs.setReturnTo(ss.getReturnTo());
			returnSlipMstList.add(rs);
		}
		return returnSlipMstList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/ListToSS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocalToSub() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<LsSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory", deptId,
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
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("localStore/lsToCsReturnSlipList", model);
		} else {
			return new ModelAndView("subStore/csReturnSlipList", model);
		}

	}

	@RequestMapping(value = "/ss/returnSlip/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		@SuppressWarnings("unchecked")
		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);
		model.put("descoKhathList", descoKhathList);

		// Start m@@ || Generate drop down list for location in Grid
		@SuppressWarnings("unchecked")
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "SS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);
		// End m@@ || Generate drop down list for location in Grid

		model.put("uuid", UUID.randomUUID().toString());
		model.put("department", department);
		return new ModelAndView("subStore/ssToCsReturnSlipForm", model);
	}

	@RequestMapping(value = "/ss/returnSlip/pageShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShowByPost(ReturnSlipMst returnSlipMst) {
		return getReturnSlipShow(returnSlipMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/pageShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShow(ReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);
		ReturnSlipMst returnSlipMstdb = returnSlipMstService
				.getReturnSlipMst(returnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<SsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory",
						SS_CS_RETURN_SLIP, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<SsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory",
						SS_CS_RETURN_SLIP, operationId, OPEN);

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
						SS_CS_RETURN_SLIP, roleNameList);

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
			// Decide for return or not
			returnStateCode = rsApprovalHierarchyHistoryOpenList.get(
					rsApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								SS_CS_RETURN_SLIP, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);
		
		/*The following four lines are added by Ihteshamul Alam*/
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		returnSlipMstdb.setCreatedBy( createBy.getName() );

		model.put("returnSlipMst", returnSlipMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);
		model.put("returnSlipDtlList", returnSlipDtlList);
		model.put("returnSlipDtlList", returnSlipDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		String rolePrefix = roleName.substring(0, 7);
		// Start m@@ || Generate drop down list for location in Grid
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);
		model.put("department", department);
		// End m@@ || Generate drop down list for location in Grid

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssReturnSlipShow", model);
		} else {
			return new ModelAndView("subStore/ssToCsAddLocationReturnSlipShow",
					model);
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

	@RequestMapping(value = "/ss/returnSlip/itemReturnSlipApproved.do", method = RequestMethod.POST)
	public String approveSsCsRsByPost(
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl,
			Model model) {
		// Update SS_RS_DTL
		List<Integer> ids = returnSlipMstDtl.getDtlId();
		List<Double> nsQtyList = returnSlipMstDtl.getQtyNewServiceable();
		List<Double> rsQtyList = returnSlipMstDtl.getQtyRecServiceable();
		List<Double> usQtyList = returnSlipMstDtl.getQtyUnServiceable();
		List<Double> tQtyList = returnSlipMstDtl.getTotalReturn();
		for (int i = 0; i < ids.size(); i++) {
			ReturnSlipDtl ssRsDtlDB = (ReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id",
							ids.get(i) + "");
			ssRsDtlDB.setQtyNewServiceable(nsQtyList.get(i));
			ssRsDtlDB.setQtyRecServiceable(rsQtyList.get(i));
			ssRsDtlDB.setQtyUnServiceable(usQtyList.get(i));
			ssRsDtlDB.setTotalReturn(tQtyList.get(i));
			ssRsDtlDB.setModifiedBy(commonService.getAuthUserName());
			ssRsDtlDB.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(ssRsDtlDB);
		}
		// Send to Next Officer
		return returnSlipForItemSubmitApproved(model, returnSlipMstDtl);
	}

	// Return Slip (RS) Approving process
	@RequestMapping(value = "/ss/returnSlip/itemReturnSlipApproved.do", method = RequestMethod.GET)
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
		
		boolean storeChangeFlag = false;

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			@SuppressWarnings("unchecked")
			List<SsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SsCsReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = ssCsReturnSlipApprovalHierarchyHistoryService
					.getSsCsReturnSlipApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_CS_RETURN_SLIP, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setCreatedDate(new Date());
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());


			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new SsCsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_CS_RETURN_SLIP,
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

			ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
							"returnSlipNo", operationId);

			@SuppressWarnings("unchecked")
			List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
							operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(SS_CS_RETURN_SLIP);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			@SuppressWarnings("unchecked")
			List<SsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SsCsReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			/*
			 * SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory =
			 * lsCsReturnSlipApprovalHierarchyHistoryService
			 * .getSsCsReturnSlipApprovalHierarchyHistory(ids[0]);
			 */

			SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (SsCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"SsCsReturnSlipApprovalHierarchyHistory", "id",
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
									SS_CS_RETURN_SLIP, nextStateCode + "");

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
						@SuppressWarnings("unchecked")
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
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_CS_RETURN_SLIP, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(returnSlipMstDtl
							.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());


					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {

					// update returnslip dtl

					for (ReturnSlipDtl rp : returnSlipDtlList) {
						@SuppressWarnings("unchecked")
						List<TempItemLocation> tempLocationList = (List<TempItemLocation>) (Object) commonService
								.getObjectListByTwoColumn("TempItemLocation",
										"uuid", returnSlipMst.getUuid(),
										"itemCode", rp.getItemCode());
						double i = 0;
						for (TempItemLocation tmp : tempLocationList) {
							if (tmp.getLedgerName().equals(NEW_SERVICEABLE)) {
								rp.setQtyNewServiceableRcv(tmp.getQuantity());
								i += tmp.getQuantity();
							}
							if (tmp.getLedgerName()
									.equals(RECOVERY_SERVICEABLE)) {
								rp.setQtyRecServiceableRcv(tmp.getQuantity());
								i += tmp.getQuantity();
							}
							if (tmp.getLedgerName().equals(UNSERVICEABLE)) {
								rp.setQtyUnServiceableRcv(tmp.getQuantity());
								i += tmp.getQuantity();
							}
						}
						rp.setTotalReturnRcv(i);
						commonService.saveOrUpdateModelObjectToDB(rp);
					}

					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser.getName());
						approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						returnSlipMst.setApproved(true);
						commonService
								.saveOrUpdateModelObjectToDB(returnSlipMst);

						// now we have to insert data in store ticket mst and
						// history
						CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						csStoreTicketMst.setStoreTicketType(SS_CS_RETURN_SLIP);
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
						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst.getSndCode());

						// csStoreTicketMst.setIssuedFor(returnSlipMst
						// .getIdenterDesignation());
						csStoreTicketMst.setFlag(false);

						// Auto generate Store Ticket number
						
						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService
								.getOperationIdByPrefixAndSequenceName(
										descoStoreTicketNoPrefix,
										descoDeptCode, separator, "CS_ST_SEQ");
						
						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService
								.saveOrUpdateModelObjectToDB(csStoreTicketMst);

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
								.setTicketNo(csStoreTicketMst.getTicketNo());
						storeTicketApprovalHierarchyHistory
								.setOperationName(CS_STORE_TICKET);
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
						// process will done and go for store ticket
						commonService
								.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						//model.addAttribute("operationId", operationId);

						//return "centralStore/ssToCsReturnSlipReport";
						return "redirect:/ss/returnSlip/ssToCsReturnSlipReport.do?ticketNo="+operationId;

					}

				}
			}
		}

		String rolePrefix = roleName.substring(0, 7);
		
		if(storeChangeFlag){
			String operationId = returnSlipMstDtl.getReturnSlipNo();
			return "redirect:/ss/returnSlip/ssReturnSlipReportCS.do?ticketNo="+operationId;
		}

		if (rolePrefix.equals("ROLE_SS")) {
			return "redirect:/ss/returnSlip/List.do";
		}
		else{
			return "redirect:/ls/returnSlip/List.do";
		}
	}
	
	// return "redirect:/ss/returnSlip/ssToCsReturnSlipReport.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/ss/returnSlip/ssReturnSlipReportCS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssReturnSlipReportCS(String ticketNo){
		Map <String, Object> model =  new HashMap<String, Object>();
		model.put("operationId", ticketNo);
		return new ModelAndView("subStore/reports/ssToCsReturnSlipReport", model);
	}
	
	// return "redirect:/ss/returnSlip/ssToCsReturnSlipReport.do?ticketNo="+ticketNo;
	@RequestMapping(value = "/ss/returnSlip/ssToCsReturnSlipReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssToCsReturnSlipReport(String ticketNo){
		Map <String, Object> model =  new HashMap<String, Object>();
		model.put("operationId", ticketNo);
		return new ModelAndView("centralStore/ssToCsReturnSlipReport", model);
	}

	@RequestMapping(value = "/ss/returnSlip/sendTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String ssCsReturnSlipSendToByPost(@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl 
			returnSlipMstDtl, Model model) {
		// Update SS_RS_DTL
		List<Integer> ids = returnSlipMstDtl.getDtlId();
		List<Double> nsQtyList = returnSlipMstDtl.getQtyNewServiceable();
		List<Double> rsQtyList = returnSlipMstDtl.getQtyRecServiceable();
		List<Double> usQtyList = returnSlipMstDtl.getQtyUnServiceable();
		List<Double> tQtyList = returnSlipMstDtl.getTotalReturn();
		for (int i = 0; i < ids.size(); i++) {
			ReturnSlipDtl ssRsDtlDB = (ReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id",
							ids.get(i) + "");
			ssRsDtlDB.setQtyNewServiceable(nsQtyList.get(i));
			ssRsDtlDB.setQtyRecServiceable(rsQtyList.get(i));
			ssRsDtlDB.setQtyUnServiceable(usQtyList.get(i));
			ssRsDtlDB.setTotalReturn(tQtyList.get(i));
			ssRsDtlDB.setModifiedBy(commonService.getAuthUserName());
			ssRsDtlDB.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(ssRsDtlDB);
		}
		// Send to Next Officer
		return ssCsReturnSlipSendToByGet(model, returnSlipMstDtl);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String ssCsReturnSlipSendToByGet(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

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

		List<SsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"SsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = ssCsReturnSlipApprovalHierarchyHistoryService
				.getSsCsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_RETURN_SLIP, currentStateCode + "");
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
		SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new SsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_RETURN_SLIP, nextStateCode + "");
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

		if (roleName.contains("ROLE_SS")) {
			return "redirect:/ss/returnSlip/List.do";
		}
		else{
			return "redirect:/ls/returnSlip/List.do";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/backTo.do", method = RequestMethod.GET)
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

		List<SsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"SsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = ssCsReturnSlipApprovalHierarchyHistoryService
				.getSsCsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_RETURN_SLIP, currentStateCode + "");
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
		SsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new SsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_CS_RETURN_SLIP, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState
				.setStateName(BACK+"ED");
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

		if (roleName.contains("ROLE_SS")) {
			return "redirect:/ss/returnSlip/List.do";
		}
		else{
			return "redirect:/ls/returnSlip/List.do";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/rsSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipSerarch(
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		String operationId = returnSlipMstDtl.getReturnSlipNo();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		List<String> opeId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			opeId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		if (returnSlipMstService.listReturnSlipMstByOperationIdList(opeId) != null) {
			returnSlipMstList = returnSlipMstService
					.listReturnSlipMstByOperationIdList(opeId);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsReturnSlipList", model);
		} else {
			return new ModelAndView("subStore/csReturnSlipList", model);
		}

	}

	@RequestMapping(value = "/ss/returnSlip/ssReturnViewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssReturnViewInventoryItem(@RequestBody String json)
			throws Exception {
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/returnSlip/ssReturnSlipViewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssReturnSlipViewInventoryItem(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			SSItemTransactionMst invItem = gson.fromJson(json, SSItemTransactionMst.class);
			
			String itemCode = invItem.getItemCode();
			Integer khathId = invItem.getKhathId();
			
			ItemMaster itemMaster = ( ItemMaster )  commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId", itemCode);
			
			SSItemTransactionMst ssItemTnxMst = ( SSItemTransactionMst ) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "itemCode", itemCode);
			
			if( ssItemTnxMst != null ) {
				ssItemTnxMst.setUom( itemMaster.getUnitCode() );
				
				List<SSItemTransactionMst> nsStockQuantity = ( List<SSItemTransactionMst> ) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst", "itemCode", itemCode, "khathId", khathId.toString(), "ledgerName", NEW_SERVICEABLE);
				
				List<SSItemTransactionMst> rsStockQuantity = ( List<SSItemTransactionMst> ) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst", "itemCode", itemCode, "khathId", khathId.toString(), "ledgerName", RECOVERY_SERVICEABLE);
				
				if( nsStockQuantity.size() > 0 ) {
					ssItemTnxMst.setNsStockQuantity( nsStockQuantity.get(0).getQuantity() );
				} else {
					ssItemTnxMst.setNsStockQuantity( 0.0 );
				}
				
				if( rsStockQuantity.size() > 0 ) {
					ssItemTnxMst.setRsStockQuantity( rsStockQuantity.get(0).getQuantity() );
				} else {
					ssItemTnxMst.setRsStockQuantity( 0.0 );
				}
				ssItemTnxMst.setItemName( itemMaster.getItemName() );
			} else {
				ssItemTnxMst = new SSItemTransactionMst();
				
				ssItemTnxMst.setItemCode( itemMaster.getItemId() );
				ssItemTnxMst.setItemName( itemMaster.getItemName() );
				ssItemTnxMst.setUom( itemMaster.getUnitCode() );
				ssItemTnxMst.setNsStockQuantity( 0.0 );
				ssItemTnxMst.setRsStockQuantity( 0.0 );
			}
			
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(ssItemTnxMst);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/ss/returnSlip/ssReturnViewInventoryItemCategory.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssReturnViewInventoryItemCategory(@RequestBody String json)
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

	@RequestMapping(value = "/ss/returnSlip/getWwOrderToReturn", method = RequestMethod.POST, produces = "application/json")
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

	@RequestMapping(value = "/ss/returnSlip/setRcvedLocation.do", method = RequestMethod.POST)
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
			TempLocationMstDtlReturnSlip temLocationDtl = gson.fromJson(
					locationQtyJsonString, TempLocationMstDtlReturnSlip.class);
			String itemCode = temLocationDtl.getItemCode();
			String uuid = temLocationDtl.getUuid();
			// Map<String, String> map = temLocationDtl.getLocQtyDtl();
			// Set<String> keys = map.keySet();
			List<LedgerLocQty> locQ = temLocationDtl.getLedLocQtyList();

			// for (Iterator i = vv.iterator(); i.hasNext();) {
			for (LedgerLocQty i : locQ) {
				String locationId = i.getLocationId();
				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								locationId);

				TempItemLocation tempItemLocation = new TempItemLocation();
				tempItemLocation.setId(null);
				tempItemLocation.setItemCode(itemCode);
				tempItemLocation.setUuid(uuid);
				tempItemLocation.setLocationId(locationId);
				tempItemLocation.setLedgerName(i.getLedgerName());
				tempItemLocation.setLocationName(storeLocations.getName());
				tempItemLocation.setQuantity(i.getQuantity());
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
	@RequestMapping(value = "/ss/returnSlip/getTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ss/returnSlip/getTemplocation.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ss/returnSlip/updateLocationQty.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ss/returnSlip/deleteLocationQty.do", method = RequestMethod.POST)
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/ss/returnSlip/saveStoreTicketForSSReturnSlip.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getSSStoreTicketCreateSaveForCSReturnSlip(
			CSStoreTicketMstDtl csStoreTicketMstDtl,
			RedirectAttributes redirectAttributes) {

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

		// List<Double> quantityList = null;

		List<String> remarksList = null;

		// List<String> ledgerNameList = null;

		// List<Integer> locationIdList = null;

		List<Double> qtyNewServiceableList = null;

		List<Double> qtyRecServiceableList = null;

		List<Double> qtyUnServiceableList = null;

		Date now = new Date();

		// SSStoreTicketMst csStoreTicketMst =
		// csStoreTicketMstService.getCSStoreTicketMst(csStoreTicketMstDtl.getId());
		SSStoreTicketMst csStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "id",
						csStoreTicketMstDtl.getId() + "");
		csStoreTicketMst.setCreatedBy(userName);
		csStoreTicketMst.setCreatedDate(now);
		csStoreTicketMst.setActive(true);
		csStoreTicketMst.setFlag(true);
		csStoreTicketMst.setApproved(false);
		csStoreTicketMst.setTicketDate(now);

		// csStoreTicketMstService.editCSStoreTicketMst(csStoreTicketMst);
		commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

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
				SSStoreTicketDtl csStoreTicketDtl = new SSStoreTicketDtl();
				csStoreTicketDtl.setActive(true);
				csStoreTicketDtl.setCreatedBy(userName);
				csStoreTicketDtl.setCreatedDate(now);
				csStoreTicketDtl.setTicketNo(csStoreTicketMstDtl.getTicketNo());
				csStoreTicketDtl.setId(null);

				if (itemIdList != null) {
					csStoreTicketDtl.setItemId(itemIdList.get(i));
				}

				if (descriptionList != null) {
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
						&& qtyNewServiceableList.get(i) > 0) {
					csStoreTicketDtl.setQuantity(qtyNewServiceableList.get(i));
					csStoreTicketDtl.setLedgerName(NEW_SERVICEABLE);
					csStoreTicketDtlService
							.addSSStoreTicketDtl(csStoreTicketDtl);
				}
				if (qtyRecServiceableList.size() > 0
						&& qtyRecServiceableList.get(i) > 0) {
					csStoreTicketDtl.setQuantity(qtyRecServiceableList.get(i));
					csStoreTicketDtl.setLedgerName(RECOVERY_SERVICEABLE);
					csStoreTicketDtlService
							.addSSStoreTicketDtl(csStoreTicketDtl);
				}
				// if (qtyUnServiceableList.size() > 0 || qtyUnServiceableList
				// != null) {
				if (qtyUnServiceableList.size() > 0
						&& qtyUnServiceableList.get(i) > 0) {
					csStoreTicketDtl.setQuantity(qtyUnServiceableList.get(i));
					csStoreTicketDtl.setLedgerName(UNSERVICEABLE);
					csStoreTicketDtlService
							.addSSStoreTicketDtl(csStoreTicketDtl);
				}

				// csStoreTicketDtlService.addCSStoreTicketDtl(csStoreTicketDtl);
			}
		}
		redirectAttributes.addFlashAttribute("csStoreTicketMst",
				csStoreTicketMstDtl);
		// return "redirect:/cs/itemRecieved/storeTicketlist.do";

		/*
		 * if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION))
		 * { return "redirect:/ss/returnSlip/openStoreTicket.do?operationId=" +
		 * csStoreTicketMstDtl.getOperationId() + "&flag=" + true +
		 * "&storeTicketType=" + LS_SS_REQUISITION + "&ticketNo=" +
		 * csStoreTicketMstDtl.getTicketNo(); } else if
		 * (csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
		 * return "redirect:/ss/returnSlip/openStoreTicket.do?operationId=" +
		 * csStoreTicketMstDtl.getOperationId() + "&flag=" + true +
		 * "&storeTicketType=" + CN_SS_REQUISITION + "&ticketNo=" +
		 * csStoreTicketMstDtl.getTicketNo(); } else if
		 * (csStoreTicketMst.getStoreTicketType().equals(LS_SS_RETURN_SLIP)) {
		 * return "redirect:/ss/returnSlip/openStoreTicket.do?operationId=" +
		 * csStoreTicketMstDtl.getOperationId() + "&flag=" + true +
		 * "&storeTicketType=" + LS_SS_RETURN_SLIP + "&ticketNo=" +
		 * csStoreTicketMstDtl.getTicketNo(); } else if
		 * (csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
		 * return "redirect:/ss/returnSlip/openStoreTicket.do?operationId=" +
		 * csStoreTicketMstDtl.getOperationId() + "&flag=" + true +
		 * "&storeTicketType=" + CN_SS_RETURN_SLIP + "&ticketNo=" +
		 * csStoreTicketMstDtl.getTicketNo(); } else { return null; }
		 */

		if (csStoreTicketMst.getStoreTicketType().equals(LS_SS_REQUISITION)) {
			SSStoreTicketMst sSStoreTicketMst = new SSStoreTicketMst();
			sSStoreTicketMst.setFlag(true);
			sSStoreTicketMst.setOperationId(csStoreTicketMstDtl
					.getOperationId());
			sSStoreTicketMst.setStoreTicketType(csStoreTicketMstDtl
					.getStoreTicketType());
			sSStoreTicketMst.setTicketNo(csStoreTicketMstDtl.getTicketNo());
			return showStoreTicetLsToSS_RS(sSStoreTicketMst);
			// return "redirect:/ss/openStoreTicket.do";
		} else if (csStoreTicketMst.getStoreTicketType().equals(
				CN_SS_REQUISITION)) {
			SSStoreTicketMst sSStoreTicketMst = new SSStoreTicketMst();
			sSStoreTicketMst.setFlag(true);
			sSStoreTicketMst.setOperationId(csStoreTicketMstDtl
					.getOperationId());
			sSStoreTicketMst.setStoreTicketType(csStoreTicketMstDtl
					.getStoreTicketType());
			sSStoreTicketMst.setTicketNo(csStoreTicketMstDtl.getTicketNo());
			return showStoreTicetLsToSS_RS(sSStoreTicketMst);
			// return "redirect:/ss/openStoreTicket.do";

		} else if (csStoreTicketMst.getStoreTicketType().equals(
				LS_SS_RETURN_SLIP)) {
			SSStoreTicketMst sSStoreTicketMst = new SSStoreTicketMst();
			sSStoreTicketMst.setFlag(true);
			sSStoreTicketMst.setOperationId(csStoreTicketMstDtl
					.getOperationId());
			sSStoreTicketMst.setStoreTicketType(csStoreTicketMstDtl
					.getStoreTicketType());
			sSStoreTicketMst.setTicketNo(csStoreTicketMstDtl.getTicketNo());
			return showStoreTicetLsToSS_RS(sSStoreTicketMst);

			// return "redirect:/ss/openStoreTicket.do";
		} else if (csStoreTicketMst.getStoreTicketType().equals(
				CN_SS_RETURN_SLIP)
				|| csStoreTicketMst.getStoreTicketType().equals(
						CN_PD_SS_RETURN)) {
			SSStoreTicketMst sSStoreTicketMst = new SSStoreTicketMst();
			sSStoreTicketMst.setFlag(true);
			sSStoreTicketMst.setOperationId(csStoreTicketMstDtl
					.getOperationId());
			sSStoreTicketMst.setStoreTicketType(csStoreTicketMstDtl
					.getStoreTicketType());
			sSStoreTicketMst.setTicketNo(csStoreTicketMstDtl.getTicketNo());
			return showStoreTicetLsToSS_RS(sSStoreTicketMst);
			// return "redirect:/ss/openStoreTicket.do";

		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public ModelAndView showStoreTicetLsToSS_RS(
			SSStoreTicketMst ssStoreTicketMst) {

		ssStoreTicketMst = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo",
						ssStoreTicketMst.getTicketNo());
		Map<String, Object> model = new HashMap<String, Object>();
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
					storeTicketApproveHistoryList.size() - 1).getStateCode();

			int nextStateCode = 0;
			for (int i = 0; i < approveHeirchyList.size(); i++) {
				if (approveHeirchyList.get(i).getStateCode() > stateCode) {
					nextStateCode = approveHeirchyList.get(i).getStateCode();
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
					storeTicketApproveHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		// current ticket no
		String ticketNo = ssStoreTicketMst.getTicketNo();

		// get all store ticket master info against current ticket no
		SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "ticketNo",
						ticketNo);
		// get all store ticket item details info against current ticket no
		List<SSStoreTicketDtl> csStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo",
						ticketNo);

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
						CN_SS_REQUISITION)) {
			return new ModelAndView("subStore/ssStoreTicketShowRequisition",
					model);
		} else {
			return null;
		}
	}

	//
	// SELECT Location object List by parent location id || Added by ashid
	@RequestMapping(value = "/ss/cs/rt/saveLocation7sDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String saveLocationDtlLS2SS(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		Double newServQty = 0.0;
		Double recServQty = 0.0;
		Double unServQty = 0.0;
		Double totalQty = 0.0;
		if (isJson) {
			Location7sMstDtl obj = gson.fromJson(json, Location7sMstDtl.class);
			List<Location7sQty> locationList = obj.getLocationList();
			for (Location7sQty loc7sQty : locationList) {
				TempItemLocation temp = new TempItemLocation();
				if (loc7sQty.getQuantity() > 0) {
					if (loc7sQty.getLocation() != null
							&& !loc7sQty.getLocation().equals("")) {
						temp.setUuid(obj.getUuid());
						temp.setItemCode(obj.getItemCode());
						temp.setLedgerName(loc7sQty.getLedgerName());
						temp.setLocationId(loc7sQty.getLocation());
						temp.setLocationName(commonService
								.getStoreLocationNamebyId(loc7sQty
										.getLocation()));
						//temp.setQuantity(loc7sQty.getQuantity());
						DecimalFormat df = new DecimalFormat("#.###");
						double qty = Double.parseDouble(df.format(loc7sQty.getQuantity()));
						temp.setQuantity(qty);

						// check for last location and set to ChildLocation
						if (loc7sQty.getGodown() != null
								&& !loc7sQty.getGodown().equals("")) {
							temp.setGodownId(loc7sQty.getGodown());
							temp.setChildLocationId(loc7sQty.getGodown());
							temp.setChildLocationName(commonService
									.getStoreLocationNamebyId(loc7sQty
											.getGodown()));
							if (loc7sQty.getFloor() != null
									&& !loc7sQty.getFloor().equals("")) {
								temp.setFloorId(loc7sQty.getFloor());
								temp.setChildLocationId(loc7sQty.getFloor());
								temp.setChildLocationName(commonService
										.getStoreLocationNamebyId(loc7sQty
												.getFloor()));
								if (loc7sQty.getSector() != null
										&& !loc7sQty.getSector().equals("")) {
									temp.setSectorId(loc7sQty.getSector());
									temp.setChildLocationId(loc7sQty
											.getSector());
									temp.setChildLocationName(commonService
											.getStoreLocationNamebyId(loc7sQty
													.getSector()));
									if (loc7sQty.getRake() != null
											&& !loc7sQty.getRake().equals("")) {
										temp.setRakeId(loc7sQty.getRake());
										temp.setChildLocationId(loc7sQty
												.getRake());
										temp.setChildLocationName(commonService
												.getStoreLocationNamebyId(loc7sQty
														.getRake()));
										if (loc7sQty.getBin() != null
												&& !loc7sQty.getBin()
														.equals("")) {
											temp.setBinId(loc7sQty.getBin());
											temp.setChildLocationId(loc7sQty
													.getBin());
											temp.setChildLocationName(commonService
													.getStoreLocationNamebyId(loc7sQty
															.getBin()));
										}
									}
								}
							}
						}
						// now save to db
						if (loc7sQty.getId().equals("0")) {
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
						} else {
							temp.setId(Integer.parseInt(loc7sQty.getId()));
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
							temp.setModifiedBy(commonService.getAuthRoleName());
							temp.setModifiedDate(new Date());
						}
						commonService.saveOrUpdateModelObjectToDB(temp);
						if (loc7sQty.getLedgerName().equals(NEW_SERVICEABLE)) {
							//newServQty += loc7sQty.getQuantity();
							newServQty += qty;
						} else if (loc7sQty.getLedgerName().equals(
								RECOVERY_SERVICEABLE)) {
							//recServQty += loc7sQty.getQuantity();
							recServQty += qty;
						} else if (loc7sQty.getLedgerName().equals(
								UNSERVICEABLE)) {
							//unServQty += loc7sQty.getQuantity();
							unServQty += qty;
						}
						//totalQty += loc7sQty.getQuantity();
						totalQty += qty;

					}
				}
			}

			// set Return Qty to Form GUI

			ReturnSlipDtl rtDtl = new ReturnSlipDtl();
			rtDtl.setQtyNewServiceableRcv(newServQty);
			rtDtl.setQtyRecServiceableRcv(recServQty);
			rtDtl.setQtyUnServiceableRcv(unServQty);
			rtDtl.setTotalReturnRcv(totalQty);
			toJson = ow.writeValueAsString(rtDtl);
			//
		} else {
			Thread.sleep(5000);
			// set success msg
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}

	//

}
