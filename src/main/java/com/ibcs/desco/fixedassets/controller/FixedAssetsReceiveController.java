package com.ibcs.desco.fixedassets.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CSItemReceivedService;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.fixedassets.bean.CombindFixedAssetBean;
import com.ibcs.desco.fixedassets.bean.FixedAssetReceiveBean;
import com.ibcs.desco.fixedassets.bean.FixedAssetsBean;
import com.ibcs.desco.fixedassets.model.DepreciationSummery;
import com.ibcs.desco.fixedassets.model.DisposalAssets;
import com.ibcs.desco.fixedassets.model.FixedAssetCategory;
import com.ibcs.desco.fixedassets.model.FixedAssetReceive;
import com.ibcs.desco.fixedassets.model.FixedAssets;
import com.ibcs.desco.fixedassets.service.FixedAssetCategoryService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
@RequestMapping(value = "/fixedAssets")
@PropertySource("classpath:common.properties")
public class FixedAssetsReceiveController extends Constrants {

	// Create Object for Service Classes

	@Autowired
	FixedAssetCategoryService fixedAssetCategoryService;

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	WSTransformerService wSTransformerService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.fixedAsset.prefix}")
	private String fixedAssetPrefix;

	@Value("${desco.fixedAsset.key}")
	private String fixedAssetKey;

	//private static String DATE_FORMAT = "dd-MM-yyyy";
	// private static String DATE_FORMAT = "mm/dd/yyyy HH:MM:SS";
	Date date = new Date();

	// Date date1 = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss.S").parse(oldstring);
	public String getCurrentDate(Date date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
		String strDate = sdfDate.format(date);

		return strDate;
	}

	public Date setCurrentDate(String date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
		Date parseDate = null;
		try {
			parseDate = sdfDate.parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return parseDate;
	}

	// get Item Received From where give item Entry after procurement and
	// generate Receiving Report
	// Added by Nasrin
	@RequestMapping(value = "/stList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getRRMstList() {
		List<CSStoreTicketMst> cSStoreT = csStoreTicketMstService.csStoreTicketListForFixedAssets();
		List<SSStoreTicketMst> sSStoreTicketMst = csStoreTicketMstService.ssStoreTicketListForFixedAssets();
		List<CSStoreTicketMst> cSStoreTicketMst = new ArrayList<CSStoreTicketMst>();
		  for(CSStoreTicketMst mst:cSStoreT){
			  CentralStoreRequisitionMst cs=(CentralStoreRequisitionMst) commonService.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst", "storeTicketNO", mst.getTicketNo());
			  if(!cs.getSenderStore().equalsIgnoreCase(ContentType.WS_CN_XFORMER.toString())){
		  mst.setRemarks(ContentType.CENTRAL_STORE.toString());
		  cSStoreTicketMst.add(mst);
			  } }
		 

		
		 for(SSStoreTicketMst mst:sSStoreTicketMst){
		  mst.setRemarks(ContentType.SUB_STORE.toString()); }
		 
		List<CSStoreTicketMst> csList = ssConvertToCs(sSStoreTicketMst);
		cSStoreTicketMst.addAll(csList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cSStoreTicketMst", cSStoreTicketMst);
		return new ModelAndView("fixedAsset/stListForFixedAssets", model);
	}

	private List<CSStoreTicketMst> ssConvertToCs(List<SSStoreTicketMst> sSStoreTicketMst) {

		List<CSStoreTicketMst> cSStoreTicketMstList = new ArrayList<CSStoreTicketMst>();
		CSStoreTicketMst cs = null;
		for (SSStoreTicketMst mst : sSStoreTicketMst) {
			cs = new CSStoreTicketMst();
			cs.setId(mst.getId());
			cs.setTicketNo(mst.getTicketNo());
			cs.setTicketDate(mst.getTicketDate());
			cs.setIssuedBy(mst.getIssuedBy());
			cs.setRemarks(mst.getRemarks());
			cs.setKhathName(mst.getKhathName());
			cSStoreTicketMstList.add(cs);
		}

		return cSStoreTicketMstList;
	}

	private CSStoreTicketMst ssConvertToCs(SSStoreTicketMst mst) {

		CSStoreTicketMst cs = null;
		cs = new CSStoreTicketMst();
		cs.setId(mst.getId());
		cs.setTicketNo(mst.getTicketNo());
		cs.setTicketDate(mst.getTicketDate());
		cs.setIssuedBy(mst.getIssuedBy());
		cs.setRemarks(mst.getRemarks());
		cs.setIssuedTo(mst.getIssuedTo());
		cs.setKhathName(mst.getKhathName());

		return cs;
	}

	@RequestMapping(value = "/stShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView stShowPost(CSStoreTicketMst ctoreTicketMst) {
		return stShow(ctoreTicketMst);
	}

	@RequestMapping(value = "/stShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView stShowGet(CSStoreTicketMst ctoreTicketMst) {
		return stShow(ctoreTicketMst);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView stShow(CSStoreTicketMst ctoreTicketMst) {

		List<CSStoreTicketDtl> cSStoreTicketDtlList = new ArrayList<CSStoreTicketDtl>();
		List<SSStoreTicketDtl> sSStoreTicketDtlList = new ArrayList<SSStoreTicketDtl>();

		Map<String, Object> model = new HashMap<String, Object>();
		CSStoreTicketMst csStoreTicketMst = null;
		SSStoreTicketMst ssStoreTicketMst = null;
		if (ctoreTicketMst.getRemarks().equalsIgnoreCase(ContentType.CENTRAL_STORE.toString())) {
			csStoreTicketMst = (CSStoreTicketMst) commonService.getAnObjectByAnyUniqueColumn("CSStoreTicketMst", "id",
					ctoreTicketMst.getId().toString());
			cSStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
					.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo", csStoreTicketMst.getTicketNo());

			model.put("cSStoreTicketDtlList", cSStoreTicketDtlList);
			model.put("cSStoreTicketMst", csStoreTicketMst);
			model.put("deptName", ContentType.CENTRAL_STORE.toString());
		} else {

			ssStoreTicketMst = (SSStoreTicketMst) commonService.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "id",
					ctoreTicketMst.getId().toString());
			sSStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
					.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo", ssStoreTicketMst.getTicketNo());

			model.put("cSStoreTicketDtlList", sSStoreTicketDtlList);
			model.put("cSStoreTicketMst", ssStoreTicketMst);
			model.put("deptName", ContentType.SUB_STORE.toString());

		}
		List<FixedAssetCategory> fixedAssetCategory = fixedAssetCategoryService.getFixedAssetCategoryList();
		List<Departments> departments = departmentsService.listDepartments();

		model.put("departments", departments);
		model.put("fixedAssetCategory", fixedAssetCategory);
		return new ModelAndView("fixedAsset/stShowForFixedAssets", model);
	}

	@RequestMapping(value = "/avgPrice.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public Double avgPrice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String itemCode = request.getParameter("itemCode").trim();
		AvgPrice items = (AvgPrice) commonService.getAnObjectByAnyUniqueColumn("AvgPrice", "itemCode", itemCode);
		if (items != null) {
			return items.getPrice();
		} else {
			return 0d;
		}
	}

	@RequestMapping(value = "/lifeTime.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String lifeTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String itemCode = request.getParameter("itemCode").trim();
		ItemMaster items = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId", itemCode);
		if (items != null) {
			return items.getLifeTime() + "," + items.getDepreciationRate();
		} else {
			return "";
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveFixedAsset.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveFixedAssetByST(FixedAssetReceiveBean fixedAsset) {

		FixedAssetReceive fixedAssetReceive = new FixedAssetReceive();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();
		ItemMaster itemMaster = null;
		if(fixedAsset.getItemId()!=null){
		 itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
				"ItemMaster", "itemId", fixedAsset.getItemId());
		}
		// fixedAssetReceive.getFromDept().trim();

		fixedAssetReceive.setId(null);
		fixedAssetReceive.setCreatedBy(userName);
		fixedAssetReceive.setCreatedDate(new Date());
		fixedAssetReceive.setReceiveDate(setCurrentDate(getCurrentDate(new Date())));
		fixedAssetReceive.setActive(true);
		fixedAssetReceive.setDepreciated(false);
		fixedAssetReceive.setAssetType(fixedAsset.getAssetType());
		fixedAssetReceive.setAvgPrice(fixedAsset.getAvgPrice());
		fixedAssetReceive.setFromDept(fixedAsset.getFromDept());
		fixedAssetReceive.setToDept(fixedAsset.getToDept());
		fixedAssetReceive.setItemId(fixedAsset.getItemId());
		fixedAssetReceive.setItemName(fixedAsset.getItemName());
		fixedAssetReceive.setKhatCode(fixedAsset.getKhatCode());
		fixedAssetReceive.setTicketId(fixedAsset.getTicketId());
		fixedAssetReceive.setTicketDate(fixedAsset.getTicketDate());
		fixedAssetReceive.setQuantity(fixedAsset.getQuantity());
		fixedAssetReceive.setTotalPrice(fixedAsset.getTotalPrice());
		fixedAssetReceive.setLifeTime(fixedAsset.getLifeTime());
		fixedAssetReceive.setDepreciationRate(fixedAsset.getDepreciationRate());
		fixedAssetReceive.setVersion(fixedAsset.getVersion());
		fixedAssetReceive.setSerialNumber(fixedAsset.getSerialNumber());
		fixedAssetReceive.setModelNumber(fixedAsset.getModelNumber());
		fixedAssetReceive.setItemMaster(itemMaster);
		fixedAssetReceive.setItemMasterId(itemMaster.getId());
		
		String regKey = commonService.getOperationIdByPrefixAndSequenceName(
					fixedAssetKey, descoDeptCode, separator,
					FA_REG_KEY_SEQ);
		fixedAssetReceive.setFaRegKey(regKey);

		commonService.saveOrUpdateModelObjectToDB(fixedAssetReceive);

		// depreciation calculation...........

		Calendar cal = Calendar.getInstance();
		cal.setTime(fixedAssetReceive.getReceiveDate());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH); 
		int lifeTime = Integer.parseInt(fixedAssetReceive.getItemMaster().getLifeTime());
		int sessionStart = 0;
		int sessionEnd = 0;
		boolean flag=false;
		//if (month <= 12 && month >= 6) {
			sessionStart = year;
			sessionEnd = year + 1;
			
			if (month <= 6 && month >= 1){
				flag=true;
				lifeTime=lifeTime+1;
			}
		//} else {
		//	sessionStart = year;
		//	sessionEnd = year + 1;
		//}
		double totalCost = fixedAssetReceive.getTotalPrice();
		double depRate = fixedAssetReceive.getItemMaster().getDepreciationRate();
		double depreciation = totalCost * depRate / 100;
		double depUptoLastYr = 0d;
		double cuDepreciation = 0d;
		double wdv = 0d;
		DepreciationSummery ds = null;
		for (int i = 1; i <= lifeTime; i++) {

			if(flag){
				ds = new DepreciationSummery();
				ds.setId(null);
				ds.setActive(true);
				ds.setCreatedBy(userName);
				ds.setCreateDate(new Date());
				ds.setAssetId(fixedAssetReceive.getItemId());
				ds.setPurchaseDate(fixedAssetReceive.getReceiveDate());
				ds.setPurchasePrice(fixedAssetReceive.getTotalPrice());
				ds.setNoOfYear("1");
				ds.setLifeTime(fixedAssetReceive.getItemMaster().getLifeTime());
				ds.setDepreciationRate(fixedAssetReceive.getItemMaster().getDepreciationRate());
				ds.setDepCurrentYear(0d);
				ds.setSessionYear(String.valueOf(sessionStart-1) + "-" + String.valueOf(sessionEnd-1));
				ds.setDepStartDate(setCurrentDate("01-07-"+String.valueOf(sessionStart)));
				ds.setDepEndDate(setCurrentDate("30-06-"+String.valueOf(sessionEnd)));
				ds.setDepUptoLastYear(0d);
				ds.setCuDepreciation(0d);
				ds.setWrittenDownValue(fixedAssetReceive.getTotalPrice());
				ds.setFaRegKey(regKey);
				commonService.saveOrUpdateModelObjectToDB(ds);
}
			DecimalFormat formatter = new DecimalFormat("##.###");
			if (i != 1) {
				depUptoLastYr += depreciation;
			}
			if(i==1 && flag){
				depUptoLastYr=0d;
				i++;
				flag=false;
			}
			String noOfYr = i + "";
			
			cuDepreciation += depreciation;
			wdv = totalCost - cuDepreciation;
			ds = new DepreciationSummery();
			ds.setId(null);
			ds.setActive(true);
			ds.setCreatedBy(userName);
			ds.setCreateDate(new Date());
			ds.setAssetId(fixedAssetReceive.getItemId());
			ds.setPurchaseDate(fixedAssetReceive.getReceiveDate());
			ds.setPurchasePrice(fixedAssetReceive.getTotalPrice());
			ds.setNoOfYear(noOfYr);
			ds.setLifeTime(fixedAssetReceive.getItemMaster().getLifeTime());
			ds.setDepreciationRate(fixedAssetReceive.getItemMaster().getDepreciationRate());
			ds.setDepCurrentYear(Double.valueOf(formatter.format(depreciation)));
			ds.setSessionYear(String.valueOf(sessionStart) + "-" + String.valueOf(sessionEnd));
			ds.setDepStartDate(setCurrentDate("01-07-"+String.valueOf(sessionStart)));
			ds.setDepEndDate(setCurrentDate("30-06-"+String.valueOf(sessionEnd)));
			ds.setDepUptoLastYear(Double.valueOf(formatter.format(depUptoLastYr)));
			ds.setCuDepreciation(Double.valueOf(formatter.format(cuDepreciation)));
			ds.setWrittenDownValue(Double.valueOf(formatter.format(wdv)));
			ds.setFaRegKey(regKey);
			commonService.saveOrUpdateModelObjectToDB(ds);

			sessionStart++;
			sessionEnd++;
		}
		// --------------------------
		if (fixedAssetReceive.getFromDept().equalsIgnoreCase(CENTRAL_STORE))

		{

			CSStoreTicketDtl ctoreTicketDtl = (CSStoreTicketDtl) commonService
					.getAnObjectByAnyUniqueColumn("CSStoreTicketDtl", "id", fixedAsset.getAssetId());
			ctoreTicketDtl.setFixedAsset(true);
			commonService.saveOrUpdateModelObjectToDB(ctoreTicketDtl);

			CSStoreTicketMst ctoreTicketMst = (CSStoreTicketMst) commonService.getAnObjectByAnyUniqueColumn(
					"CSStoreTicketMst", "id", ctoreTicketDtl.getCsStoreTicketMst().getId().toString());
			ctoreTicketMst.setRemarks(ContentType.CENTRAL_STORE.toString());
			return stShow(ctoreTicketMst);
		} else {

			SSStoreTicketDtl ctoreTicketDtl = (SSStoreTicketDtl) commonService
					.getAnObjectByAnyUniqueColumn("SSStoreTicketDtl", "id", fixedAsset.getAssetId());

			ctoreTicketDtl.setFixedAsset(true);
			commonService.saveOrUpdateModelObjectToDB(ctoreTicketDtl);

			SSStoreTicketMst ctoreTicketMst = (SSStoreTicketMst) commonService.getAnObjectByAnyUniqueColumn(
					"SSStoreTicketMst", "id", ctoreTicketDtl.getSsStoreTicketMst().getId().toString());
			ctoreTicketMst.setRemarks(ContentType.SUB_STORE.toString());
			return stShowGet(ssConvertToCs(ctoreTicketMst));
		}

		// redirectAttributes.addFlashAttribute("cSStoreTicketMst",
		// cSStoreTicketMst);

	}
	
	  /*Comparator for sorting the list by item Name*/
	  public static Comparator<ItemMaster> itemCom = new Comparator<ItemMaster>() {

	public int compare(ItemMaster s1, ItemMaster s2) {
	   String itemName1 = s1.getItemName().toUpperCase();
	   String itemName2 = s2.getItemName().toUpperCase();

	   //ascending order
	   return itemName1.compareTo(itemName2);

	   //descending order
	   //return itemName2.compareTo(itemName1);
    }

	};

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/fixedAssetForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getFixedAssetForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Departments> departments = departmentsService.listDepartments();
		List<ItemMaster> items = itemInventoryService.getInventoryItemList();

		model.put("departments", departments);
		Collections.sort(items, this.itemCom);
		model.put("itemList", items);

		return new ModelAndView("fixedAsset/fixedAssetForm", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveManualFixedAsset.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveManualFixedAsset(FixedAssetsBean fixedAsset) {

		FixedAssets fixedAssetReceive = new FixedAssets();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String descoDeptCode = department.getDescoCode();
		Calendar cal = Calendar.getInstance();
		ItemMaster itemMaster = null;
		
		// -----------------------

		fixedAssetReceive.setId(null);
		fixedAssetReceive.setCreatedBy(userName);
		fixedAssetReceive.setCreateDate(new Date());
		fixedAssetReceive.setActive(true);
		fixedAssetReceive.setDepreciated(false);
		fixedAssetReceive.setAssetType(fixedAsset.getAssetType());
		fixedAssetReceive.setBrandName(fixedAsset.getBrandName());
		fixedAssetReceive.setCc(fixedAsset.getCc());
		fixedAssetReceive.setChacisNo(fixedAsset.getChacisNo());
		//fixedAssetReceive.setItemId(fixedAsset.getItemId());
		fixedAssetReceive.setColor(fixedAsset.getColor());
		fixedAssetReceive.setQuantity(fixedAsset.getQuantity());
		fixedAssetReceive.setInstallationCharge(fixedAsset.getInstallationCharge());
		fixedAssetReceive.setTotalPrice(fixedAsset.getTotalPrice());
		if(fixedAsset.getDisEqpDivision()!=null && fixedAsset.getDisEqpDivision()!=""){
			fixedAssetReceive.setDivisionName(fixedAsset.getDisEqpDivision());
		}
			if(fixedAsset.getDisLineDivision()!=null && fixedAsset.getDisLineDivision()!=""){
				fixedAssetReceive.setDivisionName(fixedAsset.getDisLineDivision());
			}
				if(fixedAsset.getLandDivision()!=null && fixedAsset.getLandDivision()!=""){
					fixedAssetReceive.setDivisionName(fixedAsset.getLandDivision());
				}
					if(fixedAsset.getBuildingDivision()!=null && fixedAsset.getBuildingDivision()!=""){
						fixedAssetReceive.setDivisionName(fixedAsset.getBuildingDivision());
				
		}
		fixedAssetReceive.setEngineNo(fixedAsset.getEngineNo());
		if(fixedAsset.getDisEqpSubCategory()!=null && fixedAsset.getDisEqpSubCategory()!=""){
			fixedAssetReceive.setSubCategory(fixedAsset.getDisEqpSubCategory());
		}
		if(fixedAsset.getDisLineSubCategory()!=null && fixedAsset.getDisLineSubCategory()!=""){
			fixedAssetReceive.setSubCategory(fixedAsset.getDisLineSubCategory());
		}
		fixedAssetReceive.setProjectName(fixedAsset.getProjectName());
		fixedAssetReceive.setRating(fixedAsset.getRating());
		fixedAssetReceive.setKhatianNo(fixedAsset.getKhatianNo());
		fixedAssetReceive.setLocationId(fixedAsset.getLocationId());
		fixedAssetReceive.setLifeTime(fixedAsset.getLifeTime());
		fixedAssetReceive.setDepreciationRate(fixedAsset.getDepreciationRate());
		fixedAssetReceive.setAdditionLength(fixedAsset.getAdditionLength());
		fixedAssetReceive.setZone(fixedAsset.getZone());
		fixedAssetReceive.setTariffCategory(fixedAsset.getTariffCategory());
		fixedAssetReceive.setMeterType(fixedAsset.getMeterType());
		
		String regKey = commonService.getOperationIdByPrefixAndSequenceName(
					fixedAssetKey, descoDeptCode, separator,
					FA_REG_KEY_SEQ);
		fixedAssetReceive.setFaRegKey(regKey);
		
		if(!fixedAsset.getFixedAssetId().equalsIgnoreCase("")){
			fixedAssetReceive.setFixedAssetId(fixedAsset.getFixedAssetId());
			fixedAssetReceive.setFixedAssetName(itemInventoryService.getItemNameByItemCode(fixedAsset.getFixedAssetId()));
			 itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
					"ItemMaster", "itemId", fixedAsset.getFixedAssetId());
			// fixedAssetReceive.setItemMaster(itemMaster);
			 	fixedAssetReceive.setItemMasterId(itemMaster.getId());

			}else{
				fixedAssetReceive.setFixedAssetName(fixedAsset.getItemId());
				 itemMaster = itemInventoryService.getInventoryItemByItemName(fixedAsset.getItemId());
				 if(itemMaster!=null){
				 fixedAssetReceive.setFixedAssetId(itemMaster.getItemId());
				 }else{
					 String assetId = commonService.getOperationIdByPrefixAndSequenceName(
								fixedAssetPrefix, descoDeptCode, separator,
								FIXED_ASSET_SEQ);
						
					 fixedAssetReceive.setFixedAssetId(assetId);
					 fixedAssetReceive.setNonCoded(true);
				 }				 
			}
		fixedAssetReceive.setModelNumber(fixedAsset.getModelNumber());
		if(!fixedAsset.getPurchaseDate().equalsIgnoreCase("")){
			fixedAssetReceive.setPurchaseDate(setCurrentDate(fixedAsset.getPurchaseDate()));
			}
		if(!fixedAsset.getCompleteDate().equalsIgnoreCase("")){
			fixedAssetReceive.setCompleteDate(setCurrentDate(fixedAsset.getCompleteDate()));
			fixedAssetReceive.setItemMasterId(0);
			}
		fixedAssetReceive.setPurchasePrice(fixedAsset.getPurchasePrice());
		fixedAssetReceive.setSerialNumber(fixedAsset.getSerialNumber());
		fixedAssetReceive.setSeats(fixedAsset.getSeats());
		fixedAssetReceive.setTypeOfVehicle(fixedAsset.getTypeOfVehicle());
		fixedAssetReceive.setVandorName(fixedAsset.getVandorName());
		fixedAssetReceive.setVersion(fixedAsset.getVersion());
		fixedAssetReceive.setRemarks(fixedAsset.getRemarks());
		
		commonService.saveOrUpdateModelObjectToDB(fixedAssetReceive);
		
		if(!fixedAsset.getOpt().equalsIgnoreCase("")){
		if(fixedAsset.getOpt().equalsIgnoreCase("land")){
			return "redirect:/fixedAssets/fixedAssetForm.do";
		}
		}
		if(!fixedAsset.getOpt().equalsIgnoreCase("")){
		if(fixedAsset.getOpt().equalsIgnoreCase("building")){
			if(fixedAssetReceive.getCompleteDate()!= null){
				cal.setTime(fixedAssetReceive.getCompleteDate());
			}else{
			cal.setTime(fixedAssetReceive.getPurchaseDate());
			}
		
		}else{
			cal.setTime(fixedAssetReceive.getPurchaseDate());
			}
		}
		// depreciation calculation...........

		
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int lifeTime = Integer.parseInt(fixedAsset.getLifeTime());
		int sessionStart = 0;
		boolean flag=false;
		int sessionEnd = 0;
		//if (month <= 12 && month >= 6) {
			sessionStart = year;
			sessionEnd = year + 1;
			if (month <= 6 && month >= 1){
				flag=true;
				lifeTime=lifeTime+1;
			}
		//} else {
			//sessionStart = year + 1;
			//sessionEnd = year + 2;
		//}
			DecimalFormat formatter = new DecimalFormat("##.###");
		double totalCost = fixedAssetReceive.getTotalPrice();
		double depRate = fixedAsset.getDepreciationRate();
		double depreciation = totalCost * depRate / 100;
		double depUptoLastYr = 0d;
		double cuDepreciation = 0d;
		double wdv = 0d;
		DepreciationSummery ds = null;
		for (int i = 1; i <= lifeTime; i++) {
			if(flag){
				ds = new DepreciationSummery();
				ds.setId(null);
				ds.setCreatedBy(userName);
				ds.setCreateDate(new Date());
				ds.setAssetId(fixedAssetReceive.getFixedAssetId());
				ds.setNoOfYear("1");
				
				if(fixedAssetReceive.getPurchaseDate()!=null){
					ds.setPurchaseDate(fixedAssetReceive.getPurchaseDate());
					ds.setLifeTime(itemMaster.getLifeTime());
					ds.setDepreciationRate(itemMaster.getDepreciationRate());
				}else{
					ds.setPurchaseDate(fixedAssetReceive.getCompleteDate());
					ds.setLifeTime(fixedAssetReceive.getLifeTime());
					ds.setDepreciationRate(fixedAssetReceive.getDepreciationRate());					
				}
				ds.setPurchasePrice(fixedAssetReceive.getTotalPrice());
				ds.setDepCurrentYear(0d);
				ds.setSessionYear(String.valueOf(sessionStart-1) + "-" + String.valueOf(sessionEnd-1));
				ds.setDepStartDate(setCurrentDate("01-07-"+String.valueOf(sessionStart)));
				ds.setDepEndDate(setCurrentDate("30-06-"+String.valueOf(sessionEnd)));
				ds.setDepUptoLastYear(0d);
				ds.setCuDepreciation(0d);
				ds.setWrittenDownValue(fixedAssetReceive.getTotalPrice());
				ds.setFaRegKey(regKey);
				commonService.saveOrUpdateModelObjectToDB(ds);
				
}
					
			if (i != 1) {
				depUptoLastYr += depreciation;
			}
			if(i==1 && flag){
				depUptoLastYr=0d;
				i++;
				flag=false;
			}
			String noOfYr = i + "";
			
			cuDepreciation += depreciation;
			wdv = totalCost - cuDepreciation;
			
			ds = new DepreciationSummery();
			ds.setId(null);
			ds.setCreatedBy(userName);
			ds.setCreateDate(new Date());
			ds.setAssetId(fixedAssetReceive.getFixedAssetId());
			ds.setPurchaseDate(fixedAssetReceive.getPurchaseDate());
			ds.setPurchasePrice(fixedAssetReceive.getTotalPrice());
			ds.setNoOfYear(noOfYr);
			if(fixedAssetReceive.getPurchaseDate()!=null){
				ds.setPurchaseDate(fixedAssetReceive.getPurchaseDate());
				ds.setLifeTime(itemMaster.getLifeTime());
				ds.setDepreciationRate(itemMaster.getDepreciationRate());
			}else{
				ds.setPurchaseDate(fixedAssetReceive.getCompleteDate());
				ds.setLifeTime(fixedAssetReceive.getLifeTime());
				ds.setDepreciationRate(fixedAssetReceive.getDepreciationRate());					
			}
			ds.setDepCurrentYear(Double.valueOf(formatter.format(depreciation)));
			ds.setSessionYear(String.valueOf(sessionStart) + "-" + String.valueOf(sessionEnd));
			ds.setDepStartDate(setCurrentDate("01-07-"+String.valueOf(sessionStart)));
			ds.setDepEndDate(setCurrentDate("30-06-"+String.valueOf(sessionEnd)));
			ds.setDepUptoLastYear(Double.valueOf(formatter.format(depUptoLastYr)));
			ds.setCuDepreciation(Double.valueOf(formatter.format(cuDepreciation)));
			ds.setWrittenDownValue(Double.valueOf(formatter.format(wdv)));
			ds.setFaRegKey(regKey);
			commonService.saveOrUpdateModelObjectToDB(ds);

			sessionStart++;
			sessionEnd++;
		}
		// --------------------------

		return "redirect:/fixedAssets/fixedAssetForm.do";
	}

	@RequestMapping(value = "/accHeadForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView accountHeadCodeForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);
		return new ModelAndView("fixedAsset/FixedAssetAccHeadForm", model);
	}

	@RequestMapping(value = "/saveFixedAssetAccHead.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveFixedAssetAccHead(FixedAssetCategory fixedAssetCategory) {

		String userName = commonService.getAuthUserName();

		fixedAssetCategory.setId(null);
		fixedAssetCategory.setCreatedBy(userName);
		fixedAssetCategory.setCreateDate(new Date());
		fixedAssetCategory.setActive(true);

		commonService.saveOrUpdateModelObjectToDB(fixedAssetCategory);

		return "redirect:/fixedAssets/accHeadForm.do";
	}

	@RequestMapping(value = "/writtenOffForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depreciationPolicyForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<FixedAssets> fixedAsset = fixedAssetCategoryService.getFixedAssetList();

		List<ItemMaster> itemMaster = new ArrayList<ItemMaster>();

		List<ItemMaster> itemMasterList = itemInventoryService.getInventoryItemList();

		for (ItemMaster items : itemMasterList) {
			if (items.getFixedAsset() != null && items.getFixedAsset().equalsIgnoreCase("1")) {

				itemMaster.add(items);

			}

		}

		model.put("fixedAssetList", fixedAsset);
		model.put("itemList", itemMaster);

		return new ModelAndView("fixedAsset/writtenOffForm", model);
	}
	
	 /*Comparator for sorting the list by item Name*/
	  public static Comparator<FixedAssetReceive> assetCom = new Comparator<FixedAssetReceive>() {

	public int compare(FixedAssetReceive f1, FixedAssetReceive f2) {
	   Date receiveDate1 = f1.getReceiveDate();
	   Date receiveDate2 = f2.getReceiveDate();

	   //ascending order
	   return receiveDate1.compareTo(receiveDate2);

	   //descending order
	   //return receiveDate2.compareTo(receiveDate1);
  }

	};

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/fixedAssetList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView fixedAssetList() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FixedAssetReceive> fixedAssetRcv = new ArrayList<FixedAssetReceive>();
		List<FixedAssets> fixedA = new ArrayList<FixedAssets>();

		List<FixedAssets> fixedAssets = fixedAssetCategoryService.getFixedAssetList();
		List<FixedAssetReceive> fixedAssetReceives = fixedAssetCategoryService.getFixedAssetReceiveList();

		for (FixedAssets fix : fixedAssets) {
			if (!fix.isWriteOff()) {

				fixedA.add(fix);
			}
		}
		for (FixedAssetReceive fixedAssetR : fixedAssetReceives) {
			if (!fixedAssetR.isWriteOff()) {
				fixedAssetR.setRemarks(TWO);
				fixedAssetRcv.add(fixedAssetR);
			}
		}
		for (FixedAssets fixedAsset : fixedA) {

			fixedAssetRcv.add(fixedAssetConvertToFixedAssetReceive(fixedAsset));
		}

		List<Departments> departments = departmentsService.listDepartments();

		model.put("departments", departments);
		//fixedAssetRcv.sort(Comparator.comparing(FixedAssetReceive::getReceiveDate));
		//Collections.sort(fixedAssetRcv, this.assetCom);
		model.put("fixedAssetReceives", fixedAssetRcv);

		return new ModelAndView("fixedAsset/fixedAssetListForm", model);
	}

	private FixedAssetReceive fixedAssetConvertToFixedAssetReceive(FixedAssets fixedAsset) {

		FixedAssetReceive cs = null;
		cs = new FixedAssetReceive();
		cs.setId(fixedAsset.getId());
		cs.setItemId(fixedAsset.getFixedAssetId());
		cs.setItemName(fixedAsset.getFixedAssetName());
		if(fixedAsset.getCompleteDate()!=null){
		cs.setReceiveDate(fixedAsset.getCompleteDate());}
		else{
		cs.setReceiveDate(fixedAsset.getPurchaseDate());}
		cs.setToDept(fixedAsset.getLocationId());
		cs.setQuantity(fixedAsset.getQuantity());
		cs.setTotalPrice(fixedAsset.getTotalPrice());
		cs.setFaRegKey(fixedAsset.getFaRegKey());
		cs.setProjectName(fixedAsset.getProjectName());
		cs.setSubCategory(fixedAsset.getSubCategory());
		cs.setRating(fixedAsset.getRating());
		cs.setLifeTime(fixedAsset.getLifeTime());
		cs.setDepreciationRate(fixedAsset.getDepreciationRate());
		cs.setSerialNumber(fixedAsset.getSerialNumber());
		cs.setRemarks(ONE);

		return cs;
	}

	@RequestMapping(value = "/saveWriteOff.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveWriteOff(CombindFixedAssetBean fixedAsset) {

		FixedAssetReceive fixedAssets = null;
		FixedAssets fixedAsset2 = new FixedAssets();
		;
		if (fixedAsset.getFlag().equalsIgnoreCase(TWO)) {

			/*
			 * fixedAssets = (FixedAssetReceive) commonService
			 * .getObjectListByTwoColumn("FixedAssetReceive", "itemId",
			 * fixedAsset.getAssetId(), "receiveDate",
			 * fixedAsset.getReceiveDate());
			 */
			fixedAssets = fixedAssetCategoryService.getFixedAssetReceive(fixedAsset.getAssetId(),
					fixedAsset.getReceiveDate());

			String userName = commonService.getAuthUserName();
			fixedAssets.setModifiedBy(userName);
			fixedAssets.setModifiedDate(new Date());
			fixedAssets.setActive(false);
			fixedAssets.setWriteOff(true);
			fixedAssets.setReasonWriteOff(fixedAsset.getReasonWriteOff());
			fixedAssets.setWriteOffValue(fixedAsset.getWriteOffValue());
			fixedAssets.setWriteOffNote(fixedAsset.getWriteOffNote());
			commonService.saveOrUpdateModelObjectToDB(fixedAssets);

		} else if (fixedAsset.getFlag().equalsIgnoreCase(ONE)) {

			// fixedAsset2 = (FixedAssets) commonService
			// .getObjectListByTwoColumn("FixedAssets", "fixedAssetId",
			// fixedAsset.getAssetId(), "receiveDate",
			// fixedAsset.getReceiveDate());
			fixedAsset2 = fixedAssetCategoryService.getFixedAsset(fixedAsset.getAssetId(), fixedAsset.getReceiveDate());

			String userName = commonService.getAuthUserName();
			fixedAsset2.setModifiedBy(userName);
			fixedAsset2.setModifyDate(new Date());
			fixedAsset2.setActive(false);
			fixedAsset2.setWriteOff(true);
			fixedAsset2.setReasonWriteOff(fixedAsset.getReasonWriteOff());
			fixedAsset2.setWriteOffValue(fixedAsset.getWriteOffValue());
			fixedAsset2.setReasonWriteOff(fixedAsset.getWriteOffNote());
			commonService.saveOrUpdateModelObjectToDB(fixedAsset2);
		}

		return "redirect:/fixedAssets/fixedAssetList.do";
	}

	@RequestMapping(value = "/saveDisposal.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveDisposal(CombindFixedAssetBean fixedAsset) {

		String userName = commonService.getAuthUserName();

		FixedAssetReceive fixedAssets = null;
		FixedAssets fixedAsset2 = null;
		if (fixedAsset.getFlag().equalsIgnoreCase(TWO)) {

			fixedAssets = fixedAssetCategoryService.getFixedAssetReceive(fixedAsset.getAssetId(),
					fixedAsset.getFaRegKey());

			fixedAssets.setModifiedBy(userName);
			fixedAssets.setModifiedDate(new Date());
			fixedAssets.setActive(false);
			fixedAssets.setWriteOff(true);
			fixedAssets.setReasonWriteOff(fixedAsset.getReasonWriteOff());
			fixedAssets.setWriteOffValue(fixedAsset.getWriteOffValue());
			fixedAssets.setWriteOffNote(fixedAsset.getWriteOffNote());
			commonService.saveOrUpdateModelObjectToDB(fixedAssets);

		} else if (fixedAsset.getFlag().equalsIgnoreCase(ONE)) {

			fixedAsset2 = fixedAssetCategoryService.getFixedAsset(fixedAsset.getAssetId(), fixedAsset.getFaRegKey());

			fixedAsset2.setModifiedBy(userName);
			fixedAsset2.setModifyDate(new Date());
			fixedAsset2.setActive(false);
			fixedAsset2.setWriteOff(true);
			fixedAsset2.setReasonWriteOff(fixedAsset.getReasonWriteOff());
			fixedAsset2.setWriteOffValue(fixedAsset.getWriteOffValue());
			fixedAsset2.setReasonWriteOff(fixedAsset.getWriteOffNote());
			commonService.saveOrUpdateModelObjectToDB(fixedAsset2);
		}

		DisposalAssets da = new DisposalAssets();
		/*
		 * Double disposalValue=
		 * Double.valueOf(request.getParameter("disposalValue")); String
		 * reasonOfDisposal= request.getParameter("reasonOfDisposal");
		 */
		da.setId(null);
		da.setAssetCode(fixedAsset.getAssetId());
		da.setActive(true);
		da.setCreatedBy(userName);
		da.setCreateDate(new Date());
		da.setDisposalDate(new Date());
		da.setReasonOfDisposal(fixedAsset.getReasonOfDisposal());
		da.setDisposalValue(fixedAsset.getDisposalValue());
		da.setSaleValue(fixedAsset.getSaleValue());
		da.setGainOrLoss(fixedAsset.getGainOrLoss());
		da.setRemarks(fixedAsset.getDispRemarks());
		commonService.saveOrUpdateModelObjectToDB(da);

		return "redirect:/fixedAssets/fixedAssetList.do";
	}

	@RequestMapping(value = "/updateLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String updateLocation(CombindFixedAssetBean fixedAsset) {

		String userName = commonService.getAuthUserName();

		FixedAssetReceive fixedAssets = null;
		FixedAssets fixedAsset2 = null;
		if (fixedAsset.getFlag().equalsIgnoreCase(TWO)) {

			fixedAssets = fixedAssetCategoryService.getFixedAssetReceive(fixedAsset.getAssetId(),
					fixedAsset.getFaRegKey());

			fixedAssets.setModifiedBy(userName);
			fixedAssets.setModifiedDate(new Date());
			fixedAssets.setToDept(fixedAsset.getLocationId());
			commonService.saveOrUpdateModelObjectToDB(fixedAssets);

		} else if (fixedAsset.getFlag().equalsIgnoreCase(ONE)) {

			fixedAsset2 = fixedAssetCategoryService.getFixedAsset(fixedAsset.getAssetId(), fixedAsset.getFaRegKey());

			fixedAsset2.setModifiedBy(userName);
			fixedAsset2.setModifyDate(new Date());
			fixedAsset2
					.setLocationId(fixedAsset.getLocationId());
			commonService.saveOrUpdateModelObjectToDB(fixedAsset2);
		}

		return "redirect:/fixedAssets/fixedAssetList.do";
	}

	@RequestMapping(value = "/depreciationCalc.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depreciationCalc() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FixedAssetReceive> fixedAssetRcv = new ArrayList<FixedAssetReceive>();
		List<FixedAssets> fixedA = new ArrayList<FixedAssets>();

		List<FixedAssets> fixedAssets = fixedAssetCategoryService.getFixedAssetList();
		List<FixedAssetReceive> fixedAssetReceives = fixedAssetCategoryService.getFixedAssetReceiveList();

		for (FixedAssets fix : fixedAssets) {
			if (!fix.isWriteOff()) {

				fixedA.add(fix);
			}
		}
		for (FixedAssetReceive fixedAssetR : fixedAssetReceives) {
			if (!fixedAssetR.isWriteOff()) {
				fixedAssetR.setRemarks(TWO);
				fixedAssetRcv.add(fixedAssetR);
			}
		}
		for (FixedAssets fixedAsset : fixedA) {

			fixedAssetRcv.add(fixedAssetConvertToFixedAssetReceive(fixedAsset));
		}

		List<Departments> departments = departmentsService.listDepartments();

		List<FixedAssetReceive> uniqueList = new ArrayList<FixedAssetReceive>();
		for (int i = 0; i < fixedAssetRcv.size(); i++) {
			if (uniqueList.size() > 0) {
				/*if(uniqueList.contains(fixedAssetRcv.get(i).getItemId())) {
					continue;
				} else {
					uniqueList.add(fixedAssetRcv.get(i));
				}*/
				boolean flag=false;
				for(FixedAssetReceive fa:uniqueList){
					if(!fa.getItemId().matches(fixedAssetRcv.get(i).getItemId())){
						
						flag=true;
						//break;
					}else{flag=false;}
				}
					if(flag){
				uniqueList.add(fixedAssetRcv.get(i));}

			} else {

				uniqueList.add(fixedAssetRcv.get(i));
			}
		}

		model.put("departments", departments);

		model.put("fixedAssetReceives", uniqueList);

		return new ModelAndView("fixedAsset/assetListForm", model);
	}

	@RequestMapping(value = "/getDepreciation.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getDepreciation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String assetId = request.getParameter("assetId").trim();
		String purchaseDate =null;
		double purchasePrice=0d;
		double depUptoLastYear=0d;
		double depCurrentYear=0d;
		double cuDepreciation=0d;
		double writtenDownValue=0d;
		if(request.getParameter("purchaseDate")!=null){
		 purchaseDate = request.getParameter("purchaseDate").trim();
		}
		if(request.getParameter("purchasePrice")!=null){
		 purchasePrice = Double.parseDouble(request.getParameter("purchasePrice").trim());
		}
		//String strCurDate = getCurrentDate(new Date());
		//
		// Date d=setCurrentDate(date);
		DepreciationSummery ds = fixedAssetCategoryService.getDepreciation(assetId, purchaseDate);
		if (ds != null) {
			// purchasePrice=ds.getPurchasePrice();
			 depUptoLastYear=ds.getDepUptoLastYear();
			 depCurrentYear=ds.getDepCurrentYear();
			 cuDepreciation=ds.getCuDepreciation();
			 writtenDownValue=ds.getWrittenDownValue();
		}else{
			 cuDepreciation=purchasePrice;
			 
			
		}
			return purchasePrice + "," + depUptoLastYear + "," + depCurrentYear + "," + cuDepreciation + "," + writtenDownValue;
		
	}

	@RequestMapping(value = "/getDepr.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getDepr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String itemId = request.getParameter("itemId").trim();
		//String purchaseDate = request.getParameter("purchaseDate").trim();
		//String strCurDate = getCurrentDate(new Date());
		// Date d=setCurrentDate(date);
		//DepreciationSummery ds = fixedAssetCategoryService.getDepreciation(assetId, purchaseDate, strCurDate);
		ItemMaster itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
				"ItemMaster", "itemId", itemId);
		
		if (itemMaster != null) {

			return itemMaster.getLifeTime() + "," + itemMaster.getDepreciationRate();
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/getAdditionLength.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getAdditionLength(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String purchaseDate = request.getParameter("purchaseDate");
		String distributionLine = request.getParameter("distributionLine");
		String disLineDivision = request.getParameter("disLineDivision");
		
	FixedAssets fa=fixedAssetCategoryService.getAdditionLengthBySubCategory(distributionLine, disLineDivision, purchaseDate);
		if (fa != null) {

			return fa.getAdditionLength().toString();
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/getItemData.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getItemData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String itemName = request.getParameter("itemName");
		ItemMaster itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
				"ItemMaster", "itemName", itemName);
		
		if (itemMaster != null) {

			return itemMaster.getLifeTime() + "," + itemMaster.getDepreciationRate();
		} else {
			return "";
		}
	}

	
	@RequestMapping(value = "/depShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depShow(FixedAssetReceive fixedAssetReceive) {

		Map<String, Object> model = new HashMap<String, Object>();

		String strCurDate = getCurrentDate(new Date());
		String itemId = fixedAssetReceive.getItemId();
		List<DepreciationSummery> ds = fixedAssetCategoryService.getDepreciationList(itemId, strCurDate);

		model.put("itemName", itemInventoryService.getItemNameByItemCode(itemId));
		model.put("depreciationSummery", ds);
		return new ModelAndView("fixedAsset/depreciationShowForAssets", model);
	}
	
	@RequestMapping(value = "/updateFixedAsset.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView updateFixedAsset() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FixedAssetReceive> fixedAssetRcv = new ArrayList<FixedAssetReceive>();
		List<FixedAssets> fixedA = new ArrayList<FixedAssets>();

		List<FixedAssets> fixedAssets = fixedAssetCategoryService.getFixedAssetList();
		List<FixedAssetReceive> fixedAssetReceives = fixedAssetCategoryService.getFixedAssetReceiveList();

		for (FixedAssets fix : fixedAssets) {
			if (!fix.isWriteOff()) {

				fixedA.add(fix);
			}
		}
		for (FixedAssetReceive fixedAssetR : fixedAssetReceives) {
			if (!fixedAssetR.isWriteOff()) {
				fixedAssetR.setRemarks(TWO);
				fixedAssetRcv.add(fixedAssetR);
			}
		}
		for (FixedAssets fixedAsset : fixedA) {

			fixedAssetRcv.add(fixedAssetConvertToFixedAssetReceive(fixedAsset));
		}

		List<Departments> departments = departmentsService.listDepartments();

		List<FixedAssetReceive> uniqueList = new ArrayList<FixedAssetReceive>();
		for (int i = 0; i < fixedAssetRcv.size(); i++) {
			if (uniqueList.size() > 0) {
				/*if(uniqueList.contains(fixedAssetRcv.get(i).getItemId())) {
					continue;
				} else {
					uniqueList.add(fixedAssetRcv.get(i));
				}*/
				boolean flag=false;
				for(FixedAssetReceive fa:uniqueList){
					if(!fa.getItemId().matches(fixedAssetRcv.get(i).getItemId())){
						
						flag=true;
						//break;
					}else{flag=false;}
				}
					if(flag){
				uniqueList.add(fixedAssetRcv.get(i));}

			} else {

				uniqueList.add(fixedAssetRcv.get(i));
			}
		}

		model.put("departments", departments);

		model.put("fixedAssetReceives", uniqueList);

		return new ModelAndView("fixedAsset/updateFixedAssetForm", model);
	}
	@RequestMapping(value = "/editDepRate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String editDepRate(ItemMaster items) {

		//String userName = commonService.getAuthUserName();
		ItemMaster itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
				"ItemMaster", "itemId", items.getItemId());
		itemMaster.setLifeTime(items.getLifeTime());
		itemMaster.setDepreciationRate(items.getDepreciationRate());
		commonService.saveOrUpdateModelObjectToDB(itemMaster);
		
		//List<FixedAssets> fixedAssetList = (List<FixedAssets>)(Object) commonService
			//	.getObjectListByAnyColumn("FixedAssets", "fixedAssetId", items.getItemId());
		//for(FixedAssets fixedAsset:fixedAssetList){
			//fixedAsset.setLifeTime(items.getLifeTime());
			//fixedAsset.setDepreciationRate(items.getDepreciationRate());
			//commonService.saveOrUpdateModelObjectToDB(fixedAsset);
//}
		//List<DepreciationSummery> depreciationSummeryList = (List<DepreciationSummery>)(Object) commonService
			//	.getObjectListByAnyColumn("DepreciationSummery", "assetId", items.getItemId().toString());
		String strCurDate = getCurrentDate(new Date());
		List<DepreciationSummery> depreciationSummeryList = fixedAssetCategoryService.getDepreciationList(items.getItemId(), strCurDate);

		for(DepreciationSummery ds:depreciationSummeryList){
			if(ds.getWrittenDownValue()==0){continue;}//		if(Integer.parseInt(items.getLifeTime()) > Integer.parseInt(ds.getLifeTime()))
			Double depr=0d;
			Double wdv=ds.getWrittenDownValue();
			Double lastYearDep=ds.getCuDepreciation();

			List<DepreciationSummery> depSummeryList=fixedAssetCategoryService.getDepSummListForUpdate(ds.getAssetId(), getCurrentDate(ds.getDepStartDate()), getCurrentDate(ds.getPurchaseDate()));
			for(DepreciationSummery depSm : depSummeryList){
				
				depr=depSm.getPurchasePrice()*items.getDepreciationRate()/100;

				depSm.setDepCurrentYear(depr);
				depSm.setDepUptoLastYear(lastYearDep);
				depSm.setCuDepreciation(lastYearDep + depr);
				depSm.setWrittenDownValue(wdv-depr);
				depSm.setDepreciationRate(items.getDepreciationRate());
				commonService.saveOrUpdateModelObjectToDB(depSm);
				lastYearDep=depSm.getCuDepreciation();
				wdv=depSm.getWrittenDownValue();
			}
		}		
		return "redirect:/fixedAssets/fixedAssetList.do";
	}
	@RequestMapping(value = "/getItemName", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<ItemMaster> getItems(
			@RequestParam String itemName, HttpServletResponse response) {
		return simulateSearchItemName(itemName);

	}

	private List<ItemMaster> simulateSearchItemName(String itemName) {

		List<ItemMaster> itemMasterList = new ArrayList<ItemMaster>();
		List<ItemMaster> itemMasterListData = itemInventoryService
				.getInventoryItemList();
		// iterate a list and filter by tagName
		for (ItemMaster item : itemMasterListData) {
			if (item.getItemName().toLowerCase()
					.contains(itemName.toLowerCase())) {
				itemMasterList.add(item);
			}
		}

		return itemMasterList;
	}
	
	@RequestMapping(value = "/detailShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView detailShow(FixedAssetReceiveBean fixedAssetReceive) {

		Map<String, Object> model = new HashMap<String, Object>();

		String strCurDate = getCurrentDate(new Date());
		String itemId = fixedAssetReceive.getItemId();
		FixedAssetReceive fixedAsset1 = null;
		FixedAssets fixedAsset2 = null;
		if (fixedAssetReceive.getRemarks().equalsIgnoreCase(TWO)) {

			fixedAsset1 = fixedAssetCategoryService.getFixedAssetReceive(fixedAssetReceive.getItemId(),
					fixedAssetReceive.getFaRegKey());
			model.put("fixedAsset1", fixedAsset1);
			model.put("receiveDate", fixedAsset1.getReceiveDate());
			model.put("quantity", fixedAsset1.getQuantity());
			model.put("totalPrice", fixedAsset1.getTotalPrice());
			model.put("serialNumber", fixedAsset1.getSerialNumber());
			model.put("locationName", fixedAsset1.getToDept());
			
		}else if (fixedAssetReceive.getRemarks().equalsIgnoreCase(ONE)) {
			fixedAsset2 = fixedAssetCategoryService.getFixedAsset(fixedAssetReceive.getItemId(),
					fixedAssetReceive.getFaRegKey());
			model.put("fixedAsset2", fixedAsset2);
			if(fixedAsset2.getPurchaseDate()!=null){
			model.put("receiveDate", fixedAsset2.getPurchaseDate());}
			else{model.put("receiveDate", fixedAsset2.getCompleteDate());}
			model.put("quantity", fixedAsset2.getQuantity());
			model.put("totalPrice", fixedAsset2.getTotalPrice());
			model.put("serialNumber", fixedAsset2.getSerialNumber());
			model.put("locationName", fixedAsset2.getLocationId());
		}
		
		DepreciationSummery ds = fixedAssetCategoryService.getDepreciation(fixedAssetReceive.getItemId(),
					fixedAssetReceive.getReceiveDate());
		List<DepreciationSummery> dsList = new ArrayList<DepreciationSummery>();
		if(fixedAssetReceive.getFaRegKey()!=null && fixedAssetReceive.getFaRegKey()!=""){
			dsList = fixedAssetCategoryService.getDepreciationListSchedulled(fixedAssetReceive.getItemId(),
						fixedAssetReceive.getFaRegKey(), ONE);
			}else{
				
				dsList = fixedAssetCategoryService.getDepreciationListSchedulled(fixedAssetReceive.getItemId(),
							fixedAssetReceive.getReceiveDate(), TWO);
			}
		
		//List<DepreciationSummery> dsList = fixedAssetCategoryService.getDepreciationListSchedulled(fixedAssetReceive.getItemId(),
				//fixedAssetReceive.getReceiveDate());
		
		ItemMaster itemMaster = (ItemMaster) commonService.getAnObjectByAnyUniqueColumn(
				"ItemMaster", "itemId", fixedAssetReceive.getItemId());
		
		model.put("itemName", itemInventoryService.getItemNameByItemCode(itemId));
		model.put("itemId", itemId);
		model.put("depreciationSummery", ds);
		model.put("depreciationSummeryList", dsList);
		model.put("itemMaster", itemMaster);
		return new ModelAndView("fixedAsset/assetDetails", model);
	}
	
	@RequestMapping(value = "/getLocationName", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Departments> getLocationName(
			@RequestParam String locationName, HttpServletResponse response) {
		return simulateSearchLocationName(locationName);

	}

	private List<Departments> simulateSearchLocationName(String locationName) {

		List<Departments> itemMasterList = new ArrayList<Departments>();
		List<Departments> itemMasterListData = departmentsService.listDepartments();
		// iterate a list and filter by tagName
		for (Departments item : itemMasterListData) {
			if (item.getDeptName().toLowerCase()
					.contains(locationName.toLowerCase())) {
				itemMasterList.add(item);
			}
		}

		return itemMasterList;
	}
	
	@RequestMapping(value = "/categoryWiseDetailsAssetReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView categoryWiseDetailsAssetReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");
		model.put("itemCategoryList", itemCategoryList);
		return new ModelAndView("report/categoryWiseDetailsAssetReport", model);
	}
	
	@RequestMapping(value = "/depreciationSchedule.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depreciationSchedule() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FixedAssets> fixedAssets = fixedAssetCategoryService.getSLFixedAssetList();
		
				model.put("fixedAssetReceives", fixedAssets);

		return new ModelAndView("fixedAsset/assetDepreciationScheduleForm", model);
	}

	
	@RequestMapping(value = "/depreciationScheduleReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depreciationScheduleReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ItemCategory> itemCategoryList = (List<ItemCategory>) (Object) commonService
				.getAllObjectList("ItemCategory");
		model.put("itemCategoryList", itemCategoryList);
		return new ModelAndView("report/depreciationScheduleReport", model);
	}

	
	@RequestMapping(value = "/getSerialNo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<FixedAssets> getSerialNo(
			@RequestParam String serialNo, HttpServletResponse response) {
		return simulateSearchSerialNo(serialNo);

	}

	private List<FixedAssets> simulateSearchSerialNo(String serialNo) {

		List<FixedAssets> itemMasterList = new ArrayList<FixedAssets>();
		List<FixedAssets> listData = fixedAssetCategoryService.getSLFixedAssetList();
		// iterate a list and filter by tagName
		for (FixedAssets item : listData) {
			if (item.getSerialNumber().toLowerCase()
					.contains(serialNo.toLowerCase())) {
				itemMasterList.add(item);
			}
		}

		return itemMasterList;
	}
	
	@RequestMapping(value = "/depSchedule.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView depScheduleShow(FixedAssetsBean fixedAssetReceive) {

		Map<String, Object> model = new HashMap<String, Object>();

		String itemId = fixedAssetReceive.getFixedAssetId();
		String serialNumber = fixedAssetReceive.getSerialNumber();
		
		List<DepreciationSummery> dsList = fixedAssetCategoryService.getDepreciationListSchedulled(itemId,
						fixedAssetReceive.getFaRegKey(), ONE);
			
		model.put("itemName", itemInventoryService.getItemNameByItemCode(itemId));
		model.put("itemId", itemId);
		model.put("serialNumber", serialNumber);
		model.put("depreciationSummeryList", dsList);
		return new ModelAndView("fixedAsset/depScheduleShow", model);
	}


}
