package com.ibcs.desco.procurement.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.RoleService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.procurement.bean.RequisitionMstDtl;
import com.ibcs.desco.procurement.model.ProcurementFlowPriority;
import com.ibcs.desco.procurement.model.RequisitionDtl;
import com.ibcs.desco.procurement.model.RequisitionDtl2;
import com.ibcs.desco.procurement.model.RequisitionMst;
import com.ibcs.desco.procurement.model.Vendor;
import com.ibcs.desco.procurement.service.ProcurementPriorityService;
import com.ibcs.desco.procurement.service.RequisitionDetailsService;
import com.ibcs.desco.procurement.service.RequisitionMasterService;
import com.ibcs.desco.procurement.service.VendorService;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 *
 */
@Controller
@RequestMapping(value = "/procurement")
public class ProcurementController {
	
	private RoleService roleService;

	private VendorService vendorService;
	private RequisitionMasterService requisitionMasterService;
	private RequisitionDetailsService requisitionDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	DepartmentsService departmentsService;

	private ProcurementPriorityService procurementPriorityService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CommonService commonService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setProcurementPriorityService(
			ProcurementPriorityService procurementPriorityService) {
		this.procurementPriorityService = procurementPriorityService;
	}

	// search a Requisition Mst by PRF no.
	@RequestMapping(value = "/requisition/searchByPrfNo.do", method = RequestMethod.POST)
	public ModelAndView searchRequisitionMst(RequisitionMst requisitionMst) {

		List<RequisitionMst> requisitionMstList = requisitionMasterService
				.getRequisitionMstListByPRFNo(requisitionMst.getPrfNo());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("requisitionMstList", requisitionMstList);

		return new ModelAndView("procurement/requisitionList", model);
	}

	@RequestMapping(value = "/requisition/save.do", method = RequestMethod.POST)
	public String saveRequisition(
			Model model,
			@ModelAttribute("requisitionMstDtl") RequisitionMstDtl requisitionMstDtl) {

		List<String> itemNameList = null;

		List<String> itemCodeList = null;

		List<String> uomList = null;

		List<String> quantityList = null;

		List<String> costCenterList = null;

		Date today = new Date();

		RequisitionMst requisitionMst = new RequisitionMst();
		requisitionMst.setPrfNo(requisitionMstDtl.getPrfNo());
		requisitionMst.setRequisitionOfficer(requisitionMstDtl
				.getRequisitionOfficer());
		requisitionMst.setRequisitionDate(today);
		requisitionMst.setRequisitionTo(requisitionMstDtl.getRequisitionTo());
		requisitionMst.setStatus(requisitionMstDtl.getStatus());
		requisitionMst.setJustification(requisitionMstDtl.getJustification());
		requisitionMst.setDepartmentFrom(requisitionMstDtl.getDepartmentFrom());
		requisitionMst.setCreatedBy(requisitionMstDtl.getRequisitionOfficer());
		requisitionMst.setCreatedDate(today);

		requisitionMasterService.addRequisitionMst(requisitionMst);

		RequisitionMst requisitionMstdb = requisitionMasterService
				.getRequisitionMstByPRFNo(requisitionMstDtl.getPrfNo());

		// Details Table Save to Database from GUI value with Master Table Id
		if (requisitionMstDtl.getItemName() != null) {
			itemNameList = requisitionMstDtl.getItemName();
		}

		if (requisitionMstDtl.getItemCode() != null) {
			itemCodeList = requisitionMstDtl.getItemCode();
		}

		if (requisitionMstDtl.getUom() != null) {
			uomList = requisitionMstDtl.getUom();
		}

		if (requisitionMstDtl.getQuantity() != null) {
			quantityList = requisitionMstDtl.getQuantity();
		}

		if (requisitionMstDtl.getCostCenter() != null) {
			costCenterList = requisitionMstDtl.getCostCenter();
		}

		for (int i = 0; i < itemNameList.size(); i++) {
			RequisitionDtl reqDtl = new RequisitionDtl();

			reqDtl.setItemName(itemNameList.get(i));
			reqDtl.setItemCode(itemCodeList.get(i));
			reqDtl.setUom(uomList.get(i));
			reqDtl.setQuantity(quantityList.get(i));
			reqDtl.setCostCenter(costCenterList.get(i));
			reqDtl.setRequisitionId(requisitionMstdb.getId());

			requisitionDetailsService.addRequisitionDetail(reqDtl);
		}

		return "redirect:/procurement/requisition/list.do";

	}

