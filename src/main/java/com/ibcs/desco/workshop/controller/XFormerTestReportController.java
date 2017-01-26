package com.ibcs.desco.workshop.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.Roles;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.ApprovalHierarchy;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.TransformerTestApprovalHierarchyHistory;
import com.ibcs.desco.common.service.ApprovalHierarchyService;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;
import com.ibcs.desco.contractor.service.ContractorRepresentiveService;
import com.ibcs.desco.cs.bean.CentralStoreRequisitionMstDtl;
import com.ibcs.desco.cs.bean.ItemReceived;
import com.ibcs.desco.cs.model.CentralStoreRequisitionDtl;
import com.ibcs.desco.cs.model.CentralStoreRequisitionMst;
import com.ibcs.desco.workshop.model.JobCardDtl;
import com.ibcs.desco.workshop.model.JobCardMst;
import com.ibcs.desco.workshop.model.TestReport1P;
import com.ibcs.desco.workshop.model.TestReport3P;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsCnAllocation;
import com.ibcs.desco.workshop.model.WsJobSummary;
import com.ibcs.desco.workshop.service.WSTransformerService;

@Controller
@PropertySource("classpath:common.properties")
public class XFormerTestReportController extends Constrants {

	@Autowired
	UserService userService;

	@Autowired
	DepartmentsService departmentsService;

	@Autowired
	ApprovalHierarchyService approvalHierarchyService;

	@Autowired
	WSTransformerService wSTransformerService;

	@Autowired
	ContractorRepresentiveService contractorRepresentiveService;

	@Autowired
	CommonService commonService;

	@Value("${desco.requisition.prefix}")
	private String descoRequisitionNoPrefix;

	@Value("${project.separator}")
	private String separator;

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/ws/xf/contractorList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Contractor> contractorList = (List<Contractor>) (Object) commonService
					.getObjectListByTwoColumn("Contractor", "active", "1",
							"contractorType", WORKSHOP);

