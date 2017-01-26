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
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
			Job(s)</h2>
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
			<c:if test="${empty jobCardMstList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty jobCardMstList}">
				<table id="jobCardMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td style="">Job No</td>
							<td style="">Version</td>
							<td style="">Type Of Work</td>
							<td style="">Transformer Type</td>
							<td style="">Transformer Serial No</td>
							<td style="">Manufactured Name</td>
							<td style="">Manufactured Year</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${jobCardMstList}" var="jobCardMst">
							<tr>
								<td>${jobCardMst.id}</td>
								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/jobDetails.do',{id:'${jobCardMst.id}'},'POST')">
										<c:out value="${jobCardMst.jobCardNo}" />
								</a></td>
								<td><c:out value="${jobCardMst.version}" /></td>
								<td><c:out value="${jobCardMst.typeOfWork}" /></td>								
								<td><c:out value="${jobCardMst.transformerType}" /></td>
								<td><c:out value="${jobCardMst.transformerSerialNo}" /></td>
								<td><c:out value="${jobCardMst.manufacturedName}" /></td>
								<td><c:out value="${jobCardMst.manufacturedYear}" /></td>	 
								<td><c:out value="${jobCardMst.remarks}" /></td>
								<td><a href="#" class="red"
									onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/jobDetails.do',{id:'${jobCardMst.id}'},'POST')">
										<i class="glyphicon glyphicon-eye-open"></i>
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<%-- <div class="center">
					<a href="${pageContext.request.contextPath}/contractor/conJobReport.do?id=${contractor.contractNo}"
						style="text-decoration: none;" target="_blank" class="btn btn-primary btn-sm"> <span
						class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						Work Issue Report
					</a>
				</div> --%>
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
						$('#jobCardMstListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ],
							"order" : [ [ 0, "desc" ] ]

						});
						document.getElementById('jobCardMstListTable_length').style.display = 'none';
						//document.getElementById('contractorListTable_filter').style.display = 'none';
					});
</script> 

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>