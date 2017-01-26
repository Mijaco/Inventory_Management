<%@include file="../../common/wsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_multiselect/bootstrap-multiselect.css" />

<!-- @author: Ihteshamul Alam, IBCS -->

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

input {
	border: 1px solid grey;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">



		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			New HT-CT Meter Test Report Form</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
		<div class="col-sm-12">
			<a
				href="${pageContext.request.contextPath}/mtr/meterTestReportList.do"
				style="border-radius: 6px;" class="btn btn-primary"> <i
				class="fa fa-list"></i>&nbsp; Meter Testing Report List
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<form class="form-horizontal"
			action="${pageContext.request.contextPath}/mtr/meterTestReportSave.do"
			method="POST" id="htCtMeterTestReportForm">

			<!-- MST Info :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Tracking No:</td>
							<td><input type="text" id="trackingNo" name="trackingNo"
								onblur="checkTrackingNo()" class="form-control"
								style="width: 100%">
								<h5 class="text-danger hide" id="tnerror">
									<strong>Invalid Tracking No.</strong>
								</h5></td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Sales &amp; Distribution
								Division</td>
							<td><select name="deptId" class="form-control" id="deptId">
									<option disabled="disabled" selected="selected">---
										select a S&amp;D ---</option>
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
									<option value="NEW">NEW</option>
									<!-- <option value="OLD">OLD</option> -->
							</select></td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter/Metering Unit Source</td>
							<td><input type="text" id="meterUnitSource"
								name="meterUnitSource" class="form-control" style="width: 100%">
							</td>
						</tr>
						<tr>
							<td class="success text-right" style="font-weight: bold;">CMTL
								Sl. No.:</td>
							<td><input type="text" id="cmtlSlNo" name="cmtlSlNo"
								class="form-control" style="width: 100%"></td>
							<td class="success text-right" style="font-weight: bold;"></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- MST Info :: end -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
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
								name="meterNo" class="form-control" style="width: 100%">
							</td>
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
							</select> &nbsp;&nbsp; <input type="text" name="manufactureName" value=""
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
							</select> &nbsp;&nbsp; <input type="text" name="voltageRating" value=""
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
							</select> &nbsp;&nbsp; <input type="text" name="modelNo" value=""
								placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>
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
							</select> &nbsp;&nbsp; <input type="text" name="currentRating" value=""
								placeholder="Select Current Rating" class="form-control"
								style="width: 90%" /></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Manufacturing Year:</td>
							<td><input type="text" id="manufacturingYear"
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
								value="" placeholder="Active" class="form-control"
								style="width: 90%" /></td>
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
								value="" placeholder="Reactive" class="form-control"
								style="width: 90%" /></td>
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
							</select> &nbsp;&nbsp; <input type="text" name="meterConstant" value=""
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
							</select> &nbsp;&nbsp; <input type="text" name="connectionType" value=""
								placeholder="Connection Type" class="form-control"
								style="width: 90%" /></td>
						</tr>

						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Type:</td>
							<td><input type="text" id="meterType" name="meterType"
								readonly value="HTCT" class="form-control" style="width: 100%"></td>
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
									<option value="YES">YES</option>
									<option value="NO">NO</option>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- Meter, CT & PT Physical Observation :: end -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
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
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb12" name="dpf10lb12"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb" name="dpf10lb"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb05" name="dpf10lb05"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb02" name="dpf10lb02"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb01" name="dpf10lb01"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf10lb005" name="dpf10lb005"
								class="form-control" style="width: 100%"></td>
							<td><select name="dpf10Remarks" id="dpf10Remarks"
								class="form-control" style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>
						</tr>

						<tr>
							<td>PF=0.5L</td>
							<td><input type="text" id="dpf05Llmax" name="dpf05Llmax"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb12" name="dpf05Llb12"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb" name="dpf05Llb"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb05" name="dpf05Llb05"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb02" name="dpf05Llb02"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb01" name="dpf05Llb01"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf05Llb005" name="dpf05Llb005"
								class="form-control" style="width: 100%"></td>
							<td><select name="dpf05LRemarks" id="dpf05LRemarks"
								class="form-control" style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>
						</tr>

						<tr>
							<td>PF=0.8C</td>
							<td><input type="text" id="dpf08Clmax" name="dpf08Clmax"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb12" name="dpf08Clb12"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb" name="dpf08Clb"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb05" name="dpf08Clb05"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb02" name="dpf08Clb02"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb01" name="dpf08Clb01"
								class="form-control" style="width: 100%"></td>
							<td><input type="text" id="dpf08Clb005" name="dpf08Clb005"
								class="form-control" style="width: 100%"></td>
							<td><select name="dpf08CRemarks" id="dpf08CRemarks"
								class="form-control" style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- % of Error report for different Power Factor :: end -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center"
				style="margin-bottom: 10px;">
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
			<!-- Meter Testing Instrument :: start -->
			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<!-- <h6> <strong>Meter Testing Instrument</strong> </h6> -->
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Testing Instrument</td>
							<td>
								<!-- <select id="procMethod" multiple="multiple" required="required" name="procMethod"
								class="form-control">
									<option value="">MTE</option>
									<option value="">PRS-122.3</option>
									<option value="">Landis- &amp; Gyr</option>
									<option value="">Switzerland &amp; Meteronic SYS312</option>
							</select> --> <input type="text" name="meterTestinInstruments"
								id="meterTestinInstruments" class="form-control"
								value="MTE, PRS-122.3, Landis- &amp; Gyr, Switzerland &amp; Meteronic SYS312">
							</td>
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
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>
							<td class="success col-md-2 text-right"
								style="font-weight: bold;">(ii) No Load Test Report</td>
							<td><select name="noLoadTestReport" id="noLoadTestReport"
								class="form-control">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>
							<td class="success col-md-2 text-right"
								style="font-weight: bold;">(iii) Dial Test Report</td>
							<td><select name="dialTestReport" id="dialTestReport"
								class="form-control">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
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
										class="form-control" /> <span class="input-group-addon">KWH</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(ii) Off Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundOffPeak"
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
										class="form-control" /> <span class="input-group-addon">KW</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iv) Reactive (Total)</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsFoundReActive"
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
										class="form-control" /> <span class="input-group-addon">KWH</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(ii) Off Peak</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftOffPeak" name="mrAsLeftOffPeak"
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
										class="form-control" /> <span class="input-group-addon">KW</span>
								</div>
							</td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">(iv) Reactive (Total)</td>
							<td>
								<div class="input-group">
									<input type="text" id="mrAsLeftReActive"
										name="mrAsLeftReActive" class="form-control" /> <span
										class="input-group-addon">KVArH</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- ---------------- -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
			<!-- -----CT Start------- -->

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
								name="ctSlNoPhase1R" class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ctSlNoPhase2Y"
								name="ctSlNoPhase2Y" class="form-control" style="width: 100%"></td>
							<td class="col-sm-3"><input type="text" id="ctSlNoPhase3B"
								name="ctSlNoPhase3B" class="form-control" style="width: 100%"></td>
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
								value="" placeholder="Select Manufacturer" class="form-control"
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
								value="" placeholder="Select Manufacturer" class="form-control"
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
								value="" placeholder="Select Manufacturer" class="form-control"
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
								value="" placeholder="Select Model" class="form-control"
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
								value="" placeholder="Select Model" class="form-control"
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
								value="" placeholder="Select Model" class="form-control"
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
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase1R" value=""
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
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase2Y" value=""
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
							</select> &nbsp;&nbsp; <input type="text" name="ctBurdenPhase3B" value=""
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
								value="" placeholder="Accuracy Class" class="form-control"
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
								value="" placeholder="Accuracy Class" class="form-control"
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
								value="" placeholder="Accuracy Class" class="form-control"
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
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase1R" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>
							<!-- <td><input type="text" id="ctRatioPhase2Y"
								name="ctRatioPhase2Y" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctRatioPhase2Y" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase2Y" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>
							<!-- <td><input type="text" id="ctRatioPhase3B"
								name="ctRatioPhase3B" class="form-control" style="width: 100%"></td> -->
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ctRatioPhase3B" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ctRatioPhase3B" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>
						</tr>
						<tr>
							<td>Megger Test Report</td>
							<td><select name="ctMeggerTestRptPhase1R"
								id="ctMeggerTestRptPhase1R" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

							<td><select name="ctMeggerTestRptPhase2Y"
								id="ctMeggerTestRptPhase2Y" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

							<td><select name="ctMeggerTestRptPhase3B"
								id="ctMeggerTestRptPhase3B" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

						</tr>
					</tbody>
				</table>
			</div>
			<!-- -------CT End------- -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
			<!-- -------PT Start-------- -->

			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(G) Line Potential Transformer(PT)</strong>
				</h6>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Particulars</th>
							<th class="col-sm-3">Phase - 1 / (R)</th>
							<th class="col-sm-3">Phase - 2 / (Y)</th>
							<th class="col-sm-3">Phase - 3 / (B)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>PT Serial No</td>
							<td><input type="text" id="ptSlNoPhase1R"
								name="ptSlNoPhase1R" class="form-control" style="width: 100%"></td>
							<td><input type="text" id="ptSlNoPhase2Y"
								name="ptSlNoPhase2Y" class="form-control" style="width: 100%"></td>
							<td><input type="text" id="ptSlNoPhase3B"
								name="ptSlNoPhase3B" class="form-control" style="width: 100%"></td>
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
								value="" placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBrandMfgNamePhase2Y" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBrandMfgNamePhase2Y"
								value="" placeholder="Select Manufacturer" class="form-control"
								style="width: 90%" /></td>
							<td class="select-editable"><select style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBrandMfgNamePhase3B" class="form-control">
									<option value="Secure, India">Secure, India</option>
									<option value="Siemens, Germany">Siemens, Germany</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBrandMfgNamePhase3B"
								value="" placeholder="Select Manufacturer" class="form-control"
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
								value="" placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptTypeModelPhase2Y" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptTypeModelPhase2Y"
								value="" placeholder="Select Model" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptTypeModelPhase3B" class="form-control">
									<option value="LZZBJ9-10A">LZZBJ9-10A</option>
									<option value="LZZBJ9-10B">LZZBJ9-10B</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptTypeModelPhase3B"
								value="" placeholder="Select Model" class="form-control"
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
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase1R" value=""
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBurdenPhase2Y" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase2Y" value=""
								placeholder="Select Burden" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptBurdenPhase3B" class="form-control">
									<option value="10VA">10VA</option>
									<option value="15VA">15VA</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptBurdenPhase3B" value=""
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
								value="" placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptAccuracyPhase2Y" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptAccuracyPhase2Y"
								value="" placeholder="Accuracy Class" class="form-control"
								style="width: 90%" /></td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptAccuracyPhase3B" class="form-control">
									<option value="0.5">0.5</option>
									<option value="1.0">1.0</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptAccuracyPhase3B"
								value="" placeholder="Accuracy Class" class="form-control"
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
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase1R" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>

							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptRatioPhase2Y" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase2Y" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>
							<td class="select-editable"
								style="padding-top: 13px; padding-bottom: 13px;"><select
								style="width: 97%;"
								onchange="this.nextElementSibling.value=this.value"
								id="ptRatioPhase3B" class="form-control">
									<option value="30:5">30:5</option>
									<option value="32:5">32:5</option>
							</select> &nbsp;&nbsp; <input type="text" name="ptRatioPhase3B" value=""
								placeholder="Ratio" class="form-control" style="width: 90%" />
							</td>
						</tr>
						<tr>
							<td>Megger Test Report</td>
							<td><select name="ptMeggerTestRptPhase1R"
								id="ptMeggerTestRptPhase1R" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

							<td><select name="ptMeggerTestRptPhase2Y"
								id="ptMeggerTestRptPhase2Y" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

							<td><select name="ptMeggerTestRptPhase3B"
								id="ptMeggerTestRptPhase3B" class="form-control"
								style="width: 100%;">
									<option value="PASSED">PASSED</option>
									<option value="FAILED">FAILED</option>
							</select></td>

						</tr>
					</tbody>
				</table>
			</div>

			<!-- -----PT End------------ -->
			<!-- <div class="col-md-12 col-sm-12 col-xs-12" align="center" >
				<button type="submit" class="btn btn-success btn-xs saveButton"
					id="" disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div> -->
			<!-- -----Others Start------ -->

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12" style="margin-top:10px!important;">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">Remarks
								on CT &amp; PT</td>
							<td><textarea name="ctPtRemarks" id="ctPtRemarks"
									class="form-control">Name plate data ( Printed paper ) are attached on the body of CT and PT.</textarea>
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
							<td><input type="number" id="meterCTRatio"
								name="meterCTRatio" class="form-control" style="width: 100%"
								onblur="setOmfValue()"></td>
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
							<td><input type="number" id="meterPTRatio"
								name="meterPTRatio" class="form-control" style="width: 100%"
								onblur="setOmfValue()"></td>
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
								class="form-control" style="width: 100%" onblur="setOmfValue()"></td>
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
								readonly="readonly" class="form-control" style="width: 100%"
								value="0"></td>
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
									class="form-control">Current Transformer (CT) and Potential Tranformer (PT) were tested from CERS, BPDB; report attached.</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<!-- <button type="submit" class="btn btn-success" id="saveButton"
					disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button> -->
				<button type="submit" class="btn btn-success saveButton" id=""
					disabled style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
				<button type="reset" class="btn btn-danger"
					style="border-radius: 6px;">
					<i class="fa fa-fw fa-refresh"></i>&nbsp;Reset
				</button>
			</div>
		</form>

	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap_multiselect/bootstrap-multiselect.min.js"></script>

