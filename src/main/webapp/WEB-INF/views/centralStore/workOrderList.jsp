<%@include file="../common/csHeader.jsp"%>
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
			<a href="#" style="text-decoration: none;">Store Ticket Issued</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/workOrder/getForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Work Order
			</a>
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Work
				Order List</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty workOrderMstList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty workOrderMstList}">
				<!-- 				<div class="col-sm-6 pull-right"> -->
				<!-- 					<form method="POST" -->
				<%-- 						action="${pageContext.request.contextPath}/workOrder/searchByWONo.do"> --%>

				<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
				<!-- 							<input type="text" class="form-control" id="search" -->
				<!-- 								placeholder="Search by Work Order No." -->
				<!-- 								style="border: 0; border-bottom: 2px ridge;" name="workOrderNo" /> -->
				<!-- 						</div> -->
				<!-- 						<div class="col-sm-1"> -->
				<!-- 							<button type="submit" -->
				<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
				<!-- 						</div> -->
				<!-- 					</form> -->
				<!-- 				</div> -->
				<table id="workOrderListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<td style="">Work Order No</td>
							<td>ID</td>
							<td style="">Work Order Date</td>
							<td style="">Supplier Name</td>
							<td style="">PLI Performed</td>
							<td style="">PSI Performed</td>
							<td style="">Reference Document</td>
							<!--<td style="">woDate</td> -->
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workOrderMstList}" var="workOrderMst">
							<tr>

								<td><a href="#" class="blue"
									onclick="postSubmit('${pageContext.request.contextPath}/workOrder/show.do',{id:'${workOrderMst.id}'},'POST')">
										<c:out value="${workOrderMst.workOrderNo}" />
								</a></td>
								<td>${workOrderMst.id}</td>
								<td><c:out value="${workOrderMst.contractDate}" /></td>
								<td><c:out value="${workOrderMst.supplierName}" /></td>
								<td><c:choose>
										<c:when test="${workOrderMst.pli == 'true'}">
											<c:out value="Yes" />
										</c:when>
										<c:otherwise>
											<c:out value="No" />
										</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${workOrderMst.psi == 'true'}">
											<c:out value="Yes" />
										</c:when>
										<c:otherwise>
											<c:out value="No" />
										</c:otherwise>
									</c:choose></td>
								<td><c:if test="${!empty workOrderMst.referenceDoc}">
										<%-- 
										<a href="#" target="_blank"
											onclick="postSubmit('${pageContext.request.contextPath}/workOrder/download.do',{referenceDoc:'${workOrderMst.referenceDoc}'},'POST')">
											Attachment </a> 
										class="glyphicon glyphicon-folder-open blue center" 
											--%>
										<form target="_blank"
											action="${pageContext.request.contextPath}/workOrder/download.do"
											method="POST">
											<input type="hidden" value="${workOrderMst.referenceDoc}"
												name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if></td>
								<%--<td><c:out value="${workOrderMst.woDate}" /></td> --%>

								<td>
									<div class="action-buttons">
										<!-- <a href="#"> <i class="ace-icon fa fa-pencil bigger-130"></i> </a> -->
										<a href="#" class="btn btn-primary btn-xs" style="border-radius: 6px;"
											onclick="postSubmit('${pageContext.request.contextPath}/workOrder/show.do',{id:'${workOrderMst.id}'},'POST')">
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
						$('#workOrderListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 1 ],
								"visible" : false
							} ],
							"order" : [ [ 1, 'desc' ] ]
						});
						document.getElementById('workOrderListTable_length').style.display = 'none';
						//document.getElementById('workOrderListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
