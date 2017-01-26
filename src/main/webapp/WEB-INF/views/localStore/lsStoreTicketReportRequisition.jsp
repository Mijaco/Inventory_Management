<%@include file="../common/lsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="">
			<div class="o_form_buttons_edit col-md-2" style="display: block;">
				<a
					href='${pageContext.request.contextPath}/c2ls/gatePassGenerate.do?ticketNo=${ticketNo}'
					style="text-decoration: none;" class="btn btn-success btn-sm">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					Gate Pass Report
				</a> <input type="hidden" value="${ticketNo}" id="ticketNo" /> <input
					type="text" id="birtAddr" value="${properties['birtAddr']}"
					hidden="true" />

			</div>

			<h2 class="col-md-10 center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Ticket Report: ${ticketNo}</h2>

		</div>



	</div>




	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<div class="col-xs-12">
				<!--  <iframe frameborder="0" class="col-md-12" src="http://localhost:8085/birt/frameset?__report=desco/procurement/requisition_report.rptdesign&P_PRF_NO='DESCO/2015-2016/PRF001'"-->
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
						var param1 = document.getElementById("ticketNo").value;

						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/con_to_ls_requisition_store_ticket.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TICKET_NO='
								+ param1;

					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
