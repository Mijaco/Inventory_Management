package com.ibcs.desco.localStore.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LocalRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.model.LocalRequisitionDtl;
import com.ibcs.desco.localStore.model.LocalRequisitionMst;

/**
* @author Abu Taleb, IBCS 
*/

@Controller
@RequestMapping(value = "/local/requisition/")
@PropertySource("classpath:common.properties")
public class LocalRequisitionController extends Constrants  {
	
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
	
	@RequestMapping(value = "getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		
		model.put("department", department);
	 	model.put("authUser", authUser);
	 	model.put("categoryList", categoryList);

		return new ModelAndView("localStore/local/requisition/localRequisitionForm", model);
	}
	
	@RequestMapping(value = "saveForm.do", method = RequestMethod.POST)
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

		LocalRequisitionMst lsRequisitionMst = new LocalRequisitionMst();
		lsRequisitionMst.setIdenterDesignation(csRequisitionMstDtl
				.getIdenterDesignation());
		lsRequisitionMst.setDeptName(department.getDeptName());
		lsRequisitionMst.setReceivedBy(csRequisitionMstDtl.getReceivedBy());

		lsRequisitionMst.setRequisitionDate(now);
		lsRequisitionMst.setActive(csRequisitionMstDtl.isActive());
		lsRequisitionMst.setCreatedBy(userName);
		lsRequisitionMst.setCreatedDate(now);
		lsRequisitionMst.setUuid(UUID.randomUUID().toString());
		lsRequisitionMst.setReceived(false);
		lsRequisitionMst.setStorePrefix(ContentType.LOCAL_STORE.toString());
		lsRequisitionMst.setRequisitionTo(ContentType.LOCAL_STORE.toString());
		lsRequisitionMst.setRequestedDeptId(csRequisitionMstDtl
				.getRequestedDeptId());
		lsRequisitionMst.setKhathId(descoKhath.getId());
		lsRequisitionMst.setKhathName(descoKhath.getKhathName());		

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
			LocalRequisitionMst lsRequisitionMst, String roleName,
			Departments department, AuthUser authUser) {
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

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LOCAL_REQUISITION);

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
							LOCAL_REQUISITION, stateCodes[0].toString());
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
								separator, "LOCAL_REQ_SEQ");
				lsRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(lsRequisitionMst);

				LocalRequisitionMst lsRequisitionMstDb = (LocalRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"LocalRequisitionMst", "requisitionNo",
								lsRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					LocalRequisitionDtl lsRequisitionDtl = new LocalRequisitionDtl();

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
					lsRequisitionDtl.setLocalRequisitionMst(lsRequisitionMstDb);

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
			LocalRequisitionMst lsRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {

		LocalRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new LocalRequisitionApprovalHierarchyHistory();

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
		approvalHierarchyHistory.setOperationName(LOCAL_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(lsRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(lsRequisitionMst
				.getCreatedDate());
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);
		approvalHierarchyHistory.setTargetUserId(authUser.getUserid());

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

}
