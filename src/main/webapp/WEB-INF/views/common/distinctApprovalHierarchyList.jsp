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

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/settings/add/newApprovalHierarchyForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span>
				Add Approval Hierarchy
			</a>
<!-- 			<button accesskey="D" class="btn btn-info btn-sm" type="button"> -->
<!-- 				Discard</button> -->
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Approval Hierarchy List</h1>

		</div>
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

	</div>
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Approval Hierarchy</a> / List
		</h4> -->
		
	</div>
	
	<!-- <div style="color: #858585; padding: 10px 0"></div>-->
	
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty distinctApprovalHierarchyList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty distinctApprovalHierarchyList}">
				<table id="distinctApprovalHierarchyList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td class="col-md-2"  align="center">Serial No</td>
							<td class="col-md-8">Operation Name</td>
							<td class="col-md-2 center">Action</td>
						</tr>
					</thead>
					<% int count = 1; %>
					<tbody>
						<c:forEach items="${distinctApprovalHierarchyList}"
							var="approvalHierarchy">
							<tr>
								<td align="center"><% out.print(count); %></td>
								<td>
								<a href="javascript:void(0)" onclick="loadDistinctApprovalHierarchy('${approvalHierarchy}')">
								<c:out value="${approvalHierarchy}" /></a></td>
								<td align="center">
									<div class="action-buttons">
										<a class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="javascript:void(0)" onclick="loadDistinctApprovalHierarchy('${approvalHierarchy}')">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
									</div>
								</td>
							</tr>
							<% count++; %>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>

<script>
	function loadDistinctApprovalHierarchy( approvalHierarchy ) {
		
		var contextPath = $('#contextPath').val();
		var path = contextPath + "/settings/list/distinctApprovalHierarchyList.do";
		var params = {
			'operationName' : approvalHierarchy
		}
		postSubmit(path, params, "POST");
	}
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
	
<script>
	$(document)
			.ready(
					function() {
						$('#distinctApprovalHierarchyList').DataTable();
						document.getElementById('distinctApprovalHierarchyList_length').style.display = 'none';
						//document.getElementById('distinctApprovalHierarchyList_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
