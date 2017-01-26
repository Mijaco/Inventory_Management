<!-- ---------------------------------------------------------------------------------- -->
<!-- Including header.jsp -->
<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------------------------------------------------------------------- -->

<div class="main-container" id="main-container">
	<script type="text/javascript">
		try {
			ace.settings.check('main-container', 'fixed')
		} catch (e) {
		}
	</script>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">

				<!-- PAGE CONTENT Start -->
				<ul class="nav nav-pills">
					<li class="active"><a data-toggle="pill" href="#roles">Roles</a></li>
					<li><a data-toggle="pill" href="#users">User Info</a></li>
					<li><a data-toggle="pill" href="#permissions">Permission
							Info</a></li>
				</ul>

				<div class="tab-content">
					<div id="roles" class="tab-pane fade in active">
						<form class="form-inline" role="form" method="POST"
							action="${pageContext.request.contextPath}/admin/role/save.do">
							<input name="id" value="${roles.id}" hidden="true" /> <input
								name="role_id" value="${roles.role_id}" hidden="true" />
							<div class="form-group">
								<label for="role">Role Name:</label> <input type="text"
									class="form-control" id="role" name="role"
									value="${roles.role}" />
							</div>
							<!-- <button type="submit" class="btn btn-default">Add New
							Role</button> -->
							<input type="submit" value="Submit" class="btn btn-default" />
						</form>
						<hr />

						<c:if test="${!empty roleList}">
							<div>
								<div>
									<table id="dynamic-table"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>Role name</th>
												<th>Role id</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${roleList}" var="roles">
												<tr>
													<td><a
														href="${pageContext.request.contextPath}/admin/user/authUser.do?role_id=<c:out value="${roles.role_id}" />">
															<c:out value="${roles.role}" />
													</a></td>
													<td><c:out value="${roles.role_id}" /></td>
													<td><div class="hidden-sm hidden-xs action-buttons">
															<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																href="${pageContext.request.contextPath}/admin/role/edit.do?id=${roles.id}">
																<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
															</a> <a class="btn btn-danger btn-xs" style="border-radius: 6px;"
																href="${pageContext.request.contextPath}/admin/role/delete.do?id=${roles.id}">
																<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
															</a>
														</div></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</c:if>
					</div>
					<!-- --------------------------user tab start ------------------------------------- -->
					<div id="users" class="tab-pane fade">

						<div class="row">
							<button type="button" class="btn btn-info btn-lg"
								data-toggle="modal" data-target="#userModal">New User</button>
							<div class="col-xs-12">


								<div class="modal fade" id="userModal" role="dialog"
									aria-hidden="true" style="display: none;">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">×</button>
												<h4 class="modal-title">Add New User</h4>
											</div>
											<form method="POST"
												action="${pageContext.request.contextPath}/admin/user/save.do">
												<div class="modal-body">
													<fieldset>
														<label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="name" type="text" class="form-control"
																placeholder="Full Name">
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="userid" type="text" class="form-control"
																placeholder="Username"> <i
																class="ace-icon fa fa-user"></i>
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="password" type="password" class="form-control"
																placeholder="Password"> <i
																class="ace-icon fa fa-lock"></i>
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="email" type="email" class="form-control"
																placeholder="Email" /> <i
																class="ace-icon fa fa-envelope"></i>
														</span> <label class="block clearfix"> <span
																class="block input-icon input-icon-right"> <!-- <input
																	name="deptId" type="text" class="form-control"
																	placeholder="Department Id"> --> <select
																	class="form-control" name="deptId">
																		<option disabled selected>--Select a
																			Department--</option>
																		<c:if test="${!empty departmentList}">
																			<c:forEach var="department" items="${departmentList}">
																				<option value="${department.deptId}">
																					<c:out value="${department.deptName}" /></option>
																			</c:forEach>
																		</c:if>
																</select>

															</span> <label class="block clearfix"> <span
																	class="block input-icon input-icon-right"> <input
																		name="designation" type="text" class="form-control"
																		placeholder="Designation">
																</span>

															</label>
																<div>
																	<span class="block input-icon input-icon-right">
																		<select name="roleid"
																		class="chosen-select form-control"
																		id="form-field-select-3"
																		data-placeholder="Choose a role...">
																			<c:if test="${!empty roleList}">
																				<c:forEach items="${roleList}" var="roles">
																					<option value="${roles.role_id}"><c:out
																							value="${roles.role}" /></option>
																				</c:forEach>
																			</c:if>
																	</select>
																	</span>
																</div>
													</fieldset>

												</div>
												<div class="modal-footer">
													<input type="submit" value="Save" class="btn btn-primary" />
													<!-- <button type="submit" class="btn btn-primary"
													data-dismiss="modal">Save</button> -->
													<button type="button" class="btn btn-danger"
														data-dismiss="modal">Close</button>
												</div>
											</form>
										</div>

									</div>
								</div>

							</div>

							<div class="table-header">User list</div>

							<!-- div.table-responsive -->

							<!-- div.dataTables_borderWrap -->

							<div>
								<div id="dynamic-table2_wrapper"
									class="dataTables_wrapper form-inline no-footer">
									<c:if test="${!empty userList}">
										<table id="dynamic-table2"
											class="table table-striped table-bordered table-hover dataTable no-footer"
											role="grid" aria-describedby="dynamic-table2_info">
											<thead>
												<tr role="row">
													<th class="sorting_asc" tabindex="0"
														aria-controls="dynamic-table2" aria-sort="ascending"
														aria-label="user id: activate to sort column descending">User
														Id</th>
													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Description: activate to sort column ascending">Password</th>
													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Email: activate to sort column ascending">Email</th>

													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Role: activate to sort column ascending">Role</th>

													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Email: activate to sort column ascending">Department
														Id</th>

													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Email: activate to sort column ascending">Designation</th>


													<th class="sorting" tabindex="0"
														aria-controls="dynamic-table2"
														aria-label="Action: activate to sort column ascending">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${userList}" var="user">
													<tr role="row" class="odd">
														<td class="user_userid"><c:out value="${user.userid}" /></td>
														<td class="user_password"><c:out
																value="${user.password}" /></td>
														<td class="user_email"><c:out value="${user.email}" /></td>
														<td class="user_roleid"><c:out value="${user.roleid}" /></td>

														<td class="user_department"><c:out
																value="${user.deptId}" /></td>

														<td class="user_designation"><c:out
																value="${user.designation}" /></td>

														<td class="user_id" hidden="true"><c:out
																value="${user.id}" /></td>
														<td class="user_name" hidden="true"><c:out
																value="${user.name}" /></td>
														<!-- <td><span class="glyphicon glyphicon-edit"></span> <span
														class="glyphicon glyphicon-remove"></span></td> -->
														<td><div class="hidden-sm hidden-xs action-buttons">
																<!-- <a class="green" id="edit_user_btn"
															href="#">
															<i class="ace-icon fa fa-pencil bigger-130"></i> -->
																<a href="#" data-toggle="modal"
																	data-target="#userModal1" class="userModal1"> <i
																	class="ace-icon fa fa-pencil bigger-130"></i>
																</a> <a class="btn btn-danger btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/user/delete.do?id=${user.id}">
																	<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
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



						<!-- ---------Edit User-------- -->
						<div class="row">
							<div class="col-xs-12">
								<div class="modal fade" id="userModal1" role="dialog"
									aria-hidden="true" style="display: none;">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">×</button>
												<h4 class="modal-title">Edit User</h4>
											</div>
											<form method="POST"
												action="${pageContext.request.contextPath}/admin/user/save.do">
												<div class="modal-body">
													<fieldset>
														<label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="name" id="user_modal_name" type="text"
																class="form-control" placeholder="Full Name">
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="userid" id="user_modal_userid" type="text"
																class="form-control" placeholder="Username"> <i
																class="ace-icon fa fa-user"></i>
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="password" id="user_modal_password" type="password"
																class="form-control" placeholder="Password"> <i
																class="ace-icon fa fa-lock"></i>
														</span>
														</label> <label class="block clearfix"> <span
															class="block input-icon input-icon-right"> <input
																name="email" id="user_modal_email" type="email"
																class="form-control" placeholder="Email" /> <i
																class="ace-icon fa fa-envelope"></i>
														</span> <label class="block clearfix"> <span
																class="block input-icon input-icon-right"> <!-- <input
																	name="deptId" type="text" class="form-control"
																	placeholder="Department Id"> --> <select
																	class="form-control" name="deptId"
																	id="user_modal_department">
																		<option disabled selected>--Select a
																			Department--</option>
																		<c:if test="${!empty departmentList}">
																			<c:forEach var="department" items="${departmentList}">
																				<option value="${department.deptId}">
																					<c:out value="${department.deptName}" /></option>
																			</c:forEach>
																		</c:if>
																</select> <!-- <input
																	name="deptId" type="text" class="form-control"
																	placeholder="Department Id" id="user_modal_department" /> -->
															</span> <label class="block clearfix"> <span
																	class="block input-icon input-icon-right"> <input
																		name="designation" type="text" class="form-control"
																		placeholder="Designation" id="user_modal_designation" />
																</span>

															</label> <input name="id" id="user_modal_id" type="text"
																hidden="true" />


																<div>
																	<span class="block input-icon input-icon-right">
																		<select name="roleid"
																		class="chosen-select form-control"
																		id="form-field-select-3"
																		data-placeholder="Choose a role...">
																			<c:if test="${!empty roleList}">
																				<c:forEach items="${roleList}" var="roles">
																					<option value="${roles.role_id}"
																						class="user_modal_roleid">
																						<c:out value="${roles.role}" /></option>
																				</c:forEach>
																			</c:if>
																	</select>
																	</span>
																</div>
													</fieldset>

												</div>
												<div class="modal-footer">
													<input type="submit" value="Update" class="btn btn-primary" />
													<!-- <button type="submit" class="btn btn-primary"
													data-dismiss="modal">Save</button> -->
													<button type="button" class="btn btn-danger"
														data-dismiss="modal">Close</button>
												</div>
											</form>
										</div>

									</div>
								</div>

							</div>
						</div>



						<script>
							$(function() {
								$(".userModal1")
										.on(
												"click",
												function() {
													//var userName = $('[name="name"]').val();
													var rowindex = $(this)
															.closest('tr')
															.index();

													var userId = $(
															'.user_userid')
															.get(rowindex).textContent;
													var id = $('.user_id').get(
															rowindex).textContent;
													var name = $('.user_name')
															.get(rowindex).textContent;
													var password = $(
															'.user_password')
															.get(rowindex).textContent;
													var roleId = $(
															'.user_roleid')
															.get(rowindex).textContent;
													var email = $('.user_email')
															.get(rowindex).textContent;

													var roleModal = $(".user_modal_roleid");
													var roleName = "";
													if (roleModal.val() == roleId) {
														//roleModal.val(roleId+1);
													}
													for (i = 0; i < roleModal.length; i++) {
														if (roleModal[i].value == roleId) {
															//roleModal.val(roleId);					
															roleModal[i].selected = true;
															break;
														}
													}
													$("#user_modal_userid")
															.val(userId);
													$("#user_modal_name").val(
															name);
													$("#user_modal_password")
															.val(password);
													$("#user_modal_id").val(id);
													$("#user_modal_email").val(
															email);

													$('#user_modal_department')
															.val();

													$('#user_modal_designation')
															.val();
													//alert(userId + " "+id +" "+name+" "+password+" "+roleId + " "+email);
												});

							});
						</script>

						<!-- ----------------------- -->
					</div>
					<!-- --------------------------user tab end and permission tab started------------------------------------- -->
					<div id="permissions" class="tab-pane fade">
						<form class="form-inline" role="form" method="POST"
							action="${pageContext.request.contextPath}/admin/permission/save.do">
							<c:choose>
								<c:when test="${!empty permissionList}">
									<c:forEach items="${permissionList}" var="p">
										<input name="role_id" value="${p.role_id}" hidden="true" />
									</c:forEach>
								</c:when>
								<c:otherwise>
									<label for="object_id"> Role :</label>
									<select class="form-control" name="role_id">
										<c:if test="${!empty roleList}">
											<c:out value="${roleList}"></c:out>
											<c:forEach items="${roleList}" var="r">
												<option value="${r.role_id}"><c:out
														value="${r.role}" /></option>
											</c:forEach>
										</c:if>
									</select>
								</c:otherwise>
							</c:choose>

							<input name="p_read" hidden="true" value="0" /> <input
								name="p_write" hidden="true" value="0" /> <input name="p_edit"
								hidden="true" value="0" /> <input name="p_delete" hidden="true"
								value="0" />
							<div class="form-group">
								<label for="object_id">Add New Menu:</label> <select
									class="form-control" name="object_id">
									<c:if test="${!empty objectList}">
										<c:forEach items="${objectList}" var="objects">
											<option value="${objects.id}"><c:out
													value="${objects.class_name}" /></option>
										</c:forEach>
									</c:if>
								</select>
								<%-- <input type="text"
								class="form-control" id="role" name="role" value="${roles.role}" /> --%>
							</div>
							<!-- <button type="submit" class="btn btn-default">Add New
							Role</button> -->
							<input type="submit" value="Submit" class="btn btn-default" />
						</form>
						<hr />
						<div class="row">
							<div class="col-xs-12">
								<div class="table-header">Permission table</div>
								<div>
									<c:if test="${!empty permissionList}">
										<table id="dynamic-table"
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
													<th>Denay All</th>
													<th>Action</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach items="${permissionList}" var="permissions">
													<tr>
														<td><c:out value="${permissions.role_id}" /></td>
														<%-- <td><c:out value="${permissions.object_id}" /></td> --%>
														<td><c:out value="${permissions.objectName}" /></td>
														<c:choose>
															<c:when test="${permissions.p_read =='0'}">
																<td><a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=1&p_write=${permissions.p_write}&p_edit=${permissions.p_edit}&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-times-circle"></i>
																</a></td>
															</c:when>
															<c:otherwise>
																<td><a class="btn btn-success btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=0&p_write=${permissions.p_write}&p_edit=${permissions.p_edit}&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-check-circle"></i>
																</a></td>
															</c:otherwise>
														</c:choose>

														<c:choose>
															<c:when test="${permissions.p_write =='0'}">
																<td><a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=1&p_edit=${permissions.p_edit}&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-times-circle"></i>
																</a></td>
															</c:when>
															<c:otherwise>
																<td><a class="btn btn-success btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=0&p_edit=${permissions.p_edit}&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-check-circle"></i>
																</a></td>
															</c:otherwise>
														</c:choose>

														<c:choose>
															<c:when test="${permissions.p_edit =='0'}">
																<td><a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=${permissions.p_write}&p_edit=1&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-times-circle"></i>
																</a></td>
															</c:when>
															<c:otherwise>
																<td><a class="btn btn-success btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=${permissions.p_write}&p_edit=0&p_delete=${permissions.p_delete}&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-check-circle"></i>
																</a></td>
															</c:otherwise>
														</c:choose>


														<c:choose>
															<c:when test="${permissions.p_delete =='0'}">
																<td><a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=${permissions.p_write}&p_edit=${permissions.p_edit}&p_delete=1&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-times-circle"></i>
																</a></td>
															</c:when>
															<c:otherwise>
																<td><a class="btn btn-primary btn-xs" style="border-radius: 6px;"
																	href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=${permissions.p_read}&p_write=${permissions.p_write}&p_edit=${permissions.p_edit}&p_delete=0&role_id=${permissions.role_id}">
																		<i class="fa fa-fw fa-check-circle"></i>
																</a></td>
															</c:otherwise>
														</c:choose>
														<td><a
															href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=1&p_write=1&p_edit=1&p_delete=1&role_id=${permissions.role_id}">Permit
																All </a></td>
														<td><a
															href="${pageContext.request.contextPath}/admin/permission/update.do?p_id=${permissions.p_id}&object_id=${permissions.object_id}&p_read=0&p_write=0&p_edit=0&p_delete=0&role_id=${permissions.role_id}">Denay
																All </a></td>
														<td><a class="btn btn-danger btn-xs" style="border-radius: 6px;"
															href="${pageContext.request.contextPath}/admin/permission/delete.do?p_id=${permissions.p_id}&role_id=${permissions.role_id}">
																<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
														</a></td>
														<!-- <td> <span class="glyphicon glyphicon-ok"></span></td> -->
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</div>
					</div>

					<!-- ---------------------permission tab end --------------------- -->
				</div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='${pageContext.request.contextPath}/resources/assets/js/jquery.min.js'>"
								+ "<"+"/script>");
	</script>


	<!-- inline scripts related to this page -->
	<!-- <script type="text/javascript">
		jQuery(function($) {
			$("#dynamic-table").dataTable();

		})
	</script> -->

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#dynamic-table').DataTable();
							//$('#dynamic-table').children().first().style.display = 'none';
							$("#dynamic-table_wrapper > :first-child").remove();
							$("#dynamic-table_wrapper").style.display = 'none';
							;
							document.getElementById('dynamic-table_length').style.display = 'none';
							//document.getElementById('dynamic-table_filter').style.display = 'none';
						});
	</script>
	<!-- /.page-content -->
</div>
<!-- /.main-content -->
<script
  src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<!-- ---------------------------------------------------------------------------------- -->
<!-- Including ibcsFooter.jsp -->
<%@include file="../common/ibcsFooter.jsp"%>
<!-- ---------------------------------------------------------------------------------- -->