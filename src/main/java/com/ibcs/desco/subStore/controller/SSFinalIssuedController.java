package com.ibcs.desco.subStore.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionDtl;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;

@Controller
public class SSFinalIssuedController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/issued/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssItemIssuedList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<SubStoreRequisitionMst> ssIssuedMstList = (List<SubStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionMst",
						"approved", "1");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ssIssuedMstList", ssIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalItemIssuedList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/issued/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssItemIssuedDetailsInfo(
			SubStoreRequisitionMst ssReqMst) {

		String id = ssReqMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SubStoreRequisitionMst ssIssuedMstDb = (SubStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("SubStoreRequisitionMst",
						"id", id);

		List<SubStoreRequisitionDtl> ssIssuedDtlList = (List<SubStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("SubStoreRequisitionDtl",
						"requisitionNo", ssIssuedMstDb.getRequisitionNo());

		String operationId = ssIssuedMstDb.getRequisitionNo();

		// get all hierarchy history against current operation id and status
		// done

		SSStoreTicketMst ssStoreTicketMstDb = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (ssStoreTicketMstDb.getStoreTicketType().equals(LS_SS_REQUISITION)) {
			List<LsSsRequisitionApprovalHierarchyHistory> approveHistoryHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"LsSsRequisitionApprovalHierarchyHistory",
							"operationName", LS_SS_REQUISITION, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);

		} else if (ssStoreTicketMstDb.getStoreTicketType().equals(
				CN_SS_REQUISITION)) {

			List<CnSsRequisitionApprovalHierarchyHistory> approveHistoryHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"CnSsRequisitionApprovalHierarchyHistory",
							"operationName", CN_SS_REQUISITION, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);
		}

		model.put("ssIssuedDtlList", ssIssuedDtlList);
		model.put("ssIssuedMst", ssIssuedMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalItemIssuedShow", model);
	}

	@RequestMapping(value = "/ss/issued/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(SubStoreRequisitionMst ssReqMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = ssReqMst.getFromDate();

		Date secondDate = ssReqMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String senderStore = ssReqMst.getSenderStore();

		String requistionNo = ssReqMst.getRequisitionNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<SubStoreRequisitionMst> ssIssuedMstList = this
				.simulateSearchResult(requistionNo, senderStore, fromDate, toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ssIssuedMstList", ssIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalItemIssuedList", model);

	}

	@SuppressWarnings("unchecked")
	private List<SubStoreRequisitionMst> simulateSearchResult(
			String requisitionNo, String senderStore, String fromDate, String toDate) {

		List<SubStoreRequisitionMst> searchIssuedList = new ArrayList<SubStoreRequisitionMst>();
		List<SubStoreRequisitionMst> itemIssuedMstList = (List<SubStoreRequisitionMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn(
						"SubStoreRequisitionMst", "requisitionDate",
						fromDate, toDate, "approved", "1", "senderStore", senderStore);

		// iterate a list and filter by tagName
		if (itemIssuedMstList.size() > 0) {
			for (SubStoreRequisitionMst ssRequisitionMst : itemIssuedMstList) {
				if (ssRequisitionMst.getRequisitionNo().toLowerCase()
						.contains(requisitionNo.toLowerCase())) {
					searchIssuedList.add(ssRequisitionMst);
				}
			}
		}
		if (requisitionNo.length() > 0) {
			return searchIssuedList;
		} else {
			return itemIssuedMstList;
		}

	}
}