			model.put("contractorList", contractorList);
			return new ModelAndView("workshop/wsContractorList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/ws/xf/wsCnXFormerAllocation.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsCnXFormerAllocation() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Contractor> contractorList = (List<Contractor>) (Object) commonService
					.getObjectListByTwoColumn("Contractor", "active", "1",
							"contractorType", WORKSHOP);

			List<String> listA = new ArrayList<String>();
			for (Contractor contractor : contractorList) {
				listA.add(contractor.getContractNo());
			}
			List<WsCnAllocation> wsCnAllocationList = (List<WsCnAllocation>) (Object) commonService
					.getAllObjectList("WsCnAllocation");

			List<String> listB = new ArrayList<String>();
			for (WsCnAllocation ws : wsCnAllocationList) {
				listB.add(ws.getWorkOrderNo());
			}
			for (String txt : listB) {
				if (listA.contains(txt)) {
					listA.remove(txt);
				}
			}

			for (Contractor contractor : contractorList) {
				String cNo = contractor.getContractNo();
				if (listA.contains(cNo)) {
					WsCnAllocation obj = new WsCnAllocation();
					obj.setId(null);
					obj.setWorkOrderNo(cNo);
					obj.setContractorMst(contractor);
					obj.setRepairQty1P(0.0);
					obj.setRepairQty3P(0.0);
					obj.setPreventiveQty1P(0.0);
					obj.setPreventiveQty1P(0.0);
					obj.setCreatedBy(commonService.getAuthUserName());
					obj.setCreatedDate(new Date());

					commonService.saveOrUpdateModelObjectToDB(obj);

				}
			}

			List<WsCnAllocation> wsCnAllocationListNew = (List<WsCnAllocation>) (Object) commonService
					.getAllObjectList("WsCnAllocation");

			// model.put("contractorList", contractorList);
			model.put("wsCnAllocationList", wsCnAllocationListNew);

			return new ModelAndView(
					"workshop/transformer/wsCnXFormerAllocation", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ws/xf/updateWsCnAllocation.do", method = RequestMethod.POST)
	public String updatesafetymargin(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			WsCnAllocation bean = gson.fromJson(json, WsCnAllocation.class);

			WsCnAllocation allocationDB = (WsCnAllocation) commonService
					.getAnObjectByAnyUniqueColumn("WsCnAllocation", "id", ""
							+ bean.getId());
			allocationDB.setRepairQty1P(bean.getRepairQty1P());
			allocationDB.setRepairQty3P(bean.getRepairQty3P());
			allocationDB.setPreventiveQty1P(bean.getPreventiveQty1P());
			allocationDB.setPreventiveQty3P(bean.getPreventiveQty3P());
			allocationDB.setRemainingRepairQty1P(bean.getRepairQty1P());
			allocationDB.setRemainingRepairQty3P(bean.getRepairQty3P());
			allocationDB.setRemainingPreventiveQty1P(bean.getPreventiveQty1P());
			allocationDB.setRemainingPreventiveQty3P(bean.getPreventiveQty3P());
			allocationDB.setRemarks(bean.getRemarks());
			allocationDB.setModifiedBy(commonService.getAuthUserName());
			allocationDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(allocationDB);
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@RequestMapping(value = "/ws/xf/testReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView testReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userId);

			Departments department = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							authUser.getDeptId());
			String workOrderNo = department.getContactNo();

			List<TransformerRegister> transformerList = wSTransformerService
					.getTransformerListWithNullTestDateAndValidJobNo(workOrderNo);

			model.put("transformerList", transformerList);
			return new ModelAndView("workshop/transformer/testReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@RequestMapping(value = "/ws/xf/testReportForm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView testReportForm(TransformerRegister bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			TransformerRegister transformer = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							bean.getId().toString());

			List<TransformerRegister> testedXFList = wSTransformerService
					.getTransformerListBySLNoAndManufactureNameAndYear(
							transformer.getTransformerSerialNo(),
							transformer.getManufacturedName(),
							transformer.getManufacturedYear());

			if (testedXFList != null) {
				if (testedXFList.size() > 0) {
					transformer.setPreviousRepairDate(testedXFList.get(0)
							.getTestDate());
				}
			}

			model.put("transformer", transformer);

			if (transformer.getTransformerType().equalsIgnoreCase(SINGLE_PHASE)) {
				return new ModelAndView(
						"workshop/transformer/testReportForm1P", model);
			} else if (transformer.getTransformerType().equalsIgnoreCase(
					THREE_PHASE)) {
				return new ModelAndView(
						"workshop/transformer/testReportForm3P", model);
			} else {
				model.put("errorMsg",
						"Sorry!!! Transformer Type is not defined.");
				return new ModelAndView("workshop/errorWS", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@RequestMapping(value = "/ws/xf/registerRepair.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView registerRepair() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*
			 * List<TransformerRegister> transformerLis = wSTransformerService
			 * .getTransformerListWithNullTestDateAndValidJobNo("");
			 */

			List<TransformerRegister> transformerList = wSTransformerService
					.getObjectListByAnyColumnWithNotNullDate("typeOfWork",
							"Repair Works", "testDate");

			/*
			 * (List<TransformerRegister>) (Object) commonService
			 * .getObjectListByAnyColumn("TransformerRegister", "typeOfWork",
			 * "Repair Works");
			 */

			model.put("transformerList", transformerList);

			return new ModelAndView("workshop/transformer/registerRepair",
					model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@RequestMapping(value = "/ws/xf/saveTestReport1P.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveTestReport1P(
			@ModelAttribute("bean") TestReport1P bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer regId = bean.getId();
			TransformerRegister xfRegisterDB = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							regId.toString());

			xfRegisterDB.setTestDate(bean.getTestDate());
			xfRegisterDB.setModifiedBy(commonService.getAuthUserName());
			xfRegisterDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(xfRegisterDB);

			bean.setId(null);
			bean.setTsfRegMst(xfRegisterDB);
			bean.setCreatedBy(commonService.getAuthUserName());
			bean.setCreatedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(bean);

			this.saveApprovalHistoryforTransformerTest(xfRegisterDB.getId()
					.toString(), TRANSFORMER_TEST_REPORT_1P);

			TestReport1P tReportDB = (TestReport1P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport1P", "tsfRegMst",
							xfRegisterDB.getId().toString());

			model.put("report1P", tReportDB);
			return showTestReport1P(tReportDB);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ws/xf/updateTestReport1P.do", method = RequestMethod.POST)
	public String updateTestReport1P(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TestReport1P bean = gson.fromJson(json, TestReport1P.class);

			TestReport1P tReport1PDB = (TestReport1P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport1P", "id", ""
							+ bean.getId());

			String testDateString = bean.getTemp();
			DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			Date testDate = fm.parse(testDateString);

			TransformerRegister tsfReg = tReport1PDB.getTsfRegMst();
			tsfReg.setTestDate(testDate);
			commonService.saveOrUpdateModelObjectToDB(tsfReg);

			tReport1PDB.setTestDate(testDate);
			tReport1PDB.setMt_ht_g(bean.getMt_ht_g());
			tReport1PDB.setMt_ht_ht(bean.getMt_ht_ht());
			tReport1PDB.setMt_lt_g(bean.getMt_lt_g());
			tReport1PDB.setMt_lt_lt(bean.getMt_lt_lt());
			tReport1PDB.setMt_ht_lt(bean.getMt_ht_lt());
			tReport1PDB.setMt_remarks(bean.getMt_remarks());

			tReport1PDB.setVrt_ht_h1_h2(bean.getVrt_ht_h1_h2());
			tReport1PDB.setVrt_lt_x1_x2(bean.getVrt_lt_x1_x2());
			tReport1PDB.setVrt_remarks(bean.getVrt_remarks());

			tReport1PDB.setSct_ht_h1_h2(bean.getSct_ht_h1_h2());
			tReport1PDB.setSct_ht_lx(bean.getSct_ht_lx());
			tReport1PDB.setSct_ht_wa(bean.getSct_ht_wa());
			tReport1PDB.setSct_lt_lx(bean.getSct_lt_lx());
			tReport1PDB.setSct_tsc_loss(bean.getSct_tsc_loss());

			tReport1PDB.setOct_lt_h1_h2(bean.getOct_lt_h1_h2());
			tReport1PDB.setOct_lt_wa(bean.getOct_lt_wa());
			tReport1PDB.setOct_lt_lx(bean.getOct_lt_lx());
			tReport1PDB.setOct_ht_lx(bean.getOct_ht_lx());
			tReport1PDB.setOct_toc_loss(bean.getOct_toc_loss());

			tReport1PDB.setOdst(bean.getOdst());

			tReport1PDB.setTsfRegMst(tsfReg);
			tReport1PDB.setModifiedBy(commonService.getAuthUserName());
			tReport1PDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(tReport1PDB);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	@ResponseBody
	@RequestMapping(value = "/ws/xf/updateTestReport3P.do", method = RequestMethod.POST)
	public String updateTestReport3P(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			TestReport3P bean = gson.fromJson(json, TestReport3P.class);

			TestReport3P tReport3PDB = (TestReport3P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport3P", "id", ""
							+ bean.getId());

			String testDateString = bean.getTemp();
			DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			Date testDate = fm.parse(testDateString);

			TransformerRegister tsfReg = tReport3PDB.getTsfRegMst();
			tsfReg.setTestDate(testDate);
			commonService.saveOrUpdateModelObjectToDB(tsfReg);

			tReport3PDB.setTestDate(testDate);
			tReport3PDB.setImpedance(bean.getImpedance());
			//
			tReport3PDB.setCt_hv_a_b(bean.getCt_hv_a_b());
			tReport3PDB.setCt_lv_a_b(bean.getCt_lv_a_b());
			tReport3PDB.setCt_lv_a_n(bean.getCt_lv_a_n());
			tReport3PDB.setCt_hv_b_c(bean.getCt_hv_b_c());
			tReport3PDB.setCt_lv_b_c(bean.getCt_lv_b_c());
			tReport3PDB.setCt_lv_b_n(bean.getCt_lv_b_n());
			tReport3PDB.setCt_hv_c_a(bean.getCt_hv_c_a());
			tReport3PDB.setCt_lv_c_a(bean.getCt_lv_c_a());
			tReport3PDB.setCt_lv_c_n(bean.getCt_lv_c_n());
			//
			tReport3PDB.setRt_ratio(bean.getRt_ratio());
			tReport3PDB.setRt_remarks(bean.getRt_remarks());
			tReport3PDB.setRt_av_a_b(bean.getRt_av_a_b());
			tReport3PDB.setRt_av_b_c(bean.getRt_av_b_c());
			tReport3PDB.setRt_av_c_a(bean.getRt_av_c_a());
			tReport3PDB.setRt_mlv_a_b(bean.getRt_mlv_a_b());
			tReport3PDB.setRt_mlv_b_c(bean.getRt_mlv_b_c());
			tReport3PDB.setRt_mlv_c_a(bean.getRt_mlv_c_a());
			tReport3PDB.setRt_mlv_a_n(bean.getRt_mlv_a_n());
			tReport3PDB.setRt_mlv_b_n(bean.getRt_mlv_b_n());
			tReport3PDB.setRt_mlv_c_n(bean.getRt_mlv_c_n());
			//
			tReport3PDB.setIrt_hv_lvg(bean.getIrt_hv_lvg());
			tReport3PDB.setIrt_lv_hvg(bean.getIrt_lv_hvg());
			tReport3PDB.setIrt_hv_lv(bean.getIrt_hv_lv());
			tReport3PDB.setIrt_oil_temp(bean.getIrt_oil_temp());
			tReport3PDB.setIrt_remarks(bean.getIrt_remarks());
			//
			tReport3PDB.setLlit_hv_ab(bean.getLlit_hv_ab());
			tReport3PDB.setLlit_hv_bc(bean.getLlit_hv_bc());
			tReport3PDB.setLlit_hv_ca(bean.getLlit_hv_ca());
			tReport3PDB.setLlit_hvs_a(bean.getLlit_hvs_a());
			tReport3PDB.setLlit_hvs_b(bean.getLlit_hvs_b());
			tReport3PDB.setLlit_hvs_c(bean.getLlit_hvs_c());
			tReport3PDB.setLlit_lv_a(bean.getLlit_lv_a());
			tReport3PDB.setLlit_lv_b(bean.getLlit_lv_b());
			tReport3PDB.setLlit_lv_c(bean.getLlit_lv_c());
			tReport3PDB.setLlit_lv_n(bean.getLlit_lv_n());
			tReport3PDB.setLlit_lv_remarks(bean.getLlit_lv_remarks());
			tReport3PDB.setLlit_load_loss(bean.getLlit_load_loss());
			tReport3PDB.setLlit_copper_loss(bean.getLlit_copper_loss());
			tReport3PDB.setLlit_impedance_volt(bean.getLlit_impedance_volt());
			tReport3PDB.setLlit_percent_impedance(bean
					.getLlit_percent_impedance());
			tReport3PDB.setLlit_remarks(bean.getLlit_remarks());
			//
			tReport3PDB.setIlt_lvv_ab(bean.getIlt_lvv_ab());
			tReport3PDB.setIlt_lvv_bc(bean.getIlt_lvv_bc());
			tReport3PDB.setIlt_lvv_ca(bean.getIlt_lvv_ca());
			tReport3PDB.setIlt_lvv_an(bean.getIlt_lvv_an());
			tReport3PDB.setIlt_lvv_bn(bean.getIlt_lvv_bn());
			tReport3PDB.setIlt_lvv_cn(bean.getIlt_lvv_cn());
			tReport3PDB.setIlt_lva_a(bean.getIlt_lva_a());
			tReport3PDB.setIlt_lva_b(bean.getIlt_lva_b());
			tReport3PDB.setIlt_lva_c(bean.getIlt_lva_c());
			tReport3PDB.setIlt_remarks(bean.getIlt_remarks());
			//
			tReport3PDB.setTot_dieletric_volt_transformer(bean
					.getTot_dieletric_volt_transformer());
			tReport3PDB.setTot_dieletric_kv(bean.getTot_dieletric_kv());
			tReport3PDB.setTot_remarks(bean.getTot_remarks());
			//
			tReport3PDB.setTsfRegMst(tsfReg);
			tReport3PDB.setModifiedBy(commonService.getAuthUserName());
			tReport3PDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(tReport3PDB);

			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}

	// @RequestMapping(value = "/ws/xf/showTestReport1P.do", method =
	// RequestMethod.GET)
	// @PreAuthorize("isAuthenticated()")
	@SuppressWarnings("unchecked")
	public ModelAndView showTestReport1P(
			@ModelAttribute("bean") TestReport1P bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		try {
			TestReport1P tReportDB = (TestReport1P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport1P", "id",
							bean.getId() + "");

			String buttonValue = null;
			List<ApprovalHierarchy> nextManRcvProcs = null;
			String currentStatus = "";

			// operation Id which selected by login user
			String operationId = tReportDB.getTsfRegMst().getId().toString();

			// get All History for this operation Id which process is already
			// done
			List<TransformerTestApprovalHierarchyHistory> approveHistoryList = wSTransformerService
					.getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
							TRANSFORMER_TEST_REPORT_1P, operationId, DONE);

			// get All approval hierarchy of this process
			/*
			 * List<ApprovalHierarchy> approveHeirchyList =
			 * approvalHierarchyService
			 * .getApprovalHierarchyByOperationName(TRANSFORMER_TEST_REPORT_1P);
			 */

			// added by taleb taleb//
			List<AuthUser> userList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "deptId",
							department.getDeptId());
			// Role name list by role id_list
			List<String> roleIdList = new ArrayList<String>();
			for (AuthUser user : userList) {
				roleIdList.add(String.valueOf(user.getRoleid()));
			}
			List<Roles> roleObjectList = (List<Roles>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"com.ibcs.desco.admin.model.Roles", "role_id",
							roleIdList);
			// App_hier List by RoleList & Op name
			List<String> roleNameList = new ArrayList<String>();
			for (Roles role : roleObjectList) {
				roleNameList.add(role.getRole());
			}

			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndRoleName(
							TRANSFORMER_TEST_REPORT_1P, roleNameList);
			// //

			// button name from db which berry user to user
			buttonValue = approveHeirchyList.get(0).getButtonName();

			// set current status from db
			if (!approveHistoryList.isEmpty()) {
				currentStatus = approveHistoryList.get(
						approveHistoryList.size() - 1).getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// Send To next User as my wish
			List<TransformerTestApprovalHierarchyHistory> historyOpenList = wSTransformerService
					.getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
							TRANSFORMER_TEST_REPORT_1P, operationId, OPEN);

			int currentStateCode = historyOpenList.get(
					historyOpenList.size() - 1).getStateCode();

			// send to whom
			nextManRcvProcs = new ArrayList<ApprovalHierarchy>();
			for (int countStateCodes = 0; countStateCodes < approveHeirchyList
					.size(); countStateCodes++) {
				if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
					nextManRcvProcs
							.add(approveHeirchyList.get(countStateCodes));
				}
			}

			// Back To User as my wish
			List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
			for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
					.size(); countBackStateCodes++) {
				if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
					backManRcvProcs.add(approveHeirchyList
							.get(countBackStateCodes));
				}
			}

			String returnStateCode = "";
			// button Name define
			if (!historyOpenList.isEmpty() && historyOpenList != null) {

				// get current state code
				int stateCode = historyOpenList.get(historyOpenList.size() - 1)
						.getStateCode();

				// Decide for return or not
				returnStateCode = historyOpenList.get(
						historyOpenList.size() - 1).getReturn_state();

				// get next approval hierarchy
				ApprovalHierarchy approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								TRANSFORMER_TEST_REPORT_1P, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

			// all information send to browser
			model.put("report1P", tReportDB);
			model.put("returnStateCode", returnStateCode);
			model.put("approvalHistoryList", approveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("buttonValue", buttonValue);
			model.put("nextManRcvProcs", nextManRcvProcs);
			model.put("backManRcvProcs", backManRcvProcs);
			return new ModelAndView(
					"workshop/transformer/testReportForm1PShow", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView showTestReport3P(
			@ModelAttribute("bean") TestReport3P bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		String userName = commonService.getAuthUserName();
		AuthUser authUser = userService.getAuthUserByUserId(userName);
		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		try {
			TestReport3P tReportDB = (TestReport3P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport3P", "id",
							bean.getId() + "");

			String buttonValue = null;
			List<ApprovalHierarchy> nextManRcvProcs = null;
			String currentStatus = "";

			// operation Id which selected by login user
			String operationId = tReportDB.getTsfRegMst().getId().toString();

			// get All History for this operation Id which process is already
			// done
			List<TransformerTestApprovalHierarchyHistory> approveHistoryList = wSTransformerService
					.getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
							TRANSFORMER_TEST_REPORT_3P, operationId, DONE);

			// get All approval hierarchy of this process
			/*
			 * List<ApprovalHierarchy> approveHeirchyList =
			 * approvalHierarchyService
			 * .getApprovalHierarchyByOperationName(TRANSFORMER_TEST_REPORT_3P);
			 */
			// added by taleb taleb//
			List<AuthUser> userList = (List<AuthUser>) (Object) commonService
					.getObjectListByAnyColumn(
							"com.ibcs.desco.admin.model.AuthUser", "deptId",
							department.getDeptId());
			// Role name list by role id_list
			List<String> roleIdList = new ArrayList<String>();
			for (AuthUser user : userList) {
				roleIdList.add(String.valueOf(user.getRoleid()));
			}
			List<Roles> roleObjectList = (List<Roles>) (Object) commonService
					.getObjectListByAnyColumnValueList(
							"com.ibcs.desco.admin.model.Roles", "role_id",
							roleIdList);
			// App_hier List by RoleList & Op name
			List<String> roleNameList = new ArrayList<String>();
			for (Roles role : roleObjectList) {
				roleNameList.add(role.getRole());
			}

			List<ApprovalHierarchy> approveHeirchyList = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndRoleName(
							TRANSFORMER_TEST_REPORT_3P, roleNameList);
			// //

			// button name from db which berry user to user
			buttonValue = approveHeirchyList.get(0).getButtonName();

			// set current status from db
			if (!approveHistoryList.isEmpty()) {
				currentStatus = approveHistoryList.get(
						approveHistoryList.size() - 1).getStateName();
			} else {
				currentStatus = "CREATED";
			}

			// Send To next User as my wish
			List<TransformerTestApprovalHierarchyHistory> historyOpenList = wSTransformerService
					.getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
							TRANSFORMER_TEST_REPORT_3P, operationId, OPEN);

			int currentStateCode = historyOpenList.get(
					historyOpenList.size() - 1).getStateCode();

			// send to whom
			nextManRcvProcs = new ArrayList<ApprovalHierarchy>();
			for (int countStateCodes = 0; countStateCodes < approveHeirchyList
					.size(); countStateCodes++) {
				if (approveHeirchyList.get(countStateCodes).getStateCode() > currentStateCode) {
					nextManRcvProcs
							.add(approveHeirchyList.get(countStateCodes));
				}
			}

			// Back To User as my wish
			List<ApprovalHierarchy> backManRcvProcs = new ArrayList<ApprovalHierarchy>();
			for (int countBackStateCodes = 0; countBackStateCodes < approveHeirchyList
					.size(); countBackStateCodes++) {
				if (approveHeirchyList.get(countBackStateCodes).getStateCode() < currentStateCode) {
					backManRcvProcs.add(approveHeirchyList
							.get(countBackStateCodes));
				}
			}

			String returnStateCode = "";
			// button Name define
			if (!historyOpenList.isEmpty() && historyOpenList != null) {

				// get current state code
				int stateCode = historyOpenList.get(historyOpenList.size() - 1)
						.getStateCode();

				// Decide for return or not
				returnStateCode = historyOpenList.get(
						historyOpenList.size() - 1).getReturn_state();

				// get next approval hierarchy
				ApprovalHierarchy approveHeirarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								TRANSFORMER_TEST_REPORT_3P, stateCode + "");
				buttonValue = approveHeirarchy.getButtonName();
			}

			// all information send to browser
			model.put("testReport3P", tReportDB);
			model.put("returnStateCode", returnStateCode);
			model.put("approvalHistoryList", approveHistoryList);
			model.put("currentStatus", currentStatus);
			model.put("buttonValue", buttonValue);
			model.put("nextManRcvProcs", nextManRcvProcs);
			model.put("backManRcvProcs", backManRcvProcs);
			return new ModelAndView(
					"workshop/transformer/testReportForm3PShow", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	@RequestMapping(value = "/ws/xf/saveTestReport3P.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView saveTestReport3P(
			@ModelAttribute("bean") TestReport3P bean) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Integer regId = bean.getId();
			TransformerRegister xfRegisterDB = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							regId.toString());

			xfRegisterDB.setTestDate(bean.getTestDate());
			xfRegisterDB.setModifiedBy(commonService.getAuthUserName());
			xfRegisterDB.setModifiedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(xfRegisterDB);

			bean.setId(null);
			bean.setTsfRegMst(xfRegisterDB);
			bean.setCreatedBy(commonService.getAuthUserName());
			bean.setCreatedDate(new Date());

			commonService.saveOrUpdateModelObjectToDB(bean);

			this.saveApprovalHistoryforTransformerTest(xfRegisterDB.getId()
					.toString(), TRANSFORMER_TEST_REPORT_3P);

			TestReport3P tReportDB = (TestReport3P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport3P", "tsfRegMst",
							xfRegisterDB.getId().toString());

			model.put("report3P", tReportDB);
			return showTestReport3P(tReportDB);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	public void saveApprovalHistoryforTransformerTest(String op_no,
			String hierarchyName) {
		// Get All Approval Hierarchy on TRANSFORMER_TEST_REPORT_1P
		List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
				.getApprovalHierarchyByOperationName(hierarchyName);

		// get All State code which define for TRANSFORMER_TEST_REPORT_1P
		Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
		for (int i = 0; i < approvalHierarchyList.size(); i++) {
			stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
		}
		Arrays.sort(stateCodes);

		// get minimum hierarchy information
		ApprovalHierarchy approvalHierarchy = approvalHierarchyService
				.getApprovalHierarchyByOperationNameAndSateCode(hierarchyName,
						stateCodes[0].toString());

		String[] rol = approvalHierarchy.getRoleName().split(",");
		int flag = 0;
		for (int p = 0; p < rol.length; p++) {
			if (rol[p].equals(commonService.getAuthRoleName())) {
				flag = 1;
				break;
			}
		}

		// Checking role for TRANSFORMER_TEST_REPORT Process ??
		if (flag == 1) {
			String userId = commonService.getAuthUserName();
			AuthUser authUser = (AuthUser) commonService
					.getAnObjectByAnyUniqueColumn(
							"com.ibcs.desco.admin.model.AuthUser", "userid",
							userId);

			Departments department = (Departments) commonService
					.getAnObjectByAnyUniqueColumn("Departments", "deptId",
							authUser.getDeptId());

			TransformerTestApprovalHierarchyHistory approvalHierarchyHistory = new TransformerTestApprovalHierarchyHistory();
			approvalHierarchyHistory.setActRoleName(commonService
					.getAuthRoleName());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setDeptId(authUser.getDeptId());
			approvalHierarchyHistory.setcDeptName(department.getDeptName());
			approvalHierarchyHistory.setOperationName(hierarchyName);
			approvalHierarchyHistory.setCreatedBy(commonService
					.getAuthUserName());
			approvalHierarchyHistory.setCreatedDate(new Date());
			if (stateCodes.length > 0) {
				// All time start with 1st
				approvalHierarchyHistory.setStateCode(stateCodes[0]);
				// State code set from approval hierarchy Table
				approvalHierarchyHistory.setStateName(approvalHierarchy
						.getStateName());
			}
			approvalHierarchyHistory.setStatus(OPEN);
			approvalHierarchyHistory.setActive(true);
			approvalHierarchyHistory.setOperationId(op_no);
			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
		}
	}

	// Transformer Test Report (TTR) Approving process
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/approveTTR.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView approveTTR(@ModelAttribute("bean") ItemReceived bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();
			String op_id = bean.getReceivedReportNo();
			TransformerRegister tRegister = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							op_id);
			String hierarchyName = "";
			if (tRegister.getTransformerType().equalsIgnoreCase(SINGLE_PHASE)) {
				hierarchyName = TRANSFORMER_TEST_REPORT_1P;
			} else if (tRegister.getTransformerType().equalsIgnoreCase(
					THREE_PHASE)) {
				hierarchyName = TRANSFORMER_TEST_REPORT_3P;
			}

			if (hierarchyName.equalsIgnoreCase("")) {
				model.put(
						"errorMsg",
						"Process Name is undefined for this Transformer Test Report. Please Check Transformer Phase from Register.");
				if (roleName.equalsIgnoreCase("ROLE_WO_CN_USER")) {
					return new ModelAndView("workshop/errorWSCN", model);
				}
				return new ModelAndView("workshop/errorWS", model);
			}

			// Send return to next user who backed me
			if (bean.getReturn_state() != null) {

				List<TransformerTestApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
						.getObjectListByAnyColumn(
								"TransformerTestApprovalHierarchyHistory",
								"operationId", op_id);

				Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
				for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
					ids[i] = approvalHierarchyHistoryList.get(i).getId();
				}
				Arrays.sort(ids, Collections.reverseOrder());
				// get current State Code from approval hierarchy history
				TransformerTestApprovalHierarchyHistory approvalHierarchyHistory = (TransformerTestApprovalHierarchyHistory) commonService
						.getAnObjectByAnyUniqueColumn(
								"TransformerTestApprovalHierarchyHistory",
								"id", ids[0].toString());
				int currentStateCode = approvalHierarchyHistory.getStateCode();

				// current user's row status will be done after updated
				ApprovalHierarchy approvalHierarchy = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								hierarchyName, currentStateCode + "");
				approvalHierarchyHistory.setStatus(DONE);
				approvalHierarchyHistory.setCreatedBy(userName);
				approvalHierarchyHistory.setCreatedDate(new Date());
				approvalHierarchyHistory.setDeptId(deptId);
				String deptName = departmentsService.getDepartmentByDeptId(
						deptId).getDeptName();
				approvalHierarchyHistory.setcDeptName(deptName);
				approvalHierarchyHistory.setJustification(bean
						.getJustification());
				approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
						.getApprovalHeader());
				approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
				approvalHierarchyHistory.setcEmpFullName(authUser.getName());
				approvalHierarchyHistory.setcDesignation(authUser
						.getDesignation());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

				// get Next State Code from approval hierarchy history
				TransformerTestApprovalHierarchyHistory approvalHierarchyHistoryNextState = new TransformerTestApprovalHierarchyHistory();
				ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
						.getApprovalHierarchyByOperationNameAndSateCode(
								hierarchyName, bean.getReturn_state());
				approvalHierarchyHistoryNextState.setActive(true);
				approvalHierarchyHistoryNextState.setCreatedBy(userName);
				approvalHierarchyHistoryNextState.setCreatedDate(new Date());
				approvalHierarchyHistoryNextState.setDeptId(deptId);
				approvalHierarchyHistoryNextState.setStatus(OPEN);

				approvalHierarchyHistoryNextState
						.setStateName(approvalHierarchyNextSate.getStateName());
				approvalHierarchyHistoryNextState.setStateCode(Integer
						.parseInt(bean.getReturn_state()));
				approvalHierarchyHistoryNextState.setId(null);
				approvalHierarchyHistoryNextState.setOperationId(op_id);
				approvalHierarchyHistoryNextState
						.setOperationName(approvalHierarchyNextSate
								.getOperationName());
				approvalHierarchyHistoryNextState
						.setActRoleName(approvalHierarchyNextSate.getRoleName());
				approvalHierarchyHistoryNextState
						.setApprovalHeader(approvalHierarchyNextSate
								.getApprovalHeader());

				commonService
						.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

			} else {

				// get All State Codes from Approval Hierarchy and sort
				// Descending order for highest State Code
				List<ApprovalHierarchy> approvalHierarchyList = approvalHierarchyService
						.getApprovalHierarchyByOperationName(hierarchyName);
				Integer[] stateCodes = new Integer[approvalHierarchyList.size()];
				for (int i = 0; i < approvalHierarchyList.size(); i++) {
					stateCodes[i] = approvalHierarchyList.get(i).getStateCode();
				}
				Arrays.sort(stateCodes);

				// get Current State Code from Approval hierarchy by OP_No
				List<TransformerTestApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
						.getObjectListByAnyColumn(
								"TransformerTestApprovalHierarchyHistory",
								"operationId", op_id);

				Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
				for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
					ids[i] = approvalHierarchyHistoryList.get(i).getId();
				}
				Arrays.sort(ids, Collections.reverseOrder());

				// get current State Code from approval history
				TransformerTestApprovalHierarchyHistory approvalHierarchyHistory = (TransformerTestApprovalHierarchyHistory) commonService
						.getAnObjectByAnyUniqueColumn(
								"TransformerTestApprovalHierarchyHistory",
								"id", ids[0].toString());

				int currentStateCode = approvalHierarchyHistory.getStateCode();

				int nextStateCode = 0;

				// searching next State code and send this TT to next person
				for (int state : stateCodes) {

					// if next state code grater than current state code then
					// this process will go to next person
					if (state > currentStateCode) {
						nextStateCode = state;
						ApprovalHierarchy approvalHierarchy = approvalHierarchyService
								.getApprovalHierarchyByOperationNameAndSateCode(
										hierarchyName, nextStateCode + "");
						approvalHierarchyHistory.setStatus(OPEN);
						approvalHierarchyHistory.setStateCode(nextStateCode);
						approvalHierarchyHistory.setStateName(approvalHierarchy
								.getStateName());

						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setDeptId(deptId);
						String deptName = departmentsService
								.getDepartmentByDeptId(deptId).getDeptName();
						approvalHierarchyHistory.setcDeptName(deptName);
						approvalHierarchyHistory
								.setActRoleName(approvalHierarchy.getRoleName());
						approvalHierarchyHistory
								.setApprovalHeader(approvalHierarchy
										.getApprovalHeader());

						approvalHierarchyHistory.setId(null);
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
						break;
					}

					// if next state code equal to current state code then this
					// process will done for login user
					if (state == currentStateCode) {
						ApprovalHierarchy approvalHierarchy = approvalHierarchyService
								.getApprovalHierarchyByOperationNameAndSateCode(
										hierarchyName, state + "");
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setDeptId(deptId);
						String deptName = departmentsService
								.getDepartmentByDeptId(deptId).getDeptName();
						approvalHierarchyHistory.setcDeptName(deptName);
						approvalHierarchyHistory.setJustification(bean
								.getJustification());
						approvalHierarchyHistory
								.setApprovalHeader(approvalHierarchy
										.getApprovalHeader());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());

						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);
					}

					// if next state code is last as approval hierarchy than
					// this process will done and generate report
					if (currentStateCode == stateCodes[(stateCodes.length) - 1]) {
						approvalHierarchyHistory.setStatus(DONE);
						approvalHierarchyHistory.setCreatedBy(userName);
						approvalHierarchyHistory.setCreatedDate(new Date());
						approvalHierarchyHistory.setDeptId(deptId);
						String deptName = departmentsService
								.getDepartmentByDeptId(deptId).getDeptName();
						approvalHierarchyHistory.setcDeptName(deptName);
						approvalHierarchyHistory.setJustification(bean
								.getJustification());
						approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
						approvalHierarchyHistory.setcEmpFullName(authUser
								.getName());
						approvalHierarchyHistory.setcDesignation(authUser
								.getDesignation());
						commonService
								.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

						model.put("transformer", tRegister);
						return new ModelAndView(
								"workshop/transformer/reportTTR", model);
					}
				}

			}
			List<TransformerTestApprovalHierarchyHistory> ttrList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByThreeColumn(
							"TransformerTestApprovalHierarchyHistory",
							"actRoleName", roleName, "status", OPEN, "deptId",
							deptId);

			List<String> opIdList = new ArrayList<String>();
			for (TransformerTestApprovalHierarchyHistory h : ttrList) {
				opIdList.add(h.getOperationId());
			}

			List<TransformerRegister> tsRegisterList = (List<TransformerRegister>) (Object) commonService
					.getObjectListByAnyColumnValueList("TransformerRegister",
							"id", opIdList);
			model.put("tsRegisterList", tsRegisterList);

			if (roleName.equalsIgnoreCase("ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/transformer/cntTRList", model);
			}
			return new ModelAndView("workshop/transformer/wstTRList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (commonService.getAuthRoleName().equalsIgnoreCase(
					"ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/errorWSCN", model);
			}
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/getPendingTTRList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getPendingTTRList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			List<String> opIdList = new ArrayList<String>();
			if (roleName.equalsIgnoreCase("ROLE_WO_CN_USER")) {
				List<TransformerTestApprovalHierarchyHistory> ttrList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
						.getObjectListByThreeColumn(
								"TransformerTestApprovalHierarchyHistory",
								"actRoleName", roleName, "status", OPEN,
								"deptId", deptId);

				for (TransformerTestApprovalHierarchyHistory h : ttrList) {
					opIdList.add(h.getOperationId());
				}
			} else {
				// Get List for WS users
				List<TransformerTestApprovalHierarchyHistory> ttrList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
						.getObjectListByTwoColumn(
								"TransformerTestApprovalHierarchyHistory",
								"actRoleName", roleName, "status", OPEN);

				for (TransformerTestApprovalHierarchyHistory h : ttrList) {
					opIdList.add(h.getOperationId());
				}
			}
			List<TransformerRegister> tsRegisterList = (List<TransformerRegister>) (Object) commonService
					.getObjectListByAnyColumnValueList("TransformerRegister",
							"id", opIdList);
			model.put("tsRegisterList", tsRegisterList);

			if (roleName.equalsIgnoreCase("ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/transformer/cntTRList", model);
			} else {
				return new ModelAndView("workshop/transformer/wstTRList", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (commonService.getAuthRoleName().equalsIgnoreCase(
					"ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/errorWSCN", model);
			}
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@RequestMapping(value = "/ws/xf/openPendingTTR.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView openPendingTTR(
			@ModelAttribute("bean") TransformerRegister bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			TransformerRegister tRegisterDB = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							bean.getId().toString());

			if (tRegisterDB.getTransformerType().equalsIgnoreCase(SINGLE_PHASE)) {
				TestReport1P tReportDB = (TestReport1P) commonService
						.getAnObjectByAnyUniqueColumn("TestReport1P",
								"tsfRegMst", tRegisterDB.getId().toString());
				return showTestReport1P(tReportDB);
			}
			if (tRegisterDB.getTransformerType().equalsIgnoreCase(THREE_PHASE)) {
				TestReport3P tReportDB = (TestReport3P) commonService
						.getAnObjectByAnyUniqueColumn("TestReport3P",
								"tsfRegMst", tRegisterDB.getId().toString());
				return showTestReport3P(tReportDB);
			}

			model.put("errorMsg", "Transformer Type is undefined!!!");
			if (commonService.getAuthRoleName().equalsIgnoreCase(
					"ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/errorWSCN", model);
			}
			return new ModelAndView("workshop/errorWS", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			if (commonService.getAuthRoleName().equalsIgnoreCase(
					"ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/errorWSCN", model);
			}
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	// Function to send approval at any forward stateCode

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/sendToUpper.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView sendToUpper(@ModelAttribute("bean") ItemReceived bean) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			String nextStateCode = bean.getStateCode();
			String justification = bean.getJustification();
			String registerId = bean.getReceivedReportNo();
			String roleName = commonService.getAuthRoleName();
			String userName = commonService.getAuthUserName();
			AuthUser authUser = userService.getAuthUserByUserId(userName);
			String deptId = authUser.getDeptId();

			TransformerRegister tRegister = (TransformerRegister) commonService
					.getAnObjectByAnyUniqueColumn("TransformerRegister", "id",
							registerId);
			String hierarchyName = "";
			if (tRegister.getTransformerType().equalsIgnoreCase(SINGLE_PHASE)) {
				hierarchyName = TRANSFORMER_TEST_REPORT_1P;
			} else if (tRegister.getTransformerType().equalsIgnoreCase(
					THREE_PHASE)) {
				hierarchyName = TRANSFORMER_TEST_REPORT_3P;
			}

			List<TransformerTestApprovalHierarchyHistory> approvalHierarchyHistoryList = (List<TransformerTestApprovalHierarchyHistory>) (Object) commonService
					.getObjectListByAnyColumn(
							"TransformerTestApprovalHierarchyHistory",
							"operationId", registerId);

			Integer[] ids = new Integer[approvalHierarchyHistoryList.size()];
			for (int i = 0; i < approvalHierarchyHistoryList.size(); i++) {
				ids[i] = approvalHierarchyHistoryList.get(i).getId();
			}
			Arrays.sort(ids, Collections.reverseOrder());

			// get current State Code and all info from approval hierarchy
			// history
			TransformerTestApprovalHierarchyHistory approvalHierarchyHistory = (TransformerTestApprovalHierarchyHistory) commonService
					.getAnObjectByAnyUniqueColumn(
							"TransformerTestApprovalHierarchyHistory", "id",
							ids[0].toString());

			int currentStateCode = approvalHierarchyHistory.getStateCode();

			// current user's row status will be done after updated
			ApprovalHierarchy approvalHierarchy = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							hierarchyName, currentStateCode + "");
			approvalHierarchyHistory.setStatus(DONE);
			approvalHierarchyHistory.setModifiedBy(userName);
			approvalHierarchyHistory.setModifiedDate(new Date());
			approvalHierarchyHistory.setCreatedBy(userName);
			approvalHierarchyHistory.setCreatedDate(new Date());
			String deptName = departmentsService.getDepartmentByDeptId(deptId)
					.getDeptName();
			approvalHierarchyHistory.setcDeptName(deptName);
			approvalHierarchyHistory.setDeptId(authUser.getDeptId());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());
			approvalHierarchyHistory.setJustification(justification);
			approvalHierarchyHistory.setApprovalHeader(approvalHierarchy
					.getApprovalHeader());
			approvalHierarchyHistory.setcEmpId(authUser.getEmpId());
			approvalHierarchyHistory.setcEmpFullName(authUser.getName());
			approvalHierarchyHistory.setcDesignation(authUser.getDesignation());

			commonService.saveOrUpdateModelObjectToDB(approvalHierarchyHistory);

			// get Next State Code and all info from approval hierarchy history
			TransformerTestApprovalHierarchyHistory approvalHierarchyHistoryNextState = new TransformerTestApprovalHierarchyHistory();
			ApprovalHierarchy approvalHierarchyNextSate = approvalHierarchyService
					.getApprovalHierarchyByOperationNameAndSateCode(
							hierarchyName, nextStateCode + "");
			approvalHierarchyHistoryNextState.setActive(true);
			approvalHierarchyHistoryNextState.setCreatedBy(userName);
			approvalHierarchyHistoryNextState.setDeptId(deptId);
			approvalHierarchyHistoryNextState.setCreatedDate(new Date());
			approvalHierarchyHistoryNextState.setStatus(OPEN);

			approvalHierarchyHistoryNextState
					.setStateName(approvalHierarchyNextSate.getStateName());
			approvalHierarchyHistoryNextState.setStateCode(Integer
					.parseInt(nextStateCode));
			approvalHierarchyHistoryNextState.setId(null);
			approvalHierarchyHistoryNextState.setOperationId(registerId);
			approvalHierarchyHistoryNextState
					.setOperationName(approvalHierarchyNextSate
							.getOperationName());
			approvalHierarchyHistoryNextState
					.setActRoleName(approvalHierarchyNextSate.getRoleName());
			approvalHierarchyHistoryNextState
					.setApprovalHeader(approvalHierarchyNextSate
							.getApprovalHeader());

			commonService
					.saveOrUpdateModelObjectToDB(approvalHierarchyHistoryNextState);

			if (roleName.equalsIgnoreCase("ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/transformer/cntTRList", model);
			}
			return new ModelAndView("workshop/transformer/wstTRList", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());

			if (commonService.getAuthRoleName().equalsIgnoreCase(
					"ROLE_WO_CN_USER")) {
				return new ModelAndView("workshop/errorWSCN", model);
			}
			return new ModelAndView("workshop/errorWS", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/getItemRequisitionList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getItemRequisitionList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ContractorRepresentive cRep = (ContractorRepresentive) commonService
					.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
							"userId", commonService.getAuthUserName());
			String wOrder = cRep.getContractor().getContractNo();
			
			List<CentralStoreRequisitionMst> csReqMstList = (List<CentralStoreRequisitionMst>) wSTransformerService
					.getObjectListbyFourColumnAndOneNotNullColumn(
							"CentralStoreRequisitionMst", "workOrderNumber",
							wOrder, "received", "0", "senderStore", "ws", "approved", "1",
							"gatePassNo");
			model.put("centralStoreRequisitionMstList", csReqMstList);
			return new ModelAndView(
					"workshop/transformerMaterials/getItemRequisitionList",
					model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/getJobWiseItemIssueForm.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getJobWiseItemIssueForm(
			@ModelAttribute("bean") CentralStoreRequisitionMst bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ContractorRepresentive cRep = (ContractorRepresentive) commonService
					.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
							"userId", commonService.getAuthUserName());

			List<TransformerRegister> tRegisterList = wSTransformerService
					.getTransformerListWithNullTestDateAndValidJobNo(cRep
							.getContractor().getContractNo());

			/*
			 * for (TransformerRegister transformerRegister : tRegisterList) {
			 * // JobCardMst jCardMst= }
			 */
			List<CentralStoreRequisitionDtl> csReqDtlList = (List<CentralStoreRequisitionDtl>) (Object)
					commonService.getObjectListByTwoColumnWithOneNullValue("CentralStoreRequisitionDtl",
							"requisitionNo", bean.getRequisitionNo(), "cnWsStatus");
			
			List<Double> remainQtyList = null;
			for (CentralStoreRequisitionDtl csReqDtl : csReqDtlList) {
				remainQtyList = new ArrayList<Double>();
				for (TransformerRegister tr : tRegisterList) {

					List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
							.getObjectListByAnyColumnOrderByAnyColumn(
									"JobCardMst", "jobCardNo", tr.getJobNo(),
									"version", DECENDING);
					JobCardMst jobCardMst = new JobCardMst();
					if (jobCardMstList.size() > 0) {
						jobCardMst = jobCardMstList.get(0);

						List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
								.getObjectListByTwoColumn("JobCardDtl",
										"itemCode", csReqDtl.getItemCode(),
										"jobCardMst.id", jobCardMst.getId()
												.toString());
						JobCardDtl jobCardDtl = new JobCardDtl();
						if (jobCardDtlList.size() > 0) {
							jobCardDtl = jobCardDtlList.get(0);
							remainQtyList.add(jobCardDtl
									.getRemainningQuantity());
						}else{
							remainQtyList.add(0.0);
						}
					}else{
						continue;
					}

				}
				
				csReqDtl.setRemainQty(remainQtyList);
				//remainQtyList.clear();
			}
			model.put("tRegisterList", tRegisterList);
			model.put("csReqDtlList", csReqDtlList);
			model.put("requisitionNo", bean.getRequisitionNo());
			return new ModelAndView(
					"workshop/transformerMaterials/jobWiseItemIssue", model);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/ws/xf/updateJobWiseItemIssue.do", method = RequestMethod.POST)
	public String updateJobWiseItemIssue(@RequestBody String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		String userName = commonService.getAuthUserName();
		Date now = new Date();
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			CentralStoreRequisitionMstDtl bean = gson.fromJson(json, CentralStoreRequisitionMstDtl.class);

			/*TestReport3P tReport3PDB = (TestReport3P) commonService
					.getAnObjectByAnyUniqueColumn("TestReport3P", "id", ""
							+ bean.getId());*/
			CentralStoreRequisitionDtl csRequisitionDtlDb = (CentralStoreRequisitionDtl)
					commonService.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionDtl", "id", bean.getId()+"");
			
			Map <String, Double> jobCardMap = bean.getJobCardQtyMap();
			for (Map.Entry<String, Double> entry : jobCardMap.entrySet()) {
				String jobCardNo = entry.getKey();
				Double jobCardValue = entry.getValue();
				List<JobCardMst> jobCardMstList = (List<JobCardMst>)(Object)
						commonService.getObjectListByAnyColumnOrderByAnyColumn("JobCardMst", 
								"jobCardNo", jobCardNo, "version", DECENDING);
				JobCardMst jobCardMst = new JobCardMst();
				if(jobCardMstList.size() > 0){
					jobCardMst = jobCardMstList.get(0);
				}
				List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>)(Object)
						commonService.getObjectListByTwoColumn("JobCardDtl", 
								"itemCode", csRequisitionDtlDb.getItemCode(),
								"jobCardMst.id", jobCardMst.getId()+"");
				JobCardDtl jobCardDtl = new JobCardDtl();
				if(jobCardDtlList.size() > 0){
					jobCardDtl = jobCardDtlList.get(0);
					Double remaingQty = jobCardDtl.getRemainningQuantity();
					jobCardDtl.setRemainningQuantity(remaingQty - jobCardValue );					
					jobCardDtl.setModifiedBy(userName);
					jobCardDtl.setModifiedDate(now);
					commonService.saveOrUpdateModelObjectToDB(jobCardDtl);
				}
				
				List<WsJobSummary> wsJobSummaryDbList = (List<WsJobSummary>) (Object) commonService
						.getObjectListByTwoColumn("WsJobSummary", "itemCode",
								csRequisitionDtlDb.getItemCode(), "contractNo",
								jobCardMst.getContractNo());
				
				WsJobSummary wsJobSummary = new WsJobSummary();
				if (wsJobSummaryDbList.size() > 0 && wsJobSummaryDbList!= null) {
					wsJobSummary = wsJobSummaryDbList.get(0);
					wsJobSummary.setRemainingQty(wsJobSummary.getRemainingQty() - jobCardValue);
					wsJobSummary.setModifiedBy(userName);
					wsJobSummary.setModifiedDate(now);
					commonService.saveOrUpdateModelObjectToDB(wsJobSummary);
				}
				//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			}
			
			csRequisitionDtlDb.setModifiedBy(userName);
			csRequisitionDtlDb.setModifiedDate(now);
			csRequisitionDtlDb.setCnWsStatus(DONE);
			commonService.saveOrUpdateModelObjectToDB(csRequisitionDtlDb);
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
	
	@RequestMapping(value = "/ws/xf/receivedRequisitionFromWs.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView receivedRequisitionFromWs(CentralStoreRequisitionMst csRequisitionMstBean) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			CentralStoreRequisitionMst csRequisitionMstDb = (CentralStoreRequisitionMst)
					commonService.getAnObjectByAnyUniqueColumn("CentralStoreRequisitionMst", "requisitionNo", csRequisitionMstBean.getRequisitionNo());
			
			csRequisitionMstDb.setReceived(true);
			csRequisitionMstDb.setModifiedBy(commonService.getAuthUserName());
			csRequisitionMstDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csRequisitionMstDb);

			return this.getItemRequisitionList();

		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWSCN", model);
		}
	}
	
	@RequestMapping(value = "/ws/xf/contRepList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorRepresentativeListGet(
			ContractorRepresentive contractorRepresentive) {
		return finalContractorRepresentativeList(contractorRepresentive);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/contRepList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView finalContractorRepresentativeList(
			ContractorRepresentive contractorRepresentive) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		List<ContractorRepresentive> contractorRepresentiveList = (List<ContractorRepresentive>) (Object) commonService
				.getObjectListByAnyColumn("ContractorRepresentive",
						"contractor.id", contractorRepresentive.getId() + "");
		for(ContractorRepresentive c:contractorRepresentiveList){
			try {
				c.setPicture(this.getImagePath(c.getPicture()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						contractorRepresentive.getId() + "");
		
		

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("contractorRepresentiveList", contractorRepresentiveList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("deptAddress",
				department.getAddress() + ", " + department.getContactNo());

		return new ModelAndView("workshop/wsContractorRepresentativeList",
				model);
	}
	
	private String getImagePath(String path) throws Exception{
		
		if (path==null) {

			File file = new File(path);
			
			if (file.length() > 0) {
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int b;
				byte[] buffer = new byte[1024];
				while ((b = fis.read(buffer)) != -1) {
					bos.write(buffer, 0, b);
				}
				byte[] fileBytes = bos.toByteArray();
				fis.close();
				bos.close();

				byte[] encoded = Base64.encodeBase64(fileBytes);
				String encodedString = new String(encoded);
				return encodedString;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/jobsList.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobList(JobCardMst jobCardMst) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor", "id",
						jobCardMst.getId() + "");

		List<JobCardMst> jobCardMstList = (List<JobCardMst>) (Object) commonService
				.getObjectListByAnyColumn("JobCardMst", "contractNo",
						contractor.getContractNo() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobCardMstList", jobCardMstList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		
		return new ModelAndView("workshop/wsContractorJobListShow", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ws/xf/jobDetails.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView contractorWiseJobDetails(JobCardDtl jobCardDtl) {
		String userName = commonService.getAuthUserName();

		//String roleName = commonService.getAuthRoleName();

		AuthUser authUser = userService.getAuthUserByUserId(userName);

		Departments department = departmentsService
				.getDepartmentByDeptId(authUser.getDeptId());

		JobCardMst jobCardMst = (JobCardMst) commonService
				.getAnObjectByAnyUniqueColumn("JobCardMst", "id",
						jobCardDtl.getId() + "");

		Contractor contractor = (Contractor) commonService
				.getAnObjectByAnyUniqueColumn(
						"com.ibcs.desco.contractor.model.Contractor",
						"contractNo", jobCardMst.getContractNo());

		List<JobCardDtl> jobCardDtlList = (List<JobCardDtl>) (Object) commonService
				.getObjectListByAnyColumn("JobCardDtl", "jobCardMst.id",
						jobCardDtl.getId() + "");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jobCardDtlList", jobCardDtlList);
		model.put("deptName", department.getDeptName());
		model.put("contractor", contractor);
		model.put("jobCardMst", jobCardMst);
		
		return new ModelAndView("workshop/wsContractorJobDtlListShow",
				model);
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/ws/xf/wsConXFormerAllocation.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wsConXFormerAllocationSingleContractor(Contractor contractor) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			List<WsCnAllocation> wsCnAllocationListNew = (List<WsCnAllocation>) (Object) commonService
					.getObjectListByAnyColumn("WsCnAllocation", "contractorMst.id", contractor.getId().toString());
					
			model.put("wsCnAllocationList", wsCnAllocationListNew);

			return new ModelAndView(
					"workshop/transformer/wsCnXFormerAllocation", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS", model);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/ws/updateWsContractorRep.do", method = RequestMethod.POST)
	public String updateWsContractorRep(@RequestBody String json)
			throws Exception {
		Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(json);
		String toJson = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		if (isJson) {
			ContractorRepresentive bean = gson.fromJson(json,
					ContractorRepresentive.class);
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			   Date beanDate = df.parse(bean.getEndDateTxt());

			ContractorRepresentive csDtlDb = (ContractorRepresentive) commonService
					.getAnObjectByAnyUniqueColumn("ContractorRepresentive",
							"id", "" + bean.getId());
			// remove by Taleb
			//double itemQty = csDtlDb.getQuantityRequired();

			csDtlDb.setEndDate(beanDate);
			csDtlDb.setModifiedBy(commonService.getAuthUserName());
			csDtlDb.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(csDtlDb);

		
			toJson = ow.writeValueAsString("success");
		} else {
			Thread.sleep(2000);
			toJson = ow.writeValueAsString("fail");
		}
		return toJson;
	}
}
