<%@include file="../common/csHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/workOrder/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Work
				Order List
			</a>
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Work
				Order Information</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/workOrder/woSave.do"
			enctype="multipart/form-data">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="workOrderNo" class="col-sm-4 control-label">WO/Contract
							No&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<input type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}"> <input
								type="text" class="form-control" id="workOrderNo"
								onblur="checkWorkOrder()"
								placeholder="Please Enter Your WO No.."
								style="border: 0; border-bottom: 2px ridge;" name="workOrderNo" />
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplierName" class="col-sm-4 col-md-4 control-label">Supplier
							Name&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="supplierName"
								style="border: 0; border-bottom: 2px ridge;" name="supplierName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="psi" class="col-sm-4 control-label">PSI
							Performed?</label>
						<div class="col-sm-2">
							<input type="checkbox" id="psi"
								style="border: 0; border-bottom: 2px ridge;" name="psi" />
						</div>
						<label for="pli" class="col-sm-4 control-label">PLI
							Performed? </label>
						<div class="col-sm-2">
							<input type="checkbox" checked="checked" id="pli"
								style="border: 0; border-bottom: 2px ridge;" name="pli" />
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractDate" class="col-sm-4 col-md-4 control-label">Contract
							Date&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="contractDate" style="border: 0; border-bottom: 2px ridge;"
								name="contractDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="khathId" class="col-sm-4 control-label">Project
							Name&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<select class="form-control khathId" name="khathId"
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
						<label for="referenceDoc" class="col-sm-4 col-md-4 control-label">Reference
							Doc.&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="file" id="referenceDoc" accept="application/pdf"
								class='form-control'
								style="border: 0; border-bottom: 2px ridge;" name="referenceDoc" />
						</div>
					</div>

				</div>
				<!-- <div class="col-md-12 col-sm-12"> style="overflow-x: scroll; width: 60em;white-space: nowrap;" -->
				<div class="col-md-12 col-sm-12">
					<hr />
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Item List:</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1700px;">
									<div class="col-xs-12">
										<div class="form-group col-xs-2">
											<b>Category</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Item Name</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Item Code</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Purchased Qty</b>
										</div>

										<div class="form-group col-xs-1">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-xs-3">
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
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-xs-2">
																	<input type="hidden" name="description"
																		class="description" /> <select
																		class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control itemCode" name="itemId"
																		type="text" placeholder="itemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control itemQty" name="itemQty"
																		id="expectedQty0" type="number" placeholder="0.0"
																		step="0.001"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-xs-1">
																	<input class="form-control" name="cost" type="number"
																		placeholder="0.0" step="0.01"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-2">
																	<input class="form-control" name="remarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">

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
						<button type="submit" disabled="disabled" id="saveButton"
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
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/workOrderForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>