package com.ibcs.desco.subStore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.VehiclePermission;
import com.ibcs.desco.subStore.model.SSVehiclePermission;
import com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory;
import com.ibcs.desco.subStore.service.SSVPAppHierHistoryService;
import com.ibcs.desco.subStore.service.SSVehiclePermissionService;

@Controller
@PropertySource("classpath:common.properties")
public class SSVehiclePermissionController extends Constrants{

	// Service Class Object Creation
	@Autowired
	private SSVehiclePermissionService ssVehiclePermissionService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private SSVPAppHierHistoryService ssVpAppHierHistoryService;

	// Prefix Annotation for Vehicle Permission Slip No with common.properties
	@Value("${desco.vehiclePermission.prefix}")
	private String vehiclePermissionPrefix;

	@Value("${project.separator}")
	private String separator;

	// Function for view a vehicle permission form
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/vehiclePermission.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#vehiclePermission, 'WRITE')")
	public ModelAndView ssVehiclePermission(
			SSVehiclePermission vehiclePermission) {
		
		List<Departments> depts = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departments", depts);

		return new ModelAndView("subStore/ssVehiclePermissionForm", model);
	}

	// Function for save vehicle permission form's data into database
	@RequestMapping(value = "/ss/saveVehiclePermission.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveVehiclePermission(
			@ModelAttribute("vehiclePermissionUI") VehiclePermission vehiclePermissionUI)
			throws ParseException {
		// Get current user name
		String userName = commonService.getAuthUserName();
		// Get current user ROLE NAME
		String userRoleName = commonService.getAuthRoleName();
		// Check current user authentication
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// Declare date for entry time
		Date entryTime = new Date();

		// Create an object for set data
		SSVehiclePermission vehiclePermission = new SSVehiclePermission();

		// Get data from JSP page and set them into class
		String descoDeptCode = department.getDescoCode();
		String slipNo = commonService.getOperationIdByPrefixAndSequenceName(
				vehiclePermissionPrefix, descoDeptCode, separator,
				"SS_VEHICLE_PERM_SEQ");
		vehiclePermission.setSlipNo(slipNo);
		vehiclePermission.setContractorName(vehiclePermissionUI
				.getContractorName());
		vehiclePermission.setEntryTime(entryTime);
		vehiclePermission
				.setEntryPurpose(vehiclePermissionUI.getEntryPurpose());
		vehiclePermission.setVehicleType(vehiclePermissionUI.getVehicleType());
		vehiclePermission.setVehicleNumber(vehiclePermissionUI
				.getVehicleNumber());
		vehiclePermission.setDrivingLicenceNo(vehiclePermissionUI
				.getDrivingLicenceNo());
		vehiclePermission.setRequisitionBy(vehiclePermissionUI
				.getRequisitionBy());
		vehiclePermission.setReturnState("");
		vehiclePermission.setCreatedBy(authUser.getName());
		vehiclePermission.setCreatedDate(entryTime);

		// Get all approval hierarchy on SS_VEHICLE_PERMISSION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(SS_VEHICLE_PERMISSION);

		// Get All state code which define for vehicle permission process
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		// Sorting the founded state code on SS_VEHICLE_PERMISSION
		Arrays.sort(stateCodes);

		// Get Hierarchy Information
		ApprovalHierarchy approvalHierarchyInfo = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_VEHICLE_PERMISSION, stateCodes[0].toString());

		// Get Role List in a Array
		String[] role = approvalHierarchyInfo.getRoleName().split(",");

		// Login person have permission for initiate the process
		int flag = 0;
		for (int i = 0; i < role.length; i++) {
			if (role[i].equals(userRoleName)) {
				flag = 1;
				break;
			}
		}

		// Check authentication and set value into vehicle hierarchy table
		if (flag == 1) {
			SSVehiclePermissionApprovalHierarchyHistory vpApprovalHierarchyHistory = new SSVehiclePermissionApprovalHierarchyHistory();
			vpApprovalHierarchyHistory.setActRoleName(userRoleName);
			vpApprovalHierarchyHistory.setOperationId(vehiclePermission
					.getSlipNo());
			vpApprovalHierarchyHistory.setOperationName(SS_VEHICLE_PERMISSION);
			vpApprovalHierarchyHistory.setCreatedBy(vehiclePermission
					.getCreatedBy());
			vpApprovalHierarchyHistory.setCreatedDate(entryTime);
			if (stateCodes.length > 0) {
				vpApprovalHierarchyHistory.setStateCode(stateCodes[0]);
				vpApprovalHierarchyHistory.setStateName(approvalHierarchyInfo
						.getStateName());
			}
			vpApprovalHierarchyHistory.setStatus(OPEN);
			vpApprovalHierarchyHistory.setActive(vehiclePermission.getActive());
			vpApprovalHierarchyHistory.setApprovalHeader(approvalHierarchyInfo
					.getApprovalHeader());
			vpApprovalHierarchyHistory.setDeptId(authUser.getDeptId());

			commonService
					.saveOrUpdateModelObjectToDB(vpApprovalHierarchyHistory);
		}
		commonService.saveOrUpdateModelObjectToDB(vehiclePermission);

