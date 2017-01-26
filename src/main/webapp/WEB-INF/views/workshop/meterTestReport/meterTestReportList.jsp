<%@include file="../../common/wsHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>

<!-- @author: Abu Taleb, Programmer, IBCS -->
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Meter
				Testing List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>


		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
		
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		
			<c:if test="${empty meterTestingReportList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Sorry!!! No data found.</i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty meterTestingReportList}">
				<table id="meterTestReportListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">ID</td>
							<td style="">Tracking No</td>
							<td style="">CMTL Sl. No</td>
							<td style="">Test Report Date</td>
							<td style="">Meter Type</td>
							<td style="">Meter Category</td>
							<td style="" class="center">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${meterTestingReportList}" var="meterTestReport">
							<tr>
								<td>${meterTestReport.id}</td>
								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportShow.do',{'id':'${meterTestReport.id}'},'POST')">
										<c:out value="${meterTestReport.trackingNo}" />
								</a></td>
								<td><c:out value="${meterTestReport.cmtlSlNo}" /></td>
								<td><fmt:formatDate value="${meterTestReport.reportDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td><c:out value="${meterTestReport.meterType}" /></td>
								<td><c:out value="${meterTestReport.meterCategory}" /></td>
								<td>
									<div class="action-buttons center">

										<c:if test="${meterTestReport.finalSubmit==false}">
											<a href="javascript:void(0)" class="btn btn-primary btn-xs"
												style="border-radius: 6px;"
												onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportEdit.do',{'id':'${meterTestReport.id}'},'POST')">
												<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
											</a>
											<a href="javascript:void(0)" class="btn btn-success btn-xs"
												style="border-radius: 6px;"
												onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportFinalSubmit.do',{'id':'${meterTestReport.id}'},'POST')">
												<i class="fa fa-fw fa-paper-plane"></i>&nbsp;Final Submit
											</a>
											<a href="javascript:void(0)" class="btn btn-danger btn-xs"
												style="border-radius: 6px;"
												onclick="deleteThis(${meterTestReport.id})">
												<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
											</a>
											
										</c:if>
										<c:if test="${meterTestReport.finalSubmit==true}">
											<a target="_blank"
												href="${pageContext.request.contextPath}/mtr/meterTestingReport.do?id=${meterTestReport.id}"
												class="btn btn-success btn-xs" style="border-radius: 6px;">
												<i class="fa fa-fw fa-file-pdf-o"></i>&nbsp;Report
											</a>
										</c:if>

										<a href="javascript:void(0)" class="btn btn-info btn-xs"
											style="border-radius: 6px;"
											onclick="postSubmit('${pageContext.request.contextPath}/mtr/meterTestReportShow.do',{'id':'${meterTestReport.id}'},'POST')">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>

<script>
	function deleteThis( id ) {
		
		if( confirm( "Do you want to delete this Meter test report?" ) == true ) {
			var contextPath = $('#contextPath').val();
			var path = contextPath + "/mtr/deleteTestReport.do";
			var params = {
				'id' : id
			}
			postSubmit(path, params, "POST");
		}
	}
</script>

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
						$('#meterTestReportListTable').DataTable({
							"order" : [ [ 0, "desc" ] ],
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ]
						});
						document
								.getElementById('meterTestReportListTable_length').style.display = 'none';
						// document.getElementById('meterTestReportListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';

	}
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
