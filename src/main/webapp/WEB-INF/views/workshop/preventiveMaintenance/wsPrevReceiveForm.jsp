<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/prev/prevReceiveList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Received Transformer List
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Receive Transformer Form</h2>
			
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
			
		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/prev/prevReceiveSave.do" enctype="multipart/form-data">
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
				
					<div class="form-group">
						<label for="receiveFrom" class="col-sm-4 control-label">Received From: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="receiveFrom"
								value="Executive Engineer"
								style="border: 0; border-bottom: 2px ridge;"
								name="receiveFrom" /><input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
						
						</div>
					</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="referenceNumber" class="col-sm-4 control-label">Reference Number: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="referenceNumber"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="referenceNumber" />						
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="deptId" class="col-sm-4 control-label">Department:</label>
						<div class="col-sm-8">
							<select class="form-control deptId" id="deptId" name="deptName">
																		<option value="" disabled selected>Department</option>
																		<c:if test="${!empty departmentList}">
																			 <c:forEach items="${departmentList}" var="department">
																				<option value="${department.deptId}">
																					<c:out value="${department.deptName}" /></option>
																			</c:forEach> 
																		</c:if>
																	</select>
						</div>
					</div> 
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="referenceDoc"
							class="col-sm-4 control-label">Reference
							Doc. :</label>
						<div class="col-sm-8">
							<input type="file" id="referenceDoc" accept="application/pdf"
								class='form-control'
								style="border: 0; border-bottom: 2px ridge;" name="referenceDoc" />
						</div>
						
					</div>
					
				</div>

				<div class="col-md-6 col-sm-6">
				<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="receiveDate" class="col-sm-4 col-md-4 control-label">Receive
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="receiveDate" value=""
								style="border: 0; border-bottom: 2px ridge;" name="receiveDate" />
						</div>
					</div>
			<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Contract
							No : </label>
						<div class="col-sm-8">
							<select class="form-control workOrderNumber" name="workOrderNumber"
								id="workOrderNumber" style="border: 0; border-bottom: 2px ridge;" >
								<option disabled selected>Contract No</option>
								<c:if test="${!empty contractorList}">
									<c:forEach items="${contractorList}" var="contractor">
										<option value="${contractor.contractNo}">
											<c:out value="${contractor.contractNo}" /></option>
									</c:forEach>
								</c:if>
							</select>

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="zone" class="col-sm-4 control-label">Area/Zone :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="zone"
								placeholder="" value="X-Former Workshop" style="border: 0; border-bottom: 2px ridge;"
								name="zone">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="referenceDoc"
							class="col-sm-4 control-label">Remarks :</label>
						<div class="col-sm-8">
																	<input class="form-control remark" name="remark"
																		type="text" placeholder="Remarks" id="remark"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
					</div>
					
				</div>
				
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<p class="col-sm-12 btn btn-primary btn-sm">Purchased
							Item List</p>
						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1700px;">
									<div class="col-xs-12">
										<div class="form-group col-xs-2">
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
										<div class="form-group col-xs-2">
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
																<div class="form-group col-xs-2">
																	<select class="form-control category" id="category0"
																		onchange="categoryLeaveChange(this)" name="category"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					<c:out value="${category.categoryName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-xs-2">
																	<input type="hidden" name="itemName"
																		id="description0" class="description" /> <select
																		class="form-control itemName" id="itemName0"
																		onchange="itemLeaveChange(this)" name="description"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control itemCode" name="itemCode"
																		id="itemCode0" type="text" placeholder="itemCode"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
																	<input class="form-control uom" name="uom" type="text"
																		id="uom0" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-xs-1">
																	<input class="form-control quantity" name="quantity"
																		type="number" value="0" step="0.01" id="quantity0" onblur="validQty(this);"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																
																<div class="form-group col-xs-2">
																	<input class="form-control remarks" name="remarks"
																		type="text" placeholder="Remarks" id="remarks0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-1">
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
						<button type="submit" style="margin-right: 10px; border-radius: 6px;"
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
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/preventiveMaintenance/wsPrevReceiveForm.js"></script> 
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>