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
			Spare CT/PT Test Report Show</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="col-sm-12">
			<a
				href="${pageContext.request.contextPath}/mtr/meterTestReportList.do"
				style="border-radius: 6px;" class="btn btn-primary"> <i
				class="fa fa-list"></i>&nbsp; Meter Testing Report List
			</a>

			<c:if test="${meterTestingReport.finalSubmit==false}">
				<a href="#" class="btn btn-warning" style="border-radius: 6px;"
					onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportFinalSubmit.do',{'id':'${meterTestingReport.id}'},'POST')">
					<i class="fa fa-fw fa-paper-plane"></i>&nbsp;Final Submit
				</a>
				<a href="#" class="btn btn-success" style="border-radius: 6px;"
					onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportEdit.do',{'id':'${meterTestingReport.id}'},'POST')">
					<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
				</a>
			</c:if>
			<c:if test="${meterTestingReport.finalSubmit==true}">
				<a target="_blank"
					href="${pageContext.request.contextPath}/mtr/meterTestingReport.do?id=${meterTestingReport.id}"
					class="btn btn-success" style="border-radius: 6px;"> <i
					class="fa fa-fw fa-file-pdf-o"></i>&nbsp;Report
				</a>
			</c:if>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<!-- MST Info :: start -->
		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Tracking
							No:</td>
						<td>${meterTestingReport.trackingNo}</td>

						<td class="success col-md-3 text-right" style="font-weight: bold;">Sales
							&amp; Distribution Division</td>
						<td>${meterTestingReport.department.deptName }</td>
					</tr>
					<tr>
						<td class="success text-right" style="font-weight: bold;">CMTL
							Sl. No.:</td>
						<td>${meterTestingReport.cmtlSlNo}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter
							Category:</td>
						<td>${meterTestingReport.meterCategory}</td>


					</tr>
					<tr>
						<td class="success text-right" style="font-weight: bold;">Meter
							Type:</td>
						<td>${meterTestingReport.meterType}</td>
						<td class="success text-right" style="font-weight: bold;">Date
							:</td>
						<td><fmt:formatDate value="${meterTestingReport.reportDate}"
								pattern="dd-MM-yyyy hh:mm:ss a" /></td>

					</tr>
				</tbody>
			</table>
		</div>
		<!-- MST Info :: end -->

		<!-- Meter, CT & PT Physical Observation :: end -->

		<!--  Line Current Transformer(CT) :: start -->
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
						<td>${meterTestingReport.ctSlNoPhase1R}</td>
						<td>${meterTestingReport.ctSlNoPhase2Y}</td>
						<td>${meterTestingReport.ctSlNoPhase3B}</td>
					</tr>
					<tr>
						<td>CT Brand/Mfg. Name</td>
						<td>${meterTestingReport.ctBrandMfgNamePhase1R}</td>
						<td>${meterTestingReport.ctBrandMfgNamePhase2Y}</td>
						<td>${meterTestingReport.ctBrandMfgNamePhase3B}</td>
					</tr>
					<tr>
						<td>Type/Model</td>
						<td>${meterTestingReport.ctTypeModelPhase1R}</td>
						<td>${meterTestingReport.ctTypeModelPhase2Y}</td>
						<td>${meterTestingReport.ctTypeModelPhase3B}</td>
					</tr>
					<tr>
						<td>CT Burden</td>
						<td>${meterTestingReport.ctBurdenPhase1R}</td>
						<td>${meterTestingReport.ctBurdenPhase2Y}</td>
						<td>${meterTestingReport.ctBurdenPhase3B}</td>
					</tr>
					<tr>
						<td>Accuracy</td>
						<td>${meterTestingReport.ctAccuracyPhase1R}</td>
						<td>${meterTestingReport.ctAccuracyPhase2Y}</td>
						<td>${meterTestingReport.ctAccuracyPhase3B}</td>
					</tr>
					<tr>
						<td>CT Ratio</td>
						<td>${meterTestingReport.ctRatioPhase1R}</td>
						<td>${meterTestingReport.ctRatioPhase2Y}</td>
						<td>${meterTestingReport.ctRatioPhase3B}</td>
					</tr>
					<tr>
						<td>Megger Test Report</td>
						<td>${meterTestingReport.ctMeggerTestRptPhase1R}</td>
						<td>${meterTestingReport.ctMeggerTestRptPhase2Y}</td>
						<td>${meterTestingReport.ctMeggerTestRptPhase3B}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!--  Line Current Transformer(CT) :: end -->
		<!--  Line Potential Transformer(PT) :: start -->
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
						<td>${meterTestingReport.ptSlNoPhase1R}</td>
						<td>${meterTestingReport.ptSlNoPhase2Y}</td>
						<td>${meterTestingReport.ptSlNoPhase3B}</td>
					</tr>
					<tr>
						<td>PT Brand/Mfg. Name</td>
						<td>${meterTestingReport.ptBrandMfgNamePhase1R}</td>
						<td>${meterTestingReport.ptBrandMfgNamePhase2Y}</td>
						<td>${meterTestingReport.ptBrandMfgNamePhase3B}</td>
					</tr>
					<tr>
						<td>Type/Model</td>
						<td>${meterTestingReport.ptTypeModelPhase1R}</td>
						<td>${meterTestingReport.ptTypeModelPhase2Y}</td>
						<td>${meterTestingReport.ptTypeModelPhase3B}</td>
					</tr>
					<tr>
						<td>PT Burden</td>
						<td>${meterTestingReport.ptBurdenPhase1R}</td>
						<td>${meterTestingReport.ptBurdenPhase2Y}</td>
						<td>${meterTestingReport.ptBurdenPhase3B}</td>
					</tr>
					<tr>
						<td>Accuracy</td>
						<td>${meterTestingReport.ptAccuracyPhase1R}</td>
						<td>${meterTestingReport.ptAccuracyPhase2Y}</td>
						<td>${meterTestingReport.ptAccuracyPhase3B}</td>
					</tr>
					<tr>
						<td>PT Ratio</td>
						<td>${meterTestingReport.ptRatioPhase1R}</td>
						<td>${meterTestingReport.ptRatioPhase2Y}</td>
						<td>${meterTestingReport.ptRatioPhase3B}</td>
					</tr>
					<tr>
						<td>Megger Test Report</td>
						<td>${meterTestingReport.ptMeggerTestRptPhase1R}</td>
						<td>${meterTestingReport.ptMeggerTestRptPhase2Y}</td>
						<td>${meterTestingReport.ptMeggerTestRptPhase3B}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--  Line Potential Transformer(PT) :: end -->
		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-3 text-right"
							style="font-weight: bold; vertical-align: middle;">Remarks</td>
						<td><textarea readonly="readonly" id="ctPtRemarks"
								class="form-control">${meterTestingReport.ctPtRemarks}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
			<c:if test="${meterTestingReport.finalSubmit==false}">
				<a href="#" class="btn btn-warning" style="border-radius: 6px;"
					onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportFinalSubmit.do',{'id':'${meterTestingReport.id}'},'POST')">
					<i class="fa fa-fw fa-paper-plane"></i>&nbsp;Final Submit
				</a>
				<a href="#" class="btn btn-success" style="border-radius: 6px;"
					onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportEdit.do',{'id':'${meterTestingReport.id}'},'POST')">
					<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
				</a>
			</c:if>
			<c:if test="${meterTestingReport.finalSubmit==true}">
				<a target="_blank"
					href="${pageContext.request.contextPath}/mtr/meterTestingReport.do?id=${meterTestingReport.id}"
					class="btn btn-success" style="border-radius: 6px;"> <i
					class="fa fa-fw fa-file-pdf-o"></i>&nbsp;Report
				</a>
			</c:if>
		</div>

	</div>
</div>
<%@include file="../../common/ibcsFooter.jsp"%>