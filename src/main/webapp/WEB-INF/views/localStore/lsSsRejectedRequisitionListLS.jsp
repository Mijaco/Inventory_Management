<%@include file="../common/lsHeader.jsp"%>

<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

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
			Rejected Requisition List</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty ssRequisitionMstList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty ssRequisitionMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Requisition No</td>
							<td>Requisition Date</td>

							<td>Requisition From</td>
							<td>Requisition To</td>
							<td>Status</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${ssRequisitionMstList}" var="mst"
							varStatus="loop">
							<tr>
								<td>${mst.id}</td>

								<td>${mst.requisitionNo}</td>
								<td><fmt:formatDate value="${mst.createdDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td>${mst.deptName}</td>
								<td>${mst.requisitionTo=='cs'?'Central Store':'Sub Store'}</td>
								
								<td>Rejected</td>

								<td>
									<button type="button" id="btn${loop.index}"
										onclick="viewRequisition(${mst.id})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">View</span>
									</button>
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
	
	
	function viewRequisition(mstId){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/ls/ss/rejected/showRequisition.do';		
		var cData = {id : mstId};
		
		postSubmit(path, cData, 'GET');
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
