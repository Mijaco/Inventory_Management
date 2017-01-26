<%@include file="../../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/jobcard/approvedJobList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Job Card</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Job Card No:</td>
					<td class="info">${jobCardMstDb.jobCardNo}<input type="hidden"
						id="jobCardNo" value="${jobCardMstDb.jobCardNo}" />
						<input type="hidden"
						id="jobCardMstId" value="${jobCardMstDb.id}" />
						 <input
						type="hidden" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>
					<td class="success">CreatedBy:</td>
					<td class="info">${jobCardMstDb.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${jobCardMstDb.createdDate}"
							pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Transformer Serial No.:</td>
					<td class="info">${jobCardMstDb.transformerSerialNo}</td>
					<td class="success">Store Ticket/Reference No.:</td>
					<td class="info">${jobCardMstDb.typeOfWork}</td>
					<td class="success">Manufactured Name:</td>
					<td class="info">${jobCardMstDb.manufacturedName}</td>
				</tr>
				
				<tr class="">
					<td class="success">Version:</td>
					<td class="info">${jobCardMstDb.version}</td>
					<td class="success">Manufactured Year:</td>
					<td class="info">${jobCardMstDb.transformerRegister.manufacturedYear}</td>
					<td class="success">Status:</td>
					<td class="info bold"> <strong> ${currentStatus}d </strong></td>
				</tr>

			</table>
		</div>

		<br> <br>
		<hr>
		<div class="alert alert-success hide">
			<strong>Success!</strong> Safety Margin is updated.
		</div>
		<div class="alert alert-danger hide">
			<strong>Fail!</strong> Safety Margin is not updated.
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
			<c:if test="${empty jobCardDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty jobCardDtlList}">
				<table id="jobCardListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Quantity</td>
							<td style="">Recovery Quantity</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${jobCardDtlList}" var="jobCardDtl"
							varStatus="loop">
							<tr>
								<td><c:out value="${jobCardDtl.itemCode}" /> <input
									type="hidden" name="jobCardDtlId" class="jobCardDtlId"
									value="${jobCardDtl.id}" /></td>
								<td><c:out value="${jobCardDtl.itemName}" /></td>
								<td><c:out value="${jobCardDtl.unit}" /></td>
								<td>
								<c:out value="${jobCardDtl.quantityUsed}" />
								</td>
								<td>
								<c:out value="${jobCardDtl.quantityRecovery}" />
								</td>
								<td>
								<c:out value="${jobCardDtl.remarks}" />
								</td>								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#jobCardListTable').DataTable({
							"order" : [ [ 0, "asc" ] ],
							"paging": false,
							"info": false
						});
						document.getElementById('jobCardListTable_length').style.display = 'none';
						//document.getElementById('jobCardListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>