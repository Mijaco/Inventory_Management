<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<style>
.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<div class="container-fluid" style="background-color: #858585;">
	<!--  icon-box -->
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>

		</div>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>
</div>

<div class="row"
	style="background-color: white; padding: 10px; margin: 10px;">
	<!-- --------------------- -->
	<div class="oe_title">

		<table class="col-sm-12 table">
			<tr class="">
				<td class="success">Return Slip No: <input type="hidden"
					value="${returnSlipMst.returnSlipNo}" id="returnSlipNo" /> <input
					type="hidden" value="${returnStateCode}" id="returnStateCode" /> <input
					type="hidden" value="${returnSlipMst.uuid}" id="uuid" /> <input
					type="hidden" name="contextPath" id="contextPath"
					value="${pageContext.request.contextPath}" />

				</td>

				<td class="info">${returnSlipMst.returnSlipNo}</td>
				<td class="success">Created By:</td>
				<td class="info">${returnSlipMst.createdBy}</td>
				<td class="success">Created Date:</td>
				<td class="info"><fmt:formatDate
						value="${returnSlipMst.createdDate}" pattern="dd-MM-yyyy" /></td>
			</tr>
			<tr class="">
				
				<td class="success">Returned By:</td>
				<td class="info">${returnSlipMst.receiveFrom}, ${deptName}
				</td>
				
				<td class="success">Returned To:</td>
				<td class="info"><strong> ${returnSlipMst.returnTo=='cs'?'Central Store':'Sub Store'} 
				</strong></td>
				
				<td class="success">Present Status:</td>
				<td class="info"><strong> ${currentStatus} </strong></td>
			</tr>
			<tr class="">
				
				
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
					<i>Sorry!!! No Data Found in Database. </i>
				</p>
			</div>
		</c:if>
		<c:if test="${!empty returnSlipDtlList}">
			<div class="table-responsive col-xs-12">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">New Serviceable</td>
							<td style="">Recovery Serviceable</td>
							<td style="">UnServiceable</td>
							<td style="">Total Return</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl"
							varStatus="loop">
							<tr>

								<td><c:out value="${returnSlipDtl.itemCode}" /></td>
								<td><c:out value="${returnSlipDtl.description}" /></td>
								<td><c:out value="${returnSlipDtl.uom}" /> <input
									type="hidden" value="${returnSlipDtl.totalReturn}" /></td>
								<td><input type="number"
									value="${returnSlipDtl.qtyNewServiceable}"
									id="qtyNewServiceable${loop.index}" 
									onblur="setTotalQuantity(this)"></td>
								<td><input type="number"
									value="${returnSlipDtl.qtyRecServiceable}"
									id="qtyRecServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number"
									value="${returnSlipDtl.qtyUnServiceable}"
									id="qtyUnServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number"
									value="${returnSlipDtl.totalReturn}" readOnly="readOnly"
									id="totalReturn${loop.index}" /> <input type="hidden"
									value="${returnSlipDtl.id}" readOnly="readOnly"
									id="dtlId${loop.index}" /></td>

								<%-- <td><c:out value="${returnSlipDtl.qtyNewServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyRecServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyUnServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.totalReturn}" /></td> --%>

								<td><c:out value="${returnSlipDtl.remarks}" /></td>
								<td>
									<div class="action-buttons">
										<a href="javascript:void(0)" id="rtnslipdtla${loop.index}"
											onclick="editItem(this)" class="btn btn-primary btn-xs"
											style="border-radius: 6px; min-width: 70px;"> <i
											class="fa fa-fw fa-repeat"></i>&nbsp;Update
										</a> <a class="btn btn-danger btn-xs"
											style="border-radius: 6px; margin-top: 4px; min-width: 75px;"
											href="javascript:void(0)"
											onclick="deleteItem(${returnSlipDtl.id}, '${returnSlipMst.returnTo}', '${returnSlipMst.returnSlipNo}')">
											<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
										</a>
									</div>
								</td>
							</tr>
							<c:set var="count" value="${loop.index}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<!-- Commented by Ashid: After bug fix it must be active -->
		<div class="col-xs-12 align-right"
			style="position: relative; margin: 5px 0 10px 0;">
			<button class="btn btn-xs btn-success"
				style="border-radius: 6px; margin-right: 10px;"
				onclick="openDialoge()">
				<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Item
			</button>
		</div>
	</div>
	<!-- <div class="text-center"> -->
	<div class="col-xs-12">
		<label class="col-xs-1"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
		<div class="col-xs-11">
			<textarea required class="form-control" rows="2" cols=""
				id="justification"></textarea>
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
			<button class="btn btn-primary" onclick="approveing()"
				style="text-decoration: none; border-radius: 6px;" id="saveButton">
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
				action="${pageContext.request.contextPath}/ls/returnSlip/csRturnSlipSaveMultipleItem.do"
				id="saveLSCSReturnSlip" method="post">

				<!-- <input type="hidden" id="requisitionTo" name="requisitionTo" value="ss"> -->
				<input type="hidden" id="returnTo" name="returnTo"
					value="${returnSlipMst.returnTo}"> <input type="hidden"
					name="id" value="${returnSlipMst.id}"> <input type="hidden"
					id='returnSlipNo' name="returnSlipNo"
					value="${returnSlipMst.returnSlipNo}">

				<div class="form-group" style="margin-top: 1em;">
					<p class="col-sm-12 btn btn-primary btn-sm">Add New Item For
						Store Requisition</p>
					<div class="col-xs-12 table-responsive">
						<div class="table">
							<div style="width: 2000px;">
								<hr />
								<div class="col-xs-12">
									<div class="form-group col-sm-1 col-xs-12">
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
									<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Quantity Retrun</b>
										</div> -->

									<div class="form-group col-sm-1 col-xs-12">
										<b>New Serviceable</b>
									</div>

									<div class="form-group col-sm-1 col-xs-12">
										<b>Recovery Serviceable</b>
									</div>

									<div class="form-group col-sm-1 col-xs-12">
										<b>UnServiceable</b>
									</div>
									<div class="form-group col-sm-1 col-xs-12">
										<b>Total Return</b>
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
													<!-- <form role="form" autocomplete="off">  -->
													<div class="col-xs-12 entry" id="myArea0">
														<div class="row">

															<div class="form-group col-sm-1 col-xs-12">
																<select class="form-control category"
																	onchange="categoryLeaveChange(this)"
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
																<input type="hidden" name="description"
																	class="description" /> <select
																	class="form-control itemName"
																	onchange="itemLeaveChange(this)"
																	style="border: 0; border-bottom: 2px ridge;">
																	<option disabled selected>Item Name</option>
																</select>
															</div>
															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control itemCode" name="itemCode"
																	type="text" placeholder="itemCode" readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control uom" name="uom" type="text"
																	placeholder="Unit" readonly="readonly"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<!-- <div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control currentStock"
																		name="qtyRetrun" id="currentStock0" type="number"
																		placeholder="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div> -->

															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control quantityRequired"
																	name="qtyNewServiceable" id="quantityRequired0"
																	type="number" value="0" step="0.01"
																	onblur="setTotalCost(this)"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>

															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control quantityIssued"
																	name="qtyRecServiceable" id="quantityIssued0"
																	type="number" placeholder="0" step="0.01"
																	onblur="setTotalCost(this)" value="0"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control" name="qtyUnServiceable"
																	type="number" id="unitCost0" step="0.01"
																	onblur="setTotalCost(this)" value="0"
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1 col-xs-12">
																<input class="form-control totalCost" name="totalReturn"
																	value="0" type="number" id="totalCost0"
																	readonly="readonly" step="0.01" value="0"
																	style="border: 0; border-bottom: 2px ridge;" /> <strong
																	class="errTotRet text-danger hide" id="errTotRet0">Total
																	return can't 0</strong>
															</div>
															<div class="form-group col-sm-2 col-xs-12">
																<input class="form-control" name="remarks" type="text"
																	value=" " 
																	style="border: 0; border-bottom: 2px ridge;" />
															</div>
															<div class="form-group col-sm-1 col-xs-3">

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

	<script>
		function forwardToUpper(stateCode) {
			if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
				$('.justification').removeClass('hide');
				$('#justification').focus();
				return;
			} else {
				var justification = $('#justification').val();
				var returnSlipNo = $('#returnSlipNo').val();

				//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
				window.location = "${pageContext.request.contextPath}/ls/returnSlip/sendTo.do?returnSlipNo="
						+ returnSlipNo
						+ "&justification="
						+ justification
						+ "&stateCode=" + stateCode;
			}		
		}

		function backToLower(stateCode) {
			if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
				$('.justification').removeClass('hide');
				$('#justification').focus();
				return;
			} else {
				var justification = $('#justification').val();
				var returnSlipNo = $('#returnSlipNo').val();

				//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
				window.location = "${pageContext.request.contextPath}/ls/returnSlip/backTo.do?returnSlipNo="
						+ returnSlipNo
						+ "&justification="
						+ justification
						+ "&stateCode=" + stateCode;
			}		
		}

		function approveing() {
			if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
				$('.justification').removeClass('hide');
				$('#justification').focus();
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				var justification = $('#justification').val();
				var returnSlipNo = $('#returnSlipNo').val();

				var returnStateCode = $('#returnStateCode').val();

				window.location = "${pageContext.request.contextPath}/ls/itemReturnSlipApproved.do?returnSlipNo="
						+ returnSlipNo
						+ "&justification="
						+ justification
						+ "&return_state=" + returnStateCode;
			}	
		}
	</script>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/returnSlipShow.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>