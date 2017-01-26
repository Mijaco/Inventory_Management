<%@include file="../common/cnHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cn/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div>

		<h1 class="center blue" style="margin-top: 0; font-style:italic; font-family: 'Ubuntu Condensed', sans-serif;">Store Requisition
			Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cn/storeRequisitionSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Indentor:
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerDesignation"
								value="Executive Engineer"
								style="border: 0; border-bottom: 2px ridge;"
								name="identerDesignation" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="chalanNo" class="col-sm-4 control-label">Receiver
							Name:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<%-- <div class="form-group">
						<label for="contractDate"
							class="col-sm-4 col-md-4 control-label align-right">Requisition
							No:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="requisitionNo"
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								name="" value="${srId}"/>
						</div>
					</div> --%>
					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 col-md-4 control-label align-right">Requisition
							To:</label>
						<div class="col-sm-8 col-md-8">
							 <select class="form-control requisitionTo" name="requisitionTo" style="border: 0; border-bottom: 2px ridge;">
								<option value="">Select</option>
								<option value="cs">Central Store</option>
								<option value="ss">Sub Store</option>
								<option value="ss">Local Store</option>
							</select>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

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
					</div>

					<div><input type="hidden" id="contractNo" value="${contractNo}" name="contractNo" /> 
							
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> 
							<input type="hidden" class="form-control"
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
											<b>Remainning Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Req. Qty</b>
										</div>

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Issued Qty</b>
										</div> -->

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Head of Account</b>
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

																<div class="form-group col-sm-1 col-xs-12">
																	<input type="hidden" name="itemName"
																		class="description" /> <select
																		class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																		<c:if test="${!empty jobItemList}">
																			<c:forEach items="${jobItemList}" var="jobItem">
																				<option value="${jobItem.itemCode}">
																					<c:out value="${jobItem.itemName}" /></option>
																			</c:forEach>
																		</c:if>
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
																		type="number" value="0" step="0.01"
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
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/cnStoreRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>