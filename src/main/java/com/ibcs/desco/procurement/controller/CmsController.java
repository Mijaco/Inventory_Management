package com.ibcs.desco.procurement.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.AppPurchaseApprovalHierarchyHistory;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CMSApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.model.AppPurchaseMst;
import com.ibcs.desco.procurement.model.ContractManagement;
import com.ibcs.desco.procurement.model.ContractPayment;
import com.ibcs.desco.procurement.model.ProcurementProcessCommittee;
import com.ibcs.desco.procurement.service.DemandNoteService;

@Controller
@PropertySource("classpath:common.properties")
public class CmsController extends Constrants {

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
	@RequestMapping(value = "/cms/contractInfo.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractInfo() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<AppPurchaseMst> appPurchaseMstList = (List<AppPurchaseMst>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullAndOneNotNull(
							"AppPurchaseMst", "cmsFlag", "workOrderNo");

			model.put("department", department);
			model.put("authUser", authUser);
			model.put("appPurchaseMstList", appPurchaseMstList);

			return new ModelAndView("procurement/cms/contractInfo", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@RequestMapping(value = "/cms/saveContractInfo.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveContractInfo(ContractManagement bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			AppPurchaseMst appPurchaseMstDb = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id", bean
							.getId().toString());

			DescoKhath ds = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathName",
							appPurchaseMstDb.getProjectName());

			ContractManagement contractManagement = new ContractManagement();
			contractManagement.setAppPurchaseMst(appPurchaseMstDb);
			contractManagement.setCreatedBy(userName);
			contractManagement.setCreatedDate(new Date());

			contractManagement.setContractValidityMonth(bean
					.getContractValidityMonth());
			contractManagement.setContractExpiredDate(bean
					.getContractExpiredDate());
			contractManagement.setContractExtendedDate(bean
					.getContractExpiredDate());

			contractManagement.setContractNo(appPurchaseMstDb.getWorkOrderNo());
			contractManagement.setContractDate(appPurchaseMstDb
					.getWorkOrderDate());
			contractManagement.setContractDoc(appPurchaseMstDb
					.getWorkOrderDoc());
			contractManagement
					.setContractorAddress(bean.getContractorAddress());
			contractManagement.setContractorName(appPurchaseMstDb
					.getContractorName());

			contractManagement.setTenderNo(appPurchaseMstDb.getTenderNo());
			contractManagement.setDescoKhath(ds);
			contractManagement.setPgDate(bean.getPgDate());
			contractManagement.setPgAmount(appPurchaseMstDb.getPgAmount());

			commonService.saveOrUpdateModelObjectToDB(contractManagement);

			// set cms_flag in AppPurchaseMst
			appPurchaseMstDb.setCmsFlag(CONFIRMED);
			commonService.saveOrUpdateModelObjectToDB(appPurchaseMstDb);

			int maxId = (Integer) commonService.getMaxValueByObjectAndColumn(
					"ContractManagement", "id");

			ContractManagement contractManagementDb = (ContractManagement) commonService
					.getAnObjectByAnyUniqueColumn("ContractManagement", "id",
							maxId + "");

