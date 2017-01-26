<%@include file="../common/adminheader.jsp"%>
<!-- Header -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Admin Panel Role List</h1>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<c:if test="${!empty roleList}">
			<div class="col-sm-12 table-responsive">

				<c:if test="${!empty deleteflag}">
					<div class="alert alert-danger" id='deletealert'>
						<strong>Success!</strong> Role Information is deleted.
					</div>
				</c:if>
				
				<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				
				<table id="roleListTable"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>Role name</th>
							<th>User(s)</th>
							<th>Permission(s)</th>
							<th class="col-xs-1">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${roleList}" var="roles">
							<tr>
								<td><c:out value="${roles.role}" /></td>
								<td><a href="javascript:void(0)" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/userList.do',{role_id:'${roles.role_id}'},'POST')">
										User(s) </a></td>

								<td><a href="javascript:void(0)" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permissionList.do',{role_id:'${roles.role_id}'},'POST')">
										Permission(s) </a></td>

								<td><div class="hidden-sm hidden-xs action-buttons">
										
										<c:if test="${roles.role != 'ROLE_ADMIN'}">
											<a href="javascript:void(0)" class="btn btn-xs btn-danger" style="border-radius: 6px;"
												onclick="showConfirmation(${roles.id})">
												<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
											</a>
										</c:if>
										
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
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
		$('#roleListTable').DataTable();
		//$('#dynamic-table').children().first().style.display = 'none';
		//$("#roleListTable_wrapper > :first-child").remove();
		//$("#roleListTable_wrapper").style.display = 'none'; 
		document.getElementById('roleListTable_length').style.display = 'none';
		//document.getElementById('roleListTable_filter').style.display = 'none';
	});
	
	//Show confirmation before delete
	function showConfirmation(id) {
		var response = confirm("Do you want to delete this role?");
		if( response == true ) {
			var path = $('#contextPath').val() + "/adminpanel/role/delete.do";
			var param = {
				id : id
			}
			postSubmit(path, param, 'POST');

		} else {
			//console.log( "null" );
		}
	}
	
	$( document ).ready( function() {
		$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-danger").alert('close');
		});
	});
	
</script>

<!-- Footer -->
<%@include file="../common/ibcsFooter.jsp"%>