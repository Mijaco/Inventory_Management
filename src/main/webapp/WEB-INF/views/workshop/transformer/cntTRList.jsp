<%@include file="../../common/wsContractorHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/ws/xf/testReport.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Transformer Test Report
			</a>
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer
			Test Report List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty tsRegisterList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>Congratulation!!! You have no pending task on
							Transformer Test Report</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty tsRegisterList}">
				<table id="ttrListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">Test Report No</th>
							<th style="">Transformer S.L</th>
							<th style="">Manufacture Name</th>
							<th style="">Phase</th>
							<th style="">Test Date</th>
							<th style="">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${tsRegisterList}" var="transformer"
							varStatus="loop">
							<tr>
								<td><a href="javascript:void(0)"
									onclick="openTR(${loop.index})" style="text-decoration: none;">${transformer.id}</a></td>
								<td>${transformer.transformerSerialNo}</td>
								<td>${transformer.manufacturedName}</td>
								<td>${transformer.transformerType}</td>
								<td><fmt:formatDate value="${transformer.testDate}"
										pattern="dd-MM-yyyy" />
								<td>
									<button type="button" id="openTR${loop.index}"
										onclick="openTR(${loop.index})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="glyphicon glyphicon-eye-open"></i> <span
											class="bigger-30">View</span>
									</button> <input type="hidden" id="pk${loop.index}"
									value="${transformer.id}" />

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

<script>
	$(document).ready(function() {
		$('#ttrListTable').DataTable({
			"order" : [ [ 4, 'desc' ] ]
		});
		document.getElementById('ttrListTable_length').style.display = 'none';
		//document.getElementById('ttrListTable_filter').style.display = 'none';

	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	function openTR(n){
		var id=$('#pk'+n).val();
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/ws/xf/openPendingTTR.do';		
		var cData = {id : id};
		
		postSubmit(path, cData, 'POST');
	}
</script>

<%@include file="../../common/ibcsFooter.jsp"%>