package com.ibcs.desco.workshop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ibcs.desco.admin.model.Departments;
/*
 * Author: Abu Taleb
 * Programmer, IBCS
 */
@Entity
@Table(name = "METER_TESTING_REPORT")
public class MeterTestingReport {
	// primary key of table as ID start
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "METER_TEST_REP_ID_SEQ")
	@SequenceGenerator(name = "METER_TEST_REP_ID_SEQ", sequenceName = "METER_TEST_REP_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Integer id = 0;
	// primary key of table as ID end
	// master and unique field start
	@Column(name = "tracking_no", unique = true)
	private String trackingNo;

	@Column(name = "meter_unit_source")
	private String meterUnitSource;

	@Column(name = "cmtl_sl_no")
	private String cmtlSlNo;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "consumer_details")
	private String consumerDetails;

	@ManyToOne
	@JoinColumn(name = "snd_Division")
	private Departments department;

	@Transient
	private String deptId;

	@Column(name = "meter_ct_ratio")
	private String meterCTRatio;

	@Column(name = "meter_pt_ratio")
	private String meterPTRatio;

	@Column(name = "report_date")
	// @Temporal(TemporalType.DATE)
	// @DateTimeFormat(pattern = "dd-mm-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reportDate;

	@Column(name = "physical_observation")
	private String physicalObservation;

	@Column(name = "meter_testing_inst")
	private String meterTestinInstruments;

	@Column(name = "starting_test_rpt")
	private String startingTestReport;

	@Column(name = "no_load_test_rpt")
	private String noLoadTestReport;

	@Column(name = "dial_test_rpt")
	private String dialTestReport;

	@Column(name = "mr_as_fnd_peak")
	private String mrAsFoundPeak;

	@Column(name = "mr_as_fnd_off_peak")
	private String mrAsFoundOffPeak;

	@Column(name = "mr_as_fnd_md")
	private String mrAsFoundMD;

	@Column(name = "mr_as_fnd_re_active")
	private String mrAsFoundReActive;

	@Column(name = "mr_as_lft_peak")
	private String mrAsLeftPeak;

	@Column(name = "mr_as_lft_off_peak")
	private String mrAsLeftOffPeak;

	@Column(name = "mr_as_lft_md")
	private String mrAsLeftMD;

	@Column(name = "mr_as_lft_re_active")
	private String mrAsLeftReActive;

	@Column(name = "seal_info", length = 2000)
	private String sealInfo;

	@Column(name = "overall_remarks", length = 2000)
	private String overallRemarks;

	@Column(name = "ct_pt_remarks", length = 2000)
	private String ctPtRemarks;

	@Column(name = "dmf_of_meter")
	private String dmfOfMeter;

	@Column(name = "omf")
	private String omf;

	@Column(name = "document_no")
	private String documentNo;

	// for old and new type meter
	@Column(name = "meter_category")
	private String meterCategory;
	// master and unique field end
	// name plate date field start
	@Column(name = "meter_type")
	private String meterType;

	@Column(name = "meter_no")
	private String meterNo;

	@Column(name = "manufacture_name")
	private String manufactureName;

	@Column(name = "voltage_rating")
	private String voltageRating;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "current_rating")
	private String currentRating;

	@Column(name = "manufacture_year")
	private String manufacturingYear;

	@Column(name = "accuracy_class_active")
	private String accuracyClassActive;

	@Column(name = "accuracy_class_reactive")
	private String accuracyClassReActive;

	@Column(name = "meter_constant")
	private String meterConstant;

	@Column(name = "connection_type")
	private String connectionType;
	// name plate date field end

	// defferent power factor on defferent load start
	@Column(name = "dpf_10_lmax")
	private String dpf10lmax;

	@Column(name = "dpf_05L_lmax")
	private String dpf05Llmax;

	@Column(name = "dpf_08C_lmax")
	private String dpf08Clmax;

	@Column(name = "dpf_10_lb12")
	private String dpf10lb12;

	@Column(name = "dpf_05L_lb12")
	private String dpf05Llb12;

	@Column(name = "dpf_08C_lb12")
	private String dpf08Clb12;

	@Column(name = "dpf_l0_lb")
	private String dpf10lb;

	@Column(name = "dpf_05L_lb")
	private String dpf05Llb;

	@Column(name = "dpf_08C_lb")
	private String dpf08Clb;

	@Column(name = "dpf_l0_lb05")
	private String dpf10lb05;

	@Column(name = "dpf_05L_lb05")
	private String dpf05Llb05;

	@Column(name = "dpf_08C_lb05")
	private String dpf08Clb05;

	@Column(name = "dpf_l0_lb02")
	private String dpf10lb02;

	@Column(name = "dpf_05L_lb02")
	private String dpf05Llb02;

	@Column(name = "dpf_08C_lb02")
	private String dpf08Clb02;

	@Column(name = "dpf_l0_lb01")
	private String dpf10lb01;

	@Column(name = "dpf_05L_lb01")
	private String dpf05Llb01;

	@Column(name = "dpf_08C_lb01")
	private String dpf08Clb01;

	@Column(name = "dpf_l0_lb005")
	private String dpf10lb005;

	@Column(name = "dpf_05L_lb005")
	private String dpf05Llb005;

	@Column(name = "dpf_08C_lb005")
	private String dpf08Clb005;

	@Column(name = "dpf_l0_remarks")
	private String dpf10Remarks;

	@Column(name = "dpf_05L_remarks")
	private String dpf05LRemarks;

	@Column(name = "dpf_08C_remarks")
	private String dpf08CRemarks;
	// defferent power factor on defferent load end

	// line current Transformer Start
	@Column(name = "ct_SlNo_Phase_1R")
	private String ctSlNoPhase1R;

	@Column(name = "ct_SlNo_Phase_2y")
	private String ctSlNoPhase2Y;

	@Column(name = "ct_SlNo_Phase_3b")
	private String ctSlNoPhase3B;

	@Column(name = "ct_brand_mfg_name_phase_1r")
	private String ctBrandMfgNamePhase1R;

	@Column(name = "ct_brand_mfg_name_phase_2y")
	private String ctBrandMfgNamePhase2Y;

	@Column(name = "ct_brand_mfg_name_phase_3b")
	private String ctBrandMfgNamePhase3B;

	@Column(name = "ct_type_model_phase_1r")
	private String ctTypeModelPhase1R;

	@Column(name = "ct_type_model_phase_2y")
	private String ctTypeModelPhase2Y;

	@Column(name = "ct_type_model_phase_3b")
	private String ctTypeModelPhase3B;

	@Column(name = "ct_burden_phase_1r")
	private String ctBurdenPhase1R;

	@Column(name = "ct_burden_phase_2y")
	private String ctBurdenPhase2Y;

	@Column(name = "ct_burden_phase_3b")
	private String ctBurdenPhase3B;

	@Column(name = "ct_acuracy_phase_1r")
	private String ctAccuracyPhase1R;

	@Column(name = "ct_acuracy_phase_2y")
	private String ctAccuracyPhase2Y;

	@Column(name = "ct_acuracy_phase_3b")
	private String ctAccuracyPhase3B;

	@Column(name = "ct_ratio_phase_1r")
	private String ctRatioPhase1R;

	@Column(name = "ct_ratio_phase_2y")
	private String ctRatioPhase2Y;

	@Column(name = "ct_ratio_phase_3b")
	private String ctRatioPhase3B;

	@Column(name = "ct_megger_test_rpt_phase_1r")
	private String ctMeggerTestRptPhase1R;

	@Column(name = "ct_megger_test_rpt_phase_2y")
	private String ctMeggerTestRptPhase2Y;

	@Column(name = "ct_megger_test_rpt_phase_3b")
	private String ctMeggerTestRptPhase3B;

	// line current Transformer end

	// line potential Transformer Start
	@Column(name = "pt_SlNo_Phase_1R")
	private String ptSlNoPhase1R;

	@Column(name = "pt_SlNo_Phase_2y")
	private String ptSlNoPhase2Y;

	@Column(name = "pt_SlNo_Phase_3b")
	private String ptSlNoPhase3B;

	@Column(name = "pt_brand_mfg_name_phase_1r")
	private String ptBrandMfgNamePhase1R;

	@Column(name = "pt_brand_mfg_name_phase_2y")
	private String ptBrandMfgNamePhase2Y;

	@Column(name = "pt_brand_mfg_name_phase_3b")
	private String ptBrandMfgNamePhase3B;

	@Column(name = "pt_type_model_phase_1r")
	private String ptTypeModelPhase1R;

	@Column(name = "pt_type_model_phase_2y")
	private String ptTypeModelPhase2Y;

	@Column(name = "pt_type_model_phase_3b")
	private String ptTypeModelPhase3B;

	@Column(name = "pt_burden_phase_1r")
	private String ptBurdenPhase1R;

	@Column(name = "pt_burden_phase_2y")
	private String ptBurdenPhase2Y;

	@Column(name = "pt_burden_phase_3b")
	private String ptBurdenPhase3B;

	@Column(name = "pt_acuracy_phase_1r")
	private String ptAccuracyPhase1R;

	@Column(name = "pt_acuracy_phase_2y")
	private String ptAccuracyPhase2Y;

	@Column(name = "pt_acuracy_phase_3b")
	private String ptAccuracyPhase3B;

	@Column(name = "pt_ratio_phase_1r")
	private String ptRatioPhase1R;

	@Column(name = "pt_ratio_phase_2y")
	private String ptRatioPhase2Y;

	@Column(name = "pt_ratio_phase_3b")
	private String ptRatioPhase3B;

	@Column(name = "pt_megger_test_rpt_phase_1r")
	private String ptMeggerTestRptPhase1R;

	@Column(name = "pt_megger_test_rpt_phase_2y")
	private String ptMeggerTestRptPhase2Y;

	@Column(name = "pt_megger_test_rpt_phase_3b")
	private String ptMeggerTestRptPhase3B;

	// line potential Transformer end

	// others field start
	@Column(name = "is_final_submit")
	private boolean finalSubmit = false;

	@Column(name = "isActive")
	private boolean active = true;

	@Column(name = "remarks", length = 2000)
	private String remarks;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	// others field end

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getMeterUnitSource() {
		return meterUnitSource;
	}

	public void setMeterUnitSource(String meterUnitSource) {
		this.meterUnitSource = meterUnitSource;
	}

	public String getCmtlSlNo() {
		return cmtlSlNo;
	}

	public void setCmtlSlNo(String cmtlSlNo) {
		this.cmtlSlNo = cmtlSlNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getConsumerDetails() {
		return consumerDetails;
	}

	public void setConsumerDetails(String consumerDetails) {
		this.consumerDetails = consumerDetails;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getMeterCTRatio() {
		return meterCTRatio;
	}

	public void setMeterCTRatio(String meterCTRatio) {
		this.meterCTRatio = meterCTRatio;
	}

	public String getMeterPTRatio() {
		return meterPTRatio;
	}

	public void setMeterPTRatio(String meterPTRatio) {
		this.meterPTRatio = meterPTRatio;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getPhysicalObservation() {
		return physicalObservation;
	}

	public void setPhysicalObservation(String physicalObservation) {
		this.physicalObservation = physicalObservation;
	}

	public String getMeterTestinInstruments() {
		return meterTestinInstruments;
	}

	public void setMeterTestinInstruments(String meterTestinInstruments) {
		this.meterTestinInstruments = meterTestinInstruments;
	}

	public String getStartingTestReport() {
		return startingTestReport;
	}

	public void setStartingTestReport(String startingTestReport) {
		this.startingTestReport = startingTestReport;
	}

	public String getNoLoadTestReport() {
		return noLoadTestReport;
	}

	public void setNoLoadTestReport(String noLoadTestReport) {
		this.noLoadTestReport = noLoadTestReport;
	}

	public String getDialTestReport() {
		return dialTestReport;
	}

	public void setDialTestReport(String dialTestReport) {
		this.dialTestReport = dialTestReport;
	}

	public String getMrAsFoundPeak() {
		return mrAsFoundPeak;
	}

	public void setMrAsFoundPeak(String mrAsFoundPeak) {
		this.mrAsFoundPeak = mrAsFoundPeak;
	}

	public String getMrAsFoundOffPeak() {
		return mrAsFoundOffPeak;
	}

	public void setMrAsFoundOffPeak(String mrAsFoundOffPeak) {
		this.mrAsFoundOffPeak = mrAsFoundOffPeak;
	}

	public String getMrAsFoundMD() {
		return mrAsFoundMD;
	}

	public void setMrAsFoundMD(String mrAsFoundMD) {
		this.mrAsFoundMD = mrAsFoundMD;
	}

	public String getMrAsFoundReActive() {
		return mrAsFoundReActive;
	}

	public void setMrAsFoundReActive(String mrAsFoundReActive) {
		this.mrAsFoundReActive = mrAsFoundReActive;
	}

	public String getMrAsLeftPeak() {
		return mrAsLeftPeak;
	}

	public void setMrAsLeftPeak(String mrAsLeftPeak) {
		this.mrAsLeftPeak = mrAsLeftPeak;
	}

	public String getMrAsLeftOffPeak() {
		return mrAsLeftOffPeak;
	}

	public void setMrAsLeftOffPeak(String mrAsLeftOffPeak) {
		this.mrAsLeftOffPeak = mrAsLeftOffPeak;
	}

	public String getMrAsLeftMD() {
		return mrAsLeftMD;
	}

	public void setMrAsLeftMD(String mrAsLeftMD) {
		this.mrAsLeftMD = mrAsLeftMD;
	}

	public String getMrAsLeftReActive() {
		return mrAsLeftReActive;
	}

	public void setMrAsLeftReActive(String mrAsLeftReActive) {
		this.mrAsLeftReActive = mrAsLeftReActive;
	}

	public String getSealInfo() {
		return sealInfo;
	}

	public void setSealInfo(String sealInfo) {
		this.sealInfo = sealInfo;
	}

	public String getOverallRemarks() {
		return overallRemarks;
	}

	public void setOverallRemarks(String overallRemarks) {
		this.overallRemarks = overallRemarks;
	}

	public String getCtPtRemarks() {
		return ctPtRemarks;
	}

	public void setCtPtRemarks(String ctPtRemarks) {
		this.ctPtRemarks = ctPtRemarks;
	}

	public String getDmfOfMeter() {
		return dmfOfMeter;
	}

	public void setDmfOfMeter(String dmfOfMeter) {
		this.dmfOfMeter = dmfOfMeter;
	}

	public String getOmf() {
		return omf;
	}

	public void setOmf(String omf) {
		this.omf = omf;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getMeterCategory() {
		return meterCategory;
	}

	public void setMeterCategory(String meterCategory) {
		this.meterCategory = meterCategory;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getManufactureName() {
		return manufactureName;
	}

	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public String getVoltageRating() {
		return voltageRating;
	}

	public void setVoltageRating(String voltageRating) {
		this.voltageRating = voltageRating;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getCurrentRating() {
		return currentRating;
	}

	public void setCurrentRating(String currentRating) {
		this.currentRating = currentRating;
	}

	public String getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(String manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}

	public String getAccuracyClassActive() {
		return accuracyClassActive;
	}

	public void setAccuracyClassActive(String accuracyClassActive) {
		this.accuracyClassActive = accuracyClassActive;
	}

	public String getAccuracyClassReActive() {
		return accuracyClassReActive;
	}

	public void setAccuracyClassReActive(String accuracyClassReActive) {
		this.accuracyClassReActive = accuracyClassReActive;
	}

	public String getMeterConstant() {
		return meterConstant;
	}

	public void setMeterConstant(String meterConstant) {
		this.meterConstant = meterConstant;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getDpf10lmax() {
		return dpf10lmax;
	}

	public void setDpf10lmax(String dpf10lmax) {
		this.dpf10lmax = dpf10lmax;
	}

	public String getDpf05Llmax() {
		return dpf05Llmax;
	}

	public void setDpf05Llmax(String dpf05Llmax) {
		this.dpf05Llmax = dpf05Llmax;
	}

	public String getDpf08Clmax() {
		return dpf08Clmax;
	}

	public void setDpf08Clmax(String dpf08Clmax) {
		this.dpf08Clmax = dpf08Clmax;
	}

	public String getDpf10lb12() {
		return dpf10lb12;
	}

	public void setDpf10lb12(String dpf10lb12) {
		this.dpf10lb12 = dpf10lb12;
	}

	public String getDpf05Llb12() {
		return dpf05Llb12;
	}

	public void setDpf05Llb12(String dpf05Llb12) {
		this.dpf05Llb12 = dpf05Llb12;
	}

	public String getDpf08Clb12() {
		return dpf08Clb12;
	}

	public void setDpf08Clb12(String dpf08Clb12) {
		this.dpf08Clb12 = dpf08Clb12;
	}

	public String getDpf10lb() {
		return dpf10lb;
	}

	public void setDpf10lb(String dpf10lb) {
		this.dpf10lb = dpf10lb;
	}

	public String getDpf05Llb() {
		return dpf05Llb;
	}

	public void setDpf05Llb(String dpf05Llb) {
		this.dpf05Llb = dpf05Llb;
	}

	public String getDpf08Clb() {
		return dpf08Clb;
	}

	public void setDpf08Clb(String dpf08Clb) {
		this.dpf08Clb = dpf08Clb;
	}

	public String getDpf10lb05() {
		return dpf10lb05;
	}

	public void setDpf10lb05(String dpf10lb05) {
		this.dpf10lb05 = dpf10lb05;
	}

	public String getDpf05Llb05() {
		return dpf05Llb05;
	}

	public void setDpf05Llb05(String dpf05Llb05) {
		this.dpf05Llb05 = dpf05Llb05;
	}

	public String getDpf08Clb05() {
		return dpf08Clb05;
	}

	public void setDpf08Clb05(String dpf08Clb05) {
		this.dpf08Clb05 = dpf08Clb05;
	}

	public String getDpf10lb02() {
		return dpf10lb02;
	}

	public void setDpf10lb02(String dpf10lb02) {
		this.dpf10lb02 = dpf10lb02;
	}

	public String getDpf05Llb02() {
		return dpf05Llb02;
	}

	public void setDpf05Llb02(String dpf05Llb02) {
		this.dpf05Llb02 = dpf05Llb02;
	}

	public String getDpf08Clb02() {
		return dpf08Clb02;
	}

	public void setDpf08Clb02(String dpf08Clb02) {
		this.dpf08Clb02 = dpf08Clb02;
	}

	public String getDpf10lb01() {
		return dpf10lb01;
	}

	public void setDpf10lb01(String dpf10lb01) {
		this.dpf10lb01 = dpf10lb01;
	}

	public String getDpf05Llb01() {
		return dpf05Llb01;
	}

	public void setDpf05Llb01(String dpf05Llb01) {
		this.dpf05Llb01 = dpf05Llb01;
	}

	public String getDpf08Clb01() {
		return dpf08Clb01;
	}

	public void setDpf08Clb01(String dpf08Clb01) {
		this.dpf08Clb01 = dpf08Clb01;
	}

	public String getDpf10lb005() {
		return dpf10lb005;
	}

	public void setDpf10lb005(String dpf10lb005) {
		this.dpf10lb005 = dpf10lb005;
	}

	public String getDpf05Llb005() {
		return dpf05Llb005;
	}

	public void setDpf05Llb005(String dpf05Llb005) {
		this.dpf05Llb005 = dpf05Llb005;
	}

	public String getDpf08Clb005() {
		return dpf08Clb005;
	}

	public void setDpf08Clb005(String dpf08Clb005) {
		this.dpf08Clb005 = dpf08Clb005;
	}

	public String getDpf10Remarks() {
		return dpf10Remarks;
	}

	public void setDpf10Remarks(String dpf10Remarks) {
		this.dpf10Remarks = dpf10Remarks;
	}

	public String getDpf05LRemarks() {
		return dpf05LRemarks;
	}

	public void setDpf05LRemarks(String dpf05lRemarks) {
		dpf05LRemarks = dpf05lRemarks;
	}

	public String getDpf08CRemarks() {
		return dpf08CRemarks;
	}

	public void setDpf08CRemarks(String dpf08cRemarks) {
		dpf08CRemarks = dpf08cRemarks;
	}

	public String getCtSlNoPhase1R() {
		return ctSlNoPhase1R;
	}

	public void setCtSlNoPhase1R(String ctSlNoPhase1R) {
		this.ctSlNoPhase1R = ctSlNoPhase1R;
	}

	public String getCtSlNoPhase2Y() {
		return ctSlNoPhase2Y;
	}

	public void setCtSlNoPhase2Y(String ctSlNoPhase2Y) {
		this.ctSlNoPhase2Y = ctSlNoPhase2Y;
	}

	public String getCtSlNoPhase3B() {
		return ctSlNoPhase3B;
	}

	public void setCtSlNoPhase3B(String ctSlNoPhase3B) {
		this.ctSlNoPhase3B = ctSlNoPhase3B;
	}

	public String getCtBrandMfgNamePhase1R() {
		return ctBrandMfgNamePhase1R;
	}

	public void setCtBrandMfgNamePhase1R(String ctBrandMfgNamePhase1R) {
		this.ctBrandMfgNamePhase1R = ctBrandMfgNamePhase1R;
	}

	public String getCtBrandMfgNamePhase2Y() {
		return ctBrandMfgNamePhase2Y;
	}

	public void setCtBrandMfgNamePhase2Y(String ctBrandMfgNamePhase2Y) {
		this.ctBrandMfgNamePhase2Y = ctBrandMfgNamePhase2Y;
	}

	public String getCtBrandMfgNamePhase3B() {
		return ctBrandMfgNamePhase3B;
	}

	public void setCtBrandMfgNamePhase3B(String ctBrandMfgNamePhase3B) {
		this.ctBrandMfgNamePhase3B = ctBrandMfgNamePhase3B;
	}

	public String getCtTypeModelPhase1R() {
		return ctTypeModelPhase1R;
	}

	public void setCtTypeModelPhase1R(String ctTypeModelPhase1R) {
		this.ctTypeModelPhase1R = ctTypeModelPhase1R;
	}

	public String getCtTypeModelPhase2Y() {
		return ctTypeModelPhase2Y;
	}

	public void setCtTypeModelPhase2Y(String ctTypeModelPhase2Y) {
		this.ctTypeModelPhase2Y = ctTypeModelPhase2Y;
	}

	public String getCtTypeModelPhase3B() {
		return ctTypeModelPhase3B;
	}

	public void setCtTypeModelPhase3B(String ctTypeModelPhase3B) {
		this.ctTypeModelPhase3B = ctTypeModelPhase3B;
	}

	public String getCtBurdenPhase1R() {
		return ctBurdenPhase1R;
	}

	public void setCtBurdenPhase1R(String ctBurdenPhase1R) {
		this.ctBurdenPhase1R = ctBurdenPhase1R;
	}

	public String getCtBurdenPhase2Y() {
		return ctBurdenPhase2Y;
	}

	public void setCtBurdenPhase2Y(String ctBurdenPhase2Y) {
		this.ctBurdenPhase2Y = ctBurdenPhase2Y;
	}

	public String getCtBurdenPhase3B() {
		return ctBurdenPhase3B;
	}

	public void setCtBurdenPhase3B(String ctBurdenPhase3B) {
		this.ctBurdenPhase3B = ctBurdenPhase3B;
	}

	public String getCtAccuracyPhase1R() {
		return ctAccuracyPhase1R;
	}

	public void setCtAccuracyPhase1R(String ctAccuracyPhase1R) {
		this.ctAccuracyPhase1R = ctAccuracyPhase1R;
	}

	public String getCtAccuracyPhase2Y() {
		return ctAccuracyPhase2Y;
	}

	public void setCtAccuracyPhase2Y(String ctAccuracyPhase2Y) {
		this.ctAccuracyPhase2Y = ctAccuracyPhase2Y;
	}

	public String getCtAccuracyPhase3B() {
		return ctAccuracyPhase3B;
	}

	public void setCtAccuracyPhase3B(String ctAccuracyPhase3B) {
		this.ctAccuracyPhase3B = ctAccuracyPhase3B;
	}

	public String getCtRatioPhase1R() {
		return ctRatioPhase1R;
	}

	public void setCtRatioPhase1R(String ctRatioPhase1R) {
		this.ctRatioPhase1R = ctRatioPhase1R;
	}

	public String getCtRatioPhase2Y() {
		return ctRatioPhase2Y;
	}

	public void setCtRatioPhase2Y(String ctRatioPhase2Y) {
		this.ctRatioPhase2Y = ctRatioPhase2Y;
	}

	public String getCtRatioPhase3B() {
		return ctRatioPhase3B;
	}

	public void setCtRatioPhase3B(String ctRatioPhase3B) {
		this.ctRatioPhase3B = ctRatioPhase3B;
	}

	public String getCtMeggerTestRptPhase1R() {
		return ctMeggerTestRptPhase1R;
	}

	public void setCtMeggerTestRptPhase1R(String ctMeggerTestRptPhase1R) {
		this.ctMeggerTestRptPhase1R = ctMeggerTestRptPhase1R;
	}

	public String getCtMeggerTestRptPhase2Y() {
		return ctMeggerTestRptPhase2Y;
	}

	public void setCtMeggerTestRptPhase2Y(String ctMeggerTestRptPhase2Y) {
		this.ctMeggerTestRptPhase2Y = ctMeggerTestRptPhase2Y;
	}

	public String getCtMeggerTestRptPhase3B() {
		return ctMeggerTestRptPhase3B;
	}

	public void setCtMeggerTestRptPhase3B(String ctMeggerTestRptPhase3B) {
		this.ctMeggerTestRptPhase3B = ctMeggerTestRptPhase3B;
	}

	public String getPtSlNoPhase1R() {
		return ptSlNoPhase1R;
	}

	public void setPtSlNoPhase1R(String ptSlNoPhase1R) {
		this.ptSlNoPhase1R = ptSlNoPhase1R;
	}

	public String getPtSlNoPhase2Y() {
		return ptSlNoPhase2Y;
	}

	public void setPtSlNoPhase2Y(String ptSlNoPhase2Y) {
		this.ptSlNoPhase2Y = ptSlNoPhase2Y;
	}

	public String getPtSlNoPhase3B() {
		return ptSlNoPhase3B;
	}

	public void setPtSlNoPhase3B(String ptSlNoPhase3B) {
		this.ptSlNoPhase3B = ptSlNoPhase3B;
	}

	public String getPtBrandMfgNamePhase1R() {
		return ptBrandMfgNamePhase1R;
	}

	public void setPtBrandMfgNamePhase1R(String ptBrandMfgNamePhase1R) {
		this.ptBrandMfgNamePhase1R = ptBrandMfgNamePhase1R;
	}

	public String getPtBrandMfgNamePhase2Y() {
		return ptBrandMfgNamePhase2Y;
	}

	public void setPtBrandMfgNamePhase2Y(String ptBrandMfgNamePhase2Y) {
		this.ptBrandMfgNamePhase2Y = ptBrandMfgNamePhase2Y;
	}

	public String getPtBrandMfgNamePhase3B() {
		return ptBrandMfgNamePhase3B;
	}

	public void setPtBrandMfgNamePhase3B(String ptBrandMfgNamePhase3B) {
		this.ptBrandMfgNamePhase3B = ptBrandMfgNamePhase3B;
	}

	public String getPtTypeModelPhase1R() {
		return ptTypeModelPhase1R;
	}

	public void setPtTypeModelPhase1R(String ptTypeModelPhase1R) {
		this.ptTypeModelPhase1R = ptTypeModelPhase1R;
	}

	public String getPtTypeModelPhase2Y() {
		return ptTypeModelPhase2Y;
	}

	public void setPtTypeModelPhase2Y(String ptTypeModelPhase2Y) {
		this.ptTypeModelPhase2Y = ptTypeModelPhase2Y;
	}

	public String getPtTypeModelPhase3B() {
		return ptTypeModelPhase3B;
	}

	public void setPtTypeModelPhase3B(String ptTypeModelPhase3B) {
		this.ptTypeModelPhase3B = ptTypeModelPhase3B;
	}

	public String getPtBurdenPhase1R() {
		return ptBurdenPhase1R;
	}

	public void setPtBurdenPhase1R(String ptBurdenPhase1R) {
		this.ptBurdenPhase1R = ptBurdenPhase1R;
	}

	public String getPtBurdenPhase2Y() {
		return ptBurdenPhase2Y;
	}

	public void setPtBurdenPhase2Y(String ptBurdenPhase2Y) {
		this.ptBurdenPhase2Y = ptBurdenPhase2Y;
	}

	public String getPtBurdenPhase3B() {
		return ptBurdenPhase3B;
	}

	public void setPtBurdenPhase3B(String ptBurdenPhase3B) {
		this.ptBurdenPhase3B = ptBurdenPhase3B;
	}

	public String getPtAccuracyPhase1R() {
		return ptAccuracyPhase1R;
	}

	public void setPtAccuracyPhase1R(String ptAccuracyPhase1R) {
		this.ptAccuracyPhase1R = ptAccuracyPhase1R;
	}

	public String getPtAccuracyPhase2Y() {
		return ptAccuracyPhase2Y;
	}

	public void setPtAccuracyPhase2Y(String ptAccuracyPhase2Y) {
		this.ptAccuracyPhase2Y = ptAccuracyPhase2Y;
	}

	public String getPtAccuracyPhase3B() {
		return ptAccuracyPhase3B;
	}

	public void setPtAccuracyPhase3B(String ptAccuracyPhase3B) {
		this.ptAccuracyPhase3B = ptAccuracyPhase3B;
	}

	public String getPtRatioPhase1R() {
		return ptRatioPhase1R;
	}

	public void setPtRatioPhase1R(String ptRatioPhase1R) {
		this.ptRatioPhase1R = ptRatioPhase1R;
	}

	public String getPtRatioPhase2Y() {
		return ptRatioPhase2Y;
	}

	public void setPtRatioPhase2Y(String ptRatioPhase2Y) {
		this.ptRatioPhase2Y = ptRatioPhase2Y;
	}

	public String getPtRatioPhase3B() {
		return ptRatioPhase3B;
	}

	public void setPtRatioPhase3B(String ptRatioPhase3B) {
		this.ptRatioPhase3B = ptRatioPhase3B;
	}

	public String getPtMeggerTestRptPhase1R() {
		return ptMeggerTestRptPhase1R;
	}

	public void setPtMeggerTestRptPhase1R(String ptMeggerTestRptPhase1R) {
		this.ptMeggerTestRptPhase1R = ptMeggerTestRptPhase1R;
	}

	public String getPtMeggerTestRptPhase2Y() {
		return ptMeggerTestRptPhase2Y;
	}

	public void setPtMeggerTestRptPhase2Y(String ptMeggerTestRptPhase2Y) {
		this.ptMeggerTestRptPhase2Y = ptMeggerTestRptPhase2Y;
	}

	public String getPtMeggerTestRptPhase3B() {
		return ptMeggerTestRptPhase3B;
	}

	public void setPtMeggerTestRptPhase3B(String ptMeggerTestRptPhase3B) {
		this.ptMeggerTestRptPhase3B = ptMeggerTestRptPhase3B;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isFinalSubmit() {
		return finalSubmit;
	}

	public void setFinalSubmit(boolean finalSubmit) {
		this.finalSubmit = finalSubmit;
	}

}
