<%@include file="../../common/budgetHeader.jsp"%>


<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->

<div class="container-fluid icon-box" style="background-color: #858585;"
	id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 20px; padding-left: 20px">
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
		style="background-color: white; padding-left: 10px; margin: 10px; padding-top: 15px; padding-bottom: 15px;">
		<!-- ---------------------view budget details -->
		<form method="POST" action="" id="frmBudgetDetails"
			enctype="multipart/form-data">
			<div class="oe_title">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-12 col-sm-12">
					<div class="row">
						<!-- <input type="button" class="btn btn-success pull-right" id="btnEditBudget" value="Edit"> -->
					</div>
					<div class="row">
						<div class="form-group col-md-6 col-sm-12">
							<label for="supplierName"
								class="col-sm-4 col-md-4 control-label text-right">Budget
								Year :</label>
							<div class="col-sm-8 col-md-8">
								<input type="text" class="form-control"
									value="${budgetDtls[0].budgetMst.descoSession.sessionName}"
									style="border: 0; border-bottom: 2px ridge;"
									name="descoSession" readonly />

							</div>
						</div>


						<div class="form-group col-md-6 col-sm-12">
							<label for="supplierName"
								class="col-sm-4 col-md-4 control-label text-right">Budget
								Type :</label>
							<div class="col-sm-8 col-md-8">
								<input type="text" class="form-control"
									value="${budgetDtls[0].budgetMst.budgetType}"
									style="border: 0; border-bottom: 2px ridge;" name="budgetType"
									readonly />


							</div>
						</div>

					</div>
					
					<c:set var="total" value="${0}" />


					<div class="col-md-12 col-sm-12 ">
						<div class="form-group" style="margin-top: 1em;">
							<p class="col-sm-12 btn btn-primary btn-sm">Budget Item List</p>
							<div class="col-xs-12 table-responsive">
								<div class="table">

									<div class="col-xs-12">
										<!-- <div class="form-group col-xs-2">
											<b>Category</b>
										</div> -->
										<div class="form-group col-xs-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Item Code</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Cost Source</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Expenditure Category</b>
										</div>
										<div class="form-group col-xs-1">
											<b>Qty</b>
										</div>

										<div class="form-group col-xs-1">
											<b>Rate</b>
										</div>
										<div class="form-group col-xs-2 text-center">
											<b>Total Cost</b>
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
															<c:forEach items="${budgetDtls}" var="budgetDtl">
																<div class="row">
																	<div class="form-group col-xs-3">
																		<span>${budgetDtl.itemMaster.itemName}</span>
																	</div>
																	<div class="form-group col-xs-1">
																		<span>${budgetDtl.itemMaster.itemId}</span>
																	</div>
																	<div class="form-group col-xs-1">
																		<span>${budgetDtl.itemMaster.unitCode}</span>
																	</div>
																	<div class="form-group col-xs-1">
																		<span>${budgetDtl.costSource.title}</span>
																	</div>
																	<div class="form-group col-xs-2">
																		<span>${budgetDtl.lookup.title}</span>
																	</div>
																	<div class="form-group col-xs-1 text-left">
																		<span>${budgetDtl.qty}</span>
																		
																	</div>
																	<div class="form-group col-xs-1 text-left">
																			<span>${budgetDtl.rate}</span>
																		</div>
																		<div class="form-group col-xs-2 text-right">
																			<span><fmt:formatNumber type="number"
																					maxFractionDigits="5"
																					value="${budgetDtl.totalAmount}" /></span>
																			<c:set var="total"
																				value="${total + budgetDtl.totalAmount}" />
																		</div>
																		</div>
															</c:forEach>
															<hr>
															<div class="row">
																<span class="pull-right" style="padding-right: 20px"><b>Grand
																		Total : <fmt:formatNumber type="number"
																			 maxFractionDigits="3"
																			value="${total}" />
																</b> </span>
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
			</div>
		</form>
		<!-- --------------------- -->




	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/budget/budgetEntryForm.js"></script>

<!-- Start of Footer  -->
<%@include file="../../common/ibcsFooter.jsp"%>