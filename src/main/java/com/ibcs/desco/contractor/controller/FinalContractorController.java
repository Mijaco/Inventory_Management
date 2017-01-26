package com.ibcs.desco.contractor.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.JobItemMaintenance;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.model.PndJobMst;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;

@Controller
public class FinalContractorController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractor/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorList() {
		String userName = commonService.getAuthUserName();

		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "department.id", department.getId().toString());

/*		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor", "active", "1");*/
		/*List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByTwoColumn("Contractor", "active", "1", "contractorType", PROJECT);*/
		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByThreeColumn("Contractor", "active", "1", 
						"contractorType", PROJECT, "khathId", descoKhath.getId().toString());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorList", contractorList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		//return new ModelAndView("contractor/finalContractorList", model);
		// Added By Taleb
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView(
					"contractor/projects/finalContractorList", model);
		} else {
			return new ModelAndView("contractor/finalContractorList", model);
			
		}
	}

	@RequestMapping(value = "/contRep/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorRepresentativeListGet(
			ContractorRepresentive contractorRepresentive) {
		return finalContractorRepresentativeList(contractorRepresentive);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contRep/finalList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorRepresentativeList(
			ContractorRepresentive contractorRepresentive) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ContractorRepresentive> contractorRepresentiveList = (List<ContractorRepresentive>) (Object) commonService
				.getObjectListByAnyColumn("ContractorRepresentive",
						"contractor.id", contractorRepresentive.getId() + "");
		for(ContractorRepresentive c:contractorRepresentiveList){
			try {
				c.setPicture(this.getImagePath(c.getPicture()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						contractorRepresentive.getId() + "");
		
		

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorRepresentiveList", contractorRepresentiveList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/finalContractorRepresentativeList",
				model);
	}
	
	String getImagePath(String path) throws Exception{
		
		if (path.length() > 0) {

			File file = new File(path);
			
			if (file.length() > 0) {
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int b;
				byte[] buffer = new byte[1024];
				while ((b = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, b);
				}
				byte[] fileBytes = bos.toByteArray();
				fis.close();
				bos.close();

				byte[] encoded = Base64.encodeBase64(fileBytes);
				String encodedString = new String(encoded);
				return encodedString;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contMats/finalList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseMaterialList(
			JobItemMaintenance jobItemMaintenance) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<JobItemMaintenance> jobItemMaintenanceList = (List<JobItemMaintenance>) (Object) commonService
				.getObjectListByAnyColumn("JobItemMaintenance",
						"contractor.id", jobItemMaintenance.getId() + "");

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						jobItemMaintenance.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobItemMaintenanceList", jobItemMaintenanceList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/finalContractorMaterialListShow",
				model);
	}

	@RequestMapping(value = "/contractor/jobList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobListGET(PndJobMst pndJobMst) {
		return contractorWiseJobList(pndJobMst);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobs/finalList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobList(PndJobMst pndJobMst) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						pndJobMst.getId() + "");
		//remove by taleb
		/*List<PndJobMst> pndJobMstList = (List<PndJobMst>) (Object) commonService
				.getObjectListByAnyColumn("PndJobMst", "woNumber",
						contractor.getContractNo() + "");*/
		// added by Taleb
		List<PndJobMst> pndJobMstList = (List<PndJobMst>) (Object) commonService
				.getObjectListByTwoColumn("PndJobMst", "woNumber",
						contractor.getContractNo() + "", "approved", "1");
				
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pndJobMstList", pndJobMstList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/finalContractorJobListShow", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jobs/jobDetails.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobDetails(PndJobDtl pndJobDtl) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		PndJobMst pndJobMst = (PndJobMst) commonService
				.getAnObjectByAnyUniqueColumn("PndJobMst", "id",
						pndJobDtl.getId() + "");

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", pndJobMst.getWoNumber());

		List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>) (Object) commonService
				.getObjectListByAnyColumn("PndJobDtl", "pndJobMst.id",
						pndJobDtl.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("pndJobDtlList", pndJobDtlList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("pndJobMst", pndJobMst);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("contractor/finalContractorJobDtlListShow",
				model);
	}

	// @RequestMapping(value = "/cs/storeTicket/detailsInfo.do", method =
	@SuppressWarnings("unchecked")
	// RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csStoreTicketDetailsInfo(
			CSStoreTicketMst csStoreTicketMst) {

		String id = csStoreTicketMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		CSStoreTicketMst csStoreTicketMstDb = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst", "id", id);

		List<CSStoreTicketDtl> csStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo",
						csStoreTicketMstDb.getTicketNo());

		String operationId = csStoreTicketMstDb.getOperationId();

		// get all hierarchy history against current operation id and status
		// done
		List<StoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = (List<StoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"StoreTicketApprovalHierarchyHistory", "operationName",
						CS_STORE_TICKET, "operationId", operationId, "status",
						DONE);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketApproveHistoryList",
				storeTicketApproveHistoryList);
		model.put("csStoreTicketDtlList", csStoreTicketDtlList);
		model.put("csStoreTicketMst", csStoreTicketMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalStoreTicketShow", model);
	}

	// @RequestMapping(value = "/cs/storeTicket/finalListSearch.do", method =
	// RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(CSStoreTicketMst csStoreTicketMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = csStoreTicketMst.getFromDate();

		Date secondDate = csStoreTicketMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String storeTicketType = csStoreTicketMst.getStoreTicketType();

		String ticketNo = csStoreTicketMst.getTicketNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CSStoreTicketMst> storeTicketMstList = this.simulateSearchResult(
				ticketNo, storeTicketType, fromDate, toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalStoreTicketList", model);

	}

	@SuppressWarnings("unchecked")
	private List<CSStoreTicketMst> simulateSearchResult(String ticketNo,
			String storeTicketType, String fromDate, String toDate) {

		List<CSStoreTicketMst> searchSTList = new ArrayList<CSStoreTicketMst>();
		List<CSStoreTicketMst> storeTicketMstList = (List<CSStoreTicketMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn("CSStoreTicketMst",
						"ticketDate", fromDate, toDate, "approved", "1",
						"storeTicketType", storeTicketType);

		// iterate a list and filter by tagName
		for (CSStoreTicketMst csStoreTicketMst : storeTicketMstList) {
			if (csStoreTicketMst.getTicketNo().toLowerCase()
					.contains(ticketNo.toLowerCase())) {
				searchSTList.add(csStoreTicketMst);
			}
		}
		if (ticketNo.length() > 0) {
			return searchSTList;
		} else {
			return storeTicketMstList;
		}
	}
	
	@RequestMapping(value = "/fcoDoc/download.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getDemandNoteFileGM(HttpServletRequest request,
			HttpServletResponse httpServletResponse,
			Contractor contractor) throws Exception { 
		return this.getFCOFile(request, httpServletResponse,
				contractor);
	}
	
	@RequestMapping(value = "/fcoDoc/download.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public HttpServletResponse getFCOFile(HttpServletRequest request,
			HttpServletResponse httpServletResponse,
			Contractor contractor) throws Exception {

		HttpServletResponse response = httpServletResponse;
		String filePath = contractor.getDownloadDocFile();
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
			filePath = URLEncoder
					.encode("procurement_doc." + extension, "UTF8");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename="
					+ filePath);
		} else if (agent != null && agent.indexOf("Mozilla") != -1) {
			response.setCharacterEncoding("UTF-8");
			filePath = MimeUtility.encodeText("procurement_doc." + extension,
					"UTF8", "B");
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
}
