<%@include file="../../../common/homeHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
			<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
			<input type="hidden" id="auctionId" value="${mstId}" />
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<div class="row">
			<iframe frameborder="0" class="col-md-12"
				style="margin: 10px 0 10px 0; height: 500px;"
				id="requisitionReportFrame">
				<p>Your browser does not support iframes.</p>
			</iframe>
		</div>
	</div>

</div>
<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;
	var param1 = document.getElementById("auctionId").value;
	document.getElementById('requisitionReportFrame').src = birtAddr
			+ '?__report=desco/procurement/condemn_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_CONDEMN_MST_ID='
			+ param1;
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
