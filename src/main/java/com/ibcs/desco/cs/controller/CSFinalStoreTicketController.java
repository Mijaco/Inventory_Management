package com.ibcs.desco.cs.controller;

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
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;

@Controller
public class CSFinalStoreTicketController extends Constrants {

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

	@RequestMapping(value = "/cs/storeTicket/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csStoreTicketList() {
		String userName = commonService.getAuthUserName();

		String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		ApprovalHierarchy appHier = approvalHierarchyService
				.getApprovalHierarchyByRoleAndOperation(roleName,
						CS_STORE_TICKET);

		List<CSStoreTicketMst> storeTicketMstList = new ArrayList<CSStoreTicketMst>();
		if (appHier != null) {
			storeTicketMstList = csStoreTicketMstService
					.listCSStoreTicketMstifApproved(true);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalStoreTicketList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/storeTicket/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csStoreTicketDetailsInfo(
			CSStoreTicketMst csStoreTicketMst) {

		String id = csStoreTicketMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		CSStoreTicketMst csStoreTicketMstDb = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst", "id", id);

		List<CSStoreTicketDtl> csStoreTicketDtlList = (List<CSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("CSStoreTicketDtl", "ticketNo",
						csStoreTicketMstDb.getTicketNo());

		String operationId = csStoreTicketMstDb.getOperationId();

		// get all hierarchy history against current operation id and status
		// done
		List<StoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = (List<StoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"StoreTicketApprovalHierarchyHistory", "operationName",
						CS_STORE_TICKET, "operationId", operationId, "status",
						DONE);
		
		/* Following five lines are added by Ihteshamul Alam */
		String userrole = csStoreTicketMstDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		csStoreTicketMstDb.setCreatedBy(createBy.getName());
		csStoreTicketMstDb.setIssuedBy( createBy.getName() );
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketApproveHistoryList",
				storeTicketApproveHistoryList);
		model.put("csStoreTicketDtlList", csStoreTicketDtlList);
		model.put("csStoreTicketMst", csStoreTicketMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalStoreTicketShow", model);
	}

	@RequestMapping(value = "/cs/storeTicket/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(CSStoreTicketMst csStoreTicketMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = csStoreTicketMst.getFromDate();

		Date secondDate = csStoreTicketMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String storeTicketType = csStoreTicketMst.getStoreTicketType();

		String ticketNo = csStoreTicketMst.getTicketNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CSStoreTicketMst> storeTicketMstList = this.simulateSearchResult(
				ticketNo, storeTicketType, fromDate, toDate);

		/*
		 * List<CSItemTransactionMst> csItemTransactionMstList = this
		 * .simulateSearchResult(csItemTransactionMst.getItemCode(),
		 * csItemTransactionMst.getKhathId(),
		 * csItemTransactionMst.getLedgerName(), department);
		 */

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("storeTicketMstList", storeTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalStoreTicketList", model);

	}

	@SuppressWarnings("unchecked")
	private List<CSStoreTicketMst> simulateSearchResult(String ticketNo,
			String storeTicketType, String fromDate, String toDate) {

		List<CSStoreTicketMst> searchSTList = new ArrayList<CSStoreTicketMst>();
		List<CSStoreTicketMst> storeTicketMstList = (List<CSStoreTicketMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn("CSStoreTicketMst",
						"ticketDate", fromDate, toDate, "approved", "1",
						"storeTicketType", storeTicketType);

		// iterate a list and filter by tagName
		for (CSStoreTicketMst csStoreTicketMst : storeTicketMstList) {
			if (csStoreTicketMst.getTicketNo().toLowerCase()
					.contains(ticketNo.toLowerCase())) {
				searchSTList.add(csStoreTicketMst);
			}
		}
		if (ticketNo.length() > 0) {
			return searchSTList;
		} else {
			return storeTicketMstList;
		}

	}
}
