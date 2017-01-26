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
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

@Controller
public class SSFinalStoreTicketController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/storeTicket/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssStoreTicketList() {
		String userName = commonService.getAuthUserName();

		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		ApprovalHierarchy appHier = approvalHierarchyService
				.getApprovalHierarchyByRoleAndOperation(roleName,
						SS_STORE_TICKET);

		List<SSStoreTicketMst> storeTicketMstList = new ArrayList<SSStoreTicketMst>();
		if (appHier != null) {
			storeTicketMstList = (List<SSStoreTicketMst>) (Object) commonService
					.getObjectListByAnyColumn("SSStoreTicketMst", "approved",
							"1");

		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalStoreTicketList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/storeTicket/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssStoreTicketDetailsInfo(
			SSStoreTicketMst ssStoreTicketMst) {

		String id = ssStoreTicketMst.getId().toString();
		String userName = commonService.getAuthUserName();

		// String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SSStoreTicketMst ssStoreTicketMstDb = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst", "id", id);

		List<SSStoreTicketDtl> ssStoreTicketDtlList = (List<SSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSStoreTicketDtl", "ticketNo",
						ssStoreTicketMstDb.getTicketNo());

		String operationId = ssStoreTicketMstDb.getOperationId();

		// get all hierarchy history against current operation id and status
		// done
		List<StoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = (List<StoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"StoreTicketApprovalHierarchyHistory", "operationName",
						SS_STORE_TICKET, "operationId", operationId, "status",
						DONE);
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = ssStoreTicketMstDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		ssStoreTicketMstDb.setCreatedBy(createBy.getName());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketApproveHistoryList",
				storeTicketApproveHistoryList);
		model.put("ssStoreTicketDtlList", ssStoreTicketDtlList);
		model.put("ssStoreTicketMst", ssStoreTicketMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalStoreTicketShow", model);
	}

	@RequestMapping(value = "/ss/storeTicket/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(SSStoreTicketMst ssStoreTicketMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = ssStoreTicketMst.getFromDate();

		Date secondDate = ssStoreTicketMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String storeTicketType = ssStoreTicketMst.getStoreTicketType();

		String ticketNo = ssStoreTicketMst.getTicketNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<SSStoreTicketMst> storeTicketMstList = this.simulateSearchResult(
				ticketNo, storeTicketType, fromDate, toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalStoreTicketList", model);

	}

	@SuppressWarnings("unchecked")
	private List<SSStoreTicketMst> simulateSearchResult(String ticketNo,
			String storeTicketType, String fromDate, String toDate) {

		List<SSStoreTicketMst> searchSTList = new ArrayList<SSStoreTicketMst>();
		List<SSStoreTicketMst> storeTicketMstList = (List<SSStoreTicketMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn("SSStoreTicketMst",
						"ticketDate", fromDate, toDate, "approved", "1",
						"storeTicketType", storeTicketType);

		// iterate a list and filter by tagName
		for (SSStoreTicketMst ssStoreTicketMst : storeTicketMstList) {
			if (ssStoreTicketMst.getTicketNo().toLowerCase()
					.contains(ticketNo.toLowerCase())) {
				searchSTList.add(ssStoreTicketMst);
			}
		}
		if (ticketNo.length() > 0) {
			return searchSTList;
		} else {
			return storeTicketMstList;
		}

	}
}
