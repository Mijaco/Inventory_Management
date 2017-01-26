package com.ibcs.desco.budget.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.budget.bean.GeneralItemBudgetMstDtl;
import com.ibcs.desco.budget.model.CostCentre;
import com.ibcs.desco.budget.model.GeneralItemBudgetDtl;
import com.ibcs.desco.budget.model.GeneralItemBudgetMst;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.model.ProcurementPackageMst;

/*
 * Author: Shimul
 *
 */

@Controller
public class BudgetAPPController {

	@Autowired
	CommonService commonService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/appFormWithSession.do", method = RequestMethod.GET)
	public ModelAndView demandNoteSummaryFormBySession() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			model.put("descoSession", descoSession);
			return new ModelAndView("budget/app/appFormWithSession", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/budgetAPPMstList.do", method = RequestMethod.POST)
	public ModelAndView draftAppList(ProcurementPackageMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<ProcurementPackageMst> procurementPackageMstList = null;
			if (bean.getPackageType().equalsIgnoreCase("")) {
				procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByAnyColumn("ProcurementPackageMst",
								"descoSession.id", bean.getId().toString());
			} else {
				procurementPackageMstList = (List<ProcurementPackageMst>) (Object) commonService
						.getObjectListByTwoColumn("ProcurementPackageMst",
								"descoSession.id", bean.getId().toString(),
								"packageType", bean.getPackageType());
			}

			DescoSession ds = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id", bean
							.getId().toString());
			model.put("procurementPackageMstList", procurementPackageMstList);
			model.put("descoSession", ds);
			return new ModelAndView("budget/app/appList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/bgt/updateAPP.do", method = RequestMethod.POST)
	public String updateAPP(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ProcurementPackageMst bean = gson.fromJson(json,
					ProcurementPackageMst.class);

			ProcurementPackageMst appMstDB = (ProcurementPackageMst) commonService
					.getAnObjectByAnyUniqueColumn("ProcurementPackageMst",
							"id", bean.getId().toString());

			/*
			 * appMstDB.setPrepDocInvTender(bean.getPrepDocInvTender());
			 * appMstDB.setEvaluationOfTender(bean.getEvaluationOfTender());
			 * appMstDB.setAwardOfContract(bean.getAwardOfContract());
			 * appMstDB.setTentativeCompletion(bean.getTentativeCompletion());
			 */
			appMstDB.setCurrentSessionBudget(bean.getCurrentSessionBudget());
			appMstDB.setNextSessionBudget(bean.getNextSessionBudget());
			appMstDB.setModifiedBy(commonService.getAuthUserName());
			appMstDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(appMstDB);
			toJson = ow.writeValueAsString("success");
		} else {
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// General Budget Entry Form
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/generalBudgetEntryForm.do", method = RequestMethod.GET)
	public ModelAndView generalBudgetEntryForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();
			List<DescoSession> descoSession = (List<DescoSession>) (Object) commonService
					.getAllObjectList("DescoSession");
			List<CostCentre> costCentreList = (List<CostCentre>) (Object) commonService
					.getAllObjectList("CostCentre");

			model.put("categoryList", categoryList);
			model.put("descoSession", descoSession);
			model.put("costCentreList", costCentreList);

			return new ModelAndView("budget/generalBudgetEntryForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	public boolean isJSONValid(String JSON_STRING) {
		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	@RequestMapping(value = "/bgt/loadItemList.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadItemList(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			List<ItemMaster> selectItemsFromDb = itemInventoryService
					.getItemListByCategoryId(invItem.getCategoryId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@RequestMapping(value = "/bgt/viewInventoryItem.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			// InventoryItem invItem = gson.fromJson(json, InventoryItem.class);
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);

			// ItemMaster selectItemFromDb =
			// itemInventoryService.getItemGroup(invItem.getInventoryItemId());
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// Check Existence of item in a given session
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/checkItemSession.do", method = RequestMethod.POST)
	@ResponseBody
	public String checkAnnexureNo(GeneralItemBudgetMst generalItemBudgetMst) {
		String response = "";
		Integer id = generalItemBudgetMst.getId();
		String itemCode = generalItemBudgetMst.getItemCode();
		List<GeneralItemBudgetMst> gibMst = (List<GeneralItemBudgetMst>) (Object) commonService
				.getObjectListByTwoColumn("GeneralItemBudgetMst", "itemCode",
						itemCode, "descoSession.id", id.toString());
		if (gibMst.size() == 0) {
			response = "success";
		} else {
			response = "error";
		}
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/bgt/saveGeneralItemEntry.do", method = RequestMethod.POST)
	public ModelAndView saveGeneralItemEntry(
			GeneralItemBudgetMstDtl generalItemBudgetMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer sessionId = generalItemBudgetMstDtl.getSessionId();
			String itemName = generalItemBudgetMstDtl.getItemName();
			String itemCode = generalItemBudgetMstDtl.getItemCode();
			String uom = generalItemBudgetMstDtl.getUom();
			Double rate = generalItemBudgetMstDtl.getRate();
			Double mquantity = generalItemBudgetMstDtl.getMquantity();
			Double amount = generalItemBudgetMstDtl.getAmount();

			DescoSession descoSession = (DescoSession) commonService
					.getAnObjectByAnyUniqueColumn("DescoSession", "id",
							sessionId.toString());

			GeneralItemBudgetMst generalItemBudget = new GeneralItemBudgetMst();

			generalItemBudget.setId(null);
			generalItemBudget.setItemCode(itemCode);
			generalItemBudget.setItemName(itemName);
			generalItemBudget.setUom(uom);
			generalItemBudget.setQty(mquantity);
			generalItemBudget.setUnitCost(rate);
			generalItemBudget.setTotalCost(amount);
			generalItemBudget.setDescoSession(descoSession);
			generalItemBudget.setCreatedBy(commonService.getAuthUserName());
			generalItemBudget.setCreatedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(generalItemBudget);

			int maxId = (Integer) commonService.getMaxValueByObjectAndColumn(
					"GeneralItemBudgetMst", "id");
			GeneralItemBudgetMst budgetMstDb = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							maxId + "");

			List<String> costCentreList = generalItemBudgetMstDtl
					.getCostCentre();
			List<Double> costList = generalItemBudgetMstDtl.getCost();

			for (int i = 0; i < costCentreList.size(); i++) {
				GeneralItemBudgetDtl generalItemBudgetDtl = new GeneralItemBudgetDtl();

				if (costList.get(i) > 0) {
					generalItemBudgetDtl.setCostCenterId(costCentreList.get(i));
					generalItemBudgetDtl.setTotalCost(costList.get(i));
					generalItemBudgetDtl.setId(null);
					generalItemBudgetDtl.setCreatedBy(commonService
							.getAuthUserName());
					generalItemBudgetDtl.setCreatedDate(new Date());
					generalItemBudgetDtl.setBudgetMst(budgetMstDb);
					commonService
							.saveOrUpdateModelObjectToDB(generalItemBudgetDtl);
				}
			}

			List<GeneralItemBudgetMst> generalItemBudgetMstList = (List<GeneralItemBudgetMst>) (Object) commonService
					.getAllObjectList("GeneralItemBudgetMst");

			model.put("generalItemBudgetMstList", generalItemBudgetMstList);
			model.put("descoSession", descoSession);
			model.put("duplicateItemList", "[]");
			return new ModelAndView("budget/gnBudgetList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	@RequestMapping(value = "/bgt/updateItemInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateItemInfo(GeneralItemBudgetMst generalItemBudgetMst) {
		String response = "";

		Integer dtlId = generalItemBudgetMst.getId(); // DTL Id
		Double mQty = generalItemBudgetMst.getQty(); // MST Qty
		Double mstCost = generalItemBudgetMst.getTotalCost(); // MST Total Cost;
		Double dtlCost = generalItemBudgetMst.getUnitCost(); // DTL Total Cost;

		Date now = new Date();
		String username = commonService.getAuthUserName();

		GeneralItemBudgetDtl generalItemBudgetDtl = (GeneralItemBudgetDtl) commonService
				.getAnObjectByAnyUniqueColumn("GeneralItemBudgetDtl", "id",
						dtlId.toString());
		if (generalItemBudgetDtl.getId() != null) {
			Integer mstId = generalItemBudgetDtl.getBudgetMst().getId();

			generalItemBudgetDtl.setTotalCost(dtlCost);
			generalItemBudgetDtl.setModifiedBy(username);
			generalItemBudgetDtl.setModifiedDate(now);

			commonService.saveOrUpdateModelObjectToDB(generalItemBudgetDtl);

			GeneralItemBudgetMst updateMst = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							mstId.toString());
			updateMst.setTotalCost(mstCost);
			updateMst.setQty(mQty);
			updateMst.setModifiedBy(username);
			updateMst.setModifiedDate(now);
			commonService.saveOrUpdateModelObjectToDB(updateMst);

			response = "success";
		} else {
			response = "error";
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/deleteItemFromDtl.do", method = RequestMethod.POST)
	public ModelAndView deleteItemFromDtl(
			GeneralItemBudgetDtl generalItemBudgetDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer did = generalItemBudgetDtl.getId();
			GeneralItemBudgetDtl gItemBudgetDtl = (GeneralItemBudgetDtl) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetDtl", "id",
							did.toString());

			Double amount = gItemBudgetDtl.getTotalCost();

			commonService.deleteAnObjectById("GeneralItemBudgetDtl", did);
			GeneralItemBudgetMst generalItemBudgetMst = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							gItemBudgetDtl.getBudgetMst().getId().toString());

			Double rate = generalItemBudgetMst.getUnitCost();
			Double qty = generalItemBudgetMst.getQty();
			Double totalCost = generalItemBudgetMst.getTotalCost();
			Double uQty = (Double) amount / rate;

			generalItemBudgetMst.setTotalCost(totalCost - amount);
			generalItemBudgetMst.setQty(qty - uQty);
			generalItemBudgetMst.setModifiedBy(commonService.getAuthUserName());
			generalItemBudgetMst.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(generalItemBudgetMst);

			GeneralItemBudgetMst generalItemBudgetMstDb = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							generalItemBudgetMst.getId().toString());

			List<GeneralItemBudgetDtl> generalItemBudgetDtlList = (List<GeneralItemBudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("GeneralItemBudgetDtl",
							"budgetMst.id", generalItemBudgetMst.getId()
									.toString());

			List<CostCentre> ccList = (List<CostCentre>) (Object) commonService
					.getAllObjectList("CostCentre");
			Map<String, String> ccMap = new HashMap<String, String>();
			for (CostCentre costCentre : ccList) {
				String id = costCentre.getCostCentreCode();
				String cName = costCentre.getCostCentreName();
				ccMap.put(id, cName);

			}

			for (GeneralItemBudgetDtl dtl : generalItemBudgetDtlList) {
				dtl.setCostCenterName(ccMap.get(dtl.getCostCenterId()));
			}

			model.put("generalItemBudgetMst", generalItemBudgetMstDb);
			model.put("generalItemBudgetDtlList", generalItemBudgetDtlList);
			model.put("costCentreList", ccList);
			return new ModelAndView("budget/showGnItemBudget", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bgt/saveCostCentreFromShow.do", method = RequestMethod.POST)
	public ModelAndView saveCostCentreFromShow(
			GeneralItemBudgetMstDtl generalItemBudgetMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = generalItemBudgetMstDtl.getId();
			GeneralItemBudgetMst generalItemBudgetMst = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							id.toString());
			Double currQty = generalItemBudgetMst.getQty();
			Double currTotalCost = generalItemBudgetMst.getTotalCost();
			Double rate = generalItemBudgetMst.getUnitCost();

			List<String> costCentreList = generalItemBudgetMstDtl
					.getCostCentre();
			List<Double> costList = generalItemBudgetMstDtl.getCost();

			Double calcQty = 0.0, fCost = 0.0;
			for (int i = 0; i < costCentreList.size(); i++) {
				String ccNo = costCentreList.get(i);
				Integer mid = generalItemBudgetMst.getId();
				List<GeneralItemBudgetDtl> generalItemBudgetDtl = (List<GeneralItemBudgetDtl>) (Object) commonService
						.getObjectListByTwoColumn("GeneralItemBudgetDtl",
								"costCenterId", ccNo, "gen_budgetMst_id",
								mid.toString());

				if (generalItemBudgetDtl.size() > 0) {
				} else {
					if (costList.get(i) > 0) {
						GeneralItemBudgetDtl updateDt = new GeneralItemBudgetDtl();
						updateDt.setCostCenterId(costCentreList.get(i));
						updateDt.setTotalCost(costList.get(i));
						updateDt.setId(null);
						updateDt.setCreatedBy(commonService.getAuthUserName());
						updateDt.setCreatedDate(new Date());
						updateDt.setBudgetMst(generalItemBudgetMst);
						commonService.saveOrUpdateModelObjectToDB(updateDt);

						calcQty += (Double) costList.get(i) / rate;
						fCost += costList.get(i);
					}
				}
			}

			generalItemBudgetMst.setQty(currQty + calcQty);
			generalItemBudgetMst.setTotalCost(currTotalCost + fCost);
			generalItemBudgetMst.setModifiedBy(commonService.getAuthUserName());
			generalItemBudgetMst.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(generalItemBudgetMst);

			GeneralItemBudgetMst generalItemBudgetMstDb = (GeneralItemBudgetMst) commonService
					.getAnObjectByAnyUniqueColumn("GeneralItemBudgetMst", "id",
							generalItemBudgetMst.getId().toString());

			List<GeneralItemBudgetDtl> generalItemBudgetDtlList = (List<GeneralItemBudgetDtl>) (Object) commonService
					.getObjectListByAnyColumn("GeneralItemBudgetDtl",
							"budgetMst.id", generalItemBudgetMst.getId()
									.toString());

			List<CostCentre> ccList = (List<CostCentre>) (Object) commonService
					.getAllObjectList("CostCentre");
			Map<String, String> ccMap = new HashMap<String, String>();
			for (CostCentre costCentre : ccList) {
				String did = costCentre.getCostCentreCode();
				String cName = costCentre.getCostCentreName();
				ccMap.put(did, cName);

			}

			for (GeneralItemBudgetDtl dtl : generalItemBudgetDtlList) {
				dtl.setCostCenterName(ccMap.get(dtl.getCostCenterId()));
			}

			model.put("generalItemBudgetMst", generalItemBudgetMstDb);
			model.put("generalItemBudgetDtlList", generalItemBudgetDtlList);
			model.put("costCentreList", ccList);
			return new ModelAndView("budget/showGnItemBudget", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("budget/errorBudget", model);
		}
	}
}
