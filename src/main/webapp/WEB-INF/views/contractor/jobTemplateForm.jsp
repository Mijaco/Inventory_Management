<%@include file="../common/cnPndHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/template/jobEstimationList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Cost
				Estimate List
			</a>
		</div>

		<h1 class="center blue" style="margin-top: 0; font-style:italic; font-family: 'Ubuntu Condensed', sans-serif;">Cost Estimation Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/template/jobTemplateSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<!-- <div class="form-group">
						<label for="estimateNo" class="col-sm-4 control-label">Estimate No:
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="pndNo"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="pndNo" />
						</div>
					</div> -->
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Applicant Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="name"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="name">
						</div>
					</div>
				
				<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="serviceCharge" class="col-sm-4 control-label">Service Charge:&nbsp;<strong class='red'>*</strong>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(%)</label>
						<div class="col-sm-8">
							<input type="number" class="form-control" id="serviceCharge" required="required"
								placeholder="20" style="border: 0; border-bottom: 2px ridge;" step="0.01"
								name="serviceChargePercent" min="0">
						</div>
					</div>
				</div>
					<div class="col-md-6 col-sm-6">
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="address" class="col-sm-4 control-label">Applicant Address
							:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="address"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="address">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
					<label for="typeOfScheme" class="col-sm-4 control-label">Type Of Scheme:&nbsp;<strong class='red'>*</strong></label>
					<div class="col-sm-8">
						<select class="form-control" id="typeOfScheme"
								name="typeOfScheme"
								style="border: 0; border-bottom: 2px ridge;">								
										<option value="deposit">Deposit</option>						
										<option value="development">Development</option>
							</select>
							</div>
					</div>
					</div>
					
<!-- Cost Of Materials -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
					<div class="btn-primary btn-sm"> Cost Of Materials :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<!-- <div class="col-xs-50"> -->
									<div class="form-group col-sm-2">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-2 hide">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Remarks</b>
										</div>
									<!-- </div> -->

									<!-- <div class="col-xs-50"> -->
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">

																<div class="form-group col-sm-2">
																	<select class="form-control matCategory"
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

																
																<div class="form-group col-sm-2">
																	<input type="hidden" name="matItemName"
																		class="description" /> <select
																		class="form-control matItemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matItemCode" name="matItemCode"
																		type="text" placeholder="ItemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matUom" name="matUom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matQuantity" name="matQuantity" type="text"
																		placeholder="quantity" value="0" step="0.01" id="matQuantity0" onblur="setMatTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matUnitPrice" name="matUnitPrice" type="text"
																		placeholder="Unit Price" value="0" step="0.01" id="matUnitPrice0" onblur="setMatTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 hide">
																	<input class="form-control matAmount" id="matAmount0" name="matAmount" type="text"
																		placeholder="Total Amount" value="0" step="0.01" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-2">
																	<input class="form-control" name="matRemarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">

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
									<!-- </div> -->

									<hr />

								</div>
							</div>
						</div>

					</div>

				</div>

<!-- 	Cost Of Installation -->
		<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Installation :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">
									<div class="form-group col-sm-2">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-2 hide">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls1">
													<div class="aaa">
														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry1" id="costIns0">
															<div class="row">

																<div class="form-group col-sm-2">
																	<select class="form-control insCategory" id="insCategory0" name="insCategory"
																		onchange="categoryLeaveChange1(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																
																<div class="form-group col-sm-2">
																	<input type="hidden" name="insItemName" id="description0"
																		class="description1" /> <select
																		class="form-control insItemName" id="insItemName0"
																		onchange="itemLeaveChange1(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control insUom" name="insUom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control insQuantity" id="insQuantity0" name="insQuantity" type="text"
																		placeholder="Quantity" value="0" step="0.01" onblur="setInsTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control insUnitPrice" id="insUnitPrice0" name="insUnitPrice" type="text"
																		placeholder="Unit Price" value="0" step="0.01" onblur="setInsTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2 hide">
																	<input class="form-control insAmount" id="insAmount0" name="insAmount" type="text"
																		placeholder="Total Amount" value="0" step="0.01" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-2">
																	<input class="form-control" name="insRemarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">

																	<button class="btn btn-success btn-add-cost-ins" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove1" type="button">
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

<!-- 	Cost Of Recovery -->
<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Recovery :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">
										
										<div class="form-group col-sm-2">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-1 hide">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls2">
													<div class="aaa">
														
														<div class="col-xs-12 entry2" id="myAreaa0">
															<div class="row">																
																<div class="form-group col-sm-2">
																	<select class="form-control recCategory" id="recCategory0" name="recCategory"
																		onchange="categoryLeaveChange2(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																
																<div class="form-group col-sm-3">
																	<input type="hidden" name="recItemName"
																		class="description2" /> <select
																		class="form-control recItemName" id="recItemName0"
																		onchange="itemLeaveChange2(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>

																<div class="form-group col-sm-1">
																	<input class="form-control recUom" id="recUom0" name="recUom" type="text"
																		placeholder="Unit"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control recQuantity" id="recQuantity0" name="recQuantity"
																		type="number" value="0" step="0.01" placeholder="quantity" onblur="setRecTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control recUnitPrice" id="recUnitPrice0" name="recUnitPrice" 
																		type="number" value="0" step="0.01" placeholder="Unit Price" onblur="setRecTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 hide">
																	<input class="form-control recAmount" id="recAmount0" name="recAmount"
																		placeholder="Total Amount" readonly="readonly" type="number" value="0" step="0.01"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-2">
																	<input class="form-control recRemarks" name="recRemarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">

																	<button class="btn btn-success btn-add2" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove2" type="button">
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
	
	<!-- 	Cost Of Miscellanious -->			
		<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Miscellaneous :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-2 hide">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-3">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls3">
													<div class="aaa">
														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry3" id="myArea30">
															<div class="row">																
																<div class="form-group col-sm-3">
																	<input class="form-control misItemName" type="text" id="misItemName0" name="misItemName"
																  value="11KV Shut Down Fee" onBlur="showMiscUom(this.id);" style="border: 0; border-bottom: 2px ridge;" /> 
																</div>
																
																<div class="form-group col-sm-1">
																	<input class="form-control misUom" id="misUom0" name="misUom" type="text"
																		placeholder="Unit" value="no"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control misQuantity" id="misQuantity0" name="misQuantity" type="text"
																		placeholder="quantity" value="0" step="0.01" onblur="setMisTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control misUnitPrice" id="misUnitPrice0" name="misUnitPrice" type="text"
																		placeholder="Unit Price" value="0" step="0.01" onblur="setMisTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2 hide">
																	<input class="form-control misAmount" id="misAmount0" name="misAmount" type="text"
																		placeholder="Total Amount" value="0" step="0.01" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-sm-3">
																	<input class="form-control" name="misRemarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">

																	<button class="btn btn-success btn-add3" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove3" type="button">
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
					<div class="col-xs-12 col-sm-6" id="submit">
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

			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>



<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/jobTemplateForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>