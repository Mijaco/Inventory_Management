<%@include file="../../common/pdHeader.jsp"%>
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
			<a
				href="${pageContext.request.contextPath}/template/unused/jobEstimationList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Cost Estimation List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Cost Estimation</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">P&amp;D No: <input type="hidden"
						value="${costEstimationMst.pndNo}"
						id="pndNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" /> 
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>

					<td class="info">${costEstimationMst.pndNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${costEstimationMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
	value="${costEstimationMst.createdDate}" pattern="dd-MM-yyyy" />}</td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${costEstimationMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
	value="${costEstimationMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>


		<%-- <div class="">
			<c:if test="${!empty itemRcvApproveHistoryList}">
				<c:forEach items="${itemRcvApproveHistoryList}"
					var="itemRcvApproveHistory">
					<table class="col-sm-12 table">
						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">${itemRcvApproveHistory.createdBy}</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
	value="${itemRcvApproveHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
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
		</div> --%>

<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Cost Of Materials :</label>
		<div style="background: white;">
			<c:if test="${empty costEstimateMaterialsDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty costEstimateMaterialsDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Item Name</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Total Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${costEstimateMaterialsDtlList}"
							var="costEstimateMaterialsDtl" varStatus="loop">
							<tr>
								<td><c:out value="${costEstimateMaterialsDtl.itemCode}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.itemName}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.uom}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.quantity}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.unitPrice}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.totalPrice}" /></td>
								<td><c:out value="${costEstimateMaterialsDtl.remarks}" /></td>		
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>

<label for="fax" class="col-sm-4 control-label" style="font-style: italic; font-weight: bold;">Cost Of Installation :</label>
		<div style="background: white;">
			<c:if test="${empty costEstimateInstallationDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty costEstimateInstallationDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							
							<td style="">Item Name</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Total Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${costEstimateInstallationDtlList}"
							var="costEstimateInstallationDtl" varStatus="loop">
							<tr>
								<td><c:out value="${costEstimateInstallationDtl.itemName}" /></td>
								<td><c:out value="${costEstimateInstallationDtl.uom}" /></td>
								<td><c:out value="${costEstimateInstallationDtl.quantity}" /></td>
								<td><c:out value="${costEstimateInstallationDtl.unitPrice}" /></td>
								<td><c:out value="${costEstimateInstallationDtl.totalPrice}" /></td>
								<td><c:out value="${costEstimateInstallationDtl.remarks}" /></td>		
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Cost Of Recovery :</label>
		<div style="background: white;">
			<c:if test="${empty costEstimateRecoveryDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty costEstimateRecoveryDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							
							<td style="">Item Name</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Total Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${costEstimateRecoveryDtlList}"
							var="costEstimateRecoveryDtl" varStatus="loop">
							<tr>
								<td><c:out value="${costEstimateRecoveryDtl.itemName}" /></td>
								<td><c:out value="${costEstimateRecoveryDtl.uom}" /></td>
								<td><c:out value="${costEstimateRecoveryDtl.quantity}" /></td>
								<td><c:out value="${costEstimateRecoveryDtl.unitPrice}" /></td>
								<td><c:out value="${costEstimateRecoveryDtl.totalPrice}" /></td>
								<td><c:out value="${costEstimateRecoveryDtl.remarks}" /></td>
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		
	<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Cost Of Miscellanious :</label>
		<div style="background: white;">
			<c:if test="${empty costEstimateMiscellaniousDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty costEstimateMiscellaniousDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							
							<td style="">Item Name</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Total Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${costEstimateMiscellaniousDtlList}"
							var="costEstimateMiscellaniousDtl" varStatus="loop">
							<tr>
								<td><c:out value="${costEstimateMiscellaniousDtl.itemName}" /></td>
								<td><c:out value="${costEstimateMiscellaniousDtl.uom}" /></td>
								<td><c:out value="${costEstimateMiscellaniousDtl.quantity}" /></td>
								<td><c:out value="${costEstimateMiscellaniousDtl.unitPrice}" /></td>
								<td><c:out value="${costEstimateMiscellaniousDtl.totalPrice}" /></td>
								<td><c:out value="${costEstimateMiscellaniousDtl.remarks}" /></td>
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
</div> </div>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>