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
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.LSStoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.localStore.model.LSStoreTicketDtl;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;

@Controller
public class LSFinalStoreTicketController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/storeTicket/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsStoreTicketList() {
		String userName = commonService.getAuthUserName();

		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		ApprovalHierarchy appHier = approvalHierarchyService
				.getApprovalHierarchyByRoleAndOperation(roleName,
						LS_STORE_TICKET);

		List<LSStoreTicketMst> storeTicketMstList = new ArrayList<LSStoreTicketMst>();
		if (appHier != null) {
			storeTicketMstList = (List<LSStoreTicketMst>) (Object) commonService
					.getObjectListByTwoColumn("LSStoreTicketMst", "approved",
							"1", "sndCode", department.getDescoCode());

		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalStoreTicketList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/storeTicket/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsStoreTicketDetailsInfo(
			LSStoreTicketMst lsStoreTicketMst) {

		String id = lsStoreTicketMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		LSStoreTicketMst lsStoreTicketMstDb = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst", "id", id);

		List<LSStoreTicketDtl> lsStoreTicketDtlList = (List<LSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("LSStoreTicketDtl", "ticketNo",
						lsStoreTicketMstDb.getTicketNo());

		String operationId = lsStoreTicketMstDb.getOperationId();

		// get all hierarchy history against current operation id and status
		// done
		List<LSStoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"LSStoreTicketApprovalHierarchyHistory",
						"operationName", LS_STORE_TICKET, "operationId",
						operationId, "status", DONE);
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = lsStoreTicketMstDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		lsStoreTicketMstDb.setCreatedBy(createBy.getName());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketApproveHistoryList",
				storeTicketApproveHistoryList);
		model.put("lsStoreTicketDtlList", lsStoreTicketDtlList);
		model.put("lsStoreTicketMst", lsStoreTicketMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalStoreTicketShow", model);
	}

	@RequestMapping(value = "/ls/storeTicket/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(LSStoreTicketMst lsStoreTicketMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = lsStoreTicketMst.getFromDate();

		Date secondDate = lsStoreTicketMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String storeTicketType = lsStoreTicketMst.getStoreTicketType();

		String ticketNo = lsStoreTicketMst.getTicketNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LSStoreTicketMst> storeTicketMstList = this.simulateSearchResult(
				ticketNo, storeTicketType, fromDate, toDate, department);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalStoreTicketList", model);

	}

	@SuppressWarnings("unchecked")
	private List<LSStoreTicketMst> simulateSearchResult(String ticketNo,
			String storeTicketType, String fromDate, String toDate,
			Departments department) {

		List<LSStoreTicketMst> searchSTList = new ArrayList<LSStoreTicketMst>();
		List<LSStoreTicketMst> storeTicketMstList = (List<LSStoreTicketMst>) (Object) commonService
				.getObjectListByDateRangeAndThreeColumn("LSStoreTicketMst",
						"ticketDate", fromDate, toDate, "approved", "1",
						"storeTicketType", storeTicketType, "sndCode",
						department.getDescoCode());

		// iterate a list and filter by tagName
		for (LSStoreTicketMst lsStoreTicketMst : storeTicketMstList) {
			if (lsStoreTicketMst.getTicketNo().toLowerCase()
					.contains(ticketNo.toLowerCase())) {
				searchSTList.add(lsStoreTicketMst);
			}
		}
		if (ticketNo.length() > 0) {
			return searchSTList;
		} else {
			return storeTicketMstList;
		}

	}
}
