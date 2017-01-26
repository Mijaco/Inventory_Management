<%@include file="../common/ssHeader.jsp"%>
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
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Returned List After Return Slip Approval</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>

		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty returnSlipMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty returnSlipMstList}">
				<div class="col-sm-6 pull-right">
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/ss/itemRecieved/requisitionReceivedSearch.do"> --%>

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
				</div>

				<table id="itemReturnListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Return Slip No</td>
							<td style="">Receive From</td>
							<td style="">Return Slip Date</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${returnSlipMstList}" var="returnSlipMst">

							<c:choose>
								<c:when test="${!returnSlipMst.returned}">
									<tr class="info" style="font-weight: bold; color: green;">
										<td><a href="#" style="text-decoration: none;"
											onclick="postSubmit('${pageContext.request.contextPath}/ss/locUp/ssCsItemReturnedShow.do',{id:'${returnSlipMst.id}'},'POST')">
												<c:out value="${returnSlipMst.returnSlipNo}" />
										</a></td>
										<td><c:out value="${returnSlipMst.receiveFrom}" /></td>
										<td><fmt:formatDate
												value="${returnSlipMst.returnSlipDate}"
												pattern="dd-MM-yyyy" /></td>

										<td>
											<div class="action-buttons">
												<a href="#" class="btn btn-purple btn-xs" style="border-radius: 6px;"
													onclick="postSubmit('${pageContext.request.contextPath}/ss/locUp/ssCsItemReturnedShow.do',{id:'${returnSlipMst.id}'},'POST')">
													<i class="fa fa-fw fa-eye"></i>&nbsp;Show
												</a>
											</div>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr class="default">
										<td><a href="#" style="text-decoration: none;"
											onclick="postSubmit('${pageContext.request.contextPath}/ss/locUp/ssCsItemReturnedShow.do',{id:'${returnSlipMst.id}'},'POST')">
												<c:out value="${returnSlipMst.returnSlipNo}" />
										</a></td>
										<td><c:out value="${returnSlipMst.receiveFrom}" /></td>
										<td><fmt:formatDate
												value="${returnSlipMst.returnSlipDate}"
												pattern="dd-MM-yyyy" /></td>

										<td>
											<div class="action-buttons">
												<a href="javascript:void(0)" class="btn btn-primary btn-xs" style="border-radius: 6px;"
													onclick="postSubmit('${pageContext.request.contextPath}/ss/locUp/ssCsItemReturnedShow.do',{id:'${returnSlipMst.id}'},'POST')">
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
						$('#itemReturnListTable').DataTable();
						document.getElementById('itemReturnListTable_length').style.display = 'none';
						//document.getElementById('itemReturnListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
