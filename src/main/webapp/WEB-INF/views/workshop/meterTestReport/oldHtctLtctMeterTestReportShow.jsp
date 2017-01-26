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
			Used Meter Test Report Show</h2>
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
						<td class="success col-md-3 text-right" style="font-weight: bold;">Account
							No.:</td>
						<td>${meterTestingReport.accountNo}</td>

						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter/Metering
							Unit Source</td>
						<td>${meterTestingReport.meterUnitSource}</td>
					</tr>
					<tr>
						<td class="success text-right" style="font-weight: bold;">CMTL
							Sl. No.:</td>
						<td>${meterTestingReport.cmtlSlNo}</td>
						<td class="success text-right" style="font-weight: bold;">Consumer
							Details:</td>
						<td><textarea readonly="readonly" id="consumerDetails"
								class="form-control">${meterTestingReport.consumerDetails}</textarea>
						</td>
					</tr>
					<tr>
						<td class="success text-right" style="font-weight: bold;">Meter
							Category:</td>
						<td>${meterTestingReport.meterCategory}</td>
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
						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter
							No:</td>
						<td class="col-md-3">${meterTestingReport.meterNo}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Manufacturer
							Name:</td>
						<td>${meterTestingReport.manufactureName}</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Voltage
							Rating</td>
						<td class="col-md-3 col-sm-3 col-xs-3">
							${meterTestingReport.voltageRating}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Model
							No.:</td>
						<td class="col-md-3 col-sm-3 col-xs-3">${meterTestingReport.modelNo}
						</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Current
							Rating:</td>
						<td class="col-md-3 col-sm-3 col-xs-3">${meterTestingReport.currentRating}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Manufacturing
							Year:</td>
						<td>${meterTestingReport.manufacturingYear}</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Accuracy
							Class:</td>
						<td class="col-md-3 col-sm-3 col-xs-3">Active :
							${meterTestingReport.accuracyClassActive} Re-Active :
							${meterTestingReport.accuracyClassReActive}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter
							Constant:</td>
						<td class="col-md-3 col-sm-3 col-xs-3">
							${meterTestingReport.meterConstant}</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter
							Type:</td>
						<td>${meterTestingReport.meterType}</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">Connection
							Type:</td>
						<td class="col-md-3 col-sm-3 col-xs-3">${meterTestingReport.connectionType}
						</td>
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
						<td class="success col-md-3 text-right" style="font-weight: bold;">(B)
							Meter, CT &amp; PT Physical Observation:</td>
						<td>${meterTestingReport.physicalObservation=='YES'?'Physical Defected':'No Physical Defect Observed.'}</td>
					</tr>
				</tbody>
			</table>
		</div>


		<!-- Meter, CT & PT Physical Observation :: end -->

		<!-- % of Error report for different Power Factor :: start -->
		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<h6>
				<strong>(C) % of Error report for different Power Factor on
					different load:</strong>
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
						<td>${meterTestingReport.dpf10lmax}</td>
						<td>${meterTestingReport.dpf10lb12}</td>
						<td>${meterTestingReport.dpf10lb}</td>
						<td>${meterTestingReport.dpf10lb05}</td>
						<td>${meterTestingReport.dpf10lb02}</td>
						<td>${meterTestingReport.dpf10lb01}</td>
						<td>${meterTestingReport.dpf10lb005}</td>
						<td>${meterTestingReport.dpf10Remarks}</td>
					</tr>

					<tr>
						<td>PF=0.5L</td>
						<td>${meterTestingReport.dpf05Llmax}</td>
						<td>${meterTestingReport.dpf05Llb12}</td>
						<td>${meterTestingReport.dpf05Llb}</td>
						<td>${meterTestingReport.dpf05Llb05}</td>
						<td>${meterTestingReport.dpf05Llb02}</td>
						<td>${meterTestingReport.dpf05Llb01}</td>
						<td>${meterTestingReport.dpf05Llb005}</td>
						<td>${meterTestingReport.dpf05LRemarks}</td>
					</tr>

					<tr>
						<td>PF=0.8C</td>
						<td>${meterTestingReport.dpf08Clmax}</td>
						<td>${meterTestingReport.dpf08Clb12}</td>
						<td>${meterTestingReport.dpf08Clb}</td>
						<td>${meterTestingReport.dpf08Clb05}</td>
						<td>${meterTestingReport.dpf08Clb02}</td>
						<td>${meterTestingReport.dpf08Clb01}</td>
						<td>${meterTestingReport.dpf08Clb005}</td>
						<td>${meterTestingReport.dpf08CRemarks}</td>
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
						<td class="success col-md-3 text-right" style="font-weight: bold;">Meter
							Testing Instrument</td>
						<td>${meterTestingReport.meterTestinInstruments}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- Meter Testing Instrument :: end -->

		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-2 text-right" style="font-weight: bold;">(D)
							&nbsp; &nbsp; &nbsp; &nbsp;(i) Starting Test Report</td>
						<td>${meterTestingReport.startingTestReport}</td>
						<td class="success col-md-2 text-right" style="font-weight: bold;">(ii)
							No Load Test Report</td>
						<td>${meterTestingReport.noLoadTestReport}</td>
						<td class="success col-md-2 text-right" style="font-weight: bold;">(iii)
							Dial Test Report</td>
						<td>${meterTestingReport.dialTestReport}</td>
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
						<td class="success col-md-3 text-right" style="font-weight: bold;">(i)
							Peak</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsFoundPeak}
								<span class="input-group-addon">KWH</span>
							</div>
						</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(ii)
							Off Peak</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsFoundOffPeak}
								<span class="input-group-addon">KWH</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(iii)
							MD</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsFoundMD}
								<span class="input-group-addon">KW</span>
							</div>
						</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(iv)
							Reactive (Total)</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsFoundReActive}
								<span class="input-group-addon">KVArH</span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			<h6 class="blue" style="font-weight: bold;">As Left:</h6>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(i)
							Peak</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsLeftPeak}
								<span class="input-group-addon">KWH</span>
							</div>
						</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(ii)
							Off Peak</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsLeftOffPeak}
								<span class="input-group-addon">KWH</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(iii)
							MD</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsLeftMD}
								<span class="input-group-addon">KW</span>
							</div>
						</td>
						<td class="success col-md-3 text-right" style="font-weight: bold;">(iv)
							Reactive (Total)</td>
						<td>
							<div class="input-group">${meterTestingReport.mrAsLeftReActive}
								<span class="input-group-addon">KVArH</span>
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
						<td class="col-md-3 success text-right" style="font-weight: bold;">(F)
							Meter Seal Information</td>
						<td><textarea style="width: 100%" readonly="readonly"
								id="sealInfo">${meterTestingReport.sealInfo}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-3 text-right"
							style="font-weight: bold; vertical-align: middle;">(G) Meter
							Current Transformer Ratio</td>
						<td>${meterTestingReport.meterCTRatio}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="table-responsive col-md-12 col-sm-12 col-xs-12">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-md-3 text-right"
							style="font-weight: bold; vertical-align: middle;">(H) Meter
							Potential Transformer</td>
						<td>${meterTestingReport.meterPTRatio}</td>
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
						<td><textarea readonly="readonly" id="overallRemarks"
								class="form-control">${meterTestingReport.overallRemarks}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="col-md-12 col-sm-12 col-xs-12" align="center">
			<c:if test="${meterTestingReport.finalSubmit==false}">
				<a href="#" class="btn btn-warning"
					style="border-radius: 6px;"
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