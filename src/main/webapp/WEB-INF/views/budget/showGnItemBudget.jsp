<%@include file="../common/budgetHeader.jsp"%>

<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>


<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit col-md-3" style="display: block;">
			<a href="javascript:void(0)" class="btn btn-primary btn-sm"
				onclick="postSubmit('${pageContext.request.contextPath}/budget/generalItemList.do',{id:'${generalItemBudgetMst.descoSession.id}'},'POST')"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Back
			</a>
		</div>
		<h2 class="center blue col-md-6"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Budget Allocation for ${generalItemBudgetMst.itemCode}</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<table class="table table-striped table-hover table-bordered">
				<tr>
					<td class="info">Code No.</td>
					<td>${generalItemBudgetMst.itemCode}</td>
					<td class="info">Particulars</td>
					<td>${generalItemBudgetMst.itemName}</td>
				</tr>
				<tr>
					<td class="info">Unit</td>
					<td>${generalItemBudgetMst.uom}</td>
					<td class="info">Quantity</td>
					<td>
						<input type="text" id="mquantity" value="${generalItemBudgetMst.qty}" readonly style="border: 0; background: #fff !important">
						<input type="hidden" id="omquantity" value="${generalItemBudgetMst.qty}">
					</td>
				</tr>
				<tr>
					<td class="info">Rate</td>
					<td class="col-xs-2"><input id="rate" type="text" style="width: 100%; border: 0; background: white !important;" readonly value="${generalItemBudgetMst.unitCost}"></td>
					<td class="info">Amount</td>
					<td>
						<input type="text" value="${generalItemBudgetMst.totalCost}" readonly style="border: 0; background: #fff !important" id="mamount">
						<input type="hidden" id="omamount" value="${generalItemBudgetMst.totalCost}">
					</td>
				</tr>
			</table>

			<div class="alert alert-success hide">
				<strong>Success!</strong>&nbsp; Information is updated.
			</div>
			
			<div class="alert alert-danger hide">
				<strong>Error!</strong>&nbsp; Information is not updated.
			</div>

			<div class="col-xs-12">
				<button class="btn btn-xs btn-success"
					style="border-radius: 6px; margin-right: 10px;"
					onclick="openDialoge()">
					<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Cost Centre
				</button>
			</div>

			<c:if test="${!empty generalItemBudgetDtlList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Cost Center Code</td>
							<td>Cost Center Name</td>
							<td class="col-xs-2">Quantity</td>
							<td>Amount</td>
							<td class="col-xs-2">Action</td>
						</tr>
					</thead>


					<tbody>
						<c:forEach items="${generalItemBudgetDtlList}" var="gnBudgeDtl"
							varStatus="loop">
							<tr>
								<td>${gnBudgeDtl.costCenterId}</td>
								<td>${gnBudgeDtl.costCenterName}</td>
								<td>
									<input type="hidden" class="oqty${loop.index}" value="${gnBudgeDtl.totalCost / generalItemBudgetMst.unitCost}">
									<input type="text" id="quantity" class="qty${loop.index}" style="border: 0; background: #fff !important"
									onblur="calcTotal(${loop.index})" name="quantity" value="${gnBudgeDtl.totalCost / generalItemBudgetMst.unitCost}" readonly style="width: 100%">
								</td>
								<td class="col-xs-2"><input type="text" readonly style="border: 0; background-color: #fff !important; width: 100%" class="amount${loop.index}"
									value="${gnBudgeDtl.totalCost}"></td>

								<td>
									<a href="javascript:void(0)" class="editbtn${loop.index} btn btn-primary btn-xs"
										onclick="editAction(${loop.index})" style="border-radius: 6px;"><i class="fa fa-fw fa-edit"></i>&nbsp;Edit</a>
									<a href="javascript:void(0)" class="updatebtn${loop.index} btn btn-success btn-xs hide"
										onclick="updateAction(${loop.index}, ${gnBudgeDtl.id})" style="border-radius: 6px;"><i class="fa fa-fw fa-repeat"></i>&nbsp;Update</a>
									<a href="javascript:void(0)" class="btn btn-danger btn-xs" onclick="deleteItemFromBudget(${gnBudgeDtl.id})"
									 style="border-radius: 6px;"><i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->
		<div id="myDialogeBox" class="hide">
			<div class="col-md-12 col-sm-12">
				<div class="alert alert-success hide">
					<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
					<strong>Success!</strong> Allocation is available.
				</div>
				<div class="alert alert-danger hide">
					<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
					<strong>Fail!</strong>Allocation Limit is finished.
				</div>
				<form
					action="${pageContext.request.contextPath}/bgt/saveCostCentreFromShow.do"
					id="addMoreCostCentre" method="post">
					<!-- <input type="hidden" id="requisitionTo" name="requisitionTo" value="ss"> -->
					<input name="id" value="${generalItemBudgetMst.id}" type="hidden" />
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

															<div class="form-group col-sm-3 col-xs-12">
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
																	style="border: 0; border-bottom: 2px ridge;" />
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
				</form>
			</div>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/budget/showGnItemBudget.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$('#dataList').DataTable({
			"order" : [ [ 0, 'asc' ] ],
			"bLengthChange" : false
		});
		
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
