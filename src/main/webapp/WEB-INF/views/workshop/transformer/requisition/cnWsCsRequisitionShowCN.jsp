<%@include file="../../../common/wsContractorHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- ---------- Start of Style and Script for Location Grid ------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---------- End of Style and Script for Location Grid ------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cnws/cnWsRequisitionList.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Requisition
				List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Store
			Requisition</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${cnReqMst.requisitionNo}" id="requisitionNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${cnReqMst.uuid}" id="uuid" /> <input
						type="hidden" value="${cnReqMst.requisitionTo}" id="requisitionTo" />
						<input type="hidden" value="${cnReqMst.workOrderNumber}"
						id="workOrderNumber" /> <input type="hidden" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>
					<td class="info">${cnReqMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${cnReqMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${cnReqMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Requisition To:</td>
					<td class="info" id="storeDestination"></td>
					<td class="success">Work Order No.:</td>
					<td class="info">${cnReqMst.workOrderNumber}</td>
					<td class="success">Work Order Date:</td>
					<td class="info">
					<fmt:formatDate
							value="${cnReqMst.workOrderDate}" pattern="dd-MM-yyyy" />
					</td>
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
				<c:forEach items="${approveHistoryList}" var="approveHistory">
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
									pattern="dd-MM-yyyy" /></td>
						</tr>
						<c:if test="${!empty approveHistory.justification}">
							<tr class="">
								<td class="col-sm-1"></td>
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
			<c:if test="${empty cnReqDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty cnReqDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Job No</td>
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Remaining Quantity</td>
							<td style="">Required Quantity</td>
							<!-- <td style="">Unit Cost</td>
							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${cnReqDtlList}" var="cnReqDtl" varStatus="loop">
							<tr>
								<td><c:out value="${cnReqDtl.jobNo}" /> <input
									type="hidden" class="reqDtlId" id="id${loop.index}"
									value="${cnReqDtl.id}" /></td>
								<td><c:out value="${cnReqDtl.itemCode}" /></td>
								<td><c:out value="${cnReqDtl.itemName}" /></td>
								<td><c:out value="${cnReqDtl.uom}" /></td>
								<td id="remainingQty${loop.index}"><c:out
										value="${cnReqDtl.jobWiseRemainingQty}" /></td>
								<td><input type="number" id="requiredQty${loop.index}"
									class="requiredQty" onblur="qantityValidation(this)"
									name="quantityRequired" value="${cnReqDtl.quantityRequired}"
									step=".01" />

									<div>
										<span id="requiredQty-validation-error${loop.index}"
											class="bold"
											style="font-size: 10px; color: red; display: none;">Required
											quantity can not be greater <br /> than Remaining quantity.
										</span> <span id="requiredQty-validation-db-error${loop.index}"
											class="bold"
											style="font-size: 10px; color: red; display: none;">Required
											quantity is not available <br />in Store. Please reduce
											quantity.
										</span>
									</div></td>

								<%-- <td><c:out value="${cnReqDtl.unitCost}" /></td>
								<td><c:out value="${cnReqDtl.totalCost}" /></td> --%>
								<td><c:out value="${cnReqDtl.remarks}" /></td>
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>




		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification" required></textarea>
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
				<a class="btn btn-primary"
					href="Javascript:approveCnPdStoreRequisition()"
					style="text-decoration: none; border-radius: 6px;">${buttonValue}</a>
				<%-- ${buttonValue}  or Submit --%>
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
	</div>
</div>



<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/transformer/cnWsRequisitionShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>