package com.ibcs.desco.inventory.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.bean.ProjectQtyBean;
import com.ibcs.desco.inventory.model.AllLookUp;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.model.PhysicalStoreInventory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.inventory.service.LookUpService;

@Controller
public class ItemInventoryController {

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	LookUpService lookUpService;

	@Autowired
	CommonService commonService;

	/*
	 * @Autowired ReadExcelFile readExcelFile;
	 */

	@RequestMapping(value = "/inventory/showInventoryItem.do", method = RequestMethod.GET)
	public ModelAndView showItemGroup() {

		List<ItemCategory> itemGroupList = itemGroupService.getAllItemGroups();

		List<AllLookUp> lookupList = lookUpService
				.getAllLookupByParentname("UOM");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("itemMaster", new ItemMaster());
		model.put("itemGroupList", itemGroupList);
		model.put("lookupList", lookupList);

		/* readExcelFile.insertIntoDbFromExcel(); */
		return new ModelAndView("/inventory/inventoryItem", model);

	}

	@RequestMapping(value = "/inventory/addInventoryItem.do", method = RequestMethod.POST)
	public String addItemGroup(@ModelAttribute ItemMaster itemMaster,
			Model model, HttpServletRequest req, Principal principal) {

		String userName = principal.getName();
		Date today = new Date();

		itemMaster.setCreatedBy(userName);
		itemMaster.setCreateDate(today);

		if (StringUtils.isEmpty(req.getParameter("isActive"))) {
			itemMaster.setItemActive(0);
		} else {
			itemMaster.setItemActive(1);
		}

		if (StringUtils.isEmpty(req.getParameter("isSpecial"))) {
			itemMaster.setSpecialApproval(0);
		} else {
			itemMaster.setSpecialApproval(1);
		}

		if (StringUtils.isEmpty(req.getParameter("isFixedAsset"))) {
			itemMaster.setFixedAsset("0");
		} else {
			itemMaster.setFixedAsset("1");
		}
		itemMaster.setCreateDate(today);
		itemMaster.setCreatedBy(commonService.getAuthUserName());

		itemMaster.setItemId(req.getParameter("category")
				+ req.getParameter("itemId"));
		itemInventoryService.addItemInventory(itemMaster);
		model.addAttribute(new ItemMaster());
		return "redirect:/inventory/listInventoryItem.do";

	}

	@RequestMapping(value = "/inventory/listInventoryItem.do", method = RequestMethod.GET)
	public String listItemGroup(Model model) {
		List<ItemMaster> inventoryItemList = itemInventoryService
				.getInventoryItemList();

		model.addAttribute("inventoryItemList", inventoryItemList);
		// model.addAttribute(new ItemGroup());
		return "/inventory/listInventoryItem";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/editInventoryItem.do", method = RequestMethod.GET)
	public ModelAndView editInventoryItem(
			@ModelAttribute("id") ItemMaster itemMaster) {

		ItemMaster selectInventoryItem = itemInventoryService
				.getInventoryItemById(itemMaster.getId());
		List<ItemCategory> itemGroupList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");
		// List<ItemCategory> itemGroupList =
		// itemGroupService.getAllItemGroups();
		// List<ItemSubGroup> itemSubgroupList =
		// itemSubgroupService.getAllItemSubGroups();
		List<AllLookUp> lookupList = lookUpService
				.getAllLookupByParentname("UOM");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lookupList", lookupList);
		model.put("itemMaster", new ItemMaster());
		model.put("itemGroupList", itemGroupList);
		/* model.put("itemSubgroupList", itemSubgroupList); */

		List<AllLookUp> parentList = lookUpService.getAllLookUps();

		model.put("selectInventoryItem", selectInventoryItem);
		model.put("parentList", parentList);
		return new ModelAndView("inventory/itemInventoryEdit", model);
	}

	@RequestMapping(value = "/inventory/updateItemByAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateItemByAjax(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ItemMaster itemMaster = gson.fromJson(json, ItemMaster.class);
			Integer id = itemMaster.getId();
			String fixedAsset = itemMaster.getFixedAsset();
			String itemType = itemMaster.getItemType();
			String username = commonService.getAuthUserName();
			Date now = new Date();
			ItemMaster itmMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "id", id + "");
			itmMaster.setModifiedBy(username);
			itmMaster.setModifyDate(now);
			itmMaster.setFixedAsset(fixedAsset);
			itmMaster.setItemType(itemType);

			commonService.saveOrUpdateModelObjectToDB(itmMaster);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/inventory/inventoryItemUpdate.do", method = RequestMethod.POST)
	public String updateInventoryItem(Model model,
			@ModelAttribute("itemMaster") ItemMaster itemMaster) {

		ItemMaster itemMasterDB = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "id",
						itemMaster.getId() + "");

		itemMasterDB.setCategoryId(itemMaster.getCategoryId());
		itemMasterDB.setItemName(itemMaster.getItemName());
		itemMasterDB.setItemId(itemMaster.getItemId());
		itemMasterDB.setUnitCode(itemMaster.getUnitCode());
		itemMasterDB.setItemType(itemMaster.getItemType());
		itemMasterDB.setFixedAsset(itemMaster.getFixedAsset());
		itemMasterDB.setRemarks(itemMaster.getRemarks());
		itemMasterDB.setItemActive(itemMaster.getItemActive());
		itemMasterDB.setSpecialApproval(itemMaster.getSpecialApproval());
		commonService.saveOrUpdateModelObjectToDB(itemMasterDB);

		return "redirect:/inventory/showInventoryById.do?id="
				+ itemMasterDB.getId();
	}

