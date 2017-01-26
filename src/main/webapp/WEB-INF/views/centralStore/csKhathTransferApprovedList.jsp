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

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Approved Project Transfer List</h1>
	</div>

	<div class="row" style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty khathTransferMstList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>No Approved Khath Transfer found on Database.</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty khathTransferMstList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<%-- 				action="${pageContext.request.contextPath}/cs/itemRecieved/rcvItmSerch.do">	 --%>
<!-- 					<form  action="" method="POST" > -->
<!-- 					<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="search" -->
<!-- 								placeholder="Search by KT No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" -->
<!-- 								name="receivedReportNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->
				<table id="centralStorereceiveMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">Transfer No</th>
							<th style="">From</th>
							<th style="">To</th>
							<th style="">Transfered By</th>
							<th style="">Transfered Date</th>
							<th style="">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${khathTransferMstList}" var="khathTransferMst">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/cs/khath/khathTransferedShow.do?transferNo=${khathTransferMst.transferNo}"
									style="text-decoration: none;"><c:out
											value="${khathTransferMst.transferNo}" /></a></td>
								<td><c:out value="${khathTransferMst.khathFrom}" /></td>
								<td><c:out value="${khathTransferMst.khathTo}" /></td>
								<td><c:out value="${khathTransferMst.createdBy}" /></td>
								<td><fmt:formatDate
										value="${khathTransferMst.createdDate}"
										pattern="dd-MM-yyyy" /> <td>
									<div class="action-buttons">										
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/cs/khath/khathTransferedShow.do?transferNo=${khathTransferMst.transferNo}">
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
						$('#centralStorereceiveMstListTable').DataTable();
						document
								.getElementById('centralStorereceiveMstListTable_length').style.display = 'none';
						//document.getElementById('centralStorereceiveMstListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		/* $(".container-fluid").append("<div>hello</div>"); */
	}
</script> <!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
									<%@include file="../common/ibcsFooter.jsp"%>