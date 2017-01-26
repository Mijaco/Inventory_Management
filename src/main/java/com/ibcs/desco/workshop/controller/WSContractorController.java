package com.ibcs.desco.workshop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.bean.ContractorAndRepresentiveMstDtl;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.subStore.service.SSItemLocationMstService;
import com.ibcs.desco.subStore.service.SubStoreItemsService;

@Controller
@PropertySource("classpath:common.properties")
public class WSContractorController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	SSItemLocationMstService ssItemLocationMstService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

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

	@RequestMapping(value = "/ws/xf/contractorForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getWsContractorForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
							REVENUE);
			model.put("descoKhath", descoKhath);
			return new ModelAndView("workshop/wsContractorForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}

	}

	@RequestMapping(value = "/ws/xf/saveWsContractor.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveWsContractor(
			ContractorAndRepresentiveMstDtl cont_rep_mst_dtl,
			@RequestParam("picture") MultipartFile[] pictures) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
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
			contractor.setCreatedDate(new Date());

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

			List<String> representativeNameList = cont_rep_mst_dtl
					.getRepresentativeName();
			List<String> repUserIdList = cont_rep_mst_dtl.getRepUserId();
			List<String> reDesignationList = cont_rep_mst_dtl
					.getReDesignation();
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
					contractorRepresentive.setDesignation(reDesignationList
							.get(i));
					contractorRepresentive.setContactNo(repMobileList.get(i));
					contractorRepresentive.setEmail(repEmailList.get(i));
					contractorRepresentive.setListedDate(repStartDateList
							.get(i));
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
					Roles roles = (Roles) commonService
							.getAnObjectByAnyUniqueColumn(
									"com.ibcs.desco.admin.model.Roles", "role",
									ROLE_WO_CN_USER);

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
									(byteData[j] & 0xff) + 0x100, 16)
									.substring(1));
						}
						u.setPassword(hashedPassword.toString());
						u.setName(contractorRepresentive.getRepresentiveName());
						u.setEmail(contractorRepresentive.getEmail());
						u.setActive(true);
						u.setCreatedBy(userName);
						u.setCreatedDate(new Date());
						u.setUserid(contractorRepresentive.getUserId());
						u.setRoleid(roles.getRole_id());
						u.setLocked(0);
						u.setDeptId(cont.getDeptId());
						u.setDesignation(contractorRepresentive
								.getDesignation());
						u.setUserType(USER_TYPE_CONTRACTOR);
						userService.addAuthUser(u);
					} catch (Exception e) {
						e.printStackTrace();
						model.put("errorMsg", e.getMessage());
						return new ModelAndView("workshop/errorWS", model);
					}
				}

			}
			return new ModelAndView("redirect:/ws/xf/contractorList.do");

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
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

}
