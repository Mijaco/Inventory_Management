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
		style="background-color: white; padding: 10px; padding-left: 20px;">

		<h1 class="center red" style="margin-top: 0px;">Please Must Change Password</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin:10px;">

		<form class="form-horizontal" role="form"
			action="${pageContext.request.contextPath}/changedPassword.do" method="POST">
			<input type="hidden" id="id" value="${userinfo.id}"> <input
				type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="form-group">
				<label class="col-sm-4 align-right control-label" for="inputEmail3">Old
					Password &nbsp;<strong class="red">*</strong> :</label>
				<div class="col-sm-6">
					<input type="password" name="opassword" class="form-control"
						id="opassword">
					<h5 class="text-danger opassword hide">
						<strong>Invalid Old Password Field</strong>
					</h5>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 align-right control-label"
					for="inputPassword3">New Password &nbsp;<strong class="red">*</strong> :</label>
				<div class="col-sm-6">
					<input type="password" name="password" class="form-control"
						id="npassword">
					<h5 class="text-danger npassword hide">
						<strong>Invalid New Password Field</strong>
					</h5>
					<h5 class="text-danger nppassword hide">
						<strong>Password does not match</strong>
					</h5>
					<h5 class="text-success cpassword hide">
						<strong>Password Match</strong>
					</h5>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-4 align-right control-label"
					for="inputPassword3">Retype New Password &nbsp;<strong class="red">*</strong> :</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="rnpassword">
					<h5 class="text-danger rnpassword hide">
						<strong>Invalid Old Password Field</strong>
					</h5>
					<h5 class="text-danger nppassword hide">
						<strong>Password does not match</strong>
					</h5>
					<h5 class="text-success cpassword hide">
						<strong>Password Match</strong>
					</h5>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-12" align="center">
					<button id="updatepassword" type="button" class="btn btn-primary">
						<i class="fa fa-fw fa-save"></i>&nbsp;Change Password
					</button>
				</div>
			</div>
		</form>

	</div>
</div>

<script>
	$(document)
			.ready(
					function() {
						$('#updatepassword')
								.click(
										function() {
											var haserror = false, counter = 0;

											if ($('#opassword').val() == null
													|| $.trim($('#opassword')
															.val()) == '') {
												$('.opassword').removeClass(
														'hide');
												haserror = true;
											} else {
												$('.opassword')
														.addClass('hide');
											}

											if ($('#npassword').val() == null
													|| $.trim($('#npassword')
															.val()) == '') {
												$('.npassword').removeClass(
														'hide');
												counter++;
												haserror = true;
											} else {
												$('.npassword')
														.addClass('hide');
											}

											if ($('#rnpassword').val() == null
													|| $.trim($('#rnpassword')
															.val()) == '') {
												$('.rnpassword').removeClass(
														'hide');
												counter++;
												haserror = true;
											} else {
												$('.rnpassword').addClass(
														'hide');
											}

											if ($('#npassword').val() != ''
													&& $('#rnpassword').val() != '') {
												if ($('#npassword').val() != $(
														'#rnpassword').val()) {
													haserror = true;
													$('.cpassword').addClass(
															'hide');
													$('.nppassword')
															.removeClass('hide');
												} else {
													$('.nppassword').addClass(
															'hide');
													$('.cpassword')
															.removeClass('hide');
												}
											}

											if (haserror == true) {
												return;
											} else {
												$('form').submit();												
											}
										});
					});
</script>


<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>