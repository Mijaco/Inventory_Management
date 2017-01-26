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
			Spare CT/PT Test Report Form</h2>
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
			method="POST" id="ctptMeterTestReportEditForm">

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
									<option selected
										value="${meterTestingReport.department.deptId}">${meterTestingReport.department.deptName}</option>
									<c:forEach items="${depts}" var="department">
										<option value="${department.deptId}">${department.deptName}</option>
									</c:forEach>
							</select></td>
						</tr>

						<tr>
							<td class="success text-right" style="font-weight: bold;">CMTL
								Sl. No.:</td>
							<td><input type="text" id="cmtlSlNo" name="cmtlSlNo"
								value="${meterTestingReport.cmtlSlNo}" class="form-control"
								style="width: 100%"></td>
							<td class="success text-right" style="font-weight: bold;">Meter
								Category:</td>
							<td><select name="meterCategory" id="meterCategory"
								class="form-control" style="width: 100%;">
									<option selected value="${meterTestingReport.meterCategory}">${meterTestingReport.meterCategory}</option>
									<c:if test="${meterTestingReport.meterCategory=='PT'}">
										<option value="NEW">NEW</option>
									</c:if>
									<c:if test="${meterTestingReport.meterCategory=='CT'}">
										<option value="OLD">OLD</option>
									</c:if>
							</select></td>
						</tr>
						<tr>
							<td class="success col-md-3 text-right"
								style="font-weight: bold;">Meter Type:</td>
							<td><select name="meterType" id="meterType"
								class="form-control" style="width: 100%;">
									<option selected value="${meterTestingReport.meterType}">${meterTestingReport.meterType}</option>
									<c:if test="${meterTestingReport.meterType=='PT'}">
										<option value="CT">CT</option>
									</c:if>
									<c:if test="${meterTestingReport.meterType=='CT'}">
										<option value="PT">PT</option>
									</c:if>
							</select></td>
							<td class="success text-right" style="font-weight: bold;">Date
								:</td>
							<td><fmt:formatDate value="${meterTestingReport.reportDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
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
									<option value="${meterTestingReport.ctMeggerTestRptPhase1R}">${meterTestingReport.ctMeggerTestRptPhase1R}</option>
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
									<option value="${meterTestingReport.ctMeggerTestRptPhase2Y}">${meterTestingReport.ctMeggerTestRptPhase2Y}</option>
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
									<option value="${meterTestingReport.ctMeggerTestRptPhase3B}">${meterTestingReport.ctMeggerTestRptPhase3B}</option>
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
					<strong>(B) Line Potential Transformer(PT)</strong>
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
									<option value="${meterTestingReport.ptMeggerTestRptPhase1R}">${meterTestingReport.ptMeggerTestRptPhase1R}</option>
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
									<option value="${meterTestingReport.ptMeggerTestRptPhase2Y}">${meterTestingReport.ptMeggerTestRptPhase2Y}</option>
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
									<option value="${meterTestingReport.ptMeggerTestRptPhase3B}">${meterTestingReport.ptMeggerTestRptPhase3B}</option>
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
								style="font-weight: bold; vertical-align: middle;">Remarks</td>
							<td><textarea name="ctPtRemarks" id="ctPtRemarks"
									class="form-control">${meterTestingReport.ctPtRemarks}</textarea></td>
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

<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap_multiselect/bootstrap-multiselect.min.js"></script>

<%@include file="../../common/ibcsFooter.jsp"%>