<%@include file="../common/adminheader.jsp"%>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/adminPanel.do" class="btn btn-warning"> Role List </a> <a href="javascript:void(0)"
				class="btn btn-success"
				onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/getNewUserForm.do',{role_id:'${role_id}'},'POST')">
				Add New User
			</a>
		</div>
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			${roleName} User(s)</h1>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<c:if test="${empty userList}">
			<h5 class="text-success center">Sorry! No User(s) found for this role!!</h5>
		</c:if>
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" >
		
		<div class="col-xs-12 table-responsive">
			<c:if test="${!empty userList}">
				<table id="myListTable"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr role="row">
							<th class="">User Name</th>
							<th class="">User Id</th>
							<th class="">Email</th>
							<th class="">Role</th>
							<th class="">Department</th>
							<th class="">Designation</th>
							<th class="">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userList}" var="user">
							<tr>
								<td class=""><c:out value="${user.name}" /></td>
								<td class=""><c:out value="${user.userid}" /></td>
								<td class=""><c:out value="${user.email}" /></td>
								<td class=""><c:out value="${roleName}" /></td>
								<td class=""><c:out value="${user.departmentName}" /></td>
								<td class=""><c:out value="${user.designation}" /></td>
								<td class="col-xs-2"><div class="action-buttons">
										<!-- <a href="#" data-toggle="modal" data-target="#userModal1"
													class="userModal1"> <i
													class="ace-icon fa fa-pencil bigger-130"></i>
												</a> -->
										<a href="javascript:void(0)" class="btn btn-xs btn-primary"
											style="border-radius: 6px;"
											onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/user/edit.do',{id:'${user.id}'},'POST')">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a href="javascript:void(0)" class="btn btn-xs btn-danger"
											style="border-radius: 6px;"
											onclick="showConfirmation(${user.id})"> <i
											class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
										</a>
									</div></td>

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
	$(document).ready(function() {
		$('#myListTable').DataTable();
		//$('#dynamic-table').children().first().style.display = 'none';
		//$("#myListTable_wrapper > :first-child").remove();
		//$("#myListTable_wrapper").style.display = 'none';
		document.getElementById('myListTable_length').style.display = 'none';
		//document.getElementById('myListTable_filter').style.display = 'none';
		
		
		$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-danger").alert('close');
		});
	});
	
	//Show confirmation before delete
	function showConfirmation(id) {
		var response = confirm("Do you want to delete this role?");
		if( response == true ) {
			//postSubmit('${pageContext.request.contextPath}/adminpanel/user/delete.do',{id:'${user.id}'},'POST')
			var path = $('#contextPath').val() + "/adminpanel/user/delete.do";
			var param = {
				id : id
			}
			postSubmit(path, param, 'POST');

		} else {
			//console.log( "null" );
		}
	}

	
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- Footer -->
<%@include file="../common/ibcsFooter.jsp"%>