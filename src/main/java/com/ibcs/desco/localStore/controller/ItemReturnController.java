package com.ibcs.desco.localStore.controller;

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
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.LsSsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.LedgerLocQty;
import com.ibcs.desco.cs.bean.Location7sMstDtl;
import com.ibcs.desco.cs.bean.Location7sQty;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtlReturnSlip;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
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
import com.ibcs.desco.subStore.bean.SSReturnSlipMstDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;

@Controller
@PropertySource("classpath:common.properties")
public class ItemReturnController extends Constrants {

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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ls/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipMstSave(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl,
			RedirectAttributes redirectAttributes) {
		
		Date now = new Date();

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		List itemList = returnSlipMstDtl.getItemCode();

		if (itemList.size() < 1) {
			return this.getStoreReturnSlipForm();
		}

		// String returnTo=request.getParameter("returnTo");
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		if (returnSlipMstDtl.getReturnTo().equalsIgnoreCase(
				ContentType.SUB_STORE.toString())) {
			return saveReturnItemData(model, returnSlipMstDtl,
					redirectAttributes);
		}
		/*
		 * DescoKhath descoKhath = (DescoKhath) commonService
		 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
		 * returnSlipMstDtl.getKhathId() + "");
		 */

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		ReturnSlipMst returnSlipMst = new ReturnSlipMst();
		returnSlipMst.setReceiveFrom(returnSlipMstDtl.getReceiveFrom());
		// returnSlipMst.setWorkOrderDate(returnSlipMstDtl.getWorkOrderDate());
		// returnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(descoKhath.getId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setSndCode(department.getDescoCode());
		returnSlipMst.setSenderDeptName(department.getDeptName());
		// returnSlipMst.setUuid(returnSlipMstDtl.getUuid());

		// set current date time as RequisitionDate. GUI date is not used here.
		returnSlipMst.setUuid(UUID.randomUUID().toString());
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setSenderStore(ContentType.LOCAL_STORE.toString());
		// Saving master and details of requisition to Table
		addStoreTicketDtls(returnSlipMstDtl, returnSlipMst, roleName,
				department, authUser, now);

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo",
						returnSlipMst.getReturnSlipNo());

		// return "redirect:/ls/returnSlip/pageShow.do";

