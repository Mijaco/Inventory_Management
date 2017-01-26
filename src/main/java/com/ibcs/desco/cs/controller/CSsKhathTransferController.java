package com.ibcs.desco.cs.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;
import com.ibcs.desco.common.model.KhathTransferApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.KhathTransferHierarchyHistoryService;
import com.ibcs.desco.cs.bean.ItemReceived;
import com.ibcs.desco.cs.bean.KhathTransferMstDtl;
import com.ibcs.desco.cs.bean.Loc7sQtyGridValues;
import com.ibcs.desco.cs.bean.Location7sMstDtl;
import com.ibcs.desco.cs.bean.Location7sQty;
import com.ibcs.desco.cs.model.CSItemLocationDtl;
import com.ibcs.desco.cs.model.CSItemLocationMst;
import com.ibcs.desco.cs.model.CSItemTransactionDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.KhathTransferDtl;
import com.ibcs.desco.cs.model.KhathTransferMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSItemLocationMstService;
import com.ibcs.desco.cs.service.CSItemTransactionMstService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
@RequestMapping(value = "/cs/khath/")
@PropertySource("classpath:common.properties")
public class CSsKhathTransferController extends Constrants {

	// Create Object for Service Classes 

	@Autowired
	CSItemLocationMstService csItemLocationMstService;

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CSItemTransactionMstService csItemTransactionMstService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	KhathTransferHierarchyHistoryService khathTransferHierarchyHistoryService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Value("${centralStore.khatTransferNo.prefix}")
	private String khatTransferNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "transferForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getKhathTransferFrom() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// select all item category and send to view page
		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);

		// select all item category and send to view page khathList
		List<DescoKhath> khathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationOptions = "";
		for (int i = 0; i < locationsList.size(); i++) {
			locationOptions += locationsList.get(i).getId() + ":"
					+ locationsList.get(i).getName() + ";";
		}
		locationOptions = locationOptions.substring(0,
				locationOptions.length() - 1);

		//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String locationList = ow.writeValueAsString(locationsList);
		// model.put("locationList", "'" + locationOptions + "'");

		model.put("khathList", khathList);
		model.put("uuid", UUID.randomUUID());
		model.put("ledgerBooks", Constrants.LedgerBook.values());
		model.put("locationList", "'" + locationOptions + "'");

		return new ModelAndView("centralStore/csKhathTransferForm", model);
	}

	// SELECT ITEM DETAILS BY ITEM CODE, KHATH & LEDGER NAME || By ashid
	@RequestMapping(value = "/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			// I have assigned, selectedGUI_item.categoryId=khathId,
			// selectedGUI_item.itemName=LedgerName;
			ItemMaster selectedGUI_item = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(selectedGUI_item.getId());

			int khathIdFrom = selectedGUI_item.getCategoryId();
			String ledgerNameFrom = selectedGUI_item.getItemName();
			String itemCode = selectItemFromDb.getItemId();
			List<Object> obList = commonService.getObjectListByThreeColumn(
					"CSItemTransactionMst", "itemCode", itemCode, "khathId",
					khathIdFrom + "", "ledgerName", ledgerNameFrom);
			CSItemTransactionMst tnxMst = new CSItemTransactionMst();
			if (obList.size() > 0) {
				tnxMst = (CSItemTransactionMst) obList.get(0);
			}
			if (tnxMst.getQuantity() != 0.0) {
				// assigned present qty in remarks
				selectItemFromDb.setRemarks(tnxMst.getQuantity() + "");
			}
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);
		} else {
			Thread.sleep(125 * 1000);
		}
		return toJson;
	}

	@RequestMapping(value = "saveKhathTransfer.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveKhathTransfer(Model model,
			@ModelAttribute("itemReceived") KhathTransferMstDtl bean) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// items comes as List
		List<String> itemIdList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> transferedQtyList = null;
		List<Double> unitCostList = null;
		List<Double> totalCostList = null;
		List<String> remarksList = null;

		DescoKhath sourceKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						bean.getKhathIdFrom() + "");
		DescoKhath targetKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						bean.getKhathIdTo() + "");

		// Set value for the Master Information of Receiving Report
		KhathTransferMst khathTransferMst = new KhathTransferMst();
		khathTransferMst.setKhathIdFrom(bean.getKhathIdFrom());
		khathTransferMst.setKhathFrom(sourceKhath.getKhathName());
		khathTransferMst.setKhathIdTo(bean.getKhathIdTo());
		khathTransferMst.setKhathTo(targetKhath.getKhathName());
		khathTransferMst.setLedgerBook(bean.getLedgerBook());
		khathTransferMst.setUuid(bean.getUuid());
		khathTransferMst.setReferenceDoc("");
		khathTransferMst.setReferenceDoc(bean.getTransferApprovalDoc());
		khathTransferMst.setCreatedBy(userName);
		khathTransferMst.setCreatedDate(new Date());

		// Details Table Save to Database from GUI value with Master Table Id

		if (bean.getItemCode() != null) {
			itemIdList = bean.getItemCode();
		}

		if (bean.getItemName() != null) {
			itemNameList = bean.getItemName();
		}

		if (bean.getUom() != null) {
			uomList = bean.getUom();
		}

		if (bean.getTransferedQty() != null) {
			transferedQtyList = bean.getTransferedQty();
		}

		if (bean.getUnitCost() != null) {
			unitCostList = bean.getUnitCost();
		}

		if (bean.getTotalCost() != null) {
			totalCostList = bean.getTotalCost();
		}

		if (bean.getRemarks() != null) {
			remarksList = bean.getRemarks();
		}

		// Get All Approval Hierarchy on CS_RECEIVING_ITEMS
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_KHATH_TRANSFER);

		// get All State code which define for Receiving process
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get minimum hierarchy information
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_KHATH_TRANSFER, stateCodes[0].toString());

		// get Role List in a Array
		String[] rol = approvalHierarchy.getRoleName().split(",");

		// Auto Numbering on khath transfer
		String tsfrNo = "";

		// login person have permission for received process??
		int flag = 0;
		for (int i = 0; i < rol.length; i++) {
			if (rol[i].equals(roleName)) {
				flag = 1;
				break;
			}
		}

		// match the role for receiving process ??
		if (flag == 1) {

			// for Approval Hierarchy History Maintainanace Start
			KhathTransferApprovalHierarchyHistory approvalHierarchyHistory = new KhathTransferApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(roleName);
			approvalHierarchyHistory.setOperationName(CS_KHATH_TRANSFER);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setCreatedDate(new Date());
			if (stateCodes.length > 0) {
				// All time start with 1st
				approvalHierarchyHistory.setStateCode(stateCodes[0]);
				// State code set from approval heirarchy Table
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());
			}
			approvalHierarchyHistory.setStatus(OPEN);
			approvalHierarchyHistory.setActive(true);
			approvalHierarchyHistory.setDeptId(deptId);

			// Receiving Items Save Details Start from Here
			if (itemIdList != null && itemIdList.size() > 0) {
				String descoDeptCode = department.getDescoCode();

				tsfrNo = commonService.getOperationIdByPrefixAndSequenceName(
						khatTransferNoPrefix, descoDeptCode, separator,
						"cs_khath_trsf_seq");
				khathTransferMst.setTransferNo(tsfrNo);
				approvalHierarchyHistory.setOperationId(tsfrNo);

				// Receiving Items Save to Master Table
				commonService.saveOrUpdateModelObjectToDB(khathTransferMst);

				// in khathTransferMst New Insert than get Last khathTransferMst
				// Id
				Integer maxMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("KhathTransferMst", "id");
				KhathTransferMst khathTransferMstTemp = (KhathTransferMst) commonService
						.getAnObjectByAnyUniqueColumn("KhathTransferMst", "id",
								maxMstId + "");

				for (int i = 0; i < itemIdList.size(); i++) {
					KhathTransferDtl khathTransferDtl = new KhathTransferDtl();
					khathTransferDtl.setItemId(itemIdList.get(i));

					if (!uomList.isEmpty()) {
						khathTransferDtl.setDescription(itemNameList.get(i));
					} else {
						khathTransferDtl.setDescription("");
					}

					if (!uomList.isEmpty()) {
						khathTransferDtl.setUom(uomList.get(i));
					} else {
						khathTransferDtl.setUom("");
					}

					if (!transferedQtyList.isEmpty()) {
						khathTransferDtl.setTrnasferQty(transferedQtyList
								.get(i));
					} else {
						khathTransferDtl.setTrnasferQty(0.0);
					}

					if (!unitCostList.isEmpty()) {
						khathTransferDtl.setUnitCost(unitCostList.get(i));
					} else {
						khathTransferDtl.setUnitCost(0.0);
					}

					if (!totalCostList.isEmpty()) {
						khathTransferDtl.setTotalCost(totalCostList.get(i));
					} else {
						khathTransferDtl.setTotalCost(0.0);
					}

					if (!remarksList.isEmpty()) {
						khathTransferDtl.setRemarks(remarksList.get(i));
					} else {
						khathTransferDtl.setRemarks("");
					}

					// set id null for add all items in db and id set from auto
					// number
					khathTransferDtl.setId(null);
					khathTransferDtl.setKhathTransferMst(khathTransferMstTemp); // khathTransferMstTemp
					khathTransferDtl.setCreatedBy(userName);
					khathTransferDtl.setCreatedDate(new Date());
					khathTransferDtl.setActive(true);
					khathTransferDtl.setTransferNo(tsfrNo);

					// item details is saved to db if transfer qty is greater
					// than zero
					if (khathTransferDtl.getTrnasferQty() > 0) {
						commonService
								.saveOrUpdateModelObjectToDB(khathTransferDtl);
					}

				}

				// Insert a row to Approval Hierarchy History Table
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

		}

		return "redirect:/cs/khath/khathTransferShow.do?transferNo=" + tsfrNo;
		// return "centralStore/csKhathTransferForm";
	}

	// Show the items which is just drafted to transfer from khath
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/khathTransferShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView khathTransferShow(KhathTransferMst bean) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String buttonValue = null;
		List<ApprovalHierarchy> nextManRcvProcs = null;
		String currentStatus = "";

		// operation Id which selected by login user
		String transferNo = bean.getTransferNo();

		// get All History for this operation Id which process is already done
		List<KhathTransferApprovalHierarchyHistory> khathHierarchyHistoryList = khathTransferHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_KHATH_TRANSFER, transferNo, "DONE");

		// get All approval hierarchy of this process
		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_KHATH_TRANSFER);

		// button name from db which berry user to user
		buttonValue = approveHeirchyList.get(0).getButtonName();
		// set current status from db
		if (!khathHierarchyHistoryList.isEmpty()) {
			currentStatus = khathHierarchyHistoryList.get(
					khathHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		// get KhathTransferMst information against current operation id
		KhathTransferMst khathTransferMst = (KhathTransferMst) commonService
				.getAnObjectByAnyUniqueColumn("KhathTransferMst", "transferNo",
						transferNo);

		// get all item info against current transferNo no / operation id
		List<KhathTransferDtl> khathTransferDtlList = (List<KhathTransferDtl>) (Object) commonService
				.getObjectListByAnyColumn("KhathTransferDtl", "transferNo",
						transferNo);

		{
			// this block will set present ledger qty in GUI
			for (int i = 0; i < khathTransferDtlList.size(); i++) {
				int khathId = khathTransferMst.getKhathIdFrom();
				String ledgerBook = khathTransferMst.getLedgerBook();
				String itemCode = khathTransferDtlList.get(i).getItemId();

				List<CSItemTransactionMst> tnxMstList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"khathId", khathId + "", "ledgerName",
								ledgerBook, "itemCode", itemCode);

				Double presentQty = 0.0;
				if (tnxMstList.size() > 0) {
					presentQty = tnxMstList.get(0).getQuantity();
				}

				khathTransferDtlList.get(i).setPresentQty(presentQty);
				// set transfer Qty from TempLocation
				khathTransferDtlList.get(i).setTrnasferQty(
						getTotalQtyFromTempLocation(khathTransferMst.getUuid(),
								khathTransferDtlList.get(i).getItemId()));
			}
			// End of this block

		}

		// Send To next User as my wish
		List<KhathTransferApprovalHierarchyHistory> ktHistoryOpenList = khathTransferHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_KHATH_TRANSFER, transferNo, "OPEN");

		int currentStateCode = ktHistoryOpenList.get(
				ktHistoryOpenList.size() - 1).getStateCode();

		// send to whom
		nextManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManRcvProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!ktHistoryOpenList.isEmpty() && ktHistoryOpenList != null) {
			// get current state code
			int stateCode = ktHistoryOpenList.get(ktHistoryOpenList.size() - 1)
					.getStateCode();

			// deciede for return or not
			returnStateCode = ktHistoryOpenList.get(
					ktHistoryOpenList.size() - 1).getReturn_state();

			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CS_KHATH_TRANSFER, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = khathTransferMst.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		khathTransferMst.setCreatedBy(createBy.getName());
		
		// all information send to browser
		model.put("returnStateCode", returnStateCode);
		model.put("khathHierarchyHistoryList", khathHierarchyHistoryList);
		model.put("currentStatus", currentStatus);
		model.put("khathTransferMst", khathTransferMst);
		model.put("khathTransferDtlList", khathTransferDtlList);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManRcvProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		// 
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "CS", "parentId");
		String locationOptions = "";
		for (int i = 0; i < locationsList.size(); i++) {
			locationOptions += locationsList.get(i).getId() + ":"
					+ locationsList.get(i).getName() + ";";
		}
		locationOptions = locationOptions.substring(0,
				locationOptions.length() - 1);

		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String locationList = ow.writeValueAsString(locationsList);
		model.put("locationList", "'" + locationOptions + "'");

		return new ModelAndView("centralStore/csKhathTransferShow", model);
	}

	// Show all transfered List from khath to Khath
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/khathTransferedShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView khathTransferedShow(KhathTransferMst bean) {

		Map<String, Object> model = new HashMap<String, Object>();
		// operation Id which selected by login user
		String transferNo = bean.getTransferNo();
		// get KhathTransferMst information against current operation id
		KhathTransferMst khathTransferMst = (KhathTransferMst) commonService
				.getAnObjectByAnyUniqueColumn("KhathTransferMst", "transferNo",
						transferNo);
		// get all item info against current transferNo no / operation id
		List<KhathTransferDtl> khathTransferDtlList = (List<KhathTransferDtl>) (Object) commonService
				.getObjectListByAnyColumn("KhathTransferDtl", "transferNo",
						transferNo);
		// get All History for this operation Id which process is already done
		List<KhathTransferApprovalHierarchyHistory> khathHierarchyHistoryList = khathTransferHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_KHATH_TRANSFER, transferNo, "DONE");

		// set current status from db
		String currentStatus = "";
		if (!khathHierarchyHistoryList.isEmpty()) {
			currentStatus = khathHierarchyHistoryList.get(
					khathHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = khathTransferMst.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		khathTransferMst.setCreatedBy(createBy.getName());

		model.put("khathTransferMst", khathTransferMst);
		model.put("khathTransferDtlList", khathTransferDtlList);
		model.put("khathHierarchyHistoryList", khathHierarchyHistoryList);
		model.put("currentStatus", currentStatus);

		return new ModelAndView("centralStore/csKhathTransferedShow", model);
	}

	// Added by Ashid
	// Khath Transfer Approving process
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ktApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String approveCsKhathTransferBySubmit(Model model,
			@ModelAttribute("itemReceived") ItemReceived bean) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// Send return to next user who backed me
		if (!bean.getReturn_state().equals("")
				|| bean.getReturn_state().length() > 0) {

			List<KhathTransferApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<KhathTransferApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"KhathTransferApprovalHierarchyHistory",
							"operationId", bean.getReceivedReportNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			KhathTransferApprovalHierarchyHistory approvalHierarchyHistory = (KhathTransferApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"KhathTransferApprovalHierarchyHistory", "id",
							ids[0] + "");
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CS_KHATH_TRANSFER, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setJustification(bean.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistoryNextState = new ItemRcvApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CS_KHATH_TRANSFER, bean.getReturn_state());
			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setStatus(OPEN);

			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(bean.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(bean
					.getReceivedReportNo() + "");
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());

			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {

			// get Khath Transfer Item List against KT No
			String operationNo = bean.getReceivedReportNo().toString();
			KhathTransferMst ktMst = (KhathTransferMst) commonService
					.getAnObjectByAnyUniqueColumn("KhathTransferMst",
							"transferNo", operationNo);
			List<KhathTransferDtl> khathDtlList = (List<KhathTransferDtl>) (Object) commonService
					.getObjectListByAnyColumn("KhathTransferDtl", "transferNo",
							operationNo);

			// get All State Codes from Approval Hierarchy and sort Descending
			// order for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CS_KHATH_TRANSFER);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by Khath Transfer
			// No (Operation Id)
			List<KhathTransferApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<KhathTransferApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"KhathTransferApprovalHierarchyHistory",
							"operationId", bean.getReceivedReportNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			KhathTransferApprovalHierarchyHistory approvalHierarchyHistory = (KhathTransferApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"KhathTransferApprovalHierarchyHistory", "id",
							ids[0] + "");

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {
				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_KHATH_TRANSFER, nextStateCode + "");
					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy
							.getStateName());
					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setDeptId(deptId);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy
							.getRoleName());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process will done for login user
				if (state == currentStateCode) {
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_KHATH_TRANSFER, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setJustification(bean
							.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {

					// check is insert before
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setJustification(bean
							.getJustification());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					{// update kTdtl qty from temp location
						for (KhathTransferDtl d : khathDtlList) {
							d.setTrnasferQty(getTotalQtyFromTempLocation(
									ktMst.getUuid(), d.getItemId()));
							d.setTotalCost(d.getUnitCost() * d.getTrnasferQty());
							commonService.saveOrUpdateModelObjectToDB(d);
						}
					}
					this.csLedgerLocationKT(khathDtlList, userName, roleName,
							authUser, department, ktMst);
					// set KT approved flag after TNX complete
					ktMst.setApprovedFlag(CS_KHATH_TRANSFER_APPROVED);
					commonService.saveOrUpdateModelObjectToDB(ktMst);
					
					//remove Templocation data by uuid after KT approval 
					commonService.deleteAnObjectListByAnyColumn("TempItemLocation", "uuid", ktMst.getUuid());

					model.addAttribute("transferNo", ktMst.getTransferNo());
					// To be Fix view page
					// return "centralStore/csReceivedReport";
					return "redirect:/cs/khath/transferedList.do";

				}
			}

		}
		return "redirect:/cs/khath/ktList.do";
	}

	@SuppressWarnings("unchecked")
	private Double getTotalQtyFromTempLocation(String uuid, String itemCode) {
		Double qty = 0.0;
		List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
				.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
						"itemCode", itemCode);
		for (TempItemLocation t : tempItemLocationList) {
			qty += t.getQuantity();
		}
		return qty;
	}

	//
	private CSItemTransactionDtl saveOrUpdateTnxMstDtl(KhathTransferMst ktMst,
			KhathTransferDtl ktDtl, String userName, String khathId,
			boolean tnxnMode) {

		CSItemTransactionMst csTnxMst = null;
		// CSItemTransactionMst csItemTransactionMstTo = null; if(tnxnMode){
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", khathId);
		CSItemTransactionMst csTnxMstDB = csItemTransactionMstService
				.getCSItemTransectionMst(ktDtl.getItemId(),
						Integer.parseInt(khathId), ktMst.getLedgerBook());

		if (csTnxMstDB != null) {
			csTnxMstDB.setModifiedBy(userName);
			csTnxMstDB.setModifiedDate(new Date());
			// condition
			if (tnxnMode) {
				csTnxMstDB.setQuantity(csTnxMstDB.getQuantity()
						+ ktDtl.getTrnasferQty());
			} else {
				csTnxMstDB.setQuantity(csTnxMstDB.getQuantity()
						- ktDtl.getTrnasferQty());
			}
			commonService.saveOrUpdateModelObjectToDB(csTnxMstDB);
		} else {
			System.out.println("New tnx Mst created");

			csTnxMst = new CSItemTransactionMst();
			csTnxMst.setItemCode(ktDtl.getItemId());
			csTnxMst.setQuantity(ktDtl.getTrnasferQty());
			csTnxMst.setKhathName(descoKhath.getKhathName());
			csTnxMst.setKhathId(descoKhath.getId());
			csTnxMst.setLedgerName(ktMst.getLedgerBook());
			csTnxMst.setId(null);
			csTnxMst.setCreatedBy(userName);
			csTnxMst.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csTnxMst);

		}

		// select Transation Mst Id and get new Transaction Mst which not exist
		// and newly inserted
		Integer maxTnxMstId = (Integer) commonService
				.getMaxValueByObjectAndColumn("CSItemTransactionMst", "id");
		CSItemTransactionMst csItemTransactionMstTemp = (CSItemTransactionMst) commonService
				.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id",
						maxTnxMstId + "");
		// save Transection Ledger Dtl
		CSItemTransactionDtl csItemTransactionDtl = new CSItemTransactionDtl();
		// general Info
		csItemTransactionDtl.setId(null);
		csItemTransactionDtl.setItemCode(ktDtl.getItemId());
		csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
		csItemTransactionDtl.setKhathId(descoKhath.getId());
		csItemTransactionDtl.setLedgerType(ktMst.getLedgerBook());
		csItemTransactionDtl.setCreatedBy(userName);
		csItemTransactionDtl.setCreatedDate(new Date());
		csItemTransactionDtl.setRemarks(ktDtl.getRemarks());

		// transfer info
		csItemTransactionDtl.setTransactionType(CS_KHATH_TRANSFER);
		csItemTransactionDtl.setTnxnMode(tnxnMode);
		csItemTransactionDtl.setTransactionId(ktMst.getTransferNo());
		csItemTransactionDtl.setTransactionDate(ktMst.getCreatedDate());
		csItemTransactionDtl.setQuantity(ktDtl.getTrnasferQty());

		// relation with transaction master
		if (csTnxMstDB != null) {
			csItemTransactionDtl.setCsItemTransactionMst(csTnxMstDB);
		} else {
			csItemTransactionDtl
					.setCsItemTransactionMst(csItemTransactionMstTemp);
		}

		commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

		// select Last Transation Dtl Id and keep It temp
		Integer maxTnxDtlId = (Integer) commonService
				.getMaxValueByObjectAndColumn("CSItemTransactionDtl", "id");
		CSItemTransactionDtl csTnxDtlTemp = (CSItemTransactionDtl) commonService
				.getAnObjectByAnyUniqueColumn("CSItemTransactionDtl", "id",
						maxTnxDtlId + "");
		return csTnxDtlTemp;

	}

	@SuppressWarnings("unchecked")
	private void csLedgerLocationKT(List<KhathTransferDtl> ktDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, KhathTransferMst ktMst) {

		for (KhathTransferDtl ktDtl : ktDtlList) {

			CSItemTransactionDtl csTnxDtlFrom = this.saveOrUpdateTnxMstDtl(
					ktMst, ktDtl, userName, ktMst.getKhathIdFrom() + "", false);
			CSItemTransactionDtl csTnxDtlTo = this.saveOrUpdateTnxMstDtl(ktMst,
					ktDtl, userName, ktMst.getKhathIdTo() + "", true);
			// Location Update
			List<TempItemLocation> tempLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid",
							ktMst.getUuid(), "itemCode", ktDtl.getItemId());
			setLocationMstDtl(tempLocationList, ktDtl, ktMst, csTnxDtlFrom,
					userName, false);
			setLocationMstDtl(tempLocationList, ktDtl, ktMst, csTnxDtlTo,
					userName, true);

		}
	}

	void setLocationMstDtl(List<TempItemLocation> tempLocationList,
			KhathTransferDtl ktDtl, KhathTransferMst ktMst,
			CSItemTransactionDtl csTnxDtlFrom, String userName, Boolean mode) {
		for (TempItemLocation tempLoc : tempLocationList) {

			/*
			 * CSItemLocationMst csItemLocationMstdb = csItemLocationMstService
			 * .getCSItemLocationMst(tempLoc.getLocationId(),
			 * ktMst.getLedgerBook(), ktDtl.getItemId());
			 */
			CSItemLocationMst csItemLocationMstdb = csItemLocationMstService
					.getCSItemLocationMstBy4Param(ktMst.getLedgerBook(),
							ktDtl.getItemId(), tempLoc.getLocationId(),
							tempLoc.getChildLocationId());

			StoreLocations storeLocations = (StoreLocations) commonService
					.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
							tempLoc.getLocationId());

			if (csItemLocationMstdb != null) {
				csItemLocationMstdb.setModifiedBy(userName);
				csItemLocationMstdb.setModifiedDate(new Date());
				if (mode) {
					csItemLocationMstdb.setQuantity(csItemLocationMstdb
							.getQuantity() + tempLoc.getQuantity());
				} else {
					csItemLocationMstdb.setQuantity(csItemLocationMstdb
							.getQuantity() - tempLoc.getQuantity());
				}
				commonService.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
			} else {
				if (mode) {
					CSItemLocationMst csItemLocationMst = new CSItemLocationMst();
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);
					csItemLocationMst.setItemCode(ktDtl.getItemId());
					csItemLocationMst.setLedgerType(ktMst.getLedgerBook());
					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					csItemLocationMst.setChildLocationId(tempLoc.getChildLocationId());
					csItemLocationMst.setChildLocationName(tempLoc.getChildLocationName());

					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}
			}
			// in locationMst New Insert than get Last Location Mst Id
			Integer maxLocMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("CSItemLocationMst", "id");
			CSItemLocationMst csItemLocationMstTemp = (CSItemLocationMst) commonService
					.getAnObjectByAnyUniqueColumn("CSItemLocationMst", "id",
							maxLocMstId + "");
			// Location DTL
			CSItemLocationDtl csItemLocationDtl = new CSItemLocationDtl();
			csItemLocationDtl.setCreatedBy(userName);
			csItemLocationDtl.setLedgerName(ktMst.getLedgerBook());
			csItemLocationDtl.setCreatedDate(new Date());
			csItemLocationDtl.setId(null);
			csItemLocationDtl.setItemCode(ktDtl.getItemId());
			csItemLocationDtl.setLocationName(storeLocations.getName());
			csItemLocationDtl.setQuantity(tempLoc.getQuantity());
			// if true item should be added
			// if false item should be minus
			csItemLocationDtl.setTnxnMode(mode);
			// itemTransactionDtl Added
			csItemLocationDtl.setCsItemTransectionDtl(csTnxDtlFrom);
			// Store Location Added
			csItemLocationDtl.setStoreLocation(storeLocations);
			if (csItemLocationMstdb != null) {
				csItemLocationDtl.setCsItemLocationMst(csItemLocationMstdb);
			} else {
				csItemLocationDtl.setCsItemLocationMst(csItemLocationMstTemp);
			}
			commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);
		}
	}

	// Added By Ashid
	// get pending Khath transfer List
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ktList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getKhathTransferList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login person Role and login person information
		String roleName = commonService.getAuthRoleName();
		//String userName = commonService.getAuthUserName();
		//AuthUser authUser = userService.getAuthUserByUserId(userName);
		//String deptId = authUser.getDeptId();

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_KHATH_TRANSFER
		List<KhathTransferApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<KhathTransferApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"KhathTransferApprovalHierarchyHistory",
						"operationName", CS_KHATH_TRANSFER, "actRoleName",
						roleName, "status", OPEN);

		// get operationId List from approval hierarchy history
		String[] operationId = new String[approvalHierarchyHistoryList.size()];
		int x = 0;
		for (KhathTransferApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId[x] = approvalHierarchyHistory.getOperationId();
			x++;
		}

		// get all Khath Transfer List which open for login user

		List<KhathTransferMst> khathTransferMstList = khathTransferHierarchyHistoryService
				.getKhathTransferMstListByOperationIds(operationId);

		// sent to the browser
		model.put("khathTransferMstList", khathTransferMstList);
		return new ModelAndView("centralStore/csKhathTransferList", model);
	}

	// Added By Ashid
	// get all aproved Khath transfer List
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/transferedList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getApprovedKhathTransferList() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<KhathTransferMst> khathTransferMstList = (List<KhathTransferMst>) (Object) commonService
				.getObjectListByAnyColumn("KhathTransferMst", "approvedFlag",
						CS_KHATH_TRANSFER_APPROVED);
		model.put("khathTransferMstList", khathTransferMstList);
		return new ModelAndView("centralStore/csKhathTransferApprovedList",
				model);
	}

	// SELECT Location object List by parent location id || Added by ashid
	@RequestMapping(value = "/saveLocation7sDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String saveLocationDtl(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		Double totalQty = 0.0;
		if (isJson) {
			Location7sMstDtl obj = gson.fromJson(json, Location7sMstDtl.class);
			List<Location7sQty> locationList = obj.getLocationList();
			for (Location7sQty loc7sQty : locationList) {
				TempItemLocation temp = new TempItemLocation();
				if (loc7sQty.getQuantity() > 0) {
					if (loc7sQty.getLocation() != null
							&& !loc7sQty.getLocation().equals("")) {	
						temp.setUuid(obj.getUuid());
						temp.setItemCode(obj.getItemCode());
						temp.setLedgerName(obj.getLedgerName());
						temp.setLocationId(loc7sQty.getLocation());
						temp.setLocationName(commonService
								.getStoreLocationNamebyId(loc7sQty
										.getLocation()));
						//temp.setQuantity(loc7sQty.getQuantity());
						DecimalFormat df = new DecimalFormat("#.###");
						double qty = Double.parseDouble(df.format(loc7sQty.getQuantity()));
						temp.setQuantity(qty);
						
						// check for last location and set to ChildLocation
						if (loc7sQty.getGodown() != null
								&& !loc7sQty.getGodown().equals("")) {
							temp.setGodownId(loc7sQty.getGodown());
							temp.setChildLocationId(loc7sQty.getGodown());
							temp.setChildLocationName(commonService
									.getStoreLocationNamebyId(loc7sQty
											.getGodown()));
							if (loc7sQty.getFloor() != null
									&& !loc7sQty.getFloor().equals("")) {
								temp.setFloorId(loc7sQty.getFloor());
								temp.setChildLocationId(loc7sQty.getFloor());
								temp.setChildLocationName(commonService
										.getStoreLocationNamebyId(loc7sQty
												.getFloor()));
								if (loc7sQty.getSector() != null
										&& !loc7sQty.getSector().equals("")) {
									temp.setSectorId(loc7sQty.getSector());
									temp.setChildLocationId(loc7sQty
											.getSector());
									temp.setChildLocationName(commonService
											.getStoreLocationNamebyId(loc7sQty
													.getSector()));
									if (loc7sQty.getRake() != null
											&& !loc7sQty.getRake().equals("")) {
										temp.setRakeId(loc7sQty.getRake());
										temp.setChildLocationId(loc7sQty
												.getRake());
										temp.setChildLocationName(commonService
												.getStoreLocationNamebyId(loc7sQty
														.getRake()));
										if (loc7sQty.getBin() != null
												&& !loc7sQty.getBin()
														.equals("")) {
											temp.setBinId(loc7sQty.getBin());
											temp.setChildLocationId(loc7sQty
													.getBin());
											temp.setChildLocationName(commonService
													.getStoreLocationNamebyId(loc7sQty
															.getBin()));
										}
									}
								}
							}
						}
						// now save to db
						if(loc7sQty.getId().equals("0")){
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
						}else {							
							temp.setId(Integer.parseInt(loc7sQty.getId()));
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
							temp.setModifiedBy(commonService.getAuthRoleName());
							temp.setModifiedDate(new Date());
						}
						commonService.saveOrUpdateModelObjectToDB(temp);
						//totalQty += loc7sQty.getQuantity();
						totalQty += qty;
					}
				}
			}
			// set success msg
			toJson = ow.writeValueAsString(totalQty);
		} else {
			Thread.sleep(5000);
			// set success msg
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}

	//

	// SELECT Location object List by parent location id || Added by ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLocationByParentId.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getLocationByParentId(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			StoreLocations obj = gson.fromJson(json, StoreLocations.class);
			List<StoreLocations> StoreLocationListDb = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumn("StoreLocations", "parentId",
							obj.getId() + "", "storeLevel", obj.getStoreLevel());
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(StoreLocationListDb);
		} else {
			Thread.sleep(5000);
		}
		return toJson;
	}

	//

	// SELECT * Location object List by uuid and itemCode || Added by ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadDataFromTemp.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadDataFromTemp(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			Location7sMstDtl obj = gson.fromJson(json, Location7sMstDtl.class);
			List<Location7sQty> initData = new ArrayList<Location7sQty>();
			List<TempItemLocation> temlocList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							obj.getItemCode(), "uuid", obj.getUuid());
			for (TempItemLocation t : temlocList) {
				Location7sQty location7sQty = new Location7sQty();
				location7sQty.setId(t.getId() + "");
				location7sQty.setLedgerName(t.getLedgerName());
				location7sQty.setLocation(t.getLocationId());
				location7sQty.setGodown(t.getGodownId());
				location7sQty.setFloor(t.getFloorId());
				location7sQty.setSector(t.getSectorId());
				location7sQty.setRake(t.getRakeId());
				location7sQty.setBin(t.getBinId());
				location7sQty.setQuantity(t.getQuantity());
				initData.add(location7sQty);
			}
			String locationOptions = "";
			String godownOptions = "";
			String floorOptions = "";
			String sectorOptions = "";
			String rakeOptions = "";
			String binOptions = "";

			if (initData.size() > 0) {
				locationOptions=getAllOptionForGrid("1");
				godownOptions=getAllOptionForGrid("2");
				floorOptions=getAllOptionForGrid("3");
				sectorOptions=getAllOptionForGrid("4");
				rakeOptions=getAllOptionForGrid("5");
				binOptions=getAllOptionForGrid("6");				
			}
			
			Loc7sQtyGridValues data=new Loc7sQtyGridValues();
			data.setLocationOptions(locationOptions);
			data.setGodownOptions(godownOptions);
			data.setFloorOptions(floorOptions);
			data.setSectorOptions(sectorOptions);
			data.setRakeOptions(rakeOptions);
			data.setBinOptions(binOptions);
			data.setInitData(initData);
			
			// set success msg
			toJson = ow.writeValueAsString(data);
		} else {
			Thread.sleep(5000);
			toJson = ow.writeValueAsString("Failed to load data");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	String getAllOptionForGrid(String storeLevel) {
		String options="";
		List<StoreLocations> gList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumn("StoreLocations", "storeLevel",
						storeLevel, "storeCode", "CS");
		for (int i = 0; i < gList.size(); i++) {
			options += gList.get(i).getId() + ":" + gList.get(i).getName()
					+ ";";
		}
		options = options.substring(0, options.length() - 1);
		return options;
	}
	
	// delete a Location object location id || Added by ashid
	@RequestMapping(value = "/deleteRowFromTempLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String deleteRowFromTempLocation(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			TempItemLocation obj = gson.fromJson(json, TempItemLocation.class);
			commonService.deleteAnObjectById("TempItemLocation", obj.getId());			
			toJson = ow.writeValueAsString("success");
		} else {
			toJson = ow.writeValueAsString("failed");
		}
		return toJson;
	}
	
	//Forward to Upper user
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/kTSendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {

		String kTno = itemReceived.getReceivedReportNo();
		String justification = itemReceived.getJustification();
		String nextStateCode = itemReceived.getStateCode();

		// get Current User and Role Information

		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		
		List<KhathTransferApprovalHierarchyHistory> approvalHierarchyHistoryList = 
		(List<KhathTransferApprovalHierarchyHistory>)(Object)
		commonService.getObjectListByAnyColumn("KhathTransferApprovalHierarchyHistory", "operationId", kTno);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		KhathTransferApprovalHierarchyHistory approvalHierarchyHistory = (KhathTransferApprovalHierarchyHistory)
				commonService.getAnObjectByAnyUniqueColumn("KhathTransferApprovalHierarchyHistory", "id", ids[0]+"");
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_KHATH_TRANSFER, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);	

		// get Next State Code and all info from approval hierarchy history
		KhathTransferApprovalHierarchyHistory approvalHierarchyHistoryNextState = new KhathTransferApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_KHATH_TRANSFER, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(kTno + "");
		approvalHierarchyHistoryNextState
				.setOperationName(approvalHierarchyNextSate.getOperationName());
		approvalHierarchyHistoryNextState
				.setActRoleName(approvalHierarchyNextSate.getRoleName());
		approvalHierarchyHistoryNextState
				.setApprovalHeader(approvalHierarchyNextSate
						.getApprovalHeader());
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);	
		return "redirect:/cs/khath/ktList.do";
	}

	// 
}