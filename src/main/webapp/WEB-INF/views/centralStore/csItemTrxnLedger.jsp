<%@include file="../common/csHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/ledger/balance.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Current Balance
			</a>
			<h2 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">Current Stock
				Balance</h2>

			<h4 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">${deptName}</h4>

			<%-- <h5 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty csItemTransactionDtlList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h4 class="Green">
					<i>Item Information</i>
				</h4>

				<table class="table table-striped table-hover table-bordered">
					<tr>
						<th class="success center">Project Name</th>
						<td class="info"><c:out
								value="${csItemTransactionMst.khathName}" /></td>
						<th class="success center">Ledger Name</th>
						<td class="info"><c:out
								value="${csItemTransactionMst.ledgerName}" /></td>
						<th class="success center">Item Name</th>
						<td class="info"><c:out
								value="${csItemTransactionMst.itemName}" /></td>
					</tr>
					<tr>
						<th class="success center">Item Code</th>
						<td class="info"><c:out
								value="${csItemTransactionMst.itemCode}" /></td>
						<th class="success center">Balance</th>
						<td class="info">
							<%-- <c:out value="${csItemTransactionMst.quantity}" /> --%> <fmt:formatNumber
								type="number" minFractionDigits="3" groupingUsed="false"
								value="${csItemTransactionMst.quantity}" /> (${csItemTransactionMst.uom})
						</td>
						<th class="success center">Re-Order Level</th>
						<td class="info"><c:out
								value="${csItemTransactionMst.safetyMargin}" /></td>
					</tr>
				</table>
			</div>

			<c:if test="${!empty csItemTransactionDtlList}">
				<table id="itemLedgerListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">ID</td>
							<td style="">Transaction Id</td>
							<td style="">Transaction Type</td>
							<td style="">Transaction Date</td>
							<td style="">Quantity</td>
							<td style="">Balance</td>
							<td style="">Transaction Mode</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csItemTransactionDtlList}"
							var="csItemTransactionDtl" varStatus="loop">

							<tr>
								<td>${csItemTransactionDtl.id}</td>
								<td><c:out value="${csItemTransactionDtl.transactionId}" /></td>

								<td><c:out value="${csItemTransactionDtl.transactionType}" /></td>

								<td><fmt:formatDate
										value="${csItemTransactionDtl.transactionDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td>
									<%-- <c:out value="${csItemTransactionDtl.quantity}" /> --%> <fmt:formatNumber
										type="number" minFractionDigits="3" groupingUsed="false"
										value="${csItemTransactionDtl.quantity}" />
										&nbsp; ${csItemTransactionMst.uom}
								</td>

								<td>
									<%-- <c:out value="${csItemTransactionDtl.balance}" /> --%> <fmt:formatNumber
										type="number" minFractionDigits="3" groupingUsed="false"
										value="${csItemTransactionDtl.balance}" />
										&nbsp; ${csItemTransactionMst.uom}
								</td>

								<td id="tnxnMode${loop.index}"><c:choose>
										<c:when test="${csItemTransactionDtl.tnxnMode}">
       										Credit
    									</c:when>

										<c:otherwise>
        									Debit
    									</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
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
						$('#itemLedgerListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ],
							"order" : [ [ 0, "asc" ] ]
						});
						document.getElementById('itemLedgerListTable_length').style.display = 'none';
						document.getElementById('itemLedgerListTable_filter').style.display = 'none';
					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
