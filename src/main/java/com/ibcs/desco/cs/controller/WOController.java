package com.ibcs.desco.cs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.springframework.context.annotation.PropertySource;
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
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.bean.WorkOrderMstDtl;
import com.ibcs.desco.cs.model.CSProcItemRcvMst;
import com.ibcs.desco.cs.model.WorkOrderDtl;
import com.ibcs.desco.cs.model.WorkOrderMst;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
@PropertySource("classpath:common.properties")
public class WOController extends Constrants {

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

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workOrder/getForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getWorkOrderForm() {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhathList", descoKhathList);
		model.put("categoryList", categoryList);
		model.put("deptName", department.getDeptName());

		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("centralStore/workOrderForm", model);
	}

	@RequestMapping(value = "/workOrder/woSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String workOrderSave(WorkOrderMstDtl woMstDtl,
			@RequestParam("referenceDoc") MultipartFile file) {
		// get Current Dept, User and Role Information
		//String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		//AuthUser authUser = userService.getAuthUserByUserId(userName);

		//String deptId = authUser.getDeptId();

		//Departments department = departmentsService.getDepartmentByDeptId(authUser.getDeptId());
		Date now = new Date();
		String filePath = this.saveFileToDrive(file);

		WorkOrderMst workOrderMst = new WorkOrderMst(null,
				woMstDtl.getWorkOrderNo(), woMstDtl.getContractDate(),
				woMstDtl.getSupplierName(), woMstDtl.getKhathId(),
				woMstDtl.isPsi(), woMstDtl.isPli(), filePath,
				woMstDtl.isActive(), userName, now, null, null, null);

		boolean result = this.saveWorkOrderDtl(workOrderMst, woMstDtl,
				userName, now);
		if (result) {
			return "redirect:/workOrder/list.do";
		} else {
			return "redirect:/workOrder/getForm.do";
		}
	}

	private boolean saveWorkOrderDtl(WorkOrderMst workOrderMst,
			WorkOrderMstDtl woMstDtl, String userName, Date now) {
		boolean flag = false;

		List<String> itemIdList = null;
		List<String> descriptionList = null;
		List<String> uomList = null;
		List<Double> itemQtyList = null;
		List<Double> costList = null;
		List<String> remarksList = null;

		if (woMstDtl.getItemId() != null) {
			itemIdList = woMstDtl.getItemId();
		}

		if (woMstDtl.getDescription() != null) {
			descriptionList = woMstDtl.getDescription();
		}
		if (woMstDtl.getUom() != null) {
			uomList = woMstDtl.getUom();
		}
		if (woMstDtl.getItemQty() != null) {
			itemQtyList = woMstDtl.getItemQty();
		}
		if (woMstDtl.getCost() != null) {
			costList = woMstDtl.getCost();
		}
		if (woMstDtl.getRemarks() != null) {
			remarksList = woMstDtl.getRemarks();
		}
		int i = 0;
		if (itemIdList.size() > 0) {
			for (String itemId : itemIdList) {
				commonService.saveOrUpdateModelObjectToDB(workOrderMst);
				WorkOrderDtl workOrderDtl = new WorkOrderDtl(null, itemId,
						workOrderMst, woMstDtl.getWorkOrderNo(),
						descriptionList.get(i), uomList.get(i),
						itemQtyList.get(i), itemQtyList.get(i),
						costList.get(i), true, userName, now, null, null,
						remarksList.get(i));
				commonService.saveOrUpdateModelObjectToDB(workOrderDtl);
				i++;
			}			
			flag = true;
		}
		return flag;
	}

	/*
	 * public String download(Model model, WorkOrderMst woMst) throws Exception{
	 * String imagePath = woMst.getReferenceDoc();
	 * 
	 * if (imagePath.length() > 0) { File file = new File(imagePath); if
	 * (file.length() > 0) { FileInputStream fis = new FileInputStream(file);
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream(); int b; byte[]
	 * buffer = new byte[1024]; while ((b = fis.read(buffer)) != -1) {
	 * bos.write(buffer, 0, b); } byte[] fileBytes = bos.toByteArray();
	 * fis.close(); bos.close();
	 * 
	 * byte[] encoded = Base64.encodeBase64(fileBytes); String encodedString =
	 * new String(encoded);
	 * 
	 * ModelMap map = new ModelMap(); model.addAttribute("downloadFile",
	 * encodedString); } } return "centralStore/downloadFile"; }
	 */

