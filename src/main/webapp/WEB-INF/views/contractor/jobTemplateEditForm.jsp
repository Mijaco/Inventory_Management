<%@include file="../common/cnPndHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/template/jobEstimationList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Cost
				Estimate List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Cost
			Estimation Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/template/jobTemplateSave.do">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<!-- <div class="form-group">
						<label for="estimateNo" class="col-sm-4 control-label">Estimate No:
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="pndNo"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="pndNo" />
						</div>
					</div> -->
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">Applicant
							Name : </label>
						<div class="col-sm-8">
							<input type="hidden" value="${costEstimationMst.pndNo}"
								name="pndNo" /> <input type="text" class="form-control"
								id="name" value="${costEstimationMst.name}"
								style="border: 0; border-bottom: 2px ridge;" name="name">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="serviceCharge" class="col-sm-4 control-label">Service
							Charge
							:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(%)</label>
						<div class="col-sm-8">
							<input type="number" class="form-control" id="serviceCharge"
								required="required"
								value="${costEstimationMst.serviceChargePercent}"
								style="border: 0; border-bottom: 2px ridge;" step="0.01"
								name="serviceChargePercent" min="0">
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="address" class="col-sm-4 control-label">Applicant
							Address :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="address"
								value="${costEstimationMst.address}"
								style="border: 0; border-bottom: 2px ridge;" name="address">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="typeOfScheme" class="col-sm-4 control-label">Type
							Of Scheme :</label>
						<div class="col-sm-8">
							<select class="form-control" id="typeOfScheme"
								name="typeOfScheme" style="border: 0; border-bottom: 2px ridge;">
								<option value="${costEstimationMst.typeOfScheme}">${costEstimationMst.typeOfScheme}</option>
								<option value="deposit">Deposit</option>
								<option value="development">Development</option>
							</select>
						</div>
					</div>
				</div>

				<!-- Cost Of Materials -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Materials :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<!-- <div class="col-xs-50"> -->
									<div class="form-group col-sm-2">
										<b>Category</b>
									</div>
									<div class="form-group col-sm-2">
										<b>Item Name</b>
									</div>
									<div class="form-group col-sm-1">
										<b>Item Code</b>
									</div>
									<div class="form-group col-sm-1">
										<b>Unit</b>
									</div>
									<div class="form-group col-sm-1">
										<b>Quantity</b>
									</div>
									<div class="form-group col-sm-1">
										<b>Unit Price in TK.</b>
									</div>
									<div class="form-group col-sm-1">
										<b>Total Amount in TK.</b>
									</div>
									<div class="form-group col-sm-2">
										<b>Remarks</b>
									</div>
									<!-- </div> -->

									<!-- <div class="col-xs-50"> -->
									<div class="row">
										<div class="control-group" id="fields">
											<div class="controls">
												<div class="aaa">
													<!-- <form role="form" autocomplete="off">  -->
													<c:forEach items="${costEstimateMaterialsDtlList}"
														var="costEstimateMaterialsDtl" varStatus="loop">

														<div class="col-xs-12 entry" id="myArea${loop.index}">
															<div class="row">

																<div class="form-group col-sm-2"></div>


																<div class="form-group col-sm-2">
																	<input type="hidden" name="matItemName"
																		id="matItemName${loop.index}"
																		value="${costEstimateMaterialsDtl.itemName}" /> <select
																		style="width: 205px;" class="matItemNameSelect"
																		id="matItemNameSelect${loop.index}"
																		onchange="itemLeaveChange(this)"
																		name="matItemNameSelect">
																		<option value="">${costEstimateMaterialsDtl.itemName}</option>
																		<c:if test="${!empty itemList}">
																			<c:forEach items="${itemList}" var="item">
																				<option value="${item.id}">
																					<c:out value="${item.itemName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matItemCode"
																		name="matItemCode" type="text"
																		value="${costEstimateMaterialsDtl.itemCode}"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matUom" name="matUom"
																		type="text" value="${costEstimateMaterialsDtl.uom}"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matQuantity"
																		name="matQuantity" type="text"
																		value="${costEstimateMaterialsDtl.quantity}"
																		id="matQuantity${loop.index}"
																		onblur="setMatTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matUnitPrice"
																		name="matUnitPrice" type="text"
																		value="${costEstimateMaterialsDtl.unitPrice}"
																		readonly="readonly" id="matUnitPrice${loop.index}"
																		onblur="setMatTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control matAmount"
																		id="matAmount${loop.index}" name="matAmount"
																		type="text"
																		value="${costEstimateMaterialsDtl.totalPrice}"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-2">
																	<input class="form-control" name="matRemarks"
																		type="text"
																		value="${costEstimateMaterialsDtl.remarks}"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<%-- 																		<input class="btn btn-danger btn-xs" type="button" value="Del" onclick="matHide('${loop.index}')"/> --%>
																	<button type="button" class='btn btn-xs btn-danger'
																		onclick="matHide('${loop.index}')">
																		<i class='fa fa-fw fa-times'></i>
																	</button>
																</div>
															</div>

														</div>
														<c:set var="count" value="${loop.count}" scope="page" />

													</c:forEach>


													<input type="hidden" value="${count}" id="matCount">
													<div class="col-xs-12 entry" id="myArea${count}">
														<div class="row">

															<div class="form-group col-sm-2">
																<select class="form-control matCategory"
																	id="matCategory${count}"
																	onchange="categoryLeaveChange(this)"
																	style="border: 0; border-bottom: 2px ridge;">
																	<option disabled selected>Category</option>
																	<c:if test="${!empty categoryList}">
																		<c:forEach items="${categoryList}" var="category">
																			<option value="${category.categoryId}">
																				<c:out value="${category.categoryName}" />-[${category.categoryId}]
																			</option>
																		</c:forEach>
																	</c:if>
																</select>
															</div>


															<div class="form-group col-sm-2">
																<input type="hidden" name="matItemName"
																	id="matItemName${count}" class="matItemName" /> <select
																	class="form-control matItemNameSelect"
																	id="matItemNameSelect${count}"
																	onchange="itemLeaveChange(this)"
																	name="matItemNameSelect"
																	style="border: 0; border-bottom: 2px ridge;">
																	<option disabled selected>Item Name</option>
																</select>
															</div>
															<div class="form-group col-sm-1">
																<input class="form-control matItemCode"
																	name="matItemCode" type="text" placeholder="ItemCode"
																	readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1">
																<input class="form-control matUom" name="matUom"
																	type="text" placeholder="Unit" readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1">
																<input class="form-control matQuantity"
																	name="matQuantity" type="text" placeholder="quantity"
																	value="0" step="0.01" id="matQuantity${count}"
																	onblur="setMatTotalCost(this)"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1">
																<input class="form-control matUnitPrice"
																	name="matUnitPrice" type="text"
																	placeholder="Unit Price" value="0" step="0.01"
																	readonly="readonly" id="matUnitPrice${count}"
																	onblur="setMatTotalCost(this)"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1">
																<input class="form-control matAmount"
																	id="matAmount${count}" name="matAmount" type="text"
																	placeholder="Total Amount" value="0" step="0.01"
																	readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>

															<div class="form-group col-sm-2">
																<input class="form-control" name="matRemarks"
																	type="text" placeholder="Remarks"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1">
																<!-- 																	<button class="btn btn-success btn-add btn-xs" type="button"> -->
																<!-- 																		<span class="glyphicon glyphicon-plus"></span> -->
																<!-- 																	</button> -->
																<div class="form-group col-sm-1" style='padding: 0;'>
																	<!-- 																		<input class="btn btn-add btn-success btn-xs" type="button" value="+" style='padding:0 10px 0 10px; font-weight: 800'/> -->
																	<button class='btn btn-add btn-xs btn-success'
																		type='button'>
																		<i class='fa fa-fw fa-plus'></i>
																	</button>
																</div>
															</div>
														</div>

													</div>
												</div>
												<!-- ---------------------- -->

											</div>
										</div>
									</div>
									<!-- </div> -->

									<hr />

								</div>
							</div>
						</div>

					</div>

				</div>

				<!-- 	Cost Of Installation -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Installation :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-2">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls1">
													<div class="aaa">

														<c:forEach items="${costEstimateInstallationDtlList}"
															var="costEstimateInstallationDtl" varStatus="loop">

															<div class="col-xs-12 entry" id="costIns${loop.index}">
																<div class="row">

																	<div class="form-group col-sm-2"></div>


																	<div class="form-group col-sm-2">
																		<input type="hidden" name="insItemName"
																			id="insItemName${loop.index}"
																			value="${costEstimateInstallationDtl.itemName}" /> <select
																			style="width: 200px;" class="insItemNameSelect"
																			id="insItemNameSelect${loop.index}"
																			onchange="itemLeaveChange1(this)"
																			name="insItemNameSelect">
																			<option value="">${costEstimateInstallationDtl.itemName}</option>
																			<c:if test="${!empty itemList}">
																				<c:forEach items="${itemList}" var="item">
																					<option value="${item.id}">
																						<c:out value="${item.itemName}" /></option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>

																	<div class="form-group col-sm-1">
																		<input class="form-control insUom" name="insUom"
																			type="text"
																			value="${costEstimateInstallationDtl.uom}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control insQuantity"
																			name="insQuantity" type="text"
																			value="${costEstimateInstallationDtl.quantity}"
																			id="InsQuantity${loop.index}"
																			onblur="setInsTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control insUnitPrice"
																			name="insUnitPrice" type="text"
																			value="${costEstimateInstallationDtl.unitPrice}"
																			readonly="readonly" id="insUnitPrice${loop.index}"
																			onblur="setInsTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control insAmount"
																			id="insAmount${loop.index}" name="insAmount"
																			type="text"
																			value="${costEstimateInstallationDtl.totalPrice}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>

																	<div class="form-group col-sm-2">
																		<input class="form-control" name="insRemarks"
																			type="text"
																			value="${costEstimateInstallationDtl.remarks}"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<%-- 																		<input class="btn btn-danger btn-xs" type="button" value="Del" onclick="matHide('${loop.index}')"/> --%>
																		<button type="button" class='btn btn-xs btn-danger'
																			onclick="insHide('${loop.index}')">
																			<i class='fa fa-fw fa-times'></i>
																		</button>
																	</div>
																</div>

															</div>
															<c:set var="insCount" value="${loop.count}" scope="page" />

														</c:forEach>


														<input type="hidden" value="${insCount}" id="insCount">

														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry1" id="costIns${insCount}">
															<div class="row">

																<div class="form-group col-sm-2">
																	<select class="form-control insCategory"
																		id="insCategory${insCount}" name="insCategory"
																		onchange="categoryLeaveChange1(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					<c:out value="${category.categoryName}" />-[${category.categoryId}]
																				</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>


																<div class="form-group col-sm-2">
																	<input type="hidden" name="insItemName"
																		id="insItemName${insCount}" class="insItemName" /> <select
																		class="form-control insItemNameSelect"
																		id="insItemNameSelect${insCount}"
																		onchange="itemLeaveChange1(this)"
																		name="insItemNameSelect"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control insUom" name="insUom"
																		type="text" placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control insQuantity"
																		id="insQuantity${insCount}" name="insQuantity"
																		type="text" placeholder="Quantity" value="0"
																		step="0.01" onblur="setInsTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control insUnitPrice"
																		id="insUnitPrice${insCount}" name="insUnitPrice"
																		type="text" placeholder="Unit Price" value="0"
																		step="0.01" readonly="readonly"
																		onblur="setInsTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control insAmount"
																		id="insAmount${insCount}" name="insAmount" type="text"
																		placeholder="Total Amount" value="0" step="0.01"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-2">
																	<input class="form-control" name="insRemarks"
																		type="text" placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- <div class="form-group col-sm-1">

																	<button class="btn btn-success btn-add-cost-ins" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>

																</div> -->
																<div class="form-group col-sm-1">
																	<div class="form-group col-sm-1" style='padding: 0;'>
																		<!-- 																		<input class="btn btn-add2 btn-success btn-xs" type="button" value="+" style='padding:0 10px 0 10px; font-weight: 800'/> -->
																		<button
																			class='btn btn-add-cost-ins btn-xs btn-success'
																			type='button'>
																			<i class='fa fa-fw fa-plus'></i>
																		</button>
																	</div>
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

				<!-- 	Cost Of Recovery -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Recovery :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">

										<div class="form-group col-sm-2">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls2">
													<div class="aaa">

														<c:forEach items="${costEstimateRecoveryDtlList}"
															var="costEstimateRecoveryDtl" varStatus="loop">

															<div class="col-xs-12 entry" id="myAreaa${loop.index}">
																<div class="row">

																	<div class="form-group col-sm-2"></div>


																	<div class="form-group col-sm-3">
																		<input type="hidden" name="recItemName"
																			id="recItemName${loop.index}"
																			value="${costEstimateRecoveryDtl.itemName}" /> <select
																			style="width: 310px;" class="recItemNameSelect"
																			id="recItemNameSelect${loop.index}"
																			onchange="itemLeaveChange1(this)"
																			name="recItemNameSelect">
																			<option value="">${costEstimateRecoveryDtl.itemName}</option>
																			<c:if test="${!empty itemList}">
																				<c:forEach items="${itemList}" var="item">
																					<option value="${item.id}">
																						<c:out value="${item.itemName}" /></option>
																				</c:forEach>
																			</c:if>
																		</select>
																	</div>

																	<div class="form-group col-sm-1">
																		<input class="form-control recUom" name="recUom"
																			type="text" value="${costEstimateRecoveryDtl.uom}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control recQuantity"
																			name="recQuantity" type="text"
																			value="${costEstimateRecoveryDtl.quantity}"
																			id="recQuantity${loop.index}"
																			onblur="setRecTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control recUnitPrice"
																			name="recUnitPrice" type="text"
																			value="${costEstimateRecoveryDtl.unitPrice}"
																			readonly="readonly" id="recUnitPrice${loop.index}"
																			onblur="setRecTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control recAmount"
																			id="recAmount${loop.index}" name="recAmount"
																			type="text"
																			value="${costEstimateRecoveryDtl.totalPrice}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>

																	<div class="form-group col-sm-2">
																		<input class="form-control" name="recRemarks"
																			type="text"
																			value="${costEstimateRecoveryDtl.remarks}"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1"></div>
																	<div class="form-group col-sm-1">
																		<%-- 																		<input class="btn btn-danger btn-xs" type="button" value="Del" onclick="matHide('${loop.index}')"/> --%>
																		<button type="button" class='btn btn-xs btn-danger'
																			onclick="recHide('${loop.index}')">
																			<i class='fa fa-fw fa-times'></i>
																		</button>
																	</div>
																</div>

															</div>
															<c:set var="recCount" value="${loop.count}" scope="page" />

														</c:forEach>


														<input type="hidden" value="${recCount}" id="recCount">


														<div class="col-xs-12 entry2" id="myAreaa${recCount}">
															<div class="row">
																<div class="form-group col-sm-2">
																	<select class="form-control recCategory"
																		id="recCategory${recCount}" name="recCategory"
																		onchange="categoryLeaveChange2(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					<c:out value="${category.categoryName}" />-[${category.categoryId}]
																				</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>


																<div class="form-group col-sm-3">
																	<input type="hidden" name="recItemName"
																		id="recItemName${recCount}" class="recItemName" /> <select
																		class="form-control recItemNameSelect"
																		id="recItemNameSelect${recCount}"
																		onchange="itemLeaveChange2(this)"
																		name="recItemNameSelect"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>

																<div class="form-group col-sm-1">
																	<input class="form-control recUom"
																		id="recUom${recCount}" name="recUom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control recQuantity"
																		id="recQuantity${recCount}" name="recQuantity"
																		type="number" value="0" step="0.01"
																		placeholder="quantity" onblur="setRecTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control recUnitPrice"
																		id="recUnitPrice${recCount}" name="recUnitPrice"
																		type="number" value="0" step="0.01"
																		readonly="readonly" placeholder="Unit Price"
																		onblur="setRecTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control recAmount"
																		id="recAmount${recCount}" name="recAmount"
																		placeholder="Total Amount" readonly="readonly"
																		type="number" value="0" step="0.01"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-2">
																	<input class="form-control recRemarks"
																		name="recRemarks" type="text" placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- <div class="form-group col-sm-1">

																	<button class="btn btn-success btn-add-cost-ins" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>

																</div> -->
																<div class="form-group col-sm-1">
																	<div class="form-group col-sm-1" style='padding: 0;'>
																		<!-- 																		<input class="btn btn-add2 btn-success btn-xs" type="button" value="+" style='padding:0 10px 0 10px; font-weight: 800'/> -->
																		<button class='btn btn-add2 btn-xs btn-success'
																			type='button'>
																			<i class='fa fa-fw fa-plus'></i>
																		</button>
																	</div>
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

				<!-- 	Cost Of Miscellanious -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<div class="btn-primary btn-sm">Cost Of Miscellanious :</div>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1330px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-3">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Quantity</b>
										</div>
										<div class="form-group col-sm-1">
											<b>Unit Price in TK.</b>
										</div>
										<div class="form-group col-sm-2">
											<b>Total Amount in TK.</b>
										</div>
										<div class="form-group col-sm-3">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls3">
													<div class="aaa">

														<c:forEach items="${costEstimateMiscellaniousDtlList}"
															var="costEstimateMiscellaniousDtl" varStatus="loop">

															<div class="col-xs-12 entry" id="myArea3${loop.index}">
																<div class="row">



																	<div class="form-group col-sm-3">
																		<input type="text" name="misItemName"
																			id="misItemName${loop.index}"
																			value="${costEstimateMiscellaniousDtl.itemName}" />
																		<%-- <select style="width: 310px;" class="misItemNameSelect" id="misItemNameSelect${loop.index}"
																			onchange="itemLeaveChange1(this)" name="misItemNameSelect">
																			<option value="">${costEstimateMiscellaniousDtl.itemName}</option>
																			<c:if test="${!empty itemList}">
																				<c:forEach items="${itemList}" var="item">
																					<option value="${item.id}">
																						<c:out value="${item.itemName}" /></option>
																				</c:forEach>
																			</c:if>
																		</select> --%>
																	</div>

																	<div class="form-group col-sm-1">
																		<input class="form-control recUom" name="misUom"
																			type="text"
																			value="${costEstimateMiscellaniousDtl.uom}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control misQuantity"
																			name="misQuantity" type="text"
																			value="${costEstimateMiscellaniousDtl.quantity}"
																			id="misQuantity${loop.index}"
																			onblur="setMisTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1">
																		<input class="form-control misUnitPrice"
																			name="misUnitPrice" type="text"
																			value="${costEstimateMiscellaniousDtl.unitPrice}"
																			readonly="readonly" id="misUnitPrice${loop.index}"
																			onblur="setMisTotalCost(this)"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-2">
																		<input class="form-control misAmount"
																			id="misAmount${loop.index}" name="misAmount"
																			type="text"
																			value="${costEstimateMiscellaniousDtl.totalPrice}"
																			readonly="readonly"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>

																	<div class="form-group col-sm-3">
																		<input class="form-control" name="misRemarks"
																			type="text"
																			value="${costEstimateMiscellaniousDtl.remarks}"
																			style="border: 0; border-bottom: 2px ridge;" />
																	</div>
																	<div class="form-group col-sm-1"></div>
																	<div class="form-group col-sm-1">
																		<%-- 																		<input class="btn btn-danger btn-xs" type="button" value="Del" onclick="matHide('${loop.index}')"/> --%>
																		<button type="button" class='btn btn-xs btn-danger'
																			onclick="misHide('${loop.index}')">
																			<i class='fa fa-fw fa-times'></i>
																		</button>
																	</div>
																</div>

															</div>
															<c:set var="misCount" value="${loop.count}" scope="page" />

														</c:forEach>


														<input type="hidden" value="${misCount}" id="misCount">


														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry3" id="myArea3${misCount}">
															<div class="row">
																<div class="form-group col-sm-3">
																	<input class="form-control" type="text"
																		id="misItemName${misCount}" name="misItemName"
																		value="11KV Shut Down Fee"
																		onBlur="showMiscUom(this.id);"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1">
																	<input class="form-control misUom"
																		id="misUom${misCount}" name="misUom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control misQuantity"
																		id="misQuantity${misCount}" name="misQuantity"
																		type="text" placeholder="quantity" value="0"
																		step="0.01" onblur="setMisTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1">
																	<input class="form-control misUnitPrice"
																		id="misUnitPrice${misCount}" name="misUnitPrice"
																		type="text" placeholder="Unit Price" value="0"
																		step="0.01" onblur="setMisTotalCost(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2">
																	<input class="form-control misAmount"
																		id="misAmount${misCount}" name="misAmount" type="text"
																		placeholder="Total Amount" value="0" step="0.01"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-3">
																	<input class="form-control" name="misRemarks"
																		type="text" placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- <div class="form-group col-sm-1">

																	<button class="btn btn-success btn-add-cost-ins" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>

																</div> -->
																<div class="form-group col-sm-1">
																	<div class="form-group col-sm-1" style='padding: 0;'>
																		<!-- 																		<input class="btn btn-add3 btn-success btn-xs" type="button" value="+" style='padding:0 10px 0 10px; font-weight: 800'/> -->
																		<button class='btn btn-add3 btn-xs btn-success'
																			type='button'>
																			<i class='fa fa-fw fa-plus'></i>
																		</button>
																	</div>
																</div>

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
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/jobTemplateEditForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>