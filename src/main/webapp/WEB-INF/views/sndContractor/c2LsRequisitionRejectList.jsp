<%@include file="../common/sndCnHeader.jsp"%>
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
			<a
				href="${pageContext.request.contextPath}/c2ls/storeRequisitionForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Store Requisition
			</a>
			<h2 class="center blue" style="margin-top: 0px;">Store
				Requisition</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty centralStoreRequisitionMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task on Store
							Requisition. </i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty centralStoreRequisitionMstList}">

				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Requisition No</td>
							<td style="">Identer Name</td>
							<td style="">Requisition Date</td>
							<td style="">Gate Pass No</td>
							<td style="">Gate Pass Date</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionMstList}"
							var="centralStoreRequisitionMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/c2ls/storeRequisitionRejectShow.do?id=${centralStoreRequisitionMst.id}&requisitionTo=${centralStoreRequisitionMst.requisitionTo}"
									style="text-decoration: none;"><c:out
											value="${centralStoreRequisitionMst.requisitionNo}" /></a></td>
								<td><c:out
										value="${centralStoreRequisitionMst.identerDesignation}" /></td>
								<td><fmt:formatDate
										value="${centralStoreRequisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>

								<td><c:out value="${centralStoreRequisitionMst.gatePassNo}" /></td>
								<td><fmt:formatDate
										value="${centralStoreRequisitionMst.gatePassDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>
								<td>
									<div class="action-buttons">
										<%-- <a
											href="${pageContext.request.contextPath}/ls/storeRequisitionEdit.do?id=${centralStoreRequisitionMst.id}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> --%>
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/c2ls/storeRequisitionRejectShow.do?id=${centralStoreRequisitionMst.id}&requisitionTo=${centralStoreRequisitionMst.requisitionTo}">
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
						$('#storeRequisitionListTable').DataTable({
								"paging" : false,
								"info" : false,
								"order" : [ [ 0, 'desc' ] ]		
						});
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
