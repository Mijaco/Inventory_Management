package com.ibcs.desco.cs.controller;

import java.text.SimpleDateFormat;
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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.AuctionMaterialsDelivery;
import com.ibcs.desco.cs.bean.AuctionWOEntryMstDtl;
import com.ibcs.desco.cs.model.AuctionDeliveryDtl;
import com.ibcs.desco.cs.model.AuctionDeliveryMst;
import com.ibcs.desco.cs.model.AuctionWOEntryDtl;
import com.ibcs.desco.cs.model.AuctionWOEntryMst;
import com.ibcs.desco.cs.model.CSGatePassDtl;
import com.ibcs.desco.cs.model.CSGatePassMst;
import com.ibcs.desco.cs.model.CSItemTransactionDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.localStore.model.LSGatePassDtl;
import com.ibcs.desco.localStore.model.LSGatePassMst;
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.localStore.model.LSStoreTicketDtl;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.subStore.model.SSGatePassDtl;
import com.ibcs.desco.subStore.model.SSGatePassMst;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

@Controller
@PropertySource("classpath:common.properties")
public class AuctionDeliveryController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ItemGroupService itemGroupService;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.auction.delivery.prefix}")
	private String auctionDeliveryNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.gate.pass.prefix}")
	private String descoGatePassNoPrefix;

	@RequestMapping(value = "/ac/saveAuctionMaterialsDelivery.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveAuctionMaterials(
			AuctionMaterialsDelivery auctionMaterialsDelivery) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<Integer> idList = null;
			Integer mstId = 0;

			Date now = new Date();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date deliveryDate = formatter.parse(auctionMaterialsDelivery
					.getDeliveryDate());

			List<String> itemCodes = auctionMaterialsDelivery.getItemCode();
			idList = auctionMaterialsDelivery.getId();
			List<Double> receivedQty = auctionMaterialsDelivery
					.getReceivedQty();

			if (idList != null && idList.size() > 0) {
				AuctionWOEntryDtl aWoEntry = (AuctionWOEntryDtl) commonService
						.getAnObjectByAnyUniqueColumn("AuctionWOEntryDtl",
								"id", idList.get(0).toString());

				AuctionDeliveryMst auctionDeliveryMst = new AuctionDeliveryMst();
				auctionDeliveryMst.setAuctionWOEntryMst(aWoEntry
						.getAuctionWOEntryMst());
				auctionDeliveryMst.setCarriedBy(auctionMaterialsDelivery
						.getCarriedBy());
				auctionDeliveryMst
						.setReceiverContactNo(auctionMaterialsDelivery
								.getReceiverContactNo());
				auctionDeliveryMst.setReceiverName(auctionMaterialsDelivery
						.getReceiverName());
				auctionDeliveryMst.setDeliveryDate(deliveryDate);
				auctionDeliveryMst.setCreatedBy(authUser.getName());
				auctionDeliveryMst.setCreatedDate(now);
				String operationNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								auctionDeliveryNoPrefix,
								department.getDescoCode(), separator,
								"AUCTION_DELIVERY_SEQ");
				auctionDeliveryMst.setDeliveryTrxnNo(operationNo);
				auctionDeliveryMst.setActive(true);
				auctionDeliveryMst.setFinalSubmit("0");
				auctionDeliveryMst.setDepartment(department);
				auctionDeliveryMst.setUuid(auctionMaterialsDelivery.getUuid());

				commonService.saveOrUpdateModelObjectToDB(auctionDeliveryMst);

				AuctionDeliveryMst auctionDeliveryMstDb = (AuctionDeliveryMst) commonService
						.getAnObjectByAnyUniqueColumn("AuctionDeliveryMst",
								"deliveryTrxnNo", operationNo);
				mstId = auctionDeliveryMstDb.getId();
				for (int i = 0; i < idList.size(); i++) {
					AuctionDeliveryDtl auctionDeliveryDtl = new AuctionDeliveryDtl();
					ItemMaster itemMaster = (ItemMaster) commonService
							.getAnObjectByAnyUniqueColumn("ItemMaster",
									"itemId", itemCodes.get(i));
					auctionDeliveryDtl.setActive(true);
					auctionDeliveryDtl
							.setAuctionDeliveryMst(auctionDeliveryMstDb);
					auctionDeliveryDtl.setCreatedBy(userName);
					auctionDeliveryDtl.setCreatedDate(now);
					auctionDeliveryDtl.setDeliveryQty(receivedQty.get(i));
					auctionDeliveryDtl.setId(null);
					auctionDeliveryDtl.setItemMaster(itemMaster);

					if (receivedQty.get(i)!=null && receivedQty.get(i) > 0) {
						commonService
								.saveOrUpdateModelObjectToDB(auctionDeliveryDtl);
					}

				}
			}

			return new ModelAndView("redirect:/ac/condemnMaterialShow.do?id="
					+ mstId);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/condemnMaterials.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView condemnMaterials() {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();
			String roleName = commonService.getAuthRoleName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<AuctionWOEntryMst> workOrderMstList = (List<AuctionWOEntryMst>) (Object) commonService
					.getAllObjectList("AuctionWOEntryMst");

			List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
					.getAllObjectList("DescoKhath");

			model.put("workOrderMstList", workOrderMstList);
			model.put("department", department);
			model.put("uuid", UUID.randomUUID().toString());
			model.put("descoKhathList", descoKhathList);

			if (roleName.contains("ROLE_CS_")) {
				return new ModelAndView(
						"centralStore/auction/condemnMaterials", model);
			} else if (roleName.contains("ROLE_SS_")) {
				return new ModelAndView("subStore/auction/condemnMaterials",
						model);
			} else if (roleName.contains("ROLE_LS_")) {
				return new ModelAndView("localStore/auction/condemnMaterials",
						model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	@RequestMapping(value = "/ac/condemnMaterialShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView condemnMaterialShowGet(
			AuctionDeliveryMst auctionDeliveryMst) {
		return this.condemnMaterialShow(auctionDeliveryMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/condemnMaterialShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView condemnMaterialShow(
			AuctionDeliveryMst auctionDeliveryMst) {

		Integer id = auctionDeliveryMst.getId();

		String userName = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		AuctionDeliveryMst auctionDeliveryMstDb = (AuctionDeliveryMst) commonService
				.getAnObjectByAnyUniqueColumn("AuctionDeliveryMst", "id",
						id.toString());

		List<AuctionDeliveryDtl> auctionDeliveryDtlList = (List<AuctionDeliveryDtl>) (Object) commonService
				.getObjectListByAnyColumn("AuctionDeliveryDtl",
						"auctionDeliveryMst.id", auctionDeliveryMstDb.getId()
								.toString());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("auctionDeliveryMst", auctionDeliveryMstDb);
		model.put("auctionDeliveryDtlList", auctionDeliveryDtlList);
		model.put("department", department);

		if (roleName.contains("ROLE_CS_")) {
			return new ModelAndView(
					"centralStore/auction/condemnMaterialsShow", model);
		} else if (roleName.contains("ROLE_SS_")) {
			return new ModelAndView("subStore/auction/condemnMaterialsShow",
					model);
		} else if (roleName.contains("ROLE_LS_")) {
			return new ModelAndView("localStore/auction/condemnMaterialsShow",
					model);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/auctionDeliveryList.do", method = RequestMethod.GET)
	public ModelAndView getAuctionDeliveryMstList() {
		String userName = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Map<String, Object> model = new HashMap<String, Object>();
		List<AuctionDeliveryMst> auctionDeliveryMstList = (List<AuctionDeliveryMst>) (Object) commonService
				.getObjectListByAnyColumn("AuctionDeliveryMst",
						"department.deptId", authUser.getDeptId());
		// .getAllObjectList("AuctionDeliveryMst");

		model.put("auctionDeliveryMstList", auctionDeliveryMstList);

		if (roleName.contains("ROLE_CS_")) {
			return new ModelAndView("centralStore/auction/auctionDeliveryList",
					model);
		} else if (roleName.contains("ROLE_SS_")) {
			return new ModelAndView("subStore/auction/auctionDeliveryList",
					model);
		} else if (roleName.contains("ROLE_LS_")) {
			return new ModelAndView("localStore/auction/auctionDeliveryList",
					model);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadItemCodebyWoNo.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadItemCodebyWoNo(@RequestBody String json) throws Exception {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionWOEntryDtl auctionWODtl = gson.fromJson(json,
					AuctionWOEntryDtl.class);
			Integer id = auctionWODtl.getId();

			List<AuctionWOEntryDtl> auctionWoDtlDb = (List<AuctionWOEntryDtl>) (Object) commonService
					.getObjectListByTwoColumn("AuctionWOEntryDtl",
							"auctionWOEntryMst.id", id.toString(),
							"departments.id", department.getId().toString());

			for (int i = 0; i < auctionWoDtlDb.size(); i++) {
				if (auctionWoDtlDb.get(i).getRemainingQty() == 0) {
					auctionWoDtlDb.remove(i);
				}
			}

			toJson = ow.writeValueAsString(auctionWoDtlDb);

		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/ac/loadItemInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadItemInfo(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionWOEntryDtl auctionWODtl = gson.fromJson(json,
					AuctionWOEntryDtl.class);
			Integer id = auctionWODtl.getId();

			AuctionWOEntryDtl auctionWoDtlDb = (AuctionWOEntryDtl) commonService
					.getAnObjectByAnyUniqueColumn("AuctionWOEntryDtl", "id",
							id.toString());

			toJson = ow.writeValueAsString(auctionWoDtlDb);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "ac/deleteCondemnMaterials.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteCondemnMaterials(
			AuctionDeliveryMst auctionDeliveryMst) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			commonService.deleteAnObjectById("AuctionDeliveryMst",
					auctionDeliveryMst.getId());

			return new ModelAndView("redirect:/ac/auctionDeliveryList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/delivery/finalSubmit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionDeliveryMstFinalSubmit(
			@ModelAttribute("auctionDeliveryMst") AuctionDeliveryMst auctionDeliveryMst) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			String roleName = commonService.getAuthRoleName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			if (auctionDeliveryMst.getId() > 0) {
				AuctionDeliveryMst auctionDeliveryMstDb = (AuctionDeliveryMst) commonService
						.getAnObjectByAnyUniqueColumn("AuctionDeliveryMst",
								"id", auctionDeliveryMst.getId().toString());

				List<AuctionDeliveryDtl> auctionDeliveryDtlList = (List<AuctionDeliveryDtl>) (Object) commonService
						.getObjectListByAnyColumn("AuctionDeliveryDtl",
								"auctionDeliveryMst.id", auctionDeliveryMstDb
										.getId().toString());

				this.updateAuctionWODtl(auctionDeliveryDtlList);

				String storeTicketNo = "";
				String gatePassNo = "";

				// Ledger update only
				if (roleName.contains("ROLE_CS_")) {
					storeTicketNo = this.auctionDeliveryStoreTicket(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department);
					gatePassNo = this.auctionDeliveryGatePass(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department, storeTicketNo);
					this.csLedgerUpdate_auctionDelivery(auctionDeliveryMstDb,
							auctionDeliveryDtlList, userName);
				} else if (roleName.contains("ROLE_SS_")) {
					//
					storeTicketNo = this.auctionDeliveryStoreTicketSs(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department);
					gatePassNo = this.auctionDeliveryGatePassSs(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department, storeTicketNo);
					this.ssLedgerUpdate_auctionDelivery(auctionDeliveryMstDb,
							auctionDeliveryDtlList, userName);

				} else if (roleName.contains("ROLE_LS_")) {
					storeTicketNo = this.auctionDeliveryStoreTicketLs(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department);
					gatePassNo = this.auctionDeliveryGatePassLs(
							auctionDeliveryMstDb, auctionDeliveryDtlList,
							authUser, department, storeTicketNo);
					this.lsLedgerUpdate_auctionDelivery(auctionDeliveryMstDb,
							auctionDeliveryDtlList, userName);
				}

				auctionDeliveryMstDb.setStoreTicketNo(storeTicketNo);
				auctionDeliveryMstDb.setGatePassNo(gatePassNo);
				auctionDeliveryMstDb.setModifiedBy(userName);
				auctionDeliveryMstDb.setModifiedDate(new Date());
				auctionDeliveryMstDb.setFinalSubmit("1");
				commonService.saveOrUpdateModelObjectToDB(auctionDeliveryMstDb);
				return new ModelAndView("redirect:/ac/auctionDeliveryList.do");
			}
			return new ModelAndView("redirect:/ac/auctionDeliveryList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	public String auctionDeliveryStoreTicket(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department) {
		String storeTicketNo = "";
		Date now = new Date();
		CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
		csStoreTicketMst.setId(null);
		csStoreTicketMst.setStoreTicketType(AUCTION_DELIVERY);
		csStoreTicketMst.setOperationId(auctionDeliveryMst.getDeliveryTrxnNo());
		csStoreTicketMst.setIssuedTo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());
		csStoreTicketMst.setIssuedFor(auctionDeliveryMst.getReceiverName());
		csStoreTicketMst.setFlag(true);
		csStoreTicketMst.setKhathId(1);
		csStoreTicketMst.setKhathName("Revenue");
		csStoreTicketMst.setSndCode(department.getDescoCode());
		csStoreTicketMst.setApproved(true);
		csStoreTicketMst.setTicketDate(now);
		csStoreTicketMst.setCreatedBy(authUser.getName());
		csStoreTicketMst.setCreatedDate(now);
		csStoreTicketMst.setActive(true);

		// Auto generate Store Ticket number
		String descoDeptCode = department.getDescoCode();
		csStoreTicketMst.setIssuedBy(authUser.getName() + "("
				+ authUser.getUserid() + ")");
		storeTicketNo = commonService
				.getOperationIdByPrefixAndSequenceName(
						descoStoreTicketNoPrefix, descoDeptCode, separator,
						"CS_ST_SEQ");

		csStoreTicketMst.setTicketNo(storeTicketNo);
		csStoreTicketMst.setReceivedBy(auctionDeliveryMst.getReceiverName());
		commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

		/*
		 * CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst)
		 * commonService .getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
		 * "operationId", auctionDeliveryMst.getDeliveryTrxnNo());
		 */

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			CSStoreTicketDtl csStoreTicketDtl = new CSStoreTicketDtl();
			csStoreTicketDtl.setId(null);
			csStoreTicketDtl.setCsStoreTicketMst(csStoreTicketMst);

			csStoreTicketDtl.setActive(true);
			csStoreTicketDtl.setCreatedBy(authUser.getName());
			csStoreTicketDtl.setCreatedDate(now);
			csStoreTicketDtl.setTicketNo(storeTicketNo);

			csStoreTicketDtl.setItemId(aucDtl.getItemMaster().getItemId());
			csStoreTicketDtl.setDescription(aucDtl.getItemMaster()
					.getItemName());
			csStoreTicketDtl.setUom(aucDtl.getItemMaster().getUnitCode());
			csStoreTicketDtl.setCost(0.0);
			csStoreTicketDtl.setLedgerName(UNSERVICEABLE);
			csStoreTicketDtl.setRemarks("");
			csStoreTicketDtl.setQuantity(aucDtl.getDeliveryQty());
			csStoreTicketDtl.setLedgerBookNo("");
			csStoreTicketDtl.setLedgerPageNo("");
			commonService.saveOrUpdateModelObjectToDB(csStoreTicketDtl);
		}

		return storeTicketNo;
	}

	public String auctionDeliveryStoreTicketSs(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department) {
		String storeTicketNo = "";
		Date now = new Date();
		SSStoreTicketMst storeTicketMst = new SSStoreTicketMst();
		storeTicketMst.setId(null);
		storeTicketMst.setStoreTicketType(AUCTION_DELIVERY);
		storeTicketMst.setOperationId(auctionDeliveryMst.getDeliveryTrxnNo());
		storeTicketMst.setIssuedTo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());
		storeTicketMst.setIssuedFor(auctionDeliveryMst.getReceiverName());
		storeTicketMst.setFlag(true);
		storeTicketMst.setKhathId(1);
		storeTicketMst.setKhathName("Revenue");
		storeTicketMst.setSndCode(department.getDescoCode());
		storeTicketMst.setApproved(true);
		storeTicketMst.setTicketDate(now);
		storeTicketMst.setCreatedBy(authUser.getName());
		storeTicketMst.setCreatedDate(now);
		storeTicketMst.setActive(true);

		// Auto generate Store Ticket number
		String descoDeptCode = department.getDescoCode();
		storeTicketMst.setIssuedBy(authUser.getName() + "("
				+ authUser.getUserid() + ")");
		storeTicketNo = commonService
				.getOperationIdByPrefixAndSequenceName(
						descoStoreTicketNoPrefix, descoDeptCode, separator,
						"SS_ST_SEQ");

		storeTicketMst.setTicketNo(storeTicketNo);
		storeTicketMst.setReceivedBy(auctionDeliveryMst.getReceiverName());
		commonService.saveOrUpdateModelObjectToDB(storeTicketMst);

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			SSStoreTicketDtl storeTicketDtl = new SSStoreTicketDtl();
			storeTicketDtl.setId(null);
			storeTicketDtl.setSsStoreTicketMst(storeTicketMst);

			storeTicketDtl.setActive(true);
			storeTicketDtl.setCreatedBy(authUser.getName());
			storeTicketDtl.setCreatedDate(now);
			storeTicketDtl.setTicketNo(storeTicketNo);

			storeTicketDtl.setItemId(aucDtl.getItemMaster().getItemId());
			storeTicketDtl.setDescription(aucDtl.getItemMaster().getItemName());
			storeTicketDtl.setUom(aucDtl.getItemMaster().getUnitCode());
			storeTicketDtl.setCost(0.0);
			storeTicketDtl.setLedgerName(UNSERVICEABLE);
			storeTicketDtl.setRemarks("");
			storeTicketDtl.setQuantity(aucDtl.getDeliveryQty());
			storeTicketDtl.setLedgerBookNo("");
			storeTicketDtl.setLedgerPageNo("");
			commonService.saveOrUpdateModelObjectToDB(storeTicketDtl);
		}

		return storeTicketNo;
	}

	public String auctionDeliveryStoreTicketLs(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department) {
		String storeTicketNo = "";
		Date now = new Date();
		LSStoreTicketMst storeTicketMst = new LSStoreTicketMst();
		storeTicketMst.setId(null);
		storeTicketMst.setStoreTicketType(AUCTION_DELIVERY);
		storeTicketMst.setOperationId(auctionDeliveryMst.getDeliveryTrxnNo());
		storeTicketMst.setIssuedTo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());
		storeTicketMst.setIssuedFor(auctionDeliveryMst.getReceiverName());
		storeTicketMst.setFlag(true);
		storeTicketMst.setKhathId(1);
		storeTicketMst.setKhathName("Revenue");
		storeTicketMst.setSndCode(department.getDescoCode());
		storeTicketMst.setApproved(true);
		storeTicketMst.setTicketDate(now);
		storeTicketMst.setCreatedBy(authUser.getName());
		storeTicketMst.setCreatedDate(now);
		storeTicketMst.setActive(true);

		// Auto generate Store Ticket number
		String descoDeptCode = department.getDescoCode();
		storeTicketMst.setIssuedBy(authUser.getName() + "("
				+ authUser.getUserid() + ")");
		storeTicketNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoStoreTicketNoPrefix, descoDeptCode, separator,
				"C2LS_ST_SEQ");

		storeTicketMst.setTicketNo(storeTicketNo);
		storeTicketMst.setReceivedBy(auctionDeliveryMst.getReceiverName());
		commonService.saveOrUpdateModelObjectToDB(storeTicketMst);

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			LSStoreTicketDtl storeTicketDtl = new LSStoreTicketDtl();
			storeTicketDtl.setId(null);
			storeTicketDtl.setLsStoreTicketMst(storeTicketMst);

			storeTicketDtl.setActive(true);
			storeTicketDtl.setCreatedBy(authUser.getName());
			storeTicketDtl.setCreatedDate(now);
			storeTicketDtl.setTicketNo(storeTicketNo);

			storeTicketDtl.setItemId(aucDtl.getItemMaster().getItemId());
			storeTicketDtl.setDescription(aucDtl.getItemMaster().getItemName());
			storeTicketDtl.setUom(aucDtl.getItemMaster().getUnitCode());
			storeTicketDtl.setCost(0.0);
			storeTicketDtl.setLedgerName(UNSERVICEABLE);
			storeTicketDtl.setRemarks("");
			storeTicketDtl.setQuantity(aucDtl.getDeliveryQty());
			storeTicketDtl.setLedgerBookNo("");
			storeTicketDtl.setLedgerPageNo("");
			commonService.saveOrUpdateModelObjectToDB(storeTicketDtl);
		}

		return storeTicketNo;
	}

	public String auctionDeliveryGatePass(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department, String storeTicketNo) {
		String gatePassNo = "";

		// GP MST info
		String descoDeptCode = department.getDescoCode();
		CSGatePassMst csGatePassMst = new CSGatePassMst();
		csGatePassMst.setGatePassDate(new Date());
		csGatePassMst.setIssuedTo(auctionDeliveryMst.getReceiverName());
		csGatePassMst.setIssuedBy(commonService.getAuthUserName());
		csGatePassMst.setReceiverName(auctionDeliveryMst.getReceiverName());
		csGatePassMst.setFlag(true);
		csGatePassMst.setActive(true);
		csGatePassMst.setCreatedBy(authUser.getUserid());
		csGatePassMst.setCreatedDate(new Date());
		csGatePassMst.setRequisitonNo(auctionDeliveryMst.getDeliveryTrxnNo());
		csGatePassMst.setTicketNo(storeTicketNo);
		csGatePassMst.setWorkOrderNo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());

		gatePassNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoGatePassNoPrefix, descoDeptCode, separator,
				"GATE_PASS_SEQ");
		// Save CS GP Mst
		csGatePassMst.setGatePassNo(gatePassNo);
		csGatePassMst.setId(null);
		csGatePassMst.setLocationId("1");
		csGatePassMst.setLocationName("Central Store");
		commonService.saveOrUpdateModelObjectToDB(csGatePassMst);

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			ItemMaster item = aucDtl.getItemMaster();
			CSGatePassDtl csGPdtl = new CSGatePassDtl();
			csGPdtl.setCsGatePassMst(csGatePassMst);
			csGPdtl.setGatePassNo(csGatePassMst.getGatePassNo());
			csGPdtl.setItemCode(item.getItemId());
			csGPdtl.setDescription(item.getItemName());
			csGPdtl.setUom(item.getUnitCode());
			csGPdtl.setId(null);
			csGPdtl.setQuantity(aucDtl.getDeliveryQty());
			csGPdtl.setCreatedBy(authUser.getUserid());
			csGPdtl.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csGPdtl);
		}

		return gatePassNo;
	}

	public String auctionDeliveryGatePassSs(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department, String storeTicketNo) {
		String gatePassNo = "";

		// GP MST info
		String descoDeptCode = department.getDescoCode();
		SSGatePassMst gatePassMst = new SSGatePassMst();
		gatePassMst.setGatePassDate(new Date());
		gatePassMst.setIssuedTo(auctionDeliveryMst.getReceiverName());
		gatePassMst.setIssuedBy(commonService.getAuthUserName());
		gatePassMst.setReceiverName(auctionDeliveryMst.getReceiverName());
		gatePassMst.setFlag(true);
		gatePassMst.setActive(true);
		gatePassMst.setCreatedBy(authUser.getUserid());
		gatePassMst.setCreatedDate(new Date());
		gatePassMst.setRequisitonNo(auctionDeliveryMst.getDeliveryTrxnNo());
		gatePassMst.setTicketNo(storeTicketNo);
		gatePassMst.setWorkOrderNo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());

		gatePassNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoGatePassNoPrefix, descoDeptCode, separator,
				"SS_GATE_PASS_SEQ");
		// Save SS GP Mst
		gatePassMst.setGatePassNo(gatePassNo);
		gatePassMst.setId(null);
		// .setLocationId("1");
		// gatePassMst.setLocationName("Central Store");
		commonService.saveOrUpdateModelObjectToDB(gatePassMst);

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			ItemMaster item = aucDtl.getItemMaster();
			SSGatePassDtl gPdtl = new SSGatePassDtl();
			gPdtl.setSsGatePassMst(gatePassMst);
			gPdtl.setGatePassNo(gatePassMst.getGatePassNo());
			gPdtl.setItemCode(item.getItemId());
			gPdtl.setDescription(item.getItemName());
			gPdtl.setUom(item.getUnitCode());
			gPdtl.setId(null);
			gPdtl.setQuantity(aucDtl.getDeliveryQty());
			gPdtl.setCreatedBy(authUser.getUserid());
			gPdtl.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(gPdtl);
		}

		return gatePassNo;
	}

	public String auctionDeliveryGatePassLs(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> auctionDeliveryDtlList, AuthUser authUser,
			Departments department, String storeTicketNo) {
		String gatePassNo = "";

		// GP MST info
		String descoDeptCode = department.getDescoCode();
		LSGatePassMst gatePassMst = new LSGatePassMst();
		gatePassMst.setGatePassDate(new Date());
		gatePassMst.setIssuedTo(auctionDeliveryMst.getReceiverName());
		gatePassMst.setIssuedBy(commonService.getAuthUserName());
		gatePassMst.setReceiverName(auctionDeliveryMst.getReceiverName());
		gatePassMst.setFlag(true);
		gatePassMst.setActive(true);
		gatePassMst.setCreatedBy(authUser.getUserid());
		gatePassMst.setCreatedDate(new Date());
		gatePassMst.setRequisitonNo(auctionDeliveryMst.getDeliveryTrxnNo());
		gatePassMst.setTicketNo(storeTicketNo);
		gatePassMst.setWorkOrderNo(auctionDeliveryMst.getAuctionWOEntryMst()
				.getWorkOrderNo());

		gatePassNo = commonService.getOperationIdByPrefixAndSequenceName(
				descoGatePassNoPrefix, descoDeptCode, separator,
				"LS_GATE_PASS_SEQ");
		// Save CS GP Mst
		gatePassMst.setGatePassNo(gatePassNo);
		gatePassMst.setId(null);
		// .setLocationId("1");
		// gatePassMst.setLocationName("Central Store");
		commonService.saveOrUpdateModelObjectToDB(gatePassMst);

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			ItemMaster item = aucDtl.getItemMaster();
			LSGatePassDtl gPdtl = new LSGatePassDtl();
			gPdtl.setLsGatePassMst(gatePassMst);
			gPdtl.setGatePassNo(gatePassMst.getGatePassNo());
			gPdtl.setItemCode(item.getItemId());
			gPdtl.setDescription(item.getItemName());
			gPdtl.setUom(item.getUnitCode());
			gPdtl.setId(null);
			gPdtl.setQuantity(aucDtl.getDeliveryQty());
			gPdtl.setCreatedBy(authUser.getUserid());
			gPdtl.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(gPdtl);
		}

		return gatePassNo;
	}

	@RequestMapping(value = "/auction/gatePassReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionDeliveryGatePassReport(
			AuctionDeliveryMst auctionDeliveryMst) {
		String roleName = commonService.getAuthRoleName();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			AuctionDeliveryMst auctionDeliveryMstDb = (AuctionDeliveryMst) commonService
					.getAnObjectByAnyUniqueColumn("AuctionDeliveryMst", "id",
							auctionDeliveryMst.getId().toString());
			model.put("auctionDeliveryMst", auctionDeliveryMstDb);

			if (roleName.contains("ROLE_CS_")) {
				return new ModelAndView(
						"centralStore/auction/auctionDeliveryGatePassReport",
						model);
			} else if (roleName.contains("ROLE_SS_")) {
				return new ModelAndView(
						"subStore/auction/auctionDeliveryGatePassReport", model);
			} else if (roleName.contains("ROLE_LS_")) {
				return new ModelAndView(
						"localStore/auction/auctionDeliveryGatePassReport",
						model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	@RequestMapping(value = "/auction/storeTicketReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionDeliveryStoreTicketReport(
			AuctionDeliveryMst auctionDeliveryMst) {
		String roleName = commonService.getAuthRoleName();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			AuctionDeliveryMst auctionDeliveryMstDb = (AuctionDeliveryMst) commonService
					.getAnObjectByAnyUniqueColumn("AuctionDeliveryMst", "id",
							auctionDeliveryMst.getId().toString());
			model.put("auctionDeliveryMst", auctionDeliveryMstDb);

			if (roleName.contains("ROLE_CS_")) {
				return new ModelAndView(
						"centralStore/auction/auctionDeliveryStoreTicketReport",
						model);
			} else if (roleName.contains("ROLE_SS_")) {
				return new ModelAndView(
						"subStore/auction/auctionDeliveryStoreTicketReport",
						model);
			} else if (roleName.contains("ROLE_LS_")) {
				return new ModelAndView(
						"localStore/auction/auctionDeliveryStoreTicketReport",
						model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome", model);
		}
	}

	@SuppressWarnings("unchecked")
	public void updateAuctionWODtl(
			List<AuctionDeliveryDtl> auctionDeliveryDtlList) {

		for (AuctionDeliveryDtl aucDtl : auctionDeliveryDtlList) {
			/*
			 * AuctionWOEntryDtl aWoEntry = (AuctionWOEntryDtl) commonService
			 * .getAnObjectByAnyUniqueColumn("AuctionWOEntryDtl",
			 * "itemMaster.id", aucDtl.getItemMaster().getId() .toString());
			 */
			List<AuctionWOEntryDtl> aWoEntryList = (List<AuctionWOEntryDtl>) (Object) commonService
					.getObjectListByThreeColumn("AuctionWOEntryDtl",
							"itemMaster.id", aucDtl.getItemMaster().getId()
									.toString(), "departments.id", aucDtl
									.getAuctionDeliveryMst().getDepartment()
									.getId().toString(),
							"auctionWOEntryMst.id", aucDtl
									.getAuctionDeliveryMst()
									.getAuctionWOEntryMst().getId().toString());
			AuctionWOEntryDtl aWoEntry = aWoEntryList.get(0);
			aWoEntry.setRemainingQty(aWoEntry.getRemainingQty()
					- aucDtl.getDeliveryQty());
			aWoEntry.setModifiedBy(commonService.getAuthUserName());
			aWoEntry.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(aWoEntry);
		}
	}

	// Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadCsItemTnxMstData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadCsItemTnxMstData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			CSItemTransactionMst csItemTnx = gson.fromJson(json,
					CSItemTransactionMst.class);
			String itemCode = csItemTnx.getItemCode();

			/*
			 * String uom = auctionWODtl.getUom(); Departments dept = (
			 * Departments ) commonService
			 * .getAnObjectByAnyUniqueColumn("Departments", "deptId", uom);
			 */

			List<CSItemTransactionMst> csItemTnxList = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("CSItemTransactionMst",
							"itemCode", itemCode, "ledgerName", UNSERVICEABLE);

			toJson = ow.writeValueAsString(csItemTnxList);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/ac/saveDeliveryItem.do", method = RequestMethod.POST)
	@ResponseBody
	public String saveDeliveryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionWOEntryMstDtl auction = gson.fromJson(json,
					AuctionWOEntryMstDtl.class);
			List<Double> deliveryQty = null;

			deliveryQty = auction.getDeliveryQtyList();
			String itemCode = auction.getItemCode();
			String uuid = auction.getUuid();

			for (int i = 0; i < deliveryQty.size(); i++) {
				TempItemLocation temp = new TempItemLocation();
				temp.setId(null);
				temp.setCreatedBy(commonService.getAuthUserName());
				temp.setCreatedDate(new Date());

				temp.setItemCode(itemCode);
				temp.setKhathId(auction.getKhathIdList().get(i));
				temp.setLocationId(auction.getKhathIdList().get(i));
				temp.setQuantity(deliveryQty.get(i));
				temp.setUuid(uuid);

				commonService.saveOrUpdateModelObjectToDB(temp);
			}
			toJson = ow.writeValueAsString("success");
		} else {
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	private void csLedgerUpdate_auctionDelivery(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> deliveryDtlList, String userName) {

		for (AuctionDeliveryDtl adDtl : deliveryDtlList) {

			List<TempItemLocation> tmpList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							adDtl.getItemMaster().getItemId(), "uuid",
							auctionDeliveryMst.getUuid());
			for (TempItemLocation temp : tmpList) {
				List<CSItemTransactionMst> tnxList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"itemCode", adDtl.getItemMaster().getItemId(),
								"khathId", temp.getKhathId(), "ledgerName",
								UNSERVICEABLE);
				CSItemTransactionMst transactionMst = null;

				if (tnxList != null && tnxList.size() > 0) {
					transactionMst = tnxList.get(0);
					transactionMst.setModifiedBy(userName);
					transactionMst.setModifiedDate(new Date());
					transactionMst.setQuantity(transactionMst.getQuantity()
							- temp.getQuantity());
					commonService.saveOrUpdateModelObjectToDB(transactionMst);

					//

					CSItemTransactionDtl tnxDtl = new CSItemTransactionDtl();
					tnxDtl.setId(null);
					tnxDtl.setCsItemTransactionMst(transactionMst);
					tnxDtl.setCreatedBy(userName);
					tnxDtl.setCreatedDate(new Date());

					tnxDtl.setLedgerType(UNSERVICEABLE);
					tnxDtl.setItemCode(adDtl.getItemMaster().getItemId());
					tnxDtl.setKhathName(transactionMst.getKhathName());
					tnxDtl.setKhathId(transactionMst.getKhathId());

					tnxDtl.setTnxnMode(false);
					tnxDtl.setTransactionType(AUCTION_DELIVERY);
					tnxDtl.setTransactionId(auctionDeliveryMst
							.getDeliveryTrxnNo());
					tnxDtl.setTransactionDate(auctionDeliveryMst
							.getDeliveryDate());
					tnxDtl.setQuantity(temp.getQuantity());
					tnxDtl.setReceivedBy(auctionDeliveryMst.getReceiverName());
					commonService.saveOrUpdateModelObjectToDB(tnxDtl);

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void ssLedgerUpdate_auctionDelivery(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> deliveryDtlList, String userName) {

		for (AuctionDeliveryDtl adDtl : deliveryDtlList) {

			List<TempItemLocation> tmpList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							adDtl.getItemMaster().getItemId(), "uuid",
							auctionDeliveryMst.getUuid());
			for (TempItemLocation temp : tmpList) {
				List<SSItemTransactionMst> tnxList = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst",
								"itemCode", adDtl.getItemMaster().getItemId(),
								"khathId", temp.getKhathId(), "ledgerName",
								UNSERVICEABLE);
				SSItemTransactionMst transactionMst = null;

				if (tnxList != null && tnxList.size() > 0) {
					transactionMst = tnxList.get(0);
					transactionMst.setModifiedBy(userName);
					transactionMst.setModifiedDate(new Date());
					transactionMst.setQuantity(transactionMst.getQuantity()
							- temp.getQuantity());
					commonService.saveOrUpdateModelObjectToDB(transactionMst);

					//

					SSItemTransactionDtl tnxDtl = new SSItemTransactionDtl();
					tnxDtl.setId(null);
					tnxDtl.setSsItemTransactionMst(transactionMst);
					tnxDtl.setCreatedBy(userName);
					tnxDtl.setCreatedDate(new Date());

					tnxDtl.setLedgerType(UNSERVICEABLE);
					tnxDtl.setItemCode(adDtl.getItemMaster().getItemId());
					tnxDtl.setKhathName(transactionMst.getKhathName());
					tnxDtl.setKhathId(transactionMst.getKhathId());

					tnxDtl.setTnxnMode(false);
					tnxDtl.setTransactionType(AUCTION_DELIVERY);
					tnxDtl.setTransactionId(auctionDeliveryMst
							.getDeliveryTrxnNo());
					tnxDtl.setTransactionDate(auctionDeliveryMst
							.getDeliveryDate());
					tnxDtl.setQuantity(temp.getQuantity());
					tnxDtl.setReceivedBy(auctionDeliveryMst.getReceiverName());
					commonService.saveOrUpdateModelObjectToDB(tnxDtl);

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void lsLedgerUpdate_auctionDelivery(
			AuctionDeliveryMst auctionDeliveryMst,
			List<AuctionDeliveryDtl> deliveryDtlList, String userName) {

		for (AuctionDeliveryDtl adDtl : deliveryDtlList) {

			List<TempItemLocation> tmpList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							adDtl.getItemMaster().getItemId(), "uuid",
							auctionDeliveryMst.getUuid());
			for (TempItemLocation temp : tmpList) {
				List<LSItemTransactionMst> tnxList = (List<LSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("LSItemTransactionMst",
								"itemCode", adDtl.getItemMaster().getItemId(),
								"khathId", temp.getKhathId(), "ledgerName",
								UNSERVICEABLE);
				LSItemTransactionMst transactionMst = null;

				if (tnxList != null && tnxList.size() > 0) {
					transactionMst = tnxList.get(0);
					transactionMst.setModifiedBy(userName);
					transactionMst.setModifiedDate(new Date());
					transactionMst.setQuantity(transactionMst.getQuantity()
							- temp.getQuantity());
					commonService.saveOrUpdateModelObjectToDB(transactionMst);

					//

					LSItemTransactionDtl tnxDtl = new LSItemTransactionDtl();
					tnxDtl.setId(null);
					tnxDtl.setLsItemTransactionMst(transactionMst);
					tnxDtl.setCreatedBy(userName);
					tnxDtl.setCreatedDate(new Date());

					tnxDtl.setLedgerType(UNSERVICEABLE);
					tnxDtl.setItemCode(adDtl.getItemMaster().getItemId());
					tnxDtl.setKhathName(transactionMst.getKhathName());
					tnxDtl.setKhathId(transactionMst.getKhathId());

					tnxDtl.setTnxnMode(false);
					tnxDtl.setTransactionType(AUCTION_DELIVERY);
					tnxDtl.setTransactionId(auctionDeliveryMst
							.getDeliveryTrxnNo());
					tnxDtl.setTransactionDate(auctionDeliveryMst
							.getDeliveryDate());
					tnxDtl.setQuantity(temp.getQuantity());
					tnxDtl.setReceivedBy(auctionDeliveryMst.getReceiverName());
					commonService.saveOrUpdateModelObjectToDB(tnxDtl);

				}
			}
		}
	}

	// Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadCsItemTempLocData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadCsItemTempLocData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TempItemLocation tempItemLoc = gson.fromJson(json,
					TempItemLocation.class);
			String itemCode = tempItemLoc.getItemCode();
			String uuid = tempItemLoc.getUuid();

			/*
			 * String uom = auctionWODtl.getUom(); Departments dept = (
			 * Departments ) commonService
			 * .getAnObjectByAnyUniqueColumn("Departments", "deptId", uom);
			 */

			List<CSItemTransactionMst> csItemTnxList = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("CSItemTransactionMst",
							"itemCode", itemCode, "ledgerName", UNSERVICEABLE);

			for (int i = 0; i < csItemTnxList.size(); i++) {
				List<TempItemLocation> tempLocList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByThreeColumn("TempItemLocation",
								"itemCode", csItemTnxList.get(i).getItemCode(),
								"uuid", uuid, "khathId", csItemTnxList.get(i)
										.getKhathId().toString());

				if (tempLocList.size() == 0) {
					csItemTnxList.get(i).setDeliveredQty(0.0);
				} else {
					csItemTnxList.get(i).setDeliveredQty(
							tempLocList.get(0).getQuantity());
				}
			}

			toJson = ow.writeValueAsString(csItemTnxList);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// Added by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/updateCsItemTempLocData.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateCsItemTempLocData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionWOEntryMstDtl auction = gson.fromJson(json,
					AuctionWOEntryMstDtl.class);
			List<Double> deliveryQty = null;

			deliveryQty = auction.getDeliveryQtyList();
			String itemCode = auction.getItemCode();
			String uuid = auction.getUuid();
			List<String> khathIdList = auction.getKhathIdList();

			for (int i = 0; i < deliveryQty.size(); i++) {
				List<TempItemLocation> temp = (List<TempItemLocation>) (Object) commonService
						.getObjectListByThreeColumn("TempItemLocation",
								"itemCode", itemCode, "uuid", uuid, "khathId",
								khathIdList.get(i));
				if (temp.size() > 0) {

					temp.get(0).setQuantity(deliveryQty.get(i));
					temp.get(0).setModifiedBy(commonService.getAuthUserName());
					temp.get(0).setModifiedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(temp.get(0));
				} else {
					TempItemLocation tmp = new TempItemLocation();
					tmp.setId(null);
					tmp.setCreatedBy(commonService.getAuthUserName());
					tmp.setCreatedDate(new Date());

					tmp.setItemCode(itemCode);
					tmp.setKhathId(khathIdList.get(i));
					tmp.setLocationId(khathIdList.get(i));
					tmp.setQuantity(deliveryQty.get(i));
					tmp.setUuid(uuid);

					commonService.saveOrUpdateModelObjectToDB(tmp);
				}
			}
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// delete
	@RequestMapping(value = "/ac/deleteCsItemTempLocData.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCsItemTempLocData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TempItemLocation tempItemLoc = gson.fromJson(json,
					TempItemLocation.class);
			String itemCode = tempItemLoc.getItemCode();
			String uuid = tempItemLoc.getUuid();
			String khathId = tempItemLoc.getKhathId();

			commonService.deleteAnObjectByThreeColumn("TempItemLocation",
					"itemCode", itemCode, "uuid", uuid, "khathId", khathId);
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// Added by: Ahasanul Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadLsItemTnxMstData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadLsItemTnxMstData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			LSItemTransactionMst lsItemTnx = gson.fromJson(json,
					LSItemTransactionMst.class);
			String itemCode = lsItemTnx.getItemCode();

			List<LSItemTransactionMst> csItemTnxList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("LSItemTransactionMst",
							"itemCode", itemCode, "ledgerName", UNSERVICEABLE);

			toJson = ow.writeValueAsString(csItemTnxList);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// Added by: Ahasanul Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadLsItemTempLocData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadLsItemTempLocData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TempItemLocation tempItemLoc = gson.fromJson(json,
					TempItemLocation.class);
			String itemCode = tempItemLoc.getItemCode();
			String uuid = tempItemLoc.getUuid();

			List<LSItemTransactionMst> lsItemTnxList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("LSItemTransactionMst",
							"itemCode", itemCode, "ledgerName", UNSERVICEABLE);

			for (int i = 0; i < lsItemTnxList.size(); i++) {
				List<TempItemLocation> tempLocList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByThreeColumn("TempItemLocation",
								"itemCode", lsItemTnxList.get(i).getItemCode(),
								"uuid", uuid, "khathId", lsItemTnxList.get(i)
										.getKhathId().toString());

				if (tempLocList.size() == 0) {
					lsItemTnxList.get(i).setDeliveredQty(0.0);
				} else {
					lsItemTnxList.get(i).setDeliveredQty(
							tempLocList.get(0).getQuantity());
				}
			}

			toJson = ow.writeValueAsString(lsItemTnxList);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

}