	@RequestMapping(value = "/requisition/procFlowPrioritySave.do", method = RequestMethod.POST)
	public String procFlowPrioritySave(
			Model model,
			@ModelAttribute("procurementFlowPriority") ProcurementFlowPriority procurementFlowPriority) {
		procurementFlowPriority.setCreatedDate(new Date());

		procurementPriorityService
				.addProcurementFlowPriority(procurementFlowPriority);

		return "redirect:/procurement/requisition/procFlowPriorityList.do";

	}

	@RequestMapping(value = "/requisition/searchByRoleProcFlowPriority.do", method = RequestMethod.POST)
	public ModelAndView searchByRoleProcFlowPriority(
			@ModelAttribute("procurementFlowPriority") ProcurementFlowPriority procurementFlowPriority) {

		List<ProcurementFlowPriority> procurementFlowPriorityList = procurementPriorityService
				.getProcurementFlowPriorityByRoleName(procurementFlowPriority
						.getRoleName());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("procurementFlowPriorityList", procurementFlowPriorityList);
		return new ModelAndView("procurement/procFlowPriorityList", model);
	}

	@RequestMapping(value = "/requisition/procFlowPriorityUpdate.do", method = RequestMethod.POST)
	public String procFlowPriorityUpdate(
			Model model,
			@ModelAttribute("procurementFlowPriority") ProcurementFlowPriority procurementFlowPriority) {
		procurementFlowPriority.setModifiedDate(new Date());

		procurementPriorityService
				.addProcurementFlowPriority(procurementFlowPriority);

		return "redirect:/procurement/requisition/procFlowPriorityList.do";

	}

	@RequestMapping(value = "/requisition/procFlowPriorityList.do", method = RequestMethod.GET)
	public ModelAndView procFlowPriorityList() {

		List<ProcurementFlowPriority> procurementFlowPriorityList = procurementPriorityService
				.listProcurementFlowPrioritys();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("procurementFlowPriorityList", procurementFlowPriorityList);
		return new ModelAndView("procurement/procFlowPriorityList", model);
	}

	@RequestMapping(value = "/requisition/deleteProcFlowPriority.do", method = RequestMethod.GET)
	public ModelAndView deleteProcFlowPriority(
			ProcurementFlowPriority procurementFlowPriority) {

		procurementPriorityService
				.deleteProcurementPrioriy(procurementFlowPriority);

		List<ProcurementFlowPriority> procurementFlowPriorityList = procurementPriorityService
				.listProcurementFlowPrioritys();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("procurementFlowPriorityList", procurementFlowPriorityList);
		return new ModelAndView("procurement/procFlowPriorityList", model);
	}

