package com.ibcs.desco.common.controller;

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
import com.ibcs.desco.common.bean.LocalRRMstDtl;
import com.ibcs.desco.common.model.LocalRRDtl;
import com.ibcs.desco.common.model.LocalRRMst;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.localStore.model.LocalPurchaseDtl;
import com.ibcs.desco.localStore.model.LocalPurchaseMst;


@Controller
public class LocalReceivingReportController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentsService departmentsService;
	
	@Autowired
	ItemGroupService itemGroupService;
	
	@Value("${desco.project.rootPath}")
	private String descoWORootPath;
	
	@Value("${desco.local.receiving.report.prefix}")
	private String localReceivingReportPrefix;
	
	@Value("${project.separator}")
	private String separator;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/lrr/localReceivingReportForm.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView localReceivingReportForm() {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			
			List<Departments> departmentList = departmentsService.listDepartments();
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			
			List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
			
			List<LocalPurchaseMst> localPurchaseMst = ( List<LocalPurchaseMst> ) ( Object ) commonService
					.getAllObjectList("LocalPurchaseMst");
			
			model.put("departmentList", departmentList);
			model.put("department", department);
			model.put("categoryList", categoryList);
			model.put("localPurchaseMst", localPurchaseMst);
			
			return new ModelAndView("common/localReceivingReportForm", model);
		}  catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/lrr/localReceivingReportFormSave.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView localReceivingReportFormSave( LocalRRMstDtl localRRMstDtl,
			@RequestParam("uploadDoc") MultipartFile uploadDoc ) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String username = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(username);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			Date now = new Date();
			
			String filePath = "";
			filePath = this.saveFileToDrive(uploadDoc);
			
			LocalRRMst localRRMst= new LocalRRMst();
			
			String descoDeptCode = department.getDescoCode();
			String lrrNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							localReceivingReportPrefix, descoDeptCode,
							separator, "LS_LRR_SEQ");
			
			localRRMst.setId(null);
			localRRMst.setContractDate( localRRMstDtl.getContractDate() );
			localRRMst.setContractNo( localRRMstDtl.getContractNo() );
			localRRMst.setInvoiceNo( localRRMstDtl.getInvoiceNo() );
			localRRMst.setInvoiceDate( localRRMstDtl.getInvoiceDate() );
			localRRMst.setSupplierName( localRRMstDtl.getSupplierName() );
			localRRMst.setDeliveryDate( localRRMstDtl.getDeliveryDate() );
			localRRMst.setPliDate( localRRMstDtl.getPliDate() );
			localRRMst.setReferenceDoc( filePath );
			localRRMst.setDeptNo( authUser.getDeptId() );
			localRRMst.setCreatedBy( username );
			localRRMst.setCreatedDate( now );
			localRRMst.setActive(true);
			localRRMst.setConfirm("0");
			localRRMst.setlRRNo(lrrNo);
			
			commonService.saveOrUpdateModelObjectToDB(localRRMst);
			
			List<String> itemCodeList = localRRMstDtl.getItemCode();
			List<String> itemNameList = localRRMstDtl.getDescription();
			List<String> uomList = localRRMstDtl.getUom();
			//List<String> purchasedQtyList = localRRMstDtl.getPurchasedQty();
			List<String> requiredQtyList = localRRMstDtl.getRequiredQty();
			List<String> ledgerBookList = localRRMstDtl.getLedgerBook();
			List<String> pageNoList = localRRMstDtl.getPageNo();
			List<String> remarksList = localRRMstDtl.getRemarks();
			
			for( int i = 0; i < itemCodeList.size(); i++ ) {
				
				LocalRRDtl localRRDtl = new LocalRRDtl();
				
				localRRDtl.setId(null);
				localRRDtl.setItemCode(itemCodeList.get(i));
				localRRDtl.setItemName(itemNameList.get(i));
				localRRDtl.setUom(uomList.get(i));
				//localRRDtl.setPurchasedQty(purchasedQtyList.get(i));
				localRRDtl.setRequiredQty(requiredQtyList.get(i));
				localRRDtl.setLedgerBook(ledgerBookList.get(i));
				localRRDtl.setPageNo(pageNoList.get(i));
				localRRDtl.setRemarks(remarksList.get(i));
				localRRDtl.setActive(true);
				localRRDtl.setCreatedBy(username);
				localRRDtl.setCreatedDate(now);
				localRRDtl.setLocalRRMst(localRRMst);
				
				commonService.saveOrUpdateModelObjectToDB(localRRDtl);
			}
			
			List<LocalRRMst> localRRMstList = ( List<LocalRRMst> ) ( Object ) commonService
					.getAllObjectList("LocalRRMst");
			
			model.put("localRRMstList", localRRMstList);
			
			return new ModelAndView("common/localRrList", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("settings/errorSetting", model);
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
			return "Please uplode a PDF file";
		}
		return "";
	}
	
	@RequestMapping(value = "/lrr/download.do", method = RequestMethod.POST)
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lrr/lrrList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lrrList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<LocalRRMst> localRRMstList = ( List<LocalRRMst> ) ( Object ) commonService
					.getAllObjectList("LocalRRMst");
			
			model.put("localRRMstList", localRRMstList);
			
			return new ModelAndView("common/localRrList", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lrr/lrrShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lrrShow( LocalRRMst localRRMst ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = localRRMst.getId();
			
			LocalRRMst lclRrMst = ( LocalRRMst ) commonService
					.getAnObjectByAnyUniqueColumn("LocalRRMst", "id", id.toString());
			
			List<LocalRRDtl> localRrDtlList = ( List<LocalRRDtl> ) ( Object ) commonService
					.getObjectListByAnyColumn("LocalRRDtl", "localRRMst.id", id.toString());
			
			model.put("localRRDtlList", localRrDtlList);
			model.put("lclRrMst", lclRrMst);
			
			return new ModelAndView("common/localRrShow", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lrr/lrrMstConfirm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lrrMstConfirm( LocalRRMst localRRMst ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Integer id = localRRMst.getId();
			
			LocalRRMst lclRrMstC = ( LocalRRMst ) commonService
					.getAnObjectByAnyUniqueColumn("LocalRRMst", "id", id.toString());
			
			lclRrMstC.setConfirm("1");
			lclRrMstC.setModifiedBy( commonService.getAuthUserName() );
			lclRrMstC.setModifiedDate( new Date() );
			commonService.saveOrUpdateModelObjectToDB( lclRrMstC );
			
			List<LocalRRDtl> localRrDtlList = ( List<LocalRRDtl> ) ( Object ) commonService
					.getObjectListByAnyColumn("LocalRRDtl", "localRRMst.id", id.toString());
			
			model.put("localRRDtlList", localRrDtlList);
			model.put("lclRrMst", lclRrMstC);
			
			return new ModelAndView("common/localRrShow", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lrr/localRRInfoByContractNo.do", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String localRRInfoByContractNo( @RequestBody String json ) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		if (isJson) {
			LocalPurchaseMst localPurchaseMst = gson.fromJson(json,
					LocalPurchaseMst.class);

			LocalPurchaseMst localPurchaseMstList = (LocalPurchaseMst) commonService
					.getAnObjectByAnyUniqueColumn("LocalPurchaseMst", "localPurchaseNo", localPurchaseMst.getLocalPurchaseNo());
			
			List<LocalPurchaseDtl> localPurchaseDtl = ( List<LocalPurchaseDtl> ) ( Object ) commonService
					.getObjectListByAnyColumn("LocalPurchaseDtl", "localPurchaseMst.id", localPurchaseMstList.getId().toString() );
			
			localPurchaseMstList.setLocalPurchaseDtl(localPurchaseDtl);
			
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(localPurchaseMstList);

		} else {
			Thread.sleep(2000);
		}

		return toJson;
	}
}
