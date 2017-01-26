<%@include file="../common/pdHeader.jsp"%>
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
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/co/asBuilt.do"
				style="text-decoration: none; " class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				As Built Form
			</a>
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">As Built
				 List</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty asBuiltMst}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task on Cost Estimation Approve. </i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty asBuiltMst}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/cn/requisitionSearch.do"> --%>

<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by P&D No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" -->
<!-- 								name="pndNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->

				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">As Built No</td>
							<td style="">Work Order</td>
							<td style="">Created Date</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${asBuiltMst}"
							var="asBuilt">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/asBuilt/newAsBuiltShow.do?id=${asBuilt.id}"
									style="text-decoration: none;">${asBuilt.asBuiltNo}</a></td>	
											<td>${asBuilt.woNumber}</td>								
								<td><fmt:formatDate
										value="${asBuilt.createdDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>
									<div class="action-buttons" align="center">
										<%-- <a
											href="${pageContext.request.contextPath}/ls/storeRequisitionEdit.do?id=${subStoreRequisitionMst.id}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> --%>
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/asBuilt/newAsBuiltShow.do?id=${asBuilt.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a>
										<a class="btn btn-success btn-xs" style="border-radius: 6px;" target="_blank" href="${pageContext.request.contextPath}/asBuilt/singleJobReport.do?operationId=${asBuilt.asBuiltNo}"><i class="fa fa-fw fa-info"></i>&nbsp;Report</a>
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
						$('#storeRequisitionListTable').DataTable();
						document
								.getElementById('storeRequisitionListTable_length').style.display = 'none';
						//document.getElementById('storeRequisitionListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		/* $(".container-fluid").append("<div>hello</div>"); */
	}
</script>
<!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
