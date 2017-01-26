<%@include file="../common/pndCnHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<!-- -------------------Start of Style and Script for Location Grid-------------------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<!-- -------------------End of Style and Script for Location Grid-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a 
				href="${pageContext.request.contextPath}/cnpd/csRejectedRequisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Rejected Requisition List
			</a> <input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}" />
		</div>
		<h2 class="center blue ubuntu-font" style="margin-top: 0px;">Store
			Requisition</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>


	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No:</td>

					<td class="info">${csRequisitionMst.requisitionNo}</td>
					<td class="success">Requisition By:</td>
					<td class="info">${csRequisitionMst.createdBy}</td>
					<td class="success">Requisition Date:</td>
					<td class="info"><fmt:formatDate
							value="${csRequisitionMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Indenter :</td>
					<td class="info">${csRequisitionMst.identerDesignation},
						${csRequisitionMst.deptName}</td>
					<td class="success">Requisition To :</td>
					<td class="info">${csRequisitionMst.requisitionTo=='cs'?'Central Store':'Sub Store'}</td>
					<td class="success">Present Status:</td>
					<td class="info red"><strong> Rejected </strong></td>
				</tr>

			</table>
		</div>


		<c:if test="${!empty approvalHierarchyHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>
		<div id="demo" class="collapse">
			<c:if test="${!empty approvalHierarchyHistoryList}">
				<c:forEach items="${approvalHierarchyHistoryList}" var="history">

					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(history.approvalHeader)}</td>
							
							<td class="success col-sm-6 text-left">
								${history.cEmpFullName} <c:if
									test="${!empty history.cDesignation}">
									, ${history.cDesignation} 
								</c:if> <c:if test="${!empty history.cEmpId}">
									(${history.cEmpId})
								</c:if>
							</td>
							
							<td class="info col-sm-1 text-left">Date:</td>
							<td class="info col-sm-2"><fmt:formatDate
									value="${history.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
						<c:if test="${!empty history.justification}">
							<tr class="">
								<td></td>
								<td class="success" colspan="3">Comments :
									${history.justification}</td>

							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty csRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csRequisitionDtlList}">
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
							<td style="">Remarks</td>

						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csRequisitionDtlList}"
							var="centralStoreRequisitionDtl" varStatus="loop">
							<tr>
								<td>${centralStoreRequisitionDtl.itemCode}</td>
								<td>${centralStoreRequisitionDtl.itemName}</td>
								<td>${centralStoreRequisitionDtl.uom}</td>
								<td>${centralStoreRequisitionDtl.quantityRequired}</td>
								<td>${centralStoreRequisitionDtl.quantityIssued}</td>
								<td>${centralStoreRequisitionDtl.remarks}</td>

							</tr>
							<c:set var="count" value="${loop.index}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>

	</div>
</div>




<script></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>