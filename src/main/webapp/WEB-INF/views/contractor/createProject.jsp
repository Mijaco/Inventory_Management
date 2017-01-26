<%@include file="../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pd/allProjectList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Project List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Add New
			Project</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="post"
			action="${pageContext.request.contextPath}/pd/projectSave.do">

			<!-- <input type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}"> -->
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="projectName" class="col-sm-4 control-label">Project
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="khathName" value=""
								style="border: 0; border-bottom: 2px ridge;" name="khathName" />
						</div>
						<!-- <div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div> -->
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="pdName" class="col-sm-4 control-label">PD Name: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="pdName" value=""
								style="border: 0; border-bottom: 2px ridge;" name="pdName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">End Date:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control datepicker-16"
									id="endDate" style="border: 0; border-bottom: 2px ridge;"
									value="" name="endDate" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label class="col-sm-4 control-label">Description:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="description"
								style="border: 0; border-bottom: 2px ridge;" value=""
								name="description" />
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<!--<div class="form-group">
						<%-- <input type="text" id="contractNo" value="${contractNo}" name="contractNo" />  --%>

						<label for="address" class="col-sm-4 control-label">Address
							:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="address"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="address">
						</div>
					</div> -->
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="sourceofFund"
							class="col-sm-4 control-label align-right">Source of Fund:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="text" class="form-control" id="sourceOfFund"
								value="" style="border: 0; border-bottom: 2px ridge;"
								name="sourceOfFund" /> -->
								
								<select id="sourceOfFund"
								required="required" required="required" name="sourceOfFund"
								class="form-control" style="border: 0; border-bottom: 2px ridge;">
									<option value="">Select Source of Fund</option>
									<c:if test="${!empty sourceOfFund}">
										<c:forEach var="sourceFund" items="${sourceOfFund}">
											<option value="${sourceFund.title}">${sourceFund.title}</option>
										</c:forEach>
									</c:if>
							</select>
				
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="startDate"
							class="col-sm-4 col-md-4 control-label align-right">Start
							Date:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								id="startDate" value=""
								style="border: 0; border-bottom: 2px ridge;" name="startDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<!-- 	<div class="form-group">
						<label for="updateValidityDate"
							class="col-sm-4 col-md-4 control-label align-right">Updated
							Validity Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="updateValidityDate" value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="updateValidityDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div> -->
					<div class="form-group">
						<label class="col-sm-4 control-label align-right">Project
							Duration:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="duration"
								style="border: 0; border-bottom: 2px ridge;" value=""
								name="duration" readonly />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<!-- <div class="form-group">
						<label class="col-sm-4 control-label align-right">Remarks :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="remarks"
								style="border: 0; border-bottom: 2px ridge;" value=""
								name="remarks" />
						</div>
					</div> -->
					<div class="form-group">
						<label class="col-sm-4 control-label align-right">Department
							: <span class="red">*</span></label>
						<div class="col-sm-8">
							<select required="required" name="deptId" 
							class="form-control" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<option value="">--Select Department--</option>
								<c:if test="${!empty departments }">
									<c:forEach var="department" items="${departments}">
										<option value="${department.id}">${department.deptName}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
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

		</form>
		<!-- --------------------- -->
	</div>
</div>

<!-- jqurey date-picker  -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- 
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
 -->
<script>
	function dayCountsBetweenTwoDates(date1, date2) {
		
		
		var d1 = date1.split("-");
		var d2 = date2.split("-");
		
		var n1 = d1[2] + "-" + d1[1] + "-" + d1[0];
		var n2 = d2[2] + "-" + d2[1] + "-" + d2[0];
		
		var x1 = new Date(n1);
		var x2 = new Date(n2);
		var timeDifference = (x2 - x1);

		var days = Math.floor(timeDifference / (1000 * 3600 * 24));
		return days;
	}
	
	$(document)
	.ready(
			function() {
				$('#endDate')
						.change(
								function() {
									if ($('#endDate').val() == ''
											|| $.trim($('#endDate')
													.val()) == null) {
									} else {
										if ($('#startDate').val() == ''
												|| $.trim($(
														'#startDate')
														.val()) == null) {
										} else {
											var date1 = $(
													'#startDate').val();
											var date2 = $(
													'#endDate').val();
											var days = dayCountsBetweenTwoDates(
													date1, date2);
											var ext = (days > 1) ? "days"
													: "day";
											$('#duration').val(
													days + " " + ext);
										}
									}
								});

				$('#startDate')
						.change(
								function() {
									if ($('#startDate').val() == ''
											|| $.trim($('#startDate')
													.val()) == null) {
									} else {
										if ($('#endDate').val() == ''
												|| $.trim($('#endDate')
														.val()) == null) {
										} else {
											var date1 = $(
													'#startDate').val();
											var date2 = $(
													'#endDate').val();
											var days = dayCountsBetweenTwoDates(
													date1, date2);
											var ext = (days > 1) ? "days"
													: "day";
											$('#duration').val(
													days + " " + ext);
										}
									}
								});
			});

	/* $(document)
			.ready(
					function() {
						$('#endDate')
								.change(
										function() {
											if ($('#endDate').val() == ''
													|| $.trim($('#endDate')
															.val()) == null) {
											} else {
												if ($('#startDate').val() == ''
														|| $.trim($(
																'#startDate')
																.val()) == null) {
												} else {
													var date1 = new Date($(
															'#startDate').val());
													var date2 = new Date($(
															'#endDate').val());
													var days = dayCountsBetweenTwoDates(
															date1, date2);
													var ext = (days > 1) ? "days"
															: "day";
													$('#duration').val(
															days + " " + ext);
												}
											}
										});

						$('#startDate')
								.change(
										function() {
											if ($('#startDate').val() == ''
													|| $.trim($('#startDate')
															.val()) == null) {
											} else {
												if ($('#endDate').val() == ''
														|| $.trim($('#endDate')
																.val()) == null) {
												} else {
													var date1 = new Date($(
															'#startDate').val());
													var date2 = new Date($(
															'#endDate').val());
													var days = dayCountsBetweenTwoDates(
															date1, date2);
													var ext = (days > 1) ? "days"
															: "day";
													$('#duration').val(
															days + " " + ext);
												}
											}
										});
					}); */
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/contractorForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>