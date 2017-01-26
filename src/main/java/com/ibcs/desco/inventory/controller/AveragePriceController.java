package com.ibcs.desco.inventory.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSProcItemRcvMst;
import com.ibcs.desco.inventory.bean.CostSetupBean;
import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;

@Controller
public class AveragePriceController {

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;

	@RequestMapping(value = "/inventory/averageprice.do", method = RequestMethod.GET)
	public String showItemGroup(Model model) {

		// ItemCategory itemCategory = new ItemCategory();
		// model.addAttribute(itemCategory);
		return "/inventory/item_wise_average_rate";
	}

	@RequestMapping(value = "/inventory/saveaverageprice.do", method = RequestMethod.POST)
	public String addItemGroup(
			@ModelAttribute("itemCategory") @Valid AvgPrice ap,
			BindingResult result, Model model, HttpServletRequest req,
			Principal principal) {

		// if (result.hasErrors()) {
		// return "/inventory/itemGroupForm";
		// }
		// String userName = principal.getName();
		// Date today = new Date();
		// ap.getItemCode();
		/*
		 * if (StringUtils.isEmpty(req.getParameter("itemCode"))) {
		 * itemCategory.setActiveItemCategory(0); } else {
		 * itemCategory.setActiveItemCategory(1); }
		 * 
		 * itemCategory.setCreatedBy(userName);
		 * itemCategory.setCreatedDate(today);
		 * 
		 * itemGroupService.addItemGroup(itemCategory);
		 */
		commonService.saveOrUpdateModelObjectToDB(ap);
		model.addAttribute(new ItemCategory());
		return "redirect:/inventory/averagePriceList.do";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/averagePriceList.do", method = RequestMethod.GET)
	public ModelAndView showItemAveragePriceList() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<AvgPrice> avgPriceList = (List<AvgPrice>) (Object) commonService
				.getAllObjectList("AvgPrice");
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getAllObjectList("ItemMaster");
		Map<String, String> iMap = new HashMap<String, String>();
		for (ItemMaster item : itemMasterList) {
			iMap.put(item.getItemId(), item.getItemName());
		}
		for (AvgPrice avg : avgPriceList) {
			avg.setItemName(iMap.get(avg.getItemCode()));
		}
		model.put("avgPriceList", avgPriceList);
		return new ModelAndView("inventory/averagePriceList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/receivedReportList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView receivedReportList() {
		List<CSProcItemRcvMst> csProcItemRcvMst = (List<CSProcItemRcvMst>) (Object) commonService
				.getObjectListByTwoColumn("CSProcItemRcvMst", "approved", "1",
						"priceSet", "0");
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("receivedReportList", csProcItemRcvMst);
		return new ModelAndView("inventory/receivedReportList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/receivedReportShow.do", method = RequestMethod.POST)
	public ModelAndView receivedReportShow(String id) {
		CSProcItemRcvMst rrMst = (CSProcItemRcvMst) commonService
				.getAnObjectByAnyUniqueColumn("CSProcItemRcvMst", "rrNo", id);
		List<CSProcItemRcvDtl> rrDtlList = (List<CSProcItemRcvDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSProcItemRcvDtl", "rrNo",
						rrMst.getRrNo());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rrMst", rrMst);
		model.put("csItemReceivedList", rrDtlList);

		return new ModelAndView("inventory/receivedReportShow", model);
	}

	@RequestMapping(value = "/inventory/saveUnitPrices.do", method = RequestMethod.POST)
	public ModelAndView saveUnitPrices(
			@ModelAttribute("bean") CostSetupBean bean) {
		String rrMstId = bean.getRrMstId().toString();
		List<String> itemCodeList = bean.getItemCodeList();
		List<Double> quantityList = bean.getQuantityList();
		List<Double> unitCostList = bean.getUnitCostList();

		for (int i = 0; i < itemCodeList.size(); i++) {
			AvgPrice avg = (AvgPrice) commonService
					.getAnObjectByAnyUniqueColumn("AvgPrice", "itemCode",
							itemCodeList.get(i));

			if (avg == null) {
				avg = new AvgPrice();
				avg.setId(null);
				avg.setItemCode(itemCodeList.get(i));
				avg.setItemQtyTotal(quantityList.get(i));
				avg.setLast_pur_item_qty(quantityList.get(i));

				Double price = unitCostList.get(i);
				DecimalFormat df = new DecimalFormat("#.00");
				price = Double.valueOf(df.format(price));

				avg.setPrice(price);
				avg.setLast_pur_item_price(price);

				commonService.saveOrUpdateModelObjectToDB(avg);

			} else {
				Double dbQty = avg.getItemQtyTotal();
				Double dbPrice = avg.getPrice();
				Double dbTotalPrice = dbQty * dbPrice;

				Double nQty = quantityList.get(i);
				Double nPrice = unitCostList.get(i);
				Double nTotalPrice = nQty * nPrice;

				Double tQty = dbQty + nQty;
				Double tTotalPrice = dbTotalPrice + nTotalPrice;

				Double avgPrice = tTotalPrice / tQty;
				DecimalFormat df = new DecimalFormat("#.00");
				avgPrice = Double.valueOf(df.format(avgPrice));

				avg.setItemQtyTotal(tQty);
				avg.setPrice(avgPrice);
				avg.setLast_pur_item_price(nPrice);
				avg.setLast_pur_item_qty(nQty);

				commonService.saveOrUpdateModelObjectToDB(avg);
			}
		}
		CSProcItemRcvMst rrMst = (CSProcItemRcvMst) commonService
				.getAnObjectByAnyUniqueColumn("CSProcItemRcvMst", "id", rrMstId);
		rrMst.setPriceSet(true);
		commonService.saveOrUpdateModelObjectToDB(rrMst);

		return receivedReportList();
	}

	@RequestMapping(value = "/inventory/averageControlReport.do", method = RequestMethod.GET)
	public ModelAndView averageControlReport() {
		return new ModelAndView("inventory/averageControlReport");
	}
}
