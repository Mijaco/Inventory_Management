<%@include file="../../common/mpsHeader.jsp"%>
<!-- ----------------------------------------- -->
<!-- Author: Ihteshamul Alam, IBCS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_multiselect/bootstrap-multiselect.css" />

<style>
textarea {
	resize: none;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/mps/dn/demandNoteList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Annexure List
			</a> --%>
		</div>
		<div class="col-md-8">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				APP Package Entry Form</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form
			action="${pageContext.request.contextPath}/mps/procurementPackageMstInfoSave.do"
			method="POST">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-2 success text-right">Package Type:</td>
							<td class="col-xs-2"><select id="packageType"
								required="required" name="packageType" required="required"
								class="form-control">
									<option value="">Select Package Type</option>
									<option value="1">GOODS</option>
									<option value="2">WORKS &amp; SERVICES</option>
									<option value="3">MISCELLANEOUS</option>
									<%-- <c:if test="${!empty procurementPackageType}">
										<c:forEach var="procurementPackage"
											items="${procurementPackageType}">
											<option value="${procurementPackage.title}">${procurementPackage.title}</option>
										</c:forEach>
									</c:if> --%>
							</select></td>
							<td class="col-xs-2 success text-right">Financial Year:</td>
							<td class="col-xs-2"><select class="form-control"
								required="required" required="required" id="sessionId"
								name="sessionId">
									<option value="">Select Financial Year</option>
									<c:if test="${!empty descoSessions}">
										<c:forEach var="descoSession" items="${descoSessions}">
											<option value="${descoSession.id}">${descoSession.sessionName}</option>
										</c:forEach>
									</c:if>
							</select></td>

							<td class="col-xs-2 success text-right">Annexure No:</td>
							<td class="col-xs-2"><input type="text" class="form-control"
								required="required" id="annexureNo" onblur="checkAnnexureNo()" name="annexureNo" />
								<strong class="text-danger annexure hide">Annexure no. is used.</strong>
							</td>
						</tr>
						<tr>
							<td class="col-xs-2 success text-right">Package Name:</td>
							<td class="col-xs-2"><select id="packageName"
								required="required" name="packageName" required="required"
								class="form-control">
									<option value="">Select Name</option>
									<c:if test="${!empty procurementPackageName}">
										<c:forEach var="procurementPackage"
											items="${procurementPackageName}">
											<option value="${procurementPackage.title}">${procurementPackage.title}</option>
										</c:forEach>
									</c:if>
							</select></td>

							<td class="col-xs-2 success text-right">Quantity:</td>
							<td class="col-xs-2">
								<div class="input-group">
									<span class="input-group-addon" style="padding: 0;"
										id="basic-addon1"> <select id="qtyFlag" name="qtyFlag">
											<option value="1">nos</option>
											<option value="0">etc.</option>

									</select></span> <input type="number" step="0.01"
										aria-describedby="basic-addon1" id="procurementQty"
										name="procurementQty" class="form-control" />
								</div>
							</td>
							<td class="col-xs-2 success text-right">Type of Tender:</td>
							<td class="col-xs-2"><select id="procType" name="procType"
								required="required" required="required" class="form-control">
									<option value="">Select Tender Type</option>
									<option value="ssse">Single Stage Single Envelop</option>
									<option value="sste">Single Stage Two Envelope</option>
							</select></td>
						</tr>

						<tr>
							<td class="col-xs-2 success text-right">Source of Fund:</td>
							<td class="col-xs-2"><select id="sourceOfFund"
								required="required" required="required" name="sourceOfFund"
								class="form-control">
									<option value="">Select Source of Fund</option>
									<c:if test="${!empty sourceOfFund}">
										<c:forEach var="sourceFund" items="${sourceOfFund}">
											<option value="${sourceFund.title}">${sourceFund.title}</option>
										</c:forEach>
									</c:if>
							</select></td>
							<td class="col-xs-2 success text-right">Medium:</td>
							<td class="col-xs-2"><select id="procurementMedium"
								name="procurementMedium" required="required"
								class="form-control">
									<option value="">Select Medium</option>
									<c:if test="${!empty procurementMedium}">
										<c:forEach var="procMedium" items="${procurementMedium}">
											<option value="${procMedium.title}">${procMedium.title}</option>
										</c:forEach>
									</c:if>
							</select></td>
							<td class="col-xs-2 success text-right">Approving Authority:</td>
							<td class="col-xs-2"><select id="approvingAuth"
								required="required" required="required" name="approvingAuth"
								class="form-control">
									<option value="">Select Approving Authority</option>
									<c:if test="${!empty approvingAuth}">
										<c:forEach var="approvingAuthority" items="${approvingAuth}">
											<option value="${approvingAuthority.title}">${approvingAuthority.title}</option>
										</c:forEach>
									</c:if>
							</select></td>
						</tr>



						<tr>
							<td class="col-xs-2 success text-right">Estimated Cost:</td>
							<td class="col-xs-2"><input type="number" step="0.01"
								readonly="readonly" required="required" id="estimatedCost"
								name="estimatedCost" class="form-control"></td>
							<td class="col-xs-2 success text-right">Procurement Method:</td>
							<td class="col-xs-2"><select id="procMethod"
								multiple="multiple" required="required" name="procMethod"
								class="form-control">
									<!-- <option value="">Select Procurement Method</option> -->
									<c:if test="${!empty procurementMethod}">
										<c:forEach var="pm" items="${procurementMethod}">
											<option value="${pm.title}">${pm.title}</option>
										</c:forEach>
									</c:if>
							</select></td>
							<td class="col-xs-2 success text-right">Reason of
								Procurement:</td>
							<td class="col-xs-2"><textarea id="reasonsOfProc"
									required="required" name="reasonsOfProc" class="form-control"></textarea></td>
							<!-- <td class="col-xs-2 success text-right">Description</td>
							<td class="col-xs-2"><textarea id="procDescription"
									name="procDescription" class="form-control"></textarea></td> -->
						</tr>


					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12">
				<div class="form-group" style="margin-top: 1em;">
					<p class="col-sm-12 btn btn-primary btn-sm">Materials List</p>
					<div class="col-xs-12 table-responsive">
						<div class="table">
							<div style="width: 1600px;">
								<div class="col-xs-12">
									<div class="form-group col-xs-2">
										<b>Category</b>
									</div>
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
										<b>Quantity</b>
									</div>

									<div class="form-group col-xs-1">
										<b>Unit Cost</b>
									</div>
									<div class="form-group col-xs-2">
										<b>Total Cost</b>
									</div>
									<div class="form-group col-xs-1">
										<b>Add / Remove</b>
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
																			${category.categoryId}	${category.categoryName}</option>
																		</c:forEach>
																	</c:if>
																	<option value="others">Others</option>
																</select>
															</div>

															<div class="form-group col-xs-3">
																<input type="hidden" name="itemName"
																	id="description0" required="required"
																	style="border: 0; border-bottom: 2px ridge;"
																	class="form-control description" /> 
																	
																	<select
																	class="form-control itemName" id="itemName0"
																	onchange="itemLeaveChange(this)" name=""
																	style="border: 0; border-bottom: 2px ridge;">
																	<option disabled selected>Item Name</option>
																</select>
															</div>
															<div class="form-group col-xs-1">
																<input class="form-control itemCode" name="itemCode"
																	id="itemCode0" type="text" placeholder="Item Code"
																	readonly="readonly" value=" "
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-xs-1">
																<input class="form-control unit" name="unit" type="text"
																	id="unit0" readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-xs-1">
																<input class="form-control qunatity" name="qunatity"
																	id="qunatity0" type="number"  required="required"
																	onblur="setTotalCost(this)" step="0.001"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>

															<div class="form-group col-xs-1">
																<input class="form-control unitCost" name="unitCost"
																	type="number" onblur="setTotalCost(this)" 
																	step="0.01" id="unitCost0"  required="required"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-xs-2">
																<input class="form-control totalCost" name="totalCost"
																	type="number" step="0.01" id="totalCost0"  required="required"
																	style="border: 0; border-bottom: 2px ridge;" readonly>
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
			<div class="col-sm-12 center" style="margin: 5px 0 15px 0;">
				<button type="submit" class="btn btn-success btn-md" id='saveButton' disabled
					style="border-radius: 6px;">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div>
		</form>
	</div>
</div>

<!-- Initialize the plugin: -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap_multiselect/bootstrap-multiselect.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#procMethod').multiselect({
			// enableFiltering : true,
			includeSelectAllOption : true,
			maxHeight : 150,
			dropUp : true
		});
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/procurementPackage.js"></script>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>