<%@include file="../common/csHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
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
			<c:if test="${empty csItemTransactionMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty csItemTransactionMstList}">
				<div class="col-sm-10 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/cs/ledger/balanceSearch.do">

						<div class="form-group col-sm-3">
							<select class="form-control category" name="khathId" id="khathId"
								style="border: 0; border-bottom: 2px ridge;">
								
								<c:if test="${empty khathId}">
									<c:if test="${!empty descoKhathList}">
										<c:forEach items="${descoKhathList}" var="descoKhath">
											<option value="${descoKhath.id}">
												<c:out value="${descoKhath.khathName}" /></option>
										</c:forEach>
									</c:if>
								</c:if>
								<c:if test="${!empty khathId}">
									<c:if test="${!empty descoKhathList}">
										<c:forEach items="${descoKhathList}" var="descoKhath">
											<c:choose>
												<c:when test="${descoKhath.id == khathId}">
													<option value="${descoKhath.id}" selected>${descoKhath.khathName}</option>
												</c:when>
												
												<c:otherwise>
													<option value="${descoKhath.id}">${descoKhath.khathName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</c:if>
							</select>
						</div>

						<div class="form-group col-sm-3">
							<select class="form-control ledgerName" name="ledgerName"
								id="ledgerName0" style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${empty ledgerName}">
									<c:if test="${!empty ledgerBooks}">
										<c:forEach items="${ledgerBooks}" var="ledgerBook">
											<option value="${ledgerBook}">${ledgerBook}</option>
										</c:forEach>
									</c:if>
								</c:if>
								<c:if test="${!empty ledgerName}">
									<c:if test="${!empty ledgerBooks}">
										<c:forEach items="${ledgerBooks}" var="ledgerBook">
											<c:choose>
												<c:when test="${ledgerBook == ledgerName}">
													<option value="${ledgerBook}" selected>${ledgerBook}</option>
												</c:when>
												
												<c:otherwise>
													<option value="${ledgerBook}">${ledgerBook}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</c:if>
							</select>
						</div>

						<div class="form-group col-sm-3">
							<c:if test="${empty itemCode}">
								<input type="text" class="form-control" id="search" placeholder="Search by Item Code"
									style="border: 0; border-bottom: 2px ridge;" name="itemCode" />
							</c:if>
							
							<c:if test="${!empty itemCode}">
								<input type="text" class="form-control" id="search"
									style="border: 0; border-bottom: 2px ridge;" name="itemCode" value="${itemCode}">
							</c:if>
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>

				<table id="itemLedgerListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Item Name</td>
							<td style="">Unit</td>
							<td style="">Project Name</td>
							<td style="">Ledger Name</td>
							<td style="">Quantity</td>
							<td style="">Re-Order Level</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csItemTransactionMstList}"
							var="csItemTransactionMst">

							<tr>
								<td><a
									href="${pageContext.request.contextPath}/cs/ledger/itmTnxLedger.do?id=${csItemTransactionMst.id}"
									style="text-decoration: none;"><c:out
											value="${csItemTransactionMst.itemCode}" /></a></td>
								<td><c:out value="${csItemTransactionMst.itemMaster.itemName}" /></td>
								<td><c:out value="${csItemTransactionMst.itemMaster.unitCode}" /></td>
								<td><c:out value="${csItemTransactionMst.khathName}" /></td>

								<td><c:out value="${csItemTransactionMst.ledgerName}" /></td>

								<td>
									<%-- <c:out value="${csItemTransactionMst.quantity}" /> --%> <fmt:formatNumber
										type="number" minFractionDigits="3" groupingUsed="false"
										value="${csItemTransactionMst.quantity}" />
								</td>

								<td><c:out value="${csItemTransactionMst.safetyMargin}" /></td>
								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cs/ledger/itmTnxLedger.do?id=${csItemTransactionMst.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
									</div>
								</td>
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

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
		//$('.container-fluid.icon-box').removeClass('hide');
	});
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#itemLedgerListTable').DataTable();
						document.getElementById('itemLedgerListTable_length').style.display = 'none';
						document.getElementById('itemLedgerListTable_filter').style.display = 'none';
					});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
