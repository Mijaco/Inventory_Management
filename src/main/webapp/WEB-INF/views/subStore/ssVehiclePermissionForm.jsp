<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/vehiclePermissionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span>
				Vehicle Permission List
			</a>

			<h1 class="center blue">
				<em>Vehicle Permission Form</em>
			</h1>

		</div>

	</div>

	<div class="container">
		<div class="row"
			style="background-color: white; padding: 10px; margin: 10px !important;">

			<!-- --------------------- -->
			<form method="POST"
				action="${pageContext.request.contextPath}/ss/saveVehiclePermission.do">
				<div class="oe_title">

					<div class="col-md-6 col-sm-6">

						<div class="col-sm-10">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="contractorName" class="col-sm-5 control-label">Contractor/S
								&amp; D Name&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="contractorName"
									placeholder="Contractor/S & D Name"
									style="border: 0; border-bottom: 2px ridge;"
									name="contractorName" />
							</div>
						</div>
						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="entryPurpose" class="col-sm-5 col-md-5 control-label">Purpose
								of Vehicle Entry&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control" id="entryPurpose"
									style="border: 0; border-bottom: 2px ridge;"
									name="entryPurpose" />
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="driverName" class="col-sm-5 control-label">Driver
								Name </label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="driverName"
									style="border: 0; border-bottom: 2px ridge;" name="driverName">
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="requisitionBy" class="col-sm-5 control-label">Requisition
								By </label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="requisitionBy"
									style="border: 0; border-bottom: 2px ridge;"
									name="requisitionBy">
							</div>
						</div>

					</div>

					<div class="col-md-6 col-sm-6">
						<!-- <div class="form-group">
						<label for="entryTime" class="col-sm-5 control-label">Vehicle Entry
							Time</label>
						<div class="col-sm-7">						
							<input type="datetime" class="form-control" id="entryTime"
							 	style="border: 0; border-bottom: 2px ridge;"
								name="entryTime">
						</div>
					</div> -->

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="vehicleType" class="col-sm-5 control-label">Vehicle
								Type&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="vehicleType"
									placeholder="Vehicle Type"
									style="border: 0; border-bottom: 2px ridge;" name="vehicleType">
							</div>
						</div>



						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="vehicleNumber"
								class="col-sm-5 col-md-5 control-label">Vehicle Number</label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control" id="vehicleNumber"
									style="border: 0; border-bottom: 2px ridge;"
									name="vehicleNumber" />
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="drivingLicenceNo"
								class="col-sm-5 col-md-5 control-label">Driving License
								Number</label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control" id="drivingLicenceNo"
									style="border: 0; border-bottom: 2px ridge;"
									name="drivingLicenceNo" />
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

						<div class="form-group">
							<label for="numberOfPerson"
								class="col-sm-5 col-md-5 control-label">Number Of Person</label>
							<div class="col-sm-7 col-md-7">
								<input type="text" class="form-control" id="numberOfPerson"
									style="border: 0; border-bottom: 2px ridge;"
									name="numberOfPerson" />
							</div>
						</div>

						<%-- <div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" />
					</div> --%>

					</div>

					<!-- 				<div class="col-md-12"> -->
					<!-- 					<div class="form-group" style="margin-top: 2em; margin-right: 1em;"> -->

					<!-- 						<button type="submit" class="width-20 pull-right btn btn-sm  -->
					<!-- 						btn-success"> -->
					<!-- 							<i class="ace-icon fa fa-save"></i>  -->
					<!-- 							<span class="bigger-50">Save</span> -->
					<!-- 						</button> -->

					<!-- 						<button type="reset" class="width-20 pull-right btn btn-sm"  -->
					<!-- 						style="margin-right: 10px;"> -->
					<!-- 							<i class="ace-icon fa fa-refresh"></i>  -->
					<!-- 							<span class="bigger-50">Reset</span> -->
					<!-- 						</button> -->

					<!-- 					</div> -->

					<!-- 				</div> -->

					<div class="col-md-12" style="padding-top: 15px;" align="center">
						<button type="button" style="border-radius: 6px;" id="saveButton"
							class="btn btn-lg btn-success">
							<i class="fa fa-save"></i>&nbsp;Save
						</button>

						<button type="reset" class="btn btn-lg btn-danger"
							style="border-radius: 6px; margin-left: 10px;">
							<i class="fa fa-refresh"></i>&nbsp;Reset
						</button>

					</div>

				</div>

				<script>
					$(function() {
						$(document)
								.on(
										'click',
										'.btn-add',
										function(e) {
											//e.preventDefault();

											var num = $('.clonedArea').length;
											var newNum = num + 1;

											var controlForm = $('.controls div:first'), currentEntry = $(
													this).parents(
													'.entry:first'), newEntry = $(
													currentEntry
															.clone()
															.attr(
																	'id',
																	'myArea'
																			+ newNum)
															.addClass(
																	'clonedArea'))
													.appendTo(controlForm);

											// set dynamic id on item qty fields

											var mainDiv = document
													.getElementById('myArea'
															+ newNum), childDiv = mainDiv
													.getElementsByTagName('div')[0];

											// start of seting id on expectedQty fields
											var expectedQtyDiv = childDiv
													.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
													.getElementsByTagName('input')[0];
											expectedQtyInput.setAttribute('id',
													'expectedQty' + newNum);
											// end of seting id on expectedQty fields

											// start of seting id on receivedQty fields
											var receivedQtyDiv = childDiv
													.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
													.getElementsByTagName('input')[0];
											receivedQtyInput.setAttribute('id',
													'receivedQty' + newNum);
											// end of seting id on receivedQty fields

											// start of seting id on remainingQty fields
											var remainingQtyDiv = childDiv
													.getElementsByTagName('div')[6], remainingQtyInput = remainingQtyDiv
													.getElementsByTagName('input')[0];
											remainingQtyInput.setAttribute(
													'id', 'remainingQty'
															+ newNum);
											// end of seting id on remainingQty fields

											newEntry.find('input').val('');

											controlForm
													.find(
															'.entry:not(:last) .btn-add')
													.removeClass('btn-add')
													.addClass('btn-remove')
													.removeClass('btn-success')
													.addClass('btn-danger')
													.html(
															'<span class="glyphicon glyphicon-minus"></span>');
										}).on(
										'click',
										'.btn-remove',
										function(e) {
											$(this).parents('.entry:first')
													.remove();

											//e.preventDefault();
											return false;
										});

					});
				</script>
			</form>
			<!-- --------------------- -->
		</div>
	</div>

</div>


<script>
	$( document ).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('form').submit();
			}
		});
	});
</script>



<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>