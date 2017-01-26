<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">
			Your Store Ticket  ${ticketNo} is ready.
		</h1>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Ticket List
			</a>
			<input type="hidden" value="${ticketNo}" id="ticketNo"/> 
			<input type="text" id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<%-- <div class="row" style="background-color: white; padding: 10px; padding-left: 20px;">
		<h3 class="center blue"> Your Store Ticket ${ticketNo} is ready. Please go to report menu or
			<!-- <a href="http://localhost:8085/birt/frameset?__report=desco/procurement/cs_store_ticket_issue_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TICKET_NO=${ticketNo}" target="_blank" class="btn-info" style="border-radius:6px; margin:5px">Click Here </a>  to print the Store Ticket. -->
			<a href="${pageContext.request.contextPath}/cs/store/csStoreTicketReport.do" target="_blank" class="btn-info" style="border-radius:6px; margin:5px">Click Here </a>  to print the Store Ticket.
		</h3>
	</div> --%>




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
	/* var birtAddr =${properties['birtAddr']};
	alert(birtAddr); */
	
	var birtAddr = document.getElementById("birtAddr").value;
	//alert(birtAddr);
	
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("ticketNo").value;
	
						//var param2   = document.getElementById("param2").value;
						//param1="DESCO/2015-2016/PRF001";
						document.getElementById('requisitionReportFrame').src = birtAddr	+ '?__report=desco/procurement/cs_store_ticket_receive_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TICKET_NO='+ param1;

					});
	
	</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
