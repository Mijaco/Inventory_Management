<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

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
<%-- 			<a href="${pageContext.request.contextPath}/pd/createProject.do" --%>
<!-- 				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span -->
<!-- 				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> -->
<!-- 				Create New Project -->
<!-- 			</a> -->
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer Register (Repair)</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty transformerList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty transformerList}">
				<table id="transformerList" class="table table-bordered">
					<thead>
						<tr style="background: #579EC8; color: white; font-weight: normal;">
							<th>ID</th>
							<th>Transformer Serial No.</th>
							<th>Manufacturer Name & Year</th>
							<th>KVA Rating</th>
							<th>Received Date</th>
							<th>Central Store Requisition No</th>
							<th>Central Store Ticket No</th>
							<th>Test Date</th>
							<th>Job No</th>
							<th>Return Date</th>
							<th>Central Store Return Slip No</th>
							<th>Central Store Ticket</th>
							<th>Remarks</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${transformerList}"
							var="transformer">
							<tr>
								<td>${transformer.id}</td>
								<td>${transformer.transformerSerialNo}</td>
								<td>${transformer.manufacturedName} <c:if
									test="${!empty transformer.manufacturedYear}">
									, ${transformer.manufacturedYear}
								</c:if>
								</td>
								<td>${transformer.kvaRating}</td>
								<td>${transformer.receivedDate}</td>
								<td>${transformer.reqNo}</td>
								<td>${transformer.ticketNo}</td>
								<td>
									<fmt:formatDate
										value="${transformer.testDate}" pattern="dd-MM-yyyy" />
								</td>
								<td>${transformer.jobNo}</td>
								<td>${transformer.returnDate}</td>
								<td>${transformer.returnSlipNo}</td>
								<td>${transformer.ticketNo}</td>
								<td>${transformer.remarks}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>


<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script type="text/javascript">
	
	$(document).ready( function() {
		$('#transformerList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false
			} ],
			"order" : [ [ 0, 'desc' ] ]
		});
		document.getElementById('transformerList_length').style.display = 'none';
		//document.getElementById('transformerList_filter').style.display = 'none';
	});
</script>


<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
