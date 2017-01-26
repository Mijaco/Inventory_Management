<%@include file="../../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
			Assign Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/job/assignSave.do">
			<div class="oe_title">
			<!-- -------start--------- -->
				<div class="col-md-6">
					<div class="form-group">
						<label for="woNumber" class="col-sm-4 control-label">Work
							Order No:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							 
							<select class="form-control category" name="woNumber" onchange="contracotrNameChange(this)"
								id="woNumber" style="border: 0; border-bottom: 2px ridge;" required="required">
								<c:if test="${!empty contractorList}">	
								<option value="">--select an work order</option>								
									<c:forEach items="${contractorList}" var="contractor">									
										<c:if test="${contractor.contractorType == 'PROJECT'}">
											<option data-name="${contractor.contractorName}" value="${contractor.id}">${contractor.contractNo}</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select>

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
 
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Applicant Name
							&amp; Address:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="name"
								value="${costEstimationMst.name.concat(', ').concat(costEstimationMst.address)}"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								readonly="readonly" name="name" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="typeOfScheme" class="col-sm-4 control-label">Type
							Of Scheme:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="typeOfScheme"
								value="${costEstimationMst.typeOfScheme}" placeholder=""
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								name="typeOfScheme" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="jobTitle" class="col-sm-4 control-label">Job
							No:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="jobNo" value=""
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="jobNo" onblur="checkJobNo()">
							<h5 class="text-danger jobNo hide"><strong>Job no. is already used</strong></h5>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="pdNo" class="col-sm-4 control-label">Project/P&amp;D Ref. :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="pdNo" value=""
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="pdNo" />
							<h5 class="text-danger pdNo hide"><strong>Project/P&amp;D Ref. is already used</strong></h5>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
				</div>
				
				<!-- ---------------------------- -->

				<div class="col-md-6">				
 
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Contractor Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<!-- <input type="text" class="form-control" id="contractorName"
							style="border: 0; border-bottom: 2px ridge;"
								readonly="readonly"/> -->
							<select class="form-control" id="contractorName"
							style="border: 0; border-bottom: 2px ridge;">
								<option value="0" selected="selected" disabled="disabled">Select Contractor</option>
							</select>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="templateName" class="col-sm-4 control-label">Cost Estimation No. :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="templateName"
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								value="${costEstimationMst.pndNo}" name="pndNo" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="ConstructionNature" class="col-sm-4 control-label">Construction
							Nature:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<select class="form-control category" name="ConstructionNature"
								id="ConstructionNature" style="border: 0; border-bottom: 2px ridge;">
								<option value="0">Select Construction Nature</option>
								<c:if test="${!empty constructionNature}">
									<c:forEach items="${constructionNature}" var="construction">
										<option value="${construction.name}">${construction.name}</option>
									</c:forEach>
								</c:if>
								<option value="createnew">Add New Construction Nature</option>
							</select>
						</div>
					</div>
					<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="lut" class="col-sm-4 control-label">Lot :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="lut" value=""
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="lot" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					 
					<div class="form-group">
						<label for="remarks" class="col-sm-4 control-label">Remarks
							:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="remarks" value=""
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="remarks" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
				</div>
				<!-- ---------end------------- -->
				
				<c:if test="${!empty JobTemplateDtlList}">
					<div class="col-md-12 col-sm-12">
						<div class="form-group" style="margin-top: 1em;">
							<label for="fax" class="col-sm-4 control-label"
								style="font-style: italic; font-weight: bold;">Material
								List:</label>

							<div class="col-xs-12 table-responsive">
								<div class="table">
									<div style="width: 1300px;">
										<hr />
										<div class="col-xs-12">

											<div class="form-group col-sm-2 col-xs-12 bg-success">
												<b>Item Name</b>
											</div>
											<div class="form-group col-sm-2 col-xs-12 bg-success">
												<b>Item Code</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12 bg-success">
												<b>Unit</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12 bg-success">
												<b>Quantity</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12 bg-success">
												<b>Unit Cost</b>
											</div>
											<div class="form-group col-sm-2 col-xs-12 bg-success">
												<b>Total Cost</b>
											</div>
											<div class="form-group col-sm-3 col-xs-12 bg-success">
												<b>Remarks</b>
											</div>
										</div>

										<div class="col-xs-12">
											<c:forEach items="${JobTemplateDtlList}" var="pndJob"
												varStatus="loop">
												<div class="row">
													<div class="control-group" id="fields">
														<div class="controls">
															<div class="aaa">
																<!-- <form role="form" autocomplete="off">  -->
																<div class="col-xs-12 entry" id="myArea0">
																	<div class="row">
																		<div class="form-group col-sm-2 col-xs-12">
																			<input type="text" name="itemName"
																				style="border: 0; border-bottom: 2px ridge;"
																				readonly="readonly" class="form-control itemName"
																				value="${pndJob.itemName}" />
																		</div>
																		<div class="form-group col-sm-2 col-xs-12">
																			<input class="form-control itemCode" name="itemCode"
																				type="text" placeholder="itemCode"
																				readonly="readonly"
																				style="border: 0; border-bottom: 2px ridge;"
																				value="${pndJob.itemCode}" />
																		</div>
																		<div class="form-group col-sm-1 col-xs-12">
																			<input class="form-control uom" name="uom"
																				type="text" placeholder="Unit" readonly="readonly"
																				style="border: 0; border-bottom: 2px ridge;"
																				value="${pndJob.uom}" />
																		</div>
																		<div class="form-group col-sm-1 col-xs-12">
																			<input class="form-control quantity" name="quantity"
																				type="text" readonly="readonly" value="${pndJob.quantity}"
																				style="border: 0; border-bottom: 2px ridge;" />
																		</div>
																		<div class="form-group col-sm-1 col-xs-12">
																			<input class="form-control unitCost" name="unitCost"
																				type="text" readonly="readonly" value="${pndJob.unitPrice}"
																				style="border: 0; border-bottom: 2px ridge;" />
																		</div>
																		<div class="form-group col-sm-2 col-xs-12">
																			<input class="form-control totalCost"
																				name="totalCost" type="text" readonly="readonly"
																				value="${pndJob.totalPrice}"
																				style="border: 0; border-bottom: 2px ridge;" />
																		</div>
																		<div class="form-group col-sm-3 col-xs-12">
																			<input type="text" class="form-control" id="remarks"
																				style="border: 0; border-bottom: 2px ridge;"
																				readonly="readonly" value="${pndJob.remarks}"
																				name="remark" />
																		</div>
																	</div>

																</div>
															</div>
															<!-- ---------------------- -->

														</div>
													</div>
												</div>
											</c:forEach>
										</div>

										<hr />

									</div>
								</div>
							</div>

						</div>

					</div>
				</c:if>


				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton" disabled
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

