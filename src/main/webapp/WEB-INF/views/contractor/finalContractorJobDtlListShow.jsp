<%@include file="../common/cnHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/contractor/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract List
			</a> <a href="#" class="btn btn-info btn-sm"
				onclick="postSubmit('${pageContext.request.contextPath}/jobs/finalList.do',{id:'${contractor.id}'},'POST')">
				<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Job List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
			Details</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<div class="col-xs-12">
				<table class="col-xs-12 table">
					<tr class="">
						<td class="success">Contract No:</td>
						<td class="info">${pndJobMst.woNumber}</td>
						<td class="success">Job No:</td>
						<td class="info">${pndJobMst.jobNo}</td>
						<td class="success">Title:</td>
						<td class="info">${pndJobMst.jobTitle}</td>
					</tr>

					<tr class="">
						<td class="success">Name:</td>
						<td class="info">${pndJobMst.name}</td>
						<td class="success">Address:</td>
						<td class="info">${pndJobMst.address}</td>
						<td class="success">Lot:</td>
						<td class="info">${pndJobMst.lot}</td>
					</tr>
					<tr class="">
						<td class="success">PD No:</td>
						<td class="info">${pndJobMst.pdNo}</td>
						<td class="success">PnD No:</td>
						<td class="info">${pndJobMst.pndNo}</td>
						<td class="success">Location :</td>
						<td class="info">${pndJobMst.jobLocation}</td>

					</tr>					
				</table>
			</div>
		</div>

		<div style="background: white;">
			<c:if test="${empty pndJobDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty pndJobDtlList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Total Quantity</td>
							<td style="">Extended Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Remarks</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pndJobDtlList}" var="pndJobDtl">
							<tr>
								<td><c:out value="${pndJobDtl.itemCode}" /></td>
								<td><c:out value="${pndJobDtl.itemName}" /></td>
								<td><c:out value="${pndJobDtl.uom}" /></td>
								<td><c:out value="${pndJobDtl.quantity}" /></td>
								<td><c:out value="${pndJobDtl.extendQuantity}" /></td>
								<td><c:out value="${pndJobDtl.remainningQuantity}" /></td>
								<td><c:out value="${pndJobDtl.remarks}" /></td>
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