<!-- Header -->
<%@include file="../common/adminheader.jsp"%>
<!-- ----------------------------------- -->
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
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/adminPanel.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>&nbsp;Role
				List
			</a> <a href="javascript:void(0)" style="text-decoration: none;"
				class="btn btn-success btn-sm"
				onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/addAllObject.do',{role_id:'${role_id}'},'POST')">
				<span class="fa fa-fw fa-plus" aria-hidden="true"></span>&nbsp;Add
				All Object
			</a>
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${roleName} Permission(s)</h1>
	</div>
	
	<input type="hidden" id="" value="${pageContext.request.contextPath}">
	
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<div class="col-xs-12 table-responsive">
			
			<c:if test="${!empty deleteflag}">
				<div class="alert alert-danger" id='deletealert'>
					<strong>Success!</strong> Object Information is deleted.
				</div>
			</c:if>

			<c:if test="${!empty permissionList}">
				<table id="permissionListTable"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>Role Id</th>
							<th>Object</th>
							<th>P_Read</th>
							<th>P_Write</th>
							<th>P_Edit</th>
							<th>P_Delete</th>
							<th>Permit All</th>
							<th>Deny All</th>
							<th>Action</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${permissionList}" var="permissions">
							<tr>
								<td><c:out value="${roleName}" /></td>
								<td><c:out value="${permissions.objectName}" /></td>
								<c:choose>
									<c:when test="${permissions.p_read =='0'}">
										<td><a href="javascript:void(0)" class="red"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'1', p_write:'${permissions.p_read}', p_edit:'${permissions.p_edit}', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-times-circle  bigger-130"></i>
										</a></td>
									</c:when>
									<c:otherwise>
										<td><a href="javascript:void(0)" class="green"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'0', p_write:'${permissions.p_read}', p_edit:'${permissions.p_edit}', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-check-circle  bigger-130"></i>
										</a></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${permissions.p_write =='0'}">
										<td><a href="javascript:void(0)" class="red"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'1', p_edit:'${permissions.p_edit}', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-times-circle  bigger-130"></i>
										</a></td>
									</c:when>
									<c:otherwise>
										<td><a href="javascript:void(0)" class="green"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'0', p_edit:'${permissions.p_edit}', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-check-circle  bigger-130"></i>
										</a></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${permissions.p_edit =='0'}">
										<td><a href="javascript:void(0)" class="red"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'${permissions.p_write}', p_edit:'1', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-times-circle  bigger-130"></i>
										</a></td>
									</c:when>
									<c:otherwise>
										<td><a href="javascript:void(0)" class="green"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'${permissions.p_write}', p_edit:'0', p_delete:'${permissions.p_delete}', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-check-circle  bigger-130"></i>
										</a></td>
									</c:otherwise>
								</c:choose>


								<c:choose>
									<c:when test="${permissions.p_delete =='0'}">
										<td><a href="javascript:void(0)" class="red"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'${permissions.p_write}', p_edit:'${permissions.p_edit}', p_delete:'1', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-times-circle  bigger-130"></i>
										</a></td>
									</c:when>
									<c:otherwise>
										<td><a href="javascript:void(0)" class="green"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'${permissions.p_read}', p_write:'${permissions.p_write}', p_edit:'${permissions.p_edit}', p_delete:'0', role_id:'${permissions.role_id}'},
											'POST')">
												<i class="fa fa-check-circle  bigger-130"></i>
										</a></td>
									</c:otherwise>
								</c:choose>
								<td><a href="javascript:void(0)" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'1', p_write:'1', p_edit:'1', p_delete:'1', role_id:'${permissions.role_id}'},
											'POST')">
										Permit All </a></td>
								<td><a href="javascript:void(0)" class="red"
									onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/permission/update.do',
											{p_id:'${permissions.p_id}', object_id:'${permissions.object_id}', p_read:'0', p_write:'0', p_edit:'0', p_delete:'0', role_id:'${permissions.role_id}'},
											'POST')">
										Deny All </a></td>
								<td><a href="javascript:void(0)"
									class="btn btn-xs btn-danger" style="border-radius: 6px;"
									onclick="showConfirmation(${permissions.p_id}, ${permissions.role_id})">
										<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
								</a></td>
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
						$('#permissionListTable').DataTable();
						//$('#dynamic-table').children().first().style.display = 'none';
						//$("#permissionListTable_wrapper > :first-child").remove();
						//$("#permissionListTable_wrapper").style.display = 'none';
						document.getElementById('permissionListTable_length').style.display = 'none';
						//document.getElementById('permissionListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
	
	//Show confirmation before delete
	function showConfirmation(p_id, role_id) {
		var response = confirm("Do you want to delete this Permission?");
		if( response == true ) {
			
			//postSubmit('${pageContext.request.contextPath}/adminpanel/permission/delete.do',{p_id:'${permissions.p_id}', role_id:'${permissions.role_id}' },'POST')
			
			var path = $('#contextPath').val() + "/adminpanel/permission/delete.do";
			var param = {
				p_id : p_id,
				role_id : role_id
			}
			//alert(p_id + " ---- " + role_id);
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

<!-- ----------- Footer -------------- -->
<%@include file="../common/ibcsFooter.jsp"%>