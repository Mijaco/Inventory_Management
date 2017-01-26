package com.ibcs.desco.procurement.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ibcs.desco.common.model.AppPurchaseApprovalHierarchyHistory;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.bean.AppPurchaseMstDtl;
import com.ibcs.desco.procurement.model.AppPurchaseDtl;
import com.ibcs.desco.procurement.model.AppPurchaseMst;
import com.ibcs.desco.procurement.model.ProcurementPackageDtl;
import com.ibcs.desco.procurement.model.ProcurementPackageMst;
import com.ibcs.desco.procurement.model.ProcurementProcessCommittee;
import com.ibcs.desco.procurement.service.DemandNoteService;

@Controller
@PropertySource("classpath:common.properties")
public class AppPurchaseController extends Constrants {

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
	@RequestMapping(value = "/app/purchase/getForm.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAppPurchaseFrom() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			model.put("department", department);
			model.put("authUser", authUser);
			model.put("descoSessionList", descoSessionList);
			return new ModelAndView("procurement/app/loadPurchaseForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/procPackage.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getProcPackage(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ProcurementPackageMst procurementPackageMst = gson.fromJson(json,
					ProcurementPackageMst.class);

			List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
					.getObjectListByThreeColumnWithOneNullValue(
							"ProcurementPackageMst", "descoSession.id",
							procurementPackageMst.getId() + "", "confirm",
							CONFIRMED, "purchaseStatus");

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(procurementPackageMstList);

		} else {
			Thread.sleep(2 * 1000);
		}

		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/getProcurementForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getProcurementForm(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
					.getObjectListByTwoColumn("ProcurementPackageMst",
							"descoSession.id", appPurchaseMst.getId() + "",
							"annexureNo", appPurchaseMst.getAnnexureNo());
			ProcurementPackageMst procurementPackageMstDb = procurementPackageMstList
					.get(0);

			List<ProcurementPackageDtl> procPackDtlList = (List<ProcurementPackageDtl>) (Object) commonService
					.getObjectListByAnyColumn("ProcurementPackageDtl",
							"procurementPackageMst.id",
							procurementPackageMstDb.getId() + "");

			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			List<Departments> departmentList = (List<Departments>) (Object) commonService
					.getAllObjectList("Departments");

			model.put("sessionId", appPurchaseMst.getId());
			model.put("descoKhathList", descoKhathList);
			model.put("departmentList", departmentList); // added for new Form
			model.put("procurementPackageMst", procurementPackageMstDb);
			model.put("procPackDtlList", procPackDtlList);
			model.put("department", department);
			model.put("authUser", authUser);
			// return new ModelAndView("procurement/app/appPurchaseForm",
			// model);
			return new ModelAndView("procurement/app/appPurchaseRequestForm",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/saveProcurementForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveProcurementForm(
			AppPurchaseMstDtl appPurchaseMstDtl,
			@RequestParam("specificationDoc") MultipartFile specificationDoc,
			@RequestParam("requisitionAppDoc") MultipartFile requisitionAppDoc,
			@RequestParam("draftTenderDoc") MultipartFile draftTenderDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							appPurchaseMstDtl.getId() + "");
			List<AppPurchaseMst> appProcMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByTwoColumn("AppPurchaseMst",
							"descoSession.id", appPurchaseMstDtl.getId() + "",
							"procurementPackageMst.annexureNo",
							appPurchaseMstDtl.getAnnexureNo());
			if (appProcMstList.size() > 0 && appProcMstList != null) {
				return this.showProcurementForm(appProcMstList.get(0));
			} else {

				String draftTenderDocFilePath = "";
				String requisitionAppDocFilePath = "";
				String specificationDocFilePath = "";

				specificationDocFilePath = this
						.saveFileToDrive(specificationDoc);
				draftTenderDocFilePath = this.saveFileToDrive(draftTenderDoc);
				requisitionAppDocFilePath = this
						.saveFileToDrive(requisitionAppDoc);

				List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByTwoColumn("ProcurementPackageMst",
								"descoSession.id", appPurchaseMstDtl.getId()
										+ "", "annexureNo",
								appPurchaseMstDtl.getAnnexureNo());
				ProcurementPackageMst procurementPackageMstDb = procurementPackageMstList
						.get(0);
				AppPurchaseMst appPurchaseMst = new AppPurchaseMst();
				appPurchaseMst
						.setProcurementPackageMst(procurementPackageMstDb);
				appPurchaseMst.setDescoSession(descoSession);
				appPurchaseMst.setRequisitionRef(appPurchaseMstDtl
						.getRequisitionRef());
				appPurchaseMst.setProjectName(appPurchaseMstDtl
						.getProjectName());
				appPurchaseMst.setDraftTenderDoc(draftTenderDocFilePath);
				appPurchaseMst.setSpecificationDoc(specificationDocFilePath);
				appPurchaseMst.setRequisitionAppDoc(requisitionAppDocFilePath);
				appPurchaseMst.setRemarks(appPurchaseMstDtl.getComments());
				appPurchaseMst.setCreatedBy(userName);
				appPurchaseMst.setCreatedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMst);

				/* Added by Ashid || to */
				procurementPackageMstDb.setPurchaseFlag("1");
				commonService
						.saveOrUpdateModelObjectToDB(procurementPackageMstDb);

				List<ProcurementPackageDtl> procPackDtlList = (List<ProcurementPackageDtl>) (Object) commonService
						.getObjectListByAnyColumn("ProcurementPackageDtl",
								"procurementPackageMst.id",
								procurementPackageMstDb.getId() + "");
				for (ProcurementPackageDtl procurementPackageDtl : procPackDtlList) {
					AppPurchaseDtl appPurchaseDtl = new AppPurchaseDtl(null,
							procurementPackageDtl, appPurchaseMst, 0.0, true,
							null, userName, now);
					commonService.saveOrUpdateModelObjectToDB(appPurchaseDtl);
				}
				boolean flag = saveAppProcApprvHierHist(department, authUser,
						appPurchaseMst);
				if (flag) {

					return new ModelAndView(
							"redirect:/app/purchase/show.do?id="
									+ appPurchaseMst.getId());

				} else {

					return new ModelAndView(
							"redirect:/app/purchase/show.do?id="
									+ appPurchaseMst.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean saveAppProcApprvHierHist(Departments department,
			AuthUser authUser, AppPurchaseMst appPurchaseMst) {
		try {
			List<ApprovalHierarchy> appHierList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"ApprovalHierarchy", "operationName",
							PROCUREMENT_PROCESS, "stateCode", ASSENDING);

			ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
			if (appHierList.size() > 0) {
				approvalHierarchy = appHierList.get(0);
			}

			AppPurchaseApprovalHierarchyHistory approvalHierarchyHistory = new AppPurchaseApprovalHierarchyHistory(
					null, PROCUREMENT_PROCESS, appPurchaseMst, authUser,
					department, approvalHierarchy.getStateCode(),
					approvalHierarchy.getStateName(), null, OPEN,
					appPurchaseMst.getRemarks(),
					approvalHierarchy.getApprovalHeader(),
					authUser.getUserid(), new Date());
			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/saveProcurementRequestForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveProcurementRequestForm(
			AppPurchaseMstDtl appPurchaseMstDtl,
			@RequestParam("specificationDoc") MultipartFile specificationDoc,
			@RequestParam("requisitionAppDoc") MultipartFile requisitionAppDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							appPurchaseMstDtl.getId() + "");

			String specificationDocFilePath = "";
			String requisitionAppDocFilePath = "";

			specificationDocFilePath = this.saveFileToDrive(specificationDoc);
			requisitionAppDocFilePath = this.saveFileToDrive(requisitionAppDoc);

			List<AppPurchaseMst> appProcMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByTwoColumn("AppPurchaseMst",
							"descoSession.id", appPurchaseMstDtl.getId() + "",
							"procurementPackageMst.annexureNo",
							appPurchaseMstDtl.getAnnexureNo());
			if (appProcMstList.size() > 0 && appProcMstList != null) {
				return this.showProcurementForm(appProcMstList.get(0));
			} else {

				List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByTwoColumn("ProcurementPackageMst",
								"descoSession.id", appPurchaseMstDtl.getId()
										+ "", "annexureNo",
								appPurchaseMstDtl.getAnnexureNo());
				ProcurementPackageMst procurementPackageMstDb = procurementPackageMstList
						.get(0);
				AppPurchaseMst appProcMst = new AppPurchaseMst();
				appProcMst.setProcurementPackageMst(procurementPackageMstDb);
				appProcMst.setDescoSession(descoSession);
				// appProcMst.setRequestedComments(appPurchaseMstDtl.getRequestedComments());
				appProcMst.setSpecificationDoc(specificationDocFilePath);
				appProcMst.setRequisitionAppDoc(requisitionAppDocFilePath);

				appProcMst.setRequisitionRef(appPurchaseMstDtl
						.getRequisitionRef());
				appProcMst.setRemarks(appPurchaseMstDtl.getComments());
				appProcMst.setProjectName(appPurchaseMstDtl.getProjectName());
				appProcMst.setTenderNo(appPurchaseMstDtl.getTenderNo());

				appProcMst.setCreatedBy(userName);
				appProcMst.setCreatedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appProcMst);

				int maxId = (Integer) commonService
						.getMaxValueByObjectAndColumn("AppPurchaseMst", "id");

				AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
						.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id",
								maxId + "");

				List<ProcurementPackageDtl> procPackDtlList = (List<ProcurementPackageDtl>) (Object) commonService
						.getObjectListByAnyColumn("ProcurementPackageDtl",
								"procurementPackageMst.id",
								procurementPackageMstDb.getId() + "");
				for (ProcurementPackageDtl procurementPackageDtl : procPackDtlList) {
					AppPurchaseDtl appPurchaseDtl = new AppPurchaseDtl(null,
							procurementPackageDtl, appPurchaseMstDb, 0.0, true,
							null, userName, now);
					commonService.saveOrUpdateModelObjectToDB(appPurchaseDtl);
				}
				boolean flag = saveAppProcRequestedApprvHierHist(department,
						authUser, appPurchaseMstDb, appPurchaseMstDtl);
				if (flag) {
					AppPurchaseMst appPurchaseMst = new AppPurchaseMst();
					appPurchaseMst.setId(appPurchaseMstDb.getDescoSession()
							.getId());
					return this.getAppPurchaseList(appPurchaseMst);
					// return this.showProcurementForm(appPurchaseMstDb);
				} else {
					model.put("errorMsg", "Unable to save Purchase History.");
					return new ModelAndView("procurement/errorProc", model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean saveAppProcRequestedApprvHierHist(Departments department,
			AuthUser authUser, AppPurchaseMst appPurchaseMst,
			AppPurchaseMstDtl appPurchaseMstDtl) {
		try {
			List<ApprovalHierarchy> appHierList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"ApprovalHierarchy", "operationName",
							PROCUREMENT_PROCESS, "stateCode", ASSENDING);

			ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
			if (appHierList.size() > 0) {
				approvalHierarchy = appHierList.get(0);
			}

			AppPurchaseApprovalHierarchyHistory approvalHierarchyHistory = new AppPurchaseApprovalHierarchyHistory(
					null, PROCUREMENT_PROCESS, appPurchaseMst, authUser,
					department, approvalHierarchy.getStateCode(),
					approvalHierarchy.getStateName(), null, DONE,
					appPurchaseMst.getRemarks(),
					approvalHierarchy.getApprovalHeader(),
					authUser.getUserid(), new Date());
			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			String nextUserName = appPurchaseMstDtl.getUserid();
			AuthUser nextAuthUser = userService
					.getAuthUserByUserId(nextUserName);
			Departments nextDepartment = departmentsService
					.getDepartmentByDeptId(nextAuthUser.getDeptId());

			ApprovalHierarchy nextApprovalHierarchy = new ApprovalHierarchy();
			if (appHierList.size() > 0) {
				nextApprovalHierarchy = appHierList.get(1);
			}

			AppPurchaseApprovalHierarchyHistory approvalHierarchyHistoryAfterSubmit = new AppPurchaseApprovalHierarchyHistory(
					null, PROCUREMENT_PROCESS, appPurchaseMst, nextAuthUser,
					nextDepartment, nextApprovalHierarchy.getStateCode(),
					nextApprovalHierarchy.getStateName(), null, OPEN,
					nextApprovalHierarchy.getRemarks(),
					nextApprovalHierarchy.getApprovalHeader(),
					authUser.getUserid(), new Date());
			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryAfterSubmit);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@RequestMapping(value = "/app/purchase/show.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showAppPurchase(AppPurchaseMst bean) {
		AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
				.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id",
						bean.getId() + "");
		return showProcurementForm(appPurchaseMstDb);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/showProcurementForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showProcurementForm(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<ApprovalHierarchy> approvalHierarchyList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumn("ApprovalHierarchy",
							"operationName", PROCUREMENT_PROCESS);
			List<ProcurementProcessCommittee> procProcessCommitteeList = (List<ProcurementProcessCommittee>) (Object) commonService
					.getAllObjectList("ProcurementProcessCommittee");

			List<AppPurchaseApprovalHierarchyHistory> apahh = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"AppPurchaseApprovalHierarchyHistory",
							"authUser.userid", userName, "appPurchaseMst.id",
							appPurchaseMst.getId() + "", "status", OPEN);

			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id",
							appPurchaseMst.getId() + "");
			List<AppPurchaseDtl> appPurchaseDtlList = (List<AppPurchaseDtl>) (Object) commonService
					.getObjectListByAnyColumn("AppPurchaseDtl",
							"appPurchaseMst.id", appPurchaseMstDb.getId() + "");
			model.put("appPurchaseMst", appPurchaseMstDb);
			model.put("appPurchaseDtlList", appPurchaseDtlList);
			model.put("department", department);
			if (apahh.size() > 0) {
				model.put("stateCode", apahh.get(0).getStateCode());
			} else {
				model.put("stateCode", "100");
			}
			model.put("authUser", authUser);

			List<AppPurchaseApprovalHierarchyHistory> hisList = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory",
							"appPurchaseMst.id", "" + appPurchaseMst.getId(),
							"active", "1");
			String stateCodes = "";
			for (AppPurchaseApprovalHierarchyHistory h : hisList) {
				stateCodes += h.getStateCode();
			}
			
			Map<String, ApprovalHierarchy > pHistMap=new HashMap<String, ApprovalHierarchy>();
			for (ApprovalHierarchy a : approvalHierarchyList) {
				String sc = "" + a.getStateCode();
				if (!stateCodes.contains(sc)) {
					pHistMap.put(sc, a);
				}
			}
			
			List<Integer> codeList=new ArrayList<Integer>();			
			for (String key: pHistMap.keySet()) {			    
			    int code=Integer.parseInt(key);
			    codeList.add(code);
			}		    
		    Collections.sort(codeList);
		    
		    List<ApprovalHierarchy> nextHierarchyList =new ArrayList<ApprovalHierarchy>();
			for (int key: codeList) {			    
			    nextHierarchyList.add( pHistMap.get(key+""));
			}
			
			model.put("procProcessCommitteeList", procProcessCommitteeList);
			// model.put("approvalHierarchyList", approvalHierarchyList);
			model.put("approvalHierarchyList", nextHierarchyList);
			model.put("descoKhathList", descoKhathList);

			return new ModelAndView("procurement/app/appPurchaseShow", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// App purchase show committee

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/appPurchaseShowCommittee.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView appPurchaseShowCommittee(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<ApprovalHierarchy> approvalHierarchyList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumn("ApprovalHierarchy",
							"operationName", PROCUREMENT_PROCESS);
			List<ProcurementProcessCommittee> procProcessCommitteeList = (List<ProcurementProcessCommittee>) (Object) commonService
					.getAllObjectList("ProcurementProcessCommittee");

			List<AppPurchaseApprovalHierarchyHistory> apahh = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"AppPurchaseApprovalHierarchyHistory",
							"authUser.userid", userName, "appPurchaseMst.id",
							appPurchaseMst.getId() + "", "status", OPEN);

			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id",
							appPurchaseMst.getId() + "");
			List<AppPurchaseDtl> appPurchaseDtlList = (List<AppPurchaseDtl>) (Object) commonService
					.getObjectListByAnyColumn("AppPurchaseDtl",
							"appPurchaseMst.id", appPurchaseMstDb.getId() + "");
			model.put("appPurchaseMst", appPurchaseMstDb);
			model.put("appPurchaseDtlList", appPurchaseDtlList);
			model.put("department", department);
			if (apahh.size() > 0) {
				model.put("stateCode", apahh.get(0).getStateCode());
			} else {
				model.put("stateCode", "100");
			}
			model.put("authUser", authUser);
			
			List<AppPurchaseApprovalHierarchyHistory> hisList = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory",
							"appPurchaseMst.id", "" + appPurchaseMst.getId(),
							"active", "1");
			String stateCodes = "";
			for (AppPurchaseApprovalHierarchyHistory h : hisList) {
				stateCodes += h.getStateCode();
			}
			
			Map<String, ApprovalHierarchy > pHistMap=new HashMap<String, ApprovalHierarchy>();
			for (ApprovalHierarchy a : approvalHierarchyList) {
				String sc = "" + a.getStateCode();
				if (!stateCodes.contains(sc)) {
					pHistMap.put(sc, a);
				}
			}
			
			List<Integer> codeList=new ArrayList<Integer>();			
			for (String key: pHistMap.keySet()) {			    
			    int code=Integer.parseInt(key);
			    codeList.add(code);
			}		    
		    Collections.sort(codeList);
		    
		    List<ApprovalHierarchy> nextHierarchyList =new ArrayList<ApprovalHierarchy>();
			for (int key: codeList) {			    
			    nextHierarchyList.add( pHistMap.get(key+""));
			}
			

			model.put("procProcessCommitteeList", procProcessCommitteeList);
			model.put("approvalHierarchyList", nextHierarchyList);
			// model.put("approvalHierarchyList", approvalHierarchyList);
			model.put("descoKhathList", descoKhathList);

			return new ModelAndView("procurement/app/appPurchaseShowCommittee",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView showProcurementFormOLD(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id",
							appPurchaseMst.getId() + "");
			List<AppPurchaseDtl> appPurchaseDtlList = (List<AppPurchaseDtl>) (Object) commonService
					.getObjectListByAnyColumn("AppPurchaseDtl",
							"appPurchaseMst.id", appPurchaseMstDb.getId() + "");
			model.put("appPurchaseMst", appPurchaseMstDb);
			model.put("appPurchaseDtlList", appPurchaseDtlList);
			model.put("department", department);
			model.put("authUser", authUser);
			return new ModelAndView("procurement/app/appPurchaseShow", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
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
							+ "procurement");
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/getListForm.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAppPurchaseListFrom() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			model.put("descoSessionList", descoSessionList);
			return new ModelAndView("procurement/app/loadPurchaseListForm",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/loadPurchaseListFormForCU.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView loadPurchaseListFormForCU() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSessionList = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSessionList", descoSessionList);
			return new ModelAndView(
					"procurement/app/loadPurchaseListFormForCU", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/getProcurementList.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getProcurementList(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							appPurchaseMst.getId() + "");

			List<AppPurchaseMst> appProcMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumn("AppPurchaseMst",
							"descoSession.id", appPurchaseMst.getId() + "");
			model.put("descoSession", descoSession);
			model.put("department", department);
			model.put("authUser", authUser);
			model.put("appProcMstList", appProcMstList);
			return new ModelAndView("procurement/app/purchaseListFromApp",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/getAppPurchaseList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAppPurchaseList(AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<AppPurchaseApprovalHierarchyHistory> appPurchaseApprovalHierarchyHistoryList = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory", "status",
							OPEN, "authUser.userid", userName);

			List<String> appProcMstIdList = new ArrayList<String>();
			for (AppPurchaseApprovalHierarchyHistory appPurchaseApprovalHierarchyHistory : appPurchaseApprovalHierarchyHistoryList) {
				appProcMstIdList.add(appPurchaseApprovalHierarchyHistory
						.getAppPurchaseMst().getId() + "");
			}

			List<AppPurchaseMst> appProcMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumnValueList("AppPurchaseMst", "id",
							appProcMstIdList);
			// model.put("descoSession", descoSession);
			model.put("department", department);
			model.put("authUser", authUser);
			model.put("appProcMstList", appProcMstList);
			return new ModelAndView("procurement/app/purchaseListFromApp",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/committee/getAppPurchaseList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAppPurchaseListByCommittee(
			AppPurchaseMst appPurchaseMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<AppPurchaseApprovalHierarchyHistory> appPurchaseApprovalHierarchyHistoryList = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory", "status",
							OPEN, "authUser.userid", userName);

			List<String> appProcMstIdList = new ArrayList<String>();
			for (AppPurchaseApprovalHierarchyHistory appPurchaseApprovalHierarchyHistory : appPurchaseApprovalHierarchyHistoryList) {
				appProcMstIdList.add(appPurchaseApprovalHierarchyHistory
						.getAppPurchaseMst().getId() + "");
			}

			List<AppPurchaseMst> appProcMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumnValueList("AppPurchaseMst", "id",
							appProcMstIdList);
			// model.put("descoSession", descoSession);
			model.put("department", department);
			model.put("authUser", authUser);
			model.put("appProcMstList", appProcMstList);
			return new ModelAndView(
					"procurement/app/purchaseListFromAppCommittee", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@RequestMapping(value = "/app/purchase/download.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getDemandNoteFileGM(HttpServletRequest request,
			HttpServletResponse httpServletResponse,
			AppPurchaseMstDtl appPurchaseMstDtl) throws Exception {
		return this.getDemandNoteFile(request, httpServletResponse,
				appPurchaseMstDtl);
	}

	@RequestMapping(value = "/app/purchase/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getDemandNoteFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse,
			AppPurchaseMstDtl appPurchaseMstDtl) throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = appPurchaseMstDtl.getDownloadDocFile();
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
			filePath = URLEncoder
					.encode("procurement_doc." + extension, "UTF8");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="
					+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("procurement_doc." + extension,
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/sendToPurchaseForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView sendToPurchaseForm(AppPurchaseMstDtl appPurchaseMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			Integer id = appPurchaseMstDtl.getId();
			String nextStateCode = appPurchaseMstDtl.getStateCode();
			String nextUserId = appPurchaseMstDtl.getUserid();

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id", id
							+ "");

			List<AppPurchaseApprovalHierarchyHistory> appPurAppHierHist = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory", "status",
							OPEN, "appPurchaseMst.id", id + "");
			AppPurchaseApprovalHierarchyHistory currentAppPurAppHier = appPurAppHierHist
					.get(0);

			boolean flag = this.sendToAppProcApprvHierHist(authUser,
					department, appPurchaseMstDb, currentAppPurAppHier,
					nextStateCode, nextUserId);
			if (flag) {
				AppPurchaseMst appPurchaseMst = new AppPurchaseMst();
				appPurchaseMst
						.setId(appPurchaseMstDb.getDescoSession().getId());
				return this.getAppPurchaseList(appPurchaseMst);
			} else {
				return this.showProcurementForm(appPurchaseMstDb);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean sendToAppProcApprvHierHist(AuthUser loginAuthUser,
			Departments loginUserDepartment, AppPurchaseMst appPurchaseMst,
			AppPurchaseApprovalHierarchyHistory currentAppPurAppHier,
			String nextStateCode, String nextUserId) {
		try {

			Date now = new Date();
			String currentUserName = loginAuthUser.getUserid();
			{
				currentAppPurAppHier.setCreatedBy(currentUserName);
				currentAppPurAppHier.setCreatedDate(now);
				currentAppPurAppHier.setModifiedBy(currentUserName);
				currentAppPurAppHier.setModifiedDate(now);
				currentAppPurAppHier.setStatus(DONE);
				commonService.saveOrUpdateModelObjectToDB(currentAppPurAppHier);
			}

			if (nextStateCode != null) {
				AuthUser nextAuthUser = userService
						.getAuthUserByUserId(nextUserId);

				Departments nextUserDepartment = departmentsService
						.getDepartmentByDeptId(nextAuthUser.getDeptId());

				List<ApprovalHierarchy> appHierList = (List<ApprovalHierarchy>) (Object) commonService
						.getObjectListByTwoColumn("ApprovalHierarchy",
								"operationName", PROCUREMENT_PROCESS,
								"stateCode", nextStateCode);
				ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
				if (appHierList.size() > 0) {
					approvalHierarchy = appHierList.get(0);
				}

				AppPurchaseApprovalHierarchyHistory approvalHierarchyHistory = new AppPurchaseApprovalHierarchyHistory(
						null, PROCUREMENT_PROCESS, appPurchaseMst,
						nextAuthUser, nextUserDepartment,
						approvalHierarchy.getStateCode(),
						approvalHierarchy.getStateName(), null, OPEN,
						appPurchaseMst.getRemarks(),
						approvalHierarchy.getApprovalHeader(), currentUserName,
						now);
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/updateProcurementForm.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView updateProcurementForm(
			AppPurchaseMstDtl appPurchaseMstDtl,
			@RequestParam("reviewDoc") MultipartFile reviewDoc,
			@RequestParam("approvedDoc") MultipartFile approvedDoc,
			@RequestParam("workOrderDoc") MultipartFile workOrderDoc,
			@RequestParam("tendRfqPubDoc") MultipartFile tendRfqPubDoc,
			@RequestParam("tendRfqDoc") MultipartFile tendRfqDoc,
			@RequestParam("evaluationDoc") MultipartFile evaluationDoc,
			@RequestParam("pgDoc") MultipartFile pgDoc,
			@RequestParam("noaDoc") MultipartFile noaDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			Integer id = appPurchaseMstDtl.getId();
			String nextStateCode = appPurchaseMstDtl.getStateCode();
			String nextUserId = appPurchaseMstDtl.getUserid();

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id", id
							+ "");

			String reviewDocFilePath = "";
			String approvedDocFilePath = "";

			String workOrderDocFilePath = "";
			String tendRfqPubDocFilePath = "";

			String tendRfqDocFilePath = "";
			String evaluationDocFilePath = "";

			String pgDocFilePath = "";
			String noaDocFilePath = "";

			List<AppPurchaseApprovalHierarchyHistory> appPurAppHierHist = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"AppPurchaseApprovalHierarchyHistory", "status",
							OPEN, "appPurchaseMst.id", id + "");
			AppPurchaseApprovalHierarchyHistory currentAppPurAppHier = appPurAppHierHist
					.get(0);

			if (currentAppPurAppHier.getStateCode() == 200) {
				reviewDocFilePath = this.saveFileToDrive(reviewDoc);
				appPurchaseMstDb.setReviewDoc(reviewDocFilePath);
				appPurchaseMstDb.setReviewDate(appPurchaseMstDtl
						.getReviewDate());
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 300) {
				approvedDocFilePath = this.saveFileToDrive(approvedDoc);
				appPurchaseMstDb.setApprovedBy(appPurchaseMstDtl
						.getApprovedBy());
				appPurchaseMstDb.setApprovedDate(appPurchaseMstDtl
						.getApprovedDate());
				appPurchaseMstDb.setApprovedDoc(approvedDocFilePath);
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 400) {
				tendRfqPubDocFilePath = this.saveFileToDrive(tendRfqPubDoc);
				tendRfqDocFilePath = this.saveFileToDrive(tendRfqDoc);
				appPurchaseMstDb.setTendRfqPubDate(appPurchaseMstDtl
						.getTendRfqPubDate());
				appPurchaseMstDb.setTendRfqExtentionDate(appPurchaseMstDtl
						.getTendRfqExtentionDate());
				appPurchaseMstDb.setTendRfqSubDate(appPurchaseMstDtl
						.getTendRfqSubDate());
				appPurchaseMstDb.setTendRfqPubDoc(tendRfqPubDocFilePath);
				appPurchaseMstDb.setTendRfqDoc(tendRfqDocFilePath);
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 500) {
				evaluationDocFilePath = this.saveFileToDrive(evaluationDoc);
				appPurchaseMstDb.setEvaluationDoc(evaluationDocFilePath);
				appPurchaseMstDb.setTendOpeningDate(appPurchaseMstDtl
						.getTendOpeningDate());
				appPurchaseMstDb.setTendRfqEvalDate(appPurchaseMstDtl
						.getTendRfqEvalDate());
				appPurchaseMstDb.setNumberOfSubmission(appPurchaseMstDtl
						.getNumberOfSubmission());
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 600) {
				noaDocFilePath = this.saveFileToDrive(noaDoc);
				appPurchaseMstDb.setNoaDoc(noaDocFilePath);
				appPurchaseMstDb.setNoaDate(appPurchaseMstDtl.getNoaDate());
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 700) {
				pgDocFilePath = this.saveFileToDrive(pgDoc);
				appPurchaseMstDb.setExpiredDate(appPurchaseMstDtl
						.getExpiredDate());
				appPurchaseMstDb.setPgDoc(pgDocFilePath);
				appPurchaseMstDb.setPgAmount(appPurchaseMstDtl.getPgAmount());
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}
			if (currentAppPurAppHier.getStateCode() == 800) {
				workOrderDocFilePath = this.saveFileToDrive(workOrderDoc);
				appPurchaseMstDb.setWorkOrderDoc(workOrderDocFilePath);
				appPurchaseMstDb.setWorkOrderDate(appPurchaseMstDtl
						.getWorkOrderDate());
				appPurchaseMstDb.setWorkOrderNo(appPurchaseMstDtl
						.getWorkOrderNo());
				appPurchaseMstDb.setContractorName(appPurchaseMstDtl
						.getContractorName());
				appPurchaseMstDb.setContractAmount(appPurchaseMstDtl
						.getContractAmount());
				appPurchaseMstDb.setModifiedBy(userName);
				appPurchaseMstDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);
			}

			boolean flag = this.sendToAppProcApprvHierHist(authUser,
					department, /* nextDepartment, nextAuthUser, */
					appPurchaseMstDb, currentAppPurAppHier, nextStateCode,
					nextUserId);
			if (flag) {
				AppPurchaseMst appPurchaseMst = new AppPurchaseMst();
				appPurchaseMst
						.setId(appPurchaseMstDb.getDescoSession().getId());
				return this.getAppPurchaseList(appPurchaseMst);
			} else {
				return this.showProcurementForm(appPurchaseMstDb);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/purchase/deptWiseUsers.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getDeptWiseUsers(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			Departments departments = gson.fromJson(json, Departments.class);

			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "deptId",
							departments.getDeptId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(authUserList);

		} else {
			Thread.sleep(2 * 1000);
		}

		return toJson;
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/proc/app/status/purchaseList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showAppPurchaseList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<AppPurchaseApprovalHierarchyHistory> appPurAppHierHist = (List<AppPurchaseApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"AppPurchaseApprovalHierarchyHistory", "status",
							OPEN);
			model.put("list", appPurAppHierHist);

			return new ModelAndView("procurement/package/purchaseList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	// Added by: Ihteshamul Alam, IBCS
	@RequestMapping(value = "/app/purchase/checkWorkOrderNo.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkAnnexureNo(AppPurchaseMst appPurchaseMst) {
		String response = "";
		if (appPurchaseMst.getWorkOrderNo().equalsIgnoreCase("")) {
			response = "blank";
		} else {
			AppPurchaseMst appPurMst = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst",
							"workOrderNo", appPurchaseMst.getWorkOrderNo());
			if (appPurMst == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}
}
