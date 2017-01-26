<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer
			Test Report : ${transformer.id}</h1>
		<input type="hidden" id="param1" value="${transformer.id}" /> <input
			type="hidden" id="birtAddr" value="${properties['birtAddr']}" /> <input
			type="hidden" id="phase" value="${transformer.transformerType}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->
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
						var param1 = $('#param1').val();
						var phase = $('#phase').val();
						// new String(phase).valueOf() == new String("1-PHASE").valueOf()
						if (phase.valueOf() == "1-PHASE") {
							document.getElementById('requisitionReportFrame').src = birtAddr
									+ '?__report=desco/procurement/transformer_test_report_1.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TSF_REG_MST_ID='
									+ param1;
						} else if (phase.valueOf() == "3-PHASE") {
							document.getElementById('requisitionReportFrame').src = birtAddr
									+ '?__report=desco/procurement/transformer_test_report_3.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TSF_REG_MST_ID='
									+ param1;
						}

					});

	function query() {
		var param1 = document.getElementById("param1").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/cost_estimation_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_PND_NO='
				+ param1;
	}
</script>
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
