package com.ibcs.desco.localStore.controller;

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
import com.ibcs.desco.common.model.C2LSRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionDtl;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionMst;

@Controller
public class LSFinalIssuedController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/issued/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsItemIssuedList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LocalStoreRequisitionMst> lsIssuedMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByTwoColumn("LocalStoreRequisitionMst",
						"requestedDeptId", department.getDeptId(), "approved",
						"1");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lsIssuedMstList", lsIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalItemIssuedList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/issued/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsItemIssuedDetailsInfo(
			LocalStoreRequisitionMst lsReqMst) {

		String id = lsReqMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		LocalStoreRequisitionMst lsIssuedMstDb = (LocalStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("LocalStoreRequisitionMst", "id",
						id);

		List<LocalStoreRequisitionDtl> lsIssuedDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
						"requisitionNo", lsIssuedMstDb.getRequisitionNo());

		String operationId = lsIssuedMstDb.getRequisitionNo();

		// get all hierarchy history against current operation id and status
		// done

		LSStoreTicketMst lsStoreTicketMstDb = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (lsStoreTicketMstDb.getStoreTicketType().equals(
				LS_ISSUED_REQUISITION)) {
			List<C2LSRequisitionApprovalHierarchyHistory> approveHistoryHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"C2LSRequisitionApprovalHierarchyHistory",
							"operationName", LS_ISSUED_REQUISITION,
							"operationId", operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);

		}
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = lsIssuedMstDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		lsIssuedMstDb.setCreatedBy(createBy.getName());
		

		model.put("lsIssuedDtlList", lsIssuedDtlList);
		model.put("lsIssuedMst", lsIssuedMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalItemIssuedShow", model);
	}

	@RequestMapping(value = "/ls/issued/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(LocalStoreRequisitionMst lsReqMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = lsReqMst.getFromDate();

		Date secondDate = lsReqMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String senderStore = lsReqMst.getSenderStore();

		String requistionNo = lsReqMst.getRequisitionNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LocalStoreRequisitionMst> lsIssuedMstList = this
				.simulateSearchResult(requistionNo, senderStore, fromDate,
						toDate, department);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lsIssuedMstList", lsIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalItemIssuedList", model);

	}

	@SuppressWarnings("unchecked")
	private List<LocalStoreRequisitionMst> simulateSearchResult(
			String requisitionNo, String senderStore, String fromDate,
			String toDate, Departments department) {

		List<LocalStoreRequisitionMst> searchIssuedList = new ArrayList<LocalStoreRequisitionMst>();
		List<LocalStoreRequisitionMst> itemIssuedMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn(
						"LocalStoreRequisitionMst", "requisitionDate",
						fromDate, toDate, "approved", "1", "requestedDeptId", department.getDeptId());

		// iterate a list and filter by tagName
		if (itemIssuedMstList.size() > 0) {
			for (LocalStoreRequisitionMst lsRequisitionMst : itemIssuedMstList) {
				if (lsRequisitionMst.getRequisitionNo().toLowerCase()
						.contains(requisitionNo.toLowerCase())) {
					searchIssuedList.add(lsRequisitionMst);
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
