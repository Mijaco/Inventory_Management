<%@include file="../../../common/wsContractorHeader.jsp"%>
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
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Transformer List</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<strong>Congratulation!</strong> Information is updated successfully.
		</div>
		<div class="alert alert-danger hide">
			<strong>Sorry!</strong> Information update is failed!!!.
		</div>
		<!-- --------------------- -->
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty transformerList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty transformerList}">
				<table id="inventoryList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Transformer S.L</td>
							<td>Rating (KVA)</td>
							<td>Manufactured By</td>
							<td>Manufacturing Year</td>
							<td>Job No</td>
							<td>Type of Work</td>
							<td>Received From</td>
							<td>Received Date</td>
							<td>Action</td>

						</tr>
					</thead>

					<tbody>
						<c:forEach items="${transformerList}" var="transformer"
							varStatus="loop">
							<tr>
								<td>${transformer.transformerSerialNo}</td>
								<td>${transformer.kvaRating}</td>
								<td>${transformer.manufacturedName}</td>
								<td>${transformer.manufacturedYear}</td>
								<td>${transformer.jobNo}</td>
								<td>${transformer.typeOfWork}</td>
								<td>${transformer.rcvDeptName}</td>
								<td><fmt:formatDate value="${transformer.receivedDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>
									<button type="button" id="ctr${loop.index}"
										onclick="createJobReview(${loop.index})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-danger">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Job Review</span>
									</button> <input type="hidden" id="pk${loop.index}"
									value="${transformer.id}" />
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
		$('#inventoryList').DataTable({		

			"order" : [ [ 7, 'desc' ] ]
		});
		document.getElementById('inventoryList_length').style.display = 'none';
		// document.getElementById('avgPriceList_filter').style.display = 'none';
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	function createJobReview(n){
		var id=$('#pk'+n).val();
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/jobcard/review/getForm.do';		
		var cData = {id : id};
		
		postSubmit(path, cData, 'POST');
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
