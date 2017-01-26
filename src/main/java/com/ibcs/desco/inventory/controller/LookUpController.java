package com.ibcs.desco.inventory.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.inventory.model.AllLookUp;
import com.ibcs.desco.inventory.service.LookUpService;

@Controller
public class LookUpController {

	@Autowired
	LookUpService lookUpService;

	@RequestMapping(value = "/inventory/showLookUp.do", method = RequestMethod.GET)
	public ModelAndView showItemGroup() {

		List<AllLookUp> parentList = lookUpService.getAllLookUpParents();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentList", parentList);
		model.put("allLookUp", new AllLookUp());
		String viewName = "/inventory/lookupForm";
		return new ModelAndView(viewName, model);

	}

	@RequestMapping(value = "/inventory/addLookUp.do", method = RequestMethod.POST)
	public String addItemGroup(@ModelAttribute AllLookUp lookUp, Model model, HttpServletRequest req,
			Principal principal) {

		System.out.println(lookUp);
		String parentName = req.getParameter("parentName");
		
		String userName = principal.getName();
		Date today = new Date();

		lookUp.setCreatedBy(userName);
		lookUp.setCreatedDate(today);
		if(!StringUtils.isEmpty(parentName)){
			lookUp.setParentId(lookUpService.getParentID(parentName));
			lookUp.setParentName(parentName);
		}
		
		String titleForSAve = req.getParameter("titleForChild");

		if (StringUtils.isEmpty(req.getParameter("isActive"))) {
			lookUp.setIsActiveCheck(0);
		} else {
			lookUp.setIsActiveCheck(1);
		}

		if (lookUp.getTitle() != null && !lookUp.getTitle().isEmpty()) {
			Integer parentId = lookUpService.getParentID(lookUp.getTitle());
			lookUp.setParentId(parentId);
		}

		lookUp.setTitle(titleForSAve);

		System.out.println("=============================look up : =============" + lookUp);
		lookUpService.addLokUp(lookUp);
		model.addAttribute(new AllLookUp());
		return "redirect:/inventory/lookUpList.do";

	}

	@RequestMapping(value = "/inventory/lookUpList.do", method = RequestMethod.GET)
	public String listItemGroup(Model model) {
		List<AllLookUp> lookUpList = lookUpService.getAllLookUps();
		model.addAttribute("lookUpList", lookUpList);
		model.addAttribute(new AllLookUp());
		return "/inventory/listlookup";
	}

	@RequestMapping(value = "/inventory/parentId.do", method = RequestMethod.GET)
	public @ResponseBody List<Integer> listofParentId() {
		System.out.println("===========list item  group============");
		List<Integer> parentList = lookUpService.getParentIdList();
		for (int i : parentList) {
			System.out.println("=============parentList=========" + i);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("parentList", parentList);
		return parentList;
	}

	@RequestMapping(value = "/inventory/lookupfrom.do")
	public String lookUpForm(Model model) {
		model.addAttribute(new AllLookUp());
		return "/inventory/lookupForm";
	}

	@RequestMapping(value = "/inventory/editLookup.do", method = RequestMethod.GET)
	public ModelAndView editOpenBalance(@ModelAttribute("id") AllLookUp lookup) {

		System.out.println("==================edit==================" + lookup);
		AllLookUp selectLookup = lookUpService.getLookUpById(lookup.getId());
		
		List<AllLookUp> parentList = lookUpService.getAllLookUpParents();

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("show open balance " + selectLookup);
		model.put("selectLookup", selectLookup);
		model.put("parentList", parentList);
		return new ModelAndView("inventory/lookupEdit", model);
	}

	@RequestMapping(value = "/inventory/lookupUpdate.do", method = RequestMethod.POST)
	public String updateOpenBalance(@ModelAttribute("id") AllLookUp lookup, HttpServletRequest req) {

		System.out.println("=========edit balance 3 ===========" + req.getParameter("lookupId"));
		if (StringUtils.isEmpty(req.getParameter("isActive"))) {
			lookup.setIsActiveCheck(0);
		} else {
			lookup.setIsActiveCheck(1);
		}
		lookup.setId(Integer.parseInt(req.getParameter("lookupId")));
		lookUpService.editLookUp(lookup);

		return "redirect:/inventory/lookUpList.do";
	}

	@RequestMapping(value = "/inventory/showLookupById.do", method = RequestMethod.GET)
	public ModelAndView showOpeningBalance(@ModelAttribute("id") AllLookUp lookup) {

		AllLookUp selectLookup = lookUpService.getLookUpById(lookup.getId());
		List<AllLookUp> parentList = lookUpService.getAllLookUps();
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("show open balance " + selectLookup);
		model.put("selectLookup", selectLookup);
		model.put("parentList", parentList);
		return new ModelAndView("inventory/lookupShow", model);
	}

	@RequestMapping(value = "/inventory/searchByKeyword.do", method = RequestMethod.POST)
	public ModelAndView searchOpenBalanceByItemName(AllLookUp lookup, HttpServletRequest req) {
		System.out.println("====for item name search ===== " + req.getParameter("keywordForSearch"));
		List<AllLookUp> lookUpList = lookUpService.getLookUpByKeyword(req.getParameter("keywordForSearch"));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lookUpList", lookUpList);

		return new ModelAndView("inventory/listlookup", model);
	}
}
