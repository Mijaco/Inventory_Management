<%@include file="../../common/csHeader.jsp"%>

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>

<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			List of Auction Materials Delivery</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="alert alert-success hide" id='updateAlert'>
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Update Failed!</strong> Quantity can not greater than Ledger.
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty auctionDeliveryMstList}">
				<div class="col-sm-12 center">
					<p class="green ubuntu-font">
						<i>Congratulation!! You have no pending task... </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty auctionDeliveryMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Delivery No.</td>
							<td>Work Order</td>

							<td>Delivery Date</td>
							<td>Receiver Name</td>
							<td>Receiver Contact No</td>

							<!-- <td>Carried By</td> -->
							
							<td>Show</td>
							<td>Report</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${auctionDeliveryMstList}"
							var="auctionDeliveryMst" varStatus="loop">
							<tr>
								<td>${auctionDeliveryMst.id}</td>
								<td>${auctionDeliveryMst.deliveryTrxnNo}</td>
								<td>${auctionDeliveryMst.auctionWOEntryMst.workOrderNo}</td>

								<td><fmt:formatDate
										value="${auctionDeliveryMst.deliveryDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>${auctionDeliveryMst.receiverName}</td>
								<td>${auctionDeliveryMst.receiverContactNo}</td>

								<%-- <td>${auctionDeliveryMst.carriedBy}</td> --%>

								<td><a href="javascript:void(0)"
									class="btn btn-info btn-xs" style="border-radius: 6px;"
									onclick="postSubmit('${pageContext.request.contextPath}/ac/condemnMaterialShow.do',{'id':'${auctionDeliveryMst.id}'},'POST')">
										<i class="fa fa-fw fa-eye"></i>&nbsp;Show
								</a></td>
								<td><c:if test="${auctionDeliveryMst.finalSubmit=='1'}">
										<div class="col-md-6">
											<a target="_blank"
												href="${pageContext.request.contextPath}/auction/storeTicketReport.do?id=${auctionDeliveryMst.id}"
												class="btn btn-success btn-xs" style="border-radius: 6px;">
												<i class="fa fa-fw fa-file-pdf-o"></i>&nbsp;Store Ticket
											</a>
										</div>
									</c:if> <c:if test="${auctionDeliveryMst.finalSubmit=='1'}">
										<div class="col-md-5">
											<a target="_blank"
												href="${pageContext.request.contextPath}/auction/gatePassReport.do?id=${auctionDeliveryMst.id}"
												class="btn btn-success btn-xs" style="border-radius: 6px;">
												<i class="fa fa-fw fa-file-pdf-o"></i>&nbsp;Gate Pass
											</a>
										</div>
									</c:if></td>

								<td>
									<div class="action-buttons center">
										<c:if test="${auctionDeliveryMst.finalSubmit=='0'}">
											<%--
											<a href="javascript:void(0)" class="btn btn-primary btn-xs"
												style="border-radius: 6px;"> <i class="fa fa-fw fa-edit"></i>&nbsp;Edit
											</a>
											 <a href="javascript:void(0)" class="btn btn-success btn-xs"
												style="border-radius: 6px;"
												onclick="postSubmit('${pageContext.request.contextPath}/ac/condemnMaterialFinalSubmit.do',{'id':'${auctionDeliveryMst.id}'},'POST')">
												<i class="fa fa-fw fa-paper-plane"></i>&nbsp;Final Submit
											</a> --%>
											<a href="javascript:void(0)" class="btn btn-danger btn-xs"
												style="border-radius: 6px;"
												onclick="deleteThis(${auctionDeliveryMst.id})"> <i
												class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
											</a>
										</c:if>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->
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
		$('#dataList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false,
				"searchable" : false
			} ],
			"order" : [ [ 0, 'desc' ] ]
		});
		document.getElementById('dataList_length').style.display = 'none';
		// document.getElementById('dataList_filter').style.display = 'none';
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	function deleteThis( id ) {
		
		if( confirm( "Do you want to delete this Delevery Details?" ) == true ) {
			var contextPath = $('#contextPath').val();
			var path = contextPath + "/ac/deleteCondemnMaterials.do";
			var params = {
				'id' : id
			}
			postSubmit(path, params, "POST");
		}
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
