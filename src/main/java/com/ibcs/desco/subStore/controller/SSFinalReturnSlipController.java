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
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

@Controller
public class SSFinalReturnSlipController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/received/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssReturnSlipMstList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<SSReturnSlipMst> ssReturnSlipMstList = (List<SSReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipMst", "approved", "1");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ssReturnSlipMstList", ssReturnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalReturnSlipList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ss/received/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ssReturnSlipDetailsInfo(SSReturnSlipMst returnSlipMst) {

		String id = returnSlipMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		SSReturnSlipMst returnSlipMstDb = (SSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("SSReturnSlipMst", "id", id);

		List<SSReturnSlipDtl> returnSlipDtlList = (List<SSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("SSReturnSlipDtl", "returnSlipNo",
						returnSlipMstDb.getReturnSlipNo());

		String operationId = returnSlipMstDb.getReturnSlipNo();

		// get all hierarchy history against current operation id and status
		// done

		SSStoreTicketMst ssStoreTicketMstDb = (SSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("SSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (ssStoreTicketMstDb.getStoreTicketType().equals(LS_SS_REQUISITION)) {
			List<LsSsReturnSlipApprovalHierarchyHistory> approveHistoryHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"LsSsReturnSlipApprovalHierarchyHistory",
							"operationName", LS_SS_REQUISITION, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);

		} else if (ssStoreTicketMstDb.getStoreTicketType().equals(
				CN_SS_REQUISITION)) {

			List<CnSsReturnSlipApprovalHierarchyHistory> approveHistoryHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"CnSsReturnSlipApprovalHierarchyHistory",
							"operationName", CN_SS_REQUISITION, "operationId",
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

		return new ModelAndView("subStore/ssFinalReturnSlipShow", model);
	}

	@RequestMapping(value = "/ss/received/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(SSReturnSlipMst returnSlipMst) {

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

		List<SSReturnSlipMst> returnSlipMstList = this.simulateSearchResult(
				returnSlipNo, senderStore, fromDate, toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ssReturnSlipMstList", returnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("subStore/ssFinalReturnSlipList", model);

	}

	@SuppressWarnings("unchecked")
	private List<SSReturnSlipMst> simulateSearchResult(String returnSlipNo,
			String senderStore, String fromDate, String toDate) {

		List<SSReturnSlipMst> searchRSList = new ArrayList<SSReturnSlipMst>();
		List<SSReturnSlipMst> returnSlipMstList = (List<SSReturnSlipMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn("ReturnSlipMst",
						"returnSlipDate", fromDate, toDate, "approved", "1",
						"senderStore", senderStore);

		// iterate a list and filter by tagName
		if (returnSlipMstList.size() > 0) {
			for (SSReturnSlipMst rsMst : returnSlipMstList) {
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
