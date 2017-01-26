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
import com.ibcs.desco.common.model.CN2LSReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.localStore.model.LSReturnSlipDtl;
import com.ibcs.desco.localStore.model.LSReturnSlipMst;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;

@Controller
public class LSFinalReturnSlipController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/received/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsReturnSlipMstList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<LSReturnSlipMst> lsReturnSlipMstList = (List<LSReturnSlipMst>) (Object) commonService
				.getObjectListByTwoColumn("LSReturnSlipMst", "approved", "1", "requestedDeptId", authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lsReturnSlipMstList", lsReturnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalReturnSlipList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ls/received/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView lsReturnSlipDetailsInfo(LSReturnSlipMst returnSlipMst) {

		String id = returnSlipMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		LSReturnSlipMst returnSlipMstDb = (LSReturnSlipMst) commonService
				.getAnObjectByAnyUniqueColumn("LSReturnSlipMst", "id", id);

		List<LSReturnSlipDtl> returnSlipDtlList = (List<LSReturnSlipDtl>) (Object) commonService
				.getObjectListByAnyColumn("LSReturnSlipDtl", "returnSlipNo",
						returnSlipMstDb.getReturnSlipNo());

		String operationId = returnSlipMstDb.getReturnSlipNo();

		// get all hierarchy history against current operation id and status
		// done

		LSStoreTicketMst lsStoreTicketMstDb = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (lsStoreTicketMstDb.getStoreTicketType().equals(CN2_LS_RETURN_SLIP)) {
			List<CN2LSReturnSlipApprovalHierarchyHistory> approveHistoryHistoryList = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"CN2LSReturnSlipApprovalHierarchyHistory",
							"operationName", CN2_LS_RETURN_SLIP, "operationId",
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

		return new ModelAndView("localStore/lsFinalReturnSlipShow", model);
	}

	@RequestMapping(value = "/ls/received/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(LSReturnSlipMst returnSlipMst) {

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

		List<LSReturnSlipMst> returnSlipMstList = this
				.simulateSearchResult(returnSlipNo, senderStore, fromDate,
						toDate, department);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lsReturnSlipMstList", returnSlipMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsFinalReturnSlipList", model);

	}

	@SuppressWarnings("unchecked")
	private List<LSReturnSlipMst> simulateSearchResult(
			String returnSlipNo, String senderStore, String fromDate,
			String toDate, Departments department) {

		List<LSReturnSlipMst> searchRSList = new ArrayList<LSReturnSlipMst>();
		List<LSReturnSlipMst> returnSlipMstList = (List<LSReturnSlipMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn(
						"LSReturnSlipMst", "returnSlipDate",
						fromDate, toDate, "approved", "1","requestedDeptId",department.getDeptId());

		// iterate a list and filter by tagName
		if (returnSlipMstList.size() > 0) {
			for (LSReturnSlipMst rsMst : returnSlipMstList) {
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
