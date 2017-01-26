package com.ibcs.desco.inventory.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;

@Controller
public class ItemGroupController {

	@Autowired
	ItemGroupService itemGroupService;

	@RequestMapping(value = "/inventory/showitemgroup.do", method = RequestMethod.GET)
	public String showItemGroup(Model model) {
		ItemCategory itemCategory = new ItemCategory();
		model.addAttribute(itemCategory);
		return "/inventory/itemGroupForm";

	}

	@RequestMapping(value = "/inventory/addItemGroup.do", method = RequestMethod.POST)
	public String addItemGroup(@ModelAttribute("itemCategory") @Valid ItemCategory itemCategory, BindingResult result,
			Model model, HttpServletRequest req, Principal principal) {

		if (result.hasErrors()) {
			return "/inventory/itemGroupForm";
		}
		String userName = principal.getName();
		Date today = new Date();

		if (StringUtils.isEmpty(req.getParameter("isActive"))) {
			itemCategory.setActiveItemCategory(0);
		} else {
			itemCategory.setActiveItemCategory(1);
		}

		itemCategory.setCreatedBy(userName);
		itemCategory.setCreatedDate(today);

		itemGroupService.addItemGroup(itemCategory);
		model.addAttribute(new ItemCategory());
		return "redirect:/inventory/listItemgroup.do";

	}
	
	@RequestMapping(value = "/inventory/itemGroupReport.do", method = RequestMethod.GET)
	public ModelAndView openingBalanceReport() {
		System.out.println("====================openingBalanceReport.do===============");
		return new ModelAndView("inventory/itemGroupReport");
}

	@RequestMapping(value = "/inventory/listItemgroup.do", method = RequestMethod.GET)
	public String listItemGroup(Model model) {
		List<ItemCategory> itemGroupList = itemGroupService.getAllItemGroups();
		model.addAttribute("itemGroupList", itemGroupList);
		model.addAttribute(new ItemCategory());
		return "/inventory/listItemGroup";
	}

	@RequestMapping(value = "/inventory/editItemGroup.do", method = RequestMethod.GET)
	public ModelAndView editItemGroup(@ModelAttribute("categoryId") ItemCategory itemCategory, HttpServletRequest req) {
		ItemCategory selectItemGroup = itemGroupService.getGetItemGroupById(itemCategory.getId());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("selectItemGroup", selectItemGroup);
		return new ModelAndView("inventory/itemGroupEdit", model);
	}

	@RequestMapping(value = "/inventory/itemgroupUpdate.do", method = RequestMethod.POST)
	public String updateOpenBalance(@ModelAttribute("id") ItemCategory itemCategory, HttpServletRequest req) {
		itemCategory.setId(Integer.parseInt(req.getParameter("selectitemGroupId")));

		if (StringUtils.isEmpty(req.getParameter("isActive"))) {
			itemCategory.setActiveItemCategory(0);
		} else {
			itemCategory.setActiveItemCategory(1);
		}

		System.out.println(itemCategory);
		itemGroupService.editItemGroup(itemCategory);

		return "redirect:/inventory/listItemgroup.do";
	}

	@RequestMapping(value = "/inventory/showItemGroup.do", method = RequestMethod.GET)
	public ModelAndView showOpeningBalance(@ModelAttribute("categoryId") ItemCategory itemCategory) {
		ItemCategory selectItemGroup = itemGroupService.getGetItemGroupById(itemCategory.getId());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("selectItemGroup", selectItemGroup);
		return new ModelAndView("inventory/itemGroupShow", model);
	}

	@RequestMapping(value = "/inventory/searchByGroupName.do", method = RequestMethod.POST)
	public ModelAndView searchItemByGroupName(ItemCategory itemCategory, HttpServletRequest req) {
		List<ItemCategory> itemGroupList = itemGroupService
				.getItemGroupByGroupName(itemCategory.getCategoryName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("itemGroupList", itemGroupList);

		return new ModelAndView("/inventory/listItemGroup", model);
	}

}
