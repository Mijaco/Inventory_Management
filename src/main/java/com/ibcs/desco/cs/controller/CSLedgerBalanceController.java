package com.ibcs.desco.cs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSItemTransactionDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
public class CSLedgerBalanceController extends Constrants {
	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	// get store balance List filter on login user and login user role
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/ledger/balance.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAllLedgerBalance() {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		/*
		 * List<CSItemTransactionMst> csItemTransactionMstList =
		 * (List<CSItemTransactionMst>) (Object) commonService
		 * .getAllObjectList("CSItemTransactionMst");
		 */

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumn("ItemMaster", "itemType", "C");

		//Map<String, String> itemMap = new HashMap<String, String>();
		Map<String, ItemMaster> itemMap = new HashMap<String, ItemMaster>();
		for (ItemMaster itemMaster : itemMasterList) {
			//itemMap.put(itemMaster.getItemId(), itemMaster.getItemName());
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}

		List<CSItemTransactionMst> csItemTransactionMstList = (List<CSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("CSItemTransactionMst", "khathId",
						descoKhathList.get(0).getId() + "", "ledgerName",
						NEW_SERVICEABLE);

		for (CSItemTransactionMst mst : csItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()));
			//mst.setItemName(itemMap.get(mst.getItemCode()).getItemName());
			//mst.setUom(itemMap.get(mst.getItemCode()).getUnitCode());
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());

		model.put("csItemTransactionMstList", csItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/csStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/ledger/balanceSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ledgerBalanceSearch(
			CSItemTransactionMst csItemTransactionMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumn("ItemMaster", "itemType", "C");
		//Map<String, String> itemMap = new HashMap<String, String>();
		Map<String, ItemMaster> itemMap = new HashMap<String, ItemMaster>();
		for (ItemMaster itemMaster : itemMasterList) {
			//itemMap.put(itemMaster.getItemId(), itemMaster.getItemName());
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}

		List<CSItemTransactionMst> csItemTransactionMstList = this
				.simulateSearchResult(csItemTransactionMst.getItemCode(),
						csItemTransactionMst.getKhathId(),
						csItemTransactionMst.getLedgerName(), department);

		for (CSItemTransactionMst mst : csItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()));
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		
		//Added by: Ihteshamul Alam
		String khathId = "", ledgerName="", itemCode = "";
		khathId = csItemTransactionMst.getKhathId().toString();
		ledgerName = csItemTransactionMst.getLedgerName();
		itemCode = csItemTransactionMst.getItemCode();
		model.put("khathId", khathId);
		model.put("ledgerName", ledgerName);
		model.put("itemCode", itemCode);

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("csItemTransactionMstList", csItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/csStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	private List<CSItemTransactionMst> simulateSearchResult(String itemCode,
			Integer khathId, String ledgerName, Departments department) {

		List<CSItemTransactionMst> searchTranList = new ArrayList<CSItemTransactionMst>();
		/*
		 * List<CSItemTransactionMst> csItemTransactionMstList =
		 * (List<CSItemTransactionMst>) (Object) commonService
		 * .getAllObjectList("CSItemTransactionMst");
		 */

		List<CSItemTransactionMst> csItemTransactionMstList = (List<CSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("CSItemTransactionMst", "khathId",
						khathId + "", "ledgerName", ledgerName);

		// iterate a list and filter by tagName
		for (CSItemTransactionMst csItemTransactionMst : csItemTransactionMstList) {
			if (csItemTransactionMst.getItemCode().toLowerCase()
					.contains(itemCode.toLowerCase())) {
				searchTranList.add(csItemTransactionMst);
			}
		}
		if (itemCode.length() > 0) {
			return searchTranList;
		} else {
			return csItemTransactionMstList;
		}
	}

	// cs item ledger
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/ledger/itmTnxLedger.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getItemWiseTrxnLedger(
			CSItemTransactionMst csItemTransactionMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		CSItemTransactionMst csItemTransactionMstDB = (CSItemTransactionMst) commonService
				.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id",
						csItemTransactionMst.getId() + "");

		ItemMaster itemMaster = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						csItemTransactionMstDB.getItemCode());
		csItemTransactionMstDB.setItemName(itemMaster.getItemName());
		csItemTransactionMstDB.setUom(itemMaster.getUnitCode());

		/*
		 * List<CSItemTransactionDtl> csItemTransactionDtlList =
		 * (List<CSItemTransactionDtl>) (Object) commonService
		 * .getObjectListByAnyColumn("CSItemTransactionDtl",
		 * "csItemTransactionMst.id", csItemTransactionMst.getId() + "");
		 */

		List<CSItemTransactionDtl> csItemTransactionDtlList = (List<CSItemTransactionDtl>) (Object) commonService
				.getObjectListByAnyColumnOrderByAnyColumn(
						"CSItemTransactionDtl", "csItemTransactionMst.id",
						csItemTransactionMst.getId() + "", "id", ASSENDING);

		List<CSItemTransactionDtl> csItemTrxnDtlList = new ArrayList<CSItemTransactionDtl>();
		double balance = 0.0;
		for (CSItemTransactionDtl csItemTransactionDtl : csItemTransactionDtlList) {
			if (csItemTransactionDtl.isTnxnMode()) {
				balance += csItemTransactionDtl.getQuantity();
			} else {
				balance -= csItemTransactionDtl.getQuantity();
			}
			csItemTransactionDtl.setBalance(balance);
			csItemTrxnDtlList.add(csItemTransactionDtl);
		}

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("csItemTransactionMst", csItemTransactionMstDB);
		// model.put("csItemTransactionDtlList", csItemTransactionDtlList);
		model.put("csItemTransactionDtlList", csItemTrxnDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/csItemTrxnLedger", model);

	}

}
