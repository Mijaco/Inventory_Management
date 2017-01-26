<%@include file="../../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-12">

			<input type="hidden" value="${auctionDeliveryMst.storeTicketNo}"
				id="ticketNo" /> <input type="text" id="birtAddr"
				value="${properties['birtAddr']}" hidden="true" />

			<h2 class="center blue ubuntu-font col-md-12"
				style="margin-top: 0; font-style: italic;">Your Store Ticket
				${auctionDeliveryMst.storeTicketNo} is ready.</h2>
		</div>



	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
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
						var param1 = document.getElementById("ticketNo").value;
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/auction_delivery_store_ticket.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TICKET_NO='
								+ param1;

					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
