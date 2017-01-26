package com.ibcs.desco.procurement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AllocationTable;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
public class AllocationTableController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	ItemInventoryService itemInventoryService;
	
	@Autowired
	ItemGroupService itemGroupService;

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/getAllocationForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView getAllocationForm() {

		List<Departments> depts = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);

		List<DescoSession> descoSessions = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departments", depts);
		model.put("descoSessions", descoSessions);
		return new ModelAndView("procurement/allocationTableForm", model);
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/getAllocationTableForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView getAllocationTableForm() {

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		List<DescoSession> descoSessions = (List<DescoSession>) (Object) commonService
				.getAllObjectList("DescoSession");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("categoryList", categoryList);
		model.put("descoSessions", descoSessions);
		return new ModelAndView("procurement/allocationTable/allocationTableNewForm", model);
	}

	@RequestMapping(value = "/saveDescoSession.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView saveDescoSession(
			@Valid @ModelAttribute("command") DescoSession descoSession,
			BindingResult result) {

		Integer maxTnxDtlId = (Integer) commonService
				.getMaxValueByObjectAndColumn("DescoSession", "id");
		DescoSession ds = (DescoSession) commonService
				.getAnObjectByAnyUniqueColumn("DescoSession", "id", maxTnxDtlId
						+ "");
		DescoSession dsdbByName = (DescoSession) commonService
				.getAnObjectByAnyUniqueColumn("DescoSession", "sessionName",
						descoSession.getSessionName());
		descoSession.setCreatedBy(commonService.getAuthUserName());
		descoSession.setCreatedDate(new Date());

		if (ds == null) {
			if (descoSession.getStartDate().before(descoSession.getEndDate())) {
				commonService.saveOrUpdateModelObjectToDB(descoSession);
				return this.getAllocationForm();
			}
		} else {
			if (dsdbByName == null) {
				Date dbEndDate = ds.getEndDate();
				Date guiStartDate = descoSession.getStartDate();
				if (dbEndDate.before(guiStartDate)) {
					if (descoSession.getStartDate().before(
							descoSession.getEndDate())) {
						commonService.saveOrUpdateModelObjectToDB(descoSession);
						return this.getAllocationForm();
					}
				}
			} else {
				return this.getAllocationForm();
			}
		}

		return this.getAllocationForm();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allocationTab/nextform.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView allocationNextForm(
			@Valid @ModelAttribute("command") AllocationTable allocationTable,
			BindingResult result) {
		Integer sessionId = allocationTable.getDescoSessionId();
		String deptId = allocationTable.getDeptId();

		if (sessionId == null || deptId == null) {
			return this.getAllocationForm();
		}

		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", deptId);
		List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
				.getObjectListByTwoColumn("AllocationTable", "sndCode",
						department.getDescoCode(), "descoSession.id", sessionId
								+ "");

		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getAllObjectList("ItemMaster");

		DescoSession descoSession = (DescoSession) commonService
				.getAnObjectByAnyUniqueColumn("DescoSession", "id", sessionId
						+ "");

		if (allocationTableList.size() != itemMasterList.size()) {

			String userName = commonService.getAuthUserName();
			Date now = new Date();

			for (ItemMaster im : itemMasterList) {
				boolean found = false;
				for (AllocationTable allocation : allocationTableList) {
					if (im.getItemId().equalsIgnoreCase(
							allocation.getItemCode())) {
						found = true;
						break;
					}
				}
				if (!found) {
					AllocationTable at = new AllocationTable(null, 0.0, true,
							im.getItemId(), im.getItemName(), im.getUnitCode(),
							descoSession, department.getDescoCode(), 0.0, true,
							userName, now);

					commonService.saveOrUpdateModelObjectToDB(at);
				}
			}
		}

		return this.nextFormOpen(department, sessionId, descoSession);
	}
	
	// /allocationTable/nextform.do
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allocationTable/nextform.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView allocationTableNextForm(
			@Valid @ModelAttribute("command") AllocationTable allocationTable,
			BindingResult result) {
		Integer sessionId = allocationTable.getDescoSessionId();
		//String deptId = allocationTable.getDeptId();
		String itemCode = allocationTable.getItemCode();

		if (sessionId == null || itemCode == null) {
			return this.getAllocationForm();
		}

		List<Departments> depts = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);
		
		ItemMaster itemMaster = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId", itemCode);
		
		List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
				.getObjectListByTwoColumn("AllocationTable", "itemCode", itemCode, "descoSession.id", sessionId.toString());

		DescoSession descoSession = (DescoSession) commonService
				.getAnObjectByAnyUniqueColumn("DescoSession", "id", sessionId
						+ "");

		if (allocationTableList.size() != depts.size()) {

			String userName = commonService.getAuthUserName();
			Date now = new Date();

			for (Departments department : depts) {
				boolean found = false;
				for (AllocationTable allocation : allocationTableList) {
					if (department.getDescoCode().equalsIgnoreCase(
							allocation.getSndCode())) {
						found = true;
						break;
					}
				}
				if (!found) {
					AllocationTable at = new AllocationTable(null, 0.0, true,
							itemCode, itemMaster.getItemName(), itemMaster.getUnitCode(),
							descoSession, department.getDescoCode(), 0.0, true,
							userName, now);

					commonService.saveOrUpdateModelObjectToDB(at);
				}
			}
		}

		return this.nextAllocationFormOpen(itemMaster, sessionId, descoSession);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView nextAllocationFormOpen(ItemMaster itemMaster,
			Integer sessionId, DescoSession descoSession) {
		List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
				.getObjectListByTwoColumn("AllocationTable", "itemCode",
						itemMaster.getItemId(), "descoSession.id", sessionId
								+ "");
		
		
		
		List<Departments> deoartments = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);
		
		Map<String, String> deptMap = new HashMap<String, String>();
		for (Departments department : deoartments) {
			deptMap.put(department.getDescoCode(), department.getDeptName());
		}
		
		for (AllocationTable allocationTable : allocationTableList) {
			allocationTable.setSndName(deptMap.get(allocationTable.getSndCode()));
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("descoSession", descoSession);
		model.put("itemMaster", itemMaster);
		model.put("allocationTableList", allocationTableList);
		return new ModelAndView("procurement/allocationTable/allocationTableNextNewForm", model);
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView nextFormOpen(Departments department,
			Integer sessionId, DescoSession descoSession) {
		List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
				.getObjectListByTwoColumn("AllocationTable", "sndCode",
						department.getDescoCode(), "descoSession.id", sessionId
								+ "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("descoSession", descoSession);
		model.put("department", department);
		model.put("allocationTableList", allocationTableList);
		return new ModelAndView("procurement/allocationTableNextForm", model);
	}

	@RequestMapping(value = "/allocation/requisitionLimitSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public ModelAndView requisitionLimitSave(
			@Valid @ModelAttribute("command") AllocationTable allocationTable,
			BindingResult result) {
		Integer sessionId = allocationTable.getDescoSessionId();
		String deptId = allocationTable.getDeptId();

		List<String> allocationIdList = allocationTable.getAllocationId();
		List<Boolean> unLimitedReqList = allocationTable.getUnlimitedReq();
		List<Double> requistionQtyLimitList = allocationTable
				.getRequisitionLimitQty();

		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", deptId);

		DescoSession descoSession = (DescoSession) commonService
				.getAnObjectByAnyUniqueColumn("DescoSession", "id", sessionId
						+ "");

		/*
		 * List<AllocationTable> allocationTableList =
		 * (List<AllocationTable>)(Object)
		 * commonService.getObjectListByAnyColumnValueList("AllocationTable",
		 * "id", allocationIdList);
		 */

		for (int i = 0; i < allocationIdList.size(); i++) {
			AllocationTable at = (AllocationTable) commonService
					.getAnObjectByAnyUniqueColumn("AllocationTable", "id",
							allocationIdList.get(i) + "");
			// AllocationTable at = allocationTableList.get(i);
			at.setUnlimited(unLimitedReqList.get(i));
			at.setRequisitionLimit(requistionQtyLimitList.get(i));
			commonService.saveOrUpdateModelObjectToDB(at);
		}

		return this.nextFormOpen(department, sessionId, descoSession);
	}

	@ResponseBody
	@RequestMapping(value = "/allocation/requisitionLimitUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public String requisitionLimitUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			AllocationTable at = gson.fromJson(cData, AllocationTable.class);
			Integer id = at.getId();
			boolean unlimited = at.isUnlimited();
			double requisitionLimit = at.getRequisitionLimit();
			AllocationTable allocationTable = (AllocationTable) commonService
					.getAnObjectByAnyUniqueColumn("AllocationTable", "id", id
							+ "");
			allocationTable.setUnlimited(unlimited);
			allocationTable.setRequisitionLimit(requisitionLimit);
			commonService.saveOrUpdateModelObjectToDB(allocationTable);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Update Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Update Failed.");
		}
		return toJson;
	}
	
	// /allocationTable/requisitionLimitUpdate.do

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/allocation/validateAllocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#allocationTable, 'READ')")
	public String validateAllocation(@RequestBody String cData)
			throws Exception {
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		// String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();

			CentralStoreRequisitionDtl csRequisitionDtl = gson.fromJson(cData,
					CentralStoreRequisitionDtl.class);
			String itemCode = csRequisitionDtl.getItemCode();
			double quantityRequired = csRequisitionDtl.getQuantityRequired();

			DescoSession descoSession = (DescoSession) commonService
					.getCurrentDescoSession();
			
			if (descoSession == null) {
				toJson = ow.writeValueAsString("f1");
			} else {
				List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
						.getObjectListByThreeColumn("AllocationTable",
								"sndCode", department.getDescoCode(),
								"itemCode", itemCode, "descoSession.id",
								descoSession.getId() + "");
				AllocationTable allocationTable = null;
				if (allocationTableList.size() > 0) {
					allocationTable = (AllocationTable) allocationTableList
							.get(0);

					if (allocationTable.isUnlimited()) {
						toJson = ow.writeValueAsString("success");
					} else {
						if (allocationTable.getRequisitionLimit() > 0) {
							double reqQty = allocationTable.getUsedQuantity()
									+ quantityRequired;
							if (allocationTable.getRequisitionLimit() >= reqQty) {
								toJson = ow.writeValueAsString("success");
							} else {
								toJson = ow.writeValueAsString("f2");
							}
						} else {
							toJson = ow.writeValueAsString("f0");
						}
					}
				} else {
					// no allocation set for this snd
					toJson = ow.writeValueAsString("success");
					//toJson = ow.writeValueAsString("f1");
				}
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("failed");
		}

		return toJson;
	}
	
	@RequestMapping(value = "/allocationTable/viewInventoryItemCategory.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
			throws Exception {
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
}
