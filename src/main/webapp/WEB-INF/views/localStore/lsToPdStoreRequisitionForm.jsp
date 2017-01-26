<%@include file="../common/lsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.css">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition from Project</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>


	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->

		<c:if test="${!empty msg}">
			<h3 class="alert alert-danger">${msg}</h3>
		</c:if>

		<form method="POST"
			action="${pageContext.request.contextPath}/ls/pd/saveRequisition.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<input type="hidden" class="form-control" id="identerDesignation"
						value="Executive Engineer"
						style="border: 0; border-bottom: 2px ridge;"
						name="identerDesignation" />
					<div class="form-group">
						<label for="chalanNo" class="col-sm-4 control-label">
							Receiver Name:&nbsp;<strong class='red'>*</strong>
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy" required="required"> <input
								type="hidden" id="contextPath"
								value="${pageContext.request.contextPath}" />
							<h5 class="text-danger">
								<strong id="empty-message"></strong>
							</h5>
							<strong class="text-danger receivedBy hide">Receiver
								name is required</strong>
						</div>
					</div>
					<br> <br>
					<div class="form-group">
						<input type="hidden" id="khathName" name="khathName"
							value="Purbachal"> <label for="returnFor"
							class="col-sm-4 col-md-4 control-label">Project Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control category" name="khathId" id="khathId"
								style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty descoKhathList}">
									<c:forEach items="${descoKhathList}" var="descoKhath">
										<c:if test="${descoKhath.khathCode!='SND'}">
											<option value="${descoKhath.id}">${descoKhath.khathName}</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractDate" class="col-sm-4 col-md-4 control-label">Requisition
							To:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control requisitionTo" name="requisitionTo"
								id="requisitionTo" style="border: 0; border-bottom: 2px ridge;">
								<option value="cs">Central Store</option>
								<option value="ss">Sub Store</option>
							</select>
						</div>
					</div>
					<br> <br>
					<div class="form-group">
						<label for="carriedBy" class="col-sm-4 col-md-4 control-label">Carried
							By:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" name="carriedBy" id="carriedBy"
								style="width: 100%; border: 0; border-bottom: 2px ridge;">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" />
					</div>

				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div>
									<!-- style="width: 1500px;" -->
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>Category&nbsp;<strong class='red'>*</strong></b>
										</div>
										<div class="form-group col-sm-3 col-xs-12">
											<b>Item Name&nbsp;<strong class='red'>*</strong></b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
											<b>Present Store Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Required Qty&nbsp;<strong class='red'>*</strong></b>
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
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-sm-1 col-xs-12">
																	<select class="form-control category" id="category0"
																		name="category" onchange="categoryLeaveChange(this)"
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


																<div class="form-group col-sm-3">
																	<input type="hidden" name="itemName"
																		class="description" /> <select
																		class="form-control itemName" id="itemId0"
																		name="itemId" onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control itemCode" name="itemCode"
																		id="itemCode0" type="text" placeholder="itemCode"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" required>
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">
																	<input class="form-control currentStock"
																		readonly="readonly" name="currentStock"
																		id="currentStock0" type="number"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- onkeyup="reqQtyNotGreaterThenCurrentStock(this)" -->
																<div class="form-group col-sm-1">
																	<input class="form-control quantityRequired"
																		step="0.001" min="0"
																		onkeyup="reqQtyNotGreaterThenCurrentStock(this)"
																		onblur="validateAllocation(this)"
																		name="quantityRequired" id="quantityRequired0"
																		type="number" value="0"
																		style="border: 0; border-bottom: 2px ridge;" /> <strong
																		class="text-danger reqQty hide" id="reqQty0">Required
																		Qty. can't be 0</strong>
																</div>

																<div class="form-group col-sm-2">
																	<input class="form-control" name="remarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<button class="btn btn-success btn-add btn-xs" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove btn-xs" type="button">
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
			<input type="hidden" id="loadauthList" value="1">
		</form>
		<!-- --------------------- -->
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui-autocomplete/jquery-ui.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/lsToPdStoreRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>