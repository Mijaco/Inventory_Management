<%-- <%@include file="../common/ibcsHeader.jsp"%> --%>
<%@include file="../common/homeHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->

<style type="text/css">
.ui-widget-overlay {
	opacity: .6 !important;
}

.modal-body .form-horizontal .col-sm-4 {
	width: 33.333333333%;
}

.modal-body .form-horizontal .col-sm-12 {
	width: 100%;
}

.modal-body .form-horizontal .col-sm-6 {
    width: 50%;
}

.modal-body .form-horizontal .control-label {
    text-align: left;
}
.modal-body .form-horizontal .col-sm-offset-2 {
    margin-left: 15px;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<%-- <div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/jobcard/jobList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div> --%>
		<h1 class="center blue" style="margin-top: 0px;">${userinfo.name} :: Profile</h1>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="success col-xs-3">Name</td>
						<td class="col-xs-3">${userinfo.name}</td>
						<td class="success col-xs-3">Designation</td>
						<td class="col-xs-3">${userinfo.designation}</td>
					</tr>
					
					<tr>
						<td class="success col-xs-3">Department/Division</td>
						<td class="col-xs-3">${department.deptName}</td>
						<td class="success col-xs-3">Employee Id</td>
						<td class="col-xs-3">${userinfo.empId}</td>
					</tr>
					
					<tr>
						<td class="success col-xs-3">E-mail</td>
						<td class="col-xs-3">${userinfo.email}</td>
						<td class="success col-xs-3">Is Active?</td>
						<td class="col-xs-3">${userinfo.active == true ? 'Active' : 'Inactive'}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="col-xs-12" align="center">
			<button class="btn btn-primary" id="changepassword" style="border-radius: 6px;"
				 data-toggle="modal" data-target=".bs-example-modal-sm"  data-backdrop="static" data-keyboard="false" aria-hidden="true" style="display: none">
				 <i class="fa fa-fw fa-edit"></i>&nbsp;Change Password
			</button>
		</div>

		<div class="modal fade bs-example-modal-sm" tabindex="-1"
			role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Change Password</h4>
					</div>

					<div class="modal-body">
						<form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/changePassword.do">
							<input type="hidden" id="id" value="${userinfo.id}">
							<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
							<div class="form-group">
								<label class="col-sm-4 align-right control-label" for="inputEmail3">Old Password &nbsp;<strong class="red">*</strong> :</label>
								<div class="col-sm-6">
									<input type="password" name="opassword" class="form-control" id="opassword">
									<h5 class="text-danger opassword hide"><strong>Invalid Old Password Field</strong></h5>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 align-right control-label" for="inputPassword3">New Password &nbsp;<strong class="red">*</strong> :</label>
								<div class="col-sm-6">
									<input type="password" name="password" class="form-control" id="npassword">
									<h5 class="text-danger npassword hide"><strong>Invalid New Password Field</strong></h5>
									<h5 class="text-danger nppassword hide"><strong>Password does not match</strong></h5>
									<h5 class="text-success cpassword hide"><strong>Password Match</strong></h5>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 align-right control-label" for="inputPassword3">Retype New Password &nbsp;<strong class="red">*</strong> :</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="rnpassword">
									<h5 class="text-danger rnpassword hide"><strong>Invalid Old Password Field</strong></h5>
									<h5 class="text-danger nppassword hide"><strong>Password does not match</strong></h5>
									<h5 class="text-success cpassword hide"><strong>Password Match</strong></h5>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-12" align="center">
									<button id="updatepassword" type="button" class="btn btn-primary"><i class="fa fa-fw fa-save"></i>&nbsp;Change Password</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>

<script>
	$( document ).ready( function() {
		$('#updatepassword').click( function() {
			var haserror = false, counter=0;
			
			if( $('#opassword').val() == null || $.trim( $('#opassword').val() ) == '' ) {
				$('.opassword').removeClass('hide');
				haserror = true;
			} else {
				$('.opassword').addClass('hide');
			}
			
			if( $('#npassword').val() == null || $.trim( $('#npassword').val() ) == '' ) {
				$('.npassword').removeClass('hide');
				counter++;
				haserror = true;
			} else {
				$('.npassword').addClass('hide');
			}
			
			if( $('#rnpassword').val() == null || $.trim( $('#rnpassword').val() ) == '' ) {
				$('.rnpassword').removeClass('hide');
				counter++;
				haserror = true;
			} else {
				$('.rnpassword').addClass('hide');
			}
			
			if( $('#npassword').val() != '' && $('#rnpassword').val() != '' ) {
				if( $('#npassword').val() != $('#rnpassword').val() )
				{
					haserror = true;
					$('.cpassword').addClass('hide');
					$('.nppassword').removeClass('hide');
				} else {
					$('.nppassword').addClass('hide');
					$('.cpassword').removeClass('hide');
				}
			}
			
			if( haserror == true ) {
				return;
			} else {
				var contextPath = $('#contextPath').val();
				var id = $('#id').val();
				var opassword = $('#opassword').val();
				var password = $('#npassword').val();
				$.ajax({
					url : contextPath + '/changePassword.do',
					data : {"id":id, "opassword":opassword, "password":password},
					success : function(data) {
						var result = data;
						if (result == 'opdnm') {
							alert('Old Password Does not match');
						} else if( result == 'success' ) {
							alert('Password is updated');
							location.href = contextPath + "/logout.do";
						}
					},
					error : function(data) {
						alert(data);
					},
					type : 'POST'
				});
			}
		});
	});
</script>


<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>