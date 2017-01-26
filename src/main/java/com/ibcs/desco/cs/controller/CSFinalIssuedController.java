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
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;

@Controller
public class CSFinalIssuedController extends Constrants {

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
	@RequestMapping(value = "/cs/issued/finalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemIssuedList() {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CentralStoreRequisitionMst> csIssuedMstList = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionMst",
						"approved", "1");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("csIssuedMstList", csIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalItemIssuedList", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cs/issued/detailsInfo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView csItemIssuedDetailsInfo(
			CentralStoreRequisitionMst csReqMst) {

		String id = csReqMst.getId().toString();
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		CentralStoreRequisitionMst csIssuedMstDb = (CentralStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst",
						"id", id);

		List<CentralStoreRequisitionDtl> csIssuedDtlList = (List<CentralStoreRequisitionDtl>) (Object) commonService
				.getObjectListByAnyColumn("CentralStoreRequisitionDtl",
						"requisitionNo", csIssuedMstDb.getRequisitionNo());

		String operationId = csIssuedMstDb.getRequisitionNo();

		// get all hierarchy history against current operation id and status
		// done

		CSStoreTicketMst csStoreTicketMstDb = (CSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("CSStoreTicketMst",
						"operationId", operationId);

		Map<String, Object> model = new HashMap<String, Object>();

		if (csStoreTicketMstDb.getStoreTicketType().equals(LS_CS_REQUISITION)) {
			List<LsCsRequisitionApprovalHierarchyHistory> approveHistoryHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"LsCsRequisitionApprovalHierarchyHistory",
							"operationName", LS_CS_REQUISITION, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);

		} else if (csStoreTicketMstDb.getStoreTicketType().equals(
				LS_CS_REQUISITION)) {

			List<SsCsRequisitionApprovalHierarchyHistory> approveHistoryHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"SsCsRequisitionApprovalHierarchyHistory",
							"operationName", SS_CS_REQUISITION, "operationId",
							operationId, "status", DONE);

			model.put("approveHistoryHistoryList", approveHistoryHistoryList);
		}
		
		/* Following are added by Ihteshamul Alam */
		String cuserrole = csIssuedMstDb.getCreatedBy();
		//String ruserrole = csIssuedMstDb.getReceivedBy();
		/* Set created by */
		AuthUser createBy = (AuthUser) commonService
				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", cuserrole);
		csIssuedMstDb.setCreatedBy(createBy.getName());
		
		/*Set received by */
//		AuthUser receivedBy = (AuthUser) commonService
//				.getAnObjectByAnyUniqueColumn("com.ibcs.desco.admin.model.AuthUser", "userid", ruserrole);
//		String freceivebBy = receivedBy.getName();
//		if( freceivebBy == null ) { }
//		else {
//			csIssuedMstDb.setReceivedBy( receivedBy.getName() );
//		}
		
		model.put("csIssuedDtlList", csIssuedDtlList);
		model.put("csIssuedMst", csIssuedMstDb);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalItemIssuedShow", model);
	}

	@RequestMapping(value = "/cs/issued/finalListSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalListSearch(CentralStoreRequisitionMst csReqMst) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date firstDate = csReqMst.getFromDate();

		Date secondDate = csReqMst.getToDate();

		String fromDate = df.format(firstDate);
		String toDate = df.format(secondDate);

		String senderStore = csReqMst.getSenderStore();

		String requistionNo = csReqMst.getRequisitionNo();

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<CentralStoreRequisitionMst> csIssuedMstList = this
				.simulateSearchResult(requistionNo, senderStore, fromDate, toDate);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("csIssuedMstList", csIssuedMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("centralStore/finalItemIssuedList", model);

	}

	@SuppressWarnings("unchecked")
	private List<CentralStoreRequisitionMst> simulateSearchResult(
			String requisitionNo, String senderStore, String fromDate, String toDate) {

		List<CentralStoreRequisitionMst> searchIssuedList = new ArrayList<CentralStoreRequisitionMst>();
		List<CentralStoreRequisitionMst> itemIssuedMstList = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByDateRangeAndTwoColumn(
						"CentralStoreRequisitionMst", "requisitionDate",
						fromDate, toDate, "approved", "1", "senderStore", senderStore);

		// iterate a list and filter by tagName
		if (itemIssuedMstList.size() > 0) {
			for (CentralStoreRequisitionMst csRequisitionMst : itemIssuedMstList) {
				if (csRequisitionMst.getRequisitionNo().toLowerCase()
						.contains(requisitionNo.toLowerCase())) {
					searchIssuedList.add(csRequisitionMst);
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
