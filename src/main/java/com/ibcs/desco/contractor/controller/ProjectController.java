package com.ibcs.desco.contractor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.inventory.model.AllLookUp;


@Controller
@RequestMapping(value="/pd")
public class ProjectController  extends Constrants{
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentsService departmentsService;
	
	Date now = new Date();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/createProject.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView createProject() {
			
		List<DescoKhath> descoKhathList = (List<DescoKhath>)(Object) 
				commonService.getAllObjectList("DescoKhath");
		List<String> projectDeptIds = new ArrayList<String>();
		for (DescoKhath descoKhath : descoKhathList) {
			projectDeptIds.add(descoKhath.getDepartment().getId().toString());
		}
		
		List<Departments> departments = (List<Departments>)(Object)
		commonService.getObjectListByAnyColumnValueNotInListAndOneColumn("Departments", 
				"id", projectDeptIds, "parent", PD_PARENT_CODE);
		
		List<AllLookUp> sourceOfFund = (List<AllLookUp>) (Object) commonService
				.getObjectListByAnyColumn("AllLookUp", "parentName",
						SOURCE_OF_FUND);
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sourceOfFund", sourceOfFund);
		model.put("departments", departments);
		
		return new ModelAndView("contractor/createProject", model);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/projectSave.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getProjectSave( DescoKhath descokhath ) {
		String username = commonService.getAuthUserName();
		Date now = new Date();
		
		Departments department = (Departments)
				commonService.getAnObjectByAnyUniqueColumn("Departments", "id", descokhath.getDeptId());
		
		DescoKhath dsKhath = new DescoKhath(null, descokhath.getKhathName(), "PROJECT", descokhath.getDescription(),
				descokhath.getSourceOfFund(), descokhath.getPdName(), descokhath.getStartDate(),
				descokhath.getEndDate(), descokhath.getDuration(), descokhath.isActive(),
				"", username, now, department);
		commonService.saveOrUpdateModelObjectToDB(dsKhath);
		
		/*Integer MaxKhathID = ( Integer ) commonService.getMaxValueByObjectAndColumn( "DescoKhath", "id" );
		
		DescoKhath descoKhathLastRow = ( DescoKhath ) commonService.getAnObjectByAnyUniqueColumn( "DescoKhath", "id", MaxKhathID + "" );
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("descoKhathProjectInfo", descoKhathLastRow);
		//return this.createProject(); */
		List<DescoKhath> projectList = ( List<DescoKhath> ) (Object) commonService.getAllObjectList(
				"DescoKhath");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<AllLookUp> sourceOfFund = (List<AllLookUp>) (Object) commonService
				.getObjectListByAnyColumn("AllLookUp", "parentName",
						SOURCE_OF_FUND);
		
		model.put("sourceOfFund", sourceOfFund);
		model.put("projectList", projectList);
		
		return new ModelAndView("contractor/projectList", model);
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/allProjectList.do", method=RequestMethod.GET)
	public ModelAndView projectList() {
		List<DescoKhath> projectList = ( List<DescoKhath> ) (Object) commonService.getAllObjectList(
				"DescoKhath");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<AllLookUp> sourceOfFund = (List<AllLookUp>) (Object) commonService
				.getObjectListByAnyColumn("AllLookUp", "parentName",
						SOURCE_OF_FUND);
		
		model.put("sourceOfFund", sourceOfFund);
		model.put("projectList", projectList);
		
		return new ModelAndView("contractor/projectList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping( value="/projectList.do", method=RequestMethod.GET )
	public String getProjectInformation( DescoKhath descoKhath, Map<String, Object> model ) {
		DescoKhath descoKhathProjectInfo = ( DescoKhath ) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "id", descoKhath.getId() + "");
		
		
		/*List<Contractor> contractorList = (List<Contractor>) (Object) commonService
		.getObjectListByTwoColumn("Contractor", "active", "1", "khathId", descoKhath.getId() + "");*/
		
		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByThreeColumn("Contractor", "active", "1","khathId", descoKhath.getId() + "", "contractorType", PROJECT);
		
		String userName = commonService.getAuthUserName();
		//String roleName = commonService.getAuthRoleName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		model.put("contractorList", contractorList);
		model.put("descoKhathProjectInfo", descoKhathProjectInfo);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return "contractor/projectInfoShow";
	}
	
	//Added by: Ihteshamul Alam
		@RequestMapping(value="/updateProjectInfo.do", method=RequestMethod.POST)
		@ResponseBody
		public String updateContractorInfo( DescoKhath descoKhath ) {
			String response = "";
			
			Integer id = descoKhath.getId();
			
			DescoKhath dsKhath = ( DescoKhath ) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id", id.toString());
			if( dsKhath != null ) {

				dsKhath.setKhathName( descoKhath.getKhathName() );
				dsKhath.setSourceOfFund( descoKhath.getSourceOfFund() );
				dsKhath.setPdName( descoKhath.getPdName() );
				dsKhath.setDescription( descoKhath.getDescription() );
				dsKhath.setModifiedBy( commonService.getAuthUserName() );
				dsKhath.setModifiedDate( new Date() );
				
				commonService.saveOrUpdateModelObjectToDB( dsKhath );
				
				response = "success";
			} else {
				response = "failed";
			}
			return response;
		}
}
