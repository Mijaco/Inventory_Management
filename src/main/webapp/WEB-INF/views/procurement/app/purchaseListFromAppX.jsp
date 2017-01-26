<%@include file="../../common/procurementHeader.jsp"%>
<!-- ------End of Header------ -->

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
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Purchase From APP List</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty appProcMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty appProcMstList}">
				<table id="appProcMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Return Slip No</td>
							<td style="">Return Slip Date</td>
							<td style="">Return By</td>
							<td style="">Return To</td>
							<td style="">Work Order No</td>
							<td style="">Work Order Date</td>
							<td style="">Zone</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${appProcMstList}" var="appProcMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/cn/pd/returnSlipShow.do?id=${returnSlipMst.id}&returnTo=${returnSlipMst.returnTo}"
									style="text-decoration: none;">${returnSlipMst.returnSlipNo}</a></td>

								<td><fmt:formatDate value="${returnSlipMst.returnSlipDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><c:out value="${returnSlipMst.receiveFrom}" /></td>
								<td>XEN, CENTRAL STORE</td>

								<td><c:out value="${returnSlipMst.workOrderNo}" /></td>
								<td><fmt:formatDate value="${returnSlipMst.workOrderDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><c:out value="${returnSlipMst.zone}" /></td>

								<td>
									<div class="action-buttons">										
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cn/pd/returnSlipShow.do?id=${returnSlipMst.id}&returnTo=${returnSlipMst.returnTo}">
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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#appProcMstListTable').DataTable({
							"order" : [ [ 1, "desc" ] ]
						});
						document.getElementById('appProcMstListTable_length').style.display = 'none';
						//document.getElementById('appProcMstListTable_filter').style.display = 'none';

					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------ Footer ------ -->
<%@include file="../../common/ibcsFooter.jsp"%>