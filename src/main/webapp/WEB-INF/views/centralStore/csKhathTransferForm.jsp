<%@include file="../common/csHeader.jsp"%>

<!--Start ||  Location Grid CSS -->
<style type="text/css">
.ui-widget-overlay {    
    opacity: .6!important;   
}
</style>
<!-- End || Location Grid CSS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Project Transfer List
			</a>
		</div>

		<h1 class="center blue" style="margin-top: 0; font-style:italic; 
		font-family: 'Ubuntu Condensed', sans-serif;">Project Transfer Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="khathTransferForm"
			action="${pageContext.request.contextPath}/cs/khath/saveKhathTransfer.do">
			<input type="hidden" name="contextPath" id="contextPath" value="${pageContext.request.contextPath}">
			<input type="hidden" name="uuid" id="uuid" value="${uuid}" />
			<input type="hidden" id="locationList" value=${locationList} />
			
			
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="khathIdFrom" class="col-sm-4 control-label">Project Name (From)&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<select class="form-control" name="khathIdFrom" id="khathIdFrom" TABINDEX=1
							style="border: 0; border-bottom: 2px ridge;">
								<!-- <option value="">Select Project</option> -->
								<c:if test="${!empty khathList}">
									<c:forEach items="${khathList}" var="khath">
										<option value="${khath.id}">
											<c:out value="${khath.khathName}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="ledgerNameFrom" class="col-sm-4 control-label">Ledger Name(From)&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<select class="form-control" name="ledgerBook" id="ledgerNameFrom"
							style="border: 0; border-bottom: 2px ridge;">
								<!--<option value="">Select Ledger</option> -->
								<c:if test="${!empty ledgerBooks}">
									<c:forEach items="${ledgerBooks}" var="ledgerBook">
										<option value="${ledgerBook}">
											<c:out value="${ledgerBook}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">					
					<div class="form-group">
						<label for="khathIdTo"
							class="col-sm-4 col-md-4 control-label align-right">Project Name (To)&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							 <select class="form-control" name="khathIdTo" id="khathIdTo" TABINDEX=2
							 style="border: 0; border-bottom: 2px ridge;">
								<!-- <option value="">Select Project</option> -->
								<c:if test="${!empty khathList}">
									<c:forEach items="${khathList}" var="khath">
										<option value="${khath.id}">
											<c:out value="${khath.khathName}" /></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="transferApprovalDoc"
							class="col-sm-4 col-md-4 control-label align-right">Upload Approval
							:</label>
						<div class="col-sm-8 col-md-8">
							<input type="file" class="form-control"
								id="transferApprovalDoc" name="transferApprovalDoc" />
						</div>
					</div>
					
				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Transfer	Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1600px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>Category&nbsp;<strong class='red'>*</strong></b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Name&nbsp;<strong class='red'>*</strong></b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Present Qty</b>
										</div>
										
										<div class="form-group col-sm-1 col-xs-12">
											<b>Transfer Qty&nbsp;<strong class='red'>*</strong></b>
										</div>
										
										<div class="form-group col-sm-1 col-xs-12">
											<b>Set Location</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Remaining Qty</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12 hide">
											<b>Total Cost</b>
										</div>									
										<div class="form-group col-sm-1 col-xs-12">
											<b>Remarks</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Add More</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-sm-1 col-xs-12">																	
																	<select class="form-control category" required
																		onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected value="">Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																	
																</div>

																<div class="form-group col-sm-1 col-xs-12">	
																	<input type="hidden" name="itemName"
																		class="itemDescription" />																
																	<select required
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
																	<input class="form-control presentQty" readonly="readonly" 
																		name="presentQty" id="presentQty0" type="number"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control transferedQty" required
																		onkeyup="validateTransferQty(this)" readonly="readonly"
																		name="transferedQty" id="transferedQty0" type="number"
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																	<strong class="text-danger hide errorMsg" id="errorMsg0">This field is required</strong>
																</div>
																
																<div class="form-group col-sm-1 col-xs-12 locationDiv">
																	<a href="#" class="btn btn-primary" id="setDialog0">
																					<i class="glyphicon glyphicon-edit"  onclick="openGridDialog(this)"
																					aria-hidden="true"> </i>
																	</a>
<!-- 																	  <a href="javascript:MyFunction();" onclick="openDialog1(this)" >Click</a>
 -->																	
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control remainQty"
																		name="remainQty" id="remainQty0" type="number"
																		readonly="readonly" value="0" 
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="unitCost" type="text" id="unitCost0"
																		placeholder="Unit Cost" onkeyup="getTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12 hide">
																	<input class="form-control" name="totalCost" readonly="readonly" 
																		type="text" placeholder="Total Cost" id="totalCost0"
																		style="border: 0; border-bottom: 2px ridge;" />
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

<!-- -------------------------- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	 </form>
</div>
<!-- -------------------------- -->

<script>
	//Added by: Ihteshamul Alam
	$(document).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false; var counter = 0;
			
			$('.transferedQty').each( function() {
				var id = this.id;
				var name = this.name;
				var sequence = id.substr( name.length );
				
				var transferedQty = $('#transferedQty'+sequence).val();
				var presentQty = $('#presentQty'+sequence).val();
				
				if( $.trim( $('#presentQty'+sequence).val() ) == '' || $.trim( $('#presentQty'+sequence).val() ) == '0' || $.trim( $('#presentQty'+sequence).val() ) == '0.0' || $.trim( $('#presentQty'+sequence).val() ) == '0.00' || $.trim( $('#presentQty'+sequence).val() ) == '0.000' ) {
					counter++;
					$('#errorMsg'+sequence).removeClass('hide').text('This field is required');
					
				} else if( presentQty < transferedQty ) {
					counter++;
					$('#errorMsg'+sequence).removeClass('hide').text('Transfer Qty. can\'t greater than Present Qty.');
				} else {
					$('#errorMsg'+sequence).addClass('hide');
				}
				
			}); //jQuery each
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('#khathTransferForm').submit();
			}
		});
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csKhathTransferForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>