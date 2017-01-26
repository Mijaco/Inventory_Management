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
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
				Card List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty jobCardMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty jobCardMstList}">
				<table id="jobCardMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Job Card No</td>
							<td style="">Contract No.</td>
							<td style="">Work Type</td>
							<td style="">Transformer Serial No</td>
							<td style="">Manufactured Name</td>

							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${jobCardMstList}" var="jobCardMst">
							<tr>
								<td><a href="#"
									onclick="postSubmit('${pageContext.request.contextPath}/jobcard/approvedJobCardShow.do',{id:'${jobCardMst.id}'},'POST')"
									style="text-decoration: none;"> <c:out
											value="${jobCardMst.jobCardNo}" /></a></td>
								<td><c:out value="${jobCardMst.contractNo}" /></td>
								<td><c:out value="${jobCardMst.typeOfWork}" /></td>
								<td><c:out value="${jobCardMst.transformerSerialNo}" /></td>

								<td><c:out value="${jobCardMst.manufacturedName}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;" href="#"
											onclick="postSubmit('${pageContext.request.contextPath}/jobcard/approvedJobCardShow.do',{id:'${jobCardMst.id}'},'POST')">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
										
										<a class="btn btn-success btn-xs" style="border-radius: 6px;" href="#"
											onclick="postSubmit('${pageContext.request.contextPath}/jobcard/approvedJobCardReport.do',{id:'${jobCardMst.id}'},'POST')"
											target="_blank">
											<i class="fa fa-print"></i>&nbsp;Print
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
						$('#jobCardMstListTable').DataTable({
							"order" : [ [ 0, "desc" ] ]
						});
						document.getElementById('jobCardMstListTable_length').style.display = 'none';
						//document.getElementById('jobCardMstListTable_filter').style.display = 'none';

					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
