<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<!-- CSS -->
<style type="text/css">
.dtlHeader {
	background: #478fca;
	color: white;
}

.ui-widget-header, .ui-state-default, ui-button {
	background: #b9cd6d;
	border: 1px solid #b9cd6d;
	color: #FFFFFF;
	font-weight: bold;
}

.ui-dialog-titlebar-close {
	visibility: hidden;
}

.ui-widget-content {
	background: white url();
}

.btn-add {
	margin-bottom: 20px;
}
</style>
<!-- CSS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/c2ls/returnSlip/List.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a> --%>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h2 class="center blue" style="margin-top: 0px;">Return Slip</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>
	
	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${returnSlipMst.returnSlipNo}" id="returnSlipNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />

						<input type="hidden" value="${returnSlipMst.uuid}" id="uuid" /> <input
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
					<td class="success">Contact No:</td>
					<td class="info">${returnSlipMst.contractorRepresentive.contactNo}</td>
					<td class="success">Project Name:</td>
					<td class="info">${returnSlipMst.khathName}</td>
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
									value="${approveHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
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
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 2400px;">
							<hr />
							<div class="col-xs-12">
								<div class="form-group col-sm-1 col-xs-12 dtlHeader" >
									<b>Item Code</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader" >
									<b>Description</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>UOM</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>New Serviceable</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Recovery Serviceable</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader" >
									<b>Un-Serviceable</b>
								</div>
								<!-- <div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Total Return</b>
								</div> -->

								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Received Qty(Ser)</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Received Qty(Rec)</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Received Qty(Unser)</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader" >
									<b>Total Received</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Remarks</b>
								</div>
								<div class="form-group col-sm-1 col-xs-12 dtlHeader">
									<b>Action</b>
								</div>
							</div>

							<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl"
								varStatus="loop">
								<div class="col-xs-12">
									<div class="row">
										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.itemCode}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.description}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.uom}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.qtyNewServiceable}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.qtyRecServiceable}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.qtyUnServiceable}" />
										</div>

										<%-- <div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.totalReturn}" />
										</div> --%>

										<div class="form-group col-sm-1 col-xs-12">
											<input type="number"
												value="${returnSlipDtl.qtyNewServiceableRcv}"
												id="qtyNewServiceable${loop.index}"
												onblur="setTotalQuantity(this)" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<input type="number"
												value="${returnSlipDtl.qtyRecServiceableRcv}"
												id="qtyRecServiceable${loop.index}"
												onblur="setTotalQuantity(this)" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<input type="number"
												value="${returnSlipDtl.qtyUnServiceableRcv}"
												id="qtyUnServiceable${loop.index}"
												onblur="setTotalQuantity(this)" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<input type="number" value="${returnSlipDtl.totalReturnRcv}"
												readOnly="readOnly" id="totalReturn${loop.index}" /> <input
												type="hidden" value="${returnSlipDtl.id}"
												readOnly="readOnly" id="dtlId${loop.index}" />
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<c:out value="${returnSlipDtl.remarks}" />
										</div>

										<div class="form-group col-sm-1 col-xs-1">
											<a href="#" id="rtnslipdtla${loop.index}" 
											class="btn btn-primary btn-xs"  style="border-radius:4px;"
												onclick="editItem(this)"> <i
												class="ace-icon glyphicon glyphicon-ok"> Update</i>
											</a>

											<!-- Trigger the modal with a button -->
											<%-- <button type="button" class="btn btn-success"
												aria-label="Left Align" data-toggle="modal"
												data-target="#myModal"
												onclick="setModalHeader(${returnSlipDtl.itemCode}, ${loop.index})">
												<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
												Set
											</button>
											<a href="#" class="btn btn-primary"
												onclick="editItemLocation(${returnSlipDtl.itemCode}, ${loop.index})">
												<i class="glyphicon glyphicon-edit" aria-hidden="true">
											</i> Edit
											</a> --%>

										</div>
									</div>
								</div>
								<c:set var="count" value="${loop.index}" scope="page" />
							</c:forEach>
						</div>
					</div>
				</div>
				<%-- <table id="requisitionListTable"
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
							
							<td style="">New Serviceable Received</td>
							<td style="">Recovery Serviceable Received</td>
							<td style="">UnServiceable Received</td>
							<td style="">Total Received</td>
							
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
								<td><c:out value="${returnSlipDtl.qtyNewServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyRecServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyUnServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.totalReturn}" /></td>	
								<td><input type="number"
									value="${returnSlipDtl.qtyNewServiceableRcv}"
									id="qtyNewServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number"
									value="${returnSlipDtl.qtyRecServiceableRcv}"
									id="qtyRecServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /></td>
								<td><input type="number"
									value="${returnSlipDtl.qtyUnServiceableRcv}"
									id="qtyUnServiceable${loop.index}"
									onblur="setTotalQuantity(this)" /> </td>
								<td><input type="number"
									value="${returnSlipDtl.totalReturnRcv}" readOnly="readOnly"
									id="totalReturn${loop.index}" /> <input type="hidden"
									value="${returnSlipDtl.id}" readOnly="readOnly"
									id="dtlId${loop.index}" /></td>

								<td><c:out value="${returnSlipDtl.qtyNewServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyRecServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.qtyUnServiceable}" /></td>
								<td><c:out value="${returnSlipDtl.totalReturn}" /></td>

								<td><c:out value="${returnSlipDtl.remarks}" /></td>
								<td>
									<div class="action-buttons">
										<a href="#" id="rtnslipdtla${loop.index}"
											onclick="editItem(this)"> <i
											class="ace-icon glyphicon glyphicon-ok bigger-130"> </i>
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table> --%>
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
				<%-- <a class="btn btn-primary" href="Javascript:approveing()"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a> --%>
				<button type="button" class="btn btn-primary" onclick="approveing()"
					style="text-decoration: none; border-radius: 6px;" id="buttonApproving">
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
		<!-- <script>
			
		</script> -->
	</div>
