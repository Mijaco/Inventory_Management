package com.ibcs.desco.inventory.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.ibcs.desco.common.utility.DescoUtility;
import com.ibcs.desco.inventory.bean.StoreRequisitionMstDtl;
import com.ibcs.desco.inventory.model.InventoryItem;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.model.StoreRequisionDetail1;
import com.ibcs.desco.inventory.model.StoreRequisiotionDetail2;
import com.ibcs.desco.inventory.model.StoreRequisitionMst;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.inventory.service.StoreRequisitionDetailsService;
import com.ibcs.desco.inventory.service.StoreRequisitionMasterService;

@Controller
@PropertySource("classpath:common.properties")
public class StoreRequisionController {
	
	@Autowired
	private StoreRequisitionMasterService storeRequisitionMasterService;

	@Autowired
	private StoreRequisitionDetailsService storeRequisitionDetailsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Value("${SRFNO}")
	private String srfNoFromProperties;

	// search a Requisition Mst by PRF no.
	@RequestMapping(value = "/inventory/searchBySrfNo.do", method = RequestMethod.POST)
	public ModelAndView searchStoreRequisitionMst(StoreRequisitionMst storeRequisitionMst) {

		List<StoreRequisitionMst> storeRequisitionMstList = storeRequisitionMasterService
				.getStoreRequisitionMstListBySRFNo(storeRequisitionMst.getSrfNo());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeRequisitionMstList", storeRequisitionMstList);

		return new ModelAndView("inventory/storeRequisitionList", model);
	}

	@RequestMapping(value = "/inventory/saveStoreRequisition.do", method = RequestMethod.POST)
	public String saveSotreReqisition(Model model,
			@ModelAttribute("storeRequisitionMstDtl") StoreRequisitionMstDtl storeRequisitionMstDtl) {

		List<String> itemNameList = null;

		List<String> itemCodeList = null;

		List<String> uomList = null;

		List<String> quantityList = null;

		List<String> costCenterList = null;

		Date today = new Date();

		StoreRequisitionMst storeRequisitionMst = new StoreRequisitionMst();
		storeRequisitionMst.setSrfNo(storeRequisitionMstDtl.getSrfNo());
		storeRequisitionMst.setRequisitionOfficer(storeRequisitionMstDtl.getRequisitionOfficer());
		storeRequisitionMst.setRequisitionDate(today);
		storeRequisitionMst.setRequisitionTo(storeRequisitionMstDtl.getRequisitionTo());
		storeRequisitionMst.setJustification(storeRequisitionMstDtl.getJustification());
		storeRequisitionMst.setDepartmentFrom(storeRequisitionMstDtl.getDepartmentFrom());
		storeRequisitionMst.setCreatedBy(storeRequisitionMstDtl.getRequisitionOfficer());
		storeRequisitionMst.setCreatedDate(today);
		storeRequisitionMst.setStatus(storeRequisitionMstDtl.getStatus());
		storeRequisitionMasterService.addStoreRequisitionMst(storeRequisitionMst);

		StoreRequisitionMst storeRequisitionMstdb = storeRequisitionMasterService
				.getRequisitionMstBySRFNo(storeRequisitionMstDtl.getSrfNo());

		// Details Table Save to Database from GUI value with Master Table Id
		if (storeRequisitionMstDtl.getItemName() != null) {
			itemNameList = storeRequisitionMstDtl.getItemName();
		}

		if (storeRequisitionMstDtl.getItemCode() != null) {
			itemCodeList = storeRequisitionMstDtl.getItemCode();
		}

		if (storeRequisitionMstDtl.getUom() != null) {
			uomList = storeRequisitionMstDtl.getUom();
		}

		if (storeRequisitionMstDtl.getQuantity() != null) {
			quantityList = storeRequisitionMstDtl.getQuantity();
		}

		if (storeRequisitionMstDtl.getCostCenter() != null) {
			costCenterList = storeRequisitionMstDtl.getCostCenter();
		}

		for (int i = 0; i < itemNameList.size(); i++) {
			StoreRequisionDetail1 storeReqDtl = new StoreRequisionDetail1();

			storeReqDtl.setItemName(itemNameList.get(i));
			storeReqDtl.setItemCode(itemCodeList.get(i));
			storeReqDtl.setUom(uomList.get(i));
			storeReqDtl.setQuantity(quantityList.get(i));
			storeReqDtl.setCostCenter(costCenterList.get(i));
			storeReqDtl.setRequisitionId(storeRequisitionMstdb.getId());

			storeRequisitionDetailsService.addStoreRequisitionDetail(storeReqDtl);

		}

		return "redirect:/inventory/storeRequisitionList.do";

	}

