<%@include file="../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">As Built Report For <i> <b> ${operationId}  </b> </i>.
		</h1>
		 <div class="o_form_buttons_edit" style="display: block;">
			<!-- <a href="${pageContext.request.contextPath}/ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span> As Built Report
			</a> --> <input type="hidden" value="${operationId}" id="rrNo" /> <input type="text"
				id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
		</div>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<div class="col-xs-12">
				<!--  <iframe frameborder="0" class="col-md-12" src="http://localhost:8085/birt/frameset?__report=desco/procurement/requisition_report.rptdesign&P_PND_NO='DESCO/2015-2016/PRF001'"-->
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
						var param1 = document.getElementById("rrNo").value;
						//document.getElementById('requisitionReportFrame').src = birtAddr	+ '?__report=desco/procurement/cs_receiving_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RR_NO='+ param1;
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/ws_as_built_material_list.rptdesign&__toolbar=false&__showtitle=false&__title=&P_AS_BUILT_NO='+ param1;
					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
