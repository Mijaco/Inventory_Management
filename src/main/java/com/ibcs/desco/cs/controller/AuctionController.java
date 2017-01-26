package com.ibcs.desco.cs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.AuctionCategory;
import com.ibcs.desco.cs.model.AuctionCategoryReference;
import com.ibcs.desco.cs.model.AuctionCommittees;
import com.ibcs.desco.cs.model.AuctionDtl;
import com.ibcs.desco.cs.model.AuctionMst;
import com.ibcs.desco.cs.model.AuctionProcessDtl;
import com.ibcs.desco.cs.model.AuctionProcessMst;
import com.ibcs.desco.cs.model.AuctionView;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CondemnDtl;
import com.ibcs.desco.cs.model.CondemnMst;
import com.ibcs.desco.cs.service.AuctionService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;

/*
 * @author Ahasanul Ashid
 * @designation Programmer
 * @company IBCS Primax Software Ltd.
 */

@Controller
@PropertySource("classpath:common.properties")
public class AuctionController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;

	@Autowired
	AuctionService auctionService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cs/getUnserviceableItems.do", method = RequestMethod.GET)
	public ModelAndView getUserviceableItems() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<AuctionCategory> auctionCategoryList = (List<AuctionCategory>) (Object) commonService
				.getObjectListByAnyColumn("AuctionCategory", "isActive", "1");
		model.put("auctionCategoryList", auctionCategoryList);
		return new ModelAndView("centralStore/auction/unserviceableItemForm",
				model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cs/getUnserviceableItems.do", method = RequestMethod.POST)
	public ModelAndView postUnserviceableItems(AuctionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			String rawDate = bean.getDateText();
			String auctionCategoryId = bean.getAuctionCategoryId();
			if (rawDate.equals("") || rawDate == null
					|| auctionCategoryId.equals("")
					|| auctionCategoryId == null) {
				return new ModelAndView(
						"redirect:/auction/cs/getUnserviceableItems.do");
			}

			/*
			 * List<Date> countedDateList = (List<Date>) (Object) commonService
			 * .getDistinctValueListByColumnName("AuctionProcessMst", "toDate");
			 */

			List<Date> countedDateList = (List<Date>) (Object) commonService
					.getDistinctValueListByOneColumnNameAndValue(
							"AuctionProcessMst", "toDate",
							"auctionCategory.id", auctionCategoryId);

			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date beanDate = df.parse(rawDate);
			boolean flag = false;
			if (countedDateList != null) {
				for (Date date : countedDateList) {
					String dbDateText = df.format(date);
					if (dbDateText.equals(rawDate)) {
						flag = true;
						break;
					}
				}
			}

			if (flag) {
				return new ModelAndView("redirect:/auction/processlist.do",
						model);
			} else {
				String auctionName = auctionService.getNextAuctionId();
				String[] auctionCategoryArray = auctionCategoryId.split(",");
				for (int i = 0; i < auctionCategoryArray.length; i++) {
					this.viewToProcessTransaction(rawDate, beanDate, userName,
							auctionCategoryArray[i], auctionName);
				}
			}

			return new ModelAndView("redirect:/auction/processlist.do", model);

		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	String getAuctionViewKey(AuctionView auctionView) {
		String key = auctionView.getDepartments().getId().toString();
		key += "-" + auctionView.getDescoKhath().getId().toString();
		key += "-" + auctionView.getStoreLocations().getId().toString();
		key += "-" + auctionView.getItemMaster().getId().toString();
		return key;
	}

	@SuppressWarnings("unchecked")
	void viewToProcessTransaction(String rawDate, Date beanDate,
			String userName, String auctionCatId, String auctionName) {

		List<AuctionCategoryReference> aucRefList = (List<AuctionCategoryReference>) (Object) commonService
				.getObjectListByAnyColumn("AuctionCategoryReference",
						"auctionCategory.id", auctionCatId);

		List<String> itemcatList = new ArrayList<String>();
		for (AuctionCategoryReference ref : aucRefList) {
			itemcatList.add(ref.getItemCategory().getCategoryId().toString());
		}

		List<AuctionView> dataList = auctionService
				.getAuctionViewListByDateRange(rawDate, itemcatList);
		Map<String, Double> map = auctionService.getAuctionViewListByGroup(
				rawDate, itemcatList);

		if (dataList != null && dataList.size() > 0) {
			AuctionCategory auctionCategory = (AuctionCategory) commonService
					.getAnObjectByAnyUniqueColumn("AuctionCategory", "id",
							auctionCatId);

			AuctionProcessMst aucMst = new AuctionProcessMst();
			aucMst.setId(null);
			aucMst.setAuctionName(auctionName);
			aucMst.setAuctionCategory(auctionCategory);
			aucMst.setToDate(beanDate);

			aucMst.setCreatedBy(userName);
			aucMst.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(aucMst);

			int maxId = (Integer) commonService.getMaxValueByObjectAndColumn(
					"AuctionProcessMst", "id");
			AuctionProcessMst mstDB = (AuctionProcessMst) commonService
					.getAnObjectByAnyUniqueColumn("AuctionProcessMst", "id",
							maxId + "");

			for (AuctionView auctionView : dataList) {

				String tempKey = getAuctionViewKey(auctionView);
				for (String key : map.keySet()) {
					if (tempKey.equals(key)) {
						AuctionProcessDtl dtl = new AuctionProcessDtl();
						dtl.setId(null);
						dtl.setAuctionProcessMst(mstDB);
						dtl.setDepartments(auctionView.getDepartments());
						dtl.setDescoKhath(auctionView.getDescoKhath());
						dtl.setStoreLocations(auctionView.getStoreLocations());
						dtl.setItemMaster(auctionView.getItemMaster());
						dtl.setQuantity(map.get(key));
						dtl.setCreatedBy(userName);
						dtl.setCreatedDate(new Date());
						if (dtl.getQuantity() > 0) {
							commonService.saveOrUpdateModelObjectToDB(dtl);
						}
					}
				}
				// removing the used group by row
				map.remove(tempKey);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/processlist.do", method = RequestMethod.GET)
	public ModelAndView getAuctionProcesslist() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		boolean foundUnserviceble = false;
		try {
			List<AuctionProcessMst> mstList = (List<AuctionProcessMst>) (Object) commonService
					.getAllObjectList("AuctionProcessMst");
			
			// checking any AuctionProcessMst did not send to admin
			for(AuctionProcessMst each:mstList)	{
				if(each.getCs_to_admin_flag().equals("0")){
					foundUnserviceble = true;
					break;
				}
			}		
			
			model.put("auctionProcessMstList", mstList);
			model.put("foundUnserviceble", foundUnserviceble);
			return new ModelAndView("centralStore/auction/auctionProcessList",
					model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/condemnForm.do", method = RequestMethod.GET)
	public ModelAndView getCondemnForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<AuctionProcessMst> auctionProcessMstList = (List<AuctionProcessMst>) (Object) commonService
					.getObjectListByAnyColumn("AuctionProcessMst", "isActive",
							"1");

			List<AuctionCategory> auctionCategoryList = (List<AuctionCategory>) (Object) commonService
					.getObjectListByAnyColumn("AuctionCategory", "isActive",
							"1");
			model.put("auctionProcessMstList", auctionProcessMstList);
			model.put("auctionCategoryList", auctionCategoryList);
			return new ModelAndView("centralStore/auction/condemnForm", model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/saveCondemnItem.do", method = RequestMethod.POST)
	public ModelAndView getAuctionCondemnList(AuctionProcessMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String userName = commonService.getAuthUserName();
			String auctionProcessMstId = bean.getId().toString();

			List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
					.getObjectListByAnyColumn("CondemnMst",
							"auctionProcessMst.id", auctionProcessMstId);

			if (condemnMstList != null & condemnMstList.size() > 0) {
				// model.put("condemnMstList", condemnMstList);
			} else {

				AuctionProcessMst aucProcessMst = (AuctionProcessMst) commonService
						.getAnObjectByAnyUniqueColumn("AuctionProcessMst",
								"id", auctionProcessMstId);
				List<AuctionProcessDtl> dtlList = (List<AuctionProcessDtl>) (Object) commonService
						.getObjectListByAnyColumn("AuctionProcessDtl",
								"auctionProcessMst.id", auctionProcessMstId);

				if (dtlList != null && dtlList.size() > 0) {
					AuctionCategory auctionCategory = aucProcessMst
							.getAuctionCategory();

					CondemnMst condemnMst = new CondemnMst();
					condemnMst.setAuctionProcessMst(aucProcessMst);
					condemnMst.setAuctionCategory(auctionCategory);
					condemnMst.setActive(true);
					condemnMst.setCreatedBy(userName);
					condemnMst.setCreatedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(condemnMst);

					int maxId = (Integer) commonService
							.getMaxValueByObjectAndColumn("CondemnMst", "id");
					CondemnMst mstDB = (CondemnMst) commonService
							.getAnObjectByAnyUniqueColumn("CondemnMst", "id",
									maxId + "");

					Map<String, Double> maps = auctionService
							.getAuctionProcesDtlListGroupByDept(
									auctionProcessMstId, null);

					for (AuctionProcessDtl acDtl : dtlList) {
						String tempKey = getAuctionProcessDtlKey(acDtl);
						for (String key : maps.keySet()) {
							if (tempKey.equals(key)) {
								CondemnDtl condemnDtl = new CondemnDtl();
								condemnDtl.setId(null);
								condemnDtl.setCondemnMst(mstDB);
								condemnDtl.setDepartments(acDtl
										.getDepartments());
								condemnDtl.setItemMaster(acDtl.getItemMaster());
								condemnDtl.setQty(maps.get(key));
								condemnDtl.setCondemnQty(maps.get(key));

								condemnDtl.setCreatedBy(userName);
								condemnDtl.setCreatedDate(new Date());

								if (condemnDtl.getQty() > 0) {
									commonService
											.saveOrUpdateModelObjectToDB(condemnDtl);
								}
							}
						}
						// removing the used group by row
						maps.remove(tempKey);
					}
				}
			}

			// redirect to condemn list
			return new ModelAndView("redirect:/auction/admin/condemnList.do");

		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("centralStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/admin/condemnList.do", method = RequestMethod.GET)
	public ModelAndView condemnListAdminDGM() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<CondemnMst> condemnMstListAll = (List<CondemnMst>) (Object) commonService
				.getObjectListByAnyColumn("CondemnMst", "active", "1");
		model.put("condemnMstList", condemnMstListAll);
		return new ModelAndView("centralStore/auction/condemnListAdminDGM",
				model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cc/condemnList.do", method = RequestMethod.GET)
	public ModelAndView getCondemnList() {		
		Map<String, Object> model = new HashMap<String, Object>();
		String userid=commonService.getAuthUserName();
		
		List<AuctionCommittees> convenerList = (List<AuctionCommittees>) (Object) 
				commonService.getObjectListByTwoColumn("AuctionCommittees", "authUser.userid", userid,
						"committeeName", CONDEMN_COMMITTEE);
		
		List<String> condemnMstIdList=new ArrayList<String>();
		for (AuctionCommittees c : convenerList) {
			condemnMstIdList.add(c.getCondemnMst().getId().toString());
		}		
		
		List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("CondemnMst", "id", condemnMstIdList);
		
		model.put("condemnMstList", condemnMstList);
		return new ModelAndView("centralStore/auction/condemnList", model);
	}

	String getAuctionProcessDtlKey(AuctionProcessDtl obj) {
		String key = obj.getAuctionProcessMst().getId().toString();
		key += "-" + obj.getDepartments().getId().toString();
		key += "-" + obj.getItemMaster().getId().toString();
		return key;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/ac/auctionList.do", method = RequestMethod.GET)
	public ModelAndView getAuctionList() {		
		Map<String, Object> model = new HashMap<String, Object>();
		String userid=commonService.getAuthUserName();
		
		List<AuctionCommittees> convenerList = (List<AuctionCommittees>) (Object) 
				commonService.getObjectListByTwoColumn("AuctionCommittees", "authUser.userid", userid,
						"committeeName", AUCTION_COMMITTEE);
		
		List<String> condemnMstIdList=new ArrayList<String>();
		for (AuctionCommittees c : convenerList) {
			condemnMstIdList.add(c.getCondemnMst().getId().toString());
		}		
		
		List<CondemnMst> condemnMstList = (List<CondemnMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("CondemnMst", "id", condemnMstIdList);
		
		model.put("condemnMstList", condemnMstList);
		return new ModelAndView("centralStore/auction/auctionList", model);
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cs/viewUnserviceableItems.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewUnserviceableItems(AuctionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			AuctionMst mstDb = new AuctionMst();

			Departments department = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							"505");

			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			String fromDate = df.format(bean.getCountDate());

			Date fromDatePlusOneDay = new Date(bean.getCountDate().getTime()
					+ (1000 * 60 * 60 * 24));
			String toDate = df.format(fromDatePlusOneDay);

			List<AuctionMst> auctionMstList = (List<AuctionMst>) (Object) commonService
					.getObjectListByDateRangeAndTwoColumn("AuctionMst",
							"countDate", fromDate, toDate, "active", "1",
							"departments.id", department.getId() + "");

			if (auctionMstList.size() > 0) {
				mstDb = auctionMstList.get(0);
				model.put("auctionMst", mstDb);

				List<String> idList = new ArrayList<String>();
				for (AuctionMst mst : auctionMstList) {
					idList.add(mst.getId().toString());
				}

				List<AuctionDtl> auctionDtlList = (List<AuctionDtl>) (Object) commonService
						.getObjectListByAnyColumnValueListAndOneColumn(
								"AuctionDtl", "auctionMst.id", idList,
								"store_to_admin_flag", "0");

				model.put("auctionDtlList", auctionDtlList);

			}

			return new ModelAndView("centralStore/auction/csUnserviceableList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	// this is a temporary method.

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auction/cs/unserviceableList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCsUnserviceableList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			AuctionMst mstDb = new AuctionMst();

			List<CSItemTransactionMst> csTnxMstList = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByAnyColumn("CSItemTransactionMst",
							"ledgerName", UNSERVICEABLE);
			Departments department = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							"505");

			if (csTnxMstList.size() > 0) {
				AuctionMst auctionMst = new AuctionMst();
				auctionMst.setId(null);
				auctionMst.setCountDate(new Date());
				auctionMst.setDepartments(department);
				auctionMst.setCreatedDate(new Date());
				auctionMst.setCreatedBy(commonService.getAuthUserName());

				commonService.saveOrUpdateModelObjectToDB(auctionMst);

				Integer maxMstId = (Integer) commonService
						.getMaxValueByObjectAndColumn("AuctionMst", "id");
				mstDb = (AuctionMst) commonService
						.getAnObjectByAnyUniqueColumn("AuctionMst", "id",
								maxMstId + "");

				for (CSItemTransactionMst mst : csTnxMstList) {
					String itemCode = mst.getItemCode();
					ItemMaster itemMaster = (ItemMaster) commonService
							.getAnObjectByAnyUniqueColumn("ItemMaster",
									"itemId", itemCode);
					DescoKhath descoKhath = (DescoKhath) commonService
							.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
									mst.getKhathId().toString());

					AuctionDtl dtl = new AuctionDtl();
					dtl.setId(null);
					dtl.setAuctionMst(mstDb);
					dtl.setItemMaster(itemMaster);
					dtl.setDescoKhath(descoKhath);
					dtl.setLedgerQty(mst.getQuantity());
					dtl.setStoreFinalQty(mst.getQuantity());
					auctionMst.setCreatedDate(new Date());
					auctionMst.setCreatedBy(commonService.getAuthUserName());

					commonService.saveOrUpdateModelObjectToDB(dtl);

				}
			}

			List<AuctionDtl> dtlList = (List<AuctionDtl>) (Object) commonService
					.getObjectListByAnyColumn("AuctionDtl", "auctionMst.id",
							mstDb.getId().toString());

			model.put("auctionMst", mstDb);
			model.put("auctionDtlList", dtlList);
			return new ModelAndView("centralStore/auction/csUnserviceableList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/auction/store/updateAuctionDtl.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateItemByAjax(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionDtl auctionDtl = gson.fromJson(json, AuctionDtl.class);
			Integer id = auctionDtl.getId();
			Double storeFinalQty = auctionDtl.getStoreFinalQty();

			AuctionDtl auctionDtlDb = (AuctionDtl) commonService
					.getAnObjectByAnyUniqueColumn("AuctionDtl", "id", id + "");
			auctionDtlDb.setModifiedBy(commonService.getAuthUserName());
			auctionDtlDb.setModifiedDate(new Date());
			auctionDtlDb.setStoreFinalQty(storeFinalQty);

			commonService.saveOrUpdateModelObjectToDB(auctionDtlDb);

			toJson = ow.writeValueAsString(auctionDtlDb);
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/auction/cs/sendCsToAdmin.do", method = RequestMethod.POST)
	@ResponseBody
	public String sendCsToAdmin(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionProcessMst bean = gson.fromJson(json,
					AuctionProcessMst.class);
			String id = bean.getId().toString();

			AuctionProcessMst mstDb = (AuctionProcessMst) commonService
					.getAnObjectByAnyUniqueColumn("AuctionProcessMst", "id", id);
			mstDb.setCs_to_admin_flag("1");
			mstDb.setCs_to_admin_date(new Date());
			mstDb.setModifiedBy(commonService.getAuthUserName());
			mstDb.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(mstDb);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(1000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getItemMasterPrimaryIdByAuctionCategoryId(
			String auctionCategoryId) {
		List<Integer> itemMasterId = new ArrayList<Integer>();
		List<String> auctionCategoryIdList = auctionService
				.getItemCategoryList(auctionCategoryId);
		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumnValueList("ItemMaster", "categoryId",
						auctionCategoryIdList);
		for (ItemMaster im : itemMasterList) {
			itemMasterId.add(im.getId());
		}
		return itemMasterId;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getItemMasterPrimaryIdByItemCategoryId(
			List<String> itemCategoryIdList) {
		List<Integer> itemMasterId = new ArrayList<Integer>();

		List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
				.getObjectListByAnyColumnValueList("ItemCategory", "id",
						itemCategoryIdList);
		List<String> auctionCategoryIdList = new ArrayList<String>();
		for (ItemCategory ic : itemCategoryList) {
			auctionCategoryIdList.add(ic.getCategoryId().toString());
		}

		List<ItemMaster> itemMasterList = (List<ItemMaster>) (Object) commonService
				.getObjectListByAnyColumnValueList("ItemMaster", "categoryId",
						auctionCategoryIdList);

		for (ItemMaster im : itemMasterList) {
			itemMasterId.add(im.getId());
		}
		return itemMasterId;
	}
}
