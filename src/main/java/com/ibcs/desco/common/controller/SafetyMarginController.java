package com.ibcs.desco.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.common.bean.SafetyMarginBean;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.inventory.model.ItemMaster;

@Controller
public class SafetyMarginController extends Constrants {

	@Autowired
	CommonService commonService;

	@RequestMapping(value = "/settings/list/safetyMargin.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView safetymargin() {
		//
		Map<String, Object> model = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhathList", descoKhathList);

		return new ModelAndView("common/safetyMargin", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/settings/viewProjectInformation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView projectInformation(String khathId) {
		// Integer khathId = csItemTransactionMst.getKhathId();
		List<String> ledgerTypeList = new ArrayList<String>();
		ledgerTypeList.add(NEW_SERVICEABLE);
		// ledgerTypeList.add(UNSERVICEABLE);
		List<CSItemTransactionMst> csItemTransactionMstList = (List<CSItemTransactionMst>) (Object) commonService
				.getObjectListByAnyColumnValueListAndOneColumn(
						"CSItemTransactionMst", "ledgerName", ledgerTypeList,
						"khathId", khathId);
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getAllObjectList("ItemMaster");
		Map<String, String> iMap = new HashMap<String, String>();
		for (ItemMaster item : itemMasterList) {
			iMap.put(item.getItemId(), item.getItemName());
		}
		for (CSItemTransactionMst trxnMst : csItemTransactionMstList) {			
			trxnMst.setItemName(iMap.get(trxnMst.getItemCode()));
		}

		/*
		 * for (CSItemTransactionMst trxnMst : csItemTransactionMstList) {
		 * ItemMaster itemMst = (ItemMaster)
		 * commonService.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
		 * trxnMst.getItemCode()); trxnMst.setItemName(itemMst.getItemName()); }
		 */

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("itemTransactionMstList", csItemTransactionMstList);
		model.put("descoKhathList", descoKhathList);
		model.put("khathId", khathId);
		// model.put("itemMaster", itemMasterList);
		return new ModelAndView("common/safetyMargin", model);
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/settings/update/safetyMargin.do",
	 * method=RequestMethod.POST) public String updatesafetymargin(
	 * CSItemTransactionMst csItemTransactionMstList ) { Integer id =
	 * csItemTransactionMstList.getId(); Double safetyMargin =
	 * csItemTransactionMstList.getSafetyMargin(); String username =
	 * commonService.getAuthUserName(); Date now = new Date();
	 * CSItemTransactionMst itemTransactionMst = (CSItemTransactionMst)
	 * commonService.getAnObjectByAnyUniqueColumn( "CSItemTransactionMst", "id",
	 * id + ""); itemTransactionMst.setModifiedBy(username);
	 * itemTransactionMst.setModifiedDate(now);
	 * itemTransactionMst.setSafetyMargin(safetyMargin);
	 * commonService.saveOrUpdateModelObjectToDB(itemTransactionMst); return
	 * "success"; }
	 */

	@ResponseBody
	@RequestMapping(value = "/settings/update/safetyMargin.do", method = RequestMethod.POST)
	public String updatesafetymargin(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			CSItemTransactionMst csItemTransactionMstList = gson.fromJson(json,
					CSItemTransactionMst.class);
			Integer id = csItemTransactionMstList.getId();
			Double safetyMargin = csItemTransactionMstList.getSafetyMargin();
			String username = commonService.getAuthUserName();
			Date now = new Date();
			CSItemTransactionMst itemTransactionMst = (CSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id",
							id + "");
			itemTransactionMst.setModifiedBy(username);
			itemTransactionMst.setModifiedDate(now);
			itemTransactionMst.setSafetyMargin(safetyMargin);
			commonService.saveOrUpdateModelObjectToDB(itemTransactionMst);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	
	@RequestMapping(value = "/settings/update/allSafetyMargin.do", method = RequestMethod.POST)
	public ModelAndView updateAllSafetyMargin(SafetyMarginBean safetyMarginBean) {
		List<Integer> ids = safetyMarginBean.getPkList();
		List<Double> safetyMargins = safetyMarginBean.getSafetyMarginList();

		String username = commonService.getAuthUserName();
		String khatId = "";

		for (Integer i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			double safetyMargin = safetyMargins.get(i);
			if (safetyMargin > 0) {
				CSItemTransactionMst itemTransactionMst = (CSItemTransactionMst) commonService
						.getAnObjectByAnyUniqueColumn("CSItemTransactionMst",
								"id", id + "");
				Date now = new Date();
				itemTransactionMst.setModifiedBy(username);
				itemTransactionMst.setModifiedDate(now);
				itemTransactionMst.setSafetyMargin(safetyMargin);
				commonService.saveOrUpdateModelObjectToDB(itemTransactionMst);				
				khatId = itemTransactionMst.getKhathId() + "";
				
			} 
		}
		if(khatId.equals("")){
			khatId="1";
		}
		
		return projectInformation(khatId);
		// return "success";
	}
}
