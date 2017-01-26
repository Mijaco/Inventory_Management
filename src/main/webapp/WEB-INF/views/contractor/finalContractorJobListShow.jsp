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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/contractor/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
			Job(s)</h2>
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
						<td class="info">${contractor.contractNo}</td>
						<td class="success">Contractor Date:</td>
						<td class="info"><fmt:formatDate
								value="${contractor.contractDate}" pattern="dd-MM-yyyy" /></td>
						<td class="success">Expired Date:</td>
						<td class="info"><fmt:formatDate
								value="${contractor.updatedValidityDate}" pattern="dd-MM-yyyy" /></td>
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
			<c:if test="${empty pndJobMstList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty pndJobMstList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Job No</td>
							<td style="">Title</td>
							<td style="">PD No</td>
							<td style="">PnD No</td>
							<td style="">Name</td>
							<td style="">Address</td>
							<td style="">Lot</td>
							<td style="">Construction Nature</td>
							<td style="">Location</td>
							<td style="">Remarks</td>
							<td style="">As Build Report</td>
							<td style="">Action</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pndJobMstList}" var="pndJobMst">
							<tr>
								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/jobs/jobDetails.do',{id:'${pndJobMst.id}'},'POST')">
										<c:out value="${pndJobMst.jobNo}" />
								</a></td>
								<%-- <td><c:out value="${pndJobMst.jobNo}" /></td> --%>
								<td><c:out value="${pndJobMst.jobTitle}" /></td>
								<td><c:out value="${pndJobMst.pdNo}" /></td>
								<td><c:out value="${pndJobMst.pndNo}" /></td>
								<td><c:out value="${pndJobMst.name}" /></td>
								<td><c:out value="${pndJobMst.address}" /></td>
								<td><c:out value="${pndJobMst.lot}" /></td>
								<td><c:out value="${pndJobMst.constructionNature}" /></td>
								<td><c:out value="${pndJobMst.jobLocation}" /></td>
								<td><c:out value="${pndJobMst.remarks}" /></td>
								<%-- <td class="red center"><strong>${pndJobMst.asBuildNo==null?'Create As Build Report':contractor.asBuildNo}
								</strong></td> --%>

								<td class="center"><c:choose>
										<c:when test="${pndJobMst.asBuildNo==null}">
											<a href="${pageContext.request.contextPath}/co/asBuilt.do">Create
												As Build Report</a>
										</c:when>

										<c:otherwise>
											<a target="_blank" href="${pageContext.request.contextPath}/asBuilt/singleJobReport.do?operationId=${pndJobMst.asBuildNo}">${pndJobMst.asBuildNo}</a>
										</c:otherwise>
									</c:choose></td>

								<td><a href="#" class="btn btn-primary btn-xs"
									onclick="postSubmit('${pageContext.request.contextPath}/jobs/jobDetails.do',{id:'${pndJobMst.id}'},'POST')">
										<i class="glyphicon glyphicon-eye-open"></i>&nbsp;Show
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="center">
					<a
						href="${pageContext.request.contextPath}/contractor/conJobReport.do?id=${contractor.contractNo}"
						style="text-decoration: none;" target="_blank"
						class="btn btn-primary btn-sm"> <span
						class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						Work Issue Report
					</a>
				</div>
			</c:if>
		</div>
	</div>
</div>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>