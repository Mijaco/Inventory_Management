<%@include file="../common/budgetHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/bgt/costCenterList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span>&nbsp;Cost Centre List
			</a> --%>
			
			<h1  class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			General Budget Entry Form</h1>
			
		</div>		
		
	</div>
	
	<div class="container">
		<div class="col-xs-12" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form id="saveGeneralBudget" action="${pageContext.request.contextPath}/bgt/saveGeneralItemEntry.do" method="post" class="form-horizontal">
				
				<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Financial Year:</td>
								<td class="col-xs-3">
									<select id="sessionName" name="sessionId" style="width: 100%;">
										<option value="0" selected disabled>Financial Year</option>
										<c:if test="${!empty descoSession}">
											<c:forEach items="${descoSession}" var="session">
												<option value="${session.id}">${session.sessionName}</option>
											</c:forEach>
										</c:if>
									</select>
									<h5 class="red sessionName hide"> <strong>Financial Year is required</strong> </h5>
								</td>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Item Category:</td>
								<td class="col-xs-3" style="width: 100%">
									<select class="category" id="category" name="category" style="width: 100%;" onchange="categoryLeaveChange(this)">
										<option value="0" selected disabled>Category</option>
										<c:if test="${!empty categoryList}">
											<c:forEach items="${categoryList}" var="category">
												<option value="${category.categoryId}">${category.categoryName}</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Item Name:</td>
								<td class="col-xs-3">
									<input type="hidden" name="itemName" id="itemName" class="description">
									<select class="itemName" name="iName" id="iName" onchange="itemLeaveChange(this)"
										style="width: 100%;">
										<option disabled selected>Item Name</option>
									</select>
									<h5 class="red itName hide"> <strong>Item Name is required</strong> </h5>
								</td>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Item Code:</td>
								<td class="col-xs-3">
									<input type="text" id="itemCode" name="itemCode" readonly style="width: 100%">
									<h5 class="red itemCode hide"><strong>Item budget is already generated</strong></h5>
								</td>
							</tr>
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Unit:</td>
								<td class="col-xs-3">
									<input type="text" id="uom" name="uom" readonly style="width: 100%">
								</td>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Rate:</td>
								<td class="col-xs-3">
									<input type="text" id="rate" name="rate" style="width: 100%" required="required">
									<h5 class="red rate hide"> <strong>Rate is required</strong> </h5>
								</td>
							</tr>
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Quantity:</td>
								<td class="col-xs-3">
									<input type="text" id="mquantity" name="mquantity" readonly style="width: 100%">
								</td>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Amount:</td>
								<td class="col-xs-3">
									<input type="text" id="amount" name="amount" readonly style="width: 100%">
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="form-group" style="margin-top: 1em;">
					<p class="col-sm-12 btn btn-primary btn-sm" style="margin-left: -5px;">Cost Centre wise details</p>
					<div class="col-xs-12 table-responsive">
						<div class="table">
							<div style="width: 100%;">
								<hr />
								<div class="col-xs-12">
									<div class="form-group col-sm-4 col-xs-12">
										<b>Cost Centre</b>
									</div>
									<div class="form-group col-sm-3 col-xs-12" style="margin-left: 10px;">
										<b>Quantity</b>
									</div>
									<div class="form-group col-sm-3 col-xs-12" style="margin-left: 10px;">
										<b>Cost</b>
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

															<div class="form-group col-sm-4 col-xs-12">
																<select class="form-control costCentre" name="costCentre" id="costCentre0"
																	style="border: 0; border-bottom: 2px ridge;">
																	<option disabled selected>Cost Centre</option>
																	<c:if test="${!empty costCentreList}">
																		<c:forEach items="${costCentreList}" var="costCentre">
																			<option value="${costCentre.costCentreCode}">
																				<c:out value="${costCentre.costCentreName}" /></option>
																		</c:forEach>
																	</c:if>
																</select>
															</div>

															<div class="form-group col-sm-3 col-xs-12" style="margin-left: 10px;">
																<input class="form-control quantity" name="quantity" id="quantity0"
																	type="number" onblur="calculateCost(this)"
																	style="border: 0; border-bottom: 2px ridge;" required="required">
																	<h5 class="red qtError hide"> <strong>Quantity is required</strong> </h5>
															</div>
															<div class="form-group col-sm-3 col-xs-12" style="margin-left: 10px;">
																<input class="form-control cost" name="cost" id="cost0"
																	type="number" step="0.01"
																	style="border: 0; border-bottom: 2px ridge;" readonly>
															</div>
						
															<div class="form-group col-sm-2 col-xs-3" style="margin-left: 10px;">

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
				<div class="col-xs-12" align="center">
					<button type="button" id="saveButton" class="btn btn-success btn-lg" style="border-radius: 6px;">
						<i class="fa fa-fw fa-save"></i>&nbsp;Save
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/assets/js/budget/generalBudgetEntryForm.js"></script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>