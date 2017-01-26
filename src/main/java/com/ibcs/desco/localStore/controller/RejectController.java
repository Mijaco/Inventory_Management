package com.ibcs.desco.localStore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.CnCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.LsSsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.contractor.model.CnPdRequisitionDtl;
import com.ibcs.desco.contractor.model.CnPdRequisitionMst;
import com.ibcs.desco.contractor.model.CnPdRequisitionMstDtl;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CentralStoreRequisitionDtlService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.localStore.service.LSItemTransactionMstService;
import com.ibcs.desco.subStore.bean.SubStoreRequisitionMstDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.subStore.service.SubStoreRequisitionDtlService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;

/*
 * @author Ahasanul Ashid, @contributor: Ihteshamul Alam
 * @designation Programmer
 * @company IBCS Primax Software Ltd.
 */

@Controller
@PropertySource("classpath:common.properties")
public class RejectController extends Constrants {
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
	CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/cs/itemRequisitionReject.do", method = RequestMethod.POST)
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
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	//Forked & modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/ss/itemRequisitionReject.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssItemRequisitionReject(SubStoreRequisitionMstDtl bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userId);

			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"operationId", bean.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			LsSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = lsSsRequisitionApprovalHierarchyHistoryService
					.getLsSsRequisitionApprovalHierarchyHistory(ids[0]);

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
					"redirect:/ls/ss/rejectedRequisitionList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("subStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/cs/rejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLsCsRejectedRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CentralStoreRequisitionMst> csRequisitionList = (List<CentralStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CentralStoreRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("csRequisitionMstList", csRequisitionList);

			return new ModelAndView("centralStore/lsCsRejectedRequisitionList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	//Forked and Modified by :: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/ss/rejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLsSsRejectedRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<SubStoreRequisitionMst> ssRequisitionList = (List<SubStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"SubStoreRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("ssRequisitionMstList", ssRequisitionList);

			return new ModelAndView("subStore/lsSsRejectedRequisitionList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("subStore/error", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/cs/rejected/showRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionLsCsRejected(
			CentralStoreRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();

		try {
			String id = bean.getId().toString();
			CentralStoreRequisitionMst mst = (CentralStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
							"id", id);

			AuthUser authUser = userService.getAuthUserByUserId(mst
					.getCreatedBy());
			mst.setCreatedBy(authUser.getName());

			List<CentralStoreRequisitionDtl> dtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
							"centralStoreRequisitionMst.id", id);

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationId", mst.getRequisitionNo(), "stateCode",
							"ASC");

			model.put("csRequisitionMst", mst);
			model.put("csRequisitionDtlList", dtlList);
			model.put("approvalHierarchyHistoryList",
					approvalHierarchyHistoryList);

			if (roleName.contains("ROLE_CS")) {
				return new ModelAndView("localStore/csRequisitionShowRejected",
						model);
			} else if (roleName.contains("ROLE_LS")) {
				return new ModelAndView(
						"localStore/csRequisitionShowRejectedLS", model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (roleName.contains("ROLE_CS")) {
				return new ModelAndView("centralStore/error", model);
			} else {
				return new ModelAndView("localStore/errorLS", model);
			}

		}
	}
	
	//Forked & Modified by :: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/ss/rejected/showRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionLsSsRejected(
			SubStoreRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();

		try {
			String id = bean.getId().toString();
			SubStoreRequisitionMst mst = (SubStoreRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
							"id", id);

			if( mst.getCreatedBy().endsWith(")") || mst.getCreatedBy().contains("(") == true ) {
				//
			} else {
				AuthUser authUser = userService.getAuthUserByUserId(mst
						.getCreatedBy());
				mst.setCreatedBy(authUser.getName());
			}

			List<SubStoreRequisitionDtl> dtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("SubStoreRequisitionDtl",
							"subStoreRequisitionMst.id", id);

			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"operationId", mst.getRequisitionNo(), "stateCode",
							"ASC");

			model.put("csRequisitionMst", mst);
			model.put("csRequisitionDtlList", dtlList);
			model.put("approvalHierarchyHistoryList",
					approvalHierarchyHistoryList);

			if (roleName.contains("ROLE_SS")) {
				return new ModelAndView("localStore/ssRequisitionShowRejected",
						model);
			} else if (roleName.contains("ROLE_LS")) {
				return new ModelAndView(
						"localStore/ssRequisitionShowRejectedLS", model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (roleName.contains("ROLE_SS")) {
				return new ModelAndView("subStore/error", model);
			} else {
				return new ModelAndView("localStore/errorLS", model);
			}

		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/csRejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLsCsRejectedRequisitionListLS() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			String deptId = authUser.getDeptId();

			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			List<LsCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CentralStoreRequisitionMst> csRequisitionList = (List<CentralStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueListAndOneColumn(
							"CentralStoreRequisitionMst", "requisitionNo",
							requisitionNoList, "sndCode",
							department.getDescoCode());

			model.put("csRequisitionMstList", csRequisitionList);

			return new ModelAndView("localStore/lsCsRejectedRequisitionListLS",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}
	
	//Forked & Modified by :: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/ssRejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLsSsRejectedRequisitionListLS() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			String deptId = authUser.getDeptId();

			Departments department = departmentsService
					.getDepartmentByDeptId(deptId);

			List<LsSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<SubStoreRequisitionMst> ssRequisitionList = (List<SubStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueListAndOneColumn(
							"SubStoreRequisitionMst", "requisitionNo",
							requisitionNoList, "sndCode",
							department.getDescoCode());

			model.put("ssRequisitionMstList", ssRequisitionList);

			return new ModelAndView("localStore/lsSsRejectedRequisitionListLS",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("localStore/errorLS", model);
		}
	}
	
	//Contractor_PD_CS Requisition
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/cs/itemRequisitionReject.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnpdItemRequisitionReject(CnPdRequisitionMstDtl bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userId);

			List<CnCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnCsRequisitionApprovalHierarchyHistory",
							"operationId", bean.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			CnCsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnCsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnCsRequisitionApprovalHierarchyHistory", "id", ids[0]
									+ "");

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
					"redirect:/cnpd/cs/rejectedRequisitionList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/cs/rejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnPdCsRejectedRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<CnCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnCsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CnPdRequisitionMst> csRequisitionList = (List<CnPdRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CnPdRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("csRequisitionMstList", csRequisitionList);

			return new ModelAndView("centralStore/cnPdCsRejectedRequisitionList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error", model);
		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/cs/rejected/showRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionCnPdCSRejected(
			CnPdRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();

		try {
			String id = bean.getId().toString();
			CnPdRequisitionMst mst = (CnPdRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CnPdRequisitionMst",
							"id", id);

			AuthUser authUser = userService.getAuthUserByUserId(mst
					.getCreatedBy());
			mst.setCreatedBy(authUser.getName());

			List<CnPdRequisitionDtl> dtlList = (List<CnPdRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CnPdRequisitionDtl",
							"cnPdRequisitionMst.id", id);

			List<CnCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"CnCsRequisitionApprovalHierarchyHistory",
							"operationId", mst.getRequisitionNo(), "stateCode",
							"ASC");

			model.put("csRequisitionMst", mst);
			model.put("csRequisitionDtlList", dtlList);
			model.put("approvalHierarchyHistoryList",
					approvalHierarchyHistoryList);

			if (roleName.contains("ROLE_CS")) {
				return new ModelAndView("localStore/cnPdCsRequisitionShowRejected",
						model);
			} else if (roleName.contains("ROLE_CN_PD_USER")) {
				return new ModelAndView(
						"localStore/cnPdCsRequisitionShowRejectedLS", model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (roleName.contains("ROLE_CS")) {
				return new ModelAndView("centralStore/error", model);
			} else {
				return new ModelAndView("pndContractor/errorCnProject", model);
			}

		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/csRejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnPdCsRejectedRequisitionListLS() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<CnCsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnCsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CnPdRequisitionMst> csRequisitionList = (List<CnPdRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CnPdRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("csRequisitionMstList", csRequisitionList);

			return new ModelAndView("localStore/cnPdCsRejectedRequisitionListLS",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("pndContractor/errorCnProject", model);
		}
	}
	
	//Contractor_PD_SS Requisition
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/ss/itemRequisitionReject.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView cnPdSsItemRequisitionReject(CnPdRequisitionMstDtl bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userId);

			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsRequisitionApprovalHierarchyHistory",
							"operationId", bean.getRequisitionNo());

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			CnSsRequisitionApprovalHierarchyHistory approvalHierarchyHistory = (CnSsRequisitionApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"CnSsRequisitionApprovalHierarchyHistory", "id", ids[0]
									+ "");

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
					"redirect:/cnpd/ss/rejectedRequisitionList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("subStore/errorSS", model);
		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/ss/rejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnPdSsRejectedRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CnPdRequisitionMst> ssRequisitionList = (List<CnPdRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CnPdRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("ssRequisitionMstList", ssRequisitionList);

			return new ModelAndView("subStore/cnPdSsRejectedRequisitionList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("subStore/errorSS", model);
		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/ss/rejected/showRequisition.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showRequisitionCnPdSsRejected(
			CnPdRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();

		try {
			String id = bean.getId().toString();
			CnPdRequisitionMst mst = (CnPdRequisitionMst) commonService
					.getAnObjectByAnyUniqueColumn("CnPdRequisitionMst",
							"id", id);

			AuthUser authUser = userService.getAuthUserByUserId(mst
					.getCreatedBy());
			mst.setCreatedBy(authUser.getName());

			List<CnPdRequisitionDtl> dtlList = (List<CnPdRequisitionDtl>) (Object) commonService
					.getObjectListByAnyColumn("CnPdRequisitionDtl",
							"cnPdRequisitionMst.id", id);

			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumnOrderByAnyColumn(
							"CnSsRequisitionApprovalHierarchyHistory",
							"operationId", mst.getRequisitionNo(), "stateCode",
							"ASC");

			model.put("csRequisitionMst", mst);
			model.put("csRequisitionDtlList", dtlList);
			model.put("approvalHierarchyHistoryList",
					approvalHierarchyHistoryList);

			if (roleName.contains("ROLE_SS")) {
				return new ModelAndView("pndContractor/cnPdSsRequisitionShowRejected",
						model);
			} else if (roleName.contains("ROLE_CN_PD_USER")) {
				return new ModelAndView(
						"pndContractor/cnPdSsRequisitionShowRejectedLS", model);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (roleName.contains("ROLE_CS")) {
				return new ModelAndView("subStore/errorSS", model);
			} else {
				return new ModelAndView("pndContractor/errorCnProject", model);
			}

		}
	}
	
	//Forked & Modified by: Ihteshamul Alam
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cnpd/ssRejectedRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCnPdSsRejectedRequisitionListLS() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			List<CnSsRequisitionApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"CnSsRequisitionApprovalHierarchyHistory",
							"status", REJECT);

			List<String> requisitionNoList = new ArrayList<String>();

			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				requisitionNoList.add(approvalHierarchyHistoryList.get(i)
						.getOperationId());
			}

			List<CnPdRequisitionMst> ssRequisitionList = (List<CnPdRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CnPdRequisitionMst", "requisitionNo",
							requisitionNoList);

			model.put("ssRequisitionMstList", ssRequisitionList);

			return new ModelAndView("pndContractor/cnPdSsRejectedRequisitionListLS",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("pndContractor/errorCnProject", model);
		}
	}
}
