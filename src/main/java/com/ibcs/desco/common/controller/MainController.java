package com.ibcs.desco.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.RoleService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.dashboard.AdminPanel;
import com.ibcs.desco.common.dashboard.Budget;
import com.ibcs.desco.common.dashboard.Committee;
import com.ibcs.desco.common.dashboard.Contractor;
import com.ibcs.desco.common.dashboard.ContractorWorkshop;
import com.ibcs.desco.common.dashboard.FixedAsset;
import com.ibcs.desco.common.dashboard.Inventory;
import com.ibcs.desco.common.dashboard.LocalStore;
import com.ibcs.desco.common.dashboard.Mps;
import com.ibcs.desco.common.dashboard.PndContractor;
import com.ibcs.desco.common.dashboard.PndDept;
import com.ibcs.desco.common.dashboard.Procurement;
import com.ibcs.desco.common.dashboard.ProjectDirectors;
import com.ibcs.desco.common.dashboard.Reports;
import com.ibcs.desco.common.dashboard.Settings;
import com.ibcs.desco.common.dashboard.SndContractor;
import com.ibcs.desco.common.dashboard.SubStore;
import com.ibcs.desco.common.dashboard.WorkShop;
import com.ibcs.desco.common.model.C2LSRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CN2LSReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.JobCardApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LSStoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsPdSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.StoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.model.TransformerTestApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXRequisitionApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsCsXReturnSlipApprovalHierarchyHistory;
import com.ibcs.desco.common.model.WsLsRCVPreventiveApprovalHierarchyHistory;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.common.service.LsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.LsSsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.SsCsRequisitionApprovalHierarchyHistoryService;
import com.ibcs.desco.common.service.StoreTicketApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.model.AuctionProcessMst;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.cs.model.ReturnSlipMst;
import com.ibcs.desco.cs.service.CSStoreTicketMstService;
import com.ibcs.desco.cs.service.CentralStoreRequisitionMstService;
import com.ibcs.desco.cs.service.ReturnSlipMstService;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.localStore.model.LSReturnSlipMst;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionMst;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.subStore.model.SubStoreRequisitionMst;
import com.ibcs.desco.subStore.service.SSReturnSlipMstService;
import com.ibcs.desco.subStore.service.SubStoreRequisitionMstService;
import com.ibcs.desco.workshop.model.JobCardMst;
import com.ibcs.desco.workshop.model.TestReport3P;
import com.ibcs.desco.workshop.model.WsReceivePreventiveMst;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
@Controller
public class MainController extends Constrants {

	@Autowired
	CentralStoreRequisitionMstService centralStoreRequisitionMstService;
	@Autowired
	SubStoreRequisitionMstService subStoreRequisitionMstService;
	@Autowired
	CSStoreTicketMstService csStoreTicketMstService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	private RoleService roleService;

	@Autowired
	LsCsRequisitionApprovalHierarchyHistoryService lsCsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	LsSsRequisitionApprovalHierarchyHistoryService lsSsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	SsCsRequisitionApprovalHierarchyHistoryService ssCsRequisitionApprovalHierarchyHistoryService;

	@Autowired
	StoreTicketApprovalHierarchyHistoryService storeTicketApprovalHierarchyHistoryService;

	@Autowired
	ReturnSlipMstService returnSlipMstService;
	@Autowired
	SSReturnSlipMstService ssReturnSlipMstService;

	@Autowired
	UserService userService;

	@Autowired
	CommonService commonService;

	@RequestMapping(value = "/procurement.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#procurement, 'READ')")
	public ModelAndView getProcurement(Procurement procurement) {
		return new ModelAndView("procurement/procurement");
	}

	/*
	 * @RequestMapping(value = "/centeralStore.do", method = RequestMethod.GET)
	 * public ModelAndView getCenteralStore() { return new
	 * ModelAndView("centeralStore/centeralStore"); }
	 */

	/*
	 * @RequestMapping(value = "/subStore.do", method = RequestMethod.GET)
	 * 
	 * @PreAuthorize("isAuthenticated()") public ModelAndView getSubStore() {
	 * return new ModelAndView("subStore/subStore"); }
	 */
	@RequestMapping(value = "/subStore.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#subStore, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#subStore, 'READ')")
	public ModelAndView getSubStore(SubStore subStore) {
		Map<String, Object> model = new HashMap<String, Object>();
		// List<ReturnSlipMst>
		int returnSlipTray = 0;
		if (this.retrunSlipListForTray() != null) {
			returnSlipTray = this.retrunSlipListForTray().size();
		}
		
		int returnSlipTrayCS = 0;
		if (this.retrunSlipListForTrayCS() != null) {
			returnSlipTrayCS = this.retrunSlipListForTrayCS().size();
		}
		
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.reqisitionListForTray() != null) {
			requisitionTray = this.reqisitionListForTray();
		}

		// store ticket
		int storeTicketTray = 0;
		if (this.ssStoreTicketListForTray() != null) {
			storeTicketTray = this.ssStoreTicketListForTray().size();
		}
		// Store Ticket Tray End

