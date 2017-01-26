<%--
    Author     : Ihteshamul Alam, IBCS
    Created on : 28-07-2016
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/assets/images/favicon.ico">
<title>${properties['project.title']}</title>

<meta name="description" content="User login page" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- bootstrap & fontawesome -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/4.2.0/css/font-awesome.min.css" />

<!-- text fonts -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/fonts/fonts.googleapis.com.css" />

<!-- ace styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/ace.min.css" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="resources/assets/css/ace-part2.min.css" />
		<![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/ace-rtl.min.css" />

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="resources/assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="resources/assets/js/html5shiv.min.js"></script>
		<script src="resources/assets/js/respond.min.js"></script>
		<![endif]-->
</head>

<body class="login-layout light-login">
	<div class="container col-xs-6 col-xs-offset-3" style="margin-top: 200px;">
		<c:if test="${!empty expiredflag}">
			<h4 class="text-center red">Token is expired. Please visit Login page to reset password.</h4>
			<br>
			<div class="align: center">
				<a class="btn btn-success btn-lg" style="border-radius: 6px;" href="${pageContext.request.contextPath}/auth/login.do">Login</a>
			</div>
		</c:if>
		
		<c:if test="${!empty closedflag}">
			<h4 class="text-center red">Token is already used. Please visit Login page to reset password.</h4>
			<div align="center">
				<a class="btn btn-success btn-lg" style="border-radius: 6px;" href="${pageContext.request.contextPath}/auth/login.do">Login</a>
			</div>
		</c:if>
		
		<c:if test="${!empty openflag}">
			<div class="alert alert-success hide">
				<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
				<strong>Success!</strong> Password is updated and active from next login.
			</div>
			
			<div class="alert alert-danger hide">
				<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
				<strong>Error!</strong> Password is not updated.
			</div>

			<div class="panel panel-primary">
			<div class="panel-heading"><h5>Reset Password</h5></div>
			<div class="panel-body">
				<form class="form-horizontal" action="" method="POST" onclick="return false">
					<div class="form-group">
						<label for="" class="control-label col-md-3">Password</label>
						<div class="col-md-7">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-fw fa-key"></i></span>
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" autocomplete="off">
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="" class="control-label col-md-3">Retype Password</label>
						<div class="col-md-7">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-fw fa-key"></i></span>
								<input type="password" id="rpassword" name="rpassword" class="form-control" placeholder="Retype Password" autocomplete="off">
							</div>
						</div>
					</div>
					
					<input type="hidden" id='email' value='${rsToken.email}'>
					<input type="hidden" id='id' value='${rsToken.id}'>
					<input type="hidden" id='contextPath' value='${pageContext.request.contextPath}'>
					
					<br>
					<div class="col-md-7 col-md-offset-4">
						<button class="btn btn-primary btn-lg" id="updatepassword" style="border-radius: 6px;"><i class="fa fa-fw fa-save"></i>&nbsp;Update</button>
					</div>
					
				</form>
			</div>
		</div>
		</c:if>
	</div>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/jquery.2.1.1.min.js"></script>
</body>

<script>
	$( document ).ready( function() {
		
		function show_error_outline(id) {
			var trace = $('#'+id).closest('div');
			trace.removeClass('has-success');
			trace.addClass('has-error');
		}
		
		function remove_outline(id) {
			var trace = $('#'+id).closest('div');
			trace.removeClass('has-error');
			trace.removeClass('has-success');
		}
		
		$('#updatepassword').click( function() {
			//var haserror = false;
			
			if( $('#password').val() == null || $.trim( $('#password').val() ) == '' ) {
				
				show_error_outline('password');
				
				$('.alert.alert-danger').removeClass('hide').html('<strong>Error!</strong> Password field is empty!!');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
					function() {
						// $(".alert.alert-success").alert('close');
					});
			} else if( $('#rpassword').val() == null || $.trim( $('#rpassword').val() ) == '' ) {
				
				remove_outline('password');
				show_error_outline('rpassword');
				
				$('.alert.alert-danger').removeClass('hide').html('<strong>Error!</strong> Retype Password field is empty!!');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
					function() {
						// $(".alert.alert-success").alert('close');
					});
			} else if( ( $('#password').val() != null || $.trim( $('#password').val() ) != '' ) && ( $('#password').val() != $('#rpassword').val() ) ) {
				
				remove_outline('rpassword');
				show_error_outline('password');
				show_error_outline('rpassword');
				
				
				$('.alert.alert-danger').removeClass('hide').html('<strong>Error!</strong> Password does not match!!');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
					function() {
						// $(".alert.alert-success").alert('close');
					});
			} else {
				remove_outline('rpassword');
				remove_outline('password');
				
				$('#updatepassword').attr('disabled', 'disabled');
				
// 				$('.alert.alert-success').removeClass('hide').html('<strong>Success!</strong> Password is updated!!');
// 				$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
// 					function() {
// 						// $(".alert.alert-success").alert('close');
// 					});
				
				var email = $('#email').val();
				var id = $('#id').val();
				var password = $('#password').val();
				var contextPath = $('#contextPath').val();
				$.ajax({
					url : contextPath + '/auth/updatePassword.do',
					data : "{email:" + email + ", id:"+ id +", password:"+ password +"}",
					contentType : "application/json",
					success : function(data) {
						if( data == 'success' ) {
							$('.alert.alert-success').removeClass('hide');
							$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
								function() {
									// $(".alert.alert-success").alert('close');
									location.href = contextPath + "/auth/login.do";
								});
						} else {
							$('.alert.alert-danger').removeClass('hide');
							$('#updatepassword').removeAttr('disabled');
						}
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				}); //ajax
			}
		});
	});
</script>
</html>


