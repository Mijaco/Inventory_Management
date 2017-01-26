package com.ibcs.desco.localStore.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.been.LocalPurchaseMstDtl;
import com.ibcs.desco.localStore.model.LocalPurchaseDtl;
import com.ibcs.desco.localStore.model.LocalPurchaseMst;

@Controller
@RequestMapping(value = "/ls/lp")
@PropertySource("classpath:common.properties")
public class LocalPurchaseController extends Constrants {

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	UserService userService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CommonService commonService;

	@Value("${desco.project.rootPath}")
	private String descoWORootPath;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.localPurchase.prefix}")
	private String localPurchasePrefix;

	@RequestMapping(value = "/localPurchaseFrom.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lp, 'WRITE')")
	public ModelAndView localPurchaseFrom(LocalPurchaseMstDtl lp) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*List<ItemCategory> categoryList = itemGroupService
					.getAllItemGroups();*/
			
			List<ItemCategory> categoryList = itemGroupService
					.getGeneralItemGroups();

			model.put("categoryList", categoryList);
			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			return new ModelAndView("localStore/localPurchaseFrom", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveLocalPurchase.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView saveLocalPurchase(LocalPurchaseMstDtl lpMstDtl,
			@RequestParam("referenceDoc") MultipartFile referenceDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			String filePath = "";
			String localPurchaseNo = "";
			if (lpMstDtl.getItemCode().size() > 0) {
				if (lpMstDtl.getReceivedQty().get(0) > 0) {
					filePath = this.saveFileToDrive(referenceDoc);
					localPurchaseNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									localPurchasePrefix,
									department.getDescoCode(), separator,
									"LS_LP_SEQ");
					LocalPurchaseMst localPurchaseMst = new LocalPurchaseMst(
							null, localPurchaseNo, department,
							lpMstDtl.getSupplierName(),
							lpMstDtl.getReferenceNo(),
							lpMstDtl.getPurchaseDate(), filePath, true,
							lpMstDtl.getRemark(), userName, new Date(), null,
							null, lpMstDtl.getPurchaseOrderNo(), lpMstDtl.getSupplyDate());

					commonService.saveOrUpdateModelObjectToDB(localPurchaseMst);
					LocalPurchaseMst lpMstDB = (LocalPurchaseMst) commonService
							.getAnObjectByAnyUniqueColumn("LocalPurchaseMst",
									"localPurchaseNo", localPurchaseNo);

					//boolean result = 
							this.saveLocalPurchaseDtl(lpMstDB,
							lpMstDtl, userName);
				} else {
					model.put("errorMsg", "Purchase quantity can not be zero");
					return new ModelAndView("localStore/errorLS", model);
				}
			} else {
				model.put("errorMsg",
						"Please select at least 1 item for Local Purcahse");
				return new ModelAndView("localStore/errorLS", model);
			}
			List<LocalPurchaseMst> localPurchaseMstList = (List<LocalPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseMst",
							"department.deptId", deptId);

			model.put("localPurchaseMstList", localPurchaseMstList);
			return new ModelAndView("localStore/localPurchaseList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	private boolean saveLocalPurchaseDtl(LocalPurchaseMst localPurchaseMst,
			LocalPurchaseMstDtl lpMstDtl, String userName) {

		boolean flag = false;
		List<String> itemIdList = lpMstDtl.getItemCode();
		List<String> descriptionList = lpMstDtl.getDescription();
		List<String> uomList = lpMstDtl.getUom();
		List<Double> itemQtyList = lpMstDtl.getReceivedQty();
		List<Double> unitCostList = lpMstDtl.getUnitCost();
		List<Double> totalCostList = lpMstDtl.getTotalCost();
		List<String> remarksList = lpMstDtl.getRemarks();

		for (int i = 0; i < itemIdList.size(); i++) {
			LocalPurchaseDtl localPurchaseDtl = new LocalPurchaseDtl(null,
					localPurchaseMst, itemIdList.get(i),
					descriptionList.get(i), uomList.get(i), itemQtyList.get(i),
					unitCostList.get(i), totalCostList.get(i), true,
					remarksList.get(i), userName, new Date(), null, null);

			commonService.saveOrUpdateModelObjectToDB(localPurchaseDtl);

			flag = true;
		}

		return flag;
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
							+ "local_purchase");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "Please uplade a PDF file";
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/localPurchaseList.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView localPurchaseList(LocalPurchaseMstDtl lpMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			List<LocalPurchaseMst> localPurchaseMstList = (List<LocalPurchaseMst>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseMst",
							"department.deptId", deptId);

			model.put("localPurchaseMstList", localPurchaseMstList);
			return new ModelAndView("localStore/localPurchaseList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewLocalPurchase.do", method = RequestMethod.POST)
	// @PreAuthorize("isAuthenticated() and hasPermission(#lpMstDtl, 'WRITE')")
	public ModelAndView viewLocalPurchase(LocalPurchaseMst lpMst) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {	
			
			LocalPurchaseMst localPurchaseMstDB=(LocalPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalPurchaseMst",
							"id", lpMst.getId().toString());

			List<LocalPurchaseDtl> localPurchaseDtlList = (List<LocalPurchaseDtl>) (Object) commonService
					.getObjectListByAnyColumn("LocalPurchaseDtl",
							"localPurchaseMst", lpMst.getId().toString());
			
			model.put("localPurchaseMst", localPurchaseMstDB);
			model.put("localPurchaseDtlList", localPurchaseDtlList);
			return new ModelAndView("localStore/localPurchaseShow", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	
	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse, LocalPurchaseMst lpMst)
			throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = lpMst.getReferenceDoc();
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		InputStream inputStream = new BufferedInputStream(fis);
		String extension = "";

		int i = filePath.lastIndexOf('.');

		if (i > 0) {
			extension = filePath.substring(i + 1);
		}

		String agent = request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") != -1) {
			filePath = URLEncoder.encode("localPurchase." + extension, "UTF8");			
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("localPurchase." + extension, "UTF8","B");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\""
					+ filePath + "\"");
		}

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		byte by[] = new byte[32768];
		int index = inputStream.read(by, 0, 32768);
		while (index != -1) {
			out.write(by, 0, index);
			index = inputStream.read(by, 0, 32768);
		}
		inputStream.close();
		out.flush();

		return response;
	}

	//Added by: Ihteshamul Alam
	@RequestMapping(value="/updateLocalPurchase.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateLocalPurchase( @RequestBody String json ) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			LocalPurchaseDtl localPurchaseDtl = gson.fromJson(json, LocalPurchaseDtl.class);
			Integer id = localPurchaseDtl.getId();
			String username = commonService.getAuthUserName();
			LocalPurchaseDtl updateLP = (LocalPurchaseDtl) commonService.getAnObjectByAnyUniqueColumn("LocalPurchaseDtl", "id", id+"");
			
			updateLP.setReceivedQty( localPurchaseDtl.getReceivedQty() );
			updateLP.setUnitCost( localPurchaseDtl.getUnitCost() );
			updateLP.setTotalCost( localPurchaseDtl.getTotalCost() );
			updateLP.setModifiedBy( username );
			updateLP.setModifiedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB(updateLP);
			toJson = "success";
		} else {
			Thread.sleep(3000);
			toJson = "fail";
		}

		return toJson;
	}

}
