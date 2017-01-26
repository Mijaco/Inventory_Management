package com.ibcs.desco.contractor.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CnSsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.bean.ContractorAndRepresentiveMstDtl;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketDtlService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SSItemTransactionMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/pnd")
@PropertySource("classpath:common.properties")
public class ContractorController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	CnSsRequisitionApprovalHierarchyHistoryService cnSsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	CSStoreTicketDtlService csStoreTicketDtlService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	SSItemTransactionMstService ssItemTransactionMstService;

	@Autowired
	CommonService commonService;
	Date now = new Date();

	@Value("${desco.dept.code.contractors}")
	private String contractorsCode;

	@Value("${desco.project.rootPath}")
	private String descoFilePath;

	@RequestMapping(value = "/contractorForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getContractorForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService.getDepartmentByDeptId(deptId);
		
		try{
			
		// remove by Taleb
		/*List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");*/
		
		//added by taleb
		List<DescoKhath> descoKhathList = new ArrayList<DescoKhath>();
		DescoKhath descoKhath = (DescoKhath) commonService.getAnObjectByAnyUniqueColumn("DescoKhath", "department.id", department.getId().toString());
		descoKhathList.add(descoKhath);
		model.put("descoKhathList", descoKhathList);
		
		if (roleName.contains(ROLE_PROJECT_)) {
			return new ModelAndView("contractor/projects/contractorForm", model);			
		} else {
			return new ModelAndView("contractor/contractorForm", model);		}
		
		}catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("common/errorHome");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorRepresentiveForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getContractorRepresentiveForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByAnyColumn("Contractor", "active", "1");

		model.put("contractorList", contractorList);
		return new ModelAndView("contractor/contractorRepresentiveForm", model);
	}

	@RequestMapping(value = "/contractorSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getContractorSave(
			ContractorAndRepresentiveMstDtl cont_rep_mst_dtl,
			@RequestParam("picture") MultipartFile[] pictures) {
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						cont_rep_mst_dtl.getKhathId() + "");
		String userName = commonService.getAuthUserName();
		String contractorsDeptId = commonService
				.getNewDeptIdByParentDept(contractorsCode);
		Contractor contractor = new Contractor();
		contractor.setContractNo(cont_rep_mst_dtl.getContractNo());
		contractor.setTenderNo(cont_rep_mst_dtl.getTenderNo());
		contractor.setContractorName(cont_rep_mst_dtl.getContractorName());
		contractor.setContractDate(cont_rep_mst_dtl.getContractDate());
		contractor.setExpiryDate(cont_rep_mst_dtl.getExpiryDate());
		contractor.setUpdatedValidityDate(cont_rep_mst_dtl.getExpiryDate());
		contractor.setDeptId(contractorsDeptId);
		contractor.setContractorType(cont_rep_mst_dtl.getContractorType());
		contractor.setKhathId(descoKhath.getId());
		contractor.setKhathName(descoKhath.getKhathName());
		contractor.setDivision(cont_rep_mst_dtl.getDivision());
		contractor.setAddress(cont_rep_mst_dtl.getAddress());
		contractor.setRemarks(cont_rep_mst_dtl.getRemarks());
		contractor.setActive(contractor.isActive());
		contractor.setCreatedBy(userName);
		contractor.setCreatedDate(now);
		contractor.setContractorType(PROJECT);
		contractorRepresentiveService.addContractor(contractor);

		Departments dept = new Departments();
		dept.setId(null);
		dept.setCreatedBy(userName);
		dept.setCreatedDate(new Date());
		dept.setParent(contractorsCode);
		dept.setDeptId(contractorsDeptId);
		dept.setDeptName(cont_rep_mst_dtl.getContractorName());
		dept.setDeptLevel("7");
		dept.setDescoCode("99"); // Need to Change
		dept.setAddress(cont_rep_mst_dtl.getAddress());
		dept.setContactNo(cont_rep_mst_dtl.getContractNo());
		dept.setEmail("");
		dept.setContactPersion("");
		dept.setActive(true);
		// work order number is set to remarks temporarily
		dept.setRemarks(cont_rep_mst_dtl.getContractNo());
		commonService.saveOrUpdateModelObjectToDB(dept);

		//
		// List<String> repUserIdList = cont_rep_mst_dtl.getRepUserId();
		List<String> representativeNameList = cont_rep_mst_dtl
				.getRepresentativeName();
		List<String> repUserIdList = cont_rep_mst_dtl.getRepUserId();
		List<String> reDesignationList = cont_rep_mst_dtl.getReDesignation();
		List<String> repEmailList = cont_rep_mst_dtl.getRepEmail();
		List<String> repMobileList = cont_rep_mst_dtl.getRepMobile();
		List<String> repAddressList = cont_rep_mst_dtl.getRepAddress();
		List<Date> repStartDateList = cont_rep_mst_dtl.getRepStartDate();
		List<Date> repEndDateList = cont_rep_mst_dtl.getRepEndDate();
		// List<String> pictureList = cont_rep_mst_dtl.getPicture();

		Contractor cont = contractorRepresentiveService
				.getContractorByContractNo(cont_rep_mst_dtl.getContractNo());

		if (repUserIdList.size() > 0) {
			for (int i = 0; i < repUserIdList.size(); i++) {
				ContractorRepresentive contractorRepresentive = new ContractorRepresentive();
				contractorRepresentive.setContractNo(cont_rep_mst_dtl
						.getContractNo());
				contractorRepresentive.setUserId(repUserIdList.get(i));
				contractorRepresentive
						.setRepresentiveName(representativeNameList.get(i));
				contractorRepresentive.setAddress(repAddressList.get(i));
				contractorRepresentive.setDesignation(reDesignationList.get(i));
				contractorRepresentive.setContactNo(repMobileList.get(i));
				contractorRepresentive.setEmail(repEmailList.get(i));
				contractorRepresentive.setListedDate(repStartDateList.get(i));
				contractorRepresentive.setEndDate(repEndDateList.get(i));
				contractorRepresentive.setPicture(this
						.saveFileToDrive(pictures[i]));
				contractorRepresentive.setActive(true);
				contractorRepresentive.setCreatedBy(userName);
				contractorRepresentive.setCreatedDate(new Date());
				contractorRepresentive.setContractor(cont);
				contractorRepresentive.setMstId(cont.getId());
				contractorRepresentiveService
						.addContractorRepresentive(contractorRepresentive);

				// save as user
				AuthUser u = new AuthUser();
				Roles roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.Roles", "role",
							ROLE_CN_PD_USER);
				

				try {
					//String password = contractorRepresentive.getUserId();
					String password = "123456";
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(password.getBytes());
					byte byteData[] = md.digest();

					// convert the byte to hex format method 1
					StringBuffer hashedPassword = new StringBuffer();
					for (int j = 0; j < byteData.length; j++) {
						hashedPassword.append(Integer.toString(
								(byteData[j] & 0xff) + 0x100, 16).substring(1));
					}
					u.setPassword(hashedPassword.toString());
					u.setName(contractorRepresentive.getRepresentiveName());
					u.setEmail(contractorRepresentive.getEmail());
					u.setActive(true);
					u.setCreatedBy(userName);
					u.setCreatedDate(now);
					u.setUserid(contractorRepresentive.getUserId());
					u.setRoleid(roles.getRole_id());
					u.setLocked(0);
					u.setDeptId(cont.getDeptId());
					u.setDesignation(contractorRepresentive.getDesignation());
					u.setUserType(USER_TYPE_CONTRACTOR);
					userService.addAuthUser(u);
				} catch (Exception e) {
					// throw e;
				}
			}

		}

		//

		// return "redirect:/pnd/contractorForm.do";
		// return "redirect:/contractor/finalList.do";

		return "redirect:/pd/projectList.do?id="
				+ cont_rep_mst_dtl.getKhathId();

	}

	@RequestMapping(value = "/contractorRepresentiveSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String contractorRepresentiveSave(
			ContractorRepresentive contractorRepresentive) throws Exception {

		String userName = commonService.getAuthUserName();
		Date now = new Date();
		Contractor c = contractorRepresentiveService
				.getContractorByContractNo(contractorRepresentive
						.getContractNo());
		contractorRepresentive.setActive(contractorRepresentive.isActive());
		contractorRepresentive.setCreatedBy(userName);
		contractorRepresentive.setCreatedDate(now);

		contractorRepresentive.setContractor(contractorRepresentiveService
				.getContractorByContractNo(contractorRepresentive
						.getContractNo()));
		contractorRepresentive.setMstId(c.getId());

		contractorRepresentiveService
				.addContractorRepresentive(contractorRepresentive);

		// save as user
		AuthUser u = new AuthUser();
		Roles roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
				"Roles", "role", "ROLE_CN_PD_USER");
		try {
			String password = contractorRepresentive.getUserId();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			// contractorRepresentiveService.addContractorRepresentive(contractorRepresentive);

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer hashedPassword = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				hashedPassword.append(Integer.toString(
						(byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			u.setPassword(hashedPassword.toString());
			u.setName(contractorRepresentive.getRepresentiveName());
			u.setEmail(contractorRepresentive.getEmail());
			u.setActive(true);
			u.setCreatedBy(userName);
			u.setCreatedDate(now);
			u.setUserid(contractorRepresentive.getUserId());
			u.setRoleid(roles.getRole_id());
			u.setLocked(0);
			u.setDeptId(c.getDeptId());
			u.setDesignation(contractorRepresentive.getDesignation());
			u.setUserType(USER_TYPE_CONTRACTOR);
			userService.addAuthUser(u);
		} catch (Exception e) {
			throw e;
		}

		Contractor obj = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
						contractorRepresentive.getContractNo());

		return "redirect:/contRep/finalList.do?id=" + obj.getId();
	}

	@RequestMapping(value = "/checkUser.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkUser(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String result = "";
		String toJson = "";
		AuthUser u = new AuthUser();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuthUser workOrderMst = gson.fromJson(json, AuthUser.class);

			if (workOrderMst.getUserid().equals("")) {
				result = "unsuccess";
			} else {

				u = (AuthUser) commonService.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						workOrderMst.getUserid());
				if (u == null) {
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
	
	@RequestMapping(value = "/costEstimationReport.do", method = RequestMethod.GET)
	public ModelAndView costEstimationReport() {
		return new ModelAndView("contractor/costEstimationReport2");
	}
	

	@RequestMapping(value = "/checkDuplicateContractNumber", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkDuplicateContractNumber(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String result = "";
		String toJson = "";
		Contractor contractor = new Contractor();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			Contractor contractorBean = gson.fromJson(json, Contractor.class);

			if (contractorBean.getContractNo().equals("")) {
				result = "unsuccess";
			} else {
				contractor = (Contractor) commonService
						.getAnObjectByAnyUniqueColumn("Contractor",
								"contractNo", contractorBean.getContractNo());
				if (contractor == null) {
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

	// Added By Ashid
	private String saveFileToDrive(MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extension = "";
			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("png")
					|| extension.equalsIgnoreCase("jpg")
					|| extension.equalsIgnoreCase("jpeg")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();
					File dir = new File(descoFilePath + File.separator
							+ "contractorRepresentative");
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
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/updateContractorInfo.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateContractorInfo( Contractor contractor ) {
		String response = "";
		
		Integer id = contractor.getId();
		String address = contractor.getAddress();
		Contractor contract = ( Contractor ) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "id", id.toString());
		if( contract != null ) {
			contract.setAddress(address);
			contract.setModifiedBy( commonService.getAuthUserName() );
			contract.setModifiedDate( new Date() );
			commonService.saveOrUpdateModelObjectToDB(contract);
			
			response = "success";
		} else {
			response = "failed";
		}
		return response;
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/updateRepresentativeInfo.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateRepresentativeInfo( ContractorRepresentive contractorRepresentive ) {
		String response = "";
		
		Integer id = contractorRepresentive.getId();
		String representiveName = contractorRepresentive.getRepresentiveName();
		
		ContractorRepresentive representive = ( ContractorRepresentive ) commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive", "id", id.toString());
		if( representive != null ) {
			representive.setRepresentiveName(representiveName);
			representive.setAddress( contractorRepresentive.getAddress() );
			representive.setDesignation( contractorRepresentive.getDesignation() );
			representive.setModifiedBy( commonService.getAuthUserName() );
			representive.setModifiedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB(representive);
			
			response = "success";
		} else {
			response = "failed";
		}
		return response;
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value="/updateContractorMstInfo.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateContractorMstInfo( Contractor contractor ) {
		String response = "";
		
		Integer id = contractor.getId();
		
		Contractor contract = ( Contractor ) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "id", id.toString());
		if( contract != null ) {
			contract.setAddress( contractor.getAddress() );
			contract.setContractorName( contractor.getContractorName() );
			contract.setDivision( contractor.getDivision() );
			contract.setModifiedBy( commonService.getAuthUserName() );
			contract.setModifiedDate( new Date() );
			commonService.saveOrUpdateModelObjectToDB(contract);
			
			response = "success";
		} else {
			response = "failed";
		}
		return response;
	}
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value = "/checkEmail.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkEmail(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String result = "";
		String toJson = "";
		AuthUser u = new AuthUser();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			AuthUser workOrderMst = gson.fromJson(json, AuthUser.class);
	
			if (workOrderMst.getEmail().equals("")) {
				result = "unsuccess";
			} else {
	
				u = (AuthUser) commonService.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "email",
						workOrderMst.getEmail());
				if (u == null) {
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
	
	//Added by: Ihteshamul Alam
	@RequestMapping(value = "/saveMultipleRepresentativeInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String saveMultipleRepresentativeInfo(
			ContractorAndRepresentiveMstDtl cont_rep_mst_dtl,
			@RequestParam("picture") MultipartFile[] pictures) {
		
		String userName = commonService.getAuthUserName();
		Contractor contractor = ( Contractor ) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "id", cont_rep_mst_dtl.getContractorId().toString());
		
		List<String> representativeNameList = cont_rep_mst_dtl
				.getRepresentativeName();
		List<String> repUserIdList = cont_rep_mst_dtl.getRepUserId();
		List<String> reDesignationList = cont_rep_mst_dtl.getReDesignation();
		List<String> repEmailList = cont_rep_mst_dtl.getRepEmail();
		List<String> repMobileList = cont_rep_mst_dtl.getRepMobile();
		List<String> repAddressList = cont_rep_mst_dtl.getRepAddress();
		List<Date> repStartDateList = cont_rep_mst_dtl.getRepStartDate();
		List<Date> repEndDateList = cont_rep_mst_dtl.getRepEndDate();
		// List<String> pictureList = cont_rep_mst_dtl.getPicture();

		/*Contractor cont = contractorRepresentiveService
				.getContractorByContractNo( contractor.getContractNo() );*/

		if (repUserIdList.size() > 0) {
			for (int i = 0; i < repUserIdList.size(); i++) {
				ContractorRepresentive contractorRepresentive = new ContractorRepresentive();
				contractorRepresentive.setContractNo( contractor.getContractNo() );
				contractorRepresentive.setUserId(repUserIdList.get(i));
				contractorRepresentive
						.setRepresentiveName(representativeNameList.get(i));
				contractorRepresentive.setAddress(repAddressList.get(i));
				contractorRepresentive.setDesignation(reDesignationList.get(i));
				contractorRepresentive.setContactNo(repMobileList.get(i));
				contractorRepresentive.setEmail(repEmailList.get(i));
				contractorRepresentive.setListedDate(repStartDateList.get(i));
				contractorRepresentive.setEndDate(repEndDateList.get(i));
				contractorRepresentive.setPicture(this
						.saveFileToDrive(pictures[i]));
				contractorRepresentive.setActive(true);
				contractorRepresentive.setCreatedBy(userName);
				contractorRepresentive.setCreatedDate(new Date());
				contractorRepresentive.setContractor(contractor);
				contractorRepresentive.setMstId(contractor.getId());
				contractorRepresentiveService
						.addContractorRepresentive(contractorRepresentive);

				// save as user
				AuthUser u = new AuthUser();
				Roles roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.Roles", "role",
							ROLE_CN_PD_USER);
				

				try {
					//String password = contractorRepresentive.getUserId();
					String password = "123456";
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(password.getBytes());
					byte byteData[] = md.digest();

					// convert the byte to hex format method 1
					StringBuffer hashedPassword = new StringBuffer();
					for (int j = 0; j < byteData.length; j++) {
						hashedPassword.append(Integer.toString(
								(byteData[j] & 0xff) + 0x100, 16).substring(1));
					}
					u.setPassword(hashedPassword.toString());
					u.setName(contractorRepresentive.getRepresentiveName());
					u.setEmail(contractorRepresentive.getEmail());
					u.setActive(true);
					u.setCreatedBy(userName);
					u.setCreatedDate(now);
					u.setUserid(contractorRepresentive.getUserId());
					u.setRoleid(roles.getRole_id());
					u.setLocked(0);
					u.setDeptId(contractor.getDeptId());
					u.setDesignation(contractorRepresentive.getDesignation());
					u.setUserType(USER_TYPE_CONTRACTOR);
					userService.addAuthUser(u);
				} catch (Exception e) {
					// throw e;
				}
			}

		}

		//

		// return "redirect:/pnd/contractorForm.do";
		// return "redirect:/contractor/finalList.do";

		return "redirect:/pd/projectList.do?id="
				+ contractor.getKhathId();
	}
}
