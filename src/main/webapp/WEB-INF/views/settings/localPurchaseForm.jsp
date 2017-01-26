<%@include file="../common/lprrHeader.jsp"%>


<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->
<div class="container-fluid icon-box ashid"
	style="background-color: #858585;" id="department_div">
	<div class="col-xs-8 col-xs-offset-2"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="table-responsive col-xs-12">
			<table class="table table-bordered table-hover">
				<tbody>
					<tr>
						<td class="col-xs-2 success text-right" style="font-weight: bold;">Department
							Name: <input type="hidden" id="loginUserDeptId"
							value="${department.id}" />
						</td>
						<td class="col-xs-8"><select class="form-control" name="id"
							id="departmentName">
								<option value="">Select Your Department</option>
								<c:forEach items="${departmentList}" var="dept">
									<option value="${dept.id}">${dept.deptName}</option>
								</c:forEach>
						</select> <strong class="text-danger text-center hide"
							id="invalid_dept_warning"
							style="font-weight: bold; font-size: 16px;">Invalid
								Department!!! Please select correct Department.</strong></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="col-xs-12" align="center">
			<button type="button" id="verifyDepartment"
				class="btn btn-success btn-md" style="border-radius: 6px;">
				<i class="fa fa-fw fa-lock"></i>Verify
			</button>
		</div>

	</div>
</div>
<div class="container-fluid icon-box hide"
	style="background-color: #858585;" id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/settings/localPurchaseList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Local
				Purchase List
			</a>

		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Local
				Purchase Entry Form</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>


		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/settings/saveLocalPurchase.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Supplier
							Name :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="supplierName"
								style="border: 0; border-bottom: 2px ridge;" name="supplierName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="supplyDate"
							class="col-sm-4 col-md-4 control-label text-right">Supply Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15" id="supplyDate"
								style="border: 0; border-bottom: 2px ridge;" name="supplyDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Invoice
							/ Reference No : </label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="referenceNo"
								style="border: 0; border-bottom: 2px ridge;" name="referenceNo" />
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="purchaseNo"
							class="col-sm-4 col-md-4 control-label text-right">Purchase Order No:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control"
								id="purchaseOrderNo" style="border: 0; border-bottom: 2px ridge;"
								name="purchaseOrderNo" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 col-md-4 control-label text-right">Purchase Order
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								id="purchaseDate" style="border: 0; border-bottom: 2px ridge;"
								name="purchaseDate" />
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
						<p class="col-sm-12 btn btn-primary btn-sm">Purchased Item
							List</p>
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
										<div class="form-group col-xs-1">
											<b>Total Cost</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Remarks</b>
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
																				<option value="${category.categoryId}">${category.categoryName} [${category.categoryId}]</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-xs-2">
																	<input type="hidden" name="description"
																		id="description0" class="description" /> <select
																		class="form-control itemName" id="itemName0"
																		onchange="itemLeaveChange(this)" name="itemName"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
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
																	<input class="form-control receivedQty" required="required"
																		name="receivedQty" id="receivedQty0" type="number"
																		value="0" step="0.001" onblur="setTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-xs-1">
																	<input class="form-control unitCost" name="unitCost"
																		type="number" value="0" step="0.01" id="unitCost0"
																		onblur="setTotalCost(this)" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control totalCost" name="totalCost"
																		type="number" value="0" step="0.01" id="totalCost0"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-2">
																	<input class="form-control remarks" name="remarks"
																		type="text" placeholder="Remarks" id="remarks0" value=" "
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
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/localPurchaseForm.js"></script>

<!-- Start of Footer  -->
<%@include file="../common/ibcsFooter.jsp"%>