package com.ibcs.desco.subStore.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.SsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.model.SSItemLocationDtl;
import com.ibcs.desco.subStore.model.SSItemLocationMst;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/ss/itemRecieved")
@PropertySource("classpath:common.properties")
public class SSItemReceiveController extends Constrants{

	// Setting constant value for this controller

	// Create Object for Service Classes which will be instantiated from spring
	// bean

	@Autowired
	SsCsRequisitionApprovalHierarchyHistoryService ssCsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;
	
	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;
	
	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CommonService commonService;

	@Value("${subStore.ssCsReturnSlipNo.prefix}")
	private String ssCsReturnSlipNoPrefix;

	@Value("${subStore.csStoreTicketNo.prefix}")
	private String csStoreTicketNoPrefix;
	
	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csItemRequisitionReceivedList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemRequisitionReceivedList(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = ssCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						stOptIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		return new ModelAndView("subStore/csApprovedRequisitionList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csItemRequisitionReceivedPendingList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemRequisitionReceivedPendingList(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = ssCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		/*List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						stOptIdList);*/
		
		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueListAndOneColumn("CentralStoreRequisitionMst", "requisitionNo",
				stOptIdList, "received", "0");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		return new ModelAndView("subStore/csApprovedRequisitionList", model);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/storeRequisitionReceiving.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String storeRequisitionReceiving(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		Integer id = csRequisitionMstDtl.getId();
		boolean received = csRequisitionMstDtl.isReceived();
		String userName = commonService.getAuthUserName();

		CentralStoreRequisitionMst csRequisitionMst = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(id);
		CSStoreTicketMst csStoreTicketMst = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
						"operationId", csRequisitionMst.getRequisitionNo());
		List<CSStoreTicketDtl> csStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo",
						csStoreTicketMst.getTicketNo());

		// Ledger Update LS
		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			SSItemTransactionMst lsItemTransactionMst = null;
			SSItemTransactionMst lsItemTransactionMstdb = ssItemTransactionMstService
					.getSSItemTransactionMst(csStoreTicketDtl.getItemId(),
							csStoreTicketMst.getKhathId(),
							csStoreTicketDtl.getLedgerName());
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							csStoreTicketMst.getKhathId() + "");

