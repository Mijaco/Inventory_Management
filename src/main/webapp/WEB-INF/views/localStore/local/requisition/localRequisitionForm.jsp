<%@include file="../../../common/lsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/local/requisition/pendingList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Local
				Requisition List
			</a>
			<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>
		</div>
		
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Local
			Requisition Form</h2>
			
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>
			
		
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="storeRequisitionFormSave"
			action="${pageContext.request.contextPath}/local/requisition/saveForm.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Indenter:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerDesignation"
								value="${department.deptName}"
								style="border: 0; border-bottom: 2px ridge;"
								name="identerDesignation" readonly="readonly">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="chalanNo" class="col-sm-4 control-label">Receiver
							Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receivedBy"
								value="${authUser.name} ( ${authUser.empId} )" style="border: 0; border-bottom: 2px ridge;"
								name="receivedBy" readonly="readonly">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="deptName"
							class="col-sm-4 col-md-4 control-label align-right">Requisition
							To:&nbsp;<strong class='red'>*</strong></label> 
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="deptName"
								value="${department.deptName}" style="border: 0; border-bottom: 2px ridge;"
								readonly="readonly">
								
							<input type="hidden" id="requisitionTo" value="${department.deptId}"/>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div> 
				</div>

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
										<div class="form-group col-sm-2 col-xs-12">
											<b>Category</b>
										</div>
										
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Ledger Name</b>
										</div> -->
										<div class="form-group col-sm-3 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Current Stock</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Req. Qty</b>
										</div>

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Cost</b>
										</div> -->
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
																<div class="form-group col-sm-2 col-xs-12">																	
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
																	<select class="form-control category" name="ledgerName"
																		style="border: 0; border-bottom: 2px ridge;">
																		<!-- <option disabled selected>Select One</option> -->
																		<c:if test="${!empty ledgerBooks}">
																			<c:forEach items="${ledgerBooks}" var="ledgerBook">
																				<option value="${ledgerBook}">
																					<c:out value="${ledgerBook}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div> --%>

																<div class="form-group col-sm-3 col-xs-12">	
																	<input type="hidden" name="itemName"
																		class="description" />																
																	<select
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
																	<input class="form-control currentStock" readonly="readonly" 
																		name="currentStock" id="currentStock0" type="number" step="0.001"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired"
																		onkeyup="reqQtyNotGreaterThenCurrentStock(this)"
																		name="quantityRequired" id="quantityRequired0" type="number"
																		value="0" step="0.001" style="border: 0; border-bottom: 2px ridge;" />
																		<br>
																		<strong class="text-danger reqQtyError hide" id="reqQtyError0">Qty. must be greater than 0.</strong>
																</div>

																
																<!-- <div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="unitCost" type="number" id="unitCost0"
																		placeholder="Unit Cost" onkeyup="setTotalCost(this)" value="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="totalCost" readonly="readonly" 
																		type="text" placeholder="Total Cost" id="totalCost0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div> -->
																
																<div class="form-group col-sm-2 col-xs-12">
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
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/localRequisitionForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>