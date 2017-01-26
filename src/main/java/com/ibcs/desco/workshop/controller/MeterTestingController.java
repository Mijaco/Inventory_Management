package com.ibcs.desco.workshop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.workshop.model.MeterTestingRegister;

@Controller
@RequestMapping(value = "/mt")
@PropertySource("classpath:common.properties")
public class MeterTestingController extends Constrants {

	@Autowired
	CommonService commonService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/meterTestingForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getMeterTestingForm() {
		

		Map<String, Object> model = new HashMap<String, Object>();
		List<Departments> depts = (List<Departments>) (Object) commonService
				.getAllObjectList("Departments");
		model.put("depts", depts);
		return new ModelAndView("workshop/mt/meterTestingForm",
				model);
	}
	
	@RequestMapping(value = "/registerSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String meterTestingRegisterSave(MeterTestingRegister mtr) {
		
		String userName = commonService.getAuthUserName();
		mtr.setCreatedBy(userName);
		mtr.setCreatedDate(new Date());
		mtr.setStatus(TESTING);
		mtr.setId(null); 
		commonService.saveOrUpdateModelObjectToDB(mtr);
		return "redirect:/mt/registerList.do";
	}
	
	// mt/registerShow.do
	@RequestMapping(value = "/registerShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestingRegisterShow(MeterTestingRegister mtr) {
		
		int id = mtr.getId();
		
		MeterTestingRegister meterTestingRegister = (MeterTestingRegister)
				commonService.getAnObjectByAnyUniqueColumn("MeterTestingRegister", "id", id+"");
		
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("meterTestingRegister", meterTestingRegister);
		return new ModelAndView("workshop/mt/meterTestingShow",
				model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registerList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getMeterRegisterList() {
		List<MeterTestingRegister> meterTestingRegisterList = (List<MeterTestingRegister>) (Object) commonService
				.getAllObjectList("MeterTestingRegister");
		
		Map<String, Object> model = new HashMap<String, Object>();		
		model.put("meterTestingRegisterList", meterTestingRegisterList);
		return new ModelAndView("workshop/mt/meterTestingList",
				model);
	}

}
