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
		<div class="o_form_buttons_edit" style="display: block;">
			<%-- <a
				href="${pageContext.request.contextPath}/cn/pd/getReturnSlipForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Return Slip
			</a> --%>
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
				Slip List</h1>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty returnSlipMstList}">
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

			<c:if test="${!empty returnSlipMstList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/cn/returnSlip/rsSearch.do"> --%>

<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by Return Slip No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="returnSlipNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->

				<table id="returnSlipListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Return Slip No</td>
							<td style="">Return Slip Date</td>
							<td style="">Return By</td>
							<td style="">Return To</td>
							<td style="">Work Order No</td>
							<td style="">Work Order Date</td>
							<td style="">Zone</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${returnSlipMstList}" var="returnSlipMst">
							<tr>
								<td>
									<c:choose>
										<c:when test="${returnSlipMst.senderStore == 'cn' && returnSlipMst.returnTo == 'ss'}">
											<a href="${pageContext.request.contextPath}/${returnSlipMst.senderStore}/returnSlip/pageShowCn.do?id=${returnSlipMst.id}"
												style="text-decoration: none;">${returnSlipMst.returnSlipNo}</a>
										</c:when>
										<c:otherwise>
											<a
												href="${pageContext.request.contextPath}/${returnSlipMst.senderStore}/returnSlip/pageShowCs.do?id=${returnSlipMst.id}&returnTo=${returnSlipMst.returnTo}"
												style="text-decoration: none;">${returnSlipMst.returnSlipNo}</a>
										</c:otherwise>
									</c:choose>
								</td>

								<td><fmt:formatDate value="${returnSlipMst.returnSlipDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><c:out value="${returnSlipMst.receiveFrom}" /></td>
								<td>${returnSlipMst.returnTo == 'cs' ? 'XEN, CENTRAL STORE' : 'XEN, SUB STORE'}</td>

								<td><c:out value="${returnSlipMst.workOrderNo}" /></td>
								<td><fmt:formatDate value="${returnSlipMst.workOrderDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><c:out value="${returnSlipMst.zone}" /></td>

								<td>
									<div class="action-buttons">
										<c:choose>
											<c:when
												test="${returnSlipMst.senderStore == 'cn' && returnSlipMst.returnTo == 'ss'}">
												<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
													href="${pageContext.request.contextPath}/${returnSlipMst.senderStore}/returnSlip/pageShowCn.do?id=${returnSlipMst.id}"
													style="text-decoration: none;"><i class="fa fa-fw fa-eye"></i>&nbsp;Show</a>
											</c:when>
											<c:otherwise>
												<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
													href="${pageContext.request.contextPath}/${returnSlipMst.senderStore}/returnSlip/pageShowCs.do?id=${returnSlipMst.id}&returnTo=${returnSlipMst.returnTo}"
													style="text-decoration: none;"><i class="fa fa-fw fa-eye"></i>&nbsp;Show</a>
											</c:otherwise>
										</c:choose>
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
						$('#returnSlipListTable').DataTable({
							"order" : [ [ 1, "desc" ] ]
						});
						document.getElementById('returnSlipListTable_length').style.display = 'none';
						//document.getElementById('returnSlipListTable_filter').style.display = 'none';

					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
