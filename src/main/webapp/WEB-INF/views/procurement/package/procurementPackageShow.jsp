<%@include file="../../common/mpsHeader.jsp"%>
<!-- ----------------------------------------- -->
<!-- Author: Ihteshamul Alam, IBCS -->

<style>
textarea {
	resize: none;
}

.multiselect-container.dropdown-menu {
	z-index: 9999999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_multiselect/bootstrap-multiselect.css" />

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit  col-md-2">
			<a
				href="${pageContext.request.contextPath}/mps/procurementPackageMstList.do?packageType=&id=${procurementPackageMstDb.descoSession.id}"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				APP List
			</a>
		</div>		
		<div class="col-md-8">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Annexure - ${procurementPackageMstDb.annexureNo}</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<!-- Master Info Dialog Button :: start -->
		<c:if test="${empty procurementPackageMstDb.confirm}">
			<button class="btn btn-info btn-xs"
				style="border-radius: 6px; min-width: 70px;"
				onclick="openMasterDialog()">
				<i class="fa fa-fw fa-edit"></i>&nbsp;Edit Master Info.
			</button>
			<br>
			<br>
		</c:if>
		<!-- Master Info Dialog Button :: end -->

		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">

		<!-- Master Info :: start -->
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="col-xs-2 success">Package Type</td>
						<td class="col-xs-2 info">${( procurementPackageMstDb.packageType == '1' ) ? 'GOODS' : ( procurementPackageMstDb.packageType == '2' ) ? 'WORKS & SERVICES' : ( procurementPackageMstDb.packageType == '3' ) ? 'MISCELLANEOUS' : '${procurementPackageMstDb.packageType}'}</td>
						<td class="col-xs-2 success">Financial Year</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.descoSession.sessionName}</td>
						<td class="col-xs-2 success">Create By</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.createdBy}</td>
					</tr>

					<tr>
						<td class="col-xs-2 success">Package Name</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.packageName}</td>
						<td class="col-xs-2 success">Quantity</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.procurementQty}</td>
						<td class="col-xs-2 success">Type of Tender</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.procType == 'ssse' ? 'Single Stage Single Envelop' : 'Single Stage Two Envelope'}</td>
					</tr>

					<tr>
						<td class="col-xs-2 success">Source of Fund</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.sourceOfFund}</td>
						<td class="col-xs-2 success">Medium</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.procurementMedium}</td>
						<td class="col-xs-2 success">Approving Authority</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.approvingAuth}</td>
					</tr>

					<tr>
						<td class="col-xs-2 success">Estimated Cost</td>
						<td class="col-xs-2 info">
						<fmt:formatNumber type="number" groupingUsed="false" value="${procurementPackageMstDb.estimatedCost/100000}" /> Million
						<%-- ${procurementPackageMstDb.estimatedCost} --%></td>
						<td class="col-xs-2 success">Procurement Method</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.procMethod}</td>
						<td class="col-xs-2 success">Reason of Procurement</td>
						<td class="col-xs-2 info">${procurementPackageMstDb.reasonsOfProc}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- Master Info :: end -->

		<!-- Item Details :: start -->
		<c:if test="${empty procurementPackageList}">
			<h4 align="center" class="green">
				<strong>Sorry! No data found in database.</strong>
			</h4>
		</c:if>
		<c:if test="${!empty procurementPackageList}">
			<div class="table-responsive">
				<table class="table table-bordered">
					<thead>
						<tr style="background: #579EC8; color: #fff;">
							<th class="col-xs-1">Item Code</th>
							<th class="col-xs-3">Item Name</th>
							<th class="col-xs-1">Unit</th>
							<th class="col-xs-1">Quantity</th>
							<th class="col-xs-1">Unit Cost</th>
							<th class="col-xs-1">Total Cost</th>
							<c:if test="${empty procurementPackageMstDb.confirm}">
								<th class="col-xs-1">Action</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${procurementPackageList}" var="PackageList">
							<tr>
								<td>${PackageList.itemCode}</td>
								<td>${PackageList.itemName}</td>
								<td>${PackageList.unit}</td>
								<td>${PackageList.qunatity}</td>
								<td>${PackageList.unitCost}</td>
								<td>
								<fmt:formatNumber type="number" groupingUsed="false" value="${PackageList.totalCost/100000}" /> Million
								<%-- ${PackageList.totalCost} --%></td>
								<c:if test="${empty procurementPackageMstDb.confirm}">
									<td><a href="javascript:void(0)"
										class="btn btn-danger btn-xs"
										onclick="deleteItem(${PackageList.id})"
										style="border-radius: 6px;"><i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<!-- Item Details :: end -->

		<div class="col-xs-12 align-right">
			<c:if test="${empty procurementPackageMstDb.confirm}">

				<button class="btn btn-xs btn-success"
					style="border-radius: 6px; margin-right: 10px;"
					onclick="openDialoge()">
					<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Item
				</button>
				<!-- Commented By: Ashid-->
				<%-- <button class="btn btn-xs btn-warning"
					style="border-radius: 6px; margin-right: 10px;"
					onclick="postSubmit('${pageContext.request.contextPath}/mps/procurementPackageConfirm.do',{id:'${procurementPackageMstDb.id}'},'POST')">Confirm!</button> --%>
			</c:if>
			<c:if test="${!empty procurementPackageMstDb.confirm}">
				<%-- <a class="width-10  btn btn-sm btn-success"  
			style="border-radius: 6px; display: none;"
			onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/getProcurementForm.do',{id:'${procurementPackageMstDb.descoSession.id}', annexureNo:'${procurementPackageMstDb.annexureNo}'},'POST')">
			Purchase </a> --%>
				<button type="button" id="purchaseBtn${loop.index}"
					onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/getProcurementForm.do',{id:'${procurementPackageMstDb.descoSession.id}', annexureNo:'${procurementPackageMstDb.annexureNo}'},'POST')"
					style="border-radius: 6px;"
					class="width-10  btn btn-sm btn-success">Purchase</button>
			</c:if>
		</div>

	</div>
