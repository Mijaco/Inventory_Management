<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<%-- <td class="success">Requisition No: <input type="hidden"
						value="${returnSlipMst.returnSlipNo}"
						id="returnSlipNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" />
					</td> --%>
					<td class="success">Return Slip No: <input type="hidden"
						value="${returnSlipMst.returnSlipNo}" id="returnSlipNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${returnSlipMst.uuid}" id="uuid" /> <input
						type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" /> <input
						type="hidden" value=${locationList } id="locationList" /> <input
						type="hidden" value=${ledgerBooks } id="ledgerBookList" />

					</td>

					<td class="info">${returnSlipMst.returnSlipNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${returnSlipMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${returnSlipMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Work Order No:</td>
					<td class="info">${returnSlipMst.workOrderNo}</td>
					<td class="success">Work Order Date:</td>
					<td class="info"><fmt:formatDate
							value="${returnSlipMst.workOrderDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Returned By:</td>
					<td class="info"><strong>
							${returnSlipMst.receiveFrom} </strong></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${returnSlipMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
							value="${returnSlipMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>

		<c:if test="${!empty approveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty approveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${approveHistoryList}" var="approveHistory">
					<%-- <div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" /> --%>
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${approveHistory.cEmpFullName} <c:if
									test="${!empty approveHistory.cDesignation}">
									, ${approveHistory.cDesignation} 
								</c:if> <c:if test="${!empty approveHistory.cEmpId}">
									( ${approveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${approveHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
						</tr>
						<c:if test="${!empty approveHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${approveHistory.justification}">${approveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty returnSlipDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Congratulation!!! You have no pending task. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty returnSlipDtlList}">
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">
							<table
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Item Code</td>
										<td style="">Description</td>
										<td style="">UOM</td>
										<td style="">New Serviceable Qty</td>
										<td style="">Recovery Serviceable Qty</td>
										<td style="">UnServicable Qty</td>
										<td style="">Received Qty(Serviceable)</td>
										<td style="">Received Qty(Recovery Serviceable)</td>
										<td style="">Received Qty(Un-Serviceable)</td>
										<td style="">Total Received</td>
										<td style="">Remarks</td>
										<td style="">Action</td>

									</tr>
								</thead>

								<tbody>
									<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl"
										varStatus="loop">
										<tr>
											<td>${returnSlipDtl.itemCode}</td>
											<td>${returnSlipDtl.description}</td>
											<td>${returnSlipDtl.uom}</td>
											<td>${returnSlipDtl.qtyNewServiceable}</td>
											<td>${returnSlipDtl.qtyRecServiceable}</td>
											<td>${returnSlipDtl.qtyUnServiceable}</td>
											<td id="qtyNewServiceable${loop.index}">${returnSlipDtl.qtyNewServiceableRcv}</td>
											<td id="qtyRecServiceable${loop.index}">${returnSlipDtl.qtyRecServiceableRcv}</td>
											<td id="qtyUnServiceable${loop.index}">${returnSlipDtl.qtyUnServiceableRcv}</td>
											<td id="totalReturn${loop.index}">${returnSlipDtl.totalReturnRcv}</td>
											<td>${returnSlipDtl.remarks}</td>
											<td>
												<div class="action-buttons"
													data-itemcode="${returnSlipDtl.itemCode}">
													<a href="#" class="btn btn-primary"
														id="setDialog${loop.index}"> <i
														class="glyphicon glyphicon-edit"
														onclick="openGridDialog(this)" aria-hidden="true"> </i>
													</a>
												</div> <input type="hidden" value="${returnSlipDtl.id}"
												readOnly="readOnly" id="dtlId${loop.index}" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>

			</c:if>
		</div>



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
					<!-- <button onclick="backToLower()" class="btn btn-warning"
						style="text-decoration: none; border-radius: 6px;">< Back
						To</button> -->
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
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<%-- <a class="btn btn-primary"
					href="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSubmitApproved.do?receivedReportNo=${centralStoreRequisitionMst.rrNo}"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a> --%>
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
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
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
		<!-- -------------------------- -->
	</div>
</div>

<!-- ------Start of Location Grid Div ----- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div>
<!-- ------End of Location Grid Div ----- -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/subStore/cnReturnSlipShowForSS.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>