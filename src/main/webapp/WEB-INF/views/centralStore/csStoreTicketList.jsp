<%@include file="../common/csHeader.jsp"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Ticket</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Ticket List</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty csStoreTicketMstList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>Congratulation!!! You have no pending task</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csStoreTicketMstList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketSearch.do"> --%>

<!-- 						/input-group -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by Ticket No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="ticketNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->
				<table id="csStoreTicketMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">ID</th>
							<th style="">Ticket No</th>
							<th style="">Ticket Type</th>
							<th style="">Requisition/Return No</th>
							<th style="">Ticket Date</th>
							<!-- <th style="">Action</th> -->
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csStoreTicketMstList}" var="csStoreTicketMst">
							<tr>
								<td>${csStoreTicketMst.id}</td>
								<td><a
									onclick="openStoreTicket('${pageContext.request.contextPath}/cs/itemRecieved/openStoreTicket.do', 
											'${csStoreTicketMst.operationId}', ${csStoreTicketMst.flag}, 
											'${csStoreTicketMst.storeTicketType}', '${csStoreTicketMst.ticketNo}')"
									href="#" style="text-decoration: none;">${csStoreTicketMst.ticketNo}</a>

									<%-- {operationId: '${csStoreTicketMst.operationId}', flag: ${csStoreTicketMst.flag},storeTicketType: '${csStoreTicketMst.storeTicketType}', ticketNo: '${csStoreTicketMst.ticketNo}' }, 'POST')" --%>
								</td>
								<td><c:out value="${csStoreTicketMst.storeTicketType}" /></td>
								<td><c:out value="${csStoreTicketMst.operationId}" /></td>
								<td><fmt:formatDate value="${csStoreTicketMst.ticketDate}"
										pattern="dd-MM-yyyy" /></td>
								<%-- <td>
									<div class="action-buttons">
										<a
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveEdit.do?id=${csProcItemRcvMst.rrNo}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> <a class=""
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveShow.do?id=${csProcItemRcvMst.rrNo}">
											<i class="glyphicon glyphicon-eye-open"></i>
										</a>
									</div>
								</td> --%>
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
<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csStoreTicketList.js"></script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
