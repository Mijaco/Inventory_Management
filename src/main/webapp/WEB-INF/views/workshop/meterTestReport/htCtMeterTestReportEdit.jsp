<%@include file="../../common/wsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_multiselect/bootstrap-multiselect.css" />

<!-- @author: Abu Taleb, Programmer, IBCS -->

<style type="text/css">
.error {
	font-size: 16px;
	color: white;
	font-style: italic;
}

.ui-datepicker-calendar, .ui-datepicker-month {
	display: none;
}

thead tr {
	background: #579EC8 !important;
}

thead tr th {
	color: white !important;
	text-align: center;
}

textarea {
	resize: none;
}
</style>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			New HT-CT Meter Test Report Edit Form</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="col-sm-12">
			<c:if test="${meterTestingReport.finalSubmit==false}">
				<a
					href="${pageContext.request.contextPath}/mtr/meterTestReportList.do"
					style="border-radius: 6px;" class="btn btn-danger"> <i
					class="fa fa-times"></i> &nbsp;Cancel
				</a>
			</c:if>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<form class="form-horizontal"
			action="${pageContext.request.contextPath}/mtr/meterTestReportUpdate.do"
			method="POST" id="htCtMeterTestReportEditForm">

			<!-- MST Info :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Tracking No:</td>
							<td><input type="hidden" id="id" name="id"
								value="${meterTestingReport.id}">
								${meterTestingReport.trackingNo}</td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Sales &amp; Distribution
								Division</td>
							<td><select name="deptId" class="form-control" id="deptId">
									<option disabled="disabled" selected="selected">---
										select a S&amp;D ---</option>
									<option value="${meterTestingReport.department.deptId}"
										selected>${meterTestingReport.department.deptName}</option>
									<c:forEach items="${depts}" var="department">
										<option value="${department.deptId}">${department.deptName}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Category:</td>
							<td><select name="meterCategory" id="meterCategory"
								class="form-control" style="width: 100%;">
									<option value="${meterTestingReport.meterCategory}" selected>${meterTestingReport.meterCategory}</option>
							</select></td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter/Metering Unit Source</td>
							<td><input type="text" id="meterUnitSource"
								value="${meterTestingReport.meterUnitSource}"
								name="meterUnitSource" class="form-control" style="width: 100%">
							</td>
						</tr>
						<tr>
							<td class="success text-right" style="font-weight: bold;">CMTL
								Sl. No.:</td>
							<td><input type="text" id="cmtlSlNo" name="cmtlSlNo"
								value="${meterTestingReport.cmtlSlNo}" class="form-control"
								style="width: 100%"></td>
							<td class="success text-right" style="font-weight: bold;">Date
								:</td>
							<td><fmt:formatDate value="${meterTestingReport.reportDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- MST Info :: end -->

			<!-- Name Plate Data :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(A) Name Plate Data:</strong>
				</h6>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter No:</td>
							<td class="col-md-3"><input type="text" id="meterNo"
								value="${meterTestingReport.meterNo}" name="meterNo"
								class="form-control" style="width: 100%"></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Manufacturer Name:</td>
							<!-- <td><select name="manufactureName" id="manufactureName"
								class="form-control">
									<option value="0" selected disabled>Select
										Manufacturer</option>
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="manufactureName" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="manufactureName"
								value="${meterTestingReport.manufactureName}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Voltage Rating</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="voltageRating" id="voltageRating" class="form-control">
									<option value="0" selected disabled>Select Voltage
										Rating</option>
									<option value="3x---/110/v3 Volt">3x---/110/v3 Volt</option>
									<option value="3x---/220/v6 Volt">3x---/220/v6 Volt</option>
							</select></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="voltageRating" class="form-control">
									<option value="3x---/110/v3 Volt">3x---/110/v3 Volt</option>
									<option value="3x---/220/v6 Volt">3x---/220/v6 Volt</option>
							</select> &nbsp;&nbsp; <input type="text" name="voltageRating"
								value="${meterTestingReport.voltageRating}"
								placeholder="Voltage Rating" class="form-control"
								style="width: 90%;" /></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Model No.:</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="modelNo" id="modelNo" class="form-control">
									<option value="0" selected disabled>Select Model</option>
									<option value="E3M054">E3M054</option>
									<option value="E3M053">E3M053</option>
							</select></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value" id="modelNo"
								class="form-control">
									<option value="E3M054">E3M054</option>
									<option value="E3M053">E3M053</option>
							</select> &nbsp;&nbsp; <input type="text" name="modelNo"
								value="${meterTestingReport.modelNo}" placeholder="Select Model"
								class="form-control" style="width: 90%" /></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Current Rating:</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="currentRating" id="currentRating" class="form-control">
									<option value="0" selected disabled>Select Current
										Rating</option>
									<option value="3x---/5(10) Amp.">3x---/5(10) Amp.</option>
									<option value="3x---/10(10) Amp.">3x---/10(10) Amp.</option>
							</select></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="currentRating" class="form-control">
									<option value="3x---/5(10) Amp.">3x---/5(10) Amp.</option>
									<option value="3x---/10(10) Amp.">3x---/10(10) Amp.</option>
							</select> &nbsp;&nbsp; <input type="text" name="currentRating"
								value="${meterTestingReport.currentRating}"
								placeholder="Select Current Rating" class="form-control"
								style="width: 90%" /></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Manufacturing Year:</td>
							<td><input type="text" id="manufacturingYear"
								value="${meterTestingReport.manufacturingYear}"
								name="manufacturingYear" class="form-control datepicker-18"
								style="width: 100%"></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Accuracy Class(Active):</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="accuracyClassActive" style="width: 49%; display: inline;"
								id="accuracyClassActive" class="form-control">
									<option value="0" selected disabled>Active</option>
									<option value="0.5s">0.5s</option>
							</select> <select name="accuracyClassReActive"
								style="width: 49%; display: inline;" id="accuracyClassReActive"
								class="form-control">
									<option value="0" selected disabled>Reactive</option>
									<option value="0.5s">0.5s</option>
							</select></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="accuracyClassActive" class="form-control">
									<option value="0.5s">0.5s</option>
									<option value="1.0s">1.0s</option>
							</select> &nbsp;&nbsp; <input type="text" name="accuracyClassActive"
								value="${meterTestingReport.accuracyClassActive}"
								placeholder="Active" class="form-control" style="width: 90%" /></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Accuracy Class (Reactive):</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="accuracyClassActive" style="width: 49%; display: inline;"
								id="accuracyClassActive" class="form-control">
									<option value="0" selected disabled>Active</option>
									<option value="0.5s">0.5s</option>
							</select> <select name="accuracyClassReActive"
								style="width: 49%; display: inline;" id="accuracyClassReActive"
								class="form-control">
									<option value="0" selected disabled>Reactive</option>
									<option value="0.5s">0.5s</option>
							</select></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="accuracyClassReActive" class="form-control">
									<option value="0.5s">0.5s</option>
									<option value="1.0s">1.0s</option>
							</select> &nbsp;&nbsp; <input type="text" name="accuracyClassReActive"
								value="${meterTestingReport.accuracyClassReActive}"
								placeholder="Reactive" class="form-control" style="width: 90%" /></td>
						</tr>

						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Constant:</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="meterConstant" id="meterConstant" class="form-control">
									<option value="0" selected disabled>Meter Constant</option>
									<option value="16000 imp.KWh">16000 imp.KWh</option>
									<option value="18000 imp.KWh">18000 imp.KWh</option>
							</select></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="meterConstant" class="form-control">
									<option value="16000 imp.KWh">16000 imp.KWh</option>
									<option value="18000 imp.KWh">18000 imp.KWh</option>
							</select> &nbsp;&nbsp; <input type="text" name="meterConstant"
								value="${meterTestingReport.meterConstant}"
								placeholder="Meter Constant" class="form-control"
								style="width: 90%" /></td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Connection Type:</td>
							<!-- <td class="col-md-3 col-sm-3 col-xs-3"><select
								name="connectionType" id="connectionType" class="form-control">
									<option value="0" selected disabled>Connection Type</option>
									<option value="3-Phase 4-Wire">3-Phase 4-Wire</option>
									<option value="2-Phase 3-Wire">2-Phase 3-Wire</option>
							</select></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="connectionType" class="form-control">
									<option value="3-Phase 4-Wire">3-Phase 4-Wire</option>
									<option value="2-Phase 3-Wire">2-Phase 3-Wire</option>
							</select> &nbsp;&nbsp; <input type="text" name="connectionType"
								value="${meterTestingReport.connectionType}"
								placeholder="Connection Type" class="form-control"
								style="width: 90%" /></td>
						</tr>

						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Type:</td>
							<td><input type="text" id="meterType" name="meterType"
								readonly value="${meterTestingReport.meterType}"
								class="form-control" style="width: 100%"></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;"></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Name Plate Data :: end -->

			<!-- Meter, CT & PT Physical Observation :: start -->

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<!-- <h6> <strong>Meter Testing Instrument</strong> </h6> -->
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(B) Meter, CT &amp; PT Physical
								Observation:</td>
							<td><select name="physicalObservation"
								id="physicalObservation" class="form-control">
									<option value="${meterTestingReport.physicalObservation}">${meterTestingReport.physicalObservation}</option>
									<c:if test="${meterTestingReport.physicalObservation=='NO'}">
										<option value="YES">YES</option>
									</c:if>
									<c:if test="${meterTestingReport.physicalObservation=='YES'}">
										<option value="NO">NO</option>
									</c:if>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Meter, CT & PT Physical Observation :: end -->

			<!-- % of Error report for different Power Factor :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(C) % of Error report for different Power Factor
						on different load:</strong>
				</h6>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Applied Load &amp; PF</th>
							<th>lmax</th>
							<th>1.2lb (120%)</th>
							<th>lb (100%)</th>
							<th>0.5lb (50%)</th>
							<th>0.2lb (20%)</th>
							<th>0.1lb (10%)</th>
							<th>0.05lb (5%)</th>
							<th>Remarks</th>
						</tr>
					</thead>

					<tbody>
						<tr>
							<td>PF=1.0</td>
							<td><input type="text" id="dpf10lmax" name="dpf10lmax"
								value="${meterTestingReport.dpf10lmax}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb12" name="dpf10lb12"
								value="${meterTestingReport.dpf10lb12}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb" name="dpf10lb"
								value="${meterTestingReport.dpf10lb}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb05" name="dpf10lb05"
								value="${meterTestingReport.dpf10lb05}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb02" name="dpf10lb02"
								value="${meterTestingReport.dpf10lb02}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb01" name="dpf10lb01"
								value="${meterTestingReport.dpf10lb01}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf10lb005" name="dpf10lb005"
								value="${meterTestingReport.dpf10lb005}" class="form-control"
								style="width: 100%"></td>
							<td><select name="dpf10Remarks" id="dpf10Remarks"
								class="form-control" style="width: 100%;">
									<option value="${meterTestingReport.dpf10Remarks}">${meterTestingReport.dpf10Remarks}</option>
									<c:if test="${meterTestingReport.dpf10Remarks=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.dpf10Remarks=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
						</tr>

						<tr>
							<td>PF=0.5L</td>
							<td><input type="text" id="dpf05Llmax" name="dpf05Llmax"
								value="${meterTestingReport.dpf05Llmax}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb12" name="dpf05Llb12"
								value="${meterTestingReport.dpf05Llb12}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb" name="dpf05Llb"
								value="${meterTestingReport.dpf05Llb}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb05" name="dpf05Llb05"
								value="${meterTestingReport.dpf05Llb05}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb02" name="dpf05Llb02"
								value="${meterTestingReport.dpf05Llb02}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb01" name="dpf05Llb01"
								value="${meterTestingReport.dpf05Llb01}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb005" name="dpf05Llb005"
								value="${meterTestingReport.dpf05Llb005}" class="form-control"
								style="width: 100%"></td>
							<td><select name="dpf05LRemarks" id="dpf05LRemarks"
								class="form-control" style="width: 100%;">
									<option value="${meterTestingReport.dpf05LRemarks}">${meterTestingReport.dpf05LRemarks}</option>
									<c:if test="${meterTestingReport.dpf05LRemarks=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.dpf05LRemarks=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
						</tr>

						<tr>
							<td>PF=0.8C</td>
							<td><input type="text" id="dpf08Clmax" name="dpf08Clmax"
								value="${meterTestingReport.dpf08Clmax}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb12" name="dpf08Clb12"
								value="${meterTestingReport.dpf08Clb12}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb" name="dpf08Clb"
								value="${meterTestingReport.dpf08Clb}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb05" name="dpf08Clb05"
								value="${meterTestingReport.dpf08Clb05}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb02" name="dpf08Clb02"
								value="${meterTestingReport.dpf08Clb02}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb01" name="dpf08Clb01"
								value="${meterTestingReport.dpf08Clb01}" class="form-control"
								style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb005" name="dpf08Clb005"
								value="${meterTestingReport.dpf08Clb005}" class="form-control"
								style="width: 100%"></td>
							<td><select name="dpf08CRemarks" id="dpf08CRemarks"
								class="form-control" style="width: 100%;">
									<option value="${meterTestingReport.dpf08CRemarks}">${meterTestingReport.dpf08CRemarks}</option>
									<c:if test="${meterTestingReport.dpf08CRemarks=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.dpf08CRemarks=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- % of Error report for different Power Factor :: end -->

			<!-- Meter Testing Instrument :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<!-- <h6> <strong>Meter Testing Instrument</strong> </h6> -->
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Testing Instrument</td>
							<td><input type="text" name="meterTestinInstruments"
								id="meterTestinInstruments" class="form-control"
								value="${meterTestingReport.meterTestinInstruments}"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Meter Testing Instrument :: end -->

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-2 text-right"
								style="font-weight: bold;">(i) Starting Test Report</td>
							<td><select name="startingTestReport"
								id="startingTestReport" class="form-control">
									<option value="${meterTestingReport.startingTestReport}"
										selected>${meterTestingReport.startingTestReport}</option>
									<c:if test="${meterTestingReport.startingTestReport=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.startingTestReport=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
							<td class="success col-md-2 text-right"
								style="font-weight: bold;">(ii) No Load Test Report</td>
							<td><select name="noLoadTestReport" id="noLoadTestReport"
								class="form-control">
									<option value="${meterTestingReport.noLoadTestReport}" selected>${meterTestingReport.noLoadTestReport}</option>
									<c:if test="${meterTestingReport.noLoadTestReport=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.noLoadTestReport=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
							<td class="success col-md-2 text-right"
								style="font-weight: bold;">(iii) Dial Test Report</td>
							<td><select name="dialTestReport" id="dialTestReport"
								class="form-control">
									<option value="${meterTestingReport.dialTestReport}" selected>${meterTestingReport.dialTestReport}</option>
									<c:if test="${meterTestingReport.dialTestReport=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if test="${meterTestingReport.dialTestReport=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(E) Meter Reading: </strong>
				</h6>
				<h6 class="blue" style="font-weight: bold;">As Found:</h6>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(i) Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundPeak" name="mrAsFoundPeak"
										value="${meterTestingReport.mrAsFoundPeak}"
										class="form-control" /> <span class="input-group-addon">KWH</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(ii) Off Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundOffPeak"
										value="${meterTestingReport.mrAsFoundOffPeak}"
										name="mrAsFoundOffPeak" class="form-control" /> <span
										class="input-group-addon">KWH</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iii) MD</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundMD" name="mrAsFoundMD"
										value="${meterTestingReport.mrAsFoundMD}" class="form-control" />
									<span class="input-group-addon">KW</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iv) Reactive (Total)</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundReActive"
										value="${meterTestingReport.mrAsFoundReActive}"
										name="mrAsFoundReActive" class="form-control" /> <span
										class="input-group-addon">KVArH</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>

				<h6 class="blue" style="font-weight: bold;">As Left:</h6>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(i) Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftPeak" name="mrAsLeftPeak"
										value="${meterTestingReport.mrAsLeftPeak}"
										class="form-control" /> <span class="input-group-addon">KWH</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(ii) Off Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftOffPeak" name="mrAsLeftOffPeak"
										value="${meterTestingReport.mrAsLeftOffPeak}"
										class="form-control" /> <span class="input-group-addon">KWH</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iii) MD</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftMD" name="mrAsLeftMD"
										value="${meterTestingReport.mrAsLeftMD}" class="form-control" />
									<span class="input-group-addon">KW</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iv) Reactive (Total)</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftReActive"
										value="${meterTestingReport.mrAsLeftReActive}"
										name="mrAsLeftReActive" class="form-control" /> <span
										class="input-group-addon">KVArH</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>


			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(F) Line Current Transformer(CT)</strong>
				</h6>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Particulars</th>
							<th>Phase - 1 / (R)</th>
							<th>Phase - 2 / (Y)</th>
							<th>Phase - 3 / (B)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>CT Serial No</td>
							<td class="col-sm-3"><input type="text" id="ctSlNoPhase1R"
								value="${meterTestingReport.ctSlNoPhase1R}" name="ctSlNoPhase1R"
								class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ctSlNoPhase2Y"
								value="${meterTestingReport.ctSlNoPhase2Y}" name="ctSlNoPhase2Y"
								class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ctSlNoPhase3B"
								value="${meterTestingReport.ctSlNoPhase3B}" name="ctSlNoPhase3B"
								class="form-control" style="width: 100%"></td>
						</tr>
						<tr>
							<td>CT Brand/Mfg. Name</td>
							<!-- <td><input type="text" id="ctBrandMfgNamePhase1R"
								name="ctBrandMfgNamePhase1R" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBrandMfgNamePhase1R" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBrandMfgNamePhase1R"
								value="${meterTestingReport.ctBrandMfgNamePhase1R}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctBrandMfgNamePhase2Y"
								name="ctBrandMfgNamePhase2Y" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBrandMfgNamePhase2Y" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBrandMfgNamePhase2Y"
								value="${meterTestingReport.ctBrandMfgNamePhase2Y}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctBrandMfgNamePhase3B"
								name="ctBrandMfgNamePhase3B" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBrandMfgNamePhase3B" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBrandMfgNamePhase3B"
								value="${meterTestingReport.ctBrandMfgNamePhase3B}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Type/Model</td>
							<!-- <td><input type="text" id="ctTypeModelPhase1R"
								name="ctTypeModelPhase1R" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctTypeModelPhase1R" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctTypeModelPhase1R"
								value="${meterTestingReport.ctTypeModelPhase1R}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctTypeModelPhase2Y"
								name="ctTypeModelPhase2Y" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctTypeModelPhase2Y" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctTypeModelPhase2Y"
								value="${meterTestingReport.ctTypeModelPhase2Y}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctTypeModelPhase3B"
								name="ctTypeModelPhase3B" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctTypeModelPhase3B" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctTypeModelPhase3B"
								value="${meterTestingReport.ctTypeModelPhase3B}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>CT Burden</td>
							<!-- <td><input type="text" id="ctBurdenPhase1R"
								name="ctBurdenPhase1R" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBurdenPhase1R" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase1R"
								value="${meterTestingReport.ctBurdenPhase1R}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctBurdenPhase2Y"
								name="ctBurdenPhase2Y" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBurdenPhase2Y" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase2Y"
								value="${meterTestingReport.ctBurdenPhase2Y}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctBurdenPhase3B"
								name="ctBurdenPhase3B" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctBurdenPhase3B" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase3B"
								value="${meterTestingReport.ctBurdenPhase3B}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Accuracy</td>
							<!-- <td><input type="text" id="ctAccuracyPhase1R"
								name="ctAccuracyPhase1R" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctAccuracyPhase1R" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctAccuracyPhase1R"
								value="${meterTestingReport.ctAccuracyPhase1R}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>

							<!-- <td><input type="text" id="ctAccuracyPhase2Y"
								name="ctAccuracyPhase2Y" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctAccuracyPhase2Y" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctAccuracyPhase2Y"
								value="${meterTestingReport.ctAccuracyPhase2Y}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctAccuracyPhase3B"
								name="ctAccuracyPhase3B" class="form-control"
								style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctAccuracyPhase3B" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctAccuracyPhase3B"
								value="${meterTestingReport.ctAccuracyPhase3B}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" />
						</tr>
						<tr>
							<td>CT Ratio</td>
							<!-- <td><input type="text" id="ctRatioPhase1R"
								name="ctRatioPhase1R" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctRatioPhase1R" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase1R"
								value="${meterTestingReport.ctRatioPhase1R}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctRatioPhase2Y"
								name="ctRatioPhase2Y" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctRatioPhase2Y" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase2Y"
								value="${meterTestingReport.ctRatioPhase2Y}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>
							<!-- <td><input type="text" id="ctRatioPhase3B"
								name="ctRatioPhase3B" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctRatioPhase3B" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase3B"
								value="${meterTestingReport.ctRatioPhase3B}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Megger Test Report</td>
							<td><select name="ctMeggerTestRptPhase1R"
								id="ctMeggerTestRptPhase1R" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ctMeggerTestRptPhase1R}"
										selected>${meterTestingReport.ctMeggerTestRptPhase1R}</option>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase1R=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase1R=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

							<td><select name="ctMeggerTestRptPhase2Y"
								id="ctMeggerTestRptPhase2Y" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ctMeggerTestRptPhase2Y}"
										selected>${meterTestingReport.ctMeggerTestRptPhase2Y}</option>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase2Y=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase2Y=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

							<td><select name="ctMeggerTestRptPhase3B"
								id="ctMeggerTestRptPhase3B" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ctMeggerTestRptPhase3B}"
										selected>${meterTestingReport.ctMeggerTestRptPhase3B}</option>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase3B=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ctMeggerTestRptPhase3B=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

						</tr>
					</tbody>
				</table>
			</div>

			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(G) Line Potential Transformer(PT)</strong>
				</h6>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Particulars</th>
							<th>Phase - 1 / (R)</th>
							<th>Phase - 2 / (Y)</th>
							<th>Phase - 3 / (B)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>PT Serial No</td>
							<td class="col-sm-3"><input type="text" id="ptSlNoPhase1R"
								value="${meterTestingReport.ptSlNoPhase1R}" name="ptSlNoPhase1R"
								class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ptSlNoPhase2Y"
								value="${meterTestingReport.ptSlNoPhase2Y}" name="ptSlNoPhase2Y"
								class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ptSlNoPhase3B"
								value="${meterTestingReport.ptSlNoPhase3B}" name="ptSlNoPhase3B"
								class="form-control" style="width: 100%"></td>
						</tr>
						<tr>
							<td>PT Brand/Mfg. Name</td>
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBrandMfgNamePhase1R" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBrandMfgNamePhase1R"
								value="${meterTestingReport.ptBrandMfgNamePhase1R}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBrandMfgNamePhase2Y" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBrandMfgNamePhase2Y"
								value="${meterTestingReport.ptBrandMfgNamePhase2Y}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBrandMfgNamePhase3B" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBrandMfgNamePhase3B"
								value="${meterTestingReport.ptBrandMfgNamePhase3B}"
								placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Type/Model</td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptTypeModelPhase1R" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptTypeModelPhase1R"
								value="${meterTestingReport.ptTypeModelPhase1R}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptTypeModelPhase2Y" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptTypeModelPhase2Y"
								value="${meterTestingReport.ptTypeModelPhase2Y}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptTypeModelPhase3B" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptTypeModelPhase3B"
								value="${meterTestingReport.ptTypeModelPhase3B}"
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>PT Burden</td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBurdenPhase1R" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase1R"
								value="${meterTestingReport.ptBurdenPhase2Y}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBurdenPhase2Y" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase2Y"
								value="${meterTestingReport.ptBurdenPhase2Y}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBurdenPhase3B" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase3B"
								value="${meterTestingReport.ptBurdenPhase3B}"
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Accuracy</td>
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptAccuracyPhase1R" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptAccuracyPhase1R"
								value="${meterTestingReport.ptAccuracyPhase1R}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptAccuracyPhase2Y" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptAccuracyPhase2Y"
								value="${meterTestingReport.ptAccuracyPhase2Y}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptAccuracyPhase3B" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptAccuracyPhase3B"
								value="${meterTestingReport.ptAccuracyPhase3B}"
								placeholder="Accuracy Class" class="form-control"
								style="width: 90%" />
						</tr>
						<tr>
							<td>PT Ratio</td>
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptRatioPhase1R" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase1R"
								value="${meterTestingReport.ptRatioPhase1R}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptRatioPhase2Y" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase2Y"
								value="${meterTestingReport.ptRatioPhase2Y}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptRatioPhase3B" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase3B"
								value="${meterTestingReport.ptRatioPhase3B}" placeholder="Ratio"
								class="form-control" style="width: 90%" /></td>
						</tr>
						<tr>
							<td>Megger Test Report</td>
							<td><select name="ptMeggerTestRptPhase1R"
								id="ptMeggerTestRptPhase1R" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ptMeggerTestRptPhase1R}"
										selected>${meterTestingReport.ptMeggerTestRptPhase1R}</option>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase1R=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase1R=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

							<td><select name="ptMeggerTestRptPhase2Y"
								id="ptMeggerTestRptPhase2Y" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ptMeggerTestRptPhase2Y}"
										selected>${meterTestingReport.ptMeggerTestRptPhase2Y}</option>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase2Y=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase2Y=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

							<td><select name="ptMeggerTestRptPhase3B"
								id="ptMeggerTestRptPhase3B" class="form-control"
								style="width: 100%;">
									<option value="${meterTestingReport.ptMeggerTestRptPhase3B}"
										selected>${meterTestingReport.ptMeggerTestRptPhase3B}</option>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase3B=='FAILED'}">
										<option value="PASSED">PASSED</option>
									</c:if>
									<c:if
										test="${meterTestingReport.ptMeggerTestRptPhase3B=='PASSED'}">
										<option value="FAILED">FAILED</option>
									</c:if>
							</select></td>

						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">Remarks
								on CT &amp; PT</td>
							<td><textarea name="ctPtRemarks" id="ctPtRemarks"
									class="form-control">${meterTestingReport.ctPtRemarks}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">(H)
								Meter Current Transformer Ratio</td>
							<td><input type="number" id="meterCTRatio" name="meterCTRatio"
								value="${meterTestingReport.meterCTRatio}" class="form-control"
								style="width: 100%" onblur="setOmfValue()"></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">(I)
								Meter Potential Transformer</td>
							<td><input type="number" id="meterPTRatio" name="meterPTRatio"
								value="${meterTestingReport.meterPTRatio}" class="form-control"
								style="width: 100%" onblur="setOmfValue()"></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">(J) Dial
								Multiplying Factor (DMF) of Meter</td>
							<td><input type="number" id="dmfOfMeter" name="dmfOfMeter"
								value="${meterTestingReport.dmfOfMeter}" class="form-control"
								style="width: 100%" onblur="setOmfValue()"></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">(K)
								Overall Multiplying Factor (OMF)</td>
							<td><input type="number" id="omf" name="omf"
								value="${meterTestingReport.omf}" class="form-control"
								style="width: 100%" readonly="readonly"></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">Overall
								Remarks</td>
							<td><textarea name="overallRemarks" id="overallRemarks"
									class="form-control">${meterTestingReport.overallRemarks} </textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<c:if test="${meterTestingReport.finalSubmit==false}">
					<a
						href="${pageContext.request.contextPath}/mtr/meterTestReportList.do"
						style="border-radius: 6px;" class="btn btn-danger"> <i
						class="fa fa-times"></i> &nbsp;Cancel
					</a>
					<button type="submit" class="btn btn-success" id="updateButton"
						style="border-radius: 6px;">
						<i class="fa fa-fw fa-save"></i>&nbsp;Update
					</button>
				</c:if>
			</div>
		</form>

	</div>
</div>

<script>
	function setOmfValue() {
		var meterCTRatio = $('#meterCTRatio').val() || 0;
		var meterPTRatio = $('#meterPTRatio').val() || 0;
		var dmf = $('#dmfOfMeter').val() || 0;
		$('#omf').val(meterCTRatio * meterPTRatio * dmf);
	}
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap_multiselect/bootstrap-multiselect.min.js"></script>

<%@include file="../../common/ibcsFooter.jsp"%>