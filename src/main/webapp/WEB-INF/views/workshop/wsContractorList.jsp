<%@include file="../common/wsHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			<a href="${pageContext.request.contextPath}/ws/xf/contractorForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> </span> New Contractor
			</a>

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				List</h2>
		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty contractorList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Sorry!!! No Contractor Found in Database. </i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty contractorList}">
				<div class="row table-responsive">
					<div class="table">
						<div style="">

							<table id="contractorListTable"
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										
										<td style="">id</td>
										<td style="">Work Order No</td>
										<td style="">Contractor Name</td>
										<td style="">Contract Date</td>
										<td style="">Expired Date</td>
										<td style="">Representative(s)</td>										
										<td style="">Job(s)</td> 
										<td style="">Allocation List</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorList}" var="contractor"
										varStatus="loop">
										<tr>
											
											<td>${contractor.id}</td>
											<td><c:out value="${contractor.contractNo}" /></td>
											<td><c:out value="${contractor.contractorName}" /></td>
											<td><fmt:formatDate value="${contractor.contractDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><fmt:formatDate
													value="${contractor.updatedValidityDate}"
													pattern="dd-MM-yyyy" /></td>
													
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/contRepList.do',{id:'${contractor.id}'},'POST')">
													Representative </a></td>											
													
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/jobsList.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td> 
													
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/ws/xf/wsConXFormerAllocation.do',{id:'${contractor.id}'},'POST')">
													Allocation List </a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
			<!-- --------------------- -->
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
						$('#contractorListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ],
							"order" : [ [ 0, "desc" ] ]

						});
						document.getElementById('contractorListTable_length').style.display = 'none';
						//document.getElementById('contractorListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
