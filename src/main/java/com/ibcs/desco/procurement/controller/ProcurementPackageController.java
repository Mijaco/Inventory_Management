package com.ibcs.desco.procurement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.AllLookUp;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.procurement.bean.ProcurementPackageMstDtl;
import com.ibcs.desco.procurement.model.ProcurementPackageDtl;
import com.ibcs.desco.procurement.model.ProcurementPackageMst;

@Controller
public class ProcurementPackageController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	UserService userService;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.demandNote.prefix}")
	private String demandNotePrefix;

	// Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/procurementPackageForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView procurementPackageForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<AllLookUp> sourceOfFund = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							SOURCE_OF_FUND);

			List<AllLookUp> approvingAuth = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							APPROVING_AUTH);

			List<AllLookUp> procurementMethod = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROCUREMENT_METHOD);

			/*
			 * List<AllLookUp> procurementPackageType = (List<AllLookUp>)
			 * (Object) commonService .getObjectListByAnyColumn("AllLookUp",
			 * "parentName", PROC_PACKAGE_TYPE);
			 */

			List<AllLookUp> procurementPackageName = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROC_PACKAGE_NAME);

			List<AllLookUp> procurementMedium = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROC_MEDIUM);

			List<DescoSession> descoSessions = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();
			// model.put("procurementPackageType", procurementPackageType);
			model.put("descoSessions", descoSessions);
			model.put("procurementPackageName", procurementPackageName);
			model.put("sourceOfFund", sourceOfFund);
			model.put("approvingAuth", approvingAuth);
			model.put("procurementMethod", procurementMethod);
			model.put("procurementMedium", procurementMedium);
			model.put("categoryList", categoryList);

			return new ModelAndView(
					"procurement/package/procurementPackageForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/procurementPackageMstInfoSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView procurementPackageMstInfoSave(
			ProcurementPackageMstDtl procurementPackageMstDtl,
			RedirectAttributes redirectAttributes) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							procurementPackageMstDtl.getSessionId() + "");

			Date now = new Date();

			ProcurementPackageMst procurementPackageMst = new ProcurementPackageMst(
					null, descoSession,
					procurementPackageMstDtl.getPackageName(),
					procurementPackageMstDtl.getProcMethod(),
					procurementPackageMstDtl.getProcType(),
					procurementPackageMstDtl.getProcurementMedium(),
					procurementPackageMstDtl.getSourceOfFund(),
					procurementPackageMstDtl.getApprovingAuth(),
					procurementPackageMstDtl.getEstimatedCost(),
					procurementPackageMstDtl.getReasonsOfProc(),
					procurementPackageMstDtl.getProcDescription(),
					procurementPackageMstDtl.getAnnexureNo(),
					procurementPackageMstDtl.getPrepDocInvTender(),
					procurementPackageMstDtl.getEvaluationOfTender(),
					procurementPackageMstDtl.getAwardOfContract(),
					procurementPackageMstDtl.getTentativeCompletion(),
					procurementPackageMstDtl.getCurrentSessionBudget(),
					procurementPackageMstDtl.getNextSessionBudget(), null,
					userName, now,
					procurementPackageMstDtl.getProcurementQty(),
					procurementPackageMstDtl.getPackageType(),
					procurementPackageMstDtl.getQtyFlag(), null);

			commonService.saveOrUpdateModelObjectToDB(procurementPackageMst);
			Integer maxTnxDtlId = (Integer) commonService
					.getMaxValueByObjectAndColumn("ProcurementPackageMst", "id");
			ProcurementPackageMst procurementPackageMstDb = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", maxTnxDtlId + "");

			this.saveProcurementPackageDtl(procurementPackageMstDb,
					procurementPackageMstDtl, userName, now);

			ModelAndView modelAndView = new ModelAndView(
					"redirect:/mps/procurementPackageShow.do");
			redirectAttributes.addFlashAttribute("procurementPackageMst",
					procurementPackageMstDb);
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/mps/procurementPackageShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView procurementPackageShowGet(
			@ModelAttribute("procurementPackageMst") ProcurementPackageMst procurementPackageMst) {
		int id = procurementPackageMst.getId();
		return procurementPackageShow(procurementPackageMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/procurementPackageShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView procurementPackageShow(
			ProcurementPackageMst procurementPackageMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = procurementPackageMst.getId();
			ProcurementPackageMst procurementPackageMstDb = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", id + "");

			String userrole = procurementPackageMstDb.getCreatedBy();
			AuthUser createBy = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userrole);
			procurementPackageMstDb.setCreatedBy(createBy.getName());

			List<ProcurementPackageDtl> procurementPackageList = (List<ProcurementPackageDtl>) (Object) commonService
					.getObjectListByAnyColumn("ProcurementPackageDtl",
							"procurementPackageMst.id", procurementPackageMstDb
									.getId().toString());
			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();

			List<DescoSession> descoSessions = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");

			List<AllLookUp> sourceOfFund = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							SOURCE_OF_FUND);

			List<AllLookUp> approvingAuth = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							APPROVING_AUTH);

			List<AllLookUp> procurementMethod = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROCUREMENT_METHOD);

			List<AllLookUp> procurementPackageName = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROC_PACKAGE_NAME);

			List<AllLookUp> procurementMedium = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName",
							PROC_MEDIUM);

			model.put("procurementPackageMstDb", procurementPackageMstDb);
			model.put("procurementPackageList", procurementPackageList);
			model.put("categoryList", categoryList);
			model.put("descoSessions", descoSessions);
			model.put("procurementPackageName", procurementPackageName);
			model.put("sourceOfFund", sourceOfFund);
			model.put("approvingAuth", approvingAuth);
			model.put("procurementMethod", procurementMethod);
			model.put("procurementMedium", procurementMedium);
			return new ModelAndView(
					"procurement/package/procurementPackageShow", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	private boolean saveProcurementPackageDtl(
			ProcurementPackageMst procurementPackageMst,
			ProcurementPackageMstDtl procurementPackageMstDtl, String userName,
			Date now) {

		boolean flag = false;
		List<String> itemCodeList = procurementPackageMstDtl.getItemCode();
		List<String> itemNameList = procurementPackageMstDtl.getItemName();
		List<String> unitList = procurementPackageMstDtl.getUnit();
		List<Double> qunatityList = procurementPackageMstDtl.getQunatity();
		List<Double> unitCostList = procurementPackageMstDtl.getUnitCost();
		List<Double> totalCostList = procurementPackageMstDtl.getTotalCost();

		for (int i = 0; i < qunatityList.size(); i++) {
			if (qunatityList.get(i) > 0) {
				ProcurementPackageDtl procurementPackageDtl = new com.ibcs.desco.procurement.model.ProcurementPackageDtl(
						null, itemCodeList.get(i), itemNameList.get(i),
						unitList.get(i), qunatityList.get(i),
						unitCostList.get(i), totalCostList.get(i),
						procurementPackageMst, null, userName, now);

				commonService
						.saveOrUpdateModelObjectToDB(procurementPackageDtl);
			}

			flag = true;
		}

		return flag;
	}
	
	
	@RequestMapping(value = "/mps/procurementPackageMstList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView draftAppListGET(ProcurementPackageMst bean) {
		return draftAppList(bean);
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/procurementPackageMstList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView draftAppList(ProcurementPackageMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<ProcurementPackageMst> procurementPackageMstList = null;
			if (bean.getPackageType().equalsIgnoreCase("")) {
				procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByAnyColumn("ProcurementPackageMst",
								"descoSession.id", bean.getId().toString());
			} else {
				procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByTwoColumn("ProcurementPackageMst",
								"descoSession.id", bean.getId().toString(),
								"packageType", bean.getPackageType());
			}

			DescoSession ds = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id", bean
							.getId().toString());
			model.put("procurementPackageMstList", procurementPackageMstList);
			model.put("descoSession", ds);
			return new ModelAndView("procurement/package/appList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	// appFormWithSession
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mps/appFormWithSession.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView demandNoteSummaryFormBySession() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSession", descoSession);
			return new ModelAndView("procurement/package/appFormWithSession",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/deletePackageDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deletePackageDtl(
			ProcurementPackageDtl procurementPackageDtl,
			RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = procurementPackageDtl.getId();
			ProcurementPackageDtl procurementPackageDtlDb = (ProcurementPackageDtl) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageDtl",
							"id", id + "");
			ProcurementPackageMst procurementPackageMst = procurementPackageDtlDb
					.getProcurementPackageMst();

			Double quantity = procurementPackageDtlDb.getQunatity();
			Double estimatedCost = procurementPackageDtlDb.getTotalCost();
			Double mQuantity = procurementPackageMst.getProcurementQty();
			Double mCost = procurementPackageMst.getEstimatedCost();
			if (procurementPackageMst.getQtyFlag().equalsIgnoreCase("1")) {
				procurementPackageMst.setProcurementQty(mQuantity - quantity);
			}
			procurementPackageMst.setEstimatedCost(mCost - estimatedCost);
			procurementPackageMst
					.setModifiedBy(commonService.getAuthUserName());
			procurementPackageMst.setModifiedDate(new Date());

			commonService.deleteAnObjectById("ProcurementPackageDtl", id);
			commonService.saveOrUpdateModelObjectToDB(procurementPackageMst);

			// return this.procurementPackageShow(procurementPackageMst);

			ModelAndView modelAndView = new ModelAndView(
					"redirect:/mps/procurementPackageShow.do");
			redirectAttributes.addFlashAttribute("procurementPackageMst",
					procurementPackageMst);
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/saveMultipleItems.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveMultipleItems(
			ProcurementPackageMstDtl procurementPackageMstDtl,
			RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = procurementPackageMstDtl.getId();
			ProcurementPackageMst procurementPackageMst = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", id + "");

			List<String> itemCodeList = procurementPackageMstDtl.getItemCode();

			List<String> itemNameList = procurementPackageMstDtl.getItemName();

			List<String> unitList = procurementPackageMstDtl.getUnit();

			List<Double> qunatityList = procurementPackageMstDtl.getQunatity();

			List<Double> unitCostList = procurementPackageMstDtl.getUnitCost();

			List<Double> totalCostList = procurementPackageMstDtl
					.getTotalCost();
			String userName = commonService.getAuthUserName();
			Date now = new Date();

			Double finalQuantity = 0.0, finalTotalCost = 0.0;
			for (int i = 0; i < itemCodeList.size(); i++) {
				ProcurementPackageDtl procPackDtl = new com.ibcs.desco.procurement.model.ProcurementPackageDtl(
						null, itemCodeList.get(i), itemNameList.get(i),
						unitList.get(i), qunatityList.get(i),
						unitCostList.get(i), totalCostList.get(i),
						procurementPackageMst, null, userName, now);
				commonService.saveOrUpdateModelObjectToDB(procPackDtl);
				finalQuantity += qunatityList.get(i);
				finalTotalCost += totalCostList.get(i);
			}

			Double mQuantity = procurementPackageMst.getProcurementQty();
			Double mCost = procurementPackageMst.getEstimatedCost();
			if (procurementPackageMst.getQtyFlag().equalsIgnoreCase("1")) {
				procurementPackageMst.setProcurementQty(mQuantity
						+ finalQuantity);
			}
			procurementPackageMst.setEstimatedCost(mCost + finalTotalCost);
			procurementPackageMst.setModifiedBy(userName);
			procurementPackageMst.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(procurementPackageMst);

			// return this.procurementPackageShow(procurementPackageMst);

			ModelAndView modelAndView = new ModelAndView(
					"redirect:/mps/procurementPackageShow.do");
			redirectAttributes.addFlashAttribute("procurementPackageMst",
					procurementPackageMst);
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/checkAnnexureNo.do", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String checkAnnexureNo(ProcurementPackageMst procurementPackageMst) {
		String response = "";
		if (procurementPackageMst.getAnnexureNo().equals("")) {
			response = "blank";
		} else {
			ProcurementPackageMst procPackMstDb = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"annexureNo", procurementPackageMst.getAnnexureNo());
			if (procPackMstDb == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/mps/updateAPP.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String updateAPP(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ProcurementPackageMst bean = gson.fromJson(json,
					ProcurementPackageMst.class);

			ProcurementPackageMst appMstDB = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", bean.getId().toString());

			appMstDB.setPrepDocInvTender(bean.getPrepDocInvTender());
			appMstDB.setEvaluationOfTender(bean.getEvaluationOfTender());
			appMstDB.setAwardOfContract(bean.getAwardOfContract());
			appMstDB.setTentativeCompletion(bean.getTentativeCompletion());
			/*
			 * appMstDB.setCurrentSessionBudget(bean.getCurrentSessionBudget());
			 * appMstDB.setNextSessionBudget(bean.getNextSessionBudget());
			 */
			appMstDB.setModifiedBy(commonService.getAuthUserName());
			appMstDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(appMstDB);
			toJson = ow.writeValueAsString("success");
		} else {
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/updateMasterInformations.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView updateMasterInformations(
			ProcurementPackageMstDtl procurementPackageMstDtl,
			RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = procurementPackageMstDtl.getId();

			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							procurementPackageMstDtl.getSessionId() + "");

			ProcurementPackageMst procurementPackageMstDb = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", id.toString());

			procurementPackageMstDb.setAnnexureNo(procurementPackageMstDtl
					.getAnnexureNo());
			procurementPackageMstDb.setApprovingAuth(procurementPackageMstDtl
					.getApprovingAuth());
			procurementPackageMstDb.setDescoSession(descoSession);
			procurementPackageMstDb.setPackageType(procurementPackageMstDtl
					.getPackageType());
			procurementPackageMstDb.setPackageName(procurementPackageMstDtl
					.getPackageName());
			procurementPackageMstDb.setQtyFlag(procurementPackageMstDtl
					.getQtyFlag());
			procurementPackageMstDb.setProcType(procurementPackageMstDtl
					.getProcType());
			procurementPackageMstDb.setSourceOfFund(procurementPackageMstDtl
					.getSourceOfFund());
			procurementPackageMstDb
					.setProcurementMedium(procurementPackageMstDtl
							.getProcurementMedium());
			procurementPackageMstDb.setProcMethod(procurementPackageMstDtl
					.getProcMethod());
			procurementPackageMstDb.setReasonsOfProc(procurementPackageMstDtl
					.getReasonsOfProc());
			procurementPackageMstDb.setModifiedBy(commonService
					.getAuthUserName());
			procurementPackageMstDb.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(procurementPackageMstDb);

			ModelAndView modelAndView = new ModelAndView(
					"redirect:/mps/procurementPackageShow.do");
			redirectAttributes.addFlashAttribute("procurementPackageMst",
					procurementPackageMstDb);
			return modelAndView;

			// ModelAndView modelAndView = new
			// ModelAndView("redirect:/mps/procurementPackageShow.do?id="+procurementPackageMstDb.getId());
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ihteshamul Alam
	@RequestMapping(value = "/mps/procurementPackageConfirm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView procurementPackageConfirm(
			ProcurementPackageDtl procurementPackageDtl,
			RedirectAttributes redirectAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = procurementPackageDtl.getId();
			ProcurementPackageMst procurementPackageMst = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", id + "");

			procurementPackageMst.setConfirm(CONFIRMED);
			;
			procurementPackageMst
					.setModifiedBy(commonService.getAuthUserName());
			procurementPackageMst.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(procurementPackageMst);

			ProcurementPackageMst procurementPackageSession = new ProcurementPackageMst();
			procurementPackageSession.setId(procurementPackageMst
					.getDescoSession().getId());
			procurementPackageSession.setPackageType(procurementPackageMst
					.getPackageType());
			return this.draftAppList(procurementPackageSession);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("procurement/errorProc", model);
		}
	}

	// Added by: Ashid
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/mps/procurementPackageConfirmAll.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String procurementPackageConfirmAll(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ProcurementPackageMst bean = gson.fromJson(json,
					ProcurementPackageMst.class);

			String fyId = bean.getId().toString();

			List<ProcurementPackageMst> procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
					.getObjectListByAnyColumn("ProcurementPackageMst",
							"descoSession.id", fyId);

			for (ProcurementPackageMst mst : procurementPackageMstList) {
				mst.setConfirm(CONFIRMED);
				mst.setModifiedBy(commonService.getAuthUserName());
				mst.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(mst);
			}
			toJson = ow.writeValueAsString("success");
		} else {
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

}
