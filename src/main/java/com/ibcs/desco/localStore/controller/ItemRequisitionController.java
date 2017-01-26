package com.ibcs.desco.localStore.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AllocationTable;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.dashboard.Contractor;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnWsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.LsSsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.bean.Location7sMstDtl;
import com.ibcs.desco.cs.bean.Location7sQty;
import com.ibcs.desco.cs.bean.TempLocationMstDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.model.ContractorDepartmentReference;
import com.ibcs.desco.cs.model.TempItemLocation;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.localStore.service.LSItemTransactionMstService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.subStore.service.SubStoreItemsService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

@Controller
@RequestMapping(value = "/ls")
@PropertySource("classpath:common.properties")
public class ItemRequisitionController extends Constrants {
	// Create Object for Service Classes which will be instantiated from spring
	// bean

	@Autowired
	LSItemTransactionMstService lsItemTransactionMstService;

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	CentralStoreRequisitionDtlService centralStoreRequisitionDtlService;

	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;

	@Autowired
	SubStoreRequisitionDtlService subStoreRequisitionDtlService;

	@Autowired
	LsCsRequisitionApprovalHierarchyHistoryService lsCsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	LsSsRequisitionApprovalHierarchyHistoryService lsSsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@Autowired
	SubStoreItemsService subStoreItemsService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.project.rootPath}")
	private String descoFilePath;

	@RequestMapping(value = "/storeRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSave(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
							REVENUE);

			DescoSession descoSession = (DescoSession) commonService
					.getCurrentDescoSession();

			// Ledger Mst

			if (csRequisitionMstDtl.getReceivedBy().equals("")
					|| descoSession == null) {
				String msg = "Receiver Name or Sesson Can not be null";
				model.put("errorMsg", msg);
				return new ModelAndView("redirect:/ls/storeRequisitionForm.do");
			}

			if (csRequisitionMstDtl.getRequisitionTo().equalsIgnoreCase(
					ContentType.SUB_STORE.toString())) {
				SubStoreRequisitionMstDtl saveSubStoreData = importObj(
						csRequisitionMstDtl, descoKhath, department);
				return saveSubStoreData(saveSubStoreData);
			}
			CentralStoreRequisitionMst csRequisitionMst = new CentralStoreRequisitionMst();
			csRequisitionMst.setIdenterDesignation(csRequisitionMstDtl
					.getIdenterDesignation());
			csRequisitionMst.setDeptName(department.getDeptName());
			csRequisitionMst.setReceivedBy(csRequisitionMstDtl.getReceivedBy());
			csRequisitionMst.setKhathId(descoKhath.getId());
			csRequisitionMst.setKhathName(descoKhath.getKhathName());

			// set current date time as RequisitionDate. GUI date is not used
			// here.

			csRequisitionMst.setUuid(UUID.randomUUID().toString());
			csRequisitionMst.setRequisitionDate(now);
			csRequisitionMst.setActive(csRequisitionMstDtl.isActive());
			csRequisitionMst.setCreatedBy(userName);
			csRequisitionMst.setCreatedDate(now);
			csRequisitionMst.setReceived(false);
			csRequisitionMst.setRequisitionTo(csRequisitionMstDtl
					.getRequisitionTo());
			csRequisitionMst.setSenderStore(ContentType.LOCAL_STORE.toString());
			csRequisitionMst.setSndCode(department.getDescoCode());
			csRequisitionMst.setKhathId(csRequisitionMstDtl.getKhathId());
			csRequisitionMst.setKhathName(csRequisitionMstDtl.getKhathName());
			csRequisitionMst.setCarriedBy(csRequisitionMstDtl.getCarriedBy());

			boolean successFlag = true;
			String msg = "";
			// Save requisition master and details info to db if any details
			// exist
			successFlag = addStoreRequisitionDtls(csRequisitionMstDtl,
					csRequisitionMst, roleName, department, authUser, now);

			model.put("centralStoreRequisitionMst", csRequisitionMst);
			if (successFlag) {

				return getStoreRequisitionShow(csRequisitionMst);
			} else {
				msg = "Sorry! You have no permission to do this operation. Try again.";
				model.put("errorMsg", msg);
				return new ModelAndView("localStore/errorLS", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}

	}

	private SubStoreRequisitionMstDtl importObj(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			DescoKhath descoKhath, Departments department) {
		SubStoreRequisitionMstDtl ss = new SubStoreRequisitionMstDtl();
		ss.setId(csRequisitionMstDtl.getId());
		ss.setRequisitionTo(csRequisitionMstDtl.getRequisitionTo());
		ss.setIdenterDesignation(csRequisitionMstDtl.getIdenterDesignation());
		ss.setCreatedBy(csRequisitionMstDtl.getCreatedBy());
		ss.setReceivedBy(csRequisitionMstDtl.getReceivedBy());
		ss.setRequisitionNo(csRequisitionMstDtl.getRequisitionNo());
		ss.setRequisitionDate(csRequisitionMstDtl.getRequisitionDate());
		ss.setStoreTicketNO(csRequisitionMstDtl.getStoreTicketNO());
		ss.setGatePassNo(csRequisitionMstDtl.getGatePassNo());
		ss.setGatePassDate(csRequisitionMstDtl.getGatePassDate());
		ss.setItemName(csRequisitionMstDtl.getItemName());
		ss.setItemCode(csRequisitionMstDtl.getItemCode());
		ss.setUom(csRequisitionMstDtl.getUom());
		ss.setQuantityRequired(csRequisitionMstDtl.getQuantityRequired());
		ss.setQuantityIssued(csRequisitionMstDtl.getQuantityIssued());
		/*
		 * ss.setUnitCost(csRequisitionMstDtl.getUnitCost());
		 * ss.setTotalCost(csRequisitionMstDtl.getTotalCost());
		 * ss.setHeadOfAccount(csRequisitionMstDtl.getHeadOfAccount());
		 */
		ss.setRemarks(csRequisitionMstDtl.getRemarks());
		ss.setIssueqty(csRequisitionMstDtl.getIssueqty());
		ss.setSenderStore(csRequisitionMstDtl.getSenderStore());
		ss.setKhathId(descoKhath.getId());
		ss.setKhathName(descoKhath.getKhathName());
		ss.setCarriedBy(csRequisitionMstDtl.getCarriedBy());
		ss.setSndCode(department.getDescoCode());
		ss.setDeptName(department.getDeptName());
		// ss.setLedgerName(csRequisitionMstDtl.getLedgerName());
		return ss;
	}

	private CentralStoreRequisitionMst importObjToCs(
			SubStoreRequisitionMst ssRequisitionMst) {
		CentralStoreRequisitionMst cs = new CentralStoreRequisitionMst();
		cs.setId(ssRequisitionMst.getId());
		cs.setReceived(false);
		cs.setCreatedBy(ssRequisitionMst.getCreatedBy());
		cs.setReceivedBy(ssRequisitionMst.getReceivedBy());
		cs.setRequisitionNo(ssRequisitionMst.getRequisitionNo());
		cs.setRequisitionDate(ssRequisitionMst.getRequisitionDate());
		cs.setStoreTicketNO(ssRequisitionMst.getStoreTicketNO());
		cs.setGatePassNo(ssRequisitionMst.getGatePassNo());
		cs.setGatePassDate(ssRequisitionMst.getGatePassDate());
		cs.setDeptName(ssRequisitionMst.getDeptName());
		cs.setIdenterDesignation(ssRequisitionMst.getIdenterDesignation());
		cs.setModifiedBy(ssRequisitionMst.getModifiedBy());
		cs.setModifiedDate(ssRequisitionMst.getModifiedDate());
		cs.setRemarks(ssRequisitionMst.getRemarks());
		cs.setSenderStore(ssRequisitionMst.getSenderStore());
		cs.setRequisitionTo(ssRequisitionMst.getRequisitionTo());
		cs.setKhathId(ssRequisitionMst.getKhathId());
		cs.setUuid(ssRequisitionMst.getUuid());
		cs.setKhathName(ssRequisitionMst.getKhathName());
		cs.setSndCode(ssRequisitionMst.getSndCode());
		cs.setCarriedBy(ssRequisitionMst.getCarriedBy());
		return cs;
	}

	@SuppressWarnings("unused")
	private SubStoreRequisitionMst importObjToSs(CentralStoreRequisitionMst cs) {
		SubStoreRequisitionMst ss = new SubStoreRequisitionMst();
		ss.setId(cs.getId());
		ss.setCreatedBy(cs.getCreatedBy());
		ss.setReceivedBy(cs.getReceivedBy());
		ss.setRequisitionNo(cs.getRequisitionNo());
		ss.setRequisitionDate(cs.getRequisitionDate());
		ss.setStoreTicketNO(cs.getStoreTicketNO());
		ss.setGatePassNo(cs.getGatePassNo());
		ss.setGatePassDate(cs.getGatePassDate());
		ss.setDeptName(cs.getDeptName());
		ss.setIdenterDesignation(cs.getIdenterDesignation());
		ss.setModifiedBy(cs.getModifiedBy());
		ss.setModifiedDate(cs.getModifiedDate());
		ss.setRemarks(cs.getRemarks());
		ss.setSenderStore(cs.getSenderStore());
		ss.setRequisitionTo(cs.getRequisitionTo());
		ss.setKhathId(cs.getKhathId());
		ss.setUuid(cs.getUuid());
		ss.setKhathName(cs.getKhathName());
		ss.setSndCode(cs.getSndCode());
		ss.setCarriedBy(cs.getCarriedBy());
		return ss;
	}

	public ModelAndView saveSubStoreData(
			SubStoreRequisitionMstDtl ssRequisitionMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		Date now = new Date();
		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * ssRequisitionMstDtl.getKhathId() + "");
			 */

			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
							REVENUE);

			SubStoreRequisitionMst ssRequisitionMst = new SubStoreRequisitionMst();
			ssRequisitionMst.setIdenterDesignation(ssRequisitionMstDtl
					.getIdenterDesignation());
			ssRequisitionMst.setDeptName(department.getDeptName());
			ssRequisitionMst.setKhathId(descoKhath.getId());
			ssRequisitionMst.setKhathName(descoKhath.getKhathName());
			ssRequisitionMst.setUuid(UUID.randomUUID().toString());
			// set current date time as RequisitionDate. GUI date is not used
			// here.
			ssRequisitionMst.setRequisitionDate(now);
			ssRequisitionMst.setActive(ssRequisitionMstDtl.isActive());
			ssRequisitionMst.setCreatedBy(authUser.getName() + " (" + userName
					+ ")");
			ssRequisitionMst.setCreatedDate(now);
			ssRequisitionMst.setRequisitionTo(ssRequisitionMstDtl
					.getRequisitionTo());
			ssRequisitionMst.setSenderStore(ContentType.LOCAL_STORE.toString());
			ssRequisitionMst.setSndCode(department.getDescoCode());

			ssRequisitionMst.setKhathId(ssRequisitionMstDtl.getKhathId());
			ssRequisitionMst.setKhathName(ssRequisitionMstDtl.getKhathName());
			ssRequisitionMst.setCarriedBy(ssRequisitionMstDtl.getCarriedBy());
			boolean successFlag = true;
			String msg = "";
			// Save requisition master and details info to db if any details
			// exist
			successFlag = addLsSsStoreRequisitionDtls(ssRequisitionMstDtl,
					ssRequisitionMst, roleName, department, authUser, now);

			model.put("subStoreRequisitionMst", ssRequisitionMst);

			if (successFlag) {
				// msg="Congrats! You have submitted 1 requisition successfully.";
				/*
				 * return "redirect:/ls/storeRequisitionShowOfLocalToSub.do?id="
				 * + ssRequisitionMst.getId() + "&requisitionTo=" +
				 * ssRequisitionMst.getRequisitionTo();
				 */
				return getStoreRequisitionShowOfLocalToSub(ssRequisitionMst);
				// return new
				// ModelAndView("redirect:/ls/storeRequisitionShowOfLocalToSub.do");
			} else {
				msg = "Sorry! You have no permission to do this operation. Try again.";
				model.put("errorMsg", msg);
				return new ModelAndView("localStore/errorLS", model);

				// return new ModelAndView("redirect:/ls/requisitionList.do");

			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean addStoreRequisitionDtls(
			CentralStoreRequisitionMstDtl csRequisitionMstDtl,
			CentralStoreRequisitionMst csRequisitionMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		// List<String> headOfAccountList = null;
		List<String> remarksList = null;
		// List<String> ledgerNameList = null;

		if (csRequisitionMstDtl.getItemCode() != null) {
			itemCodeList = csRequisitionMstDtl.getItemCode();
		}

		if (csRequisitionMstDtl.getItemName() != null) {
			itemNameList = csRequisitionMstDtl.getItemName();
		}

		if (csRequisitionMstDtl.getUom() != null) {
			uomList = csRequisitionMstDtl.getUom();
		}

		if (csRequisitionMstDtl.getQuantityRequired() != null) {
			quantityRequiredList = csRequisitionMstDtl.getQuantityRequired();
		}

		/*
		 * if (csRequisitionMstDtl.getUnitCost() != null) { unitCostList =
		 * csRequisitionMstDtl.getUnitCost(); }
		 * 
		 * if (csRequisitionMstDtl.getTotalCost() != null) { totalCostList =
		 * csRequisitionMstDtl.getTotalCost(); }
		 */

		/*
		 * if (csRequisitionMstDtl.getHeadOfAccount() != null) {
		 * headOfAccountList = csRequisitionMstDtl.getHeadOfAccount(); }
		 */

		if (csRequisitionMstDtl.getRemarks() != null) {
			remarksList = csRequisitionMstDtl.getRemarks();
		}

		/*
		 * if (csRequisitionMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csRequisitionMstDtl.getLedgerName(); }
		 */

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_CS_REQUISITION);

		/*
		 * Get all State code which for local to Central Store requisition
		 * process
		 */

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get hierarchy information for minimum state code of LS item
		// requisition hierarchy
		ApprovalHierarchy approvalHierarchy = null;
		String[] roles = null;

		if (stateCodes.length > 0) {
			approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_REQUISITION, stateCodes[0].toString());
			roles = approvalHierarchy.getRoleName().split(",");
		}

		// login person have permission for requisition process??
		int flag = 0; // 0 = no permission
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1; // 1 = has permission
				break;
			}
		}

		// do next transaction if the role has permission for requisition??

		if (flag == 1) {

			// Requisition Items Details insert process Start from Here
			if (itemCodeList != null && itemCodeList.size() > 0) {

				// Saving master information of requisition to Master Table
				// Get and set requisition no from db
				String descoDeptCode = department.getDescoCode();
				String requisitionNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoRequisitionNoPrefix, descoDeptCode,
								separator, "LS_CS_REQ_SEQ");
				csRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

				CentralStoreRequisitionMst csRequisitionMstDb = (CentralStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CentralStoreRequisitionMst", "requisitionNo",
								csRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					CentralStoreRequisitionDtl csRequisitionDtl = new CentralStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						csRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						csRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						csRequisitionDtl.setUom(uomList.get(i));
					} else {
						csRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						csRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						csRequisitionDtl.setQuantityRequired(0);
					}

					/*
					 * if (!unitCostList.isEmpty()) {
					 * csRequisitionDtl.setUnitCost(unitCostList.get(i)); } else
					 * { csRequisitionDtl.setUnitCost(unitCostList.get(i)); }
					 * 
					 * if (!totalCostList.isEmpty()) {
					 * csRequisitionDtl.setTotalCost(totalCostList.get(i)); }
					 * else { csRequisitionDtl.setTotalCost(0); } if
					 * (!headOfAccountList.isEmpty()) {
					 * csRequisitionDtl.setHeadOfAccount
					 * (headOfAccountList.get(i)); } else {
					 * csRequisitionDtl.setHeadOfAccount(""); } // set Ledger
					 * Name if (!ledgerNameList.isEmpty()) {
					 * csRequisitionDtl.setLedgerName(ledgerNameList.get(i)); }
					 * else { csRequisitionDtl.setLedgerName(""); }
					 */

					csRequisitionDtl.setQuantityIssued(0.0);

					if (!remarksList.isEmpty()) {
						csRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						csRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					csRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					csRequisitionDtl.setRequisitionNo(csRequisitionMst
							.getRequisitionNo());
					csRequisitionDtl.setCreatedBy(csRequisitionMst
							.getCreatedBy());
					csRequisitionDtl.setCreatedDate(now);
					csRequisitionDtl.setActive(true);
					csRequisitionDtl
							.setCentralStoreRequisitionMst(csRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);

					// Allocation Update
					if (csRequisitionDtl.getItemCode().length() > 0) {
						DescoSession descoSession = (DescoSession) commonService
								.getCurrentDescoSession();

						List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
								.getObjectListByThreeColumn("AllocationTable",
										"sndCode", department.getDescoCode(),
										"itemCode",
										csRequisitionDtl.getItemCode(),
										"descoSession.id", descoSession.getId()
												+ "");
						AllocationTable allocationTable = null;
						if (allocationTableList.size() > 0) {
							allocationTable = (AllocationTable) allocationTableList
									.get(0);
							double usedQty = allocationTable.getUsedQuantity();
							double beanQty = quantityRequiredList.get(i);
							allocationTable.setUsedQuantity(usedQty + beanQty);
							commonService
									.saveOrUpdateModelObjectToDB(allocationTable);
						} else {
							continue;
						}
					}
					// Allocation Update End
				}

				// Start Approval Hierarchy History insert process
				addStoreRequisitionHierarchyHistory(csRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			CentralStoreRequisitionMst csRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new LsCsRequisitionApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(csRequisitionMst
				.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(LS_CS_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(csRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(now);
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			approvalHierarchyHistory.setStateName(approvalHierarchy
					.getStateName());
		}

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

	}

	@SuppressWarnings("unchecked")
	public boolean addLsSsStoreRequisitionDtls(
			SubStoreRequisitionMstDtl ssRequisitionMstDtl,
			SubStoreRequisitionMst ssRequisitionMst, String roleName,
			Departments department, AuthUser authUser, Date now) {
		// items comes as List from GUI
		List<String> itemCodeList = null;
		List<String> itemNameList = null;
		List<String> uomList = null;
		List<Double> quantityRequiredList = null;
		// List<Double> unitCostList = null;
		// List<Double> totalCostList = null;
		// List<String> ledgerNameList = null;
		List<String> remarksList = null;

		if (ssRequisitionMstDtl.getItemCode() != null) {
			itemCodeList = ssRequisitionMstDtl.getItemCode();
		}

		if (ssRequisitionMstDtl.getItemName() != null) {
			itemNameList = ssRequisitionMstDtl.getItemName();
		}

		if (ssRequisitionMstDtl.getUom() != null) {
			uomList = ssRequisitionMstDtl.getUom();
		}

		if (ssRequisitionMstDtl.getQuantityRequired() != null) {
			quantityRequiredList = ssRequisitionMstDtl.getQuantityRequired();
		}

		/*
		 * if (ssRequisitionMstDtl.getUnitCost() != null) { unitCostList =
		 * ssRequisitionMstDtl.getUnitCost(); }
		 * 
		 * if (ssRequisitionMstDtl.getTotalCost() != null) { totalCostList =
		 * ssRequisitionMstDtl.getTotalCost(); } if
		 * (ssRequisitionMstDtl.getLedgerName() != null) { ledgerNameList =
		 * ssRequisitionMstDtl.getLedgerName(); }
		 * 
		 * if (ssRequisitionMstDtl.getHeadOfAccount() != null) {
		 * headOfAccountList = ssRequisitionMstDtl.getHeadOfAccount(); }
		 */

		if (ssRequisitionMstDtl.getRemarks() != null) {
			remarksList = ssRequisitionMstDtl.getRemarks();
		}

		// Get All Approval Hierarchy on LS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_SS_REQUISITION);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get hierarchy information for minimum state code of LS item
		// requisition hierarchy
		ApprovalHierarchy approvalHierarchy = null;
		String[] roles = null;

		if (stateCodes.length > 0) {
			approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_SS_REQUISITION, stateCodes[0].toString());
			roles = approvalHierarchy.getRoleName().split(",");
		}

		// login person have permission for requisition process??
		int flag = 0; // 0 = no permission
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].equals(roleName)) {
				flag = 1; // 1 = has permission
				break;
			}
		}

		// do next transaction if the role has permission for requisition??

		if (flag == 1) {

			// Requisition Items Details insert process Start from Here
			if (itemCodeList != null && itemCodeList.size() > 0) {

				// Saving master information of requisition to Master Table
				// Get and set requisition no from db
				String descoDeptCode = department.getDescoCode();
				String requisitionNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoRequisitionNoPrefix, descoDeptCode,
								separator, "LS_CS_REQ_SEQ");
				ssRequisitionMst.setRequisitionNo(requisitionNo);
				commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);

				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"requisitionNo",
								ssRequisitionMst.getRequisitionNo());
				for (int i = 0; i < itemCodeList.size(); i++) {
					SubStoreRequisitionDtl ssRequisitionDtl = new SubStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						ssRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						ssRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						ssRequisitionDtl.setUom(uomList.get(i));
					} else {
						ssRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						ssRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						ssRequisitionDtl.setQuantityRequired(0);
					}

					/*
					 * if (!unitCostList.isEmpty()) {
					 * ssRequisitionDtl.setUnitCost(unitCostList.get(i)); } else
					 * { ssRequisitionDtl.setUnitCost(unitCostList.get(i)); }
					 * 
					 * if (!totalCostList.isEmpty()) {
					 * ssRequisitionDtl.setTotalCost(totalCostList.get(i)); }
					 * else { ssRequisitionDtl.setTotalCost(0); }
					 * 
					 * if (!ledgerNameList.isEmpty()) {
					 * ssRequisitionDtl.setLedgerName(ledgerNameList.get(i)); }
					 * else { ssRequisitionDtl.setLedgerName(""); }
					 */

					if (!remarksList.isEmpty()) {
						ssRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						ssRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					ssRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					ssRequisitionDtl.setRequisitionNo(ssRequisitionMst
							.getRequisitionNo());
					ssRequisitionDtl.setCreatedBy(ssRequisitionMst
							.getCreatedBy());
					ssRequisitionDtl.setCreatedDate(now);
					ssRequisitionDtl.setActive(true);
					ssRequisitionDtl
							.setSubStoreRequisitionMst(ssRequisitionMstDb);

					// insert item detail in ssRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);

					// Allocation Update
					if (ssRequisitionDtl.getItemCode().length() > 0) {
						DescoSession descoSession = (DescoSession) commonService
								.getCurrentDescoSession();
						List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
								.getObjectListByThreeColumn("AllocationTable",
										"sndCode", department.getDescoCode(),
										"itemCode",
										ssRequisitionDtl.getItemCode(),
										"descoSession.id", descoSession.getId()
												+ "");
						AllocationTable allocationTable = null;
						if (allocationTableList.size() > 0) {
							allocationTable = (AllocationTable) allocationTableList
									.get(0);
							double usedQty = allocationTable.getUsedQuantity();
							double beanQty = quantityRequiredList.get(i);
							allocationTable.setUsedQuantity(usedQty + beanQty);
							commonService
									.saveOrUpdateModelObjectToDB(allocationTable);
						} else {
							continue;
						}
					}
					// Allocation Update End
				}

				// Start Approval Hierarchy History insert process
				addLsSsStoreRequisitionHierarchyHistory(ssRequisitionMst,
						approvalHierarchy, stateCodes, roleName, department,
						authUser, now);

			}
			return true;
		} else {
			return false;
		}

	}

	public void addLsSsStoreRequisitionHierarchyHistory(
			SubStoreRequisitionMst ssRequisitionMst,
			ApprovalHierarchy approvalHierarchy, Integer[] stateCodes,
			String roleName, Departments department, AuthUser authUser, Date now) {
		LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new LsSsRequisitionApprovalHierarchyHistory();
		approvalHierarchyHistory.setActRoleName(roleName);
		approvalHierarchyHistory.setcDeptName(department.getDeptName());
		approvalHierarchyHistory.setDeptId(department.getDeptId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setOperationId(ssRequisitionMst
				.getRequisitionNo());
		approvalHierarchyHistory.setOperationName(LS_SS_REQUISITION);
		approvalHierarchyHistory.setCreatedBy(ssRequisitionMst.getCreatedBy());
		approvalHierarchyHistory.setCreatedDate(now);
		approvalHierarchyHistory.setStatus(OPEN);
		approvalHierarchyHistory.setActive(true);

		if (stateCodes.length > 0) {
			// All time start with 1st
			// State code set from approval Hierarchy Table
			approvalHierarchyHistory.setStateCode(stateCodes[0]);
			approvalHierarchyHistory.setStateName(approvalHierarchy
					.getStateName());
		}

		// Insert a row to Approval Hierarchy History Table
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/requisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showReqisitionListofLocal() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<CnSsRequisitionApprovalHierarchyHistory> cnSsRequisitionHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<CnCsRequisitionApprovalHierarchyHistory> cnCsRequisitionHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<CnWsCsRequisitionApprovalHierarchyHistory> cnWsCsRequisitionHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		// nasrin.ibcs
		List<WsCsRequisitionApprovalHierarchyHistory> wsCsRequisitionHistoryList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<WsCsXRequisitionApprovalHierarchyHistory> wsCsXRequisitionHistoryList = (List<WsCsXRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsXRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		// added by Ashid
		List<LsPdCsRequisitionApprovalHierarchyHistory> lsPdCsRequisitionHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		// added by Ashid
		List<LsPdSsRequisitionApprovalHierarchyHistory> lsPdSsRequisitionHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList1 = new String[lsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList1[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList2 = new String[ssCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList2[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList3 = new String[lsSsRequisitionHistoryList
				.size()];

		for (int i = 0; i < lsSsRequisitionHistoryList.size(); i++) {
			operationIdList3[i] = lsSsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList4 = new String[cnSsRequisitionHistoryList
				.size()];

		for (int i = 0; i < cnSsRequisitionHistoryList.size(); i++) {
			operationIdList4[i] = cnSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList5 = new String[cnCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < cnCsRequisitionHistoryList.size(); i++) {
			operationIdList5[i] = cnCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		// nasrin.ibcs
		String[] operationIdList6 = new String[wsCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < wsCsRequisitionHistoryList.size(); i++) {
			operationIdList6[i] = wsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList7 = new String[wsCsXRequisitionHistoryList
				.size()];
		for (int i = 0; i < wsCsXRequisitionHistoryList.size(); i++) {
			operationIdList7[i] = wsCsXRequisitionHistoryList.get(i)
					.getOperationId();
		}// nasrin.ibcs

		/* cnWsCsRequisitionHistoryList */
		String[] operationIdList8 = new String[cnWsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < cnWsCsRequisitionHistoryList.size(); i++) {
			operationIdList8[i] = cnWsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		// lsPdCsRequisitionHistoryList
		String[] operationIdList9 = new String[lsPdCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsPdCsRequisitionHistoryList.size(); i++) {
			operationIdList9[i] = lsPdCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		// lsPdSsRequisitionHistoryList
		String[] operationIdList10 = new String[lsPdSsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsPdSsRequisitionHistoryList.size(); i++) {
			operationIdList10[i] = lsPdSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		CentralStoreRequisitionMst cs = new CentralStoreRequisitionMst();
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = new ArrayList<CentralStoreRequisitionMst>();
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList1 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList1);
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList2 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList2);
		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList3);
		List<SubStoreRequisitionMst> subStoreRequisitionMstList2 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList4);
		// Added By Ashid
		List<SubStoreRequisitionMst> subStoreRequisitionMstList3 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList10);

		// Added By Ashid
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList5 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList5);

		// nasrin.ibcs
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList6 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList6);
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList7 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList7);

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList8 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList8);
		// nasrin.ibcs

		// Added By Ashid
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList9 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList9);

		if (subStoreRequisitionMstList2 != null) {
			if (subStoreRequisitionMstList != null) {
				subStoreRequisitionMstList.addAll(subStoreRequisitionMstList2);
			} else {
				for (SubStoreRequisitionMst ss : subStoreRequisitionMstList2) {
					cs = new CentralStoreRequisitionMst();
					centralStoreRequisitionMstList.add(importObjToCs(ss));
				}
			}
		}

		// Added By Ashid
		if (subStoreRequisitionMstList3 != null) {
			if (subStoreRequisitionMstList != null) {
				subStoreRequisitionMstList.addAll(subStoreRequisitionMstList3);
			} else {
				for (SubStoreRequisitionMst ss : subStoreRequisitionMstList3) {
					cs = new CentralStoreRequisitionMst();
					centralStoreRequisitionMstList.add(importObjToCs(ss));
				}
			}
		}

		if (centralStoreRequisitionMstList1 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList1);
		}
		if (centralStoreRequisitionMstList2 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList2);
		}

		if (centralStoreRequisitionMstList5 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList5);
		}

		// nasrin.ibcs
		if (centralStoreRequisitionMstList6 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList6);
		}
		if (centralStoreRequisitionMstList7 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList7);
		}// nasrin.ibcs

		if (centralStoreRequisitionMstList8 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList8);
		}

		// added by ashid

		if (centralStoreRequisitionMstList9 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList9);
		}

		if (subStoreRequisitionMstList != null) {
			for (SubStoreRequisitionMst ss : subStoreRequisitionMstList) {
				// cs = new CentralStoreRequisitionMst();
				centralStoreRequisitionMstList.add(importObjToCs(ss));
			}
		}

		for (CentralStoreRequisitionMst reqMst : centralStoreRequisitionMstList) {
			if (reqMst.getSndCode() != null) {
				Departments d = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments",
								"descoCode", reqMst.getSndCode());
				if (d != null) {
					reqMst.setSndCode(d.getDeptName());
				} else {
					Departments contractorDept = (Departments) commonService
							.getAnObjectByAnyUniqueColumn("Departments",
									"deptId", reqMst.getSndCode());
					reqMst.setSndCode(contractorDept.getDeptName());
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList",
				centralStoreRequisitionMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		} else if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionList", model);
		} else if (roleName.equals("ROLE_PROJECT_DIRECTOR")) {
			return new ModelAndView("localStore/lspdRequisitionList", model);
		} else {
			return new ModelAndView("localStore/csRequisitionList", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm(String msg) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		List<DescoKhath> descoKhathList = (List<DescoKhath>) (Object) commonService
				.getAllObjectList("DescoKhath");

		model.put("msg", msg);
		model.put("categoryList", categoryList);
		model.put("descoKhathList", descoKhathList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsToCsStoreRequisitionForm", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(CentralStoreRequisitionMst bean)
			throws Exception {

		String requisitionTo = bean.getRequisitionTo();
		String reqMstId = bean.getId().toString();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		if (requisitionTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {

			SubStoreRequisitionMst subStoreRequisitionMstdb = (SubStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
							"id", reqMstId);
			if (subStoreRequisitionMstdb.getKhathId() > 1) {
				return new ModelAndView(
						"redirect:/ls/pd/requisitionShow.do?id=" + reqMstId
								+ "&requisitionTo=" + requisitionTo);
			}

			return getStoreRequisitionShowOfLocalToSub(subStoreRequisitionMstdb);

		}

		CentralStoreRequisitionMst centralStoreRequisitionMstdb = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"id", reqMstId);

		if (centralStoreRequisitionMstdb.getKhathId() > 1) {
			return new ModelAndView("redirect:/ls/pd/requisitionShow.do?id="
					+ reqMstId + "&requisitionTo=" + requisitionTo);
		}

		// Set full name of Requisition Creator
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						centralStoreRequisitionMstdb.getCreatedBy());
		centralStoreRequisitionMstdb.setCreatedBy(createBy.getName());

		Integer khathId = centralStoreRequisitionMstdb.getKhathId();

		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);
		// This Block will set Curent stock of CS
		for (int i = 0; i < centralStoreRequisitionDtlList.size(); i++) {
			String itemCode = centralStoreRequisitionDtlList.get(i)
					.getItemCode();
			List<CSItemTransactionMst> csItemTnxMstRecovery = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName",
							RECOVERY_SERVICEABLE);

			List<CSItemTransactionMst> csItemTnxMstNew = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName", NEW_SERVICEABLE);

			if (csItemTnxMstRecovery.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(
								csItemTnxMstRecovery.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(0.0);
			}

			if (csItemTnxMstNew.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(
								csItemTnxMstNew.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(0.0);
			}
		}

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, DONE);

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 */

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, OPEN);

		int currentStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
				.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Roles", "role_id",
						roleIdList);
		// App_hier List by RoleList & Op name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(
						LS_CS_REQUISITION, roleNameList);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!sCsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sCsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sCsRequisitionApprovalHierarchyHistoryOpenList.get(
					sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								LS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get all Location under CS || Added by Ashid
		List<StoreLocations> storeLocationList = this
				.getStoreLocationList("CS");

		/* Following four line are added by Ihteshamul Alam */
		// String userrole =
		// centralStoreRequisitionMstdb.getCreatedBy().toLowerCase();
		// AuthUser createdBy = (AuthUser) commonService
		// .getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser",
		// "userid", userrole);
		// if( createdBy.getName() == null ) { }
		// else {
		// centralStoreRequisitionMstdb.setCreatedBy( createdBy.getName() );
		// }
		//

		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		DescoKhath dsKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", reqMstId);

		model.put("dsKhath", dsKhath);
		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("locationList", storeLocationList);

		model.put("reqTo", ContentType.CENTRAL_STORE.toString());
		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		model.put("deptName", department.getDeptName());

		model.put("categoryList", categoryList);

		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		{
			// Start m@@ || Generate drop down list for location in Grid
			List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "CS", "parentId");
			String locationOptions = commonService
					.getLocationListForGrid(locationsList);
			String ledgerBookList = commonService.getLedgerListForGrid();
			model.put("locationList", locationOptions);
			model.put("ledgerBookList", ledgerBookList);

			// End m@@ || Generate drop down list for location in Grid
		}

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsRequisitionShow", model);
		} else if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssRequisitionShow", model);
		} else {
			return new ModelAndView("localStore/csRequisitionShow", model);
		}

	}

	// Store Requisition (SR) Approving process
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemRequisitionSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		boolean storeChangeFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();
		
		// get Requisites Item List against SR No
		String operationId = csRequisitionMstDtl.getRequisitionNo();

		CentralStoreRequisitionMst csRequisitionMst = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", operationId);

		// Send return to next user who backed me
		if (!csRequisitionMstDtl.getReturn_state().equals("")
				|| csRequisitionMstDtl.getReturn_state().length() > 0) {

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo",
							csRequisitionMstDtl.getRequisitionNo());

			List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							centralStoreRequisitionDtl
									.setQuantityIssued(issuedqty);
						} else {
							centralStoreRequisitionDtl
									.setQuantityIssued(centralStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
						counter++;
					}
				}
			}

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId",
							csRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
					.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(csRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_CS_REQUISITION,
							csRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(csRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(csRequisitionMstDtl.getRequisitionNo());
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setcDeptName(department
					.getDeptName());
			approvalHierarchyHistoryNextState.setcDesignation(authUser
					.getDesignation());

			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {			

			List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"requisitionNo", operationId);

			// Get All State Codes from Approval Hierarchy
			// and sort Descending order for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
					.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;
			// update issued qty

			List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							centralStoreRequisitionDtl
									.setQuantityIssued(issuedqty);
						} else {
							centralStoreRequisitionDtl
									.setQuantityIssued(centralStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
						counter++;
					}
				}
			}

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_CS_REQUISITION, nextStateCode + "");

					// next role name
					// next role id
					// next role users dept

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
							.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {

					} else {
						Roles role = (Roles) commonService
								.getAnObjectByAnyUniqueColumn(
										"com.ibcs.desco.admin.model.Roles",
										"role", approvalHierarchy.getRoleName());
						List<AuthUser> userList = (List<AuthUser>) (Object) commonService
								.getObjectListByAnyColumn(
										"com.ibcs.desco.admin.model.AuthUser",
										"roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart
								.getDeptName());

						storeChangeFlag = true;
					}
					// AuthUser

					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy
							.getStateName());

					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy
							.getRoleName());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process
				// will done for login user
				if (state == currentStateCode) {

					// item Quantity History setup Start :: added by Shimul
					String historyChange = "";
					int counter = 0;
					if (issuedQtyList != null) {
						for (int i = 0; i < issuedQtyList.size(); i++) {
							if (counter == 0) {
								historyChange += centralStoreRequisitionDtlList
										.get(i).getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ centralStoreRequisitionDtlList.get(i)
												.getUom();
								counter++;
							} else {
								historyChange += ",   "
										+ centralStoreRequisitionDtlList.get(i)
												.getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ centralStoreRequisitionDtlList.get(i)
												.getUom();
							}
						}
					}
					approvalHierarchyHistory.setHistoryChange(historyChange);
					// item Quantity History setup end

					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_CS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(csRequisitionMstDtl
									.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now we have to insert data in store ticket mst and
					// history
					CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
					// csStoreTicketMst.setTicketNo(ticketNo);
					csStoreTicketMst.setStoreTicketType(LS_CS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(csRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(csRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);
					csStoreTicketMst.setKhathId(csRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(csRequisitionMst
							.getKhathName());
					csStoreTicketMst.setSndCode(csRequisitionMst.getSndCode());

					// Auto generate Store Ticket number
					String descoDeptCode = department.getDescoCode();

					// csStoreTicketMst.setIssuedBy(csRequisitionMst.getRequisitionTo());
					csStoreTicketMst.setIssuedBy(authUser.getName() + "("
							+ userName + ")");
					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "CS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);
					csStoreTicketMst.setReceivedBy(csRequisitionMst
							.getReceivedBy());
					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

					// Requisition mst update
					csRequisitionMst.setStoreTicketNO(storeTicketNo);
					csRequisitionMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);

					CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
									"operationId", operationId);

					//

					// Get All Approval Hierarchy on CS_STORE_TICKET
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

					Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
							.size()];
					for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
						sStoreTicketStateCodes[i] = approvalHierarchyListDb
								.get(i).getStateCode();
					}
					Arrays.sort(sStoreTicketStateCodes);

					// get approve hierarchy for last state
					ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									CS_STORE_TICKET,
									sStoreTicketStateCodes[0].toString());

					StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

					storeTicketApprovalHierarchyHistory
							.setActRoleName(storeTicketpprovalHierarchy
									.getRoleName());
					storeTicketApprovalHierarchyHistory
							.setOperationId(operationId);
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory.setcDeptName(department
							.getDeptName());
					storeTicketApprovalHierarchyHistory
							.setcDesignation(authUser.getDesignation());
					storeTicketApprovalHierarchyHistory
							.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory
							.setOperationName(CS_STORE_TICKET);
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
					storeTicketApprovalHierarchyHistory
							.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						storeTicketApprovalHierarchyHistory
								.setStateCode(sStoreTicketStateCodes[0]);
						storeTicketApprovalHierarchyHistory
								.setStateName(storeTicketpprovalHierarchy
										.getStateName());
					}
					storeTicketApprovalHierarchyHistory.setStatus(OPEN);
					storeTicketApprovalHierarchyHistory.setActive(true);
					// process will done and go for store ticket
					commonService
							.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

					// model.addAttribute("operationId", operationId);

					// return "centralStore/csRequisitionReport";
					return "redirect:/ls/csRequisitionReport.do?srNo="
							+ operationId;

				}
			}
		}
		if (storeChangeFlag) {
			//added by Taleb
			csRequisitionMst.setRecommend("1");
			commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);
			
			// model.addAttribute("operationId",
			// csRequisitionMstDtl.getRequisitionNo());
			// return "localStore/reports/lsCsRequisitionReport";
			//String operationId = csRequisitionMstDtl.getRequisitionNo();
			return "redirect:/ls/showRequisitionReportCS.do?srNo="
					+ operationId;
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}

	@RequestMapping(value = "/showRequisitionReportCS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportCS(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("localStore/reports/lsCsRequisitionReport",
				model);
	}

	@RequestMapping(value = "/csRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csRequisitionReport(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("centralStore/csRequisitionReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/sendTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemSendTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String rrNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", csRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						centralStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						centralStoreRequisitionDtl
								.setQuantityIssued(centralStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
				.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(rrNo + "");
		approvalHierarchyHistoryNextState
				.setOperationName(approvalHierarchyNextSate.getOperationName());
		approvalHierarchyHistoryNextState
				.setActRoleName(approvalHierarchyNextSate.getRoleName());
		approvalHierarchyHistoryNextState
				.setApprovalHeader(approvalHierarchyNextSate
						.getApprovalHeader());

		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryNextState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		return "redirect:/ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/backTo.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String centralSotreReceivedItemBackTo(
			Model model,
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		String rrNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String backStateCode = csRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", csRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = csRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (CentralStoreRequisitionDtl centralStoreRequisitionDtl : centralStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (centralStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						centralStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						centralStoreRequisitionDtl
								.setQuantityIssued(centralStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(centralStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsCsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
				.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_CS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(rrNo + "");
		approvalHierarchyHistoryBackState
				.setOperationName(approvalHierarchyBackSate.getOperationName());
		approvalHierarchyHistoryBackState
				.setActRoleName(approvalHierarchyBackSate.getRoleName());
		approvalHierarchyHistoryBackState
				.setApprovalHeader(approvalHierarchyBackSate
						.getApprovalHeader());

		// for return same user
		approvalHierarchyHistoryBackState.setStage(BACK);
		approvalHierarchyHistoryBackState.setReturn_to(roleName);
		approvalHierarchyHistoryBackState
				.setReturn_state(currentStateCode + "");

		approvalHierarchyHistoryBackState.setDeptId(deptId);
		approvalHierarchyHistoryBackState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryBackState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionSearch(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		String operationId = csRequisitionMstDtl.getRequisitionNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN, operationId);

		String[] operationIdList = new String[lsCsRequisitionHistoryList.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList",
				centralStoreRequisitionMstList);

		String rolePrefix = roleName.substring(0, 7);

		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionList", model);
		} else {
			return new ModelAndView("localStore/csRequisitionList", model);
		}

	}

	@RequestMapping(value = "/viewInventoryItemCategory.do", method = RequestMethod.POST)
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
			Thread.sleep(125 * 1000);
		}

		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInventoryItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String viewInventoryItem(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = isJSONValid(json);
		String toJson = "";

		DescoKhath dk = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		if (isJson) {
			ItemMaster invItem = gson.fromJson(json, ItemMaster.class);
			ItemMaster selectItemFromDb = itemInventoryService
					.getInventoryItemById(invItem.getId());

			/*
			 * CentralStoreItems centralStoreItems = centralStoreItemsService
			 * .getCentralStoreItemsByItemId(selectItemFromDb.getItemId());
			 */
			Double presentQty = 0.0;
			if (invItem.getRequisitionTo().equals(
					ContentType.CENTRAL_STORE.toString())) {
				List<CSItemTransactionMst> tnxMstList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								NEW_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				List<CSItemTransactionMst> tnxMstList1 = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("CSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								RECOVERY_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				if (tnxMstList.size() > 0) {
					presentQty = tnxMstList.get(0).getQuantity();
				}

				if (tnxMstList1.size() > 0) {
					presentQty += tnxMstList1.get(0).getQuantity();
				}

				if (presentQty != null) {
					presentQty = presentQty
							- getBookingQtyLsCsReq(
									selectItemFromDb.getItemId(), dk.getId()
											.toString());
				}

			} else if (invItem.getRequisitionTo().equals(
					ContentType.SUB_STORE.toString())) {
				List<SSItemTransactionMst> tnxMstList = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								NEW_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				List<SSItemTransactionMst> tnxMstList1 = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByThreeColumn("SSItemTransactionMst",
								"khathId", dk.getId() + "", "ledgerName",
								RECOVERY_SERVICEABLE, "itemCode",
								selectItemFromDb.getItemId());

				if (tnxMstList.size() > 0) {
					presentQty = tnxMstList.get(0).getQuantity();
				}

				if (tnxMstList1.size() > 0) {
					presentQty += tnxMstList1.get(0).getQuantity();
				}

				if (presentQty != null) {
					presentQty = presentQty
							- getBookingQtyLsSsReq(
									selectItemFromDb.getItemId(), dk.getId()
											.toString());
				}
			}

			selectItemFromDb.setCurrentStock(presentQty != null ? presentQty
					: 0.0);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(selectItemFromDb);

		} else {
			Thread.sleep(3000);
		}

		return toJson;
	}

	@SuppressWarnings("unchecked")
	public Double getBookingQtyLsCsReq(String itemCode, String khathId) {
		Double bookingQty = 0.0;

		List<CSStoreTicketMst> csTicketList = (List<CSStoreTicketMst>) (Object) commonService
				.getObjectListByThreeColumn("CSStoreTicketMst", "approved",
						"0", "storeTicketType", "LS_CS_REQUISITION", "khathId",
						khathId);
		for (CSStoreTicketMst m : csTicketList) {
			List<CentralStoreRequisitionDtl> reqDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByThreeColumn("CentralStoreRequisitionDtl",
							"itemCode", itemCode, "requisitionNo",
							m.getOperationId(),
							"centralStoreRequisitionMst.khathId", khathId);
			for (CentralStoreRequisitionDtl r : reqDtlList) {
				bookingQty += r.getQuantityIssued();
			}

		}
		return bookingQty;

	}

	@SuppressWarnings("unchecked")
	public Double getBookingQtyLsSsReq(String itemCode, String khathId) {
		Double bookingQty = 0.0;

		List<SSStoreTicketMst> ssTicketList = (List<SSStoreTicketMst>) (Object) commonService
				.getObjectListByThreeColumn("SSStoreTicketMst", "approved",
						"0", "storeTicketType", "LS_SS_REQUISITION", "khathId",
						khathId);
		for (SSStoreTicketMst m : ssTicketList) {
			List<SubStoreRequisitionDtl> reqDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByThreeColumn("SubStoreRequisitionDtl",
							"itemCode", itemCode, "requisitionNo",
							m.getOperationId(),
							"subStoreRequisitionMst.khathId", khathId);
			for (SubStoreRequisitionDtl r : reqDtlList) {
				bookingQty += r.getQuantityIssued();
			}

		}
		return bookingQty;

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reqisitionListofLocalToSub.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView reqisitionListToSub() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[lsSsRequisitionHistoryList.size()];
		for (int i = 0; i < lsSsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("subStoreRequisitionMstList", subStoreRequisitionMstList);
		// added by taleb
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionList", model);
		}
		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("subStore/ssToCsRequisitionList", model);
		} else {
			return new ModelAndView("subStore/csRequisitionList", model);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionShowOfLocalToSub.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShowOfLocalToSub(
			SubStoreRequisitionMst subStoreRequisitionMst) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// Test Dual
		// Long abc = commonService.getNextVal();
		//

		SubStoreRequisitionMst subStoreRequisitionMstdb = subStoreRequisitionMstService
				.getSubStoreRequisitionMst(subStoreRequisitionMst.getId());
		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", operationId);

		String khath_id = subStoreRequisitionMstdb.getKhathId().toString();

		// This Block will set Current stock of SS
		for (int i = 0; i < subStoreRequisitionDtlList.size(); i++) {
			String itemCode = subStoreRequisitionDtlList.get(i).getItemCode();
			List<SSItemTransactionMst> ssItemTnxMstRecovery = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("SSItemTransactionMst",
							"itemCode", itemCode, "khathId", khath_id,
							"ledgerName", RECOVERY_SERVICEABLE);

			List<SSItemTransactionMst> ssItemTnxMstNew = (List<SSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("SSItemTransactionMst",
							"itemCode", itemCode, "khathId", khath_id,
							"ledgerName", NEW_SERVICEABLE);

			if (ssItemTnxMstRecovery.size() > 0) {
				subStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(
								ssItemTnxMstRecovery.get(0).getQuantity());
			} else {
				subStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(0.0);
			}

			if (ssItemTnxMstNew.size() > 0) {
				subStoreRequisitionDtlList.get(i).setNewServiceableStockQty(
						ssItemTnxMstNew.get(0).getQuantity());
			} else {
				subStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(0.0);
			}
		}

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionApprovalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory",
						LS_SS_REQUISITION, operationId, DONE);

		/*
		 * List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
		 * .getApprovalHierarchyByOperationName(LS_CS_REQUISITION);
		 */

		if (!lsSsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = lsSsRequisitionApprovalHierarchyHistoryList.get(
					lsSsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionApprovalHierarchyHistoryOpenList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory",
						LS_SS_REQUISITION, operationId, OPEN);

		int currentStateCode = lsSsRequisitionApprovalHierarchyHistoryOpenList
				.get(lsSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Roles", "role_id",
						roleIdList);
		// App_hier List by RoleList & Op name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(
						LS_SS_REQUISITION, roleNameList);
		/* buttonValue = approveHeirchyList.get(0).getButtonName(); */

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!lsSsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& lsSsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = lsSsRequisitionApprovalHierarchyHistoryOpenList
					.get(lsSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = lsSsRequisitionApprovalHierarchyHistoryOpenList
					.get(lsSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								LS_SS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		List<StoreLocations> storeLocationList = this
				.getStoreLocationList("SS");
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("returnStateCode", returnStateCode);
		model.put("subStoreRequisitionMst", subStoreRequisitionMstdb);
		model.put("senderStore", subStoreRequisitionMst.getSenderStore());
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("locationList", storeLocationList);

		model.put("itemRcvApproveHistoryList",
				lsSsRequisitionApprovalHierarchyHistoryList);
		model.put("subStoreRequisitionDtlList", subStoreRequisitionDtlList);
		model.put("reqTo", ContentType.SUB_STORE.toString());

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		{
			// Start m@@ || Generate drop down list for location in Grid
			List<StoreLocations> locationsList = (List<StoreLocations>) (Object) commonService
					.getObjectListByTwoColumnWithOneNullValue("StoreLocations",
							"storeCode", "SS", "parentId");
			String locationOptions = commonService
					.getLocationListForGrid(locationsList);
			String ledgerBookList = commonService.getLedgerListForGrid();

			model.put("locationList", locationOptions);
			model.put("ledgerBookList", ledgerBookList);
			// End m@@ || Generate drop down list for location in Grid
		}

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();
		model.put("categoryList", categoryList);

		DescoKhath dsKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						subStoreRequisitionMst.getKhathId().toString());
		model.put("dsKhath", dsKhath);
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsRequisitionShowToSub", model);
		}
		if (rolePrefix.equals("ROLE_SS")) {
			return new ModelAndView("localStore/ssRequisitionShow", model);
		} else {
			return new ModelAndView("subStore/csRequisitionShow", model);
		}
	}

	//

	// SELECT Location object List by parent location id || Added by ashid
	@RequestMapping(value = "/ss/sr/saveLocation7sDtl.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String saveLocationDtl(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		Double totalQty = 0.0;
		if (isJson) {
			Location7sMstDtl obj = gson.fromJson(json, Location7sMstDtl.class);
			List<Location7sQty> locationList = obj.getLocationList();
			for (Location7sQty loc7sQty : locationList) {
				TempItemLocation temp = new TempItemLocation();
				if (loc7sQty.getQuantity() > 0) {
					if (loc7sQty.getLocation() != null
							&& !loc7sQty.getLocation().equals("")) {
						temp.setUuid(obj.getUuid());
						temp.setItemCode(obj.getItemCode());
						temp.setLedgerName(loc7sQty.getLedgerName());
						temp.setLocationId(loc7sQty.getLocation());
						temp.setLocationName(commonService
								.getStoreLocationNamebyId(loc7sQty
										.getLocation()));
						// temp.setQuantity(loc7sQty.getQuantity());
						DecimalFormat df = new DecimalFormat("#.###");
						double qty = Double.parseDouble(df.format(loc7sQty
								.getQuantity()));
						temp.setQuantity(qty);

						// check for last location and set to ChildLocation
						if (loc7sQty.getGodown() != null
								&& !loc7sQty.getGodown().equals("")) {
							temp.setGodownId(loc7sQty.getGodown());
							temp.setChildLocationId(loc7sQty.getGodown());
							temp.setChildLocationName(commonService
									.getStoreLocationNamebyId(loc7sQty
											.getGodown()));
							if (loc7sQty.getFloor() != null
									&& !loc7sQty.getFloor().equals("")) {
								temp.setFloorId(loc7sQty.getFloor());
								temp.setChildLocationId(loc7sQty.getFloor());
								temp.setChildLocationName(commonService
										.getStoreLocationNamebyId(loc7sQty
												.getFloor()));
								if (loc7sQty.getSector() != null
										&& !loc7sQty.getSector().equals("")) {
									temp.setSectorId(loc7sQty.getSector());
									temp.setChildLocationId(loc7sQty
											.getSector());
									temp.setChildLocationName(commonService
											.getStoreLocationNamebyId(loc7sQty
													.getSector()));
									if (loc7sQty.getRake() != null
											&& !loc7sQty.getRake().equals("")) {
										temp.setRakeId(loc7sQty.getRake());
										temp.setChildLocationId(loc7sQty
												.getRake());
										temp.setChildLocationName(commonService
												.getStoreLocationNamebyId(loc7sQty
														.getRake()));
										if (loc7sQty.getBin() != null
												&& !loc7sQty.getBin()
														.equals("")) {
											temp.setBinId(loc7sQty.getBin());
											temp.setChildLocationId(loc7sQty
													.getBin());
											temp.setChildLocationName(commonService
													.getStoreLocationNamebyId(loc7sQty
															.getBin()));
										}
									}
								}
							}
						}
						// now save to db
						if (loc7sQty.getId().equals("0")) {
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
						} else {
							temp.setId(Integer.parseInt(loc7sQty.getId()));
							temp.setCreatedBy(commonService.getAuthUserName());
							temp.setCreatedDate(new Date());
							temp.setModifiedBy(commonService.getAuthRoleName());
							temp.setModifiedDate(new Date());
						}
						commonService.saveOrUpdateModelObjectToDB(temp);

						// totalQty += loc7sQty.getQuantity();
						totalQty += qty;

					}
				}
			}

			toJson = ow.writeValueAsString(totalQty);

		} else {
			Thread.sleep(5000);
			toJson = ow.writeValueAsString("Failed");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemRequisitionSubmitApprovedLocalToSub.do", method = RequestMethod.GET)
	public String itemRequisitionSubmitApprovedOfLocalToSub(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		boolean storeChangeFlag = false;

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		String deptId = authUser.getDeptId();
		
		// get Item Requisition List against SR No
		String operationId = ssRequisitionMstDtl.getRequisitionNo();

		SubStoreRequisitionMst ssRequisitionMst = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"requisitionNo", operationId);

		// Send return to next user who backed me
		if (!ssRequisitionMstDtl.getReturn_state().equals("")
				|| ssRequisitionMstDtl.getReturn_state().length() > 0) {

			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"operationId",
							ssRequisitionMstDtl.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());
			// get current State Code and all info from approval hierarchy
			// history
			LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsSsRequisitionApprovalHierarchyHistoryService
					.getLsSsRequisitionApprovalHierarchyHistory(ids[0]);
			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_SS_REQUISITION, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setJustification(ssRequisitionMstDtl
					.getJustification());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsSsRequisitionApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							LS_SS_REQUISITION,
							ssRequisitionMstDtl.getReturn_state());

			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);
			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(ssRequisitionMstDtl.getReturn_state()));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState
					.setOperationId(ssRequisitionMstDtl.getRequisitionNo());
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setcDeptName(department
					.getDeptName());
			approvalHierarchyHistoryNextState.setcDesignation(authUser
					.getDesignation());

			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		} else {
			
			List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl",
							"requisitionNo", operationId);

			// get All State Codes from Approval Hierarchy and sort Desending
			// oder
			// for highest State Code
			List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_SS_REQUISITION);
			Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
			for (int i = 0; i < approvalHierarchyList.size(); i++) {
				stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
			}
			Arrays.sort(stateCodes);

			// get Current State Code from Approval hierarchy by RR No
			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"operationId", operationId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsSsRequisitionApprovalHierarchyHistoryService
					.getLsSsRequisitionApprovalHierarchyHistory(ids[0]);

			int currentStateCode = approvalHierarchyHistory.getStateCode();
			int nextStateCode = 0;

			// update issued qty
			List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
			if (issuedQtyList != null) {
				if (issuedQtyList.size() > 0) {
					int counter = 0;
					for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
						double issuedqty = Double.parseDouble(issuedQtyList
								.get(counter));
						if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
							subStoreRequisitionDtl.setQuantityIssued(issuedqty);
						} else {
							subStoreRequisitionDtl
									.setQuantityIssued(subStoreRequisitionDtl
											.getQuantityRequired());
						}
						commonService
								.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
						counter++;
					}
				}
			}

			// searching next State code and decision for send this RR to next
			// person
			for (int state : stateCodes) {

				// if next state code grater than current state code than this
				// process will go to next person
				if (state > currentStateCode) {
					nextStateCode = state;
					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_SS_REQUISITION, nextStateCode + "");

					// next role name
					// next role id
					// next role users dept

					String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
							.getRoleName().substring(0, 8);
					// checking department switching
					if (r1.equals(r2)) {

					} else {
						Roles role = (Roles) commonService
								.getAnObjectByAnyUniqueColumn(
										"com.ibcs.desco.admin.model.Roles",
										"role", approvalHierarchy.getRoleName());
						List<AuthUser> userList = (List<AuthUser>) (Object) commonService
								.getObjectListByAnyColumn(
										"com.ibcs.desco.admin.model.AuthUser",
										"roleid", role.getRole_id() + "");
						Departments depart = (Departments) commonService
								.getAnObjectByAnyUniqueColumn("Departments",
										"deptId", userList.get(0).getDeptId());
						approvalHierarchyHistory.setDeptId(depart.getDeptId());
						approvalHierarchyHistory.setcDeptName(depart
								.getDeptName());

						storeChangeFlag = true;
					}
					// AuthUser

					approvalHierarchyHistory.setStatus(OPEN);
					approvalHierarchyHistory.setStateCode(nextStateCode);
					approvalHierarchyHistory.setStateName(approvalHierarchy
							.getStateName());

					approvalHierarchyHistory.setId(null);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setCreatedDate(new Date());
					approvalHierarchyHistory.setActRoleName(approvalHierarchy
							.getRoleName());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					break;
				}

				// if next state code equal to current state code than this
				// process
				// will done for login user
				if (state == currentStateCode) {

					// item Quantity History setup Start :: added by Ihteshamul
					// Alam
					String historyChange = "";
					int counter = 0;
					if (issuedQtyList != null) {
						for (int i = 0; i < issuedQtyList.size(); i++) {
							if (counter == 0) {
								historyChange += subStoreRequisitionDtlList
										.get(i).getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ subStoreRequisitionDtlList.get(i)
												.getUom();
								counter++;
							} else {
								historyChange += ",   "
										+ subStoreRequisitionDtlList.get(i)
												.getItemCode()
										+ ": "
										+ issuedQtyList.get(i)
										+ " "
										+ subStoreRequisitionDtlList.get(i)
												.getUom();
							}
						}
					}
					approvalHierarchyHistory.setHistoryChange(historyChange);
					// item Quantity History setup end

					ApprovalHierarchy approvalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_SS_REQUISITION, state + "");
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory
							.setJustification(ssRequisitionMstDtl
									.getJustification());
					approvalHierarchyHistory
							.setApprovalHeader(approvalHierarchy
									.getApprovalHeader());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				}

				// if next state code is last as approval hierarchy than this
				// process will done and go for generate a store ticket
				if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
					approvalHierarchyHistory.setStatus(DONE);
					approvalHierarchyHistory.setCreatedBy(userName);
					approvalHierarchyHistory.setModifiedBy(userName);
					approvalHierarchyHistory.setModifiedDate(new Date());
					approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
					approvalHierarchyHistory
							.setcEmpFullName(authUser.getName());
					approvalHierarchyHistory.setcDesignation(authUser
							.getDesignation());

					commonService
							.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

					// now we have to insert data in store ticket mst and
					// history
					SSStoreTicketMst csStoreTicketMst = new SSStoreTicketMst();
					// csStoreTicketMst.setTicketNo(ticketNo);
					csStoreTicketMst.setStoreTicketType(LS_SS_REQUISITION);
					csStoreTicketMst.setOperationId(operationId);
					csStoreTicketMst
							.setIssuedTo(ssRequisitionMst.getDeptName());
					csStoreTicketMst.setIssuedFor(ssRequisitionMst
							.getIdenterDesignation());
					csStoreTicketMst.setFlag(false);

					csStoreTicketMst.setKhathId(ssRequisitionMst.getKhathId());
					csStoreTicketMst.setKhathName(ssRequisitionMst
							.getKhathName());

					// Auto generate Store Ticket number
					String descoDeptCode = department.getDescoCode();
					// csStoreTicketMst.setIssuedBy(userName);
					csStoreTicketMst.setIssuedBy(authUser.getName() + "("
							+ userName + ")");
					String storeTicketNo = commonService
							.getOperationIdByPrefixAndSequenceName(
									descoStoreTicketNoPrefix, descoDeptCode,
									separator, "SS_ST_SEQ");

					csStoreTicketMst.setTicketNo(storeTicketNo);

					commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

					// Requisition Approved
					ssRequisitionMst.setStoreTicketNO(storeTicketNo);
					ssRequisitionMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);

					SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
							.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
									"operationId", operationId);

					//

					// Get All Approval Hierarchy on CS_STORE_TICKET
					List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
							.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

					Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
							.size()];
					for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
						sStoreTicketStateCodes[i] = approvalHierarchyListDb
								.get(i).getStateCode();
					}
					Arrays.sort(sStoreTicketStateCodes);

					// get approve hierarchy for last state
					ApprovalHierarchy storeTicketpprovalHierarchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									SS_STORE_TICKET,
									sStoreTicketStateCodes[0].toString());

					StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = new StoreTicketApprovalHierarchyHistory();

					storeTicketApprovalHierarchyHistory
							.setActRoleName(storeTicketpprovalHierarchy
									.getRoleName());
					storeTicketApprovalHierarchyHistory
							.setOperationId(operationId);
					storeTicketApprovalHierarchyHistory.setDeptId(deptId);
					storeTicketApprovalHierarchyHistory.setcDeptName(department
							.getDeptName());
					storeTicketApprovalHierarchyHistory
							.setcDesignation(authUser.getDesignation());
					storeTicketApprovalHierarchyHistory
							.setTicketNo(csStoreTicketMstdb.getTicketNo());
					storeTicketApprovalHierarchyHistory
							.setOperationName(SS_STORE_TICKET);
					storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
					storeTicketApprovalHierarchyHistory
							.setCreatedDate(new Date());
					if (stateCodes.length > 0) {
						storeTicketApprovalHierarchyHistory
								.setStateCode(sStoreTicketStateCodes[0]);
						storeTicketApprovalHierarchyHistory
								.setStateName(storeTicketpprovalHierarchy
										.getStateName());
					}
					storeTicketApprovalHierarchyHistory.setStatus(OPEN);
					storeTicketApprovalHierarchyHistory.setActive(true);
					// process will done and go for store ticket
					commonService
							.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);

					model.addAttribute("operationId", operationId);
					// return "subStore/lsToSsRequisitionReport";
					return "redirect:/ls/ssRequisitionReport.do?srNo="
							+ operationId;

				}
			}
		}

		if (storeChangeFlag) {
			ssRequisitionMst.setRecommend("1");
			commonService.saveOrUpdateModelObjectToDB(ssRequisitionMst);
			// model.addAttribute("operationId", operationId);
			//String operationId = ssRequisitionMstDtl.getRequisitionNo();
			return "redirect:/ls/showRequisitionReportSS.do?srNo="
					+ operationId;
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}

	@RequestMapping(value = "/showRequisitionReportSS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionReportSS(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("localStore/reports/lsToSsRequisitionReport",
				model);

	}

	@RequestMapping(value = "/ss/showRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsToSsRequisitionReportByQuery() {
		return new ModelAndView(
				"localStore/reports/lsToSsRequisitionReportByQuery");
	}

	@RequestMapping(value = "/cs/showRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsToCsRequisitionReportByQuery() {
		return new ModelAndView(
				"localStore/reports/lsToCsRequisitionReportByQuery");
	}

	@RequestMapping(value = "/ssRequisitionReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssRequisitionReport(String srNo) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("operationId", srNo);
		return new ModelAndView("subStore/lsToSsRequisitionReport", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/sendToLocalToSub.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String sendToLocalToSub(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {

		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String nextStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current Dept, User and Role Information
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", ssRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						subStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						subStoreRequisitionDtl
								.setQuantityIssued(subStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsSsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsSsRequisitionApprovalHierarchyHistoryService
				.getLsSsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new LsSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_REQUISITION, nextStateCode + "");
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(rrNo + "");
		approvalHierarchyHistoryNextState
				.setOperationName(approvalHierarchyNextSate.getOperationName());
		approvalHierarchyHistoryNextState
				.setActRoleName(approvalHierarchyNextSate.getRoleName());
		approvalHierarchyHistoryNextState
				.setApprovalHeader(approvalHierarchyNextSate
						.getApprovalHeader());

		approvalHierarchyHistoryNextState.setDeptId(deptId);
		approvalHierarchyHistoryNextState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryNextState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

		return "redirect:/ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisition/backToLocalToSub.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String backToLocalToSub(
			Model model,
			@ModelAttribute("ssRequisitionMstDtl") SubStoreRequisitionMstDtl ssRequisitionMstDtl) {
		String rrNo = ssRequisitionMstDtl.getRequisitionNo();
		String justification = ssRequisitionMstDtl.getJustification();
		String backStateCode = ssRequisitionMstDtl.getStateCode();

		// get Current User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update issued qty
		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", ssRequisitionMstDtl.getRequisitionNo());

		List<String> issuedQtyList = ssRequisitionMstDtl.getIssueqty();
		if (issuedQtyList != null) {
			if (issuedQtyList.size() > 0) {
				int counter = 0;
				for (SubStoreRequisitionDtl subStoreRequisitionDtl : subStoreRequisitionDtlList) {
					double issuedqty = Double.parseDouble(issuedQtyList
							.get(counter));
					if (subStoreRequisitionDtl.getQuantityRequired() >= issuedqty) {
						subStoreRequisitionDtl.setQuantityIssued(issuedqty);
					} else {
						subStoreRequisitionDtl
								.setQuantityIssued(subStoreRequisitionDtl
										.getQuantityRequired());
					}
					commonService
							.saveOrUpdateModelObjectToDB(subStoreRequisitionDtl);
					counter++;
				}
			}
		}

		List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LsSsRequisitionApprovalHierarchyHistory",
						"operationId", rrNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsSsRequisitionApprovalHierarchyHistoryService
				.getLsSsRequisitionApprovalHierarchyHistory(ids[0]);
		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new LsSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						LS_SS_REQUISITION, backStateCode + "");
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(BACK + "ED");
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(rrNo + "");
		approvalHierarchyHistoryBackState
				.setOperationName(approvalHierarchyBackSate.getOperationName());
		approvalHierarchyHistoryBackState
				.setActRoleName(approvalHierarchyBackSate.getRoleName());
		approvalHierarchyHistoryBackState
				.setApprovalHeader(approvalHierarchyBackSate
						.getApprovalHeader());

		// for return same user
		approvalHierarchyHistoryBackState.setStage(BACK);
		approvalHierarchyHistoryBackState.setReturn_to(roleName);
		approvalHierarchyHistoryBackState
				.setReturn_state(currentStateCode + "");

		approvalHierarchyHistoryBackState.setDeptId(deptId);
		approvalHierarchyHistoryBackState
				.setcDeptName(department.getDeptName());
		approvalHierarchyHistoryBackState.setcDesignation(authUser
				.getDesignation());

		commonService
				.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryBackState);

		return "redirect:/ls/requisitionList.do";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csItemRequisitionReceivedList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemRequisitionReceivedList(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<String> reqHierarchyOptIdList = lsCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						stOptIdList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/csApprovedRequisitionList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csItemRequisitionReceivedPendingList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemRequisitionReceivedPendingList(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<String> reqHierarchyOptIdList = lsCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		/*
		 * List<CentralStoreRequisitionMst> csApprovedRequisitions =
		 * (List<CentralStoreRequisitionMst>) (Object) commonService
		 * .getObjectListByAnyColumnValueList( "CentralStoreRequisitionMst",
		 * "requisitionNo", stOptIdList);
		 */

		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueListAndOneColumn(
						"CentralStoreRequisitionMst", "requisitionNo",
						stOptIdList, "received", "0");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/csApprovedRequisitionList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionReceiving.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String storeRequisitionReceiving(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		Integer id = csRequisitionMstDtl.getId();
		// boolean received = csRequisitionMstDtl.isReceived();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		CentralStoreRequisitionMst csRequisitionMst = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(id);
		CSStoreTicketMst csStoreTicketMst = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
						"operationId", csRequisitionMst.getRequisitionNo());
		List<CSStoreTicketDtl> csStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo",
						csStoreTicketMst.getTicketNo());

		// this block will load revenue khath by hardcode
		DescoKhath descoKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "khathCode",
						REVENUE);

		// Ledger Update LS
		for (CSStoreTicketDtl csStoreTicketDtl : csStoreTicketDtlList) {

			// commented block will load dynamic khath from store ticket
			/*
			 * DescoKhath descoKhath = (DescoKhath) commonService
			 * .getAnObjectByAnyUniqueColumn("DescoKhath", "id",
			 * csStoreTicketMst.getKhathId() + "");
			 */

			LSItemTransactionMst lsItemTransactionMst = null;
			/*
			 * LSItemTransactionMst lsItemTransactionMstdb =
			 * lsItemTransactionMstService
			 * .getLSItemTransectionMst(csStoreTicketDtl.getItemId(),
			 * csStoreTicketMst.getKhathId(), csStoreTicketDtl.getLedgerName());
			 */
			List<LSItemTransactionMst> lsItemTransactionMstList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"itemCode", csStoreTicketDtl.getItemId(),
							"khathId", descoKhath.getId() + "", "ledgerName",
							csStoreTicketDtl.getLedgerName(), "sndCode",
							department.getDescoCode());

			LSItemTransactionMst lsItemTransactionMstdb = null;

			if (lsItemTransactionMstList.size() > 0) {
				lsItemTransactionMstdb = lsItemTransactionMstList.get(0);
			}

			// Ledger Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionMstdb.setModifiedBy(userName);
				lsItemTransactionMstdb.setModifiedDate(new Date());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ csStoreTicketDtl.getQuantity());
					}

				}
				commonService
						.saveOrUpdateModelObjectToDB(lsItemTransactionMstdb);
			} else {
				lsItemTransactionMst = new LSItemTransactionMst();
				lsItemTransactionMst.setItemCode(csStoreTicketDtl.getItemId());
				if (csStoreTicketMst.getStoreTicketType().equals(
						LS_CS_REQUISITION)
						|| csStoreTicketMst.getStoreTicketType().equals(
								LS_PD_CS_REQUISITION)) {

					if (csStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMst.setLedgerName(NEW_SERVICEABLE);
						lsItemTransactionMst.setQuantity(csStoreTicketDtl
								.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMst
								.setLedgerName(RECOVERY_SERVICEABLE);
						lsItemTransactionMst.setQuantity(csStoreTicketDtl
								.getQuantity());
					} else if (csStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMst.setLedgerName(UNSERVICEABLE);
						lsItemTransactionMst.setQuantity(csStoreTicketDtl
								.getQuantity());
					}

				}
				lsItemTransactionMst.setKhathName(descoKhath.getKhathName());
				lsItemTransactionMst.setKhathId(descoKhath.getId());
				lsItemTransactionMst.setLedgerName(csStoreTicketDtl
						.getLedgerName());
				lsItemTransactionMst.setId(null);
				lsItemTransactionMst.setCreatedBy(userName);
				lsItemTransactionMst.setCreatedDate(new Date());
				lsItemTransactionMst.setSndCode(csStoreTicketMst.getSndCode());
				commonService.saveOrUpdateModelObjectToDB(lsItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("LSItemTransactionMst", "id");
			LSItemTransactionMst lsItemTransactionMstLastRow = (LSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("LSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			LSItemTransactionDtl lsItemTransactionDtl = new LSItemTransactionDtl();
			// common Info
			lsItemTransactionDtl.setTnxnMode(true);
			lsItemTransactionDtl.setItemCode(csStoreTicketDtl.getItemId());
			lsItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			lsItemTransactionDtl.setKhathId(descoKhath.getId());
			lsItemTransactionDtl
					.setLedgerType(csStoreTicketDtl.getLedgerName());
			lsItemTransactionDtl.setId(null);
			lsItemTransactionDtl.setCreatedBy(userName);
			lsItemTransactionDtl.setCreatedDate(new Date());
			lsItemTransactionDtl.setRemarks(csStoreTicketMst.getRemarks());
			// transaction Related Info
			lsItemTransactionDtl.setTransactionType(csStoreTicketMst
					.getStoreTicketType());
			lsItemTransactionDtl.setTransactionId(csStoreTicketMst
					.getOperationId());
			lsItemTransactionDtl.setTransactionDate(csStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionDtl.setLedgerType(lsItemTransactionMstdb
						.getLedgerName());
				lsItemTransactionDtl
						.setLsItemTransactionMst(lsItemTransactionMstdb);
			} else {
				lsItemTransactionDtl.setLedgerType(lsItemTransactionMstLastRow
						.getLedgerName());
				lsItemTransactionDtl
						.setLsItemTransactionMst(lsItemTransactionMstLastRow);
			}

			// item related info
			lsItemTransactionDtl.setIssuedBy(csStoreTicketMst.getIssuedBy());
			lsItemTransactionDtl.setIssuedFor(csStoreTicketMst.getIssuedFor());
			lsItemTransactionDtl.setIssuedTo(csStoreTicketMst.getIssuedTo());
			lsItemTransactionDtl.setReturnFor(csStoreTicketMst.getReturnFor());
			lsItemTransactionDtl.setRetrunBy(csStoreTicketMst.getReturnBy());
			lsItemTransactionDtl
					.setReceivedBy(csStoreTicketMst.getReceivedBy());
			lsItemTransactionDtl.setReceivedFrom(csStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			lsItemTransactionDtl.setQuantity(csStoreTicketDtl.getQuantity());
			/*
			 * lsItemTransactionDtl.setNewServQty(csStoreTicketDtl.getQuantity())
			 * ; lsItemTransactionDtl.setRecoverServeQty(csStoreTicketDtl
			 * .getQuantity());
			 * lsItemTransactionDtl.setUnservQty(csStoreTicketDtl
			 * .getQuantity());
			 * 
			 * lsItemTransactionDtl.setTotalQty(csStoreTicketDtl.getQuantity());
			 */

			commonService.saveOrUpdateModelObjectToDB(lsItemTransactionDtl);
		}

		// item received completed by received flag true
		csRequisitionMst.setReceived(true);
		csRequisitionMst.setReceivedBy(userName);
		commonService.saveOrUpdateModelObjectToDB(csRequisitionMst);
		return "redirect:/ls/csItemRequisitionReceivedList.do";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionReceivedShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionReceivedShow(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		// needed property
		Integer mstId = csRequisitionMstDtl.getId();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// Mst Data
		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(mstId);
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		// Dtl Data
		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		// operation Id which selected by login user
		String currentStatus = "";
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = new ArrayList<LsCsRequisitionApprovalHierarchyHistory>();

		if (centralStoreRequisitionMstdb.getKhathId() > 1) {

			List<LsPdCsRequisitionApprovalHierarchyHistory> lsPdRequisitionApprovalHierarchyHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"LsPdCsRequisitionApprovalHierarchyHistory",
							LS_PD_CS_REQUISITION, operationId, DONE);
			if (lsPdRequisitionApprovalHierarchyHistoryList != null) {
				for (LsPdCsRequisitionApprovalHierarchyHistory lsPdSsReqHistory : lsPdRequisitionApprovalHierarchyHistoryList) {
					LsCsRequisitionApprovalHierarchyHistory hist = new LsCsRequisitionApprovalHierarchyHistory();
					hist.setActive(lsPdSsReqHistory.isActive());
					hist.setActRoleName(lsPdSsReqHistory.getActRoleName());
					hist.setApprovalHeader(lsPdSsReqHistory.getApprovalHeader());
					hist.setcDeptName(lsPdSsReqHistory.getcDeptName());
					hist.setcDesignation(lsPdSsReqHistory.getcDesignation());
					hist.setcEmpFullName(lsPdSsReqHistory.getcEmpFullName());
					hist.setcEmpId(lsPdSsReqHistory.getcEmpId());
					hist.setCreatedBy(lsPdSsReqHistory.getCreatedBy());
					hist.setCreatedDate(lsPdSsReqHistory.getCreatedDate());
					hist.setDeptId(lsPdSsReqHistory.getDeptId());
					hist.setHistoryChange(lsPdSsReqHistory.getHistoryChange());
					hist.setId(lsPdSsReqHistory.getId());
					hist.setJustification(lsPdSsReqHistory.getJustification());
					hist.setOperationId(lsPdSsReqHistory.getOperationId());
					hist.setOperationName(lsPdSsReqHistory.getOperationName());
					hist.setRemarks(lsPdSsReqHistory.getRemarks());
					hist.setStatus(lsPdSsReqHistory.getStatus());
					hist.setStateName(lsPdSsReqHistory.getStateName());
					hist.setStage(lsPdSsReqHistory.getStage());
					hist.setReturn_to(lsPdSsReqHistory.getReturn_to());
					hist.setReturn_state(lsPdSsReqHistory.getReturn_state());
					hist.setModifiedBy(lsPdSsReqHistory.getModifiedBy());
					hist.setModifiedDate(lsPdSsReqHistory.getModifiedDate());
					lsCsRequisitionHistoryList.add(hist);
				}
			}

		} else {
			lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
							"LsCsRequisitionApprovalHierarchyHistory",
							LS_CS_REQUISITION, operationId, DONE);
		}

		if (!lsCsRequisitionHistoryList.isEmpty()) {
			currentStatus = lsCsRequisitionHistoryList.get(
					lsCsRequisitionHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("reqTo", ContentType.CENTRAL_STORE.toString());
		model.put("itemRcvApproveHistoryList", lsCsRequisitionHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsItemReceivedShow", model);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionReceivedSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView requisitionReceivedSearch(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
		requisitionNo = requisitionNo.toUpperCase();
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<String> reqHierarchyOptIdList = lsCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<String> resultOptId = searchSting(stOptIdList, requisitionNo);

		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						resultOptId);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList", csApprovedRequisitions);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/csApprovedRequisitionList", model);
	}

	static java.util.List<String> searchSting(java.util.List<String> value,
			String searchValue) {
		java.util.List<String> returnValue = new java.util.ArrayList<String>();
		for (String s : value) {
			if (s.contains(searchValue)) {
				returnValue.add(s);
			}
		}
		return returnValue;
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	public List<StoreLocations> getStoreLocationList(String storeCode) {

		List<StoreLocations> storeLocationList = (List<StoreLocations>) (Object) commonService
				.getObjectListByAnyColumn("StoreLocations", "storeCode",
						storeCode);

		try {
			return storeLocationList;

		} catch (Exception ex) {
			return null;
		}
	}

	// Added by Ashid
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setRcvedLocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String setRcvedLocation(@RequestBody String locationQtyJsonString)
			throws InterruptedException, JsonGenerationException,
			JsonMappingException, IOException {

		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(locationQtyJsonString);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(
					locationQtyJsonString, TempLocationMstDtl.class);
			String itemCode = temLocationDtl.getItemCode();
			String uuid = temLocationDtl.getUuid();
			Map<String, String> map = temLocationDtl.getLocQtyDtl();
			Set<String> keys = map.keySet();

			for (Iterator i = keys.iterator(); i.hasNext();) {
				String locationId = (String) i.next();
				StoreLocations storeLocations = (StoreLocations) commonService
						.getAnObjectByAnyUniqueColumn("StoreLocations", "id",
								locationId);

				Double qty = Double.parseDouble(map.get(locationId));

				TempItemLocation tempItemLocation = new TempItemLocation();
				tempItemLocation.setId(null);
				tempItemLocation.setItemCode(itemCode);
				tempItemLocation.setUuid(uuid);
				tempItemLocation.setLocationId(locationId);
				tempItemLocation.setLocationName(storeLocations.getName());
				tempItemLocation.setQuantity(qty);
				tempItemLocation.setCreatedBy(commonService.getAuthUserName());
				tempItemLocation.setCreatedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(tempItemLocation);

			}

			toJson = ow.writeValueAsString("success");

		} else {
			Thread.sleep(125 * 1000);
			toJson = ow.writeValueAsString("failure");
		}

		return toJson;
	}

	// return total Qty. Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTotalQtyByUuidAndItemCode.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTotalQtyByUuidAndItemCode(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String uuid = tp.getUuid();
			String itemCode = tp.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "uuid", uuid,
							"itemCode", itemCode);
			Double qty = 0.0;
			for (TempItemLocation temp : tempItemLocationList) {
				qty += temp.getQuantity();
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(qty);
		}
		return toJson;
	}

	// Added by Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTemplocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String getTemplocation(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempLocationMstDtl temLocationDtl = gson.fromJson(cData,
					TempLocationMstDtl.class);
			String uuid = temLocationDtl.getUuid();
			String itemCode = temLocationDtl.getItemCode();
			List<TempItemLocation> tempItemLocationList = (List<TempItemLocation>) (Object) commonService
					.getObjectListByTwoColumn("TempItemLocation", "itemCode",
							itemCode, "uuid", uuid);
			// System.out.println(itemCode + " : " + uuid);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString(tempItemLocationList);
		}

		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/updateLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			String id = tp.getId().toString();
			Double quantity = tp.getQuantity();

			TempItemLocation tempItemLocationDb = (TempItemLocation) commonService
					.getAnObjectByAnyUniqueColumn("TempItemLocation", "id", id);
			tempItemLocationDb.setQuantity(quantity);
			tempItemLocationDb.setModifiedBy(commonService.getAuthUserName());
			tempItemLocationDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(tempItemLocationDb);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Upadate Failed.");

		}
		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/updateRRdtlAfterLocatinUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String updateRRdtlAfterLocatinUpdate(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CSProcItemRcvDtl tpDtl = gson.fromJson(cData,
					CSProcItemRcvDtl.class);
			String rrNo = tpDtl.getRrNo();
			String itemId = tpDtl.getItemId();
			double receivedQty = tpDtl.getReceivedQty();
			List<Object> obList = commonService.getObjectListByTwoColumn(
					"CSProcItemRcvDtl", "rrNo", rrNo, "itemId", itemId);
			CSProcItemRcvDtl cSProcItemRcvDtlDB = new CSProcItemRcvDtl();
			System.out.println(cSProcItemRcvDtlDB.getReceivedQty());
			if (obList.size() > 0) {
				cSProcItemRcvDtlDB = (CSProcItemRcvDtl) obList.get(0);
			}
			if (cSProcItemRcvDtlDB.getReceivedQty() != 0.0) {
				cSProcItemRcvDtlDB.setReceivedQty(receivedQty);
				cSProcItemRcvDtlDB.setModifiedBy(commonService
						.getAuthUserName());
				cSProcItemRcvDtlDB.setModifiedDate(new Date());
			}
			commonService.saveOrUpdateModelObjectToDB(cSProcItemRcvDtlDB);

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity updated Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow
					.writeValueAsString("Error from updateRRdtlAfterLocatinUpdate.do");
		}
		return toJson;
	}

	// Added by Ashid
	@RequestMapping(value = "/deleteLocationQty.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String deleteLocationQty(@RequestBody String cData) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			TempItemLocation tp = gson.fromJson(cData, TempItemLocation.class);
			Integer id = tp.getId();
			commonService.deleteAnObjectById("TempItemLocation", id);
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Qantity deleted Successfully.");
		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}

	// Save Multiple Item from Show page - Ihteshamul Alam
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/storeRequisitionSaveMultipleItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSaveMultipleItem(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		try {
			String requisitionTo = csRequisitionMstDtl.getRequisitionTo();
			String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
			List<String> itemCodeList = null;
			List<String> itemNameList = null;
			List<String> uomList = null;
			List<Double> quantityRequiredList = null;
			// List<Double> quantityIssued =
			// csRequisitionMstDtl.getQuantityIssued();
			List<String> remarksList = null;

			if (requisitionTo.equalsIgnoreCase("cs")) {
				if (csRequisitionMstDtl.getItemCode() != null) {
					itemCodeList = csRequisitionMstDtl.getItemCode();
				}

				if (csRequisitionMstDtl.getItemName() != null) {
					itemNameList = csRequisitionMstDtl.getItemName();
				}

				if (csRequisitionMstDtl.getUom() != null) {
					uomList = csRequisitionMstDtl.getUom();
				}

				if (csRequisitionMstDtl.getQuantityRequired() != null) {
					quantityRequiredList = csRequisitionMstDtl
							.getQuantityRequired();
				}

				if (csRequisitionMstDtl.getRemarks() != null) {
					remarksList = csRequisitionMstDtl.getRemarks();
				}

				CentralStoreRequisitionMst csRequisitionMstDb = (CentralStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CentralStoreRequisitionMst", "requisitionNo",
								requisitionNo);
				// String username = commonService.getAuthUserName();
				for (int i = 0; i < itemCodeList.size(); i++) {
					CentralStoreRequisitionDtl csRequisitionDtl = new CentralStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						csRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						csRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						csRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						csRequisitionDtl.setUom(uomList.get(i));
					} else {
						csRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						csRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						csRequisitionDtl.setQuantityRequired(0);
					}

					csRequisitionDtl.setQuantityIssued(0.0);

					if (!remarksList.isEmpty()) {
						csRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						csRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					csRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					csRequisitionDtl.setRequisitionNo(requisitionNo);
					csRequisitionDtl.setCreatedBy(userName);
					csRequisitionDtl.setCreatedDate(new Date());
					csRequisitionDtl.setActive(true);
					csRequisitionDtl
							.setCentralStoreRequisitionMst(csRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(csRequisitionDtl);

					// Allocation Update
					if (csRequisitionDtl.getItemCode().length() > 0) {
						DescoSession descoSession = (DescoSession) commonService
								.getCurrentDescoSession();
						List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
								.getObjectListByThreeColumn("AllocationTable",
										"sndCode", department.getDescoCode(),
										"itemCode",
										csRequisitionDtl.getItemCode(),
										"descoSession.id", descoSession.getId()
												+ "");
						AllocationTable allocationTable = null;
						if (allocationTableList.size() > 0) {
							allocationTable = (AllocationTable) allocationTableList
									.get(0);
							double usedQty = allocationTable.getUsedQuantity();
							double beanQty = quantityRequiredList.get(i);
							allocationTable.setUsedQuantity(usedQty + beanQty);
							commonService
									.saveOrUpdateModelObjectToDB(allocationTable);
						} else {
							continue;
						}
					}
					// Allocation Update End
				}

				//
				// View Show Page
				CentralStoreRequisitionMst centralStoreRequisitionMst = new CentralStoreRequisitionMst();
				centralStoreRequisitionMst.setId(csRequisitionMstDb.getId());
				centralStoreRequisitionMst.setRequisitionTo(requisitionTo);
				centralStoreRequisitionMst.setRequisitionNo(requisitionNo);
				return this.getStoreRequisitionShow(centralStoreRequisitionMst);
			}

			else if (requisitionTo.equalsIgnoreCase("ss")) {

				if (csRequisitionMstDtl.getItemCode() != null) {
					itemCodeList = csRequisitionMstDtl.getItemCode();
				}

				if (csRequisitionMstDtl.getItemName() != null) {
					itemNameList = csRequisitionMstDtl.getItemName();
				}

				if (csRequisitionMstDtl.getUom() != null) {
					uomList = csRequisitionMstDtl.getUom();
				}

				if (csRequisitionMstDtl.getQuantityRequired() != null) {
					quantityRequiredList = csRequisitionMstDtl
							.getQuantityRequired();
				}

				if (csRequisitionMstDtl.getRemarks() != null) {
					remarksList = csRequisitionMstDtl.getRemarks();
				}

				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"requisitionNo", requisitionNo);
				// String username = commonService.getAuthUserName();
				for (int i = 0; i < itemCodeList.size(); i++) {
					SubStoreRequisitionDtl ssRequisitionDtl = new SubStoreRequisitionDtl();

					if (!itemCodeList.isEmpty()) {
						ssRequisitionDtl.setItemCode(itemCodeList.get(i));
					} else {
						ssRequisitionDtl.setItemCode("");
					}

					if (!itemNameList.isEmpty()) {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					} else {
						ssRequisitionDtl.setItemName(itemNameList.get(i));
					}

					if (!uomList.isEmpty()) {
						ssRequisitionDtl.setUom(uomList.get(i));
					} else {
						ssRequisitionDtl.setUom("");
					}

					if (!quantityRequiredList.isEmpty()) {
						ssRequisitionDtl
								.setQuantityRequired(quantityRequiredList
										.get(i));
					} else {
						ssRequisitionDtl.setQuantityRequired(0);
					}

					ssRequisitionDtl.setQuantityIssued(0.0);

					if (!remarksList.isEmpty()) {
						ssRequisitionDtl.setRemarks(remarksList.get(i));
					} else {
						ssRequisitionDtl.setRemarks("");
					}

					// set id null for auto number
					ssRequisitionDtl.setId(null);

					// set RequisitionNo to each detail row
					ssRequisitionDtl.setRequisitionNo(requisitionNo);
					ssRequisitionDtl.setCreatedBy(userName);
					ssRequisitionDtl.setCreatedDate(new Date());
					ssRequisitionDtl.setActive(true);
					ssRequisitionDtl
							.setSubStoreRequisitionMst(ssRequisitionMstDb);

					// insert item detail in csRequisitionDtl table
					commonService.saveOrUpdateModelObjectToDB(ssRequisitionDtl);

					// Allocation Update
					if (ssRequisitionDtl.getItemCode().length() > 0) {
						DescoSession descoSession = (DescoSession) commonService
								.getCurrentDescoSession();
						List<AllocationTable> allocationTableList = (List<AllocationTable>) (Object) commonService
								.getObjectListByThreeColumn("AllocationTable",
										"sndCode", department.getDescoCode(),
										"itemCode",
										ssRequisitionDtl.getItemCode(),
										"descoSession.id", descoSession.getId()
												+ "");
						AllocationTable allocationTable = null;
						if (allocationTableList.size() > 0) {
							allocationTable = (AllocationTable) allocationTableList
									.get(0);
							double usedQty = allocationTable.getUsedQuantity();
							double beanQty = quantityRequiredList.get(i);
							allocationTable.setUsedQuantity(usedQty + beanQty);
							commonService
									.saveOrUpdateModelObjectToDB(allocationTable);
						} else {
							continue;
						}
					}
					// Allocation Update End
				}

				//
				// View Show Page
				CentralStoreRequisitionMst centralStoreRequisitionMst = new CentralStoreRequisitionMst();
				centralStoreRequisitionMst.setId(ssRequisitionMstDb.getId());
				centralStoreRequisitionMst.setRequisitionTo(requisitionTo);
				centralStoreRequisitionMst.setRequisitionNo(requisitionNo);
				return this.getStoreRequisitionShow(centralStoreRequisitionMst);

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	// Delete Item from show page :: Ihteshamul Alam
	@RequestMapping(value = "/deleteItem.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteItem(
			@ModelAttribute("csRequisitionMstDtl") CentralStoreRequisitionMstDtl csRequisitionMstDtl) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer itemId = csRequisitionMstDtl.getId();
			String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
			String requisitionTo = csRequisitionMstDtl.getRequisitionTo();

			if (requisitionTo.equalsIgnoreCase("cs")) {
				CentralStoreRequisitionMst csRequisitionMstDb = (CentralStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn(
								"CentralStoreRequisitionMst", "requisitionNo",
								requisitionNo);
				commonService.deleteAnObjectById("CentralStoreRequisitionDtl",
						itemId);

				// View Show Page
				CentralStoreRequisitionMst centralStoreRequisitionMst = new CentralStoreRequisitionMst();
				centralStoreRequisitionMst.setId(csRequisitionMstDb.getId());
				centralStoreRequisitionMst.setRequisitionTo(requisitionTo);
				centralStoreRequisitionMst.setRequisitionNo(requisitionNo);
				return this.getStoreRequisitionShow(centralStoreRequisitionMst);
			} else if (requisitionTo.equalsIgnoreCase("ss")) {

				SubStoreRequisitionMst ssRequisitionMstDb = (SubStoreRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
								"requisitionNo", requisitionNo);
				commonService.deleteAnObjectById("SubStoreRequisitionDtl",
						itemId);

				// View Show Page
				CentralStoreRequisitionMst centralStoreRequisitionMst = new CentralStoreRequisitionMst();
				centralStoreRequisitionMst.setId(ssRequisitionMstDb.getId());
				centralStoreRequisitionMst.setRequisitionTo(requisitionTo);
				centralStoreRequisitionMst.setRequisitionNo(requisitionNo);
				return this.getStoreRequisitionShow(centralStoreRequisitionMst);

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	// Added by: Shimul, IBCS
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadAuthUserList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String loadAuthUserList() throws Exception {
		String toJson = "";
		List<AuthUser> authList = (List<AuthUser>) (Object) commonService
				.getAllObjectList("com.ibcs.desco.admin.model.AuthUser");
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(authList);
		return toJson;
	}

	// Added by: Shimul, IBCS
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/newRepresentativeForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView newRepresentativeForm() {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String username = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(username);
			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			List<com.ibcs.desco.contractor.model.Contractor> contractList = (List<com.ibcs.desco.contractor.model.Contractor>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.contractor.model.Contractor",
							"sndDeptPk", department.getId().toString());

			model.put("department", department);
			model.put("contractList", contractList);
			return new ModelAndView("localStore/newRepresentativeForm", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveRepresentativeInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveRepresentativeInfo(
			ContractorRepresentive cont_rep_mst_dtl,
			@RequestParam("pictures") MultipartFile pictures) {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Date now = new Date();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		/*
		 * Departments targetDept = (Departments) commonService
		 * .getAnObjectByAnyUniqueColumn("Departments", "deptId",
		 * department.getDeptId());
		 */

		com.ibcs.desco.contractor.model.Contractor cont = contractorRepresentiveService
				.getContractorByContractNo(cont_rep_mst_dtl.getContractNo());

		ContractorRepresentive contractorRepresentive = new ContractorRepresentive();
		contractorRepresentive.setContractNo(cont.getId().toString());
		contractorRepresentive.setUserId(cont_rep_mst_dtl.getUserId());
		contractorRepresentive.setRepresentiveName(cont_rep_mst_dtl
				.getRepresentiveName());
		contractorRepresentive.setAddress(cont_rep_mst_dtl.getAddress());
		contractorRepresentive
				.setDesignation(cont_rep_mst_dtl.getDesignation());
		contractorRepresentive.setContactNo(cont_rep_mst_dtl.getContactNo());
		contractorRepresentive.setEmail(cont_rep_mst_dtl.getEmail());
		contractorRepresentive.setListedDate(cont_rep_mst_dtl.getListedDate());
		contractorRepresentive.setEndDate(cont_rep_mst_dtl.getEndDate());
		contractorRepresentive.setPicture(this.saveFileToDrive(pictures));
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
		if (cont_rep_mst_dtl.getContractorType().equalsIgnoreCase(REVENUE)) {
			roles = (Roles) commonService.getAnObjectByAnyUniqueColumn(
					"com.ibcs.desco.admin.model.Roles", "role",
					ROLE_SND_CN_USER);
		} else {
			roles = (Roles) commonService
					.getAnObjectByAnyUniqueColumn(
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
			userService.addAuthUser(u);

			// assign contractor to snd
			AuthUser authUserdb = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							u.getUserid());
			ContractorDepartmentReference contractorDepartmentReference = new ContractorDepartmentReference();
			contractorDepartmentReference.setContractorId(authUserdb.getId());
			contractorDepartmentReference.setDeptId(department.getId());
			contractorDepartmentReference.setId(null);
			commonService
					.saveOrUpdateModelObjectToDB(contractorDepartmentReference);
		} catch (Exception e) {
			// throw e;
		}

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

		return new ModelAndView("localStore/lsContractorList", model);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contractorList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorList() {
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<com.ibcs.desco.contractor.model.Contractor> contractorList = (List<com.ibcs.desco.contractor.model.Contractor>) (Object) commonService
				.getObjectListByThreeColumn(
						"com.ibcs.desco.contractor.model.Contractor", "active",
						"1", "contractorType", REVENUE, "sndDeptPk", department.getId().toString());

		/*List<com.ibcs.desco.contractor.model.Contractor> contractorList1 = (List<com.ibcs.desco.contractor.model.Contractor>) (Object) commonService
				.getObjectListByTwoColumn(
						"com.ibcs.desco.contractor.model.Contractor", "active",
						"1", "contractorType", WORKSHOP);

		if (contractorList1.size() > 0) {
			contractorList.addAll(contractorList1);
		}*/
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorList", contractorList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/lsContractorList", model);
	}

	@RequestMapping(value = "/contRepresentative/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorRepresentativeListGet(
			ContractorRepresentive contractorRepresentive) {
		return finalContractorRepresentativeList(contractorRepresentive);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/contRepresentative/finalList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorRepresentativeList(
			ContractorRepresentive contractorRepresentive) {
		String userName = commonService.getAuthUserName();

		// String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ContractorRepresentive> contractorRepresentiveList = (List<ContractorRepresentive>) (Object) commonService
				.getObjectListByAnyColumn("ContractorRepresentive",
						"contractor.id", contractorRepresentive.getId() + "");
		for (ContractorRepresentive c : contractorRepresentiveList) {
			try {
				c.setPicture(this.getImagePath(c.getPicture()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		com.ibcs.desco.contractor.model.Contractor contractor = (com.ibcs.desco.contractor.model.Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						contractorRepresentive.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorRepresentiveList", contractorRepresentiveList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/contractorRepresentativeList",
				model);
	}

	String getImagePath(String path) throws Exception {

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

	// Added by: Shimul
	@RequestMapping(value = "/updateRepresentativeInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateRepresentativeInfo(
			ContractorRepresentive contractorRepresentive) {
		String response = "";

		Integer id = contractorRepresentive.getId();

		ContractorRepresentive cnRepresentative = (ContractorRepresentive) commonService
				.getAnObjectByAnyUniqueColumn("ContractorRepresentive", "id",
						id.toString());

		cnRepresentative.setRepresentiveName(contractorRepresentive
				.getRepresentiveName());
		cnRepresentative.setAddress(contractorRepresentive.getAddress());
		cnRepresentative
				.setDesignation(contractorRepresentive.getDesignation());
		cnRepresentative.setContactNo(contractorRepresentive.getContactNo());
		cnRepresentative.setModifiedBy(commonService.getAuthUserName());
		cnRepresentative.setModifiedDate(new Date());

		commonService.saveOrUpdateModelObjectToDB(cnRepresentative);
		response = "success";
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/itemRequisitionReject.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView itemRequisitionReject(CentralStoreRequisitionMstDtl bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userId);

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId", bean.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			LsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsCsRequisitionApprovalHierarchyHistoryService
					.getLsCsRequisitionApprovalHierarchyHistory(ids[0]);

			approvalHierarchyHistory.setStatus(REJECT);
			approvalHierarchyHistory.setModifiedBy(userId);
			approvalHierarchyHistory.setModifiedDate(new Date());

			approvalHierarchyHistory.setCreatedBy(userId);
			approvalHierarchyHistory.setCreatedDate(new Date());

			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			approvalHierarchyHistory.setJustification(bean.getJustification());
			approvalHierarchyHistory.setApprovalHeader(REJECT_HEADER);
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			return new ModelAndView(
					"redirect:/ls/cs/rejectedRequisitionList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/recommendedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView recommendedRequisitionList() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());		

		CentralStoreRequisitionMst cs = new CentralStoreRequisitionMst();
		
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = new ArrayList<CentralStoreRequisitionMst>();
		
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList1 = (List<CentralStoreRequisitionMst>)(Object)
				commonService.getObjectListByThreeColumn("CentralStoreRequisitionMst", 
						"recommend", "1", "approved", "0", "sndCode", department.getDescoCode());
		
		List<SubStoreRequisitionMst> subStoreRequisitionMstList = (List<SubStoreRequisitionMst>)(Object)
				commonService.getObjectListByThreeColumn("SubStoreRequisitionMst", 
						"recommend", "1", "approved", "0", "sndCode", department.getDescoCode());
		
		if (centralStoreRequisitionMstList1 != null) {			
				centralStoreRequisitionMstList.addAll(centralStoreRequisitionMstList1);			
		}
		
		if (subStoreRequisitionMstList != null) {
			for (SubStoreRequisitionMst ss : subStoreRequisitionMstList) {
				centralStoreRequisitionMstList.add(importObjToCs(ss));
			}
		}

		for (CentralStoreRequisitionMst reqMst : centralStoreRequisitionMstList) {
			if (reqMst.getSndCode() != null) {
				Departments d = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments",
								"descoCode", reqMst.getSndCode());
				if (d != null) {
					reqMst.setSndCode(d.getDeptName());
				} else {
					Departments contractorDept = (Departments) commonService
							.getAnObjectByAnyUniqueColumn("Departments",
									"deptId", reqMst.getSndCode());
					reqMst.setSndCode(contractorDept.getDeptName());
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("centralStoreRequisitionMstList",
				centralStoreRequisitionMstList);
		
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsToCsRequisitionRecommendedList", model);
		} else {
			return new ModelAndView("localStore/csRequisitionList", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionRecommendShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionRecommendShow(CentralStoreRequisitionMst bean)
			throws Exception {

		String requisitionTo = bean.getRequisitionTo();
		String reqMstId = bean.getId().toString();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		if (requisitionTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {
			return new ModelAndView("localStore/errorLS");
		}

		CentralStoreRequisitionMst centralStoreRequisitionMstdb = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"id", reqMstId);

		if (centralStoreRequisitionMstdb.getKhathId() > 1) {
			return new ModelAndView("localStore/errorLS");
		}

		// Set full name of Requisition Creator
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						centralStoreRequisitionMstdb.getCreatedBy());
		centralStoreRequisitionMstdb.setCreatedBy(createBy.getName());

		Integer khathId = centralStoreRequisitionMstdb.getKhathId();

		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);
		// This Block will set Curent stock of CS
		for (int i = 0; i < centralStoreRequisitionDtlList.size(); i++) {
			String itemCode = centralStoreRequisitionDtlList.get(i)
					.getItemCode();
			List<CSItemTransactionMst> csItemTnxMstRecovery = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName",
							RECOVERY_SERVICEABLE);

			List<CSItemTransactionMst> csItemTnxMstNew = (List<CSItemTransactionMst>) (Object) commonService
					.getObjectListByThreeColumn("CSItemTransactionMst",
							"itemCode", itemCode, "khathId",
							khathId.toString(), "ledgerName", NEW_SERVICEABLE);

			if (csItemTnxMstRecovery.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(
								csItemTnxMstRecovery.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setRecoveryServiceableStockQty(0.0);
			}

			if (csItemTnxMstNew.size() > 0) {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(
								csItemTnxMstNew.get(0).getQuantity());
			} else {
				centralStoreRequisitionDtlList.get(i)
						.setNewServiceableStockQty(0.0);
			}
		}

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, DONE);

		if (!sCsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sCsRequisitionApprovalHierarchyHistoryList.get(
					sCsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<LsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory",
						LS_CS_REQUISITION, operationId, OPEN);

		int currentStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
				.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
				.getStateCode();

		// role id list From Auth_User by dept Id
		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId", deptId);
		// Role name list by role id_list
		List<String> roleIdList = new ArrayList<String>();
		for (AuthUser user : userList) {
			roleIdList.add(String.valueOf(user.getRoleid()));
		}
		List<Roles> roleObjectList = (List<Roles>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"com.ibcs.desco.admin.model.Roles", "role_id",
						roleIdList);
		// App_hier List by RoleList & Op name
		List<String> roleNameList = new ArrayList<String>();
		for (Roles role : roleObjectList) {
			roleNameList.add(role.getRole());
		}

		List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndRoleName(
						LS_CS_REQUISITION, roleNameList);

		// Send To Upper Authority of same department
		List<ApprovalHierarchy> nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

		//

		// Back To User as my wish
		List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
		for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
				.size(); countBackStateCodes++) {
			if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
				backManRcvProcs
						.add(approveHeirchyList.get(countBackStateCodes));
			}
		}

		String returnStateCode = "";
		// button Name define
		if (!sCsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sCsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sCsRequisitionApprovalHierarchyHistoryOpenList.get(
					sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = sCsRequisitionApprovalHierarchyHistoryOpenList
					.get(sCsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval heirarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								LS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		
		Map<String, Object> model = new HashMap<String, Object>();

		List<ItemCategory> categoryList = itemGroupService.getAllItemGroups();

		DescoKhath dsKhath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id", reqMstId);

		model.put("dsKhath", dsKhath);
		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", centralStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);

		model.put("reqTo", ContentType.CENTRAL_STORE.toString());
		model.put("itemRcvApproveHistoryList",
				sCsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList",
				centralStoreRequisitionDtlList);

		model.put("deptName", department.getDeptName());

		model.put("categoryList", categoryList);

		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_LS")) {
			return new ModelAndView("localStore/lsRequisitionRecommendShow", model);
		} else {
			return new ModelAndView("localStore/csRequisitionShow", model);
		}
	}

}
