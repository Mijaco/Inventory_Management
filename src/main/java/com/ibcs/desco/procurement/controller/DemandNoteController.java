package com.ibcs.desco.procurement.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.bean.DemandNoteMstDtl;
import com.ibcs.desco.procurement.model.DemandNoteDtl;
import com.ibcs.desco.procurement.model.DemandNoteMst;
import com.ibcs.desco.procurement.model.DemandNoteNonCodedDtl;
import com.ibcs.desco.procurement.model.DemandNoteSummary;
import com.ibcs.desco.procurement.service.DemandNoteService;

@Controller
@PropertySource("classpath:common.properties")
public class DemandNoteController extends Constrants {

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	UserService userService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Autowired
	DemandNoteService demandNoteService;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.demandNote.prefix}")
	private String demandNotePrefix;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNote1.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNote1From() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			List<ItemCategory> categoryList = itemGroupService
					.getConstructionItemGroups();
			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			model.put("categoryList", categoryList);
			model.put("department", department);
			model.put("authUser", authUser);
			model.put("descoSessionList", descoSessionList);
			return new ModelAndView("procurement/demandNote/demandNote1", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@SuppressWarnings({ "unused" })
	@RequestMapping(value = "/mps/dn/saveDemandNote1.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveDemandNote1(DemandNoteMstDtl dnMstDtl,
			@RequestParam("referenceDoc") MultipartFile referenceDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							dnMstDtl.getFinancialYear());

			String filePath = "";
			String demandNoteNo = "";
			if (dnMstDtl.getItemCode().size() > 0) {
				if (dnMstDtl.getRequiredQunatity().get(0) > 0) {
					filePath = this.saveFileToDrive(referenceDoc);
					demandNoteNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									demandNotePrefix,
									department.getDescoCode(), separator,
									"PROC_DEMAND_NOTE_SEQ");

					DemandNoteMst demandNoteMst = new DemandNoteMst(null,
							department, department.getDeptName(), demandNoteNo,
							filePath, CODED_ITEM, new Date(), false, true,
							dnMstDtl.getRemarks(), userName, new Date(),
							descoSession);

					commonService.saveOrUpdateModelObjectToDB(demandNoteMst);
					DemandNoteMst dnMstDB = (DemandNoteMst) commonService
							.getAnObjectByAnyUniqueColumn("DemandNoteMst",
									"demandNoteNo", demandNoteNo);

					boolean result = this.saveDemandNoteDtl(dnMstDB, dnMstDtl,
							userName);
				} else {
					model.put("errorMsg", "Quantity can not be zero");
					return new ModelAndView("settings/errorSetting", model);
				}
			} else {
				model.put("errorMsg", "Please select at least 1 item.");
				return new ModelAndView("settings/errorSetting", model);
			}

			return this.demandNoteList();

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	private boolean saveDemandNoteDtl(DemandNoteMst dnMst,
			DemandNoteMstDtl dnMstDtl, String userName) {

		boolean flag = false;
		List<String> itemIdList = dnMstDtl.getItemCode();
		List<Double> itemQtyList = dnMstDtl.getRequiredQunatity();
		List<Double> unitCostList = dnMstDtl.getUnitCost();
		List<Double> totalCostList = dnMstDtl.getTotalCost();
		List<Double> previousYearConsumptionList = dnMstDtl
				.getPreviousYearConsumption();

		Date now = new Date();
		for (int i = 0; i < itemIdList.size(); i++) {
			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							itemIdList.get(i));
			DemandNoteDtl demandNoteDtl = new DemandNoteDtl(null, itemMaster,
					itemQtyList.get(i), unitCostList.get(i),
					totalCostList.get(i), previousYearConsumptionList.get(i),
					null, dnMst, true, null, userName, now);

			commonService.saveOrUpdateModelObjectToDB(demandNoteDtl);

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
							+ "demand_note");
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
			return "";
		}
		return "";
	}

	@RequestMapping(value = "/demandNote/downloadDemandNote.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getDemandNoteFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, DemandNoteMst dnMst)
			throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = dnMst.getReferenceDoc();
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
			filePath = URLEncoder.encode("demand_note." + extension, "UTF8");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="
					+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("demand_note." + extension,
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

	/*
	 * // Demand Note 2
	 * 
	 * @RequestMapping(value = "/mps/dn/demandNote2.do", method =
	 * RequestMethod.GET) public ModelAndView AnnexureTwo(LocalPurchaseMstDtl
	 * lp) { Map<String, Object> model = new HashMap<String, Object>(); try {
	 * String userName = commonService.getAuthUserName(); AuthUser authUser =
	 * userService.getAuthUserByUserId(userName); Departments department =
	 * departmentsService .getDepartmentByDeptId(authUser.getDeptId());
	 * List<ItemCategory> categoryList = itemGroupService
	 * .getGeneralItemGroups();
	 * 
	 * model.put("categoryList", categoryList); model.put("department",
	 * department); model.put("authUser", authUser); return new
	 * ModelAndView("procurement/demandNote/demandNote2", model); } catch
	 * (Exception e) { e.printStackTrace(); model.put("errorMsg",
	 * e.getMessage()); return new ModelAndView("procurement/errorProc", model);
	 * } }
	 */
	/*
	 * @SuppressWarnings({ "unused" })
	 * 
	 * @RequestMapping(value = "/mps/dn/saveDemandNote2.do", method =
	 * RequestMethod.POST) //
	 * 
	 * @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	 * public ModelAndView saveLocalPurchase2(DemandNoteMstDtl dnMstDtl,
	 * 
	 * @RequestParam("referenceDoc") MultipartFile referenceDoc) { Map<String,
	 * Object> model = new HashMap<String, Object>(); try { String userName =
	 * commonService.getAuthUserName(); AuthUser authUser =
	 * userService.getAuthUserByUserId(userName); String deptId =
	 * authUser.getDeptId(); Departments department = departmentsService
	 * .getDepartmentByDeptId(deptId);
	 * 
	 * String filePath = ""; String demandNoteNo = ""; if
	 * (dnMstDtl.getItemCode().size() > 0) { if
	 * (dnMstDtl.getRequiredQunatity().get(0) > 0) { filePath =
	 * this.saveFileToDrive(referenceDoc); demandNoteNo = commonService
	 * .getOperationIdByPrefixAndSequenceName( demandNotePrefix,
	 * department.getDescoCode(), separator, "PROC_DEMAND_NOTE_SEQ");
	 * 
	 * DemandNoteMst demandNoteMst = new DemandNoteMst(null, department,
	 * department.getDeptName(), demandNoteNo, filePath, TWO, new Date(), false,
	 * true, dnMstDtl.getRemarks(), userName, new Date());
	 * 
	 * commonService.saveOrUpdateModelObjectToDB(demandNoteMst); DemandNoteMst
	 * dnMstDB = (DemandNoteMst) commonService
	 * .getAnObjectByAnyUniqueColumn("DemandNoteMst", "demandNoteNo",
	 * demandNoteNo);
	 * 
	 * boolean result = this.saveDemandNoteDtl2(dnMstDB, dnMstDtl, userName); }
	 * else { model.put("errorMsg", "Purchase quantity can not be zero"); return
	 * new ModelAndView("settings/errorSetting", model); } } else {
	 * model.put("errorMsg",
	 * "Please select at least 1 item for Local Purcahse"); return new
	 * ModelAndView("settings/errorSetting", model); }
	 * 
	 * List<LocalPurchaseMst> localPurchaseMstList = (List<LocalPurchaseMst>)
	 * (Object) commonService .getObjectListByAnyColumn("LocalPurchaseMst",
	 * "department.deptId", deptId); model.put("localPurchaseMstList",
	 * localPurchaseMstList); return new
	 * ModelAndView("procurement/demandNote/demandNote2List", model);
	 * 
	 * 
	 * return this.demandNoteList();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); model.put("errorMsg",
	 * e.getMessage()); return new ModelAndView("settings/errorSetting", model);
	 * } }
	 * 
	 * private boolean saveDemandNoteDtl2(DemandNoteMst dnMst, DemandNoteMstDtl
	 * dnMstDtl, String userName) {
	 * 
	 * boolean flag = false; List<String> itemIdList = dnMstDtl.getItemCode();
	 * List<String> itemNameList = dnMstDtl.getItemName(); List<String> uomList
	 * = dnMstDtl.getUom(); List<Double> itemQtyList =
	 * dnMstDtl.getRequiredQunatity(); List<Double> unitCostList =
	 * dnMstDtl.getUnitCost(); List<Double> totalCostList =
	 * dnMstDtl.getTotalCost(); List<Double> existingQty =
	 * dnMstDtl.getExistingQty();
	 * 
	 * Date now = new Date(); for (int i = 0; i < itemIdList.size(); i++) {
	 * DemandNoteDtl demandNoteDtl = new DemandNoteDtl(null, itemIdList.get(i),
	 * itemNameList.get(i), uomList.get(i), itemQtyList.get(i),
	 * unitCostList.get(i), totalCostList.get(i), null, existingQty.get(i),
	 * dnMst, true, null, userName, now);
	 * 
	 * commonService.saveOrUpdateModelObjectToDB(demandNoteDtl);
	 * 
	 * flag = true; }
	 * 
	 * return flag; }
	 */

	// Demand Note 3
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNote3.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNote3From() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			List<ItemCategory> categoryList = itemGroupService
					.getGeneralItemGroups();
			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			model.put("categoryList", categoryList);
			model.put("department", department);
			model.put("authUser", authUser);
			model.put("descoSessionList", descoSessionList);
			return new ModelAndView("procurement/demandNote/demandNote3", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings({ "unused" })
	@RequestMapping(value = "/mps/dn/saveDemandNote3.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveDemandNoteNC(DemandNoteMstDtl dnMstDtl,
			@RequestParam("referenceDoc") MultipartFile referenceDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							dnMstDtl.getFinancialYear());

			String filePath = "";
			String demandNoteNo = "";

			Date now = new Date();
			if (dnMstDtl.getItemName().size() > 0) {
				if (dnMstDtl.getRequiredQunatity().get(0) > 0) {
					filePath = this.saveFileToDrive(referenceDoc);
					demandNoteNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									demandNotePrefix,
									department.getDescoCode(), separator,
									"PROC_DEMAND_NOTE_SEQ");

					DemandNoteMst demandNoteMst = new DemandNoteMst(null,
							department, department.getDeptName(), demandNoteNo,
							filePath, NON_CODED_ITEM, new Date(), false, true,
							dnMstDtl.getRemarks(), userName, now, descoSession);

					commonService.saveOrUpdateModelObjectToDB(demandNoteMst);
					DemandNoteMst dnMstDB = (DemandNoteMst) commonService
							.getAnObjectByAnyUniqueColumn("DemandNoteMst",
									"demandNoteNo", demandNoteNo);

					boolean result = this.saveDemandNoteNCDtl(dnMstDB,
							dnMstDtl, userName, now);
				} else {
					model.put("errorMsg", "Quantity can not be zero");
					return new ModelAndView("settings/errorSetting", model);
				}
			} else {
				model.put("errorMsg", "Please select at least 1 item.");
				return new ModelAndView("settings/errorSetting", model);
			}

			return this.demandNoteList();

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	private boolean saveDemandNoteNCDtl(DemandNoteMst demandNoteMst,
			DemandNoteMstDtl dnMstDtl, String userName, Date now) {

		boolean flag = false;
		List<String> itemNameList = dnMstDtl.getItemName();
		List<String> uomList = dnMstDtl.getUom();
		List<Double> itemQtyList = dnMstDtl.getRequiredQunatity();
		List<Double> unitCostList = dnMstDtl.getUnitCost();
		List<Double> totalCostList = dnMstDtl.getTotalCost();

		for (int i = 0; i < itemQtyList.size(); i++) {
			DemandNoteNonCodedDtl demandNoteDtl = new DemandNoteNonCodedDtl(
					null, itemNameList.get(i), uomList.get(i),
					itemQtyList.get(i), unitCostList.get(i),
					totalCostList.get(i), demandNoteMst, userName, now);

			commonService.saveOrUpdateModelObjectToDB(demandNoteDtl);

			flag = true;
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteList() {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DemandNoteMst> demandNoteMstList = (List<DemandNoteMst>) (Object) commonService
					.getObjectListByAnyColumn("DemandNoteMst", "department.id",
							department.getId() + "");

			model.put("demandNoteMstList", demandNoteMstList);
			model.put("department", department);
			return new ModelAndView("procurement/demandNote/demandNoteList",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteShow(DemandNoteMst demandNoteMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = demandNoteMst.getId();

			DemandNoteMst demandNoteDb = (DemandNoteMst) commonService
					.getAnObjectByAnyUniqueColumn("DemandNoteMst", "id", id
							+ "");
			List<DemandNoteDtl> demandNoteDtl = null;
			List<DemandNoteNonCodedDtl> demandNoteNCDtl = null;
			if (demandNoteDb.getAnnexureType().equals(CODED_ITEM)) {
				demandNoteDtl = (List<DemandNoteDtl>) (Object) commonService
						.getObjectListByAnyColumn("DemandNoteDtl",
								"demandNoteMst", id + "");
			} else if (demandNoteDb.getAnnexureType().equals(NON_CODED_ITEM)) {
				demandNoteNCDtl = (List<DemandNoteNonCodedDtl>) (Object) commonService
						.getObjectListByAnyColumn("DemandNoteNonCodedDtl",
								"demandNoteMst", id + "");
			}
			String userrole = demandNoteDb.getCreatedBy();
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userrole);
			demandNoteDb.setCreatedBy(createBy.getName());

			List<ItemCategory> categoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");

			model.put("categoryList", categoryList);
			model.put("demandNoteDb", demandNoteDb);
			if (demandNoteDb.getAnnexureType().equals(CODED_ITEM)) {
				model.put("demandNoteDtlList", demandNoteDtl);
				return new ModelAndView(
						"procurement/demandNote/demandNoteShow", model);
			} else {
				model.put("demandNoteDtlList", demandNoteNCDtl);
				return new ModelAndView(
						"procurement/demandNote/demandNoteNCShow", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteSummaryForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteSummaryForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			List<Departments> departmentList = (List<Departments>) (Object) commonService
					.getAllObjectList("Departments");

			List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
					.getAllObjectList("ItemCategory");

			model.put("descoSession", descoSession);
			model.put("departmentList", departmentList);
			model.put("itemCategoryList", itemCategoryList);
			return new ModelAndView(
					"procurement/demandNote/demandNoteSummaryForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/mps/dn/generateDemandNoteSummary.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView generateDemandNoteSummary(DescoSession descoSession,
			DemandNoteMst dNoteMst) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer session = descoSession.getId();
			String annexureType = dNoteMst.getAnnexureType();
			String itemCategoryCode = dNoteMst.getItemCategoryCode();
			String departmentId = dNoteMst.getDepartmentId();

			List<DemandNoteDtl> demandNoteDtlList = null;
			List<DemandNoteNonCodedDtl> demandNoteNCDtlList = null;
			List<DemandNoteDtl> summaryDemandNoteDtlList = null;
			if (annexureType.equalsIgnoreCase(NON_CODED_ITEM)) {
				if (departmentId.length() == 0) {
					demandNoteNCDtlList = (List<DemandNoteNonCodedDtl>) (Object) commonService
							.getObjectListByTwoColumn("DemandNoteNonCodedDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"demandNoteMst.descoSession.id",
									session.toString());
				} else if (departmentId.length() > 0) {
					demandNoteNCDtlList = (List<DemandNoteNonCodedDtl>) (Object) commonService
							.getObjectListByThreeColumn(
									"DemandNoteNonCodedDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"demandNoteMst.descoSession.id",
									session.toString(),
									"demandNoteMst.department", departmentId);
				}

			} else if (annexureType.startsWith("1")) {
				String itemType = annexureType.substring(1);
				if (departmentId.length() == 0
						&& itemCategoryCode.length() == 0) { // only two params
					demandNoteDtlList = (List<DemandNoteDtl>) (Object) commonService
							.getObjectListByThreeColumn("DemandNoteDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"itemMaster.itemType", itemType,
									"demandNoteMst.descoSession.id",
									session.toString());

					summaryDemandNoteDtlList = getSummaryDemandNoteDtl(demandNoteDtlList);

				} else if (departmentId.length() > 0
						&& itemCategoryCode.length() == 0) { // department id (3
																// params)
					demandNoteDtlList = (List<DemandNoteDtl>) (Object) commonService
							.getObjectListByFourColumn("DemandNoteDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"itemMaster.itemType", itemType,
									"demandNoteMst.descoSession.id",
									session.toString(),
									"demandNoteMst.department", departmentId);

					summaryDemandNoteDtlList = getSummaryDemandNoteDtl(demandNoteDtlList);

				} else if (itemCategoryCode.length() > 0
						&& departmentId.length() == 0) { // item category (3
															// params)
					demandNoteDtlList = (List<DemandNoteDtl>) (Object) commonService
							.getObjectListByFourColumn("DemandNoteDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"itemMaster.itemType", itemType,
									"demandNoteMst.descoSession.id",
									session.toString(),
									"itemMaster.categoryId", itemCategoryCode);

					summaryDemandNoteDtlList = getSummaryDemandNoteDtl(demandNoteDtlList);
				} else if (itemCategoryCode.length() > 0
						&& departmentId.length() > 0) {
					demandNoteDtlList = (List<DemandNoteDtl>) (Object) commonService
							.getObjectListByFiveColumn("DemandNoteDtl",
									"demandNoteMst.confirm", CONFIRMED,
									"itemMaster.itemType", itemType,
									"demandNoteMst.descoSession.id",
									session.toString(),
									"itemMaster.categoryId", itemCategoryCode,
									"demandNoteMst.department", departmentId);
				}

			}
			model.put("message", dNoteMst.getMessages());
			model.put("sessionid", session);
			model.put("annexureType", annexureType);

			if (annexureType.equals(CODED_ITEM + SYSTEM_MATERIALS)
					|| annexureType.equals(CODED_ITEM + GENERAL_ITEMS)) {
				model.put("demandNoteDtlListDb", summaryDemandNoteDtlList);
				return new ModelAndView(
						"procurement/demandNote/demandNoteSummaryList", model);
			} else {
				model.put("demandNoteDtlListDb", demandNoteNCDtlList);
				return new ModelAndView(
						"procurement/demandNote/demandNoteSummaryNCList", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	List<DemandNoteDtl> getSummaryDemandNoteDtl(
			List<DemandNoteDtl> demandNoteDtlList) {
		List<Integer> itemIDListMix = new ArrayList<Integer>();

		for (DemandNoteDtl demandNoteDtl : demandNoteDtlList) {
			itemIDListMix.add(demandNoteDtl.getItemMaster().getId());
		}

		List<Integer> itemIdListUnique = new ArrayList<Integer>(
				new HashSet<Integer>(itemIDListMix));
		List<DemandNoteDtl> demandNoteDtlFiltered = new ArrayList<DemandNoteDtl>();
		for (Integer id : itemIdListUnique) {
			Double reqQty = 0.0d;
			Double uCost = 0.0d;
			Double tCost = 0.0d;
			Double pConsumption = 0.0d;
			DemandNoteDtl dtl = new DemandNoteDtl();
			Integer count = 0;
			for (DemandNoteDtl demandNoteDtl : demandNoteDtlList) {
				if (demandNoteDtl.getItemMaster().getId() == id) {
					reqQty += demandNoteDtl.getRequiredQunatity();
					uCost += demandNoteDtl.getEstimateUnitCost();
					tCost += demandNoteDtl.getEstimateTotalCost();
					pConsumption += demandNoteDtl.getPreviousYearConsumption();
					count++;
				} else {
					continue;
				}

				dtl.setItemMaster(demandNoteDtl.getItemMaster());
				dtl.setRequiredQunatity(reqQty);
				dtl.setEstimateUnitCost(uCost / count);
				dtl.setEstimateTotalCost(tCost);
				dtl.setPreviousYearConsumption(pConsumption);
				dtl.setDemandNoteMst(demandNoteDtl.getDemandNoteMst());

			}
			demandNoteDtlFiltered.add(dtl);
		}
		return demandNoteDtlFiltered;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteSummaryShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView DemandNoteSummaryShow(DescoSession descoSession,
			DemandNoteDtl demandNoteDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = descoSession.getId();
			String itemCode = demandNoteDtl.getRemarks();

			List<DemandNoteDtl> demandNoteDtlDb = (List<DemandNoteDtl>) (Object) commonService
					.getObjectListByThreeColumn("DemandNoteDtl",
							"demandNoteMst.descoSession.id", id.toString(),
							"itemMaster.itemId", itemCode,
							"demandNoteMst.confirm", CONFIRMED);

			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							itemCode);

			model.put("itemMaster", itemMaster);
			model.put("demandNoteDtlDb", demandNoteDtlDb);
			return new ModelAndView(
					"procurement/demandNote/demandNoteSummaryShow", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/mps/dn/saveRequirementSummary.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveRequirementSummary(DemandNoteMst demandNoteMst) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			Integer sessionId = demandNoteMst.getId();
			String annexureType = demandNoteMst.getAnnexureType();
			DescoSession dsSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							sessionId + "");

			List<DemandNoteSummary> demandNoteSummaryList = (List<DemandNoteSummary>) (Object) commonService
					.getObjectListByTwoColumn("DemandNoteSummary",
							"annexureType", annexureType, "descoSession.id",
							dsSession.getId() + "");

			if (demandNoteSummaryList.size() > 0) {
				String message = "Your requriement summary already exists. Please check your summary list.";
				demandNoteMst.setMessages(message);
				return this.generateDemandNoteSummary(dsSession, demandNoteMst);
			} else {
				Date now = new Date();

				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

				Date startDate = dsSession.getStartDate();
				Date endDate = dsSession.getEndDate();

				String fromDate = df.format(startDate);
				String toDate = df.format(endDate);

				List<DemandNoteMst> dNMstList = (List<DemandNoteMst>) (Object) commonService
						.getObjectListByDateRange("DemandNoteMst",
								"demandDate", fromDate, toDate, "annexureType",
								annexureType);
				List<String> ids = new ArrayList<String>();
				for (DemandNoteMst demandNoteMst2 : dNMstList) {
					ids.add(demandNoteMst2.getId() + "");
				}

				List<Object> demandNoteDtlList = (List<Object>) (Object)

				demandNoteService
						.getDemandNoteSummarybyMstIdColumnValueList(ids);
				List<DemandNoteDtl> demandNoteDtlListDb = new ArrayList<DemandNoteDtl>();
				for (int i = 0; i < demandNoteDtlList.size(); i++) {
					Object[] row = (Object[]) demandNoteDtlList.get(i);
					DemandNoteSummary dnSummary = new DemandNoteSummary();
					dnSummary.setAnnexureType(annexureType);
					dnSummary.setDescoSession(dsSession);
					dnSummary.setItemCode(row[0] + "");
					dnSummary.setItemName(row[1] + "");
					dnSummary.setEstimateUnitCost(Double.parseDouble(row[2]
							+ ""));
					dnSummary.setRequiredQunatity(Double.parseDouble(row[3]
							+ ""));
					dnSummary.setEstimateTotalCost(Double.parseDouble(row[2]
							+ "")
							* Double.parseDouble(row[3] + ""));
					dnSummary.setUnit(row[4] + "");
					if (annexureType.equals(TWO)) {
						dnSummary.setExistingQty(Double
								.parseDouble(row[6] + ""));
						dnSummary.setPreviousYearConsumption(0.0);
					} else {
						dnSummary.setPreviousYearConsumption(Double
								.parseDouble(row[5] + ""));
						dnSummary.setExistingQty(0.0);
					}
					dnSummary.setApproved(false);
					dnSummary.setActive(true);
					dnSummary.setCreatedBy(userName);
					dnSummary.setCreatedDate(now);
					dnSummary.setId(null);

					commonService.saveOrUpdateModelObjectToDB(dnSummary);
					// demandNoteDtlListDb.add(dnDtl);
				}
				// return this.demandNoteSummaryBySession(dsSession, dnSummary);

				List<DemandNoteSummary> dmnSummaryList = (List<DemandNoteSummary>) (Object)
				// commonService.getObjectListByAnyColumn("DemandNoteSummary",
				// "descoSession.id", dsSession.getId() + "");
				commonService.getObjectListByTwoColumn("DemandNoteSummary",
						"descoSession.id", dsSession.getId() + "",
						"annexureType", annexureType);
				DescoSession descoSession = (DescoSession) commonService
						.getAnObjectByAnyUniqueColumn("DescoSession", "id",
								dsSession.getId() + "");

				model.put("demandNoteSummaryList", dmnSummaryList);
				model.put("descoSession", descoSession);
				model.put("annexureType", annexureType);
				return new ModelAndView(
						"procurement/demandNote/demandNoteSummaryListBySession",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteSummaryBySession.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteSummaryBySession(DescoSession dsSession,
			DemandNoteSummary dmnSummary) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// DemandNoteSummary dmnSummary = new DemandNoteSummary();
			String annexureType = dmnSummary.getAnnexureType();

			List<DemandNoteSummary> demandNoteSummaryList = (List<DemandNoteSummary>) (Object)
			// commonService.getObjectListByAnyColumn("DemandNoteSummary",
			// "descoSession.id", dsSession.getId() + "");
			commonService.getObjectListByTwoColumn("DemandNoteSummary",
					"descoSession.id", dsSession.getId() + "", "annexureType",
					annexureType);
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							dsSession.getId() + "");

			model.put("demandNoteSummaryList", demandNoteSummaryList);
			model.put("descoSession", descoSession);
			model.put("annexureType", annexureType);
			return new ModelAndView(
					"procurement/demandNote/demandNoteSummaryListBySession",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/dn/demandNoteSummaryFormBySession.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteSummaryFormBySession() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSession", descoSession);
			return new ModelAndView(
					"procurement/demandNote/demandNoteSummaryFormBySession",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// demand Note Dtl Delete
	@RequestMapping(value = "/mps/dn/demandNoteDtlDelete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteDtldelete(DemandNoteDtl dnDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			DemandNoteDtl demandNoteDtl = (DemandNoteDtl) commonService
					.getAnObjectByAnyUniqueColumn("DemandNoteDtl", "id",
							dnDtl.getId() + "");

			DemandNoteMst dnMst = demandNoteDtl.getDemandNoteMst();
			commonService.deleteAnObjectById("DemandNoteDtl", dnDtl.getId());
			return this.demandNoteShow(dnMst);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	@RequestMapping(value = "/mps/dn/demandNoteNCDtlDelete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteNCDtldelete(DemandNoteNonCodedDtl dnDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			DemandNoteNonCodedDtl demandNoteDtl = (DemandNoteNonCodedDtl) commonService
					.getAnObjectByAnyUniqueColumn("DemandNoteNonCodedDtl",
							"id", dnDtl.getId() + "");

			DemandNoteMst dnMst = demandNoteDtl.getDemandNoteMst();
			commonService.deleteAnObjectById("DemandNoteNonCodedDtl",
					dnDtl.getId());
			return this.demandNoteShow(dnMst);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("settings/errorSetting", model);
		}
	}

	// Taleb
	@RequestMapping(value = "/mps/dn/demandNoteDtlUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String demandNoteDtlUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		boolean flag = false;
		if (isJson) {
			DemandNoteDtl demandNoteDtl = gson.fromJson(cData,
					DemandNoteDtl.class);
			Integer demandNoteDtlId = demandNoteDtl.getId();
			DemandNoteDtl demandNoteDtlDb = (DemandNoteDtl) commonService
					.getAnObjectByAnyUniqueColumn("DemandNoteDtl", "id",
							demandNoteDtlId + "");
			if (demandNoteDtlDb != null) {
				demandNoteDtlDb.setRequiredQunatity(demandNoteDtl
						.getRequiredQunatity());
				demandNoteDtlDb.setEstimateUnitCost(demandNoteDtl
						.getEstimateUnitCost());
				demandNoteDtlDb.setEstimateTotalCost(demandNoteDtl
						.getEstimateTotalCost());
				demandNoteDtlDb.setPreviousYearConsumption(demandNoteDtl
						.getPreviousYearConsumption());
				demandNoteDtlDb.setModifiedBy(commonService.getAuthUserName());
				demandNoteDtlDb.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(demandNoteDtlDb);
				flag = true;
			}
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (flag) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Update Failed.");
		}
		return toJson;
	}

	@RequestMapping(value = "/mps/dn/demandNoteNCDtlUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String demandNoteDtlNCUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		boolean flag = false;
		if (isJson) {
			DemandNoteDtl demandNoteDtl = gson.fromJson(cData,
					DemandNoteDtl.class);
			Integer demandNoteDtlId = demandNoteDtl.getId();
			DemandNoteNonCodedDtl demandNoteDtlDb = (DemandNoteNonCodedDtl) commonService
					.getAnObjectByAnyUniqueColumn("DemandNoteNonCodedDtl",
							"id", demandNoteDtlId + "");
			if (demandNoteDtlDb != null) {
				demandNoteDtlDb.setRequiredQunatity(demandNoteDtl
						.getRequiredQunatity());
				demandNoteDtlDb.setEstimateUnitCost(demandNoteDtl
						.getEstimateUnitCost());
				demandNoteDtlDb.setEstimateTotalCost(demandNoteDtl
						.getEstimateTotalCost());
				demandNoteDtlDb.setModifiedBy(commonService.getAuthUserName());
				demandNoteDtlDb.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(demandNoteDtlDb);
				flag = true;
			}
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (flag) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Update Failed.");
		}
		return toJson;
	}

	@RequestMapping(value = "/mps/dn/saveNewCodedDemandNoteDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveNewCodedDemandNoteDtl(DemandNoteMstDtl dnMstDtl) {

		DemandNoteMst dnMst = (DemandNoteMst) commonService
				.getAnObjectByAnyUniqueColumn("DemandNoteMst", "id", dnMstDtl
						.getId().toString());
		String userName = commonService.getAuthUserName();

		List<String> itemIdList = dnMstDtl.getItemCode();
		List<Double> itemQtyList = dnMstDtl.getRequiredQunatity();
		List<Double> unitCostList = dnMstDtl.getUnitCost();
		List<Double> totalCostList = dnMstDtl.getTotalCost();
		List<Double> previousYearConsumptionList = dnMstDtl
				.getPreviousYearConsumption();

		Date now = new Date();
		for (int i = 0; i < itemIdList.size(); i++) {
			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							itemIdList.get(i));
			DemandNoteDtl demandNoteDtl = new DemandNoteDtl(null, itemMaster,
					itemQtyList.get(i), unitCostList.get(i),
					totalCostList.get(i), previousYearConsumptionList.get(i),
					null, dnMst, true, null, userName, now);

			commonService.saveOrUpdateModelObjectToDB(demandNoteDtl);

		}

		return this.demandNoteShow(dnMst);
	}

	@RequestMapping(value = "/mps/dn/saveNewNonCodedDemandNoteDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveNewNonCodedDemandNoteDtl(DemandNoteMstDtl dnMstDtl) {

		DemandNoteMst demandNoteMst = (DemandNoteMst) commonService
				.getAnObjectByAnyUniqueColumn("DemandNoteMst", "id", dnMstDtl
						.getId().toString());
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		List<String> itemNameList = dnMstDtl.getItemName();
		List<String> uomList = dnMstDtl.getUom();
		List<Double> itemQtyList = dnMstDtl.getRequiredQunatity();
		List<Double> unitCostList = dnMstDtl.getUnitCost();
		List<Double> totalCostList = dnMstDtl.getTotalCost();

		for (int i = 0; i < itemQtyList.size(); i++) {
			DemandNoteNonCodedDtl demandNoteDtl = new DemandNoteNonCodedDtl(
					null, itemNameList.get(i), uomList.get(i),
					itemQtyList.get(i), unitCostList.get(i),
					totalCostList.get(i), demandNoteMst, userName, now);

			commonService.saveOrUpdateModelObjectToDB(demandNoteDtl);

		}

		return this.demandNoteShow(demandNoteMst);
	}

	// /mps/dn/confirmDemandNote.do
	@RequestMapping(value = "/mps/dn/confirmDemandNote.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView confirmDemandNote(DemandNoteMst dnMst) {

		DemandNoteMst demandNoteMst = (DemandNoteMst) commonService
				.getAnObjectByAnyUniqueColumn("DemandNoteMst", "id", dnMst
						.getId().toString());
		String userName = commonService.getAuthUserName();

		demandNoteMst.setConfirm(CONFIRMED);
		demandNoteMst.setModifiedBy(userName);
		demandNoteMst.setModifiedDate(new Date());
		demandNoteMst.setDemandDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(demandNoteMst);
		return this.demandNoteShow(dnMst);
	}
}