			// Ledger Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionMstdb.setModifiedBy(userName);
				lsItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					}

				}
				commonService
						.saveOrUpdateModelObjectToDB(lsItemTransactionMstdb);
			} else {
				lsItemTransactionMst = new SSItemTransactionMst();
				lsItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName().equals(NEW_SERVICEABLE)) {
						lsItemTransactionMst.setQuantity(lsItemTransactionMst
								.getQuantity()
								+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMst.setQuantity(lsItemTransactionMst
								.getQuantity()
								+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMst.setQuantity(lsItemTransactionMst
								.getQuantity()
								+ csStoreTicketDtl.getQuantity());
					}

				}
				lsItemTransactionMst.setKhathName(descoKhath.getKhathName());
				lsItemTransactionMst.setKhathId(descoKhath.getId());
				lsItemTransactionMst.setLedgerName(csStoreTicketDtl
						.getLedgerName());
				lsItemTransactionMst.setId(null);
				lsItemTransactionMst.setCreatedBy(userName);
				lsItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(lsItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("SSItemTransactionMst", "id");
			SSItemTransactionMst lsItemTransactionMstLastRow = (SSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			SSItemTransactionDtl lsItemTransactionDtl = new SSItemTransactionDtl();
			// common Info
			lsItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			lsItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			lsItemTransactionDtl.setKhathId(descoKhath.getId());
			lsItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			lsItemTransactionDtl.setId(null);
			lsItemTransactionDtl.setCreatedBy(userName);
			lsItemTransactionDtl.setCreatedDate(new Date());
			lsItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			lsItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			lsItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			lsItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());
			lsItemTransactionDtl.setTnxnMode(true);
			// relation with transaction Mst
			if (lsItemTransactionMstdb != null) {
				
				lsItemTransactionDtl
						.setSsItemTransactionMst(lsItemTransactionMstdb);
			} else {
				lsItemTransactionDtl
						.setSsItemTransactionMst(lsItemTransactionMstLastRow);
			}

			// item related info
			lsItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			lsItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			lsItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			lsItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			lsItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			lsItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			lsItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			

			lsItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());

			commonService.saveOrUpdateModelObjectToDB(lsItemTransactionDtl);
			
			Integer maxTnxDtlId = (Integer) commonService
					.getMaxValueByObjectAndColumn("SSItemTransactionDtl", "id");
			SSItemTransactionDtl csItemTransactionDtlTemp = (SSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSItemTransactionDtl", "id",
							maxTnxDtlId + "");

			// Location Update
			// Location Mst
			
			 // added by nasrin || check requisition or return and get data frm
		// related model
		List<TempItemLocation> tempLocationList = null;

		if (csStoreTicketMst.getStoreTicketType().equals(SS_CS_REQUISITION)||csStoreTicketMst.getStoreTicketType().equals(CN_SS_REQUISITION)) {
			CentralStoreRequisitionMst csReqMst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst", "requisitionNo",
							csStoreTicketMst.getOperationId());

			tempLocationList = (List<TempItemLocation>) (Object) commonService.getObjectListByTwoColumn(
					"TempItemLocation", "uuid", csReqMst.getUuid(), "itemCode", csStoreTicketDtl.getItemId());
		} else if (csStoreTicketMst.getStoreTicketType().equals(SS_CS_RETURN_SLIP)||csStoreTicketMst.getStoreTicketType().equals(CN_SS_RETURN_SLIP)) {
			SSReturnSlipMst returnSlipMst = (SSReturnSlipMst) commonService.getAnObjectByAnyUniqueColumn(
					"SSReturnSlipMst", "returnSlipNo", csStoreTicketMst.getOperationId());

			tempLocationList = (List<TempItemLocation>) (Object) commonService.getObjectListByTwoColumn(
					"TempItemLocation", "uuid", returnSlipMst.getUuid(), "itemCode", csStoreTicketDtl.getItemId());
		}
		// end of block
		

			for (TempItemLocation tempLoc : tempLocationList) {

				SSItemLocationMst csItemLocationMstdb = ssItemLocationMstService
						.getSSItemLocationMst(tempLoc.getLocationId(),
								csStoreTicketDtl.getLedgerName(),
								csStoreTicketDtl.getItemId());

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								tempLoc.getLocationId());

				// LEDGERNAME, ITEMCODE, LOCATIONID
				if (csItemLocationMstdb != null) {
					csItemLocationMstdb.setModifiedBy(userName);
					csItemLocationMstdb.setModifiedDate(new Date());

					if (csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_REQUISITION)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() - tempLoc.getQuantity());
					} else if (csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)) {
						csItemLocationMstdb.setQuantity(csItemLocationMstdb
								.getQuantity() + tempLoc.getQuantity());
					}
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					SSItemLocationMst csItemLocationMst = new SSItemLocationMst();
					csItemLocationMst.setItemCode(csStoreTicketDtl.getItemId());
					csItemLocationMst.setLedgerType(csStoreTicketDtl
							.getLedgerName());
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);

					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					commonService
							.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}

				Integer maxLocMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("SSItemLocationMst", "id");
				SSItemLocationMst csItemLocationMstTemp = (SSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("SSItemLocationMst",
								"id", maxLocMstId + "");

				// Location DTL
				SSItemLocationDtl csItemLocationDtl = new SSItemLocationDtl();
				// general info
				csItemLocationDtl.setItemCode(csStoreTicketDtl.getItemId());
				csItemLocationDtl.setCreatedBy(userName);
				csItemLocationDtl.setCreatedDate(new Date());
				csItemLocationDtl.setId(null);
				
				// Location Info
				csItemLocationDtl.setLocationName(storeLocations.getName());
				csItemLocationDtl.setStoreLocation(storeLocations);

				csItemLocationDtl.setQuantity(tempLoc.getQuantity());

				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_REQUISITION)) {
					csItemLocationDtl.setTnxnMode(false);
				} else if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)
						|| csStoreTicketMst.getStoreTicketType().equals(
								CN_SS_RETURN_SLIP)) {
					csItemLocationDtl.setTnxnMode(true);
				}

				// set Item Location Mst Id
				if (csItemLocationMstdb != null) {
					csItemLocationDtl.setLedgerName(csItemLocationMstdb.getLedgerType());
					csItemLocationDtl.setSsItemLocationMst(csItemLocationMstdb);
				} else {
					csItemLocationDtl.setLedgerName(csItemLocationMstTemp.getLedgerType());
					csItemLocationDtl
							.setSsItemLocationMst(csItemLocationMstTemp);
				}

				// set Transaction dtl info for get khathwise and location wise
				// item report
				csItemLocationDtl
						.setSsItemTransactionDtl(csItemTransactionDtlTemp);

				csItemLocationDtl.setStoreLocation(storeLocations);

				commonService.saveOrUpdateModelObjectToDB(csItemLocationDtl);
			}
			
			//---------
		}

		// item received completed by received flag true
		csRequisitionMst.setReceived(true);
		csRequisitionMst.setReceivedBy(userName);
		commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);
		return "redirect:/ss/itemRecieved/csItemRequisitionReceivedList.do";

	}

	@RequestMapping(value = "/storeRequisitionReceivedShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionReceivedShow(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		// needed property
		Integer mstId = csRequisitionMstDtl.getId();
		//String roleName = commonService.getAuthRoleName();
		//String userName = commonService.getAuthUserName();
		//AuthUser authUser = userService.getAuthUserByUserId(userName);
		//String deptId = authUser.getDeptId();

		// Mst Data
		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(mstId);
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		// Dtl Data
		@SuppressWarnings("unchecked")
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		// operation Id which selected by login user
		String currentStatus = "";

		@SuppressWarnings("unchecked")
		List<SsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory",
						SS_CS_REQUISITION, operationId, DONE);

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}
		
		// Start m@@ || Generate drop down list for location in Grid
		@SuppressWarnings("unchecked")
		List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
				.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
						"storeCode", "SS", "parentId");
		String locationOptions = commonService
				.getLocationListForGrid(locationsList);
		String ledgerBookList = commonService.getLedgerListForGrid();
		model.put("locationList", locationOptions);
		model.put("ledgerBookList", ledgerBookList);
		// End m@@ || Generate drop down list for location in Grid
		
		
		/*Following four lines are added by Ihteshamul Alam*/
		String userrole = centralStoreRequisitionMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		centralStoreRequisitionMstdb.setCreatedBy( createBy.getName() );
		
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("reqTo", ContentType.CENTRAL_STORE.toString());
		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);
		model.put("uuid", UUID.randomUUID().toString());

		return new ModelAndView("subStore/ssItemReceivedShow", model);

	}

	/*@RequestMapping(value = "/requisitionReceivedSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView requisitionReceivedSearch(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
		requisitionNo = requisitionNo.toUpperCase();
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = lsCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<String> resultOptId = searchSting(stOptIdList, requisitionNo);

		List<CentralStoreRequisitionMst> csApprovedRequisitions = csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						resultOptId);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		return new ModelAndView("localStore/csApprovedRequisitionList", model);
	}

	static java.util.List<String> searchSting(java.util.List<String> value,
			String searchValue) {
		java.util.List<String> returnValue = new java.util.ArrayList<String>();
		for (String s : value) {
			if (s.contains(searchValue)) {
				returnValue.add(s);
			}
		}
		return returnValue;
	}

*/


	/*private void csLedgerLocationRR(List<CSProcItemRcvDtl> itemRcvDtlList, String userName, String roleName,
			AuthUser authUser, Departments dept, CSProcItemRcvMst csProcItemRcvMst) {

		for (CSProcItemRcvDtl csProcItemRcvDtl : itemRcvDtlList) {

			CSItemTransactionMst csItemTransactionMst = null;

			// 0 get Transaction Mst Item if Exist
			CSItemTransactionMst csItemTransactionMstdb = csItemTransactionMstService.getCSItemTransectionMst(
					csProcItemRcvDtl.getItemId(), csProcItemRcvMst.getKhathId(), NEW_SERVICABLE);
			DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
					csProcItemRcvMst.getKhathId() + "");
			// Ledger Mst
			// 1. update or save Transaction Mst
			if (csItemTransactionMstdb != null) {
				csItemTransactionMstdb.setModifiedBy(userName);
				csItemTransactionMstdb.setModifiedDate(new Date());
				csItemTransactionMstdb
						.setQuantity(csItemTransactionMstdb.getQuantity() + csProcItemRcvDtl.getReceivedQty());
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMstdb);
			} else {
				csItemTransactionMst = new CSItemTransactionMst();
				csItemTransactionMst.setItemCode(csProcItemRcvDtl.getItemId());
				csItemTransactionMst.setQuantity(csProcItemRcvDtl.getReceivedQty());
				csItemTransactionMst.setKhathName(descoKhath.getKhathName());
				csItemTransactionMst.setKhathId(descoKhath.getId());
				csItemTransactionMst.setLedgerName(NEW_SERVICABLE);
				csItemTransactionMst.setId(null);
				csItemTransactionMst.setCreatedBy(userName);
				csItemTransactionMst.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(csItemTransactionMst);
			}
			// select Transation Mst Id and get new Transaction Mst which not
			// exist and newly inserted
			Integer maxTnxMstId = (Integer) commonService.getMaxValueByObjectAndColumn("CSItemTransactionMst", "id");
			CSItemTransactionMst csItemTransactionMstTemp = (CSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionMst", "id", maxTnxMstId + "");
			// save Transection Ledger Dtl
			CSItemTransactionDtl csItemTransactionDtl = new CSItemTransactionDtl();
			// general Info
			csItemTransactionDtl.setId(null);
			csItemTransactionDtl.setItemCode(csProcItemRcvDtl.getItemId());
			csItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			csItemTransactionDtl.setKhathId(descoKhath.getId());

			csItemTransactionDtl.setLedgerType(NEW_SERVICABLE);
			csItemTransactionDtl.setCreatedBy(userName);
			csItemTransactionDtl.setCreatedDate(new Date());
			csItemTransactionDtl.setRemarks(csProcItemRcvDtl.getRemarks());

			// rcv info
			csItemTransactionDtl.setTransactionType(CS_RECEIVING_ITEMS);
			csItemTransactionDtl.setTransactionId(csProcItemRcvMst.getRrNo());
			csItemTransactionDtl.setTransactionDate(csProcItemRcvMst.getReceivedDate());
			csItemTransactionDtl.setQuantity(csProcItemRcvDtl.getReceivedQty());

			csItemTransactionDtl.setReceivedBy(csProcItemRcvMst.getReceivedBy());
			csItemTransactionDtl.setReceivedFrom(csProcItemRcvMst.getSupplierName());
			// relation with transaction master
			if (csItemTransactionMstdb != null) {
				csItemTransactionDtl.setCsItemTransactionMst(csItemTransactionMstdb);
			} else {
				csItemTransactionDtl.setCsItemTransactionMst(csItemTransactionMstTemp);
			}

			commonService.saveOrUpdateModelObjectToDB(csItemTransactionDtl);

			// 5. select Transation Dtl Id and keep It temp
			Integer maxTnxDtlId = (Integer) commonService.getMaxValueByObjectAndColumn("CSItemTransactionDtl", "id");
			CSItemTransactionDtl csItemTransactionDtlTemp = (CSItemTransactionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CSItemTransactionDtl", "id", maxTnxDtlId + "");

			// Location Update
			// Location Mst
			// 6. update or save location mst

			List<TempItemLocation> tempLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", csProcItemRcvMst.getUuid(), "itemCode",
							csProcItemRcvDtl.getItemId());

			for (TempItemLocation tempLoc : tempLocationList) {

				CSItemLocationMst csItemLocationMstdb = csItemLocationMstService
						.getCSItemLocationMst(tempLoc.getLocationId(), NEW_SERVICABLE, csProcItemRcvDtl.getItemId());

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id", tempLoc.getLocationId());

				if (csItemLocationMstdb != null) {
					csItemLocationMstdb.setModifiedBy(userName);
					csItemLocationMstdb.setModifiedDate(new Date());
					csItemLocationMstdb.setQuantity(csItemLocationMstdb.getQuantity() + tempLoc.getQuantity());
					commonService.saveOrUpdateModelObjectToDB(csItemLocationMstdb);
				} else {
					CSItemLocationMst csItemLocationMst = new CSItemLocationMst();
					csItemLocationMst.setCreatedBy(userName);
					csItemLocationMst.setCreatedDate(new Date());
					csItemLocationMst.setId(null);
					csItemLocationMst.setItemCode(csProcItemRcvDtl.getItemId());
					csItemLocationMst.setLedgerType(NEW_SERVICABLE);
					csItemLocationMst.setLocationName(storeLocations.getName());
					csItemLocationMst.setQuantity(tempLoc.getQuantity());
					csItemLocationMst.setStoreLocation(storeLocations);
					commonService.saveOrUpdateModelObjectToDB(csItemLocationMst);
				}
				// in locationMst New Insert than get Last Location Mst Id
				Integer maxLocMstId = (Integer) commonService.getMaxValueByObjectAndColumn("CSItemLocationMst", "id");
				CSItemLocationMst csItemLocationMstTemp = (CSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("CSItemLocationMst", "id", maxLocMstId + "");

				// Location DTL
				CSItemLocationDtl csItemLocationDtl = new CSItemLocationDtl();

				// general info
				csItemLocationDtl.setCreatedBy(userName);
				csItemLocationDtl.setLedgerName(NEW_SERVICABLE);
				csItemLocationDtl.setCreatedDate(new Date());
				csItemLocationDtl.setId(null);
				csItemLocationDtl.setItemCode(csProcItemRcvDtl.getItemId());
				csItemLocationDtl.setLocationName(storeLocations.getName());
				csItemLocationDtl.setQuantity(tempLoc.getQuantity());

				// if true item should be added and if false item should be
				// minus
				csItemLocationDtl.setTnxnMode(true);

				// itemTransactionDtl Added
				csItemLocationDtl.setCsItemTransectionDtl(csItemTransactionDtlTemp);

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
	}*/
	
	@SuppressWarnings("unchecked")
	public List<StoreLocations> getStoreLocationList(String storeCode) {

		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByAnyColumn("StoreLocations", "storeCode",
						storeCode);

		try {
			return storeLocationList;

		} catch (Exception ex) {
			return null;
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setSSRcvedLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String setSSRcvedLocation(@RequestBody String locationQtyJsonString)
			throws InterruptedException, JsonGenerationException, JsonMappingException, IOException {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(locationQtyJsonString);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(
					locationQtyJsonString, TempLocationMstDtl.class);
			String itemCode = temLocationDtl.getItemCode();
			String uuid = temLocationDtl.getUuid();
			Map<String, String> map = temLocationDtl.getLocQtyDtl();
			Set<String> keys = map.keySet();

			for (Iterator i = keys.iterator(); i.hasNext();) {
				String locationId = (String) i.next();
				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								locationId);

				Double qty = Double.parseDouble(map.get(locationId));

				TempItemLocation tempItemLocation = new TempItemLocation();
				tempItemLocation.setId(null);
				tempItemLocation.setItemCode(itemCode);
				tempItemLocation.setUuid(uuid);
				tempItemLocation.setLocationId(locationId);
				tempItemLocation.setLocationName(storeLocations.getName());
				tempItemLocation.setQuantity(qty);
				tempItemLocation.setCreatedBy(commonService.getAuthUserName());
				tempItemLocation.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(tempItemLocation);

			}
			
			toJson = ow.writeValueAsString("success");

		} else {
			Thread.sleep(125 * 1000);
			toJson = ow.writeValueAsString("failure");
		}

		return toJson;
	}

	// return total Qty. Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSSTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getSSTotalQtyByUuidAndItemCode(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String uuid = tp.getUuid();
			String itemCode = tp.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"itemCode", itemCode);
			Double qty = 0.0;
			for (TempItemLocation temp : tempItemLocationList) {
				qty += temp.getQuantity();
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(qty);
		}
		return toJson;
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTemplocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getSSTemplocation(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(cData,
					TempLocationMstDtl.class);
			String uuid = temLocationDtl.getUuid();
			String itemCode = temLocationDtl.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							itemCode, "uuid", uuid);
			// System.out.println(itemCode + " : " + uuid);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(tempItemLocationList);
		}

		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/updateSSLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateSSLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String id = tp.getId().toString();
			Double quantity = tp.getQuantity();

			TempItemLocation tempItemLocationDb = (TempItemLocation) commonService
					.getAnObjectByAnyUniqueColumn("TempItemLocation", "id", id);
			tempItemLocationDb.setQuantity(quantity);
			tempItemLocationDb.setModifiedBy(commonService.getAuthUserName());
			tempItemLocationDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(tempItemLocationDb);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Upadate Failed.");

		}
		return toJson;
	}

	// Added by Ashid
	/*@RequestMapping(value = "/updateRRdtlAfterLocatinUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateRRdtlAfterLocatinUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CSProcItemRcvDtl tpDtl = gson.fromJson(cData,
					CSProcItemRcvDtl.class);
			String rrNo = tpDtl.getRrNo();
			String itemId = tpDtl.getItemId();
			double receivedQty = tpDtl.getReceivedQty();
			List<Object> obList = commonService.getObjectListByTwoColumn(
					"CSProcItemRcvDtl", "rrNo", rrNo, "itemId", itemId);
			CSProcItemRcvDtl cSProcItemRcvDtlDB = new CSProcItemRcvDtl();
			System.out.println(cSProcItemRcvDtlDB.getReceivedQty());
			if (obList.size() > 0) {
				cSProcItemRcvDtlDB = (CSProcItemRcvDtl) obList.get(0);
			}
			if (cSProcItemRcvDtlDB.getReceivedQty() != 0.0) {
				cSProcItemRcvDtlDB.setReceivedQty(receivedQty);
				cSProcItemRcvDtlDB.setModifiedBy(commonService
						.getAuthUserName());
				cSProcItemRcvDtlDB.setModifiedDate(new Date());
			}
			commonService.saveOrUpdateModelObjectToDB(cSProcItemRcvDtlDB);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow
					.writeValueAsString("Error from updateRRdtlAfterLocatinUpdate.do");
		}
		return toJson;
	}*/

	// Added by Ashid
	@RequestMapping(value = "/deleteSSLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String deleteSSLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			Integer id = tp.getId();
			commonService.deleteAnObjectById("TempItemLocation", id);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity deleted Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}
}
