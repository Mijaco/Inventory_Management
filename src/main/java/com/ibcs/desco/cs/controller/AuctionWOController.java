package com.ibcs.desco.cs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.AuctionWOEntryMstDtl;
import com.ibcs.desco.cs.model.AuctionProcessMst;
import com.ibcs.desco.cs.model.AuctionWOEntryDtl;
import com.ibcs.desco.cs.model.AuctionWOEntryMst;
import com.ibcs.desco.cs.model.CondemnDtl;
import com.ibcs.desco.cs.model.CondemnMst;
import com.ibcs.desco.cs.model.WorkOrderMst;
import com.ibcs.desco.cs.service.AuctionService;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;

/*
 * Author: Ihteshamul Alam
 * Programmer, IBCS
 */

@Controller
public class AuctionWOController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	AuctionService auctionService;

	@Value("${desco.project.rootPath}")
	private String descoRootPath;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/auctionWoForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionWoForm(String id) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CondemnMst cMst = (CondemnMst) commonService
					.getAnObjectByAnyUniqueColumn("CondemnMst", "id", id);
			List<CondemnDtl> margedCondemnDtlList = new ArrayList<CondemnDtl>();
			Map<Integer, ItemMaster> distinctItemMap = new HashMap<Integer, ItemMaster>();
			List<CondemnDtl> condemnDtlList = (List<CondemnDtl>) (Object) commonService
					.getObjectListByAnyColumn("CondemnDtl", "condemnMst.id", id);

			for (CondemnDtl d : condemnDtlList) {
				distinctItemMap.put(d.getItemMaster().getId(),
						d.getItemMaster());
			}
			for (Integer k : distinctItemMap.keySet()) {
				CondemnDtl temp = new CondemnDtl();
				Double total = 0.0;
				for (CondemnDtl cd : condemnDtlList) {
					if (cd.getItemMaster().getId().equals(k)) {
						total += cd.getCondemnQty();
					}
				}
				temp.setItemMaster(distinctItemMap.get(k));
				temp.setCondemnQty(total);
				margedCondemnDtlList.add(temp);
			}

			model.put("condemnMst", cMst);
			model.put("condemnDtlList", margedCondemnDtlList);
			return new ModelAndView("centralStore/auction/workOrderForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}

	@RequestMapping(value = "/ac/checkWorkOrder.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkWorkOrder(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String result = "";
		String toJson = "";
		AuctionWOEntryMst workOrderMstDb = new AuctionWOEntryMst();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuctionWOEntryMst workOrderMst = gson.fromJson(json,
					AuctionWOEntryMst.class);

			if (workOrderMst.getWorkOrderNo().equals("")) {
				result = "unsuccess";
			} else {
				workOrderMstDb = (AuctionWOEntryMst) commonService
						.getAnObjectByAnyUniqueColumn("AuctionWOEntryMst",
								"workOrderNo", workOrderMst.getWorkOrderNo());
				if (workOrderMstDb == null) {
					result = "success";
				} else {
					result = "unsuccess";
				}
			}

			toJson = ow.writeValueAsString(result);

		} else {
			Thread.sleep(5000);
		}
		return toJson;
	}

	@RequestMapping(value = "/ac/auctionWOSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String workOrderSave(AuctionWOEntryMstDtl woMstDtl,
			@RequestParam("referenceDoc") MultipartFile file) {
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		// String filePath = this.saveFileToDrive(file, "work_order");
		String filePath = commonService.saveFileToDrive(file, descoRootPath,
				"work_order");

		CondemnMst condemnMst = (CondemnMst) commonService
				.getAnObjectByAnyUniqueColumn("CondemnMst", "id",
						woMstDtl.getCondemnMstId());

		AuctionWOEntryMst workOrderMst = new AuctionWOEntryMst();

		workOrderMst.setId(null);
		workOrderMst.setActive(true);
		workOrderMst.setCreatedBy(userName);
		workOrderMst.setCreatedDate(now);

		// workOrderMst.setKhathId(woMstDtl.getKhathId());
		workOrderMst.setCondemnMst(condemnMst);
		workOrderMst.setAuctionName(woMstDtl.getAuctionName());
		workOrderMst.setWorkOrderNo(woMstDtl.getWorkOrderNo());
		workOrderMst.setContractDate(woMstDtl.getContractDate());
		workOrderMst.setSupplierName(woMstDtl.getSupplierName());
		workOrderMst.setReferenceDocs(filePath);

		boolean result = this.saveAuctionWorkOrderDtl(workOrderMst, woMstDtl,
				userName, now);

		
		if (result) {
			AuctionProcessMst apMstDb=condemnMst.getAuctionProcessMst();
			apMstDb.setAuction_wo_flag("1");
			commonService.saveOrUpdateModelObjectToDB(apMstDb);
			
			return "redirect:/ac/wolist.do";
		} else {
			return "redirect:/workOrder/getForm.do";
		}
	}

	// Added by ashid
	@SuppressWarnings("unchecked")
	private boolean saveAuctionWorkOrderDtl(AuctionWOEntryMst workOrderMst,
			AuctionWOEntryMstDtl woMstDtl, String userName, Date now) {

		List<String> itemMstIdList = null;
		List<Double> costList = null;

		if (woMstDtl.getPk() != null) {
			itemMstIdList = woMstDtl.getPk();
		}
		if (woMstDtl.getCost() != null) {
			costList = woMstDtl.getCost();
		}

		if (itemMstIdList.size() > 0) {
			commonService.saveOrUpdateModelObjectToDB(workOrderMst);
			CondemnMst cMst = workOrderMst.getCondemnMst();

			List<CondemnDtl> condemnDtlList = (List<CondemnDtl>) (Object) commonService
					.getObjectListByAnyColumn("CondemnDtl", "condemnMst.id",
							cMst.getId().toString());

			Map<Integer, Double> itemMstQtyMap = new HashMap<Integer, Double>();

			for (int i = 0; i < itemMstIdList.size(); i++) {
				int item_pk = Integer.parseInt(itemMstIdList.get(i));
				itemMstQtyMap.put(item_pk, costList.get(i));
			}

			for (CondemnDtl d : condemnDtlList) {
				AuctionWOEntryDtl dtl = new AuctionWOEntryDtl();
				dtl.setId(null);
				dtl.setCreatedBy(userName);
				dtl.setCreatedDate(now);

				dtl.setItemMaster(d.getItemMaster());
				dtl.setAuctionWOEntryMst(workOrderMst);
				dtl.setDepartments(d.getDepartments());

				dtl.setAuctionQty(d.getCondemnQty());
				dtl.setRemainingQty(d.getCondemnQty());
				dtl.setCost(itemMstQtyMap.get(d.getItemMaster().getId()));

				commonService.saveOrUpdateModelObjectToDB(dtl);
			}
		}

		return true;
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/ac/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, WorkOrderMst woMst)
			throws Exception {

		HttpServletResponse response = httpServletResponse;

		String imagePath = woMst.getReferenceDoc();
		File file = new File(imagePath);
		FileInputStream fis = new FileInputStream(file);
		InputStream in = new BufferedInputStream(fis);

		String extension = "";
		int i = imagePath.lastIndexOf('.');

		if (i > 0) {
			extension = imagePath.substring(i + 1);
		}

		String agent = request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") != -1) {
			imagePath = URLEncoder.encode("download." + extension, "UTF8");

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="
					+ imagePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			imagePath = MimeUtility.encodeText("download." + extension, "UTF8",
					"B");

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\""
					+ imagePath + "\"");
		}

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		byte by[] = new byte[32768];
		int index = in.read(by, 0, 32768);
		while (index != -1) {
			out.write(by, 0, index);
			index = in.read(by, 0, 32768);
		}
		out.flush();

		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/wolist.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView workOrderList(AuctionWOEntryMst woMstDtl) {

		List<AuctionWOEntryMst> workOrderMstList = (List<AuctionWOEntryMst>) (Object) commonService
				.getAllObjectList("AuctionWOEntryMst");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workOrderMstList", workOrderMstList);

		return new ModelAndView("centralStore/auction/auctionWorkOrderList",
				model);
	}
	
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/finalClose.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String closeAuction(AuctionProcessMst wpm) {

		Integer id = wpm.getId();

		String userName = commonService.getAuthUserName();

//		AuthUser authUser = userService.getAuthUserByUserId(userName);
		AuctionProcessMst auctionProcessMst = (AuctionProcessMst) commonService.getAnObjectByAnyUniqueColumn("AuctionProcessMst", "id", id.toString());
		if(auctionProcessMst != null){
			auctionProcessMst.setActive(false);
			auctionProcessMst.setModifiedBy(userName);
			auctionProcessMst.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(auctionProcessMst);
		}
		
		return "redirect:/ac/wolist.do";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/show.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showWorkOrder(AuctionWOEntryMst woMst) {

		Integer id = woMst.getId();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		AuctionWOEntryMst workOrderMstDb = (AuctionWOEntryMst) commonService
				.getAnObjectByAnyUniqueColumn("AuctionWOEntryMst", "id", id
						+ "");

		/*
		 * List<CSProcItemRcvMst> rcvItemList = (List<CSProcItemRcvMst>)
		 * (Object) commonService .getObjectListByAnyColumn("CSProcItemRcvMst",
		 * "contractNo", workOrderMstDb.getWorkOrderNo());
		 */

		List<AuctionWOEntryDtl> workOrderDtlList = (List<AuctionWOEntryDtl>) (Object) commonService
				.getObjectListByAnyColumn("AuctionWOEntryDtl",
						"auctionWOEntryMst.id", workOrderMstDb.getId()
								.toString());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workOrderMst", workOrderMstDb);
		model.put("workOrderDtlList", workOrderDtlList);
		model.put("department", department);
		
		return new ModelAndView("centralStore/auction/workOrderShow", model);
	}

	/*
	 * @RequestMapping(value = "/ac/condemnMaterials.do", method =
	 * RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView condemnMaterials()
	 * {
	 * 
	 * Map<String, Object> model = new HashMap<String, Object>();
	 * 
	 * try { String userName = commonService.getAuthUserName();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * Departments department = departmentsService
	 * .getDepartmentByDeptId(authUser.getDeptId());
	 * 
	 * @SuppressWarnings("unchecked") List<AuctionWOEntryMst> workOrderMstList =
	 * (List<AuctionWOEntryMst>) (Object) commonService
	 * .getAllObjectList("AuctionWOEntryMst");
	 * 
	 * model.put("workOrderMstList", workOrderMstList); model.put("department",
	 * department); model.put("deptAddress", department.getAddress() + ", " +
	 * department.getContactNo());
	 * 
	 * return new ModelAndView("centralStore/auction/condemnMaterials", model);
	 * } catch (Exception e) { e.printStackTrace(); model.put("errorMsg",
	 * e.getMessage()); return new ModelAndView("centralStore/error", model); }
	 * }
	 * 
	 * @RequestMapping(value = "/ac/condemnMaterialShow.do", method =
	 * RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView
	 * condemnMaterialShowGet(AuctionWOEntryMst woMst) { return
	 * this.condemnMaterialShow(woMst); }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/ac/condemnMaterialShow.do", method =
	 * RequestMethod.POST)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView
	 * condemnMaterialShow(AuctionWOEntryMst woMst) {
	 * 
	 * Integer id = woMst.getId();
	 * 
	 * String userName = commonService.getAuthUserName();
	 * 
	 * AuthUser authUser = userService.getAuthUserByUserId(userName);
	 * 
	 * Departments department = departmentsService
	 * .getDepartmentByDeptId(authUser.getDeptId());
	 * 
	 * AuctionWOEntryMst workOrderMstDb = (AuctionWOEntryMst) commonService
	 * .getAnObjectByAnyUniqueColumn("AuctionWOEntryMst", "id", id + "");
	 * 
	 * 
	 * List<CSProcItemRcvMst> rcvItemList = (List<CSProcItemRcvMst>) (Object)
	 * commonService .getObjectListByAnyColumn("CSProcItemRcvMst", "contractNo",
	 * workOrderMstDb.getWorkOrderNo());
	 * 
	 * 
	 * List<AuctionWOEntryDtl> workOrderDtlList = (List<AuctionWOEntryDtl>)
	 * (Object) commonService .getObjectListByAnyColumn("AuctionWOEntryDtl",
	 * "auctionWOEntryMst.id", workOrderMstDb.getId() .toString());
	 * 
	 * int khathID = workOrderMstDb.getKhathId(); DescoKhath descoKhath =
	 * (DescoKhath) commonService .getAnObjectByAnyUniqueColumn("DescoKhath",
	 * "id", khathID + "");
	 * 
	 * Map<String, Object> model = new HashMap<String, Object>();
	 * model.put("workOrderMst", workOrderMstDb); model.put("workOrderDtlList",
	 * workOrderDtlList); model.put("khathInfo", descoKhath);
	 * model.put("deptName", department.getDeptName()); model.put("deptAddress",
	 * department.getAddress() + ", " + department.getContactNo()); return new
	 * ModelAndView("centralStore/auction/condemnMaterialsShow", model); }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/ac/loadItemCodebyWoNo.do", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String loadItemCodebyWoNo(@RequestBody String json)
	 * throws Exception { Gson gson = new GsonBuilder().create(); Boolean isJson
	 * = commonService.isJSONValid(json); String toJson = ""; ObjectWriter ow =
	 * new ObjectMapper().writer() .withDefaultPrettyPrinter(); if (isJson) {
	 * AuctionWOEntryDtl auctionWODtl = gson.fromJson(json,
	 * AuctionWOEntryDtl.class); Integer id = auctionWODtl.getId();
	 * 
	 * List<AuctionWOEntryDtl> auctionWoDtlDb = (List<AuctionWOEntryDtl>)
	 * (Object) commonService .getObjectListByAnyColumn("AuctionWOEntryDtl",
	 * "auctionWOEntryMst.id", id.toString());
	 * 
	 * toJson = ow.writeValueAsString(auctionWoDtlDb); } else {
	 * Thread.sleep(1000); toJson = ow.writeValueAsString("fail"); } return
	 * toJson; }
	 * 
	 * @RequestMapping(value = "/ac/loadItemInfo.do", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String loadItemInfo(@RequestBody String json) throws
	 * Exception { Gson gson = new GsonBuilder().create(); Boolean isJson =
	 * commonService.isJSONValid(json); String toJson = ""; ObjectWriter ow =
	 * new ObjectMapper().writer() .withDefaultPrettyPrinter(); if (isJson) {
	 * AuctionWOEntryDtl auctionWODtl = gson.fromJson(json,
	 * AuctionWOEntryDtl.class); Integer id = auctionWODtl.getId(); String uom =
	 * auctionWODtl.getUom();
	 * 
	 * Departments dept = (Departments) commonService
	 * .getAnObjectByAnyUniqueColumn("Departments", "deptId", uom);
	 * 
	 * AuctionWOEntryDtl auctionWoDtlDb = (AuctionWOEntryDtl) commonService
	 * .getAnObjectByAnyUniqueColumn("AuctionWOEntryDtl", "id", id.toString());
	 * 
	 * toJson = ow.writeValueAsString(auctionWoDtlDb); } else {
	 * Thread.sleep(1000); toJson = ow.writeValueAsString("fail"); } return
	 * toJson; }
	 * 
	 * @RequestMapping(value = "/ac/saveAuctionMaterials.do", method =
	 * RequestMethod.POST)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView
	 * saveAuctionMaterials( AuctionMaterialsDelivery auctionMaterialsDelivery)
	 * { Map<String, Object> model = new HashMap<String, Object>(); try {
	 * 
	 * List<Integer> idList = null; Integer mstId = 0;
	 * 
	 * idList = auctionMaterialsDelivery.getId();
	 * 
	 * for (int i = 0; i < idList.size(); i++) { AuctionWOEntryDtl aWoEntry =
	 * (AuctionWOEntryDtl) commonService
	 * .getAnObjectByAnyUniqueColumn("AuctionWOEntryDtl", "id",
	 * idList.get(i).toString());
	 * 
	 * mstId = aWoEntry.getAuctionWOEntryMst().getId();
	 * 
	 * if (aWoEntry != null) { Double receivedQty = aWoEntry.getReceivedQty();
	 * 
	 * aWoEntry.setReceivedQty(receivedQty);
	 * aWoEntry.setModifiedBy(commonService.getAuthUserName());
	 * aWoEntry.setModifiedDate(new Date()); }
	 * 
	 * commonService.saveOrUpdateModelObjectToDB(aWoEntry); }
	 * 
	 * return new ModelAndView("redirect:/ac/condemnMaterialShow.do?id=" +
	 * mstId); } catch (Exception e) { e.printStackTrace();
	 * model.put("errorMsg", e.getMessage()); return new
	 * ModelAndView("centralStore/error"); } }
	 */
}
