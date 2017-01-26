package com.ibcs.desco.contractor.controller;

//@author Ashid
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

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.dashboard.PndContractor;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CnCsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CnSsReturnSlipApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.service.ReturnSlipMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.bean.SSReturnSlipMstDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@PropertySource("classpath:common.properties")
public class CnToCsItemReturnController extends Constrants {

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
	CnCsReturnSlipApprovalHierarchyHistoryService cnCsReturnSlipApprovalHierarchyHistoryService;

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
	
	@Autowired
	ReturnSlipMstService returnSlipMstService;

	@Value("${desco.returnslip.prefix}")
	private String descoReturnSlipNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/pd/getReturnSlipForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#pdContractor, 'READ')")
	public ModelAndView getReturnSlip(PndContractor pdContractor) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);
			Contractor contractor = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
							department.getRemarks());

			List<PndJobMst> jobList = (List<PndJobMst>) (Object) commonService
					.getObjectListByAnyColumn("PndJobMst", "woNumber",
							contractor.getContractNo());

			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();
			model.put("categoryList", categoryList);
			model.put("deptName", department.getDeptName());
			model.put("department", department);
			model.put("contractor", contractor);
			model.put("jobList", jobList);
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());
			return new ModelAndView("pndContractor/cnPdReturnSlipForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("pndContractor/errorCnProject", model);
		}
	}

	@RequestMapping(value = "/cn/pd/returnSlip/Save.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipMstSave(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		
		if( returnSlipMstDtl.getReturnTo().equalsIgnoreCase(
				ContentType.SUB_STORE.toString()) ) {
			return this.saveSSStoreReturn( model, returnSlipMstDtl, userName, roleName );
		}
		
		Date now = new Date();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());
		Contractor contractor = contractorRepresentiveService
				.getContractorByContractNo(contractorRep.getContractNo());
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						contractor.getKhathId() + "");

		ReturnSlipMst returnSlipMst = new ReturnSlipMst();
		returnSlipMst.setReceiveFrom(authUser.getDesignation());
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
		returnSlipMst.setSenderStore(ContentType.CONTRACTOR.toString());
		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addCnPdReturnDtls(returnSlipMstDtl, returnSlipMst,
				roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
		}

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "returnSlipNo",
						returnSlipMst.getReturnSlipNo());
		return "redirect:/cn/pd/returnSlipShow.do?id="
				+ returnSlipMstdb.getId()+"&returnTo="+returnSlipMstdb.getReturnTo();

	}
	
	//Added by: Ihteshamul Alam
	public String saveSSStoreReturn( Model model, ReturnSlipMstDtl returnSlipMstDtl, String userName,
			String roleName ) {
		
		Date now = new Date();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());
		Contractor contractor = contractorRepresentiveService
				.getContractorByContractNo(contractorRep.getContractNo());
		
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						contractor.getKhathId() + "");
		
		SSReturnSlipMst ssreturnSlipMst = new SSReturnSlipMst();
		ssreturnSlipMst.setReceiveFrom(authUser.getDesignation());
		ssreturnSlipMst.setWorkOrderDate(contractor.getContractDate());
		ssreturnSlipMst.setWorkOrderNo(returnSlipMstDtl.getWorkOrderNo());
		ssreturnSlipMst.setZone(returnSlipMstDtl.getZone());
		ssreturnSlipMst.setReturnTo(returnSlipMstDtl.getReturnTo());
		ssreturnSlipMst.setKhathId(contractor.getKhathId());
		ssreturnSlipMst.setKhathName(descoKhath.getKhathName());
		ssreturnSlipMst.setUuid(UUID.randomUUID().toString());
		ssreturnSlipMst.setSndCode(department.getDescoCode());
		ssreturnSlipMst.setSenderDeptName(department.getDeptName());
		ssreturnSlipMst.setReturnSlipDate(now);
		ssreturnSlipMst.setActive(returnSlipMstDtl.isActive());
		ssreturnSlipMst.setCreatedBy(userName);
		ssreturnSlipMst.setCreatedDate(now);
		ssreturnSlipMst.setReturnTo(ContentType.SUB_STORE.toString());
		ssreturnSlipMst.setSenderStore(ContentType.CONTRACTOR.toString());
		boolean successFlag = true;
		String msg = "";
		// Saving master and details information of requisition to Table iff any
		// details exist
		successFlag = addCnPdSsReturnDtls(returnSlipMstDtl, ssreturnSlipMst,
				roleName, department, authUser);

		if (successFlag) {
			// msg="Congrats! You have submitted 1 requisition successfully.";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
		}

		SSReturnSlipMst ssreturnSlipMstdb = (SSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "returnSlipNo",
						ssreturnSlipMst.getReturnSlipNo());
		return "redirect:/cn/pd/ssReturnSlipShow.do?id="
				+ ssreturnSlipMstdb.getId();
	}

	public boolean addCnPdReturnDtls(ReturnSlipMstDtl returnSlipMstDtl,
			ReturnSlipMst returnSlipMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> newServiceableList = null;

		List<Double> reccoveryServicableList = null;

		List<Double> unServiceableList = null;

		List<Double> totalReturnList = null;

		List<String> jobNoList = null;

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

		if (returnSlipMstDtl.getJobNo() != null) {
			jobNoList = returnSlipMstDtl.getJobNo();
		}

		// Get All Approval Hierarchy on CN_SS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_PD_CS_RETURN);

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
							CN_PD_CS_RETURN, stateCodes[0].toString());
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
				/*String returnSlipNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoReturnSlipNoPrefix, descoDeptCode,
								separator, "SS_CS_RS_SEQ");*/
				// sequence add by taleb
				String returnSlipNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoReturnSlipNoPrefix, descoDeptCode,
								separator, "WS_CS_X_RS_SEQ");
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

					if (newServiceableList.size() > 0) {
						returnSlipDtl.setQtyNewServiceable(newServiceableList
								.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!reccoveryServicableList.isEmpty()) {
						returnSlipDtl
								.setQtyRecServiceable(reccoveryServicableList
										.get(i));
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
						returnSlipDtl.setTotalReturn(totalReturnList.get(i));

					} else {
						returnSlipDtl.setTotalReturn(0.0);
					}

					if (!jobNoList.isEmpty()) {
						returnSlipDtl.setJobNo(jobNoList.get(i));
					} else {
						returnSlipDtl.setJobNo("");
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
	
	//Added by: Ihteshamul Alam
	public boolean addCnPdSsReturnDtls( ReturnSlipMstDtl returnSlipMstDtl, SSReturnSlipMst ssreturnSlipMst,
			String roleName, Departments department, AuthUser authUser ) {
		
		List<String> itemCodeList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<Double> newServiceableList = null;

		List<Double> reccoveryServicableList = null;

		List<Double> unServiceableList = null;

		List<Double> totalReturnList = null;

		List<String> jobNoList = null;

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

		if (returnSlipMstDtl.getJobNo() != null) {
			jobNoList = returnSlipMstDtl.getJobNo();
		}

		// Get All Approval Hierarchy on CN_SS_RETURN_SLIP
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_PD_SS_RETURN);

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
							CN_PD_SS_RETURN, stateCodes[0].toString());
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
								separator, "WS_CS_X_RS_SEQ");
				ssreturnSlipMst.setReturnSlipNo(returnSlipNo);
				commonService.saveOrUpdateModelObjectToDB(ssreturnSlipMst);

				SSReturnSlipMst returnSlipMstDb = (SSReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
								"returnSlipNo", ssreturnSlipMst.getReturnSlipNo());
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
						returnSlipDtl.setDescription("");
					}

					if (!uomList.isEmpty()) {
						returnSlipDtl.setUom(uomList.get(i));
					} else {
						returnSlipDtl.setUom("");
					}

					if (newServiceableList.size() > 0) {
						returnSlipDtl.setQtyNewServiceable(newServiceableList
								.get(i));

					} else {
						returnSlipDtl.setQtyNewServiceable(0.0);
					}

					if (!reccoveryServicableList.isEmpty()) {
						returnSlipDtl
								.setQtyRecServiceable(reccoveryServicableList
										.get(i));
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
						returnSlipDtl.setTotalReturn(totalReturnList.get(i));

					} else {
						returnSlipDtl.setTotalReturn(0.0);
					}

					if (!jobNoList.isEmpty()) {
						returnSlipDtl.setJobNo(jobNoList.get(i));
					} else {
						returnSlipDtl.setJobNo("");
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
					returnSlipDtl.setReturnSlipNo(ssreturnSlipMst
							.getReturnSlipNo());

					returnSlipDtl.setCreatedBy(ssreturnSlipMst.getCreatedBy());
					returnSlipDtl
							.setCreatedDate(ssreturnSlipMst.getCreatedDate());
					returnSlipDtl.setActive(true);
					returnSlipDtl.setReturnSlipMst(returnSlipMstDb);

					// insert item detail in returnSlipDtl table
					commonService.saveOrUpdateModelObjectToDB(returnSlipDtl);
				}

				// Start Approval Hierarchy History insert process
				addSsReturnSlipHierarchyHistory(ssreturnSlipMst, approvalHierarchy,
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
		CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new CnCsReturnSlipApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory
				.setOperationId(returnSlipMst.getReturnSlipNo());
		approvalHierarchyHistory.setOperationName(CN_PD_CS_RETURN);
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
	
	//Added by: Ihteshamul Alam
	public void addSsReturnSlipHierarchyHistory( SSReturnSlipMst ssreturnSlipMst, ApprovalHierarchy approvalHierarchy,
			Integer[] stateCodes, String roleName, Departments department, AuthUser authUser) {
		CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = new CnSsReturnSlipApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory
				.setOperationId(ssreturnSlipMst.getReturnSlipNo());
		approvalHierarchyHistory.setOperationName(CN_PD_SS_RETURN);
		approvalHierarchyHistory.setCreatedBy(ssreturnSlipMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(ssreturnSlipMst.getCreatedDate());

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
	@RequestMapping(value = "/cn/pd/returnSlipList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReturnnListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		/*List<CnCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);*/
		
		List<CnCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryListFinal =
				new ArrayList<CnCsReturnSlipApprovalHierarchyHistory>();
		List<CnCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByFourColumnWithoutLike(
						"CnCsReturnSlipApprovalHierarchyHistory",
						"operationName", CN_PD_CS_RETURN, "actRoleName",
						roleName, "status", OPEN, "deptId", deptId);
		if(storeSlipApprovalHierarchyHistoryList != null){
			for (CnCsReturnSlipApprovalHierarchyHistory hist : storeSlipApprovalHierarchyHistoryList) {
				if(hist.getTargetUserId() == null || hist.getTargetUserId().length() == 0){
					storeSlipApprovalHierarchyHistoryListFinal.add(hist);
				} else {
					if(hist.getTargetUserId().equals(userName)){
						storeSlipApprovalHierarchyHistoryListFinal.add(hist);
					}
				}
			}
		}
		
		/*List<CnSsReturnSlipApprovalHierarchyHistory> ssStoreSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);*/
		List<CnSsReturnSlipApprovalHierarchyHistory> ssStoreSlipApprovalHierarchyHistoryListFinal =
				new ArrayList<CnSsReturnSlipApprovalHierarchyHistory>();
		List<CnSsReturnSlipApprovalHierarchyHistory> ssStoreSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByFourColumnWithoutLike(
						"CnSsReturnSlipApprovalHierarchyHistory",
						"operationName", CN_PD_SS_RETURN, "actRoleName",
						roleName, "status", OPEN, "deptId", deptId);
		
		if(ssStoreSlipApprovalHierarchyHistoryList != null){
			for (CnSsReturnSlipApprovalHierarchyHistory hist : ssStoreSlipApprovalHierarchyHistoryList) {
				if(hist.getTargetUserId() == null || hist.getTargetUserId().length() == 0){
					ssStoreSlipApprovalHierarchyHistoryListFinal.add(hist);
				} else {
					if(hist.getTargetUserId().equals(userName)){
						ssStoreSlipApprovalHierarchyHistoryListFinal.add(hist);
					}
				}
			}
		}

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryListFinal.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryListFinal.get(i)
					.getOperationId());
		}
		
		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < ssStoreSlipApprovalHierarchyHistoryListFinal.size(); i++) {
			operationId1.add(ssStoreSlipApprovalHierarchyHistoryListFinal.get(i)
					.getOperationId());
		}
		
		List<ReturnSlipMst> returnSlipMstList1 = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("ReturnSlipMst",
						"returnSlipNo", operationId);
		
		List<SSReturnSlipMst> ssreturnSlipMstList = (List<SSReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSReturnSlipMst",
						"returnSlipNo", operationId1);
		
		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		
		if( returnSlipMstList1 != null ) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
		
		if( ssreturnSlipMstList != null ) {
			returnSlipMstList.addAll( importObjListToSs(ssreturnSlipMstList) );
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", returnSlipMstList);

		//String rolePrefix = roleName.substring(0, 7);

		if (roleName.contains("ROLE_CN")) {
			return new ModelAndView("pndContractor/cnPdReturnSlipList", model);
		} else if (roleName.contains("ROLE_PROJECT")) {
			return new ModelAndView("pndContractor/pdReturnSlipList", model);
		} else if (roleName.contains("ROLE_CS")) {
			return new ModelAndView("pndContractor/cnToSsReturnSlipList", model);
		} else if (roleName.contains("ROLE_SS")) {
			return new ModelAndView("pndContractor/cnToSsReturnSlipList", model);
		} else {
			model.put(
					"errorMsg",
					"You are not authorized to view this List. Please Login as Development and Project's users.");
			return new ModelAndView("pndContractor/errorContractorMain", model);
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
			rs.setWorkOrderDate( ss.getWorkOrderDate() );
			rs.setWorkOrderNo( ss.getWorkOrderNo() );
			returnSlipMstList.add(rs);
		}
		return returnSlipMstList;
	}

	@RequestMapping(value = "/cn/returnSlip/pageShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShowCS(ReturnSlipMst returnSlipMst) {

		return returnSlipShow(returnSlipMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/pd/returnSlipShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnSlipShow(ReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();
		
		if( returnSlipMst.getReturnTo().equalsIgnoreCase(
				ContentType.SUB_STORE.toString()) ) {
			return new ModelAndView("redirect:/cn/pd/ssReturnSlipShow.do?id="+returnSlipMst.getId());
		}

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "id",
						returnSlipMst.getId() + "");

		/*
		 * The following four lines are added by Ihteshamul Alam. It will show
		 * Username instead of userid
		 */
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		returnSlipMstdb.setCreatedBy(createBy.getName());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;
		String currentStatus = "";

		List<CnCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory",
						CN_PD_CS_RETURN, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory",
						CN_PD_CS_RETURN, operationId, OPEN);

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
						CN_PD_CS_RETURN, roleNameList);

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
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_CS_RETURN, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();
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

		//String rolePrefix = roleName.substring(0, 7);
		if (roleName.contains("ROLE_CN")) {
			return new ModelAndView("pndContractor/cnPdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_PROJECT")) {
			return new ModelAndView("pndContractor/pdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_CS")) {
			// Get all Location under CS || Added by Ashid
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);

			return new ModelAndView("centralStore/cnPdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_SS")) {
			return new ModelAndView("contractor/cnToSsReturnSlipShow", model);
		} else {
			return null;
		}
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/pd/ssReturnSlipShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssReturnSlipShow(SSReturnSlipMst ssreturnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		SSReturnSlipMst returnSlipMstdb = (SSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "id",
						ssreturnSlipMst.getId() + "");

		/*
		 * The following four lines are added by Ihteshamul Alam. It will show
		 * Username instead of userid
		 */
		String userrole = returnSlipMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		returnSlipMstdb.setCreatedBy(createBy.getName());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;
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
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_RETURN, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();
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

		//String rolePrefix = roleName.substring(0, 7);
		if (roleName.contains("ROLE_CN")) {
			return new ModelAndView("pndContractor/cnPdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_PROJECT")) {
			return new ModelAndView("pndContractor/pdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_CS")) {
			// Get all Location under CS || Added by Ashid
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);

			return new ModelAndView("centralStore/cnPdReturnSlipShow", model);
		} else if (roleName.contains("ROLE_SS")) {
			return new ModelAndView("contractor/cnToSsReturnSlipShow", model);
		} else {
			return null;
		}
	}
	
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value = "/cn/pd/itemReturnSlipApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")

	public String getreturnSlipForItemSubmitApproved( Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl ) {
		return this.returnSlipForItemSubmitApproved(model, returnSlipMstDtl);
	}
	
	// Return Slip (RS) Approving process
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/pd/itemReturnSlipApproved.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String returnSlipForItemSubmitApproved(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);
		
		if( returnSlipMstDtl.getReturnTo().equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {
			return this.returnSlipForSsItemSubmitApproved( model, returnSlipMstDtl, authUser, department );
		}

		// Send return to next user who backed me
		if (!returnSlipMstDtl.getReturn_state().equals("")
				|| returnSlipMstDtl.getReturn_state().length() > 0) {
			List<CnCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnCsReturnSlipApprovalHierarchyHistory",
							"operationId", returnSlipMstDtl.getReturnSlipNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current approval hierarchy history by State Code
			CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnCsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_CS_RETURN, currentStateCode + "");
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
			CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnCsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_CS_RETURN, returnSlipMstDtl.getReturn_state());

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
			// get Item List against operation No
			String operationId = returnSlipMstDtl.getReturnSlipNo();

			ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
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
					.getApprovalHierarchyByOperationName(CN_PD_CS_RETURN);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RS No
			List<CnCsReturnSlipApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnCsReturnSlipApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnCsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnCsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// searching next State code and decision for send
			// this process to next person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CN_PD_CS_RETURN, nextStateCode + "");

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
							.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {
						//
					} else {
						Roles role = (Roles) commonService
								.getAnObjectByAnyUniqueColumn(
										"com.ibcs.desco.admin.model.Roles",
										"role", approvalHierarchy.getRoleName());
						
							if(role.getRole().contains(ROLE_PROJECT_)){
								DescoKhath descoKhath = (DescoKhath) commonService
										.getAnObjectByAnyUniqueColumn("DescoKhath",
												"id", returnSlipMst.getKhathId().toString());												
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
						
						/*List<AuthUser> userList = (List<AuthUser>) (Object) commonService
								.getObjectListByAnyColumn(
										"com.ibcs.desco.admin.model.AuthUser",
										"roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart
								.getDeptName());*/
					}

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
				// process will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CN_PD_CS_RETURN, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
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
					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);
					if (csStoreTicketMstdb == null) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						returnSlipMst.setApproved(true);
						commonService
								.saveOrUpdateModelObjectToDB(returnSlipMst);

						// now insert data in store ticket mst and history
						CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
						csStoreTicketMst.setStoreTicketType(CN_PD_CS_RETURN);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						AuthUser returnUser=userService.getAuthUserByUserId(returnSlipMst.getCreatedBy());
						csStoreTicketMst.setReturnFor(returnUser.getName());
						// csStoreTicketMst.setReceivedBy(userName);
						csStoreTicketMst.setFlag(false);
						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst.getSndCode());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService
								.getOperationIdByPrefixAndSequenceName(
										descoStoreTicketNoPrefix,
										descoDeptCode, separator, "CS_ST_SEQ");

						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService
								.saveOrUpdateModelObjectToDB(csStoreTicketMst);

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
						commonService
								.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						model.addAttribute("operationId", operationId);

						// return "centralStore/returnSlipReportCN";

						return "redirect:/cn/pd/csReturnSlipReport.do?rsNo="
								+ operationId;
					}

				}
			}
		}

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CS")) {
			return "redirect:/ls/returnSlip/List.do";
		} else {
			if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
				return "redirect:/cn/pd/returnSlipReport.do?rsNo="
						+ returnSlipMstDtl.getReturnSlipNo();
			}
			return "redirect:/cn/pd/returnSlipList.do";
		}
	}
	
	@SuppressWarnings("unchecked")
	//Added by: Ihteshamul Alam
	public String returnSlipForSsItemSubmitApproved( Model model, ReturnSlipMstDtl returnSlipMstDtl, AuthUser authUser, Departments department ) {
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
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
			// get current approval hierarchy history by State Code
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnSsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnSsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_RETURN, currentStateCode + "");
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
			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsReturnSlipApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_RETURN, returnSlipMstDtl.getReturn_state());

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
			// get Item List against operation No
			String operationId = returnSlipMstDtl.getReturnSlipNo();

			SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService
					.getAnObjectByAnyUniqueColumn("SSReturnSlipMst",
							"returnSlipNo", operationId);

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

			CnSsReturnSlipApprovalHierarchyHistory approvalHierarchyHistory = (CnSsReturnSlipApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnSsReturnSlipApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// searching next State code and decision for send
			// this process to next person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CN_PD_SS_RETURN, nextStateCode + "");

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
							.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {
						//
					} else {
						Roles role = (Roles) commonService
								.getAnObjectByAnyUniqueColumn(
										"com.ibcs.desco.admin.model.Roles",
										"role", approvalHierarchy.getRoleName());
						
						if(role.getRole().contains(ROLE_PROJECT_)){
							DescoKhath descoKhath = (DescoKhath) commonService
									.getAnObjectByAnyUniqueColumn("DescoKhath",
											"id", returnSlipMst.getKhathId().toString());												
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
						
						/*List<AuthUser> userList = (List<AuthUser>) (Object) commonService
								.getObjectListByAnyColumn(
										"com.ibcs.desco.admin.model.AuthUser",
										"roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart
								.getDeptName());*/
					}

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
				// process will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CN_PD_SS_RETURN, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
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
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setModifiedBy(userName);
						approvalHierarchyHistory.setModifiedDate(new Date());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						returnSlipMst.setApproved(true);
						commonService
								.saveOrUpdateModelObjectToDB(returnSlipMst);

						// now insert data in store ticket mst and history
						SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
						csStoreTicketMst.setStoreTicketType(CN_PD_SS_RETURN);
						csStoreTicketMst.setOperationId(operationId);
						csStoreTicketMst.setWorkOrderNo(returnSlipMst
								.getWorkOrderNo());
						csStoreTicketMst.setWorkOrderDate(returnSlipMst
								.getWorkOrderDate());
						csStoreTicketMst.setReturnBy(returnSlipMst
								.getReceiveFrom());
						AuthUser returnUser=userService.getAuthUserByUserId(returnSlipMst.getCreatedBy());
						csStoreTicketMst.setReturnFor(returnUser.getName());
						// csStoreTicketMst.setReceivedBy(userName);
						csStoreTicketMst.setFlag(false);
						csStoreTicketMst.setKhathId(returnSlipMst.getKhathId());
						csStoreTicketMst.setKhathName(returnSlipMst
								.getKhathName());
						csStoreTicketMst.setSndCode(returnSlipMst.getSndCode());

						String descoDeptCode = department.getDescoCode();
						String storeTicketNo = commonService
								.getOperationIdByPrefixAndSequenceName(
										descoStoreTicketNoPrefix,
										descoDeptCode, separator, "SS_ST_SEQ");

						csStoreTicketMst.setTicketNo(storeTicketNo);

						commonService
								.saveOrUpdateModelObjectToDB(csStoreTicketMst);

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
						commonService
								.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

						model.addAttribute("operationId", operationId);

						// return "centralStore/returnSlipReportCN";

						return "redirect:/cn/pd/csReturnSlipReport.do?rsNo="
								+ operationId;
					}

				}
			}
		}

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_SS")) {
			return "redirect:/ls/returnSlip/List.do";
		} else {
			if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
				return "redirect:/cn/pd/returnSlipReport.do?rsNo="
						+ returnSlipMstDtl.getReturnSlipNo();
			}
			return "redirect:/cn/pd/returnSlipList.do";
		}
	}

	@RequestMapping(value = "/cn/pd/returnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView cnTopdReturnSlipReport(String rsNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", rsNo);
		return new ModelAndView("pndContractor/reports/cnPdReturnReportMid",
				model);
	}

	@RequestMapping(value = "/cn/pd/csReturnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView cnTopdReturnSlipReportCS(String rsNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", rsNo);
		return new ModelAndView("centralStore/returnSlipReportCN", model);
	}

	// @RequestMapping(value = "/cn/returnSlip/sendTo.do", method =
	// RequestMethod.GET)
	@SuppressWarnings("unchecked")
	@PreAuthorize("isAuthenticated()")
	public String contractorItemSendTo(
			Model model,
			@ModelAttribute("returnSlipMstDtl") SSReturnSlipMstDtl returnSlipMstDtl) {

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

	// @RequestMapping(value = "/cn/returnSlip/backTo.do", method =
	// RequestMethod.GET)
	@SuppressWarnings("unchecked")
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

	// @RequestMapping(value = "/cn/returnSlip/rsSearch.do", method =
	// RequestMethod.POST)
	@SuppressWarnings("unchecked")
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

	// @RequestMapping(value = "/cn/returnSlip/cnViewInventoryItem.do", method =
	// RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String cnViewInventoryItem(@RequestBody String json)
			throws Exception {
		String toJson = "";
		String s = json.substring(1, json.length() - 1);
		String[] strValue = s.split(",");
		String itemId[] = strValue[0].split(":");
		String contractId = strValue[1].toString();
		PndJobDtl selectItemFromDb = pndJobDtlService.getPndJobDtl(
				itemId[1].toString(), contractId);

		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(selectItemFromDb);

		return toJson;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/pdReturnSlip/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String cnPdReturnSlipSendTo(
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
		
		if( returnSlipMstDtl.getReturnTo().equalsIgnoreCase( ContentType.SUB_STORE.toString() ) ) {
			return this.sendToSS( model, returnSlipMstDtl, department, authUser );
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
		approvalHierarchyHistory.setCreatedDate(new Date());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_CS_RETURN, nextStateCode + "");
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
	private String sendToSS( Model model, ReturnSlipMstDtl returnSlipMstDtl, Departments department, AuthUser authUser ) {
		
		String rrNo = returnSlipMstDtl.getReturnSlipNo();
		String justification = returnSlipMstDtl.getJustification();
		String nextStateCode = returnSlipMstDtl.getStateCode();
		
		String userName = commonService.getAuthUserName();
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
						CN_PD_SS_RETURN, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setCreatedDate(new Date());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
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

		return "redirect:/cn/returnSlip/List.do";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/returnSlip/pageShowCs.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getReturnSlipShow(SSReturnSlipMst returnSlipMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		ReturnSlipMst returnSlipMstdb = returnSlipMstService
				.getReturnSlipMst(returnSlipMst.getId());

		String operationId = returnSlipMstdb.getReturnSlipNo();

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						operationId);

		String buttonValue = null;

		List<ApprovalHierarchy> nextManReqProcs = null;
		List<ApprovalHierarchy> nextManReqProcs1 = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory",
						CN_PD_CS_RETURN, operationId, DONE);

		if (!rsApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = rsApprovalHierarchyHistoryList.get(
					rsApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnCsReturnSlipApprovalHierarchyHistory> rsApprovalHierarchyHistoryOpenList = (List<CnCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnCsReturnSlipApprovalHierarchyHistory",
						CN_PD_CS_RETURN, operationId, OPEN);

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
						CN_PD_CS_RETURN, roleNameList);

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
								CN_PD_CS_RETURN, stateCode + "");
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

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CO")) {
			return new ModelAndView("contractor/cnReturnSlipShow", model);
		}
		else if (rolePrefix.equals("ROLE_CS")) {
			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationListJson = commonService
					.getLocationListForGrid(storeLocationList);
			String ledgerListJson = commonService.getLedgerListForGrid();

			model.put("locationList", locationListJson);
			model.put("ledgerBooks", ledgerListJson);
			return new ModelAndView("contractor/cnToSsReturnSlipShow", model);
		} else {
			return new ModelAndView("contractor/projects/cnReturnSlipShow", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cn/sendToCnCsPdReturn.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String sendToCnCsPdReturn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {
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
		CnCsReturnSlipApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnCsReturnSlipApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_CS_RETURN, nextStateCode + "");
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
	@RequestMapping(value = "/cn/backToCnCsPdReturn.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String backToCnPdReturn(
			Model model,
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

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
		approvalHierarchyHistoryBackState.setTargetUserId(backedUserId);

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/ls/returnSlip/List.do";
	}

}
