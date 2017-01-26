<%@include file="../../common/wsContractorHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/wsx/transformer/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Transformer
				Requisition List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer
			Requisition Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/wsx/transformer/storeRequisitionSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Indenter:
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerDesignation"
								value="Executive Engineer, Testing & Repair Division"
								style="border: 0; border-bottom: 2px ridge;"
								name="identerDesignation" /> <input type="hidden"
								name="khathId" id="khathId" value="${descoKhath.id}"> <input
								type="hidden" name="workOrderNumber" id="workOrderNumber"
								value="${representative.contractNo}">
						</div>
					</div>
					
				</div>

				<div class="col-md-6 col-sm-6">
				
					<div class="form-group">
						<label for="chalanNo" class="col-sm-4 control-label">Receiver
							Name:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								value="${representative.representiveName}" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy">
						</div>
					</div>
					<!-- <div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
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
											<b>Reserve Quantity</b>
										</div>

										<div class="form-group col-xs-2">
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

																<div class="form-group col-xs-2">
																	<select class="form-control category" id="category0"
																		onchange="categoryLeaveChange(this)" name="category"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty category}">
																			<option value="${category.categoryId}">
																				<c:out value="${category.categoryName}" /></option>
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
																	<input type="hidden" name="itemName" id="itemName0"
																		class="itemName" /> <select
																		class="form-control itemNameSelect"
																		name="itemNameSelect" id="itemNameSelect0"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																		<c:if test="${!empty itemList}">
																			<c:forEach items="${itemList}" var="item">
																				<option value="${item.id}">
																					<c:out value="${item.itemName} [${item.itemId}]" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode" id="itemCode0"
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
																	<input class="form-control quantityRequired" onblur="validQty(this)"
																		name="quantityRequired" id="quantityRequired0"
																		type="number" value="0" step="0.01"
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
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/transformerRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>