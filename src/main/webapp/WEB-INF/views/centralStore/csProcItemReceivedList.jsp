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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Received Items</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/itemRecieved/procItemRcvFromByWOrder.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Store Receive Form
			</a>
		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Receiving
			Report List</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row" style="background-color: white; padding-left: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty csProcItemRcvMstList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>Congratulation!!! You have no pending task</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csProcItemRcvMstList}">
				<div class="col-sm-6 pull-right">
<!-- 					<form method="POST" -->
<%-- 						action="${pageContext.request.contextPath}/cs/itemRecieved/rcvItmSerch.do"> --%>
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by RR No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" -->
<!-- 								name="receivedReportNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
				</div>
				<table id="centralStorereceiveMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">R.R No</th>
							<th style="">Chalan No</th>
							<th style="">Contract No</th>
							<th style="">Received By</th>
							<th style="">Received Date</th>
							<th style="">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csProcItemRcvMstList}" var="csProcItemRcvMst">
							<tr>
								<td><a
									onclick="postSubmit('${pageContext.request.contextPath}/cs/itemRecieved/itemRcvShow.do',{receivedReportNo:'${csProcItemRcvMst.rrNo}'},'POST')"
									href="#" style="text-decoration: none;"><c:out
											value="${csProcItemRcvMst.rrNo}" /></a></td>
								<td><c:out value="${csProcItemRcvMst.chalanNo}" /></td>
								<td><c:out value="${csProcItemRcvMst.contractNo}" /></td>
								<td><c:out value="${csProcItemRcvMst.receivedBy}" /></td>
								<td><fmt:formatDate
										value="${csProcItemRcvMst.receivedDate}"
										pattern="dd-MM-yyyy" />
								<td>
									<div class="action-buttons">
										<!-- <a href="#"> <i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> --> <a onclick="postSubmit('${pageContext.request.contextPath}/cs/itemRecieved/itemRcvShow.do',{receivedReportNo:'${csProcItemRcvMst.rrNo}'},'POST')"
											href="javascript:void(0)" class="btn btn-primary btn-xs" style="border-radius: 6px;">
											<i class="glyphicon glyphicon-eye-open"></i>&nbsp;Show
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
<script>
$(document)
	.ready(
		function() {
			$('#centralStorereceiveMstListTable').DataTable({
				"columnDefs" : [ {
					"targets" : [ 0 ],
					"visible" : true
				} ],
				"order" : [ [ 0, 'desc' ] ]
			});
			document.getElementById('centralStorereceiveMstListTable_length').style.display = 'none';
			//document.getElementById('centralStorereceiveMstListTable_filter').style.display = 'none';
		});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
