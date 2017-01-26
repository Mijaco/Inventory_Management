package com.ibcs.desco.budget.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.AllLookUp;

/*
 * @author  		Md. Ahasanul Ashid
 * @company 		IBCS Primax Software Ltd.
 * @Designation  	Programmer
 */

@Controller
public class BudgetReportController extends Constrants {

	@Autowired
	CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/generalItemReport.do", method = RequestMethod.GET)
	public ModelAndView generalItemUploadForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSession", descoSession);
			return new ModelAndView("budget/report/generalItemReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/budgetDetailsRpt.do", method = RequestMethod.GET)
	public ModelAndView budgetDetailsRpt() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			
			//get types of budget from AllLookUp by parent BUDGET_TYPE
			List<AllLookUp> budgetTypes = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName", BUDGET_TYPE);
			
			model.put("descoSession", descoSession);
			model.put("budgetTypes", budgetTypes);
			return new ModelAndView("budget/report/budgetDetailsReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/budgetCostSourceWiseRpt.do", method = RequestMethod.GET)
	public ModelAndView budgetDetailsCosetSourceWiseRpt() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			
			//get types of budget from AllLookUp by parent BUDGET_TYPE
			List<AllLookUp> srcsOfFund = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName", SOURCE_OF_FUND);
			
			model.put("descoSession", descoSession);
			model.put("srcsOfFund", srcsOfFund);
			return new ModelAndView("budget/report/budgetCstSrcReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/budget/budgetExpCatRpt.do", method = RequestMethod.GET)
	public ModelAndView budgetDetailsExpCatRpt() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			
			//get types of budget from AllLookUp by parent BUDGET_TYPE
			List<AllLookUp> expCats = (List<AllLookUp>) (Object) commonService
					.getObjectListByAnyColumn("AllLookUp", "parentName", BUDGET_EXPENDITURE_CATEGORY);
			
			model.put("descoSession", descoSession);
			model.put("expCats", expCats);
			return new ModelAndView("budget/report/budgetExpCatsReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

}
