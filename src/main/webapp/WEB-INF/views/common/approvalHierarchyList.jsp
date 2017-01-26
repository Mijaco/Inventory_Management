<%@include file="../common/settingsHeader.jsp"%>
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
			<a href="#" style="text-decoration: none;">Approval Hierarchy</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/settings/add/newApprovalHierarchyForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Approval Hierarchy
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Approval Hierarchy List</h1>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty approvalHierarchyList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty approvalHierarchyList}">
				<%-- <div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/common/searchByRoleApprovalHierarchy.do">
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by ROLE"
								style="border: 0; border-bottom: 2px ridge;" name="roleName" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div> --%>
				<table id="approvalHierarchyListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">State Name</td>
							<td style="">Role</td>
							<td style="">State Code</td>
							<td style="">Operation Name</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${approvalHierarchyList}"
							var="approvalHierarchy">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/settings/show/approvalHierarchy.do?id=${approvalHierarchy.id}"
									style="text-decoration: none;"><c:out
											value="${approvalHierarchy.stateName}" /></a></td>
								<td><c:out value="${approvalHierarchy.roleName}" /></td>
								<td><c:out value="${approvalHierarchy.stateCode}" /></td>
								<td><c:out value="${approvalHierarchy.operationName}" /></td>
								<td><c:out value="${approvalHierarchy.remarks}" /></td>

								<td style="text-align: center;">
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/settings/update/approvalHierarchy.do?id=${approvalHierarchy.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/settings/show/approvalHierarchy.do?id=${approvalHierarchy.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
										<%-- <a class=""
											href="${pageContext.request.contextPath}/common/deleteApprovalHierarchy.do?id=${approvalHierarchy.id}">
											<i class="ace-icon fa fa-trash-o bigger-130"></i>
										</a> --%>
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
						$('#approvalHierarchyListTable').DataTable();
						document.getElementById('approvalHierarchyListTable_length').style.display = 'none';
						//document.getElementById('approvalHierarchyListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>


<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
