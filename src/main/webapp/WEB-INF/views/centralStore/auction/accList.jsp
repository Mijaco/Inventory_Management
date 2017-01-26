<%@include file="../../common/auctionHeader.jsp"%>

<input type="hidden" id="contextPath"
	value="${pageContext.request.contextPath}" />

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue ubuntu-font" style="margin-top: 10px;">
			Auction Committee List</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty auctionCommitteeList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty auctionCommitteeList}">
				<table id="dataList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Auction Name</td>
							<td>Auction Category</td>
							<td>Count Date</td>
							<td>Convener</td>
							<td>Memo No</td>
							<td>Committee Doc.</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${auctionCommitteeList}" var="mst"
							varStatus="loop">
							<tr>
								<td>${mst.id}</td>

								<td>${mst.condemnMst.auctionProcessMst.auctionName}</td>
								<td>${mst.condemnMst.auctionCategory.name}</td>
								<td><fmt:formatDate
										value="${mst.condemnMst.auctionProcessMst.toDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>${mst.authUser.name} - ${mst.authUser.designation}</td>
								<td>${mst.memoNo}</td>
								<td> 
								<c:choose>
								<c:when test="${mst.docPath}">
								<form target="_blank"
											action="${pageContext.request.contextPath}/workOrder/download.do"
											method="POST">
											<input type="hidden" value="${mst.docPath}"
												name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
								</c:when>
								<c:otherwise>
								<label class="btn btn-default">No file</label>
								</c:otherwise>
								</c:choose>
								
								
								</td>
								<td>
								<a
									href="${pageContext.request.contextPath}/auction/admin/acCmtUpdate.do?id=${mst.condemnMst.id}&type=1"
									style="border-radius: 6px;" class="btn btn btn-sm btn-success" >Update Committee</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->
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
		$('#dataList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false,
				"searchable" : false
			} ],
			"order" : [ [ 0, 'desc' ] ]
		});
		document.getElementById('dataList_length').style.display = 'none';
		// document.getElementById('dataList_filter').style.display = 'none';
	});

	function dummy(id) {
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/auction/addCcConvener.do';
		var param = {
			apId : id
		};
		postSubmit(path, param, 'GET');
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
