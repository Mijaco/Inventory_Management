<%@include file="../../common/wsHeader.jsp"%>
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
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/prev/gatePassForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Gate Pass
			</a>

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate Pass List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty transformerRegisterList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task on Gate Pass. </i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty transformerRegisterList}">


				<table id="returnSlipListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Transformer Serial No</td>
							<td style="">Note Number</td>
							<td style="">Received From</td>
							<td style="">Received Date</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${transformerRegisterList}" var="transformerRegister" varStatus="loop">
							<tr>
								<td>
								<form id="myForm${loop.index}" action="${pageContext.request.contextPath}/prev/gatePassShowPost.do" method="POST">
								<input type="hidden" name="id" value="${transformerRegister.id}" />
								<input type="hidden" name="reqNo" value="${transformerRegister.reqNo}" />								
								<a href="#" onclick="document.getElementById('myForm${loop.index}').submit();"> <c:out value="${transformerRegister.transformerSerialNo}" /></a>
									</form>		
								</td>		
								<td>${transformerRegister.reqNo}</td>						
								<td>${transformerRegister.rcvDeptName}</td>
								<td><fmt:formatDate value="${transformerRegister.receivedDate}"
										pattern="dd-MM-yyyy" /></td>

								<td>
									<a href="#" onclick="document.getElementById('myForm${loop.index}').submit();"><i class='glyphicon glyphicon-eye-open'></i> </a>
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
						$('#returnSlipListTable').DataTable({
							"order" : [ [ 2, "desc" ] ]
						});
						document
								.getElementById('returnSlipListTable_length').style.display = 'none';
						//document.getElementById('returnSlipListTable_filter').style.display = 'none';

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
<%@include file="../../common/ibcsFooter.jsp"%>
