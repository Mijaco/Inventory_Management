<%@include file="../../common/settingsHeader.jsp"%>
<!--End of Header -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<style type="text/css">
.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;"
	id="">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/mps/dn/demandNoteList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requirement List
			</a>

		</div>
		<br>
		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Requirement Note Details</h2>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<c:if test="${!empty demandNoteDb}">
			<div class="table-responsive">
				<!-- --------------------- -->
				<div class="alert alert-warning hide">
					<strong>Congratulation!</strong> Information is updated
					successfully.
				</div>
				<div class="alert alert-danger hide">
					<strong>Sorry!</strong> Information update is failed!!!.
				</div>
				<!-- --------------------- -->
				<table class="table table-bordered table-hove">
					<tbody>
						<tr>
							<td class="success">Requirement Note No:</td>
							<td class="">${demandNoteDb.demandNoteNo}<input
								id="contextPath" value="${pageContext.request.contextPath}"
								type="hidden" /> <input id="demandNoteMstId"
								value="${demandNoteDb.id}" type="hidden" />
							</td>
							<td class="success">Requested Department:</td>
							<td class="">${demandNoteDb.senderName}</td>
						</tr>

						<tr>
							<td class="success">Requirement Date:</td>
							<td class=""><fmt:formatDate
									value="${demandNoteDb.demandDate}" pattern="dd-MM-yyyy" /></td>
							<td class="success">Requirement Type:</td>
							<td class="">${demandNoteDb.annexureType=='1'?'Coded Items':'Non-Coded Items'}</td>
						</tr>

						<tr>
							<td class="success">Uploaded Document:</td>
							<td class=""><c:if
									test="${!empty demandNoteDb.referenceDoc}">
									<form target="_blank"
										action="${pageContext.request.contextPath}/demandNote/downloadDemandNote.do"
										method="POST">
										<input type="hidden" value="${demandNoteDb.referenceDoc}"
											name="referenceDoc" />
										<button type="submit" class="fa fa-file-pdf-o red center"
											aria-hidden="true" style="font-size: 1.5em;"></button>
									</form>
								</c:if></td>
							<td class="success">Created By:</td>
							<td class="">${demandNoteDb.createdBy}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${empty demandNoteDtlList}">
			<h4 class="text-center green" style="font-weight: bold;">Sorry!
				No data found in database!!</h4>
		</c:if>
		<c:if test="${!empty demandNoteDtlList}">
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="demandNoteDtl">
					<thead>
						<tr style="background: #579EC8; color: #fff;">
							<th>Item Type</th>
							<th>Item Code</th>
							<th>Item Name</th>
							<th>Unit</th>
							<c:if test="${demandNoteDb.annexureType  == '2'}">
								<th>Existing Qty.</th>
							</c:if>
							<th>Required Qty</th>
							<th>${demandNoteDb.annexureType  == '2' ? 'Rate' : 'Est. Unit Cost'}

							</th>
							<th>${demandNoteDb.annexureType  == '2' ? 'Amount' : 'Est. Total Cost'}
							</th>
							<c:if test="${demandNoteDb.annexureType  != '2'}">
								<th>Consumption <br />in Prev. Year
								</th>
							</c:if>

							<c:if test="${empty demandNoteDb.confirm}">
								<th>Action</th>
							</c:if>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${demandNoteDtlList}" var="demandNoteDtl"
							varStatus="loop">
							<tr>
								<td>${demandNoteDtl.itemMaster.itemType =='C' ? 'System Materials':'General Items'}</td>
								<td>${demandNoteDtl.itemMaster.itemId}<input type="hidden"
									value="${demandNoteDtl.id}" id="demandNoteDtlId${loop.index}" />
								</td>
								<td>${demandNoteDtl.itemMaster.itemName}</td>
								<td>${demandNoteDtl.itemMaster.unitCode}</td>
								<c:if test="${demandNoteDb.annexureType  == '2'}">
									<td>${demandNoteDtl.existingQty}</td>
								</c:if>
								<td><input type="number" step="0.01" style="width: 100%"
									onblur="setTotalCost2(this)" name="rQty" id="rQty${loop.index}"
									readonly="readonly" value="${demandNoteDtl.requiredQunatity}" />
									<%-- ${demandNoteDtl.requiredQunatity} --%></td>
								<td><input type="number" step="0.01" style="width: 100%"
									onblur="setTotalCost2(this)" name="eUnitCost"
									id="eUnitCost${loop.index}" readonly="readonly"
									value="${demandNoteDtl.estimateUnitCost}" /> <%-- ${demandNoteDtl.estimateUnitCost} --%></td>
								<td><input type="number" step="0.01" style="width: 100%"
									name="eTotalCost" id="eTotalCost${loop.index}"
									readonly="readonly" value="${demandNoteDtl.estimateTotalCost}" />
									<%-- ${demandNoteDtl.estimateTotalCost} --%></td>
								<c:if test="${demandNoteDb.annexureType  != '2'}">
									<td><input type="number" step="0.01" style="width: 100%"
										name="pYearConsumption" id="pYearConsumption${loop.index}"
										readonly="readonly"
										value="${demandNoteDtl.previousYearConsumption}" /> <%-- ${demandNoteDtl.previousYearConsumption} --%></td>
								</c:if>
								<c:if test="${empty demandNoteDb.confirm}">
									<td class="center">

										<button class="btn btn-xs btn-primary"
											id="btnEditDtl${loop.index}" style="border-radius: 6px;"
											onclick="editDtl(${loop.index})">
											<i class="fa fa-fw fa-edit"></i>&nbsp; Edit
										</button>
										<button class="btn btn-xs btn-success hide"
											id="btnUpdateDtl${loop.index}" style="border-radius: 6px;"
											onclick="updateDtl(${loop.index})">
											<i class="fa fa-check"></i>&nbsp; Update
										</button>
										<button class="btn btn-xs btn-danger"
											style="border-radius: 6px;"
											onclick="confirmDelete(${loop.index})">
											<i class="fa fa-fw fa-trash-o"></i>&nbsp; Delete
										</button>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- ------- -->
				<c:if test="${empty demandNoteDb.confirm}">
					<div class="col-xs-12 align-right">
						<button class="btn btn-xs btn-success"
							style="border-radius: 6px; margin-right: 10px;"
							onclick="openDialoge()">
							<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Item
						</button>
					</div>
				</c:if>
				<!-- ------- -->
			</div>
		</c:if>
		<c:if test="${empty demandNoteDb.confirm}">
			<div class="col-sm-12" style="padding-top: 15px;">
				<div class="col-sm-12 center">
					<button type="button" id="confirmButton"
						style="margin-right: 10px; border-radius: 6px;"
						class="btn btn-danger btn-lg">
						<i class="glyphicon glyphicon-ok-circle fa-1x"></i> <span class="">Confirm</span>
					</button>
				</div>
			</div>
		</c:if>

	</div>

