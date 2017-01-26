<%@include file="../common/wsContractorHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ws/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a> <input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition Form To Central Store</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/ws/storeRequisitionSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Indenter:
						</label>
						<div class="col-sm-8">
							<b>Executive Engineer, ${contractor.contractor.division}</b> <input
								type="hidden" class="form-control" id="identerDesignation"
								value="Executive Engineer, ${contractor.contractor.division}"
								style="border: 0; border-bottom: 2px ridge;"
								name="identerDesignation" /> <input type="hidden"
								name="khathId" id="khathId" value="${descoKhath.id}">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="contractorNo" class="col-sm-4 control-label">Contractor
							No :</label>
						<div class="col-sm-8">
							<b> ${contractor.contractor.contractNo}, Date: <fmt:formatDate
									value="${contractor.contractor.contractDate}"
									pattern="dd-MM-yyyy" />
							</b> <input type="hidden" id="workOrderNumber"
								value="${contractor.contractor.contractNo}"
								name="workOrderNumber">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractorNo" class="col-sm-4 control-label">Contractor
							Name :</label>
						<div class="col-sm-8">
							<b>${contractor.contractor.contractorName}</b>
						</div>
					</div>

					<!-- <div class="form-group">
						<label for="returnFor"
							class="col-sm-4 col-md-4 control-label align-right">Date
							:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="requisitionDate"
								style="border: 0; border-bottom: 2px ridge;"
								name="requisitionDate" />
						</div>
					</div> -->

					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" />
					</div>

				</div>
				<input type="hidden" name="requisitionTo" value="cs" />
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1700px;">
									<hr />
									<div class="col-xs-12">
										<!-- <div class="form-group col-xs-2">
											<b>Category</b>
										</div> -->
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Ledger Name</b>
										</div> -->
										<div class="form-group col-xs-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Reserve Quantity</b>
										</div>

										<div class="form-group col-xs-1">
											<b>Required Qty</b>
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

																<%-- <div class="form-group col-xs-2">
																	<select class="form-control category"
																		onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					${category.categoryName} [${category.categoryId}]</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div> --%>
															 <div class="form-group col-sm-3 col-xs-12">
																	<select class="form-control itemCodeSelect" name="itemCodeSelect" id="itemCodeSelect0"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Select One</option>
																		<c:if test="${!empty jobCardTemplateList}">
																			<c:forEach items="${jobCardTemplateList}" var="jobCardTemplate">
																				<option value="${jobCardTemplate.itemCode}">
																					<c:out value="${jobCardTemplate.itemName}" />--[${jobCardTemplate.itemCode}]</option>
																			</c:forEach>
																		</c:if>
																	</select>
																	<input type="hidden" name="itemName" id="itemName0"
																		class="description" />
																</div>
																<!-- <div class="form-group col-xs-2">
																	<input type="hidden" name="itemName"
																		class="description" /> <select
																		class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div> -->
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode" 
																		id="itemCode0"
																		type="text" placeholder="itemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" id="unit0" name="uom" type="text"
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
																		type="number" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
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
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/transformerMaterialsRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>