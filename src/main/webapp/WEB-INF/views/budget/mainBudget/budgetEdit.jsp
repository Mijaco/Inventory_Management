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
				Budget List
			</a>
		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Budget
				Details</h2>
			<%-- <h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>

 --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin-top: 25px; margin-bottom: 25px; margin: 10px;">		
		
		<!-- edit budget details start-->
		
		<form method="POST" id="frmBudgetEdit"
			action="${pageContext.request.contextPath}/budget/editBudget.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
					<input type="hidden" name="id" value="${budgetDtls[0].budgetMst.id}">
				<!-- <div class="col-md-12 col-sm-12"> -->
				<div class="row" style="padding-left: 10px; margin-top: 25px; margin-bottom: 25px; ">
					<div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Budget
							Year :</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="text" class="form-control" id="descoSession"
								style="border: 0; border-bottom: 2px ridge;" name="descoSession" /> -->
							<select class="form-control" id="descoSessionForBudget" 
								name="descoSession" style="border: 0; border-bottom: 2px ridge;">
								<option disabled>--Select Budget Year--</option>
								<c:if test="${!empty descoSession}">
									<c:forEach items="${descoSession}" var="dSession" >
										<option value="${dSession.id}" ${budgetDtls[0].budgetMst.descoSession.sessionName == dSession.sessionName ? 'selected':''}>${dSession.sessionName}
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Budget
							Type :</label>
						<div class="col-sm-8 col-md-8">
						<label id="budgetType"	class="form-control">${budgetDtls[0].budgetMst.budgetType}</label>
							<%-- <select class="form-control" id="budgetTypeForBudget"
								name="budgetTypeId" style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty budgetTypes}">
									<c:forEach items="${budgetTypes}" var="budgetType">
										<option ${budgetType.id == budgetDtls[0].budgetMst.budgetType.id ? 'selected': ''} value="${budgetType.id}">${budgetType.title}
										</option>
									</c:forEach>
								</c:if>		 --%>
								<%-- <option value="Capital Expenditure" ${budgetDtls[0].budgetMst.budgetType eq 'Capital Expenditure' ? 'selected':''}>Expences</option>
								<option value="Assets" ${budgetDtls[0].budgetMst.budgetType eq 'Assets' ? 'selected':''}>Assets</option>
								<option value="Income" ${budgetDtls[0].budgetMst.budgetType eq 'Income' ? 'selected':''}>Income</option>
								<option value="Liability" ${budgetDtls[0].budgetMst.budgetType eq 'Liability' ? 'selected':''}>Liability</option>
						 --%>	<!-- </select> -->
						</div>
					</div>
					
						

					<%-- <div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Cost Source :</label>
						<div class="col-sm-8 col-md-8">
							<select class="form-control" id="projectFundSrcForBudget"
								name="projectFundSrc" style="border: 0; border-bottom: 2px ridge;">
								<option value="ADB" ${budgetDtls[0].budgetMst.budgetType eq 'ADB' ? 'selected':''}>ADB</option>
								<option value="GOB" ${budgetDtls[0].budgetMst.budgetType eq 'GOB' ? 'selected':''}>GOB</option>
								<option value="AIIB" ${budgetDtls[0].budgetMst.budgetType eq 'AIIB' ? 'selected':''}>AIIB</option>
								<option value="Desco" ${budgetDtls[0].budgetMst.budgetType eq 'Desco' ? 'selected':''}>Desco</option>
							</select>
						</div>
					</div> --%>
					
					<div class="form-group col-md-6">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">APP Year :</label>
						<div class="col-sm-8 col-md-8">							
							<label class="form-control" id="appYearForBudget"
								style="border: 0; border-bottom: 2px ridge;">${budgetDtls[0].budgetMst.descoSession.sessionName}</label>
						</div>
					</div>

					
