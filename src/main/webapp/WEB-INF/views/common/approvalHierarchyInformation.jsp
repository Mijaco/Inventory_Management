<%@include file="../common/adminheader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<h1 class="center blue"
			style="margin-top: 25px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Approval Hierarchy Information</h1>
	</div>
	<!-- <div style="color: #858585; padding: 10px 0"></div>-->
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
			
			<c:if test="${!empty success}">
				<div class="alert alert-success">
					<strong>Success!</strong>&nbsp;Role is removed from approval hierarchy.
				</div>
			</c:if>
			
			<div class="alert alert-danger hide">
				<strong>Warning!</strong>&nbsp;Role is not removed from approval hierarchy.
			</div>
			
			<table class="table table-bordered table-striped" id="approvalHierarchyInformation">
				<thead>
					<tr>
						<th style="background: #579EC8; color: white; font-weight: normal;">Serial</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Operation Name</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Role Name</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">State Code</th>
						<th style="background: #579EC8; color: white; font-weight: normal;">Status</th>
						<th class="col-xs-3" style="background: #579EC8; color: white; font-weight: normal;">Action</th>
					</tr>
				</thead>
				<%
					int count = 1;
				%>
				<tbody>
					<c:forEach items="${userrole}" var="user">
						<tr>
							<td>
								<%
									out.print(count);
								%>
							</td>
							<td>${user.operationName}</td>
							<td>${user.roleName}</td>
							<td>${user.stateCode}</td>
							<td>${user.buttonName}</td>
							<td>
								<div class='action-buttons'>
									<a class="btn btn-xs btn-primary" style="border-radius: 6px;"
										href="${pageContext.request.contextPath}/settings/update/approvalHierarchyInformation.do?id=${user.id}">
										<i class="ace-icon fa fa-fw fa-pencil bigger-130"></i>&nbsp;Edit</a>
									<a class="btn btn-xs btn-success" style="border-radius: 6px;"
										href="${pageContext.request.contextPath}/settings/show/approvalHierarchyInformation.do?id=${user.id}">
										<i class="fa fa-fw fa-eye"></i>&nbsp;View</a>
									<a class="btn btn-xs btn-danger" style="border-radius: 6px;"
										href="javascript:void(0)" onclick="deleteThis(${user.id}, '${user.operationName}')">
										<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
								</div>
							</td>
						</tr>
						<%
							count++;
						%>
					</c:forEach>
				</tbody>
			</table>
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


//Added by: Shimul
function deleteThis( id, operationName ) {
	if( confirm( "Do you want to delete this role from approval hierarchy?" ) == true ) {
		var contextPath = $('#contextPath').val();
		var path = contextPath + "/settings/deleteRoleFormHierarchy.do";
		
		var params = {
				'id' : id,
				'operationName' : operationName
		}
		
		postSubmit(path, params, "POST");
	}
}

$(document)
	.ready(
		function() {
			$('#approvalHierarchyInformation').DataTable();
			document.getElementById('approvalHierarchyInformation_length').style.display = 'none';
			//document.getElementById('approvalHierarchyInformation_filter').style.display = 'none';
		});
		
		$('.alert.alert-success').fadeTo(4000, 500).slideUp(500, function() { } );
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