	@RequestMapping(value = "/inventory/showInventoryById.do", method = RequestMethod.GET)
	public ModelAndView showInventoryItem(
			@ModelAttribute("inventoryItemId") ItemMaster itemMaster) {

		List<ItemCategory> itemGroupList = itemGroupService.getAllItemGroups();

		ItemMaster selectInventoryItem = itemInventoryService
				.getInventoryItemById(itemMaster.getId());
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("itemMaster", new ItemMaster());
		model.put("itemGroupList", itemGroupList);

		List<AllLookUp> parentList = lookUpService.getAllLookUps();

		model.put("selectInventoryItem", selectInventoryItem);
		model.put("parentList", parentList);
		return new ModelAndView("inventory/inventoryItemShow", model);
	}

	@RequestMapping(value = "/inventory/searchByInventoryItemName.do", method = RequestMethod.POST)
	public ModelAndView searchInventoryItemByItemName(ItemMaster itemMaster,
			HttpServletRequest req) {

		List<ItemMaster> inventoryItemList = itemInventoryService
				.getInventoryItemByName(req.getParameter("itemNameForSearch"));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inventoryItemList", inventoryItemList);

		return new ModelAndView("inventory/listInventoryItem", model);
	}

	@RequestMapping(value = "/inventory/getPhysicalStoreInventoryForm.do", method = RequestMethod.GET)
	public ModelAndView getPhysicalStoreInventoryForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Date> inventoryDateList = itemInventoryService
					.getDistinctInventoryDateListFromPhysicalInventory();

			model.put("inventoryDateList", inventoryDateList);
			return new ModelAndView("inventory/physicalStoreInventoryForm",
					model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("inventory/error", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inventory/getPhysicalStoreInventoryForm.do", method = RequestMethod.POST)
	public ModelAndView getPhysicalStoreInventoryFormNextPage(
			@ModelAttribute("inventoryDate") PhysicalStoreInventory bean) {
		// PhysicalStoreInventory inventory
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*
			 * DateFormat f = new SimpleDateFormat("yyyy-MM-dd"); Date startDate
			 * = f.parse(inventoryDate);
			 */

			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			String fromDate = df.format(bean.getInventoryDate());
			String toDate = df.format(bean.getInventoryDate());

			List<Integer> projectIdList = (List<Integer>) (Object) commonService
					.getDistinctValueListByColumnName("PhysicalStoreInventory",
							"descoKhath.id");
			Collections.sort(projectIdList);

			List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			Map<Integer, String> khathMap = new HashMap<Integer, String>();

			for (DescoKhath descoKhath : khathList) {
				khathMap.put(descoKhath.getId(), descoKhath.getKhathName());
			}

			Map<String, Map<String, PhysicalStoreInventory>> projectMap = new HashMap<String, Map<String, PhysicalStoreInventory>>();
			List<String> projectNameList = new ArrayList<String>();

			for (int projectId : projectIdList) {
				projectNameList.add(khathMap.get(projectId));
				List<PhysicalStoreInventory> inventoryList = (List<PhysicalStoreInventory>) (Object) commonService
						.getObjectListByDateRangeAndTwoColumn(
								"PhysicalStoreInventory", "inventoryDate",
								fromDate, toDate, "flag", "0", "descoKhath.id",
								projectId + "");
				Map<String, PhysicalStoreInventory> itemMap = new HashMap<String, PhysicalStoreInventory>();
				for (int i = 0; i < inventoryList.size(); i++) {
					PhysicalStoreInventory p = inventoryList.get(i);
					itemMap.put(p.getItemCode(), p);
				}
				projectMap.put(projectId + "", itemMap);
			}

			List<PhysicalStoreInventory> physicalStoreInventoryList = new ArrayList<PhysicalStoreInventory>();

			if (projectMap.size() > 0 && projectIdList.size() > 0) {
				String p1 = projectIdList.get(0) + "";
				Map<String, PhysicalStoreInventory> p1map = projectMap.get(p1);

				for (Entry<String, PhysicalStoreInventory> entry : p1map
						.entrySet()) {
					String itemCode = entry.getKey();
					PhysicalStoreInventory item = entry.getValue();
					List<ProjectQtyBean> proQtyBeanList = new ArrayList<ProjectQtyBean>();
					Double total = 0.0;
					for (int n : projectIdList) {
						if(projectMap.get(n + "").size()>0){
							ProjectQtyBean pqb = new ProjectQtyBean();
							pqb.setProjectId(n);
							pqb.setProjectName(khathMap.get(n));							
							if(projectMap.get(n + "").get(itemCode)!=null){
								Double qty=projectMap.get(n + "").get(itemCode)
										.getTotalQty();
								total += qty;								
								pqb.setQty(qty);								
							}else{
								pqb.setQty(0.0);
							}
							proQtyBeanList.add(pqb);
						}
						
					}
					item.setTotalQty(total);
					item.setProjectQtyList(proQtyBeanList);
					physicalStoreInventoryList.add(item);
				}

			}

			model.put("invDate", bean.getInventoryDate());
			model.put("projectNameList", projectNameList);
			model.put("inventoryList", physicalStoreInventoryList);

			return new ModelAndView("inventory/physicalStoreInventory", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("inventory/error", model);
		}
	}

	// Added by Ashid
	@RequestMapping(value = "/inventory/updateAnPhysicalInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateAnPhysicalInventoryItem(
			@RequestBody String inventoryJsonString) throws Exception {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(inventoryJsonString);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			PhysicalStoreInventory psInventory = gson.fromJson(
					inventoryJsonString, PhysicalStoreInventory.class);
			String id = "" + psInventory.getId();
			String countedDateString = psInventory.getDateText();
			DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			Date countedDate = fm.parse(countedDateString);

			PhysicalStoreInventory psInventoryDB = (PhysicalStoreInventory) commonService
					.getAnObjectByAnyUniqueColumn("PhysicalStoreInventory",
							"id", id);
			String userid = commonService.getAuthUserName();
			AuthUser AuthUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userid);

			psInventoryDB.setCountedQty(psInventory.getCountedQty());
			psInventoryDB.setCountedDate(countedDate);
			psInventoryDB.setShortageQty(psInventory.getShortageQty());
			psInventoryDB.setShortageValue(psInventory.getShortageValue());
			psInventoryDB.setSurplusQty(psInventory.getSurplusQty());
			psInventoryDB.setSurplusValue(psInventory.getSurplusValue());
			psInventoryDB.setPercentage(psInventory.getPercentage());
			psInventoryDB.setRemarks(psInventory.getRemarks());
			psInventoryDB.setStatus("1");
			psInventoryDB.setModifiedBy(AuthUser.getName());
			psInventoryDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(psInventoryDB);

			toJson = ow.writeValueAsString("success");

		} else {
			Thread.sleep(3000);
			toJson = ow.writeValueAsString("fail");
		}

		return toJson;
	}

