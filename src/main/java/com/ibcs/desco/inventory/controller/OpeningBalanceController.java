package com.ibcs.desco.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.model.OpeningBalance;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.inventory.service.OpeningBalanceService;

@Controller
public class OpeningBalanceController {

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	OpeningBalanceService openingBalanceService;

	@RequestMapping(value = "/inventory/listOpeningBalance.do", method = RequestMethod.GET)
	public ModelAndView showOpeningBalanceList() {
		System.out.println("==================opening balance list==============");
		List<OpeningBalance> openingBalanceList = openingBalanceService.getAllIOpeningBalance();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("opningBalanceList", openingBalanceList);
		model.put("openingBalance", new OpeningBalance());
		return new ModelAndView("/inventory/listOpeningBalance", model);
	}
	
	@RequestMapping(value = "/inventory/ItemRate.do", method = RequestMethod.GET)
	public ModelAndView Item_wise_average_rate() {
		System.out.println("==================opening balance list==============");
		List<OpeningBalance> openingBalanceList = openingBalanceService.getAllIOpeningBalance();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("opningBalanceList", openingBalanceList);
		model.put("openingBalance", new OpeningBalance());
		return new ModelAndView("/inventory/item_wise_average_rate", model);
	}
	

	@RequestMapping(value = "/inventory/createOpeningBalance.do", method = RequestMethod.GET)
	public String showOpeningbalance(Model model) {
		System.out.println("========create opeing balnce==============");

		model.addAttribute(new OpeningBalance());
		return "/inventory/openingBalanceForm";
	}

	@RequestMapping(value = "/inventory/saveOpeningBalance.do", method = RequestMethod.POST)
	public String createOpeningbalance(@Valid OpeningBalance openingBalance, BindingResult result) {
		System.out.println("========create opeing balnce==============");
		openingBalanceService.createOpeingBalance(openingBalance);
		return "redirect:/inventory/listOpeningBalance.do";
	}

	@RequestMapping(value = "/inventory/getItemName.do", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getItemName(HttpServletResponse response) {

		System.out.println("===============open balance autocomplete===============");
		List<ItemMaster> inventoryItemList = itemInventoryService.getInventoryItemList();
		String jsonInventoryItemNames = new Gson().toJson(inventoryItemList);
		System.out.println("jsonInventoryItemNames:: " + jsonInventoryItemNames);
		return jsonInventoryItemNames;
	}

	@RequestMapping(value = "/inventory/editOpeningBalance.do", method = RequestMethod.GET)
	public ModelAndView editOpenBalance(@ModelAttribute("id") OpeningBalance openBalance) {

		System.out.println("==================edit==================" + openBalance);
		OpeningBalance selectOpeningBalance = openingBalanceService.getOpenBalanceById(openBalance.getId());

		System.out.println("select opening balance : " + selectOpeningBalance);
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("=====in edit ==============" + selectOpeningBalance);
		model.put("selectOpeningBalance", selectOpeningBalance);
		return new ModelAndView("inventory/openBalanceEdit", model);
	}

	@RequestMapping(value = "/inventory/openBalanceUpdate.do", method = RequestMethod.POST)
	public String updateOpenBalance(@ModelAttribute("id") OpeningBalance openingBalance, HttpServletRequest req) {

		System.out.println("=========edit balance 3 ===========" + req.getParameter("selectOpeningBalanceId"));
		openingBalance.setId(Integer.parseInt(req.getParameter("selectOpeningBalanceId")));
		openingBalanceService.editOpenBalance(openingBalance);

		return "redirect:/inventory/listOpeningBalance.do";
	}
	
	
	@RequestMapping(value = "/inventory/openingBalanceReport.do", method = RequestMethod.GET)
		public ModelAndView openingBalanceReport() {
			System.out.println("====================openingBalanceReport.do===============");
			return new ModelAndView("inventory/openingBalanceReport");
	}
	
	
/*	
		<!--@RequestMapping(value = "/requisition/vendorReport.do", method = RequestMethod.GET)
		public ModelAndView vendorReport() {

			return new ModelAndView("procurement/vendorReport");
	}-->*/

	@RequestMapping(value = "/inventory/showOpeningBalance.do", method = RequestMethod.GET)
	public ModelAndView showOpeningBalance(@ModelAttribute("id") OpeningBalance openingBalance) {

		OpeningBalance selectOpeningBalance = openingBalanceService.getOpenBalanceById(openingBalance.getId());

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("show open balance " + selectOpeningBalance);
		model.put("selectOpeningBalance", selectOpeningBalance);
		return new ModelAndView("inventory/openBalanceShow", model);
	}

	@RequestMapping(value = "/inventory/searchByItemName.do", method = RequestMethod.POST)
	public ModelAndView searchOpenBalanceByItemName(OpeningBalance openingBalance, HttpServletRequest req) {
		System.out.println("====for item name search ===== " + req.getParameter("itemNameForSearch"));
		List<OpeningBalance> openingBalanceList = openingBalanceService
				.getOpeningBalanceByItemName(req.getParameter("itemNameForSearch"));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("opningBalanceList", openingBalanceList);

		return new ModelAndView("/inventory/listOpeningBalance", model);
	}

}
