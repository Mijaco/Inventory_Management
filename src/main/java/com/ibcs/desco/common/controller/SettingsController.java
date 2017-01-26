package com.ibcs.desco.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Operation;
import com.ibcs.desco.common.model.State;
import com.ibcs.desco.common.model.StoreLedger;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.localStore.been.LocalPurchaseMstDtl;
import com.ibcs.desco.localStore.model.LocalPurchaseDtl;
import com.ibcs.desco.localStore.model.LocalPurchaseMst;

@Controller
@PropertySource("classpath:common.properties")
public class SettingsController {

	@Autowired
	private CommonService commonService;

	@Autowired
	private ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private ItemGroupService itemGroupService;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.localPurchase.prefix}")
	private String localPurchasePrefix;

	// View a New Approval Hierarchy Form
	@RequestMapping(value = "/settings/add/newApprovalHierarchyForm.do", method = RequestMethod.GET)
	public ModelAndView approvalHierarchyForm() {

		@SuppressWarnings("unchecked")
		List<Roles> roleList = (List<Roles>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.Roles");
		@SuppressWarnings("unchecked")
		List<Operation> operationList = (List<Operation>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.Operation");
		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.State");
		@SuppressWarnings("unchecked")
		List<ApprovalHierarchy> approvalHierarchyList = (List<ApprovalHierarchy>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.ApprovalHierarchy");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("operationList", operationList);
		model.put("stateList", stateList);
		model.put("approvalHierarchyList", approvalHierarchyList);
		return new ModelAndView("common/approvalHierarchyForm", model);
	}

	// View a New Department entry form
	@RequestMapping(value = "/settings/add/newDepartmentForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView departmentForm() {
		List<Departments> departmentList = activeDepartment();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departmentList", departmentList);
		return new ModelAndView("common/departmentForm", model);
	}

	// View a New Operation entry form
	@RequestMapping(value = "/settings/add/newOperationForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView operationForm() {
		@SuppressWarnings("unchecked")
		List<Operation> operationList = (List<Operation>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.Operation");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationList", operationList);
		return new ModelAndView("common/operationForm", model);
	}

	// View a New State entry form
	@RequestMapping(value = "/settings/add/newStateForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView stateForm() {
		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.State");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("stateList", stateList);
		return new ModelAndView("common/stateForm", model);
	}

	// View a New Store Ledger entry form
	@RequestMapping(value = "/settings/add/newStoreLedgerForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeLedgerForm() {
		@SuppressWarnings("unchecked")
		List<StoreLedger> storeLedgerList = (List<StoreLedger>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.StoreLedger");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLedgerList", storeLedgerList);
		return new ModelAndView("common/storeLedgerForm", model);
	}

	// View a New Store Location entry form
	@RequestMapping(value = "/settings/add/newStoreLocationForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeLocationForm() {
		@SuppressWarnings("unchecked")
		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.StoreLocations");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLocationList", storeLocationList);
		return new ModelAndView("common/storeLocationForm", model);
	}

	// Load Parent Location Select box using Store Code

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/loadParentLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadParentLocation(@RequestBody String json) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations storeLocations = gson.fromJson(json,
					StoreLocations.class);

			List<StoreLocations> parentLocations = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", storeLocations.getStoreCode(),
							"parentId");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(parentLocations);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// Load godown select ox using Parent Location Id

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/loadgodown.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadgodown(@RequestBody String json) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations storeLocations = gson.fromJson(json,
					StoreLocations.class);

			List<StoreLocations> godowns = (List<StoreLocations>) (Object) commonService
					.getObjectListByAnyColumn("StoreLocations", "parentId",
							storeLocations.getParentId() + "");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(godowns);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// load floor select box using Godown Id
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/loadfloor.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadfloor(@RequestBody String json) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations storeLocations = gson.fromJson(json,
					StoreLocations.class);

			List<StoreLocations> godowns = (List<StoreLocations>) (Object) commonService
					.getObjectListByAnyColumn("StoreLocations", "parentId",
							storeLocations.getParentId() + "");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(godowns);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// load block select box using Floor Id
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/loadblock.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadblock(@RequestBody String json) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations storeLocations = gson.fromJson(json,
					StoreLocations.class);

			List<StoreLocations> godowns = (List<StoreLocations>) (Object) commonService
					.getObjectListByAnyColumn("StoreLocations", "parentId",
							storeLocations.getParentId() + "");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(godowns);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// load block select box using Floor Id
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/loadrack.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadrack(@RequestBody String json) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations storeLocations = gson.fromJson(json,
					StoreLocations.class);

			List<StoreLocations> godowns = (List<StoreLocations>) (Object) commonService
					.getObjectListByAnyColumn("StoreLocations", "parentId",
							storeLocations.getParentId() + "");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(godowns);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// Save a new Approval Hierarchy into DB
	@RequestMapping(value = "/settings/add/newApprovalHierarchySave.do", method = RequestMethod.POST)
	public ModelAndView approvalHierarchySave(
			@RequestParam String action,
			@ModelAttribute("approvalHierarchy") ApprovalHierarchy approvalHierarchy) {

		approvalHierarchy.setCreatedBy(commonService.getAuthUserName());
		approvalHierarchy.setCreatedDate(new Date());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchy);

		if (action.equals("add")) {
			// return "redirect:/settings/list/approvalHierarchy.do";
			// return
			// "redirect:/settings/list/distinctApprovalHierarchyList.do?operationName="
			// + approvalHierarchy.getOperationName();
			// http://localhost:8085/desco/settings/list/distinctApprovalHierarchyList.do?operationName=Test

			Map<String, Object> model = new HashMap<String, Object>();
			List<Object> result = approvalHierarchyService
					.getUserRoleByOperationName(
							"com.ibcs.desco.common.model.ApprovalHierarchy",
							"operationName",
							approvalHierarchy.getOperationName());
			model.put("userrole", result);
			return new ModelAndView("common/approvalHierarchyInformation",
					model);

		} else {
			return new ModelAndView(
					"redirect:/settings/add/newApprovalHierarchyForm.do");
		}
	}

	// Save a new Department into DB
	@RequestMapping(value = "/settings/add/newDepartmentSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String departmentSave(
			@ModelAttribute("departments") Departments departments) {

		departments.setCreatedBy(commonService.getAuthUserName());
		departments.setCreatedDate(new Date());

		commonService.saveOrUpdateModelObjectToDB(departments);

		return "redirect:/settings/list/department.do";
	}

	// Save a new Operation into DB
	@RequestMapping(value = "/settings/add/newOperationSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String operationSave(Model model, @RequestParam String action,
			@ModelAttribute("operation") Operation operation) {

		operation.setCreatedBy(commonService.getAuthUserName());
		operation.setCreatedDate(new Date());

		commonService.saveOrUpdateModelObjectToDB(operation);
		if (action.equals("add")) {
			return "redirect:/settings/list/operation.do";
		} else {
			return "redirect:/settings/add/newOperationForm.do";
		}
	}

	// Save a new State into DB
	@RequestMapping(value = "/settings/add/newStateSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String stateSave(Model model, @RequestParam String action,
			@ModelAttribute("state") State state) {

		state.setCreatedBy(commonService.getAuthUserName());
		state.setCreatedDate(new Date());
		state.setId(null);

		commonService.saveOrUpdateModelObjectToDB(state);
		if (action.equals("add")) {
			return "redirect:/settings/list/state.do";
		} else {
			return "redirect:/settings/add/newStateForm.do";
		}
	}

	// Save a new Store Ledger into DB
	@RequestMapping(value = "/settings/add/newStoreLedgerSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String storeLedgerSave(Model model, @RequestParam String action,
			@ModelAttribute("storeLedger") StoreLedger storeLedger) {

		storeLedger.setCreatedBy(commonService.getAuthUserName());
		storeLedger.setCreatedDate(new Date());

		commonService.saveOrUpdateModelObjectToDB(storeLedger);
		if (action.equals("add")) {
			return "redirect:/settings/list/storeLedger.do";
		} else {
			return "redirect:/settings/add/newStoreLedgerForm.do";
		}
	}

	@RequestMapping(value = "/settings/add/newStoreLocationSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeLocationSave(
			@ModelAttribute("storeLocation") StoreLocations storeLocation) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer parentId = storeLocation.getParentId();
			String userName = commonService.getAuthUserName();

			storeLocation.setId(null);
			storeLocation.setActive(true);
			storeLocation.setCreatedBy(userName);
			storeLocation.setCreatedDate(new Date());
			storeLocation.setStoreLevel(parentId + 1 + "");

			if (parentId == 0) {
				storeLocation.setParentId(null);
			}

			commonService.saveOrUpdateModelObjectToDB(storeLocation);

			return new ModelAndView("redirect:/settings/list/storeLocation.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	// Update an Existing Approval Hierarchy into DB
	@RequestMapping(value = "/settings/update/existingApprovalHierarchy.do", method = RequestMethod.POST)
	public String existingApprovalHierarchyUpdate(
			Model model,
			@ModelAttribute("approvalHierarchy") ApprovalHierarchy approvalHierarchy) {

		ApprovalHierarchy approvalHierarchydb = (ApprovalHierarchy) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.ApprovalHierarchy", "id",
						approvalHierarchy.getId().toString());

		String userName = commonService.getAuthUserName();
		Date now = new Date();
		approvalHierarchydb.setModifiedDate(now);
		approvalHierarchydb.setActive(approvalHierarchy.isActive());
		approvalHierarchydb.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchydb.setButtonName(approvalHierarchy.getButtonName());
		approvalHierarchydb.setModifiedBy(userName);
		approvalHierarchydb.setOperationName(approvalHierarchy
				.getOperationName());
		approvalHierarchydb.setStateCode(approvalHierarchy.getStateCode());
		approvalHierarchydb.setStateName(approvalHierarchy.getStateName());
		approvalHierarchydb.setRoleName(approvalHierarchy.getRoleName());
		approvalHierarchydb.setRemarks(approvalHierarchy.getRemarks());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchydb);

		return "redirect:/settings/list/distinctApprovalHierarchy.do";

	}

	// Update an Existing Department into DB
	@RequestMapping(value = "/settings/update/existingDepartment.do", method = RequestMethod.POST)
	public String existingDepartmentUpdate(Model model,
			@ModelAttribute("department") Departments departments) {

		Departments departmentDB = (Departments) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.Departments", "id",
						departments.getId().toString());

		int flag = checkRoleAuth(commonService.getAuthRoleName());

		if (flag == 1) {

			departmentDB.setDeptName(departments.getDeptName());
			departmentDB.setParent(departments.getParent());
			departmentDB.setDeptLevel(departments.getDeptLevel());
			departmentDB.setAddress(departments.getAddress());
			departmentDB.setActive(departments.isActive());
			departmentDB.setContactPersion(departments.getContactPersion());
			departmentDB.setContactNo(departments.getContactNo());
			departmentDB.setEmail(departments.getEmail());
			departmentDB.setRemarks(departments.getRemarks());

			departmentDB.setModifiedBy(commonService.getAuthUserName());
			departmentDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(departmentDB);
			return "redirect:/settings/list/department.do";
		} else {
			return "redirect:/settings/update/department.do";
		}
	}

	// Update an Existing Operation into DB
	@RequestMapping(value = "/settings/update/existingOperation.do", method = RequestMethod.POST)
	public String existingOperationUpdate(Model model,
			@ModelAttribute("operation") Operation operation) {

		Operation operationDB = (Operation) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.Operation", "id",
						operation.getId().toString());

		int flag = checkRoleAuth(commonService.getAuthRoleName());

		if (flag == 1) {

			operationDB.setOperationName(operation.getOperationName());
			operationDB.setActive(operation.isActive());
			operationDB.setRemarks(operation.getRemarks());

			operationDB.setModifiedBy(commonService.getAuthUserName());
			operationDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(operationDB);

			return "redirect:/settings/list/operation.do";
		} else {
			return "redirect:/settings/update/operation.do";
		}
	}

	// Update an Existing State into DB
	@SuppressWarnings("unused")
	@RequestMapping(value = "/settings/update/existingState.do", method = RequestMethod.POST)
	public String existingStateUpdate(Model model,
			@ModelAttribute("state") State state) {

		State stateDB = (State) commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.State", "id", state.getId()
						.toString());

		int flag = checkRoleAuth(commonService.getAuthRoleName());

		if (flag == 1) {

			stateDB.setStateName(state.getStateName());
			stateDB.setActive(state.isActive());
			stateDB.setRemarks(state.getRemarks());
			boolean abc = state.isActive();
			stateDB.setModifiedBy(commonService.getAuthUserName());
			stateDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(stateDB);

			return "redirect:/settings/list/state.do";
		} else {
			return "redirect:/settings/update/state.do";
		}
	}

	// Update an Existing Store Ledger into DB
	@RequestMapping(value = "/settings/update/existingStoreLedger.do", method = RequestMethod.POST)
	public String existingStoreLedgerUpdate(Model model,
			@ModelAttribute("storeLedger") StoreLedger storeLedger) {

		StoreLedger storeLedgerDB = (StoreLedger) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.StoreLedger", "id",
						storeLedger.getId().toString());

		int flag = checkRoleAuth(commonService.getAuthRoleName());

		if (flag == 1) {

			storeLedgerDB.setLedgerCode(storeLedger.getLedgerCode());
			storeLedgerDB.setLedgerName(storeLedger.getLedgerName());
			storeLedgerDB.setActive(storeLedger.isActive());
			storeLedgerDB.setRemarks(storeLedger.getRemarks());

			storeLedgerDB.setModifiedBy(commonService.getAuthUserName());
			storeLedgerDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(storeLedgerDB);

			return "redirect:/settings/list/storeLedger.do";
		} else {
			return "redirect:/settings/update/storeLedger.do";
		}
	}

	// Update an Existing Store Location into DB
	@RequestMapping(value = "/settings/update/existingStoreLocation.do", method = RequestMethod.POST)
	public String existingStoreLocationUpdate(Model model,
			@ModelAttribute("storeLocation") StoreLocations storeLocation) {

		StoreLocations storeLocationDB = (StoreLocations) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.StoreLocations", "id",
						storeLocation.getId().toString());

		int flag = checkRoleAuth(commonService.getAuthRoleName());

		if (flag == 1) {

			storeLocationDB.setStoreCode(storeLocation.getStoreCode());
			storeLocationDB.setName(storeLocation.getName());
			storeLocationDB.setActive(storeLocation.isActive());
			storeLocationDB.setDescription(storeLocation.getDescription());
			storeLocationDB.setRemarks(storeLocation.getRemarks());

			storeLocationDB.setModifiedBy(commonService.getAuthUserName());
			storeLocationDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(storeLocationDB);

			return "redirect:/settings/list/storeLocation.do";
		} else {
			return "redirect:/settings/update/storeLocation.do";
		}
	}

	// Show Approval Hierarchy Update Page View
	@RequestMapping(value = "/settings/update/approvalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView updateApprovalHierarchy(
			ApprovalHierarchy approvalHierarchy) {

		@SuppressWarnings("unchecked")
		List<Roles> roleList = (List<Roles>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.Roles");
		@SuppressWarnings("unchecked")
		List<Operation> operationList = (List<Operation>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.Operation");
		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.State");
		/*
		 * @SuppressWarnings("unchecked") List<ApprovalHierarchy>
		 * approvalHierarchyList = (List<ApprovalHierarchy>) (Object)
		 * commonService.getAllObjectList(
		 * "com.ibcs.desco.common.model.ApprovalHierarchy");
		 */

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("operationList", operationList);
		model.put("stateList", stateList);
		// model.put("approvalHierarchyList", approvalHierarchyList);
		model.put("approvalHierarchy", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.ApprovalHierarchy", "id",
						approvalHierarchy.getId().toString()));
		return new ModelAndView("common/approvalHierarchyUpdate", model);
	}

	// Show Department Update Page View
	@RequestMapping(value = "/settings/update/department.do", method = RequestMethod.GET)
	public ModelAndView updateDepartment(Departments departments) {
		List<Departments> departmentList = activeDepartment();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departmentList", departmentList);
		model.put("department", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.admin.model.Departments", "id", departments
						.getId().toString()));
		return new ModelAndView("common/departmentUpdate", model);
	}

	// Show Operation Update Page View
	@RequestMapping(value = "/settings/update/operation.do", method = RequestMethod.GET)
	public ModelAndView updateOperation(Operation operation) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operation", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.Operation", "id", operation
						.getId().toString()));
		return new ModelAndView("common/operationUpdate", model);
	}

	// Show State Update Page View
	@RequestMapping(value = "/settings/update/state.do", method = RequestMethod.GET)
	public ModelAndView updateState(State state) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("state", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.State", "id", state.getId()
						.toString()));
		return new ModelAndView("common/stateUpdate", model);
	}

	// Show Store Ledger Update Page View
	@RequestMapping(value = "/settings/update/storeLedger.do", method = RequestMethod.GET)
	public ModelAndView updateStoreLedger(StoreLedger storeLedger) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLedger", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.StoreLedger", "id", storeLedger
						.getId().toString()));
		return new ModelAndView("common/storeLedgerUpdate", model);
	}

	// Show Store Location Update Page View
	@RequestMapping(value = "/settings/update/storeLocation.do", method = RequestMethod.GET)
	public ModelAndView updateStoreLocation(StoreLocations storeLocation) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLocation", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.StoreLocations", "id",
				storeLocation.getId().toString()));
		return new ModelAndView("common/storeLocationUpdate", model);
	}

	// List of Approval Hierarchy
	@RequestMapping(value = "/settings/list/approvalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView approvalHierarchyList() {
		@SuppressWarnings("unchecked")
		List<ApprovalHierarchy> approvalHierarchyList = (List<ApprovalHierarchy>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.ApprovalHierarchy");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchyList", approvalHierarchyList);
		return new ModelAndView("common/approvalHierarchyList", model);
	}

	// List of Department
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/list/department.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView departmentList() {

		List<Departments> departmentsList = (List<Departments>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.Departments");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("activeDepartmentList", departmentsList);
		return new ModelAndView("common/departmentList", model);
	}

	// List of Operation
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/list/operation.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView operationList() {

		List<Operation> operationList = (List<Operation>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.Operation");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationList", operationList);
		return new ModelAndView("common/operationList", model);
	}

	// List of State
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/list/state.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView stateList() {

		List<State> stateList = (List<State>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.State");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("stateList", stateList);
		return new ModelAndView("common/stateList", model);
	}

	// List of Store Ledger
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/list/storeLedger.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeLedgerList() {

		List<StoreLedger> storeLedgerList = (List<StoreLedger>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.StoreLedger");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLedgerList", storeLedgerList);
		return new ModelAndView("common/storeLedgerList", model);
	}

	// List of Store Location
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/list/storeLocation.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeLocationList() {

		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.StoreLocations");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLocationList", storeLocationList);
		return new ModelAndView("common/storeLocationList", model);
	}

	// Show a Approval Hierarchy Information
	@RequestMapping(value = "/settings/show/approvalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView showApprovalHierarchy(
			ApprovalHierarchy approvalHierarchy) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchy", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.ApprovalHierarchy", "id",
						approvalHierarchy.getId().toString()));
		return new ModelAndView("common/approvalHierarchyShow", model);
	}

	// Show a department Information
	@RequestMapping(value = "/settings/show/department.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showDepartment(Departments departments) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departmentShowById", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.Departments", "id",
						departments.getId().toString()));
		return new ModelAndView("common/departmentShow", model);
	}

	// Show a Operation Information
	@RequestMapping(value = "/settings/show/operation.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showOperation(Operation operation) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationShow", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.Operation", "id", operation
						.getId().toString()));
		return new ModelAndView("common/operationShow", model);
	}

	// Show a state Information
	@RequestMapping(value = "/settings/show/state.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showState(State state) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("stateShow", commonService.getAnObjectByAnyUniqueColumn(
				"com.ibcs.desco.common.model.State", "id", state.getId()
						.toString()));
		return new ModelAndView("common/stateShow", model);
	}

	// Show a store ledger Information
	@RequestMapping(value = "/settings/show/storeLedger.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showStoreLedger(StoreLedger storeLedger) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLedgerShow", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.StoreLedger", "id",
						storeLedger.getId().toString()));
		return new ModelAndView("common/storeLedgerShow", model);
	}

	// Show a store location Information
	@RequestMapping(value = "/settings/show/storeLocation.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showStoreLocation(StoreLocations storeLocation) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeLocationShow", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.StoreLocations", "id",
						storeLocation.getId().toString()));
		return new ModelAndView("common/storeLocationShow", model);
	}

	// Delete Store Location Information
	// Added by: Ihteshamul Alam

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/delete/storeLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteStoreLocation(StoreLocations storeLocation) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Integer id = storeLocation.getId();

			commonService.deleteAnObjectById("StoreLocations", id);

			List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
					.getAllObjectList("com.ibcs.desco.common.model.StoreLocations");
			model.put("storeLocationList", storeLocationList);
			model.put("deleteflag", 1);
			return new ModelAndView("common/storeLocationList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	// login person have permission for requisition process??
	// 0 have no permission and 1 have a permission
	private int checkRoleAuth(String roleName) {
		int flag = 0;
		String[] roles = "ROLE_ADMIN".split(",");

		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1;
			}
		}
		return flag;
	}

	private List<Departments> activeDepartment() {

		@SuppressWarnings("unchecked")
		List<Departments> departmentsList = (List<Departments>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.Departments");

		// String[] deptIdList = new String[departmentsList.size()];
		List<String> activeDeptId = new ArrayList<String>();

		for (int i = 0; i < departmentsList.size(); i++) {
			if (departmentsList.get(i).isActive() == true) {
				activeDeptId.add(departmentsList.get(i).getDeptId());
			}
		}
		@SuppressWarnings("unchecked")
		List<Departments> activeDepartmentList = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Departments", "deptId",
						activeDeptId);
		return activeDepartmentList;
	}

	// Added by: Ihteshamul Alam, IBCS

	// Distinct List of Approval Hierarchy
	@RequestMapping(value = "settings/list/distinctApprovalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView distinctApprovalHierarchyList() {

		@SuppressWarnings("unchecked")
		List<ApprovalHierarchy> distinctApprovalHierarchyList = (List<ApprovalHierarchy>) (Object) approvalHierarchyService
				.getDistinctObjectByColumnName();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("distinctApprovalHierarchyList",
				distinctApprovalHierarchyList);
		return new ModelAndView("common/distinctApprovalHierarchyList", model);
	}

	@RequestMapping(value = "settings/list/distinctApprovalHierarchyList.do", method = RequestMethod.POST)
	public ModelAndView getUserRole(ApprovalHierarchy approvalHierarchy) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<Object> result = approvalHierarchyService
				.getUserRoleByOperationName(
						"com.ibcs.desco.common.model.ApprovalHierarchy",
						"operationName", approvalHierarchy.getOperationName());
		model.put("userrole", result);
		return new ModelAndView("common/approvalHierarchyInformation", model);
	}

	@RequestMapping(value = "/settings/update/approvalHierarchyInformation.do", method = RequestMethod.GET)
	public ModelAndView approvalHierarchyInformation(
			ApprovalHierarchy approvalHierarchy) {

		@SuppressWarnings("unchecked")
		List<Roles> roleList = (List<Roles>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.Roles");
		@SuppressWarnings("unchecked")
		List<Operation> operationList = (List<Operation>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.Operation");
		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.common.model.State");
		/*
		 * @SuppressWarnings("unchecked") List<ApprovalHierarchy>
		 * approvalHierarchyList = (List<ApprovalHierarchy>) (Object)
		 * commonService.getAllObjectList(
		 * "com.ibcs.desco.common.model.ApprovalHierarchy");
		 */

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("operationList", operationList);
		model.put("stateList", stateList);
		// model.put("approvalHierarchyList", approvalHierarchyList);
		model.put("approvalHierarchy", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.ApprovalHierarchy", "id",
						approvalHierarchy.getId().toString()));
		return new ModelAndView("common/approvalHierarchyInformationUpdate",
				model);
	}

	@RequestMapping(value = "/settings/show/approvalHierarchyInformation.do", method = RequestMethod.GET)
	public ModelAndView showApprovalHierarchyInformation(
			ApprovalHierarchy approvalHierarchy) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchy", commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.common.model.ApprovalHierarchy", "id",
						approvalHierarchy.getId().toString()));
		return new ModelAndView("common/approvalHierarchyInformationShow",
				model);
	}

	// Added by Ihteshamul Alam
	@RequestMapping(value = "/settings/localPurchaseForm.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	public ModelAndView localPurchaseFrom(LocalPurchaseMstDtl lp) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<Departments> departmentList = departmentsService
					.listDepartments();

			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();

			model.put("categoryList", categoryList);
			model.put("department", department);
			model.put("departmentList", departmentList);

			return new ModelAndView("settings/localPurchaseForm", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/settings/saveLocalPurchase.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView saveLocalPurchase(LocalPurchaseMstDtl lpMstDtl,
			@RequestParam("referenceDoc") MultipartFile referenceDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			String filePath = "";
			String localPurchaseNo = "";
			if (lpMstDtl.getItemCode().size() > 0) {
				if (lpMstDtl.getReceivedQty().get(0) > 0) {
					filePath = this.saveFileToDrive(referenceDoc);
					localPurchaseNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									localPurchasePrefix,
									department.getDescoCode(), separator,
									"LS_LP_SEQ");
					LocalPurchaseMst localPurchaseMst = new LocalPurchaseMst(
							null, localPurchaseNo, department,
							lpMstDtl.getSupplierName(),
							lpMstDtl.getReferenceNo(),
							lpMstDtl.getPurchaseDate(), filePath, true,
							lpMstDtl.getRemark(), userName, new Date(), null,
							null, lpMstDtl.getPurchaseOrderNo(),
							lpMstDtl.getSupplyDate());

					commonService.saveOrUpdateModelObjectToDB(localPurchaseMst);
					LocalPurchaseMst lpMstDB = (LocalPurchaseMst) commonService
							.getAnObjectByAnyUniqueColumn("LocalPurchaseMst",
									"localPurchaseNo", localPurchaseNo);

					boolean result = this.saveLocalPurchaseDtl(lpMstDB,
							lpMstDtl, userName);
				} else {
					model.put("errorMsg", "Purchase quantity can not be zero");
					return new ModelAndView("settings/errorSetting", model);
				}
			} else {
				model.put("errorMsg",
						"Please select at least 1 item for Local Purcahse");
				return new ModelAndView("settings/errorSetting", model);
			}
			List<LocalPurchaseMst> localPurchaseMstList = (List<LocalPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseMst",
							"department.deptId", deptId);

			model.put("localPurchaseMstList", localPurchaseMstList);
			return new ModelAndView("settings//localPurchaseList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	private boolean saveLocalPurchaseDtl(LocalPurchaseMst localPurchaseMst,
			LocalPurchaseMstDtl lpMstDtl, String userName) {

		boolean flag = false;
		List<String> itemIdList = lpMstDtl.getItemCode();
		List<String> descriptionList = lpMstDtl.getDescription();
		List<String> uomList = lpMstDtl.getUom();
		List<Double> itemQtyList = lpMstDtl.getReceivedQty();
		List<Double> unitCostList = lpMstDtl.getUnitCost();
		List<Double> totalCostList = lpMstDtl.getTotalCost();
		List<String> remarksList = lpMstDtl.getRemarks();

		for (int i = 0; i < itemIdList.size(); i++) {
			LocalPurchaseDtl localPurchaseDtl = new LocalPurchaseDtl(null,
					localPurchaseMst, itemIdList.get(i),
					descriptionList.get(i), uomList.get(i), itemQtyList.get(i),
					unitCostList.get(i), totalCostList.get(i), true,
					remarksList.get(i), userName, new Date(), null, null);

			commonService.saveOrUpdateModelObjectToDB(localPurchaseDtl);

			flag = true;
		}

		return flag;
	}

	private String saveFileToDrive(MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("pdf")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(descoWORootPath + File.separator
							+ "local_purchase");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "Please uplode a PDF file";
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/localPurchaseList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView localPurchaseList(LocalPurchaseMstDtl lpMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			List<LocalPurchaseMst> localPurchaseMstList = (List<LocalPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseMst",
							"department.deptId", deptId);

			model.put("localPurchaseMstList", localPurchaseMstList);
			return new ModelAndView("settings/localPurchaseList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/viewLocalPurchase.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView viewLocalPurchase(LocalPurchaseMst lpMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			LocalPurchaseMst localPurchaseMstDB = (LocalPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalPurchaseMst", "id",
							lpMst.getId().toString());

			List<LocalPurchaseDtl> localPurchaseDtlList = (List<LocalPurchaseDtl>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseDtl",
							"localPurchaseMst", lpMst.getId().toString());

			model.put("localPurchaseMst", localPurchaseMstDB);
			model.put("localPurchaseDtlList", localPurchaseDtlList);
			return new ModelAndView("settings/localPurchaseShow", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@RequestMapping(value = "settings/updateLocalPurchase.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateLocalPurchase(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			LocalPurchaseDtl localPurchaseDtl = gson.fromJson(json,
					LocalPurchaseDtl.class);
			Integer id = localPurchaseDtl.getId();
			String username = commonService.getAuthUserName();
			// toJson = id + " -- " + rq + " -- " + uc + " -- " + tc + "";
			LocalPurchaseDtl updateLP = (LocalPurchaseDtl) commonService
					.getAnObjectByAnyUniqueColumn("LocalPurchaseDtl", "id", id
							+ "");

			updateLP.setReceivedQty(localPurchaseDtl.getReceivedQty());
			updateLP.setUnitCost(localPurchaseDtl.getUnitCost());
			updateLP.setTotalCost(localPurchaseDtl.getTotalCost());
			updateLP.setModifiedBy(username);
			updateLP.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(updateLP);
			toJson = "success";
		} else {
			Thread.sleep(3000);
			toJson = "fail";
		}

		return toJson;
	}

	@RequestMapping(value = "/settings/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, LocalPurchaseMst lpMst)
			throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = lpMst.getReferenceDoc();
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		InputStream inputStream = new BufferedInputStream(fis);
		String extension = "";

		int i = filePath.lastIndexOf('.');

		if (i > 0) {
			extension = filePath.substring(i + 1);
		}

		String agent = request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") != -1) {
			filePath = URLEncoder.encode("localPurchase." + extension, "UTF8");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="
					+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("localPurchase." + extension,
					"UTF8", "B");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\""
					+ filePath + "\"");
		}

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		byte by[] = new byte[32768];
		int index = inputStream.read(by, 0, 32768);
		while (index != -1) {
			out.write(by, 0, index);
			index = inputStream.read(by, 0, 32768);
		}
		inputStream.close();
		out.flush();

		return response;
	}

	// Added by: Shimul
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/searchDepartmentInformation.do", method = RequestMethod.POST)
	@ResponseBody
	public String searchDepartmentInformation(Departments departments) {
		String response = "";
		String departmentId = departments.getDeptId();

		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						departmentId);

		if (department == null) {
			String deptCode = departmentId + "01";
			response = "1##" + deptCode;
		} else {

			List<Departments> departmentList = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							departmentId);

			long maxx = 0;

			for (int i = 0; i < departmentList.size(); i++) {

				long num = Long.parseLong(departmentList.get(i).getDeptId());

				if (num > maxx) {
					maxx = num;
				}
			}

			String deptLevel = Integer.parseInt(department.getDeptLevel()) + 1
					+ "";
			String deptCode = "";

			if (maxx == 0) {
				deptCode = departmentId + "01";
			} else {

				deptCode = (maxx + 1) + "";
			}

			response = deptLevel + "##" + deptCode;
		}

		return response;
	}

	// Added by: Shimul
	@RequestMapping(value = "/settings/deleteRoleFormHierarchy.do", method = RequestMethod.POST)
	public ModelAndView deleteRoleFormHierarchy(
			ApprovalHierarchy approvalHierarchy) {
		Integer id = approvalHierarchy.getId();
		String operationName = approvalHierarchy.getOperationName();

		commonService.deleteAnObjectById(
				"com.ibcs.desco.common.model.ApprovalHierarchy", id);

		Map<String, Object> model = new HashMap<String, Object>();
		List<Object> result = approvalHierarchyService
				.getUserRoleByOperationName(
						"com.ibcs.desco.common.model.ApprovalHierarchy",
						"operationName", operationName);
		model.put("userrole", result);
		model.put("success", "1");
		return new ModelAndView("common/approvalHierarchyInformation", model);
	}
}
