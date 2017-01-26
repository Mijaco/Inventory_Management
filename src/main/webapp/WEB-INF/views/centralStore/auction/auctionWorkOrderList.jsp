<%@include file="../../common/auctionHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
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
				style="margin-top: 10px; font-style: italic;">Work Order List
				for Auction</h2>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty workOrderMstList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty workOrderMstList}">
				<table id="workOrderListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">Work Order No</td>
							<td>ID</td>
							<td style="">Work Order Date</td>
							<td style="">Supplier Name</td>
							<td style="">Reference Document</td>
							<!--<td style="">woDate</td> -->
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workOrderMstList}" var="workOrderMst">
							<tr>

								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/ac/show.do',{id:'${workOrderMst.id}'},'POST')">
										<c:out value="${workOrderMst.workOrderNo}" />
								</a></td>
								<td>${workOrderMst.id}</td>
								<td><fmt:formatDate value="${workOrderMst.contractDate}"
										pattern="dd-MM-yyyy" /></td>

								<td>${workOrderMst.supplierName}</td>
								<td><c:if test="${!empty workOrderMst.referenceDocs}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/ac/download.do"
											method="POST">
											<input type="hidden" value="${workOrderMst.referenceDocs}"
												name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if></td>

								<td>
									<div class="action-buttons">
										<a href="javascript:void(0)" class="btn btn-primary btn-xs"
											style="border-radius: 6px;"
											onclick="postSubmit('${pageContext.request.contextPath}/ac/show.do',{id:'${workOrderMst.id}'},'POST')">
											<i class="fa fa-fw fa-eye"></i>&nbsp; Show
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
						$('#workOrderListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 1 ],
								"visible" : false
							} ],
							"order" : [ [ 1, 'desc' ] ]
						});
						document.getElementById('workOrderListTable_length').style.display = 'none';
						//document.getElementById('workOrderListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
