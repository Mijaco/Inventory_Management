package com.ibcs.desco.workshop.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.JobCardApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.workshop.bean.JobCardMstDtl;
import com.ibcs.desco.workshop.model.JobCardDtl;
import com.ibcs.desco.workshop.model.JobCardMst;
import com.ibcs.desco.workshop.model.JobCardTemplate;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsInventoryDtl;
import com.ibcs.desco.workshop.model.WsInventoryMst;
import com.ibcs.desco.workshop.model.WsJobSummary;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
@RequestMapping(value = "/jobcard")
@PropertySource("classpath:common.properties")
public class JobCardController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	WSTransformerService wSTransformerService;

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
	ContractorRepresentiveService contractorRepresentiveService;

	@Value("${desco.job.card.prefix}")
	private String descoJobCardPrefix;

	@Value("${desco.ws.department.code}")
	private String descoWSDeptCode;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobCardForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		@SuppressWarnings("unused")
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);
		try {
			ContractorRepresentive contractorRep = contractorRepresentiveService
					.getContractorRep(authUser.getUserid());

			/*
			 * Contractor contractor = (Contractor) commonService
			 * .getAnObjectByAnyUniqueColumn(
			 * "com.ibcs.desco.contractor.model.Contractor", "contractNo",
			 * contractorRep.getContractNo());
			 * 
			 * List<Contractor> contractorList = (List<Contractor>) (Object)
			 * commonService .getObjectListByTwoColumn("Contractor", "active",
			 * "1", "contractorType", WORKSHOP);
			 */

			List<Contractor> contractorList = (List<Contractor>) (Object) commonService
					.getObjectListByTwoColumn("Contractor", "active", "1",
							"contractNo", contractorRep.getContractNo());

			model.put("contractorList", contractorList);
			return new ModelAndView("workshop/transformer/jobCardForm", model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csStoreTicketByWO.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getCsStoreTicketByWO(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			TransformerRegister transformerRegister = gson.fromJson(json,
					TransformerRegister.class);
			List<String> transformerRegisterDb = (List<String>) (Object)
			// commonService.getObjectListByTwoColumn("TransformerRegister",
			// "contractNo", transformerRegister.getContractNo(), "jobNo", "");
			// commonService.getObjectListByTwoColumnWithOneNullValue(
			// "TransformerRegister", "contractNo",
			// transformerRegister.getContractNo(), "jobNo");
			commonService.getDistinctValueListByColumnNameAndNullValue(
					"TransformerRegister", "ticketNo", "contractNo",
					transformerRegister.getContractNo(), "jobNo");

			List<TransformerRegister> trList = new ArrayList<TransformerRegister>();
			for (String ticketNo : transformerRegisterDb) {
				TransformerRegister tr = new TransformerRegister();
				tr.setTicketNo(ticketNo);
				trList.add(tr);
			}
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(trList);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}


	// /transformerByST.do

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/transformerByST.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTransformerByST(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			TransformerRegister transformerRegister = gson.fromJson(json,
					TransformerRegister.class);
			List<TransformerRegister> transformerRegisterDb = (List<TransformerRegister>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue(
							"TransformerRegister", "ticketNo",
							transformerRegister.getTicketNo(), "jobNo");

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(transformerRegisterDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobCardByTransformerSlNo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getJobCardByTransformerSlNo(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			TransformerRegister transformerRegister = gson.fromJson(json,
					TransformerRegister.class);
			List<JobCardTemplate> jobCardTemplateList = null;
			if (transformerRegister.getTransformerSerialNo().length() > 0) {
				jobCardTemplateList = (List<JobCardTemplate>) (Object) commonService
						.getAllObjectList("JobCardTemplate");
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(jobCardTemplateList);

		} else {
			Thread.sleep(5000);
		}

		return toJson;
	}

	//
	@RequestMapping(value = "/newJobCardSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsJobCardSave(Model model,
			@ModelAttribute("jobCardMst") JobCardMst jobCardMst,
			RedirectAttributes redirectAttributes) {

		if (jobCardMst.getTransformerSerialNo() == null
				|| jobCardMst.getTransformerSerialNo().length() < 1) {
			return this.getJobCardForm();
		}

		// get Current Role, User and date

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		TransformerRegister transformerRegister = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"transformerSerialNo",
						jobCardMst.getTransformerSerialNo());

		JobCardMst jobCardMstNew = new JobCardMst();
		jobCardMstNew.setContractNo(transformerRegister.getContractNo());
		jobCardMstNew.setCreatedBy(userName);
		jobCardMstNew.setCreatedDate(new Date());
		jobCardMstNew.setManufacturedName(transformerRegister
				.getManufacturedName());
		jobCardMstNew.setTypeOfWork(transformerRegister.getTypeOfWork());
		jobCardMstNew.setTransformerType(transformerRegister
				.getTransformerType());
		jobCardMstNew.setTransformerSerialNo(transformerRegister
				.getTransformerSerialNo());
		jobCardMstNew.setTransformerRegister(transformerRegister);
		jobCardMstNew.setManufacturedYear(transformerRegister
				.getManufacturedYear());
		jobCardMstNew.setVersion(1);
		// jobCardMstNew.setJobCardNo(jobCardMst.getJobCardNo());
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addJobCardDtls(transformerRegister, jobCardMstNew,
				roleName, department, authUser);
		if (successFlag) {
			Map<String, Object> models = new HashMap<String, Object>();
			try {
				/*
				 * List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object)
				 * commonService .getObjectListByTwoColumn("JobCardDtl",
				 * "active", "1", "jobCardMst.id", jobCardMstNew.getId() + "");
				 * 
				 * models.put("jobCardDtlList", jobCardDtlList);
				 * models.put("jobCardMstNew", jobCardMstNew);
				 * models.put("transformerRegister", transformerRegister);
				 * return new ModelAndView("workshop/transformer/jobCardShow",
				 * models);
				 */
				return this.getJobCardShow(jobCardMstNew);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ModelAndView("workshop/errorWS", models);
			}
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return this.getJobCardForm();
		}

	}
	@RequestMapping(value = "/mnjobCardReport.do", method = RequestMethod.GET)
	public ModelAndView receivingReport() {
		return new ModelAndView("workshop/reports/mnjobCardReport");
	}
	@SuppressWarnings("unchecked")
	public boolean addJobCardDtls(TransformerRegister transformerRegister,
			JobCardMst jobCardMst, String roleName, Departments department,
			AuthUser authUser) {

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(WS_JOB_CARD);

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
							WS_JOB_CARD, stateCodes[0].toString());
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

			// Saving master information of requisition to Master Table
			// Get and set requisition no from db

			if (jobCardMst.getVersion() > 1) {
				jobCardMst.setJobCardNo(transformerRegister.getJobNo());
			} else {

				// String descoDeptCode = department.getDescoCode();
				Departments wsDept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								descoWSDeptCode);

				String descoWSDeptCode = wsDept.getDescoCode();
				String jobCardNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoJobCardPrefix, descoWSDeptCode, separator,
								"WS_JOB_CARD_SEQ");
				jobCardMst.setJobCardNo(jobCardNo);
			}
			jobCardMst.setId(null);
			commonService.saveOrUpdateModelObjectToDB(jobCardMst);

			Integer lastJobCardId = (Integer) commonService
					.getMaxValueByObjectAndColumn("JobCardMst", "id");

			/*
			 * JobCardMst jobCardMstDb = (JobCardMst) commonService
			 * .getAnObjectByAnyUniqueColumn("JobCardMst", "jobCardNo",
			 * jobCardMst.getJobCardNo());
			 */
			JobCardMst jobCardMstDb = (JobCardMst) commonService
					.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
							lastJobCardId + "");
			// jobCardMst.setId(jobCardMstDb.getId());
			// jobCardMst.setJobCardNo(jobCardMstDb.getJobCardNo());
			List<JobCardTemplate> jobCardTemplateList = (List<JobCardTemplate>) (Object) commonService
					.getObjectListByAnyColumn("JobCardTemplate", "typeOfWork",
							transformerRegister.getTypeOfWork());
			/*List<JobCardTemplate> jobCardTemplateList = (List<JobCardTemplate>) (Object) commonService
					.getAllObjectList("JobCardTemplate");*/
			
			if (jobCardTemplateList.size() > 0) {

				for (int i = 0; i < jobCardTemplateList.size(); i++) {
					JobCardDtl jobCardDtl = new JobCardDtl();
					jobCardDtl.setCreatedBy(authUser.getUserid());
					jobCardDtl.setCreatedDate(new Date());
					jobCardDtl.setItemCode(jobCardTemplateList.get(i)
							.getItemCode());
					jobCardDtl.setItemName(jobCardTemplateList.get(i)
							.getItemName());
					jobCardDtl.setUnit(jobCardTemplateList.get(i).getUnit());
					jobCardDtl.setJobCardMst(jobCardMstDb);
					jobCardDtl.setRemarks("");
					jobCardDtl.setQuantityUsed(0.0);
					jobCardDtl.setQuantityUsed(0.0);
					commonService.saveOrUpdateModelObjectToDB(jobCardDtl);
				}

			}

			transformerRegister.setJobNo(jobCardMstDb.getJobCardNo());
			commonService.saveOrUpdateModelObjectToDB(transformerRegister);

			// Start Approval Hierarchy History insert process

			addWsJobCardHierarchyHistory(jobCardMstDb, approvalHierarchy,
					stateCodes, roleName, department, authUser);

			return true;
		} else {
			return false;
		}

	}

	public void addWsJobCardHierarchyHistory(JobCardMst jobCardMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		JobCardApprovalHierarchyHistory approvalHierarchyHistory = new JobCardApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		// approvalHierarchyHistory.setOperationId(jobCardMst.getJobCardNo());
		approvalHierarchyHistory.setOperationId(jobCardMst.getId().toString());
		approvalHierarchyHistory.setOperationName(WS_JOB_CARD);
		approvalHierarchyHistory.setCreatedBy(jobCardMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(jobCardMst.getCreatedDate());
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

	@RequestMapping(value = "/checkJobNo.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkJobNo(TransformerRegister transformerRegister) {
		// return "";
		String response = "";
		if (transformerRegister.getJobNo().equals((""))) {
			response = "unsuccess";
		} else {
			JobCardMst tRegister = (JobCardMst) commonService
					.getAnObjectByAnyUniqueColumn("JobCardMst", "jobCardNo",
							transformerRegister.getJobNo() + "");

			if (tRegister == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}

	//
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/jobList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobCardList() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<JobCardApprovalHierarchyHistory> jobCardApprovalHierarchyHistoryList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
							"JobCardApprovalHierarchyHistory", deptId,
							roleName, OPEN);

			List<String> operationIdList = new ArrayList<String>();
			for (int i = 0; i < jobCardApprovalHierarchyHistoryList.size(); i++) {
				operationIdList.add(jobCardApprovalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			/*
			 * List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object)
			 * commonService .getObjectListByAnyColumnValueList("JobCardMst",
			 * "jobCardNo", operationIdList);
			 */
			List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
					.getObjectListByAnyColumnValueList("JobCardMst", "id",
							operationIdList);

			model.put("jobCardMstList", jobCardMstList);
			String rolePrefix = roleName.substring(0, 7);
			if (rolePrefix.equals("ROLE_WO")) {
				return new ModelAndView("workshop/transformer/jobCardListCN",
						model);
			} else {
				return new ModelAndView("workshop/transformer/jobCardList",
						model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/approvedJobList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getApprovedJobCardList() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
					.getObjectListByAnyColumn("JobCardMst", "approved", "1");

			model.put("jobCardMstList", jobCardMstList);
			return new ModelAndView("workshop/transformer/approvedJobCardList",
					model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("workshop/errorWS", model);
		}
	}
	
	
	@RequestMapping(value = "/approvedJobCardReport.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getApprovedJobCardReport(JobCardMst jobCardMst)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		JobCardMst jobCardMstDb = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
						jobCardMst.getId() + "");
		model.put("jobCardMst", jobCardMstDb);
		return new ModelAndView("workshop/reports/approvedJobCardReport",
				model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approvedJobCardShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getApprovedJobCardShow(JobCardMst jobCardMst)
			throws Exception {

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		JobCardMst jobCardMstDb = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
						jobCardMst.getId() + "");

		// Set full name of Requisition Creator
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						jobCardMstDb.getCreatedBy());
		jobCardMstDb.setCreatedBy(createBy.getName());

		// String operationId = jobCardMstDb.getJobCardNo();
		String operationId = jobCardMstDb.getId() + "";

		List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
				.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.id",
						jobCardMstDb.getId() + "");

		// operation Id which selected by login user
		String currentStatus = "";

		List<JobCardApprovalHierarchyHistory> jobCardApprovalHierarchyHistoryListDone = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobCardApprovalHierarchyHistory", WS_JOB_CARD,
						operationId, DONE);

		if (!jobCardApprovalHierarchyHistoryListDone.isEmpty()) {
			currentStatus = jobCardApprovalHierarchyHistoryListDone.get(
					jobCardApprovalHierarchyHistoryListDone.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobCardMstDb", jobCardMstDb);
		model.put("currentStatus", currentStatus);
		model.put("approveHistoryList", jobCardApprovalHierarchyHistoryListDone);
		model.put("jobCardDtlList", jobCardDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress", department.getAddress());

		return new ModelAndView("workshop/transformer/approvedJobCardShow",
				model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobCardShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobCardShow(JobCardMst jobCardMst) throws Exception {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		JobCardMst jobCardMstDb = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
						jobCardMst.getId() + "");

		// Set full name of Requisition Creator
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						jobCardMstDb.getCreatedBy());
		jobCardMstDb.setCreatedBy(createBy.getName());

		// String operationId = jobCardMstDb.getJobCardNo();
		String operationId = jobCardMstDb.getId() + "";

		List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
				.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.id",
						jobCardMstDb.getId() + "");

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<JobCardApprovalHierarchyHistory> jobCardApprovalHierarchyHistoryListDone = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobCardApprovalHierarchyHistory", WS_JOB_CARD,
						operationId, DONE);

		if (!jobCardApprovalHierarchyHistoryListDone.isEmpty()) {
			currentStatus = jobCardApprovalHierarchyHistoryListDone.get(
					jobCardApprovalHierarchyHistoryListDone.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<JobCardApprovalHierarchyHistory> jobCardApprovalHierarchyHistoryListOpen = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobCardApprovalHierarchyHistory", WS_JOB_CARD,
						operationId, OPEN);

		int currentStateCode = jobCardApprovalHierarchyHistoryListOpen.get(
				jobCardApprovalHierarchyHistoryListOpen.size() - 1)
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
				.getApprovalHierarchyByOperationNameAndRoleName(WS_JOB_CARD,
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
		if (!jobCardApprovalHierarchyHistoryListOpen.isEmpty()
				&& jobCardApprovalHierarchyHistoryListOpen != null) {
			// get current state code
			int stateCode = jobCardApprovalHierarchyHistoryListOpen.get(
					jobCardApprovalHierarchyHistoryListOpen.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = jobCardApprovalHierarchyHistoryListOpen.get(
					jobCardApprovalHierarchyHistoryListOpen.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								WS_JOB_CARD, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		
		WsInventoryMst wsInventoryMst = (WsInventoryMst) commonService
				.getAnObjectByAnyUniqueColumn("WsInventoryMst", "transformerSerialNo",
						jobCardMstDb.getTransformerSerialNo());
		
		List<WsInventoryDtl> wsInventoryDtl = (List<WsInventoryDtl>) (Object) commonService
				.getObjectListByAnyColumn("WsInventoryDtl", "wsInventoryMst.id",
						wsInventoryMst.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);
		model.put("jobCardMstDb", jobCardMstDb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", jobCardApprovalHierarchyHistoryListDone);
		model.put("jobCardDtlList", jobCardDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		model.put("wsInventoryMst", wsInventoryMst);
		model.put("wsInventoryDtl", wsInventoryDtl);
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/transformer/jobCardShowCN", model);
		} else {
			return new ModelAndView("workshop/transformer/jobCardShow", model);
		}

	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/jobCardSubmitApproved.do", method = RequestMethod.POST)
	public String wsJobCardSubmitApproved(Model model,
			@ModelAttribute("jobCardMstDlt") JobCardMstDtl jobCardMstDlt) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		JobCardMst jobCardMst = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
						jobCardMstDlt.getId() + "");

		List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
				.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.id",
						jobCardMstDlt.getId() + "");

		// Send return to next user who backed me
		if (jobCardMstDlt.getReturn_state() != null) {

			/*
			 * List<JobCardApprovalHierarchyHistory>
			 * approvalHierarchyHistoryList =
			 * (List<JobCardApprovalHierarchyHistory>) (Object) commonService
			 * .getObjectListByAnyColumn( "JobCardApprovalHierarchyHistory",
			 * "operationId", jobCardMst.getJobCardNo());
			 */
			List<JobCardApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobCardApprovalHierarchyHistory", "operationId",
							jobCardMst.getId() + "");

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			JobCardApprovalHierarchyHistory approvalHierarchyHistory = (JobCardApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"JobCardApprovalHierarchyHistory", "id", ids[0]
									+ "");
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_JOB_CARD, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(jobCardMstDlt
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_JOB_CARD, jobCardMstDlt.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(jobCardMstDlt.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(jobCardMst
					.getJobCardNo());
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

			// get Requisites Item List against SR No
			String operationId = jobCardMst.getId() + "";
			// String operationId = jobCardMst.getJobCardNo();

			/*
			 * CentralStoreRequisitionMst csRequisitionMst =
			 * (CentralStoreRequisitionMst) commonService
			 * .getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
			 * "requisitionNo", operationId);
			 * 
			 * List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList =
			 * (List<CentralStoreRequisitionDtl>) (Object) commonService
			 * .getObjectListByAnyColumn("CentralStoreRequisitionDtl",
			 * "requisitionNo", operationId);
			 */

			// Get All State Codes from Approval Hierarchy
			// and sort Descending order for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_JOB_CARD);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<JobCardApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobCardApprovalHierarchyHistory", "operationId",
							operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			JobCardApprovalHierarchyHistory approvalHierarchyHistory = (JobCardApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"JobCardApprovalHierarchyHistory", "id", ids[0]
									+ "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;
			// update issued qty

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									WS_JOB_CARD, nextStateCode + "");

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
									WS_JOB_CARD, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(jobCardMstDlt
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
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// job card mst update by approved
					jobCardMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(jobCardMst);
					if (jobCardMst.getVersion() == 1) {
						for (JobCardDtl jobCard : jobCardDtlList) {
							List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
									.getObjectListByTwoColumn("WsJobSummary",
											"itemCode", jobCard.getItemCode(),
											"contractNo", jobCard
													.getJobCardMst()
													.getContractNo());
							if (wsJobSummaryDbList.size() > 0) {
								WsJobSummary jobCardSummaryDb = wsJobSummaryDbList
										.get(0);
								double remQty = jobCardSummaryDb
										.getRemainingQty();
								jobCardSummaryDb.setRemainingQty(remQty
										+ jobCard.getQuantityUsed());
								//
								double pTotalQty = jobCardSummaryDb
										.getTotalQty();
								jobCardSummaryDb.setTotalQty(pTotalQty
										+ jobCard.getQuantityUsed());

								jobCardSummaryDb.setModifiedBy(userName);
								jobCardSummaryDb.setModifiedDate(new Date());
								//
								commonService
										.saveOrUpdateModelObjectToDB(jobCardSummaryDb);
							} else {
								WsJobSummary wsJobSummary = new WsJobSummary(
										null, jobCard.getJobCardMst()
												.getContractNo(),
										jobCard.getItemCode(),
										jobCard.getQuantityUsed(),
										jobCard.getQuantityUsed(), true,
										userName, new Date());
								commonService
										.saveOrUpdateModelObjectToDB(wsJobSummary);
							}
						}
					} else {
						Integer currentVersion = jobCardMst.getVersion();
						List<JobCardMst> jobCardMstList = (List<JobCardMst>)(Object)
								commonService.getObjectListByTwoColumn("JobCardMst", 
										"jobCardNo", jobCardMst.getJobCardNo(), "version", (currentVersion-1)+"");
						JobCardMst jobCardMstDb = jobCardMstList.get(0);
						for (JobCardDtl jobCard : jobCardDtlList) {
							List<JobCardDtl> jobCardDtlListDb = (List<JobCardDtl>)(Object) 
									commonService.getObjectListByTwoColumn("JobCardDtl",
											"itemCode", jobCard.getItemCode(), 
											"jobCardMst.id", jobCardMstDb.getId()+"");
							JobCardDtl jobCardDtlDb = jobCardDtlListDb.get(0);
							double addingQuantity = jobCard.getQuantityUsed() - jobCardDtlDb.getQuantityUsed();
							List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
									.getObjectListByTwoColumn("WsJobSummary",
											"itemCode", jobCard.getItemCode(),
											"contractNo", jobCard
													.getJobCardMst()
													.getContractNo());
							if (wsJobSummaryDbList.size() > 0) {
								WsJobSummary jobCardSummaryDb = wsJobSummaryDbList
										.get(0);
								double remQty = jobCardSummaryDb
										.getRemainingQty();
								jobCardSummaryDb.setRemainingQty(remQty
										+ addingQuantity);
								//
								double pTotalQty = jobCardSummaryDb
										.getTotalQty();
								jobCardSummaryDb.setTotalQty(pTotalQty
										+ addingQuantity);

								jobCardSummaryDb.setModifiedBy(userName);
								jobCardSummaryDb.setModifiedDate(new Date());
								//
								commonService
										.saveOrUpdateModelObjectToDB(jobCardSummaryDb);
							} else {
								WsJobSummary wsJobSummary = new WsJobSummary(
										null, jobCard.getJobCardMst()
												.getContractNo(),
										jobCard.getItemCode(),
										jobCard.getQuantityUsed(),
										jobCard.getQuantityUsed(), true,
										userName, new Date());
								commonService
										.saveOrUpdateModelObjectToDB(wsJobSummary);
							}
						}
					}

					// model.addAttribute("operationId", operationId);
					model.addAttribute("operationId", jobCardMst.getJobCardNo());

					return "workshop/reports/jobCardReport";

				}
			}
		}
		return "redirect:/jobcard/jobList.do";
	}

	// @SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updatesafetymargin(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			JobCardDtl jobCardDtl = gson.fromJson(json, JobCardDtl.class);

			String username = commonService.getAuthUserName();
			Date now = new Date();
			JobCardDtl jobCardDtlDb = (JobCardDtl) commonService
					.getAnObjectByAnyUniqueColumn("JobCardDtl", "id",
							jobCardDtl.getId() + "");
			// double currentQty = jobCardDtlDb.getQuantityUsed();

			jobCardDtlDb.setModifiedBy(username);
			jobCardDtlDb.setModifiedDate(now);
			jobCardDtlDb.setQuantityRecovery(jobCardDtl.getQuantityRecovery());
			jobCardDtlDb.setQuantityUsed(jobCardDtl.getQuantityUsed());
			jobCardDtlDb.setRemainningQuantity(jobCardDtl.getQuantityUsed());
			jobCardDtlDb.setRemarks(jobCardDtl.getRemarks());
			commonService.saveOrUpdateModelObjectToDB(jobCardDtlDb);

			/*------------Remaining Quantity should be minus form WS_JOB_SUMMARY When Requisition Save-----------------*/
			/*
			 * List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>)
			 * (Object) commonService .getObjectListByTwoColumn("WsJobSummary",
			 * "itemCode", jobCardDtlDb.getItemCode(), "contractNo",
			 * jobCardDtlDb.getJobCardMst().getContractNo()); if
			 * (wsJobSummaryDbList.size() > 0) { WsJobSummary jobCardSummaryDb =
			 * wsJobSummaryDbList .get(0); double remQty =
			 * jobCardSummaryDb.getRemainingQty() - currentQty;
			 * jobCardSummaryDb.setRemainingQty(remQty +
			 * jobCardDtl.getQuantityUsed());
			 * 
			 * jobCardSummaryDb.setModifiedBy(commonService.getAuthUserName());
			 * jobCardSummaryDb.setModifiedDate(new Date()); // commonService
			 * .saveOrUpdateModelObjectToDB(jobCardSummaryDb); }
			 */
			/*------------------------------*/

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// @SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsJobCardDelete(
			@ModelAttribute("jobCardDtl") JobCardDtl jobCardDtl) {

		JobCardDtl jobCardDtlDb = (JobCardDtl) commonService
				.getAnObjectByAnyUniqueColumn("JobCardDtl", "id",
						jobCardDtl.getId() + "");

		// double currentQty = jobCardDtlDb.getQuantityUsed();
		JobCardMst jobCardMst = (JobCardMst) jobCardDtlDb.getJobCardMst();

		/*------------Remaining Quantity should be minus form WS_JOB_SUMMARY When Requisition Save-----------------*/
		/*
		 * List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object)
		 * commonService .getObjectListByTwoColumn("WsJobSummary", "itemCode",
		 * jobCardDtlDb.getItemCode(), "contractNo",
		 * jobCardDtlDb.getJobCardMst().getContractNo()); if
		 * (wsJobSummaryDbList.size() > 0) { WsJobSummary jobCardSummaryDb =
		 * wsJobSummaryDbList .get(0); double remQty =
		 * jobCardSummaryDb.getRemainingQty() - currentQty;
		 * jobCardSummaryDb.setRemainingQty(remQty +
		 * jobCardDtl.getQuantityUsed());
		 * 
		 * jobCardSummaryDb.setModifiedBy(commonService.getAuthUserName());
		 * jobCardSummaryDb.setModifiedDate(new Date()); // commonService
		 * .saveOrUpdateModelObjectToDB(jobCardSummaryDb); }
		 */
		/*------------------------------*/

		commonService.deleteAnObjectById("JobCardDtl", jobCardDtl.getId());

		try {
			return this.getJobCardShow(jobCardMst);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/updateAll.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsJobCardUpdateAll(
			@ModelAttribute("jobCardMstDtl") JobCardMstDtl jobCardMstDtl) {

		// get Current Role, User and date
		String userName = commonService.getAuthUserName();

		List<Integer> jobDtlIdList = jobCardMstDtl.getJobDtlIdList();
		List<String> remarksList = jobCardMstDtl.getRemarks();
		List<Double> qtyUsedList = jobCardMstDtl.getQuantityUsed();
		List<Double> qtyRecList = jobCardMstDtl.getQuantityRecovery();
		JobCardMst jobCardMst = null;
		int i = 0;
		if (jobDtlIdList.size() > 0) {
			for (Integer id : jobDtlIdList) {
				JobCardDtl jobCardDtlDb = (JobCardDtl) commonService
						.getAnObjectByAnyUniqueColumn("JobCardDtl", "id", id
								+ "");
				// double currentQty = jobCardDtlDb.getQuantityUsed();

				jobCardDtlDb.setQuantityRecovery(qtyRecList.get(i));
				jobCardDtlDb.setQuantityUsed(qtyUsedList.get(i));
				jobCardDtlDb.setRemainningQuantity(qtyUsedList.get(i));
				jobCardDtlDb.setRemarks(remarksList.get(i));
				jobCardDtlDb.setModifiedBy(userName);
				jobCardDtlDb.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(jobCardDtlDb);
				jobCardMst = (JobCardMst) jobCardDtlDb.getJobCardMst();

				/*------------Remaining Quantity should be minus form WS_JOB_SUMMARY When Requisition Save-----------------*/
				/*
				 * List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>)
				 * (Object) commonService
				 * .getObjectListByTwoColumn("WsJobSummary", "itemCode",
				 * jobCardDtlDb.getItemCode(), "contractNo",
				 * jobCardDtlDb.getJobCardMst().getContractNo()); if
				 * (wsJobSummaryDbList.size() > 0) { WsJobSummary
				 * jobCardSummaryDb = wsJobSummaryDbList .get(0); double remQty
				 * = jobCardSummaryDb.getRemainingQty()-currentQty;
				 * jobCardSummaryDb.setRemainingQty(remQty +
				 * qtyUsedList.get(i));
				 * 
				 * jobCardSummaryDb.setModifiedBy(userName);
				 * jobCardSummaryDb.setModifiedDate(new Date()); //
				 * commonService .saveOrUpdateModelObjectToDB(jobCardSummaryDb);
				 * } else { continue; }
				 */
				/*------------------------------*/
				i++;
			}
		}

		try {
			return this.getJobCardShow(jobCardMst);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(Model model,
			@ModelAttribute("jobCardMstDtl") JobCardMstDtl jobCardMstDtl) {

		// String operationNo = jobCardMstDtl.getJobCardNo();
		String operationNo = jobCardMstDtl.getId() + "";
		String justification = jobCardMstDtl.getJustification();
		String nextStateCode = jobCardMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<JobCardApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("JobCardApprovalHierarchyHistory",
						"operationId", operationNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		JobCardApprovalHierarchyHistory approvalHierarchyHistory = (JobCardApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"JobCardApprovalHierarchyHistory", "id", ids[0] + "");
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(WS_JOB_CARD,
						currentStateCode + "");
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
		JobCardApprovalHierarchyHistory approvalHierarchyHistoryNextState = new JobCardApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(WS_JOB_CARD,
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
		approvalHierarchyHistoryNextState.setOperationId(operationNo + "");
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

		return "redirect:/jobcard/jobList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(Model model,
			@ModelAttribute("jobCardMstDtl") JobCardMstDtl jobCardMstDtl) {

		// String operationNo = jobCardMstDtl.getJobCardNo();
		String operationNo = jobCardMstDtl.getId() + "";
		String justification = jobCardMstDtl.getJustification();
		String backStateCode = jobCardMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<JobCardApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn("JobCardApprovalHierarchyHistory",
						"operationId", operationNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		JobCardApprovalHierarchyHistory approvalHierarchyHistory = (JobCardApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"JobCardApprovalHierarchyHistory", "id", ids[0] + "");
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(WS_JOB_CARD,
						currentStateCode + "");
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
		JobCardApprovalHierarchyHistory approvalHierarchyHistoryBackState = new JobCardApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(WS_JOB_CARD,
						backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(operationNo + "");
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

		return "redirect:/jobcard/jobList.do";
	}

	/*-----------Job Card Review Information--------------*/
	@RequestMapping(value = "/review/getJobReviewForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobReviewForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userId);

			Departments department = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							authUser.getDeptId());
			String workOrderNo = department.getContactNo();

			List<TransformerRegister> transformerList = wSTransformerService
					.getTransformerListWithNullTestDateAndValidJobNo(workOrderNo);

			model.put("transformerList", transformerList);
			return new ModelAndView(
					"workshop/transformer/jobreview/jobReviewForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/review/getForm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsJobCardReviewSave(Model model,
			@ModelAttribute("jobCardMst") JobCardMst jobCardMst,
			RedirectAttributes redirectAttributes) {

		// get Current Role, User and date

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		TransformerRegister transformerRegister = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
						jobCardMst.getId() + "");

		List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
				.getObjectListByAnyColumn("JobCardMst", "jobCardNo",
						transformerRegister.getJobNo());
		// Integer [] jobCardNoArray = new Integer[jobCardMstList.size()];
		List<Integer> jobCardNoList = new ArrayList<Integer>();
		for (int i = 0; i < jobCardMstList.size(); i++) {
			jobCardNoList.add(jobCardMstList.get(i).getVersion());
			// jobCardNoArray[i] = jobCardMstList.get(i).getVersion();
		}
		// Arrays.sort(jobCardNoArray);

		Collections.reverse(jobCardNoList);

		JobCardMst jobCardMstNew = new JobCardMst();
		jobCardMstNew.setContractNo(transformerRegister.getContractNo());
		jobCardMstNew.setCreatedBy(userName);
		jobCardMstNew.setCreatedDate(new Date());
		jobCardMstNew.setManufacturedName(transformerRegister
				.getManufacturedName());
		jobCardMstNew.setTypeOfWork(transformerRegister.getTypeOfWork());
		jobCardMstNew.setTransformerType(transformerRegister
				.getTransformerType());
		jobCardMstNew.setTransformerSerialNo(transformerRegister
				.getTransformerSerialNo());
		jobCardMstNew.setTransformerRegister(transformerRegister);
		jobCardMstNew.setManufacturedYear(transformerRegister
				.getManufacturedYear());
		jobCardMstNew.setVersion(jobCardNoList.get(0) + 1);
		// jobCardMstNew.setJobCardNo(jobCardMst.getJobCardNo());
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addJobCardDtls(transformerRegister, jobCardMstNew,
				roleName, department, authUser);
		if (successFlag) {
			Map<String, Object> models = new HashMap<String, Object>();
			try {
				return this.getJobCardShow(jobCardMstNew);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ModelAndView("workshop/errorWS", models);
			}
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return this.getJobReviewForm();
		}

	}
	
	//Delete Multiple Job - Added by Ihteshamul Alam
	@RequestMapping(value="/deleteMultiple.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteMultiple( @ModelAttribute("jobCardDtl")
		JobCardDtl jobCardDtl ) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String jobCardNo = jobCardDtl.getJobCardNo();
			
			String [] jobIds = jobCardNo.split("###");
			
			JobCardDtl jobCardDtlDb = (JobCardDtl) commonService.getAnObjectByAnyUniqueColumn("JobCardDtl", "id", jobIds[0] + "");
			
			for( int i = 0; i < jobIds.length; i++ ) {
				String jobId = jobIds[i];
//				System.out.println(jobId);
				commonService.deleteAnObjectById("JobCardDtl", Integer.parseInt(jobId));
			}
			
			
			JobCardMst jobCardMst = (JobCardMst) jobCardDtlDb.getJobCardMst();

			return this.getJobCardShow(jobCardMst);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}
	
	/*-----------Job Card Review End -----------------*/

}