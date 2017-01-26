<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<style type="text/css">
.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
		</div>


		<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition from Project</h3>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}"
						id="requisitionNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" />
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Requisition Date:</td>
					<td class="info"><fmt:formatDate
							value="${centralStoreRequisitionMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
					<td class="success">Requisition To:</td>
					<td class="info"><c:if
							test="${centralStoreRequisitionMst.requisitionTo=='cs'}">Central Store </c:if>
						<c:if test="${centralStoreRequisitionMst.requisitionTo=='ss'}">Sub Store </c:if>
					</td>
				</tr>
				<tr class="">
					<td class="success">Indenter</td>
					<td class="info">${centralStoreRequisitionMst.identerDesignation},
						${centralStoreRequisitionMst.deptName}</td>
					<td class="success">Created By:</td>
					<td class="info">${centralStoreRequisitionMst.createdBy}</td>

					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>
				<tr>
					<td class="success">Project Name:</td>
					<td class="info"><b>${centralStoreRequisitionMst.khathName}
					</b></td>
					<td class="success">Carried By:</td>
					<td class="info">${centralStoreRequisitionMst.carriedBy}</td>
				</tr>

			</table>
		</div>
		
		<input type="hidden" id="khathId" value="${centralStoreRequisitionMst.khathId}">
		
		<c:if test="${!empty itemRcvApproveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>
		<div id="demo" class="collapse">
			<c:if test="${!empty itemRcvApproveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->
				
				<c:forEach items="${itemRcvApproveHistoryList}"
					var="itemRcvApproveHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${itemRcvApproveHistory.cEmpFullName} <c:if
									test="${!empty itemRcvApproveHistory.cDesignation}">
									, ${itemRcvApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty itemRcvApproveHistory.cEmpId}">
									(${itemRcvApproveHistory.cEmpId})
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${itemRcvApproveHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
						<c:if test="${!empty itemRcvApproveHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty centralStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty centralStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">CS/SS Present Qty (NS)</td>
							<td style="">CS/SS Present Qty (RS)</td>
							<td style="">Required Qty</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}"
							var="centralStoreRequisitionDtl">
							<tr>
								<td>${centralStoreRequisitionDtl.itemCode}</td>
								<td>${centralStoreRequisitionDtl.itemName}</td>
								<td>${centralStoreRequisitionDtl.uom}</td>
								<td>${centralStoreRequisitionDtl.newServiceableStockQty}</td>
								<td>${centralStoreRequisitionDtl.recoveryServiceableStockQty}</td>
								<td>${centralStoreRequisitionDtl.quantityRequired}</td>
								<td>${centralStoreRequisitionDtl.remarks}</td>
								<td>
									<a href="javascript:void(0)" class="btn btn-danger btn-xs" style="border-radius: 6px;"
									onclick="deleteThis(${centralStoreRequisitionDtl.id}, '${centralStoreRequisitionMst.requisitionNo}', '${centralStoreRequisitionMst.requisitionTo}')"><i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<div class="col-xs-12 align-right">
				<button class="btn btn-xs btn-success"
					style="border-radius: 6px; margin-right: 10px;"
					onclick="openDialoge()">
					<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Item
				</button>
			</div>
		</div>
		<br> <br>

		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
				<strong class="justification text-danger hide">This field is required</strong>
			</div>
		</div>
		<div class="col-xs-12">
			<hr />
		</div>
		<div class="row">
			<div class="col-md-4 col-sm-4 text-left">
				<c:if test="${!empty backManRcvProcs}">

					<div class="dropup pull-left">
						<button class="btn btn-warning dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Back to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

							<c:forEach items="${backManRcvProcs}" var="backMan">
								<li class=""><a
									href="Javascript:backToLower(${backMan.stateCode})">For
										${backMan.buttonName}</a></li>
							</c:forEach>

						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<button type="button" class="btn btn-primary" onclick="approveing()" id="approveButton"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </button>
			</div>
			<div class="col-md-4 col-sm-4 text-right">

				<c:if test="${!empty nextManRcvProcs}">

					<div class="dropup pull-right">
						<button class="btn btn-danger dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Send to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

							<c:forEach items="${nextManRcvProcs}" var="nextMan">
								<li class=""><a
									href="Javascript:forwardToUpper(${nextMan.stateCode})">For
										${nextMan.buttonName}</a></li>
							</c:forEach>

						</ul>
					</div>
				</c:if>
			</div>
		</div>
		<hr />

		<div id="editModal" class="modal fade editModal" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title center">Edit an Item</h4>
					</div>

					<!-- --------------------- -->
					<div class="alert alert-success hide">
						<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
						<strong>Success!</strong> Allocation is available.
					</div>
					<div class="alert alert-danger hide">
						<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
						<strong>Fail!</strong>Allocation Limit is finished.
					</div>
					<div class="row"
						style="background-color: white; padding: 5px; padding-left: 10px; margin-top: 5px; margin-bottom: 5px; margin-left: 15px; margin-right: 5px;">

						<form method="POST"
							action="${pageContext.request.contextPath}/cs/itemRecieved/updateItem.do">
							<div class="oe_title">

								<input class="o_form_input o_form_field o_form_required"
									id="modal_itemId" placeholder="Item Code" type="text"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_description" placeholder="Description" type="text"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_expectedQuantity" placeholder="Expected Quantity"
									type="text" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_receivedQuantity" placeholder="Received Quantity"
									type="text" name="receivedQty"
									onkeyup="receivedQtyToRemainQty(this)"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_reminingQuantity" placeholder="Remining Quantity"
									type="text" name="remainingQty" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />



								<input type="text" hidden="true" id="modal_id" name="id" />
								<hr />
								<div class="text-center">
									<input class="btn btn-success" type="submit" value="Update"
										style="border-radius: 6px" />
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>


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
					action="${pageContext.request.contextPath}/ls/pd/addMultipleItems.do"
					id="saveLSStoreRequisition" method="post">
					
					<input type="hidden" id="requisitionTo" name="requisitionTo"
						value="${centralStoreRequisitionMst.requisitionTo}">
					<input type="hidden" id="requisitionNo" name="requisitionNo"
						value="${centralStoreRequisitionMst.requisitionNo}">
					<input name="id" value="${centralStoreRequisitionMst.id}" type="hidden">
					
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div>
									<!-- style="width: 1500px;" -->
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-2 col-xs-12">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Store Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Required Qty</b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
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
																<div class="form-group col-sm-2 col-xs-12">
																	<select class="form-control category" id="category0"
																		name="category" onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
																

																<div class="form-group col-sm-2 col-xs-12">
																	<input type="hidden" name="itemName"
																		class="description" /> <select
																		class="form-control itemName" id="itemId0"
																		name="itemId" onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode"
																		id="itemCode0" type="text" placeholder="itemCode"
																		readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" required>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control currentStock"
																		readonly="readonly" name="currentStock"
																		id="currentStock0" type="number"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<!-- onkeyup="reqQtyNotGreaterThenCurrentStock(this)" -->
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired"
																		step="0.001" min="0"
																		onblur="reqQtyNotGreaterThenCurrentStock(this)"																		
																		name="quantityRequired" id="quantityRequired0"
																		type="number" value="0"
																		style="border: 0; border-bottom: 2px ridge;" /> <strong
																		class="text-danger reqQty hide" id="reqQty0">Required
																		Qty. can't be 0</strong>
																</div>


																

																<div class="form-group col-sm-2 col-xs-12">
																	<input class="form-control" name="remarks" type="text"
																		placeholder="Remarks"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2 col-xs-3">
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
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/lsPdRequisitionShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>