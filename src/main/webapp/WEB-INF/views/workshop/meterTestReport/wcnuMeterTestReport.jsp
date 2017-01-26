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
</style>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Whole Current Meter (New/Used) Test Report Form</h2>
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
			method="POST" id="wcMeterTestReportForm">

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
								style="font-weight: bold;">Account No.:</td>
							<td><input style="width: 100%;" type="text" id="accountNo"
								name="accountNo"></td>

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
							<td class="success text-right" style="font-weight: bold;">Consumer
								Details:</td>
							<td><textarea name="consumerDetails" id="consumerDetails"
									class="form-control"></textarea></td>
						</tr>
						<tr>
							<td class="success text-right" style="font-weight: bold;">Meter
								Category:</td>
							<td><select name="meterCategory" id="meterCategory"
								class="form-control" style="width: 100%;">
									<option value="NEW">NEW</option>
									<option value="OLD">OLD</option>
							</select></td>
							<td class="success text-right" style="font-weight: bold;"></td>
							<td></td>
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
							<td><input type="hidden" id="meterType" name="meterType"
								readonly value="WC" class="form-control" style="width: 100%">
								<input type="text" id="mType" name="mType" readonly
								value="Whole Current Meter (New/Used)" class="form-control"
								style="width: 100%"></td>
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
								style="font-weight: bold;">(B) Physical Observation:</td>
							<td><select name="physicalObservation"
								id="physicalObservation" class="form-control">
									<option value="YES">YES</option>
									<option value="NO">NO</option>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- <div class="col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tr class="col-md-6">
							<td class="col-md-6">
								<h6>
									<strong>(B) Meter, CT &amp; PT Physical Observation:</strong>
								</h6>
							</td>
							<td>
								<select name="" id="" class="form-control">
									<option value="YES">YES</option>
									<option value="NO">NO</option>
								</select>
							</td>
						</tr>
				</table>
			</div> -->
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
								style="font-weight: bold;">(D) &nbsp; &nbsp; &nbsp;
								&nbsp;(i) Starting Test Report</td>
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
					<strong>(E) Reading of Meter: </strong>
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


			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-md-3 success text-right"
								style="font-weight: bold;">(F) Meter Seal Information</td>
							<td><textarea style="width: 100%" name="sealInfo"
									id="sealInfo">This Combo box is for comments on meter seal.</textarea>
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
								style="font-weight: bold; vertical-align: middle;">Overall
								Remarks</td>
							<td><textarea name="overallRemarks" id="overallRemarks"
									class="form-control"></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success" id="saveButton"
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
		var saveButton = $('#saveButton');
		var baseURL = $('#contextPath').val();

		$.ajax({
			url : baseURL + "/mtr/checkTrackingNo.do",
			data : {
				"trackingNo" : trackingNo
			},
			success : function(data) {
				if (data == "success") {
					$('#tnerror').addClass('hide');
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

	$(document).ready(function() {
		$('#procMethod').multiselect({
			// enableFiltering : true,
			includeSelectAllOption : true,
			maxHeight : 350,
			onDropdownShown : true
		});
	});
</script>
<%@include file="../../common/ibcsFooter.jsp"%>