<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/adminPanel.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>&nbsp; Role List
			</a>
		</div>

		<h1 class="center blue" style="margin-top: 0; font-style:italic; 
		font-family: 'Ubuntu Condensed', sans-serif;">New User Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- ------ Form Start ---------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/adminpanel/saveUser.do">
			<input type="hidden" name="contextPath" id="contextPath" value="${pageContext.request.contextPath}">			
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Full Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input name="name" type="text" class="form-control"
							 style="border: 0; border-bottom: 2px ridge;" 
								id="name" placeholder="Enter Full Name of User"/>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="email"
							class="col-sm-4 col-md-4 control-label">Email:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7 col-md-7">
							 <input name="email" type="email" class="form-control"
							  style="border: 0; border-bottom: 2px ridge;" 
								id="email" onblur="checkEmail()" placeholder="Enter your email id" /> 
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision-1" style="font-size: 2em;" class=""></i>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="userid" class="col-sm-4 control-label">User id:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<input name="userid" type="text" class="form-control"
							 style="border: 0; border-bottom: 2px ridge;" 
							id="userid" onblur="checkUserId()" placeholder="Enter a user id"/> 
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision-2" style="font-size: 2em;" class=""></i>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					
					<div class="form-group">
						<label for="password"
							class="col-sm-4 col-md-4 control-label">Password:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							 <input name="password" type="password" class="form-control"
							  style="border: 0; border-bottom: 2px ridge;" 
							 id="password" placeholder="Enter your password"/> 
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="empId"
							class="col-sm-4 col-md-4 control-label align-right">Employee ID</label>
						<div class="col-sm-8 col-md-8">
							<input name="empId" type="text" class="form-control"
							 style="border: 0; border-bottom: 2px ridge;" 
								id="empId" placeholder="Enter DESCO Employee ID"/>
						</div>
					</div>	
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="deptId"
							class="col-sm-4 col-md-4 control-label align-right">Department:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" name="deptId" id="deptId"
							style="border: 0; border-bottom: 2px ridge;">
								<option disabled selected>Select a Department</option>
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
							class="col-sm-4 col-md-4 control-label align-right">Designation:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input name="designation" type="text" class="form-control"
							 style="border: 0; border-bottom: 2px ridge;" 
								id="designation" 	placeholder="Designation"/>
						</div>
					</div>	
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="roleid"
							class="col-sm-4 col-md-4 control-label align-right">Role:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select name="roleid" class="form-control"
								 style="border: 0; border-bottom: 2px ridge;" id="roleid">
									<c:if test="${!empty roleList}">
										<option disabled selected>Select a Role</option>									
										<c:forEach items="${roleList}" var="roles">
											<option value="${roles.role_id}"><c:out
													value="${roles.role}" /></option>
										</c:forEach>
									</c:if>
							</select>
						</div>
					</div>	
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				</div>
				
				
				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success" disabled id="saveButton" name="saveButton">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
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
			
			<input type="hidden" id="chkEmail" name="chkEmail" value='1' />
			<input type="hidden" id="chkUserId" name="chkUserId" value='2' />
			<input type="hidden" id="contextPath" name="contextpath"
				value='${pageContext.request.contextPath}' />
		</form>
		<!-- --------------------- -->
	</div>
</div>
 
 
 <script>
 	
 	function isCheckEmail( email ) {
 		if( email == null || email == '' ) {
 			return false;
 		} else {
 			$('#chkEmail').load( contextPath + '/adminpanel/checkEmail.do', {"email":email}, function(d) {
 	 			if( d == 'success' ) {
 	 				return true;
 	 			} else {
 	 				return false;
 	 			}
 	 		});
 		}
 	}
 	
 	function isCheckUserId( userid ) {
 		if( userid == null || userid == '' ) {
 			return false;
 		} else {
 			$('#chkUserId').load( contextPath + '/adminpanel/checkUserId.do', {"userid":userid}, function(d) {
 	 			if( d == 'success' ) {
 	 				return true;
 	 			} else {
 	 				return false;
 	 			}
 	 		});
 		}
 	}
 
 	function checkEmail() {
 		var email = $('#email').val();
 		var saveButton = $("#saveButton");

		var contextPath = $('#contextPath').val();
		var workOrderDecision = $("#workOrderDecision-1");
		
		$.ajax({
			url : contextPath + '/adminpanel/checkEmail.do',
			data : {"email":email},
			success : function(data) {
				var result = data;
				if (result == 'success') {
					saveButton.prop("disabled", false);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green");

				} else {
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-remove-sign red big");
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
 	}
 	
 	function checkUserId() {
 		var userid = $('#userid').val();
 		var saveButton = $("#saveButton");

		var contextPath = $('#contextPath').val();

		var workOrderDecision = $("#workOrderDecision-2");
		
		$.ajax({
			url : contextPath + '/adminpanel/checkUserId.do',
			data : {"userid":userid},
			success : function(data) {
				var result = data;
				if (result == 'success') {
					saveButton.prop("disabled", false);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green");

				} else {
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-remove-sign red big");
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
 	}
 	
 	$(document).ready( function() {
 		$('#saveButton').click( function() {
 			var haserror = false;
 			
 			var userid = $('#userid').val();
 			var email = $('#email').val();
 			
 			if( isCheckEmail( email ) ) {
 				haserror = true;
 			}
 			
 			if( isCheckUserId( userid ) ) {
 				haserror = true;
 			}
 			
 			if( haserror == false ) {
 				return;
 			} else {
 				$('#saveButton').prop('disabled', true);
 				$('form').submit();
 			}
 		});
 	});
 	
 </script>
 
 
 <%@include file="../common/ibcsFooter.jsp"%>