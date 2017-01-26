<%@include file="../../common/settingsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requirement List
			</a>

		</div>
		<div class="col-md-8">

			<h1 class="center blue"
				style="margin-top: 10px; font-family: 'Ubuntu Condensed', sans-serif;">
				Material Planning and Stores Division</h1>

			<h3 class="center"
				style="margin-top: 10px; font-family: 'Ubuntu Condensed', sans-serif;">
				Requirement of System Materials and General Items</h3>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/mps/dn/saveDemandNote1.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Department
							Name :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="supplierName"
								value="${department.deptName}" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="supplierName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">
							Created By : </label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="referenceNo"
								readonly="readonly" name="referenceNo"
								value="${empty authUser.empId ? authUser.name : authUser.name.concat(' (').concat(authUser.empId).concat(')')} "
								style="border: 0; border-bottom: 2px ridge;" />
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="financialYear"
							class="col-sm-4 col-md-4 control-label text-right">Financial
							Year :</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" required="required"
								style="border: 0; border-bottom: 2px ridge;" 
								name="financialYear" id="financialYear">
								<option value="">Select	Financial Year</option>
								<c:forEach items="${descoSessionList}" var="sessions">
									<option value="${sessions.id}">${sessions.sessionName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="referenceDoc"
							class="col-sm-4 col-md-4 control-label text-right">Upload
							Doc. :</label>
						<div class="col-sm-8 col-md-8">
							<input type="file" id="referenceDoc" accept="application/pdf"
								class='form-control'
								style="border: 0; border-bottom: 2px ridge;" name="referenceDoc" />
						</div>
					</div>

				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<p class="col-sm-12 btn btn-primary btn-sm">Materials List</p>
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
											<b>Required Qty</b>
										</div>

										<div class="form-group col-xs-1">
											<b>Est. Unit Cost</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Est. Total Cost</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Consumption in Prev. Year </b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-xs-2">
																	<select class="form-control category" id="category0"
																		onchange="categoryLeaveChange(this)" name="category"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					${category.categoryId} ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-xs-2">
																	<input type="hidden" name="itemName" id="description0"
																		class="description" /> <select
																		class="form-control itemName" id="itemName0"
																		onchange="itemLeaveChange(this)" name="" required="required"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option value="">Select Item Name</option>
																	</select>
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control itemCode" name="itemCode"
																		id="itemCode0" type="text" placeholder="itemCode"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control uom" name="uom" type="text"
																		id="uom0" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control requiredQunatity"
																		name="requiredQunatity" id="requiredQunatity0"
																		type="number" step="0.001"
																		onblur="setTotalCost(this)" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-xs-1">
																	<input class="form-control unitCost" name="unitCost"
																		type="number" step="0.01" id="unitCost0"
																		onblur="setTotalCost(this)"  required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control totalCost" name="totalCost"
																		type="number" value="0" step="0.01" id="totalCost0"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-2">
																	<input class="form-control previousYearConsumption"
																		name="previousYearConsumption" type="text" value="0"
																		id="previousYearConsumption0"
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
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-md btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-md btn-danger"
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
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/demandNote1.js"></script>

<%@include file="../../common/ibcsFooter.jsp"%>