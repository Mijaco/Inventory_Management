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
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.ReturnSlipDtl;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;

@Controller
public class CSFinalReturnSlipController extends Constrants {

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
	@RequestMapping(value = "/cs/received/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csReturnSlipMstList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ReturnSlipMst> csReturnSlipMstList = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipMst", "approved", "1");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("csReturnSlipMstList", csReturnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalReturnSlipList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/received/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csReturnSlipDetailsInfo(ReturnSlipMst returnSlipMst) {

		String id = returnSlipMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		ReturnSlipMst returnSlipMstDb = (ReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("ReturnSlipMst", "id", id);

		List<ReturnSlipDtl> returnSlipDtlList = (List<ReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("ReturnSlipDtl", "returnSlipNo",
						returnSlipMstDb.getReturnSlipNo());

		String operationId = returnSlipMstDb.getReturnSlipNo();

		// get all hierarchy history against current operation id and status
		// done

		CSStoreTicketMst csStoreTicketMstDb = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (csStoreTicketMstDb.getStoreTicketType().equals(LS_CS_REQUISITION)) {
			List<LsCsReturnSlipApprovalHierarchyHistory> approveHistoryHistoryList = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"LsCsReturnSlipApprovalHierarchyHistory",
							"operationName", LS_CS_RETURN_SLIP, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);

		} else if (csStoreTicketMstDb.getStoreTicketType().equals(
				LS_CS_REQUISITION)) {

			List<SsCsReturnSlipApprovalHierarchyHistory> approveHistoryHistoryList = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"SsCsReturnSlipApprovalHierarchyHistory",
							"operationName", SS_CS_RETURN_SLIP, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);
		}
		
		/* Following four lines are added by Ihteshamul Alam */
		String userrole = returnSlipMstDb.getCreatedBy();
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", userrole);
		returnSlipMstDb.setCreatedBy(createBy.getName());

		model.put("returnSlipDtlList", returnSlipDtlList);
		model.put("returnSlipMst", returnSlipMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalReturnSlipShow", model);
	}

	@RequestMapping(value = "/cs/received/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(ReturnSlipMst returnSlipMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = returnSlipMst.getFromDate();

		Date secondDate = returnSlipMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String senderStore = returnSlipMst.getSenderStore();

		String returnSlipNo = returnSlipMst.getReturnSlipNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ReturnSlipMst> returnSlipMstList = this
				.simulateSearchResult(returnSlipNo, senderStore, fromDate,
						toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("csReturnSlipMstList", returnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalReturnSlipList", model);

	}

	@SuppressWarnings("unchecked")
	private List<ReturnSlipMst> simulateSearchResult(
			String returnSlipNo, String senderStore, String fromDate,
			String toDate) {

		List<ReturnSlipMst> searchRSList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn(
						"ReturnSlipMst", "returnSlipDate",
						fromDate, toDate, "approved", "1", "senderStore",
						senderStore);

		// iterate a list and filter by tagName
		if (returnSlipMstList.size() > 0) {
			for (ReturnSlipMst rsMst : returnSlipMstList) {
				if (rsMst.getReturnSlipNo().toLowerCase()
						.contains(returnSlipNo.toLowerCase())) {
					searchRSList.add(rsMst);
				}
			}
		}
		if (returnSlipNo.length() > 0) {
			return searchRSList;
		} else {
			return returnSlipMstList;
		}

	}
}
