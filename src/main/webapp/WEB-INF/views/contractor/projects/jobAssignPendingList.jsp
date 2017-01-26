<%@include file="../../common/pdHeader.jsp"%>
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
		style="background-color: white; padding: 10px; padding-left: 10px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job Assign Pending
				 List</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty jobMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty jobMstList}">
				<table id="jobMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Job No</td>
							<td style="">Project/P&amp;D Ref. :</td>
							<td style="">P&amp;D No</td>
							<td style="">Construction Nature</td>
							<td style="">Contractor Name</td>
							<td style="">Work Order No</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${jobMstList}"
							var="jobMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/job/assignDetailsShow.do?id=${jobMst.id}"
									style="text-decoration: none;"><c:out
											value="${jobMst.jobNo}" /></a></td>
								<td>${jobMst.pdNo}</td>
								<td>${jobMst.pndNo}</td>
								<td>${jobMst.constructionNature}</td>
								<td>${jobMst.contractor.contractorName}</td>
								<td>${jobMst.contractor.contractNo}</td>
								<td>
									<div class="action-buttons">										
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/job/assignDetailsShow.do?id=${jobMst.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
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
						$('#jobMstListTable').DataTable(
								{
									"order" : [ [ 4, "desc" ] ]
								}	
						);
						document
								.getElementById('jobMstListTable_length').style.display = 'none'; 

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
	}

	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
