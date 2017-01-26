package com.ibcs.desco.cs.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.ItemRcvApprovalHierarchyHistoryService;
import com.ibcs.desco.cs.bean.ItemReceived;
import com.ibcs.desco.cs.model.CSItemReceived;
import com.ibcs.desco.cs.service.CSItemReceivedService;
import com.ibcs.desco.cs.service.CentralStoreItemsService;
import com.ibcs.desco.inventory.service.ItemInventoryService;

@Controller
@RequestMapping(value = "/centralStore/rcvPrcess")
@PropertySource("classpath:common.properties")
public class CSReceivingProcessController {

	/*private static final Logger logger = LoggerFactory
			.getLogger(CSReceivingProcessController.class);*/

	public static final String CS_RECEIVING_ITEMS = "CS_RECEIVING_ITEMS";
	public static final String OPEN = "OPEN";
	public static final String DONE = "DONE";
	public static final String RECEIVED = "RECEIVED";

	@Autowired
	ItemInventoryService itemInventoryService;

	@Autowired
	CSItemReceivedService csItemReceivedService;

	@Autowired
	ItemRcvApprovalHierarchyHistoryService itemRcvApprovalHierarchyHistoryService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	CentralStoreItemsService centralStoreItemsService;

	@RequestMapping(value = "/itemRcvFrom.do", method = RequestMethod.GET)
	public ModelAndView getCSItemRcvFrom() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("invItemList", itemInventoryService.getInventoryItemList());
		return new ModelAndView("centralStore/csItemReceivedForm", model);
	}

	@RequestMapping(value = "/itemRcvShow.do", method = RequestMethod.GET)
	public ModelAndView getCSItemRcvShow(ItemReceived itemReceived) {

		Map<String, Object> model = new HashMap<String, Object>();

		String currentStatus = "";
		String operationId = itemReceived.getReceivedReportNo();
		List<ItemRcvApprovalHierarchyHistory> itemRcvApproveHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						CS_RECEIVING_ITEMS, operationId, DONE);

		if (!itemRcvApproveHistoryList.isEmpty()) {
			currentStatus = itemRcvApproveHistoryList.get(
					itemRcvApproveHistoryList.size() - 1).getStateName();
		} else {
			currentStatus = "CREATED";
		}

		model.put("itemRcvApproveHistoryList", itemRcvApproveHistoryList);
		model.put("currentStatus", currentStatus);
		model.put("csItemReceivedList", csItemReceivedService
				.listCSItemReceivedByReceivedReportNo(operationId));
		return new ModelAndView("centralStore/csItemReceivedShow", model);
	}

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getCSItemRcvList() {
		Map<String, Object> model = new HashMap<String, Object>();

		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByRoleAndOperation(roleName,
						CS_RECEIVING_ITEMS);

		int currentStateCode = approvalHierarchy.getStateCode();

		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByStatusAndStateCode(
						currentStateCode, OPEN);
		Integer[] operationId = new Integer[approvalHierarchyHistoryList.size()];
		int x = 0;
		for (Object approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId[x] = Integer.parseInt(approvalHierarchyHistory
					.toString());
			x++;
		}

		List<CSItemReceived> receivedItemList = csItemReceivedService
				.listCSItemReceivedByOperationIds(operationId);

		model.put("receivedItemList", receivedItemList);
		return new ModelAndView("centralStore/csItemReceivedList", model);
	}

	@RequestMapping(value = "/centralStore.do", method = RequestMethod.GET)
	public ModelAndView getCenteralStore() {
		Map<String, Object> model = new HashMap<String, Object>();

		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}
		ApprovalHierarchy ApprovalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByRoleAndOperation(roleName,
						CS_RECEIVING_ITEMS);

		int currentStateCode = ApprovalHierarchy.getStateCode();

		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByStatusAndStateCode(
						currentStateCode, OPEN);
		Integer[] operationId = new Integer[approvalHierarchyHistoryList.size()];
		int x = 0;
		for (Object approvalHierarchyHistory : approvalHierarchyHistoryList) {
			operationId[x] = Integer.parseInt(approvalHierarchyHistory
					.toString());
			x++;
		}

		List<CSItemReceived> receivedItemList = csItemReceivedService
				.listCSItemReceivedByOperationIds(operationId);

		model.put("receivedItemTotal", receivedItemList.size());
		return new ModelAndView("centralStore/centralStore", model);
	}

	@RequestMapping(value = "/itemReceivedSave.do", method = RequestMethod.POST)
	public String centralSotreReceivedItemSave(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {

		List<String> itemIdList = null;
		List<String> descriptionList = null;
		List<String> uomList = null;
		List<Double> quantityList = null;
		List<Double> costList = null;
		List<String> ledgerBookNoList = null;
		List<String> ledgerPageNoList = null;
		List<String> remarksList = null;

		List<CSItemReceived> csItemReceivedDbList = csItemReceivedService
				.listCSItemReceiveds();

		Date now = new Date();

		CSItemReceived csItemReceived = new CSItemReceived();
		csItemReceived.setContractNo(itemReceived.getContractNo());
		csItemReceived.setChalanNo(itemReceived.getChalanNo());
		csItemReceived.setContractDate(itemReceived.getContractDate());
		csItemReceived.setInvoiceDate(itemReceived.getInvoiceDate());
		csItemReceived.setLandingDate(itemReceived.getLandingDate());
		csItemReceived.setDeliveryDate(itemReceived.getDeliveryDate());
		csItemReceived.setActive(itemReceived.isActive());
		csItemReceived.setCreatedBy(itemReceived.getCreatedBy());
		csItemReceived.setCreatedDate(now);
		csItemReceived.setReceivedBy(itemReceived.getCreatedBy());
		csItemReceived.setReceivedDate(now);

		Integer[] rrNos = new Integer[csItemReceivedDbList.size()];
		if (csItemReceivedDbList != null && !csItemReceivedDbList.isEmpty()) {

			for (int i = 0; i < csItemReceivedDbList.size(); i++) {
				rrNos[i] = csItemReceivedDbList.get(i).getReceivedReportNo();
			}
			Arrays.sort(rrNos, Collections.reverseOrder());
			csItemReceived.setReceivedReportNo(rrNos[0] + 1);
		} else {
			csItemReceived.setReceivedReportNo(1000);
		}

		// Details Table Save to Database from GUI value with Master Table Id}

		if (itemReceived.getItemId() != null) {
			itemIdList = itemReceived.getItemId();
		}

		if (itemReceived.getDescription() != null) {
			descriptionList = itemReceived.getDescription();
		}

		if (itemReceived.getUom() != null) {
			uomList = itemReceived.getUom();
		}

		if (itemReceived.getQuantity() != null) {
			quantityList = itemReceived.getQuantity();
		}

		if (itemReceived.getCost() != null) {
			costList = itemReceived.getCost();
		}

		if (itemReceived.getLedgerBookNo() != null) {
			ledgerBookNoList = itemReceived.getLedgerBookNo();
		}

		if (itemReceived.getLedgerPageNo() != null) {
			ledgerPageNoList = itemReceived.getLedgerPageNo();
		}

		if (itemReceived.getRemarks() != null) {
			remarksList = itemReceived.getRemarks();
		}

		// Get All Approval Hierarchy on CS_RECEIVING_ITEMS
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(CS_RECEIVING_ITEMS);

		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(
						CS_RECEIVING_ITEMS, stateCodes[0].toString());

		String[] rol = approvalHierarchy.getRoleName().split(",");

		int flag = 0;
		for (int p = 0; p < rol.length; p++) {
			if (rol[p].equals(itemReceived.getRoleName())) {
				flag = 1;
				break;
			}
		}

		if (flag == 1) {

			// for Approval Hierarchy History Maintainanace Start
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = new ItemRcvApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(itemReceived.getRoleName());
			approvalHierarchyHistory.setOperationId(csItemReceived
					.getReceivedReportNo().toString());
			approvalHierarchyHistory.setOperationName(CS_RECEIVING_ITEMS);
			approvalHierarchyHistory.setCreatedBy(itemReceived.getCreatedBy());
			approvalHierarchyHistory.setCreatedDate(now);
			if (stateCodes.length > 0) {
				// All time start with 1st
				approvalHierarchyHistory.setStateCode(stateCodes[0]);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());// State
			}
			approvalHierarchyHistory.setStatus(OPEN);
			approvalHierarchyHistory.setActive(itemReceived.isActive());

			// for Approval Hierarchy History Maintainanace end

			// for Central Store History Maintanance Start

			// Receiving Items Save Operation Start from Here
			if (itemIdList != null) {
				for (int i = 0; i < itemIdList.size(); i++) {
					csItemReceived.setItemId(itemIdList.get(i));
					csItemReceived.setDescription(descriptionList.get(i));
					csItemReceived.setUom(uomList.get(i));
					csItemReceived.setQuantity(quantityList.get(i));
					csItemReceived.setCost(costList.get(i));
					csItemReceived.setLedgerBookNo(ledgerBookNoList.get(i));
					csItemReceived.setLedgerPageNo(ledgerPageNoList.get(i));
					csItemReceived.setRemarks(remarksList.get(i));
					csItemReceived.setId(null);

					// Receiving Items Main Table
					csItemReceivedService.addCSItemReceived(csItemReceived);
				}

				// central Store Item History
				itemRcvApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(approvalHierarchyHistory);
			}
		}

		return "redirect:/centralStore/rcvPrcess/list.do";

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/itemReceivedSubmitApproved.do", method = RequestMethod.GET)
	public String centralSotreReceivedItemSubmitApproved(Model model,
			@ModelAttribute("itemReceived") ItemReceived itemReceived) {

		// get Current User and Role Information
		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}

		// get Received Item List against RR No
		List<CSItemReceived> itemReceivedList = csItemReceivedService
				.listCSItemReceivedByReceivedReportNo(itemReceived
						.getReceivedReportNo());

		// get All State Codes from Approval Hierarchy and sort Dececding oder
		// for highest State Code
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.listApprovalHierarchys();
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get Current State Code from Approval hierarchy by RR No (Operation
		// Id)
		List<ItemRcvApprovalHierarchyHistory> approvalHierarchyHistoryList = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistoryByOperationId(itemReceived
						.getReceivedReportNo());
		Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
		for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
			ids[i] = approvalHierarchyHistoryList.get(i).getId();
		}
		Arrays.sort(ids, Collections.reverseOrder());

		ItemRcvApprovalHierarchyHistory approvalHierarchyHistory = itemRcvApprovalHierarchyHistoryService
				.getApprovalHierarchyHistory(ids[0]);
		Integer currentStateCode = approvalHierarchyHistory.getStateCode();

		Integer nextStateCode = 0;

		// searching next State code
		for (int state : stateCodes) {
			if (state > currentStateCode) {
				nextStateCode = state;
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								CS_RECEIVING_ITEMS, nextStateCode.toString());
				approvalHierarchyHistory.setStatus(OPEN);
				approvalHierarchyHistory.setStateCode(nextStateCode);
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());
				approvalHierarchyHistory.setId(null);
				approvalHierarchyHistory.setCreatedBy(auth.getName());
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setActRoleName(approvalHierarchy
						.getRoleName());
				itemRcvApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(approvalHierarchyHistory);
				break;
			}

			if (state == currentStateCode) {
				approvalHierarchyHistory.setStatus(DONE);
				itemRcvApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(approvalHierarchyHistory);
			}

			if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
				approvalHierarchyHistory.setStatus(DONE);
				itemRcvApprovalHierarchyHistoryService
						.addApprovalHierarchyHistory(approvalHierarchyHistory);			

			}
		}

		return "redirect:/centralStore/rcvPrcess/list.do";
	}

}
