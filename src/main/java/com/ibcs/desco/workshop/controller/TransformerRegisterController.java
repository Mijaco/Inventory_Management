package com.ibcs.desco.workshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.WsInventoryReportApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.workshop.bean.RepairAndMaintenanceTargetBean;
import com.ibcs.desco.workshop.bean.TransformerCloseoutBean;
import com.ibcs.desco.workshop.bean.TransformerRegisterBean;
import com.ibcs.desco.workshop.bean.WsInventoryReportMstDtl;
import com.ibcs.desco.workshop.model.JobCardDtl;
import com.ibcs.desco.workshop.model.JobCardMst;
import com.ibcs.desco.workshop.model.JobCardTemplate;
import com.ibcs.desco.workshop.model.ManufactureNames;
import com.ibcs.desco.workshop.model.RepairAndMaintenanceTarget;
import com.ibcs.desco.workshop.model.TransformerCloseOutDtl;
import com.ibcs.desco.workshop.model.TransformerCloseOutMst;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsAsBuiltDtl;
import com.ibcs.desco.workshop.model.WsInventoryDtl;
import com.ibcs.desco.workshop.model.WsInventoryLookUp;
import com.ibcs.desco.workshop.model.WsInventoryMst;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
// @RequestMapping(value = "/transformer/register")
@PropertySource("classpath:common.properties")
public class TransformerRegisterController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	WSTransformerService wSTransformerService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Value("${desco.inventoryReport.prefix}")
	private String descoInvRptPrefix;

	@Value("${desco.xCloseout.prefix}")
	private String descoXCloseoutPrefix;

	@Value("${desco.ws.department.code}")
	private String descoWSDeptCode;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;
	// private static String DATE_FORMAT = "MM-DD-YYYY";
	private static String DATE_FORMAT = "dd-MM-yyyy";

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

	@ResponseBody
	@RequestMapping(value = "/transformer/register/transformerRegisterUpdate.do", method = RequestMethod.POST)
	public String transformerRegisterUpdate(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();

		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TransformerRegisterBean bean = gson.fromJson(json,
					TransformerRegisterBean.class);

			TransformerRegister transformerRegister = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							"" + bean.getId());

			if (bean.getTransformerSerialNo() != null
					|| bean.getTransformerSerialNo() != "") {
				transformerRegister.setTransformerSerialNo(bean
						.getTransformerSerialNo());
			}
			if (bean.getManufacturedName() != null
					|| bean.getManufacturedName() != "") {
				transformerRegister.setManufacturedName(bean
						.getManufacturedName());
			}
			if (bean.getManufacturedYear() != null
					|| bean.getManufacturedYear() != "") {
				transformerRegister.setManufacturedYear(bean
						.getManufacturedYear());
			}
			if (bean.getKvaRating() != null || bean.getKvaRating() != "") {
				transformerRegister.setKvaRating(bean.getKvaRating());
			}
			if (!bean.getReceivedDate().isEmpty()) {
				transformerRegister.setReceivedDate(setCurrentDate(bean
						.getReceivedDate()));
			}
			if (!bean.getTestDate().isEmpty()) {
				transformerRegister.setTestDate(setCurrentDate(bean
						.getTestDate()));
			}
			if (bean.getJobNo() != null || bean.getJobNo() != "") {
				transformerRegister.setJobNo(bean.getJobNo());
			}
			if (!bean.getReturnDate().isEmpty()) {
				transformerRegister.setReturnDate(setCurrentDate(bean
						.getReturnDate()));
			}
			if (bean.getReturnSlipNo() != null || bean.getReturnSlipNo() != "") {
				transformerRegister.setReturnSlipNo(bean.getReturnSlipNo());
			}
			if (bean.getReturnTicketNo() != null
					|| bean.getReturnTicketNo() != "") {
				transformerRegister.setReturnTicketNo(bean.getReturnTicketNo());
			}
			if (bean.getBillNo() != null || bean.getBillNo() != "") {
				transformerRegister.setBillNo(bean.getBillNo());
			}
			if (bean.getRemarks() != null || bean.getRemarks() != "") {
				transformerRegister.setRemarks(bean.getRemarks());
			}

			transformerRegister.setModifiedBy(commonService.getAuthUserName());
			transformerRegister.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(transformerRegister);
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/transformer/register/transformerRegisterEntry.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getTransformerRegisterEntry() {

		String roleName = commonService.getAuthRoleName();
		// String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		List<TransformerRegister> transformerRegisterList = wSTransformerService
				.getRegisteredTransformerListBeforeReturn();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("transformerRegisterList", transformerRegisterList);
		// List<String> mName=new ArrayList<String>();
		/*
		 * for (Constrants.ManufactureNames name :
		 * Constrants.ManufactureNames.values()) { mName.add(name.toString()); }
		 */
		List<ManufactureNames> mn = wSTransformerService.getAllName();
		model.put("manufactureNames", mn);
		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WS")) {// workshop
			return new ModelAndView(
					"workshop/transformer/wsTransformerRegisterEntry", model);
		} else {
			return null;
		}

	}

	// static String ENCRYPTION_KEY = "Secret Passphrase";

	@RequestMapping(value = "/transformer/register/getTransformerList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public ModelAndView getTransformerList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// String psk = "0123456789012345";
		// String iv = "0123456789012345";
		// String reqNo =
		// decryptString(request.getParameter("reqNo").trim(),psk.getBytes(),iv.getBytes());
		// String s="";
		// String key = request.getParameter("key").trim();
		// String iv = request.getParameter("iv").trim();
		// String salt = request.getParameter("salt").trim();
		// String ciphertext = request.getParameter("ciphertext").trim();

		String reqNo = request.getParameter("reqNo").trim();
		// AESJavaScript ajs = new AESJavaScript();
		/* ajs.setKey("Secret Passphrase"); */
		// String hexCiphertext =
		// "U2FsdGVkX1/uYgVsNZmpbgKQJ8KD+8R8yyYn5+irhoI=";

		// String decrypted = ajs.decrypt(reqNo.getBytes(), ENCRYPTION_KEY);
		List<TransformerRegister> transformerRegList = wSTransformerService
				.getRegisteredTransformerListByReqNo(reqNo);

		List<TransformerRegister> transformerRegisterList = wSTransformerService
				.getRegisteredTransformerListBeforeReturn();

		Map<String, Object> model = new HashMap<String, Object>();

		List<ManufactureNames> mn = wSTransformerService.getAllName();
		model.put("manufactureNames", mn);

		model.put("transformerRegList", transformerRegList);
		model.put("transformerRegisterList", transformerRegisterList);

		return new ModelAndView(
				"workshop/transformer/wsTransformerRegisterEntry", model);
	}

	// ------inventory Report process-----------------------

	@RequestMapping(value = "/inventory/inventoryReportForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView inventoryReportForm() {

		Map<String, Object> model = new HashMap<String, Object>();

		List<TransformerRegister> transformerRegisterList = wSTransformerService
				.getTransformerListForInventoryReport();

		List<WsInventoryLookUp> wsInventoryLookUpItemList = wSTransformerService
				.getWsInventoryLookUpItemList();

		/* model.put("ManufactureNames", Constrants.ManufactureNames.values()); */
		model.put("transformerRegisterList", transformerRegisterList);
		model.put("wsInventoryLookUpItemList", wsInventoryLookUpItemList);
		return new ModelAndView("workshop/wsInventoryReportForm", model);
	}

	@RequestMapping(value = "/getTransformerNo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<TransformerRegister> getTransformerNo(
			@RequestParam String transformerSerialNo,
			HttpServletResponse response) {
		return simulateSearchItemName(transformerSerialNo);

	}

	private List<TransformerRegister> simulateSearchItemName(
			String transformerSerialNo) {

		List<TransformerRegister> transformerRegisterList = new ArrayList<TransformerRegister>();
		List<TransformerRegister> transformerRegisterListData = wSTransformerService
				.getTransformerListForInventoryReport();
		// iterate a list and filter by tagName
		for (TransformerRegister item : transformerRegisterListData) {
			if (item.getTransformerSerialNo().toLowerCase()
					.contains(transformerSerialNo.toLowerCase())) {
				transformerRegisterList.add(item);
			}
		}

		return transformerRegisterList;
	}

	@RequestMapping(value = "/getTransformerData.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTransformerData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String transformerNo = request.getParameter("transformerNo");
		TransformerRegister transformerRegister = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"transformerSerialNo", transformerNo);

		if (transformerRegister != null) {

			return transformerRegister.getRcvDeptName() + ","
					+ transformerRegister.getKvaRating() + ","
					+ transformerRegister.getManufacturedName() + ","
					+ transformerRegister.getManufacturedYear() + ","
					+ transformerRegister.getReceivedDate();
		} else {
			return "";
		}
	}

	@RequestMapping(value = "/inventory/inventoryReportSave.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	public String inventoryReportSave(Model model,
			WsInventoryReportMstDtl wsInventoryReportMstDtl) {
		// get Current Role, User and date

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		WsInventoryMst wsInventoryMst = new WsInventoryMst();
		wsInventoryMst.setContractNo(wsInventoryReportMstDtl.getContractNo());
		wsInventoryMst.setTypeOfWork(wsInventoryReportMstDtl.getTypeOfWork());
		wsInventoryMst.setWsInventoryNo(wsInventoryReportMstDtl
				.getInventoryNo());
		wsInventoryMst.setInventoryDate(now);
		wsInventoryMst.setTransformerSerialNo(wsInventoryReportMstDtl
				.getTransformerSerialNo());

		wsInventoryMst.setManufacturedName(wsInventoryReportMstDtl
				.getManufacturedName());
		wsInventoryMst.setManufacturedYear(wsInventoryReportMstDtl
				.getManufacturedYear());
		wsInventoryMst.setReceivedFrom(wsInventoryReportMstDtl
				.getReceivedFrom());
		wsInventoryMst.setReceivedDate(setCurrentDate(wsInventoryReportMstDtl
				.getReceivedDate()));
		wsInventoryMst.setLastRepairDate(setCurrentDate(wsInventoryReportMstDtl
				.getLastRepairDate()));
		wsInventoryMst.setMeggerResultHtE(wsInventoryReportMstDtl
				.getMeggerResultHtE());
		wsInventoryMst.setMeggerResultHtHtAb(wsInventoryReportMstDtl
				.getMeggerResultHtHtAb());
		wsInventoryMst.setMeggerResultHtHtBc(wsInventoryReportMstDtl
				.getMeggerResultHtHtBc());
		wsInventoryMst.setMeggerResultHtHtCa(wsInventoryReportMstDtl
				.getMeggerResultHtHtCa());
		wsInventoryMst.setMeggerResultHtLt(wsInventoryReportMstDtl
				.getMeggerResultHtLt());
		wsInventoryMst.setMeggerResultLtE(wsInventoryReportMstDtl
				.getMeggerResultLtE());
		wsInventoryMst.setMeggerResultLtLtAb(wsInventoryReportMstDtl
				.getMeggerResultLtLtAb());
		wsInventoryMst.setMeggerResultLtLtBc(wsInventoryReportMstDtl
				.getMeggerResultLtLtBc());
		wsInventoryMst.setMeggerResultLtLtCa(wsInventoryReportMstDtl
				.getMeggerResultLtLtCa());
		wsInventoryMst.setIsPaintingRequired(wsInventoryReportMstDtl
				.getIsPaintingRequired());
		TransformerRegister transformerRegister = (TransformerRegister) commonService
				.getAnObjectByAnyUniqueColumn("TransformerRegister",
						"transformerSerialNo",
						wsInventoryReportMstDtl.getTransformerSerialNo());

		wsInventoryMst.setTransformerRegister(transformerRegister);

		wsInventoryMst.setActive(wsInventoryReportMstDtl.isActive());
		wsInventoryMst.setCreatedBy(userName);
		wsInventoryMst.setCreatedDate(now);
		boolean successFlag = true;
		String msg = "";
		// Save requisition master and details info to db if any details exist
		successFlag = addInventoryReportDtls(wsInventoryReportMstDtl,
				wsInventoryMst, roleName, department, authUser);

		// redirectAttributes.addFlashAttribute("wsPrevReceiveMst",wsInventoryMstDb);
		if (successFlag) {

			WsInventoryMst wsInventoryMstDb = (WsInventoryMst) commonService
					.getAnObjectByAnyUniqueColumn("WsInventoryMst",
							"wsInventoryNo", wsInventoryMst.getWsInventoryNo());

			return "redirect:/inventory/wsInventoryReportShow.do?id="
					+ wsInventoryMstDb.getId();
		}
		/* return new ModelAndView("localStore/prevReceiveList", model); */
		else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/inventory/wsInventoryReportList.do";
		}

	}

	public boolean addInventoryReportDtls(
			WsInventoryReportMstDtl wsInventoryReportMstDtl,
			WsInventoryMst wsInventoryMst, String roleName,
			Departments department, AuthUser authUser) {
		// items comes as List from GUI
		@SuppressWarnings("unused")
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> standardQuantityList = null;
		List<Double> useableQuantityList = null;
		List<Double> unUseableQuantityList = null;
		List<Double> notFoundList = null;
		List<Double> totalFoundList = null;
		List<String> remarksList = null;

		if (wsInventoryReportMstDtl.getItemCode() != null) {
			itemCodeList = wsInventoryReportMstDtl.getItemCode();
		}

		if (wsInventoryReportMstDtl.getItemName() != null) {
			itemNameList = wsInventoryReportMstDtl.getItemName();
		}

		if (wsInventoryReportMstDtl.getUnit() != null) {
			uomList = wsInventoryReportMstDtl.getUnit();
		}

		if (wsInventoryReportMstDtl.getStandardQuantity() != null) {
			standardQuantityList = wsInventoryReportMstDtl
					.getStandardQuantity();
		}

		if (wsInventoryReportMstDtl.getUseableQuantity() != null) {
			useableQuantityList = wsInventoryReportMstDtl.getUseableQuantity();
		}

		if (wsInventoryReportMstDtl.getUnUseableQuantity() != null) {
			unUseableQuantityList = wsInventoryReportMstDtl
					.getUnUseableQuantity();
		}

		if (wsInventoryReportMstDtl.getNotFound() != null) {
			notFoundList = wsInventoryReportMstDtl.getNotFound();
		}

		if (wsInventoryReportMstDtl.getTotalFound() != null) {
			totalFoundList = wsInventoryReportMstDtl.getTotalFound();
		}

		if (wsInventoryReportMstDtl.getRemarks() != null) {
			remarksList = wsInventoryReportMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(WS_INVENTORY_REPORT);

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
							WS_INVENTORY_REPORT, stateCodes[0].toString());
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
			if (standardQuantityList != null) {

				// Saving master information of requisition to Master Table
				// Get and set requisition no from db
				// String descoDeptCode = department.getDescoCode();
				Departments wsDept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								descoWSDeptCode);

				String descoWSDeptCode = wsDept.getDescoCode();
				String invNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoInvRptPrefix, descoWSDeptCode, separator,
								"WS_INV_SEQ");
				wsInventoryMst.setWsInventoryNo(invNo);
				commonService.saveOrUpdateModelObjectToDB(wsInventoryMst);

				WsInventoryMst wsInventoryMstDb = (WsInventoryMst) commonService
						.getAnObjectByAnyUniqueColumn("WsInventoryMst",
								"wsInventoryNo",
								wsInventoryMst.getWsInventoryNo());
				for (int i = 0; i < standardQuantityList.size(); i++) {
					WsInventoryDtl wsInventoryDtl = new WsInventoryDtl();

					/*
					 * if (!itemCodeList.isEmpty()) {
					 * wsInventoryDtl.setItemCode(itemCodeList.get(i)); } else {
					 * wsInventoryDtl.setItemCode(""); }
					 */
					if (standardQuantityList.get(i) == null) {
						continue;
					}
					if (!itemNameList.isEmpty()) {
						wsInventoryDtl.setItemName(itemNameList.get(i));
					} else {
						wsInventoryDtl.setItemName("");
					}

					if (!uomList.isEmpty()) {
						wsInventoryDtl.setUnit(uomList.get(i));
					} else {
						wsInventoryDtl.setUnit("");
					}
					if (!standardQuantityList.isEmpty()) {
						wsInventoryDtl.setStandardQuantity(standardQuantityList
								.get(i));
					} else {
						wsInventoryDtl.setStandardQuantity(0.0);
					}
					if (!useableQuantityList.isEmpty()) {
						wsInventoryDtl.setUseableQuantity(useableQuantityList
								.get(i));
					} else {
						wsInventoryDtl.setUseableQuantity(0.0);
					}
					if (!unUseableQuantityList.isEmpty()) {
						wsInventoryDtl
								.setUnUseableQuantity(unUseableQuantityList
										.get(i));
					} else {
						wsInventoryDtl.setUnUseableQuantity(0.0);
					}
					if (!notFoundList.isEmpty()) {
						wsInventoryDtl.setNotFound(notFoundList.get(i));
					} else {
						wsInventoryDtl.setNotFound(0.0);
					}
					if (!totalFoundList.isEmpty()) {
						wsInventoryDtl.setTotalFound(totalFoundList.get(i));
					} else {
						wsInventoryDtl.setTotalFound(0.0);
					}

					if (!remarksList.isEmpty()) {
						wsInventoryDtl.setRemarks(remarksList.get(i));
					} else {
						wsInventoryDtl.setRemarks("");
					}
					// set id null for auto number
					wsInventoryDtl.setId(null);

					// set RequisitionNo to each detail row

					wsInventoryDtl.setCreatedBy(wsInventoryMst.getCreatedBy());
					wsInventoryDtl.setCreatedDate(wsInventoryMst
							.getCreatedDate());
					wsInventoryDtl.setActive(true);
					wsInventoryDtl.setWsInventoryMst(wsInventoryMstDb);

					// insert item detail in wsInventoryDtl table
					commonService.saveOrUpdateModelObjectToDB(wsInventoryDtl);

				}

				// Start Approval Hierarchy History insert process
				addInventoryReportHierarchyHistory(wsInventoryMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addInventoryReportHierarchyHistory(
			WsInventoryMst wsInventoryMst, ApprovalHierarchy approvalHierarchy,
			Integer[] stateCodes, String roleName, Departments department,
			AuthUser authUser) {
		WsInventoryReportApprovalHierarchyHistory approvalHierarchyHistory = new WsInventoryReportApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setId(null);
		approvalHierarchyHistory.setOperationId(wsInventoryMst
				.getWsInventoryNo());
		approvalHierarchyHistory.setOperationName(WS_INVENTORY_REPORT);
		approvalHierarchyHistory.setCreatedBy(wsInventoryMst.getCreatedBy());
		approvalHierarchyHistory
				.setCreatedDate(wsInventoryMst.getCreatedDate());
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

		// System.out.println(approvalHierarchyHistory.getId());

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/wsInventoryReportList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsInventoryReportList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<WsInventoryReportApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsInventoryReportApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationId.add(ssCsRequisitionHistoryList.get(i).getOperationId());
		}

		List<WsInventoryMst> wsInventoryMst = (List<WsInventoryMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("WsInventoryMst",
						"wsInventoryNo", operationId);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("wsInventoryMst", wsInventoryMst);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_WO")) {// workshop
			model.put("flag", "Y");
			model.put("pageHeader", "../common/wsContractorHeader.jsp");
			return new ModelAndView("workshop/wsInventoryReportList", model);
		}
		if (rolePrefix.equals("ROLE_CS")) {// workshop
			model.put("pageHeader", "../common/csHeader.jsp");
			return new ModelAndView("workshop/wsInventoryReportList", model);
		}
		if (rolePrefix.equals("ROLE_WS")) {// workshop
			model.put("pageHeader", "../common/wsHeader.jsp");
			return new ModelAndView("workshop/wsInventoryReportList", model);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/inventory/inventoryReportShowPost.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView inventoryReportShowPost(WsInventoryMst wsPrevReceiveMst) {

		return getInventoryReportShowGet(wsPrevReceiveMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/wsInventoryReportShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getInventoryReportShowGet(
			WsInventoryMst wsPrevReceiveMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		WsInventoryMst wsReceivePreventiveMst = (WsInventoryMst) commonService
				.getAnObjectByAnyUniqueColumn("WsInventoryMst", "id",
						wsPrevReceiveMst.getId().toString());

		String userrole = wsReceivePreventiveMst.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		wsReceivePreventiveMst.setCreatedBy(createBy.getName());

		String operationId = wsReceivePreventiveMst.getWsInventoryNo();

		List<WsInventoryDtl> wsReceivePreventiveDtl = (List<WsInventoryDtl>) (Object) commonService
				.getObjectListByAnyColumn("WsInventoryDtl",
						"wsInventoryMst.id", wsReceivePreventiveMst.getId()
								.toString());

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<WsInventoryReportApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsInventoryReportApprovalHierarchyHistory",
						WS_INVENTORY_REPORT, operationId, DONE);

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

		List<WsInventoryReportApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"WsInventoryReportApprovalHierarchyHistory",
						WS_INVENTORY_REPORT, operationId, OPEN);

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
						WS_INVENTORY_REPORT, roleNameList);
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

		Contractor c = (Contractor) commonService.getAnObjectByAnyUniqueColumn(
				"Contractor", "contractNo",
				wsReceivePreventiveMst.getContractNo());
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
								WS_INVENTORY_REPORT, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get Contractor info

		Map<String, Object> model = new HashMap<String, Object>();

		// List<StoreLocations> storeLocationList = getStoreLocationList("CS");
		model.put("returnStateCode", returnStateCode);
		model.put("wsInventoryReportMst", wsReceivePreventiveMst);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("contractor", c);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("wsInventoryReportDtl", wsReceivePreventiveDtl);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			model.put("pageHeader", "../common/wsContractorHeader.jsp");

			return new ModelAndView("workshop/wsInventoryReportShow", model);
		}
		if (rolePrefix.equals("ROLE_CS")) {// workshop
			model.put("pageHeader", "../common/csHeader.jsp");
			return new ModelAndView("workshop/wsInventoryReportShow", model);
		}
		if (rolePrefix.equals("ROLE_WS")) {
			model.put("pageHeader", "../common/wsHeader.jsp");

			return new ModelAndView("workshop/wsInventoryReportShow", model);
		} else {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/inventory/updateWsInventory.do", method = RequestMethod.POST)
	public String updateWsRequisition(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			WsInventoryDtl bean = gson.fromJson(json, WsInventoryDtl.class);

			WsInventoryDtl csDtlDb = (WsInventoryDtl) commonService
					.getAnObjectByAnyUniqueColumn("WsInventoryDtl", "id", ""
							+ bean.getId());

			csDtlDb.setStandardQuantity(bean.getStandardQuantity());
			csDtlDb.setUseableQuantity(bean.getUseableQuantity());
			csDtlDb.setUnUseableQuantity(bean.getUnUseableQuantity());
			csDtlDb.setNotFound(bean.getNotFound());
			csDtlDb.setTotalFound(bean.getTotalFound());
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

	@RequestMapping(value = "/inventory/deleteAnItem.do", method = RequestMethod.POST)
	public ModelAndView deleteAnItem(WsInventoryDtl bean) throws Exception {

		WsInventoryDtl csDtlDb = (WsInventoryDtl) commonService
				.getAnObjectByAnyUniqueColumn("WsInventoryDtl", "id",
						"" + bean.getId());

		/*--------WsInventory Item Delete---------*/
		commonService.deleteAnObjectById("WsInventoryDtl", bean.getId());

		return this.getInventoryReportShowGet(csDtlDb.getWsInventoryMst());
	}

	@RequestMapping(value = "/inventory/itemInventoryApproved.do", method = RequestMethod.GET)
	public String itemInventoryApprovedGet(Model model,
			WsInventoryReportMstDtl wsInventoryReportMstDtl) {

		return itemInventoryApproved(model, wsInventoryReportMstDtl);

	}

	@RequestMapping(value = "/itemInventoryApproved.do", method = RequestMethod.POST)
	public String itemInventoryApprovedPost(Model model,
			WsInventoryReportMstDtl wsInventoryReportMstDtl) {

		return itemInventoryApproved(model, wsInventoryReportMstDtl);
	}

	@SuppressWarnings("unchecked")
	public String itemInventoryApproved(Model model,
			WsInventoryReportMstDtl wsInventoryMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!wsInventoryMstDtl.getReturn_state().equals("")
				|| wsInventoryMstDtl.getReturn_state().length() > 0) {

			List<WsInventoryReportApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsInventoryReportApprovalHierarchyHistory",
							"operationId", wsInventoryMstDtl.getInventoryNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			WsInventoryReportApprovalHierarchyHistory approvalHierarchyHistory = (WsInventoryReportApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsInventoryReportApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_INVENTORY_REPORT, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(wsInventoryMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			WsInventoryReportApprovalHierarchyHistory approvalHierarchyHistoryNextState = new WsInventoryReportApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							WS_INVENTORY_REPORT,
							wsInventoryMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(wsInventoryMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(wsInventoryMstDtl
					.getInventoryNo());
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
			String operationId = wsInventoryMstDtl.getInventoryNo();

			/*
			 * WsInventoryMst wsInventoryMst = (WsInventoryMst) commonService
			 * .getAnObjectByAnyUniqueColumn("WsInventoryMst", "wsInventoryNo",
			 * operationId);
			 */

			/*
			 * List<WsInventoryDtl> wsReceivePreventiveDtlList =
			 * (List<WsInventoryDtl>) (Object) commonService
			 * .getObjectListByAnyColumn("WsInventoryDtl", "wsInventoryMst.id",
			 * wsInventoryMst.getId().toString());
			 */

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(WS_INVENTORY_REPORT);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<WsInventoryReportApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"WsInventoryReportApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			WsInventoryReportApprovalHierarchyHistory approvalHierarchyHistory = (WsInventoryReportApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"WsInventoryReportApprovalHierarchyHistory", "id",
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
									WS_INVENTORY_REPORT, nextStateCode + "");

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
									WS_INVENTORY_REPORT, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setJustification(wsInventoryMstDtl
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
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					model.addAttribute("operationId", operationId);

					return "workshop/wsInventoryReport";

				}
			}
		}
		return "redirect:/inventory/wsInventoryReportList.do";
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/inventory/sendTo.do", method =
	 * RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public String
	 * centralSotreReceivedItemSendTo( Model model,
	 * 
	 * @ModelAttribute("wsInventoryMstDtl") WsReceivePreventiveMstDtl
	 * wsInventoryMstDtl) {
	 * 
	 * String rrNo = wsInventoryMstDtl.getNoteNumber(); String justification =
	 * wsInventoryMstDtl.getJustification(); String nextStateCode =
	 * wsInventoryMstDtl.getStateCode();
	 * 
	 * // get Current Dept, User and Role Information // String roleName =
	 * commonService.getAuthRoleName(); String userName =
	 * commonService.getAuthUserName();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * String deptId = authUser.getDeptId();
	 * 
	 * Departments department = departmentsService
	 * .getDepartmentByDeptId(authUser.getDeptId());
	 * 
	 * List<WsInventoryReportApprovalHierarchyHistory>
	 * approvalHierarchyHistoryList =
	 * (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
	 * .getObjectListByAnyColumn( "WsInventoryReportApprovalHierarchyHistory",
	 * "operationId", rrNo);
	 * 
	 * Integer[] ids = new Integer[approvalHierarchyHistoryList.size()]; for
	 * (int i = 0; i < approvalHierarchyHistoryList.size(); i++) { ids[i] =
	 * approvalHierarchyHistoryList.get(i).getId(); } Arrays.sort(ids,
	 * Collections.reverseOrder()); // get current State Code and all info from
	 * approval hierarchy history WsInventoryReportApprovalHierarchyHistory
	 * approvalHierarchyHistory = (WsInventoryReportApprovalHierarchyHistory)
	 * commonService .getAnObjectByAnyUniqueColumn(
	 * "WsInventoryReportApprovalHierarchyHistory", "id", ids[0].toString());
	 * int currentStateCode = approvalHierarchyHistory.getStateCode();
	 * 
	 * // current user's row status will be done after updated ApprovalHierarchy
	 * approvalHierarchy = approvalHierarchyService
	 * .getApprovalHierarchyByOperationNameAndSateCode( WS_INVENTORY_REPORT,
	 * currentStateCode + ""); approvalHierarchyHistory.setStatus(DONE);
	 * approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
	 * approvalHierarchyHistory.setcEmpFullName(authUser.getName());
	 * approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
	 * approvalHierarchyHistory.setCreatedBy(userName);
	 * approvalHierarchyHistory.setModifiedBy(userName);
	 * approvalHierarchyHistory.setModifiedDate(new Date());
	 * approvalHierarchyHistory.setJustification(justification);
	 * approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
	 * .getApprovalHeader());
	 * 
	 * commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
	 * 
	 * // get Next State Code and all info from approval hierarchy history
	 * WsInventoryReportApprovalHierarchyHistory
	 * approvalHierarchyHistoryNextState = new
	 * WsInventoryReportApprovalHierarchyHistory(); ApprovalHierarchy
	 * approvalHierarchyNextSate = approvalHierarchyService
	 * .getApprovalHierarchyByOperationNameAndSateCode( WS_INVENTORY_REPORT,
	 * nextStateCode + ""); approvalHierarchyHistoryNextState.setActive(true);
	 * approvalHierarchyHistoryNextState.setCreatedBy(userName);
	 * approvalHierarchyHistoryNextState.setCreatedDate(new Date());
	 * approvalHierarchyHistoryNextState.setStatus(OPEN);
	 * 
	 * approvalHierarchyHistoryNextState
	 * .setStateName(approvalHierarchyNextSate.getStateName());
	 * approvalHierarchyHistoryNextState.setStateCode(Integer
	 * .parseInt(nextStateCode)); approvalHierarchyHistoryNextState.setId(null);
	 * approvalHierarchyHistoryNextState.setOperationId(rrNo + "");
	 * approvalHierarchyHistoryNextState
	 * .setOperationName(approvalHierarchyNextSate.getOperationName());
	 * approvalHierarchyHistoryNextState
	 * .setActRoleName(approvalHierarchyNextSate.getRoleName());
	 * approvalHierarchyHistoryNextState
	 * .setApprovalHeader(approvalHierarchyNextSate .getApprovalHeader());
	 * 
	 * approvalHierarchyHistoryNextState.setDeptId(deptId);
	 * approvalHierarchyHistoryNextState
	 * .setcDeptName(department.getDeptName());
	 * approvalHierarchyHistoryNextState.setcDesignation(authUser
	 * .getDesignation());
	 * 
	 * commonService
	 * .saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);
	 * 
	 * return "redirect:/prev/prevReceiveList.do"; }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/inventory/backTo.do", method =
	 * RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public String
	 * centralSotreReceivedItemBackTo( Model model,
	 * 
	 * @ModelAttribute("wsInventoryMstDtl") WsReceivePreventiveMstDtl
	 * wsInventoryMstDtl) { String rrNo = wsInventoryMstDtl.getNoteNumber();
	 * String justification = wsInventoryMstDtl.getJustification(); String
	 * backStateCode = wsInventoryMstDtl.getStateCode();
	 * 
	 * // get Current User and Role Information String roleName =
	 * commonService.getAuthRoleName(); String userName =
	 * commonService.getAuthUserName();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * String deptId = authUser.getDeptId();
	 * 
	 * Departments department = departmentsService
	 * .getDepartmentByDeptId(authUser.getDeptId());
	 * 
	 * List<WsInventoryReportApprovalHierarchyHistory>
	 * approvalHierarchyHistoryList =
	 * (List<WsInventoryReportApprovalHierarchyHistory>) (Object) commonService
	 * .getObjectListByAnyColumn( "WsInventoryReportApprovalHierarchyHistory",
	 * "operationId", rrNo);
	 * 
	 * Integer[] ids = new Integer[approvalHierarchyHistoryList.size()]; for
	 * (int i = 0; i < approvalHierarchyHistoryList.size(); i++) { ids[i] =
	 * approvalHierarchyHistoryList.get(i).getId(); } Arrays.sort(ids,
	 * Collections.reverseOrder()); // get current State Code and all info from
	 * approval hierarchy history WsInventoryReportApprovalHierarchyHistory
	 * approvalHierarchyHistory = (WsInventoryReportApprovalHierarchyHistory)
	 * commonService .getAnObjectByAnyUniqueColumn(
	 * "WsInventoryReportApprovalHierarchyHistory", "id", ids[0].toString());
	 * 
	 * int currentStateCode = approvalHierarchyHistory.getStateCode();
	 * 
	 * // current user's row status will be done after updated ApprovalHierarchy
	 * approvalHierarchy = approvalHierarchyService
	 * .getApprovalHierarchyByOperationNameAndSateCode( WS_INVENTORY_REPORT,
	 * currentStateCode + ""); approvalHierarchyHistory.setStatus(DONE);
	 * approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
	 * approvalHierarchyHistory.setcEmpFullName(authUser.getName());
	 * approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
	 * approvalHierarchyHistory.setCreatedBy(userName);
	 * approvalHierarchyHistory.setModifiedBy(userName);
	 * approvalHierarchyHistory.setModifiedDate(new Date());
	 * approvalHierarchyHistory.setJustification(justification);
	 * approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
	 * .getApprovalHeader());
	 * approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
	 * approvalHierarchyHistory.setcEmpFullName(authUser.getName());
	 * approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
	 * 
	 * commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
	 * 
	 * // get Next State Code and all info from approval hierarchy history
	 * WsInventoryReportApprovalHierarchyHistory
	 * approvalHierarchyHistoryBackState = new
	 * WsInventoryReportApprovalHierarchyHistory(); ApprovalHierarchy
	 * approvalHierarchyBackSate = approvalHierarchyService
	 * .getApprovalHierarchyByOperationNameAndSateCode( WS_INVENTORY_REPORT,
	 * backStateCode + ""); approvalHierarchyHistoryBackState.setActive(true);
	 * approvalHierarchyHistoryBackState.setCreatedBy(userName);
	 * approvalHierarchyHistoryBackState.setCreatedDate(new Date());
	 * approvalHierarchyHistoryBackState.setStatus(OPEN);
	 * approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
	 * approvalHierarchyHistoryBackState.setStateCode(Integer
	 * .parseInt(backStateCode)); approvalHierarchyHistoryBackState.setId(null);
	 * approvalHierarchyHistoryBackState.setOperationId(rrNo + "");
	 * approvalHierarchyHistoryBackState
	 * .setOperationName(approvalHierarchyBackSate.getOperationName());
	 * approvalHierarchyHistoryBackState
	 * .setActRoleName(approvalHierarchyBackSate.getRoleName());
	 * approvalHierarchyHistoryBackState
	 * .setApprovalHeader(approvalHierarchyBackSate .getApprovalHeader());
	 * 
	 * // for return same user approvalHierarchyHistoryBackState.setStage(BACK);
	 * approvalHierarchyHistoryBackState.setReturn_to(roleName);
	 * approvalHierarchyHistoryBackState .setReturn_state(currentStateCode +
	 * "");
	 * 
	 * approvalHierarchyHistoryBackState.setDeptId(deptId);
	 * approvalHierarchyHistoryBackState
	 * .setcDeptName(department.getDeptName());
	 * approvalHierarchyHistoryBackState.setcDesignation(authUser
	 * .getDesignation());
	 * 
	 * commonService
	 * .saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);
	 * 
	 * return "redirect:/prev/prevReceiveList.do"; }
	 */

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

	@RequestMapping(value = "/report/totalXRcvReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView totalXRcvReport() {
		return new ModelAndView(
				"workshop/reports/totalTransformerReceiveReport");
	}

	@RequestMapping(value = "/report/totalXReturnReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView totalXReturnReport() {
		return new ModelAndView("workshop/reports/totalTransformerReturnReport");
	}

	@RequestMapping(value = "/report/totalXExistReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView totalXExistReport() {
		return new ModelAndView(
				"workshop/reports/totalTransformerExistingReport");
	}

	@RequestMapping(value = "/report/totalXBillPaidReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView totalXBillPaidReport() {
		return new ModelAndView(
				"workshop/reports/totalTransformerBillPaidReport");
	}

	@RequestMapping(value = "/report/progressReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView progressReport() {
		return new ModelAndView("workshop/reports/progressReport");
	}

	@RequestMapping(value = "/report/xTestReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView xTestReport() {
		return new ModelAndView("workshop/reports/xFormerTestReport");
	}

	@RequestMapping(value = "/report/xCloseOut.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView xCloseOut() {
		return new ModelAndView("workshop/reports/wsCloseOutReport");
	}

	@RequestMapping(value = "/targetForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView setTargetForm() {
		return new ModelAndView("workshop/repairMaintenanceTargetForm");
	}

	@RequestMapping(value = "/targetSave.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	public String targetSave(Model model, RepairAndMaintenanceTargetBean bean) {
		// get Current Role, User and date

		String userName = commonService.getAuthUserName();
		Date now = new Date();
		List<String> targetDateList = null;

		// List<String> workOrderNoList=null;
		List<String> strTargetMonthList = null;
		List<Double> repairTarget1PList = null;
		List<Double> repairTarget3PList = null;
		List<Double> preventiveTarget1PList = null;
		List<Double> preventiveTarget3PList = null;
		List<String> remarksList = null;

		if (bean.getTargetDate() != null) {
			targetDateList = bean.getTargetDate();
		}

		if (bean.getStrTargetMonth() != null) {
			strTargetMonthList = bean.getStrTargetMonth();
		}

		if (bean.getRepairTarget1P() != null) {
			repairTarget1PList = bean.getRepairTarget1P();
		}

		if (bean.getRepairTarget3P() != null) {
			repairTarget3PList = bean.getRepairTarget3P();
		}

		if (bean.getPreventiveTarget1P() != null) {
			preventiveTarget1PList = bean.getPreventiveTarget1P();
		}

		if (bean.getPreventiveTarget3P() != null) {
			preventiveTarget3PList = bean.getPreventiveTarget3P();
		}

		if (bean.getRemarks() != null) {
			remarksList = bean.getRemarks();
		}
		for (int i = 0; i < targetDateList.size(); i++) {
			RepairAndMaintenanceTarget target = new RepairAndMaintenanceTarget();

			if (!targetDateList.isEmpty()) {
				target.setTargetDate(setCurrentDate(targetDateList.get(i)));
			} else {
				target.setTargetDate(null);
			}

			if (!strTargetMonthList.isEmpty()) {
				target.setStrTargetMonth(strTargetMonthList.get(i));
			} else {
				target.setStrTargetMonth("");
			}
			if (!repairTarget1PList.isEmpty()) {
				target.setRepairTarget1P(repairTarget1PList.get(i));
			} else {
				target.setRepairTarget1P(0.0);
			}
			if (!repairTarget3PList.isEmpty()) {
				target.setRepairTarget3P(repairTarget3PList.get(i));
			} else {
				target.setRepairTarget3P(0.0);
			}
			if (!preventiveTarget1PList.isEmpty()) {
				target.setPreventiveTarget1P(preventiveTarget1PList.get(i));
			} else {
				target.setPreventiveTarget1P(0.0);
			}
			if (!preventiveTarget3PList.isEmpty()) {
				target.setPreventiveTarget3P(preventiveTarget3PList.get(i));
			} else {
				target.setPreventiveTarget3P(0.0);
			}

			if (!remarksList.isEmpty()) {
				target.setRemarks(remarksList.get(i));
			} else {
				target.setRemarks("");
			}
			// set id null for auto number
			target.setId(null);

			// set RequisitionNo to each detail row
			target.setWorkOrderNo(bean.getWorkOrderNo());
			target.setCreatedBy(userName);
			target.setCreatedDate(now);
			Contractor c = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
							bean.getWorkOrderNo().toString());

			target.setContractorMst(c);
			// target.setActive(true);
			// insert item detail in target table
			commonService.saveOrUpdateModelObjectToDB(target);

		}

		model.addAttribute("msg", "");
		return "redirect:/targetForm.do";

	}

	@RequestMapping(value = "/getwOrderNo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Contractor> getwOrderNo(
			@RequestParam String wOrderNo, HttpServletResponse response) {
		return simulateSearchWOrderNo(wOrderNo);

	}

	private List<Contractor> simulateSearchWOrderNo(String wOrderNo) {

		List<Contractor> contractorList = new ArrayList<Contractor>();
		List<Contractor> contractorListData = wSTransformerService
				.getContractorList(WORKSHOP);
		// iterate a list and filter by tagName
		for (Contractor con : contractorListData) {
			if (con.getContractNo().toLowerCase()
					.contains(wOrderNo.toLowerCase())) {
				contractorList.add(con);
			}
		}

		return contractorList;
	}

	@RequestMapping(value = "/showMonth.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String showMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targetDate = request.getParameter("targetDate").trim();
		String targetMonth = getCurrentmonth(targetDate);
		if (targetMonth != null) {
			return targetMonth;
		} else {
			return "error show";
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkMonth.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public boolean checkMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targetDate = request.getParameter("targetDate").trim();
		String workOrderNo = request.getParameter("workOrderNo").trim();
		String targetMonth = getCurrentmonth(targetDate);
		
		List<RepairAndMaintenanceTarget> r=(List<RepairAndMaintenanceTarget>) (Object) commonService.getObjectListByTwoColumn("RepairAndMaintenanceTarget","strTargetMonth", targetMonth, "workOrderNo", workOrderNo);
		if(r!=null && r.size()>0)
		
		{
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "/closeoutForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView closeoutForm() {

		return new ModelAndView("workshop/transformerCloseoutForm");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTransformerData.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewTransformerData(String contractNo,
			String startDate, String endDate) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<JobCardTemplate> jobCardTemplateList = (List<JobCardTemplate>) (Object) commonService
				.getObjectListByAnyColumn("JobCardTemplate", "typeOfWork",
						REPAIR_WORKS);
		List<TransformerRegister> tFormerList = wSTransformerService
				.getReturnTransformerList(contractNo, startDate, endDate);

		if (tFormerList != null && tFormerList.size() > 0) {
			for (JobCardTemplate j : jobCardTemplateList) {
				double rcvQty = 0.0;
				double consumeQty = 0.0;
				for (TransformerRegister t : tFormerList) {
					WsAsBuiltDtl wsasDtl = wSTransformerService.getAsbuilt(
							t.getJobNo(), j.getItemCode());
					if (wsasDtl != null) {
						rcvQty += (wsasDtl.getReceivedQuantity());
						j.setRemainingQty(rcvQty);
						consumeQty += (wsasDtl.getMaterialsConsume());
						j.setConsumeQty(consumeQty);
					}
				}
			}

		} else {
			jobCardTemplateList = null;
			model.put("flag", 1);
		}
		if (jobCardTemplateList == null) {
			model.put("flag", 1);
		}
		model.put("jobCardTemplateList", jobCardTemplateList);
		model.put("contractNo", contractNo);
		model.put("startDate", startDate);
		model.put("endDate", endDate);
		return new ModelAndView("workshop/transformerCloseoutForm", model);
	}

	@RequestMapping(value = "/closeoutSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String closeoutSave(Model model, TransformerCloseoutBean bean) {
		// Map<String, Object> model = new HashMap<String, Object>();

		String userName = commonService.getAuthUserName();
		Date now = new Date();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();

		TransformerCloseOutMst asBuiltMst = new TransformerCloseOutMst();
		String asBuiltNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoXCloseoutPrefix, descoDeptCode, separator,
				"X_CLOSEOUT_SEQ");
		asBuiltMst.setxCloseoutNo(asBuiltNo);
		asBuiltMst.setStartDate(setCurrentDate(bean.getStartDate()));
		asBuiltMst.setEndDate(setCurrentDate(bean.getEndDate()));
		asBuiltMst.setActive(true);
		asBuiltMst.setCreatedBy(userName);
		asBuiltMst.setCreatedDate(now);
		asBuiltMst.setWoNumber(bean.getWoNumber());

		boolean successFlag = true;
		String msg = "";

		successFlag = saveAsBuilt(bean, asBuiltMst);

		if (successFlag) {

			return "redirect:/transformerCloseOutList.do";
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/transformerCloseOutList.do";

			// return new ModelAndView("workshop/wsAsBuiltDetailForm",model);
		}
	}

	private boolean saveAsBuilt(TransformerCloseoutBean asBuiltReportBean,
			TransformerCloseOutMst asBuiltMst) {

		commonService.saveOrUpdateModelObjectToDB(asBuiltMst);

		saveAsBuiltDtl(asBuiltReportBean, asBuiltMst);

		return true;
	}

	@SuppressWarnings("unchecked")
	private void saveAsBuiltDtl(TransformerCloseoutBean asBuiltReportBean,
			TransformerCloseOutMst asBuiltMst) {

		List<String> itemCodeList = null;

		List<String> itemNameList = null;

		List<String> uomList = null;

		List<Double> balanceList = null;

		List<Double> rcvPurCashQtyList = null;

		List<Double> rcvFromStoreQtyList = null;

		List<Double> totalQtyList = null;

		List<Double> materialsConsumeList = null;

		List<Double> materialsReturnList = null;

		List<Double> actualReturnList = null;

		List<Double> qtyShortList = null;

		List<Double> qtyExcessList = null;

		List<String> remarksList = null;

		if (asBuiltReportBean.getItemCode() != null) {
			itemCodeList = asBuiltReportBean.getItemCode();
		}
		if (asBuiltReportBean.getItemName() != null) {
			itemNameList = asBuiltReportBean.getItemName();
		}

		if (asBuiltReportBean.getUom() != null) {
			uomList = asBuiltReportBean.getUom();
		}

		if (asBuiltReportBean.getBalance() != null) {
			balanceList = asBuiltReportBean.getBalance();
		}
		if (asBuiltReportBean.getRcvPurCashQty() != null) {
			rcvPurCashQtyList = asBuiltReportBean.getRcvPurCashQty();
		}
		if (asBuiltReportBean.getRcvFromStoreQty() != null) {
			rcvFromStoreQtyList = asBuiltReportBean.getRcvFromStoreQty();
		}
		if (asBuiltReportBean.getTotalQty() != null) {
			totalQtyList = asBuiltReportBean.getTotalQty();
		}

		if (asBuiltReportBean.getMaterialsConsume() != null) {
			materialsConsumeList = asBuiltReportBean.getMaterialsConsume();
		}
		if (asBuiltReportBean.getMaterialsReturn() != null) {
			materialsReturnList = asBuiltReportBean.getMaterialsReturn();
		}
		if (asBuiltReportBean.getActualReturn() != null) {
			actualReturnList = asBuiltReportBean.getActualReturn();
		}
		if (asBuiltReportBean.getMaterialsConsume() != null) {
			materialsConsumeList = asBuiltReportBean.getMaterialsConsume();
		}
		if (asBuiltReportBean.getQtyShort() != null) {
			qtyShortList = asBuiltReportBean.getQtyShort();
		}

		if (asBuiltReportBean.getQtyExcess() != null) {
			qtyExcessList = asBuiltReportBean.getQtyExcess();
		}
		if (asBuiltReportBean.getRemarks() != null) {
			remarksList = asBuiltReportBean.getRemarks();
		}

		for (int i = 0; i < itemCodeList.size(); i++) {

			TransformerCloseOutDtl jobDtl = new TransformerCloseOutDtl();

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

			if (!rcvPurCashQtyList.isEmpty()) {
				jobDtl.setRcvPurCashQty(rcvPurCashQtyList.get(i));
			} else {
				jobDtl.setRcvPurCashQty(0.0);
			}

			if (!rcvFromStoreQtyList.isEmpty()) {
				jobDtl.setRcvFromStoreQty(rcvFromStoreQtyList.get(i));
			} else {
				jobDtl.setRcvFromStoreQty(0.0);
			}

			if (!totalQtyList.isEmpty()) {
				jobDtl.setTotalQty(totalQtyList.get(i));
			} else {
				jobDtl.setTotalQty(0.0);
			}

			if (!materialsConsumeList.isEmpty()) {
				jobDtl.setMaterialsConsume(materialsConsumeList.get(i));
			} else {
				jobDtl.setMaterialsConsume(0.0);
			}

			if (!materialsReturnList.isEmpty()) {
				jobDtl.setMaterialsReturn(materialsReturnList.get(i));
			} else {
				jobDtl.setMaterialsReturn(0.0);
			}
			if (!actualReturnList.isEmpty()) {
				jobDtl.setActualReturn(actualReturnList.get(i));
			} else {
				jobDtl.setActualReturn(0.0);
			}
			if (!qtyShortList.isEmpty()) {
				jobDtl.setQtyShort(qtyShortList.get(i));
			} else {
				jobDtl.setRemarks("");
			}
			if (!qtyExcessList.isEmpty()) {
				jobDtl.setQtyExcess(qtyExcessList.get(i));
			} else {
				jobDtl.setQtyExcess(0.0);
			}
			/*
			 * if (!remarksList.isEmpty()) {
			 * jobDtl.setRemarks(remarksList.get(i)); } else {
			 * jobDtl.setRemarks(""); }
			 */

			jobDtl.setId(null);
			jobDtl.setTransformerCloseOutMst(asBuiltMst);
			jobDtl.setActive(asBuiltMst.isActive());
			jobDtl.setCreatedBy(asBuiltMst.getCreatedBy());
			jobDtl.setCreatedDate(asBuiltMst.getCreatedDate());
			commonService.saveOrUpdateModelObjectToDB(jobDtl);

			// update transformerRegister & JobCardMst table

			List<TransformerRegister> tFormerList = wSTransformerService
					.getReturnTransformerList(asBuiltReportBean.getWoNumber(),
							asBuiltReportBean.getStartDate(),
							asBuiltReportBean.getEndDate());
			for (TransformerRegister t : tFormerList) {
				t.setCloseOut(true);
				commonService.saveOrUpdateModelObjectToDB(t);

				JobCardMst j = (JobCardMst) commonService
						.getAnObjectByAnyUniqueColumn("JobCardMst",
								"jobCardNo", t.getJobNo());
				j.setCloseOut(true);
				commonService.saveOrUpdateModelObjectToDB(j);
				
				List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
						.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.jobCardNo",j.getJobCardNo());

				for(JobCardDtl job: jobCardDtlList){
					job.setRemainningQuantity(0.0);
				}
			}

		}

	}

	@RequestMapping(value = "/ws/progressList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView progressList() {
		return new ModelAndView("workshop/progressList");
	}

	@RequestMapping(value = "/searchProgress.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView searchProgress(RepairAndMaintenanceTargetBean bean) {

		Map<String, Object> model = new HashMap<String, Object>();		

		model.put("jobCardTemplateList", "");

		return new ModelAndView("workshop/progress", model);
	}

	@RequestMapping(value = "/transformerCloseOutList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView transformerCloseOutList() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TransformerCloseOutMst> transformerCloseOutMstList = wSTransformerService
				.getTransformerCloseOutList();

		model.put("transformerCloseOutMstList", transformerCloseOutMstList);

		return new ModelAndView("workshop/wsTransformerCloseOutList", model);
	}

	@RequestMapping(value = "/transformerCloseOutDtl.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView transformerCloseOutDtl(
			TransformerCloseOutMst transformerCloseOutMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TransformerCloseOutDtl> transformerCloseOutMstDetails = wSTransformerService
				.getTransformerCloseOutDetails(transformerCloseOutMst.getId()
						.toString());

		model.put("transformerCloseOutMstDetails",
				transformerCloseOutMstDetails);

		return new ModelAndView("workshop/wsTransformerCloseOutDetails", model);
	}
	
	@RequestMapping(value = "/manufactureNameList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView manufactureNameList() {

		Map<String, Object> model = new HashMap<String, Object>();

		 List<ManufactureNames> manufactureNameList=wSTransformerService.getAllName();
		
		 model.put("manufactureNameList", manufactureNameList);
		
		return new ModelAndView("workshop/xFormerManufactureNameListPage", model);
	}


	@RequestMapping(value = "/saveManufactureName.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveManufactureName(ManufactureNames manufactureName) {

		manufactureName.setId(null);
		commonService.saveOrUpdateModelObjectToDB(manufactureName);

		return "redirect:/manufactureNameList.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateManufactureName.do", method = RequestMethod.POST)
	public String updateManufactureName(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ManufactureNames bean = gson.fromJson(json,
					ManufactureNames.class);

			ManufactureNames csDtlDb = (ManufactureNames) commonService
					.getAnObjectByAnyUniqueColumn("ManufactureNames",
							"id", "" + bean.getId());
		
			csDtlDb.setManufactureName(bean.getManufactureName());
			commonService.saveOrUpdateModelObjectToDB(csDtlDb);

		
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
	
	
}