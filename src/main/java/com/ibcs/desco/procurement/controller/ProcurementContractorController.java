package com.ibcs.desco.procurement.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.ibcs.desco.cs.model.ContractorDepartmentReference;
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
@RequestMapping(value = "/proc")
@PropertySource("classpath:common.properties")
public class ProcurementContractorController extends Constrants {

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procContractorForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getProcContractorForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		List<Departments> depts = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);

		model.put("descoKhath", descoKhath);
		model.put("depts", depts);
		return new ModelAndView("procurement/contractor/procContractorForm",
				model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/wsContractorForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getWsContractorForm() {
		Map<String, Object> model = new HashMap<String, Object>();

		List<DescoKhath> descoKhath = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("descoKhath", descoKhath);		
		return new ModelAndView("procurement/contractor/wsContractorForm",
				model);
	}

	@RequestMapping(value = "/contractorSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getContractorSave(
			ContractorAndRepresentiveMstDtl cont_rep_mst_dtl,
			@RequestParam("picture") MultipartFile[] pictures) {
		
		String deptId = cont_rep_mst_dtl.getDeptId();
		
		Departments targetDept = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId", deptId);
		
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
		contractor.setSndDeptPk(targetDept.getId().toString());

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
				Roles roles = null;
				if (cont_rep_mst_dtl.getContractorType().equalsIgnoreCase(
						REVENUE)) {
					roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.Roles", "role",
							ROLE_SND_CN_USER);
				} else {
					roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.Roles", "role",
							ROLE_WO_CN_USER);
				}

				try {
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
					
					// assign contractor to snd
					AuthUser authUserdb = (AuthUser)
							commonService.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
									"userid", u.getUserid());
					ContractorDepartmentReference contractorDepartmentReference
						= new ContractorDepartmentReference();
					contractorDepartmentReference.setContractorId(authUserdb.getId());
					contractorDepartmentReference.setDeptId(targetDept.getId());
					contractorDepartmentReference.setId(null);
						commonService
								.saveOrUpdateModelObjectToDB(contractorDepartmentReference);
				} catch (Exception e) {
					// throw e;
				}
			}

		}

		//

		// return "redirect:/pnd/contractorForm.do";
		// return "redirect:/contractor/finalList.do";
		if (cont_rep_mst_dtl.getContractorType().equalsIgnoreCase(REVENUE)) {
			ContractorDepartmentReference contractorDepartmentReference = new ContractorDepartmentReference();
			Contractor contractorDb = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
							cont_rep_mst_dtl.getContractNo());
			contractorDepartmentReference.setContractorId(contractorDb.getId());
			contractorDepartmentReference.setDeptId(targetDept.getId());
			contractorDepartmentReference.setId(null);
			commonService
					.saveOrUpdateModelObjectToDB(contractorDepartmentReference);
			return "redirect:/ls/contractorList.do";
		} else {
			// return "redirect:/workshop/contractorList.do";
			return "redirect:/proc/contractorList.do";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sndAssignContractorForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getSndAssignContractorForm() {

		List<Departments> depts = (List<Departments>) (Object) commonService
				.getObjectListByAnyColumn("Departments", "parent",
						SND_PARENT_CODE);

		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByTwoColumn("Contractor", "active", "1",
						"contractorType", REVENUE);

		List<Contractor> contractorList1 = (List<Contractor>) (Object) commonService
				.getObjectListByTwoColumn("Contractor", "active", "1",
						"contractorType", WORKSHOP);

		if (contractorList1.size() > 0) {
			contractorList.addAll(contractorList1);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("departments", depts);
		model.put("contractorList", contractorList);
		return new ModelAndView(
				"procurement/contractor/sndAssignContractorForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorList() {
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<Contractor> contractorList = (List<Contractor>) (Object) commonService
				.getObjectListByTwoColumn("Contractor", "active", "1",
						"contractorType", REVENUE);

		List<Contractor> contractorList1 = (List<Contractor>) (Object) commonService
				.getObjectListByTwoColumn("Contractor", "active", "1",
						"contractorType", WORKSHOP);

		if (contractorList1.size() > 0) {
			contractorList.addAll(contractorList1);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorList", contractorList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("procurement/contractor/procContractorList",
				model);
	}

	// sndContractorAssignSave.do
	@RequestMapping(value = "/sndContractorAssignSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView sndContractorAssignSave(
			ContractorDepartmentReference contractorDepartmentReference) {
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		commonService
				.saveOrUpdateModelObjectToDB(contractorDepartmentReference);

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		/*return new ModelAndView("procurement/contractor/procSndContractorList",
				model);*/
		return new ModelAndView("procurement/contractor/procContractorList",
				model);
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

		return new ModelAndView("procurement/contractor/contractorRepresentativeList",
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
	
	//Added by Shimul
	@RequestMapping(value="/updateRepresentativeInfo.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateRepresentativeInfo( ContractorRepresentive contractorRepresentive ) {
		String response = "";
		
		Integer id = contractorRepresentive.getId();
		
		ContractorRepresentive cnRepresentative = ( ContractorRepresentive )  commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive", "id", id.toString());
		
		cnRepresentative.setRepresentiveName( contractorRepresentive.getRepresentiveName() );
		cnRepresentative.setAddress( contractorRepresentive.getAddress() );
		cnRepresentative.setDesignation( contractorRepresentive.getDesignation() );
		cnRepresentative.setContactNo( contractorRepresentive.getContactNo() );
		cnRepresentative.setModifiedBy( commonService.getAuthUserName() );
		cnRepresentative.setModifiedDate( new Date() );
		
		commonService.saveOrUpdateModelObjectToDB(cnRepresentative);
		response = "success";
		return response;
	}

	//Added by: Ihteshamul Alam
		@RequestMapping(value = "/saveProcMultipleRepresentativeInfo.do", method = RequestMethod.POST)
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

			return "redirect:/contRep/finalList.do?id=" + cont_rep_mst_dtl.getContractorId();
		}
}
