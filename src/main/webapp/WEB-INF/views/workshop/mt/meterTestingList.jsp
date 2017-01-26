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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/mt/meterTestingForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Meter
				Testing Register Form
			</a>

			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Meter
				Testing Register</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty meterTestingRegisterList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty meterTestingRegisterList}">

				<table id="meterTestingRegisterListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">Reference No.</td>
							<td>ID</td>
							<td style="">S&amp;D/ Division</td>
							<td style="">Consumer Name</td>
							<td style="">Consumer Address</td>
							<td style="">Meter No.</td>
							<td style="">Meter Type</td>
							<td style="">Meter Source</td>
							<td style="">Sanctioned Load</td>
							<td style="">Received Date</td>
							<td style="">Status</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${meterTestingRegisterList}"
							var="meterTestingRegister">
							<tr>

								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/mt/registerShow.do',{id:'${meterTestingRegister.id}'},'POST')">
										<c:out value="${meterTestingRegister.refNo}" />
								</a></td>
								<td>${meterTestingRegister.id}</td>
								<td><c:out value="${meterTestingRegister.senderDeptName}" /></td>
								<td><c:out value="${meterTestingRegister.consumerName}" /></td>
								<td><c:out value="${meterTestingRegister.consumerAddress}" /></td>
								<td><c:out value="${meterTestingRegister.meterNo}" /></td>
								<td><c:out value="${meterTestingRegister.meterType}" /></td>
								<td><c:out value="${meterTestingRegister.meterSource}" /></td>
								<td><c:out value="${meterTestingRegister.sanctionedLoad}" /></td>
								<td><c:out value="${meterTestingRegister.receivedDate}" /></td>
								<td><c:out value="${meterTestingRegister.status}" /></td>
								<td>
									<div class="action-buttons">
										<a href="#" class="red"
											onclick="postSubmit('${pageContext.request.contextPath}/mt/registerShow.do',{id:'${meterTestingRegister.id}'},'POST')">
											<i class="glyphicon glyphicon-eye-open"></i>
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
						$('#meterTestingRegisterListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 1 ],
								"visible" : false
							} ],
							"order" : [ [ 1, 'desc' ] ]
						});
						document
								.getElementById('meterTestingRegisterListTable_length').style.display = 'none';
						//document.getElementById('meterTestingRegisterListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
