<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/khath/transferedList.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Project
				Transfer List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Project Transfer
			Information</h1>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-right: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Project Transfer No: <input type="hidden"
						value="${khathTransferMst.transferNo}" id="transferNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${khathTransferMst.uuid}" id="uuid" />
						<input type="hidden" value="${pageContext.request.contextPath}"
						id="contextPath" />
					</td>

					<td class="info">${khathTransferMst.transferNo}</td>
					<td class="success">Project From:</td>
					<td class="info">${khathTransferMst.khathFrom}</td>
					<td class="success">Project To:</td>
					<td class="info">${khathTransferMst.khathTo}</td>
				</tr>
				<tr class="">
					<td class="success">Ledger Book:</td>
					<td class="info">${khathTransferMst.ledgerBook}</td>
					<td class="success">UUID:</td>
					<td class="info"><%-- ${khathTransferMst.uuid} --%></td>
					<td class="success">Remarks:</td>
					<td class="info">${khathTransferMst.remarks}</td>

				</tr>
				<tr class="">
					<td class="success">Transfer Date:</td>
					<td class="info"><fmt:formatDate
							value="${khathTransferMst.createdDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Created By:</td>
					<td class="info">${khathTransferMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${khathTransferMst.createdDate}" pattern="dd-MM-yyyy" /></td>

				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${khathTransferMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
							value="${khathTransferMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>

		<c:if test="${!empty khathHierarchyHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty khathHierarchyHistoryList}">

				<c:forEach items="${khathHierarchyHistoryList}"
					var="khathHierarchyHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${khathHierarchyHistory.cEmpFullName} <c:if
									test="${!empty khathHierarchyHistory.cDesignation}">
									, ${khathHierarchyHistory.cDesignation} 
								</c:if> <c:if test="${!empty khathHierarchyHistory.cEmpId}">
									( ${khathHierarchyHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
							value="${khathHierarchyHistory.createdDate}" pattern="dd-MM-yyyy" />
							</td>
						</tr>
						<c:if test="${!empty khathHierarchyHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s):</td>
								<td class="danger col-sm-11" colspan="3"
									title="${khathHierarchyHistory.justification}">${khathHierarchyHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty khathTransferDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty khathTransferDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Transfered Quantity</td>
<!-- 							<td style="">Unit Cost</td> -->
<!-- 							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${khathTransferDtlList}" var="khathTransferDtl">
							<tr>

								<td><c:out value="${khathTransferDtl.itemId}" /></td>
								<td><c:out value="${khathTransferDtl.description}" /></td>
								<td><c:out value="${khathTransferDtl.uom}" /></td>
								<td><c:out value="${khathTransferDtl.trnasferQty}" /></td>
<%-- 								<td><c:out value="${khathTransferDtl.unitCost}" /></td> --%>
<%-- 								<td><c:out value="${khathTransferDtl.totalCost}" /></td> --%>
								<td><c:out value="${khathTransferDtl.remarks}" /></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>

		<div class="col-xs-12">
			<hr />
		</div>


	</div>
</div>

<!-- -------------------------- -->

<!-- JS call -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csKhathTransferShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>