		// store ticket
		int itemReceivedTray = 0;
		if (this.ssItemReceivedListForTray() != null) {
			itemReceivedTray = this.ssItemReceivedListForTray().size();
		}
		// Store Ticket Tray End
		model.put("senderStore", ContentType.SUB_STORE.toString());
		model.put("returnSlipTray", returnSlipTray);
		model.put("returnSlipTrayCS", returnSlipTrayCS);
		model.put("requisitionTray", requisitionTray);
		model.put("storeTicketTray", storeTicketTray);
		model.put("itemReceivedTray", itemReceivedTray);
		return new ModelAndView("subStore/subStore", model);
	}

	@RequestMapping(value = "/localStore.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#localStore, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#localStore, 'READ')")
	public ModelAndView getLocalStore(LocalStore localStore) {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(deptId);
		
		int contReturnSlipTray = 0;
		int contRequisitionTray = 0;

		Map<String, Object> model = new HashMap<String, Object>();
		// List<ReturnSlipMst>
		int returnSlipTray = 0;
		if (this.lsRetrunSlipListForTray() != null) {
			returnSlipTray = this.lsRetrunSlipListForTray().size();
		}
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.lsReqisitionListForTray() != null) {
			requisitionTray = this.lsReqisitionListForTray().size();
		}

		int storeTicketTray = 0;
		if (this.lsStoreTicketTray() != null) {
			storeTicketTray = this.lsStoreTicketTray().size();
		}

		int itemReceivedTray = 0;
		if (this.lsItemReceivedTray() != null) {
			itemReceivedTray = this.lsItemReceivedTray().size();
		}

		int ssItemReceivedTray = 0;
		if (this.ssItemReceivedTray() != null) {
			ssItemReceivedTray = this.ssItemReceivedTray().size();
		}
		
		// contRequisitionTray
		if (this.contReqisitionListForTray() != null) {
			contRequisitionTray = this.contReqisitionListForTray().size();
		}
		
		// contReturnSlipTray
		if (this.contRetrunSlipListForTray() != null) {
			contReturnSlipTray = this.contRetrunSlipListForTray().size();
		}

		// Store Ticket Tray End
		model.put("senderStore", ContentType.LOCAL_STORE.toString());
		model.put("ssItemReceivedTray", ssItemReceivedTray);
		model.put("itemReceivedTray", itemReceivedTray);
		model.put("returnSlipTray", returnSlipTray);
		model.put("requisitionTray", requisitionTray);
		model.put("storeTicketTray", storeTicketTray);		
		//added by taleb
		model.put("contReturnSlipTray", contReturnSlipTray);
		model.put("contRequisitionTray", contRequisitionTray);

		model.put("department", department);
		return new ModelAndView("localStore/localStore", model);
	}

	@RequestMapping(value = "/adminPanel.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#adminPanel, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#adminPanel, 'READ')")
	public ModelAndView getAdminPanel(AdminPanel adminPanel) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roleList", roleService.listRoles());
		return new ModelAndView("admin/adminPanels", model);
		// return new ModelAndView("admin/admin");
	}

	// Add new role
	@RequestMapping(value = "/addNewRole.do", method = RequestMethod.GET)
	public ModelAndView addNewRole() {
		return new ModelAndView("admin/addNewRole");
	}

	// Add new user
	@RequestMapping(value = "/addNewUser.do", method = RequestMethod.GET)
	public ModelAndView addNewUser() {
		return new ModelAndView("admin/addNewUser");
	}

	@RequestMapping(value = "/budget.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#budget, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#budget, 'READ')")
	public ModelAndView getBudget(Budget budget) {
		return new ModelAndView("budget/budget");
	}

	@RequestMapping(value = "/pnd.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#pnd, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#pnd, 'READ')")
	public ModelAndView getPndHome(PndDept pnd) {
		Map<String, Object> model = new HashMap<String, Object>();
		int returnSlipTray = 0;
		if (this.cnRetrunSlipListForTray() != null) {
			returnSlipTray = this.cnRetrunSlipListForTray().size();
		}
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.cnRequisitionListForTray() != null) {
			requisitionTray = this.cnRequisitionListForTray().size();
		}
		model.put("returnSlipTray", returnSlipTray);
		model.put("requisitionTray", requisitionTray);
		return new ModelAndView("contractor/pnd", model);
	}

	@RequestMapping(value = "/contractor.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#contractor, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#contractor, 'READ')")
	public ModelAndView getContractor(Contractor contractor) {
		Map<String, Object> model = new HashMap<String, Object>();
		int returnSlipTray = 0;
		if (this.cnRetrunSlipListForTray() != null) {
			returnSlipTray = this.cnRetrunSlipListForTray().size();
		}
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.cnRequisitionListForTray() != null) {
			requisitionTray = this.cnRequisitionListForTray().size();
		}

		model.put("returnSlipTray", returnSlipTray);
		model.put("requisitionTray", requisitionTray);
		return new ModelAndView("contractor/contractor");
	}

	@RequestMapping(value = "/projectDirectors.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#projectDirectors, 'READ')")
	public ModelAndView getProjectDirectors(ProjectDirectors projectDirectors) {
		
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		// List<CentralStoreRequisitionMst>
		int requisitionTray = 0;
		if (this.cnCsSsRequisitionListForTrayPD() != null) {
			requisitionTray = this.cnCsSsRequisitionListForTrayPD().size();
		}

		model.put("department", department);
		model.put("requisitionTray", requisitionTray);
		return new ModelAndView("contractor/pd/projectDirector", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/contractorMain.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#contractor, 'READ')")
	public ModelAndView getContractorMain(Contractor contractor) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("contractor/contractorMain");
	}

	@RequestMapping(value = "/procurementMain.do", method = RequestMethod.GET)
	public ModelAndView procurementMain() {
		return new ModelAndView("common/procurementMain");
	}

	@RequestMapping(value = "/mps.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#mps, 'READ')")
	public ModelAndView mps(Mps mps) {
		return new ModelAndView("common/mps");
	}

	@RequestMapping(value = "/committee.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#committee, 'READ')")
	public ModelAndView committee(Committee committee) {
		return new ModelAndView("common/committee");
	}

	@RequestMapping(value = "/workshop.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@PostAuthorize("hasPermission(#workShop, 'READ')")
	public ModelAndView getWorkshop(WorkShop workShop) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		int wsXRequisitionTray = 0;
		if (this.wsXRequisitionTray() != null) {
			wsXRequisitionTray = this.wsXRequisitionTray().size();			
		}
		int wsMatRequisitionTray = 0;
		if (this.wsMatRequisitionTray() != null) {
			wsMatRequisitionTray = this.wsMatRequisitionTray().size();			
		}
		int wsPrevItemReceiveTray = 0;
		if (this.wsPrevItemReceiveTray() != null) {
			wsPrevItemReceiveTray = this.wsPrevItemReceiveTray().size();			
		}
		int wsXReturnTray = 0;
		if (this.wsXReturnTray() != null) {
			wsXReturnTray = this.wsXReturnTray().size();			
		}
		int wsReturnTray = 0;
		if (this.wsReturnTray() != null) {
			wsReturnTray = this.wsReturnTray().size();			
		}
		int wsJobCardTray = 0;
		if (this.wsJobCardTray() != null) {
			wsJobCardTray = this.wsJobCardTray().size();			
		}
		int wsXFormerTestTray = 0;
		if (this.wsXFormerTestTray() != null) {
			wsXFormerTestTray = this.wsXFormerTestTray().size();			
		}

		// Store Ticket Tray End
		model.put("senderStore", ContentType.WS_CN_XFORMER);
		model.put("wsXRequisitionTray", wsXRequisitionTray);
		model.put("wsMatRequisitionTray", wsMatRequisitionTray);
		model.put("wsPrevItemReceiveTray", wsPrevItemReceiveTray);
		model.put("wsXReturnTray", wsXReturnTray);
		model.put("wsReturnTray", wsReturnTray);
		model.put("wsJobCardTray", wsJobCardTray);
		model.put("wsXFormerTestTray", wsXFormerTestTray);

		return new ModelAndView("workshop/workshop", model);
	}

	// nasrin.ibcs-primax
	@SuppressWarnings("unchecked")
	private List<CentralStoreRequisitionMst> wsXRequisitionTray() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = new ArrayList<String>();

		List<WsCsXRequisitionApprovalHierarchyHistory> wsRequisitionApprovalList = (List<WsCsXRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsXRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		for (WsCsXRequisitionApprovalHierarchyHistory WsCsHistory : wsRequisitionApprovalList) {

			reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
		}

		List<CentralStoreRequisitionMst> wsApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList(
						"CentralStoreRequisitionMst", "requisitionNo",
						reqHierarchyOptIdList);

		return wsApprovedRequisitions;
	}
	// nasrin.ibcs-primax
		@SuppressWarnings("unchecked")
		private List<CentralStoreRequisitionMst> wsMatRequisitionTray() {
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			List<String> reqHierarchyOptIdList = new ArrayList<String>();

			List<WsCsRequisitionApprovalHierarchyHistory> wsRequisitionApprovalList = (List<WsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
							"WsCsRequisitionApprovalHierarchyHistory", deptId,
							roleName, OPEN);
			for (WsCsRequisitionApprovalHierarchyHistory WsCsHistory : wsRequisitionApprovalList) {

				reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
			}

			List<CentralStoreRequisitionMst> wsApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"CentralStoreRequisitionMst", "requisitionNo",
							reqHierarchyOptIdList);

			return wsApprovedRequisitions;
		}
		// nasrin.ibcs-primax
		@SuppressWarnings("unchecked")
		private List<WsReceivePreventiveMst> wsPrevItemReceiveTray() {
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			List<String> reqHierarchyOptIdList = new ArrayList<String>();

			List<WsLsRCVPreventiveApprovalHierarchyHistory> wsRequisitionApprovalList = (List<WsLsRCVPreventiveApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
							"WsLsRCVPreventiveApprovalHierarchyHistory", deptId,
							roleName, OPEN);
			for (WsLsRCVPreventiveApprovalHierarchyHistory WsCsHistory : wsRequisitionApprovalList) {

				reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
			}

			List<WsReceivePreventiveMst> wsReceivePreventiveMst = (List<WsReceivePreventiveMst>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"WsReceivePreventiveMst", "noteNumber",
							reqHierarchyOptIdList);
			

			return wsReceivePreventiveMst;
		}


	@SuppressWarnings("unchecked")
	private List<ReturnSlipMst> wsXReturnTray() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = new ArrayList<String>();

		List<WsCsXReturnSlipApprovalHierarchyHistory> wsReturnApprovalList = (List<WsCsXReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsXReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		for (WsCsXReturnSlipApprovalHierarchyHistory WsCsHistory : wsReturnApprovalList) {

			reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
		}
		

		List<ReturnSlipMst> wsApprovedReturns = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("ReturnSlipMst",
						"returnSlipNo", reqHierarchyOptIdList);

		return wsApprovedReturns;
	}
	@SuppressWarnings("unchecked")
	private List<ReturnSlipMst> wsReturnTray() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = new ArrayList<String>();

	List<WsCsReturnSlipApprovalHierarchyHistory> wsReturnApprovalList = (List<WsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"WsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		for (WsCsReturnSlipApprovalHierarchyHistory WsCsHistory : wsReturnApprovalList) {

			reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
		}
		

		List<ReturnSlipMst> wsApprovedReturns = (List<ReturnSlipMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("ReturnSlipMst",
						"returnSlipNo", reqHierarchyOptIdList);

		return wsApprovedReturns;
	}

	@SuppressWarnings("unchecked")
	private List<JobCardMst> wsJobCardTray() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = new ArrayList<String>();

	List<JobCardApprovalHierarchyHistory> wsReturnApprovalList = (List<JobCardApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"JobCardApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		for (JobCardApprovalHierarchyHistory WsCsHistory : wsReturnApprovalList) {

			reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
		}
		

		List<JobCardMst> wsApprovedReturns = (List<JobCardMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("JobCardMst",
						"id", reqHierarchyOptIdList);

		return wsApprovedReturns;
	}
	@SuppressWarnings("unchecked")
	private List<TestReport3P> wsXFormerTestTray() {
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = new ArrayList<String>();

	List<TransformerTestApprovalHierarchyHistory> wsReturnApprovalList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"TransformerTestApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		for (TransformerTestApprovalHierarchyHistory WsCsHistory : wsReturnApprovalList) {

			reqHierarchyOptIdList.add(WsCsHistory.getOperationId());
		}
		

		List<TestReport3P> wsApprovedReturns = (List<TestReport3P>) (Object) commonService
				.getObjectListByAnyColumnValueList("TestReport3P",
						"tsfRegMst.id", reqHierarchyOptIdList);

		return wsApprovedReturns;
	}
	@RequestMapping(value = "/settings.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getSettings(Settings settings) {
		return new ModelAndView("common/settings");
	}
	
	@RequestMapping(value = "/pettycash.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")	
	public ModelAndView getPettyCash() {
		return new ModelAndView("common/pettyCash");
	}
	
	@RequestMapping(value = "/lprr.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")	
	public ModelAndView getLocalPurchaseAndLocalRR() {
		return new ModelAndView("common/lprr");
	}

	@RequestMapping(value = "/reports.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#reports, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#reports, 'READ')")
	public ModelAndView getReports(Reports reports) {
		return new ModelAndView("report/reports");
	}
	
	@RequestMapping(value = "/auction.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#auctionProcessMst, 'READ')")
	public ModelAndView getAuction(AuctionProcessMst auctionProcessMst) {
		return new ModelAndView("auction/auction");
	}

	@RequestMapping(value = "/inventory.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#inventory, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#inventory, 'READ')")
	public String getInventory(Inventory inventory) {
		return "redirect:inventory/averagePriceList.do";
	}

	@RequestMapping(value = "/fixedAssets.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#fixedAsset, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#fixedAsset, 'READ')")
	public ModelAndView getFixedAssets(FixedAsset fixedAsset) {
		return new ModelAndView("fixedAsset/fixedAssets");
	}

	@RequestMapping(value = "/createVendor.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#procurement, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#procurement, 'READ')")
	public ModelAndView getCreateVendor(Procurement procurement) {
		return new ModelAndView("procurement/createVendor");
	}

	@RequestMapping(value = "/contractorSnd.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#sndContractor, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#sndContractor, 'READ')")
	public ModelAndView getSndConractor(SndContractor sndContractor) {
		int contReturnSlipTray = 0;
		int contRequisitionTray = 0;

		Map<String, Object> model = new HashMap<String, Object>();
		// contRequisitionTray
		if (this.contReqisitionListForTray() != null) {
			contRequisitionTray = this.contReqisitionListForTray().size();
		}
		
		// contReturnSlipTray
		if (this.contRetrunSlipListForTray() != null) {
			contReturnSlipTray = this.contRetrunSlipListForTray().size();
		}
		
		model.put("contReturnSlipTray", contReturnSlipTray);
		model.put("contRequisitionTray", contRequisitionTray);
		return new ModelAndView("sndContractor/contractor", model);
	}

	@RequestMapping(value = "/contractorPnd.do", method = RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	// @PostAuthorize("hasPermission(#pndContractor, 'READ')")
	@PreAuthorize("isAuthenticated() and hasPermission(#pndContractor, 'READ')")
	public ModelAndView getSndConractor(PndContractor pndContractor) {
		return new ModelAndView("pndContractor/contractorPnd");
	}

	@RequestMapping(value = "/contractorWorkshop.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated() and hasPermission(#contractorWorkshop, 'READ')")
	public ModelAndView getContractorWorkshop(
			ContractorWorkshop contractorWorkshop) {
		return new ModelAndView("workshop/contractorWorkshop");
	}

	// Item Requisition List
	@SuppressWarnings("unchecked")
	public/* List<SubStoreRequisitionMst> */Integer reqisitionListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");
		List<CnSsRequisitionApprovalHierarchyHistory> cnSsRequisitionHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");
		
		//added taleb
		List<LsPdSsRequisitionApprovalHierarchyHistory> lsPdSsRequisitionHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList = new String[lsSsRequisitionHistoryList.size()];
		for (int i = 0; i < lsSsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList1 = new String[cnSsRequisitionHistoryList
				.size()];
		for (int i = 0; i < cnSsRequisitionHistoryList.size(); i++) {
			operationIdList1[i] = cnSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList2 = new String[ssCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList2[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		
		String[] operationIdList6 = new String[lsPdSsRequisitionHistoryList
			                       				.size()];
			for (int i = 0; i < lsPdSsRequisitionHistoryList.size(); i++) {
			     operationIdList6[i] = lsPdSsRequisitionHistoryList.get(i)
			           .getOperationId();
			}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = new ArrayList<SubStoreRequisitionMst>();
		List<SubStoreRequisitionMst> subStoreRequisitionMstList2 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);		

		List<SubStoreRequisitionMst> subStoreRequisitionMstList1 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList1);

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList2 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList2);
		
		List<SubStoreRequisitionMst> subStoreRequisitionMstList3 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList6);
		
		if(subStoreRequisitionMstList2 != null){
			subStoreRequisitionMstList.addAll(subStoreRequisitionMstList2);
		}

		if (subStoreRequisitionMstList1 != null) {
			subStoreRequisitionMstList.addAll(subStoreRequisitionMstList1);
		}
		
		// added by Taleb
		if (subStoreRequisitionMstList3 != null) {
			subStoreRequisitionMstList.addAll(subStoreRequisitionMstList3);
		}

		/*
		 * if (centralStoreRequisitionMstList2 != null) {
		 * subStoreRequisitionMstList.addAll(centralStoreRequisitionMstList2); }
		 */
		int listOne = 0, listTwo = 0;
		if (subStoreRequisitionMstList != null) {
			listOne = subStoreRequisitionMstList.size();
		}
		if (centralStoreRequisitionMstList2 != null) {
			listTwo = centralStoreRequisitionMstList2.size();
		}

		return listOne + listTwo;
	}

	// Return Slip List
	@SuppressWarnings("unchecked")
	public List<ReturnSlipMst> lsRetrunSlipListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<LsSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList3 = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}
		List<String> operationId3 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList3.size(); i++) {
			operationId3.add(storeSlipApprovalHierarchyHistoryList3.get(i)
					.getOperationId());
		}

		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		List<SSReturnSlipMst> ssReturnSlipMstList = new ArrayList<SSReturnSlipMst>();

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId3) != null) {
			ssReturnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId3);
		}
		if (returnSlipMstList1 != null) {
			returnSlipMstList.addAll(returnSlipMstList1);
		}
		if (returnSlipMstList2 != null) {
			returnSlipMstList.addAll(returnSlipMstList2);
		}
		if (ssReturnSlipMstList != null) {
			returnSlipMstList.addAll(importObjListToSs(ssReturnSlipMstList));
		}

		return returnSlipMstList;

	}

	// for Local Store Item Requisition List
	@SuppressWarnings({ "unchecked", "unused" })
	public List<CentralStoreRequisitionMst> lsReqisitionListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<LsCsRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<LsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<SsCsRequisitionApprovalHierarchyHistory> ssCsRequisitionHistoryList = (List<SsCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<LsSsRequisitionApprovalHierarchyHistory> lsSsRequisitionHistoryList = (List<LsSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		List<CnSsRequisitionApprovalHierarchyHistory> cnSsRequisitionHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<LsPdCsRequisitionApprovalHierarchyHistory> lsPdCsRequisitionHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdCsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		
		List<LsPdSsRequisitionApprovalHierarchyHistory> lsPdSsRequisitionHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsPdSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		String[] operationIdList1 = new String[lsCsRequisitionHistoryList
				.size()];
		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIdList1[i] = lsCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList2 = new String[ssCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < ssCsRequisitionHistoryList.size(); i++) {
			operationIdList2[i] = ssCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList3 = new String[lsSsRequisitionHistoryList
				.size()];

		for (int i = 0; i < lsSsRequisitionHistoryList.size(); i++) {
			operationIdList3[i] = lsSsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		String[] operationIdList4 = new String[cnSsRequisitionHistoryList
				.size()];

		for (int i = 0; i < cnSsRequisitionHistoryList.size(); i++) {
			operationIdList4[i] = cnSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		String[] operationIdList5 = new String[lsPdCsRequisitionHistoryList
				.size()];

		for (int i = 0; i < lsPdCsRequisitionHistoryList.size(); i++) {
			operationIdList5[i] = lsPdCsRequisitionHistoryList.get(i)
					.getOperationId();
		}
		
		String[] operationIdList6 = new String[lsPdSsRequisitionHistoryList
		                       				.size()];
		for (int i = 0; i < lsPdSsRequisitionHistoryList.size(); i++) {
		     operationIdList6[i] = lsPdSsRequisitionHistoryList.get(i)
		           .getOperationId();
		}

		CentralStoreRequisitionMst cs = null;
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = new ArrayList<CentralStoreRequisitionMst>();
		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList1 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList1);

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList2 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList2);

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList3);

		List<SubStoreRequisitionMst> subStoreRequisitionMstList2 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList4);

		List<CentralStoreRequisitionMst> centralStoreRequisitionMstList5 = centralStoreRequisitionMstService
				.listCentralStoreRequisitionMstByOperationIds(operationIdList5);
		
		List<SubStoreRequisitionMst> subStoreRequisitionMstList3 = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList6);

		if (subStoreRequisitionMstList2 != null) {
			if (subStoreRequisitionMstList != null) {
				subStoreRequisitionMstList.addAll(subStoreRequisitionMstList2);
			} else {
				for (SubStoreRequisitionMst ss : subStoreRequisitionMstList2) {
					cs = new CentralStoreRequisitionMst();
					centralStoreRequisitionMstList.add(importObjToCs(ss));
				}
			}
		}
		if (centralStoreRequisitionMstList1 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList1);
		}
		if (centralStoreRequisitionMstList2 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList2);
		}
		if (centralStoreRequisitionMstList5 != null) {
			centralStoreRequisitionMstList
					.addAll(centralStoreRequisitionMstList5);
		}
		if (subStoreRequisitionMstList != null) {
			for (SubStoreRequisitionMst ss : subStoreRequisitionMstList) {
				cs = new CentralStoreRequisitionMst();
				centralStoreRequisitionMstList.add(importObjToCs(ss));
			}
		}
		
		// added by Taleb
		if (subStoreRequisitionMstList3 != null) {
			for (SubStoreRequisitionMst ss : subStoreRequisitionMstList3) {
				cs = new CentralStoreRequisitionMst();
				centralStoreRequisitionMstList.add(importObjToCs(ss));
			}
		}
		
		return centralStoreRequisitionMstList;
	}

	// Local Store Return Slip List
	@SuppressWarnings("unchecked")
	public List<SSReturnSlipMst> retrunSlipListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<LsSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<LsSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		List<LsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<LsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"LsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);
		
		/*List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);*/
		
		List<CnSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList3 = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}
		
		/*List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}*/
		
		List<String> operationId3 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList3.size(); i++) {
			operationId3.add(storeSlipApprovalHierarchyHistoryList3.get(i)
					.getOperationId());
		}

		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList1 = new ArrayList<ReturnSlipMst>();
