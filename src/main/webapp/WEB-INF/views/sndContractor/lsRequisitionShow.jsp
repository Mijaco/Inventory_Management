<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->


<!-- CSS -->
<style type="text/css">
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

		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/c2ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
		</div>
		<h2 class="center blue" style="margin-top: 0px;">Store
			Requisition</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>


	</div>

	<div class="row"
		style="background-color: white; padding: 10px;  margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}"
						id="requisitionNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" /> <input
						type="hidden" value="${centralStoreRequisitionMst.uuid}" id="uuid" />
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${centralStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${centralStoreRequisitionMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Contract No:</td>
					<td class="info">${centralStoreRequisitionMst.contractorRepresentive.contractNo}</td>
					<td class="success">Contract Date:</td>
					<td class="info"><fmt:formatDate
							value="${centralStoreRequisitionMst.contractorRepresentive.listedDate}"
							pattern="dd-MM-yyyy" /></td>
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
							<td style="">Current Qty (NS)</td>
							<td style="">Current Qty (RS)</td>
							<td style="">Required Qty</td>
							<td style="">Issued Qty (NS)</td>
							<td style="">Issued Qty (RS)</td>
							<td style="">Total Issued</td>
							<!-- <td style="">Unit Cost</td>
							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
							<!-- <td style="">Action</td> -->
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}"
							var="centralStoreRequisitionDtl" varStatus="loop">
							<tr>

								<td><c:out value="${centralStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.uom}" /></td>
								<td id="presentStockNs${loop.index}"><fmt:formatNumber
										type="number" maxIntegerDigits="3"
										value="${centralStoreRequisitionDtl.presentStockNs}" /></td>
								<td id="presentStockRs${loop.index}"><fmt:formatNumber
										type="number" maxIntegerDigits="3"
										value="${centralStoreRequisitionDtl.presentStockRs}" /></td>

								<td><fmt:formatNumber type="number" maxIntegerDigits="3"
										value="${centralStoreRequisitionDtl.quantityRequired}" /></td>
								<%-- <td><c:out value="${centralStoreRequisitionDtl.quantityIssued}" /></td> --%>
								<td><input class="quantityIssuedNS" type="number"
									style="width: 100%;" onblur="checkNsQty(this)"
									id="quantityIssuedNS${loop.index}" name="quantityIssuedNS"
									value="${centralStoreRequisitionDtl.quantityIssuedNS}" /></td>

								<td><input class="quantityIssuedRS" type="number"
									style="width: 100%;" onblur="checkRsQty(this)"
									name="quantityIssuedRS" id="quantityIssuedRS${loop.index}"
									value="${centralStoreRequisitionDtl.quantityIssuedRS}" /></td>
								<%-- <td><c:out value="${centralStoreRequisitionDtl.unitCost}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.totalCost}" /></td> --%>
								<td id="totalIssuedQty${loop.index}">
									<fmt:formatNumber type="number" maxIntegerDigits="3"
										value="${centralStoreRequisitionDtl.quantityIssuedNS+centralStoreRequisitionDtl.quantityIssuedRS}" />
								
								</td>
								<td>${centralStoreRequisitionDtl.remarks}</td>
								<%-- <td>
									<div class="action-buttons">
										<a href="#" data-toggle="modal" data-target="#editModal"
											class="editModal"
											onclick="editItem(${centralStoreRequisitionDtl.id})"> <i
											class="ace-icon fa fa-pencil bigger-130"></i>
										</a>
										<!-- Trigger the modal with a button -->
										<button type="button" class="btn btn-success"
											aria-label="Left Align" data-toggle="modal"
											data-target="#myModal"
											onclick="setModalHeader(${centralStoreRequisitionDtl.itemCode}, ${loop.index})">
											<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
											Set
										</button>
										<a href="#" class="btn btn-primary"
											onclick="editItemLocation(${centralStoreRequisitionDtl.itemCode}, ${loop.index})">
											<i class="glyphicon glyphicon-edit" aria-hidden="true"> </i>
											Edit
										</a>
									</div>
								</td> --%>
							</tr>
							<c:set var="count" value="${loop.index}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
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
				<button type="button" class="btn btn-primary" onclick="approveing()"
					style="text-decoration: none; border-radius: 6px;" id="buttonApproving">
					${buttonValue} </button>
				
				<button type="button" class="btn btn-danger" onclick="requisitionReject()"
					style="text-decoration: none; border-radius: 6px;" id="buttonReject">Reject</button>
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
					action="${pageContext.request.contextPath}/ls/setRcvedLocation.do">
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
<script>
	function checkNsQty(element) {
		var id = $(element).attr('id');
		var name = $(element).attr('name');
		var sequence = id.substr(name.length);

		var presentStockNs = $("#presentStockNs" + sequence).text();
		var quantityIssuedNS = $("#" + id).val();

		presentStockNs = parseFloat(presentStockNs);
		quantityIssuedNS = parseFloat(quantityIssuedNS);

		if (quantityIssuedNS > presentStockNs) {
			alert("Issued quantity can not be greater than present stock.")
			$("#" + id).val(presentStockNs);
		}
		setTotalIssuedQty(sequence);
	}

	function checkRsQty(element) {
		var id = $(element).attr('id');
		var name = $(element).attr('name');
		var sequence = id.substr(name.length);

		var presentStockRs = $("#presentStockRs" + sequence).text();
		var quantityIssuedRS = $("#" + id).val();

		presentStockRs = parseFloat(presentStockRs);
		quantityIssuedRS = parseFloat(quantityIssuedRS);

		if (quantityIssuedRS > presentStockRs) {
			alert("Issued quantity can not be greater than present stock.")
			$("#" + id).val(presentStockRs);
		}

		setTotalIssuedQty(sequence);
	}

	function setTotalIssuedQty(index) {
		var quantityIssuedNS = $("#quantityIssuedNS" + index).val();
		var quantityIssuedRS = $("#quantityIssuedRS" + index).val();

		quantityIssuedNS = parseFloat(quantityIssuedNS);
		quantityIssuedRS = parseFloat(quantityIssuedRS);

		var totalQty = quantityIssuedNS + quantityIssuedRS;
		$("#totalIssuedQty" + index).text(totalQty.toFixed(3));
	}
	
	function requisitionReject() {
		var haserror = false;
		
		if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
			$('.justification').removeClass('hide');
			$('#justification').focus();
			return;
		} else {
			if( confirm("Do you want to reject this Requisition?") == true ) {
				$('#buttonReject').prop('disabled', true);
				$('.justification').addClass('hide');
				
				var justification = $('#justification').val();
				var requisitionNo = $('#requisitionNo').val();
				
				window.location = $('#contextPath').val()
				+ "/c2ls/requisition/reject.do?requisitionNo=" + requisitionNo
				+ "&justification=" + justification + "";
			}
		}
	}
	
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/c2LsRequisitionShowAddedLocation.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>