		return returnSlipPageShowPOST(returnSlipMstdb);

	}

	public boolean addStoreTicketDtls(ReturnSlipMstDtl returnSlipMstDtl,
			ReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
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
				.getApprovalHierarchyByOperationName(LS_CS_RETURN_SLIP);

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
							LS_CS_RETURN_SLIP, stateCodes[0].toString());
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
							.setCreatedDate(now);
					returnSlipDtl.setActive(true);
					returnSlipDtl.setReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addReturnSlipHierarchyHistory(returnSlipMst, approvalHierarchy,
						stateCodes, roleName, department, authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addReturnSlipHierarchyHistory(ReturnSlipMst returnSlipMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, 
			Date now) {
		LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new LsCsReturnSlipApprovalHierarchyHistory();
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
		approvalHierarchyHistory.setOperationName(LS_CS_RETURN_SLIP);
		approvalHierarchyHistory.setCreatedBy(returnSlipMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(now);

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
	@RequestMapping(value = "/ls/returnSlip/List.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<LsSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList3 = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<CnCsReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList4 = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsXReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList5 = (List<WsCsXReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsXReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList6 = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		
		List<CnSsReturnSlipApprovalHierarchyHistory> ssStoreSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}
		List<String> operationId3 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList3.size(); i++) {
			operationId3.add(storeSlipApprovalHierarchyHistoryList3.get(i)
					.getOperationId());
		}
		List<String> operationId4 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList4.size(); i++) {
			operationId4.add(returnSlipApprovalHierarchyHistoryList4.get(i)
					.getOperationId());
		}
		List<String> operationId5 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList5.size(); i++) {
			operationId5.add(returnSlipApprovalHierarchyHistoryList5.get(i)
					.getOperationId());
		}
		List<String> operationId6 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList6.size(); i++) {
			operationId6.add(returnSlipApprovalHierarchyHistoryList6.get(i)
					.getOperationId());
		}
		
		List<String> operationId7 = new ArrayList<String>();
		for (int i = 0; i < ssStoreSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId7.add(ssStoreSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList4 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList5 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList6 = new ArrayList<ReturnSlipMst>();
		List<SSReturnSlipMst> ssReturnSlipMstList = new ArrayList<SSReturnSlipMst>();

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId4) != null) {
			returnSlipMstList4 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId4);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId5) != null) {
			returnSlipMstList5 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId5);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId6) != null) {
			returnSlipMstList6 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId6);
		}
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId3) != null) {
			ssReturnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId3);
		}
		
		List<SSReturnSlipMst> ssReturnSlipMstList1 = (List<SSReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSReturnSlipMst",
						"returnSlipNo", operationId7);
		
		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
		if (returnSlipMstList2 != null) {
			returnSlipMstList.addAll(returnSlipMstList2);
		}
		if (returnSlipMstList4 != null) {
			returnSlipMstList.addAll(returnSlipMstList4);
		}
		if (returnSlipMstList5 != null) {
			returnSlipMstList.addAll(returnSlipMstList5);
		}
		if (returnSlipMstList6 != null) {
			returnSlipMstList.addAll(returnSlipMstList6);
		}

		if (ssReturnSlipMstList != null) {
			returnSlipMstList.addAll(importObjListToSs(ssReturnSlipMstList));
		}
		
		if( ssReturnSlipMstList1 != null ) {
			returnSlipMstList.addAll( importObjListToSs(ssReturnSlipMstList1) );
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("localStore/lsToSsReturnSlipList", model);
		} else {
			return new ModelAndView("localStore/csReturnSlipList", model);
		}

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/returnSlip/ssList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofCS() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
/*		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);*/
		List<LsSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList3 = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<CnCsReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList4 = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsXReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList5 = (List<WsCsXReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsXReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsReturnSlipApprovalHierarchyHistory> returnSlipApprovalHierarchyHistoryList6 = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		
		List<CnSsReturnSlipApprovalHierarchyHistory> ssStoreSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		/*List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}*/
		List<String> operationId3 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList3.size(); i++) {
			operationId3.add(storeSlipApprovalHierarchyHistoryList3.get(i)
					.getOperationId());
		}
		List<String> operationId4 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList4.size(); i++) {
			operationId4.add(returnSlipApprovalHierarchyHistoryList4.get(i)
					.getOperationId());
		}
		List<String> operationId5 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList5.size(); i++) {
			operationId5.add(returnSlipApprovalHierarchyHistoryList5.get(i)
					.getOperationId());
		}
		List<String> operationId6 = new ArrayList<String>();
		for (int i = 0; i < returnSlipApprovalHierarchyHistoryList6.size(); i++) {
			operationId6.add(returnSlipApprovalHierarchyHistoryList6.get(i)
					.getOperationId());
		}
		
		List<String> operationId7 = new ArrayList<String>();
		for (int i = 0; i < ssStoreSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId7.add(ssStoreSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
//		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList4 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList5 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList6 = new ArrayList<ReturnSlipMst>();
		List<SSReturnSlipMst> ssReturnSlipMstList = new ArrayList<SSReturnSlipMst>();

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
/*		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}
*/
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId4) != null) {
			returnSlipMstList4 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId4);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId5) != null) {
			returnSlipMstList5 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId5);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId6) != null) {
			returnSlipMstList6 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId6);
		}
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId3) != null) {
			ssReturnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId3);
		}
		
		List<SSReturnSlipMst> ssReturnSlipMstList1 = (List<SSReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSReturnSlipMst",
						"returnSlipNo", operationId7);
		
		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
	/*	if (returnSlipMstList2 != null) {
			returnSlipMstList.addAll(returnSlipMstList2);
		}*/
		if (returnSlipMstList4 != null) {
			returnSlipMstList.addAll(returnSlipMstList4);
		}
		if (returnSlipMstList5 != null) {
			returnSlipMstList.addAll(returnSlipMstList5);
		}
		if (returnSlipMstList6 != null) {
			returnSlipMstList.addAll(returnSlipMstList6);
		}

		if (ssReturnSlipMstList != null) {
			returnSlipMstList.addAll(importObjListToSs(ssReturnSlipMstList));
		}
		
		if( ssReturnSlipMstList1 != null ) {
			returnSlipMstList.addAll( importObjListToSs(ssReturnSlipMstList1) );
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsReturnSlipList", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("localStore/lsToSsReturnSlipList", model);
		} else {
			return new ModelAndView("localStore/csReturnSlipList", model);
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
			rs.setSenderDeptName(ss.getSenderDeptName());
			returnSlipMstList.add(rs);
		}
		return returnSlipMstList;
	}

	@RequestMapping(value = "/ls/returnSlip/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreReturnSlipForm() {
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		/*
		 * List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object)
		 * commonService .getAllObjectList("DescoKhath");
		 * model.put("descoKhathList", descoKhathList); model.put("ledgerBooks",
		 * Constrants.LedgerBook.values());
		 */

		model.put("categoryList", categoryList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/lsToCsReturnSlipForm", model);
	}

	@RequestMapping(value = "/ls/returnSlip/pageShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipPageShowPOST(ReturnSlipMst returnSlipMst) {
		return getReturnSlipShow(returnSlipMst);
	}

	@RequestMapping(value = "/ls/returnSlip/pageShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipPageShowGET(
			@ModelAttribute("returnSlipMst") ReturnSlipMst returnSlipMst) {
		/*
		 * returnSlipMst = (ReturnSlipMst) commonService
		 * .getAnObjectByAnyUniqueColumn("ReturnSlipMst", "id",
		 * returnSlipMst.getId() + "");
		 */
		return getReturnSlipShow(returnSlipMst);
	}

	// @RequestMapping(value = "/ls/returnSlip/pageShow.do", method =
	// RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	@SuppressWarnings({ "unchecked" })
	public ModelAndView getReturnSlipShow(ReturnSlipMst returnSlipMst) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String returnTo = returnSlipMst.getReturnTo();
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		if (returnTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {
			SSReturnSlipMst returnSlipMstdb = ssReturnSlipMstService
					.getSSReturnSlipMst(returnSlipMst.getId());
			return getReturnSlipShowToSub(returnSlipMstdb);
		}
		ReturnSlipMst returnSlipMstdb = returnSlipMstService
				.getReturnSlipMst(returnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();
		
		//String createdBy = returnSlipMstdb.getCreatedBy();

		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", returnSlipMstdb.getCreatedBy());
		returnSlipMstdb.setCreatedBy(createBy.getName());
		
		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<LsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory",
						LS_CS_RETURN_SLIP, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory",
						LS_CS_RETURN_SLIP, operationId, OPEN);

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
						LS_CS_RETURN_SLIP, roleNameList);

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
								LS_CS_RETURN_SLIP, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}

		// Get all Location under CS || Added by Ashid
		// List<StoreLocations> storeLocationList = getStoreLocationList("CS");

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
		
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);
		
		String rolePrefix = roleName.substring(0, 7);
		
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/returnSlipShow", model);
		} else {
			// Get all Location under CS || Added by Ashid
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);
			// model.put("locationList", storeLocationList);
			// model.put("ledgerBooks", Constrants.LedgerBook.values());
			return new ModelAndView("localStore/csReturnSlipShow", model);
		}
	}

	// Return Slip (RS) Approving process
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/ls/itemReturnSlipApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApproved(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		
		boolean storeChangedFlag = false;

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {

			List<LsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn( "LsCsReturnSlipApprovalHierarchyHistory", "operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsCsReturnSlipApprovalHierarchyHistoryService
					.getLsCsReturnSlipApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_RETURN_SLIP, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_RETURN_SLIP,
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

			List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
							operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_CS_RETURN_SLIP);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<LsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsReturnSlipApprovalHierarchyHistory",
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

			LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (LsCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsCsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			/*
			 * { List <Integer> dtlIdList = returnSlipMstDtl.getDtlId(); List
			 * <Double> qtyNewServiceableList =
			 * returnSlipMstDtl.getQtyNewServiceable(); List <Double>
			 * qtyRecServiceableList = returnSlipMstDtl.getQtyRecServiceable();
			 * List <Double> qtyUnServiceableList =
			 * returnSlipMstDtl.getQtyUnServiceable(); List <Double>
			 * totalReturnList = returnSlipMstDtl.getTotalReturn(); int i = 0;
			 * for(int x:dtlIdList){ ReturnSlipDtl rsDtl =
			 * (ReturnSlipDtl)commonService
			 * .getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id", x+"");
			 * rsDtl.setQtyNewServiceable(qtyNewServiceableList.get(i));
			 * rsDtl.setQtyRecServiceable(qtyRecServiceableList.get(i));
			 * rsDtl.setQtyUnServiceable(qtyUnServiceableList.get(i));
			 * rsDtl.setTotalReturn(totalReturnList.get(i));
			 * commonService.saveOrUpdateModelObjectToDB(rsDtl); i++; } }
			 */

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_CS_RETURN_SLIP, nextStateCode + "");

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
						
						storeChangedFlag = true;
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
									LS_CS_RETURN_SLIP, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
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

					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						// return Slip Approved
						returnSlipMst.setApproved(true);
						commonService
								.saveOrUpdateModelObjectToDB(returnSlipMst);
						// now we have to insert data in store ticket mst and
						// history
						
						String userId = returnSlipMst.getCreatedBy();
						AuthUser athUser = ( AuthUser ) commonService
								.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userId);
						
						CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						csStoreTicketMst.setStoreTicketType(LS_CS_RETURN_SLIP);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setIssuedTo(department.getDeptName());
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						csStoreTicketMst.setReturnFor( athUser.getName() );
						csStoreTicketMst.setReceivedBy(userName);
						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						// csStoreTicketMst.setIssuedFor(returnSlipMst
						// .getIdenterDesignation());
						csStoreTicketMst.setFlag(false);
						csStoreTicketMst.setSndCode(returnSlipMst.getSndCode());

						// Auto generate Store Ticket number
						/*
						 * int storeTicketMaxId = (Integer) commonService
						 * .getMaxValueByObjectAndColumn( "CSStoreTicketMst",
						 * "id"); Date now = new Date(); String year =
						 * now.getYear() + ""; String mm = now.getMonth() + "";
						 * String formattedId = String.format("%06d",
						 * storeTicketMaxId + 1);
						 * csStoreTicketMst.setTicketNo(csStoreTicketNoPrefix +
						 * year.trim() + "-" + mm.trim() + "-" + formattedId);
						 */

						/*
						 * String storeTicketNo =
						 * commonService.getCustomSequence1(
						 * csStoreTicketNoPrefix, "-");
						 * commonService.saveOrUpdateCustomSequence1ToDB
						 * (storeTicketNo);
						 */
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
								.setTicketNo(storeTicketNo);
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
						storeTicketApprovalHierarchyHistory
								.setcDeptName(department.getDeptName());
						storeTicketApprovalHierarchyHistory
								.setcDesignation(authUser.getDesignation());
						// process will done and go for store ticket
						commonService
								.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						//model.addAttribute("operationId", operationId);

						//return "centralStore/csReturnSlipReport";
						return "redirect:/ls/csReturnSlipReport.do?operationId="+operationId;
					}

				}
			}
		}
		if( storeChangedFlag == true ) {
			String operationId = returnSlipMstDtl.getReturnSlipNo();
			return "redirect:/ls/showReturnSlipReport.do?operationId="+operationId;
		} else {
			return "redirect:/ls/returnSlip/List.do";
		}
	}
	
	@RequestMapping(value="/ls/showReturnSlipReport.do", method=RequestMethod.GET)
	public ModelAndView showReturnSlipReport( String operationId ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", operationId);
		return new ModelAndView("localStore/reports/lsCsReturnSlipReport", model);
	}
	
	@RequestMapping(value="/ls/csReturnSlipReport.do", method=RequestMethod.GET)
	public ModelAndView csReturnSlipReport( String operationId ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", operationId);
		return new ModelAndView("centralStore/csReturnSlipReport", model);
	}

	@RequestMapping(value = "/ls/returnSlip/sendTo.do", method = RequestMethod.GET)
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
		List<LsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsCsReturnSlipApprovalHierarchyHistoryService
				.getLsCsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_RETURN_SLIP, currentStateCode + "");
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
		LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_RETURN_SLIP, nextStateCode + "");
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

	@RequestMapping(value = "/ls/returnSlip/backTo.do", method = RequestMethod.GET)
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
		List<LsCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsCsReturnSlipApprovalHierarchyHistoryService
				.getLsCsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_RETURN_SLIP, currentStateCode + "");
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
		LsCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_RETURN_SLIP, backStateCode + "");
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

		return "redirect:/ls/returnSlip/List.do";
	}

	@RequestMapping(value = "/ls/returnSlip/rsSearch.do", method = RequestMethod.POST)
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

		@SuppressWarnings("unchecked")
		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		@SuppressWarnings("unchecked")
		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}
		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}

		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
		if (returnSlipMstList2 != null) {
			returnSlipMstList.addAll(returnSlipMstList2);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsReturnSlipList", model);
		} else {
			return new ModelAndView("localStore/csReturnSlipList", model);
		}

	}

	@RequestMapping(value = "/ls/returnSlip/viewInventoryItemCategory.do", method = RequestMethod.POST)
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

	@RequestMapping(value = "/ls/returnSlip/viewInventoryItem.do", method = RequestMethod.POST)
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
/*
			CentralStoreItems centralStoreItems = centralStoreItemsService
					.getCentralStoreItemsByItemId(selectItemFromDb.getItemId());

			selectItemFromDb
					.setCurrentStock(centralStoreItems != null ? centralStoreItems
							.getBalance() : 0.0);*/

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

	@RequestMapping(value = "/ls/returnSlip/getWwOrderToReturn", method = RequestMethod.POST, produces = "application/json")
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

	private ModelAndView saveReturnItemData(Model model,
			ReturnSlipMstDtl returnSlipMstDtl,
			RedirectAttributes redirectAttributes) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		SSReturnSlipMst returnSlipMst = new SSReturnSlipMst();

		returnSlipMst.setReceiveFrom(returnSlipMstDtl.getReceiveFrom());
		returnSlipMst.setWorkOrderDate(returnSlipMstDtl.getWorkOrderDate());
		returnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		returnSlipMst.setZone(returnSlipMstDtl.getZone());
		returnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		returnSlipMst.setKhathId(descoKhath.getId());
		returnSlipMst.setKhathName(descoKhath.getKhathName());
		returnSlipMst.setSndCode(department.getDescoCode());
		returnSlipMst.setSenderDeptName(department.getDeptName());
		// returnSlipMst.setUuid(returnSlipMstDtl.getUuid());
		// set current date time as RequisitionDate. GUI date is not used here.
		returnSlipMst.setUuid(UUID.randomUUID().toString());
		returnSlipMst.setReturnSlipDate(now);
		returnSlipMst.setActive(returnSlipMstDtl.isActive());
		returnSlipMst.setCreatedBy(userName);
		returnSlipMst.setCreatedDate(now);
		returnSlipMst.setSenderStore(ContentType.LOCAL_STORE.toString());

		// Saving Mst and Dtl of requisition to Table if any Dtl exist
		addLsToSsReturnSlipDtls(returnSlipMstDtl, returnSlipMst, roleName,
				department, authUser, now);

		SSReturnSlipMst returnSlipMstdb = (SSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
						"returnSlipNo", returnSlipMst.getReturnSlipNo());
		/*
		 * redirectAttributes .addFlashAttribute("ssreturnSlipMst",
		 * returnSlipMstdb); return "redirect:/ls/returnSlip/pageShowToSub.do";
		 * // return "localStore/lsToCsRequisitionList";
		 */
		return getReturnSlipShowToSub(returnSlipMstdb);

	}

	public boolean addLsToSsReturnSlipDtls(ReturnSlipMstDtl returnSlipMstDtl,
			SSReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
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

		// Get All Approval Hierarchy on LS_CS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_SS_RETURN_SLIP);

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
							LS_SS_RETURN_SLIP, stateCodes[0].toString());
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
								separator, "LS_SS_RS_SEQ");

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

					if (!qtyRecServicableList.isEmpty()) {
						returnSlipDtl.setQtyRecServiceable(qtyRecServicableList
								.get(i));
					} else {
						returnSlipDtl.setQtyRecServiceable(0.0);
					}

					if (!qtyUnServicableList.isEmpty()) {
						returnSlipDtl.setQtyUnServiceable(qtyUnServicableList
								.get(i));
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
					returnSlipDtl.setReturnSlipNo(returnSlipMst
							.getReturnSlipNo());

					returnSlipDtl.setCreatedBy(returnSlipMst.getCreatedBy());
					returnSlipDtl
							.setCreatedDate(now);
					returnSlipDtl.setActive(true);
					returnSlipDtl.setReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addSSReturnSlipHierarchyHistory(returnSlipMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addSSReturnSlipHierarchyHistory(SSReturnSlipMst returnSlipMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {
		LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new LsSsReturnSlipApprovalHierarchyHistory();
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
		approvalHierarchyHistory.setOperationName(LS_SS_RETURN_SLIP);
		approvalHierarchyHistory.setCreatedBy(returnSlipMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(now);

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

	@RequestMapping(value = "/ls/returnSlip/pageShowToSub.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipPageShowToSubPOST(
			SSReturnSlipMst ssreturnSlipMst) {
		return getReturnSlipShowToSub(ssreturnSlipMst);
	}

	@RequestMapping(value = "/ls/returnSlip/pageShowToSub.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipPageShowToSubGET(
			SSReturnSlipMst ssreturnSlipMst) {
		return getReturnSlipShowToSub(ssreturnSlipMst);
	}

	public ModelAndView getReturnSlipShowToSub(SSReturnSlipMst ssreturnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId()); 

		SSReturnSlipMst returnSlipMstdb = ssReturnSlipMstService
				.getSSReturnSlipMst(ssreturnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		@SuppressWarnings("unchecked")
		List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;

		// operation Id which selected by login user
		String currentStatus = "";

		@SuppressWarnings("unchecked")
		List<LsSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory",
						LS_SS_RETURN_SLIP, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		@SuppressWarnings("unchecked")
		List<LsSsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory",
						LS_SS_RETURN_SLIP, operationId, OPEN);

		int currentStateCode = rsApprovalHierarchyHistoryOpenList.get(
				rsApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

		// role id list From Auth_User by dept Id
		@SuppressWarnings("unchecked")
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		@SuppressWarnings("unchecked")
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
						LS_SS_RETURN_SLIP, roleNameList);

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
								LS_SS_RETURN_SLIP, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}
		}
		
		/*Following four lines are added by Ihteshamul Alam*/
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		returnSlipMstdb.setCreatedBy( createBy.getName() );

		// List<StoreLocations> storeLocationList = getStoreLocationList("CS");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);

		model.put("returnSlipMst", returnSlipMstdb);

		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", rsApprovalHierarchyHistoryList);
		model.put("deptName", department.getDeptName());
		model.put("returnSlipDtlList", returnSlipDtlList);
		
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);
		
		String rolePrefix = roleName.substring(0, 7);
		
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToSsReturnSlipShow", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			// Get all Location under SS || Added by Ashid
			@SuppressWarnings("unchecked")
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "SS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);
			// model.put("locationList", storeLocationList);
			// model.put("ledgerBooks", Constrants.LedgerBook.values());
			return new ModelAndView("localStore/returnSlipShowForSS", model);
		} else {
			return new ModelAndView("localStore/csReturnSlipShow", model);
		}
	}

	// Return Slip (RS) Approving process
	@RequestMapping(value = "/ls/itemReturnSlipApprovedLsToSs.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApprovedLsToSs(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		
		boolean storeChangedFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			@SuppressWarnings("unchecked")
			List<LsSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsSsReturnSlipApprovalHierarchyHistoryService
					.getLsSsReturnSlipApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_SS_RETURN_SLIP, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(returnSlipMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsSsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_SS_RETURN_SLIP,
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

			@SuppressWarnings("unchecked")
			List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSReturnSlipDtl",
							"returnSlipNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_SS_RETURN_SLIP);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			@SuppressWarnings("unchecked")
			List<LsSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			/*
			 * LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory =
			 * lsCsReturnSlipApprovalHierarchyHistoryService
			 * .getLsCsReturnSlipApprovalHierarchyHistory(ids[0]);
			 */

			LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (LsSsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"LsSsReturnSlipApprovalHierarchyHistory", "id",
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
									LS_SS_RETURN_SLIP, nextStateCode + "");

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
						
						storeChangedFlag = true;
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
									LS_SS_RETURN_SLIP, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
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
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// return Slip Approved
					returnSlipMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(returnSlipMst);
					// update returnslip dtl
					for (SSReturnSlipDtl rp : returnSlipDtlList) {
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

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						// now we have to insert data in store ticket mst and
						// history
						SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
						// csStoreTicketMst.setTicketNo(ticketNo);
						csStoreTicketMst.setStoreTicketType(LS_SS_RETURN_SLIP);
						csStoreTicketMst.setOperationId(operationId);
						// csStoreTicketMst.setIssuedTo(department.getDeptName());
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getSenderDeptName());
						String rsCreator=returnSlipMst.getCreatedBy();
						AuthUser returnUser=userService.getAuthUserByUserId(rsCreator);
						csStoreTicketMst.setReturnFor(returnUser.getName());
						csStoreTicketMst.setReceivedBy(userName);
						csStoreTicketMst.setReceivedFrom(returnSlipMst.getReceiveFrom());
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
										descoDeptCode, separator, "SS_ST_SEQ");

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

						//return "subStore/lsToSsReturnSlipReport";
						return "redirect:/ls/lsToSsReturnSlipReport.do?rsNo="+operationId;

					}

				}
			}
		}
		
		if( storeChangedFlag == true ) {
			String operationId = returnSlipMstDtl.getReturnSlipNo();
			return "redirect:/ls/lsToSsReturnSlipReportForLs.do?rsNo="+operationId;
		} else {
			return "redirect:/ls/returnSlip/List.do";
		}
	}
	
	@RequestMapping(value="/ls/lsToSsReturnSlipReportForLs.do", method=RequestMethod.GET)
	public ModelAndView lsToSsReturnSlipReportForLs( String rsNo ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", rsNo);
		return new ModelAndView("localStore/reports/lsSsReturnSlipReport", model);
	}
	
	@RequestMapping(value="/ls/lsToSsReturnSlipReport.do", method=RequestMethod.GET)
	public ModelAndView lsToSsReturnSlipReport( String rsNo ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", rsNo);
		return new ModelAndView("subStore/lsToSsReturnSlipReport", model);
	}

	@RequestMapping(value = "/ls/returnSlip/sendToLsToSs.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

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
		List<LsSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsSsReturnSlipApprovalHierarchyHistoryService
				.getLsSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_RETURN_SLIP, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_RETURN_SLIP, nextStateCode + "");
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

	@RequestMapping(value = "/ls/returnSlip/backToLsToSs.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
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

		@SuppressWarnings("unchecked")
		List<LsSsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsSsReturnSlipApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = lsSsReturnSlipApprovalHierarchyHistoryService
				.getLsSsReturnSlipApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_RETURN_SLIP, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsSsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_RETURN_SLIP, backStateCode + "");
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

		return "redirect:/ls/lsToSsReturnSlip/List.do";
	}

	/*
	 * @RequestMapping(value = "/ls/returnSlip/rsSearchLsToSs.do", method =
	 * RequestMethod.POST)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView
	 * returnSlipSerarchLsToSs(
	 * 
	 * @ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl)
	 * {
	 * 
	 * String roleName = commonService.getAuthRoleName(); String userName =
	 * commonService.getAuthUserName();
	 * 
	 * String operationId = returnSlipMstDtl.getReturnSlipNo();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * String deptId = authUser.getDeptId();
	 * 
	 * List<LsSsReturnSlipApprovalHierarchyHistory>
	 * storeSlipApprovalHierarchyHistoryList1 =
	 * (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
	 * .getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
	 * "LsSsReturnSlipApprovalHierarchyHistory", deptId, roleName, OPEN,
	 * operationId);
	 * 
	 * List<SsCsReturnSlipApprovalHierarchyHistory>
	 * storeSlipApprovalHierarchyHistoryList2 =
	 * (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
	 * .getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
	 * "SsCsReturnSlipApprovalHierarchyHistory", deptId, roleName, OPEN);
	 * List<String> operationId1 = new ArrayList<String>(); for (int i = 0; i <
	 * storeSlipApprovalHierarchyHistoryList1.size(); i++) {
	 * operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
	 * .getOperationId()); } List<String> operationId2 = new
	 * ArrayList<String>(); for (int i = 0; i <
	 * storeSlipApprovalHierarchyHistoryList2.size(); i++) {
	 * operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
	 * .getOperationId()); } List<ReturnSlipMst> returnSlipMstList=new
	 * ArrayList<ReturnSlipMst>(); List<ReturnSlipMst> returnSlipMstList1 = new
	 * ArrayList<ReturnSlipMst>(); List<ReturnSlipMst> returnSlipMstList2 = new
	 * ArrayList<ReturnSlipMst>(); if (returnSlipMstService
	 * .listReturnSlipMstByOperationIdList(operationId1) != null) {
	 * returnSlipMstList1 = returnSlipMstService
	 * .listReturnSlipMstByOperationIdList(operationId1); } if
	 * (returnSlipMstService .listReturnSlipMstByOperationIdList(operationId2)
	 * != null) { returnSlipMstList2 = returnSlipMstService
	 * .listReturnSlipMstByOperationIdList(operationId2); }
	 * 
	 * if(returnSlipMstList1!=null){
	 * returnSlipMstList.addAll(returnSlipMstList1);}
	 * if(returnSlipMstList2!=null){
	 * returnSlipMstList.addAll(returnSlipMstList2);}
	 * 
	 * Map<String, Object> model = new HashMap<String, Object>();
	 * model.put("returnSlipMstList", returnSlipMstList);
	 * 
	 * String rolePrefix = roleName.substring(0, 7);
	 * 
	 * if (rolePrefix.equals("ROLE_LS")) { return new
	 * ModelAndView("localStore/lsToSsReturnSlipList", model); } else { return
	 * new ModelAndView("localStore/ssReturnSlipList", model); }
	 * 
	 * }
	 */

	// Added by Taleb
	@RequestMapping(value = "/ls/editReturnSlipDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String editReturnSlipDtl(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			ReturnSlipDtl returnSlipDtl = gson.fromJson(cData,
					ReturnSlipDtl.class);
			Integer id = returnSlipDtl.getId();
			ReturnSlipDtl returnSlipDtldb = (ReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id", id
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

	@RequestMapping(value = "/ls/editReturnSlipDtlCs.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String editReturnSlipDtlCs(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			ReturnSlipDtl returnSlipDtl = gson.fromJson(cData,
					ReturnSlipDtl.class);
			Integer id = returnSlipDtl.getId();
			ReturnSlipDtl returnSlipDtldb = (ReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id", id
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

	// Added by Ashid
	@RequestMapping(value = "/ls/rs/setRcvedLocation.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ls/rs/getTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
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
			@SuppressWarnings("unchecked")
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
	@RequestMapping(value = "/ls/rs/getTemplocation.do", method = RequestMethod.POST)
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
			@SuppressWarnings("unchecked")
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
	@RequestMapping(value = "/ls/rs/updateLocationQty.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ls/rs/updateRRdtlAfterLocatinUpdate.do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/ls/rs/deleteLocationQty.do", method = RequestMethod.POST)
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

	// SELECT Location object List by parent location id || Added by ashid
	@RequestMapping(value = "/ls/cs/rt/saveLocation7sDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String saveLocationDtl(@RequestBody String json) throws Exception {
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

			// set success msg
			String returnSlipDtlId = obj.getLedgerName();
			ReturnSlipDtl rtDtl = (ReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipDtl", "id",
							returnSlipDtlId);
			rtDtl.setQtyNewServiceableRcv(newServQty);
			rtDtl.setQtyRecServiceableRcv(recServQty);
			rtDtl.setQtyUnServiceableRcv(unServQty);
			rtDtl.setTotalReturnRcv(totalQty);
			rtDtl.setModifiedBy(commonService.getAuthUserName());
			rtDtl.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(rtDtl);
			toJson = ow.writeValueAsString(rtDtl);
			//

			//
		} else {
			Thread.sleep(5000);
			// set success msg
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}

	//
	// SELECT Location object List by parent location id || Added by ashid
	@RequestMapping(value = "/ls/ss/rt/saveLocation7sDtl.do", method = RequestMethod.POST)
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

			// set success msg
			String returnSlipDtlId = obj.getLedgerName();

			SSReturnSlipDtl rtDtl = (SSReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSReturnSlipDtl", "id",
							returnSlipDtlId);
			rtDtl.setQtyNewServiceableRcv(newServQty);
			rtDtl.setQtyRecServiceableRcv(recServQty);
			rtDtl.setQtyUnServiceableRcv(unServQty);
			rtDtl.setTotalReturnRcv(totalQty);
			rtDtl.setModifiedBy(commonService.getAuthUserName());
			rtDtl.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(rtDtl);
			toJson = ow.writeValueAsString(rtDtl);
			//

			//
		} else {
			Thread.sleep(5000);
			// set success msg
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/ls/returnSlip/deleteItem.do", method=RequestMethod.POST)
	public ModelAndView deleteItem( ReturnSlipMst returnSlipMst ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = returnSlipMst.getId();
			String returnTo = returnSlipMst.getReturnTo();
			String returnSlipNo = returnSlipMst.getReturnSlipNo();
			if( returnTo.equalsIgnoreCase("cs") ) {
				
				ReturnSlipMst rSlipMst = ( ReturnSlipMst ) commonService.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo", returnSlipNo);
				commonService.deleteAnObjectById("ReturnSlipDtl", id);
				return this.returnSlipPageShowPOST(rSlipMst);
				
			} else if( returnTo.equalsIgnoreCase("ss") ) {
				
				SSReturnSlipMst ssReturnSlipMst = ( SSReturnSlipMst ) commonService.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "returnSlipNo", returnSlipNo);
				commonService.deleteAnObjectById("SSReturnSlipDtl", id);
				return this.getReturnSlipShowToSub(ssReturnSlipMst);
				
			} else {
				return null;
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/ls/returnSlip/editItem.do", method=RequestMethod.POST)
	@ResponseBody
	public String editItem( @RequestBody String json ) throws Exception {
		
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			SSReturnSlipDtl ssReturnSlipDtl = gson.fromJson(json,
					SSReturnSlipDtl.class);
			Integer id = ssReturnSlipDtl.getId();
			SSReturnSlipDtl updateDtl = (SSReturnSlipDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSReturnSlipDtl", "id", id
							+ "");
			updateDtl.setModifiedBy(commonService.getAuthUserName());
			updateDtl.setModifiedDate(new Date());
			updateDtl.setQtyNewServiceable(ssReturnSlipDtl
					.getQtyNewServiceable());
			updateDtl.setQtyRecServiceable(ssReturnSlipDtl
					.getQtyRecServiceable());
			updateDtl.setQtyUnServiceable(ssReturnSlipDtl
					.getQtyUnServiceable());
			updateDtl.setTotalReturn(ssReturnSlipDtl.getTotalReturn());
			commonService.saveOrUpdateModelObjectToDB(updateDtl);

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
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unused")
	@RequestMapping(value="/ls/returnSlip/csRturnSlipSaveMultipleItem.do", method=RequestMethod.POST)
	public ModelAndView csRturnSlipSaveMultipleItem( ReturnSlipMstDtl returnSlipMstDtl ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = returnSlipMstDtl.getId();
			String returnTo = returnSlipMstDtl.getReturnTo();
			String returnSlipNo = returnSlipMstDtl.getReturnSlipNo();
			
			List<String> itemCodeList = returnSlipMstDtl.getItemCode();
			List<String> descriptionList = returnSlipMstDtl.getDescription();
			List<String> uomList = returnSlipMstDtl.getUom();
			List<Double> totalReturnList = returnSlipMstDtl.getTotalReturn();
			List<Double> qtyUnServiceableList = returnSlipMstDtl.getQtyUnServiceable();
			List<Double> qtyRecServiceableList = returnSlipMstDtl.getQtyRecServiceable();
			List<Double> qtyNewServiceableList = returnSlipMstDtl.getQtyNewServiceable();
			List<String> remarksList = returnSlipMstDtl.getRemarks();
			
			if( returnTo.equalsIgnoreCase("cs") ) {
				
				ReturnSlipMst rsMst = ( ReturnSlipMst ) commonService.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo", returnSlipNo);
				for( int i = 0; i < itemCodeList.size(); i++ ) {
					ReturnSlipDtl rsDtl = new ReturnSlipDtl();
					rsDtl.setItemCode( itemCodeList.get(i));
					rsDtl.setDescription(descriptionList.get(i));
					rsDtl.setUom(uomList.get(i));
					rsDtl.setTotalReturn(totalReturnList.get(i));
					rsDtl.setQtyNewServiceable(qtyNewServiceableList.get(i));
					rsDtl.setQtyRecServiceable(qtyRecServiceableList.get(i));
					rsDtl.setQtyUnServiceable(qtyUnServiceableList.get(i));
					rsDtl.setId(null);
					rsDtl.setCreatedBy( commonService.getAuthUserName() );
					rsDtl.setCreatedDate( new Date() );
					rsDtl.setRemarks(remarksList.get(i));
					rsDtl.setReturnSlipNo(returnSlipNo);
					rsDtl.setReturnSlipMst(rsMst);
					commonService.saveOrUpdateModelObjectToDB(rsDtl);
				}
				
				return this.returnSlipPageShowPOST(rsMst);
				
			} else if( returnTo.equalsIgnoreCase("ss") ) {
				SSReturnSlipMst ssRSMst = ( SSReturnSlipMst ) commonService.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "returnSlipNo", returnSlipNo);
				
				for( int i = 0; i < itemCodeList.size(); i++ ) {
					SSReturnSlipDtl ssRSDtl = new SSReturnSlipDtl();
					ssRSDtl.setItemCode( itemCodeList.get(i));
					ssRSDtl.setDescription(descriptionList.get(i));
					ssRSDtl.setUom(uomList.get(i));
					ssRSDtl.setTotalReturn(totalReturnList.get(i));
					ssRSDtl.setQtyNewServiceable(qtyNewServiceableList.get(i));
					ssRSDtl.setQtyRecServiceable(qtyRecServiceableList.get(i));
					ssRSDtl.setQtyUnServiceable(qtyUnServiceableList.get(i));
					ssRSDtl.setId(null);
					ssRSDtl.setCreatedBy( commonService.getAuthUserName() );
					ssRSDtl.setCreatedDate( new Date() );
					ssRSDtl.setRemarks(remarksList.get(i));
					ssRSDtl.setReturnSlipNo(returnSlipNo);
					ssRSDtl.setReturnSlipMst(ssRSMst);
					commonService.saveOrUpdateModelObjectToDB(ssRSDtl);
				}
				
				return this.getReturnSlipShowToSub(ssRSMst);
				
			} else {
				return null;
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

}