	@RequestMapping(value = "/inventory/storeRequisitionCreate.do", method = RequestMethod.GET)
	public ModelAndView createStoreRequisition() {
		StoreRequisitionMst storeRequisitionMstdb = storeRequisitionMasterService
				.getStoreRequisitionMstLastRowFromTab();

		String newSrfNo = "";

		if (storeRequisitionMstdb != null) {

			String srfNo = storeRequisitionMstdb.getSrfNo();
			String firstVal = srfNo.substring(0, 19);
			String lastVal = srfNo.substring(19, 22);
			int newVal = Integer.parseInt(lastVal) + 1;

			if ((newVal + "").length() == 1) {
				newSrfNo = firstVal + "00" + newVal;
			} else if ((newVal + "").length() == 2) {
				newSrfNo = firstVal + "0" + newVal;
			} else {
				newSrfNo = firstVal + newVal;
			}
		} else {
			System.out.println("srfNoFromProperties==== " + srfNoFromProperties);
			newSrfNo = srfNoFromProperties;
		}
		List<ItemMaster> invItemList = itemInventoryService.getInventoryItemList();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("srfNo", newSrfNo);
		model.put("invItemList", invItemList);

		return new ModelAndView("inventory/storeRequisitionForm", model);
	}

	@RequestMapping(value = "/inventory/storeRequisitionShow.do", method = RequestMethod.GET)
	public ModelAndView showStoreRequisitionMst(@ModelAttribute("id") StoreRequisitionMst storeRequisitionMst) {

		StoreRequisitionMst storeRequisitionMstDB = storeRequisitionMasterService
				.getStoreRequisitionMst(storeRequisitionMst.getId());
		List<StoreRequisionDetail1> storeRequisitionDtlList = storeRequisitionDetailsService
				.getAllStoreRequisitionDetail(storeRequisitionMst.getId());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeRequisitionMst", storeRequisitionMstDB);
		model.put("storeRequisitionDtlList", storeRequisitionDtlList);
		return new ModelAndView("inventory/storeRequisitionShow", model);
	}

	@RequestMapping(value = "/inventory/requisitionStoreEdit.do", method = RequestMethod.GET)
	public ModelAndView editRequisitionMst(@ModelAttribute("id") StoreRequisitionMst storeRequisitionMst) {

		StoreRequisitionMst storeRequisitionMstDB = storeRequisitionMasterService
				.getStoreRequisitionMst(storeRequisitionMst.getId());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeRequisitionMst", storeRequisitionMstDB);
		return new ModelAndView("inventory/storeRequisitionMstEdit", model);
	}

	// for save a Requisition Dtl
	@RequestMapping(value = "/inventory/storeRequisitionDtlSave.do", method = RequestMethod.POST)
	public String saveRequisitionDtl(StoreRequisionDetail1 storeRequisionDetail1) {
		System.out.println("in controller store requisition =========" + storeRequisionDetail1);

		storeRequisitionDetailsService.addStoreRequisitionDetail(storeRequisionDetail1);

		return "redirect:/inventory/storeRequisitionShow.do?id=" + storeRequisionDetail1.getRequisitionId();
	}

	// update a Requisition Dtl
	@RequestMapping(value = "/inventory/storeRequisittionDtlEdit.do", method = RequestMethod.POST)
	public String updateRequisitionDtl(StoreRequisiotionDetail2 storeRequisitionDt2) {

		storeRequisitionDetailsService.editStoreRequisitionDetail(storeRequisitionDt2);
		return "redirect:/inventory/storeRequisitionShow.do?id=" + storeRequisitionDt2.getRequisitionId();
	}

	// update a Requisition Mst
	@RequestMapping(value = "/inventory/requuisitionMasterUpdate.do", method = RequestMethod.POST)
	public String updateRequisitionMst(StoreRequisitionMst storeRequisitionMst) {

		StoreRequisitionMst storeRequisitionMstDB = storeRequisitionMasterService
				.getStoreRequisitionMst(storeRequisitionMst.getId());

		// have to set all master field which is editable in view page
		storeRequisitionMstDB.setJustification(storeRequisitionMst.getJustification());
		storeRequisitionMstDB.setModifiedBy(storeRequisitionMst.getModifiedBy());
		storeRequisitionMstDB.setModifiedDate(new Date());

		// end of update particular master field

		storeRequisitionMasterService.editStoreRequisitionMst(storeRequisitionMstDB);
		return "redirect:/inventory/storeRequisitionShow.do?id=" + storeRequisitionMst.getId();
	}

	@RequestMapping(value = "/inventory/viewInventoryItem.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = DescoUtility.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			InventoryItem invItem = gson.fromJson(json, InventoryItem.class);

			ItemMaster selectItemFromDb = itemInventoryService.getInventoryItemById(invItem.getInventoryItemId());
			System.out.println("selectItemFromDb   " + selectItemFromDb);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/inventory/viewStoreRequisitionDtl.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewRequisitionDtl(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = DescoUtility.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreRequisionDetail1 storeRequisitionDetail1 = gson.fromJson(json, StoreRequisionDetail1.class);

			StoreRequisionDetail1 selectStoreRequisionDetail1 = storeRequisitionDetailsService
					.getStoreRequisitionDetail(storeRequisitionDetail1.getId());

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectStoreRequisionDetail1);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

}