</div>

<!-- Master Info. Dialog :: start -->
<div id="masterInfoDialogeBox" class="hide">
	<div class="col-md-12 col-sm-12">
		<form
			action="${pageContext.request.contextPath}/mps/updateMasterInformations.do"
			method="POST" id="updateMasterInfo">
			<input type="hidden" id="id" name="id"
				value="${procurementPackageMstDb.id}">
			<div class="table-responsive">
				<p class="col-sm-12 btn btn-primary btn-sm">Master Information</p>
				<br> <br>
				<table class="table table-bordered">
					<tr>
						<td class="col-xs-2 success text-right">Package Type:</td>
						<td class="col-xs-2"><select id="packageType"
							required="required" name="packageType" required="required"
							class="form-control">
								<option value="1"
									${procurementPackageMstDb.packageType == '1' ? 'selected' : ''}>GOODS</option>
								<option value="2"
									${procurementPackageMstDb.packageType == '2' ? 'selected' : ''}>WORKS
									&amp; SERVICES</option>
								<option value="3"
									${procurementPackageMstDb.packageType == '3' ? 'selected' : ''}>MISCELLANEOUS</option>
						</select></td>
						<td class="col-xs-2 success text-right">Financial Year</td>
						<td class="col-xs-2"><select class="form-control"
							required="required" required="required" id="sessionId"
							name="sessionId">
								<c:if test="${!empty descoSessions}">
									<c:forEach var="descoSession" items="${descoSessions}">
										<option value="${descoSession.id}"
											${descoSession.sessionName == procurementPackageMstDb.descoSession.sessionName ? 'selected' : ''}>${descoSession.sessionName}</option>
									</c:forEach>
								</c:if>
						</select></td>
						<td class="col-xs-2 success text-right">Annexure No</td>
						<td class="col-xs-2"><input type="hidden" id="prevAnnexureNo"
							value="${procurementPackageMstDb.annexureNo}"> <input
							type="text" class="form-control" required="required"
							id="annexureNo" onblur="checkAnnexureNoFromShow()"
							name="annexureNo" value="${procurementPackageMstDb.annexureNo}">
							<strong class="text-danger annexure hide">Annexure no.
								is used.</strong></td>
					</tr>
					<tr>
						<td class="col-xs-2 success text-right">Package Name</td>
						<td class="col-xs-2"><select id="packageName"
							required="required" name="packageName" required="required"
							class="form-control">
								<option value="">Select Name</option>
								<c:if test="${!empty procurementPackageName}">
									<c:forEach var="procurementPackage"
										items="${procurementPackageName}">
										<option value="${procurementPackage.title}"
											${procurementPackage.title == procurementPackageMstDb.packageName ? 'selected' : ''}>${procurementPackage.title}</option>
									</c:forEach>
								</c:if>
						</select></td>
						<td class="col-xs-2 success text-right">Quantity</td>
						<td class="col-xs-2"><select id="qtyFlag" name="qtyFlag"
							style="width: 100%">
								<option value="1"
									${procurementPackageMstDb.qtyFlag == '1' ? 'selected' : ''}>nos</option>
								<option value="0"
									${procurementPackageMstDb.qtyFlag == '0' ? 'selected' : ''}>etc.</option>
						</select></td>
						<td class="col-xs-2 success text-right">Type of Tender</td>
						<td class="col-xs-2"><select id="procType" name="procType"
							required="required" required="required" class="form-control">
								<option value="ssse"
									${procurementPackageMstDb.procType == 'ssse' ? 'selected' : ''}>Single
									Stage Single Envelop</option>
								<option value="sste"
									${procurementPackageMstDb.procType == 'sste' ? 'selected' : ''}>Single
									Stage Two Envelope</option>
						</select></td>
					</tr>
					<tr>
						<td class="col-xs-2 success text-right">Source of Fund</td>
						<td class="col-xs-2"><select id="sourceOfFund"
							required="required" required="required" name="sourceOfFund"
							class="form-control">
								<option value="">Select Source of Fund</option>
								<c:if test="${!empty sourceOfFund}">
									<c:forEach var="sourceFund" items="${sourceOfFund}">
										<option value="${sourceFund.title}"
											${sourceFund.title == procurementPackageMstDb.sourceOfFund ? 'selected' : ''}>${sourceFund.title}</option>
									</c:forEach>
								</c:if>
						</select></td>
						<td class="col-xs-2 success text-right">Medium</td>
						<td class="col-xs-2"><select id="procurementMedium"
							name="procurementMedium" required="required" class="form-control">
								<option value="">Select Medium</option>
								<c:if test="${!empty procurementMedium}">
									<c:forEach var="procMedium" items="${procurementMedium}">
										<option value="${procMedium.title}"
											${procMedium.title == procurementPackageMstDb.procurementMedium ? 'selected' : ''}>${procMedium.title}</option>
									</c:forEach>
								</c:if>
						</select></td>
						<td class="col-xs-2 success text-right">Approving Authority</td>
						<td class="col-xs-2"><select id="approvingAuth"
							required="required" required="required" name="approvingAuth"
							class="form-control">
								<option value="">Select Approving Authority</option>
								<c:if test="${!empty approvingAuth}">
									<c:forEach var="approvingAuthority" items="${approvingAuth}">
										<option value="${approvingAuthority.title}"
											${approvingAuthority.title == procurementPackageMstDb.approvingAuth ? 'selected' : ''}>${approvingAuthority.title}</option>
									</c:forEach>
								</c:if>
						</select></td>
					</tr>
					<tr>
						<td class="col-xs-2 success text-right">Procurement Method</td>
						<td class="col-xs-2"><input type="hidden" id="procMethodVal"
							value="${procurementPackageMstDb.procMethod}"> <select
							id="procMethod" multiple="multiple" required="required"
							name="procMethod" class="form-control selectMultiple">
								<!-- <option value="">Select Procurement Method</option> -->
								<c:if test="${!empty procurementMethod}">
									<c:forEach var="pm" items="${procurementMethod}">
										<option value="${pm.title}">${pm.title}</option>
									</c:forEach>
								</c:if>
						</select></td>
						<td class="col-xs-2 success text-right">Reason of Procurement</td>
						<td class="col-xs-2"><textarea id="reasonsOfProc"
								required="required" name="reasonsOfProc" class="form-control">${procurementPackageMstDb.reasonsOfProc}</textarea>
						</td>
						<td class="col-xs-2" colspan="2" align="center">
							<button id="saveButton" type="submit"
								class="btn btn-success btn-lg" style="border-radius: 6px;">
								<i class="fa fa-fw fa-repeat"></i>&nbsp;Update
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<!-- Master Info. Dialog :: end -->

