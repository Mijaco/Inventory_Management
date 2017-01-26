<%@include file="../common/ssHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.css">

<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ss/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition</h1>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="ssCsRequisitionForm"
			action="${pageContext.request.contextPath}/ss/storeRequisitionSave.do">
			<input type="hidden" id="identerDesignation"
				value="Executive Engineer" name="identerDesignation" />
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Requisition
							To:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">XEN, Central Store</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="receivedBy" class="col-sm-4 control-label">Receiver
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy" required="required">
							<strong class="receivedBy text-danger hide">This field is required</strong>
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="khathId"
							class="col-sm-4 col-md-4 control-label align-right">Project
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control category" name="khathId" id="khathId"
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

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="carriedBy" class="col-sm-4 control-label align-right">Carried
							By: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="carriedBy"
								style="border: 0; border-bottom: 2px ridge;" name="carriedBy" />
						</div>
					</div>

					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" />
					</div>

				</div>
				<input type="hidden" name="requisitionTo" value="cs" /> <input
					type="hidden" id="loadauthList"> <input type="hidden"
					id="contextPath" value="${pageContext.request.contextPath}">

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1800px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-xs-2">
											<b>Category</b>
										</div>
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Ledger Name</b>
										</div> -->
										<div class="form-group col-xs-2">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>CS Quantity</b>
										</div>

										<div class="form-group col-xs-2">
											<b>Required Qty&nbsp;<strong class='red'>*</strong></b>
										</div>

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Issued Qty</b>
										</div> -->

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Cost</b>
										</div> -->
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

																<div class="form-group col-xs-2">
																	<select class="form-control category"
																		onchange="categoryLeaveChange(this)"
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
																<%-- <div class="form-group col-sm-1 col-xs-12">
																	<select class="form-control ledgerName" name="ledgerName" id="ledgerName0"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Select One</option>
																		<c:if test="${!empty ledgerBooks}">
																			<c:forEach items="${ledgerBooks}" var="ledgerBook">
																				<option value="${ledgerBook}">
																					<c:out value="${ledgerBook}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div> --%>
																<div class="form-group col-xs-2">
																	<input type="hidden" name="itemName"
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
																	<input class="form-control currentStock"
																		name="currentStock" id="currentStock0" type="number"
																		placeholder="0" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired"
																		onblur="reqQtyNotGreaterThenCurrentStock(this)"
																		name="quantityRequired" id="quantityRequired0"
																		type="number" value="" step="0.001" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																	<strong class="text-danger hide errorMsg" id="errorMsg0">This field is required</strong>	
																</div>

																<!-- <div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityIssued"
																		name="quantityIssued" id="quantityIssued0"
																		type="number" placeholder="0" readonly="readonly"
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																</div> -->

																<!-- <input class="form-control" name="unitCost"
																		type="hidden" id="unitCost0"
																		onblur="setTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" /> 
																
																
																	<input class="form-control" name="totalCost"
																		type="hidden" id="totalCost0" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" /> -->

																<div class="form-group col-xs-2">
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

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.js"></script>

<script>
	$(document).ready(function() {
		//Added by: Ihteshamul Alam, IBCS
		var contextPath = $('#contextPath').val();

		var userList = [];
		var path = contextPath + "/ls/loadAuthUserList.do";

		$('#loadauthList').load(path, {}, function(d) {
			var gap = jQuery.parseJSON(d);
			for ( var ii in gap) {
				var data = gap[ii].name + " (" + gap[ii].userid + ")";
				userList.push(data);
			}
		});

		//Added by: Ihteshamul Alam, IBCS
		$('#receivedBy').autocomplete({
			source : userList,
			response : function(event, ui) {
				// ui.content is the array that's about to be sent to the response callback.
				if (ui.content.length === 0) {
					$("#empty-message").text("No results found");
				} else {
					$("#empty-message").empty();
				}
			}
		}); //autocomplete
		
		//
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( $('#receivedBy').val() == null || $.trim( $('#receivedBy').val() ) == '' ) {
				$('.receivedBy').removeClass('hide');
				haserror = true;
			} else {
				$('.receivedBy').addClass('hide');
			}
			
			var counter = 0;
			
			$('.quantityRequired').each( function() {
				var id = this.id;
				var name = this.name;
				var sequence = id.substr( name.length );
				var currentStock = $('#currentStock'+sequence).val();
				var requiredQty = $('#quantityRequired'+sequence).val();
				
				if( requiredQty == null || $.trim( requiredQty ) == '' || $.trim( requiredQty ) == '0' || $.trim( requiredQty ) == '0.0' || $.trim( requiredQty ) == '0.00' || $.trim( requiredQty ) == '0.000' ) {
					$('#errorMsg'+sequence).removeClass('hide').text('This field is required');
					counter++;
				} else if( ( currentStock == '' || currentStock == '0' || currentStock == '0.0' || currentStock == '0.00' || currentStock == '0.000' ) || ( requiredQty > currentStock ) ) {
					$('#errorMsg'+sequence).removeClass('hide').text('Required qty can\'t greater than current stock');
					counter++;
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
				$('#ssCsRequisitionForm').submit();
			}
		});
	});
</script>


<script
	src="${pageContext.request.contextPath}/resources/assets/js/subStore/ssToCsStoreRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>