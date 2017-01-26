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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.JobEstimationApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.JobEstimationApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.bean.CostEstimationBean;
import com.ibcs.desco.contractor.model.CostEstimateInstallationDtl;
import com.ibcs.desco.contractor.model.CostEstimateMaterialsDtl;
import com.ibcs.desco.contractor.model.CostEstimateMiscellaniousDtl;
import com.ibcs.desco.contractor.model.CostEstimateRecoveryDtl;
import com.ibcs.desco.contractor.model.CostEstimationMst;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.JobTemplateDtlService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

//jobTemplate, JobAssign, JobItemMaintenance
@Controller
@RequestMapping(value = "/template")
@PropertySource("classpath:common.properties")
public class JobTemplateController extends Constrants {

	@Autowired
	UserService userService;

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
	JobEstimationApprovalHierarchyHistoryService jobEstimationApprovalHierarchyHistoryService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Value("${pnd.estimation.prefix}")
	private String costEstimationPrefix;

	@Value("${project.separator}")
	private String separator;

	Date now = new Date();

	@RequestMapping(value = "/jobTemplate.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	@PreAuthorize("isAuthenticated() and hasPermission(#costEstimationMst, 'WRITE')")
	public ModelAndView getJobTemplate(CostEstimationMst costEstimationMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();
			model.put("categoryList", categoryList);
			return new ModelAndView("contractor/jobTemplateForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("contractor/error", model);
		}
	}

	@RequestMapping(value = "/jobTemplateSave.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@PreAuthorize("isAuthenticated() and hasPermission(#costEstimationMst, 'WRITE')")
	public String getJobTemplateSave(Model model,
			CostEstimationBean jobTemplateBean,
			CostEstimationMst costEstimationMst) {
		// get Current Role, User and date
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		String pndNo = null;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();

		CostEstimationMst jobMst = new CostEstimationMst();

		if (jobTemplateBean.getPndNo() != null) {
			pndNo = jobTemplateBean.getPndNo() + PND_NO_EDIT;

		} else {
			pndNo = commonService.getOperationIdByPrefixAndSequenceName(
					costEstimationPrefix, descoDeptCode, separator,
					"COST_ESTMT_REQ_SEQ");
		}

		jobMst.setActive(jobMst.isActive());
		jobMst.setCreatedBy(userName);
		jobMst.setCreatedDate(now);
		jobMst.setPndNo(pndNo);
		jobMst.setName(jobTemplateBean.getName());
		jobMst.setAddress(jobTemplateBean.getAddress());
		jobMst.setTypeOfScheme(jobTemplateBean.getTypeOfScheme());
		jobMst.setServiceChargePercent(jobTemplateBean
				.getServiceChargePercent());
		calculateGrandTotal(jobMst, jobTemplateBean);

		boolean successFlag = true;
		String msg = "";

		successFlag = saveCostEstimate(jobTemplateBean, jobMst, roleName,
				department, authUser);

		if (successFlag) {

			//return "redirect:/template/jobEstimationShow.do?id="+ jobMst.getId();
			return "redirect:/template/costEstimationShow.do?id="+ jobMst.getId();
		} else {
			msg = "Sorry! You have no permission to do this operation. Try again.";
			model.addAttribute("msg", msg);
			return "redirect:/template/jobEstimationList.do";
		}

	}

	private void calculateGrandTotal(CostEstimationMst jobMst,
			CostEstimationBean jobTemplateBean) {

		Double matTotal = 0.0;
		Double insTotal = 0.0;
		Double recTotal = 0.0;
		Double misTotal = 0.0;
		Double sChargeAmt = 0.0;
		Double grandTotal = 0.0;

		for (Double d : jobTemplateBean.getMatAmount()) {
			matTotal += d;
		}
		for (Double d : jobTemplateBean.getInsAmount()) {
			insTotal += d;
		}
		for (Double d : jobTemplateBean.getRecAmount()) {
			recTotal += d;
		}
		for (Double d : jobTemplateBean.getMisAmount()) {
			misTotal += d;
		}
		sChargeAmt = (insTotal + recTotal)
				* jobTemplateBean.getServiceChargePercent() / 100;

		grandTotal = matTotal + insTotal + recTotal + sChargeAmt + misTotal;

		jobMst.setServiceChargeAmount(sChargeAmt);
		jobMst.setGrandTotal(grandTotal);
	}

	@SuppressWarnings("unused")
	private boolean saveCostEstimate(CostEstimationBean jobTemplateBean,
			CostEstimationMst jobMst, String roleName, Departments department,
			AuthUser authUser) {

		jobTemplateDtlService.addJobTemplateMst(jobMst);

		saveMatjobDtl(jobTemplateBean, jobMst);
		saveInsjobDtl(jobTemplateBean, jobMst);
		saveRecjobDtl(jobTemplateBean, jobMst);
		saveMisjobDtl(jobTemplateBean, jobMst);

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(PND_JOB_ESTIMATE);
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
							PND_JOB_ESTIMATE, stateCodes[0].toString());
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

		addCostEstimateHierarchyHistory(jobMst, approvalHierarchy, stateCodes,
				roleName, department, authUser);
		return true;
	}

	private void saveMatjobDtl(CostEstimationBean jobTemplateBean,
			CostEstimationMst jobMst) {

		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
		List<Double> unitPriceList = null;
		List<Double> totalPriceList = null;
		List<String> remarkList = null;

		if (jobTemplateBean.getMatItemCode() != null) {
			itemCodeList = jobTemplateBean.getMatItemCode();
		}

		if (jobTemplateBean.getMatItemName() != null) {
			itemNameList = jobTemplateBean.getMatItemName();
		}

		if (jobTemplateBean.getMatUom() != null) {
			uomList = jobTemplateBean.getMatUom();
		}

		if (jobTemplateBean.getMatQuantity() != null) {
			quantityList = jobTemplateBean.getMatQuantity();
		}

		if (jobTemplateBean.getMatUnitPrice() != null) {
			unitPriceList = jobTemplateBean.getMatUnitPrice();
		}

		if (jobTemplateBean.getMatAmount() != null) {
			totalPriceList = jobTemplateBean.getMatAmount();
		}

		if (jobTemplateBean.getMatRemarks() != null) {
			remarkList = jobTemplateBean.getMatRemarks();
		}

		for (int i = 0; i < itemCodeList.size(); i++) {
			CostEstimateMaterialsDtl jobDtl = new CostEstimateMaterialsDtl();
			if (itemNameList.get(i) != "" && quantityList.get(i) != 0
					&& unitPriceList.get(i) != 0 && totalPriceList.get(i) != 0) {
				if (!itemCodeList.isEmpty()) {
					jobDtl.setItemCode(itemCodeList.get(i));
				} else {
					jobDtl.setItemCode("");
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

				if (!quantityList.isEmpty()) {
					jobDtl.setQuantity(quantityList.get(i));
				} else {
					jobDtl.setQuantity(0.0);
				}

				if (!unitPriceList.isEmpty()) {
					jobDtl.setUnitPrice(unitPriceList.get(i));
				} else {
					jobDtl.setUnitPrice(0.0);
				}

				if (!totalPriceList.isEmpty()) {
					jobDtl.setTotalPrice(totalPriceList.get(i));
				} else {
					jobDtl.setTotalPrice(0.0);
				}

				if (!remarkList.isEmpty()) {
					jobDtl.setRemarks(remarkList.get(i));
				} else {
					jobDtl.setRemarks("");
				}

				jobDtl.setId(null);
				jobDtl.setCostEstimationMst(jobMst);
				jobDtl.setActive(jobMst.isActive());
				jobDtl.setCreatedBy(jobMst.getCreatedBy());
				jobDtl.setCreatedDate(jobMst.getCreatedDate());
				jobTemplateDtlService.addJobMatDtl(jobDtl);
			}
		}

	}

	private void saveInsjobDtl(CostEstimationBean jobTemplateBean,
			CostEstimationMst jobMst) {

		// List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
		List<Double> unitPriceList = null;
		List<Double> totalPriceList = null;
		List<String> remarkList = null;

		if (jobTemplateBean.getInsItemName() != null) {
			itemNameList = jobTemplateBean.getInsItemName();
		}

		if (jobTemplateBean.getInsUom() != null) {
			uomList = jobTemplateBean.getInsUom();
		}

		if (jobTemplateBean.getInsQuantity() != null) {
			quantityList = jobTemplateBean.getInsQuantity();
		}

		if (jobTemplateBean.getInsUnitPrice() != null) {
			unitPriceList = jobTemplateBean.getInsUnitPrice();
		}

		if (jobTemplateBean.getInsAmount() != null) {
			totalPriceList = jobTemplateBean.getInsAmount();
		}

		if (jobTemplateBean.getInsRemarks() != null) {
			remarkList = jobTemplateBean.getInsRemarks();
		}

		for (int i = 0; i < uomList.size(); i++) {
			CostEstimateInstallationDtl jobDtl = new CostEstimateInstallationDtl();
			if (itemNameList.get(i) != "" && quantityList.get(i) != 0
					&& unitPriceList.get(i) != 0 && totalPriceList.get(i) != 0) {
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

				if (!quantityList.isEmpty()) {
					jobDtl.setQuantity(quantityList.get(i));
				} else {
					jobDtl.setQuantity(0.0);
				}

				if (!unitPriceList.isEmpty()) {
					jobDtl.setUnitPrice(unitPriceList.get(i));
				} else {
					jobDtl.setUnitPrice(0.0);
				}

				if (!totalPriceList.isEmpty()) {
					jobDtl.setTotalPrice(totalPriceList.get(i));
				} else {
					jobDtl.setTotalPrice(0.0);
				}

				if (!remarkList.isEmpty()) {
					jobDtl.setRemarks(remarkList.get(i));
				} else {
					jobDtl.setRemarks("");
				}
				jobDtl.setId(null);
				jobDtl.setCostEstimationMst(jobMst);
				jobDtl.setActive(jobMst.isActive());
				jobDtl.setCreatedBy(jobMst.getCreatedBy());
				jobDtl.setCreatedDate(jobMst.getCreatedDate());
				jobTemplateDtlService.addJobInsDtl(jobDtl);
			}
		}

	}

	private void saveRecjobDtl(CostEstimationBean jobTemplateBean,
			CostEstimationMst jobMst) {

		// List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
		List<Double> unitPriceList = null;
		List<Double> totalPriceList = null;
		List<String> remarkList = null;

		if (jobTemplateBean.getRecItemName() != null) {
			itemNameList = jobTemplateBean.getRecItemName();
		}

		if (jobTemplateBean.getRecUom() != null) {
			uomList = jobTemplateBean.getRecUom();
		}

		if (jobTemplateBean.getRecQuantity() != null) {
			quantityList = jobTemplateBean.getRecQuantity();
		}

		if (jobTemplateBean.getRecUnitPrice() != null) {
			unitPriceList = jobTemplateBean.getRecUnitPrice();
		}

		if (jobTemplateBean.getRecAmount() != null) {
			totalPriceList = jobTemplateBean.getRecAmount();
		}

		if (jobTemplateBean.getRecRemarks() != null) {
			remarkList = jobTemplateBean.getRecRemarks();
		}

		for (int i = 0; i < uomList.size(); i++) {
			CostEstimateRecoveryDtl jobDtl = new CostEstimateRecoveryDtl();
			if (itemNameList.get(i) != "" && quantityList.get(i) != 0
					&& unitPriceList.get(i) != 0 && totalPriceList.get(i) != 0) {
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

				if (!quantityList.isEmpty()) {
					jobDtl.setQuantity(quantityList.get(i));
				} else {
					jobDtl.setQuantity(0.0);
				}

				if (!unitPriceList.isEmpty()) {
					jobDtl.setUnitPrice(unitPriceList.get(i));
				} else {
					jobDtl.setUnitPrice(0.0);
				}

				if (!totalPriceList.isEmpty()) {
					jobDtl.setTotalPrice(totalPriceList.get(i));
				} else {
					jobDtl.setTotalPrice(0.0);
				}

				if (!remarkList.isEmpty()) {
					jobDtl.setRemarks(remarkList.get(i));
				} else {
					jobDtl.setRemarks("");
				}
				jobDtl.setId(null);
				jobDtl.setCostEstimationMst(jobMst);
				jobDtl.setActive(jobMst.isActive());
				jobDtl.setCreatedBy(jobMst.getCreatedBy());
				jobDtl.setCreatedDate(jobMst.getCreatedDate());
				jobTemplateDtlService.addJobRecDtl(jobDtl);
			}
		}

	}

	private void saveMisjobDtl(CostEstimationBean jobTemplateBean,
			CostEstimationMst jobMst) {

		// List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
		List<Double> unitPriceList = null;
		List<Double> totalPriceList = null;
		List<String> remarkList = null;

		if (jobTemplateBean.getMisItemName() != null) {
			itemNameList = jobTemplateBean.getMisItemName();
		}

		if (jobTemplateBean.getMisUom() != null) {
			uomList = jobTemplateBean.getMisUom();
		}

		if (jobTemplateBean.getMisQuantity() != null) {
			quantityList = jobTemplateBean.getMisQuantity();
		}

		if (jobTemplateBean.getMisUnitPrice() != null) {
			unitPriceList = jobTemplateBean.getMisUnitPrice();
		}

		if (jobTemplateBean.getMisAmount() != null) {
			totalPriceList = jobTemplateBean.getMisAmount();
		}

		if (jobTemplateBean.getMisRemarks() != null) {
			remarkList = jobTemplateBean.getMisRemarks();
		}

		for (int i = 0; i < uomList.size(); i++) {
			CostEstimateMiscellaniousDtl jobDtl = new CostEstimateMiscellaniousDtl();
			if (itemNameList.get(i) != "" && quantityList.get(i) != 0
					&& unitPriceList.get(i) != 0 && totalPriceList.get(i) != 0) {
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

				if (!quantityList.isEmpty()) {
					jobDtl.setQuantity(quantityList.get(i));
				} else {
					jobDtl.setQuantity(0.0);
				}

				if (!unitPriceList.isEmpty()) {
					jobDtl.setUnitPrice(unitPriceList.get(i));
				} else {
					jobDtl.setUnitPrice(0.0);
				}

				if (!totalPriceList.isEmpty()) {
					jobDtl.setTotalPrice(totalPriceList.get(i));
				} else {
					jobDtl.setTotalPrice(0.0);
				}

				if (!remarkList.isEmpty()) {
					jobDtl.setRemarks(remarkList.get(i));
				} else {
					jobDtl.setRemarks("");
				}

				jobDtl.setId(null);
				jobDtl.setCostEstimationMst(jobMst);
				jobDtl.setActive(jobMst.isActive());
				jobDtl.setCreatedBy(jobMst.getCreatedBy());
				jobDtl.setCreatedDate(jobMst.getCreatedDate());
				jobTemplateDtlService.addJobMiscDtl(jobDtl);
			}
		}

	}

	@RequestMapping(value = "/viewInventoryItemCategory.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
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

	@RequestMapping(value = "/viewInventoryItemCategory1.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewInventoryItemCategory1(@RequestBody String json)
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

	@RequestMapping(value = "/viewInventoryItemCategory2.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewInventoryItemCategory2(@RequestBody String json)
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

	@RequestMapping(value = "/viewInventoryItem.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String ssViewInventoryItem(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			AvgPrice avgPrice = itemInventoryService
					.getAvgPriceByItemCode(selectItemFromDb.getItemId());

			if (avgPrice != null) {
				selectItemFromDb.setPrice(avgPrice.getPrice());
			} else {
				selectItemFromDb.setPrice(0.0);
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/showUnit.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String showUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String itemName = request.getParameter("itemName").trim();
		ItemMaster items = itemInventoryService
				.getInventoryItemByItemName(itemName);
		if (items != null) {
			return items.getUnitCode();
		} else {
			return "no";
		}
	}

	@RequestMapping(value = "/getItems", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<ItemMaster> getItems(
			@RequestParam String itemName, HttpServletResponse response) {
		return simulateSearchItemName(itemName);

	}

	private List<ItemMaster> simulateSearchItemName(String itemName) {

		List<ItemMaster> JobTemplateMstList = new ArrayList<ItemMaster>();
		List<ItemMaster> JobTemplateMstListData = itemInventoryService
				.getInventoryItemList();
		// iterate a list and filter by tagName
		for (ItemMaster orderNo : JobTemplateMstListData) {
			if (orderNo.getItemName().toLowerCase()
					.contains(itemName.toLowerCase())) {
				JobTemplateMstList.add(orderNo);
			}
		}

		return JobTemplateMstList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobEstimationList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobEstimationList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<JobEstimationApprovalHierarchyHistory> jobEstimationHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(
						"JobEstimationApprovalHierarchyHistory", deptId,
						roleName, OPEN, userName);
		/*
		 * List<JobEstimationApprovalHierarchyHistory> jobEstimationHistoryList
		 * = (List<JobEstimationApprovalHierarchyHistory>) (Object)
		 * commonService .getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
		 * "JobEstimationApprovalHierarchyHistory", deptId, roleName, OPEN);
		 */
		String[] operationIdList = new String[jobEstimationHistoryList.size()];
		for (int i = 0; i < jobEstimationHistoryList.size(); i++) {
			operationIdList[i] = jobEstimationHistoryList.get(i)
					.getOperationId();
		}

		List<CostEstimationMst> costEstimationMst = jobTemplateDtlService
				.listCostEstimationMstByOperationIds(operationIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("costEstimationMst", costEstimationMst);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_PN")) {
			return new ModelAndView("contractor/jobEstimationList", model);
		} else {
			return new ModelAndView("contractor/jobEstimationList", model);
		}

	}

	@RequestMapping(value = "/pnd/jobEstimationList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pndJobEstimationList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CostEstimationMst> costEstimationMst = jobTemplateDtlService
					.getJobTemplateMst();
			model.put("costEstimationMst", costEstimationMst);

			return new ModelAndView("contractor/pnd/jobEstimationList", model);

		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("contractor/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/unused/jobEstimationList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView unusedJobEstimationList() {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();
		try {
			List<CostEstimationMst> costEstimationMst = (List<CostEstimationMst>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue(
							"CostEstimationMst", "confirmed", "1", "department.id");
			model.put("costEstimationMst", costEstimationMst);
			if (roleName.contains(ROLE_PROJECT_)) {
				return new ModelAndView(
						"contractor/projects/jobEstimationList", model);
			} else {
				return new ModelAndView("contractor/dnpd/jobEstimationList",
						model);
			}
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("contractor/error", model);
		}
	}

	@RequestMapping(value = "/jobEstimationApprovedList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobEstimationApprovedList() {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();

		List<CostEstimationMst> costEstimationMstList = new ArrayList<CostEstimationMst>();
		List<CostEstimationMst> costEstimationMst = jobTemplateDtlService
				.getJobTemplateMst();

		// removed by Taleb
		/*
		 * for (CostEstimationMst c : costEstimationMst) { if (c.isActive() ==
		 * true && c.isApprove() == true && c.isAssign() == true) {
		 * costEstimationMstList.add(c); } }
		 */

		// Added By Taleb
		for (CostEstimationMst c : costEstimationMst) {
			if (c.isActive() == true && c.isApprove() == true) {
				costEstimationMstList.add(c);
			}
		}

		model.put("costEstimationMstList", costEstimationMstList);

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_PN")) {
			return new ModelAndView("contractor/jobEstimationApproveList",
					model);
		} else {
			model.put(
					"errorMsg",
					"You are not authorized to view this List. Please Login as Planning and Design users.");
			return new ModelAndView("pndContractor/errorPND", model);
		}

	}

	public void addCostEstimateHierarchyHistory(CostEstimationMst jobMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser) {
		JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = new JobEstimationApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(jobMst.getPndNo());
		approvalHierarchyHistory.setOperationName(PND_JOB_ESTIMATE);
		approvalHierarchyHistory.setCreatedBy(jobMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(jobMst.getCreatedDate());
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);
		// added by taleb
		approvalHierarchyHistory.setTargetUserId(authUser.getUserid());

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobEstimationShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobEstimationShow(CostEstimationMst costEstimationMst)
			throws IOException {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		/*
		 * Departments department = departmentsService
		 * .getDepartmentByDeptId(authUser.getDeptId());
		 */

		// Test Dual
		// Long abc = commonService.getNextVal();
		//

		String deptId = authUser.getDeptId();

		CostEstimationMst costEstimationMsttdb = jobTemplateDtlService
				.getJobTemplateMst(costEstimationMst.getId());

		String operationId = costEstimationMsttdb.getPndNo();

		List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateInstallationDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
						"costEstimationMst.pndNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<JobEstimationApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobEstimationApprovalHierarchyHistory",
						PND_JOB_ESTIMATE, operationId, DONE);

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

		List<JobEstimationApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobEstimationApprovalHierarchyHistory",
						PND_JOB_ESTIMATE, operationId, OPEN);

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

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationNameAndRoleName( PND_JOB_ESTIMATE,
		 * roleNameList);
		 */
		// Added By Taleb
		/*
		 * String rangeIndicator = "1"; if(roleName.equals("ROLE_PND_XEN")){
		 * rangeIndicator = "2"; }else if(roleName.equals("ROLE_PND_SE")){
		 * rangeIndicator = "3"; } List<ApprovalHierarchy> approveHeirchyList =
		 * approvalHierarchyService
		 * .getApprovalHierarchyByOperationNameAndRoleNameAndRangeIndicator(
		 * PND_JOB_ESTIMATE, roleNameList, rangeIndicator);
		 */
		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationNameAndRoleName( PND_JOB_ESTIMATE,
		 * roleNameList);
		 */
		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(PND_JOB_ESTIMATE);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
			/*
			 * if(approveHeirchyList.get(countStateCodes).getStateCode() ==
			 * currentStateCode){
			 * if(approveHeirchyList.get(countStateCodes).getRangeIndicator
			 * ().equals(rangeIndicator)){
			 * nextManReqProcs.add(approveHeirchyList.get(countStateCodes)); } }
			 */
		}

		// added by Taleb for Send To
		for (ApprovalHierarchy approvalHierarchy : nextManReqProcs) {
			Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					approvalHierarchy.getRoleName());
			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid",
							role.getRole_id() + "");
			approvalHierarchy.setAuthUser(authUserList);
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

		// added by Taleb for Back To
		for (ApprovalHierarchy approvalHierarchy : backManRcvProcs) {
			Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					approvalHierarchy.getRoleName());
			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid",
							role.getRole_id() + "");
			approvalHierarchy.setAuthUser(authUserList);
		}

		//

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
								PND_JOB_ESTIMATE, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}

		// ObjectWriter ow = new
		// ObjectMapper().writer().withDefaultPrettyPrinter();
		// model.put("locationList", "'" + locationOptions + "'");
		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemMaster> itemList = itemInventoryService.getInventoryItemList();
		model.put("itemList", itemList);

		model.put("returnStateCode", returnStateCode);
		model.put("costEstimationMst", costEstimationMsttdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);

		model.put("costEstimateMaterialsDtlList", costEstimateMaterialsDtlList);

		model.put("costEstimateInstallationDtlList",
				costEstimateInstallationDtlList);

		model.put("costEstimateRecoveryDtlList", costEstimateRecoveryDtlList);

		model.put("costEstimateMiscellaniousDtlList",
				costEstimateMiscellaniousDtlList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_PN")) {
			return new ModelAndView("contractor/jobEstimationShow", model);
		} else {
			return new ModelAndView("contractor/jobEstimationShow", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/costEstimationShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView costEstimationShow(CostEstimationMst costEstimationMst)
			throws IOException {

		CostEstimationMst costEstimationMsttdb = jobTemplateDtlService
				.getJobTemplateMst(costEstimationMst.getId());

		String operationId = costEstimationMsttdb.getPndNo();

		List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateInstallationDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
						"costEstimationMst.pndNo", operationId);		
		// return
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<ItemMaster> itemList = itemInventoryService.getInventoryItemList();
		model.put("itemList", itemList);
		
		model.put("costEstimationMst", costEstimationMsttdb);		

		model.put("costEstimateMaterialsDtlList", costEstimateMaterialsDtlList);

		model.put("costEstimateInstallationDtlList",
				costEstimateInstallationDtlList);

		model.put("costEstimateRecoveryDtlList", costEstimateRecoveryDtlList);

		model.put("costEstimateMiscellaniousDtlList",
				costEstimateMiscellaniousDtlList);

		return new ModelAndView("contractor/pnd/jobEstimationShow", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobEstimationApproveShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobEstimationApproveShow(
			CostEstimationMst costEstimationMst) throws IOException {

		String roleName = commonService.getAuthRoleName();
		// String userName = commonService.getAuthUserName();

		// AuthUser authUser = userService.getAuthUserByUserId(userName);

		/*
		 * Departments department = departmentsService
		 * .getDepartmentByDeptId(authUser.getDeptId());
		 */

		// Test Dual
		// Long abc = commonService.getNextVal();
		//

		// String deptId = authUser.getDeptId();

		CostEstimationMst costEstimationMsttdb = jobTemplateDtlService
				.getJobTemplateMst(costEstimationMst.getId());

		String operationId = costEstimationMsttdb.getPndNo();

		List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateInstallationDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
						"costEstimationMst.pndNo", operationId);

		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemMaster> itemList = itemInventoryService.getInventoryItemList();
		model.put("itemList", itemList);

		model.put("costEstimationMst", costEstimationMsttdb);

		model.put("costEstimateMaterialsDtlList", costEstimateMaterialsDtlList);

		model.put("costEstimateInstallationDtlList",
				costEstimateInstallationDtlList);

		model.put("costEstimateRecoveryDtlList", costEstimateRecoveryDtlList);

		model.put("costEstimateMiscellaniousDtlList",
				costEstimateMiscellaniousDtlList);

		// Added By Taleb
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/jobEstimationApproveShow", model);
		}
		if (roleName.contains("ROLE_PND_")) {
			return new ModelAndView("contractor/jobEstimationApproveShow",
					model);
		} else {
			return new ModelAndView(
					"contractor/dnpd/jobEstimationApproveShow", model);
			
		}

		/*
		 * // Remove by Taleb String rolePrefix = roleName.substring(0, 7); if
		 * (rolePrefix.equals("ROLE_PN")) { return new
		 * ModelAndView("contractor/jobEstimationApproveShow", model); } else {
		 * return null; }
		 */

	}

	//
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/costEstimationSubmitApproved.do", method = RequestMethod.POST)
	public String costEstimationSubmitApproved(
			Model model,
			@ModelAttribute("jobTemplateBean") CostEstimationBean jobTemplateBean) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();

		// Send return to next user who backed me
		if (!jobTemplateBean.getReturn_state().equals("")
				|| jobTemplateBean.getReturn_state().length() > 0) {

			List<JobEstimationApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobEstimationApprovalHierarchyHistory",
							"operationId", jobTemplateBean.getPndNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = jobEstimationApprovalHierarchyHistoryService
					.getJobEstimationApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							PND_JOB_ESTIMATE, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(jobTemplateBean
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			JobEstimationApprovalHierarchyHistory approvalHierarchyHistoryNextState = new JobEstimationApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							PND_JOB_ESTIMATE, jobTemplateBean.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(jobTemplateBean.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(jobTemplateBean
					.getPndNo());
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
			String operationId = jobTemplateBean.getPndNo();

			CostEstimationMst costEstimationMst = (CostEstimationMst) commonService
					.getAnObjectByAnyUniqueColumn("CostEstimationMst", "pndNo",
							operationId);

			List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateInstallationDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
							"costEstimationMst.pndNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(PND_JOB_ESTIMATE);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<JobEstimationApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobEstimationApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = jobEstimationApprovalHierarchyHistoryService
					.getJobEstimationApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			int nextStateCode = 0;

			// update cost estimation mat dtl tables

			updateMatDtl(costEstimateMaterialsDtlList, jobTemplateBean,
					userName);
			updateInsDtl(costEstimateInstallationDtlList, jobTemplateBean,
					userName);
			updateRecDtl(costEstimateRecoveryDtlList, jobTemplateBean, userName);
			updateMisDtl(costEstimateMiscellaniousDtlList, jobTemplateBean,
					userName);

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									PND_JOB_ESTIMATE, nextStateCode + "");

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
									PND_JOB_ESTIMATE, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());
					approvalHierarchyHistory.setJustification(jobTemplateBean
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

					costEstimationMst.setModifiedBy(userName);
					costEstimationMst.setModifiedDate(now);
					costEstimationMst.setApprove(true);
					commonService
							.saveOrUpdateModelObjectToDB(costEstimationMst);
					model.addAttribute("operationId", operationId);

					return "contractor/costEstimationReport";

				}
			}
		}
		return "redirect:/template/jobEstimationList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("jobTemplateBean") CostEstimationBean jobTemplateBean) {

		String pndNo = jobTemplateBean.getPndNo();
		String justification = jobTemplateBean.getJustification();
		String nextStateCode = jobTemplateBean.getStateCode();
		String nextUserId = jobTemplateBean.getUserid();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// get Received Item List against RR No
		String operationId = jobTemplateBean.getPndNo();

		List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateInstallationDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
						"costEstimationMst.pndNo", operationId);
		List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
						"costEstimationMst.pndNo", operationId);

		// update cost estimation mat dtl tables

		updateMatDtl(costEstimateMaterialsDtlList, jobTemplateBean, userName);
		updateInsDtl(costEstimateInstallationDtlList, jobTemplateBean, userName);
		updateRecDtl(costEstimateRecoveryDtlList, jobTemplateBean, userName);
		updateMisDtl(costEstimateMiscellaniousDtlList, jobTemplateBean,
				userName);

		List<JobEstimationApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"JobEstimationApprovalHierarchyHistory", "operationId",
						pndNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = jobEstimationApprovalHierarchyHistoryService
				.getJobEstimationApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						PND_JOB_ESTIMATE, currentStateCode + "");
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
		JobEstimationApprovalHierarchyHistory approvalHierarchyHistoryNextState = new JobEstimationApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						PND_JOB_ESTIMATE, nextStateCode + "");
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

		// added by Taleb
		approvalHierarchyHistoryNextState.setTargetUserId(nextUserId);

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		return "redirect:/template/jobEstimationList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("jobTemplateBean") CostEstimationBean jobTemplateBean) {
		String pndNo = jobTemplateBean.getPndNo();
		String justification = jobTemplateBean.getJustification();
		String backStateCode = jobTemplateBean.getStateCode();

		// added by Taleb
		String backedUserId = jobTemplateBean.getUserid();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// get Cost Estimation Items
		/*
		 * String operationId = jobTemplateBean.getPndNo();
		 * 
		 * List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList =
		 * (List<CostEstimateMaterialsDtl>) (Object) commonService
		 * .getObjectListByAnyColumn("CostEstimateMaterialsDtl",
		 * "costEstimationMst.pndNo", operationId);
		 * List<CostEstimateInstallationDtl> costEstimateInstallationDtlList =
		 * (List<CostEstimateInstallationDtl>) (Object) commonService
		 * .getObjectListByAnyColumn("CostEstimateInstallationDtl",
		 * "costEstimationMst.pndNo", operationId);
		 * List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList =
		 * (List<CostEstimateRecoveryDtl>) (Object) commonService
		 * .getObjectListByAnyColumn("CostEstimateRecoveryDtl",
		 * "costEstimationMst.pndNo", operationId);
		 * List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList =
		 * (List<CostEstimateMiscellaniousDtl>) (Object) commonService
		 * .getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
		 * "costEstimationMst.pndNo", operationId);
		 */

		// update cost estimation mat dtl tables

		/*
		 * updateMatDtl(costEstimateMaterialsDtlList, jobTemplateBean,
		 * userName); updateInsDtl(costEstimateInstallationDtlList,
		 * jobTemplateBean, userName); updateRecDtl(costEstimateRecoveryDtlList,
		 * jobTemplateBean, userName);
		 * updateMisDtl(costEstimateMiscellaniousDtlList, jobTemplateBean,
		 * userName);
		 */

		List<JobEstimationApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"JobEstimationApprovalHierarchyHistory", "operationId",
						pndNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = jobEstimationApprovalHierarchyHistoryService
				.getJobEstimationApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						PND_JOB_ESTIMATE, currentStateCode + "");
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
		JobEstimationApprovalHierarchyHistory approvalHierarchyHistoryBackState = new JobEstimationApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						PND_JOB_ESTIMATE, backStateCode + "");
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
		// added by Taleb
		approvalHierarchyHistoryBackState.setTargetUserId(backedUserId);

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/template/jobEstimationList.do";
	}

	private void updateMatDtl(
			List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList,
			CostEstimationBean costEstimationBean, String userName) {

		List<String> itemCodeList = costEstimationBean.getMatItemCode();
		List<String> itemNameList = costEstimationBean.getMatItemName();
		List<String> remarkList = costEstimationBean.getMatRemarks();
		List<String> unitList = costEstimationBean.getMatUom();
		List<Double> quantityList = costEstimationBean.getMatQuantity();
		List<Double> unitPriceList = costEstimationBean.getMatUnitPrice();
		List<Double> totalPriceList = costEstimationBean.getMatAmount();
		// for (int i = 0; i < itemCodeList.size(); i++) {
		int i = 0;
		int q = itemCodeList.size();
		for (CostEstimateMaterialsDtl mat : costEstimateMaterialsDtlList) {

			mat.setItemCode(itemCodeList.get(i));
			mat.setItemName(itemNameList.get(i));
			mat.setUom(unitList.get(i));
			mat.setQuantity(quantityList.get(i));
			mat.setUnitPrice(unitPriceList.get(i));
			mat.setTotalPrice(totalPriceList.get(i));
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

	private void updateInsDtl(
			List<CostEstimateInstallationDtl> costEstimateInstallationDtlList,
			CostEstimationBean costEstimationBean, String userName) {

		List<String> itemNameList = costEstimationBean.getInsItemName();
		List<String> remarkList = costEstimationBean.getInsRemarks();
		List<String> unitList = costEstimationBean.getInsUom();
		List<Double> quantityList = costEstimationBean.getInsQuantity();
		List<Double> unitPriceList = costEstimationBean.getInsUnitPrice();
		List<Double> totalPriceList = costEstimationBean.getInsAmount();
		int i = 0;
		int q = itemNameList.size();
		for (CostEstimateInstallationDtl ins : costEstimateInstallationDtlList) {

			ins.setItemName(itemNameList.get(i));
			ins.setUom(unitList.get(i));
			ins.setQuantity(quantityList.get(i));
			ins.setUnitPrice(unitPriceList.get(i));
			ins.setTotalPrice(totalPriceList.get(i));
			if (remarkList.size() > 0) {
				ins.setRemarks(remarkList.get(i));
			}
			ins.setModifiedBy(userName);
			ins.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(ins);
			if (i < q) {
				i++;
			}
		}
	}

	private void updateRecDtl(
			List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList,
			CostEstimationBean costEstimationBean, String userName) {

		List<String> itemNameList = costEstimationBean.getRecItemName();
		List<String> remarkList = costEstimationBean.getRecRemarks();
		List<String> unitList = costEstimationBean.getRecUom();
		List<Double> quantityList = costEstimationBean.getRecQuantity();
		List<Double> unitPriceList = costEstimationBean.getRecUnitPrice();
		List<Double> totalPriceList = costEstimationBean.getRecAmount();
		int i = 0;
		int q = itemNameList.size();
		for (CostEstimateRecoveryDtl rec : costEstimateRecoveryDtlList) {

			rec.setItemName(itemNameList.get(i));
			rec.setUom(unitList.get(i));
			rec.setQuantity(quantityList.get(i));
			rec.setUnitPrice(unitPriceList.get(i));
			rec.setTotalPrice(totalPriceList.get(i));
			if (remarkList.size() > 0) {
				rec.setRemarks(remarkList.get(i));
			}
			rec.setModifiedBy(userName);
			rec.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(rec);
			if (i < q) {
				i++;
			}
		}
	}

	private void updateMisDtl(
			List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList,
			CostEstimationBean costEstimationBean, String userName) {

		List<String> itemNameList = costEstimationBean.getMisItemName();
		List<String> remarkList = costEstimationBean.getMisRemarks();
		List<String> unitList = costEstimationBean.getMisUom();
		List<Double> quantityList = costEstimationBean.getMisQuantity();
		List<Double> unitPriceList = costEstimationBean.getMisUnitPrice();
		List<Double> totalPriceList = costEstimationBean.getMisAmount();
		int i = 0;
		int q = itemNameList.size();
		for (CostEstimateMiscellaniousDtl mis : costEstimateMiscellaniousDtlList) {

			mis.setItemName(itemNameList.get(i));
			mis.setUom(unitList.get(i));
			mis.setQuantity(quantityList.get(i));
			mis.setUnitPrice(unitPriceList.get(i));
			mis.setTotalPrice(totalPriceList.get(i));
			if (remarkList.size() > 0) {
				mis.setRemarks(remarkList.get(i));
			}
			mis.setModifiedBy(userName);
			mis.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(mis);
			if (i < q) {
				i++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobEstimationEdit.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobTemplateEdit(CostEstimationMst mst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CostEstimationMst costEstimationMst = jobTemplateDtlService
					.getJobTemplateMst(mst.getId());
			String operationId = costEstimationMst.getPndNo();

			List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateInstallationDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
							"costEstimationMst.pndNo", operationId);
			List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
					.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
							"costEstimationMst.pndNo", operationId);

			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();
			List<ItemMaster> itemList = itemInventoryService
					.getInventoryItemList();

			model.put("itemList", itemList);
			model.put("categoryList", categoryList);
			model.put("costEstimationMst", costEstimationMst);
			model.put("costEstimateMaterialsDtlList",
					costEstimateMaterialsDtlList);
			model.put("costEstimateInstallationDtlList",
					costEstimateInstallationDtlList);
			model.put("costEstimateRecoveryDtlList",
					costEstimateRecoveryDtlList);
			model.put("costEstimateMiscellaniousDtlList",
					costEstimateMiscellaniousDtlList);

			return new ModelAndView("contractor/jobTemplateEditForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("contractor/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/costEstimationApproved.do", method = RequestMethod.POST)
	public ModelAndView costEstimationApproved(
			@ModelAttribute("jobTemplateBean") CostEstimationBean jobTemplateBean) {
		String pndNo = jobTemplateBean.getPndNo();
		String justification = jobTemplateBean.getJustification();

		// get Current User and Role Information

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<JobEstimationApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobEstimationApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobEstimationApprovalHierarchyHistory",
							"operationId", pndNo);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			JobEstimationApprovalHierarchyHistory approvalHierarchyHistory = jobEstimationApprovalHierarchyHistoryService
					.getJobEstimationApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							PND_JOB_ESTIMATE, currentStateCode + "");
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
			approvalHierarchyHistory.setRemarks("APPROVED");

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			
			model.put("operationId", pndNo);
			return new ModelAndView("contractor/costEstimationReport", model);

			//return new ModelAndView("redirect:/template/jobEstimationApprovedList.do", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("contractor/error", model);
		}
	}
	
	@RequestMapping(value = "/costEstimationReport.do", method = RequestMethod.GET)
	public ModelAndView costEstimationReport(CostEstimationMst jobTemplateBean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String primaryId = jobTemplateBean.getId().toString();
		
		CostEstimationMst ceMst = (CostEstimationMst) commonService.getAnObjectByAnyUniqueColumn("CostEstimationMst", "id", primaryId);
		model.put("operationId", ceMst.getPndNo());
		return new ModelAndView("contractor/costEstimationReport");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCostEstimation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String costEstimationUpdate(
			Model model,
			@ModelAttribute("jobTemplateBean") CostEstimationBean jobTemplateBean) {

	
		String justification = jobTemplateBean.getJustification();		

		
		String userName = commonService.getAuthUserName();


		// get Received Item List against RR No
		String primaryId = jobTemplateBean.getPndNo();
		
		CostEstimationMst ceMst = (CostEstimationMst) commonService.getAnObjectByAnyUniqueColumn("CostEstimationMst", "id", primaryId);

		List<CostEstimateMaterialsDtl> costEstimateMaterialsDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", ceMst.getPndNo() );
		List<CostEstimateInstallationDtl> costEstimateInstallationDtlList = (List<CostEstimateInstallationDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateInstallationDtl",
						"costEstimationMst.pndNo", ceMst.getPndNo() );
		List<CostEstimateRecoveryDtl> costEstimateRecoveryDtlList = (List<CostEstimateRecoveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateRecoveryDtl",
						"costEstimationMst.pndNo", ceMst.getPndNo() );
		List<CostEstimateMiscellaniousDtl> costEstimateMiscellaniousDtlList = (List<CostEstimateMiscellaniousDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMiscellaniousDtl",
						"costEstimationMst.pndNo", ceMst.getPndNo() );

		// update cost estimation mat dtl tables
		
		ceMst.setRemarks( justification );
		ceMst.setModifiedBy(userName);
		ceMst.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(ceMst);
		updateMatDtl(costEstimateMaterialsDtlList, jobTemplateBean, userName);
		updateInsDtl(costEstimateInstallationDtlList, jobTemplateBean, userName);
		updateRecDtl(costEstimateRecoveryDtlList, jobTemplateBean, userName);
		updateMisDtl(costEstimateMiscellaniousDtlList, jobTemplateBean,
				userName);

		return "redirect:/template/pnd/jobEstimationList.do";
	}
	
	//added by taleb
	@RequestMapping(value = "/confirmCostEstimation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String confirmCostEstimation(CostEstimationBean jobTemplateBean) {
		
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// get Received Item List against RR No
		String primaryId = jobTemplateBean.getId().toString();
		
		CostEstimationMst ceMst = (CostEstimationMst) commonService.getAnObjectByAnyUniqueColumn("CostEstimationMst", "id", primaryId);
		ceMst.setConfirmed( "1" );
		ceMst.setModifiedBy( userName );
		ceMst.setModifiedDate( new Date() );
		ceMst.setConfirmedBy( authUser.getName() + " ("+authUser.getEmpId() + ")" );
		commonService.saveOrUpdateModelObjectToDB(ceMst);
		
		return "redirect:/template/pnd/jobEstimationList.do";
	}
}
