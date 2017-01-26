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
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate
				Pass List</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>

	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty gatePassMstList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>Congratulation!!! You have no pending task</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty gatePassMstList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/cs/gatePassSearch.do">
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Gate Pass No."
								style="border: 0; border-bottom: 2px ridge;" name="gatePassNo" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="gpMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">ID</th>
							<th style="">Gate Pass No</th>
							<th style="">Requisition No</th>
							<th style="">Ticket No</th>
							<th style="">Issued To</th>
							<th style="">Gate Pass Date</th>
							<!-- <th style="">Action</th> -->
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${gatePassMstList}" var="gatePassMst">
							<tr>
								<td>${gatePassMst.id}</td>
								<td><a
									href="${pageContext.request.contextPath}/cs/openGatePass.do?requisitonNo=${gatePassMst.requisitonNo}&flag=${gatePassMst.flag}&gatePassNo=${gatePassMst.gatePassNo}&ticketNo=${gatePassMst.ticketNo}"
									style="text-decoration: none;"><c:out
											value="${gatePassMst.gatePassNo}" /></a></td>
								<td><c:out value="${gatePassMst.requisitonNo}" /></td>
								<td><c:out value="${gatePassMst.ticketNo}" /></td>
								<td><c:out value="${gatePassMst.issuedTo}" /></td>
								<td><fmt:formatDate value="${gatePassMst.gatePassDate}"
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


<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#gpMstListTable').DataTable({
							"order" : [ [ 0, "desc" ] ],
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ]
						});
						document.getElementById('gpMstListTable_length').style.display = 'none';
						document.getElementById('gpMstListTable_filter').style.display = 'none';

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
