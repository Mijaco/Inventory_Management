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
import com.ibcs.desco.common.model.AsBuiltApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.AsBuiltApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.bean.AsBuiltReportBean;
import com.ibcs.desco.contractor.model.AsBuiltDtl;
import com.ibcs.desco.contractor.model.AsBuiltMst;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.JobTemplateDtlService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

//jobTemplate, JobAssign, JobItemMaintenance
@Controller
@RequestMapping(value = "/asBuilt")
@PropertySource("classpath:common.properties")
public class AsBuiltReportController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	JobTemplateDtlService jobTemplateDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	AsBuiltApprovalHierarchyHistoryService asBuiltApprovalHierarchyHistoryService;

	@Autowired
	CommonService commonService;

	@Value("${desco.asBuilt.prefix}")
	private String descoAsBuiltNoPrefix;

	@Value("${project.separator}")
	private String separator;

	Date now = new Date();

	/*
	 * @RequestMapping(value = "/asBuilt.do", method = RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView getAsBuilt() {
	 * Map<String, Object> model = new HashMap<String, Object>();
	 * 
	 * String userName = commonService.getAuthUserName();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * String deptId = authUser.getDeptId();
	 * 
	 * Departments department =
	 * departmentsService.getDepartmentByDeptId(deptId);
	 * 
	 * //ContractorRepresentive contractorRep =
	 * contractorRepresentiveService.getContractorRep(authUser.getUserid());
	 * 
	 * List<Contractor> contractorList =
	 * contractorRepresentiveService.getContractorList();
	 * 
	 * List<PndJobMst> pndJobMstList = (List<PndJobMst>) (Object) commonService
	 * .getObjectListByAnyColumn("PndJobMst", "woNumber",
	 * contractorRep.getContractNo());
	 * 
	 * model.put("pndJobMstList", pndJobMstList); model.put("contractorList",
	 * contractorList); model.put("deptName", department.getDeptName());
	 * model.put("deptAddress", department.getAddress() + ", " +
	 * department.getContactNo());
	 * 
	 * return new ModelAndView("contractor/asBuiltForm", model); }
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asBuilt.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAsBuilt() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
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

			List<PndJobMst> pndJobMstList = (List<PndJobMst>) (Object) commonService
					.getObjectListByAnyColumn("PndJobMst", "woNumber",
							contractorRep.getContractNo());

			model.put("pndJobMstList", pndJobMstList);
			model.put("contractor", contractor);
			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			return new ModelAndView("contractor/asBuiltForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("pndContractor/errorCnProject", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewJobNoContractNo.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			Contractor contract = gson.fromJson(json, Contractor.class);
			List<PndJobMst> pndJobMstList = (List<PndJobMst>) (Object) commonService
					.getObjectListByAnyColumn("PndJobMst", "woNumber",
							contract.getContractNo());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(pndJobMstList);

		} else {
			Thread.sleep(2000);
		}

		return toJson;
	}

	@RequestMapping(value = "/asBuiltReportNext.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView asBuiltReportNext(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		String contractNo = request.getParameter("contractNo");
		String[] jobNo = request.getParameterValues("jobNo");
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<AsBuiltDtl> asBuiltDtl = pndJobDtlService.getPndJobDtlList(jobNo,
				contractNo);

		Contractor contractor = contractorRepresentiveService
				.getContractorByContractNo(contractNo);

		model.put("asBuiltDtl", asBuiltDtl);
		model.put("contractor", contractor);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/asBuiltDetailForm", model);
	}

	@RequestMapping(value = "/getJobItems", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<PndJobMst> getJobItems(
			@RequestParam String jobNo, HttpServletResponse response) {
		return simulateSearchJob(jobNo);

	}

	private List<PndJobMst> simulateSearchJob(String jobNo) {

		List<PndJobMst> JobTemplateMstList = new ArrayList<PndJobMst>();
		List<PndJobMst> JobTemplateMstListData = pndJobDtlService.getJobList();
		// iterate a list and filter by tagName
		for (PndJobMst orderNo : JobTemplateMstListData) {
			if (orderNo.getJobNo().toLowerCase().contains(jobNo.toLowerCase())) {
				JobTemplateMstList.add(orderNo);
			}
		}

		return JobTemplateMstList;
	}

	@RequestMapping(value = "/viewJobItems.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewJobItems(String jobNo) {

		Map<String, Object> model = new HashMap<String, Object>();

		PndJobMst mst = pndJobDtlService.getJobMst(jobNo);

		List<PndJobDtl> jobList = pndJobDtlService.getPndJobDtlList(jobNo);

		model.put("mst", mst);
		model.put("jobList", jobList);

		return new ModelAndView("contractor/asBuiltForm", model);
	}

	@RequestMapping(value = "/asBuiltReportSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String asBuiltReportSave(Model model,
			AsBuiltReportBean asBuiltReportBean) {
		// Map<String, Object> model = new HashMap<String, Object>();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();

		AsBuiltMst asBuiltMst = new AsBuiltMst();
		String asBuiltNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoAsBuiltNoPrefix, descoDeptCode, separator, "AS_BUILT_SEQ");
		asBuiltMst.setAsBuiltNo(asBuiltNo);
		asBuiltMst.setActive(true);
		asBuiltMst.setCreatedBy(userName);
		asBuiltMst.setCreatedDate(now);
		asBuiltMst.setWoNumber(asBuiltReportBean.getWoNumber());

		boolean successFlag = true;
		String msg = "";

		successFlag = saveAsBuilt(asBuiltReportBean, asBuiltMst, roleName,
				department, authUser);

		if (successFlag) {

			return "redirect:/asBuilt/asBuiltShow.do?id=" + asBuiltMst.getId();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/asBuilt/asBuiltList.do";

			// return new ModelAndView("contractor/asBuiltDetailForm",model);
		}
	}

	@SuppressWarnings("unused")
	private boolean saveAsBuilt(AsBuiltReportBean asBuiltReportBean,
			AsBuiltMst asBuiltMst, String roleName, Departments department,
			AuthUser authUser) {

		commonService.saveOrUpdateModelObjectToDB(asBuiltMst);

		saveAsBuiltDtl(asBuiltReportBean, asBuiltMst);

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(PD_AS_BUILT);
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
							PD_AS_BUILT, stateCodes[0].toString());
			roles = approvalHierarchy.getRoleName().split(",");
		}

		// login person have permission for requisition process??
		/*int flag = 0; // 0 = no permission
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1; // 1 = has permission
				break;
			}
		}*/

		addAsBuiltHierarchyHistory(asBuiltMst, approvalHierarchy, stateCodes,
				roleName, department, authUser);
		return true;
	}

	public void addAsBuiltHierarchyHistory(AsBuiltMst jobMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		AsBuiltApprovalHierarchyHistory approvalHierarchyHistory = new AsBuiltApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(jobMst.getAsBuiltNo());
		approvalHierarchyHistory.setOperationName(PD_AS_BUILT);
		approvalHierarchyHistory.setCreatedBy(jobMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(jobMst.getCreatedDate());
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

	private void saveAsBuiltDtl(AsBuiltReportBean asBuiltReportBean,
			AsBuiltMst asBuiltMst) {

		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> consumeList = null;
		List<Double> reUseList = null;
		List<Double> totalList = null;
		List<Double> recServiceableList = null;

		List<Double> recUnServiceableList = null;

		List<Double> reBalServiceableList = null;

		List<Double> reBalUnServiceableList = null;

		List<String> remarkList = null;

		if (asBuiltReportBean.getItemCode() != null) {
			itemCodeList = asBuiltReportBean.getItemCode();
		}
		if (asBuiltReportBean.getItemCode() != null) {
			itemNameList = asBuiltReportBean.getItemName();
		}

		if (asBuiltReportBean.getUom() != null) {
			uomList = asBuiltReportBean.getUom();
		}

		if (asBuiltReportBean.getConsume() != null) {
			consumeList = asBuiltReportBean.getConsume();
		}

		if (asBuiltReportBean.getReUse() != null) {
			reUseList = asBuiltReportBean.getReUse();
		}

		if (asBuiltReportBean.getTotal() != null) {
			totalList = asBuiltReportBean.getTotal();
		}

		if (asBuiltReportBean.getRecServiceable() != null) {
			recServiceableList = asBuiltReportBean.getRecServiceable();
		}

		if (asBuiltReportBean.getRecUnServiceable() != null) {
			recUnServiceableList = asBuiltReportBean.getRecUnServiceable();
		}

		if (asBuiltReportBean.getReBalServiceable() != null) {
			reBalServiceableList = asBuiltReportBean.getReBalServiceable();
		}

		if (asBuiltReportBean.getReBalUnServiceable() != null) {
			reBalUnServiceableList = asBuiltReportBean.getReBalUnServiceable();
		}

		if (asBuiltReportBean.getRemarks() != null) {
			remarkList = asBuiltReportBean.getRemarks();
		}

		for (int i = 0; i < uomList.size(); i++) {
			AsBuiltDtl jobDtl = new AsBuiltDtl();

			if (!itemCodeList.isEmpty()) {
				jobDtl.setItemCode(itemCodeList.get(i));
			} else {
				jobDtl.setItemCode(itemCodeList.get(i));
			}
			if (!itemNameList.isEmpty()) {
				jobDtl.setItemName(itemNameList.get(i));
			} else {
				jobDtl.setItemName(itemNameList.get(i));
			}

			if (!uomList.isEmpty()) {
				jobDtl.setUom(uomList.get(i));
			} else {
				jobDtl.setUom("");
			}

			if (!consumeList.isEmpty()) {
				jobDtl.setConsume(consumeList.get(i));
			} else {
				jobDtl.setConsume(0.0);
			}

			if (!reUseList.isEmpty()) {
				jobDtl.setReUse(reUseList.get(i));
			} else {
				jobDtl.setReUse(0.0);
			}

			if (!totalList.isEmpty()) {
				jobDtl.setTotal(totalList.get(i));
			} else {
				jobDtl.setTotal(0.0);
			}

			if (!recServiceableList.isEmpty()) {
				jobDtl.setRecServiceable(recServiceableList.get(i));
			} else {
				jobDtl.setRecServiceable(0.0);
			}

			if (!recUnServiceableList.isEmpty()) {
				jobDtl.setRecUnServiceable(recUnServiceableList.get(i));
			} else {
				jobDtl.setRecUnServiceable(0.0);
			}

			if (!reBalServiceableList.isEmpty()) {
				jobDtl.setReBalServiceable(reBalServiceableList.get(i));
			} else {
				jobDtl.setReBalServiceable(0.0);
			}

			if (!reBalUnServiceableList.isEmpty()) {
				jobDtl.setReBalUnServiceable(recUnServiceableList.get(i));
			} else {
				jobDtl.setReBalUnServiceable(0.0);
			}

			if (!remarkList.isEmpty()) {
				jobDtl.setRemarks(remarkList.get(i));
			} else {
				jobDtl.setRemarks("");
			}

			jobDtl.setId(null);
			jobDtl.setAsBuiltMst(asBuiltMst);
			jobDtl.setActive(asBuiltMst.isActive());
			jobDtl.setCreatedBy(asBuiltMst.getCreatedBy());
			jobDtl.setCreatedDate(asBuiltMst.getCreatedDate());
			commonService.saveOrUpdateModelObjectToDB(jobDtl);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asBuiltList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView asBuiltList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<AsBuiltApprovalHierarchyHistory> jobEstimationHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"AsBuiltApprovalHierarchyHistory", deptId, roleName,
						OPEN);

		String[] operationIdList = new String[jobEstimationHistoryList.size()];
		for (int i = 0; i < jobEstimationHistoryList.size(); i++) {
			operationIdList[i] = jobEstimationHistoryList.get(i)
					.getOperationId();
		}

		List<AsBuiltMst> asBuiltMst = jobTemplateDtlService
				.listAsBuiltMstByOperationIds(operationIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("asBuiltMst", asBuiltMst);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_PD")) {
			return new ModelAndView("contractor/pdAsBuiltList", model);
		} else {
			return new ModelAndView("contractor/asBuiltList", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asBuiltShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView AsBuiltShow(AsBuiltMst asBuiltMst) throws IOException {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		/*
		 * Departments department = departmentsService
		 * .getDepartmentByDeptId(authUser.getDeptId());
		 */

		// Test Dual
		//Long abc = commonService.getNextVal();
		//

		String deptId = authUser.getDeptId();

		/*
		 * AsBuiltMst asBuiltMstdb = commonService.
		 * .getAsBuiltMst(asBuiltMst.getId());
		 */
		AsBuiltMst asBuiltMstdb = (AsBuiltMst) commonService
				.getAnObjectByAnyUniqueColumn("AsBuiltMst", "id", asBuiltMst
						.getId().toString());

		String id = asBuiltMstdb.getId().toString();

		List<AsBuiltDtl> costEstimateMaterialsDtlList = (List<AsBuiltDtl>) (Object) commonService
				.getObjectListByAnyColumn("AsBuiltDtl", "asBuiltMst.id", id);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<AsBuiltApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"AsBuiltApprovalHierarchyHistory", PD_AS_BUILT,
						asBuiltMstdb.getAsBuiltNo(), DONE);

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 */

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<AsBuiltApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"AsBuiltApprovalHierarchyHistory", PD_AS_BUILT,
						asBuiltMstdb.getAsBuiltNo(), OPEN);

		int currentStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
				.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

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
				.getApprovalHierarchyByOperationNameAndRoleName(PD_AS_BUILT,
						roleNameList);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

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
		if (!sCsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sCsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sCsRequisitionApprovalHierarchyHistoryOpenList.get(
					sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								PD_AS_BUILT, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		// model.put("locationList", "'" + locationOptions + "'");
		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemMaster> itemList = itemInventoryService.getInventoryItemList();
		model.put("itemList", itemList);

		model.put("returnStateCode", returnStateCode);
		model.put("asBuiltMst", asBuiltMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("asBuiltDtlList", costEstimateMaterialsDtlList);
		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_PD")) {
			return new ModelAndView("contractor/pdAsBuiltShow", model);
		} else {
			return new ModelAndView("contractor/asBuiltShow", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asBuiltSubmitApproved.do", method = RequestMethod.POST)
	public String asBuiltSubmitApproved(
			Model model,
			@ModelAttribute("asBuiltReportBean") AsBuiltReportBean asBuiltReportBean) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!asBuiltReportBean.getReturn_state().equals("")
				|| asBuiltReportBean.getReturn_state().length() > 0) {

			List<AsBuiltApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"AsBuiltApprovalHierarchyHistory", "operationId",
							asBuiltReportBean.getAsBuiltNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			AsBuiltApprovalHierarchyHistory approvalHierarchyHistory = asBuiltApprovalHierarchyHistoryService
					.getAsBuiltApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							PD_AS_BUILT, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(asBuiltReportBean
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			AsBuiltApprovalHierarchyHistory approvalHierarchyHistoryNextState = new AsBuiltApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							PD_AS_BUILT, asBuiltReportBean.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(asBuiltReportBean.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(asBuiltReportBean
					.getWoNumber());
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
			String operationId = asBuiltReportBean.getAsBuiltNo();

			AsBuiltMst asBuiltMst = (AsBuiltMst) commonService
					.getAnObjectByAnyUniqueColumn("AsBuiltMst", "id",
							asBuiltReportBean.getId().toString());

			List<AsBuiltDtl> asBuiltDtlList = (List<AsBuiltDtl>) (Object) commonService
					.getObjectListByAnyColumn("AsBuiltDtl",
							"asBuiltMst.asBuiltNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(PD_AS_BUILT);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<AsBuiltApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"AsBuiltApprovalHierarchyHistory", "operationId",
							asBuiltMst.getAsBuiltNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			AsBuiltApprovalHierarchyHistory approvalHierarchyHistory = asBuiltApprovalHierarchyHistoryService
					.getAsBuiltApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// update cost estimation mat dtl tables

			updateAsBuiltDtl(asBuiltDtlList, asBuiltReportBean, userName);

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									PD_AS_BUILT, nextStateCode + "");

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
									PD_AS_BUILT, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					approvalHierarchyHistory.setJustification(asBuiltReportBean
							.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done
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

					asBuiltMst.setModifiedBy(userName);
					asBuiltMst.setModifiedDate(now);
					asBuiltMst.setApprove(true);
					commonService.saveOrUpdateModelObjectToDB(asBuiltMst);
					//model.addAttribute("operationId", operationId);

					//return "contractor/asBuiltReport";
					return "redirect:/asBuilt/singleJobReport.do?operationId="+operationId;

				}
			}
		}

		return "redirect:/asBuilt/asBuiltList.do";

	}
	
	@RequestMapping(value = "/singleJobReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssRequisitionReport(String operationId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", operationId);
		return new ModelAndView("contractor/asBuiltReport", model);
	}

	private void updateAsBuiltDtl(List<AsBuiltDtl> asBuiltDtlList,
			AsBuiltReportBean asBuiltReportBean, String userName) {

		List<String> itemCodeList = asBuiltReportBean.getItemCode();
		List<String> remarkList = asBuiltReportBean.getRemarks();
		List<Double> consumeList = asBuiltReportBean.getConsume();
		List<Double> reUseList = asBuiltReportBean.getReUse();
		List<Double> totalList = asBuiltReportBean.getTotal();
		List<Double> recServiceableList = asBuiltReportBean.getRecServiceable();
		List<Double> recUnServiceableList = asBuiltReportBean
				.getRecUnServiceable();
		List<Double> reBalServiceableList = asBuiltReportBean
				.getReBalServiceable();
		List<Double> reBalUnServiceableList = asBuiltReportBean
				.getReBalUnServiceable();

		// for (int i = 0; i < itemCodeList.size(); i++) {
		int i = 0;
		int q = itemCodeList.size();
		for (AsBuiltDtl mat : asBuiltDtlList) {

			mat.setConsume(consumeList.get(i));
			mat.setReUse(reUseList.get(i));
			mat.setTotal(totalList.get(i));
			mat.setRecServiceable(recServiceableList.get(i));
			mat.setRecUnServiceable(recUnServiceableList.get(i));
			mat.setReBalServiceable(reBalServiceableList.get(i));
			mat.setReBalUnServiceable(reBalUnServiceableList.get(i));
			if (remarkList.size() > 0) {
				mat.setRemarks(remarkList.get(i));
			}
			mat.setModifiedBy(userName);
			mat.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(mat);
			if (i < q) {
				i++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(Model model,
			@ModelAttribute("jobTemplateBean") AsBuiltReportBean jobTemplateBean) {

		String pndNo = jobTemplateBean.getAsBuiltNo();
		String justification = jobTemplateBean.getJustification();
		String nextStateCode = jobTemplateBean.getStateCode();

		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<AsBuiltApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("AsBuiltApprovalHierarchyHistory",
						"operationId", pndNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		AsBuiltApprovalHierarchyHistory approvalHierarchyHistory = asBuiltApprovalHierarchyHistoryService
				.getAsBuiltApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(PD_AS_BUILT,
						currentStateCode + "");
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
		AsBuiltApprovalHierarchyHistory approvalHierarchyHistoryNextState = new AsBuiltApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(PD_AS_BUILT,
						nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(pndNo + "");
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

		return "redirect:/asBuilt/asBuiltList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(Model model,
			@ModelAttribute("jobTemplateBean") AsBuiltReportBean jobTemplateBean) {
		String pndNo = jobTemplateBean.getAsBuiltNo();
		String justification = jobTemplateBean.getJustification();
		String backStateCode = jobTemplateBean.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<AsBuiltApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<AsBuiltApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("AsBuiltApprovalHierarchyHistory",
						"operationId", pndNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		AsBuiltApprovalHierarchyHistory approvalHierarchyHistory = asBuiltApprovalHierarchyHistoryService
				.getAsBuiltApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(PD_AS_BUILT,
						currentStateCode + "");
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
		AsBuiltApprovalHierarchyHistory approvalHierarchyHistoryBackState = new AsBuiltApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(PD_AS_BUILT,
						backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState
				.setStateName(approvalHierarchyBackSate.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(pndNo + "");
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

		return "redirect:/asBuilt/asBuiltList.do";

	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value = "/newAsBuiltReport.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView newAsBuiltReport( HttpServletRequest request ) {

		Map<String, Object> model = new HashMap<String, Object>();
		String contractNo = request.getParameter("contractNo");
		String[] jobNo = request.getParameterValues("jobName");
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<AsBuiltDtl> asBuiltDtl = pndJobDtlService.getPndJobDtlList(jobNo,
				contractNo);

		Contractor contractor = contractorRepresentiveService
				.getContractorByContractNo(contractNo);

		model.put("jobNo", jobNo[0]);
		model.put("asBuiltDtl", asBuiltDtl);
		model.put("contractor", contractor);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/newAsBuiltDetailForm", model);
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value = "/newAsBuiltReportSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String newAsBuiltReportSave(AsBuiltReportBean asBuiltReportBean) {
		
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();
		
		PndJobMst pndJobMst = (PndJobMst)
				commonService.getAnObjectByAnyUniqueColumn(
						"PndJobMst", "jobNo", asBuiltReportBean.getJobNo());

		AsBuiltMst asBuiltMst = new AsBuiltMst();
		String asBuiltNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoAsBuiltNoPrefix, descoDeptCode, separator, "AS_BUILT_SEQ");
		asBuiltMst.setAsBuiltNo(asBuiltNo);
		asBuiltMst.setActive(true);
		asBuiltMst.setPndJobMst(pndJobMst);
		asBuiltMst.setCreatedBy(userName);
		asBuiltMst.setCreatedDate(now);
		asBuiltMst.setWoNumber(asBuiltReportBean.getWoNumber());
		asBuiltMst.setSaveFrom("Project_User");
		
		commonService.saveOrUpdateModelObjectToDB(asBuiltMst);
		
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> consumeList = null;
		List<Double> reUseList = null;
		List<Double> totalList = null;
		List<Double> recServiceableList = null;

		List<Double> recUnServiceableList = null;

		List<Double> reBalServiceableList = null;

		List<Double> reBalUnServiceableList = null;

		List<String> remarkList = null;

		if (asBuiltReportBean.getItemCode() != null) {
			itemCodeList = asBuiltReportBean.getItemCode();
		}
		if (asBuiltReportBean.getItemCode() != null) {
			itemNameList = asBuiltReportBean.getItemName();
		}

		if (asBuiltReportBean.getUom() != null) {
			uomList = asBuiltReportBean.getUom();
		}

		if (asBuiltReportBean.getConsume() != null) {
			consumeList = asBuiltReportBean.getConsume();
		}

		if (asBuiltReportBean.getReUse() != null) {
			reUseList = asBuiltReportBean.getReUse();
		}

		if (asBuiltReportBean.getTotal() != null) {
			totalList = asBuiltReportBean.getTotal();
		}

		if (asBuiltReportBean.getRecServiceable() != null) {
			recServiceableList = asBuiltReportBean.getRecServiceable();
		}

		if (asBuiltReportBean.getRecUnServiceable() != null) {
			recUnServiceableList = asBuiltReportBean.getRecUnServiceable();
		}

		if (asBuiltReportBean.getReBalServiceable() != null) {
			reBalServiceableList = asBuiltReportBean.getReBalServiceable();
		}

		if (asBuiltReportBean.getReBalUnServiceable() != null) {
			reBalUnServiceableList = asBuiltReportBean.getReBalUnServiceable();
		}

		if (asBuiltReportBean.getRemarks() != null) {
			remarkList = asBuiltReportBean.getRemarks();
		}

		for (int i = 0; i < uomList.size(); i++) {
			AsBuiltDtl jobDtl = new AsBuiltDtl();

			if (!itemCodeList.isEmpty()) {
				jobDtl.setItemCode(itemCodeList.get(i));
			} else {
				jobDtl.setItemCode(itemCodeList.get(i));
			}
			if (!itemNameList.isEmpty()) {
				jobDtl.setItemName(itemNameList.get(i));
			} else {
				jobDtl.setItemName(itemNameList.get(i));
			}

			if (!uomList.isEmpty()) {
				jobDtl.setUom(uomList.get(i));
			} else {
				jobDtl.setUom("");
			}

			if (!consumeList.isEmpty()) {
				jobDtl.setConsume(consumeList.get(i));
			} else {
				jobDtl.setConsume(0.0);
			}

			if (!reUseList.isEmpty()) {
				jobDtl.setReUse(reUseList.get(i));
			} else {
				jobDtl.setReUse(0.0);
			}

			if (!totalList.isEmpty()) {
				jobDtl.setTotal(totalList.get(i));
			} else {
				jobDtl.setTotal(0.0);
			}

			if (!recServiceableList.isEmpty()) {
				jobDtl.setRecServiceable(recServiceableList.get(i));
			} else {
				jobDtl.setRecServiceable(0.0);
			}

			if (!recUnServiceableList.isEmpty()) {
				jobDtl.setRecUnServiceable(recUnServiceableList.get(i));
			} else {
				jobDtl.setRecUnServiceable(0.0);
			}

			if (!reBalServiceableList.isEmpty()) {
				jobDtl.setReBalServiceable(reBalServiceableList.get(i));
			} else {
				jobDtl.setReBalServiceable(0.0);
			}

			if (!reBalUnServiceableList.isEmpty()) {
				jobDtl.setReBalUnServiceable(recUnServiceableList.get(i));
			} else {
				jobDtl.setReBalUnServiceable(0.0);
			}

			if (!remarkList.isEmpty()) {
				jobDtl.setRemarks(remarkList.get(i));
			} else {
				jobDtl.setRemarks("");
			}

			jobDtl.setId(null);
			jobDtl.setAsBuiltMst(asBuiltMst);
			jobDtl.setActive(asBuiltMst.isActive());
			jobDtl.setCreatedBy(asBuiltMst.getCreatedBy());
			jobDtl.setCreatedDate(asBuiltMst.getCreatedDate());
			commonService.saveOrUpdateModelObjectToDB(jobDtl);
		}
		
		pndJobMst.setAsBuildNo(asBuiltNo);
		pndJobMst.setModifiedBy(userName);
		pndJobMst.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pndJobMst);
		
		return "redirect:/asBuilt/newAsBuiltList.do";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/newAsBuiltList.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView newAsBuiltList() {
		
		Map<String, Object> model = new HashMap<String, Object>();

		List<AsBuiltMst> asBuiltMst = ( List<AsBuiltMst> ) ( Object ) commonService
				.getObjectListByAnyColumn("AsBuiltMst", "saveFrom", "Project_User");
		
		model.put("asBuiltMst", asBuiltMst);
		return new ModelAndView("contractor/newAsBuiltList", model);
	}
	
	//Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newAsBuiltShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView newAsBuiltShow(AsBuiltMst asBuiltMst) throws IOException {
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		AsBuiltMst asBuiltMstdb = (AsBuiltMst) commonService
				.getAnObjectByAnyUniqueColumn("AsBuiltMst", "id", asBuiltMst
						.getId().toString());

		String id = asBuiltMstdb.getId().toString();

		List<AsBuiltDtl> costEstimateMaterialsDtlList = (List<AsBuiltDtl>) (Object) commonService
				.getObjectListByAnyColumn("AsBuiltDtl", "asBuiltMst.id", id);

		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemMaster> itemList = itemInventoryService.getInventoryItemList();
		model.put("itemList", itemList);
		model.put("asBuiltMst", asBuiltMstdb);
		model.put("asBuiltDtlList", costEstimateMaterialsDtlList);
		model.put("deptId", deptId);
		return new ModelAndView("contractor/newAsBuiltShow", model);

	}
}
