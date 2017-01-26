<%@include file="../common/lsHeader.jsp"%>
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
			<%-- <a
				href="${pageContext.request.contextPath}/ls/storeRequisitionForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Store Requisition
			</a> --%>
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Received List After Requisition Approval</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty centralStoreRequisitionMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<div class="col-sm-12 center">
				<h6 class="red">
					<i>${msg}</i>
				</h6>
			</div>

			<c:if test="${!empty centralStoreRequisitionMstList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/ss/itemRecieved/requisitionReceivedSearch.do">

						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Requisition No."
								style="border: 0; border-bottom: 2px ridge;"
								name="requisitionNo" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>

				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Requisition No</td>
							<td style="">Identer Name</td>
							<td style="">Requisition Date</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionMstList}"
							var="centralStoreRequisitionMst">

							<c:choose>
								<c:when test="${!centralStoreRequisitionMst.received}">
									<tr class="info" style="font-weight: bold; color: green;">
										<td><a
											href="${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceivedShow.do?id=${centralStoreRequisitionMst.id}&requisitionTo=${centralStoreRequisitionMst.requisitionTo}"
											style="text-decoration: none;"><c:out
													value="${centralStoreRequisitionMst.requisitionNo}" /></a></td>
										<td><c:out
												value="${centralStoreRequisitionMst.identerDesignation}" /></td>
										<td><fmt:formatDate
												value="${centralStoreRequisitionMst.requisitionDate}"
												pattern="dd-MM-yyyy" /></td>

										<td>
											<div class="action-buttons">
												<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
													href="${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceivedShow.do?id=${centralStoreRequisitionMst.id}&received=${centralStoreRequisitionMst.received}">
													<i class="fa fa-fw fa-eye"></i>&nbsp;Show
												</a> |
												<c:if test="${!centralStoreRequisitionMst.received}">
													<a class="btn btn-success btn-xs" style="border-radius: 6px;"
														href="${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceiving.do?id=${centralStoreRequisitionMst.id}&received=${centralStoreRequisitionMst.received}">
														<i class="fa fa-fw fa-download"></i>&nbsp;Receive
													</a>
												</c:if>
											</div>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr class="default">
										<td><a
											href="${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceivedShow.do?id=${centralStoreRequisitionMst.id}&requisitionTo=${centralStoreRequisitionMst.requisitionTo}"
											style="text-decoration: none;"><c:out
													value="${centralStoreRequisitionMst.requisitionNo}" /></a></td>
										<td><c:out
												value="${centralStoreRequisitionMst.identerDesignation}" /></td>
										<td><fmt:formatDate
												value="${centralStoreRequisitionMst.requisitionDate}"
												pattern="dd-MM-yyyy" /></td>

										<td>
											<div class="action-buttons">
												<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
													href="${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceivedShow.do?id=${centralStoreRequisitionMst.id}&received=${centralStoreRequisitionMst.received}">
													<i class="fa fa-fw fa-eye"></i>&nbsp;Show
												</a>
											</div>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
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
						$('#centralStoreRequisitionListTable').DataTable();
						document
								.getElementById('centralStoreRequisitionListTable_length').style.display = 'none';
						document
								.getElementById('centralStoreRequisitionListTable_filter').style.display = 'none';

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