<script>
	function checkTrackingNo() {
		var trackingNo = $('#trackingNo').val(); //tnerror
		//var saveButton = $('#saveButton');
		var saveButton = $('.saveButton');
		var baseURL = $('#contextPath').val();

		$.ajax({
			url : baseURL + "/mtr/checkTrackingNo.do",
			data : {
				"trackingNo" : trackingNo
			},
			success : function(data) {
				if (data == "success") {
					if ($('#tnerror').hasClass('hide') == false) {
						$('#tnerror').addClass('hide');
					}
					saveButton.prop('disabled', false);
				} else if (data == "failed") {

					$('#tnerror').html(
							"<strong>This field is required</strong>")
							.removeClass('hide');
					saveButton.prop('disabled', true);
				} else {
					$('#tnerror')
							.html("<strong>Tracking No. is used.</strong>")
							.removeClass('hide');
					saveButton.prop('disabled', true);
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		}); // ajax
	}

	function setOmfValue() {
		var meterCTRatio = $('#meterCTRatio').val() || 0;
		var meterPTRatio = $('#meterPTRatio').val() || 0;
		var dmf = $('#dmfOfMeter').val() || 0;
		$('#omf').val(meterCTRatio * meterPTRatio * dmf);
	}
</script>

<%@include file="../../common/ibcsFooter.jsp"%>