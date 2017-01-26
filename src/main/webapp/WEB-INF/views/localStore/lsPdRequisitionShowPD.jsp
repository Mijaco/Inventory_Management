<%@include file="../common/pdHeader.jsp"%>
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
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Store Requisition from Project</h3>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->

		<input type="hidden" id="requisitionTo"
			value="${centralStoreRequisitionMst.requisitionTo}">

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
							<td style="">CS (NS) Qty</td>
							<td style="">CS (RS) Qty</td>
							<td style="">Required Qty</td>
							<td style="">Remarks</td>
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
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
				<%-- <c:if test="${!empty backManRcvProcs}">

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
				</c:if> --%>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<button class="btn btn-primary" onclick="approveing()" id="approveButton"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </button>
			</div>
			<div class="col-md-4 col-sm-4 text-right">

				<%-- <c:if test="${!empty nextManRcvProcs}">

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
				</c:if> --%>
			</div>

		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/lsPdRequisitionShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>