</div>

<!-- -------------------------- -->
<div id="myDialogeBox" class="hide">
	<div class="col-md-12 col-sm-12">
		<form
			action="${pageContext.request.contextPath}/mps/dn/saveNewCodedDemandNoteDtl.do"
			id="saveNewDemandDtlForm" method="post">
			<input name="id" value="${demandNoteDb.id}" type="hidden" /> <input
				type="hidden" name="" value="" />
			<div class="form-group" style="margin-top: 1em;">
				<p class="col-sm-12 btn btn-primary btn-sm">Add New Item For
					Requirement Note</p>
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
									<b>Required Qty</b>
								</div>

								<div class="form-group col-xs-1">
									<b>Est. Unit Cost</b>
								</div>
								<div class="form-group col-xs-1">
									<b>Est. Total Cost</b>
								</div>
								<div class="form-group col-xs-2">
									<b>Consumption in Prev. Year </b>
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
															<input type="hidden" name="itemName" id="description0"
																class="description" /> <select
																class="form-control itemName" id="itemName0"
																onchange="itemLeaveChange(this)" name=""
																required="required"
																style="border: 0; border-bottom: 2px ridge;">
																<option value="">Select Item Name</option>
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
															<input class="form-control requiredQunatity"
																name="requiredQunatity" id="requiredQunatity0"
																type="number" step="0.001" onblur="setTotalCost(this)"
																required="required"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>

														<div class="form-group col-xs-1">
															<input class="form-control unitCost" name="unitCost"
																type="number" step="0.01" id="unitCost0"
																onblur="setTotalCost(this)" required="required"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-xs-1">
															<input class="form-control totalCost" name="totalCost"
																type="number" value="0" step="0.01" id="totalCost0"
																readonly="readonly"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-xs-2">
															<input class="form-control previousYearConsumption"
																name="previousYearConsumption" type="text" value="0"
																id="previousYearConsumption0"
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
		</form>
	</div>
</div>
<!-- -------------------------- -->

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/demandNoteShow.js"></script>

<%@include file="../../common/ibcsFooter.jsp"%>