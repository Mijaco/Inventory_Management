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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.bean.PettyCashMstDtl;
import com.ibcs.desco.common.model.PettyCashDtl;
import com.ibcs.desco.common.model.PettyCashHead;
import com.ibcs.desco.common.model.PettyCashMst;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.localStore.model.LocalPurchaseMst;

/*
 * Author: Ihteshamul Alam, IBCS
 * 
 */

@Controller
public class PettyCashController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	DepartmentsService departmentsService;
	
	@Autowired
	UserService userService;
	
	@Value("${desco.project.rootPath}")
	private String descoWORootPath;
	
	@RequestMapping(value="/pettycash/pettyCashHeadSetup.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashHeadSetup() {
		return new ModelAndView("common/pettyCashHead");
	}
	
	@RequestMapping(value="/pettycash/checkPettyCashCode.do", method=RequestMethod.POST)
	@ResponseBody
	public String checkPettyCashCode( PettyCashHead pettyCashHead ) {
		
		String response = "";
		String pettyCode = pettyCashHead.getPettyCashCode();
		
		PettyCashHead pettyCash = ( PettyCashHead ) commonService
				.getAnObjectByAnyUniqueColumn("PettyCashHead", "pettyCashCode", pettyCode);
		
		if( pettyCash == null ) {
			response = "success";
		} else {
			response = "fail";
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pettycash/pettyCashHeadSave.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashHeadSave( PettyCashHead pettyCashHead ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String username = commonService.getAuthUserName();
			Date now = new Date();
			
			pettyCashHead.setCreatedBy(username);
			pettyCashHead.setCreatedDate(now);
			pettyCashHead.setActive(true);
			pettyCashHead.setId(null);
			commonService.saveOrUpdateModelObjectToDB(pettyCashHead);
			
			List<PettyCashHead> pettyCashHeadList = ( List<PettyCashHead> ) ( Object ) commonService
					.getAllObjectList("PettyCashHead");
			
			model.put("pettyCashHeadList", pettyCashHeadList);
			return new ModelAndView("common/pettyCashHeadList", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pettycash/pettyCashHeadList.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashHeadList() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<PettyCashHead> pettyCashHeadList = ( List<PettyCashHead> ) ( Object ) commonService
					.getAllObjectList("PettyCashHead");

			model.put("pettyCashHeadList", pettyCashHeadList);
			
			return new ModelAndView("common/pettyCashHeadList", model);
			
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pettycash/pettyCashForm.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			
			List<PettyCashHead> pettyCashHeadList = ( List<PettyCashHead> ) ( Object ) commonService
					.getAllObjectList("PettyCashHead");
			
			List<Departments> departmentList = departmentsService.listDepartments();
			
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());
			
			model.put("pettyCashHeadList", pettyCashHeadList);
			model.put("departmentList", departmentList);
			model.put("department", department);
			
			return new ModelAndView("common/pettyCashForm", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pettycash/pettyCashSave.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashSave( PettyCashMstDtl pettyCashMstDtl,
			@RequestParam("uploadDoc") MultipartFile uploadDoc) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			String username = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(username);
			Date now = new Date();
			
			String filePath = "";
			filePath = this.saveFileToDrive(uploadDoc);
			
			PettyCashMst pettyCashMst = new PettyCashMst();
			
			pettyCashMst.setPurchaseBy(pettyCashMstDtl.getPurchaseBy());
			pettyCashMst.setPurchaseDate(pettyCashMstDtl.getPurchaseDate());
			pettyCashMst.setReferenceNo(pettyCashMstDtl.getReferenceNo());
			pettyCashMst.setReferenceDoc(filePath);
			pettyCashMst.setDeptCode(authUser.getDeptId());
			pettyCashMst.setCreatedBy(username);
			pettyCashMst.setCreatedDate(now);
			pettyCashMst.setId(null);
			pettyCashMst.setActive(true);
			commonService.saveOrUpdateModelObjectToDB(pettyCashMst);
			
			List<String> pettyCashCodeList = pettyCashMstDtl.getPettyCashCode();
			
			//List<String> pettyCashHeadList = pettyCashMstDtl.getPettyCashHead();
			
			List<String> descriptionList = pettyCashMstDtl.getDescription();
			
			List<Double> totalCostList = pettyCashMstDtl.getTotalCost();
			
			List<String> remarksList = pettyCashMstDtl.getRemarks();
			
			for( int i = 0; i < pettyCashCodeList.size(); i++ ) {
				PettyCashDtl pettyCashDtl = new PettyCashDtl();
				
				PettyCashHead pettyCashHead = ( PettyCashHead ) commonService.getAnObjectByAnyUniqueColumn("PettyCashHead", "pettyCashCode", pettyCashCodeList.get(i) );
				
				pettyCashDtl.setId(null);
				pettyCashDtl.setActive(true);
				pettyCashDtl.setPettyCashCode( pettyCashCodeList.get(i) );
				pettyCashDtl.setPettyCashHead( pettyCashHead.getPettyCashHead() );
				pettyCashDtl.setDescription( descriptionList.get(i) );
				pettyCashDtl.setRemarks( remarksList.get(i) );
				pettyCashDtl.setTotalCost( totalCostList.get(i) );
				pettyCashDtl.setCreatedBy(username);
				pettyCashDtl.setCreatedDate(now);
				pettyCashDtl.setPettyCashMst(pettyCashMst);
				
				commonService.saveOrUpdateModelObjectToDB(pettyCashDtl);
			}
			
			List<PettyCashMst> pettyCashMstList = ( List<PettyCashMst> ) (Object) commonService
					.getAllObjectList("PettyCashMst");
			
			model.put("pettyCashMstList", pettyCashMstList);
			
			return new ModelAndView("common/pettyCashList", model);
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
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
	
	@RequestMapping(value = "/petty/download.do", method = RequestMethod.POST)
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
	@RequestMapping(value="/pettycash/pettyCashList.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashList() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<PettyCashMst> pettyCashMstList = ( List<PettyCashMst> ) (Object) commonService
					.getAllObjectList("PettyCashMst");
			
			model.put("pettyCashMstList", pettyCashMstList);
			
			return new ModelAndView("common/pettyCashList", model);
			
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pettycash/pettyCashShow.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pettyCashShow( PettyCashMst pettyCashMst ) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer id = pettyCashMst.getId();
			
			PettyCashMst pettyCashMstInfo = ( PettyCashMst ) commonService
					.getAnObjectByAnyUniqueColumn("PettyCashMst", "id", id.toString());
			
			List<PettyCashDtl> pettyCashDtl = ( List<PettyCashDtl> ) ( Object ) commonService
					.getObjectListByAnyColumn("PettyCashDtl", "pettyCashMst.id", id.toString());
			
			model.put("pettyCashMstInfo", pettyCashMstInfo);
			model.put("pettyCashDtl", pettyCashDtl);
			
			return new ModelAndView("common/pettyCashShow", model);
			
		} catch( Exception E ) {
			model.put("errorMsg", E.getMessage());
			return new ModelAndView("common/errorCommon", model);
		}
	}
}
