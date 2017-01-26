<%@include file="../../common/procurementHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<style>
.ui-datepicker table {
	display: none;
}

.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<%-- <div class="o_form_buttons_edit  col-md-2">
			<a
				href="${pageContext.request.contextPath}/mps/procurementPackageForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create APP Package
			</a>
		</div>		 --%>
		<div class="col-md-12 ">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Purchase List From APP</h2>

		</div>

		<div class="col-sm-12 text-right">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
			<!-- --------------------- -->
			<div class="alert alert-success hide">
				<strong>Congratulation!</strong> Information is updated
				successfully.
			</div>
			<div class="alert alert-danger hide">
				<strong>Sorry!</strong> Information update is failed!!!.
			</div>
			<!-- --------------------- -->
		</div>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px 0;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty appProcMstList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Congratulations! you have no pending task. </i>
					</p>
				</div>
			</c:if>


			<c:if test="${!empty appProcMstList}">
				<table id="appProcMstListTable" style="width: 2400px;"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">Annexure No</td>
							<td style="">Package Name</td>
							<td style="">Requisition Ref.</td>
							<td style="">Project Name</td>
							<td style="">Draft Tender Copy</td>
							<td style="">Approved Requisition
								Copy</td>
							<td style="">Comments</td>
							<td style="">Action</td>
							
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${appProcMstList}" varStatus="loop"
							var="appProcMst">
							<tr>
							<td><a href="javascript:void(0)" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/showProcurementForm.do',{id:'${appProcMst.id}'},'POST')">
										${appProcMst.procurementPackageMst.annexureNo} </a></td>
								<td>${appProcMst.procurementPackageMst.packageName}<input
									type="hidden" id="pk${loop.index}"
									value="${appProcMst.id}" />
								</td>
								<td>${appProcMst.requisitionRef}</td>
								<td>${appProcMst.projectName}</td>
								<td><%-- <a href="javascript:void(0)" class="fa fa-file-pdf-o red center" target="_blank"
								onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/download.do',{downloadDocFile:'${appProcMst.draftTenderDoc}'},'POST')"
											aria-hidden="true" style="font-size: 1.5em;"></a> --%><%-- ${appProcMst.draftTenderDoc} --%>
								<c:if test="${!empty appProcMst.draftTenderDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/app/purchase/download.do"
											method="POST">
											<input type="hidden" value="${appProcMst.draftTenderDoc}"
												name="downloadDocFile" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if>			
								</td>
								<td><%-- <a href="javascript:void(0)" class="fa fa-file-pdf-o red center" target="_blank"
								onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/download.do',{downloadDocFile:'${appProcMst.requisitionAppDoc}'},'POST')"
											aria-hidden="true" style="font-size: 1.5em;"></a> --%>
								<%-- ${appProcMst.requisitionAppDoc} --%>
								<c:if test="${!empty appProcMst.requisitionAppDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/app/purchase/download.do"
											method="POST">
											<input type="hidden" value="${appProcMst.requisitionAppDoc}"
												name="downloadDocFile" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if>
								</td>
								<td>${appProcMst.remarks}</td>
								<td>
									<div class="action-buttons">										
										<a href="javascript:void(0)" class="btn btn-primary btn-sm" style="border-radius: 6px;"
									onclick="postSubmit('${pageContext.request.contextPath}/app/purchase/showProcurementForm.do',{id:'${appProcMst.id}'},'POST')">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
									</div>
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
	$(document).ready(function() {
		$('#appProcMstListTable').DataTable({
			/* "order" : [ [ 2, "desc" ] ],
			"bLengthChange" : false */
		});

	});
	
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});		
</script>


<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>