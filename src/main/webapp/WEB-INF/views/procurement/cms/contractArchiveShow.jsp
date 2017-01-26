<%@include file="../../common/procurementHeader.jsp"%>
<!-- ----------------------------------------- -->

<style>
textarea {
	resize: none;
}

.multiselect-container.dropdown-menu {
	z-index: 9999999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/mps/dn/demandNoteList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Annexure List
			</a> --%>
		</div>
		<div class="col-md-8">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Contract Management - Show</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">
			<c:if test="${empty paymentList}">
				<h4>No data found in database</h4>
			</c:if>
			
			<c:if test="${!empty paymentList}">
				<div class="table-responsive">
					<table class="table table-bordered" id="contractPaymentTable">
						<thead>
							<tr style="background: #579EC8; color: white;">
								<th>Contract No.</th>
								<th>Contractor Name</th>
								<th>Invoice No.</th>
								<th>Approved Inv. Doc</th>
								<th>Payment Date</th>
								<th>Payment Amount</th>
								<th>Payment By</th>
								<th>Cheque No.</th>
								<th>Bank Name</th>
								<th>Branch</th>
								<th>Cheque Received by</th>
								<th>Cheque Doc</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="totalPayment" value="${0}"/>
							<c:forEach items="${paymentList}" var="contractPayment">
								<tr>
									<td>${contractPayment.contractManagement.contractNo}</td>
									<td>${contractPayment.contractManagement.contractorName}</td>
									<td>${contractPayment.descoInvNo}</td>
									<td>
										<c:if
											test="${!empty contractPayment.contractorAppInvDoc}">
											<form target="_blank"
												action="${pageContext.request.contextPath}/ws/download.do"
												method="POST">
												<input type="hidden"
													value="${contractPayment.contractorAppInvDoc}"
													name="downloadPath" />
												<button type="submit" class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></button>
											</form>
										</c:if>
									</td>
									<td>${contractPayment.paymentDate}</td>
									<td>
									 <c:set var="totalPayment" value="${totalPayment + contractPayment.paymentAmount}" />
									<fmt:formatNumber type="number" groupingUsed="false" value="${contractPayment.paymentAmount}" />
									<%-- ${contractPayment.paymentAmount} --%></td>
									<td>${contractPayment.paymentBy}</td>
									<td>${contractPayment.checkNo}</td>
									<td>${contractPayment.bankName}</td>
									<td>${contractPayment.branchName}</td>
									<td>${contractPayment.checkReceivedBy}</td>
									<td>
										<c:if test="${!empty contractPayment.descoCheckDoc}">
											<form target="_blank"
												action="${pageContext.request.contextPath}/ws/download.do"
												method="POST">
												<input type="hidden" value="${contractPayment.descoCheckDoc}"
													name="downloadPath" />
												<button type="submit" class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></button>
											</form>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<tr style="border:0;">
							<td style="border-right:0;"></td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;text-align:right"> Total = </td>
							<td></td>
							<td> <fmt:formatNumber type="number" groupingUsed="false" value="${totalPayment}" /> </td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;"> </td>
							<td style="border-right:0;"> </td>
							<td> </td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:if>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script type="text/javascript">	
	$(document).ready(function() {
		$('#contractPaymentTable').DataTable({
			"order" : [ [ 4, "desc" ] ],
			"bLengthChange" : false,
			"paging": false,
			"info" : false
		});

	});
	
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});		
</script>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>