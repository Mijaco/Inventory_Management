package com.ibcs.desco.subStore.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.Location7sMstDtl;
import com.ibcs.desco.cs.bean.Location7sQty;
import com.ibcs.desco.cs.bean.ReturnSlipMstDtl;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.subStore.model.SSItemLocationDtl;
import com.ibcs.desco.subStore.model.SSItemLocationMst;
import com.ibcs.desco.subStore.model.SSItemTransactionDtl;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;

@Controller
@RequestMapping(value = "/ss/locUp")
@PropertySource("classpath:common.properties")
public class SSLocationUpdateController extends Constrants {

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CommonService commonService;

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/returnedItemList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView returnedItemList(
			@ModelAttribute("returnSlipMstDtl") ReturnSlipMstDtl returnSlipMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<SsCsReturnSlipApprovalHierarchyHistory> ssCsRtrnHierarchyList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByTwoColumn(
						"SsCsReturnSlipApprovalHierarchyHistory", "deptId",
						deptId, "status", DONE);

		List<String> reqHierarchyOptIdList = new ArrayList<String>();
		for (SsCsReturnSlipApprovalHierarchyHistory rsAppHierHist : ssCsRtrnHierarchyList) {
			reqHierarchyOptIdList.add(rsAppHierHist.getOperationId());
		}
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<ReturnSlipMst> csApprovedReRS = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueListAndOneColumn("ReturnSlipMst",
						"returnSlipNo", stOptIdList, "returned", "0");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipMstList", csApprovedReRS);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("subStore/ssApprovedRSList", model);
	}

