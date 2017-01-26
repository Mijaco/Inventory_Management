<%@include file="../../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->
<style>
textarea {
	resize:none;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
			Close Out</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="identerDesignation" style="display: block;"
						class="col-sm-3 control-label align-right">Indenter :
						&nbsp;<strong class='red'>*</strong>
					</label>
					<div class="col-sm-7">
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
			</div>

			<div class="col-md-12 col-sm-12">

				<div class="col-sm-10">&nbsp;&nbsp;</div>

				<div class="form-group">
					<label for="contractNo" style="display: block;"
						class="align-right col-sm-3 col-md-3 control-label">Work
						Order No : &nbsp;<strong class='red'>*</strong>
					</label>
					<div class="col-sm-7 col-md-7">
						<input class="form-control" type="text" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" id="contractNo"
							value="" name="contractNo" />
					</div>
				</div>
			</div>

			<div class="col-md-12 col-sm-12">

				<div class="col-sm-10">&nbsp;&nbsp;</div>

				<div class="form-group">
					<label for="contractDate" style="display: block;"
						class="col-sm-3 col-md-3 align-right control-label">Work
						Order Date : &nbsp;<strong class='red'>*</strong>
					</label>
					<div class="col-sm-7 col-md-7">
						<input class="form-control" type="text" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" id="contractDate"
							value="" name="contractDate" />
					</div>
				</div>
			</div>

			<div class="col-md-12 col-sm-12" align="center"
				style="margin-top: 15px;">
				<button type="button" style="margin-right: 6px; border-radius: 6px;"
					id="reports" class="btn btn-primary">Reports</button>

				<button type="button" onclick="contractorClosed(this)"
					style="margin-right: 6px; border-radius: 6px;"
					id="contractorClosed" class="btn btn-warning" disabled>
					<i class="fa fa-fw fa-power-off"></i>&nbsp; Close Out
				</button>

				<button class="btn btn-danger" id="forcefullyCloseOut"
					style="border-radius: 6px;" data-toggle="modal" disabled
					data-target=".bs-example-modal-sm" data-backdrop="static"
					data-keyboard="false" aria-hidden="true" style="display: none">
					<i class="fa fa-fw fa-times-circle-o"></i>&nbsp;Forcefully Close
					Out
				</button>
				<!-- <button type="button" onclick="forcefullyCloseOut(this)"
						style="margin-right: 6px; border-radius: 6px;"
						id="forcefullyCloseOut"
						class="btn btn-lg btn-warning" disabled>Forcefully
						Close Out</button> -->
			</div>
		</div>
		<!-- --------------------- -->
	</div>

	<!-- -------Modal Start--------- -->
	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Forcefully Close Out Contractor</h4>
				</div>

				<div class="modal-body">
					<form class="form-horizontal" method="POST"
						enctype="multipart/form-data" role="form"
						action="${pageContext.request.contextPath}/co/contractor/forcefullyCloseOut.do">
						<input type="hidden" name="id" id="contracotrPrimaryId" value="">
						<div class="form-group">
							<label class="col-sm-4 align-right control-label"
								for="reasionCloseOut">Reason for Forcefully Close Out
								&nbsp;<strong class="red">*</strong> :
							</label>
							<div class="col-sm-6">
								<textarea name="reasionCloseOut" id="reasionCloseOut"
									class="form-control"></textarea>
								<h5 class="text-danger reasionCloseOut hide">
									<strong>This Field is Required</strong>
								</h5>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 align-right control-label"
								for="paymentDetails">Payment Details &nbsp;<strong
								class="red">*</strong> :
							</label>
							<div class="col-sm-6">
								<textarea name="paymentDetails" id="paymentDetails"
									class="form-control"></textarea>
								<h5 class="text-danger paymentDetails hide">
									<strong>This Field is Required</strong>
								</h5>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-4 align-right control-label"
								for="closeOutInfo">Related Document(s) :</label>
							<div class="col-sm-6">
								<input type="file" class="form-control" id="closeOutInfo"
									name="closeOutInfo">
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-12" align="center">
								<button id="forcelyCloseOutContractor" type="button"
									class="btn btn-primary">
									<i class="fa fa-fw fa-power-off"></i>&nbsp;Close Out
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- -------Modal End--------- -->

</div>

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
							$('#contracotrPrimaryId').val(data.id);
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

						$('#forcelyCloseOutContractor').click(
								function() {
									var haserror = false;

									var paymentDetails = $("#paymentDetails")
											.val();
									var reasionCloseOut = $("#reasionCloseOut")
											.val();

									if (paymentDetails == null
											|| $.trim(paymentDetails) == '') {
										$('.paymentDetails')
												.removeClass('hide');
										haserror = true;
									} else {
										$('.paymentDetails').addClass('hide');
									}

									if (reasionCloseOut == null
											|| $.trim(reasionCloseOut) == '') {
										$('.reasionCloseOut').removeClass(
												'hide');
										haserror = true;
									} else {
										$('.reasionCloseOut').addClass('hide');
									}

									if (haserror == true) {
										return;
									} else {
										$('form').submit();
									}
								});
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
</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>