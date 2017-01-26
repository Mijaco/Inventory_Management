<%@include file="../../common/procurementHeader.jsp"%>
<!-- ----------------------------------------- -->
<!-- Author: Ihteshamul Alam, IBCS -->

<style>
textarea {
	resize: none;
}

.ui-autocomplete-input {
	padding-left: -10px !important;
}

</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-12">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Payment List of Contractor
			</h1>
		</div>
	</div>
	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<div class="table-responsive">
			<c:if test="${empty contractPaymentList}">
				<h4 class="center text-success">No data found in database.</h4>
			</c:if>
			
			<c:if test="${!empty contractPaymentList}">
				<table class="table table-bordered" id="contractPayment">
					<thead>
						<tr>
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
						<c:forEach items="${contractPaymentList}" var="contractPayment">
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
									<input type="hidden" value="${contractPayment.contractorAppInvDoc}"
										name="downloadPath" />
									<button type="submit" class="fa fa-file-pdf-o red center"
										aria-hidden="true" style="font-size: 1.5em;"></button>
								</form>
							</c:if>
								</td>
								<td>${contractPayment.paymentDate}</td>
								<td>${contractPayment.paymentAmount}</td>
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
					</tbody>
				</table>
			</c:if>
		</div>
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
		$(document)
				.ready(
						function() {
							$('#contractPayment').DataTable();
							document
									.getElementById('contractPayment_length').style.display = 'none';
							//document.getElementById('demandNoteMstListTable_filter').style.display = 'none';
						});
	</script>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>