	//
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/ssCsItemReturnedShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView itemReturnedShowSS2CS(ReturnSlipMstDtl returnSlipMstDtl) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "id",
						returnSlipMstDtl.getId() + "");

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						returnSlipMst.getReturnSlipNo());

		Map<String, Object> model = new HashMap<String, Object>();

		// Start m@@ || Generate drop down list for location in Grid
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
		String userrole = returnSlipMst.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		returnSlipMst.setCreatedBy( createBy.getName() );

		model.put("returnSlipDtlList", returnSlipDtlList);
		model.put("returnSlipMst", returnSlipMst);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("subStore/ssToCsShowReturnSlipReturned", model);
	}

	//
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ssCsItemReturnedLocationUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssLocationUpdateAfterReturnedSuccessfull(
			ReturnSlipMst returnSlipMst) {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		ReturnSlipMst returnSlipMstdb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "id",
						returnSlipMst.getId() + "");
		// Location Updated
		this.ssLocationUpdate(returnSlipMstdb, userName, roleName, department);

		// if location successfully than return will be true
		returnSlipMstdb.setReturned(true);
		commonService.saveOrUpdateModelObjectToDB(returnSlipMstdb);

		// this line will delete all row from TempItemLocation by uuid
		commonService.deleteAnObjectListByAnyColumn("TempItemLocation", "uuid",
				returnSlipMstdb.getUuid());

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						returnSlipMstdb.getReturnSlipNo());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnSlipDtlList", returnSlipDtlList);
		model.put("returnSlipMst", returnSlipMstdb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("subStore/ssToCsShowReturnSlipReturned", model);
	}

	@SuppressWarnings("unchecked")
	private void ssLocationUpdate(ReturnSlipMst returnSlipMstBean,
			String userName, String roleName, Departments department) {

		CSStoreTicketMst csStoreTicketMst = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
						"operationId", returnSlipMstBean.getReturnSlipNo());
		List<CSStoreTicketDtl> csStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo",
						csStoreTicketMst.getTicketNo());

		List<TempItemLocation> tempLocationList = null;

		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {
			List<SSItemTransactionDtl> ssItemTransactionDtlList = (List<SSItemTransactionDtl>) (Object) commonService
					.getObjectListByFourColumn("SSItemTransactionDtl",
							"transactionId", csStoreTicketMst.getOperationId(),
							"khathId", csStoreTicketMst.getKhathId() + "",
							"ledgerType", csStoreTicketDtl.getLedgerName(),
							"itemCode", csStoreTicketDtl.getItemId());
			SSItemTransactionDtl ssItemTransactionDtlTemp = ssItemTransactionDtlList
					.get(0);

			if (csStoreTicketMst.getStoreTicketType().equals(SS_CS_RETURN_SLIP)) {
				ReturnSlipMst returnSlipMst = (ReturnSlipMst) commonService
						.getAnObjectByAnyUniqueColumn("ReturnSlipMst",
								"returnSlipNo",
								csStoreTicketMst.getOperationId());

				tempLocationList = (List<TempItemLocation>) (Object) commonService
						.getObjectListByTwoColumn("TempItemLocation", "uuid",
								returnSlipMst.getUuid(), "itemCode",
								csStoreTicketDtl.getItemId());
			}
			// end of block

			for (TempItemLocation tempLoc : tempLocationList) {
				List<SSItemLocationMst> ssItemLocationMstList = (List<SSItemLocationMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemLocationMst",
								"storeLocation.id", tempLoc.getLocationId(),
								"ledgerType", csStoreTicketDtl.getLedgerName(),
								"itemCode", csStoreTicketDtl.getItemId());

				SSItemLocationMst ssItemLocationMstdb = null;

				if (ssItemLocationMstList.size() > 0) {
					ssItemLocationMstdb = ssItemLocationMstList.get(0);
				}

				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								tempLoc.getLocationId());

				// LEDGERNAME, ITEMCODE, LOCATIONID
				if (ssItemLocationMstdb != null) {
					ssItemLocationMstdb.setModifiedBy(userName);
					ssItemLocationMstdb.setModifiedDate(new Date());

					if (csStoreTicketMst.getStoreTicketType().equals(
							SS_CS_RETURN_SLIP)) {
						ssItemLocationMstdb.setQuantity(ssItemLocationMstdb
								.getQuantity() - tempLoc.getQuantity());
					}
					commonService
							.saveOrUpdateModelObjectToDB(ssItemLocationMstdb);
				} else {
					SSItemLocationMst ssItemLocationMst = new SSItemLocationMst();
					ssItemLocationMst.setItemCode(csStoreTicketDtl.getItemId());
					ssItemLocationMst.setLedgerType(csStoreTicketDtl
							.getLedgerName());
					ssItemLocationMst.setCreatedBy(userName);
					ssItemLocationMst.setCreatedDate(new Date());
					ssItemLocationMst.setId(null);

					ssItemLocationMst.setLocationName(storeLocations.getName());
					ssItemLocationMst.setQuantity(tempLoc.getQuantity());
					ssItemLocationMst.setStoreLocation(storeLocations);
					commonService
							.saveOrUpdateModelObjectToDB(ssItemLocationMst);
				}

				Integer maxLocMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("SSItemLocationMst", "id");
				SSItemLocationMst ssItemLocationMstTemp = (SSItemLocationMst) commonService
						.getAnObjectByAnyUniqueColumn("SSItemLocationMst",
								"id", maxLocMstId + "");

				// Location DTL
				SSItemLocationDtl ssItemLocationDtl = new SSItemLocationDtl();
				// general info
				ssItemLocationDtl.setItemCode(csStoreTicketDtl.getItemId());
				ssItemLocationDtl.setCreatedBy(userName);
				ssItemLocationDtl.setCreatedDate(new Date());
				ssItemLocationDtl.setId(null);

				// Location Info
				ssItemLocationDtl.setLocationName(storeLocations.getName());
				ssItemLocationDtl.setStoreLocation(storeLocations);

				ssItemLocationDtl.setQuantity(tempLoc.getQuantity());

				if (csStoreTicketMst.getStoreTicketType().equals(
						SS_CS_RETURN_SLIP)) {
					ssItemLocationDtl.setTnxnMode(false);
				}

				// set Item Location Mst Id
				if (ssItemLocationMstdb != null) {
					ssItemLocationDtl.setLedgerName(ssItemLocationMstdb.getLedgerType());
					ssItemLocationDtl.setSsItemLocationMst(ssItemLocationMstdb);
				} else {
					ssItemLocationDtl.setLedgerName(ssItemLocationMstTemp.getLedgerType());
					ssItemLocationDtl
							.setSsItemLocationMst(ssItemLocationMstTemp);
				}

				// set Transaction dtl info for get khathwise and location wise
				// item report
				ssItemLocationDtl
						.setSsItemTransactionDtl(ssItemTransactionDtlTemp);

				ssItemLocationDtl.setStoreLocation(storeLocations);

				commonService.saveOrUpdateModelObjectToDB(ssItemLocationDtl);

			}
		}
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
		Double newServQty = 0.0;
		Double recServQty = 0.0;
		Double unServQty = 0.0;
		Double totalQty = 0.0;
		DecimalFormat df = new DecimalFormat("#.###");
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
						temp.setLedgerName(loc7sQty.getLedgerName());
						temp.setLocationId(loc7sQty.getLocation());
						temp.setLocationName(commonService
								.getStoreLocationNamebyId(loc7sQty
										.getLocation()));
						//temp.setQuantity(loc7sQty.getQuantity());
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
						if (loc7sQty.getId().equals("0")) {
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
						} else {
							temp.setId(Integer.parseInt(loc7sQty.getId()));
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
							temp.setModifiedBy(commonService.getAuthRoleName());
							temp.setModifiedDate(new Date());
						}
						commonService.saveOrUpdateModelObjectToDB(temp);
						if (loc7sQty.getLedgerName().equals(NEW_SERVICEABLE)) {
							// newServQty += loc7sQty.getQuantity();
							newServQty += qty;
						} else if (loc7sQty.getLedgerName().equals(
								RECOVERY_SERVICEABLE)) {
							// recServQty += loc7sQty.getQuantity();
							recServQty += qty;
						} else if (loc7sQty.getLedgerName().equals(
								UNSERVICEABLE)) {
							// unServQty += loc7sQty.getQuantity();
							unServQty += qty;
						}
						// totalQty += loc7sQty.getQuantity();
						totalQty += qty;

					}
				}
			}

			// set success msg
			ReturnSlipDtl rtDtl = new ReturnSlipDtl();
			rtDtl.setQtyNewServiceableRcv(newServQty);
			rtDtl.setQtyRecServiceableRcv(recServQty);
			rtDtl.setQtyUnServiceableRcv(unServQty);
			rtDtl.setTotalReturnRcv(totalQty);
			toJson = ow.writeValueAsString(rtDtl);

		} else {
			Thread.sleep(5000);
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}

}
