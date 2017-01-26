<%@include file="../common/adminheader.jsp"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box"
	style="background-color: #858585;">

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">

			<a
				href="${pageContext.request.contextPath}/settings/add/newOperationForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Add
				New Operation
			</a>

		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			List of Operation</h1>

	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty operationList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i> You have no Operation </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty operationList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST" action="#"> -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" -->
<!-- 								id="searchByOperationName" -->
<!-- 								placeholder="Search by Operation Name" -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" -->
<!-- 								name="searchByOperationName" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->
				<table id="operationListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="text-align: center;">Operation Name</th>
							<th style="text-align: center;" class="hide">Operation Created By</th>
							<th style="text-align: center;">Operation status</th>
							<th style="text-align: center;">Remarks</th>
							<th style="text-align: center;" class="col-xs-2">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${operationList}" var="operationList">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/settings/show/operation.do?id=${operationList.id}"
									style="text-decoration: none;"> <c:out
											value="${operationList.operationName}" /></a></td>
								<td class="hide"><c:out value="${operationList.createdBy}" /></td>
								<td><c:out value="${operationList.active == true ? 'Active' : 'Inactive'}" /></td>
								<td><c:out value="${operationList.remarks}" /></td>
								<td style="text-align: center;">
									<div class="action-buttons">
										<a class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/update/operation.do?
											id=${operationList.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/show/operation.do?id=${operationList.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
										<%-- <a 	class="red"
											href="${pageContext.request.contextPath}
											/settings/delete/operation.do?
											id=${operationList.id}">
											<i class="ace-icon fa fa-trash-o bigger-130"></i>
										</a> --%>

									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
								$('#operationListTable').DataTable();
								document
										.getElementById('operationListTable_length').style.display = 'none';
								//document.getElementById('operationListTable_filter').style.display = 'none';
							});
		</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>