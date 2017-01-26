package com.ibcs.desco.budget.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.budget.model.CostCentre;
import com.ibcs.desco.common.service.CommonService;

@Controller
public class CostCentreController {
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value="/bgt/costCenterForm.do", method=RequestMethod.GET)
	public ModelAndView costCenterForm() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("budget/costCentre");
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
	
	@RequestMapping(value="/bgt/checkCostCentreCode.do", method=RequestMethod.POST)
	@ResponseBody
	public String checkCostCentreCode( CostCentre costCentre ) {
		String response = "";
		if (costCentre.getCostCentreCode().equals("")) {
			response = "blank";
		} else {
			CostCentre csCentre = ( CostCentre ) commonService .getAnObjectByAnyUniqueColumn("CostCentre",
							"costCentreCode", costCentre.getCostCentreCode());
			if (csCentre == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/bgt/costCenterSave.do", method=RequestMethod.POST)
	public ModelAndView costCenterSave( CostCentre costCentre ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			costCentre.setId(null);
			costCentre.setActive(true);
			costCentre.setCreatedBy( commonService.getAuthUserName() );
			costCentre.setCreatedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB(costCentre);
			
			List<CostCentre> costCentreList = ( List<CostCentre> ) ( Object ) commonService.getAllObjectList("com.ibcs.desco.budget.model.CostCentre");
			model.put("costCentreList", costCentreList);
			
			return new ModelAndView("budget/costCentreList", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/bgt/costCenterList.do", method=RequestMethod.GET)
	public ModelAndView costCenterList() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			List<CostCentre> costCentreList = ( List<CostCentre> ) ( Object ) commonService.getAllObjectList("com.ibcs.desco.budget.model.CostCentre");
			model.put("costCentreList", costCentreList);
			
			return new ModelAndView("budget/costCentreList", model);
			
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/bgt/deleteCostCenter.do", method=RequestMethod.POST)
	public ModelAndView deleteCostCenter( CostCentre costCentre ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = costCentre.getId();
			
			commonService.deleteAnObjectById("CostCentre", id);
			
			List<CostCentre> costCentreList = ( List<CostCentre> ) ( Object ) commonService.getAllObjectList("com.ibcs.desco.budget.model.CostCentre");
			model.put("costCentreList", costCentreList);
			
			return new ModelAndView("budget/costCentreList", model);
			
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
}
