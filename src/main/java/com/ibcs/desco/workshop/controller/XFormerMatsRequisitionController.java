package com.ibcs.desco.workshop.controller;

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
import com.ibcs.desco.common.bean.MyPair;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnWsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.RequisitionLock;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.CnPdRequisitionMstDtl;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CentralStoreRequisitionDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.workshop.bean.CnWsRequisitionMstDtl;
import com.ibcs.desco.workshop.model.JobCardTemplate;
import com.ibcs.desco.workshop.model.WsJobSummary;

@Controller
@RequestMapping(value = "/cnws")
@PropertySource("classpath:common.properties")
public class XFormerMatsRequisitionController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	CentralStoreRequisitionDtlService centralStoreRequisitionDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.requisition.unlock.expired.hours}")
	private String unlockExpiredHours;

	@Value("${desco.workshop.indentor}")
	private String wsIndentor;

	@Value("${desco.ws.department.code}")
	private String descoWSDeptCode;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMatsRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getMatsRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			// String deptId = authUser.getDeptId();
			ContractorRepresentive contractorRep = contractorRepresentiveService
					.getContractorRep(authUser.getUserid());

			List<JobCardTemplate> inventoryLookupItemList = (List<JobCardTemplate>) (Object) commonService
					.getObjectListByAnyColumn("JobCardTemplate", "typeOfWork",
							REPAIR_WORKS);

			for (JobCardTemplate jct : inventoryLookupItemList) {
				List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
						.getObjectListByTwoColumn("WsJobSummary", "itemCode",
								jct.getItemCode(), "contractNo",
								contractorRep.getContractNo());
				if (wsJobSummaryDbList.size() > 0 && wsJobSummaryDbList!= null) {
					jct.setRemainingQty(wsJobSummaryDbList.get(0)
							.getRemainingQty());
				}else{
					jct.setRemainingQty(0.0);
				}				
			}

			model.put("contractorRep", contractorRep);
			model.put("inventoryLookupItemList", inventoryLookupItemList);

			return new ModelAndView(
					"workshop/transformerMaterials/getMatsRequisitionForm",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	@RequestMapping(value = "/wsMatsRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSave(
			CnWsRequisitionMstDtl cnWsRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", contractorRep.getContractNo());

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		Date now = new Date();

		CentralStoreRequisitionMst csReqMst = new CentralStoreRequisitionMst();
		csReqMst.setActive(true);
		csReqMst.setApproved(false);
		csReqMst.setCreatedBy(userName);
		csReqMst.setCreatedDate(now);
		csReqMst.setDeptName(department.getDeptName());
		csReqMst.setIdenterDesignation(wsIndentor);
		csReqMst.setKhathId(descoKhath.getId());
		csReqMst.setKhathName(descoKhath.getKhathName());
		csReqMst.setReceived(false);
		csReqMst.setRequisitionTo(ContentType.CENTRAL_STORE.toString());
		csReqMst.setWorkOrderNumber(contractor.getContractNo());
		csReqMst.setUuid(UUID.randomUUID().toString());
		csReqMst.setSenderStore("cnws");
		csReqMst.setReceivedBy(contractorRep.getRepresentiveName());
		csReqMst.setRequisitionDate(now);

		CentralStoreRequisitionMst csRequisitionMstDb = this
				.contractorRequisitionDtlSave(csReqMst, cnWsRequisitionMstDtl,
						userName, roleName, department, now, authUser);

		return this.showXformerMatsRequisition(csRequisitionMstDb);
	}

	@SuppressWarnings("unchecked")
	private CentralStoreRequisitionMst contractorRequisitionDtlSave(
			CentralStoreRequisitionMst csRequisitionMst,
			CnWsRequisitionMstDtl cnWsRequisitionMstDtl, String userName,
			String roleName, Departments department, Date now, AuthUser authUser) {

		List<String> dtlIdList = null;
		List<Double> requiredQuantityList = null;
		List<String> dtlRemarks = null;

		String requisitionNo = "";

		if (cnWsRequisitionMstDtl.getDtlId() != null) {
			dtlIdList = cnWsRequisitionMstDtl.getDtlId();
		}

		if (cnWsRequisitionMstDtl.getQuantityRequired() != null) {
			requiredQuantityList = cnWsRequisitionMstDtl.getQuantityRequired();
		}

		if (cnWsRequisitionMstDtl.getRemarksDtl() != null) {
			dtlRemarks = cnWsRequisitionMstDtl.getRemarksDtl();
		}

		CentralStoreRequisitionMst csRequisitionMstDb = null;
		String requisitionTo = csRequisitionMst.getRequisitionTo();
		if (dtlIdList != null) {

			List<JobCardTemplate> templatesItemList = (List<JobCardTemplate>) (Object) commonService
					.getObjectListByAnyColumnValueList("JobCardTemplate", "id",
							dtlIdList);

			// Requisition Master Save(Insert)
			// String descoDeptCode = department.getDescoCode();
			Departments wsDept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							descoWSDeptCode);

			String descoWSDeptCode = wsDept.getDescoCode();
			// sequence remove by taleb
			/*requisitionNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoRequisitionNoPrefix, descoWSDeptCode,
							separator, "WS_CS_X_REQ_SEQ");*/
			// sequence add by taleb
			requisitionNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoRequisitionNoPrefix, descoWSDeptCode,
							separator, "CN_CS_REQ_SEQ");
			// ---------------------------
			csRequisitionMst.setSndCode(wsDept.getDescoCode());
			csRequisitionMst.setRequisitionNo(requisitionNo);
			commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

			// get Last Requisition by Requisition No
			csRequisitionMstDb = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
							"requisitionNo", requisitionNo);

			List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>();

			int count = 0;
			for (JobCardTemplate jobCardTemplate : templatesItemList) {
				CentralStoreRequisitionDtl cnWsCsRequisitionDtl = new CentralStoreRequisitionDtl(
						null, jobCardTemplate.getItemCode(),
						jobCardTemplate.getItemName(),
						jobCardTemplate.getUnit(),
						requiredQuantityList.get(count), requisitionNo, true,
						dtlRemarks.get(count), userName, now,
						csRequisitionMstDb);
				if (requiredQuantityList.get(count) > 0) {
					/*------------Remaining Quantity should be minus form WS_JOB_SUMMARY When Requisition Save-----------------*/
					List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
							.getObjectListByTwoColumn("WsJobSummary",
									"itemCode", jobCardTemplate.getItemCode(),
									"contractNo",
									csRequisitionMstDb.getWorkOrderNumber());
					if (wsJobSummaryDbList.size() > 0) {
						WsJobSummary jobCardSummaryDb = wsJobSummaryDbList
								.get(0);
						double remQty = jobCardSummaryDb.getRemainingQty();
						jobCardSummaryDb.setRemainingQty(remQty
								- cnWsCsRequisitionDtl.getQuantityRequired());

						jobCardSummaryDb.setModifiedBy(userName);
						jobCardSummaryDb.setModifiedDate(new Date());
						//
						commonService
								.saveOrUpdateModelObjectToDB(jobCardSummaryDb);
					} else {
						continue;
					}
					/*------------------------------*/
					commonService
							.saveOrUpdateModelObjectToDB(cnWsCsRequisitionDtl);
				} else {
					count++;
					continue;
				}
				// for lock Table
				MyPair pair = new MyPair(jobCardTemplate.getItemCode(),
						requiredQuantityList.get(count));
				itemCodeAndQtyList.add(pair);
				count++;
			}
			// requisition item Lock
			this.requisitionLock(itemCodeAndQtyList, requisitionNo, userName,
					now, requisitionTo);
		}

		this.addStoreRequisitionHierarchyHistory(csRequisitionMstDb, roleName,
				department, authUser, requisitionTo);

		return csRequisitionMstDb;
	}

	private void requisitionLock(List<MyPair> itemCodeAndQtyList,
			String requisitionNo, String userName, Date now,
			String requisitionTo) {
		int extendHours = Integer.parseInt(unlockExpiredHours);
		Date unlockDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(unlockDate);
		c.add(Calendar.HOUR, extendHours);
		unlockDate = c.getTime();

		List<MyPair> finalItemCodeAndQtyList = new ArrayList<MyPair>();
		boolean flag = false;
		for (MyPair m : itemCodeAndQtyList) {
			String icode = m.key();
			for (MyPair mp : finalItemCodeAndQtyList) {
				if (m.key().equals(mp.key())) {
					double nn = mp.value() + m.value();
					MyPair pair = new MyPair(icode, nn);
					finalItemCodeAndQtyList.remove(mp);
					finalItemCodeAndQtyList.add(pair);
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (finalItemCodeAndQtyList.size() == 0) {
				finalItemCodeAndQtyList.add(m);
			}

			if (flag) {
				finalItemCodeAndQtyList.add(m);
			}
		}

		// lock table add data
		for (MyPair mpr : finalItemCodeAndQtyList) {
			RequisitionLock requisitionLock = new RequisitionLock(null,
					requisitionNo, mpr.key().toString(), requisitionTo, true,
					mpr.value(), now, unlockDate, userName, now, true);
			// if (mpr.value() > 0) {
			commonService.saveOrUpdateModelObjectToDB(requisitionLock);
			// }
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			CentralStoreRequisitionMst csRequisitionMst, String roleName,
			Departments department, AuthUser authUser, String requisitionTo) {

		// Get All Approval Hierarchy on CN_WS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = null;
		if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
			approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN_WS_CS_REQUISITION);
		}
		// else if (requisitionTo.equals(ContentType.SUB_STORE.toString())) {
		// approvalHierarchyList = approvalHierarchyService
		// .getApprovalHierarchyByOperationName(CN_WS_SS_REQUISITION);
		// }

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		ApprovalHierarchy approvalHierarchy = null;
		if (stateCodes.length > 0) {

			if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
				approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, stateCodes[0].toString());
			}
			// else if (requisitionTo.equals(ContentType.SUB_STORE.toString()))
			// {
			// approvalHierarchy = approvalHierarchyService
			// .getApprovalHierarchyByOperationNameAndSateCode(
			// CN_PD_SS_REQUISITION, stateCodes[0].toString());
			// }
		}
		if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
			CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new CnWsCsRequisitionApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(roleName);
			approvalHierarchyHistory.setcDeptName(department.getDeptName());
			approvalHierarchyHistory.setDeptId(department.getDeptId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setOperationId(csRequisitionMst
					.getRequisitionNo());
			approvalHierarchyHistory.setOperationName(CN_WS_CS_REQUISITION);
			approvalHierarchyHistory.setCreatedBy(csRequisitionMst
					.getCreatedBy());
			approvalHierarchyHistory.setCreatedDate(csRequisitionMst
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
		/*
		 * else { CnSsRequisitionApprovalHierarchyHistory
		 * approvalHierarchyHistory = new
		 * CnSsRequisitionApprovalHierarchyHistory();
		 * approvalHierarchyHistory.setActRoleName(roleName);
		 * approvalHierarchyHistory.setcDeptName(department.getDeptName());
		 * approvalHierarchyHistory.setDeptId(department.getDeptId());
		 * approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		 * approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		 * approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		 * approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
		 * .getApprovalHeader());
		 * approvalHierarchyHistory.setOperationId(csRequisitionMst
		 * .getRequisitionNo());
		 * approvalHierarchyHistory.setOperationName(CN_PD_SS_REQUISITION);
		 * approvalHierarchyHistory.setCreatedBy(csRequisitionMst
		 * .getCreatedBy());
		 * approvalHierarchyHistory.setCreatedDate(csRequisitionMst
		 * .getCreatedDate()); approvalHierarchyHistory.setStatus(OPEN);
		 * approvalHierarchyHistory.setActive(true);
		 * 
		 * if (stateCodes.length > 0) { // All time start with 1st // State code
		 * set from approval Hierarchy Table
		 * approvalHierarchyHistory.setStateCode(stateCodes[0]);
		 * approvalHierarchyHistory.setStateName(approvalHierarchy
		 * .getStateName()); }
		 * 
		 * // Insert a row to Approval Hierarchy History Table
		 * commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
		 * }
		 */

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showXformerMatsRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showXformerMatsRequisition(
			@ModelAttribute("csRequisitionMst") CentralStoreRequisitionMst bean) {

		CentralStoreRequisitionMst cnReqMst = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"id", bean.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CentralStoreRequisitionDtl> cnReqDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"centralStoreRequisitionMst.id", cnReqMst.getId() + "");

		for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : cnReqDtlList) {
			List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
					.getObjectListByTwoColumn("WsJobSummary", "itemCode",
							centralStoreRequisitionDtl.getItemCode(),
							"contractNo", cnReqMst.getWorkOrderNumber());
			if (wsJobSummaryDbList.size() > 0) {
				centralStoreRequisitionDtl.setRemainingQty(wsJobSummaryDbList
						.get(0).getRemainingQty());
			}
		}

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
						cnReqMst.getWorkOrderNumber());
		// cnReqMst.setWorkOrderDate(contractor.getContractDate());

		//
		String buttonValue = null;
		List<ApprovalHierarchy> nextManReqProcs = null;
		String currentStatus = "";
		List<CnWsCsRequisitionApprovalHierarchyHistory> csApprovalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, cnReqMst.getRequisitionNo(), DONE);

		if (!csApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = csApprovalHierarchyHistoryList.get(
					csApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnWsCsRequisitionApprovalHierarchyHistory> csApprovalHierarchyHistoryOpenList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, cnReqMst.getRequisitionNo(), OPEN);

		int currentStateCode = csApprovalHierarchyHistoryOpenList.get(
				csApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId",
						department.getDeptId());
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
						CN_WS_CS_REQUISITION, roleNameList);

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
		if (!csApprovalHierarchyHistoryOpenList.isEmpty()
				&& csApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = csApprovalHierarchyHistoryOpenList.get(
					csApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// Decide for return or not
			returnStateCode = csApprovalHierarchyHistoryOpenList.get(
					csApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_WS_CS_REQUISITION, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();

		}

		model.put("returnStateCode", returnStateCode);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", csApprovalHierarchyHistoryList);

		model.put("cnReqMst", cnReqMst);
		model.put("cnReqDtlList", cnReqDtlList);
		model.put("contractor", contractor);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		String roleName = commonService.getAuthRoleName();
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView(
					"workshop/transformer/requisition/cnWsMatsRequisitionShowCN",
					model);
		} else {
			return new ModelAndView(
					"workshop/transformer/requisition/cnWsMatsRequisitionShow",
					model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnWsMatsRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnWsMatsRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login person Role and login person information
		String roleName = commonService.getAuthRoleName();
		// String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_KHATH_TRANSFER
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationName", CN_WS_CS_REQUISITION, "actRoleName",
						roleName, "status", OPEN);

		/*
		 * List<CnSsRequisitionApprovalHierarchyHistory>
		 * approvalHierarchyHistoryListSS =
		 * (List<CnSsRequisitionApprovalHierarchyHistory>) (Object)
		 * commonService .getObjectListByThreeColumn(
		 * "CnSsRequisitionApprovalHierarchyHistory", "operationName",
		 * CN_PD_SS_REQUISITION, "actRoleName", roleName, "status", OPEN);
		 */

		// get operationId List from approval hierarchy history
		// String[] operationId = new
		// String[approvalHierarchyHistoryList.size()];
		// int x = 0;
		List<String> operationId = new ArrayList<String>();
		for (CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId.add(approvalHierarchyHistory.getOperationId());
			// operationId[x] = approvalHierarchyHistory.getOperationId();
			// x++;
		}

		/*
		 * String[] operationId1 = new String[approvalHierarchyHistoryListSS
		 * .size()]; int y = 0; for (CnSsRequisitionApprovalHierarchyHistory
		 * approvalHierarchyHistory : approvalHierarchyHistoryListSS) {
		 * operationId1[y] = approvalHierarchyHistory.getOperationId(); y++; }
		 * 
		 * String[] operationIds = new String[operationId1.length +
		 * operationId.length]; operationIds = (String[])
		 * ArrayUtils.addAll(operationId, operationId1);
		 */
		// get all cnPdRequisitionMst List which open for login user

		List<CentralStoreRequisitionMst> cnWsRequisitionMstList = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						operationId);
		// sent to the browser
		model.put("cnWsRequisitionMstList", cnWsRequisitionMstList);
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView(
					"workshop/transformer/requisition/cnToWsMatsRequisitionListCN",
					model);
		} else {
			return new ModelAndView(
					"workshop/transformer/requisition/cnToWsMatsRequisitionList",
					model);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateWsRequisition.do", method = RequestMethod.POST)
	public String updateWsRequisition(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			CentralStoreRequisitionDtl bean = gson.fromJson(json,
					CentralStoreRequisitionDtl.class);

			CentralStoreRequisitionDtl csDtlDb = (CentralStoreRequisitionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl",
							"id", "" + bean.getId());
			// remove by taleb
			//double itemQty = csDtlDb.getQuantityRequired();

			csDtlDb.setQuantityRequired(bean.getQuantityRequired());
			csDtlDb.setRemarks(bean.getRemarks());
			csDtlDb.setModifiedBy(commonService.getAuthUserName());
			csDtlDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csDtlDb);

		
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/deleteAnItem.do", method = RequestMethod.POST)
	 * public String deleteAnItem(@RequestBody String json) throws Exception {
	 * Gson gson = new GsonBuilder().create(); Boolean isJson =
	 * commonService.isJSONValid(json); String toJson = ""; ObjectWriter ow =
	 * new ObjectMapper().writer() .withDefaultPrettyPrinter(); if (isJson) {
	 * CentralStoreRequisitionDtl bean = gson.fromJson(json,
	 * CentralStoreRequisitionDtl.class);
	 * 
	 * CentralStoreRequisitionDtl csDtlDb = (CentralStoreRequisitionDtl)
	 * commonService .getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl",
	 * "id", "" + bean.getId());
	 * 
	 * commonService.deleteAnObjectById("CentralStoreRequisitionDtl",
	 * bean.getId()); toJson = ow.writeValueAsString("success"); } else {
	 * Thread.sleep(2000); toJson = ow.writeValueAsString("fail"); } return
	 * toJson; }
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAnItem.do", method = RequestMethod.POST)
	public ModelAndView deleteAnItem(CentralStoreRequisitionDtl bean)
			throws Exception {

		CentralStoreRequisitionDtl csDtlDb = (CentralStoreRequisitionDtl) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl",
						"id", "" + bean.getId());

		/*------------Remaining Quantity should be plus to WS_JOB_SUMMARY When Requisition Item Delete-----------------*/
		List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
				.getObjectListByTwoColumn("WsJobSummary", "itemCode", csDtlDb
						.getItemCode(), "contractNo", csDtlDb
						.getCentralStoreRequisitionMst().getWorkOrderNumber());

		if (wsJobSummaryDbList.size() > 0) {
			WsJobSummary jobCardSummaryDb = wsJobSummaryDbList.get(0);
			double remQty = jobCardSummaryDb.getRemainingQty();
			jobCardSummaryDb.setRemainingQty(remQty
					+ csDtlDb.getQuantityRequired());

			jobCardSummaryDb.setModifiedBy(commonService.getAuthUserName());
			jobCardSummaryDb.setModifiedDate(new Date());
			//
			commonService.saveOrUpdateModelObjectToDB(jobCardSummaryDb);
		}
		/*--------Requisition Lock Delete---------*/

		List<RequisitionLock> rl = (List<RequisitionLock>) (Object) commonService
				.getObjectListByThreeColumn("RequisitionLock", "requisitionNo",
						csDtlDb.getCentralStoreRequisitionMst()
								.getRequisitionNo(), "itemCode", csDtlDb
								.getItemCode(), "storeCode",
						ContentType.CENTRAL_STORE.toString());
		if (rl != null) {
			commonService.deleteAnObjectById("RequisitionLock", rl.get(0)
					.getId());
		}
		/*--------Requisition Item Delete---------*/
		commonService.deleteAnObjectById("CentralStoreRequisitionDtl",
				bean.getId());

		return this.showXformerMatsRequisition(csDtlDb
				.getCentralStoreRequisitionMst());
	}

	@RequestMapping(value = "/sendToCnWsMatsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String sendToCnPdRequisition(
			Model model,
			@ModelAttribute("cnWsRequisitionMst") CnWsRequisitionMstDtl csRequisitionMstDtl) {

		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update requisition qty
		CentralStoreRequisitionMst cnWsReqMstDB = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.sendToForCS(requisitionNo, userName, justification,
					nextStateCode, deptId, department, authUser);
		}

		// Show Pending Requisition List
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CS")) {
			return "redirect:/ls/requisitionList.do";
		} else {
			return "redirect:/cnws/cnPdRequisitionList.do";
		}
		// return this.getCnPdRequisitionList();
	}

	@SuppressWarnings("unchecked")
	private void sendToForCS(String requisitionNo, String userName,
			String justification, String nextStateCode, String deptId,
			Departments department, AuthUser authUser) {
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id",
						ids[0] + "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, currentStateCode + "");
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
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnWsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, nextStateCode);
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(requisitionNo);
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
	}

	@RequestMapping(value = "/backToCnWsMatsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String backToCnWsRequisition(
			Model model,
			@ModelAttribute("cnWsRequisitionMst") CnWsRequisitionMstDtl csRequisitionMstDtl) {
		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String backStateCode = csRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		CentralStoreRequisitionMst cnWsReqMstDB = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", requisitionNo);

		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.backToCS(requisitionNo, authUser, roleName, userName,
					justification, backStateCode, deptId, department);
		}

		// Show Pending Requisition List
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CS")) {
			return "redirect:/ls/requisitionList.do";
		} else {
			return "redirect:/cnws/cnWsRequisitionList.do";
		}
	}

	@SuppressWarnings("unchecked")
	private void backToCS(String requisitionNo, AuthUser authUser,
			String roleName, String userName, String justification,
			String backStateCode, String deptId, Departments department) {
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id",
						ids[0] + "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, currentStateCode + "");
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
		approvalHierarchyHistory.setStateName(BACK + "ED");
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnWsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, backStateCode);
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(approvalHierarchy
				.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(requisitionNo + "");
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
	}

	@RequestMapping(value = "/approveCnWsMatsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView approveCnWsMatsRequisition(
			@ModelAttribute("cnWsRequisitionMst") CnWsRequisitionMstDtl bean) {
		String requisitionNo = bean.getRequisitionNo();
		String justification = bean.getJustification();
		// List<String> cnReqDtlIdList = bean.getCnReqDtlId();
		// List<Double> requiredQtyList = bean.getQuantityRequired();

		//String user = commonService.getAuthUserName();
		//String roleName = commonService.getAuthRoleName();
		// String rolePrefix = roleName.substring(0, 7);
		// AuthUser authUser = userService.getAuthUserByUserId(user);
		// String deptId = authUser.getDeptId();
		// Departments department =
		// departmentsService.getDepartmentByDeptId(deptId);

		CentralStoreRequisitionMst cnWsReqMstDB = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		/*
		 * List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>(); for (int i
		 * = 0; i < cnReqDtlIdList.size(); i++) { String id =
		 * cnReqDtlIdList.get(i); Double requiredQty = requiredQtyList.get(i);
		 * CnWsCsRequisitionDtl dtlDB = (CnWsCsRequisitionDtl) commonService
		 * .getAnObjectByAnyUniqueColumn("CnWsCsRequisitionDtl", "id", id);
		 * dtlDB.setQuantityRequired(requiredQty); dtlDB.setModifiedBy(user);
		 * dtlDB.setModifiedDate(new Date());
		 * commonService.saveOrUpdateModelObjectToDB(dtlDB); // for locak Table
		 * MyPair pair = new MyPair(dtlDB.getItemCode(), requiredQty);
		 * itemCodeAndQtyList.add(pair); }
		 */

		/*
		 * this.requisitionLockUpdate(itemCodeAndQtyList, requisitionNo, user,
		 * new Date(), cnWsReqMstDB.getRequisitionTo());
		 */

		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.updateCnWS_CsRequisitionHierarchyHistory(cnWsReqMstDB,
					justification);
		}
		/*
		 * String rolePrefix = roleName.substring(0, 7); if
		 * (rolePrefix.equals("ROLE_WO")) { return
		 * this.getCnWsMatsRequisitionList(); // return
		 * this.getStoreRequisitionForm(); } else
		 * if(rolePrefix.equals("ROLE_CS")){ return
		 * this.getCnWsMatsRequisitionList(); }else { // Show Pending
		 * Requisition List return this.getCnWsMatsRequisitionList();
		 * 
		 * }
		 */
		return this.getCnWsMatsRequisitionList();
	}

	@SuppressWarnings("unchecked")
	private void updateCnWS_CsRequisitionHierarchyHistory(
			CentralStoreRequisitionMst cnWsReqMstDB, String justification) {
		String user = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		AuthUser authUser = userService.getAuthUserByUserId(user);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get All State Codes from Approval Hierarchy
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_WS_CS_REQUISITION);

		// Sort State Codes in Descending order
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", cnWsReqMstDB.getRequisitionNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = (approvalHierarchyHistoryList.get(i)).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		// get current State Code from approval hierarchy history

		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id",
						ids[0] + "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();
		int nextStateCode = 0;

		// searching next State code and send this to next person
		for (int state : stateCodes) {
			// if next state code grater than current state code than this
			// process will go to next person
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, nextStateCode + "");

				// next role name
				// next role id
				// next role users dept

				String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
						.getRoleName().substring(0, 8);
				// checking department switching
				if (r1.equals(r2)) {
					//
				} else {
					Roles role = (Roles) commonService
							.getAnObjectByAnyUniqueColumn(
									"com.ibcs.desco.admin.model.Roles", "role",
									approvalHierarchy.getRoleName());
					List<AuthUser> userList = (List<AuthUser>) (Object) commonService
							.getObjectListByAnyColumn(
									"com.ibcs.desco.admin.model.AuthUser",
									"roleid", role.getRole_id() + "");
					Departments depart = (Departments) commonService
							.getAnObjectByAnyUniqueColumn("Departments",
									"deptId", userList.get(0).getDeptId());
					approvalHierarchyHistory.setDeptId(depart.getDeptId());
					approvalHierarchyHistory.setcDeptName(depart.getDeptName());

					/*
					 * if (approvalHierarchy.getRoleName().equalsIgnoreCase(
					 * "ROLE_CS_JAM")) { this.wsToCsRequisitionSave(user,
					 * cnWsReqMstDB); }
					 */
				}

				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());

				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				break;
			}

			// if state code equal to current state code than this
			// process will done for login user
			if (state == currentStateCode) {
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, state + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				approvalHierarchyHistory.setJustification(justification);
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
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
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory
						.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// now we have to insert data in store ticket mst and
				// history
				CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
				// csStoreTicketMst.setTicketNo(ticketNo);
				csStoreTicketMst.setStoreTicketType(CN_WS_CS_REQUISITION);
				csStoreTicketMst
						.setOperationId(cnWsReqMstDB.getRequisitionNo());
				csStoreTicketMst.setIssuedTo(cnWsReqMstDB.getDeptName());
				csStoreTicketMst.setIssuedFor(cnWsReqMstDB
						.getIdenterDesignation());
				csStoreTicketMst.setFlag(false);

				csStoreTicketMst.setKhathId(cnWsReqMstDB.getKhathId());
				csStoreTicketMst.setKhathName(cnWsReqMstDB.getKhathName());

				// Auto generate Store Ticket number
				String descoDeptCode = department.getDescoCode();

				csStoreTicketMst.setIssuedBy(cnWsReqMstDB.getRequisitionTo());

				String storeTicketNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode,
								separator, "CS_ST_SEQ");

				csStoreTicketMst.setTicketNo(storeTicketNo);

				commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

				// Requisition Approved
				// Update PD_Req_MST
				cnWsReqMstDB.setStoreTicketNO(storeTicketNo);
				cnWsReqMstDB.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnWsReqMstDB);
				{
					// Update CS_Req_MST
					CentralStoreRequisitionMst csReqMstDb = (CentralStoreRequisitionMst) commonService
							.getAnObjectByAnyUniqueColumn(
									"CentralStoreRequisitionMst",
									"requisitionNo",
									cnWsReqMstDB.getRequisitionNo());

					csReqMstDb.setStoreTicketNO(storeTicketNo);
					csReqMstDb.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(csReqMstDb);
				}

				CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
						.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
								"operationId", cnWsReqMstDB.getRequisitionNo());

				//

				// Get All Approval Hierarchy on CS_STORE_TICKET
				List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
						.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

				Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
						.size()];
				for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
					sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i)
							.getStateCode();
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
				storeTicketApprovalHierarchyHistory.setOperationId(cnWsReqMstDB
						.getRequisitionNo());
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setcDeptName(department
						.getDeptName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				storeTicketApprovalHierarchyHistory
						.setTicketNo(csStoreTicketMstdb.getTicketNo());
				storeTicketApprovalHierarchyHistory
						.setOperationName(CS_STORE_TICKET);
				storeTicketApprovalHierarchyHistory.setCreatedBy(user);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
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

				// model.addAttribute("operationId", reqMst.getRequisitionNo());

				// return "centralStore/csRequisitionReport";

			}
		}

	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/storeQuantityCheckAndValidation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String storeQuantityCheck(@RequestBody String cData)
			throws Exception {

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		//String deptId = authUser.getDeptId();

		/*Departments department = departmentsService
				.getDepartmentByDeptId(deptId);*/

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", contractorRep.getContractNo());

		/*DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);*/

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CnPdRequisitionMstDtl cnPdRequisitionMstDtl = gson.fromJson(cData,
					CnPdRequisitionMstDtl.class);
			Integer dtlPkId = cnPdRequisitionMstDtl.getId();
			JobCardTemplate jobCardTemplate = (JobCardTemplate) commonService
					.getAnObjectByAnyUniqueColumn("JobCardTemplate", "id",
							dtlPkId + "");
			Double requiredQty = cnPdRequisitionMstDtl.getQuantity();

			Double dbQty = 0.0;

			List<CSItemTransactionMst> csTxnMstList = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("CSItemTransactionMst",
							"itemCode", jobCardTemplate.getItemCode(),
							"khathId", contractor.getKhathId() + "");
			for (CSItemTransactionMst trxn : csTxnMstList) {
				if (trxn.getLedgerName().equalsIgnoreCase(UNSERVICEABLE)) {
					continue;
				} else {
					dbQty += trxn.getQuantity();
				}
			}
			double lockQty = 0.0;
			List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
					.getObjectListByTwoColumn("RequisitionLock", "itemCode",
							jobCardTemplate.getItemCode(), "storeCode",
							ContentType.CENTRAL_STORE.toString());
			for (RequisitionLock rl : reqLockList) {
				lockQty += rl.getQuantity();
			}

			dbQty -= lockQty;

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (requiredQty <= dbQty) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/storeQuantityCheckShowPage.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String storeQuantityCheckShowPage(@RequestBody String cData)
			throws Exception {

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		//String deptId = authUser.getDeptId();

		/*Departments department = departmentsService
				.getDepartmentByDeptId(deptId);*/

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", contractorRep.getContractNo());

		/*DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);*/

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CnPdRequisitionMstDtl cnPdRequisitionMstDtl = gson.fromJson(cData,
					CnPdRequisitionMstDtl.class);
			Integer dtlPkId = cnPdRequisitionMstDtl.getId();
			CentralStoreRequisitionDtl csRequisitionDtl = (CentralStoreRequisitionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl",
							"id", dtlPkId + "");
			Double requiredQty = cnPdRequisitionMstDtl.getQuantity();

			Double dbQty = 0.0;

			List<CSItemTransactionMst> csTxnMstList = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("CSItemTransactionMst",
							"itemCode", csRequisitionDtl.getItemCode(),
							"khathId", contractor.getKhathId() + "");
			for (CSItemTransactionMst trxn : csTxnMstList) {
				if (trxn.getLedgerName().equalsIgnoreCase(UNSERVICEABLE)) {
					continue;
				} else {
					dbQty += trxn.getQuantity();
				}
			}
			double lockQty = 0.0;
			List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
					.getObjectListByTwoColumn("RequisitionLock", "itemCode",
							csRequisitionDtl.getItemCode(), "storeCode",
							ContentType.CENTRAL_STORE.toString());
			for (RequisitionLock rl : reqLockList) {
				lockQty += rl.getQuantity();
			}

			dbQty -= lockQty;

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (requiredQty <= dbQty) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow
					.writeValueAsString("Sorry!!! This Item is not available");
		}
		return toJson;
	}

}