		return "redirect:/ss/showVehiclePermission.do?id="
				+ vehiclePermission.getId();
	}

	@RequestMapping(value = "/ss/vehiclePermissionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView vehiclePermissionList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> vpAppHierHistoryList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory",
						deptId, roleName, OPEN);

		String[] operationIdList = new String[vpAppHierHistoryList.size()];
		for (int i = 0; i < vpAppHierHistoryList.size(); i++) {
			operationIdList[i] = vpAppHierHistoryList.get(i).getOperationId();
		}

		List<SSVehiclePermission> vehiclePermissionList = ssVehiclePermissionService
				.getSSVehiclePermissionListByOperationId(operationIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("vehiclePermissionList", vehiclePermissionList);
		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssVehiclePermissionList", model);
		} else {
			return new ModelAndView("subStore/ssVehiclePermissionList");
		}
	}

	@RequestMapping(value = "/ss/showVehiclePermission.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView vehiclePermissionShow(
			VehiclePermission vehiclePermission) {
		// Get the ROLE name
		String roleName = commonService.getAuthRoleName();
		// Get the login user name
		String userName = commonService.getAuthUserName();
		// Check user authentication
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		// Get the showing vehicle permission id
		int id = vehiclePermission.getId();
		// Get all data of showing vehicle permission id
		SSVehiclePermission vehiclePermissionShow = ssVehiclePermissionService
				.getSSVehiclePermission(id);
		// Create object for getting and setting data
		Map<String, Object> model = new HashMap<String, Object>();
		// Button showing for different ROLE's login user
		String buttonValue = null;
		//
		String currentStatus = "";
		// Get the vehicle permission slip no of showing id
		String operationId = vehiclePermissionShow.getSlipNo();
		// Get logged in user's department id
		String deptId = authUser.getDeptId();

		// Get a list of vehicle permission with DONE status
		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> vpAppHierHistoryDoneList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SSVehiclePermissionApprovalHierarchyHistory",
						SS_VEHICLE_PERMISSION, operationId, DONE);

		// Get current status of the vehicle slip no
		if (!vpAppHierHistoryDoneList.isEmpty()) {
			currentStatus = vpAppHierHistoryDoneList.get(
					vpAppHierHistoryDoneList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		// Get a list by vehicle slip no and OPEN status
		// @SuppressWarnings("unchecked")
		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> vpAppHierHistoryOpenList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SSVehiclePermissionApprovalHierarchyHistory",
						SS_VEHICLE_PERMISSION, operationId, OPEN);

		int currentStateCode = vpAppHierHistoryOpenList.get(
				vpAppHierHistoryOpenList.size() - 1).getStateCode();

		// Authenticate User list by Department ID
		@SuppressWarnings("unchecked")
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);

		// ROLE List By ROLE ID
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		@SuppressWarnings("unchecked")
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Roles", "role_id",
						roleIdList);

		// App_Hier_History List By Role List and Operation Name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(
						SS_VEHICLE_PERMISSION, roleNameList);

		// Send to upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();
		for (int countStateCodes = 0; countStateCodes < approvalHierarchyList
				.size(); countStateCodes++) {
			if (approvalHierarchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approvalHierarchyList.get(countStateCodes));
			}
		}

		// Back to user as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approvalHierarchyList
				.size(); countBackStateCodes++) {
			if (approvalHierarchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs.add(approvalHierarchyList
						.get(countBackStateCodes));
			}
		}

		String returnState = "";

		// Button Name Define
		if (!vpAppHierHistoryOpenList.isEmpty()
				&& vpAppHierHistoryOpenList != null) {

			// Get current state code
			int stateCode = vpAppHierHistoryOpenList.get(
					vpAppHierHistoryOpenList.size() - 1).getStateCode();

			// Decide for return or not
			returnState = vpAppHierHistoryOpenList.get(
					vpAppHierHistoryOpenList.size() - 1).getReturn_state();

			// Get next Approval Hierarchy
			ApprovalHierarchy approvalHierarchy = null;
			{
				approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								SS_VEHICLE_PERMISSION, stateCode + "");
				buttonValue = approvalHierarchy.getButtonName();
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"hh:mm:ss a dd-MM-yyyy");

		model.put("returnState", returnState);
		model.put("vehiclePermissionShow", vehiclePermissionShow);
		model.put("dateFormate",
				simpleDateFormat.format(vehiclePermissionShow.getEntryTime()));
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("vpAppHierHistoryDoneList", vpAppHierHistoryDoneList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssVehiclePermissionShow", model);
		} else {
			return new ModelAndView("subStore/ssVehiclePermissionShow", model);
		}
	}

	@RequestMapping(value = "/ss/vehiclePermissionSubmitApproved.do", method = RequestMethod.GET)
	public String vehiclePermissionSubmitApproved(
			Model model,
			@ModelAttribute("vehiclePermissionUI") SSVehiclePermission vehiclePermissionUI) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// Send return to the next user who backed me
		// if(!vehiclePermissionUI.getReturnState().equals("") ||
		// vehiclePermissionUI.getReturnState().length() > 0){
		if (vehiclePermissionUI.getReturnState() != null) {
			@SuppressWarnings("unchecked")
			List<SSVehiclePermissionApprovalHierarchyHistory> vpAppHierHistoryList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SSVehiclePermissionApprovalHierarchyHistory",
							"operationId", vehiclePermissionUI.getSlipNo());

			Integer[] ids = new Integer[vpAppHierHistoryList.size()];
			for (int i = 0; i < vpAppHierHistoryList.size(); i++) {
				ids[i] = vpAppHierHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// Get current State code and all info from approval hierarchy
			// history

			SSVehiclePermissionApprovalHierarchyHistory vpApprovalHierarchyHistory = ssVpAppHierHistoryService
					.getSSVpApprovalHierarchyHistory(ids[0]);

			int currentStateCode = vpApprovalHierarchyHistory.getStateCode();

			// Current User's Row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_VEHICLE_PERMISSION, currentStateCode + "");
			vpApprovalHierarchyHistory.setStatus(DONE);
			vpApprovalHierarchyHistory.setCreatedBy(userName);
			vpApprovalHierarchyHistory.setRemarks(vehiclePermissionUI
					.getRemarks());
			vpApprovalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService
					.saveOrUpdateModelObjectToDB(vpApprovalHierarchyHistory);

			// Get next state code and all info from approval hierarchy history
			SSVehiclePermissionApprovalHierarchyHistory vpAppHierHistoryNextState = new SSVehiclePermissionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextState = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							SS_VEHICLE_PERMISSION,
							vehiclePermissionUI.getReturnState());

			vpAppHierHistoryNextState.setActive(true);
			vpAppHierHistoryNextState.setCreatedBy(userName);
			vpAppHierHistoryNextState.setCreatedDate(new Date());
			vpAppHierHistoryNextState.setStatus(OPEN);
			vpAppHierHistoryNextState.setStateName(approvalHierarchyNextState
					.getStateName());
			vpAppHierHistoryNextState.setStateCode(Integer
					.parseInt(vehiclePermissionUI.getReturnState()));
			vpAppHierHistoryNextState.setId(null);
			vpAppHierHistoryNextState.setOperationId(vehiclePermissionUI
					.getSlipNo());
			vpAppHierHistoryNextState
					.setOperationName(approvalHierarchyNextState
							.getOperationName());
			vpAppHierHistoryNextState.setActRoleName(approvalHierarchyNextState
					.getRoleName());
			vpAppHierHistoryNextState
					.setApprovalHeader(approvalHierarchyNextState
							.getApprovalHeader());
			vpAppHierHistoryNextState.setDeptId(deptId);
			vpAppHierHistoryNextState.setcDeptName(department.getDeptName());
			vpAppHierHistoryNextState
					.setcDesignation(authUser.getDesignation());

			commonService
					.saveOrUpdateModelObjectToDB(vpAppHierHistoryNextState);

		} else {
			// Get vehicle permission list against slip No
			String operationId = vehiclePermissionUI.getSlipNo();

			// Get all state code from approval hierarchy and sort descending
			// order to get highest state code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(SS_VEHICLE_PERMISSION);

			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// Get current state code from approval hierarchy by slip No
			@SuppressWarnings("unchecked")
			List<SSVehiclePermissionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"SSVehiclePermissionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// Get current state code and all info from approval hierarchy
			// history
			SSVehiclePermissionApprovalHierarchyHistory approvalHierarchyHistory = ssVpAppHierHistoryService
					.getSSVpApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;

			// Searching next state code and decision for send this vehicle
			// permission to next person
			for (int state : stateCodes) {
				// If next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_VEHICLE_PERMISSION, nextStateCode + "");

					// Next ROLE name, ROlE ID and ROLE Users Department
					String r1 = roleName.substring(0, 8);
					String r2 = approvalHierarchy.getRoleName().substring(0, 8);

					if (r1.equals(r2)) {
						// Code Needed
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
						Departments departments = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(departments
								.getDeptId());
						approvalHierarchyHistory.setcDeptName(departments
								.getDeptName());
					}

					// Authentic user
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

				// If next state code equal to current state code than this
				// process will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_VEHICLE_PERMISSION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setRemarks(vehiclePermissionUI
							.getRemarks());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// If next state code is last as approval hierarchy than this
				// process
				// will done and go for generate a vehicle slip
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {

					approvalHierarchyHistory.setStatus(DONE);
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// Get all approval Hierarchy on SS_VEHICLE_PERMISSION
					List<ApprovalHierarchy> approvalHierarchiesListDB = approvalHierarchyService
							.getApprovalHierarchyByOperationName(SS_VEHICLE_PERMISSION);
					Integer[] vpStateCodes = new Integer[approvalHierarchiesListDB
							.size()];

					for (int i = 0; i < approvalHierarchiesListDB.size(); i++) {
						vpStateCodes[i] = approvalHierarchiesListDB.get(i)
								.getStateCode();
					}
					Arrays.sort(vpStateCodes);

					// Get Approve Hierarchy for last state
					ApprovalHierarchy vpApprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_VEHICLE_PERMISSION,
									vpStateCodes[0].toString());

					SSVehiclePermissionApprovalHierarchyHistory vpAppHierarchyHistory = new SSVehiclePermissionApprovalHierarchyHistory();
					vpAppHierarchyHistory.setActRoleName(vpApprovalHierarchy
							.getRoleName());
					vpAppHierarchyHistory.setOperationId(operationId);
					vpAppHierarchyHistory
							.setOperationName(SS_VEHICLE_PERMISSION);
					vpAppHierarchyHistory.setCreatedBy(userName);
					vpAppHierarchyHistory.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						vpAppHierarchyHistory.setStateCode(vpStateCodes[0]);
						vpAppHierarchyHistory.setStateName(vpApprovalHierarchy
								.getStateName());
					}
					vpAppHierarchyHistory.setStatus(OPEN);
					vpAppHierarchyHistory.setActive(true);

					commonService
							.saveOrUpdateModelObjectToDB(vpAppHierarchyHistory);

					model.addAttribute("operationId", operationId);
					System.out.println("Print a slip");
					//return "subStore/ssVehiclePermissionReport";
					String slipNo = vehiclePermissionUI.getSlipNo();
					return "redirect:/ss/ssVehiclePermissionReport.do?operationId="+slipNo;
				}
			}
		}
		return "redirect:/ss/vehiclePermissionList.do";

	}
	
	@RequestMapping(value="/ss/ssVehiclePermissionReport.do", method=RequestMethod.GET)
	public ModelAndView ssVehiclePermissionReport( String operationId ) {
		Map<String, Object> model = new HashMap<String, Object>();
		String [] slipNo = operationId.split(",");
		model.put("operationId", slipNo[0]);
		return new ModelAndView("subStore/ssVehiclePermissionReport", model);
	}
	
	@RequestMapping(value="/ss/vehiclePermissionReport.do", method=RequestMethod.GET)
	public ModelAndView vehiclePermissionReport() {
		return new ModelAndView("report/ssVehiclePermissionReport");
	}

	@RequestMapping(value = "/ss/vehiclePermissionSendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String vehiclePermissionSendTo(
			@ModelAttribute("vehiclePermissionUI") SSVehiclePermission vehiclePermission) {

		String slipNo = vehiclePermission.getSlipNo();
		String remarks = vehiclePermission.getRemarks();
		String nextStateCode = vehiclePermission.getReturnState();

		// get Current Department, User and Role Information
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory",
						"operationId", slipNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SSVehiclePermissionApprovalHierarchyHistory approvalHierarchyHistory = ssVpAppHierHistoryService
				.getSSVpApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_VEHICLE_PERMISSION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setRemarks(remarks);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		SSVehiclePermissionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new SSVehiclePermissionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_VEHICLE_PERMISSION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(slipNo + "");
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

		return "redirect:/ss/vehiclePermissionList.do";
	}

	@RequestMapping(value = "/ss/vehiclePermissionBackTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String vehiclePermissionBackTo(
			@ModelAttribute("vehiclePermissionUI") SSVehiclePermission vehiclePermission) {
		String slipNo = vehiclePermission.getSlipNo();
		String remarks = vehiclePermission.getRemarks();
		String backStateCode = vehiclePermission.getReturnState();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		@SuppressWarnings("unchecked")
		List<SSVehiclePermissionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<SSVehiclePermissionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory",
						"operationId", slipNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		SSVehiclePermissionApprovalHierarchyHistory approvalHierarchyHistory = ssVpAppHierHistoryService
				.getSSVpApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_VEHICLE_PERMISSION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setJustification(remarks);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		SSVehiclePermissionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new SSVehiclePermissionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						SS_VEHICLE_PERMISSION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState
				.setStateName(approvalHierarchyBackSate.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(slipNo + "");
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

		return "redirect:/ss/vehiclePermissionList.do";
	}
}
