<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<style>
textarea {
	resize: none;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/settings/list/department.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span>
				Department List
			</a>
			<!-- 			<button accesskey="D" class="btn btn-info btn-sm" type="button"> Discard </button> -->

			<h1 class="center blue">
				<em>New Department Entry Form</em>
			</h1>

		</div>

	</div>

	<div class="container-fluid">
		<div class="row"
			style="background-color: white; padding: 10px; margin: 10px;">

			<!-- --------------------- -->
			<form method="POST"
				action="${pageContext.request.contextPath}/settings/add/newDepartmentSave.do"
				id="newDepartmentForm">

				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}">

				<div class="oe_title">

					<div class="col-md-6 col-sm-6">

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="parent" class="col-sm-5 control-label">Parent
								Department:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<select class="form-control category"
									style="border: 0; border-bottom: 2px ridge;" name="parent"
									id="parent">
									<option disabled selected value="0">Department Name</option>
									<c:if test="${!empty departmentList}">
										<c:forEach items="${departmentList}" var="departmentList">
											<option value="${departmentList.deptId}">
												<c:out value="${departmentList.deptName}" />
											</option>
										</c:forEach>
									</c:if>
								</select>
								<h5 class="text-danger parent hide">
									<strong>This field is required</strong>
								</h5>
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="deptId" class="col-sm-5 control-label">Department
								ID:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="deptId"
									placeholder="Department ID"
									style="border: 0; border-bottom: 2px ridge;" name="deptId"
									required="required" readonly>
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="deptName" class="col-sm-5 col-md-5 control-label">Department
								Name:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control" id="deptName"
									placeholder="Department Name"
									style="border: 0; border-bottom: 2px ridge;" name="deptName"
									required="required" />
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="deptLevel" class="col-sm-5 control-label">Department
								Level:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="deptLevel"
									placeholder="Department Level"
									style="border: 0; border-bottom: 2px ridge;" name="deptLevel"
									readonly>
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="address" class="col-sm-5 control-label">Department
								Address:</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="address"
									placeholder="Department Address"
									style="border: 0; border-bottom: 2px ridge;" name="address">
							</div>
						</div>

					</div>

					<div class="col-md-6 col-sm-6">

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="contactPersion" class="col-sm-5 control-label">Contact
								Person:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="contactPersion"
									placeholder="Contact Person"
									style="border: 0; border-bottom: 2px ridge;"
									name="contactPersion">
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="contactNo" class="col-sm-5 col-md-5 control-label">Contact
								Number:&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control"
									placeholder="Contact Number" id="contactNo"
									style="border: 0; border-bottom: 2px ridge;" name="contactNo" />
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="email" class="col-sm-5 col-md-5 control-label">E-mail
								Address:</label>
							<div class="col-sm-7 col-md-7">
								<input type="email" class="form-control"
									placeholder="E-mail Address" id="email"
									style="border: 0; border-bottom: 2px ridge;" name="email" />
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="remarks" class="col-sm-5 col-md-5 control-label">Remarks:
							</label>
							<div class="col-sm-7 col-md-7">
								<textarea rows="4" cols="" id="remarks" class="form-control"
									maxlength="1000" name="remarks"></textarea>
							</div>
						</div>

					</div>

					<div class="col-md-12" style="padding-top: 15px;" align="center">
						<button type="reset" class="btn btn-lg btn-danger"
							style="border-radius: 6px;">
							<i class="fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>

						<button type="button" value="add" name="actions"
							style="border-radius: 6px; margin-left: 10px;"
							class="btn btn-lg btn-success" id="saveButton">
							<i class="fa fa-save"></i>&nbsp;Add
						</button>
					</div>

				</div>
			</form>
			<!-- --------------------- -->

			<script
				src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		</div>
	</div>
</div>

<script>
	//Added by Shimul
	$(document)
			.ready(
					function() {

						$('#parent')
								.change(
										function() {

											if ($('.parent').hasClass('hide') == false) {
												$('.parent').addClass('hide');
											}

											var deptCode = $('option:selected',
													'#parent').val();
											var baseURL = $('#contextPath')
													.val();

											$
													.ajax({
														url : baseURL
																+ "/settings/searchDepartmentInformation.do",
														data : {
															"deptId" : deptCode
														},
														success : function(data) {
															var gap = data
																	.split("##");
															$('#deptId').val(
																	gap[1]);
															$('#deptLevel')
																	.val(gap[0]);
														},
														error : function(data) {
															alert("Server Error");
														},
														type : 'POST'
													});
										});

						$('#saveButton, #addMoreButton')
								.click(
										function() {
											var haserror = false;

											if ($('#parent').val() == null
													|| $.trim($('#parent')
															.val()) == '0'
													|| $.trim($('#parent')
															.val()) == '') {
												$('.parent')
														.removeClass('hide');
												haserror = true;
											} else {
												$('.parent').addClass('hide');
											}

											if (haserror == true) {
												return;
											} else {
												$('#saveButton').prop('disabled', true);
												$('#addMoreButton').prop('disabled', true);
												$('#newDepartmentForm')
														.submit();
											}
										});
					});
</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>