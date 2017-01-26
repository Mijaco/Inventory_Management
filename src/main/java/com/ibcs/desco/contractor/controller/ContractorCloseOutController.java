package com.ibcs.desco.contractor.controller;

import java.util.ArrayList;
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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobMst;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;

@Controller
@RequestMapping(value = "/co")
@PropertySource("classpath:common.properties")
public class ContractorCloseOutController  extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;
	
	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;
	
	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;
	
	@Value("${desco.project.rootPath}")
	private String descoRootPath;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.requisition.unlock.expired.hours}")
	private String unlockExpiredHours;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/asBuilt.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAsBuilt() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn(
							"DescoKhath",
							"department.id", department.getId().toString()); 
			
			List<Contractor> contractorList = (List<Contractor>) (Object) commonService
					.getObjectListByThreeColumnWithOneNullValue(
							"com.ibcs.desco.contractor.model.Contractor",
							"khathId", descoKhath.getId().toString(), 
							"contractorType", PROJECT, "closeOut");
			
			model.put("contractorList", contractorList);
			model.put("department", department);

			return new ModelAndView("contractor/projects/asBuiltForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("common/errorHome", model);
		}
	}
	
	// Taleb
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/getContractorInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String quantityValidation(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			Contractor contractor = gson.fromJson(cData,
					Contractor.class);
			Integer contractorPk = contractor.getId();
			
			Contractor contractorDb = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor",
							"id", contractorPk.toString().trim());
			
			List<PndJobMst> pndJobMstList = (List<PndJobMst>)(Object)
					commonService.getObjectListByThreeColumnWithOneNullValue(
							"PndJobMst", "contractor.id", contractorPk.toString(), 
							"approved", "1", "asBuildNo");
			contractorDb.setPndJobMstList(pndJobMstList);
			
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			
			toJson = ow.writeValueAsString(contractorDb);			

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Fail");
		}
		return toJson;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractor/closeOut.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorCloseOut(Contractor contractor) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();
			
			Contractor contractorDb = (Contractor) 
					commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor", "id", contractor.getId().toString());
			
			List<ContractorRepresentive> contRepList = (List<ContractorRepresentive>)(Object)
					commonService.getObjectListByAnyColumn(
							"ContractorRepresentive", "contractor.id", contractor.getId().toString());
			
			List<String> userIdList = new ArrayList<String>();
			for (ContractorRepresentive cr : contRepList) {
				userIdList.add(cr.getUserId());
			}
			
			List<AuthUser> userList = (List<AuthUser>)(Object)
					commonService.getObjectListByAnyColumnValueList(
							"com.ibcs.desco.admin.model.AuthUser", "userid", userIdList);
			
			for (AuthUser au : userList) {
				au.setActive(false);
				au.setLocked(1);
				au.setModifiedBy(userName);
				au.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(au);
			}
			
			contractorDb.setCloseOut("CLOSE OUT");
			contractorDb.setModifiedBy(userName);
			contractorDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(contractorDb);
			
			return new ModelAndView("redirect:/contractor/finalList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("common/errorHome", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractor/forcefullyCloseOut.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView forcefullyCloseOut(Contractor contractor, @RequestParam("closeOutInfo") MultipartFile closeOutInfo) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();
			
			Contractor contractorDb = (Contractor) 
					commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor", "id", contractor.getId().toString());
			
			List<ContractorRepresentive> contRepList = (List<ContractorRepresentive>)(Object)
					commonService.getObjectListByAnyColumn(
							"ContractorRepresentive", "contractor.id", contractor.getId().toString());
			
			List<String> userIdList = new ArrayList<String>();
			for (ContractorRepresentive cr : contRepList) {
				userIdList.add(cr.getUserId());
			}
			
			List<AuthUser> userList = (List<AuthUser>)(Object)
					commonService.getObjectListByAnyColumnValueList(
							"com.ibcs.desco.admin.model.AuthUser", "userid", userIdList);
			
			for (AuthUser au : userList) {
				au.setActive(false);
				au.setLocked(1);
				au.setModifiedBy(userName);
				au.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(au);
			}
			
			contractorDb.setCloseOut("FORCEFULLY CLOSE OUT");
			contractorDb.setModifiedBy(userName);
			contractorDb.setModifiedDate(new Date());	
			String filePath = commonService.saveFileToDrive(closeOutInfo, descoRootPath,
					"fco_doc");
			contractorDb.setFcoDoc(filePath);
			contractorDb.setReasionCloseOut(contractor.getReasionCloseOut());
			contractorDb.setPaymentDetails(contractor.getPaymentDetails());
			commonService.saveOrUpdateModelObjectToDB(contractorDb);
			
			return new ModelAndView("redirect:/contractor/finalList.do");
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("common/errorHome", model);
		}
	}
	
	@RequestMapping(value = "/contractor/reports.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorReports( Contractor contractor ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		Integer contractorId = contractor.getId();
		model.put("contractorId", contractorId);
		
		return new ModelAndView("contractor/projects/asBuiltReport", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCloseOutForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getContractorCloseOutForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn(
							"DescoKhath",
							"department.id", department.getId().toString()); 
			
			List<Contractor> contractorList = (List<Contractor>) (Object) commonService
					.getObjectListByThreeColumnWithOneNullValue(
							"com.ibcs.desco.contractor.model.Contractor",
							"khathId", descoKhath.getId().toString(), 
							"contractorType", PROJECT, "closeOut");
			
			model.put("contractorList", contractorList);
			model.put("department", department);

			return new ModelAndView("contractor/projects/contractorCloseOut", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("common/errorHome", model);
		}
	}
}
