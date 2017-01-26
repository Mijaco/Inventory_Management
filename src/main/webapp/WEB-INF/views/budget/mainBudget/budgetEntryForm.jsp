<%@include file="../../common/budgetHeader.jsp"%>


<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->

<div class="container-fluid icon-box" style="background-color: #858585;"
	id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/budget/getBudgetList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Budgets List
			</a>
		</div>
		<div class="o_form_buttons_edit col-md-2 pull-right" >			
			<a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/mps/appFormWithSession.do">
						Go to APP</a>
		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Budget
				Entry Form</h2>
			<%-- <h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>
 --%>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 15px; margin-bottom: 15px;">
		<!-- --------------------- -->
		<form method="POST" id="budgetEntryForm"
			action="${pageContext.request.contextPath}/budget/addNewBudget.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-12 col-sm-12">
					<div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Budget
							Year :</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="text" class="form-control" id="descoSession"
								style="border: 0; border-bottom: 2px ridge;" name="descoSession" /> -->
							<select class="form-control" id="descoSessionForBudget" required
								name="descoSession" style="border: 0; border-bottom: 2px ridge;">
								<option disabled selected>--Select Budget Year--</option>
								<c:if test="${!empty descoSession}">
									<c:forEach items="${descoSession}" var="dSession">
										<option value="${dSession.id}">${dSession.sessionName}
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-6">
						<label for="budgetType"
							class="col-sm-4 col-md-4 control-label text-right">Budget
							Type :</label>
						<div class="col-sm-8 col-md-8">
						<label id="budgetType"	class="form-control">Capital Expenditure</label>
							<%-- <select class="form-control" id="budgetTypeForBudget" required
								name="budgetTypeId" style="border: 0; border-bottom: 2px ridge;">
								<option disabled selected>--Select Budget Type--</option>	
								<c:if test="${!empty budgetTypes}">
									<c:forEach items="${budgetTypes}" var="budgetType">
										<option value="${budgetType.id}">${budgetType.title}
										</option>
									</c:forEach>
								</c:if>	 --%>						
								<!-- <option value="Capital Expenditure">Capital Expenditure</option>
								<option value="Assets">Assets</option>
								<option value="Income">Income</option>
								<option value="Liability">Liability</option> -->
							</select>
						</div>
					</div>

					<!-- <div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Status
							:</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" id="budgetStatusForBudget"
								name="budgetStatus" style="border: 0; border-bottom: 2px ridge;">
								<option value="Draft">Draft</option>
								<option value="Approve">Approve</option>

							</select>
						</div>
					</div>
 -->

					<!-- <div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Cost
							Source :</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" id="projectFundSrcForBudget"
								name="projectFundSrc"
								style="border: 0; border-bottom: 2px ridge;">
								<option value="ADB">ADB</option>
								<option value="GOB">GOB</option>
								<option value="AIIB">AIIB</option>
								<option value="Desco">Desco</option>
							</select>
						</div>
					</div> -->

					<div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">APP
							Year :</label>
						<div class="col-sm-8 col-md-8">
							<label class="form-control" id="appYearForBudget"
								style="border: 0; border-bottom: 2px ridge;"></label>
						</div>
					</div>



					<div class="col-md-12 col-sm-12">
						<div class="form-group" style="margin-top: 1em;">
							<p class="col-sm-12 btn btn-primary btn-sm">Budget Item List</p>
							<div class="col-xs-12 table-responsive">
								<div class="table">
									<div style="width: 1700px;">
										<div class="col-xs-12">
											<div class="form-group col-xs-1">
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
												<b>Qty</b>
											</div>

											<div class="form-group col-xs-1">
												<b>Rate</b>
											</div>
											<div class="form-group col-xs-1">
												<b>Funding Source</b>
											</div>
											<div class="form-group col-xs-1">
												<b>Total Cost</b>
											</div>
											<div class="form-group col-xs-1">
												<b>Expenditure Category</b>
											</div>
											<!-- <div class="form-group col-xs-2">
											<b>Remarks</b>
										</div> -->
										</div>

										<div class="col-xs-12">
											<div class="row">
												<div class="control-group" id="fields">
													<div class="controls">
														<div class="aaa">
															<div class="col-xs-12 entry" id="myArea0">
																<div class="row">
																	<div class="form-group col-xs-1">
																		<select class="form-control category" id="category0" disabled
																			onchange="categoryLeaveChange(this)" name="category"
																			style="border: 0; border-bottom: 2px ridge;">
																			<option disabled selected>Category</option>
																			<c:if test="${!empty categoryList}">
																				<c:forEach items="${categoryList}" var="category">
																					<option value="${category.categoryId}">${category.categoryId}
																						- ${category.categoryName}</option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>

																	<div class="form-group col-xs-2">
																		<input type="hidden" name="description"
																			id="description0" class="description" /> <select disabled
																			class="form-control itemName" id="itemName0"
																			onchange="itemLeaveChange(this)" name="itemName"
																			style="border: 0; border-bottom: 2px ridge;">
																			<option disabled selected>Item Name</option>
																		</select>
																	</div>
																	<div class="form-group col-xs-1">
																		<input name="itemId" id="itemId0" type="hidden"
																			class="itemId" readonly
																			style="border: 0; border-bottom: 2px ridge;" /> <input
																			class="form-control itemCode" name="itemCode"
																			id="itemCode0" type="text" placeholder=""
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-xs-1">
																		<input class="form-control uom" name="uom" type="text"
																			id="uom0" readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-xs-1">
																		<input class="form-control receivedQty"
																			required="required" name="qty"
																			data-field-name="receivedQty" id="receivedQty0"
																			type="number" value="0" step="0.001"
																			onblur="setTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>

																	<div class="form-group col-xs-1">
																		<input class="form-control unitCost" name="rate"
																			data-field-name="unitCost" type="number" value="0"
																			step="0.01" id="unitCost0"
																			onblur="setTotalCost(this)" required="required"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-xs-1">
																	<select class="form-control cost source" id="costSource0"
																		name="costSource">
																		<c:forEach items="${costSources}" var="costSource">
																					<option value="${costSource.id}">${costSource.title}</option>
																				</c:forEach>
																		<!-- <option value="DESCO">Desco Own Source</option>
																		<option value="GOB">GOB</option>
																		<option value="ADB">ADB</option>																		
																		<option value="AIIB">AIIB</option>
																		<option value="OTHER">Other</option> -->
																	</select>
																	</div>
																	<div class="form-group col-xs-1">
																		<input class="form-control totalCost" name="totalCost"
																			type="number" value="0" step="0.01" id="totalCost0"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-xs-1">
																		<select class="form-control expCat" id="expCat0"
																			name="lookUpId"
																			style="border: 0; border-bottom: 2px ridge;">
																			<!-- <option disabled selected>Expenditure Category</option> -->
																			<c:if test="${!empty budgetExpenditureCategories}">
																				<c:forEach items="${budgetExpenditureCategories}" var="expCat">
																					<option value="${expCat.id}">${expCat.title}</option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>
																	
																	<div class="form-group col-xs-1">
																		<button class="btn btn-success btn-add" type="button">
																			<span class="glyphicon glyphicon-plus"></span>
																		</button>
																		<button class="btn btn-danger btn-remove"
																			type="button">
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
							<div class="pull-right">
								<h4>
									<span>Grand Total : </span><span id="grandTotal">0.0</span>
								</h4>

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
								class="width-20  pull-left btn btn-md btn-default"
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

<!-- Button trigger modal -->
<button type="button" id="btn-modal" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
</button>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Form Submitting Error</h4>
      </div>
      <div class="modal-body">
        <h4 class="red" id="myModalLabelMsg"></h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/budget/budgetEntryForm.js"></script>

<!-- Start of Footer  -->
<%@include file="../../common/ibcsFooter.jsp"%>