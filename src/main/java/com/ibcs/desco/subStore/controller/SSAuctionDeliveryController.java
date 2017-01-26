package com.ibcs.desco.subStore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.AuctionWOEntryMst;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;

@Controller
public class SSAuctionDeliveryController extends Constrants {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentsService departmentsService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/ssCondemnMaterials.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView condemnMaterials() {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String userName = commonService.getAuthUserName();

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

			return new ModelAndView("subStore/auction/condemnMaterials",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadSsItemTnxMstData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadSsItemTnxMstData(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			SSItemTransactionMst csItemTnx = gson.fromJson(json,
					SSItemTransactionMst.class);
			String itemCode = csItemTnx.getItemCode();

			List<SSItemTransactionMst> csItemTnxList = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("SSItemTransactionMst",
							"itemCode", itemCode, "ledgerName", UNSERVICEABLE);

			toJson = ow.writeValueAsString(csItemTnxList);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/loadSsItemTempLocData.do", method = RequestMethod.POST)
	@ResponseBody
	public String loadSsItemTempLocData(@RequestBody String json)
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
			
			List<SSItemTransactionMst> csItemTnxList = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByTwoColumn("SSItemTransactionMst",
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
}
