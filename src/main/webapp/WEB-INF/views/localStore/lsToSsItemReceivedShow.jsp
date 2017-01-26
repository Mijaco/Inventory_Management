<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ls/itemRecieved/ssItemRequisitionReceivedList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Received List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item
			Received from Sub Store</h2>

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
						value="${subStoreRequisitionMst.requisitionNo}" id="requisitionNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" />
					</td>

					<td class="info">${subStoreRequisitionMst.requisitionNo}<input
						type="hidden" value="${uuid}" id="uuid" /><input type="hidden"
						name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" /></td>
					<td class="success">Created By:</td>
					<td class="info">${subStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
						value="${subStoreRequisitionMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${subStoreRequisitionMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
						value="${subStoreRequisitionMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>


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
					<%-- <div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" /> --%>
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
									( ${itemRcvApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
								value="${itemRcvApproveHistory.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
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
			<c:if test="${empty subStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty subStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Quantity</td>
							<td style="">Issued Quantity</td>
							<!-- <td style="width: 15px;">Rcv Quantity</td> -->
<!-- 							<td style="">Unit Cost</td> -->
<!-- 							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
						<!-- 	<td style="">Action</td> -->
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${subStoreRequisitionDtlList}"
							var="subStoreRequisitionDtl" varStatus="loop">
							<tr>

								<td><c:out value="${subStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.uom}" /></td>
								<td><c:out
										value="${subStoreRequisitionDtl.quantityRequired}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.quantityIssued}" /></td>
								<%-- <td><input style="width: 100%;" type="text"
									readonly="readonly" value="" id="issuedQty${loop.index}"
									name="quantityIssued" /></td> --%>
<%-- 								<td><c:out value="${subStoreRequisitionDtl.unitCost}" /></td> --%>
<%-- 								<td><c:out value="${subStoreRequisitionDtl.totalCost}" /></td> --%>
								<td><c:out value="${subStoreRequisitionDtl.remarks}" /></td>
								<%-- <td>
									<div class="action-buttons">
										<a href="#" data-toggle="modal" data-target="#editModal"
											class="editModal"
											onclick="editItem(${subStoreRequisitionDtl.id})"> <i
											class="ace-icon fa fa-pencil bigger-130"></i>
										</a>
										<!-- Trigger the modal with a button -->
										<button type="button" class="btn btn-success"
											aria-label="Left Align" data-toggle="modal"
											data-target="#myModal"
											onclick="setModalHeader(${subStoreRequisitionDtl.itemCode}, ${loop.index})">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
											Set
										</button>
										<a href="#" class="btn btn-primary"
											onclick="editItemLocation(${subStoreRequisitionDtl.itemCode}, ${loop.index})">
											<i class="glyphicon glyphicon-edit" aria-hidden="true"> </i>
											Edit
										</a>
									</div>
								</td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<c:if test="${!subStoreRequisitionMst.received}">
			<hr />


			<div class="center">

				<a
					href="${pageContext.request.contextPath}/ls/itemRecieved/storeRequisitionReceiving.do?id=${subStoreRequisitionMst.id}&received=${subStoreRequisitionMst.received}"
					class="btn btn-success" style="border-radius: 6px;">Receive</a>
			</div>
		</c:if>
		<!-- -------------------------- -->

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
					action="${pageContext.request.contextPath}/ls/itemRecieved/setSSRcvedLocation.do">
					<input type="hidden" name="itemCode" id="hiddenItemCode" value="" />
					<input type="hidden" name="uuid" id="hiddenUUID" value="" /> <input
						type="hidden" name="index" id="hiddenIndex" value="" />
					<div class="myControl">
						<div class="aaa">
							<div class="col-md-12 entry" id="myArea0">
								<div class="col-md-6">
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

								<div class="col-md-4">
									<input class="form-control locationQty" name="locationQty"
										id="locationQty0" type="text" placeholder=""
										readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" />
								</div>
								<div class="col-md-2">
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
<!-- ------------------------------ -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/lsAddLocationForItemReceive.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>