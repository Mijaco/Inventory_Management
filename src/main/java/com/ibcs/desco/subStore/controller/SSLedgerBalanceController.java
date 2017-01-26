package com.ibcs.desco.subStore.controller;

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
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;

@Controller
public class SSLedgerBalanceController extends Constrants {
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
	@RequestMapping(value = "/ss/ledger/balance.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAllLedgerBalance() {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		/*List<SSItemTransactionMst> ssItemTransactionMstList = (List<SSItemTransactionMst>) (Object) commonService
				.getAllObjectList("SSItemTransactionMst");*/

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumn("ItemMaster", "itemType", "C");
		Map<String, ItemMaster> itemMap = new HashMap<String, ItemMaster>();
		for (ItemMaster itemMaster : itemMasterList) {
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}
		
		List<SSItemTransactionMst> ssItemTransactionMstList = (List<SSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("SSItemTransactionMst", "khathId",
						descoKhathList.get(0).getId() + "", "ledgerName", NEW_SERVICEABLE);
		
		for (SSItemTransactionMst mst : ssItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()).getItemName());
			//mst.setUom(itemMap.get(mst.getItemCode()).getUnitCode());
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());

		model.put("ssItemTransactionMstList", ssItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("subStore/ssStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/ledger/balanceSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ledgerBalanceSearch(
			SSItemTransactionMst ssItemTransactionMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumn("ItemMaster", "itemType", "C");
		Map<String, ItemMaster> itemMap = new HashMap<String, ItemMaster>();
		for (ItemMaster itemMaster : itemMasterList) {
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}

		List<SSItemTransactionMst> ssItemTransactionMstList = this
				.simulateSearchResult(ssItemTransactionMst.getItemCode(),
						ssItemTransactionMst.getKhathId(),
						ssItemTransactionMst.getLedgerName(), department);
		
		for (SSItemTransactionMst mst : ssItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()));
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");
		
		//Added by: Ihteshamul Alam
		String khathId = "", ledgerName="", itemCode = "";
		khathId = ssItemTransactionMst.getKhathId().toString();
		ledgerName = ssItemTransactionMst.getLedgerName();
		itemCode = ssItemTransactionMst.getItemCode();

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("ssItemTransactionMstList", ssItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		
		model.put("khathId", khathId);
		model.put("ledgerName", ledgerName);
		model.put("itemCode", itemCode);
		return new ModelAndView("subStore/ssStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	private List<SSItemTransactionMst> simulateSearchResult(String itemCode,
			Integer khathId, String ledgerName, Departments department) {

		List<SSItemTransactionMst> searchTranList = new ArrayList<SSItemTransactionMst>();
		/*
		 * List<CSItemTransactionMst> csItemTransactionMstList =
		 * (List<CSItemTransactionMst>) (Object) commonService
		 * .getAllObjectList("CSItemTransactionMst");
		 */

		List<SSItemTransactionMst> ssItemTransactionMstList = (List<SSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("SSItemTransactionMst", "khathId",
						khathId + "", "ledgerName", ledgerName);

		// iterate a list and filter by tagName
		for (SSItemTransactionMst ssItemTransactionMst : ssItemTransactionMstList) {
			if (ssItemTransactionMst.getItemCode().toLowerCase()
					.contains(itemCode.toLowerCase())) {
				searchTranList.add(ssItemTransactionMst);
			}
		}
		if (itemCode.length() > 0) {
			return searchTranList;
		} else {
			return ssItemTransactionMstList;
		}
	}

	// cs item ledger
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/ledger/itmTnxLedger.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getItemWiseTrxnLedger(
			SSItemTransactionMst ssItemTransactionMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SSItemTransactionMst ssItemTransactionMstDB = (SSItemTransactionMst) commonService
				.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "id",
						ssItemTransactionMst.getId() + "");
		
		ItemMaster itemMaster = (ItemMaster) commonService
				.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
						ssItemTransactionMstDB.getItemCode());
		ssItemTransactionMstDB.setItemName(itemMaster.getItemName());
		ssItemTransactionMstDB.setUom(itemMaster.getUnitCode());
		
		/*List<SSItemTransactionDtl> ssItemTransactionDtlList = (List<SSItemTransactionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSItemTransactionDtl",
						"ssItemTransactionMst.id", ssItemTransactionMst.getId()
								+ "");*/
		List<SSItemTransactionDtl> ssItemTransactionDtlList = (List<SSItemTransactionDtl>) (Object) commonService
				.getObjectListByAnyColumnOrderByAnyColumn("SSItemTransactionDtl",
				"ssItemTransactionMst.id", ssItemTransactionMst.getId()
				+ "", "id", ASSENDING);

		List<SSItemTransactionDtl> ssItemTrxnDtlList = new ArrayList<SSItemTransactionDtl>();
		double balance = 0.0;
		if (ssItemTransactionDtlList != null) {
			for (SSItemTransactionDtl ssItemTransactionDtl : ssItemTransactionDtlList) {
				if (ssItemTransactionDtl.isTnxnMode()) {
					balance += ssItemTransactionDtl.getQuantity();
				} else {
					balance -= ssItemTransactionDtl.getQuantity();
				}
				ssItemTransactionDtl.setBalance(balance);
				ssItemTrxnDtlList.add(ssItemTransactionDtl);
			}
		}
		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhathList", descoKhathList);
		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("ssItemTransactionMst", ssItemTransactionMstDB);
		// model.put("csItemTransactionDtlList", csItemTransactionDtlList);
		model.put("ssItemTransactionDtlList", ssItemTrxnDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("subStore/ssItemTrxnLedger", model);

	}

}
