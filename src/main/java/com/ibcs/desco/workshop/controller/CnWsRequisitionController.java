package com.ibcs.desco.workshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.bean.MyPair;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnWsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.RequisitionLock;
import com.ibcs.desco.common.model.StoreLocations;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.CnPdRequisitionDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.CnPdRequisitionMstDtl;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.model.PndJobDtl;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.contractor.service.PndJobDtlService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.model.CSItemTransactionMst;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CentralStoreRequisitionDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.inventory.model.ItemMaster;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.workshop.bean.CnWsRequisitionMstDtl;
import com.ibcs.desco.workshop.bean.JobCardMstDtl;
import com.ibcs.desco.workshop.model.CnWsCsRequisitionDtl;
import com.ibcs.desco.workshop.model.CnWsCsRequisitionMst;
import com.ibcs.desco.workshop.model.JobCardDtl;
import com.ibcs.desco.workshop.model.JobCardMst;

@Controller
@RequestMapping(value = "/cnws")
@PropertySource("classpath:common.properties")
public class CnWsRequisitionController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;

	@Autowired
	CentralStoreRequisitionDtlService centralStoreRequisitionDtlService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CommonService commonService;

	@Autowired
	PndJobDtlService pndJobDtlService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@Value("${desco.store.ticket.prefix}")
	private String descoStoreTicketNoPrefix;

	@Value("${desco.requisition.unlock.expired.hours}")
	private String unlockExpiredHours;
	
	@Value("${desco.ws.department.code}")
	private String descoWSDeptCode;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRequisitionForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			String deptId = authUser.getDeptId();

			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			ContractorRepresentive contractorRep = contractorRepresentiveService
					.getContractorRep(authUser.getUserid());

			Contractor contractor = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor",
							"contractNo", contractorRep.getContractNo());

			List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
					.getObjectListByTwoColumn("JobCardMst", "contractNo",
							contractorRep.getContractNo(), "approved", "1");

			/*
			 * getObjectListByAnyColumn("JobCardMst", "woNumber",
			 * contractorRep.getContractNo());
			 */

			model.put("authUser", authUser);
			model.put("jobCardMstList", jobCardMstList);
			model.put("contractor", contractor);
			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			return new ModelAndView(
					"workshop/transformer/requisition/wsStoreRequisitionForm",
					model);
		} catch (Exception e) {
			model.put("errorMsg", e);
			return new ModelAndView("pndContractor/errorCnProject", model);
		}
	}

	// storeRequisitionNext.do
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeRequisitionNext.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionNext(JobCardMstDtl jobCardMstDtl) {
		Map<String, Object> model = new HashMap<String, Object>();
		// String roleName = commonService.getAuthRoleName();

		List<String> jobNoIdList = null;
		if (jobCardMstDtl.getJobNoIdList() != null) {
			jobNoIdList = jobCardMstDtl.getJobNoIdList();
		} else {
			return this.getStoreRequisitionForm();
		}
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		List<List<JobCardDtl>> requestedJobList = new ArrayList<List<JobCardDtl>>();

		for (String jobId : jobNoIdList) {

			List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
					.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.id",
							jobId);
			{
				JobCardMst pjmst = (JobCardMst) commonService
						.getAnObjectByAnyUniqueColumn("JobCardMst", "id", jobId);
				for (JobCardDtl pd : jobCardDtlList) {
					pd.setJobCardNo(pjmst.getJobCardNo());
				}
			}
			requestedJobList.add(jobCardDtlList);
		}

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", contractorRep.getContractNo());

		List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
				.getObjectListByAnyColumn("JobCardMst", "contractNo",
						contractorRep.getContractNo());

		model.put("jobCardMstList", jobCardMstList);
		model.put("contractor", contractor);
		model.put("requestedJobList", requestedJobList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		model.put("senderInfo", jobCardMstDtl);

		return new ModelAndView(
				"workshop/transformer/requisition/wsContractorRequisitonForm",
				model);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/storeRequisitionSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView storeRequisitionSave(
			CnWsRequisitionMstDtl cnWsRequisitionMstDtl) {

		String roleName = commonService.getAuthRoleName();

		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		ContractorRepresentive contractorRep = contractorRepresentiveService
				.getContractorRep(authUser.getUserid());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", contractorRep.getContractNo());

		List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
				.getObjectListByAnyColumn("JobCardMst", "contractNo",
						contractorRep.getContractNo());

		Date now = new Date();

		CnWsCsRequisitionMst cnWsCsReqMst = new CnWsCsRequisitionMst();
		cnWsCsReqMst.setDeptId(deptId);
		cnWsCsReqMst.setDeptName(department.getDeptName());
		cnWsCsReqMst.setId(null);
		cnWsCsReqMst.setIdenterDesignation(department.getDeptName());
		cnWsCsReqMst.setKhathId(contractor.getKhathId());
		cnWsCsReqMst.setKhathName(contractor.getKhathName());
		cnWsCsReqMst.setReceivedBy(cnWsRequisitionMstDtl.getReceivedBy());
		cnWsCsReqMst.setRemarks(cnWsRequisitionMstDtl.getRemarks());
		cnWsCsReqMst.setRequisitionTo(cnWsRequisitionMstDtl.getRequisitionTo());
		cnWsCsReqMst.setRequisitionDate(now);
		cnWsCsReqMst.setUuid(UUID.randomUUID().toString());
		cnWsCsReqMst.setWorkOrderNumber(contractor.getContractNo());
		cnWsCsReqMst.setWorkOrderDate(contractor.getContractDate());
		cnWsCsReqMst.setCreatedBy(userName);
		cnWsCsReqMst.setCreatedDate(new Date());

		CnWsCsRequisitionMst cnWsCsRequisitionMstDb = this
				.contractorRequisitionDtlSave(cnWsCsReqMst,
						cnWsRequisitionMstDtl, userName, roleName, department,
						now, authUser);

		return this.showCnPdRequisition(cnWsCsRequisitionMstDb);
		
	}

	private CnWsCsRequisitionMst contractorRequisitionDtlSave(
			CnWsCsRequisitionMst cnWsCsRequisitionMst,
			CnWsRequisitionMstDtl cnWsRequisitionMstDtl, String userName,
			String roleName, Departments department, Date now, AuthUser authUser) {

		List<String> jobCardDtlIdList = null;
		List<Double> requiredQuantityList = null;
		List<String> jobNoList = null;

		String requisitionNo = "";

		if (cnWsRequisitionMstDtl.getJobCardDtlId() != null) {
			jobCardDtlIdList = cnWsRequisitionMstDtl.getJobCardDtlId();
		}

		if (cnWsRequisitionMstDtl.getQuantityRequired() != null) {
			requiredQuantityList = cnWsRequisitionMstDtl.getQuantityRequired();
		}

		if (cnWsRequisitionMstDtl.getJobCardNo() != null) {
			jobNoList = cnWsRequisitionMstDtl.getJobCardNo();
		}

		CnWsCsRequisitionMst cnWsCsRequisitionMstDb = null;
		String requisitionTo = cnWsRequisitionMstDtl.getRequisitionTo();
		if (jobCardDtlIdList != null) {
			// Requisition Master Save(Insert)
			//String descoDeptCode = department.getDescoCode();
			Departments wsDept = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							descoWSDeptCode);

			String descoWSDeptCode = wsDept.getDescoCode();
			// sequence remove by taleb
			/*requisitionNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoRequisitionNoPrefix, descoWSDeptCode, separator,
							"WS_CS_X_REQ_SEQ");*/
			// sequence add by taleb
			requisitionNo = commonService
					.getOperationIdByPrefixAndSequenceName(
							descoRequisitionNoPrefix, descoWSDeptCode, separator,
							"CN_CS_REQ_SEQ");
			// ---------------------------
			cnWsCsRequisitionMst.setRequisitionNo(requisitionNo);
			commonService.saveOrUpdateModelObjectToDB(cnWsCsRequisitionMst);

			// get Last Requisition by Requisition No
			cnWsCsRequisitionMstDb = (CnWsCsRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst",
							"requisitionNo", requisitionNo);

			List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>();

			int count = 0;
			for (String jobId : jobCardDtlIdList) {
				JobCardDtl jobCardDtl = (JobCardDtl) commonService
						.getAnObjectByAnyUniqueColumn("JobCardDtl", "id", jobId);

				CnWsCsRequisitionDtl cnWsCsRequisitionDtl = new CnWsCsRequisitionDtl(
						null, jobCardDtl.getItemCode(), jobCardDtl.getItemName(),
						jobCardDtl.getUnit(), jobCardDtl.getQuantityUsed(),
						requiredQuantityList.get(count), requisitionNo,
						jobNoList.get(count), true, userName, now,
						cnWsCsRequisitionMst);
				commonService.saveOrUpdateModelObjectToDB(cnWsCsRequisitionDtl);

				// for lock Table
				MyPair pair = new MyPair(jobCardDtl.getItemCode(),
						requiredQuantityList.get(count));
				itemCodeAndQtyList.add(pair);
				count++;
			}
			// requisition item Lock
			this.requisitionLock(itemCodeAndQtyList, requisitionNo, userName,
					now, requisitionTo);
		}

		this.addStoreRequisitionHierarchyHistory(cnWsCsRequisitionMstDb,
				roleName, department, authUser, requisitionTo);

		return cnWsCsRequisitionMstDb;
	}

	private void requisitionLock(List<MyPair> itemCodeAndQtyList,
			String requisitionNo, String userName, Date now,
			String requisitionTo) {
		int extendHours = Integer.parseInt(unlockExpiredHours);
		Date unlockDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(unlockDate);
		c.add(Calendar.HOUR, extendHours);
		unlockDate = c.getTime();

		List<MyPair> finalItemCodeAndQtyList = new ArrayList<MyPair>();
		boolean flag = false;
		for (MyPair m : itemCodeAndQtyList) {
			String icode = m.key();
			for (MyPair mp : finalItemCodeAndQtyList) {
				if (m.key().equals(mp.key())) {
					double nn = mp.value() + m.value();
					MyPair pair = new MyPair(icode, nn);
					finalItemCodeAndQtyList.remove(mp);
					finalItemCodeAndQtyList.add(pair);
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (finalItemCodeAndQtyList.size() == 0) {
				finalItemCodeAndQtyList.add(m);
			}

			if (flag) {
				finalItemCodeAndQtyList.add(m);
			}
		}

		// lock table add data
		for (MyPair mpr : finalItemCodeAndQtyList) {
			RequisitionLock requisitionLock = new RequisitionLock(null,
					requisitionNo, mpr.key().toString(), requisitionTo, true,
					mpr.value(), now, unlockDate, userName, now, true);
			// if (mpr.value() > 0) {
			commonService.saveOrUpdateModelObjectToDB(requisitionLock);
			// }
		}

	}

	public void addStoreRequisitionHierarchyHistory(
			CnWsCsRequisitionMst cnWsCsRequisitionMst, String roleName,
			Departments department, AuthUser authUser, String requisitionTo) {

		// Get All Approval Hierarchy on CN_WS_CS_REQUISITION
		List<ApprovalHierarchy> approvalHierarchyList = null;
		if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
			approvalHierarchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(CN_WS_CS_REQUISITION);
		} 
		//else if (requisitionTo.equals(ContentType.SUB_STORE.toString())) {
			//approvalHierarchyList = approvalHierarchyService
				//	.getApprovalHierarchyByOperationName(CN_WS_SS_REQUISITION);
		//}

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		ApprovalHierarchy approvalHierarchy = null;
		if (stateCodes.length > 0) {

			if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
				approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, stateCodes[0].toString());
			} 
			//else if (requisitionTo.equals(ContentType.SUB_STORE.toString())) {
			//	approvalHierarchy = approvalHierarchyService
			//			.getApprovalHierarchyByOperationNameAndSateCode(
			//					CN_PD_SS_REQUISITION, stateCodes[0].toString());
			//}
		}
		if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
			CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new CnWsCsRequisitionApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(roleName);
			approvalHierarchyHistory.setcDeptName(department.getDeptName());
			approvalHierarchyHistory.setDeptId(department.getDeptId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setOperationId(cnWsCsRequisitionMst
					.getRequisitionNo());
			approvalHierarchyHistory.setOperationName(CN_WS_CS_REQUISITION);
			approvalHierarchyHistory.setCreatedBy(cnWsCsRequisitionMst
					.getCreatedBy());
			approvalHierarchyHistory.setCreatedDate(cnWsCsRequisitionMst
					.getCreatedDate());
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
		/*else {
			CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = new CnSsRequisitionApprovalHierarchyHistory();
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
			approvalHierarchyHistory.setOperationName(CN_PD_SS_REQUISITION);
			approvalHierarchyHistory.setCreatedBy(csRequisitionMst
					.getCreatedBy());
			approvalHierarchyHistory.setCreatedDate(csRequisitionMst
					.getCreatedDate());
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
		}*/

	}

	// Taleb
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/quantityValidation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String quantityValidation(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CnPdRequisitionMstDtl cnPdRequisitionMstDtl = gson.fromJson(cData,
					CnPdRequisitionMstDtl.class);
			Integer jobDtlId = cnPdRequisitionMstDtl.getId();
			Double requiredQty = cnPdRequisitionMstDtl.getQuantity();
			String requisitionTo = cnPdRequisitionMstDtl.getRequisitionTo();
			String contractNo = cnPdRequisitionMstDtl.getContractNo();

			PndJobDtl pndJobDtl = (PndJobDtl) commonService
					.getAnObjectByAnyUniqueColumn("PndJobDtl", "id", jobDtlId
							+ "");

			Contractor contractor = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor",
							"contractNo", contractNo.trim());
			double dbQty = 0.0;
			// itemcode, khathId, ledgertype
			if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
				List<CSItemTransactionMst> csTxnMstList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByTwoColumn("CSItemTransactionMst",
								"itemCode", pndJobDtl.getItemCode(), "khathId",
								contractor.getKhathId() + "");
				for (CSItemTransactionMst trxn : csTxnMstList) {
					dbQty += trxn.getQuantity();
				}

				double lockQty = 0.0;
				List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
						.getObjectListByTwoColumn("RequisitionLock",
								"itemCode", pndJobDtl.getItemCode(),
								"storeCode", requisitionTo);
				for (RequisitionLock rl : reqLockList) {
					lockQty += rl.getQuantity();
				}
				dbQty -= lockQty;
			} else if (requisitionTo.equals(ContentType.SUB_STORE.toString())) {
				List<SSItemTransactionMst> ssTxnMstList = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByTwoColumn("SSItemTransactionMst",
								"itemCode", pndJobDtl.getItemCode(), "khathId",
								contractor.getKhathId() + "");
				for (SSItemTransactionMst trxn : ssTxnMstList) {
					dbQty += trxn.getQuantity();
				}

				double lockQty = 0.0;
				List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
						.getObjectListByTwoColumn("RequisitionLock",
								"itemCode", pndJobDtl.getItemCode(),
								"storeCode", requisitionTo);
				for (RequisitionLock rl : reqLockList) {
					lockQty += rl.getQuantity();
				}
				dbQty -= lockQty;
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (requiredQty <= dbQty) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}

	// Ashid
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/storeQuantityCheck.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String storeQuantityCheck(@RequestBody String cData)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(cData);
		String toJson = "";
		if (isJson) {
			CnPdRequisitionMstDtl cnPdRequisitionMstDtl = gson.fromJson(cData,
					CnPdRequisitionMstDtl.class);
			Integer dtlPkId = cnPdRequisitionMstDtl.getId();
			Double requiredQty = cnPdRequisitionMstDtl.getQuantity();
			String requisitionTo = cnPdRequisitionMstDtl.getRequisitionTo();
			String contractNo = cnPdRequisitionMstDtl.getContractNo();

			CnPdRequisitionDtl cnPdReqDtl = (CnPdRequisitionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CnPdRequisitionDtl", "id",
							dtlPkId + "");

			Contractor contractor = (Contractor) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.contractor.model.Contractor",
							"contractNo", contractNo.trim());
			double dbQty = 0.0;
			// itemcode, khathId, ledgertype
			if (requisitionTo.equals(ContentType.CENTRAL_STORE.toString())) {
				List<CSItemTransactionMst> csTxnMstList = (List<CSItemTransactionMst>) (Object) commonService
						.getObjectListByTwoColumn("CSItemTransactionMst",
								"itemCode", cnPdReqDtl.getItemCode(),
								"khathId", contractor.getKhathId() + "");
				for (CSItemTransactionMst trxn : csTxnMstList) {
					dbQty += trxn.getQuantity();
				}
				double lockQty = 0.0;
				List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
						.getObjectListByTwoColumn("RequisitionLock",
								"itemCode", cnPdReqDtl.getItemCode(),
								"storeCode", requisitionTo);
				for (RequisitionLock rl : reqLockList) {
					lockQty += rl.getQuantity();
				}

				List<RequisitionLock> reqLockList1 = (List<RequisitionLock>) (Object) commonService
						.getObjectListByThreeColumn("RequisitionLock",
								"itemCode", cnPdReqDtl.getItemCode(),
								"requisitionNo", cnPdReqDtl.getRequisitionNo(),
								"storeCode", requisitionTo);
				lockQty -= reqLockList1.get(0).getQuantity();
				dbQty -= lockQty;
			} else if (requisitionTo.equals(ContentType.SUB_STORE.toString())) {
				List<SSItemTransactionMst> ssTxnMstList = (List<SSItemTransactionMst>) (Object) commonService
						.getObjectListByTwoColumn("SSItemTransactionMst",
								"itemCode", cnPdReqDtl.getItemCode(),
								"khathId", contractor.getKhathId() + "");
				for (SSItemTransactionMst trxn : ssTxnMstList) {
					dbQty += trxn.getQuantity();
				}

				double lockQty = 0.0;
				List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
						.getObjectListByTwoColumn("RequisitionLock",
								"itemCode", cnPdReqDtl.getItemCode(),
								"storeCode", requisitionTo);
				for (RequisitionLock rl : reqLockList) {
					lockQty += rl.getQuantity();
				}

				List<RequisitionLock> reqLockList1 = (List<RequisitionLock>) (Object) commonService
						.getObjectListByThreeColumn("RequisitionLock",
								"itemCode", cnPdReqDtl.getItemCode(),
								"requisitionNo", cnPdReqDtl.getRequisitionNo(),
								"storeCode", requisitionTo);
				lockQty -= reqLockList1.get(0).getQuantity();
				dbQty -= lockQty;
			}

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			if (requiredQty <= dbQty) {
				toJson = ow.writeValueAsString("success");
			} else {
				toJson = ow.writeValueAsString("failure");
			}

		} else {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			toJson = ow.writeValueAsString("Sorry!!! Delete Failed.");
		}
		return toJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showCnWsCsRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showCnPdRequisition(
			@ModelAttribute("cnWsCsRequisitionMst") CnWsCsRequisitionMst bean) {

		CnWsCsRequisitionMst cnReqMst = (CnWsCsRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst", "id",
						bean.getId() + "");

		/*if (cnReqMst.getRequisitionTo().equalsIgnoreCase(
				ContentType.SUB_STORE.toString())) {
			return this.showCnPdSSRequisition(bean);
		}*/

		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CnWsCsRequisitionDtl> cnReqDtlList = (List<CnWsCsRequisitionDtl>)(Object)
				commonService.getObjectListByAnyColumn("CnWsCsRequisitionDtl", "cnWsCsRequisitionMst.id", cnReqMst.getId() + "");
				/*pndJobDtlService
				.getCnPdRequisitionDtlListOrderByJobNo(cnReqMst.getId() + "");*/

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
						cnReqMst.getWorkOrderNumber());
		cnReqMst.setWorkOrderDate(contractor.getContractDate());

		//
		String buttonValue = null;
		List<ApprovalHierarchy> nextManReqProcs = null;
		String currentStatus = "";
		List<CnWsCsRequisitionApprovalHierarchyHistory> csApprovalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, cnReqMst.getRequisitionNo(), DONE);

		if (!csApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = csApprovalHierarchyHistoryList.get(
					csApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnWsCsRequisitionApprovalHierarchyHistory> csApprovalHierarchyHistoryOpenList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, cnReqMst.getRequisitionNo(), OPEN);

		int currentStateCode = csApprovalHierarchyHistoryOpenList.get(
				csApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId",
						department.getDeptId());
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
						CN_WS_CS_REQUISITION, roleNameList);

		// Send To Upper Authority of same department
		nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

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
		if (!csApprovalHierarchyHistoryOpenList.isEmpty()
				&& csApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = csApprovalHierarchyHistoryOpenList.get(
					csApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// Decide for return or not
			returnStateCode = csApprovalHierarchyHistoryOpenList.get(
					csApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_WS_CS_REQUISITION, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();

		}

		model.put("returnStateCode", returnStateCode);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", csApprovalHierarchyHistoryList);

		model.put("cnReqMst", cnReqMst);
		model.put("cnReqDtlList", cnReqDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		String roleName = commonService.getAuthRoleName();
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
		return new ModelAndView("workshop/transformer/requisition/cnWsCsRequisitionShowCN", model);
		}else{
			return new ModelAndView("workshop/transformer/requisition/cnWsCsRequisitionShow", model);
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView showCnPdSSRequisition(
			@ModelAttribute("cnPdRequisitionMst") CnPdRequisitionMst bean) {

		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		CnPdRequisitionMst cnReqMst = (CnPdRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CnPdRequisitionMst", "id",
						bean.getId() + "");

		List<CnPdRequisitionDtl> cnReqDtlList = pndJobDtlService
				.getCnPdRequisitionDtlListOrderByJobNo(cnReqMst.getId() + "");

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn("Contractor", "contractNo",
						cnReqMst.getWorkOrderNumber());
		cnReqMst.setWorkOrderDate(contractor.getContractDate());

		//
		String buttonValue = null;
		List<ApprovalHierarchy> nextManReqProcs = null;
		String currentStatus = "";
		List<CnSsRequisitionApprovalHierarchyHistory> ssApprovalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory",
						CN_PD_SS_REQUISITION, cnReqMst.getRequisitionNo(), DONE);

		if (!ssApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = ssApprovalHierarchyHistoryList.get(
					ssApprovalHierarchyHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnSsRequisitionApprovalHierarchyHistory> ssApprovalHierarchyHistoryOpenList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory",
						CN_PD_SS_REQUISITION, cnReqMst.getRequisitionNo(), OPEN);

		int currentStateCode = ssApprovalHierarchyHistoryOpenList.get(
				ssApprovalHierarchyHistoryOpenList.size() - 1).getStateCode();

		List<AuthUser> userList = (List<AuthUser>) (Object) commonService
				.getObjectListByAnyColumn(
						"com.ibcs.desco.admin.model.AuthUser", "deptId",
						department.getDeptId());
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
						CN_PD_SS_REQUISITION, roleNameList);

		// Send To Upper Authority of same department
		nextManReqProcs = new ArrayList<ApprovalHierarchy>();

		for (int countStateCodes = 0; countStateCodes < approveHeirchyList
				.size(); countStateCodes++) {
			if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
				nextManReqProcs.add(approveHeirchyList.get(countStateCodes));
			}
		}

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
		if (!ssApprovalHierarchyHistoryOpenList.isEmpty()
				&& ssApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = ssApprovalHierarchyHistoryOpenList.get(
					ssApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// Decide for return or not
			returnStateCode = ssApprovalHierarchyHistoryOpenList.get(
					ssApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							CN_PD_SS_REQUISITION, stateCode + "");
			buttonValue = approveHeirarchy.getButtonName();

		}

		model.put("returnStateCode", returnStateCode);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("approveHistoryList", ssApprovalHierarchyHistoryList);

		model.put("cnReqMst", cnReqMst);
		model.put("cnReqDtlList", cnReqDtlList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("contractor/cnPdRequisitionShow", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approveCnWsCSRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getApproveCnWsRequisition(CentralStoreRequisitionMstDtl bean,
			Model model) {
		String requisitionNo = bean.getRequisitionNo();
		String justification = bean.getJustification();
		List<Double> requiredQtyList = bean.getQuantityRequired();

		String user = commonService.getAuthUserName();
		CentralStoreRequisitionMst csReqMstDB = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", requisitionNo);
		List<CentralStoreRequisitionDtl> csReqDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		int i = 0;
		for (CentralStoreRequisitionDtl csReqDtl : csReqDtlList) {
			Double requiredQty = requiredQtyList.get(i);
			csReqDtl.setQuantityIssued(requiredQty);
			csReqDtl.setModifiedBy(user);
			csReqDtl.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csReqDtl);
			i++;
		}

		boolean result = this.csRequisitionHierarchyHistory(csReqMstDB,
				justification);
		if (result) {
			model.addAttribute("operationId", csReqMstDB.getRequisitionNo());
			return "centralStore/reports/cnWsCsRequisitionReport";
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approveCnPdSSRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getApproveCnPdSSRequisition(SubStoreRequisitionMstDtl bean,
			Model model) {
		String requisitionNo = bean.getRequisitionNo();
		String justification = bean.getJustification();
		List<Double> requiredQtyList = bean.getQuantityRequired();

		String user = commonService.getAuthUserName();
		SubStoreRequisitionMst ssReqMstDB = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"requisitionNo", requisitionNo);
		List<SubStoreRequisitionDtl> ssReqDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		int i = 0;
		for (SubStoreRequisitionDtl ssReqDtl : ssReqDtlList) {
			Double requiredQty = requiredQtyList.get(i);
			ssReqDtl.setQuantityIssued(requiredQty);
			ssReqDtl.setModifiedBy(user);
			ssReqDtl.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(ssReqDtl);
			i++;
		}

		boolean result = this.ssRequisitionHierarchyHistory(ssReqMstDB,
				justification);
		if (result) {
			model.addAttribute("operationId", ssReqMstDB.getRequisitionNo());
			return "subStore/cnPdSsRequisitionReport";
		} else {
			return "redirect:/ls/requisitionList.do";
		}
	}

	@RequestMapping(value = "/approveCnWsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView approveCnWsRequisition(
			@ModelAttribute("cnPdRequisitionMst") CnWsRequisitionMstDtl bean) {
		String requisitionNo = bean.getRequisitionNo();
		String justification = bean.getJustification();
		List<String> cnReqDtlIdList = bean.getCnReqDtlId();
		List<Double> requiredQtyList = bean.getQuantityRequired();

		String user = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		// String rolePrefix = roleName.substring(0, 7);
		// AuthUser authUser = userService.getAuthUserByUserId(user);
		// String deptId = authUser.getDeptId();
		// Departments department =
		// departmentsService.getDepartmentByDeptId(deptId);

		CnWsCsRequisitionMst cnWsReqMstDB = (CnWsCsRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>();
		for (int i = 0; i < cnReqDtlIdList.size(); i++) {
			String id = cnReqDtlIdList.get(i);
			Double requiredQty = requiredQtyList.get(i);
			CnWsCsRequisitionDtl dtlDB = (CnWsCsRequisitionDtl) commonService
					.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionDtl", "id",
							id);
			dtlDB.setQuantityRequired(requiredQty);
			dtlDB.setModifiedBy(user);
			dtlDB.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(dtlDB);
			// for locak Table
			MyPair pair = new MyPair(dtlDB.getItemCode(), requiredQty);
			itemCodeAndQtyList.add(pair);
		}

		this.requisitionLockUpdate(itemCodeAndQtyList, requisitionNo, user,
				new Date(), cnWsReqMstDB.getRequisitionTo());

		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.updateCnWS_CsRequisitionHierarchyHistory(cnWsReqMstDB,
					justification);
		} 
		/*else {
			this.updateCnPD_SsRequisitionHierarchyHistory(cnWsReqMstDB,
					justification);
		}*/

		if (roleName.equals("ROLE_WO_CN_USER")) {
			return this.getCnRequisitionList();
			// return this.getStoreRequisitionForm();
		} else {
			// Show Pending Requisition List
			return this.getCnWsRequisitionList();

		}
	}

	@SuppressWarnings("unchecked")
	private void requisitionLockUpdate(List<MyPair> itemCodeAndQtyList,
			String requisitionNo, String user, Date now, String requisitionTo) {
		List<MyPair> finalItemCodeAndQtyList = new ArrayList<MyPair>();
		boolean flag = false;
		for (MyPair m : itemCodeAndQtyList) {
			String icode = m.key();
			for (MyPair mp : finalItemCodeAndQtyList) {
				if (m.key().equals(mp.key())) {
					double nn = mp.value() + m.value();
					MyPair pair = new MyPair(icode, nn);
					finalItemCodeAndQtyList.remove(mp);
					finalItemCodeAndQtyList.add(pair);
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (finalItemCodeAndQtyList.size() == 0) {
				finalItemCodeAndQtyList.add(m);
			}

			if (flag) {
				finalItemCodeAndQtyList.add(m);
			}
		}

		// lock table add data
		for (MyPair mpr : finalItemCodeAndQtyList) {
			List<RequisitionLock> requisitionLockList = (List<RequisitionLock>) (Object) commonService
					.getObjectListByTwoColumn("RequisitionLock",
							"requisitionNo", requisitionNo, "itemCode", mpr
									.key().toString());

			if (requisitionLockList.size() > 0) {
				RequisitionLock reqLock = requisitionLockList.get(0);
				reqLock.setQuantity(mpr.value());
				commonService.saveOrUpdateModelObjectToDB(reqLock);
			}
			/*
			 * else{ RequisitionLock requisitionLock = new RequisitionLock(null,
			 * requisitionNo, mpr.key().toString(), requisitionTo, true,
			 * mpr.value(), now, unlockDate, userName, now, true); if
			 * (mpr.value() > 0) {
			 * commonService.saveOrUpdateModelObjectToDB(requisitionLock); } }
			 */

		}
	}

	@SuppressWarnings("unchecked")
	private void updateCnWS_CsRequisitionHierarchyHistory(
			CnWsCsRequisitionMst cnWsReqMstDB, String justification) {
		String user = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		AuthUser authUser = userService.getAuthUserByUserId(user);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get All State Codes from Approval Hierarchy
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_WS_CS_REQUISITION);

		/*
		 * if (reqMst.getRequisitionTo().equals(
		 * ContentType.CENTRAL_STORE.toString())) { // } else if
		 * (reqMst.getRequisitionTo().equals( ContentType.SUB_STORE.toString()))
		 * { // }
		 */

		// Sort State Codes in Descending order
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", cnWsReqMstDB.getRequisitionNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = (approvalHierarchyHistoryList.get(i)).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		// get current State Code from approval hierarchy history

		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();
		int nextStateCode = 0;

		// searching next State code and send this to next person
		for (int state : stateCodes) {
			// if next state code grater than current state code than this
			// process will go to next person
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, nextStateCode + "");

				// next role name
				// next role id
				// next role users dept

				String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
						.getRoleName().substring(0, 8);
				// checking department switching
				if (r1.equals(r2)) {
					//
				} else {
					Roles role = (Roles) commonService
							.getAnObjectByAnyUniqueColumn(
									"com.ibcs.desco.admin.model.Roles", "role",
									approvalHierarchy.getRoleName());
					List<AuthUser> userList = (List<AuthUser>) (Object) commonService
							.getObjectListByAnyColumn(
									"com.ibcs.desco.admin.model.AuthUser",
									"roleid", role.getRole_id() + "");
					Departments depart = (Departments) commonService
							.getAnObjectByAnyUniqueColumn("Departments",
									"deptId", userList.get(0).getDeptId());
					approvalHierarchyHistory.setDeptId(depart.getDeptId());
					approvalHierarchyHistory.setcDeptName(depart.getDeptName());

					if (approvalHierarchy.getRoleName().equalsIgnoreCase(
							"ROLE_CS_JAM")) {
						this.wsToCsRequisitionSave(user, cnWsReqMstDB);
					}

					/*if (approvalHierarchy.getRoleName().equalsIgnoreCase(
							"ROLE_SS_JAM")) {
						this.pdToSsRequisitionSave(user, cnPdReqMstDB);
					}*/
				}

				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());

				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				break;
			}

			// if state code equal to current state code than this
			// process will done for login user
			if (state == currentStateCode) {
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, state + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				approvalHierarchyHistory.setJustification(justification);
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

			// if next state code is last as approval hierarchy than this
			// process will done and go for generate a store ticket
			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// now we have to insert data in store ticket mst and
				// history
				CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
				// csStoreTicketMst.setTicketNo(ticketNo);
				csStoreTicketMst.setStoreTicketType(CN_WS_CS_REQUISITION);
				csStoreTicketMst
						.setOperationId(cnWsReqMstDB.getRequisitionNo());
				csStoreTicketMst.setIssuedTo(cnWsReqMstDB.getDeptName());
				csStoreTicketMst.setIssuedFor(cnWsReqMstDB
						.getIdenterDesignation());
				csStoreTicketMst.setFlag(false);

				csStoreTicketMst.setKhathId(cnWsReqMstDB.getKhathId());
				csStoreTicketMst.setKhathName(cnWsReqMstDB.getKhathName());

				// Auto generate Store Ticket number
				String descoDeptCode = department.getDescoCode();
				csStoreTicketMst.setIssuedBy(cnWsReqMstDB.getRequisitionTo());

				String storeTicketNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode,
								separator, "CS_ST_SEQ");

				csStoreTicketMst.setTicketNo(storeTicketNo);

				commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

				// Requisition Approved
				// Update PD_Req_MST
				cnWsReqMstDB.setStoreTicketNO(storeTicketNo);
				cnWsReqMstDB.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnWsReqMstDB);
				{
					// Update CS_Req_MST
					CentralStoreRequisitionMst csReqMstDb = (CentralStoreRequisitionMst) commonService
							.getAnObjectByAnyUniqueColumn(
									"CentralStoreRequisitionMst",
									"requisitionNo",
									cnWsReqMstDB.getRequisitionNo());

					csReqMstDb.setStoreTicketNO(storeTicketNo);
					csReqMstDb.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(csReqMstDb);
				}

				/*
				 * // Cn_pd_requisition dtl update start
				 * List<CnPdRequisitionDtl> cnPdReqDtlList =
				 * (List<CnPdRequisitionDtl>) (Object) commonService
				 * .getObjectListByAnyColumn("CnPdRequisitionDtl",
				 * "requisitionNo", cnPdReqMstDB.getRequisitionNo());
				 * 
				 * for (CnPdRequisitionDtl reqDtl : cnPdReqDtlList) {
				 * reqDtl.setQuantityIssued(reqDtl.getQuantityRequired());
				 * commonService.saveOrUpdateModelObjectToDB(reqDtl);
				 * 
				 * List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>) (Object)
				 * commonService .getObjectListByThreeColumn("PndJobDtl",
				 * "pndJobMst.jobNo", reqDtl.getJobNo(), "pndJobMst.woNumber",
				 * cnPdReqMstDB.getWorkOrderNumber(), "itemCode",
				 * reqDtl.getItemCode()); if (pndJobDtlList.size() > 0) {
				 * PndJobDtl pndJobDtl = pndJobDtlList.get(0);
				 * pndJobDtl.setRemainningQuantity(pndJobDtl
				 * .getRemainningQuantity() - reqDtl.getQuantityIssued());
				 * commonService.saveOrUpdateModelObjectToDB(pndJobDtl); } } //
				 * Cn_pd_requisition dtl update end
				 */
				CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
						.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
								"operationId", cnWsReqMstDB.getRequisitionNo());

				//

				// Get All Approval Hierarchy on CS_STORE_TICKET
				List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
						.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

				Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
						.size()];
				for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
					sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i)
							.getStateCode();
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
				storeTicketApprovalHierarchyHistory.setOperationId(cnWsReqMstDB
						.getRequisitionNo());
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setcDeptName(department
						.getDeptName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				storeTicketApprovalHierarchyHistory
						.setTicketNo(csStoreTicketMstdb.getTicketNo());
				storeTicketApprovalHierarchyHistory
						.setOperationName(CS_STORE_TICKET);
				storeTicketApprovalHierarchyHistory.setCreatedBy(user);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
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

				// model.addAttribute("operationId", reqMst.getRequisitionNo());

				// return "centralStore/csRequisitionReport";

			}
		}

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void updateCnPD_SsRequisitionHierarchyHistory(
			CnPdRequisitionMst cnPdReqMstDB, String justification) {
		String user = commonService.getAuthUserName();
		String roleName = commonService.getAuthRoleName();
		AuthUser authUser = userService.getAuthUserByUserId(user);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get All State Codes from Approval Hierarchy
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_PD_SS_REQUISITION);

		// Sort State Codes in Descending order
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationId", cnPdReqMstDB.getRequisitionNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = (approvalHierarchyHistoryList.get(i)).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		// get current State Code from approval hierarchy history

		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnSsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnSsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();
		int nextStateCode = 0;

		// searching next State code and send this to next person
		for (int state : stateCodes) {
			// if next state code grater than current state code than this
			// process will go to next person
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_REQUISITION, nextStateCode + "");

				// next role name
				// next role id
				// next role users dept

				String r1 = roleName.substring(0, 8), r2 = approvalHierarchy
						.getRoleName().substring(0, 8);
				// checking department switching
				if (r1.equals(r2)) {
					//
				} else {
					Roles role = (Roles) commonService
							.getAnObjectByAnyUniqueColumn(
									"com.ibcs.desco.admin.model.Roles", "role",
									approvalHierarchy.getRoleName());
					List<AuthUser> userList = (List<AuthUser>) (Object) commonService
							.getObjectListByAnyColumn(
									"com.ibcs.desco.admin.model.AuthUser",
									"roleid", role.getRole_id() + "");
					Departments depart = (Departments) commonService
							.getAnObjectByAnyUniqueColumn("Departments",
									"deptId", userList.get(0).getDeptId());
					approvalHierarchyHistory.setDeptId(depart.getDeptId());
					approvalHierarchyHistory.setcDeptName(depart.getDeptName());

					if (approvalHierarchy.getRoleName().equalsIgnoreCase(
							"ROLE_SS_JAM")) {
						this.pdToSsRequisitionSave(user, cnPdReqMstDB);
					}
				}

				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());

				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				break;
			}

			// if state code equal to current state code than this
			// process will done for login user
			if (state == currentStateCode) {
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_REQUISITION, state + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				approvalHierarchyHistory.setJustification(justification);
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

			// if next state code is last as approval hierarchy than this
			// process will done and go for generate a store ticket
			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// now we have to insert data in store ticket mst and
				// history
				SSStoreTicketMst ssStoreTicketMst = new SSStoreTicketMst();
				// csStoreTicketMst.setTicketNo(ticketNo);
				ssStoreTicketMst.setStoreTicketType(CN_PD_SS_REQUISITION);
				ssStoreTicketMst
						.setOperationId(cnPdReqMstDB.getRequisitionNo());
				ssStoreTicketMst.setIssuedTo(cnPdReqMstDB.getDeptName());
				ssStoreTicketMst.setIssuedFor(cnPdReqMstDB
						.getIdenterDesignation());
				ssStoreTicketMst.setFlag(false);

				ssStoreTicketMst.setKhathId(cnPdReqMstDB.getKhathId());
				ssStoreTicketMst.setKhathName(cnPdReqMstDB.getKhathName());

				// Auto generate Store Ticket number
				String descoDeptCode = department.getDescoCode();

				String storeTicketNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode,
								separator, "SS_ST_SEQ");

				ssStoreTicketMst.setTicketNo(storeTicketNo);

				commonService.saveOrUpdateModelObjectToDB(ssStoreTicketMst);

				// Requisition Approved
				// Update PD_Req_MST
				cnPdReqMstDB.setStoreTicketNO(storeTicketNo);
				cnPdReqMstDB.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnPdReqMstDB);
				{
					// Update SS_Req_MST
					SubStoreRequisitionMst ssReqMst = (SubStoreRequisitionMst) commonService
							.getAnObjectByAnyUniqueColumn(
									"SubStoreRequisitionMst", "requisitionNo",
									cnPdReqMstDB.getRequisitionNo());
					ssReqMst.setStoreTicketNO(storeTicketNo);
					ssReqMst.setApproved(true);
					commonService.saveOrUpdateModelObjectToDB(ssReqMst);
				}
				/*
				 * // Cn_pd_requisition dtl update start
				 * List<CnPdRequisitionDtl> cnPdReqDtlList =
				 * (List<CnPdRequisitionDtl>) (Object) commonService
				 * .getObjectListByAnyColumn("CnPdRequisitionDtl",
				 * "requisitionNo", cnPdReqMstDB.getRequisitionNo());
				 * 
				 * for (CnPdRequisitionDtl reqDtl : cnPdReqDtlList) {
				 * reqDtl.setQuantityIssued(reqDtl.getQuantityRequired());
				 * commonService.saveOrUpdateModelObjectToDB(reqDtl);
				 * 
				 * List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>) (Object)
				 * commonService .getObjectListByThreeColumn("PndJobDtl",
				 * "pndJobMst.jobNo", reqDtl.getJobNo(), "pndJobMst.woNumber",
				 * cnPdReqMstDB.getWorkOrderNumber(), "itemCode",
				 * reqDtl.getItemCode()); if (pndJobDtlList.size() > 0) {
				 * PndJobDtl pndJobDtl = pndJobDtlList.get(0);
				 * pndJobDtl.setRemainningQuantity(pndJobDtl
				 * .getRemainningQuantity() - reqDtl.getQuantityIssued());
				 * commonService.saveOrUpdateModelObjectToDB(pndJobDtl); } } //
				 * Cn_pd_requisition dtl update end
				 */
				SSStoreTicketMst csStoreTicketMstdb = (SSStoreTicketMst) commonService
						.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
								"operationId", cnPdReqMstDB.getRequisitionNo());

				//

				// Get All Approval Hierarchy on CS_STORE_TICKET
				List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
						.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

				Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
						.size()];
				for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
					sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i)
							.getStateCode();
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
				storeTicketApprovalHierarchyHistory.setOperationId(cnPdReqMstDB
						.getRequisitionNo());
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setcDeptName(department
						.getDeptName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				storeTicketApprovalHierarchyHistory
						.setTicketNo(csStoreTicketMstdb.getTicketNo());
				storeTicketApprovalHierarchyHistory
						.setOperationName(SS_STORE_TICKET);
				storeTicketApprovalHierarchyHistory.setCreatedBy(user);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
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

				// model.addAttribute("operationId", reqMst.getRequisitionNo());

				// return "centralStore/csRequisitionReport";

			}
		}

	}

	@SuppressWarnings("unchecked")
	private boolean csRequisitionHierarchyHistory(
			CentralStoreRequisitionMst cnWsReqMstDB, String justification) {
		String user = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(user);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get All State Codes from Approval Hierarchy
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_WS_CS_REQUISITION);

		// Sort State Codes in Descending order
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", cnWsReqMstDB.getRequisitionNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = (approvalHierarchyHistoryList.get(i)).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		// get current State Code from approval hierarchy history

		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();
		int nextStateCode = 0;

		// searching next State code and send this to next person
		for (int state : stateCodes) {
			// if next state code grater than current state code than this
			// process will go to next person
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, nextStateCode + "");

				// next role name
				// next role id
				// next role users dept

				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());

				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				break;
			}

			// if state code equal to current state code than this
			// process will done for login user
			if (state == currentStateCode) {
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, state + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				approvalHierarchyHistory.setJustification(justification);
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

			// if next state code is last as approval hierarchy than this
			// process will done and go for generate a store ticket
			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());				
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// now we have to insert data in store ticket mst and
				// history
				CSStoreTicketMst csStoreTicketMst = new CSStoreTicketMst();
				// csStoreTicketMst.setTicketNo(ticketNo);
				csStoreTicketMst.setStoreTicketType(CN_WS_CS_REQUISITION);
				csStoreTicketMst
						.setOperationId(cnWsReqMstDB.getRequisitionNo());
				csStoreTicketMst.setIssuedTo(cnWsReqMstDB.getDeptName());
				csStoreTicketMst.setIssuedFor(cnWsReqMstDB
						.getIdenterDesignation());
				csStoreTicketMst.setFlag(false);

				csStoreTicketMst.setKhathId(cnWsReqMstDB.getKhathId());
				csStoreTicketMst.setKhathName(cnWsReqMstDB.getKhathName());

				// Auto generate Store Ticket number
				String descoDeptCode = department.getDescoCode();
				csStoreTicketMst.setIssuedBy(cnWsReqMstDB.getRequisitionTo());

				String storeTicketNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode,
								separator, "CS_ST_SEQ");

				csStoreTicketMst.setTicketNo(storeTicketNo);

				commonService.saveOrUpdateModelObjectToDB(csStoreTicketMst);

				// Requisition Approved
				// Update PD_Req_MST
				cnWsReqMstDB.setStoreTicketNO(storeTicketNo);
				cnWsReqMstDB.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnWsReqMstDB);

				/*CnWsCsRequisitionMst cnWsRequisitionMst = (CnWsCsRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst",
								"requisitionNo",
								cnWsReqMstDB.getRequisitionNo());
				cnWsRequisitionMst.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnWsRequisitionMst);

				List<CnWsCsRequisitionDtl> cnWsReqDtlList = (List<CnWsCsRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("CnWsCsRequisitionDtl",
								"requisitionNo",
								cnWsReqMstDB.getRequisitionNo());

				for (CnWsCsRequisitionDtl reqDtl : cnWsReqDtlList) {
					reqDtl.setQuantityIssued(reqDtl.getQuantityRequired());
					commonService.saveOrUpdateModelObjectToDB(reqDtl);

					List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
							.getObjectListByThreeColumn("JobCardDtl",
									"jobCardMst.jobCardNo", reqDtl.getJobNo(),
									"jobCardMst.contractNo",
									cnWsReqMstDB.getWorkOrderNumber(),
									"itemCode", reqDtl.getItemCode());
					if (jobCardDtlList.size() > 0) {
						JobCardDtl jobCardDtl = jobCardDtlList.get(0);
						jobCardDtl.setRemainningQuantity(jobCardDtl
								.getRemainningQuantity()
								- reqDtl.getQuantityIssued());
						commonService.saveOrUpdateModelObjectToDB(jobCardDtl);
					}
				}*/
				// Cn_pd_requisition dtl update end

				CSStoreTicketMst csStoreTicketMstdb = (CSStoreTicketMst) commonService
						.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
								"operationId", cnWsReqMstDB.getRequisitionNo());

				//

				// Get All Approval Hierarchy on CS_STORE_TICKET
				List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
						.getApprovalHierarchyByOperationName(CS_STORE_TICKET);

				Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
						.size()];
				for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
					sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i)
							.getStateCode();
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
				storeTicketApprovalHierarchyHistory.setOperationId(cnWsReqMstDB
						.getRequisitionNo());
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setcDeptName(department
						.getDeptName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				storeTicketApprovalHierarchyHistory
						.setTicketNo(csStoreTicketMstdb.getTicketNo());
				storeTicketApprovalHierarchyHistory
						.setOperationName(CS_STORE_TICKET);
				storeTicketApprovalHierarchyHistory.setCreatedBy(user);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
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

				// model.addAttribute("operationId", reqMst.getRequisitionNo());

				// return "centralStore/csRequisitionReport";

				return true;

			}
		}
		return false;
		//

	}

	@SuppressWarnings("unchecked")
	private boolean ssRequisitionHierarchyHistory(
			SubStoreRequisitionMst cnPdReqMstDB, String justification) {
		String user = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(user);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);

		// get All State Codes from Approval Hierarchy
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CN_PD_SS_REQUISITION);

		// Sort State Codes in Descending order
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationId", cnPdReqMstDB.getRequisitionNo());

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = (approvalHierarchyHistoryList.get(i)).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		// get current State Code from approval hierarchy history

		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnSsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnSsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();
		int nextStateCode = 0;

		// searching next State code and send this to next person
		for (int state : stateCodes) {
			// if next state code grater than current state code than this
			// process will go to next person
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_REQUISITION, nextStateCode + "");

				// next role name
				// next role id
				// next role users dept

				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());

				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
				break;
			}

			// if state code equal to current state code than this
			// process will done for login user
			if (state == currentStateCode) {
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_REQUISITION, state + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				approvalHierarchyHistory.setJustification(justification);
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
			}

			// if next state code is last as approval hierarchy than this
			// process will done and go for generate a store ticket
			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setCreatedBy(user);
				approvalHierarchyHistory.setModifiedBy(user);
				approvalHierarchyHistory.setModifiedDate(new Date());
				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// now we have to insert data in store ticket mst and
				// history
				SSStoreTicketMst ssStoreTicketMst = new SSStoreTicketMst();
				// csStoreTicketMst.setTicketNo(ticketNo);
				ssStoreTicketMst.setStoreTicketType(CN_PD_SS_REQUISITION);
				ssStoreTicketMst
						.setOperationId(cnPdReqMstDB.getRequisitionNo());
				ssStoreTicketMst.setIssuedTo(cnPdReqMstDB.getDeptName());
				ssStoreTicketMst.setIssuedFor(cnPdReqMstDB
						.getIdenterDesignation());
				ssStoreTicketMst.setFlag(false);

				ssStoreTicketMst.setKhathId(cnPdReqMstDB.getKhathId());
				ssStoreTicketMst.setKhathName(cnPdReqMstDB.getKhathName());

				// Auto generate Store Ticket number
				String descoDeptCode = department.getDescoCode();

				String storeTicketNo = commonService
						.getOperationIdByPrefixAndSequenceName(
								descoStoreTicketNoPrefix, descoDeptCode,
								separator, "SS_ST_SEQ");

				ssStoreTicketMst.setTicketNo(storeTicketNo);

				commonService.saveOrUpdateModelObjectToDB(ssStoreTicketMst);

				// Requisition Approved
				// Update PD_Req_MST
				cnPdReqMstDB.setStoreTicketNO(storeTicketNo);
				cnPdReqMstDB.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnPdReqMstDB);

				CnPdRequisitionMst cnPdRequisitionMst = (CnPdRequisitionMst) commonService
						.getAnObjectByAnyUniqueColumn("CnPdRequisitionMst",
								"requisitionNo",
								cnPdReqMstDB.getRequisitionNo());
				cnPdRequisitionMst.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(cnPdRequisitionMst);

				// Cn_pd_requisition dtl update start
				List<CnPdRequisitionDtl> cnPdReqDtlList = (List<CnPdRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("CnPdRequisitionDtl",
								"requisitionNo",
								cnPdReqMstDB.getRequisitionNo());

				for (CnPdRequisitionDtl reqDtl : cnPdReqDtlList) {
					reqDtl.setQuantityIssued(reqDtl.getQuantityRequired());
					commonService.saveOrUpdateModelObjectToDB(reqDtl);

					List<PndJobDtl> pndJobDtlList = (List<PndJobDtl>) (Object) commonService
							.getObjectListByThreeColumn("PndJobDtl",
									"pndJobMst.jobNo", reqDtl.getJobNo(),
									"pndJobMst.woNumber",
									cnPdReqMstDB.getWorkOrderNo(), "itemCode",
									reqDtl.getItemCode());
					if (pndJobDtlList.size() > 0) {
						PndJobDtl pndJobDtl = pndJobDtlList.get(0);
						pndJobDtl.setRemainningQuantity(pndJobDtl
								.getRemainningQuantity()
								- reqDtl.getQuantityIssued());
						commonService.saveOrUpdateModelObjectToDB(pndJobDtl);
					}
				}
				// Cn_pd_requisition dtl update end

				SSStoreTicketMst ssStoreTicketMstdb = (SSStoreTicketMst) commonService
						.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
								"operationId", cnPdReqMstDB.getRequisitionNo());

				//

				// Get All Approval Hierarchy on CS_STORE_TICKET
				List<ApprovalHierarchy> approvalHierarchyListDb = approvalHierarchyService
						.getApprovalHierarchyByOperationName(SS_STORE_TICKET);

				Integer[] sStoreTicketStateCodes = new Integer[approvalHierarchyListDb
						.size()];
				for (int i = 0; i < approvalHierarchyListDb.size(); i++) {
					sStoreTicketStateCodes[i] = approvalHierarchyListDb.get(i)
							.getStateCode();
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
				storeTicketApprovalHierarchyHistory.setOperationId(cnPdReqMstDB
						.getRequisitionNo());
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setcDeptName(department
						.getDeptName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				storeTicketApprovalHierarchyHistory
						.setTicketNo(ssStoreTicketMstdb.getTicketNo());
				storeTicketApprovalHierarchyHistory
						.setOperationName(SS_STORE_TICKET);
				storeTicketApprovalHierarchyHistory.setCreatedBy(user);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
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

				// model.addAttribute("operationId", reqMst.getRequisitionNo());

				// return "centralStore/csRequisitionReport";

				return true;

			}
		}
		return false;
		//

	}

	@SuppressWarnings("unchecked")
	private void wsToCsRequisitionSave(String user,
			CnWsCsRequisitionMst cnWsReqMstDB) {
		// if next user is ROLE_CS_JAM, insert into
		// CS_REQ_MST_DTL

		// set Mst Start
		CentralStoreRequisitionMst csReqMst = new CentralStoreRequisitionMst();
		csReqMst.setCreatedBy(user);
		csReqMst.setCreatedDate(new Date());
		csReqMst.setRequisitionNo(cnWsReqMstDB.getRequisitionNo());
		csReqMst.setReceivedBy(cnWsReqMstDB.getReceivedBy());
		csReqMst.setRequisitionDate(cnWsReqMstDB.getRequisitionDate());
		csReqMst.setId(null);
		csReqMst.setKhathId(cnWsReqMstDB.getKhathId());
		DescoKhath khath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						cnWsReqMstDB.getKhathId() + "");
		csReqMst.setKhathName(khath.getKhathName()); // New
		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						cnWsReqMstDB.getDeptId());
		csReqMst.setDeptName(department.getDeptName()); // New
		csReqMst.setUuid(cnWsReqMstDB.getUuid());
		csReqMst.setIdenterDesignation(cnWsReqMstDB.getIdenterDesignation());
		csReqMst.setWorkOrderNumber(cnWsReqMstDB.getWorkOrderNumber());
		csReqMst.setSenderStore("cnws"); // New
		csReqMst.setRequisitionTo(ContentType.CENTRAL_STORE.toString()); // New
		csReqMst.setSndCode(cnWsReqMstDB.getDeptId()); // New
		csReqMst.setReceived(false);
		csReqMst.setApproved(false);
		csReqMst.setActive(true);
		commonService.saveOrUpdateModelObjectToDB(csReqMst);
		// mst end and start dtl
		CentralStoreRequisitionMst csReqMstDb = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"requisitionNo", cnWsReqMstDB.getRequisitionNo());

		List<CnWsCsRequisitionDtl> cnWsCsRequisitionDtlList = (List<CnWsCsRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CnWsCsRequisitionDtl",
						"requisitionNo", cnWsReqMstDB.getRequisitionNo());

		List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>();
		for (CnWsCsRequisitionDtl cnWsReqDtl : cnWsCsRequisitionDtlList) {
			MyPair pair = new MyPair(cnWsReqDtl.getItemCode(),
					cnWsReqDtl.getQuantityRequired());
			itemCodeAndQtyList.add(pair);
		}

		//
		List<MyPair> finalItemCodeAndQtyList = new ArrayList<MyPair>();
		boolean flag = false;
		for (MyPair m : itemCodeAndQtyList) {
			String icode = m.key();
			for (MyPair mp : finalItemCodeAndQtyList) {
				if (m.key().equals(mp.key())) {
					double nn = mp.value() + m.value();
					MyPair pair = new MyPair(icode, nn);
					finalItemCodeAndQtyList.remove(mp);
					finalItemCodeAndQtyList.add(pair);
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (finalItemCodeAndQtyList.size() == 0) {
				finalItemCodeAndQtyList.add(m);
			}

			if (flag) {
				finalItemCodeAndQtyList.add(m);
			}
		}
		//
		for (MyPair mpr : finalItemCodeAndQtyList) {
			// System.out.println(mpr.key() + " : " + mpr.value());
			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId", mpr
							.key().trim());
			CentralStoreRequisitionDtl csReqDtl = new CentralStoreRequisitionDtl();
			csReqDtl.setActive(true);
			csReqDtl.setCentralStoreRequisitionMst(csReqMstDb);
			csReqDtl.setId(null);
			csReqDtl.setItemCode(mpr.key());
			csReqDtl.setQuantityIssued(0.0);
			csReqDtl.setQuantityRequired(mpr.value());
			csReqDtl.setItemName(itemMaster.getItemName());
			csReqDtl.setRequisitionNo(csReqMstDb.getRequisitionNo());
			csReqDtl.setCreatedBy(user);
			csReqDtl.setCreatedDate(new Date());
			csReqDtl.setUom(itemMaster.getUnitCode());
			csReqDtl.setUnitCost(0);
			csReqDtl.setTotalCost(0);
			csReqDtl.setRemarks("");
			if (csReqDtl.getQuantityRequired() > 0) {
				commonService.saveOrUpdateModelObjectToDB(csReqDtl);
			}

		}
		// dtl end
		this.cnWsRequisitionDtl0QtyDelete(cnWsCsRequisitionDtlList);
		this.requisitionLock0QtyDelete(cnWsReqMstDB.getRequisitionNo());
	}

	private void cnWsRequisitionDtl0QtyDelete(
			List<CnWsCsRequisitionDtl> cnWsCsRequisitionDtlList) {
		for (CnWsCsRequisitionDtl reqDtl : cnWsCsRequisitionDtlList) {
			if (reqDtl.getQuantityRequired() <= 0) {
				commonService.deleteAnObjectById("CnPdRequisitionDtl",
						reqDtl.getId());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void pdToSsRequisitionSave(String user,
			CnPdRequisitionMst cnPdReqMstDB) {
		// if next user is ROLE_CS_JAM, insert into
		// CS_REQ_MST_DTL

		// set Mst Start
		SubStoreRequisitionMst ssReqMst = new SubStoreRequisitionMst();
		ssReqMst.setCreatedBy(user);
		ssReqMst.setCreatedDate(new Date());
		ssReqMst.setRequisitionNo(cnPdReqMstDB.getRequisitionNo());
		ssReqMst.setReceivedBy(cnPdReqMstDB.getReceivedBy());
		ssReqMst.setRequisitionDate(cnPdReqMstDB.getRequisitionDate());
		ssReqMst.setId(null);
		ssReqMst.setKhathId(cnPdReqMstDB.getKhathId());
		DescoKhath khath = (DescoKhath) commonService
				.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
						cnPdReqMstDB.getKhathId() + "");
		ssReqMst.setKhathName(khath.getKhathName()); // New
		Departments department = (Departments) commonService
				.getAnObjectByAnyUniqueColumn("Departments", "deptId",
						cnPdReqMstDB.getDeptId());
		ssReqMst.setDeptName(department.getDeptName()); // New
		ssReqMst.setUuid(cnPdReqMstDB.getUuid());
		ssReqMst.setIdenterDesignation(cnPdReqMstDB.getIdenterDesignation());
		ssReqMst.setWorkOrderNo(cnPdReqMstDB.getWorkOrderNumber());
		ssReqMst.setSenderStore("cnpd"); // New
		ssReqMst.setRequisitionTo(ContentType.SUB_STORE.toString()); // New
		ssReqMst.setSndCode(cnPdReqMstDB.getDeptId()); // New
		ssReqMst.setReceived(false);
		ssReqMst.setApproved(false);
		ssReqMst.setActive(true);
		commonService.saveOrUpdateModelObjectToDB(ssReqMst);
		// mst end and start dtl
		SubStoreRequisitionMst csReqMstDb = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"requisitionNo", cnPdReqMstDB.getRequisitionNo());

		List<CnPdRequisitionDtl> cnPdRequisitionDtlList = (List<CnPdRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CnPdRequisitionDtl",
						"requisitionNo", cnPdReqMstDB.getRequisitionNo());

		List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>();
		for (CnPdRequisitionDtl cnPdReqDtl : cnPdRequisitionDtlList) {
			MyPair pair = new MyPair(cnPdReqDtl.getItemCode(),
					cnPdReqDtl.getQuantityRequired());
			itemCodeAndQtyList.add(pair);
		}

		//
		List<MyPair> finalItemCodeAndQtyList = new ArrayList<MyPair>();
		boolean flag = false;
		for (MyPair m : itemCodeAndQtyList) {
			String icode = m.key();
			for (MyPair mp : finalItemCodeAndQtyList) {
				if (m.key().equals(mp.key())) {
					double nn = mp.value() + m.value();
					MyPair pair = new MyPair(icode, nn);
					finalItemCodeAndQtyList.remove(mp);
					finalItemCodeAndQtyList.add(pair);
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (finalItemCodeAndQtyList.size() == 0) {
				finalItemCodeAndQtyList.add(m);
			}

			if (flag) {
				finalItemCodeAndQtyList.add(m);
			}
		}
		//
		for (MyPair mpr : finalItemCodeAndQtyList) {
			// System.out.println(mpr.key() + " : " + mpr.value());
			ItemMaster itemMaster = (ItemMaster) commonService
					.getAnObjectByAnyUniqueColumn("ItemMaster", "itemId", mpr
							.key().trim());
			SubStoreRequisitionDtl ssReqDtl = new SubStoreRequisitionDtl();
			ssReqDtl.setActive(true);
			ssReqDtl.setSubStoreRequisitionMst(csReqMstDb);
			ssReqDtl.setId(null);
			ssReqDtl.setItemCode(mpr.key());
			ssReqDtl.setQuantityIssued(0.0);
			ssReqDtl.setQuantityRequired(mpr.value());
			ssReqDtl.setItemName(itemMaster.getItemName());
			ssReqDtl.setRequisitionNo(csReqMstDb.getRequisitionNo());
			ssReqDtl.setCreatedBy(user);
			ssReqDtl.setCreatedDate(new Date());
			ssReqDtl.setUom(itemMaster.getUnitCode());
			ssReqDtl.setUnitCost(0);
			ssReqDtl.setTotalCost(0);
			ssReqDtl.setRemarks("");
			if (ssReqDtl.getQuantityRequired() > 0) {
				commonService.saveOrUpdateModelObjectToDB(ssReqDtl);
			}

		}
		// dtl end
		//this.cnWsRequisitionDtl0QtyDelete(cnPdRequisitionDtlList);
		this.requisitionLock0QtyDelete(cnPdReqMstDB.getRequisitionNo());
	}

	@SuppressWarnings("unchecked")
	private void requisitionLock0QtyDelete(String requisitionNo) {
		List<RequisitionLock> reqLockList = (List<RequisitionLock>) (Object) commonService
				.getObjectListByAnyColumn("RequisitionLock", "requisitionNo",
						requisitionNo);
		for (RequisitionLock reqLock : reqLockList) {
			if (reqLock.getQuantity() <= 0) {
				commonService.deleteAnObjectById("RequisitionLock",
						reqLock.getId());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/requisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login person Role and login person information
		String roleName = commonService.getAuthRoleName();
		// String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_KHATH_TRANSFER
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationName", CN_WS_CS_REQUISITION, "actRoleName",
						roleName, "status", OPEN);

		/*List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryListSS = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationName", CN_PD_SS_REQUISITION, "actRoleName",
						roleName, "status", OPEN);
						
		String[] operationId1 = new String[approvalHierarchyHistoryListSS
				.size()];
		int y = 0;
		for (CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryListSS) {
			operationId1[y] = approvalHierarchyHistory.getOperationId();
			y++;
		}
		*/

		// get operationId List from approval hierarchy history
		//String[] operationId = new String[approvalHierarchyHistoryList.size()];
		List<String> operationIdList = new ArrayList<String>();
		//int x = 0;
		for (CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			//operationId[x] = approvalHierarchyHistory.getOperationId();
			operationIdList.add(approvalHierarchyHistory.getOperationId());
			//x++;
		}

		

		//String[] operationIds = new String[operationId.length];
		//operationIds = (String[]) ArrayUtils.addAll(operationId, operationId1);

		// get all cnPdRequisitionMst List which open for login user

		/*List<CnPdRequisitionMst> cnPdRequisitionMstList = pndJobDtlService
				.getCnPdRequisitionMstListByOperationIds(operationIds);*/
		
		List<CnWsCsRequisitionMst> cnWsCsRequisitionMstList = (List<CnWsCsRequisitionMst>)(Object)
				commonService.getObjectListByAnyColumnValueList("CnWsCsRequisitionMst", "requisitionNo", operationIdList);
		// sent to the browser
		model.put("cnWsCsRequisitionMstList", cnWsCsRequisitionMstList);
		return new ModelAndView("workshop/transformer/requisition/cnToWsRequisitionListCN", model);
	}

	// Added By Ashid
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnWsRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnWsRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login person Role and login person information
		String roleName = commonService.getAuthRoleName();
		// String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		// get all approval hierarchy history where status open, as login user
		// role and operation name CS_KHATH_TRANSFER
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationName", CN_WS_CS_REQUISITION, "actRoleName",
						roleName, "status", OPEN);

		/*List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryListSS = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationName", CN_PD_SS_REQUISITION, "actRoleName",
						roleName, "status", OPEN);*/

		// get operationId List from approval hierarchy history
		//String[] operationId = new String[approvalHierarchyHistoryList.size()];
		//int x = 0;
		List<String> operationId = 
				new ArrayList<String>();
		for (CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId.add(approvalHierarchyHistory.getOperationId());
			//operationId[x] = approvalHierarchyHistory.getOperationId();
			//x++;
		}

		/*String[] operationId1 = new String[approvalHierarchyHistoryListSS
				.size()];
		int y = 0;
		for (CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory : approvalHierarchyHistoryListSS) {
			operationId1[y] = approvalHierarchyHistory.getOperationId();
			y++;
		}

		String[] operationIds = new String[operationId1.length
				+ operationId.length];
		operationIds = (String[]) ArrayUtils.addAll(operationId, operationId1);
		 */
		// get all cnPdRequisitionMst List which open for login user

		List<CnWsCsRequisitionMst> cnWsRequisitionMstList = (List<CnWsCsRequisitionMst>)(Object)
		commonService.getObjectListByAnyColumnValueList("CnWsCsRequisitionMst", "requisitionNo", operationId);		
		// sent to the browser
		model.put("cnWsRequisitionMstList", cnWsRequisitionMstList);
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_WO")) {
			return new ModelAndView("workshop/transformer/requisition/cnToWsRequisitionListCN", model);
		}else{
			return new ModelAndView("workshop/transformer/requisition/cnToWsRequisitionList", model);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/storeRequisitionShow.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getStoreRequisitionShow(
			CentralStoreRequisitionMst centralStoreRequisitionMst)
			throws Exception {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		String requisitionTo = centralStoreRequisitionMst.getRequisitionTo();

		/*if (requisitionTo.equalsIgnoreCase(ContentType.SUB_STORE.toString())) {
			return this.getStoreRequisitionShowSS(centralStoreRequisitionMst);
		}*/

		CentralStoreRequisitionMst centralStoreRequisitionMstdb = centralStoreRequisitionMstService
				.getCentralStoreRequisitionMst(centralStoreRequisitionMst
						.getId());
		String operationId = centralStoreRequisitionMstdb.getRequisitionNo();

		List<CentralStoreRequisitionDtl> centralStoreRequisitionDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnWsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, operationId, DONE);

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

		List<CnWsCsRequisitionApprovalHierarchyHistory> sCsRequisitionApprovalHierarchyHistoryOpenList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						CN_WS_CS_REQUISITION, operationId, OPEN);

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
						CN_WS_CS_REQUISITION, roleNameList);
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
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_WS_CS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get all Location under CS || Added by Ashid
		List<StoreLocations> storeLocationList = this
				.getStoreLocationList("CS");

		/*
		 * The following four lines are added by Ihteshamul Alam. it will show
		 * username instead of userid
		 */
		String userrole = centralStoreRequisitionMstdb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.admin.model.AuthUser", "userid",
						userrole);
		centralStoreRequisitionMstdb.setCreatedBy(createBy.getName());

		Map<String, Object> model = new HashMap<String, Object>();
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
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

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
		return new ModelAndView("workshop/transformer/requisition/wsCsRequisitionShow", model);

	}

	@SuppressWarnings("unchecked")
	public ModelAndView getStoreRequisitionShowSS(
			CentralStoreRequisitionMst subStoreRequisitionMst) throws Exception {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SubStoreRequisitionMst subStoreRequisitionMstdb = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst", "id",
						subStoreRequisitionMst.getId() + "");

		String operationId = subStoreRequisitionMstdb.getRequisitionNo();

		List<SubStoreRequisitionDtl> subStoreRequisitionDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", operationId);

		String buttonValue = null;

		// operation Id which selected by login user
		String currentStatus = "";

		List<CnSsRequisitionApprovalHierarchyHistory> sSsRequisitionApprovalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory",
						CN_PD_SS_REQUISITION, operationId, DONE);

		if (!sSsRequisitionApprovalHierarchyHistoryList.isEmpty()) {
			currentStatus = sSsRequisitionApprovalHierarchyHistoryList.get(
					sSsRequisitionApprovalHierarchyHistoryList.size() - 1)
					.getStateName();
		} else {
			currentStatus = "CREATED";
		}

		List<CnSsRequisitionApprovalHierarchyHistory> sSsRequisitionApprovalHierarchyHistoryOpenList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory",
						CN_PD_SS_REQUISITION, operationId, OPEN);

		int currentStateCode = sSsRequisitionApprovalHierarchyHistoryOpenList
				.get(sSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
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
						CN_PD_SS_REQUISITION, roleNameList);
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
		if (!sSsRequisitionApprovalHierarchyHistoryOpenList.isEmpty()
				&& sSsRequisitionApprovalHierarchyHistoryOpenList != null) {
			// get current state code
			int stateCode = sSsRequisitionApprovalHierarchyHistoryOpenList.get(
					sSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getStateCode();
			// deciede for return or not
			returnStateCode = sSsRequisitionApprovalHierarchyHistoryOpenList
					.get(sSsRequisitionApprovalHierarchyHistoryOpenList.size() - 1)
					.getReturn_state();
			// get next approval hierarchy
			ApprovalHierarchy approveHeirarchy = null;
			{
				approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CN_PD_SS_REQUISITION, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

		}
		// Get all Location under CS || Added by Ashid
		List<StoreLocations> storeLocationList = this
				.getStoreLocationList("SS");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnStateCode", returnStateCode);
		model.put("centralStoreRequisitionMst", subStoreRequisitionMstdb);
		model.put("currentStatus", currentStatus);
		model.put("buttonValue", buttonValue);
		model.put("nextManRcvProcs", nextManReqProcs);
		model.put("backManRcvProcs", backManRcvProcs);
		model.put("locationList", storeLocationList);

		model.put("reqTo", ContentType.CENTRAL_STORE.toString());
		model.put("itemRcvApproveHistoryList",
				sSsRequisitionApprovalHierarchyHistoryList);
		model.put("centralStoreRequisitionDtlList", subStoreRequisitionDtlList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

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
		return new ModelAndView("contractor/pdSsRequisitionShow", model);

	}

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

	@RequestMapping(value = "/sendToCnWsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String sendToCnPdRequisition(
			Model model,
			@ModelAttribute("cnWsRequisitionMst") CnWsRequisitionMstDtl csRequisitionMstDtl) {

		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
		String justification = csRequisitionMstDtl.getJustification();
		String nextStateCode = csRequisitionMstDtl.getStateCode();

		/*
		 * List<String> cnReqDtlIdList = csRequisitionMstDtl.getCnReqDtlId();
		 * List<Double> requiredQtyList = csRequisitionMstDtl
		 * .getQuantityRequired();
		 */

		// get Current Dept, User and Role Information
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// update requisition qty
		CnWsCsRequisitionMst cnWsReqMstDB = (CnWsCsRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst",
						"requisitionNo", requisitionNo);
		// Update all Requisition Dtl
		/*
		 * List<MyPair> itemCodeAndQtyList = new ArrayList<MyPair>(); for (int i
		 * = 0; i < cnReqDtlIdList.size(); i++) { String id =
		 * cnReqDtlIdList.get(i); Double requiredQty = requiredQtyList.get(i);
		 * CnPdRequisitionDtl dtlDB = (CnPdRequisitionDtl) commonService
		 * .getAnObjectByAnyUniqueColumn("CnPdRequisitionDtl", "id", id);
		 * dtlDB.setQuantityRequired(requiredQty);
		 * dtlDB.setModifiedBy(userName); dtlDB.setModifiedDate(new Date());
		 * commonService.saveOrUpdateModelObjectToDB(dtlDB); // for lock Table
		 * MyPair pair = new MyPair(dtlDB.getItemCode(), requiredQty);
		 * itemCodeAndQtyList.add(pair); }
		 * 
		 * this.requisitionLockUpdate(itemCodeAndQtyList, requisitionNo,
		 * userName, new Date(), cnPdReqMstDB.getRequisitionTo());
		 */

		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.sendToForCS(requisitionNo, userName, justification,
					nextStateCode, deptId, department, authUser);
		} 
		/*else {
			this.sendToForSS(requisitionNo, userName, justification,
					nextStateCode, deptId, department, authUser);
		}*/

		// Show Pending Requisition List
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CS")) {
			return "redirect:/ls/requisitionList.do";
		} else {
			return "redirect:/cnws/cnPdRequisitionList.do";
		}
		// return this.getCnPdRequisitionList();
	}

	@SuppressWarnings("unchecked")
	private void sendToForCS(String requisitionNo, String userName,
			String justification, String nextStateCode, String deptId,
			Departments department, AuthUser authUser) {
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, currentStateCode + "");
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
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnWsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, nextStateCode);
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(requisitionNo);
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
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void sendToForSS(String requisitionNo, String userName,
			String justification, String nextStateCode, String deptId,
			Departments department, AuthUser authUser) {
		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnSsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnSsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_REQUISITION, currentStateCode + "");
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
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryNextState = new CnSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_REQUISITION, nextStateCode);
		approvalHierarchyHistoryNextState.setActive(true);
		approvalHierarchyHistoryNextState.setCreatedBy(userName);
		approvalHierarchyHistoryNextState.setCreatedDate(new Date());
		approvalHierarchyHistoryNextState.setStatus(OPEN);

		approvalHierarchyHistoryNextState
				.setStateName(approvalHierarchyNextSate.getStateName());
		approvalHierarchyHistoryNextState.setStateCode(Integer
				.parseInt(nextStateCode));
		approvalHierarchyHistoryNextState.setId(null);
		approvalHierarchyHistoryNextState.setOperationId(requisitionNo);
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
	}

	@RequestMapping(value = "/backToCnWsRequisition.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String backToCnWsRequisition(
			Model model,
			@ModelAttribute("cnWsRequisitionMst") CnWsRequisitionMstDtl csRequisitionMstDtl) {
		String requisitionNo = csRequisitionMstDtl.getRequisitionNo();
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
		CnWsCsRequisitionMst cnWsReqMstDB = (CnWsCsRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CnWsCsRequisitionMst",
						"requisitionNo", requisitionNo);

		if (cnWsReqMstDB.getRequisitionTo().equalsIgnoreCase(
				ContentType.CENTRAL_STORE.toString())) {
			this.backToCS(requisitionNo, authUser, roleName, userName,
					justification, backStateCode, deptId, department);
		} 
		/*else {
			this.backToSS(requisitionNo, authUser, roleName, userName,
					justification, backStateCode, deptId, department);
		}*/

		// Show Pending Requisition List
		String rolePrefix = roleName.substring(0, 7);
		if (rolePrefix.equals("ROLE_CS")) {
			return "redirect:/ls/requisitionList.do";
		} else {
			return "redirect:/cnws/cnWsRequisitionList.do";
		}// return this.getCnPdRequisitionList();
	}

	@SuppressWarnings("unchecked")
	private void backToCS(String requisitionNo, AuthUser authUser,
			String roleName, String userName, String justification,
			String backStateCode, String deptId, Departments department) {
		List<CnWsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnWsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnWsCsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnWsCsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, currentStateCode + "");
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
		approvalHierarchyHistory.setStateName(BACK + "ED");
		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnWsCsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnWsCsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_WS_CS_REQUISITION, backStateCode);
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(approvalHierarchy
				.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(requisitionNo + "");
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
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void backToSS(String requisitionNo, AuthUser authUser,
			String roleName, String userName, String justification,
			String backStateCode, String deptId, Departments department) {
		List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"CnSsRequisitionApprovalHierarchyHistory",
						"operationId", requisitionNo);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());
		// get current State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnSsRequisitionApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"CnSsRequisitionApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = approvalHierarchyHistory.getStateCode();

		// current user's row status will be done after updated
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_REQUISITION, currentStateCode + "");
		approvalHierarchyHistory.setStatus(DONE);
		approvalHierarchyHistory.setCreatedBy(userName);
		approvalHierarchyHistory.setModifiedBy(userName);
		approvalHierarchyHistory.setModifiedDate(new Date());
		approvalHierarchyHistory.setJustification(justification);
		approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
				.getApprovalHeader());
		approvalHierarchyHistory.setStateName(BACK + "ED");
		approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
		approvalHierarchyHistory.setcEmpFullName(authUser.getName());
		approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

		commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

		// get Next State Code and all info from approval hierarchy history
		CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistoryBackState = new CnSsRequisitionApprovalHierarchyHistory();
		ApprovalHierarchy approvalHierarchyBackSate = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CN_PD_SS_REQUISITION, backStateCode);
		approvalHierarchyHistoryBackState.setActive(true);
		approvalHierarchyHistoryBackState.setCreatedBy(userName);
		approvalHierarchyHistoryBackState.setCreatedDate(new Date());
		approvalHierarchyHistoryBackState.setStatus(OPEN);
		approvalHierarchyHistoryBackState.setStateName(approvalHierarchy
				.getStateName());
		approvalHierarchyHistoryBackState.setStateCode(Integer
				.parseInt(backStateCode));
		approvalHierarchyHistoryBackState.setId(null);
		approvalHierarchyHistoryBackState.setOperationId(requisitionNo + "");
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
	}

}
