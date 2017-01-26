package com.ibcs.desco.localStore.controller;

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
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;

@Controller
public class LSLedgerBalanceController extends Constrants{
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
	@RequestMapping(value = "/ls/ledger/balance.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getAllLedgerBalance() {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		/*List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
				.getObjectListByAnyColumn("LSItemTransactionMst", "sndCode",
						department.getDescoCode());*/
		
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumn("ItemMaster", "itemType", "C");
		//Map<String, String> itemMap = new HashMap<String, String>();
		Map<String, ItemMaster> itemMap = new HashMap<String, ItemMaster>();
		for (ItemMaster itemMaster : itemMasterList) {
			//itemMap.put(itemMaster.getItemId(), itemMaster.getItemName());
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}
		
		List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("LSItemTransactionMst", "ledgerName", NEW_SERVICEABLE, "sndCode", department.getDescoCode());
		
		for (LSItemTransactionMst mst : lsItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()));
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}

		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("lsItemTransactionMstList", lsItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/lsStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/ledger/balanceSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ledgerBalanceSearch(
			LSItemTransactionMst lsItemTransactionMst) {
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
			//itemMap.put(itemMaster.getItemId(), itemMaster.getItemName());
			itemMap.put(itemMaster.getItemId(), itemMaster);
		}

		List<LSItemTransactionMst> lsItemTransactionMstList = this
				.simulateSearchResult(lsItemTransactionMst.getItemCode(), lsItemTransactionMst.getLedgerName(),
						department);
		
		for (LSItemTransactionMst mst : lsItemTransactionMstList) {
			//mst.setItemName(itemMap.get(mst.getItemCode()));
			mst.setItemMaster(itemMap.get(mst.getItemCode()));
		}
		
		//Added by: Ihteshamul Alam
		String khathId = "", ledgerName="", itemCode = "";
		//khathId = lsItemTransactionMst.getKhathId().toString();
		ledgerName = lsItemTransactionMst.getLedgerName();
		itemCode = lsItemTransactionMst.getItemCode();
		
		model.put("khathId", khathId);
		model.put("ledgerName", ledgerName);
		model.put("itemCode", itemCode);
		
		model.put("ledgerBooks", Constrants.LedgerBook1.values());
		model.put("lsItemTransactionMstList", lsItemTransactionMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/lsStockBalanceList", model);

	}

	@SuppressWarnings("unchecked")
	private List<LSItemTransactionMst> simulateSearchResult(String itemCode, String ledgerName,
			Departments department) {

		List<LSItemTransactionMst> searchTranList = new ArrayList<LSItemTransactionMst>();
		
		List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
				.getObjectListByTwoColumn("LSItemTransactionMst", "ledgerName", ledgerName, "sndCode", department.getDescoCode());
		
		/*List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
				.getObjectListByAnyColumn("LSItemTransactionMst", "sndCode",
						department.getDescoCode());*/

		// iterate a list and filter by tagName
		for (LSItemTransactionMst lsItemTransactionMst : lsItemTransactionMstList) {
			if (lsItemTransactionMst.getItemCode().toLowerCase()
					.contains(itemCode.toLowerCase())) {
				searchTranList.add(lsItemTransactionMst);
			}
		}
		if (itemCode.length() > 0) {
			return searchTranList;
		} else {
			return lsItemTransactionMstList;
		}
	}
	
	
	// cs item ledger
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/ls/ledger/itmTnxLedger.do", method = RequestMethod.GET)
		@PreAuthorize("isAuthenticated()")
		public ModelAndView getItemWiseTrxnLedger(LSItemTransactionMst lsItemTransactionMst) {
			Map<String, Object> model = new HashMap<String, Object>();
			// String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			// String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			LSItemTransactionMst lsItemTransactionMstDB = (LSItemTransactionMst) commonService
				.getAnObjectByAnyUniqueColumn("LSItemTransactionMst", "id", lsItemTransactionMst.getId()+"");
			
			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId",
							lsItemTransactionMstDB.getItemCode());
			lsItemTransactionMstDB.setItemName(itemMaster.getItemName());
			lsItemTransactionMstDB.setUom(itemMaster.getUnitCode());
			
			/*List<LSItemTransactionDtl> lsItemTransactionDtlList = (List<LSItemTransactionDtl>)(Object)commonService
					.getObjectListByAnyColumn("LSItemTransactionDtl", "lsItemTransactionMst.id", lsItemTransactionMst.getId()+"");*/
			
			List<LSItemTransactionDtl> lsItemTransactionDtlList = (List<LSItemTransactionDtl>)(Object)commonService
					.getObjectListByAnyColumnOrderByAnyColumn("LSItemTransactionDtl",
					"lsItemTransactionMst.id", lsItemTransactionMst.getId()
					+ "", "id", ASSENDING);
			
			List<LSItemTransactionDtl> lsItemTrxnDtlList = new ArrayList<LSItemTransactionDtl>();
			double balance = 0.0;
			for(LSItemTransactionDtl lsItemTransactionDtl: lsItemTransactionDtlList){
				if(lsItemTransactionDtl.isTnxnMode()){
					balance+=lsItemTransactionDtl.getQuantity();
				}else{
					balance-=lsItemTransactionDtl.getQuantity();
				}
				lsItemTransactionDtl.setBalance(balance);
				lsItemTrxnDtlList.add(lsItemTransactionDtl);
			}
			
			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");
			
			model.put("descoKhathList", descoKhathList);
			model.put("ledgerBooks", Constrants.LedgerBook1.values());
			model.put("lsItemTransactionMst", lsItemTransactionMstDB);
			//model.put("csItemTransactionDtlList", csItemTransactionDtlList);
			model.put("lsItemTransactionDtlList", lsItemTrxnDtlList);
			model.put("deptName", department.getDeptName());
			model.put("deptAddress",
					department.getAddress() + ", " + department.getContactNo());
			return new ModelAndView("localStore/lsItemTrxnLedger", model);

		}

}
