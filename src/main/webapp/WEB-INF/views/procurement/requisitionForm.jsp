<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / New
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requisition List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/procurement/requisition/save.do">
			<div class="oe_title">



				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="prfNo" class="col-sm-4 control-label">PRF No.</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="prfNo"
								placeholder="${prfNo}" value="${prfNo}"
								style="border: 0; border-bottom: 2px ridge;" name="prfNo" />

						</div>
					</div>

					<div class="form-group">
						<label for="requisitionOfficer" class="col-sm-4 control-label">Requisition
							By</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="requisitionOfficer"
								value='<sec:authentication property="principal.username" />'
								placeholder="admin" style="border: 0; border-bottom: 2px ridge;"
								name="requisitionOfficer" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="requisitionTo" class="col-sm-4 control-label">Requisition
							To</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="requisitionTo"
								value="Purchase & Procurement Office" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="requisitionTo">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="requisitionDate"
							class="col-sm-4 col-md-4 control-label">Req. Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="requisitionDate"
								readonly="readonly" style="border: 0; border-bottom: 2px ridge;"
								name="requisitionDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="departmentFrom"
							class="col-sm-4 col-md-4 control-label">Requisition From</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="departmentFrom"
								value="Finance & Accounts" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="departmentFrom" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="status" class="col-sm-4 col-md-4 control-label">Status</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="status" placeholder="INITIATED"
								value="100" style="border: 0; border-bottom: 2px ridge;"
								name="status" />
						</div>
					</div>

				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requested
							Item(s):</label>
						<div class="col-md-12 col-sm-12 col-xs-12">
							<hr />
							<div class="col-xs-12">
								<div class="row">
									<div class="control-group" id="fields">
										<div class="controls">
											<div class="aaa">
												<!-- <form role="form" autocomplete="off">  -->
												<div class="col-xs-12 entry" id="myArea0">
													<div class="row">
														<div class="form-group col-sm-3 col-xs-12">
															<input type="hidden" name="itemName"
																id="itemNameHideField" class="itemNameHideField" />
															<!-- <select class="form-control" name="itemName"  onchange="itemLeaveChange()"
																id="itemName" style="border: 0; border-bottom: 2px ridge;"> -->
															<select class="form-control itemName"
																onchange="itemLeaveChange(this)"
																style="border: 0; border-bottom: 2px ridge;">
																<option disabled selected>-- select an Item --
																</option>
																<c:if test="${!empty invItemList}">
																	<c:forEach items="${invItemList}" var="invItem">
																		<option value="${invItem.id}">
																			<c:out value="${invItem.itemName}" /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
														<div class="form-group col-sm-2 col-xs-12">
															<input class="form-control itemCode" name="itemCode"
																type="text" placeholder="Item Code"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-2 col-xs-12">
															<input class="form-control uom" name="uom" type="text"
																placeholder="UOM"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="quantity" type="text"
																placeholder="Quantity"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-3 col-xs-12">
															<input class="form-control" name="costCenter" type="text"
																placeholder="Finance & Accounts"
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

				<div class="col-md-12">
					<label for="justification" class="col-sm-4 control-label">Justification
						:</label>
					<div class="form-group col-sm-12">
						<textarea rows="3" cols="" id="justification" class="form-control"
							maxlength="1000" name="justification"></textarea>
					</div>

				</div>
				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em;">
						<button type="reset" class="width-20 pull-left btn btn-sm">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
						<button type="submit" style="margin-left: 10px;"
							class="width-20 pull-left btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

				</div>

			</div>
						
		</form>
		<!-- --------------------- -->
	</div>
</div>



<script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/requisitionForm.js"></script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>