<!-- Dialog -->
<div id="myDialogeBox" class="hide">
	<div class="col-md-12 col-sm-12">
		<form
			action="${pageContext.request.contextPath}/mps/saveMultipleItems.do"
			id="saveMultipleProcurementPackage" method="post">

			<input name="id" value="${procurementPackageMstDb.id}" type="hidden">

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
																		${category.categoryId}- ${category.categoryName} </option>
																	</c:forEach>
																</c:if>
																<option value="others">Others</option>
															</select>
														</div>

														<div class="form-group col-xs-3">
															<input type="hidden" name="itemName" id="description0"
																required="required"
																style="border: 0; border-bottom: 2px ridge;"
																class="form-control description" /> <select
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
																id="qunatity0" type="number" required="required"
																onblur="setTotalCost(this)" step="0.001"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>

														<div class="form-group col-xs-1">
															<input class="form-control unitCost" name="unitCost"
																type="number" onblur="setTotalCost(this)" step="0.01"
																id="unitCost0" required="required"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-xs-2">
															<input class="form-control totalCost" name="totalCost"
																type="number" step="0.01" id="totalCost0"
																required="required"
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
		</form>
	</div>
</div>

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
		
		var value = [];
		var values = $('#procMethodVal').val().split(',');
		for( var i = 0; i < values.length; i++ ) {
			value.push( values[i] );
		}
		$('#procMethod').val(value);
		$("#procMethod").multiselect("refresh");
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/procurementPackageShow.js"></script>


<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>