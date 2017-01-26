package com.ibcs.desco.workshop.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.admin.model.AuthUser;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.service.DepartmentsService;
import com.ibcs.desco.admin.service.UserService;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.AuctionMst;
import com.ibcs.desco.workshop.model.MeterTestingReport;
import com.ibcs.desco.workshop.service.MeterTestingReportService;

/*
 * Author: Abu Taleb And Ihteshamul Alam
 * Programmer, IBCS
 */

@Controller
@PropertySource("classpath:common.properties")
public class MeterTestReportController extends Constrants {

	@Autowired
	CommonService commonService;

	@Autowired
	UserService userService;

	@Autowired
	MeterTestingReportService meterTestingReportService;

	@Autowired
	DepartmentsService departmentsService;

	@Value("${desco.ws.mt.htct.new}")
	private String mtHtctNewDocNo;

	@Value("${desco.ws.mt.ltct.new}")
	private String mtLtctNewDocNo;

	@Value("${desco.ws.mt.wc.all}")
	private String mtWCAllDocNo;

	@Value("${desco.ws.mt.ltct.htct.old}")
	private String mtHtclLtctOldDocNo;

	@Value("${desco.ws.mt.ctpt.all}")
	private String mtCtPtAllDocNo;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/htCtMeterTestReportForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView htCtMeterTestReportForm(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);
			return new ModelAndView(
					"workshop/meterTestReport/htCtMeterTestReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	@RequestMapping(value = "/mtr/meterTestReportSave.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportSave(
			@ModelAttribute("meterTestingReport") MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Departments department = departmentsService
					.getDepartmentByDeptId(meterTestingReport.getDeptId());
			String userName = commonService.getAuthUserName();
			meterTestingReport.setCreatedBy(userName);
			meterTestingReport.setCreatedDate(new Date());
			meterTestingReport.setId(null);
			meterTestingReport.setDepartment(department);
			meterTestingReport.setReportDate(new Date());

			if (meterTestingReport.getMeterCategory().equalsIgnoreCase(NEW)
					&& meterTestingReport.getMeterType().equals(HTCT)) {
				meterTestingReport.setDocumentNo(mtHtctNewDocNo);
			} else if (meterTestingReport.getMeterCategory().equalsIgnoreCase(
					NEW)
					&& meterTestingReport.getMeterType().equals(LTCT)) {
				meterTestingReport.setDocumentNo(mtLtctNewDocNo);
			} else if (meterTestingReport.getMeterType().equals(WC)) {
				meterTestingReport.setDocumentNo(mtWCAllDocNo);
			} else if (meterTestingReport.getMeterCategory().equalsIgnoreCase(
					OLD)
					&& (meterTestingReport.getMeterType().equals(HTCT) || meterTestingReport
							.getMeterType().equals(LTCT))) {
				meterTestingReport.setDocumentNo(mtHtclLtctOldDocNo);
			} else if (meterTestingReport.getMeterType().equals(CT)
					|| meterTestingReport.getMeterType().equals(PT)) {
				meterTestingReport.setDocumentNo(mtCtPtAllDocNo);
			} else {
				meterTestingReport.setDocumentNo("");
			}
			meterTestingReportService
					.saveOrUpdateMeterTestingReport(meterTestingReport);
			return new ModelAndView("redirect:/mtr/meterTestReportList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/ltCtMeterTestReportForm.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView ltCtMeterTestReportForm(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);
			return new ModelAndView(
					"workshop/meterTestReport/ltCtMeterTestReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// List
	@RequestMapping(value = "/mtr/meterTestReportList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportList(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*
			 * List<MeterTestingReport> meterTestingReportList =
			 * meterTestingReportService .listMeterTestingReport();
			 */
			List<MeterTestingReport> meterTestingReportList = meterTestingReportService
					.listMeterTestingReportByFinalSubmit(false);
			model.put("deptName", department.getDeptName());
			model.put("meterTestingReportList", meterTestingReportList);
			return new ModelAndView(
					"workshop/meterTestReport/meterTestReportList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// List
	@RequestMapping(value = "/mtr/meterTestReportFinalList.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportFinalList(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			/*
			 * List<MeterTestingReport> meterTestingReportList =
			 * meterTestingReportService .listMeterTestingReport();
			 */
			List<MeterTestingReport> meterTestingReportList = meterTestingReportService
					.listMeterTestingReportByFinalSubmit(true);
			model.put("deptName", department.getDeptName());
			model.put("meterTestingReportList", meterTestingReportList);
			return new ModelAndView(
					"workshop/meterTestReport/meterTestReportList", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Show
	@RequestMapping(value = "/mtr/meterTestReportShow.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportShow(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			MeterTestingReport meterTestingReportDb = meterTestingReportService
					.getMeterTestingReport(meterTestingReport.getId());

			if (meterTestingReportDb != null) {
				model.put("deptName", department.getDeptName());
				model.put("meterTestingReport", meterTestingReportDb);

				if (meterTestingReportDb.getMeterCategory().equalsIgnoreCase(
						NEW)
						&& meterTestingReportDb.getMeterType().equals(HTCT)) {
					return new ModelAndView(
							"workshop/meterTestReport/htCtMeterTestReportShow",
							model);
				} else if (meterTestingReportDb.getMeterCategory()
						.equalsIgnoreCase(NEW)
						&& meterTestingReportDb.getMeterType().equals(LTCT)) {
					return new ModelAndView(
							"workshop/meterTestReport/ltCtMeterTestReportShow",
							model);
				} else if (meterTestingReportDb.getMeterType().equals(WC)) {
					return new ModelAndView(
							"workshop/meterTestReport/wcMeterTestReportShow",
							model);
				} else if (meterTestingReportDb.getMeterCategory()
						.equalsIgnoreCase(OLD)
						&& (meterTestingReportDb.getMeterType().equals(HTCT) || meterTestingReportDb
								.getMeterType().equals(LTCT))) {
					return new ModelAndView(
							"workshop/meterTestReport/oldHtctLtctMeterTestReportShow",
							model);
				} else if (meterTestingReportDb.getMeterType().equals(CT)
						|| meterTestingReportDb.getMeterType().equals(PT)) {
					return new ModelAndView(
							"workshop/meterTestReport/ctptMeterTestReportShow",
							model);
				} else {
					model.put("errorMsg", "Show page load Problem...");
					return new ModelAndView("workshop/errorWS");
				}
			} else {
				model.put("errorMsg", "Show page load Problem...");
				return new ModelAndView("workshop/errorWS");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Edit Form
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/meterTestReportEdit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportEdit(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			MeterTestingReport meterTestingReportDb = meterTestingReportService
					.getMeterTestingReport(meterTestingReport.getId());

			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);

			if (meterTestingReportDb != null) {
				model.put("deptName", department.getDeptName());
				model.put("meterTestingReport", meterTestingReportDb);

				if (meterTestingReportDb.getMeterCategory().equalsIgnoreCase(
						NEW)
						&& meterTestingReportDb.getMeterType().equals(HTCT)) {
					return new ModelAndView(
							"workshop/meterTestReport/htCtMeterTestReportEdit",
							model);
				} else if (meterTestingReportDb.getMeterCategory()
						.equalsIgnoreCase(NEW)
						&& meterTestingReportDb.getMeterType().equals(LTCT)) {
					return new ModelAndView(
							"workshop/meterTestReport/ltCtMeterTestReportEdit",
							model);
				} else if (meterTestingReportDb.getMeterType().equals(WC)) {
					return new ModelAndView(
							"workshop/meterTestReport/wcMeterTestReportEdit",
							model);
				} else if (meterTestingReportDb.getMeterCategory()
						.equalsIgnoreCase(OLD)
						&& (meterTestingReportDb.getMeterType().equals(HTCT) || meterTestingReportDb
								.getMeterType().equals(LTCT))) {
					return new ModelAndView(
							"workshop/meterTestReport/oldHtctLtctMeterTestReportEdit",
							model);
				} else if (meterTestingReportDb.getMeterType().equals(CT)
						|| meterTestingReportDb.getMeterType().equals(PT)) {
					return new ModelAndView(
							"workshop/meterTestReport/ctptMeterTestReportEdit",
							model);
				} else {
					model.put("errorMsg", "Edit page load Problem...");
					return new ModelAndView("workshop/errorWS");
				}
			} else {
				model.put("errorMsg", "Edit page load Problem...");
				return new ModelAndView("workshop/errorWS");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Whole Current Meter (New/Used) Form
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/wcnuMeterTestReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView wcnuMeterTestReport(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);
			return new ModelAndView(
					"workshop/meterTestReport/wcnuMeterTestReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Used Meter Test Report
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/usedMeterTestReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView usedMeterTestReport(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);
			return new ModelAndView(
					"workshop/meterTestReport/usedMeterTestReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Spare CT/PT Test Report
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mtr/spareCtPtMeterTestReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView spareCtPtMeterTestReport(
			MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Departments> depts = (List<Departments>) (Object) commonService
					.getObjectListByAnyColumn("Departments", "parent",
							SND_PARENT_CODE);

			model.put("depts", depts);
			return new ModelAndView(
					"workshop/meterTestReport/spareCtPtMeterTestReport", model);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	@RequestMapping(value = "/mtr/checkTrackingNo.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public String checkTrackingNo(MeterTestingReport meterTestingReport) {
		String response = "";

		if (meterTestingReport.getTrackingNo().equals("")) {
			response = "failed";
		} else {
			MeterTestingReport mtr = (MeterTestingReport) commonService
					.getAnObjectByAnyUniqueColumn("MeterTestingReport",
							"trackingNo", meterTestingReport.getTrackingNo());

			if (mtr == null) {
				response = "success";
			} else {
				response = "unsuccess";
			}
		}
		return response;
	}

	@RequestMapping(value = "/mtr/meterTestReportUpdate.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportUpdate(
			@ModelAttribute("meterTestingReport") MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (meterTestingReport.getId() > 0) {
				MeterTestingReport meterTestingReportDb = meterTestingReportService
						.getMeterTestingReport(meterTestingReport.getId());

				Departments department = departmentsService
						.getDepartmentByDeptId(meterTestingReport.getDeptId());
				String userName = commonService.getAuthUserName();

				meterTestingReport.setCreatedBy(meterTestingReportDb
						.getCreatedBy());
				meterTestingReport.setCreatedDate(meterTestingReportDb
						.getCreatedDate());
				meterTestingReport.setDepartment(department);
				meterTestingReport.setReportDate(meterTestingReportDb
						.getReportDate());
				meterTestingReport.setModifiedBy(userName);
				meterTestingReport.setModifiedDate(new Date());
				meterTestingReport.setTrackingNo(meterTestingReportDb
						.getTrackingNo());
				meterTestingReportService
						.saveOrUpdateMeterTestingReport(meterTestingReport);
			}
			return new ModelAndView("redirect:/mtr/meterTestReportList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	@RequestMapping(value = "/mtr/meterTestReportFinalSubmit.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestReportFinalSubmit(
			@ModelAttribute("meterTestingReport") MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (meterTestingReport.getId() > 0) {
				MeterTestingReport meterTestingReportDb = meterTestingReportService
						.getMeterTestingReport(meterTestingReport.getId());

				String userName = commonService.getAuthUserName();

				meterTestingReportDb.setModifiedBy(userName);
				meterTestingReportDb.setModifiedDate(new Date());
				meterTestingReportDb.setFinalSubmit(true);
				meterTestingReportService
						.saveOrUpdateMeterTestingReport(meterTestingReportDb);
				return new ModelAndView(
						"redirect:/mtr/meterTestingReport.do?id="
								+ meterTestingReportDb.getId() + "");
			}
			return new ModelAndView("redirect:/mtr/meterTestReportList.do");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	// Reports
	@RequestMapping(value = "/mtr/meterTestingReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView meterTestingReport(MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String userName = commonService.getAuthUserName();

			AuthUser authUser = userService.getAuthUserByUserId(userName);

			Departments department = departmentsService
					.getDepartmentByDeptId(authUser.getDeptId());

			MeterTestingReport meterTestingReportDb = meterTestingReportService
					.getMeterTestingReport(meterTestingReport.getId());

			if (meterTestingReportDb != null) {
				model.put("deptName", department.getDeptName());
				model.put("meterTestingReport", meterTestingReportDb);
				if (meterTestingReportDb.isFinalSubmit()) {

					if (meterTestingReportDb.getMeterCategory()
							.equalsIgnoreCase(NEW)
							&& meterTestingReportDb.getMeterType().equals(HTCT)) {
						return new ModelAndView(
								"workshop/meterTestReport/reports/htCtMeterTestReportNew",
								model);
					} else if (meterTestingReportDb.getMeterCategory()
							.equalsIgnoreCase(NEW)
							&& meterTestingReportDb.getMeterType().equals(LTCT)) {
						return new ModelAndView(
								"workshop/meterTestReport/reports/ltCtMeterTestReportNew",
								model);
					} else if (meterTestingReportDb.getMeterType().equals(WC)) {
						return new ModelAndView(
								"workshop/meterTestReport/reports/wcMeterTestReportAll",
								model);
					} else if (meterTestingReportDb.getMeterCategory()
							.equalsIgnoreCase(OLD)
							&& (meterTestingReportDb.getMeterType()
									.equals(HTCT) || meterTestingReportDb
									.getMeterType().equals(LTCT))) {
						return new ModelAndView(
								"workshop/meterTestReport/reports/htctLtctMeterTestReportOld",
								model);
					} else if (meterTestingReportDb.getMeterType().equals(CT)
							|| meterTestingReportDb.getMeterType().equals(PT)) {
						return new ModelAndView(
								"workshop/meterTestReport/reports/ctptMeterTestReportAll",
								model);
					} else {
						model.put("errorMsg", "Show page load Problem...");
						return new ModelAndView("workshop/errorWS");
					}
				} else {
					model.put("errorMsg",
							"Sorry!!! This Meter Testing Report is not Finally Submitted...");
					return new ModelAndView("workshop/errorWS");
				}
			} else {
				return new ModelAndView(
						"redirect:/mtr/meterTestReportFinalList.do");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	@RequestMapping(value = "mtr/deleteTestReport.do", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteTestReport(MeterTestingReport meterTestingReport) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*
			 * MeterTestingReport mtrTestReport = ( MeterTestingReport )
			 * commonService.getAnObjectByAnyUniqueColumn("MeterTestingReport",
			 * "id", meterTestingReport.getId().toString() );
			 * meterTestingReportService
			 * .deleteMeterTestingReport(mtrTestReport);
			 */

			commonService.deleteAnObjectById("MeterTestingReport",
					meterTestingReport.getId());

			return new ModelAndView("redirect:/mtr/meterTestReportList.do");
		} catch (Exception e) {

			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("workshop/errorWS");
		}
	}

	//@RequestMapping(value = "/mtr/testingByAmpersion.do", method = RequestMethod.GET)
	//@PreAuthorize("isAuthenticated()")
	public void testingByAmpersion(MeterTestingReport meterTestingReport) {
		@SuppressWarnings("unused")
		String response = "";

		AuctionMst mtr = (AuctionMst) meterTestingReportService
				.getAuctionMstByCreatedBy("A&B");
		if (mtr != null) {
			System.out.print(mtr.getCreatedBy());
			response = "success";
		} else {
			response = "unsuccess";
		}
	}
	
	@RequestMapping(value = "/report/monthlyMeterTestingReport.do", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView monthlyMeterTestingReport() {
		return new ModelAndView(
				"workshop/reports/monthlyMeterTestingReport");
	}
}