	@RequestMapping(value = "/inventory/physicalStoreInventory.do", method = RequestMethod.GET)
	public ModelAndView getPhysicalStoreInventoryProcessForm() {
		return new ModelAndView("inventory/physicalStoreInventoryProcessForm");
	}

	@RequestMapping(value = "/inventory/physicalStoreInventory.do", method = RequestMethod.POST)
	public ModelAndView postPhysicalStoreInventoryProcessForm(PhysicalStoreInventory bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		PhysicalStoreInventory physicalStoreInventory = new PhysicalStoreInventory();
		try {
			String rawDate=bean.getDateText();
			if(rawDate.equals("") || rawDate == null){
				return new ModelAndView("redirect:/inventory/physicalStoreInventory.do");
			}
			String formattedDate=rawDate.replaceAll("-", "/");
			
			List<Date> inventoryDateList = itemInventoryService
					.getDistinctInventoryDateListFromPhysicalInventory();
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date beanDate = df.parse(rawDate);
			boolean flag=false;
			if(inventoryDateList != null){
				for (Date date : inventoryDateList) {					
					String dbDateText = df.format(date);
					if(dbDateText.equals(rawDate)){
						flag = true;
						break;
					}
				}
			}else{
				boolean result = commonService.savePhysicalInventotyByProcedure(formattedDate);
				
				if(result){
					physicalStoreInventory.setInventoryDate(beanDate);
					return this.getPhysicalStoreInventoryFormNextPage(physicalStoreInventory);
				}else{
					return new ModelAndView("redirect:/inventory/physicalStoreInventory.do");
				}
			}
			
			
			if(flag){
				physicalStoreInventory.setInventoryDate(beanDate);
				return this.getPhysicalStoreInventoryFormNextPage(physicalStoreInventory);
			}else{
				// call insert method (formattedDate)
				boolean result = commonService.savePhysicalInventotyByProcedure(formattedDate);
				
				if(result){
					physicalStoreInventory.setInventoryDate(beanDate);
					return this.getPhysicalStoreInventoryFormNextPage(physicalStoreInventory);
				}else{
					return new ModelAndView("redirect:/inventory/physicalStoreInventory.do");
				}
			}
		
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("inventory/error", model);
		}

	}

}
