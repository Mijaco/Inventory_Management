package com.ibcs.desco.localStore.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.service.CommonService;

@Controller
@RequestMapping(value = "/ls")
public class LocalStoreController {

	@Autowired
	CommonService commonService;
	
	@Autowired
	 UserService userService;

	 @Autowired
	 DepartmentsService departmentsService;

	@RequestMapping(value = "/store/gatePassReport.do", method = RequestMethod.GET)
	public ModelAndView gatePassReport() {
		return new ModelAndView("localStore/gatePassReport");
	}
	
	@RequestMapping(value = "/store/requisitionReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionReport() {
		return new ModelAndView("localStore/requisitionReport");
	}

	@RequestMapping(value = "/store/requisitionStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionStoreTicketReport() {
		return new ModelAndView("localStore/requisitionStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/returnSlipReport.do", method = RequestMethod.GET)
	public ModelAndView returnSlipReport() {
		return new ModelAndView("localStore/returnSlipReport");
	}
	
	@RequestMapping(value = "/store/returnStoreTicketReport.do", method = RequestMethod.GET)
	public ModelAndView returnStoreTicketReport() {
		return new ModelAndView("localStore/returnStoreTicketReport");
	}
	
	@RequestMapping(value = "/store/lsLedgerReport.do", method = RequestMethod.GET)
	public ModelAndView lsLedgerReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();

		  AuthUser authUser = userService.getAuthUserByUserId(userName);
		  String deptId = authUser.getDeptId();
		  
		  Departments department = departmentsService.getDepartmentByDeptId(deptId);
		  model.put("department", department);
		return new ModelAndView("localStore/lsLedgerReport", model);
	}
	
}
