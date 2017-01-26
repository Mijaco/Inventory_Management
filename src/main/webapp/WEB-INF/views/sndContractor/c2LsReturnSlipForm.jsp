<%@include file="../common/sndCnHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/c2ls/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip Form</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
			
<%-- 		<h6 class="center red"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${msg}</h6> --%>


	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px">
		<!-- --------------------- -->
		<form method="POST" id="c2lsReturnSlipSave"
			action="${pageContext.request.contextPath}/c2ls/returnSlip/Save.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="receiveFrom" class="col-sm-4 control-label">Please
							Received From:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receiveFrom"
								value="${contractorRep.contractor.contractorName}"
								style="border: 0; border-bottom: 2px ridge;" name="receiveFrom"
								readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="workOrderNo" class="col-sm-4 control-label">Work
							Order No:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workOrderNo"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="workOrderNo"
								value="${contractorRep.contractor.contractNo}"
								readonly="readonly" />
						</div>
					</div>
					<!-- <div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="zone" class="col-sm-4 control-label">Area/Zone
							:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="zone" placeholder=""
								style="border: 0; border-bottom: 2px ridge;" name="zone">
						</div>
					</div> -->
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="returnTo"
							class="col-sm-4 col-md-4 control-label align-right">Return
							To:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control returnTo" name="returnTo"
								style="border: 0; border-bottom: 2px ridge;">
								<!-- <option disabled selected>Category</option> -->
								<c:if test="${!empty deptIdList}">
									<c:forEach items="${deptIdList}" var="dept">
										<option value="${dept.deptId}">
											<c:out value="${dept.deptName}" /></option>
									</c:forEach>
								</c:if>
							</select>
							<!-- <select class="form-control requisitionTo" name="returnTo"	style="border: 0; border-bottom: 2px ridge;">
								<option value="ls">Local Store</option>
							</select> -->
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="workOrderDate"
							class="col-sm-4 col-md-4 control-label align-right">Work
							Order Date:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<label> <strong> <fmt:formatDate
										value="${contractorRep.contractor.contractDate}"
										pattern="dd-MM-yyyy" /></strong></label>
						</div>
					</div>
					<%-- <div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor"
							class="col-sm-4 col-md-4 control-label align-right">Khath
							Name :</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control category" name="khathId"
								style="border: 0; border-bottom: 2px ridge;">
								<!-- <option disabled selected>Category</option> -->
								<c:if test="${!empty descoKhathList}">
									<c:forEach items="${descoKhathList}" var="descoKhath">
										<option value="${descoKhath.id}">
											<c:out value="${descoKhath.khathName}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div> --%>
				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 2000px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Quantity Retrun</b>
										</div> -->

										<div class="form-group col-sm-1 col-xs-12">
											<b>New Serviceable</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Recovery Serviceable</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>UnServiceable</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Return&nbsp;<strong style='color: red;'>*</strong></b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">

																<div class="form-group col-sm-1 col-xs-12">
																	<select class="form-control category" id="category"
																		onchange="categoryLeaveChange(this)" name="category"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-sm-2 col-xs-12">
																	<input type="hidden" name="description"
																		class="description" /> <select id="itemName0"
																		name="itemName" class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode"
																		id="itemCode0" type="text" placeholder="itemCode"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" name="uom" type="text"
																		id="uom0" placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- <div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control currentStock"
																		name="qtyRetrun" id="currentStock0" type="number"
																		placeholder="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div> -->

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired"
																		name="qtyNewServiceable" id="qtyNewServiceable0"
																		type="number" value="0" step="0.001"
																		onblur="setTotalCost(this)" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityIssued"
																		name="qtyRecServiceable" id="qtyRecServiceable0"
																		type="number" placeholder="0" step="0.001"
																		onblur="setTotalCost(this)" value="0" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="qtyUnServiceable"
																		type="number" id="qtyUnServiceable0" step="0.001"
																		onblur="setTotalCost(this)" value="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control totalReturn" name="totalReturn"
																		value="0" type="number" id="totalReturn0" min="0.001"
																		readonly="readonly" step="0.001" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																	<h5 class="text-danger hide" id="errReturn0"><strong>Total Return can't 0</strong></h5>
																</div>
																<div class="form-group col-sm-2 col-xs-12">
																	<input class="form-control" name="remarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-3">

																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove" type="button">
																		<span class="glyphicon glyphicon-minus"></span>
																	</button>

																</div>
															</div>

														</div>
													</div>
													<!-- ---------------------- -->

												</div>
											</div>
										</div>
									</div>

									<hr />

								</div>
							</div>
						</div>

					</div>

				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button" id="saveButton"
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
	$( document ).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false, counter = 0;
			
			$('.totalReturn').each( function() {
				if( $(this).val() == null || $.trim( $(this).val() ) == '' || $.trim( $(this).val() ) == '0' || $.trim( $(this).val() ) == '0.0' || $.trim( $(this).val() ) == '0.00' || $.trim( $(this).val() ) == '0.000' ) {
					
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					
					$('#errReturn'+sequence).removeClass('hide');
					
					counter++;
				} else {
					
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					
					$('#errReturn'+sequence).addClass('hide');
				}
			}); // jQuery .each
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('#c2lsReturnSlipSave').submit();
			}
		}); // Save Button Click
	}); // jQuery
</script>


<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/cnToSndReturnSlipForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
