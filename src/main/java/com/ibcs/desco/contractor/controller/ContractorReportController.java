package com.ibcs.desco.contractor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/contractor")
public class ContractorReportController {

	@RequestMapping(value = "/conMaterialReport.do", method = RequestMethod.GET)
	public ModelAndView conMaterialReport() {
		return new ModelAndView("contractor/conMaterialReport");
	}

	@RequestMapping(value = "/conJobReport.do", method = RequestMethod.GET)
	public ModelAndView conJobReport(Model model, String id) {
		model.addAttribute("workOrderNo", id);
		return new ModelAndView("contractor/conJobReport");
	}

}
