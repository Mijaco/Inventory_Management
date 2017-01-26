<%@include file="../common/lsHeader.jsp"%>

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
			<a href="${pageContext.request.contextPath}/proc/procContractorForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span> Add
				Contractor
			</a>

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
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
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">

							<table id="contractorListTable"
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Contract No</td>
										<td style="">Contractor Name</td>
										<td style="">Created Date</td>
										<td style="">Address</td>
										<td style="">Contractor Type</td>
										<td style="">Project</td>
										<td style="">Tender No</td>
										<td style="">Contract Date</td>
										<td style="">Expired Date</td>
										<td style="">Representative(s)</td>
										<!-- <td style="">Material List</td>
										<td style="">Job(s)</td> -->
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorList}" var="contractor"
										varStatus="loop">
										<tr>
											<td><c:out value="${contractor.contractNo}" /></td>
											<td><c:out value="${contractor.contractorName}" /></td>
											<td><c:out value="${contractor.createdDate}" /></td>
											<td><c:out value="${contractor.address}" /></td>
											<td><c:out value="${contractor.division}" /></td>
											<td><c:out value="${contractor.khathName}" /></td>
											<td><c:out value="${contractor.tenderNo}" /></td>
											<td><fmt:formatDate value="${contractor.contractDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><fmt:formatDate
													value="${contractor.updatedValidityDate}"
													pattern="dd-MM-yyyy" /></td>
											<%-- 
											<td id="active${loop.index}">
												<c:choose>
													<c:when test="${contractor.active}">
			       										Yes
			    									</c:when>
			
													<c:otherwise>
			        									No
			    									</c:otherwise>
												</c:choose>
											</td>
											<td><c:out value="${contractor.remarks}" /></td>
											 --%>
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/ls/contRepresentative/finalList.do',{id:'${contractor.id}'},'POST')">
													Representative </a></td>

											<%-- <td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/contMats/finalList.do',{id:'${contractor.id}'},'POST')">
													Material List </a></td>

											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/jobDetails.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td> 
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/finalList.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td> --%>
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
	/* var $datepicker = $('#fromDate');
	var d = new Date();
	d.setMonth(d.getMonth() - 1, d.getDate());
	$datepicker.datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$datepicker.datepicker('setDate', d);

	var $datepicker1 = $('#toDate');
	$datepicker1.datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$datepicker1.datepicker('setDate', new Date()); */

	$(document)
			.ready(
					function() {
						$('#contractorListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 2 ],
								"visible" : false,
								"searchable" : false
							} ],
							"order" : [ [ 2, "desc" ] ]

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
