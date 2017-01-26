<%@include file="../../../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<!-- @author: Abu Taleb, Programmer, IBCS -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">Your Meter Testing Report
			${meterTestingReport.trackingNo} : ${meterTestingReport.meterNo} is
			ready.</h1>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/mtr/meterTestReportFinalList.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Meter
				Test Report Final List
			</a> <input type="hidden" value="${meterTestingReport.id}"
				id="meterTestingReportId" /> <input type="text" id="birtAddr"
				value="${properties['birtAddr']}" hidden="true" />

		</div>
	</div>


	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<div class="col-xs-12">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 410px;"
					id="requisitionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>

			<!-- --------------------- -->
		</div>
	</div>

</div>
<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;

	$(document)
			.ready(
					function() {
						var param1 = document
								.getElementById("meterTestingReportId").value;
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/lt_ct_meter_test_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_PRIMARY_ID='
								+ param1;

					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