</div>
				
				<div class="col-md-12 col-sm-12" style="padding-left: 10px; margin-top: 25px; margin-bottom: 25px; ">
					<div class="form-group" style="margin-top: 1em;">
						<p class="col-sm-12 btn btn-primary btn-sm">Budget Item
							List</p>
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
											<b>Cost Source</b>
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
													<c:forEach items="${budgetDtls}" var="budgetDtl" varStatus="loop">
														<div class="col-xs-12 entry clonedArea" id="myArea0">
															<div class="row">
																<div class="form-group col-xs-1">
																<input type="hidden" name="budgetDtlId" class="budgetDtlId" id="budgetDtl${loop.index}" value="${budgetDtl.id}"/>
																	<select class="form-control category" id="category${loop.index}"
																		onchange="categoryLeaveChange(this)" name="category"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}" ${category.categoryId == budgetDtl.itemMaster.categoryId ? 'selected':''}>${category.categoryId} - ${category.categoryName}
																					</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-xs-2">
																	<input type="hidden" name="description" 
																		id="description0" class="description" /> 
																		<select
																		class="form-control itemName" id="itemName${loop.index}"
																		onchange="itemLeaveChange(this)" name="itemName"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option >Item Name</option>
																		<option value="${budgetDtl.itemMaster.id}" selected>${budgetDtl.itemMaster.itemId} - ${budgetDtl.itemMaster.itemName }</option>
																	</select>
																</div>
																<div class="form-group col-xs-1">
																<input name="itemId"
																		id="itemId${loop.index}" type="hidden" class="itemId"	value="${budgetDtl.itemMaster.id}"																
																		style="border: 0; border-bottom: 2px ridge;" />
																	<input class="form-control itemCode" name="itemCode" 
																		value="${budgetDtl.itemMaster.itemId}"
																		id="itemCode${loop.index}" type="text" placeholder=""
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control uom" name="uom" type="text"
																		id="uom${loop.index}" readonly="readonly" value="${budgetDtl.itemMaster.unitCode}"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control receivedQty"
																		required="required" name="qty" data-field-name="receivedQty"
																		id="receivedQty${loop.index}" type="number" step="0.001" value="${budgetDtl.qty}"
																		onblur="setTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-xs-1">
																	<input class="form-control unitCost" name="rate" data-field-name="unitCost"
																		type="number" value="${budgetDtl.rate}" step="0.01" id="unitCost${loop.index}" 
																		onblur="setTotalCost(this)" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<select class="form-control cost source" id="costSource0"
																		name="costSource">
																		<c:forEach items="${costSources}" var="costSource">
																					<option value="${costSource.id}">${costSource.title}</option>
																				</c:forEach>
																		<%-- <option value="DESCO" ${budgetDtl.costSource eq "DESCO" ? 'selected="selected"' : ''}>Desco Own Source</option>
																		<option value="GOB" ${budgetDtl.costSource eq "GOB" ? 'selected="selected"' : ''}>GOB</option>
																		<option value="ADB" ${budgetDtl.costSource eq "ADB" ? 'selected="selected"' : ''}>ADB</option>																		
																		<option value="AIIB" ${budgetDtl.costSource eq "AIIB" ? 'selected="selected"' : ''}>AIIB</option>
																		<option value="OTHER"${budgetDtl.costSource eq "OTHER" ? 'selected="selected"' : ''}>Other</option>
																	 --%></select>
																	</div>
																<div class="form-group col-xs-1">
																	
																<fmt:formatNumber var='totalCostOnItem'  type="number" maxFractionDigits="5" value="${budgetDtl.rate * budgetDtl.qty}" />
																	<input class="form-control totalCost" name="totalCost"
																		type="text" value="${totalCostOnItem}" id="totalCost${loop.index}"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																		<select class="form-control expCat" id="expCat${loop.index}"
																			name="lookUpId"
																			style="border: 0; border-bottom: 2px ridge;">
																			<!-- <option disabled selected>Expenditure Category</option> -->
																			<c:if test="${!empty budgetExpenditureCategories}">
																				<c:forEach items="${budgetExpenditureCategories}" var="expCat">
																					<option value="${expCat.id}" ${budgetDtl.lookup.id == expCat.id ? 'selected="selected"' : ''}>${expCat.title}</option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>
																<!-- <div class="form-group col-xs-2">
																	<input class="form-control remarks" name="remarks"
																		type="text" placeholder="Remarks" id="remarks0"
																		value=" " style="border: 0; border-bottom: 2px ridge;" />
																</div> -->
																<div class="form-group col-xs-1">
																	<button class="btn btn-success btn-add-more" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove" type="button">
																		<span class="glyphicon glyphicon-minus"></span>
																	</button>

																</div>
															</div>

														</div>
														</c:forEach>
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
						<div class="pull-right" >
						<h4>
						<span>Grand Total : </span><span id="grandTotal"> <fmt:formatNumber
																			type="number" maxFractionDigits="3" value="${budgetDtls[0].budgetMst.totalAmount}" /></span>
						</h4>
						
						</div>
						
					</div>

				</div>

				<div class="col-md-12" style="padding-top: 5px;padding-bottom: 15px;">
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
			<!-- </div> -->

		</form>
		<!-- edit budget details end-->
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