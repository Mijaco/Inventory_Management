<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
			<input type="hidden" value="${operationId}" id="rrNo" /> <input
				type="text" id="birtAddr" value="${properties['birtAddr']}"
				hidden="true" />
		</div>

		<h2 class="center blue col-md-8"
			style="font-style: italic; margin-top: 0px; font-family: 'Ubuntu Condensed', sans-serif;">
			Item Requisition Report ${operationId} is ready.</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<div class="row">
			<iframe frameborder="0" class="col-md-12"
				style="margin: 10px 0 10px 0; height: 430px;"
				id="requisitionReportFrame">
				<p>Your browser does not support iframes.</p>
			</iframe>
		</div>
	</div>

</div>
<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("rrNo").value;
						//document.getElementById('requisitionReportFrame').src = birtAddr	+ '?__report=desco/procurement/cs_receiving_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RR_NO='+ param1;
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/ws_to_cs_req.rptdesign&__toolbar=false&__showtitle=false&__title=&P_REQUISITION_NO='
								+ param1;
					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