	@RequestMapping(value = "/requisition/editProcFlowPriority.do", method = RequestMethod.GET)
	public ModelAndView editProcFlowPriority(
			ProcurementFlowPriority procurementFlowPriority) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		model.put("procurementFlowPriority", procurementPriorityService
				.getProcurementFlowPriority(procurementFlowPriority.getId()));
		return new ModelAndView("procurement/procFlowPriorityEdit", model);
	}

	@RequestMapping(value = "/requisition/showProcFlowPriority.do", method = RequestMethod.GET)
	public ModelAndView showProcFlowPriority(
			ProcurementFlowPriority procurementFlowPriority) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("procurementFlowPriority", procurementPriorityService
				.getProcurementFlowPriority(procurementFlowPriority.getId()));
		return new ModelAndView("procurement/procFlowPriorityShow", model);
	}

	@RequestMapping(value = "/requisition/create.do", method = RequestMethod.GET)
	public ModelAndView createRequisition() {
		RequisitionMst requisitionMstdb = requisitionMasterService
				.getRequisitionMstLastRowFromTab();
		String newPrfNo = "";
		if (requisitionMstdb != null) {
			String prfNo = requisitionMstdb.getPrfNo();
			String firstVal = prfNo.substring(0, 19);
			String lastVal = prfNo.substring(19, prfNo.length());
			int newVal = Integer.parseInt(lastVal) + 1;

			if ((newVal + "").length() == 1) {
				newPrfNo = firstVal + "00" + newVal;
			} else if ((newVal + "").length() == 2) {
				newPrfNo = firstVal + "0" + newVal;
			} else {
				newPrfNo = firstVal + newVal;
			}
		} else {
			newPrfNo = "DESCO/2015-2016/PRF001";
		}

		List<ItemMaster> invItemList = itemInventoryService
				.getInventoryItemList();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("prfNo", newPrfNo);
		model.put("invItemList", invItemList);

		return new ModelAndView("procurement/requisitionForm", model);
	}

	@RequestMapping(value = "/requisition/procFlowPriorityForm.do", method = RequestMethod.GET)
	public ModelAndView procFlowPriorityForm() {

		List<Roles> roleList = roleService.listRoles();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleList);
		model.put("procFlowPriorityList",
				procurementPriorityService.listProcurementFlowPrioritys());
		return new ModelAndView("procurement/procFlowPriorityForm", model);
	}

	@RequestMapping(value = "/requisition/requisitionMstDtlReport.do", method = RequestMethod.GET)
	public ModelAndView requisitionMstDtlReport() {

		return new ModelAndView("procurement/requisitionReport");
	}

	@RequestMapping(value = "/requisition/vendorReport.do", method = RequestMethod.GET)
	public ModelAndView vendorReport() {

		return new ModelAndView("procurement/vendorReport");
	}

	@RequestMapping(value = "/requisition/list.do", method = RequestMethod.GET)
	public ModelAndView requisitionList() {
		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}

		List<ProcurementFlowPriority> procurementFlowPrioritydb = procurementPriorityService
				.getProcurementFlowPriorityByRoleName(roleName);
		ProcurementFlowPriority procurementFlowPriority = new ProcurementFlowPriority();
		if (procurementFlowPrioritydb.size() > 0 && procurementFlowPrioritydb != null) {
			procurementFlowPriority = procurementFlowPrioritydb.get(0);
		}

		// List<RequisitionMst> requisitionMstList =
		// requisitionMasterService.getAllRequisitionMst();

		List<RequisitionMst> requisitionMstList = requisitionMasterService
				.getRequisitionMstListByStatus(procurementFlowPriority
						.getPriority() + "");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("requisitionMstList", requisitionMstList);
		return new ModelAndView("procurement/requisitionList", model);
	}

	@RequestMapping(value = "/requisition/show.do", method = RequestMethod.GET)
	public ModelAndView showRequisitionMst(
			@ModelAttribute("id") RequisitionMst requisitionMst) {

		RequisitionMst requisitionMstDB = requisitionMasterService
				.getRequisitionMst(requisitionMst.getId());
		List<RequisitionDtl> requisitionDtlList = requisitionDetailsService
				.getAllRequisitionDetail(requisitionMst.getId());

		ProcurementFlowPriority procurementFlowPriority = procurementPriorityService
				.getProcurementFlowPriorityByPriority(
						Integer.parseInt(requisitionMstDB.getStatus())).get(0);

		List<ItemMaster> invItemList = itemInventoryService
				.getInventoryItemList();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("requisitionMst", requisitionMstDB);
		model.put("requisitionDtlList", requisitionDtlList);
		model.put("stateName", procurementFlowPriority.getStateName());
		model.put("invItemList", invItemList);
		return new ModelAndView("procurement/requisitionShow", model);
	}

	@RequestMapping(value = "/requisition/mstEdit.do", method = RequestMethod.GET)
	public ModelAndView editRequisitionMst(
			@ModelAttribute("id") RequisitionMst requisitionMst) {

		RequisitionMst requisitionMstDB = requisitionMasterService
				.getRequisitionMst(requisitionMst.getId());
		ProcurementFlowPriority procurementFlowPriority = procurementPriorityService
				.getProcurementFlowPriorityByPriority(
						Integer.parseInt(requisitionMstDB.getStatus())).get(0);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("requisitionMst", requisitionMstDB);
		model.put("stateName", procurementFlowPriority.getStateName());
		return new ModelAndView("procurement/requisitionMstEdit", model);
	}

	@RequestMapping(value = "/requisition/submit2HeadOfDept.do", method = RequestMethod.POST)
	public String submit2HeadOfDept(Model model,
			@ModelAttribute("requisitionMst") RequisitionMst requisitionMst) {
		System.out.println(requisitionMst.getId() + "----"
				+ requisitionMst.getPrfNo());

		RequisitionMst requisitionMstdb = requisitionMasterService
				.getRequisitionMstByPRFNo(requisitionMst.getPrfNo());

		List<ProcurementFlowPriority> procurementFlowPriorityList = procurementPriorityService
				.listProcurementFlowPrioritys();
		Integer[] priorityArray = new Integer[procurementFlowPriorityList
				.size()];
		for (int i = 0; i < procurementFlowPriorityList.size(); i++) {
			priorityArray[i] = procurementFlowPriorityList.get(i).getPriority();
		}

		Arrays.sort(priorityArray);
		int priority = 0;
		int status = Integer.parseInt(requisitionMstdb.getStatus());
		for (int j = 0; j < priorityArray.length; j++) {
			priority = priorityArray[j];
			if (priority > status) {
				break;
			}
		}

		requisitionMstdb.setStatus(priority + "");
		requisitionMasterService.editRequisitionMst(requisitionMstdb);
		return "redirect:/procurement/requisition/list.do";
	}

	@RequestMapping(value = "/vendor/save.do", method = RequestMethod.POST)
	public String add(Model model, @ModelAttribute("vendor") Vendor vendor) {

		String add1 = vendor.getAddress1();
		String add2 = vendor.getAddress2();
		String add3 = vendor.getAddress3();
		String add = add1 + ", " + add2 + ", " + add3;

		if ((add1 + add2 + add3).length() > 0) {
			vendor.setAddress(add);
		} else {
			vendor.setAddress("");
		}

		if (vendor.getId() == 0) {
			vendorService.addVendor(vendor);
		} else {
			// vendorService.equals(vendor);
			vendorService.addVendor(vendor);
		}

		return "redirect:/procurement/vendor/list.do";
	}


	
	@RequestMapping(value = "/vendor/list.do", method = RequestMethod.GET)
	public ModelAndView getListVendor() {

		List<Vendor> vendor = vendorService.listVendors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("vendorList", vendor);

		return new ModelAndView("procurement/listVendor", model);
	}

	// for save a Requisition Dtl
	@RequestMapping(value = "/requisition/dtlSave.do", method = RequestMethod.POST)
	public String saveRequisitionDtl(RequisitionDtl requisitionDtl) {

		System.out.println("requisitionDtl============== " + requisitionDtl
				+ " id" + requisitionDtl.getRequisitionId());
		requisitionDetailsService.addRequisitionDetail(requisitionDtl);

		return "redirect:/procurement/requisition/show.do?id="
				+ requisitionDtl.getRequisitionId();
	}

	// update a Requisition Dtl
	@RequestMapping(value = "/requisition/dtlEdit.do", method = RequestMethod.POST)
	public String updateRequisitionDtl(RequisitionDtl2 requisitionDtl) {

		requisitionDetailsService.editRequisitionDetail(requisitionDtl);

		return "redirect:/procurement/requisition/show.do?id="
				+ requisitionDtl.getRequisitionId();
	}

	// update a Requisition Mst
	@RequestMapping(value = "/requisition/mstUpdate.do", method = RequestMethod.POST)
	public String updateRequisitionMst(RequisitionMst requisitionMst) {

		RequisitionMst requisitionMstDB = requisitionMasterService
				.getRequisitionMst(requisitionMst.getId());

		// have to set all master field which is editable in view page
		requisitionMstDB.setJustification(requisitionMst.getJustification());
		requisitionMstDB.setModifiedBy(requisitionMst.getModifiedBy());
		requisitionMstDB.setModifiedDate(new Date());

		// end of update particular master field

		requisitionMasterService.editRequisitionMst(requisitionMstDB);
		return "redirect:/procurement/requisition/show.do?id="
				+ requisitionMst.getId();
	}

	// for Delete a Vendor
	@RequestMapping(value = "/vendor/delete.do", method = RequestMethod.GET)
	public ModelAndView deleteVendor(
			@Valid @ModelAttribute("vendor") Vendor vendor) {
		vendorService.deleteVendor(vendor);
		List<Vendor> vendors = vendorService.listVendors();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("vendorList", vendors);
		return new ModelAndView("procurement/listVendor", model);
	}

	// for get a Vendor
	@RequestMapping(value = "/vendor/viewVendorInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewAndEditVendor(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			Vendor vendor = gson.fromJson(json, Vendor.class);

			Vendor selectVendor = vendorService.getVendor(vendor.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectVendor);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	// for get a Vendor
	@RequestMapping(value = "/requisition/viewInventoryItem.do", method = RequestMethod.POST)
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

	// for get a Vendor
	@RequestMapping(value = "/requisition/viewRequisitionDtl.do", method = RequestMethod.POST)
	@ResponseBody
	public String viewRequisitionDtl(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			RequisitionDtl requisitionDtl = gson.fromJson(json,
					RequisitionDtl.class);

			RequisitionDtl selectRequisitionDtl = requisitionDetailsService
					.getRequisitionDetail(requisitionDtl.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectRequisitionDtl);

		} else {
			Thread.sleep(125 * 1000);
		}

		return toJson;
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

	public RequisitionMasterService getRequisitionMasterService() {
		return requisitionMasterService;
	}

	public void setRequisitionMasterService(
			RequisitionMasterService requisitionMasterService) {
		this.requisitionMasterService = requisitionMasterService;
	}

	public RequisitionDetailsService getRequisitionDetailsService() {
		return requisitionDetailsService;
	}

	public void setRequisitionDetailsService(
			RequisitionDetailsService requisitionDetailsService) {
		this.requisitionDetailsService = requisitionDetailsService;
	}

	public VendorService getVendorService() {
		return vendorService;
	}

	public void setVendorService(VendorService vendorService) {
		this.vendorService = vendorService;
	}

}
