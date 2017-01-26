<%@include file="../../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/asBuilt/newAsBuiltList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> As
				Built List
			</a>
		</div>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">As
			Built Form</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/asBuilt/newAsBuiltReport.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="identerDesignation" class="col-sm-4 control-label">Indenter
							: &nbsp;<strong class='red'>*</strong>
						</label>
						<div class="col-sm-8">
							<select style="border: 0; border-bottom: 2px ridge;"
								class="form-control" name="identerDesignation"
								id="identerDesignation" onchange="contractorInformation(this)">
								<option value="0" selected disabled>Select a Contractor</option>
								<c:if test="${!empty contractorList}">
									<c:forEach var="contractor" items="${contractorList}">
										<option value="${contractor.id }">${contractor.contractorName}</option>
									</c:forEach>
								</c:if>
							</select> <strong class="text-danger identerDesignation hide">This
								field is required</strong>
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractNo" class="col-sm-4 col-md-4 control-label">Work
							Order No : &nbsp;<strong class='red'>*</strong>
						</label>
						<div class="col-sm-8 col-md-8">
							<input class="form-control" type="text" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" id="contractNo"
								value="" name="contractNo" />
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="jobNo" class="col-sm-4 control-label">Job No :
							&nbsp;<strong class='red'>*</strong>
						</label>
						<div class="col-sm-8">
							<select style="border: 0; border-bottom: 2px ridge;"
								class="form-control" name="jobNo" id="jobNo">
								<option value="0" selected disabled>Select a Job</option>
							</select> <strong class="text-danger jobNo hide">This field is
								required</strong> <input type="hidden" id="jobName" name="jobName">
						</div>
					</div>


					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractDate" class="col-sm-4 col-md-4 control-label">Work
							Order Date : &nbsp;<strong class='red'>*</strong>
						</label>
						<div class="col-sm-8 col-md-8">
							<input class="form-control" type="text" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" id="contractDate"
								value="" name="contractDate" />
						</div>
					</div>
				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12">
						<button type="button"
							style="margin-right: 6px; border-radius: 6px;" id="reports"
							class="width-10 pull-right btn btn-lg btn-primary">Reports
						</button>

						<button type="button" id="asbuiltbtn"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-right btn btn-lg btn-success" disabled>As
							Built</button>
					</div>
				</div>

			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>

<%-- <script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/asBuiltForm.js"></script> --%>

<script type="text/javascript">
	//Modified by: Shimul

	function contractorInformation(event) {
		//alert(event.selectedOptions[0].value);

		var contractorPk = event.selectedOptions[0].value;
		var baseURL = $('#contextPath').val();

		var params = {
			id : contractorPk
		}

		var jsonData = JSON.stringify(params);

		$
				.ajax({
					url : baseURL + "/co/getContractorInfo.do",
					data : jsonData,
					contentType : "application/json",
					success : function(response) {
						var data = JSON.parse(response);

						var init = "<option value='0' selected disabled>Select a Job</option>";
						$('#jobNo > option').remove();
						$('#jobNo').append(init);

						if (data == 'Fail') {
							alert('Sorry something goes wrong');
							$('#contractNo').val('');
							$('#contractDate').val('');
						} else {
							$('#contractNo').val(data.contractNo);
							var d = new Date(data.contractDate);
							var datestring = d.getDate()  + "-" + (d.getMonth()+1) + "-" + d.getFullYear();
							$('#contractDate').val(datestring);
							/* $('#contractDate').val(data.contractDate); */
							//$('#indenterStatus').val( data[0]. );
							for ( var ii in data.pndJobMstList) {
								var optn = "<option value="+data.pndJobMstList[ii].id+">"
										+ data.pndJobMstList[ii].jobNo
										+ "</option>";
								$('#jobNo').append(optn);
							}

							if (data.pndJobMstList.length == 0) {
								$('#forcefullyCloseOut')
										.prop('disabled', false);
								$("#contractorClosed").prop('disabled', false);
							} else {
								$('#forcefullyCloseOut').prop('disabled', true);
								$("#contractorClosed").prop('disabled', true);
							}
						}
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
	}

	$(document)
			.ready(
					function() {

						$('#jobNo').change(
								function() {
									$('#jobName').val(
											$('option:selected', '#jobNo')
													.text());
									$('#asbuiltbtn').prop('disabled', false);

								});

						$('#asbuiltbtn')
								.click(
										function() {
											var haserror = false;

											if ($('#identerDesignation').val() == null
													|| $
															.trim($(
																	'#identerDesignation')
																	.val()) == '0') {
												$('.identerDesignation')
														.removeClass('hide');
												haserror = true;
											} else {
												$('.identerDesignation')
														.addClass('hide');
											}

											if ($('#jobNo').val() == null
													|| $
															.trim($('#jobNo')
																	.val()) == '0') {
												$('.jobNo').removeClass('hide');
												haserror = true;
											} else {
												$('.jobNo').addClass('hide');
											}

											if (haserror == true) {
												return;
											} else {
												$('form').submit();
											}
										});

						//forcefullyCloseOut click
						$('#forcefullyCloseOut')
								.click(
										function() {
											var haserror = false;
											if ($('#remarks').val() == null
													|| $.trim($('#remarks')
															.val()) == '') {
												$('.remarks').removeClass(
														'hide');
												haserror = true;
											} else {
												$('.remarks').addClass('hide');
											}

											if (haserror == true) {
												return;
											} else {
												$('form').submit();
											}
										}); //forcefullyCloseOut click

						//added by: Shimul
						$('#reports').click(
								function() {
									var haserror = false;

									if ($('#identerDesignation').val() == null
											|| $.trim($('#identerDesignation')
													.val()) == '0') {
										$('.identerDesignation').removeClass(
												'hide');
										haserror = true;
									} else {
										$('.identerDesignation').addClass(
												'hide');
									}

									if (haserror == true) {
										return;
									} else {
										var id = $('option:selected',
												'#identerDesignation').val();
										var baseURL = $('#contextPath').val();
										var path = baseURL
												+ "/co/contractor/reports.do";
										window.open(path + "?id=" + id,
												"_blank");
									}
								}); //reports
					});

	function contractorClosed(event) {
		var id = $("#identerDesignation").val();
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/co/contractor/closeOut.do";

		var params = {
			'id' : id
		}
		postSubmit(path, params, "POST");
	}

	function forcefullyCloseOut(event) {
		var forcefullyCORemarks = $("#remarks").val();
		var id = $("#identerDesignation").val();
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/co/contractor/forcefullyCloseOut.do";

		var params = {
			'id' : id,
			'forcefullyCORemarks' : forcefullyCORemarks
		}
		postSubmit(path, params, "POST");
	}
</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>