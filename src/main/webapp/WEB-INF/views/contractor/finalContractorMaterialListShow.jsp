<%@include file="../common/pdHeader.jsp"%>
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
				Contractor List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item
			Issued Show</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">
			<div class="col-xs-12">
				<table class="col-xs-12 table">
					<tr class="">
						<td class="success">Contract No:</td>
						<td class="info">${contractor.contractNo}</td>
						<td class="success">Contractor Date:</td>
						<td class="info"><fmt:formatDate
								value="${contractor.contractDate}"
								pattern="dd-MM-yyyy" /></td>
						<td class="success">Expired Date:</td>
						<td class="info"><fmt:formatDate
								value="${contractor.updatedValidityDate}"
								pattern="dd-MM-yyyy" /></td>
					</tr>

					<tr class="">
						<td class="success">Contractor Name:</td>
						<td class="info">${contractor.contractorName}</td>
						<td class="success">Contractor Address:</td>
						<td class="info">${contractor.address}</td>
						<td class="success">Division:</td>
						<td class="info">${contractor.division}</td>
					</tr>
					<tr class="">
						<td class="success">Tender No:</td>
						<td class="info">${contractor.tenderNo}</td>
						<td class="success">Project:</td>
						<td class="info">${contractor.khathName}</td>

						<td class="success">Is Active?</td>
						<td class="info"><c:choose>
								<c:when test="${contractor.active}">
       										Yes
    									</c:when>

								<c:otherwise>
        									No
    									</c:otherwise>
							</c:choose></td>

					</tr>
				</table>
			</div>
		</div>

		<div style="background: white;">
			<c:if test="${empty jobItemMaintenanceList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty jobItemMaintenanceList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Total Quantity</td>
							<td style="">As Build Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Remarks</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${jobItemMaintenanceList}"
							var="jobItemMaintenance">
							<tr>
								<td><c:out value="${jobItemMaintenance.itemCode}" /></td>
								<td><c:out value="${jobItemMaintenance.itemName}" /></td>
								<td><c:out value="${jobItemMaintenance.uom}" /></td>
								<td><c:out value="${jobItemMaintenance.quantity}" /></td>
								<td><c:out value="${jobItemMaintenance.asBuiltQuantity}" /></td>
								<td><c:out value="${jobItemMaintenance.remainningQuantity}" /></td>
								<td><c:out value="${jobItemMaintenance.remarks}" /></td>
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