	@SuppressWarnings("resource")
	@RequestMapping(value = "/workOrder/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, WorkOrderMst woMst)
			throws Exception {

		HttpServletResponse response = httpServletResponse;

		String imagePath = woMst.getReferenceDoc();

		File file = new File(imagePath);

		FileInputStream fis = new FileInputStream(file);

		InputStream in = new BufferedInputStream(fis);
		// String filename = "";

		String extension = "";

		int i = imagePath.lastIndexOf('.');

		if (i > 0) {
			extension = imagePath.substring(i + 1);
		}

		String agent = request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") != -1) {
			imagePath = URLEncoder.encode("download." + extension, "UTF8");
			// response.setContentType("application/x-download");
			// response.setHeader("Content-Disposition","attachment;filename=" +
			// imagePath);
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

			// response.setContentType("application/force-download");
			// response.addHeader("Content-Disposition",
			// "attachment; filename=\"" + imagePath + "\"");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\""
					+ imagePath + "\"");
		}

		/*
		 * BufferedOutputStream out = new BufferedOutputStream(
		 * response.getOutputStream());
		 * 
		 * byte by[] = new byte[32768]; int index = in.read(by, 0, 32768); while
		 * (index != -1) { out.write(by, 0, index); index = in.read(by, 0,
		 * 32768); }
		 */

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		// ServletOutputStream out = response.getOutputStream();
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
	@RequestMapping(value = "/workOrder/list.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView workOrderList(WorkOrderMstDtl woMstDtl) {

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WorkOrderMst> workOrderMstList = (List<WorkOrderMst>) (Object) commonService
				.getAllObjectList("WorkOrderMst");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workOrderMstList", workOrderMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/workOrderList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workOrder/show.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showWorkOrder(WorkOrderMst woMst) {

		Integer id = woMst.getId();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		WorkOrderMst workOrderMstDb = (WorkOrderMst) commonService
				.getAnObjectByAnyUniqueColumn("WorkOrderMst", "id", id + "");

		List<CSProcItemRcvMst> rcvItemList = (List<CSProcItemRcvMst>) (Object) commonService
				.getObjectListByAnyColumn("CSProcItemRcvMst", "contractNo",
						workOrderMstDb.getWorkOrderNo());

		List<WorkOrderDtl> workOrderDtlList = (List<WorkOrderDtl>) (Object) commonService
				.getObjectListByAnyColumn("WorkOrderDtl", "workOrderNo",
						workOrderMstDb.getWorkOrderNo());
		
		int khathID = workOrderMstDb.getKhathId();
		DescoKhath descoKhath = ( DescoKhath ) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", khathID + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workOrderMst", workOrderMstDb);
		model.put("workOrderDtlList", workOrderDtlList);
		model.put("khathInfo", descoKhath);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		if (rcvItemList.size() > 0) {
			return new ModelAndView("centralStore/workOrderShow", model);
		} else {
			return new ModelAndView("centralStore/workOrderEdit", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workOrder/update.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView updateWorkOrderDtl(WorkOrderDtl woDtl) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		Integer id = woDtl.getId();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		WorkOrderDtl workOrderDtlDb = (WorkOrderDtl) commonService
				.getAnObjectByAnyUniqueColumn("WorkOrderDtl", "id", id + "");
		workOrderDtlDb.setItemQty(woDtl.getItemQty());
		workOrderDtlDb.setRemainingQty(woDtl.getItemQty());
		workOrderDtlDb.setCost(woDtl.getCost());
		workOrderDtlDb.setRemarks(woDtl.getRemarks());
		workOrderDtlDb.setModifiedBy(userName);
		workOrderDtlDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(workOrderDtlDb);

		WorkOrderMst workOrderMstDb = (WorkOrderMst) commonService
				.getAnObjectByAnyUniqueColumn("WorkOrderMst", "workOrderNo",
						workOrderDtlDb.getWorkOrderNo());

		List<WorkOrderDtl> workOrderDtlList = (List<WorkOrderDtl>) (Object) commonService
				.getObjectListByAnyColumn("WorkOrderDtl", "workOrderNo",
						workOrderMstDb.getWorkOrderNo());
		
		DescoKhath descoKhath = ( DescoKhath ) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", workOrderMstDb.getKhathId() + "");
		model.put("khathInfo", descoKhath);
		
		model.put("workOrderMst", workOrderMstDb);
		model.put("workOrderDtlList", workOrderDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		model.put("successflag", 1);
		

		return new ModelAndView("centralStore/workOrderEdit", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/workOrder/delete.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteWorkOrderDtl(WorkOrderDtl woDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Integer id = woDtl.getId();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		WorkOrderDtl workOrderDtlDb = (WorkOrderDtl) commonService
				.getAnObjectByAnyUniqueColumn("WorkOrderDtl", "id", id + "");
		String woNo = workOrderDtlDb.getWorkOrderNo();

		commonService.deleteAnObjectById("WorkOrderDtl", id);

		WorkOrderMst workOrderMstDb = (WorkOrderMst) commonService
				.getAnObjectByAnyUniqueColumn("WorkOrderMst", "workOrderNo",
						woNo);

		List<WorkOrderDtl> workOrderDtlList = (List<WorkOrderDtl>) (Object) commonService
				.getObjectListByAnyColumn("WorkOrderDtl", "workOrderNo", woNo);
		
		DescoKhath descoKhath = ( DescoKhath ) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", workOrderMstDb.getKhathId() + "");
		model.put("khathInfo", descoKhath);

		
		model.put("workOrderMst", workOrderMstDb);
		model.put("workOrderDtlList", workOrderDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		model.put("deleteflag", 1);
		
		return new ModelAndView("centralStore/workOrderEdit", model);

	}

	@RequestMapping(value = "/workOrder/searchByWONo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView searchByWONo(WorkOrderMst workOrderMst) {

		String workOrderNo = workOrderMst.getWorkOrderNo();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<WorkOrderMst> workOrderMstList = this.simulateSearchResult(workOrderNo);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workOrderMstList", workOrderMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/workOrderList", model);
	}

	@SuppressWarnings("unchecked")
	private List<WorkOrderMst> simulateSearchResult(String workOrderNo) {

		List<WorkOrderMst> searchWOList = new ArrayList<WorkOrderMst>();

		List<WorkOrderMst> woMstList = (List<WorkOrderMst>) (Object) commonService
				.getAllObjectList("WorkOrderMst");
		// iterate a list and filter by tagName
		for (WorkOrderMst woMst : woMstList) {
			if (woMst.getWorkOrderNo().toLowerCase()
					.contains(workOrderNo.toLowerCase())) {
				searchWOList.add(woMst);
			}
		}
		if (workOrderNo.length() > 0) {
			return searchWOList;
		} else {
			return woMstList;
		}
	}

	private String saveFileToDrive(MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();

			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("pdf")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(descoWORootPath + File.separator
							+ "work_order");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					// return serverFile.getAbsolutePath();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "";
		}
		return "";
	}

	@RequestMapping(value = "/workOrder/viewInventoryItemCategory.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItemCategory(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";
		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			List<ItemMaster> selectItemsFromDb = itemInventoryService
					.getItemListByCategoryId(invItem.getCategoryId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemsFromDb);

		} else {
			Thread.sleep(5000);
		}

		return toJson;
	}

	@RequestMapping(value = "/workOrder/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			// InventoryItem invItem = gson.fromJson(json, InventoryItem.class);
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);

			// ItemMaster selectItemFromDb =
			// itemInventoryService.getItemGroup(invItem.getInventoryItemId());
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(5000);
		}
		return toJson;
	}

	@RequestMapping(value = "/workOrder/checkWorkOrder.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkWorkOrder(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String result = "";
		String toJson = "";
		WorkOrderMst workOrderMstDb = new WorkOrderMst();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			WorkOrderMst workOrderMst = gson.fromJson(json, WorkOrderMst.class);

			if (workOrderMst.getWorkOrderNo().equals("")) {
				result = "unsuccess";
			} else {
				workOrderMstDb = (WorkOrderMst) commonService
						.getAnObjectByAnyUniqueColumn("WorkOrderMst",
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

	public boolean isJSONValid(String JSON_STRING) {
		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}
}
