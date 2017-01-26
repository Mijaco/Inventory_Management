package com.ibcs.desco.workshop.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.WsGatePassApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsLsRCVPreventiveApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.service.LSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.workshop.bean.TransformerRegisterBean;
import com.ibcs.desco.workshop.bean.WsReceivePreventiveMstDtl;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsCnAllocation;
import com.ibcs.desco.workshop.model.WsReceivePreventiveDtl;
import com.ibcs.desco.workshop.model.WsReceivePreventiveMst;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
@RequestMapping(value = "/prev")
@PropertySource("classpath:common.properties")
public class WsPreventiveMaintenanceController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	TransformerRegisterController transformerRegisterController;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;
	
	@Autowired
	WSTransformerService wSTransformerService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;

	@Autowired
	LSItemTransactionMstService lsItemTransactionMstService;

	@Autowired
	CommonService commonService;

	@Value("${desco.preventiveMaintenanceNote.prefix}")
	private String descoNoteNoPrefix;
	
	@Value("${desco.ws.gate.pass.prefix}")
	private String descoWsGatePassPrefix;
	
	@Value("${desco.ws.department.code}")
	private String descoWSDeptCode;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;
	
	private static String DATE_FORMAT = "yyyy-MM-dd";
	
	public Date setCurrentDate(String date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
		Date parseDate = null;
		try {
			parseDate = sdfDate.parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return parseDate;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/receiveForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		//String userName = commonService.getAuthUserName();
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		List<ItemCategory> catList = new ArrayList<ItemCategory>();
	for(ItemCategory category:categoryList){
		if(category.getCategoryId()==300){catList.add(category);}
		//if(category.getCategoryId()==302){catList.add(category);}
	}
		//ItemCategory catList = itemGroupService.getGetItemGroupById(46);
		model.put("categoryList", catList);

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);
		
		List<Contractor> contractor = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor",
						"contractorType", WORKSHOP);
		
		List<Departments> departments = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent", DEPT_ID_520);
		
		model.put("departmentList", departments);
		model.put("contractorList", contractor);
		model.put("descoKhath", descoKhath);
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		return new ModelAndView(
				"workshop/preventiveMaintenance/wsPrevReceiveForm", model);
	}

	@RequestMapping(value = "/prevReceiveSave.do", method = RequestMethod.POST)
	//@PreAuthorize("isAuthenticated()")
	public String wsStoreRequisitionSave(Model model, WsReceivePreventiveMstDtl wsReceivePreventiveMstDtl,
			@RequestParam("referenceDoc") MultipartFile referenceDoc) {
		// get Current Role, User and date
		
			String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		String filePath = "";
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		Departments senderDepartment = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						wsReceivePreventiveMstDtl.getDeptName());

		filePath = this.saveFileToDrive(referenceDoc);
		WsReceivePreventiveMst csRequisitionMst = new WsReceivePreventiveMst();
		csRequisitionMst.setSenderDeptName(senderDepartment.getDeptName());
		csRequisitionMst.setSenderDeptId(senderDepartment.getDeptId());
		csRequisitionMst.setRcvDeptName(department.getDeptName());
		csRequisitionMst.setReceiveDate(setCurrentDate(wsReceivePreventiveMstDtl.getReceiveDate()));
		csRequisitionMst.setWoNumber(wsReceivePreventiveMstDtl.getWorkOrderNumber());
		csRequisitionMst.setReferenceNumber(wsReceivePreventiveMstDtl.getReferenceNumber());
		csRequisitionMst.setZone(wsReceivePreventiveMstDtl.getZone());
		csRequisitionMst.setRemarks(wsReceivePreventiveMstDtl.getRemark());
		csRequisitionMst.setReferenceDoc(filePath);


		csRequisitionMst.setSndCode(department.getDescoCode());
		csRequisitionMst.setActive(wsReceivePreventiveMstDtl.isActive());
		csRequisitionMst.setCreatedBy(userName);
		csRequisitionMst.setCreatedDate(now);
		csRequisitionMst.setSenderStore(ContentType.WS_LS_PREVENTIVE_MAINTENANCE.toString());
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addStoreRequisitionDtls(wsReceivePreventiveMstDtl,
				csRequisitionMst, roleName, department, authUser);
		
		
		//redirectAttributes.addFlashAttribute("wsPrevReceiveMst",csRequisitionMstDb);
		if (successFlag) {
			
			WsReceivePreventiveMst csRequisitionMstDb = (WsReceivePreventiveMst) commonService.getAnObjectByAnyUniqueColumn("WsReceivePreventiveMst",
					"noteNumber", csRequisitionMst.getNoteNumber());
			
			return "redirect:/prev/prevReceiveShow.do?id=" + csRequisitionMstDb.getId();
		}
			/*return new ModelAndView("localStore/prevReceiveList", model);*/
		 else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/prev/prevReceiveList.do";
		}
	

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
							+ "XFormer_Receive_Preventive");
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
			return "Please upload a PDF file";
		}
		return "";
	}

	public boolean addStoreRequisitionDtls(
			WsReceivePreventiveMstDtl csRequisitionMstDtl,
			WsReceivePreventiveMst csRequisitionMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
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

		if (csRequisitionMstDtl.getQuantity() != null) {
			quantityList = csRequisitionMstDtl.getQuantity();
		}

		if (csRequisitionMstDtl.getRemarks() != null) {
			remarksList = csRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(WS_LS_PRV_NOTE);

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
							WS_LS_PRV_NOTE, stateCodes[0].toString());
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
				//String descoDeptCode = department.getDescoCode();
				Departments wsDept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								descoWSDeptCode);

				String descoWSDeptCode = wsDept.getDescoCode();
				String noteNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoNoteNoPrefix, descoWSDeptCode,
								separator, "WS_PREV_RCV_SEQ");
				csRequisitionMst.setNoteNumber(noteNo);
				commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

				WsReceivePreventiveMst csRequisitionMstDb = (WsReceivePreventiveMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"WsReceivePreventiveMst", "noteNumber",
								csRequisitionMst.getNoteNumber());
				for (int i = 0; i < itemCodeList.size(); i++) {
					WsReceivePreventiveDtl csRequisitionDtl = new WsReceivePreventiveDtl();

					if (!itemCodeList.isEmpty()) {
						csRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						csRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						csRequisitionDtl.setItemName("");
					}

					if (!uomList.isEmpty()) {
						csRequisitionDtl.setUom(uomList.get(i));
					} else {
						csRequisitionDtl.setUom("");
					}
					if (!quantityList.isEmpty()) {
						csRequisitionDtl.setQuantity(quantityList.get(i));
					} else {
						csRequisitionDtl.setQuantity(0.0);
					}
					
					if (!remarksList.isEmpty()) {
						csRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						csRequisitionDtl.setRemarks("");
					}
					// set id null for auto number
					csRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					
					csRequisitionDtl.setCreatedBy(csRequisitionMst
							.getCreatedBy());
					csRequisitionDtl.setCreatedDate(csRequisitionMst
							.getCreatedDate());
					csRequisitionDtl.setActive(true);
					csRequisitionDtl
							.setWsReceivePreventiveMst(csRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);
					saveRemainingQtyBeforeApprove(csRequisitionMstDb.getWoNumber(),csRequisitionDtl.getItemCode(), csRequisitionDtl.getQuantity());
					
				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(csRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser);

			}
			return true;
		} else {
			return false;
		}

	}
	private void saveRemainingQtyBeforeApprove(String contractNo, String itemCode, Double qty){
		
		ItemMaster item = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						itemCode);
		String transformerType = getXFormerType(item.getItemName());
		
		WsCnAllocation allocation = (WsCnAllocation) commonService
				.getAnObjectByAnyUniqueColumn("WsCnAllocation",
						"workOrderNo", contractNo);

		Double remainningQty=0d;
		if (transformerType == ContentType.SINGLE_PHASE.toString()) {
			
			remainningQty=allocation.getRemainingPreventiveQty1P() - qty;
			allocation.setRemainingPreventiveQty1P(remainningQty);
		} else {
			remainningQty=allocation.getRemainingPreventiveQty3P() - qty;
			allocation.setRemainingPreventiveQty3P(remainningQty);
		}
		
		commonService.saveOrUpdateModelObjectToDB(allocation);
		
	}
	public void addStoreRequisitionHierarchyHistory(
			WsReceivePreventiveMst csRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistory = new WsLsRCVPreventiveApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setId(null);		
		approvalHierarchyHistory.setOperationId(csRequisitionMst
				.getNoteNumber());
		approvalHierarchyHistory.setOperationName(WS_LS_PRV_NOTE);
		approvalHierarchyHistory.setCreatedBy(csRequisitionMst.getCreatedBy());
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
		
		//System.out.println(approvalHierarchyHistory.getId());

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/prevReceiveList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsPrevReceiveList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<WsLsRCVPreventiveApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsLsRCVPreventiveApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationId.add(ssCsRequisitionHistoryList.get(i).getOperationId());
		}

		List<WsReceivePreventiveMst> wsReceivePreventiveMst = (List<WsReceivePreventiveMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("WsReceivePreventiveMst", "noteNumber", operationId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("wsReceivePreventiveMst",
				wsReceivePreventiveMst);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WS")) {// workshop
			return new ModelAndView("workshop/preventiveMaintenance/wsPrevReceiveList",
					model);
		}else{return null;}
	}

	@RequestMapping(value = "/prevReceiveShowPost.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getPrevReceiveShowPost(WsReceivePreventiveMst wsPrevReceiveMst) {

		return getPrevReceiveShowGet(wsPrevReceiveMst);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/prevReceiveShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getPrevReceiveShowGet(
			WsReceivePreventiveMst wsPrevReceiveMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		
		
		WsReceivePreventiveMst wsReceivePreventiveMst = (WsReceivePreventiveMst) commonService
				.getAnObjectByAnyUniqueColumn("WsReceivePreventiveMst",
						"id", wsPrevReceiveMst
						.getId().toString());
		
		String userrole = wsReceivePreventiveMst.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
				"userid", userrole);
		wsReceivePreventiveMst.setCreatedBy(createBy.getName());
		
		String operationId = wsReceivePreventiveMst.getNoteNumber();

		List<WsReceivePreventiveDtl> wsReceivePreventiveDtl = (List<WsReceivePreventiveDtl>) (Object) commonService
				.getObjectListByAnyColumn("WsReceivePreventiveDtl",
						"wsReceivePreventiveMst.id", wsReceivePreventiveMst.getId().toString());

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<WsLsRCVPreventiveApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsLsRCVPreventiveApprovalHierarchyHistory",
						WS_LS_PRV_NOTE, operationId, DONE);

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

		List<WsLsRCVPreventiveApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsLsRCVPreventiveApprovalHierarchyHistory",
						WS_LS_PRV_NOTE, operationId, OPEN);

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
				.getApprovalHierarchyByOperationNameAndRoleName(
						WS_LS_PRV_NOTE, roleNameList);
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

		
		Contractor c = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor",
						"contractNo", wsReceivePreventiveMst.getWoNumber());
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
								WS_LS_PRV_NOTE, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get Contractor info

		Map<String, Object> model = new HashMap<String, Object>();
		// Start m@@ || Generate drop down list for location in Grid
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);

		// End m@@ || Generate drop down list for location in Grid

		// List<StoreLocations> storeLocationList = getStoreLocationList("CS");
		model.put("returnStateCode", returnStateCode);
		model.put("wsReceivePreventiveMst", wsReceivePreventiveMst);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("contractor", c);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("wsReceivePreventiveDtl",
				wsReceivePreventiveDtl);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WS")) {
			return new ModelAndView("workshop/preventiveMaintenance/wsPrevReceiveShow",
					model);
		}
		else{return null;}
	}
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, WsReceivePreventiveMst lpMst)
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
			filePath = URLEncoder.encode("XFormer_Receive_Preventive." + extension, "UTF8");			
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("XFormer_Receive_Preventive." + extension, "UTF8","B");
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


	@RequestMapping(value = "/itemReturnSlipApproved.do", method = RequestMethod.GET)
	public String sotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") WsReceivePreventiveMstDtl csRequisitionMstDtl) {

		return receivedItemSubmitApproved(model, csRequisitionMstDtl);

	}

	@RequestMapping(value = "/itemReturnSlipApproved.do", method = RequestMethod.POST)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") WsReceivePreventiveMstDtl csRequisitionMstDtl) {

		return receivedItemSubmitApproved(model, csRequisitionMstDtl);
	}

	@SuppressWarnings("unchecked")
	public String receivedItemSubmitApproved(Model model,
			WsReceivePreventiveMstDtl csRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!csRequisitionMstDtl.getReturn_state().equals("")
				|| csRequisitionMstDtl.getReturn_state().length() > 0) {

			List<WsLsRCVPreventiveApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsLsRCVPreventiveApprovalHierarchyHistory",
							"operationId",
							csRequisitionMstDtl.getNoteNumber());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistory = (WsLsRCVPreventiveApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsLsRCVPreventiveApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_LS_PRV_NOTE, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(csRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsLsRCVPreventiveApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_LS_PRV_NOTE,
							csRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(csRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(csRequisitionMstDtl.getNoteNumber());
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
			String operationId = csRequisitionMstDtl.getNoteNumber();

			WsReceivePreventiveMst wsReceivePreventiveMst = (WsReceivePreventiveMst) commonService
					.getAnObjectByAnyUniqueColumn("WsReceivePreventiveMst",
							"noteNumber", operationId);

			List<WsReceivePreventiveDtl> wsReceivePreventiveDtlList = (List<WsReceivePreventiveDtl>) (Object) commonService
					.getObjectListByAnyColumn("WsReceivePreventiveDtl",
							"wsReceivePreventiveMst.id", wsReceivePreventiveMst.getId().toString());

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_LS_PRV_NOTE);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<WsLsRCVPreventiveApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsLsRCVPreventiveApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistory = (WsLsRCVPreventiveApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsLsRCVPreventiveApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;
			// update issued qty

			List<Double> quantityList = csRequisitionMstDtl
					.getQuantity();

			if (quantityList != null) {
				int p = 0;
				for (WsReceivePreventiveDtl csReqDtl : wsReceivePreventiveDtlList) {
					Double quantity = quantityList.get(p);
					csReqDtl.setQuantity(quantity);
					csReqDtl.setModifiedBy(userName);
					csReqDtl.setModifiedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(csReqDtl);
					// dtlList.add(csReqDtl);
					p++;
				}
			}
			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									WS_LS_PRV_NOTE, nextStateCode + "");

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
									WS_LS_PRV_NOTE, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(csRequisitionMstDtl
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
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					
					saveTransformerRegister(wsReceivePreventiveDtlList, wsReceivePreventiveMst, authUser);

					model.addAttribute("operationId", operationId);

					return "workshop/preventiveMaintenance/wsRcvPrevMainReport";

				}
			}
		}
		return "redirect:/prev/prevReceiveList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") WsReceivePreventiveMstDtl csRequisitionMstDtl) {

		String rrNo = csRequisitionMstDtl.getNoteNumber();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsLsRCVPreventiveApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsLsRCVPreventiveApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistory = (WsLsRCVPreventiveApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsLsRCVPreventiveApprovalHierarchyHistory", "id",
						ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_LS_PRV_NOTE, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsLsRCVPreventiveApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_LS_PRV_NOTE, nextStateCode + "");
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

		return "redirect:/prev/prevReceiveList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") WsReceivePreventiveMstDtl csRequisitionMstDtl) {
		String rrNo = csRequisitionMstDtl.getNoteNumber();
		String justification = csRequisitionMstDtl.getJustification();
		String backStateCode = csRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsLsRCVPreventiveApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsLsRCVPreventiveApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistory = (WsLsRCVPreventiveApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsLsRCVPreventiveApprovalHierarchyHistory", "id",
						ids[0].toString());

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_LS_PRV_NOTE, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
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
		WsLsRCVPreventiveApprovalHierarchyHistory approvalHierarchyHistoryBackState = new WsLsRCVPreventiveApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_LS_PRV_NOTE, backStateCode + "");
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

		return "redirect:/prev/prevReceiveList.do";
	}
	
	private void saveTransformerRegister(List<WsReceivePreventiveDtl> dtlList,
			WsReceivePreventiveMst wsReceivePreventiveMst, AuthUser authUser) {

		for (WsReceivePreventiveDtl dtl : dtlList) {
			TransformerRegister tRegister = null;
			double d = dtl.getQuantity();
			ItemMaster item = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							dtl.getItemCode());
			
			String transformerType = getXFormerType(item.getItemName());
			
			for (double p = 1.0; p <= d; p++) {
				tRegister = new TransformerRegister();
				// Model model = null;
				tRegister.setId(null);
				tRegister.setContractNo(wsReceivePreventiveMst.getWoNumber());
				tRegister.setItemCode(dtl.getItemCode());
				tRegister.setReqNo(wsReceivePreventiveMst.getNoteNumber());
				tRegister.setTicketNo(wsReceivePreventiveMst.getNoteNumber());
				tRegister.setTypeOfWork(PREVENTIVE_MAINTENANCE);
				tRegister.setRcvDeptName(wsReceivePreventiveMst.getSenderDeptName());
				tRegister.setRcvDeptCode(wsReceivePreventiveMst.getSenderDeptId());
				tRegister.setTransformerType(transformerType);
				tRegister.setCreatedBy(wsReceivePreventiveMst.getCreatedBy());
				tRegister.setCreatedDate(wsReceivePreventiveMst.getCreatedDate());
				// transformerRegisterController.transformerRegisterSave(model,
				// tRegister);
				commonService.saveOrUpdateModelObjectToDB(tRegister);

			}
		}
	}

	@RequestMapping(value = "/validQty.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public boolean validQty(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Boolean result = true;
		String contractNo = request.getParameter("contractNo").trim();
		String itemCode = request.getParameter("itemCode").trim();
		String quantity = request.getParameter("quantity")
				.trim();
		
		ItemMaster item = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						itemCode);
		String transformerType = getXFormerType(item.getItemName());
		
		WsCnAllocation allocation = (WsCnAllocation) commonService
				.getAnObjectByAnyUniqueColumn("WsCnAllocation",
						"workOrderNo", contractNo);

		Double remainningQty=0d;
		if (transformerType == ContentType.SINGLE_PHASE.toString()) {
			remainningQty=allocation.getRemainingPreventiveQty1P();//remaing qty
		} else {
			remainningQty=allocation.getRemainingPreventiveQty3P();
		}
		
		if(remainningQty<Double.valueOf(quantity)){
			result=false;
		}