<script>

	function checkJobNo() {
		var jobNo = $('#jobNo').val();
		var baseURL = $('#contextPath').val();
		var saveButton = $('#saveButton');
		
		$.ajax({
			url : baseURL + "/job/checkJobNo.do",
			data : {"jobNo":jobNo},
			success : function(data) {
				
				if( data == "success" ) {
					if( $('.jobNo').hasClass('hide') == false ) {
						$('.jobNo').addClass('hide');
					}
					saveButton.prop("disabled", false);
					/* if( isValidPdNo() == true ) {
						saveButton.prop("disabled", false);
					} */
					
				} else if( data == 'invalid' ) {
					$('.jobNo').removeClass('hide').html('<strong>Invalid Job No.</strong>');
					saveButton.prop("disabled", true);
				}
				else {
					$('.jobNo').removeClass('hide').html('<strong>Job no. is already used</strong>');
					saveButton.prop("disabled", true);
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	function checkPdNo() {
		var pdNo = $('#pdNo').val();
		var baseURL = $('#contextPath').val();
		var saveButton = $('#saveButton');
		
		$.ajax({
			url : baseURL + "/job/checkPdNo.do",
			data : {"pdNo":pdNo},
			success : function(data) {
				if( data == "success" ) {
					if( $('.pdNo').hasClass('hide') == false ) {
						$('.pdNo').addClass('hide');
					}
					
					if( isValidJobNo() == true ) {
						saveButton.prop("disabled", false);
					}
					
				} else if( data == 'invalid' ) {
					$('.pdNo').removeClass('hide').html('<strong>Invalid Project/P&amp;D Ref. No.</strong>');
					saveButton.prop("disabled", true);
				}
				else {
					$('.pdNo').removeClass('hide').html('<strong>Project/P&amp;D Ref. is already used</strong>');
					saveButton.prop("disabled", true);
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	function isValidJobNo() {
		var jobNo = $('#jobNo').val();
		var baseURL = $('#contextPath').val();
		var saveButton = $('#saveButton');
		
		$.ajax({
			url : baseURL + "/job/checkJobNo.do",
			data : {"jobNo":jobNo},
			success : function(data) {
				if( data == "success" ) {
					return true;
				} else {
					return false;
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	function isValidPdNo() {
		var pdNo = $('#pdNo').val();
		var baseURL = $('#contextPath').val();
		var saveButton = $('#saveButton');
		
		$.ajax({
			url : baseURL + "/job/checkPdNo.do",
			data : {"pdNo":pdNo},
			success : function(data) {
				if( data == "success" ) {
					return true;
				} else {
					return false;
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	function contracotrNameChange(contractorName){		
		//$("#contractorName").val($("#woNumber").find(':selected').attr('data-name'));
		$('#contractorName').empty().append($('<option>', {
		    value: $("#woNumber").find(':selected').attr('data-name'),
		    text: $("#woNumber").find(':selected').attr('data-name')
		}));
	}

</script>

<%-- <script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/jobAssignForm.js"></script> --%>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>