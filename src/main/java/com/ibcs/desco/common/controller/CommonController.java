package com.ibcs.desco.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.RoleService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.OperationService;
import com.ibcs.desco.common.service.StateService;

@Controller
@RequestMapping(value = "/common")
public class CommonController {

	private RoleService roleService;

	private ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Autowired
	OperationService operationService;

	@Autowired
	StateService stateService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ApprovalHierarchyService getApprovalHierarchyService() {
		return approvalHierarchyService;
	}

	public void setApprovalHierarchyService(
			ApprovalHierarchyService approvalHierarchyService) {
		this.approvalHierarchyService = approvalHierarchyService;
	}

	@RequestMapping(value = "/approvalHierarchySave.do", method = RequestMethod.POST)
	public String approvalHierarchySave(
			Model model,
			@ModelAttribute("approvalHierarchy") ApprovalHierarchy approvalHierarchy) {
		approvalHierarchy.setCreatedDate(new Date());

		approvalHierarchyService.addApprovalHierarchy(approvalHierarchy);

		return "redirect:/common/approvalHierarchyList.do";

	}

	@RequestMapping(value = "/searchByRoleApprovalHierarchy.do", method = RequestMethod.POST)
	public ModelAndView searchByRoleApprovalHierarchy(
			@ModelAttribute("approvalHierarchy") ApprovalHierarchy approvalHierarchy) {

		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByRoleName(approvalHierarchy.getRoleName());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchyList", approvalHierarchyList);
		return new ModelAndView("common/approvalHierarchyList", model);
	}

	@RequestMapping(value = "/approvalHierarchyUpdate.do", method = RequestMethod.POST)
	public String approvalHierarchyUpdate(
			Model model,
			@ModelAttribute("approvalHierarchy") ApprovalHierarchy approvalHierarchy) {

		ApprovalHierarchy approvalHierarchydb = approvalHierarchyService
				.getApprovalHierarchy(approvalHierarchy.getId());
		approvalHierarchydb.setModifiedDate(new Date());
		approvalHierarchydb.setActive(approvalHierarchy.isActive());
		approvalHierarchydb.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchydb.setButtonName(approvalHierarchy.getButtonName());
		approvalHierarchydb.setModifiedBy(commonService.getAuthUserName());
		approvalHierarchydb.setOperationName(approvalHierarchy
				.getOperationName());
		approvalHierarchydb.setStateCode(approvalHierarchy.getStateCode());
		approvalHierarchydb.setStateName(approvalHierarchy.getOperationName());
		approvalHierarchydb.setRoleName(approvalHierarchy.getRoleName());
		approvalHierarchydb.setRemarks(approvalHierarchy.getRemarks());

		approvalHierarchyService.addApprovalHierarchy(approvalHierarchydb);

		return "redirect:/common/approvalHierarchyList.do";

	}

	@RequestMapping(value = "/approvalHierarchyList.do", method = RequestMethod.GET)
	public ModelAndView approvalHierarchyList() {

		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.listApprovalHierarchys();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchyList", approvalHierarchyList);
		return new ModelAndView("common/approvalHierarchyList", model);
	}

	@RequestMapping(value = "/deleteApprovalHierarchy.do", method = RequestMethod.GET)
	public String deleteApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {

		approvalHierarchyService.deleteApprovalHierarchy(approvalHierarchy);
		return "redirect:/common/approvalHierarchyList.do";
	}

	@RequestMapping(value = "/editApprovalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView editApprovalHierarchy(
			ApprovalHierarchy approvalHierarchy) {

		List<Roles> roleList = roleService.listRoles();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("operationList", operationService.listOperations());
		model.put("stateList", stateService.listStates());
		model.put("approvalHierarchyList",
				approvalHierarchyService.listApprovalHierarchys());
		model.put("approvalHierarchy", approvalHierarchyService
				.getApprovalHierarchy(approvalHierarchy.getId()));
		return new ModelAndView("common/approvalHierarchyEdit", model);
	}

	@RequestMapping(value = "/showApprovalHierarchy.do", method = RequestMethod.GET)
	public ModelAndView showApprovalHierarchy(
			ApprovalHierarchy approvalHierarchy) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("approvalHierarchy", approvalHierarchyService
				.getApprovalHierarchy(approvalHierarchy.getId()));
		return new ModelAndView("common/approvalHierarchyShow", model);
	}

	@RequestMapping(value = "/approvalHierarchyForm.do", method = RequestMethod.GET)
	public ModelAndView approvalHierarchyForm() {

		List<Roles> roleList = roleService.listRoles();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("operationList", operationService.listOperations());
		model.put("stateList", stateService.listStates());
		model.put("approvalHierarchyList",
				approvalHierarchyService.listApprovalHierarchys());
		return new ModelAndView("common/approvalHierarchyForm", model);
	}

}
