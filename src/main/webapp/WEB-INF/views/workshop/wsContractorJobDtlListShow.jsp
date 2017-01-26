<%@include file="../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ws/xf/contractorList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract List
			</a> <a href="#" class="btn btn-info btn-sm"
				onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/jobsList.do',{id:'${contractor.id}'},'POST')">
				<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Job List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
			Details</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<div class="col-xs-12">
				<table class="col-xs-12 table">
					<tr class="">
						<td class="success">Contract No:</td>
						<td class="info">${jobCardMst.contractNo}</td>						
						<td class="success">Job No:</td>
						<td class="info">${jobCardMst.jobCardNo}</td>						
						<td class="success">Version:</td>
						<td class="info">${jobCardMst.version}</td>
					</tr>

					<tr class="">
						<td class="success">Transformer Type:</td>
						<td class="info">${jobCardMst.transformerType}</td>
						<td class="success">Transformer Serial No:</td>
						<td class="info">${jobCardMst.transformerSerialNo}</td>
						<td class="success">Manufactured Name:</td>
						<td class="info">${jobCardMst.manufacturedName}</td>
					</tr>
					
					<tr class="">
						<td class="success">Manufactured Year:</td>
						<td class="info">${jobCardMst.manufacturedYear}</td>
						<td class="success">Type of Work:</td>
						<td class="info">${jobCardMst.typeOfWork}</td>
						<td class="success">Remarks:</td>
						<td class="info">${jobCardMst.remarks}</td>
					</tr>
				</table>
			</div>
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
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Used Quantity</td>
							<td style="">Recovery Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Remarks</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${jobCardDtlList}" var="jobCardDtl">
							<tr>
								<td><c:out value="${jobCardDtl.itemCode}" /></td>
								<td><c:out value="${jobCardDtl.itemName}" /></td>
								<td><c:out value="${jobCardDtl.unit}" /></td>
								<td><fmt:formatNumber
											type="number" minFractionDigits="3" groupingUsed="false"
											value="${jobCardDtl.quantityUsed}" /></td>
								<td><fmt:formatNumber
											type="number" minFractionDigits="3" groupingUsed="false"
											value="${jobCardDtl.quantityRecovery}" /></td>
											
								<td><fmt:formatNumber
											type="number" minFractionDigits="3" groupingUsed="false"
											value="${jobCardDtl.remainningQuantity}" /></td>
								
								<td><c:out value="${jobCardDtl.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>