//		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();
		List<SSReturnSlipMst> returnSlipMstList3 = new ArrayList<SSReturnSlipMst>();
		
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId) != null) {
			returnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId);
		}

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList1 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId1);
		}
		
		/*if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}*/
		
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId3) != null) {
			returnSlipMstList3 = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId3);
		}
		
		if(returnSlipMstList3.size() > 0){
			returnSlipMstList.addAll(returnSlipMstList3);
		}

		SSReturnSlipMst ssRsMst = null;

		if (returnSlipMstList1 != null) {
			for (ReturnSlipMst rsMst : returnSlipMstList1) {
				ssRsMst = new SSReturnSlipMst();
				ssRsMst.setActive(rsMst.isActive());
				ssRsMst.setApproved(rsMst.isApproved());
				ssRsMst.setCreatedBy(rsMst.getCreatedBy());
				ssRsMst.setCreatedDate(rsMst.getCreatedDate());
				ssRsMst.setId(rsMst.getId());
				ssRsMst.setKhathId(rsMst.getKhathId());
				ssRsMst.setKhathName(rsMst.getKhathName());
				ssRsMst.setModifiedBy(rsMst.getModifiedBy());
				ssRsMst.setModifiedDate(rsMst.getModifiedDate());
				ssRsMst.setReceiveFrom(rsMst.getReceiveFrom());
				ssRsMst.setRemarks(rsMst.getRemarks());
				ssRsMst.setReturnTo(rsMst.getReturnTo());
				ssRsMst.setReturnSlipDate(rsMst.getReturnSlipDate());
				ssRsMst.setReturnSlipNo(rsMst.getReturnSlipNo());
				ssRsMst.setSenderDeptName(rsMst.getSenderDeptName());
				ssRsMst.setSndCode(rsMst.getSndCode());
				ssRsMst.setUuid(rsMst.getUuid());
				ssRsMst.setSenderStore(rsMst.getSenderStore());
				returnSlipMstList.add(ssRsMst);
			}
		}
		
		/*if (returnSlipMstList2 != null) {
			for (ReturnSlipMst rsMst : returnSlipMstList2) {
				ssRsMst = new SSReturnSlipMst();
				ssRsMst.setActive(rsMst.isActive());
				ssRsMst.setApproved(rsMst.isApproved());
				ssRsMst.setCreatedBy(rsMst.getCreatedBy());
				ssRsMst.setCreatedDate(rsMst.getCreatedDate());
				ssRsMst.setId(rsMst.getId());
				ssRsMst.setKhathId(rsMst.getKhathId());
				ssRsMst.setKhathName(rsMst.getKhathName());
				ssRsMst.setModifiedBy(rsMst.getModifiedBy());
				ssRsMst.setModifiedDate(rsMst.getModifiedDate());
				ssRsMst.setReceiveFrom(rsMst.getReceiveFrom());
				ssRsMst.setRemarks(rsMst.getRemarks());
				ssRsMst.setReturnTo(rsMst.getReturnTo());
				ssRsMst.setReturnSlipDate(rsMst.getReturnSlipDate());
				ssRsMst.setReturnSlipNo(rsMst.getReturnSlipNo());
				ssRsMst.setSenderDeptName(rsMst.getSenderDeptName());
				ssRsMst.setSndCode(rsMst.getSndCode());
				ssRsMst.setUuid(rsMst.getUuid());
				ssRsMst.setSenderStore(rsMst.getSenderStore());
				returnSlipMstList.add(ssRsMst);
			}
		}*/

		return returnSlipMstList;

	}
	
	@SuppressWarnings("unchecked")
	public List<SSReturnSlipMst> retrunSlipListForTrayCS() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<SsCsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList2 = (List<SsCsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"SsCsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId2 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList2.size(); i++) {
			operationId2.add(storeSlipApprovalHierarchyHistoryList2.get(i)
					.getOperationId());
		}
		
		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		List<ReturnSlipMst> returnSlipMstList2 = new ArrayList<ReturnSlipMst>();

		
		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId2) != null) {
			returnSlipMstList2 = returnSlipMstService
					.listReturnSlipMstByOperationIdList(operationId2);
		}
		
		SSReturnSlipMst ssRsMst = null;
		
		if (returnSlipMstList2 != null) {
			for (ReturnSlipMst rsMst : returnSlipMstList2) {
				ssRsMst = new SSReturnSlipMst();
				ssRsMst.setActive(rsMst.isActive());
				ssRsMst.setApproved(rsMst.isApproved());
				ssRsMst.setCreatedBy(rsMst.getCreatedBy());
				ssRsMst.setCreatedDate(rsMst.getCreatedDate());
				ssRsMst.setId(rsMst.getId());
				ssRsMst.setKhathId(rsMst.getKhathId());
				ssRsMst.setKhathName(rsMst.getKhathName());
				ssRsMst.setModifiedBy(rsMst.getModifiedBy());
				ssRsMst.setModifiedDate(rsMst.getModifiedDate());
				ssRsMst.setReceiveFrom(rsMst.getReceiveFrom());
				ssRsMst.setRemarks(rsMst.getRemarks());
				ssRsMst.setReturnTo(rsMst.getReturnTo());
				ssRsMst.setReturnSlipDate(rsMst.getReturnSlipDate());
				ssRsMst.setReturnSlipNo(rsMst.getReturnSlipNo());
				ssRsMst.setSenderDeptName(rsMst.getSenderDeptName());
				ssRsMst.setSndCode(rsMst.getSndCode());
				ssRsMst.setUuid(rsMst.getUuid());
				ssRsMst.setSenderStore(rsMst.getSenderStore());
				returnSlipMstList.add(ssRsMst);
			}
		}

		return returnSlipMstList;

	}

	// contractor Item Requisition List to ss
	@SuppressWarnings("unchecked")
	public List<SubStoreRequisitionMst> cnRequisitionListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<CnSsRequisitionApprovalHierarchyHistory> lsSsRequisitionHistoryList = (List<CnSsRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsRequisitionApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		String[] operationIdList = new String[lsSsRequisitionHistoryList.size()];
		for (int i = 0; i < lsSsRequisitionHistoryList.size(); i++) {
			operationIdList[i] = lsSsRequisitionHistoryList.get(i)
					.getOperationId();
		}

		List<SubStoreRequisitionMst> subStoreRequisitionMstList = subStoreRequisitionMstService
				.listSubStoreRequisitionMstByOperationIds(operationIdList);

		return subStoreRequisitionMstList;
	}
	
	// contractor Item Requisition List to ss
		@SuppressWarnings({ "unchecked", "unused" })
		public List<CentralStoreRequisitionMst> cnCsSsRequisitionListForTrayPD() {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();			

			List<LsPdCsRequisitionApprovalHierarchyHistory> lsPdCsRequisitionHistoryList = (List<LsPdCsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
							"LsPdCsRequisitionApprovalHierarchyHistory", deptId,
							roleName, OPEN);
			
			List<LsPdSsRequisitionApprovalHierarchyHistory> lsPdSsRequisitionHistoryList = (List<LsPdSsRequisitionApprovalHierarchyHistory>) (Object) commonService
					.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
							"LsPdSsRequisitionApprovalHierarchyHistory", deptId,
							roleName, OPEN);

			String[] operationIdList = new String[lsPdCsRequisitionHistoryList
					.size()];

			for (int i = 0; i < lsPdCsRequisitionHistoryList.size(); i++) {
				operationIdList[i] = lsPdCsRequisitionHistoryList.get(i)
						.getOperationId();
			}
			
			String[] operationIdList1 = new String[lsPdSsRequisitionHistoryList
			                       				.size()];
			for (int i = 0; i < lsPdSsRequisitionHistoryList.size(); i++) {
				operationIdList1[i] = lsPdSsRequisitionHistoryList.get(i)
			           .getOperationId();
			}

			CentralStoreRequisitionMst cs = null;
			List<CentralStoreRequisitionMst> centralStoreRequisitionMstList = new ArrayList<CentralStoreRequisitionMst>();
			

			List<CentralStoreRequisitionMst> centralStoreRequisitionMstList1 = centralStoreRequisitionMstService
					.listCentralStoreRequisitionMstByOperationIds(operationIdList);
			
			List<SubStoreRequisitionMst> subStoreRequisitionMstList2 = subStoreRequisitionMstService
					.listSubStoreRequisitionMstByOperationIds(operationIdList1);

			if (centralStoreRequisitionMstList1 != null) {
				centralStoreRequisitionMstList
						.addAll(centralStoreRequisitionMstList1);
			}	
			
			// added by Taleb
			if (subStoreRequisitionMstList2 != null) {
				for (SubStoreRequisitionMst ss : subStoreRequisitionMstList2) {
					cs = new CentralStoreRequisitionMst();
					centralStoreRequisitionMstList.add(importObjToCs(ss));
				}
			}
			
			return centralStoreRequisitionMstList;
		}


	// contractor Return Slip List to ss
	@SuppressWarnings("unchecked")
	public List<SSReturnSlipMst> cnRetrunSlipListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<CnSsReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList = (List<CnSsReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CnSsReturnSlipApprovalHierarchyHistory", deptId,
						roleName, "OPEN");

		List<String> operationId = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList.size(); i++) {
			operationId.add(storeSlipApprovalHierarchyHistoryList.get(i)
					.getOperationId());
		}

		List<SSReturnSlipMst> returnSlipMstList = new ArrayList<SSReturnSlipMst>();
		if (ssReturnSlipMstService
				.listSSReturnSlipMstByOperationIdList(operationId) != null) {
			returnSlipMstList = ssReturnSlipMstService
					.listSSReturnSlipMstByOperationIdList(operationId);
		}

		return returnSlipMstList;

	}
	
	@SuppressWarnings("unchecked")
	private List<LSStoreTicketMst> lsStoreTicketTray() {
		// get login user information and login user role

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		// get which all approval hierarchy which open for login user
		List<LSStoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"LSStoreTicketApprovalHierarchyHistory", "deptId",
						deptId, "actRoleName", roleName, "status", OPEN);

		// get all operation id which open for this login user
		List<String> operationId = new ArrayList<String>();
		for (LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationId.add(storeTicketApprovalHierarchyHistory
					.getOperationId());
		}

		// get All store ticket which open for the login user
		List<LSStoreTicketMst> csStoreTicketMstList = (List<LSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LSStoreTicketMst",
						"operationId", operationId);

		return csStoreTicketMstList;
	}

	@SuppressWarnings("unchecked")
	private List<CentralStoreRequisitionMst> lsItemReceivedTray() {
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = lsCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);

		List<String> stOptIdList = new ArrayList<String>();
		if (reqHierarchyOptIdList.size() > 0) {
			stOptIdList = csStoreTicketMstService
					.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
							true, true);
		}
		List<CentralStoreRequisitionMst> csApprovedRequisitions = new ArrayList<CentralStoreRequisitionMst>();

		if (stOptIdList.size() > 0) {
			/*
			 * csApprovedRequisitions = (List<CentralStoreRequisitionMst>)
			 * (Object) commonService .getObjectListByAnyColumnValueList(
			 * "CentralStoreRequisitionMst", "requisitionNo", stOptIdList);
			 */
			csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueListAndOneColumn(
							"CentralStoreRequisitionMst", "requisitionNo",
							stOptIdList, "received", "0");
		}

		return csApprovedRequisitions;
	}

	@SuppressWarnings("unchecked")
	private List<SubStoreRequisitionMst> ssItemReceivedTray() {
		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = lsSsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		
		List<String> reqHierarchyOptIdList1 = lsSsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatusLSPDSS(deptId, DONE);
		
		if(reqHierarchyOptIdList1!=null){
			reqHierarchyOptIdList.addAll(reqHierarchyOptIdList1);
		}
		
		List<SubStoreRequisitionMst> ssApprovedRequisitions = new ArrayList<SubStoreRequisitionMst>();
		if (reqHierarchyOptIdList.size() > 0) {
			List<String> stOptIdList = csStoreTicketMstService
					.getOperationIdListByDeptIdAndStatusForSS(
							reqHierarchyOptIdList, true, true);

			/*
			 * ssApprovedRequisitions = (List<SubStoreRequisitionMst>) (Object)
			 * commonService .getObjectListByAnyColumnValueList(
			 * "SubStoreRequisitionMst", "requisitionNo", stOptIdList);
			 */
			ssApprovedRequisitions = (List<SubStoreRequisitionMst>) (Object) commonService
					.getObjectListByAnyColumnValueListAndOneColumn(
							"SubStoreRequisitionMst", "requisitionNo",
							stOptIdList, "received", "0");
		}

		return ssApprovedRequisitions;
	}

	private CentralStoreRequisitionMst importObjToCs(
			SubStoreRequisitionMst ssRequisitionMst) {
		CentralStoreRequisitionMst cs = new CentralStoreRequisitionMst();
		cs.setId(ssRequisitionMst.getId());
		cs.setReceived(false);
		cs.setCreatedBy(ssRequisitionMst.getCreatedBy());
		cs.setReceivedBy(ssRequisitionMst.getReceivedBy());
		cs.setRequisitionNo(ssRequisitionMst.getRequisitionNo());
		cs.setRequisitionDate(ssRequisitionMst.getRequisitionDate());
		cs.setStoreTicketNO(ssRequisitionMst.getStoreTicketNO());
		cs.setGatePassNo(ssRequisitionMst.getGatePassNo());
		cs.setGatePassDate(ssRequisitionMst.getGatePassDate());
		cs.setDeptName(ssRequisitionMst.getDeptName());
		cs.setIdenterDesignation(ssRequisitionMst.getIdenterDesignation());
		cs.setModifiedBy(ssRequisitionMst.getModifiedBy());
		cs.setModifiedDate(ssRequisitionMst.getModifiedDate());
		cs.setRemarks(ssRequisitionMst.getRemarks());
		cs.setSenderStore(ssRequisitionMst.getSenderStore());
		cs.setRequisitionTo(ssRequisitionMst.getRequisitionTo());
		cs.setKhathId(ssRequisitionMst.getKhathId());
		cs.setUuid(ssRequisitionMst.getUuid());
		cs.setKhathName(ssRequisitionMst.getKhathName());
		return cs;
	}

	private List<ReturnSlipMst> importObjListToSs(
			List<SSReturnSlipMst> ssReturnSlipMstList) {
		List<ReturnSlipMst> returnSlipMstList = new ArrayList<ReturnSlipMst>();
		ReturnSlipMst rs = null;
		for (SSReturnSlipMst ss : ssReturnSlipMstList) {
			rs = new ReturnSlipMst();
			rs.setId(ss.getId());
			rs.setCreatedBy(ss.getCreatedBy());
			rs.setReceiveFrom(ss.getReceiveFrom());
			rs.setReturnSlipNo(ss.getReturnSlipNo());
			rs.setReturnSlipDate(ss.getReturnSlipDate());
			rs.setModifiedBy(ss.getModifiedBy());
			rs.setModifiedDate(ss.getModifiedDate());
			rs.setRemarks(ss.getRemarks());
			rs.setSenderStore(ss.getSenderStore());
			rs.setReturnTo(ss.getReturnTo());
			returnSlipMstList.add(rs);
		}
		return returnSlipMstList;
	}

	@SuppressWarnings("unchecked")
	private List<SSStoreTicketMst> ssStoreTicketListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<StoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = storeTicketApprovalHierarchyHistoryService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndTicketNo(
						deptId, roleName, OPEN, "");

		List<String> operationList = new ArrayList<String>();
		// get all operation id which open for this login user

		for (StoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationList.add(storeTicketApprovalHierarchyHistory
					.getOperationId());
		}

		List<SSStoreTicketMst> ssStoreTicketMstList = (List<SSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("SSStoreTicketMst",
						"operationId", operationList);

		return ssStoreTicketMstList;
	}

	// ssItemReceivedListForTray
	@SuppressWarnings("unchecked")
	private List<CentralStoreRequisitionMst> ssItemReceivedListForTray() {

		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> reqHierarchyOptIdList = ssCsRequisitionApprovalHierarchyHistoryService
				.getOperationIdListByDeptIdAndStatus(deptId, DONE);
		List<String> stOptIdList = csStoreTicketMstService
				.getOperationIdListByDeptIdAndStatus(reqHierarchyOptIdList,
						true, true);

		List<CentralStoreRequisitionMst> csApprovedRequisitions = (List<CentralStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueListAndOneColumn(
						"CentralStoreRequisitionMst", "requisitionNo",
						stOptIdList, "received", "0");

		return csApprovedRequisitions;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LSReturnSlipMst> contRetrunSlipListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		String deptId = authUser.getDeptId();

		List<CN2LSReturnSlipApprovalHierarchyHistory> storeSlipApprovalHierarchyHistoryList1 = (List<CN2LSReturnSlipApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"CN2LSReturnSlipApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationId1 = new ArrayList<String>();
		for (int i = 0; i < storeSlipApprovalHierarchyHistoryList1.size(); i++) {
			operationId1.add(storeSlipApprovalHierarchyHistoryList1.get(i)
					.getOperationId());
		}

		List<LSReturnSlipMst> returnSlipMstList = new ArrayList<LSReturnSlipMst>();

		if (returnSlipMstService
				.listReturnSlipMstByOperationIdList(operationId1) != null) {
			returnSlipMstList = (List<LSReturnSlipMst>) (Object) commonService
					.getObjectListByAnyColumnValueList("LSReturnSlipMst",
							"returnSlipNo", operationId1);

		}
		if (returnSlipMstList != null) {
			for (LSReturnSlipMst rsMst : returnSlipMstList) {
				Departments dept = (Departments) commonService
						.getAnObjectByAnyUniqueColumn("Departments", "deptId",
								rsMst.getReturnTo());
				rsMst.setReturnTo(dept.getDeptName());
			}
		}

		return returnSlipMstList;

	}
	
	@SuppressWarnings("unchecked")
	public List<LocalStoreRequisitionMst> contReqisitionListForTray() {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		List<C2LSRequisitionApprovalHierarchyHistory> lsCsRequisitionHistoryList = (List<C2LSRequisitionApprovalHierarchyHistory>) (Object) commonService
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
						"C2LSRequisitionApprovalHierarchyHistory", deptId,
						roleName, OPEN);

		List<String> operationIds = new ArrayList<String>();

		for (int i = 0; i < lsCsRequisitionHistoryList.size(); i++) {
			operationIds
					.add(lsCsRequisitionHistoryList.get(i).getOperationId());
		}
		List<LocalStoreRequisitionMst> lsRequisitionMstList = (List<LocalStoreRequisitionMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LocalStoreRequisitionMst",
						"requisitionNo", operationIds);
		
		return lsRequisitionMstList;
	}

}
