<!-- ---------------------------------------------------------------------------------- -->
<!-- Including header.jsp -->
<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/adminPanel.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>&nbsp;Role
				List
			</a>
			<%-- <a href="#" onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/userList.do',{role_id:'${currentUser.id}'},'POST')"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;
			</a> --%>
			<a href="javascript:void(0)" class="btn btn-success btn-sm"
				onclick="postSubmit('${pageContext.request.contextPath}/adminpanel/userList.do',{role_id:'${role.role_id}'},'POST')">
				<span
				class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp; User List </a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">User
			Edit Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- ------ Form Start ---------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/adminpanel/user/update.do">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Full Name</label>
						<div class="col-sm-8">
							<input name="name" type="text" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="name"
								value="${currentUser.name}" /> <input type="hidden" name="id"
								value="${currentUser.id}" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="email" class="col-sm-4 col-md-4 control-label">Email</label>
						<div class="col-sm-8 col-md-8">
							<input name="email" type="email" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="email"
								value="${currentUser.email}" readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="userid" class="col-sm-4 control-label">User id</label>
						<div class="col-sm-8">
							<input name="userid" type="text" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="userid"
								value="${currentUser.userid}" readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>


					<div class="form-group">
						<label for="password" class="col-sm-4 col-md-4 control-label">Password</label>
						<div class="col-sm-8 col-md-8">
							<input name="password" type="password" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="password"
								value="${currentUser.password}" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="password" class="col-sm-4 col-md-4 control-label">Is Active?</label>
						<div class="col-sm-8 col-md-8">
							<c:if test="${currentUser.active eq true}">
								<input type="checkbox" checked name="isactive" id="isactive" />
								<input type="hidden" id="active" name="active" value="true">
							</c:if>
							<c:if test="${currentUser.active eq false}">
								<input type="checkbox" name="isactive" id="isactive" />
								<input type="hidden" id="active" name="active" value="false">
							</c:if>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="empId"
							class="col-sm-4 col-md-4 control-label align-right">Employee
							ID</label>
						<div class="col-sm-8 col-md-8">
							<input name="empId" type="text" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="empId"
								value="${currentUser.empId}" readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="deptId"
							class="col-sm-4 col-md-4 control-label align-right">Department</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" name="deptId" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<!-- <option disabled selected>Select a Department</option> -->
								<option selected="selected" value="${dept.deptId}">${dept.deptName}</option>
								<c:if test="${!empty departmentList}">
									<c:forEach var="department" items="${departmentList}">
										<option value="${department.deptId}">
											<c:out value="${department.deptName}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="designation"
							class="col-sm-4 col-md-4 control-label align-right">Designation</label>
						<div class="col-sm-8 col-md-8">
							<input name="designation" type="text" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="designation"
								value="${currentUser.designation}" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="roleid"
							class="col-sm-4 col-md-4 control-label align-right">Role</label>
						<div class="col-sm-8 col-md-8">
							<select name="roleid" class="form-control"
								style="border: 0; border-bottom: 2px ridge;" id="roleid">
								<c:if test="${!empty roleList}">
									<!-- <option disabled selected>Select a Role</option> -->
									<option selected="selected" value="${role.role_id}">${role.role}</option>
									<c:forEach items="${roleList}" var="roles">
										<option value="${roles.role_id}"><c:out
												value="${roles.role}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="password" class="col-sm-4 col-md-4 control-label align-right">Is Locked?</label>
						<div class="col-sm-8 col-md-8">
							<c:if test="${currentUser.locked == 1}">
								<input type="checkbox" checked name="islocked" id="islocked" />
								<input type="hidden" id="locked" name="locked" value="true">
							</c:if>
							<c:if test="${currentUser.locked == 0}">
								<input type="checkbox" name="islocked" id="islocked" />
								<input type="hidden" id="locked" name="locked" value="false">
							</c:if>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				</div>


				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>

			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>

<script>
	$( document ).ready( function() {
		
		//isActive
		$('#isactive').click( function() {
			if( $('#isactive').prop('checked') == true ) {
				$('#active').val('true');
			} else {
				$('#active').val('false');
			}
			//alert( $('#active').val() );
		});
		
		//isLocked
		$('#islocked').click( function() {
			if( $('#islocked').prop('checked') == true ) {
				$('#locked').val('1');
			} else {
				$('#locked').val('0');
			}
			//alert( $('#locked').val() );
		});
	});
</script>

<%@include file="../common/ibcsFooter.jsp"%>