			// return this.contractManagementList();
			boolean flag = this.saveCMSApprvHierHist(department, authUser,
					contractManagementDb);
			if (flag) {
				return new ModelAndView(
						"redirect:/cms/contractManagementList.do");
			} else {
				return new ModelAndView("procurement/errorProc", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean saveCMSApprvHierHist(Departments department,
			AuthUser authUser, ContractManagement contractManagement) {
		try {
			List<ApprovalHierarchy> appHierList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"ApprovalHierarchy", "operationName",
							CONTRACT_MANAGEMENT_PROCESS, "stateCode", ASSENDING);

			ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
			if (appHierList.size() > 0) {
				approvalHierarchy = appHierList.get(0);
			}

			CMSApprovalHierarchyHistory approvalHierarchyHistory = new CMSApprovalHierarchyHistory(
					null, CONTRACT_MANAGEMENT_PROCESS, contractManagement,
					authUser, department, approvalHierarchy.getStateCode(),
					approvalHierarchy.getStateName(), null, OPEN,
					contractManagement.getJustification(),
					approvalHierarchy.getApprovalHeader(),
					authUser.getUserid(), new Date());
			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/contractArchive.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractArchive() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<ContractManagement> contractManagementList = (List<ContractManagement>) (Object) commonService
					.getObjectListByAnyColumn("ContractManagement",
							"confirmFlag", CONFIRMED);

			model.put("contractManagementList", contractManagementList);

			return new ModelAndView("procurement/cms/contractArchives", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/contractManagementList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractManagementList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<CMSApprovalHierarchyHistory> cmsApprovalHierarchyHistoryList = (List<CMSApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn("CMSApprovalHierarchyHistory",
							"status", OPEN, "authUser.userid", userName);

			List<String> cmsIdList = new ArrayList<String>();
			for (CMSApprovalHierarchyHistory cmsApprovalHierarchyHistory : cmsApprovalHierarchyHistoryList) {
				cmsIdList.add(cmsApprovalHierarchyHistory
						.getContractManagement().getId().toString());
			}
			List<ContractManagement> contractManagementList = (List<ContractManagement>) (Object) commonService
					.getObjectListByAnyColumnValueList("ContractManagement",
							"id", cmsIdList);

			if (cmsApprovalHierarchyHistoryList.size() > 0) {
				model.put("stateCode", cmsApprovalHierarchyHistoryList.get(0)
						.getStateCode());
			}

			model.put("department", department);
			model.put("authUser", authUser);
			model.put("contractManagementList", contractManagementList);

			return new ModelAndView("procurement/cms/contractList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/committee/contractList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractListForCommittee() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<CMSApprovalHierarchyHistory> cmsApprovalHierarchyHistoryList = (List<CMSApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn("CMSApprovalHierarchyHistory",
							"status", OPEN, "authUser.userid", userName);

			List<String> cmsIdList = new ArrayList<String>();
			for (CMSApprovalHierarchyHistory cmsApprovalHierarchyHistory : cmsApprovalHierarchyHistoryList) {
				cmsIdList.add(cmsApprovalHierarchyHistory
						.getContractManagement().getId().toString());
			}
			List<ContractManagement> contractManagementList = (List<ContractManagement>) (Object) commonService
					.getObjectListByAnyColumnValueList("ContractManagement",
							"id", cmsIdList);

			model.put("department", department);
			model.put("authUser", authUser);
			model.put("contractManagementList", contractManagementList);
			if (cmsApprovalHierarchyHistoryList.size() > 0) {
				model.put("stateCode", cmsApprovalHierarchyHistoryList.get(0)
						.getStateCode());
			}

			return new ModelAndView("procurement/committee/contractList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/cms/loadContractDetails.do", method = RequestMethod.POST)
	public String loadContractDetails(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AppPurchaseMst bean = gson.fromJson(json, AppPurchaseMst.class);

			AppPurchaseMst appMstDB = (AppPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("AppPurchaseMst", "id", bean
							.getId().toString());

			toJson = ow.writeValueAsString(appMstDB);
		} else {
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/contractShow.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractShow(ContractManagement bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			ContractManagement cmsDB = (ContractManagement) commonService
					.getAnObjectByAnyUniqueColumn("ContractManagement", "id",
							bean.getId().toString());
			List<ApprovalHierarchy> approvalHierarchyList = (List<ApprovalHierarchy>) (Object) commonService
					.getObjectListByAnyColumn("ApprovalHierarchy",
							"operationName", CONTRACT_MANAGEMENT_PROCESS);

			List<ProcurementProcessCommittee> committeeList = (List<ProcurementProcessCommittee>) (Object) commonService
					.getAllObjectList("ProcurementProcessCommittee");

			List<CMSApprovalHierarchyHistory> apahh = (List<CMSApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn("CMSApprovalHierarchyHistory",
							"authUser.userid", userName,
							"contractManagement.id", cmsDB.getId() + "",
							"status", OPEN);
			if (apahh.size() > 0) {
				model.put("stateCode", apahh.get(0).getStateCode());
			} else {
				model.put("stateCode", "100");
			}
			
			List<CMSApprovalHierarchyHistory> hisList = (List<CMSApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn(
							"CMSApprovalHierarchyHistory",
							"contractManagement.id", cmsDB.getId() + "",
							"active", "1");
			String stateCodes = "";
			for (CMSApprovalHierarchyHistory h : hisList) {
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

			model.put("cms", cmsDB);
			model.put("cmsHistory", apahh.get(0));
			model.put("committeeList", committeeList);
			// model.put("approvalHierarchyList", approvalHierarchyList);
			model.put("approvalHierarchyList", nextHierarchyList);

			String code = bean.getStateCode();

			return new ModelAndView("procurement/cms/contractShow" + code,
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/contractArchiveShow.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractArchiveShow(ContractManagement bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ContractPayment> paymentList = ( List<ContractPayment> )  ( Object )  commonService
					.getObjectListByAnyColumn( "ContractPayment", "contractManagement.id",
							bean.getId().toString() );

			model.put("paymentList", paymentList);
			
			return new ModelAndView("procurement/cms/contractArchiveShow",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cms/updateContractManagementByStateCode.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView updateContractManagement(
			ContractManagement contractManagement,
			@RequestParam("refDoc") MultipartFile refDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			String id = contractManagement.getId().toString();
			String nextStateCode = contractManagement.getStateCode();
			String nextUserId = contractManagement.getUserid();

			ContractManagement contractManagementDb = (ContractManagement) commonService
					.getAnObjectByAnyUniqueColumn("ContractManagement", "id",
							id);

			String refDocFilePath = "";

			List<CMSApprovalHierarchyHistory> cmsAppHierHist = (List<CMSApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByTwoColumn("CMSApprovalHierarchyHistory",
							"status", OPEN, "contractManagement.id", id + "");
			CMSApprovalHierarchyHistory currentCMSAppHier = cmsAppHierHist
					.get(0);

			if (currentCMSAppHier.getStateCode() == 200) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setDrwApprovalCopy(refDocFilePath);
				contractManagementDb.setDrwApprovalDate(contractManagement
						.getDrwApprovalDate());
				contractManagementDb.setDrwApprovalRemarks(contractManagement
						.getDrwApprovalRemarks());

				contractManagementDb.setModifiedBy(userName);
				contractManagementDb.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);

			} else if (currentCMSAppHier.getStateCode() == 300) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb
						.setPsiDate(contractManagement.getPsiDate());
				contractManagementDb.setPsiReport(refDocFilePath);
				contractManagementDb.setPsiRemarks(contractManagement
						.getPsiRemarks());
				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);
			} else if (currentCMSAppHier.getStateCode() == 400) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setPsiVerificationDate(contractManagement
						.getPsiVerificationDate());
				contractManagementDb.setPsiVerificationRef(refDocFilePath);
				contractManagementDb
						.setPsiVerificationRemarks(contractManagement
								.getPsiVerificationRemarks());
				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);

			} else if (currentCMSAppHier.getStateCode() == 500) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setPsiAcceptanceDate(contractManagement
						.getPsiAcceptanceDate());
				contractManagementDb.setPsiAcceptanceRef(refDocFilePath);
				contractManagementDb.setPsiAcceptanceRemarks(contractManagement
						.getPsiAcceptanceRemarks());
				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);
			} else if (currentCMSAppHier.getStateCode() == 600) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setChalanNo(contractManagement
						.getChalanNo());
				contractManagementDb.setGoodsReceivedDate(contractManagement
						.getGoodsReceivedDate());
				contractManagementDb.setChalanCopy(refDocFilePath);
				contractManagementDb.setGoodsReceivedRemarks(contractManagement
						.getGoodsReceivedRemarks());
				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);
			} else if (currentCMSAppHier.getStateCode() == 700) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setPliReportDate(contractManagement
						.getPliReportDate());
				contractManagementDb.setPliReportCopy(refDocFilePath);
				contractManagementDb.setPliReportRemarks(contractManagement
						.getPliReportRemarks());
				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);
			} else if (currentCMSAppHier.getStateCode() == 800) {
				refDocFilePath = this.saveFileToDrive(refDoc);

				contractManagementDb.setPliAcceptanceDate(contractManagement
						.getPliAcceptanceDate());
				contractManagementDb.setPliAcceptanceRef(refDocFilePath);
				contractManagementDb.setPliAcceptanceRemarks(contractManagement
						.getPliAcceptanceRemarks());

				contractManagementDb.setConfirmFlag(CONFIRMED);

				contractManagementDb.setModifiedBy(commonService
						.getAuthUserName());
				contractManagementDb.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(contractManagementDb);

				// Set this state is the last.
				nextStateCode = null;
			}

			boolean flag = this.sendToCmsApprvHierHist(authUser, department,
					contractManagementDb, currentCMSAppHier, nextStateCode,
					nextUserId);
			if (flag) {
				if (currentCMSAppHier.getStateCode() == 100
						|| currentCMSAppHier.getStateCode() == 500
						|| currentCMSAppHier.getStateCode() == 800) {
					return new ModelAndView(
							"redirect:/cms/contractManagementList.do");
				} else {
					return new ModelAndView(
							"redirect:/cms/committee/contractList.do");
				}

			} else {
				model.put("errorMsg",
						"Unable to execute : sendToCmsApprvHierHist Method");
				return new ModelAndView("procurement/errorProc", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean sendToCmsApprvHierHist(AuthUser loginAuthUser,
			Departments loginUserDepartment,
			ContractManagement contractManagement,
			CMSApprovalHierarchyHistory currentCMSAppHier,
			String nextStateCode, String nextUserId) {
		try {
			//
			Date now = new Date();
			String currentUserName = loginAuthUser.getUserid();
			{
				currentCMSAppHier.setCreatedBy(currentUserName);
				currentCMSAppHier.setCreatedDate(now);
				currentCMSAppHier.setModifiedBy(currentUserName);
				currentCMSAppHier.setModifiedDate(now);
				currentCMSAppHier.setStatus(DONE);
				commonService.saveOrUpdateModelObjectToDB(currentCMSAppHier);
			}

			if (nextStateCode != null) {
				AuthUser nextAuthUser = userService
						.getAuthUserByUserId(nextUserId);

				Departments nextUserDepartment = departmentsService
						.getDepartmentByDeptId(nextAuthUser.getDeptId());

				List<ApprovalHierarchy> appHierList = (List<ApprovalHierarchy>) (Object) commonService
						.getObjectListByTwoColumn("ApprovalHierarchy",
								"operationName", CONTRACT_MANAGEMENT_PROCESS,
								"stateCode", nextStateCode);
				ApprovalHierarchy approvalHierarchy = new ApprovalHierarchy();
				if (appHierList.size() > 0) {
					approvalHierarchy = appHierList.get(0);
				}

				CMSApprovalHierarchyHistory approvalHierarchyHistory = new CMSApprovalHierarchyHistory(
						null, CONTRACT_MANAGEMENT_PROCESS, contractManagement,
						nextAuthUser, nextUserDepartment,
						approvalHierarchy.getStateCode(),
						approvalHierarchy.getStateName(), null, OPEN,
						contractManagement.getRemarks(),
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
}
