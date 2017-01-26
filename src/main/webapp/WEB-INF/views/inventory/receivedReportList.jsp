<%@include file="../inventory/inventoryheader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 46%;
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
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Received Report List</h1>
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 20px; margin-right: 20px;">
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty receivedReportList}">
				<div class="col-sm-12 center">
					<p class="blue">
						<i>Congratulation!!! You have no pending Task. </i>
					</p>
				</div>
			</c:if>

			<c:if test="${!empty receivedReportList}">
				<table id="receivedReportList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Received Report No</td>
							<td>Receive Date</td>
							<td>Chalan No.</td>
							<td>Invoice Date</td>
							<td>Contract No</td>
							<td>Contract Date</td>
							<td>Supplier Name</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${receivedReportList}" var="receivedReport">
							<tr>
								<td>${receivedReport.id}</td>
								<td>
									<a onclick="showRR('${receivedReport.rrNo}')" 
									href="JavaScript:void()" style="text-decoration: none">
									${receivedReport.rrNo}</a></td>
								<td>${receivedReport.receivedDate}</td>
								<td>${receivedReport.chalanNo}</td>
								<td>${receivedReport.invoiceDate}</td>
								<td>${receivedReport.contractNo}</td>
								<td>${receivedReport.contractDate}</td>
								<td>${receivedReport.supplierName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>


<script type="text/javascript">
function showRR(id){
	var contextPath=$("#contextPath").val();
	var path=contextPath +"/inventory/receivedReportShow.do";
	postSubmit(path, {id:id}, 'POST');
}
	$(document)
			.ready(
					function() {
						$('#receivedReportList').DataTable({
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false
							} ],
							"order" : [ [ 0, 'desc' ] ]
						});

						//
						document.getElementById('receivedReportList_length').style.display = 'none';
						// document.getElementById('receivedReportList_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>
<!-- -------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
