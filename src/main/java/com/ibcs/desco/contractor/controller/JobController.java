package com.ibcs.desco.contractor.controller;

//@author nasrin
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.JobAssignApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.bean.PndJobBean;
import com.ibcs.desco.contractor.model.ConstructionNature;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.CostEstimateMaterialsDtl;
import com.ibcs.desco.contractor.model.CostEstimationMst;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.JobTemplateDtlService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

//jobTemplate, JobAssign, JobItemMaintenance
@Controller
@RequestMapping(value = "/job")
@PropertySource("classpath:common.properties")
public class JobController extends Constrants {
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	UserService userService;

	@Autowired
	JobTemplateDtlService jobTemplateDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	PndJobDtlService pndJobDtlService;
	
	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;	

	@Value("${project.dnpd.job.assign.prefix}")
	private String jobAssignPrefix;

	@Value("${project.separator}")
	private String separator;
	
	Date now = new Date();

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobAssign.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getjobAssign() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor", "active", "1");

		List<ConstructionNature> constructionNature = (List<ConstructionNature>) (Object) commonService
				.getObjectListByAnyColumn("ConstructionNature", "active", "1");

		model.put("contractorList", contractorList);
		model.put("templateList", jobTemplateDtlService.getJobTemplateMst());
		model.put("constructionNature", constructionNature);
		return new ModelAndView("contractor/jobAssignForm", model);
	}

	@RequestMapping(value = "/getTemplates.do", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<CostEstimationMst> getJobTemplateMst(
			@RequestParam String templateName, HttpServletResponse response) {
		return simulateSearchResult(templateName);

	}

	private List<CostEstimationMst> simulateSearchResult(String templateName) {
		List<CostEstimationMst> jobTemplateMstList = new ArrayList<CostEstimationMst>();
		// List<CostEstimationMst> JobTemplateMstListData =
		// jobTemplateDtlService.getJobTemplateMst();

		/*
		 * List<CostEstimationMst> jobTemplateMstListData =
		 * (List<CostEstimationMst>) (Object) commonService
		 * .getObjectListByAnyColumn("CostEstimationMst", "assign", "0");
		 */
		List<CostEstimationMst> jobTemplateMstListData = jobTemplateDtlService
				.getCostEstimationMstByAssignFlag();

		// iterate a list and filter by tagName
		for (CostEstimationMst orderNo : jobTemplateMstListData) {
			if (orderNo.getPndNo().toLowerCase()
					.contains(templateName.toLowerCase())) {
				jobTemplateMstList.add(orderNo);
			}
		}
		return jobTemplateMstList;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assignContractor.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobAssignContractor(String id) {

		Map<String, Object> model = new HashMap<String, Object>();	
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		
		DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "department.id", department.getId().toString());
		

		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor", "khathId", descoKhath.getId().toString());

		CostEstimationMst cEstMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "id", id
						+ "");

		List<CostEstimateMaterialsDtl> cEstDtlList = jobTemplateDtlService
				.getJobTemplateDtl(Integer.parseInt(id));

		List<ConstructionNature> constructionNature = (List<ConstructionNature>) (Object) commonService
				.getObjectListByAnyColumn("ConstructionNature", "active", "1");
		model.put("constructionNature", constructionNature);
		model.put("costEstimationMst", cEstMst);	
		model.put("JobTemplateDtlList", cEstDtlList);
		model.put("contractorList", contractorList);
		
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView("contractor/projects/jobAssignForm", model);			
		} else {
			return new ModelAndView("contractor/dnpd/jobAssignForm",
					model);
		}
		
	}
	
	
	// @added by Taleb instruction by Alamin Bhai
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTemplateItems.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewTemplateItems(int eId, String cNo) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor", "active", "1");

		CostEstimationMst cEstMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "id", eId
						+ "");

		List<CostEstimateMaterialsDtl> cEstDtlList = jobTemplateDtlService
				.getJobTemplateDtl(eId);

		List<ConstructionNature> constructionNature = (List<ConstructionNature>) (Object) commonService
				.getObjectListByAnyColumn("ConstructionNature", "active", "1");

		model.put("constructionNature", constructionNature);
		model.put("woNumber", cNo);
		model.put("costEstimationMst", cEstMst);		
		model.put("JobTemplateDtlList", cEstDtlList);
		model.put("contractorList", contractorList);
		return new ModelAndView("contractor/jobAssignForm", model);
	}

	@RequestMapping(value = "/getWOrder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Contractor> getWOrder(
			@RequestParam String woNumber, HttpServletResponse response) {
		return simulateSearchContractNo(woNumber);

	}

	private List<Contractor> simulateSearchContractNo(String woNumber) {

		List<Contractor> JobTemplateMstList = new ArrayList<Contractor>();
		List<Contractor> JobTemplateMstListData = contractorRepresentiveService
				.getContractorList();
		// iterate a list and filter by tagName
		for (Contractor orderNo : JobTemplateMstListData) {
			if (orderNo.getContractNo().toLowerCase()
					.contains(woNumber.toLowerCase())) {
				JobTemplateMstList.add(orderNo);
			}
		}

		return JobTemplateMstList;
	}

	@RequestMapping(value = "/jobAssignSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getjobAssignSave(PndJobBean pndJobBean) {
		// Map<String, Object> model = new HashMap<String, Object>();
		String contractorPk = pndJobBean.getWoNumber();
		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "id", contractorPk);

		String userName = commonService.getAuthUserName();
		CostEstimationMst costMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "pndNo",
						pndJobBean.getPndNo());
		PndJobMst jobMst = new PndJobMst();

		jobMst.setActive(true);
		jobMst.setCreatedBy(userName);
		jobMst.setCreatedDate(now);
		jobMst.setWoNumber(contractor.getContractNo());
		jobMst.setContractor(contractor);

		jobMst.setJobNo(pndJobBean.getJobNo());
		jobMst.setJobTitle(pndJobBean.getJobTitle());
		jobMst.setJobLocation(pndJobBean.getJobLocation());
		jobMst.setPndNo(pndJobBean.getPndNo());
		jobMst.setPdNo(pndJobBean.getPdNo());
		jobMst.setName(costMst.getName());
		jobMst.setAddress(costMst.getAddress());
		jobMst.setLot(pndJobBean.getLot());
		jobMst.setConstructionNature(pndJobBean.getConstructionNature());
		jobMst.setRemarks(pndJobBean.getRemarks());
		pndJobDtlService.addPndJobMst(jobMst);

		// Save PndJobDtl
		savePndJobDtlList(pndJobBean, jobMst);
		// savejobItems(pndJobBean, jobMst);

		// Update Cost_Est_Mst and Set Flag.
		// So that, P&D number can not use twice.
		CostEstimationMst cMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "pndNo",
						pndJobBean.getPndNo());
		cMst.setAssign(true);
		commonService.saveOrUpdateModelObjectToDB(cMst);

		/*Contractor obj = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
						pndJobBean.getWoNumber());*/
		return "redirect:/contractor/jobList.do?id=" + contractorPk;
		// return "redirect:/job/jobAssign.do";
	}

	@SuppressWarnings("unchecked")
	private void savePndJobDtlList(PndJobBean pndJobBean, PndJobMst jobMst) {
		PndJobMst jobMstDB = (PndJobMst) commonService
				.getAnObjectByAnyUniqueColumn("PndJobMst", "pndNo",
						jobMst.getPndNo());
		List<CostEstimateMaterialsDtl> cstDtlList = (List<CostEstimateMaterialsDtl>) (Object) commonService
				.getObjectListByAnyColumn("CostEstimateMaterialsDtl",
						"costEstimationMst.pndNo", jobMst.getPndNo());
		List<PndJobDtl> pndJobDtlList = new ArrayList<PndJobDtl>();
		for (CostEstimateMaterialsDtl cDtl : cstDtlList) {
			PndJobDtl jDtl = new PndJobDtl();
			jDtl.setId(null);
			jDtl.setItemCode(cDtl.getItemCode());
			jDtl.setItemName(cDtl.getItemName());
			jDtl.setUom(cDtl.getUom());
			jDtl.setQuantity(cDtl.getQuantity());
			jDtl.setRemainningQuantity(cDtl.getQuantity());
			jDtl.setRemarks(cDtl.getRemarks());
			jDtl.setActive(true);
			jDtl.setCreatedBy(commonService.getAuthUserName());
			jDtl.setCreatedDate(new Date());
			jDtl.setPndJobMst(jobMstDB);
			jDtl.setPndJobMstId(jobMstDB.getId());

			commonService.saveOrUpdateModelObjectToDB(jDtl);
			pndJobDtlList.add(jDtl);
		}

		// adjust total Job item Contractor wise
		//remove by Taleb instruction By Alamin Bhai
		//adjustContractorWiseJobItems(pndJobDtlList);

	}

	@SuppressWarnings("unused")
	private void savejobItems(PndJobBean pndJobBean, PndJobMst jobMst) {

		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> qtyList = null;
		List<Double> extQtyList = null;

		if (pndJobBean.getItemCode() != null) {
			itemCodeList = pndJobBean.getItemCode();
		}

		if (pndJobBean.getItemName() != null) {
			itemNameList = pndJobBean.getItemName();
		}

		if (pndJobBean.getUom() != null) {
			uomList = pndJobBean.getUom();
		}

		if (pndJobBean.getQuantity() != null) {
			qtyList = pndJobBean.getQuantity();
		}
		if (pndJobBean.getExtendQuantity() != null) {
			extQtyList = pndJobBean.getExtendQuantity();
		}

		List<PndJobDtl> pndJobDtlList = new ArrayList<PndJobDtl>();
		for (int i = 0; i < itemCodeList.size(); i++) {
			PndJobDtl jobDtl = new PndJobDtl();

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

			if (!qtyList.isEmpty()) {
				jobDtl.setQuantity(qtyList.get(i));
				jobDtl.setRemainningQuantity(qtyList.get(i));
			} else {
				jobDtl.setQuantity(0.0);
			}

			if (extQtyList != null) {
				jobDtl.setExtendQuantity(extQtyList.get(i));
			} else {
				jobDtl.setExtendQuantity(qtyList.get(i));
			}

			jobDtl.setId(null);
			jobDtl.setPndJobMst(jobMst);
			jobDtl.setActive(jobMst.isActive());
			jobDtl.setCreatedBy(jobMst.getCreatedBy());
			jobDtl.setCreatedDate(jobMst.getCreatedDate());
			pndJobDtlService.addPndJobDtl(jobDtl);
			pndJobDtlList.add(jobDtl);
		}

		// adjust total Job item Contractor wise
		adjustContractorWiseJobItems(pndJobDtlList);
	}

	private void adjustContractorWiseJobItems(List<PndJobDtl> pndJobDtlList) {

		for (PndJobDtl pndJobDtl : pndJobDtlList) {
			JobItemMaintenance jobItems = pndJobDtlService.getTotalJobItems(
					pndJobDtl.getItemCode(), pndJobDtl.getPndJobMst()
							.getWoNumber());

			if (jobItems != null) {
				jobItems.setQuantity(jobItems.getQuantity()
						+ pndJobDtl.getQuantity());
				jobItems.setRemainningQuantity(jobItems.getRemainningQuantity()
						+ pndJobDtl.getQuantity());
				jobItems.setModifiedBy(pndJobDtl.getCreatedBy());
				jobItems.setModifiedDate(now);
				commonService.saveOrUpdateModelObjectToDB(jobItems);
			} else {
				JobItemMaintenance jobItem = new JobItemMaintenance();
				Contractor c = contractorRepresentiveService
						.getContractorByContractNo(pndJobDtl.getPndJobMst()
								.getWoNumber());

				jobItem.setActive(true);
				jobItem.setItemCode(pndJobDtl.getItemCode());
				jobItem.setItemName(pndJobDtl.getItemName());
				jobItem.setUom(pndJobDtl.getUom());
				jobItem.setContractor(contractorRepresentiveService
						.getContractorByContractNo(pndJobDtl.getPndJobMst()
								.getWoNumber()));
				jobItem.setQuantity(pndJobDtl.getQuantity());
				jobItem.setRemainningQuantity(pndJobDtl.getQuantity());
				jobItem.setAsBuiltQuantity(0.0);
				jobItem.setCreatedBy(pndJobDtl.getCreatedBy());
				jobItem.setCreatedDate(now);
				jobItem.setContractorMstId(c.getId());
				commonService.saveOrUpdateModelObjectToDB(jobItem);

			}
		}
	}

	/* Added by Ihteshamul Alam */

	@RequestMapping(value = "/constructionNature.do", method = RequestMethod.GET)
	public ModelAndView constructionNature() {
		String roleName = commonService.getAuthRoleName();
		// Added By Taleb
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/constructionNature");
		}else {
			return new ModelAndView("contractor/constructionNature");
			
		}
		
	}

	@RequestMapping(value = "/checkConstructionNature.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkConstructionNature(ConstructionNature constructionNature) {
		String response = "";
		if (constructionNature.getName().equals("")) {
			response = "unsuccess";
		} else {
			ConstructionNature cNature = (ConstructionNature) commonService
					.getAnObjectByAnyUniqueColumn("ConstructionNature", "name",
							constructionNature.getName() + "");
			if (cNature == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/saveConstructionNature.do", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveConstructionNature(
			ConstructionNature constructionNature) {
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		ConstructionNature cNature = (ConstructionNature) commonService
				.getAnObjectByAnyUniqueColumn("ConstructionNature", "name",
						constructionNature.getName());
		ConstructionNature consNature = new ConstructionNature(null,
				constructionNature.getName(), constructionNature.getRemarks(),
				constructionNature.isActive(), userName, now);
		commonService.saveOrUpdateModelObjectToDB(consNature);

		Map<String, Object> model = new HashMap<String, Object>();
		List<ConstructionNature> constructionNatureList = (List<ConstructionNature>) (Object) commonService
				.getAllObjectList("ConstructionNature");
		model.put("constructionNatureList", constructionNatureList);
		// Added By Taleb
		String roleName = commonService.getAuthRoleName();
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/constructionNatureList", model);
		}else {
			return new ModelAndView("contractor/constructionNatureList", model);
			
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/constructionNatureList.do", method = RequestMethod.GET)
	public ModelAndView constructionNatureList() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ConstructionNature> constructionNatureList = (List<ConstructionNature>) (Object) commonService
				.getAllObjectList("ConstructionNature");
		model.put("constructionNatureList", constructionNatureList);
		
		// Added By Taleb
		String roleName = commonService.getAuthRoleName();
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/constructionNatureList", model);
		}else {
			return new ModelAndView("contractor/constructionNatureList", model);
			
		}
		
	}

	@RequestMapping(value = "/editConstructionNature.do", method = RequestMethod.GET)
	public ModelAndView editConstructionNature(
			ConstructionNature constructionNature) {
		Map<String, Object> model = new HashMap<String, Object>();

		ConstructionNature consNature = (ConstructionNature) commonService
				.getAnObjectByAnyUniqueColumn("ConstructionNature", "id",
						constructionNature.getId() + "");
		model.put("consNature", consNature);
		
		// Added By Taleb
		String roleName = commonService.getAuthRoleName();
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/constructionNature", model);
		} else {
			return new ModelAndView("contractor/constructionNature", model);
			
		}
	}

	@RequestMapping(value = "/update/ConstructionNature.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateConstructionNature(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ConstructionNature constructionNatureList = gson.fromJson(json,
					ConstructionNature.class);
			Integer id = constructionNatureList.getId();
			String safetyMargin = constructionNatureList.getName();
			String username = commonService.getAuthUserName();
			Date now = new Date();
			ConstructionNature constructionNature = (ConstructionNature) commonService
					.getAnObjectByAnyUniqueColumn("ConstructionNature", "id",
							id + "");
			constructionNature.setModifiedBy(username);
			constructionNature.setModifiedDate(now);
			constructionNature.setName(safetyMargin);
			commonService.saveOrUpdateModelObjectToDB(constructionNature);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
	
	//Added by: Ihetshamul Alam
	
	@RequestMapping(value = "/checkJobNo.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkJobNo(PndJobMst pndJobMst) {
		String response = "";
		if ( pndJobMst.getJobNo().equals("") ) {
			response = "invalid";
		} else {
			PndJobMst pndJob = (PndJobMst) commonService
					.getAnObjectByAnyUniqueColumn("PndJobMst", "jobNo",
							pndJobMst.getJobNo());
			if (pndJob == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}
	
	
	//Added By: Ihetshamul Alam
	@RequestMapping(value = "/checkPdNo.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkPdNo(PndJobMst pndJobMst) {
		String response = "";
		if ( pndJobMst.getPdNo().equals("") ) {
			response = "invalid";
		} else {
			PndJobMst pndJob = (PndJobMst) commonService
					.getAnObjectByAnyUniqueColumn("PndJobMst", "pdNo",
							pndJobMst.getPdNo());
			if (pndJob == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}
	
	
	//added by Taleb instruction by Alamin
	@RequestMapping(value = "/assignSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String jobAssignSave(PndJobBean pndJobBean) {
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		
		String descoDeptCode = department.getDescoCode();
		
		String contractorPk = pndJobBean.getWoNumber();
		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "id", contractorPk);

		CostEstimationMst costMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "pndNo",
						pndJobBean.getPndNo());
		PndJobMst jobMst = new PndJobMst();

		jobMst.setActive(true);
		jobMst.setCreatedBy(userName);
		jobMst.setCreatedDate(now);
		jobMst.setWoNumber(contractor.getContractNo());
		jobMst.setContractor(contractor);

		jobMst.setJobNo(pndJobBean.getJobNo());
		jobMst.setJobTitle(pndJobBean.getJobTitle());
		jobMst.setJobLocation(pndJobBean.getJobLocation());
		jobMst.setPndNo(pndJobBean.getPndNo());
		jobMst.setPdNo(pndJobBean.getPdNo());
		jobMst.setName(costMst.getName());
		jobMst.setAddress(costMst.getAddress());
		jobMst.setLot(pndJobBean.getLot());
		jobMst.setConstructionNature(pndJobBean.getConstructionNature());
		jobMst.setRemarks(pndJobBean.getRemarks());
		
		String autoJobNo = commonService.getOperationIdByPrefixAndSequenceName(
				jobAssignPrefix, descoDeptCode, separator,
				"JOB_ASSIGN_SEQ");
		
		jobMst.setAutoJobNo(autoJobNo);
		pndJobDtlService.addPndJobMst(jobMst);

		// Save PndJobDtl
		savePndJobDtlList(pndJobBean, jobMst);
		
		// add history
		// Get All Approval Hierarchy on JOB_ASSIGN_TO_PROJECT_CONTRACTOR
		
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
			.getApprovalHierarchyByRoleAndOperation(roleName, JOB_ASSIGN_TO_PROJECT_CONTRACTOR);
			
		/*List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(JOB_ASSIGN_TO_PROJECT_CONTRACTOR);*/
		this.addJobAssignHierarchyHistory(jobMst, approvalHierarchy,
				roleName, department, authUser);
		
		// Update Cost_Est_Mst and Set Flag.
		// So that, P&D number can not use twice.
		CostEstimationMst cMst = (CostEstimationMst) commonService
				.getAnObjectByAnyUniqueColumn("CostEstimationMst", "pndNo",
						pndJobBean.getPndNo());
		cMst.setDepartment(department);
		cMst.setRoleName(roleName);
		cMst.setAuthUser(authUser);
		//cMst.setAssign(true);
		commonService.saveOrUpdateModelObjectToDB(cMst);
		//return "redirect:/contractor/jobList.do?id=" + contractorPk;
		return "redirect:/job/jobAssignPendingList.do";
	}
	
	private void addJobAssignHierarchyHistory(PndJobMst jobMst, ApprovalHierarchy approvalHierarchy,
			String roleName, Departments department, AuthUser authUser){
		JobAssignApprovalHierarchyHistory approvalHierarchyHistory = new JobAssignApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(jobMst.getAutoJobNo());
		approvalHierarchyHistory.setOperationName(JOB_ASSIGN_TO_PROJECT_CONTRACTOR);
		approvalHierarchyHistory.setCreatedBy(jobMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(jobMst.getCreatedDate());
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);
		// added by taleb
		approvalHierarchyHistory.setTargetUserId(authUser.getUserid());
		approvalHierarchyHistory.setStateCode(approvalHierarchy.getStateCode());
		approvalHierarchyHistory.setStateName(approvalHierarchy
					.getStateName());

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
	}
	
	//added by Taleb instruction by Alamin
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobAssignPendingList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobAssignPendingList(){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			
			List<JobAssignApprovalHierarchyHistory> jobEstimationHistoryList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(
							"JobAssignApprovalHierarchyHistory", deptId,
							roleName, OPEN, userName);
			
			List<String> operationIdList = new ArrayList<String>();
			for (int i = 0; i < jobEstimationHistoryList.size(); i++) {
				operationIdList.add(jobEstimationHistoryList.get(i)
						.getOperationId());				
			}
			
			List<PndJobMst> jobMstList = (List<PndJobMst>) (Object)
					commonService.getObjectListByAnyColumnValueList(
							"PndJobMst", "autoJobNo", operationIdList);
			model.put("jobMstList", jobMstList);
			if (roleName.contains(ROLE_PROJECT_)) {
				return new ModelAndView(
						"contractor/projects/jobAssignPendingList", model);
			} else {
				return new ModelAndView(
						"contractor/dnpd/jobAssignPendingList", model);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assignDetailsShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobDetails(PndJobDtl pndJobDtl) {
		String userName = commonService.getAuthUserName();

		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		
		PndJobMst pndJobMst = (PndJobMst) commonService
				.getAnObjectByAnyUniqueColumn("PndJobMst", "id",
						pndJobDtl.getId() + "");
		
		String operationId = pndJobMst.getAutoJobNo();
		
		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", pndJobMst.getWoNumber());

		List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>) (Object) commonService
				.getObjectListByAnyColumn("PndJobDtl", "pndJobMst.id",
						pndJobDtl.getId() + "");
		
		// start added for hierarchy
		
		// operation Id which selected by login user
		String currentStatus = "";

		List<JobAssignApprovalHierarchyHistory> jobAssaignApprovalHierarchyHistoryList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobAssignApprovalHierarchyHistory",
						JOB_ASSIGN_TO_PROJECT_CONTRACTOR, operationId, DONE);

		if (!jobAssaignApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = jobAssaignApprovalHierarchyHistoryList.get(
					jobAssaignApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<JobAssignApprovalHierarchyHistory> jobAssignApprovalHierarchyHistoryOpenList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"JobAssignApprovalHierarchyHistory",
						JOB_ASSIGN_TO_PROJECT_CONTRACTOR, operationId, OPEN);

		int currentStateCode = jobAssignApprovalHierarchyHistoryOpenList
				.get(jobAssignApprovalHierarchyHistoryOpenList.size() - 1)
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
				.getApprovalHierarchyByOperationName(JOB_ASSIGN_TO_PROJECT_CONTRACTOR);
		
		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}			
		}

		// added by Taleb for Send To
		for (ApprovalHierarchy approvalHierarchy : nextManReqProcs) {
			Roles role = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					approvalHierarchy.getRoleName());
			List<AuthUser> authUserList = (List<AuthUser>) (Object) commonService
					.getObjectListByTwoColumn(
							"com.ibcs.desco.admin.model.AuthUser", "roleid",
							role.getRole_id() + "", "deptId", deptId);
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
		if (!jobAssignApprovalHierarchyHistoryOpenList.isEmpty()
				&& jobAssignApprovalHierarchyHistoryOpenList != null) {
			// deciede for return or not
			returnStateCode = jobAssignApprovalHierarchyHistoryOpenList
					.get(jobAssignApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
		}
		//end
		

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pndJobDtlList", pndJobDtlList);
		model.put("contractor", contractor);
		model.put("pndJobMst", pndJobMst);
		model.put("returnStateCode", returnStateCode);
		model.put("currentStatus", currentStatus);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approvalHierarchyHistoryList",
				jobAssaignApprovalHierarchyHistoryList);
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/assignDetailsShow", model);
		} else {
			return new ModelAndView(
					"contractor/dnpd/assignDetailsShow", model);				
		}		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assignJobApproved.do", method = RequestMethod.POST)
	public ModelAndView assignJobApproved(
			@ModelAttribute("pndJobBean") PndJobBean pndJobBean) {
		String pndPkNo = pndJobBean.getPndNo();
		String justification = pndJobBean.getJustification();

		
		PndJobMst pndJobMstDb = (PndJobMst)
				commonService.getAnObjectByAnyUniqueColumn(
						"PndJobMst", "id", pndPkNo);
		// get Current User and Role Information

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<JobAssignApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobAssignApprovalHierarchyHistory",
							"operationId", pndJobMstDb.getAutoJobNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			JobAssignApprovalHierarchyHistory approvalHierarchyHistory = (JobAssignApprovalHierarchyHistory)
					commonService.getAnObjectByAnyUniqueColumn(
							"JobAssignApprovalHierarchyHistory", "id", ids[0].toString());				
					
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							JOB_ASSIGN_TO_PROJECT_CONTRACTOR, currentStateCode + "");
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
			
			pndJobMstDb.setApproved("1");
			pndJobMstDb.setModifiedBy(userName);
			pndJobMstDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(pndJobMstDb);
			
			CostEstimationMst costEstimationMst = (CostEstimationMst)
					commonService.getAnObjectByAnyUniqueColumn(
							"CostEstimationMst", "pndNo", pndJobMstDb.getPndNo());
			costEstimationMst.setAssign(true);
			costEstimationMst.setModifiedBy(userName);
			costEstimationMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(costEstimationMst);
			
			// adjust total Job item Contractor wise
			//added by Taleb instruction By Alamin Bhai
			List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>)(Object)
					commonService.getObjectListByAnyColumn("PndJobDtl", "pndJobMst.id", pndJobMstDb.getId().toString());
			this.adjustContractorWiseJobItems(pndJobDtlList);

			return new ModelAndView(
					"redirect:/job/jobAssignPendingList.do", model);
		}  catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView jobAssignSendTo(
			@ModelAttribute("pndJobBean") PndJobBean pndJobBean) {

		//get From GUI
		String pndPkNo = pndJobBean.getPndNo();
		String justification = pndJobBean.getJustification();
		String nextStateCode = pndJobBean.getStateCode();
		String nextUserId = pndJobBean.getUserid();	
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			// get Current Dept, User and Role Information
			String userName = commonService.getAuthUserName();	
			AuthUser authUser = userService.getAuthUserByUserId(userName);	
			String deptId = authUser.getDeptId();	
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			
			// get auto Job no like operationId		
			PndJobMst pndJobMstDb = (PndJobMst)
					commonService.getAnObjectByAnyUniqueColumn(
							"PndJobMst", "id", pndPkNo);
			String operationId = pndJobMstDb.getAutoJobNo();
			
			List<JobAssignApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobAssignApprovalHierarchyHistory", "operationId",
							operationId);
	
			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy history
			JobAssignApprovalHierarchyHistory approvalHierarchyHistory = (JobAssignApprovalHierarchyHistory)
					commonService.getAnObjectByAnyUniqueColumn("JobAssignApprovalHierarchyHistory", 
							"id", ids[0].toString());
			
			int currentStateCode = approvalHierarchyHistory.getStateCode();
	
			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							JOB_ASSIGN_TO_PROJECT_CONTRACTOR, currentStateCode + "");
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
			JobAssignApprovalHierarchyHistory approvalHierarchyHistoryNextState = new JobAssignApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							JOB_ASSIGN_TO_PROJECT_CONTRACTOR, nextStateCode + "");
			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
	
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(nextStateCode));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(operationId);
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
	
			return new ModelAndView(
					"redirect:/job/jobAssignPendingList.do", model);
		}  catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backTo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView assignJobBackTo(
			@ModelAttribute("pndJobBean") PndJobBean pndJobBean) {
		
		//get From GUI
		String pndPkNo = pndJobBean.getPndNo();
		String justification = pndJobBean.getJustification();
		String backStateCode = pndJobBean.getStateCode();
		// added by Taleb
		String backedUserId = pndJobBean.getUserid();
		
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			// get Current User and Role Information
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();	
			AuthUser authUser = userService.getAuthUserByUserId(userName);	
			String deptId = authUser.getDeptId();	
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
	
			// get auto Job no like operationId		
			PndJobMst pndJobMstDb = (PndJobMst)
					commonService.getAnObjectByAnyUniqueColumn(
							"PndJobMst", "id", pndPkNo);
			String operationId = pndJobMstDb.getAutoJobNo();
			
			List<JobAssignApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<JobAssignApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"JobAssignApprovalHierarchyHistory", "operationId",
							operationId);
	
			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy history
			JobAssignApprovalHierarchyHistory approvalHierarchyHistory = (JobAssignApprovalHierarchyHistory)
					commonService.getAnObjectByAnyUniqueColumn("JobAssignApprovalHierarchyHistory", 
							"id", ids[0].toString());
			
			int currentStateCode = approvalHierarchyHistory.getStateCode();
	
			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							JOB_ASSIGN_TO_PROJECT_CONTRACTOR, currentStateCode + "");
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
			JobAssignApprovalHierarchyHistory approvalHierarchyHistoryBackState = new JobAssignApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							JOB_ASSIGN_TO_PROJECT_CONTRACTOR, backStateCode + "");
			approvalHierarchyHistoryBackState.setActive(true);
			approvalHierarchyHistoryBackState.setCreatedBy(userName);
			approvalHierarchyHistoryBackState.setCreatedDate(new Date());
			approvalHierarchyHistoryBackState.setStatus(OPEN);
			approvalHierarchyHistoryBackState
					.setStateName(approvalHierarchyBackSate.getStateName());
			approvalHierarchyHistoryBackState.setStateCode(Integer
					.parseInt(backStateCode));
			approvalHierarchyHistoryBackState.setId(null);
			approvalHierarchyHistoryBackState.setOperationId(operationId);
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
	
			return new ModelAndView(
					"redirect:/job/jobAssignPendingList.do", model);
		}  catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}
}
