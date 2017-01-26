<%@include file="../../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-8">
			<div class="o_form_buttons_edit col-md-4" style="display: block;">
				<a
					href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do"
					style="text-decoration: none;" class="btn btn-success btn-sm">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					Store Ticket List
				</a> <input type="hidden" value="${ticketNo}" id="ticketNo" /> <input
					type="text" id="birtAddr" value="${properties['birtAddr']}"
					hidden="true" />
			</div>
			<h1 class="center blue col-md-8"style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;" >
			Your Store Ticket ${ticketNo} is ready.
			</h1>
		</div>
		<div class="col-xs-4">
			<c:if test="${!empty multipleGPNoList}">	
				<p class="bold green">Auto Generated Gate Pass List</p>		
				<c:forEach items="${multipleGPNoList}" var="gatePassNo">
					<a 	href="${pageContext.request.contextPath}/cs/gatePassGenerate.do?gatePassNo=${gatePassNo}"
					target="_blank">
						<c:out value="${gatePassNo}"></c:out>
					</a>
				</c:forEach>
			</c:if>
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
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/wsx_to_cs_req_st.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TICKET_NO='
								+ param1;

					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
