<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cn/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cn/returnSlip/Save.do">
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
				
					<div class="form-group">
						<label for="receiveFrom" class="col-sm-4 control-label">Please
							Received From: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receiveFrom"
								value="Executive Engineer"
								style="border: 0; border-bottom: 2px ridge;"
								name="receiveFrom" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="requisitionNo" class="col-sm-4 control-label">Work
							Order No:</label>
						<div class="col-sm-8">
						<div class="ui-widget">
							<input type="text" class="form-control" id="requisitionNo"
										placeholder="1234"
										style="border: 0; border-bottom: 2px ridge;"
										value="${SubStoreReqMst.requisitionNo}" name="requisitionNo" />
								</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
					<%-- <input type="text" id="contractNo" value="${contractNo}" name="contractNo" />  --%>
						
						<label for="zone" class="col-sm-4 control-label">Area/Zone :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="zone"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="zone">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

				 <div class="form-group">
						<label for="rsNo"
							class="col-sm-4 col-md-4 control-label align-right">Return
							Slip No:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="rsNo"
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								value="${rsNo}" />
						</div>
					</div> 
					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 col-md-4 control-label align-right">Return
							To:</label>
						<div class="col-sm-8 col-md-8">
							 <select class="form-control requisitionTo" name="returnTo"	style="border: 0; border-bottom: 2px ridge;">
								<option value="">Select</option>
								<option value="cs">Central Store</option>
								<option value="ss">Sub Store</option>
								<option value="ss">Local Store</option>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="workOrderDate"
							class="col-sm-4 col-md-4 control-label align-right">Work
							Order Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="workOrderDate" value="${SubStoreReqMst.requisitionDate}"
								style="border: 0; border-bottom: 2px ridge;"
								name="workOrderDate" />
						</div>
					</div>
				</div>

			<c:if test="${!empty OrderDtl}">

									<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						 <label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Return
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 2500px;">
									<hr />
									 <div class="col-xs-12">
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
											<b>Quantity Retrun</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Serviceable</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Repairable</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Non Repairable</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Received</b>
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

																	<c:forEach items="${OrderDtl}" var="orderDtl"
																		varStatus="loop">
																		<div class="row">
																		<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control itemName" name=itemName
																					type="text" placeholder="itemName"
																					readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.itemName}" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control itemCode" name="itemCode"
																					type="text" placeholder="itemCode"
																					readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.itemCode}" />
																			</div>
																			
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control uom" name="uom"
																					type="text" placeholder="Unit" readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.uom}" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control qtyRetrun"
																					name="qtyRetrun" id="qtyRetrun${loop.index}"
																					type="number"
																					style="border: 0; border-bottom: 2px ridge;"
																					value=""/>
																			</div>

																			
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired"
																		onblur="reqQtyNotGreaterThenCurrentStock(this)"
																		name="qtyServiceable" id="quantityRequired0"
																		type="number" value="0" step="0.01"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityIssued"
																		name="qtyRepairable" id="quantityIssued0"
																		type="number" placeholder="0"
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="qtyNonRepairable"
																		type="number" id="unitCost0"
																		onblur="setTotalCost(this)" value="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="totalReceived" value="0"
																		type="number" id="totalCost0" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control" name="remarks"
																					type="text" placeholder="Remarks" value="NA"
																					style="border: 0; border-bottom: 2px ridge;" />
																			</div>

																			
																		</div>
																		<c:set var="count" value="${loop.count}" scope="page" />
																	</c:forEach>
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
						<button type="submit"
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
</c:if> 
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>



<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/cnReturnSlipForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>