<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ss/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip Form</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>
			
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="ssCsReturnSlipForm"
			action="${pageContext.request.contextPath}/ss/returnSlip/Save.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="receiveFrom" class="col-sm-4 control-label">
							Received From:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receiveFrom"
								value="Executive Engineer" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="receiveFrom" />
								<input type="hidden" value="${uuid}" id="uuid"  name="uuid"/>
								<input type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}" />
								<input type="hidden" value="${locationList}" id="locationList" />
								<input type="hidden" value="${ledgerBookList}" id="ledgerBookList" />
						</div>
					</div>
				</div>
				
				<input type="hidden" class="form-control" id="zone"	name="zone">

				<div class="col-md-6 col-sm-6">					
					<div class="form-group">
						<label for="returnFor"
							class="col-sm-4 col-md-4 control-label align-right">Project Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control category"
								name="khathId" id="khathId"
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
					</div>					
				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Return Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 2000px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Stock Quantity (NS)</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Stock Quantity (RS)</b>
										</div>
										
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Set Location and Qty</b>
										</div> -->

										<div class="form-group col-sm-1 col-xs-12">
											<b>Return Quantity</b>
											<br/> (New Serviceable) 
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Return Quantity</b>
											<br/> (Recovery Serviceable) 
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Return Quantity</b>
											<br/> (UnServiceable) 
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Return&nbsp;<strong class='red'>*</strong></b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
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
																	<select class="form-control category"
																		onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input type="hidden" name="description"
																		class="description" /> <select
																		class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode"
																		type="text" placeholder="itemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control nscurrentStock" name="nscurrentStock" type="text"
																		placeholder="0" readonly="readonly" id="nscurrentStock0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control rscurrentStock" name="rscurrentStock" type="text"
																		placeholder="0" readonly="readonly" id="rscurrentStock0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<!-- <div class="form-group col-sm-1 col-xs-12 action-buttons"  data-itemcode="">																	
																	<a href="#" class="btn btn-primary" id="setDialog0">
																			<i class="glyphicon glyphicon-edit"  onclick="openGridDialog(this)"
																			aria-hidden="true"> </i>
																	</a>
																</div> -->

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control qtyNewServiceable" onblur="setTotalReturn(this)"																	
																		name="qtyNewServiceable" id="qtyNewServiceable0"
																		type="number" value="0" step="0.001" value="0" 
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control qtyRecServiceable" onblur="setTotalReturn(this)"	
																		name="qtyRecServiceable" id="qtyRecServiceable0"
																		type="number" step="0.001"  																	
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control qtyUnServiceable" name="qtyUnServiceable" onblur="setTotalReturn(this)"
																		type="number" id="qtyUnServiceable0" step="0.001"
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control totalReturn" name="totalReturn" value="0"
																		type="number" id="totalReturn0" readonly="readonly"
																		step="0.001" style="border: 0; border-bottom: 2px ridge;" />
																	<strong class="errTotRet text-danger hide" id="errTotRet0">Total return can't 0</strong>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
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

<!-- ------Start of Location Grid Div ----- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	 </form>
</div>
<!-- ------End of Location Grid Div ----- -->

<script>
	$( document ).ready( function() {
		$('#saveButton').click( function() {
			var counter = 0, haserror = false;
			
			$('.totalReturn').each( function() {
				if( $( this ).val() == null || $.trim( $( this ).val() ) == '' || $.trim( $( this ).val() ) == '0' || $.trim( $( this ).val() ) == '0.0' || $.trim( $( this ).val() ) == '0.00' || $.trim( $( this ).val() ) == '0.000' ) {
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					
					$('#errTotRet'+sequence).removeClass('hide');
					
					counter++;
				} else {
					
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					
					$('#errTotRet'+sequence).addClass('hide');
				}
			});
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$( '#ssCsReturnSlipForm' ).submit();
			}
		});
	}); 
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/subStore/ssToCsReturnSlipForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>