return result;
		
		
	}

	@RequestMapping(value = "/transformer/wsViewInventoryItemCategory.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String wsViewInventoryItemCategory(@RequestBody String json)
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
	
	/*
	*Start gate pass process
	*
	*/
		
	@RequestMapping(value = "/gatePassForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getGatePassForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		//String userName = commonService.getAuthUserName();
		
		List<TransformerRegister> transformerRegisterList = wSTransformerService.getTransformerListForGatePass();//transformer serial no for gate pass 
		//ItemCategory catList = itemGroupService.getGetItemGroupById(46);
		model.put("transformerRegisterList", transformerRegisterList);		
		
		return new ModelAndView(
				"workshop/preventiveMaintenance/wsGatePassForm", model);
	}
	
	@RequestMapping(value = "/getGatePassDetail.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public ModelAndView getGatePassDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String transformerSerialNo = request.getParameter("transformerSerialNo").trim();
		
		TransformerRegister transformerRegister = wSTransformerService.getRegisteredTransformer(transformerSerialNo);

		List<TransformerRegister> transformerRegisterList = wSTransformerService.getTransformerListForGatePass();//transformer serial no for gate pass 
		
		ItemMaster item = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						transformerRegister.getItemCode());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("transformerRegister", transformerRegister);
		model.put("transformerRegisterList", transformerRegisterList);
		model.put("item", item);

		return new ModelAndView(
				"workshop/preventiveMaintenance/wsGatePassForm", model);
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveGatePass.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveGatePass(Model model,
			@ModelAttribute("transformerRegister") TransformerRegister transformerRegister) {

		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		TransformerRegister item = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister", "transformerSerialNo",
						transformerRegister.getTransformerSerialNo());
		 
		String gatePassNo = commonService
				.getOperationIdByPrefixAndSequenceName(
						descoWsGatePassPrefix, descoWSDeptCode,
						separator, "WS_GATE_PASS_SEQ");
		item.setReturnTicketNo(gatePassNo);
		item.setReturnDate(now);

		commonService.saveOrUpdateModelObjectToDB(item);
		
		// Get All Approval Hierarchy on LS_CS_REQUISITION
				List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
						.getApprovalHierarchyByOperationName(WS_GATE_PASS);

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
									WS_GATE_PASS, stateCodes[0].toString());
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
				
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addGatePassHierarchyHistory(item, approvalHierarchy, stateCodes, roleName, department, authUser, now);

		
		
		//redirectAttributes.addFlashAttribute("wsPrevReceiveMst",csRequisitionMstDb);
		if (successFlag) {
			
			return "redirect:/prev/gatePassShow.do?id=" + item.getId();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/prev/gatePassList.do";
		}
		

	}
	
	public boolean addGatePassHierarchyHistory(
			 TransformerRegister transformerRegister,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {
		WsGatePassApprovalHierarchyHistory approvalHierarchyHistory = new WsGatePassApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setId(null);		
		approvalHierarchyHistory.setOperationId(transformerRegister
				.getTransformerSerialNo());
		approvalHierarchyHistory.setOperationName(WS_GATE_PASS);
		approvalHierarchyHistory.setCreatedBy(authUser.getUserid());
		approvalHierarchyHistory.setCreatedDate(now);
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
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gatePassList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsGatePassList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<WsGatePassApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsGatePassApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationId.add(ssCsRequisitionHistoryList.get(i).getOperationId());
		}

		List<TransformerRegister> transformerRegisterList = (List<TransformerRegister>) (Object) commonService
				.getObjectListByAnyColumnValueList("TransformerRegister", "transformerSerialNo", operationId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("transformerRegisterList", transformerRegisterList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WS")) {// workshop
			return new ModelAndView("workshop/preventiveMaintenance/wsGatePassList",
					model);
		}else{return null;}
	}

	@RequestMapping(value = "/gatePassShowPost.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView gatePassShowPost(TransformerRegister transformerRegister) {

		return gatePassShowGet(transformerRegister);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gatePassShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView gatePassShowGet(
			TransformerRegister transformerRegister) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		
		
		TransformerRegister transformerRegisterDb = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"id", transformerRegister
						.getId().toString());
		
		String userrole = transformerRegisterDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
				"userid", userrole);
		transformerRegisterDb.setCreatedBy(createBy.getName());
		
		String operationId = transformerRegisterDb.getTransformerSerialNo();
		ItemMaster item = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						transformerRegisterDb.getItemCode());

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<WsGatePassApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsGatePassApprovalHierarchyHistory",
						WS_GATE_PASS, operationId, DONE);

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

		List<WsGatePassApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsGatePassApprovalHierarchyHistory",
						WS_GATE_PASS, operationId, OPEN);

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
				.getApprovalHierarchyByOperationNameAndRoleName(
						WS_GATE_PASS, roleNameList);
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
								WS_GATE_PASS, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get Contractor info

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("returnStateCode", returnStateCode);
		model.put("transformerRegister", transformerRegisterDb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		 model.put("item", item);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WS")) {
			return new ModelAndView("workshop/preventiveMaintenance/wsGatePassShow",
					model);
		}
		else{return null;}
	}


	@RequestMapping(value = "/gatePassApproved.do", method = RequestMethod.GET)
	public String gatePassApprovedGet(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") TransformerRegisterBean transformerRegister) {

		return gatePassApproved(model, transformerRegister);

	}

	@RequestMapping(value = "/gatePassApproved.do", method = RequestMethod.POST)
	public String gatePassApprovedPost(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") TransformerRegisterBean transformerRegister) {

		return gatePassApproved(model, transformerRegister);
	}

	@SuppressWarnings("unchecked")
	public String gatePassApproved(Model model,
			TransformerRegisterBean transformerRegisterBean) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();
		
		TransformerRegister transformer = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"id", transformerRegisterBean.getId().toString());
		
		String operationId = transformer.getTransformerSerialNo();

		// Send return to next user who backed me
		if (!transformerRegisterBean.getReturn_state().equals("")
				|| transformerRegisterBean.getReturn_state().length() > 0) {

			List<WsGatePassApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsGatePassApprovalHierarchyHistory",
							"operationId",
							operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			WsGatePassApprovalHierarchyHistory approvalHierarchyHistory = (WsGatePassApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsGatePassApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_GATE_PASS, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(transformerRegisterBean
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			WsGatePassApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsGatePassApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_GATE_PASS,
							transformerRegisterBean.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(transformerRegisterBean.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(transformerRegisterBean.getTransformerSerialNo());
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
			

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_GATE_PASS);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<WsGatePassApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsGatePassApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			WsGatePassApprovalHierarchyHistory approvalHierarchyHistory = (WsGatePassApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsGatePassApprovalHierarchyHistory", "id",
							ids[0].toString());

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
									WS_GATE_PASS, nextStateCode + "");

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
									WS_GATE_PASS, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(transformerRegisterBean
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
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					
					model.addAttribute("operationId", operationId);
					model.addAttribute("gpNo", transformer.getReturnTicketNo());
					return "workshop/preventiveMaintenance/wsGatePassListReport";

				}
			}
		}
		return "redirect:/prev/gatePassList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendToGatePass.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String sendToGatePass(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") TransformerRegisterBean transformerRegisterBean) {

		TransformerRegister transformer = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"id", transformerRegisterBean.getId().toString());
		
		String operationId = transformer.getTransformerSerialNo();
		String justification = transformerRegisterBean.getJustification();
		String nextStateCode = transformerRegisterBean.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsGatePassApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsGatePassApprovalHierarchyHistory",
						"operationId", operationId);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsGatePassApprovalHierarchyHistory approvalHierarchyHistory = (WsGatePassApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsGatePassApprovalHierarchyHistory", "id",
						ids[0].toString());
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_GATE_PASS, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		WsGatePassApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsGatePassApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_GATE_PASS, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(operationId + "");
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

		return "redirect:/prev/gatePassList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backToGatePass.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String backToGatePass(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") TransformerRegisterBean transformerRegisterBean) {
		
		TransformerRegister transformer = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"id", transformerRegisterBean.getId().toString());
		
		String operationId = transformer.getTransformerSerialNo();
		String justification = transformerRegisterBean.getJustification();
		String backStateCode = transformerRegisterBean.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WsGatePassApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsGatePassApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"WsGatePassApprovalHierarchyHistory",
						"operationId", operationId);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		WsGatePassApprovalHierarchyHistory approvalHierarchyHistory = (WsGatePassApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"WsGatePassApprovalHierarchyHistory", "id",
						ids[0].toString());

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_GATE_PASS, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
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
		WsGatePassApprovalHierarchyHistory approvalHierarchyHistoryBackState = new WsGatePassApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						WS_GATE_PASS, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(operationId + "");
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

		return "redirect:/prev/gatePassList.do";
	}
	
}