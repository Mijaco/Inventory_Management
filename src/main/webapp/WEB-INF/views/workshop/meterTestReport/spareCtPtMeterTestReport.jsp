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
			Spare CT/PT Test Report Form</h2>
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
			method="POST" id="ctptMeterTestReportForm">

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
						<!-- <tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Category:</td>
							<td><select name="meterCategory" id="meterCategory"
								class="form-control" style="width: 100%;">
									<option value="NEW">NEW</option>
									<option value="OLD">OLD</option>
							</select></td>

							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter/Metering Unit Source</td>
							<td><input type="text" id="meterUnitSource"
								name="meterUnitSource" class="form-control" style="width: 100%">
							</td>
						</tr> -->
						<tr>
							<td class="success text-right" style="font-weight: bold;">CMTL
								Sl. No.:</td>
							<td><input type="text" id="cmtlSlNo" name="cmtlSlNo"
								class="form-control" style="width: 100%"></td>
							<td class="success text-right" style="font-weight: bold;">Meter
								Category:</td>
							<td><select name="meterCategory" id="meterCategory"
								class="form-control" style="width: 100%;">
									<option value="NEW">NEW</option>
									<option value="OLD">OLD</option>
							</select></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Type:</td>
							<td><select name="meterType" id="meterType"
								class="form-control" style="width: 100%;">
									<option value="CT">CT</option>
									<option value="PT">PT</option>
							</select></td>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;"></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- MST Info :: end -->

			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(A) Line Current Transformer(CT)</strong>
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

			<div style="position: relative; margin-top: 10px !important;"
				class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<h6>
					<strong>(B) Line Potential Transformer(PT)</strong>
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

			<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold; vertical-align: middle;">Remarks</td>
							<td><textarea name="ctPtRemarks" id="ctPtRemarks"
									class="form-control">This Combo box is for the overall comments on the CT/PT or both</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12" align="center">
				<button type="submit" class="btn btn-success"
					style="border-radius: 6px;">
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