</div>

<!-- ----------Modal Start--------- -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					Set Location for <span id="itemCodeforLocationModal"></span>
				</h4>
			</div>
			<div class="modal-body">

				<form id="modalForm" method="POST"
					action="${pageContext.request.contextPath}/c2ls/rs/setRcvedLocation.do">
					<input type="hidden" name="itemCode" id="hiddenItemCode" value="" />
					<input type="hidden" name="uuid" id="hiddenUUID" value="" /> <input
						type="hidden" name="index" id="hiddenIndex" value="" />
					<div class="myControl">
						<div class="aaa">
							<div class="col-md-12 entry" id="myArea0">
								<div class="col-md-5">
									<select class="form-control ledgerName" name="ledgerName"
										id="ledgerName0" autocomplete="on"
										style="border: 0; border-bottom: 2px ridge;">
										<c:if test="${!empty ledgerBooks}">
											<c:forEach items="${ledgerBooks}" var="ledgerBook">
												<option value="${ledgerBook}">
													<c:out value="${ledgerBook}" /></option>
											</c:forEach>
										</c:if>
									</select>
								</div>
								<div class="col-md-4">
									<select class="form-control location" name="locationId"
										onchange="locationChange(this)" id="location0"
										autocomplete="off"
										style="border: 0; border-bottom: 2px ridge;">
										<option disabled selected>Select Location</option>
										<c:if test="${!empty locationList}">
											<c:forEach items="${locationList}" var="location">
												<option value="${location.id}">
													<c:out value="${location.name}" /></option>
											</c:forEach>
										</c:if>
									</select>
								</div>
								<div class="col-md-2">
									<input class="form-control locationQty" name="locationQty"
										id="locationQty0" type="text" placeholder=""
										readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" />
								</div>
								<div class="col-md-1">
									<button class="btn btn-success btn-add" type="button">
										<span class="glyphicon glyphicon-plus"></span>
									</button>
								</div>
							</div>
						</div>
					</div>

					<div class="">
						<p
							style="margin: 15px; color: blue; font-size: 10px; font-style: italic;">First
							select a location and input item quantity for that location. If
							you need more location just click on Plus (+) button on the right
							side.</p>
					</div>

				</form>
			</div>
			<div class="modal-footer" style="text-align: center;">

				<a class="btn btn-primary" onclick="setLocation();">Set</a>
				<button type="button" class="btn btn-danger"
					onclick="resetLocation()" data-dismiss="modal">Reset</button>



			</div>
		</div>

	</div>
</div>
<!-- -------------------------- -->
<div id="myDialog" title="Title">
	<div id="dynamicTable"></div>
</div>
<!-- -----------calling js file-------------- -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/lsReturnSlipShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>