<%@include file="../common/ssHeader.jsp"%>
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
			<%-- <a
				href="${pageContext.request.contextPath}/cs/itemRecieved/procItemRcvFrom.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Central Store Receive Form
			</a> --%>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Ticket List</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
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
				<div class="col-sm-6 pull-right">
					<form method="POST" action="${pageContext.request.contextPath}/ss/storeTicketSearch.do">

						<!-- /input-group -->
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Ticket No."
								style="border: 0; border-bottom: 2px ridge;" name="ticketNo" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="storeTicketListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">Ticket No</th>
							<th style="">Ticket Type</th>
							<th style="">R. No</th>
							<th style="">Ticket Date</th>
							<!-- <th style="">Action</th> -->
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csStoreTicketMstList}" var="csStoreTicketMst" varStatus="loop">
							<tr>
								<td>
								<form id="myForm${loop.index}" action="${pageContext.request.contextPath}/ss/openStoreTicket.do" method="POST">
								<input type="hidden"
								value="${returnSlipDtl.id}" readOnly="readOnly"/>
								
								<input type="hidden" name="flag" id="flag" value="${csStoreTicketMst.flag}" />
								<input type="hidden" name="operationId" id="operationId" value="${csStoreTicketMst.operationId}" />
								<input type="hidden" name="storeTicketType" id="storeTicketType" value="${csStoreTicketMst.storeTicketType}" />
								<input type="hidden" name="ticketNo" id="ticketNo" value="${csStoreTicketMst.ticketNo}" />
								<%-- <a href="${pageContext.request.contextPath}/ss/openStoreTicket.do?operationId=${csStoreTicketMst.operationId}&flag=${csStoreTicketMst.flag}&storeTicketType=${csStoreTicketMst.storeTicketType}&ticketNo=${csStoreTicketMst.ticketNo}" --%>

    <a href="#" onclick="document.getElementById('myForm${loop.index}').submit();">
    <c:out value="${csStoreTicketMst.ticketNo}" /></a>
</form></td>
								<td><c:out value="${csStoreTicketMst.storeTicketType}" /></td>
								<td><c:out value="${csStoreTicketMst.operationId}" /></td>
								<td><fmt:formatDate value="${csStoreTicketMst.ticketDate}"
										pattern="dd-MM-yyyy" /></td>
								<%-- <td>
									<div class="action-buttons">
										<a
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveEdit.do?id=${csProcItemRcvMst.rrNo}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> <a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveShow.do?id=${csProcItemRcvMst.rrNo}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
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
	
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#storeTicketListTable').DataTable({
							"order" : [ [ 2, "desc" ] ]
						});
						document
								.getElementById('storeTicketListTable_length').style.display = 'none';
						document
								.getElementById('storeTicketListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';

	}
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
