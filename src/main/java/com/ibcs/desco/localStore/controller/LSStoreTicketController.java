package com.ibcs.desco.localStore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.DescoKhath;
import com.ibcs.desco.common.model.LSStoreTicketApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.inventory.service.ItemGroupService;
import com.ibcs.desco.inventory.service.ItemInventoryService;
import com.ibcs.desco.localStore.been.LSStoreTicketMstDtl;
import com.ibcs.desco.localStore.model.LSGatePassDtl;
import com.ibcs.desco.localStore.model.LSGatePassMst;
import com.ibcs.desco.localStore.model.LSItemTransactionDtl;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;
import com.ibcs.desco.localStore.model.LSReturnSlipDtl;
import com.ibcs.desco.localStore.model.LSStoreTicketDtl;
import com.ibcs.desco.localStore.model.LSStoreTicketMst;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionDtl;
import com.ibcs.desco.localStore.model.LocalStoreRequisitionMst;

@Controller
@PropertySource("classpath:common.properties")
public class LSStoreTicketController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	ItemGroupService itemGroupService;

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CommonService commonService;

	@Value("${localStore.ssStoreTicketNo.prefix}")
	private String lsSsStoreTicketNoPrefix;

	@Value("${desco.gate.pass.prefix}")
	private String descoGatePassNoPrefix;

	@Value("${project.separator}")
	private String separator;

	Date now = new Date();

	// store ticket and show that
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "c2ls/openStoreTicket.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLSStoreTicketCreateOpen(
			LSStoreTicketMst lsStoreTicketMst) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Map<String, Object> model = new HashMap<String, Object>();

		// first time flag value is 0.. so if 0 than user should be generate a
		// store ticket against operation id
		// so first condition is true (1). if one user will only show the store
		// ticket
		if (lsStoreTicketMst.isFlag()) {
			// target : show the store ticket
			String currentStatus = "";

			String operationId = lsStoreTicketMst.getOperationId();

			// get all hierarchy history against current operation id and status
			// done
			List<LSStoreTicketApprovalHierarchyHistory> storeTicketApproveHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"LSStoreTicketApprovalHierarchyHistory",
							"operationName", LS_STORE_TICKET, "operationId",
							operationId, "status", DONE);

			// get all approval hierarchy against store_ticket operation
			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationName(LS_STORE_TICKET);

			// set the button name or value
			String buttonValue = "";
			if (!storeTicketApproveHistoryList.isEmpty()
					&& storeTicketApproveHistoryList != null) {

				int stateCode = storeTicketApproveHistoryList.get(
						storeTicketApproveHistoryList.size() - 1)
						.getStateCode();

				int nextStateCode = 0;
				for (int i = 0; i < approveHeirchyList.size(); i++) {
					if (approveHeirchyList.get(i).getStateCode() > stateCode) {
						nextStateCode = approveHeirchyList.get(i)
								.getStateCode();
						break;
					}
				}
				if (nextStateCode > 0) {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_STORE_TICKET, nextStateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				} else {
					ApprovalHierarchy approveHeirchy = approvalHierarchyService
							.getApprovalHierarchyByOperationNameAndSateCode(
									LS_STORE_TICKET, stateCode + "");
					buttonValue = approveHeirchy.getButtonName();
				}

			} else {
				buttonValue = approveHeirchyList.get(0).getButtonName();
			}

			// set current status
			if (!storeTicketApproveHistoryList.isEmpty()) {
				currentStatus = storeTicketApproveHistoryList.get(
						storeTicketApproveHistoryList.size() - 1)
						.getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// current ticket no
			String ticketNo = lsStoreTicketMst.getTicketNo();

			// get all store ticket master info against current ticket no
			LSStoreTicketMst csStoreTicketMstdb = (LSStoreTicketMst) commonService
					.getAnObjectByAnyUniqueColumn("LSStoreTicketMst",
							"ticketNo", ticketNo);

			// get all store ticket item details info against current ticket no
			List<LSStoreTicketDtl> lsStoreTicketDtlList = (List<LSStoreTicketDtl>) (Object) commonService
					.getObjectListByAnyColumn("LSStoreTicketDtl", "ticketNo",
							ticketNo);

			// sent to browser
			model.put("storeTicketApproveHistoryList",
					storeTicketApproveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("csStoreTicketMst", csStoreTicketMstdb);
			model.put("lsStoreTicketDtlList", lsStoreTicketDtlList);
			model.put("buttonValue", buttonValue);

			model.put("deptName", department.getDeptName());
			model.put("deptAddress", department.getAddress() + ", "
					+ department.getContactNo());

			if (lsStoreTicketMst.getStoreTicketType()
					.equals(CN2_LS_RETURN_SLIP)) {
				return new ModelAndView("localStore/lsStoreTicketShowRS", model);
			} else if (lsStoreTicketMst.getStoreTicketType().equals(
					LS_ISSUED_REQUISITION)) {
				return new ModelAndView(
						"localStore/lsStoreTicketShowRequisition", model);
			} else {
				return null;
			}

		} else {
			// target : for generate a store ticket now will open a form of
			// store ticket with some value
			// store ticket master (common) information
			LSStoreTicketMst lsStoreTicketMstdb = (LSStoreTicketMst) commonService
					.getAnObjectByAnyUniqueColumn("LSStoreTicketMst",
							"ticketNo", lsStoreTicketMst.getTicketNo());

			// Store ticket Items information
			if (lsStoreTicketMst.getStoreTicketType()
					.equals(CN2_LS_RETURN_SLIP)) {
				List<LSReturnSlipDtl> csReturnSlipDtlList = (List<LSReturnSlipDtl>) (Object) commonService
						.getObjectListByAnyColumn("LSReturnSlipDtl",
								"returnSlipNo",
								lsStoreTicketMst.getOperationId());

				AuthUser returnUser = (AuthUser) commonService
						.getAnObjectByAnyUniqueColumn(
								"com.ibcs.desco.admin.model.AuthUser",
								"userid", lsStoreTicketMstdb.getReturnFor());

				if (returnUser != null) {
					lsStoreTicketMstdb.setReturnFor(returnUser.getName());
				}

				model.put("csStoreTicketMst", lsStoreTicketMstdb);
				model.put("csReturnSlipDtlList", csReturnSlipDtlList);
				model.put("deptName", department.getDeptName());
				model.put("deptAddress", department.getAddress() + ", "
						+ department.getContactNo());
				return new ModelAndView("localStore/lsStoreTicketFormRS", model);
			} else if (lsStoreTicketMst.getStoreTicketType().equals(
					LS_ISSUED_REQUISITION)) {
				List<LocalStoreRequisitionDtl> csRequisitionDtlList = (List<LocalStoreRequisitionDtl>) (Object) commonService
						.getObjectListByAnyColumn("LocalStoreRequisitionDtl",
								"requisitionNo",
								lsStoreTicketMst.getOperationId());
				model.put("csStoreTicketMst", lsStoreTicketMstdb);
				model.put("csRequisitionDtlList", csRequisitionDtlList);
				model.put("deptName", department.getDeptName());
				model.put("deptAddress", department.getAddress() + ", "
						+ department.getContactNo());

				return new ModelAndView(
						"localStore/lsStoreTicketFormRequisition", model);
			}

			return null;
		}
	}

	// get store ticket List filter on login user and login user role
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "c2ls/storeTicketlist.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLSStoreTicketList() {
		Map<String, Object> model = new HashMap<String, Object>();

		// get login user information and login user role

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// get which all approval hierarchy which open for login user

		List<LSStoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByThreeColumn(
						"LSStoreTicketApprovalHierarchyHistory", "deptId",
						deptId, "actRoleName", roleName, "status", OPEN);

		// get all operation id which open for this login user
		List<String> operationId = new ArrayList<String>();
		// String[] operationId = new
		// String[storeTicketApprovalHierarchyHistoryList.size()];
		// int x = 0;
		for (LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			// operationId[x] =
			// storeTicketApprovalHierarchyHistory.getOperationId();
			operationId.add(storeTicketApprovalHierarchyHistory
					.getOperationId());
			// x++;
		}

		// get All store ticket which open for the login user
		List<LSStoreTicketMst> csStoreTicketMstList = (List<LSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LSStoreTicketMst",
						"operationId", operationId);

		model.put("csStoreTicketMstList", csStoreTicketMstList);

		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());
		return new ModelAndView("localStore/lsStoreTicketList", model);
	}

	// get store ticket Search on login user and login user role
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "c2ls/storeTicketSearch.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getLSStoreTicketSearch(LSStoreTicketMst csStoreTicketMst) {
		Map<String, Object> model = new HashMap<String, Object>();

		String operationId = csStoreTicketMst.getTicketNo();

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		// get which all approval hierarchy which open for login user
		List<LSStoreTicketApprovalHierarchyHistory> storeTicketApprovalHierarchyHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByFourColumn(
						"LSStoreTicketApprovalHierarchyHistory", "deptId",
						deptId, "actRoleName", roleName, "status", OPEN,
						"operationId", operationId);

		// get all operation id which open for this login user
		List<String> operationIdList = new ArrayList<String>();
		// String[] operationIdList = new
		// String[storeTicketApprovalHierarchyHistoryList.size()];
		// int x = 0;
		for (LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory : storeTicketApprovalHierarchyHistoryList) {
			operationIdList.add(storeTicketApprovalHierarchyHistory
					.getOperationId());
			// x++;
		}

		// get All store ticket which open for the login user
		List<LSStoreTicketMst> csStoreTicketMstList = (List<LSStoreTicketMst>) (Object) commonService
				.getObjectListByAnyColumnValueList("LSStoreTicketMst",
						"operationId", operationIdList);

		model.put("csStoreTicketMstList", csStoreTicketMstList);
		model.put("deptName", department.getDeptName());
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("localStore/lsStoreTicketList", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/c2ls/saveStoreTicket.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getLSStoreTicketCreateSave(
			LSStoreTicketMstDtl lsStoreTicketMstDtl) {

		// String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		// AuthUser authUser = userService.getAuthUserByUserId(userName);
		// String deptId = authUser.getDeptId();

		List<String> itemIdList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		List<String> ledgerBookNoList = null;

		List<String> ledgerPageNoList = null;

		// List<Double> quantityList = null;

		List<Double> quantityNSList = null;

		List<Double> quantityRSList = null;

		List<Double> quantityUSList = null;

		List<String> remarksList = null;

		List<String> ledgerNameList = null;

		Date now = new Date();

		LSStoreTicketMst lsStoreTicketMst = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst", "id",
						lsStoreTicketMstDtl.getId() + "");

		lsStoreTicketMst.setCreatedBy(userName);
		lsStoreTicketMst.setCreatedDate(now);
		lsStoreTicketMst.setActive(true);
		lsStoreTicketMst.setFlag(true);
		lsStoreTicketMst.setApproved(false);
		lsStoreTicketMst.setTicketDate(now);
		commonService.saveOrUpdateModelObjectToDB(lsStoreTicketMst);

		if (lsStoreTicketMstDtl.getItemId() != null) {
			itemIdList = lsStoreTicketMstDtl.getItemId();
		}

		if (lsStoreTicketMstDtl.getDescription() != null) {
			descriptionList = lsStoreTicketMstDtl.getDescription();
		}

		if (lsStoreTicketMstDtl.getUom() != null) {
			uomList = lsStoreTicketMstDtl.getUom();
		}

		if (lsStoreTicketMstDtl.getLedgerName() != null) {
			ledgerNameList = lsStoreTicketMstDtl.getLedgerName();
		}
		if (lsStoreTicketMstDtl.getRemarks() != null) {
			remarksList = lsStoreTicketMstDtl.getRemarks();
		}

		/*
		 * if (lsStoreTicketMstDtl.getQuantity() != null) { quantityList =
		 * lsStoreTicketMstDtl.getQuantity(); }
		 */

		if (lsStoreTicketMstDtl.getQtyNewServiceable() != null) {
			quantityNSList = lsStoreTicketMstDtl.getQtyNewServiceable();
		}

		if (lsStoreTicketMstDtl.getQtyRecServiceable() != null) {
			quantityRSList = lsStoreTicketMstDtl.getQtyRecServiceable();
		}

		if (lsStoreTicketMstDtl.getQtyUnServiceable() != null) {
			quantityUSList = lsStoreTicketMstDtl.getQtyUnServiceable();
		}

		if (lsStoreTicketMstDtl.getLedgerBookNo() != null) {
			ledgerBookNoList = lsStoreTicketMstDtl.getLedgerBookNo();
		}

		if (lsStoreTicketMstDtl.getLedgerPageNo() != null) {
			ledgerPageNoList = lsStoreTicketMstDtl.getLedgerPageNo();
		}

		if (lsStoreTicketMstDtl.getLedgerName() != null) {
			ledgerNameList = lsStoreTicketMstDtl.getLedgerName();
		}

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				LSStoreTicketDtl lsStoreTicketDtl = new LSStoreTicketDtl();
				lsStoreTicketDtl.setActive(true);
				lsStoreTicketDtl.setCreatedBy(userName);
				lsStoreTicketDtl.setCreatedDate(now);
				lsStoreTicketDtl.setTicketNo(lsStoreTicketMstDtl.getTicketNo());
				lsStoreTicketDtl.setId(null);
				lsStoreTicketDtl.setLsStoreTicketMst(lsStoreTicketMst);

				if (itemIdList != null) {
					lsStoreTicketDtl.setItemId(itemIdList.get(i));
				}

				if (descriptionList.size() > 0 || descriptionList != null) {
					lsStoreTicketDtl.setDescription(descriptionList.get(i));
				} else {
					lsStoreTicketDtl.setDescription("");
				}

				if (uomList.size() > 0 || uomList != null) {
					lsStoreTicketDtl.setUom(uomList.get(i));
				} else {
					lsStoreTicketDtl.setUom("");
				}

				/*
				 * if (ledgerNameList != null) {
				 * lsStoreTicketDtl.setLedgerName(ledgerNameList.get(i)); } else
				 * { lsStoreTicketDtl.setLedgerName(""); }
				 */
				if (remarksList.size() > 0) {
					lsStoreTicketDtl.setRemarks(remarksList.get(i));
				} else {
					lsStoreTicketDtl.setRemarks("");
				}

				/*
				 * if (quantityList.size() > 0 || quantityList != null) {
				 * lsStoreTicketDtl.setQuantity(quantityList.get(i)); } else {
				 * lsStoreTicketDtl.setQuantity(0.0); }
				 */

				if (ledgerBookNoList.size() > 0) {
					lsStoreTicketDtl.setLedgerBookNo(ledgerBookNoList.get(i));
				} else {
					lsStoreTicketDtl.setLedgerBookNo("");
				}

				if (ledgerPageNoList.size() > 0) {
					lsStoreTicketDtl.setLedgerPageNo(ledgerPageNoList.get(i));
				} else {
					lsStoreTicketDtl.setLedgerPageNo("");
				}

				if (ledgerNameList.size() > 0) {
					lsStoreTicketDtl.setLedgerName(ledgerNameList.get(i));
				} else {
					lsStoreTicketDtl.setLedgerName("");
				}

				lsStoreTicketDtl.setLsStoreTicketMst(lsStoreTicketMst);

				if (quantityNSList.size() > 0 || quantityNSList != null) {
					if (quantityNSList.get(i) > 0) {
						lsStoreTicketDtl.setQuantity(quantityNSList.get(i));
						lsStoreTicketDtl.setId(null);
						lsStoreTicketDtl.setLedgerName(NEW_SERVICEABLE);

						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
					}
				}

				if (quantityRSList.size() > 0 || quantityRSList != null) {
					if (quantityRSList.get(i) > 0) {
						lsStoreTicketDtl.setQuantity(quantityRSList.get(i));
						lsStoreTicketDtl.setId(null);
						lsStoreTicketDtl.setLedgerName(RECOVERY_SERVICEABLE);
						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
					}
				}

				if (lsStoreTicketMst.getStoreTicketType().equals(
						CN2_LS_RETURN_SLIP)) {
					if (quantityUSList.size() > 0 || quantityUSList != null) {
						if (quantityUSList.get(i) > 0) {
							lsStoreTicketDtl.setQuantity(quantityUSList.get(i));
							lsStoreTicketDtl.setId(null);
							lsStoreTicketDtl.setLedgerName(UNSERVICEABLE);
							commonService
									.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
						}
					}
				}

				// commonService.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
			}
		}

		if (lsStoreTicketMst.getStoreTicketType().equals(LS_ISSUED_REQUISITION)) {
			return "redirect:/c2ls/openStoreTicket.do?operationId="
					+ lsStoreTicketMstDtl.getOperationId() + "&flag=" + true
					+ "&storeTicketType=" + LS_ISSUED_REQUISITION
					+ "&ticketNo=" + lsStoreTicketMstDtl.getTicketNo();
		} else if (lsStoreTicketMst.getStoreTicketType().equals(
				CN2_LS_RETURN_SLIP)) {
			return "redirect:/c2ls/openStoreTicket.do?operationId="
					+ lsStoreTicketMstDtl.getOperationId() + "&flag=" + true
					+ "&storeTicketType=" + CN2_LS_RETURN_SLIP + "&ticketNo="
					+ lsStoreTicketMstDtl.getTicketNo();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/c2ls/saveStoreTicketForCSReturnSlip.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String getLSStoreTicketCreateSaveForCSReturnSlip(
			LSStoreTicketMstDtl lsStoreTicketMstDtl) {

		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();

		List<String> itemIdList = null;

		List<String> descriptionList = null;

		List<String> uomList = null;

		// List<Double> costList = null;

		List<String> ledgerBookNoList = null;

		List<String> ledgerPageNoList = null;

		// List<Double> quantityList = null;

		List<String> remarksList = null;

		// List<String> ledgerNameList = null;

		// List<Integer> locationIdList = null;

		List<Double> qtyNewServiceableList = null;

		List<Double> qtyRecServiceableList = null;

		List<Double> qtyUnServiceableList = null;

		Date now = new Date();

		LSStoreTicketMst lsStoreTicketMst = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst", "id",
						lsStoreTicketMstDtl.getId() + "");
		lsStoreTicketMst.setCreatedBy(authUser.getName());
		lsStoreTicketMst.setCreatedDate(now);
		lsStoreTicketMst.setActive(true);
		lsStoreTicketMst.setFlag(true);
		lsStoreTicketMst.setApproved(false);
		lsStoreTicketMst.setTicketDate(now);
		lsStoreTicketMst.setReceivedBy(authUser.getName());
		lsStoreTicketMst.setReturnFor(lsStoreTicketMstDtl.getReturnFor());
		commonService.saveOrUpdateModelObjectToDB(lsStoreTicketMst);

		if (lsStoreTicketMstDtl.getItemId() != null) {
			itemIdList = lsStoreTicketMstDtl.getItemId();
		}

		if (lsStoreTicketMstDtl.getDescription() != null) {
			descriptionList = lsStoreTicketMstDtl.getDescription();
		}

		if (lsStoreTicketMstDtl.getUom() != null) {
			uomList = lsStoreTicketMstDtl.getUom();
		}

		/*
		 * if (csStoreTicketMstDtl.getCost() != null) { costList =
		 * csStoreTicketMstDtl.getCost(); }
		 */
		/*
		 * if (csStoreTicketMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csStoreTicketMstDtl.getLedgerName(); }
		 */
		if (lsStoreTicketMstDtl.getRemarks() != null) {
			remarksList = lsStoreTicketMstDtl.getRemarks();
		}

		/*
		 * if (csStoreTicketMstDtl.getQuantity() != null) { quantityList =
		 * csStoreTicketMstDtl.getQuantity(); }
		 */

		if (lsStoreTicketMstDtl.getLedgerBookNo() != null) {
			ledgerBookNoList = lsStoreTicketMstDtl.getLedgerBookNo();
		}

		if (lsStoreTicketMstDtl.getLedgerPageNo() != null) {
			ledgerPageNoList = lsStoreTicketMstDtl.getLedgerPageNo();
		}

		/*
		 * if (csStoreTicketMstDtl.getLedgerName() != null) { ledgerNameList =
		 * csStoreTicketMstDtl.getLedgerName(); }
		 */

		if (lsStoreTicketMstDtl.getQtyNewServiceable() != null) {
			qtyNewServiceableList = lsStoreTicketMstDtl.getQtyNewServiceable();
		}

		if (lsStoreTicketMstDtl.getQtyRecServiceable() != null) {
			qtyRecServiceableList = lsStoreTicketMstDtl.getQtyRecServiceable();
		}

		if (lsStoreTicketMstDtl.getQtyUnServiceable() != null) {
			qtyUnServiceableList = lsStoreTicketMstDtl.getQtyUnServiceable();
		}

		if (itemIdList != null) {
			for (int i = 0; i < itemIdList.size(); i++) {
				LSStoreTicketDtl lsStoreTicketDtl = new LSStoreTicketDtl();
				lsStoreTicketDtl.setActive(true);
				lsStoreTicketDtl.setCreatedBy(userName);
				lsStoreTicketDtl.setCreatedDate(now);
				lsStoreTicketDtl.setTicketNo(lsStoreTicketMstDtl.getTicketNo());
				lsStoreTicketDtl.setId(null);
				lsStoreTicketDtl.setLsStoreTicketMst(lsStoreTicketMst);

				if (itemIdList != null) {
					lsStoreTicketDtl.setItemId(itemIdList.get(i));
				}

				if (descriptionList.size() > 0 || descriptionList != null) {
					lsStoreTicketDtl.setDescription(descriptionList.get(i));
				} else {
					lsStoreTicketDtl.setDescription("");
				}

				if (uomList.size() > 0 || uomList != null) {
					lsStoreTicketDtl.setUom(uomList.get(i));
				} else {
					lsStoreTicketDtl.setUom("");
				}

				/*
				 * if (costList != null) {
				 * csStoreTicketDtl.setCost(costList.get(i)); } else {
				 * csStoreTicketDtl.setCost(0.0); }
				 */
				/*
				 * if (ledgerNameList != null) {
				 * csStoreTicketDtl.setLedgerName(ledgerNameList.get(i)); } else
				 * { csStoreTicketDtl.setCost(0.0); }
				 */
				if (remarksList.size() > 0) {
					lsStoreTicketDtl.setRemarks(remarksList.get(i));
				} else {
					lsStoreTicketDtl.setRemarks("");
				}

				/*
				 * if (quantityList.size() > 0 || quantityList != null) {
				 * csStoreTicketDtl.setQuantity(quantityList.get(i)); } else {
				 * csStoreTicketDtl.setQuantity(0.0); }
				 */

				if (ledgerBookNoList.size() > 0) {
					lsStoreTicketDtl.setLedgerBookNo(ledgerBookNoList.get(i));
				} else {
					lsStoreTicketDtl.setLedgerBookNo("");
				}

				if (ledgerPageNoList.size() > 0) {
					lsStoreTicketDtl.setLedgerPageNo(ledgerPageNoList.get(i));
				} else {
					lsStoreTicketDtl.setLedgerPageNo("");
				}

				/*
				 * if (ledgerNameList.size() > 0) {
				 * csStoreTicketDtl.setLedgerName(ledgerNameList.get(i)); } else
				 * { csStoreTicketDtl.setLedgerName(""); }
				 */

				if (qtyNewServiceableList.size() > 0
						|| qtyNewServiceableList != null) {
					if (qtyNewServiceableList.get(i) > 0) {
						lsStoreTicketDtl.setId(null);
						lsStoreTicketDtl.setQuantity(qtyNewServiceableList
								.get(i));
						lsStoreTicketDtl.setLedgerName(NEW_SERVICEABLE);
						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
					}
				}
				if (qtyRecServiceableList.size() > 0
						|| qtyRecServiceableList != null) {
					if (qtyRecServiceableList.get(i) > 0) {
						lsStoreTicketDtl.setId(null);
						lsStoreTicketDtl.setQuantity(qtyRecServiceableList
								.get(i));
						lsStoreTicketDtl.setLedgerName(RECOVERY_SERVICEABLE);
						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
					}
				}
				if (qtyUnServiceableList.size() > 0
						|| qtyUnServiceableList != null) {
					if (qtyUnServiceableList.get(i) > 0) {
						lsStoreTicketDtl.setId(null);
						lsStoreTicketDtl.setQuantity(qtyUnServiceableList
								.get(i));
						lsStoreTicketDtl.setLedgerName(UNSERVICEABLE);
						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketDtl);
					}
				}

				// csStoreTicketDtlService.addCSStoreTicketDtl(csStoreTicketDtl);
			}
		}

		// return "redirect:/cs/itemRecieved/storeTicketlist.do";

		if (lsStoreTicketMst.getStoreTicketType().equals(LS_ISSUED_REQUISITION)) {
			return "redirect:/c2ls/openStoreTicket.do?operationId="
					+ lsStoreTicketMstDtl.getOperationId() + "&flag=" + true
					+ "&storeTicketType=" + LS_CS_REQUISITION + "&ticketNo="
					+ lsStoreTicketMstDtl.getTicketNo();
		} else if (lsStoreTicketMst.getStoreTicketType().equals(
				CN2_LS_RETURN_SLIP)) {
			return "redirect:/c2ls/openStoreTicket.do?operationId="
					+ lsStoreTicketMstDtl.getOperationId() + "&flag=" + true
					+ "&storeTicketType=" + CN2_LS_RETURN_SLIP + "&ticketNo="
					+ lsStoreTicketMstDtl.getTicketNo();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/c2ls/storeTicketSubmitApproved.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView LSStoreTicketSubmitApproved(
			@ModelAttribute("csStoreTicketMstDtl") LSStoreTicketMstDtl lsStoreTicketMstDtl,
			RedirectAttributes redirectAttributes) {

		// get Current User and Role Information
		Map<String, Object> model = new HashMap<String, Object>();
		String roleName = commonService.getAuthRoleName();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		String deptId = authUser.getDeptId();
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());
		//
		String ticketNo = lsStoreTicketMstDtl.getTicketNo();
		LSStoreTicketMst lsStoreTicketMst = (LSStoreTicketMst) commonService
				.getAnObjectByAnyUniqueColumn("LSStoreTicketMst", "ticketNo",
						ticketNo);

		List<LSStoreTicketDtl> lsStoreTicketDtlList = (List<LSStoreTicketDtl>) (Object) commonService
				.getObjectListByAnyColumn("LSStoreTicketDtl", "ticketNo",
						ticketNo);

		// get All State Codes from Approval Hierarchy and sort Dececding oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(LS_STORE_TICKET);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get Current State Code from Approval hierarchy by RR No (Operation
		// Id)

		String operationId = lsStoreTicketMstDtl.getOperationId();
		List<LSStoreTicketApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<LSStoreTicketApprovalHierarchyHistory>) (Object) commonService
				.getObjectListByAnyColumn(
						"LSStoreTicketApprovalHierarchyHistory", "operationId",
						operationId);

		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		LSStoreTicketApprovalHierarchyHistory storeTicketApprovalHierarchyHistory = (LSStoreTicketApprovalHierarchyHistory) commonService
				.getAnObjectByAnyUniqueColumn(
						"LSStoreTicketApprovalHierarchyHistory", "id", ids[0]
								+ "");

		int currentStateCode = storeTicketApprovalHierarchyHistory
				.getStateCode();

		int nextStateCode = 0;

		for (int state : stateCodes) {
			if (state > currentStateCode) {
				nextStateCode = state;
				/*
				 * ApprovalHierarchy approvalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * CS_RECEIVING_ITEMS, nextStateCode + "");
				 */
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								lsStoreTicketMstDtl.getStoreTicketType(),
								nextStateCode + "");

				storeTicketApprovalHierarchyHistory.setStatus(OPEN);
				storeTicketApprovalHierarchyHistory.setStateCode(nextStateCode);
				storeTicketApprovalHierarchyHistory
						.setStateName(approvalHierarchy.getStateName());
				storeTicketApprovalHierarchyHistory.setId(null);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setDeptId(deptId);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory
						.setActRoleName(approvalHierarchy.getRoleName());
				storeTicketApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());
				storeTicketApprovalHierarchyHistory.setTicketNo(ticketNo);
				storeTicketApprovalHierarchyHistory.setcEmpId(authUser
						.getEmpId());
				storeTicketApprovalHierarchyHistory.setcEmpFullName(authUser
						.getName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());

				commonService
						.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);
				break;
			}

			if (state == currentStateCode) {
				/*
				 * ApprovalHierarchy approvalHierarchy =
				 * approvalHierarchyService
				 * .getApprovalHierarchyByOperationNameAndSateCode(
				 * CS_RECEIVING_ITEMS, state + "");
				 */
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								lsStoreTicketMst.getStoreTicketType(), state
										+ "");

				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory.setModifiedBy(userName);
				storeTicketApprovalHierarchyHistory.setModifiedDate(new Date());

				storeTicketApprovalHierarchyHistory.setTicketNo(ticketNo);

				storeTicketApprovalHierarchyHistory
						.setApprovalHeader(approvalHierarchy
								.getApprovalHeader());
				storeTicketApprovalHierarchyHistory.setcEmpId(authUser
						.getEmpId());
				storeTicketApprovalHierarchyHistory.setcEmpFullName(authUser
						.getName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				commonService
						.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);
			}

			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				storeTicketApprovalHierarchyHistory.setStatus(DONE);
				storeTicketApprovalHierarchyHistory.setCreatedBy(userName);
				storeTicketApprovalHierarchyHistory.setCreatedDate(new Date());
				storeTicketApprovalHierarchyHistory.setModifiedBy(userName);
				storeTicketApprovalHierarchyHistory.setModifiedDate(new Date());
				storeTicketApprovalHierarchyHistory.setcEmpId(authUser
						.getEmpId());
				storeTicketApprovalHierarchyHistory.setcEmpFullName(authUser
						.getName());
				storeTicketApprovalHierarchyHistory.setcDesignation(authUser
						.getDesignation());
				commonService
						.saveOrUpdateModelObjectToDB(storeTicketApprovalHierarchyHistory);
				// new ledger update Ledger Mst
				this.lsLedgerLocation(lsStoreTicketDtlList, userName, roleName,
						authUser, department, lsStoreTicketMst);
				// Ledger Update

				// send LocalStore for Received item
				lsStoreTicketMst.setApproved(true);
				commonService.saveOrUpdateModelObjectToDB(lsStoreTicketMst);

				// open the gate pass for Item issued against Requisition
				/*
				 * if (lsStoreTicketMst.getStoreTicketType().equals(
				 * LS_ISSUED_REQUISITION)) {
				 * this.lsGatePassGenerate(operationId, ticketNo, department,
				 * lsStoreTicketMst, lsStoreTicketDtlList); }
				 */

				// Instant Store Ticket Report Call
				model.put("ticketNo", ticketNo);

				if (lsStoreTicketMst.getStoreTicketType().equals(
						CN2_LS_RETURN_SLIP)) {

					// return new
					// ModelAndView("localStore/lsStoreTicketReportRS", model);
					return new ModelAndView(
							"redirect:/c2ls/viewStoreTicketRS.do?ticketNo="
									+ ticketNo);

				} else if (lsStoreTicketMst.getStoreTicketType().equals(
						LS_ISSUED_REQUISITION)) {
					List<String> gatePassNoList = this.lsGatePassNoList(
							authUser, operationId, department,
							lsStoreTicketMst, lsStoreTicketDtlList);

					// update lsStoreTicketMst by GP no
					if (!gatePassNoList.isEmpty() && gatePassNoList.size() > 0) {
						lsStoreTicketMst.setGatePass(gatePassNoList.get(0));
						commonService
								.saveOrUpdateModelObjectToDB(lsStoreTicketMst);
					}

					model.put("gatePassNoList", gatePassNoList);

					// return new
					// ModelAndView("localStore/lsStoreTicketReportRequisition",
					// model);
					/*
					 * redirectAttributes.addFlashAttribute("ticketNo",
					 * ticketNo);
					 * redirectAttributes.addFlashAttribute("gatePassNoList",
					 * gatePassNoList);
					 */
					return new ModelAndView(
							"redirect:/c2ls/viewStoreTicket.do?ticketNo="
									+ ticketNo);
				} else {
					return null;
				}
			}
		}

		return new ModelAndView("redirect:/c2ls/storeTicketlist.do");
	}

	// Added by Ashid
	@RequestMapping(value = "/c2ls/viewStoreTicketRS.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewStoreTicketRS(
			@ModelAttribute("ticketNo") String ticketNo) {
		// ticketNo=ST1320160900000003&operationId=RS9920160900000001
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("ticketNo", ticketNo);

		return new ModelAndView("localStore/lsStoreTicketReportRS", model);
	}

	// @Taleb

	@RequestMapping(value = "/c2ls/viewStoreTicket.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView viewStoreTicket(
			@ModelAttribute("ticketNo") String ticketNo) {

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("ticketNo", ticketNo);

		return new ModelAndView("localStore/lsStoreTicketReportRequisition",
				model);
	}

	private List<String> lsGatePassNoList(AuthUser authUser,
			String operationId, Departments department,
			LSStoreTicketMst lsStoreTicketMst,
			List<LSStoreTicketDtl> lsStoreTicketDtlList) {
		Date now = new Date();
		List<String> gatePassNoList = new ArrayList<String>();
		String descoDeptCode = department.getDescoCode();
		String gatePassNo = commonService
				.getOperationIdByPrefixAndSequenceName(descoGatePassNoPrefix,
						descoDeptCode, separator, "LS_GATE_PASS_SEQ");

		LSGatePassMst lsGatePassMst = new LSGatePassMst(null, gatePassNo, now,
				lsStoreTicketMst.getTicketNo(), operationId,
				lsStoreTicketMst.getWorkOrderNo(),
				lsStoreTicketMst.getIssuedFor(), authUser.getName(),
				lsStoreTicketMst.getIssuedTo(),
				lsStoreTicketMst.getReceivedBy(),
				lsStoreTicketMst.getReceivedBy(), true, null, true,
				authUser.getName(), now);
		commonService.saveOrUpdateModelObjectToDB(lsGatePassMst);

		LSGatePassMst lsGatePassMstDb = (LSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("LSGatePassMst", "gatePassNo",
						gatePassNo);

		for (LSStoreTicketDtl lsStoreTicketDtl : lsStoreTicketDtlList) {
			LSGatePassDtl lsGatePassDtl = new LSGatePassDtl(null,
					lsGatePassMstDb.getGatePassNo(),
					lsStoreTicketDtl.getItemId(),
					lsStoreTicketDtl.getDescription(),
					lsStoreTicketDtl.getUom(), lsStoreTicketDtl.getQuantity(),
					lsGatePassMstDb, true, authUser.getName(), now);
			commonService.saveOrUpdateModelObjectToDB(lsGatePassDtl);
		}

		gatePassNoList.add(lsGatePassMstDb.getGatePassNo());
		return gatePassNoList;
	}

	@SuppressWarnings("unchecked")
	private void lsLedgerLocation(List<LSStoreTicketDtl> lsStoreTicketDtlList,
			String userName, String roleName, AuthUser authUser,
			Departments dept, LSStoreTicketMst lsStoreTicketMst) {

		for (LSStoreTicketDtl lsStoreTicketDtl : lsStoreTicketDtlList) {

			LSItemTransactionMst lsItemTransactionMst = null;
			LSItemTransactionMst lsItemTransactionMstdb = null;
			List<LSItemTransactionMst> lsItemTransactionMstdbList = (List<LSItemTransactionMst>) (Object) commonService
					.getObjectListByFourColumn("LSItemTransactionMst",
							"khathId", lsStoreTicketMst.getKhathId() + "",
							"ledgerName", lsStoreTicketDtl.getLedgerName(),
							"itemCode", lsStoreTicketDtl.getItemId(),
							"sndCode", dept.getDescoCode());
			if (lsItemTransactionMstdbList.size() > 0) {
				lsItemTransactionMstdb = lsItemTransactionMstdbList.get(0);
			}
			DescoKhath descoKhath = (DescoKhath) commonService
					.getAnObjectByAnyUniqueColumn("DescoKhath", "id",
							lsStoreTicketMst.getKhathId() + "");

			// Ledger Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionMstdb.setModifiedBy(userName);
				lsItemTransactionMstdb.setModifiedDate(new Date());
				if (lsStoreTicketMst.getStoreTicketType().equals(
						LS_ISSUED_REQUISITION)) {

					if (lsStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					}

				} else if (lsStoreTicketMst.getStoreTicketType().equals(
						CN2_LS_RETURN_SLIP)) {
					if (lsStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMstdb
								.setQuantity(lsItemTransactionMstdb
										.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					}
				}
				commonService
						.saveOrUpdateModelObjectToDB(lsItemTransactionMstdb);
			} else {
				lsItemTransactionMst = new LSItemTransactionMst();
				lsItemTransactionMst.setItemCode(lsStoreTicketDtl.getItemId());
				if (lsStoreTicketMst.getStoreTicketType().equals(
						LS_ISSUED_REQUISITION)) {

					if (lsStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					}
					if (lsStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										- lsStoreTicketDtl.getQuantity());
					}

				} else if (lsStoreTicketMst.getStoreTicketType().equals(
						CN2_LS_RETURN_SLIP)) {
					if (lsStoreTicketDtl.getLedgerName()
							.equals(NEW_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							RECOVERY_SERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					} else if (lsStoreTicketDtl.getLedgerName().equals(
							UNSERVICEABLE)) {
						lsItemTransactionMst
								.setQuantity(lsItemTransactionMst.getQuantity()
										+ lsStoreTicketDtl.getQuantity());
					}
				}
				lsItemTransactionMst.setKhathName(descoKhath.getKhathName());
				lsItemTransactionMst.setKhathId(descoKhath.getId());
				lsItemTransactionMst.setLedgerName(lsStoreTicketDtl
						.getLedgerName());
				lsItemTransactionMst.setId(null);
				lsItemTransactionMst.setCreatedBy(userName);
				lsItemTransactionMst.setCreatedDate(new Date());
				lsItemTransactionMst.setSndCode(dept.getDescoCode());
				commonService.saveOrUpdateModelObjectToDB(lsItemTransactionMst);
			}

			Integer maxTnxMstId = (Integer) commonService
					.getMaxValueByObjectAndColumn("LSItemTransactionMst", "id");
			LSItemTransactionMst csItemTransactionMstLastRow = (LSItemTransactionMst) commonService
					.getAnObjectByAnyUniqueColumn("LSItemTransactionMst", "id",
							maxTnxMstId + "");

			// Ledger Dtl
			LSItemTransactionDtl lsItemTransactionDtl = new LSItemTransactionDtl();
			// common Info
			lsItemTransactionDtl.setItemCode(lsStoreTicketDtl.getItemId());
			lsItemTransactionDtl.setKhathName(descoKhath.getKhathName());
			lsItemTransactionDtl.setKhathId(descoKhath.getId());
			lsItemTransactionDtl
					.setLedgerType(lsStoreTicketDtl.getLedgerName());
			lsItemTransactionDtl.setId(null);
			lsItemTransactionDtl.setCreatedBy(userName);
			lsItemTransactionDtl.setCreatedDate(new Date());
			lsItemTransactionDtl.setRemarks(lsStoreTicketMst.getRemarks());
			// transaction Related Info
			if (lsStoreTicketMst.getStoreTicketType()
					.equals(CN2_LS_RETURN_SLIP)) {
				lsItemTransactionDtl.setTnxnMode(true);
			} else {
				lsItemTransactionDtl.setTnxnMode(false);
			}
			lsItemTransactionDtl.setTransactionType(lsStoreTicketMst
					.getStoreTicketType());
			lsItemTransactionDtl.setTransactionId(lsStoreTicketMst
					.getOperationId());
			lsItemTransactionDtl.setTransactionDate(lsStoreTicketMst
					.getTicketDate());

			// relation with transaction Mst
			if (lsItemTransactionMstdb != null) {
				lsItemTransactionDtl
						.setLsItemTransactionMst(lsItemTransactionMstdb);
			} else {
				lsItemTransactionDtl
						.setLsItemTransactionMst(csItemTransactionMstLastRow);
			}

			// item related info
			lsItemTransactionDtl.setIssuedBy(lsStoreTicketMst.getIssuedBy());
			lsItemTransactionDtl.setIssuedFor(lsStoreTicketMst.getIssuedFor());
			lsItemTransactionDtl.setIssuedTo(lsStoreTicketMst.getIssuedTo());
			lsItemTransactionDtl.setReturnFor(lsStoreTicketMst.getReturnFor());
			lsItemTransactionDtl.setRetrunBy(lsStoreTicketMst.getReturnBy());
			lsItemTransactionDtl
					.setReceivedBy(lsStoreTicketMst.getReceivedBy());
			lsItemTransactionDtl.setReceivedFrom(lsStoreTicketMst
					.getReceivedFrom());

			// Ledger Quantity Info
			lsItemTransactionDtl.setQuantity(lsStoreTicketDtl.getQuantity());

			commonService.saveOrUpdateModelObjectToDB(lsItemTransactionDtl);
		}
	}

	@SuppressWarnings("unused")
	private void lsGatePassGenerate(String operationId, String ticketNo,
			Departments department, LSStoreTicketMst lsStoreTicketMst,
			List<LSStoreTicketDtl> lsStoreTicketDtlList) {
		LocalStoreRequisitionMst lsReqDb = (LocalStoreRequisitionMst) commonService
				.getAnObjectByAnyUniqueColumn("LocalStoreRequisitionMst",
						"requisitionNo", operationId);

		String userName = commonService.getAuthUserName();
		Date now = new Date();
		// code for gate pass
		LSGatePassMst lsGatePassMst = new LSGatePassMst();
		// need to generate gatepassNo
		String descoDeptCode = department.getDescoCode();
		String gatePassNo = commonService
				.getOperationIdByPrefixAndSequenceName(descoGatePassNoPrefix,
						descoDeptCode, separator, "LS_GATE_PASS_SEQ");
		lsGatePassMst.setGatePassNo(gatePassNo);

		lsGatePassMst.setIssuedTo(lsReqDb.getIdenterDesignation());
		lsGatePassMst.setIssuedBy(userName);
		lsGatePassMst.setCreatedBy(userName);
		lsGatePassMst.setCreatedDate(now);
		lsGatePassMst.setFlag(true);
		lsGatePassMst.setActive(true);
		lsGatePassMst.setGatePassDate(new Date());
		lsGatePassMst.setRequisitonNo(operationId);
		lsGatePassMst.setTicketNo(ticketNo);
		lsGatePassMst.setWorkOrderNo(lsStoreTicketMst.getWorkOrderNo());
		// Save command for Central Store Gate pass
		commonService.saveOrUpdateModelObjectToDB(lsGatePassMst);

		LSGatePassMst lsGatePassMstdb = (LSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("LSGatePassMst", "ticketNo",
						ticketNo);

		for (LSStoreTicketDtl lsStoreTicketDtl : lsStoreTicketDtlList) {
			LSGatePassDtl lsGatePassDtl = new LSGatePassDtl();
			lsGatePassDtl.setCreatedBy(userName);
			lsGatePassDtl.setCreatedDate(now);
			lsGatePassDtl.setActive(true);
			lsGatePassDtl.setDescription(lsStoreTicketDtl.getDescription());
			lsGatePassDtl.setGatePassNo(gatePassNo);
			lsGatePassDtl.setId(null);
			lsGatePassDtl.setItemCode(lsStoreTicketDtl.getItemId());
			lsGatePassDtl.setLsGatePassMst(lsGatePassMstdb);
			lsGatePassDtl.setQuantity(lsStoreTicketDtl.getQuantity());
			lsGatePassDtl.setUom(lsStoreTicketDtl.getUom());
			lsGatePassDtl.setRemarks(lsStoreTicketDtl.getRemarks());
			commonService.saveOrUpdateModelObjectToDB(lsGatePassDtl);
		}

		lsStoreTicketMst.setGatePass(lsGatePassMstdb.getGatePassNo());
		commonService.saveOrUpdateModelObjectToDB(lsStoreTicketMst);
	}

	@RequestMapping(value = "/c2ls/gatePassGenerate.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView gatePassGenerate(LSGatePassMst lsGatePassMst) {
		LSGatePassMst lsGatePassMstdb = (LSGatePassMst) commonService
				.getAnObjectByAnyUniqueColumn("LSGatePassMst", "ticketNo",
						lsGatePassMst.getTicketNo());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gatePassNo", lsGatePassMstdb.getGatePassNo());
		// model.put("deptName", department.getDeptName());
		// model.put("deptAddress",
		// department.getAddress()+", "+department.getContactNo());

		return new ModelAndView("localStore/lsGatePassReport", model);
	}

}
